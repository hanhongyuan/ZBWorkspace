package com.sgcc.pms.zbztjc.jczhgl.sdkhzb.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.jczhgl.gg.Constants;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class SdkhzbBizc implements ISdkhzbBizc{
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(SdkhzbBizc.class);
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryResultObject query(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols ="";
		StringBuffer querySql = new StringBuffer();
		String querySq = "";
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					querySql.append(" and p.wsid in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = filterValue.split(",")[0];
					querySq =" and TJMONTH = '"+startTime+"' ";
				}
			}
		}
		
		String sql = 
				"with tab as(select p.wsmc WSDEPMC,p.wsid,decode(WQXJC,NULL,0,'',0,WQXJC)WQXJC,decode(DXHCJC,NULL,0,'',0,DXHCJC) DXHCJC,decode(DXWDJC,NULL,0,'',0,DXWDJC) DXWDJC,\n" +
						"  decode(FPJC,NULL,0,'',0,FPJC) FPJC,decode(FBJC,NULL,0,'',0,FBJC) FBJC,decode(XCWHDJC,NULL,0,'',0,XCWHDJC) XCWHDJC,\n" + 
						"  decode(DXWD,NULL,0,'',0,DXWD) DXWD,decode(WFZDJC,NULL,0,'',0,WFZDJC)WFZDJC,decode(GTQXJC,NULL,0,'',0,GTQXJC) GTQXJC,\n" + 
						"  decode(TXJC,NULL,0,'',0,TXJC) TXJC,\n" + 
						"   decode(WQXJC+ DXHCJC+DXWDJC+FPJC+FBJC+XCWHDJC+DXWD+WFZDJC+GTQXJC+TXJC,null,0,'',0,WQXJC+ DXHCJC+DXWDJC+FPJC+FBJC+XCWHDJC+DXWD+WFZDJC+GTQXJC+TXJC) HJ\n" + 
						"    from\n" + 
						"(select LINKEDPROVICEDEPT,\n" + 
						"sum(decode(monitoringtype,'018001',1,0)) WQXJC,\n" + 
						"sum(decode(monitoringtype,'018002',1,0)) TXJC,\n" + 
						"sum(decode(monitoringtype,'013001',1,0)) DXHCJC,\n" + 
						"sum(decode(monitoringtype,'013002',1,0)) DXWDJC,\n" + 
						"sum(decode(monitoringtype,'013004',1,0)) FPJC,\n" + 
						"sum(decode(monitoringtype,'013005',1,0)) FBJC,\n" + 
						"sum(decode(monitoringtype,'014001',1,0)) XCWHDJC,\n" + 
						"  sum(decode(monitoringtype,'013006',1,0)) DXWD,\n" + 
						"  sum(decode(monitoringtype,'013003',1,0)) WFZDJC,\n" + 
						"sum(decode(monitoringtype,'012001',1,0)) GTQXJC\n" + 
						"  from (SELECT monitoringtype ,LINKEDPROVICEDEPT FROM mw_app.cmst_dataindex_scored where 1=1 "+querySq+"\n" + 
						"UNION all\n" + 
						"SELECT monitoringtype ,LINKEDPROVICEDEPT FROM cmst_imageindex_scored  where 1=1 "+querySq+")\n" + 
						" group by LINKEDPROVICEDEPT\n" + 
						") t,mw_app.cmst_zb_comm_wspz p\n" + 
						"where t.LINKEDPROVICEDEPT(+) = p.wsid "+querySql+" order by p.ZDYPX)\n" + 
						"select '国家电网公司'  WSDEPMC,'' wsid,sum(WQXJC) WQXJC,sum(DXHCJC) DXHCJC, sum(DXWDJC) DXWDJC,sum(FPJC) FPJC,\n" + 
						"  sum(FBJC) FBJC, sum(XCWHDJC) XCWHDJC, sum(DXWD) DXWD, sum(WFZDJC) WFZDJC,\n" + 
						" sum(GTQXJC) GTQXJC, sum(TXJC) TXJC,sum(HJ) HJ from tab\n" + 
						"union all\n" + 
						"select tab.* from tab";

		try{
			System.out.println(sql);
			cols = "WSDEPMC,LINKEDPROVICEDEPT,WQXJC,DXHCJC,DXWDJC,FPJC,FBJC,XCWHDJC,DXWD,WFZDJC,GTQXJC,TXJC,HJ";
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result, cols);
			qro.setItems(result);
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行输电装置考核指标计算的初始化DataGrid的查询SQL中发生异常",e);
		}
		
		return null;
	}
 	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	   private List<Map> transToColumns(List<Object[]> list, String columns)
	   {
	       List<Map> items = new ArrayList();
	       String[] cols = columns.split("\\,");
	       for (int i = 0; i < list.size(); i++){
	           Map map = new HashMap();
	           for (int m = 0; m < cols.length; m++){
	        	   map.put(cols[m].trim(), list.get(i)[m]);
	           }
	           items.add(map);
	       }
	       return items;
	   }

	@Override
	public QueryResultObject getDetailList(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		StringBuffer querySql = new StringBuffer();
		//String querySq = "";
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					querySql.append(" and T.LINKEDPROVICEDEPT in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					querySql.append(" and t.monitoringtype in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
					}
//					querySq += " and (";
//					for(int j=0; j<filterValueArr.length; j++)
//					{
//						querySq += " t.monitoringtype like '%" + filterValueArr[j] + "%' or";
//					}
//					querySq = querySq.substring(0, querySq.length()-2);
//					querySq += ")";
				} else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = filterValue.split(",")[0];
					querySql.append(" and TJMONTH = '");
					querySql.append(startTime+"'");
				}
			}
		}
	
		String sql = "select  wsmc as PROVINCE_NAME ,LINKEDLINENAME,LINKEDPOLENAME,MANUFACTURER,t.DEVICECODE,a.DEVICENAME,TYPENAME,\n" +
						"     case when DATAACCESSRATE = 0 then '0'||'%'\n" + 
						"    else DATAACCESSRATE*100||'%'\n" + 
						"    end DATAACCESSRATE, ACCESSRATESCORED,\n" + 
						"    case when IMAGEEFFECTIVERATE = 0 then '0'||'%'\n" + 
						"    else IMAGEEFFECTIVERATE*100||'%'\n" + 
						"    end IMAGEEFFECTIVERATE,\n" + 
						"     EFFECTIVERATESCORED,ALLSCORED,\n" + 
						"  BREAKDOWNSTIME,DOWNSTIMESCORED,TJMONTH  from MW_APP.CMST_IMAGEINDEX_SCORED  t ,\n" + 
						"  mw_app.cmst_linedevice  a,\n" + 
						"   mw_app.cmst_devicemonitype   b,\n" + 
						"   mw_app.cmst_monitoringtype m,\n" + 
						"    mw_app.cmst_zb_comm_wspz p\n" + 
						"  where a.devicecode = b.linkeddevice\n" + 
						"  and t.devicecode = a.devicecode\n" + 
						"   and b.monitoringtype = m.typecode\n" + 
						"   and t.monitoringtype = m.typecode\n" + 
						"  and a.linkedprovicedept = p.wsid\n" + 
						"  and b.monitoringtype is not null"  +querySql.toString();
		try{
			cols = "PROVINCE_NAME,LINKEDLINENAME,LINKEDPOLENAME,MANUFACTURER,DEVICECODE,DEVICENAME,TYPENAME,DATAACCESSRATE,ACCESSRATESCORED,IMAGEEFFECTIVERATE,EFFECTIVERATESCORED,ALLSCORED,BREAKDOWNSTIME,DOWNSTIMESCORED,TJMONTH";
			
			System.out.println(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行输电装置考核指标详情时出错",e);
		}
		
		return null;
	}


	@Override
	public QueryResultObject getdataDetailList(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		StringBuffer querySql = new StringBuffer();
		//String querySq = "";
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					querySql.append(" and T.LINKEDPROVICEDEPT in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					querySql.append(" and t.monitoringtype in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
					}
//					querySq += " and (";
//					for(int j=0; j<filterValueArr.length; j++)
//					{
//						querySq += " t.monitoringtype like '%" + filterValueArr[j] + "%' or";
//					}
//					querySq = querySq.substring(0, querySq.length()-2);
//					querySq += ")";
				} else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = filterValue.split(",")[0];
					querySql.append(" and TJMONTH = '");
					querySql.append(startTime+"'");
				}
			}
		}
	
		String sql = 
				"select  wsmc as PROVINCE_NAME ,LINKEDLINENAME,LINKEDPOLENAME,MANUFACTURER,t.DEVICECODE,a.DEVICENAME,TYPENAME,\n" +
						"     case when DATAACCESSRATE = 0 then '0'||'%'\n" + 
						"    else DATAACCESSRATE*100||'%'\n" + 
						"    end DATAACCESSRATE, ACCESSRATESCORED,\n" + 
						"    ARACCURACYRATESCORED,\n" + 
						"     ERRORRATESCORED,ALLSCORED,\n" + 
						"  BREAKDOWNSTIME,DOWNSTIMESCORED,TJMONTH  from MW_APP.cmst_dataindex_scored  t ,\n" + 
						"  mw_app.cmst_linedevice  a,\n" + 
						"   mw_app.cmst_devicemonitype   b,\n" + 
						"   mw_app.cmst_monitoringtype m,\n" + 
						"    mw_app.cmst_zb_comm_wspz p\n" + 
						"  where a.devicecode = b.linkeddevice\n" + 
						"  and t.devicecode = a.devicecode\n" + 
						"   and b.monitoringtype = m.typecode\n" + 
						"   and t.monitoringtype = m.typecode\n" + 
						"  and a.linkedprovicedept = p.wsid\n" + 
						"  and b.monitoringtype is not null"  +querySql.toString();
		try{
			cols = "PROVINCE_NAME,LINKEDLINENAME,LINKEDPOLENAME,MANUFACTURER,DEVICECODE,DEVICENAME,TYPENAME,DATAACCESSRATE,ACCESSRATESCORED,ARACCURACYRATESCORED,ERRORRATESCORED,ALLSCORED,BREAKDOWNSTIME,DOWNSTIMESCORED,TJMONTH";
			
			System.out.println(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行输电装置考核指标详情时出错",e);
		}
		
		return null;
	}
		
}
