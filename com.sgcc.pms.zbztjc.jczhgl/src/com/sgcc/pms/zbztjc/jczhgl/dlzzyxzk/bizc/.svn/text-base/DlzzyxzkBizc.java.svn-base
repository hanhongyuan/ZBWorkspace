package com.sgcc.pms.zbztjc.jczhgl.dlzzyxzk.bizc;

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

public class DlzzyxzkBizc implements IDlzzyxzkBizc{
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(DlzzyxzkBizc.class);
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryResultObject query(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		String cols ="";
		StringBuffer querySql = new StringBuffer();
		String yearIndex = "";
		String monthIndex = "";
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					querySql.append(" and LINKEDDEPWS in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					querySql.append(" and MONITORINGTYPES in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
					}
				} else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = null;
					String endTime = null;
					int filterVlength =filterValue.length();
					if(",".equals(filterValue.substring(0, 1))){
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}else{
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}
				}else if (Constants.SCCJ.equals(filterKey)){
					querySql.append(" and MANUFACTURER LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}else if (Constants.XLMC.equals(filterKey)){
					querySql.append(" and LINKEDLINENAME LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}
			}
		}
		
		String sql = " WITH tab AS (SELECT T.*, '查看' LOOKUP  FROM MW_APP.Cmsv_Cabledevice_Xtf T \n"+
					" where 1 = 1 and T.LINKEDDEPWS IS NOT NULL  AND T.MONITORINGTYPES LIKE '03%' "+ querySql.toString() + " order by t.XH) \n"+
					" SELECT tab.*, DECODE(SFSS, 'T', '是', 'F', '否') ISRT FROM tab where 1 = 1";

		try{
			cols = "OBJ_ID,WSDEPMC,JCXH,LINKEDPROVICEDEPT,DEVICECODE,MONITORINGTYPES,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,LINKEDCAC_DISPLAY,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,JCXX,SFSS,IFSS";
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行变电装置运行状况查询统计的初始化DataGrid的查询SQL中发生异常",e);
		}
		
		return null;
	}
 
	
	@SuppressWarnings({ "unchecked", "unused" })
	public QueryResultObject statByDydj(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		String dydj = "";
		String ssjrzsColumnSql = "SSJRZZS37+SSJRZZS36+SSJRZZS35+ SSJRZZS34+SSJRZZS33+SSJRZZS86+SSJRZZS85+SSJRZZS84+SSJRZZS83+SSJRZZS82";
		String zzzsColumnSql = "zzzs37+zzzs36+zzzs35+zzzs34+zzzs33+zzzs86+zzzs85+zzzs84+zzzs83+zzzs82";
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				
				if (Constants.SSDW.equals(filterKey)) {
					querySq.append(" and wsid in ('");
					querySq.append(filterValue.replace(",", "','"));
					querySq.append("')");
					querySql.append(" and linkedprovicedept in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
					
				} else if (Constants.DYDJ.equals(filterKey)) {
					dydj = filterValue;
					zzzsColumnSql = zzzsColumnSql.substring(zzzsColumnSql.length());
					ssjrzsColumnSql = ssjrzsColumnSql.substring(ssjrzsColumnSql.length());
					//37,36,35
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					ssjrzsColumnSql+="SSJRZZS"+filterValueArr[j]+"+";
					zzzsColumnSql +="ZZZS"+filterValueArr[j]+"+";
					}
					ssjrzsColumnSql = ssjrzsColumnSql.substring(0,ssjrzsColumnSql.length()-1);
					zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					querySql.append(" and MONITORINGTYPES in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				}
				else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = null;
					String endTime = null;
					int filterVlength =filterValue.length();
					if(",".equals(filterValue.substring(0, 1))){
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}else{
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}
				}else if (Constants.SCCJ.equals(filterKey)){
					querySql.append(" and MANUFACTURER LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}else if (Constants.BDZMC.equals(filterKey)){
					querySql.append(" and LINKEDSTATIONNAME LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}
			}
		}
		System.out.println();
		System.out.println();
		System.out.println(ssjrzsColumnSql);
		System.out.println(zzzsColumnSql);
		System.out.println();
		System.out.println();
		
		
		
		//zzzs37+zzzs36+zzzs35+zzzs34+zzzs33+zzzs86+zzzs85+zzzs84+zzzs83+zzzs82
		//String queryConditionSql = " and DYDJ in('37') and linkeddepws in('63EBEC8E-E766-40D7-ACF4-FEA945102112-02711') and TYPECODE in('013005') and rundate >= to_date('2015-12-09','YYYY-MM-DD') and rundate <= to_date('2016-12-09','YYYY-MM-DD')";
		String sql="with XXX as (select temp.*,\n" +
				"     (" + zzzsColumnSql + ") allZZZS,\n" + 
				"    (" + ssjrzsColumnSql + ") allSSJRZZS,\n" + 
				"    nvl(round(((" + ssjrzsColumnSql + ")/decode(" + zzzsColumnSql + ",0,null," + zzzsColumnSql + "))*100,2),0)||'%' allSSJRL,\n" + 
				"(" + zzzsColumnSql + ")-(" + ssjrzsColumnSql + ") allWSSJRS\n" + 
				" from ( select (select wsmc from MW_APP.cmst_zb_comm_wspz where wsid=X.linkedprovicedept) SSWSMC ,\n" + 
				"  nvl(sum(ZZZS37),0) ZZZS37,nvl(sum(SSJRZZS37),0) SSJRZZS37,nvl(round((sum(SSJRZZS37)/decode(sum(ZZZS37),0,null,sum(ZZZS37)))*100,2),0)||'%' SSJRL37,nvl(sum(ZZZS37),0)-nvl(sum(SSJRZZS37),0) WSSJRS37,\n" + 
				"  nvl(sum(ZZZS36),0) ZZZS36,nvl(sum(SSJRZZS36),0) SSJRZZS36,nvl(round((sum(SSJRZZS36)/decode(sum(ZZZS36),0,null,sum(ZZZS36)))*100,2),0)||'%' SSJRL36,nvl(sum(ZZZS36),0)-nvl(sum(SSJRZZS36),0) WSSJRS36,\n" + 
				"  nvl(sum(ZZZS35),0) ZZZS35,nvl(sum(SSJRZZS35),0) SSJRZZS35,nvl(round((sum(SSJRZZS35)/decode(sum(ZZZS35),0,null,sum(ZZZS35)))*100,2),0)||'%' SSJRL35,nvl(sum(ZZZS35),0)-nvl(sum(SSJRZZS35),0) WSSJRS35,\n" + 
				"  nvl(sum(ZZZS34),0) ZZZS34,nvl(sum(SSJRZZS34),0) SSJRZZS34,nvl(round((sum(SSJRZZS34)/decode(sum(ZZZS34),0,null,sum(ZZZS34)))*100,2),0)||'%' SSJRL34,nvl(sum(ZZZS34),0)-nvl(sum(SSJRZZS34),0) WSSJRS34,\n" + 
				"  nvl(sum(ZZZS33),0) ZZZS33,nvl(sum(SSJRZZS33),0) SSJRZZS33,nvl(round((sum(SSJRZZS33)/decode(sum(ZZZS33),0,null,sum(ZZZS33)))*100,2),0)||'%' SSJRL33,nvl(sum(ZZZS33),0)-nvl(sum(SSJRZZS33),0) WSSJRS33,\n" + 
				"  nvl(sum(ZZZS32),0) ZZZS32,nvl(sum(SSJRZZS32),0) SSJRZZS32,nvl(round((sum(SSJRZZS32)/decode(sum(ZZZS32),0,null,sum(ZZZS32)))*100,2),0)||'%' SSJRL32,nvl(sum(ZZZS32),0)-nvl(sum(SSJRZZS32),0) WSSJRS32,\n" + 
				"  nvl(sum(ZZZS30),0) ZZZS30,nvl(sum(SSJRZZS30),0) SSJRZZS30,nvl(round((sum(SSJRZZS30)/decode(sum(ZZZS30),0,null,sum(ZZZS30)))*100,2),0)||'%' SSJRL30,nvl(sum(ZZZS30),0)-nvl(sum(SSJRZZS30),0) WSSJRS30,\n" + 
				"  nvl(sum(ZZZS25),0) ZZZS25,nvl(sum(SSJRZZS25),0) SSJRZZS25,nvl(round((sum(SSJRZZS25)/decode(sum(ZZZS25),0,null,sum(ZZZS25)))*100,2),0)||'%' SSJRL25,nvl(sum(ZZZS25),0)-nvl(sum(SSJRZZS25),0) WSSJRS25,\n" + 
				"  nvl(sum(ZZZS80),0) ZZZS80,nvl(sum(SSJRZZS80),0) SSJRZZS80,nvl(round((sum(SSJRZZS80)/decode(sum(ZZZS80),0,null,sum(ZZZS80)))*100,2),0)||'%' SSJRL80,nvl(sum(ZZZS80),0)-nvl(sum(SSJRZZS80),0) WSSJRS80,\n" + 
				"  nvl(sum(ZZZS81),0) ZZZS81,nvl(sum(SSJRZZS81),0) SSJRZZS81,nvl(round((sum(SSJRZZS81)/decode(sum(ZZZS81),0,null,sum(ZZZS81)))*100,2),0)||'%' SSJRL81,nvl(sum(ZZZS81),0)-nvl(sum(SSJRZZS81),0) WSSJRS81,\n" + 
				"  nvl(sum(ZZZS86),0) ZZZS86,nvl(sum(SSJRZZS86),0) SSJRZZS86,nvl(round((sum(SSJRZZS86)/decode(sum(ZZZS86),0,null,sum(ZZZS86)))*100,2),0)||'%' SSJRL86,nvl(sum(ZZZS86),0)-nvl(sum(SSJRZZS86),0) WSSJRS86,\n" + 
				"  nvl(sum(ZZZS85),0) ZZZS85,nvl(sum(SSJRZZS85),0) SSJRZZS85,nvl(round((sum(SSJRZZS85)/decode(sum(ZZZS85),0,null,sum(ZZZS85)))*100,2),0)||'%' SSJRL85,nvl(sum(ZZZS85),0)-nvl(sum(SSJRZZS85),0) WSSJRS85,\n" + 
				"  nvl(sum(ZZZS84),0) ZZZS84,nvl(sum(SSJRZZS84),0) SSJRZZS84,nvl(round((sum(SSJRZZS84)/decode(sum(ZZZS84),0,null,sum(ZZZS84)))*100,2),0)||'%' SSJRL84,nvl(sum(ZZZS84),0)-nvl(sum(SSJRZZS84),0) WSSJRS84,\n" + 
				"  nvl(sum(ZZZS83),0) ZZZS83,nvl(sum(SSJRZZS83),0) SSJRZZS83,nvl(round((sum(SSJRZZS83)/decode(sum(ZZZS83),0,null,sum(ZZZS83)))*100,2),0)||'%' SSJRL83,nvl(sum(ZZZS83),0)-nvl(sum(SSJRZZS83),0) WSSJRS83,\n" + 
				"  nvl(sum(ZZZS82),0) ZZZS82,nvl(sum(SSJRZZS82),0) SSJRZZS82,nvl(round((sum(SSJRZZS82)/decode(sum(ZZZS82),0,null,sum(ZZZS82)))*100,2),0)||'%' SSJRL82,nvl(sum(ZZZS82),0)-nvl(sum(SSJRZZS82),0) WSSJRS82\n" + 
				"  ,X.linkedprovicedept  linkedprovicedept\n" + 
				"    ,(select cast(zdypx as varchar2(10)) PMS_XH from MW_APP.cmst_zb_comm_wspz where wsid=X.linkedprovicedept and rownum=1) xh\n" + 
				"   from ( select dydj,\n" + 
				"                  linkedprovicedept,\n" + 
				"                 (decode(dydj, '37', ZZZS, 0)) ZZZS37,\n" + 
				"                 (decode(dydj, '36', ZZZS, 0)) ZZZS36,\n" + 
				"                 (decode(dydj, '35', ZZZS, 0)) ZZZS35,\n" + 
				"                 (decode(dydj, '34', ZZZS, 0)) ZZZS34,\n" + 
				"                 (decode(dydj, '33', ZZZS, 0)) ZZZS33,\n" + 
				"                 (decode(dydj, '32', ZZZS, 0)) ZZZS32,\n" + 
				"                 (decode(dydj, '30', ZZZS, 0)) ZZZS30,\n" + 
				"                 (decode(dydj, '25', ZZZS, 0)) ZZZS25,\n" + 
				"                 (decode(dydj, '80', ZZZS, 0)) ZZZS80,\n" + 
				"                 (decode(dydj, '81', ZZZS, 0)) ZZZS81,\n" + 
				"                 (decode(dydj, '86', ZZZS, 0)) ZZZS86,\n" + 
				"                 (decode(dydj, '85', ZZZS, 0)) ZZZS85,\n" + 
				"                 (decode(dydj, '84', ZZZS, 0)) ZZZS84,\n" + 
				"                 (decode(dydj, '83', ZZZS, 0)) ZZZS83,\n" + 
				"                 (decode(dydj, '82', ZZZS, 0)) ZZZS82\n" + 
				"                from\n" + 
				"                   ( "+
				
				"      select count(distinct a.devicecode) zzzs,\n" +
				"\t        a.LinkedEquipmentDY dydj,\n" + 
				"\t        count(distinct a.linkedstation) fgbdzs,\n" + 
				"\t      deps.wsid linkedprovicedept from\n" + 
				"    (select * from mw_app.CMSV_TransfDEVICE_XTF\n" + 
				"where  \n" + 
				"   LINKEDEQUIPMENTDY IS NOT NULL   and linkedprovicedept IS NOT NULL  AND MONITORINGTYPES LIKE '02%' and  WSDEPMC NOT LIKE '%电网%' AND WSDEPMC NOT LIKE '%分部%'   \n" + querySql+
				"  ) a ,MW_APP.cmst_zb_comm_wspz deps where deps.wsid= a.linkedprovicedept(+) " + querySq+
				"\t  group by deps.wsid,a.LinkedEquipmentDY "+
				") ) X\n" + 
				"               left join(\n" + 
				"                   select linkedprovicedept,dydj,\n" + 
				"                  (decode(dydj, '37', SSJRZZS, 0)) SSJRZZS37,\n" + 
				"                   (decode(dydj, '36', SSJRZZS, 0)) SSJRZZS36,\n" + 
				"                   (decode(dydj, '35', SSJRZZS, 0)) SSJRZZS35,\n" + 
				"                   (decode(dydj, '34', SSJRZZS, 0)) SSJRZZS34,\n" + 
				"                   (decode(dydj, '33', SSJRZZS, 0)) SSJRZZS33,\n" + 
				"                   (decode(dydj, '32', SSJRZZS, 0)) SSJRZZS32,\n" + 
				"                   (decode(dydj, '30', SSJRZZS, 0)) SSJRZZS30,\n" + 
				"                   (decode(dydj, '25', SSJRZZS, 0)) SSJRZZS25,\n" + 
				"                   (decode(dydj, '80', SSJRZZS, 0)) SSJRZZS80,\n" + 
				"                   (decode(dydj, '81', SSJRZZS, 0)) SSJRZZS81,\n" + 
				"                   (decode(dydj, '86', SSJRZZS, 0)) SSJRZZS86,\n" + 
				"                   (decode(dydj, '85', SSJRZZS, 0)) SSJRZZS85,\n" + 
				"                   (decode(dydj, '84', SSJRZZS, 0)) SSJRZZS84,\n" + 
				"                   (decode(dydj, '83', SSJRZZS, 0)) SSJRZZS83,\n" + 
				"                   (decode(dydj, '82', SSJRZZS, 0)) SSJRZZS82\n" + 
				"                   from (  "+
				"select a.LinkedEquipmentDY dydj,\n" +
				"\t\t                          a.linkedprovicedept,\n" + 
				"\t\t                         count(distinct devicecode) ssjrzzs\n" + 
				"\n" + 
				"\t\t                     from mw_app.CMSV_TransfDEVICE_XTF a\n" + 
				"\t\t                     where  a.linkedprovicedept is not null  AND MONITORINGTYPES LIKE '02%' and  WSDEPMC NOT LIKE '%电网%' AND WSDEPMC NOT LIKE '%分部%'   \n" + querySql+
				"                        \n" + 
				"                        AND LINKEDEQUIPMENTDY IS NOT NULL\n" + 
				"                        \n" + 
				"                       and a.devicecode in\n" + 
				"\t\t           (select ZZBM from mw_app.CMSV_DEVICEUSED_INFO where sfss='T')\n" + 
				"\t\t                    group by a.linkedprovicedept, a.LinkedEquipmentDY "+
				")\n" + 
				"                  ) Y on X.dydj=Y.dydj and X.linkedprovicedept=Y.linkedprovicedept\n" + 
				"   group by X.linkedprovicedept\n" + 
				"  order by xh\n" + 
				"   ) temp\n" + 
				"  where temp.SSWSMC is not null )\n" + 
				" select * from (  "+
				"  select "+
				"sswsmc,WSSJRS37,SSJRZZS37,zzzs37,case when zzzs37 = 0 then '-' else  to_char(substr(SSJRL37,0,length(SSJRL37)-1),'990.00')||'%'  end SSJRL37,\n" +
				"  WSSJRS36,SSJRZZS36,zzzs36,case when zzzs36 = 0 then '-' else  to_char(substr(SSJRL36,0,length(SSJRL36)-1),'990.00')||'%'  end SSJRL36,\n" + 
				"  WSSJRS35,SSJRZZS35,zzzs35,case when zzzs35 = 0 then '-' else  to_char(substr(SSJRL35,0,length(SSJRL35)-1),'990.00')||'%'  end SSJRL35,\n" + 
				"  WSSJRS34,SSJRZZS34,zzzs34,case when zzzs34 = 0 then '-' else  to_char(substr(SSJRL34,0,length(SSJRL34)-1),'990.00')||'%'  end SSJRL34,\n" + 
				"  WSSJRS33,SSJRZZS33,zzzs33,case when zzzs33 = 0 then '-' else  to_char(substr(SSJRL33,0,length(SSJRL33)-1),'990.00')||'%'  end SSJRL33,\n" + 
				"  WSSJRS32,SSJRZZS32,zzzs32,case when zzzs32 = 0 then '-' else  to_char(substr(SSJRL32,0,length(SSJRL32)-1),'990.00')||'%'  end SSJRL32,\n" + 
				"  WSSJRS30,SSJRZZS30,zzzs30,case when zzzs30 = 0 then '-' else  to_char(substr(SSJRL30,0,length(SSJRL30)-1),'990.00')||'%'  end SSJRL30,\n" + 
				"  WSSJRS25,SSJRZZS25,zzzs25,case when zzzs25 = 0 then '-' else  to_char(substr(SSJRL25,0,length(SSJRL25)-1),'990.00')||'%'  end SSJRL25,\n" + 
				"  WSSJRS80,SSJRZZS80,zzzs80,case when zzzs80 = 0 then '-' else  to_char(substr(SSJRL80,0,length(SSJRL80)-1),'990.00')||'%'  end SSJRL80,\n" + 
				"  WSSJRS81,SSJRZZS81,zzzs81,case when zzzs81 = 0 then '-' else  to_char(substr(SSJRL81,0,length(SSJRL81)-1),'990.00')||'%'  end SSJRL81,\n" + 
				"  WSSJRS86,SSJRZZS86,zzzs86,case when zzzs86 = 0 then '-' else  to_char(substr(SSJRL86,0,length(SSJRL86)-1),'990.00')||'%'  end SSJRL86,\n" + 
				"  WSSJRS85,SSJRZZS85,zzzs85,case when zzzs85 = 0 then '-' else  to_char(substr(SSJRL85,0,length(SSJRL85)-1),'990.00')||'%'  end SSJRL85,\n" + 
				"  WSSJRS84,SSJRZZS84,zzzs84,case when zzzs84 = 0 then '-' else  to_char(substr(SSJRL84,0,length(SSJRL84)-1),'990.00')||'%'  end SSJRL84,\n" + 
				"  WSSJRS83,SSJRZZS83,zzzs83,case when zzzs83 = 0 then '-' else  to_char(substr(SSJRL83,0,length(SSJRL83)-1),'990.00')||'%'  end SSJRL83,\n" + 
				"  WSSJRS82,SSJRZZS82,zzzs82,case when zzzs82 = 0 then '-' else  to_char(substr(SSJRL82,0,length(SSJRL82)-1),'990.00')||'%'  end SSJRL82,\n" + 
				"  linkedprovicedept,xh,\n" + 
				"  allWSSJRS,allSSJRZZS,allzzzs,case when allzzzs = 0 then '-' else  to_char(substr(allSSJRL,0,length(allSSJRL)-1),'990.00')||'%'  end allSSJRL "+

				" ,'1' PX  from XXX\n"+
				"union all select '国家电网公司',\n" +
				" nvl(sum(WSSJRS37),0) WSSJRSHJ37,nvl(sum(SSJRZZS37),0) SSJRZZSHJ37,nvl(sum(zzzs37),0) zzzsHJ37 ,(case when sum(zzzs37)=0 then '-' else to_char((sum(SSJRZZS37) /sum(zzzs37)*100),'990.99')||'%'end) FGLHJ37,\n" + 																										
				" nvl(sum(WSSJRS36),0) WSSJRSHJ36,nvl(sum(SSJRZZS36),0) SSJRZZSHJ36,nvl(sum(zzzs36),0) zzzsHJ36 ,(case when sum(zzzs36)=0 then '-' else to_char((sum(SSJRZZS36) /sum(zzzs36)*100),'990.99')||'%'end) FGLHJ36,\n" + 
				" nvl(sum(WSSJRS35),0) WSSJRSHJ35,nvl(sum(SSJRZZS35),0) SSJRZZSHJ35,nvl(sum(zzzs35),0) zzzsHJ35 ,(case when sum(zzzs35)=0 then '-' else to_char((sum(SSJRZZS35) /sum(zzzs35)*100),'990.99')||'%'end) FGLHJ35,\n" + 
				" nvl(sum(WSSJRS34),0) WSSJRSHJ34,nvl(sum(SSJRZZS34),0) SSJRZZSHJ34,nvl(sum(zzzs34),0) zzzsHJ34 ,(case when sum(zzzs34)=0 then '-' else to_char((sum(SSJRZZS34) /sum(zzzs34)*100),'990.99')||'%'end) FGLHJ34,\n" + 
				" nvl(sum(WSSJRS33),0) WSSJRSHJ33,nvl(sum(SSJRZZS33),0) SSJRZZSHJ33,nvl(sum(zzzs33),0) zzzsHJ33 ,(case when sum(zzzs33)=0 then '-' else to_char((sum(SSJRZZS33) /sum(zzzs33)*100),'990.99')||'%'end) FGLHJ33,\n" + 
				" nvl(sum(WSSJRS32),0) WSSJRSHJ32,nvl(sum(SSJRZZS32),0) SSJRZZSHJ32,nvl(sum(zzzs32),0) zzzsHJ32 ,(case when sum(zzzs32)=0 then '-' else to_char((sum(SSJRZZS32) /sum(zzzs32)*100),'990.99')||'%'end) FGLHJ32,\n" + 
				" nvl(sum(WSSJRS30),0) WSSJRSHJ30,nvl(sum(SSJRZZS30),0) SSJRZZSHJ30,nvl(sum(zzzs30),0) zzzsHJ30 ,(case when sum(zzzs30)=0 then '-' else to_char((sum(SSJRZZS30) /sum(zzzs30)*100),'990.99')||'%'end) FGLHJ30,\n" + 
				" nvl(sum(WSSJRS25),0) WSSJRSHJ25,nvl(sum(SSJRZZS25),0) SSJRZZSHJ25,nvl(sum(zzzs25),0) zzzsHJ25 ,(case when sum(zzzs25)=0 then '-' else to_char((sum(SSJRZZS25) /sum(zzzs25)*100),'990.99')||'%'end) FGLHJ25,\n" + 
				"\t  nvl(sum(WSSJRS80),0) WSSJRSHJ80,nvl(sum(SSJRZZS80),0) SSJRZZSHJ80,nvl(sum(zzzs80),0) zzzsHJ80 ,(case when sum(zzzs80)=0 then '-' else to_char((sum(SSJRZZS80) /sum(zzzs80)*100),'990.99')||'%'end) FGLHJ80,\n" + 
				"\t  nvl(sum(WSSJRS81),0) WSSJRSHJ81,nvl(sum(SSJRZZS81),0) SSJRZZSHJ81,nvl(sum(zzzs81),0) zzzsHJ81 ,(case when sum(zzzs81)=0 then '-' else to_char((sum(SSJRZZS81) /sum(zzzs81)*100),'990.99')||'%'end) FGLHJ81,\n" + 
				"\t  nvl(sum(WSSJRS86),0) WSSJRSHJ86,nvl(sum(SSJRZZS86),0) SSJRZZSHJ86,nvl(sum(zzzs86),0) zzzsHJ86 ,(case when sum(zzzs86)=0 then '-' else to_char((sum(SSJRZZS86) /sum(zzzs86)*100),'990.99')||'%'end) FGLHJ86,\n" + 
				"\t  nvl(sum(WSSJRS85),0) WSSJRSHJ85,nvl(sum(SSJRZZS85),0) SSJRZZSHJ85,nvl(sum(zzzs85),0) zzzsHJ85 ,(case when sum(zzzs85)=0 then '-' else to_char((sum(SSJRZZS85) /sum(zzzs85)*100),'990.99')||'%'end) FGLHJ85,\n" + 
				"\t  nvl(sum(WSSJRS84),0) WSSJRSHJ84,nvl(sum(SSJRZZS84),0) SSJRZZSHJ84,nvl(sum(zzzs84),0) zzzsHJ84 ,(case when sum(zzzs84)=0 then '-' else to_char((sum(SSJRZZS84) /sum(zzzs84)*100),'990.99')||'%'end) FGLHJ84,\n" + 
				"\t  nvl(sum(WSSJRS83),0) WSSJRSHJ83,nvl(sum(SSJRZZS83),0) SSJRZZSHJ83,nvl(sum(zzzs83),0) zzzsHJ83 ,(case when sum(zzzs83)=0 then '-' else to_char((sum(SSJRZZS83) /sum(zzzs83)*100),'990.99')||'%'end) FGLHJ83,\n" + 
				"\t  nvl(sum(WSSJRS82),0) WSSJRSHJ82,nvl(sum(SSJRZZS82),0) SSJRZZSHJ82,nvl(sum(zzzs82),0) zzzsHJ82 ,(case when sum(zzzs82)=0 then '-' else to_char((sum(SSJRZZS82) /sum(zzzs82)*100),'990.99')||'%'end) FGLHJ82,\n" + 
				"\t  '','',\n" + 
				"\t   nvl(sum(ALLWSSJRS),0) ALLWSSJRSHJ,nvl(sum(ALLSSJRZZS),0) ALLSSJRZZSHJ,nvl(sum(ALLzzzs),0) ALLzzzsHJ ,(case when sum(ALLzzzs)=0 then '-' else to_char((sum(ALLSSJRZZS) /sum(ALLzzzs)*100),'990.99')||'%'end) ALLFGLHJ\n" + 
				"\t  ,'0' PX  from xxx "+
				" ) order by PX,xh ";
		try{

			cols = "SSWSMC,WSSJRS37,SSJRZZS37,ZZZS37,SSJRL37,WSSJRS36,SSJRZZS36,ZZZS36,SSJRL36," +
					"WSSJRS35,SSJRZZS35,ZZZS35,SSJRL35,WSSJRS34,SSJRZZS34,ZZZS34,SSJRL34,WSSJRS33," +
					"SSJRZZS33,ZZZS33,SSJRL33,WSSJRS32,SSJRZZS32,ZZZS32,SSJRL32,WSSJRS30," +
					"SSJRZZS30,ZZZS30,SSJRL30,WSSJRS25,SSJRZZS25,ZZZS25,SSJRL25,WSSJRS80," +
					"SSJRZZS80,ZZZS80,SSJRL80,WSSJRS81,SSJRZZS81,ZZZS81,SSJRL81,WSSJRS86," +
					"SSJRZZS86,ZZZS86,SSJRL86,WSSJRS85,SSJRZZS85,ZZZS85,SSJRL85,WSSJRS84," +
					"SSJRZZS84,ZZZS84,SSJRL84,WSSJRS83,SSJRZZS83,ZZZS83,SSJRL83,WSSJRS82," +
					"SSJRZZS82,ZZZS82,SSJRL82,LINKEDPROVICEDEPT,XH," +
					"ALLWSSJRS,ALLSSJRZZS,ALLZZZS,ALLSSJRL,PX";
			
			
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			log.info("执行按电压等级统计监测装置运行状况时出错！", e);
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	public QueryResultObject statByJclx(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		String jclx = "";
		String zzzsColumnSql = "";
		String fgbdzColumnSql = "";
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();
		StringBuffer jclxquerySql = new StringBuffer();
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();

				if (Constants.SSDW.equals(filterKey)) {
					querySq.append(" and wsid in ('");
					querySq.append(filterValue.replace(",", "','"));
					querySq.append("')");
					querySql.append(" and linkeddepws in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					jclx = filterValue;
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
						zzzsColumnSql +="zzzs"+filterValueArr[j]+"+";
						}
						zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);

					querySql.append(" and MONITORINGTYPES in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				}
				else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = null;
					String endTime = null;
					int filterVlength =filterValue.length();
					if(",".equals(filterValue.substring(0, 1))){
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}else{
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}
				}else if (Constants.SCCJ.equals(filterKey)){
					querySql.append(" and MANUFACTURER LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}else if (Constants.BDZMC.equals(filterKey)){
					querySql.append(" and LINKEDSTATIONNAME LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}
			}
		}
		
		try{
			
			
			cols ="SSWSMC,WSSJRS021001,SSJRZZS021001,ZZZS021001,SSJRL021001,WSSJRS021002," +
					"SSJRZZS021002,ZZZS021002,SSJRL021002,WSSJRS021003,SSJRZZS021003,ZZZS021003," +
					"SSJRL021003,WSSJRS021004,SSJRZZS021004,ZZZS021004,SSJRL021004,WSSJRS021005," +
					"SSJRZZS021005,ZZZS021005,SSJRL021005,WSSJRS022001,SSJRZZS022001,ZZZS022001," +
					"SSJRL022001,WSSJRS023001,SSJRZZS023001,ZZZS023001,SSJRL023001,WSSJRS024001," +
					"SSJRZZS024001,ZZZS024001,SSJRL024001,WSSJRS024002,SSJRZZS024002,ZZZS024002," +
					"SSJRL024002,WSSJRS024003,SSJRZZS024003,ZZZS024003,SSJRL024003,WSSJRS024004," +
					"SSJRZZS024004,ZZZS024004,SSJRL024004,WSSJRS024005,SSJRZZS024005,ZZZS024005," +
					"SSJRL024005,WSSJRS024006,SSJRZZS024006,ZZZS024006,SSJRL024006,WSSJRS026001," +
					"SSJRZZS026001,ZZZS026001,SSJRL026001,WSSJRS021007,SSJRZZS021007,ZZZS021007,S" +
					"SJRL021007,LINKEDPROVICEDEPT,XH,ALLWSSJRS,ALLSSJRZZS,ALLZZZS,ALLSSJRL,PX";
			
		String	sql ="with XXX as (select temp.* ,\n" +
		"(ZZZS021001+ZZZS021002+ZZZS021003+ZZZS021004+ZZZS021005+ZZZS022001+ZZZS023001+ZZZS024001+ZZZS024002+ZZZS024003+ZZZS024004+ZZZS024005+ZZZS024005+ZZZS026001+ZZZS021007) allZZZS,\n" + 
		"(SSJRZZS021001+SSJRZZS021002+SSJRZZS021003+SSJRZZS021004+SSJRZZS021005+SSJRZZS022001+SSJRZZS023001+SSJRZZS024001+SSJRZZS024002+SSJRZZS024003+SSJRZZS024004+SSJRZZS024005+SSJRZZS024005+SSJRZZS026001+SSJRZZS021007) allSSJRZZS,\n" + 
		" nvl(round(((SSJRZZS021001+SSJRZZS021002+SSJRZZS021003+SSJRZZS021004+SSJRZZS021005+SSJRZZS022001+SSJRZZS023001+SSJRZZS024001+SSJRZZS024002+SSJRZZS024003+SSJRZZS024004+SSJRZZS024005+SSJRZZS024005+SSJRZZS026001+SSJRZZS021007)/decode(ZZZS021001+ZZZS021002+ZZZS021003+ZZZS021004+ZZZS021005+ZZZS022001+ZZZS023001+ZZZS024001+ZZZS024002+ZZZS024003+ZZZS024004+ZZZS024005+ZZZS024005+ZZZS026001+ZZZS021007,0,null,ZZZS021001+ZZZS021002+ZZZS021003+ZZZS021004+ZZZS021005+ZZZS022001+ZZZS023001+ZZZS024001+ZZZS024002+ZZZS024003+ZZZS024004+ZZZS024005+ZZZS024005+ZZZS026001+ZZZS021007))*100,2),0)||'%' allSSJRL,\n" + 
		"(ZZZS021001+ZZZS021002+ZZZS021003+ZZZS021004+ZZZS021005+ZZZS022001+ZZZS023001+ZZZS024001+ZZZS024002+ZZZS024003+ZZZS024004+ZZZS024005+ZZZS024005+ZZZS026001+ZZZS021007)-(SSJRZZS021001+SSJRZZS021002+SSJRZZS021003+SSJRZZS021004+SSJRZZS021005+SSJRZZS022001+SSJRZZS023001+SSJRZZS024001+SSJRZZS024002+SSJRZZS024003+SSJRZZS024004+SSJRZZS024005+SSJRZZS024005+SSJRZZS026001+SSJRZZS021007)  allWSSJRS\n" + 
		"from( select (select wsmc from MW_APP.cmst_zb_comm_wspz where wsid=X.linkedprovicedept) SSWSMC ,\n" + 
		"  sum(ZZZS021001) ZZZS021001,nvl(sum(SSJRZZS021001),0) SSJRZZS021001,nvl(round((sum(SSJRZZS021001)/decode(sum(ZZZS021001),0,null,sum(ZZZS021001)))*100,2),0)||'%' SSJRL021001,sum(ZZZS021001)-nvl(sum(SSJRZZS021001),0) WSSJRS021001,\n" + 
		"  sum(ZZZS021002) ZZZS021002,nvl(sum(SSJRZZS021002),0) SSJRZZS021002,nvl(round((sum(SSJRZZS021002)/decode(sum(ZZZS021002),0,null,sum(ZZZS021002)))*100,2),0)||'%' SSJRL021002,sum(ZZZS021002)-nvl(sum(SSJRZZS021002),0) WSSJRS021002,\n" + 
		"  sum(ZZZS021003) ZZZS021003,nvl(sum(SSJRZZS021003),0) SSJRZZS021003,nvl(round((sum(SSJRZZS021003)/decode(sum(ZZZS021003),0,null,sum(ZZZS021003)))*100,2),0)||'%' SSJRL021003,sum(ZZZS021003)-nvl(sum(SSJRZZS021003),0) WSSJRS021003,\n" + 
		"  sum(ZZZS021004) ZZZS021004,nvl(sum(SSJRZZS021004),0) SSJRZZS021004,nvl(round((sum(SSJRZZS021004)/decode(sum(ZZZS021004),0,null,sum(ZZZS021004)))*100,2),0)||'%' SSJRL021004,sum(ZZZS021004)-nvl(sum(SSJRZZS021004),0) WSSJRS021004,\n" + 
		"  sum(ZZZS021005) ZZZS021005,nvl(sum(SSJRZZS021005),0) SSJRZZS021005,nvl(round((sum(SSJRZZS021005)/decode(sum(ZZZS021005),0,null,sum(ZZZS021005)))*100,2),0)||'%' SSJRL021005,sum(ZZZS021005)-nvl(sum(SSJRZZS021005),0) WSSJRS021005,\n" + 
		"  sum(ZZZS022001) ZZZS022001,nvl(sum(SSJRZZS022001),0) SSJRZZS022001,nvl(round((sum(SSJRZZS022001)/decode(sum(ZZZS022001),0,null,sum(ZZZS022001)))*100,2),0)||'%' SSJRL022001,sum(ZZZS022001)-nvl(sum(SSJRZZS022001),0) WSSJRS022001,\n" + 
		"  sum(ZZZS023001) ZZZS023001,nvl(sum(SSJRZZS023001),0) SSJRZZS023001,nvl(round((sum(SSJRZZS023001)/decode(sum(ZZZS023001),0,null,sum(ZZZS023001)))*100,2),0)||'%' SSJRL023001,sum(ZZZS023001)-nvl(sum(SSJRZZS023001),0) WSSJRS023001,\n" + 
		"  sum(ZZZS024001) ZZZS024001,nvl(sum(SSJRZZS024001),0) SSJRZZS024001,nvl(round((sum(SSJRZZS024001)/decode(sum(ZZZS024001),0,null,sum(ZZZS024001)))*100,2),0)||'%' SSJRL024001,sum(ZZZS024001)-nvl(sum(SSJRZZS024001),0) WSSJRS024001,\n" + 
		"  sum(ZZZS024002) ZZZS024002,nvl(sum(SSJRZZS024002),0) SSJRZZS024002,nvl(round((sum(SSJRZZS024002)/decode(sum(ZZZS024002),0,null,sum(ZZZS024002)))*100,2),0)||'%' SSJRL024002,sum(ZZZS024002)-nvl(sum(SSJRZZS024002),0) WSSJRS024002,\n" + 
		"  sum(ZZZS024003) ZZZS024003,nvl(sum(SSJRZZS024003),0) SSJRZZS024003,nvl(round((sum(SSJRZZS024003)/decode(sum(ZZZS024003),0,null,sum(ZZZS024003)))*100,2),0)||'%' SSJRL024003,sum(ZZZS024003)-nvl(sum(SSJRZZS024003),0) WSSJRS024003,\n" + 
		"  sum(ZZZS024004) ZZZS024004,nvl(sum(SSJRZZS024004),0) SSJRZZS024004,nvl(round((sum(SSJRZZS024004)/decode(sum(ZZZS024004),0,null,sum(ZZZS024004)))*100,2),0)||'%' SSJRL024004,sum(ZZZS024004)-nvl(sum(SSJRZZS024004),0) WSSJRS024004,\n" + 
		"  sum(ZZZS024005) ZZZS024005,nvl(sum(SSJRZZS024005),0) SSJRZZS024005,nvl(round((sum(SSJRZZS024005)/decode(sum(ZZZS024005),0,null,sum(ZZZS024005)))*100,2),0)||'%' SSJRL024005,sum(ZZZS024005)-nvl(sum(SSJRZZS024005),0) WSSJRS024005,\n" + 
		"  sum(ZZZS024006) ZZZS024006,nvl(sum(SSJRZZS024006),0) SSJRZZS024006,nvl(round((sum(SSJRZZS024006)/decode(sum(ZZZS024006),0,null,sum(ZZZS024006)))*100,2),0)||'%' SSJRL024006,sum(ZZZS024006)-nvl(sum(SSJRZZS024006),0) WSSJRS024006,\n" + 
		"  sum(ZZZS026001) ZZZS026001,nvl(sum(SSJRZZS026001),0) SSJRZZS026001,nvl(round((sum(SSJRZZS026001)/decode(sum(ZZZS026001),0,null,sum(ZZZS026001)))*100,2),0)||'%' SSJRL026001,sum(ZZZS026001)-nvl(sum(SSJRZZS026001),0) WSSJRS026001,\n" + 
		"  sum(ZZZS021007) ZZZS021007,nvl(sum(SSJRZZS021007),0) SSJRZZS021007,nvl(round((sum(SSJRZZS021007)/decode(sum(ZZZS021007),0,null,sum(ZZZS021007)))*100,2),0)||'%' SSJRL021007,sum(ZZZS021007)-nvl(sum(SSJRZZS021007),0) WSSJRS021007\n" + 
		"  ,X.linkedprovicedept linkedprovicedept\n" + 
		"    ,(select cast(zdypx as varchar2(10)) PMS_XH from MW_APP.cmst_zb_comm_wspz where wsid=X.linkedprovicedept and rownum=1) xh\n" + 
		"   from ( select T.linkedprovicedept,\n" + 
		"                 T.monitoringtypes,\n" + 
		"                 (decode(T.monitoringtypes, '021001', zzzs, 0)) zzzs021001,  --局部放电\n" + 
		"                 (decode(T.monitoringtypes, '021002', zzzs, 0)) zzzs021002,   --油中溶解气体\n" + 
		"                 (decode(T.monitoringtypes, '021003', zzzs, 0)) zzzs021003,   --微水\n" + 
		"                 (decode(T.monitoringtypes, '021004', zzzs, 0)) zzzs021004,  --铁芯接地电流\n" + 
		"                 (decode(T.monitoringtypes, '021005', zzzs, 0)) zzzs021005,  --顶层油温\n" + 
		"                 (decode(T.monitoringtypes, '022001', zzzs, 0)) zzzs022001,  --绝缘监测\n" + 
		"                 (decode(T.monitoringtypes, '023001', zzzs, 0)) zzzs023001,  --绝缘监测\n" + 
		"                 (decode(T.monitoringtypes, '024001', zzzs, 0)) zzzs024001,  --局部放电\n" + 
		"                 (decode(T.monitoringtypes, '024002', zzzs, 0)) zzzs024002,  --分合闸线圈电流波形\n" + 
		"                 (decode(T.monitoringtypes, '024003', zzzs, 0)) zzzs024003,  --负荷电流波形\n" + 
		"                 (decode(T.monitoringtypes, '024004', zzzs, 0)) zzzs024004,  --SF6气体压力\n" + 
		"                 (decode(T.monitoringtypes, '024005', zzzs, 0)) zzzs024005,  --SF6气体水分\n" + 
		"                 (decode(T.monitoringtypes, '024006', zzzs, 0)) zzzs024006,  --储能电机工作状态\n" + 
		"                 (decode(T.monitoringtypes, '026001', zzzs, 0)) zzzs026001,  --变电视频监测\n" + 
		"                 (decode(T.monitoringtypes, '021007', zzzs, 0)) zzzs021007  --变压器振动波谱\n" + 
		"            from ( "+
		"  select count(distinct a.devicecode) zzzs,\n" +
		"\t\t                       deps.wsid linkedprovicedept,\n" + 
		"\t                        a.monitoringtypes  from\n" + 
		"( select * from mw_app.CMSV_TransfDEVICE_XTF\n" + 
		" where \n" +
		"   LINKEDEQUIPMENTDY IS NOT NULL   and linkedprovicedept IS NOT NULL  AND MONITORINGTYPES LIKE '02%' and  WSDEPMC NOT LIKE '%电网%' AND WSDEPMC NOT LIKE '%分部%'   \n" + querySql+
		"  ) a,MW_APP.cmst_zb_comm_wspz deps where deps.wsid= a.linkedprovicedept(+) " +  querySq+
		"\t  group by deps.wsid,a.monitoringtypes\n" + 
		") T ) X\n" + 
		"               left join(\n" + 
		"                    select linkedprovicedept,monitoringtypes,\n" + 
		"                 (decode(monitoringtypes, '021001', SSJRZZS, 0)) SSJRZZS021001, --局部放电\n" + 
		"                 (decode(monitoringtypes, '021002', SSJRZZS, 0)) SSJRZZS021002, --油中溶解气体\n" + 
		"                 (decode(monitoringtypes, '021003', SSJRZZS, 0)) SSJRZZS021003, --微水\n" + 
		"                 (decode(monitoringtypes, '021004', SSJRZZS, 0)) SSJRZZS021004, --铁芯接地电流\n" + 
		"                 (decode(monitoringtypes, '021005', SSJRZZS, 0)) SSJRZZS021005, --顶层油温\n" + 
		"                 (decode(monitoringtypes, '022001', SSJRZZS, 0)) SSJRZZS022001, --绝缘监测\n" + 
		"                 (decode(monitoringtypes, '023001', SSJRZZS, 0)) SSJRZZS023001, --绝缘监测\n" + 
		"                 (decode(monitoringtypes, '024001', SSJRZZS, 0)) SSJRZZS024001, --局部放电\n" + 
		"                 (decode(monitoringtypes, '024002', SSJRZZS, 0)) SSJRZZS024002, --分合闸线圈电流波形\n" + 
		"                 (decode(monitoringtypes, '024003', SSJRZZS, 0)) SSJRZZS024003, --负荷电流波形\n" + 
		"                 (decode(monitoringtypes, '024004', SSJRZZS, 0)) SSJRZZS024004, --SF6气体压力\n" + 
		"                 (decode(monitoringtypes, '024005', SSJRZZS, 0)) SSJRZZS024005, --SF6气体水分\n" + 
		"                 (decode(monitoringtypes, '024006', SSJRZZS, 0)) SSJRZZS024006, --储能电机工作状态\n" + 
		"                 (decode(monitoringtypes, '026001', SSJRZZS, 0)) SSJRZZS026001, --变电视频监测\n" + 
		"                 (decode(monitoringtypes, '021007', SSJRZZS, 0)) SSJRZZS021007 --变压器振动波谱\n" + 
		"                   from (\n" + 
		"                     select   a.linkedprovicedept,\n" + 
		"                      a.monitoringtypes,\n" + 
		"                      count(distinct devicecode) ssjrzzs\n" + 
		"                     from mw_app.CMSV_TransfDEVICE_XTF a\n" + 
		"                     where  linkedprovicedept is not null  AND MONITORINGTYPES LIKE '02%' and  WSDEPMC NOT LIKE '%电网%' AND WSDEPMC NOT LIKE '%分部%'   "+querySql+
		"							\n" +
		"                         AND LINKEDEQUIPMENTDY IS NOT NULL\n" + 
		"                         "+
		"							and a.devicecode in\n" + 
		"            (select ZZBM from mw_app.CMSV_DEVICEUSED_INFO where sfss='T') " + querySq+
		"                     group by linkedprovicedept, monitoringtypes)\n" + 
		"                  ) Y on X.monitoringtypes=Y.monitoringtypes and X.linkedprovicedept=Y.linkedprovicedept\n" + 
		"   group by X.linkedprovicedept\n" + 
		"  order by xh\n" + 
		"    )temp\n" + 
		"     where temp.SSWSMC is not null )\n" + 
		" select * from ( "+
		"     select "+
		"sswsmc,WSSJRS021001,SSJRZZS021001,zzzs021001,case when zzzs021001 = 0 then '-' else  to_char(substr(SSJRL021001,0,length(SSJRL021001)-1),'990.00')||'%'  end SSJRL021001,\n" +
		"  WSSJRS021002,SSJRZZS021002,zzzs021002,case when zzzs021002 = 0 then '-' else  to_char(substr(SSJRL021002,0,length(SSJRL021002)-1),'990.00')||'%'  end SSJRL021002,\n" + 
		"  WSSJRS021003,SSJRZZS021003,zzzs021003,case when zzzs021003 = 0 then '-' else  to_char(substr(SSJRL021003,0,length(SSJRL021003)-1),'990.00')||'%'  end SSJRL021003,\n" + 
		"  WSSJRS021004,SSJRZZS021004,zzzs021004,case when zzzs021004 = 0 then '-' else  to_char(substr(SSJRL021004,0,length(SSJRL021004)-1),'990.00')||'%'  end SSJRL021004,\n" + 
		"  WSSJRS021005,SSJRZZS021005,zzzs021005,case when zzzs021005 = 0 then '-' else  to_char(substr(SSJRL021005,0,length(SSJRL021005)-1),'990.00')||'%'  end SSJRL021005,\n" + 
		"  WSSJRS022001,SSJRZZS022001,zzzs022001,case when zzzs022001 = 0 then '-' else  to_char(substr(SSJRL022001,0,length(SSJRL022001)-1),'990.00')||'%'  end SSJRL022001,\n" + 
		"  WSSJRS023001,SSJRZZS023001,zzzs023001,case when zzzs023001 = 0 then '-' else  to_char(substr(SSJRL023001,0,length(SSJRL023001)-1),'990.00')||'%'  end SSJRL023001,\n" + 
		"  WSSJRS024001,SSJRZZS024001,zzzs024001,case when zzzs024001 = 0 then '-' else  to_char(substr(SSJRL024001,0,length(SSJRL024001)-1),'990.00')||'%'  end SSJRL024001,\n" + 
		"  WSSJRS024002,SSJRZZS024002,zzzs024002,case when zzzs024002 = 0 then '-' else  to_char(substr(SSJRL024002,0,length(SSJRL024002)-1),'990.00')||'%'  end SSJRL024002,\n" + 
		"  WSSJRS024003,SSJRZZS024003,zzzs024003,case when zzzs024003 = 0 then '-' else  to_char(substr(SSJRL024003,0,length(SSJRL024003)-1),'990.00')||'%'  end SSJRL024003,\n" + 
		"  WSSJRS024004,SSJRZZS024004,zzzs024004,case when zzzs024004 = 0 then '-' else  to_char(substr(SSJRL024004,0,length(SSJRL024004)-1),'990.00')||'%'  end SSJRL024004,\n" + 
		"  WSSJRS024005,SSJRZZS024005,zzzs024005,case when zzzs024005 = 0 then '-' else  to_char(substr(SSJRL024005,0,length(SSJRL024005)-1),'990.00')||'%'  end SSJRL024005,\n" + 
		"  WSSJRS024006,SSJRZZS024006,zzzs024006,case when zzzs024006 = 0 then '-' else  to_char(substr(SSJRL024006,0,length(SSJRL024006)-1),'990.00')||'%'  end SSJRL024006,\n" + 
		"  WSSJRS026001,SSJRZZS026001,zzzs026001,case when zzzs026001 = 0 then '-' else  to_char(substr(SSJRL026001,0,length(SSJRL026001)-1),'990.00')||'%'  end SSJRL026001,\n" + 
		"  WSSJRS021007,SSJRZZS021007,zzzs021007,case when zzzs021007 = 0 then '-' else  to_char(substr(SSJRL021007,0,length(SSJRL021007)-1),'990.00')||'%'  end SSJRL021007,\n" + 
		"  linkedprovicedept,xh,\n" + 
		"  allWSSJRS,allSSJRZZS,allzzzs,case when allzzzs = 0 then '-' else  to_char(substr(allSSJRL,0,length(allSSJRL)-1),'990.00')||'%'  end allSSJRL "+

		" ,'1' PX from XXX\n" +
		"union all select '国家电网公司',\n" +
		" nvl(sum(WSSJRS021001),0) WSSJRSHJ021001,nvl(sum(SSJRZZS021001),0) SSJRZZSHJ021001,nvl(sum(zzzs021001),0) zzzsHJ021001 ,case when sum(zzzs021001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021001)/sum(zzzs021001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021001,\n" + 
		" nvl(sum(WSSJRS021002),0) WSSJRSHJ021002,nvl(sum(SSJRZZS021002),0) SSJRZZSHJ021002,nvl(sum(zzzs021002),0) zzzsHJ021002 ,case when sum(zzzs021002) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021002)/sum(zzzs021002))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021002,\n" + 
		" nvl(sum(WSSJRS021003),0) WSSJRSHJ021003,nvl(sum(SSJRZZS021003),0) SSJRZZSHJ021003,nvl(sum(zzzs021003),0) zzzsHJ021003 ,case when sum(zzzs021003) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021003)/sum(zzzs021003))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021003,\n" + 
		" nvl(sum(WSSJRS021004),0) WSSJRSHJ021004,nvl(sum(SSJRZZS021004),0) SSJRZZSHJ021004,nvl(sum(zzzs021004),0) zzzsHJ021004 ,case when sum(zzzs021004) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021004)/sum(zzzs021004))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021004,\n" + 
		" nvl(sum(WSSJRS021005),0) WSSJRSHJ021005,nvl(sum(SSJRZZS021005),0) SSJRZZSHJ021005,nvl(sum(zzzs021005),0) zzzsHJ021005 ,case when sum(zzzs021005) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021005)/sum(zzzs021005))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021005,\n" + 
		" nvl(sum(WSSJRS022001),0) WSSJRSHJ022001,nvl(sum(SSJRZZS022001),0) SSJRZZSHJ022001,nvl(sum(zzzs022001),0) zzzsHJ022001 ,case when sum(zzzs022001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS022001)/sum(zzzs022001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ022001,\n" + 
		" nvl(sum(WSSJRS023001),0) WSSJRSHJ023001,nvl(sum(SSJRZZS023001),0) SSJRZZSHJ023001,nvl(sum(zzzs023001),0) zzzsHJ023001 ,case when sum(zzzs023001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS023001)/sum(zzzs023001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ023001,\n" + 
		" nvl(sum(WSSJRS024001),0) WSSJRSHJ024001,nvl(sum(SSJRZZS024001),0) SSJRZZSHJ024001,nvl(sum(zzzs024001),0) zzzsHJ024001 ,case when sum(zzzs024001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024001)/sum(zzzs024001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024001,\n" + 
		"\t  nvl(sum(WSSJRS024002),0) WSSJRSHJ024002,nvl(sum(SSJRZZS024002),0) SSJRZZSHJ024002,nvl(sum(zzzs024002),0) zzzsHJ024002 ,case when sum(zzzs024002) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024002)/sum(zzzs024002))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024002,\n" + 
		"\t  nvl(sum(WSSJRS024003),0) WSSJRSHJ024003,nvl(sum(SSJRZZS024003),0) SSJRZZSHJ024003,nvl(sum(zzzs024003),0) zzzsHJ024003 ,case when sum(zzzs024003) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024003)/sum(zzzs024003))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024003,\n" + 
		"\t  nvl(sum(WSSJRS024004),0) WSSJRSHJ024004,nvl(sum(SSJRZZS024004),0) SSJRZZSHJ024004,nvl(sum(zzzs024004),0) zzzsHJ024004 ,case when sum(zzzs024004) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024004)/sum(zzzs024004))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024004,\n" + 
		"\t  nvl(sum(WSSJRS024005),0) WSSJRSHJ024005,nvl(sum(SSJRZZS024005),0) SSJRZZSHJ024005,nvl(sum(zzzs024005),0) zzzsHJ024005 ,case when sum(zzzs024005) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024005)/sum(zzzs024005))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024005,\n" + 
		"\t  nvl(sum(WSSJRS024006),0) WSSJRSHJ024006,nvl(sum(SSJRZZS024006),0) SSJRZZSHJ024006,nvl(sum(zzzs024006),0) zzzsHJ024006 ,case when sum(zzzs024006) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024006)/sum(zzzs024006))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024006,\n" + 
		"\t  nvl(sum(WSSJRS026001),0) WSSJRSHJ026001,nvl(sum(SSJRZZS026001),0) SSJRZZSHJ026001,nvl(sum(zzzs026001),0) zzzsHJ026001 ,case when sum(zzzs026001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS026001)/sum(zzzs026001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ026001,\n" + 
		"\t  nvl(sum(WSSJRS026001),0) WSSJRSHJ026001,nvl(sum(SSJRZZS026001),0) SSJRZZSHJ026001,nvl(sum(zzzs026001),0) zzzsHJ026001 ,case when sum(zzzs026001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS026001)/sum(zzzs026001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ026001,\n" + 
		"\t  '','',\n" + 
		"\t   nvl(sum(ALLWSSJRS),0) ALLWSSJRSHJ,nvl(sum(ALLSSJRZZS),0) ALLSSJRZZSHJ,nvl(sum(ALLzzzs),0) ALLzzzsHJ ,case when sum(ALLzzzs)= 0 then '-' else RTrim(to_char(round(nvl(round((sum(ALLSSJRZZS)/sum(ALLzzzs))*100,2),0), 2) || '', 'fm990.999'),'.')||'%' end ALLFGLHJ021001\n" + 
		"\t  ,'0' PX from xxx "+
		" ) order by PX,xh ";

					
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			//count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			   
			qro.setItems(result);
			//qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			log.info("执行按监测类型统计输电监测装置运行状况时出错！", e);
			e.printStackTrace();
		}

		return null;
		
	}

    
	@SuppressWarnings("unchecked")
	public QueryResultObject statBySccj(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		String jclx = "";
		String zzzsColumnSql = "";
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();

				if (Constants.SSDW.equals(filterKey)) {
					querySq.append(" and wsid in ('");
					querySq.append(filterValue.replace(",", "','"));
					querySq.append("')");
					querySql.append(" and linkeddepws in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					jclx = filterValue;
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
						zzzsColumnSql +="zzzs"+filterValueArr[j]+"+";
						}
						zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
				
					querySql.append(" and MONITORINGTYPES in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				}
				else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = null;
					String endTime = null;
					int filterVlength =filterValue.length();
					if(",".equals(filterValue.substring(0, 1))){
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}else{
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}
				}else if (Constants.SCCJ.equals(filterKey)){
					querySql.append(" and MANUFACTURER LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}else if (Constants.BDZMC.equals(filterKey)){
					querySql.append(" and LINKEDSTATIONNAME LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}
			}
		}
		
		String sql = "with XXX as (select temp.*,\n" +
		"(ZZZS021001+ZZZS021002+ZZZS021003+ZZZS021004+ZZZS021005+ZZZS022001+ZZZS023001+ZZZS024001+ZZZS024002+ZZZS024003+ZZZS024004+ZZZS024005+ZZZS024005+ZZZS026001+ZZZS021007) allZZZS,\n" + 
		"(SSJRZZS021001+SSJRZZS021002+SSJRZZS021003+SSJRZZS021004+SSJRZZS021005+SSJRZZS022001+SSJRZZS023001+SSJRZZS024001+SSJRZZS024002+SSJRZZS024003+SSJRZZS024004+SSJRZZS024005+SSJRZZS024005+SSJRZZS026001+SSJRZZS021007) allSSJRZZS,\n" + 
		" nvl(round(((SSJRZZS021001+SSJRZZS021002+SSJRZZS021003+SSJRZZS021004+SSJRZZS021005+SSJRZZS022001+SSJRZZS023001+SSJRZZS024001+SSJRZZS024002+SSJRZZS024003+SSJRZZS024004+SSJRZZS024005+SSJRZZS024005+SSJRZZS026001+SSJRZZS021007)/decode(ZZZS021001+ZZZS021002+ZZZS021003+ZZZS021004+ZZZS021005+ZZZS022001+ZZZS023001+ZZZS024001+ZZZS024002+ZZZS024003+ZZZS024004+ZZZS024005+ZZZS024005+ZZZS026001+ZZZS021007,0,null,ZZZS021001+ZZZS021002+ZZZS021003+ZZZS021004+ZZZS021005+ZZZS022001+ZZZS023001+ZZZS024001+ZZZS024002+ZZZS024003+ZZZS024004+ZZZS024005+ZZZS024005+ZZZS026001+ZZZS021007))*100,2),0)||'%' allSSJRL,\n" + 
		"(ZZZS021001+ZZZS021002+ZZZS021003+ZZZS021004+ZZZS021005+ZZZS022001+ZZZS023001+ZZZS024001+ZZZS024002+ZZZS024003+ZZZS024004+ZZZS024005+ZZZS024005+ZZZS026001+ZZZS021007)-(SSJRZZS021001+SSJRZZS021002+SSJRZZS021003+SSJRZZS021004+SSJRZZS021005+SSJRZZS022001+SSJRZZS023001+SSJRZZS024001+SSJRZZS024002+SSJRZZS024003+SSJRZZS024004+SSJRZZS024005+SSJRZZS024005+SSJRZZS026001+SSJRZZS021007)  allWSSJRS\n" + 
		"from (select decode(X.manufacturer,null,'(空)',X.manufacturer) SSWSMC,\n" + 
		"  sum(ZZZS021001) ZZZS021001,nvl(sum(SSJRZZS021001),0) SSJRZZS021001,nvl(round((sum(SSJRZZS021001)/decode(sum(ZZZS021001),0,null,sum(ZZZS021001)))*100,2),0)||'%' SSJRL021001,sum(ZZZS021001)-nvl(sum(SSJRZZS021001),0) WSSJRS021001,\n" + 
		"  sum(ZZZS021002) ZZZS021002,nvl(sum(SSJRZZS021002),0) SSJRZZS021002,nvl(round((sum(SSJRZZS021002)/decode(sum(ZZZS021002),0,null,sum(ZZZS021002)))*100,2),0)||'%' SSJRL021002,sum(ZZZS021002)-nvl(sum(SSJRZZS021002),0) WSSJRS021002,\n" + 
		"  sum(ZZZS021003) ZZZS021003,nvl(sum(SSJRZZS021003),0) SSJRZZS021003,nvl(round((sum(SSJRZZS021003)/decode(sum(ZZZS021003),0,null,sum(ZZZS021003)))*100,2),0)||'%' SSJRL021003,sum(ZZZS021003)-nvl(sum(SSJRZZS021003),0) WSSJRS021003,\n" + 
		"  sum(ZZZS021004) ZZZS021004,nvl(sum(SSJRZZS021004),0) SSJRZZS021004,nvl(round((sum(SSJRZZS021004)/decode(sum(ZZZS021004),0,null,sum(ZZZS021004)))*100,2),0)||'%' SSJRL021004,sum(ZZZS021004)-nvl(sum(SSJRZZS021004),0) WSSJRS021004,\n" + 
		"  sum(ZZZS021005) ZZZS021005,nvl(sum(SSJRZZS021005),0) SSJRZZS021005,nvl(round((sum(SSJRZZS021005)/decode(sum(ZZZS021005),0,null,sum(ZZZS021005)))*100,2),0)||'%' SSJRL021005,sum(ZZZS021005)-nvl(sum(SSJRZZS021005),0) WSSJRS021005,\n" + 
		"  sum(ZZZS022001) ZZZS022001,nvl(sum(SSJRZZS022001),0) SSJRZZS022001,nvl(round((sum(SSJRZZS022001)/decode(sum(ZZZS022001),0,null,sum(ZZZS022001)))*100,2),0)||'%' SSJRL022001,sum(ZZZS022001)-nvl(sum(SSJRZZS022001),0) WSSJRS022001,\n" + 
		"  sum(ZZZS023001) ZZZS023001,nvl(sum(SSJRZZS023001),0) SSJRZZS023001,nvl(round((sum(SSJRZZS023001)/decode(sum(ZZZS023001),0,null,sum(ZZZS023001)))*100,2),0)||'%' SSJRL023001,sum(ZZZS023001)-nvl(sum(SSJRZZS023001),0) WSSJRS023001,\n" + 
		"  sum(ZZZS024001) ZZZS024001,nvl(sum(SSJRZZS024001),0) SSJRZZS024001,nvl(round((sum(SSJRZZS024001)/decode(sum(ZZZS024001),0,null,sum(ZZZS024001)))*100,2),0)||'%' SSJRL024001,sum(ZZZS024001)-nvl(sum(SSJRZZS024001),0) WSSJRS024001,\n" + 
		"  sum(ZZZS024002) ZZZS024002,nvl(sum(SSJRZZS024002),0) SSJRZZS024002,nvl(round((sum(SSJRZZS024002)/decode(sum(ZZZS024002),0,null,sum(ZZZS024002)))*100,2),0)||'%' SSJRL024002,sum(ZZZS024002)-nvl(sum(SSJRZZS024002),0) WSSJRS024002,\n" + 
		"  sum(ZZZS024003) ZZZS024003,nvl(sum(SSJRZZS024003),0) SSJRZZS024003,nvl(round((sum(SSJRZZS024003)/decode(sum(ZZZS024003),0,null,sum(ZZZS024003)))*100,2),0)||'%' SSJRL024003,sum(ZZZS024003)-nvl(sum(SSJRZZS024003),0) WSSJRS024003,\n" + 
		"  sum(ZZZS024004) ZZZS024004,nvl(sum(SSJRZZS024004),0) SSJRZZS024004,nvl(round((sum(SSJRZZS024004)/decode(sum(ZZZS024004),0,null,sum(ZZZS024004)))*100,2),0)||'%' SSJRL024004,sum(ZZZS024004)-nvl(sum(SSJRZZS024004),0) WSSJRS024004,\n" + 
		"  sum(ZZZS024005) ZZZS024005,nvl(sum(SSJRZZS024005),0) SSJRZZS024005,nvl(round((sum(SSJRZZS024005)/decode(sum(ZZZS024005),0,null,sum(ZZZS024005)))*100,2),0)||'%' SSJRL024005,sum(ZZZS024005)-nvl(sum(SSJRZZS024005),0) WSSJRS024005,\n" + 
		"  sum(ZZZS024006) ZZZS024006,nvl(sum(SSJRZZS024006),0) SSJRZZS024006,nvl(round((sum(SSJRZZS024006)/decode(sum(ZZZS024006),0,null,sum(ZZZS024006)))*100,2),0)||'%' SSJRL024006,sum(ZZZS024006)-nvl(sum(SSJRZZS024006),0) WSSJRS024006,\n" + 
		"  sum(ZZZS026001) ZZZS026001,nvl(sum(SSJRZZS026001),0) SSJRZZS026001,nvl(round((sum(SSJRZZS026001)/decode(sum(ZZZS026001),0,null,sum(ZZZS026001)))*100,2),0)||'%' SSJRL026001,sum(ZZZS026001)-nvl(sum(SSJRZZS026001),0) WSSJRS026001,\n" + 
		"  sum(ZZZS021007) ZZZS021007,nvl(sum(SSJRZZS021007),0) SSJRZZS021007,nvl(round((sum(SSJRZZS021007)/decode(sum(ZZZS021007),0,null,sum(ZZZS021007)))*100,2),0)||'%' SSJRL021007,sum(ZZZS021007)-nvl(sum(SSJRZZS021007),0) WSSJRS021007\n" + 
		"  ,(select OBJ_ID from mw_app.cmst_manufacturer_zb a where a.name=X.manufacturer and rownum=1) manufacturer_OBJID\n" + 
		"  from ( select  decode(T.manufacturer,null,'(空)',T.manufacturer) manufacturer,\n" + 
		"                 T.monitoringtypes,\n" + 
		"                 (decode(T.monitoringtypes, '021001', zzzs, 0)) zzzs021001,  --局部放电\n" + 
		"                 (decode(T.monitoringtypes, '021002', zzzs, 0)) zzzs021002,  --油中溶解气体\n" + 
		"                 (decode(T.monitoringtypes, '021003', zzzs, 0)) zzzs021003,  --微水\n" + 
		"                 (decode(T.monitoringtypes, '021004', zzzs, 0)) zzzs021004,  --铁芯接地电流\n" + 
		"                 (decode(T.monitoringtypes, '021005', zzzs, 0)) zzzs021005,  --顶层油温\n" + 
		"                 (decode(T.monitoringtypes, '022001', zzzs, 0)) zzzs022001,  --绝缘监测\n" + 
		"                 (decode(T.monitoringtypes, '023001', zzzs, 0)) zzzs023001,  --绝缘监测\n" + 
		"                 (decode(T.monitoringtypes, '024001', zzzs, 0)) zzzs024001,  --局部放电\n" + 
		"                 (decode(T.monitoringtypes, '024002', zzzs, 0)) zzzs024002,  --分合闸线圈电流波形\n" + 
		"                 (decode(T.monitoringtypes, '024003', zzzs, 0)) zzzs024003,  --负荷电流波形\n" + 
		"                 (decode(T.monitoringtypes, '024004', zzzs, 0)) zzzs024004,  --SF6气体压力\n" + 
		"                 (decode(T.monitoringtypes, '024005', zzzs, 0)) zzzs024005,  --SF6气体水分\n" + 
		"                 (decode(T.monitoringtypes, '024006', zzzs, 0)) zzzs024006,  --储能电机工作状态\n" + 
		"                 (decode(T.monitoringtypes, '026001', zzzs, 0)) zzzs026001,  --变电视频监测\n" + 
		"                 (decode(T.monitoringtypes, '021007', zzzs, 0)) zzzs021007  --变压器振动波谱\n" + 
		"            from (select count(distinct a.devicecode) zzzs,\n" + 
		"                        a.manufacturer,\n" + 
		"                         a.monitoringtypes\n" + 
		"                    from mw_app.cmsv_transfdevice_xtF a where LINKEDEQUIPMENTDY IS NOT NULL  AND MONITORINGTYPES LIKE '02%' " +querySql+
		"					\n" +
		"        		 \n" + 
		"      			     "+
		"  and linkedprovicedept IS NOT NULL and  WSDEPMC NOT LIKE '%电网%' "+
        "AND  WSDEPMC NOT LIKE '%分部%' "+
		"                   group by a.manufacturer, a.monitoringtypes) T ) X\n" + 
		"                left join(\n" + 
		"                    select manufacturer,monitoringtypes,\n" + 
		"                  (decode(monitoringtypes, '021001', SSJRZZS, 0)) SSJRZZS021001,  --局部放电\n" + 
		"                 (decode(monitoringtypes, '021002', SSJRZZS, 0)) SSJRZZS021002,  --油中溶解气体\n" + 
		"                 (decode(monitoringtypes, '021003', SSJRZZS, 0)) SSJRZZS021003,  --微水\n" + 
		"                 (decode(monitoringtypes, '021004', SSJRZZS, 0)) SSJRZZS021004,  --铁芯接地电流\n" + 
		"                 (decode(monitoringtypes, '021005', SSJRZZS, 0)) SSJRZZS021005,  --顶层油温\n" + 
		"                 (decode(monitoringtypes, '022001', SSJRZZS, 0)) SSJRZZS022001,  --绝缘监测\n" + 
		"                 (decode(monitoringtypes, '023001', SSJRZZS, 0)) SSJRZZS023001,  --绝缘监测\n" + 
		"                 (decode(monitoringtypes, '024001', SSJRZZS, 0)) SSJRZZS024001,  --局部放电\n" + 
		"                 (decode(monitoringtypes, '024002', SSJRZZS, 0)) SSJRZZS024002,  --分合闸线圈电流波形\n" + 
		"                 (decode(monitoringtypes, '024003', SSJRZZS, 0)) SSJRZZS024003,  --负荷电流波形\n" + 
		"                 (decode(monitoringtypes, '024004', SSJRZZS, 0)) SSJRZZS024004,  --SF6气体压力\n" + 
		"                 (decode(monitoringtypes, '024005', SSJRZZS, 0)) SSJRZZS024005,  --SF6气体水分\n" + 
		"                 (decode(monitoringtypes, '024006', SSJRZZS, 0)) SSJRZZS024006,  --储能电机工作状态\n" + 
		"                 (decode(monitoringtypes, '026001', SSJRZZS, 0)) SSJRZZS026001,  --变电视频监测\n" + 
		"                 (decode(monitoringtypes, '021007', SSJRZZS, 0)) SSJRZZS021007  --变压器振动波谱\n" + 
		"                   from (\n" + 
		"                      select   a.manufacturer,\n" + 
		"                      a.monitoringtypes,\n" + 
		"                       count(distinct devicecode) ssjrzzs\n" + 
		"                      from mw_app.cmsv_transfdevice_xtF a\n" + 
		"                      where  a.devicecode in\n" + 
		"            (select ZZBM from mw_app.CMSV_DEVICEUSED_INFO where sfss='T')   AND MONITORINGTYPES LIKE '02%' " +querySql+ 
		"  \n" +
		"  AND LINKEDEQUIPMENTDY IS NOT NULL\n" + 
		"   "+
		"  and linkedprovicedept IS NOT NULL and  WSDEPMC NOT LIKE '%电网%' "+
        "AND  WSDEPMC NOT LIKE '%分部%' "+
		"                     group by manufacturer, monitoringtypes)\n" + 
		"                  ) Y on X.monitoringtypes=Y.monitoringtypes and X.manufacturer=Y.manufacturer\n" + 
		"   group by X.manufacturer\n" + 
		"  order by X.manufacturer\n" + 
		"  ) temp\n" + 
		"  where temp.SSWSMC is not null)\n" + 
		" select * from  ( "+
		"     select "+
		"sswsmc,WSSJRS021001,SSJRZZS021001,zzzs021001,case when zzzs021001 = 0 then '-' else  to_char(substr(SSJRL021001,0,length(SSJRL021001)-1),'990.00')||'%'  end SSJRL021001,\n" +
		"  WSSJRS021002,SSJRZZS021002,zzzs021002,case when zzzs021002 = 0 then '-' else  to_char(substr(SSJRL021002,0,length(SSJRL021002)-1),'990.00')||'%'  end SSJRL021002,\n" + 
		"  WSSJRS021003,SSJRZZS021003,zzzs021003,case when zzzs021003 = 0 then '-' else  to_char(substr(SSJRL021003,0,length(SSJRL021003)-1),'990.00')||'%'  end SSJRL021003,\n" + 
		"  WSSJRS021004,SSJRZZS021004,zzzs021004,case when zzzs021004 = 0 then '-' else  to_char(substr(SSJRL021004,0,length(SSJRL021004)-1),'990.00')||'%'  end SSJRL021004,\n" + 
		"  WSSJRS021005,SSJRZZS021005,zzzs021005,case when zzzs021005 = 0 then '-' else  to_char(substr(SSJRL021005,0,length(SSJRL021005)-1),'990.00')||'%'  end SSJRL021005,\n" + 
		"  WSSJRS022001,SSJRZZS022001,zzzs022001,case when zzzs022001 = 0 then '-' else  to_char(substr(SSJRL022001,0,length(SSJRL022001)-1),'990.00')||'%'  end SSJRL022001,\n" + 
		"  WSSJRS023001,SSJRZZS023001,zzzs023001,case when zzzs023001 = 0 then '-' else  to_char(substr(SSJRL023001,0,length(SSJRL023001)-1),'990.00')||'%'  end SSJRL023001,\n" + 
		"  WSSJRS024001,SSJRZZS024001,zzzs024001,case when zzzs024001 = 0 then '-' else  to_char(substr(SSJRL024001,0,length(SSJRL024001)-1),'990.00')||'%'  end SSJRL024001,\n" + 
		"  WSSJRS024002,SSJRZZS024002,zzzs024002,case when zzzs024002 = 0 then '-' else  to_char(substr(SSJRL024002,0,length(SSJRL024002)-1),'990.00')||'%'  end SSJRL024002,\n" + 
		"  WSSJRS024003,SSJRZZS024003,zzzs024003,case when zzzs024003 = 0 then '-' else  to_char(substr(SSJRL024003,0,length(SSJRL024003)-1),'990.00')||'%'  end SSJRL024003,\n" + 
		"  WSSJRS024004,SSJRZZS024004,zzzs024004,case when zzzs024004 = 0 then '-' else  to_char(substr(SSJRL024004,0,length(SSJRL024004)-1),'990.00')||'%'  end SSJRL024004,\n" + 
		"  WSSJRS024005,SSJRZZS024005,zzzs024005,case when zzzs024005 = 0 then '-' else  to_char(substr(SSJRL024005,0,length(SSJRL024005)-1),'990.00')||'%'  end SSJRL024005,\n" + 
		"  WSSJRS024006,SSJRZZS024006,zzzs024006,case when zzzs024006 = 0 then '-' else  to_char(substr(SSJRL024006,0,length(SSJRL024006)-1),'990.00')||'%'  end SSJRL024006,\n" + 
		"  WSSJRS026001,SSJRZZS026001,zzzs026001,case when zzzs026001 = 0 then '-' else  to_char(substr(SSJRL026001,0,length(SSJRL026001)-1),'990.00')||'%'  end SSJRL026001,\n" + 
		"  WSSJRS021007,SSJRZZS021007,zzzs021007,case when zzzs021007 = 0 then '-' else  to_char(substr(SSJRL021007,0,length(SSJRL021007)-1),'990.00')||'%'  end SSJRL021007,\n" + 
		"  manufacturer_OBJID,\n" + 
		"  allWSSJRS,allSSJRZZS,allzzzs,case when allzzzs = 0 then '-' else  to_char(substr(allSSJRL,0,length(allSSJRL)-1),'990.00')||'%'  end allSSJRL "+
		" ,'1' PX from XXX\n" + 
		"union all select '厂家合计',\n" +
		" nvl(sum(WSSJRS021001),0) WSSJRSHJ021001,nvl(sum(SSJRZZS021001),0) SSJRZZSHJ021001,nvl(sum(zzzs021001),0) zzzsHJ021001 ,case when sum(zzzs021001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021001)/sum(zzzs021001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021001,\n" + 
		" nvl(sum(WSSJRS021002),0) WSSJRSHJ021002,nvl(sum(SSJRZZS021002),0) SSJRZZSHJ021002,nvl(sum(zzzs021002),0) zzzsHJ021002 ,case when sum(zzzs021002) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021002)/sum(zzzs021002))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021002,\n" + 
		" nvl(sum(WSSJRS021003),0) WSSJRSHJ021003,nvl(sum(SSJRZZS021003),0) SSJRZZSHJ021003,nvl(sum(zzzs021003),0) zzzsHJ021003 ,case when sum(zzzs021003) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021003)/sum(zzzs021003))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021003,\n" + 
		" nvl(sum(WSSJRS021004),0) WSSJRSHJ021004,nvl(sum(SSJRZZS021004),0) SSJRZZSHJ021004,nvl(sum(zzzs021004),0) zzzsHJ021004 ,case when sum(zzzs021004) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021004)/sum(zzzs021004))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021004,\n" + 
		" nvl(sum(WSSJRS021005),0) WSSJRSHJ021005,nvl(sum(SSJRZZS021005),0) SSJRZZSHJ021005,nvl(sum(zzzs021005),0) zzzsHJ021005 ,case when sum(zzzs021005) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021005)/sum(zzzs021005))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021005,\n" + 
		" nvl(sum(WSSJRS022001),0) WSSJRSHJ022001,nvl(sum(SSJRZZS022001),0) SSJRZZSHJ022001,nvl(sum(zzzs022001),0) zzzsHJ022001 ,case when sum(zzzs022001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS022001)/sum(zzzs022001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ022001,\n" + 
		" nvl(sum(WSSJRS023001),0) WSSJRSHJ023001,nvl(sum(SSJRZZS023001),0) SSJRZZSHJ023001,nvl(sum(zzzs023001),0) zzzsHJ023001 ,case when sum(zzzs023001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS023001)/sum(zzzs023001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ023001,\n" + 
		" nvl(sum(WSSJRS024001),0) WSSJRSHJ024001,nvl(sum(SSJRZZS024001),0) SSJRZZSHJ024001,nvl(sum(zzzs024001),0) zzzsHJ024001 ,case when sum(zzzs024001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024001)/sum(zzzs024001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024001,\n" + 
		"\t  nvl(sum(WSSJRS024002),0) WSSJRSHJ024002,nvl(sum(SSJRZZS024002),0) SSJRZZSHJ024002,nvl(sum(zzzs024002),0) zzzsHJ024002 ,case when sum(zzzs024002) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024002)/sum(zzzs024002))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024002,\n" + 
		"\t  nvl(sum(WSSJRS024003),0) WSSJRSHJ024003,nvl(sum(SSJRZZS024003),0) SSJRZZSHJ024003,nvl(sum(zzzs024003),0) zzzsHJ024003 ,case when sum(zzzs024003) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024003)/sum(zzzs024003))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024003,\n" + 
		"\t  nvl(sum(WSSJRS024004),0) WSSJRSHJ024004,nvl(sum(SSJRZZS024004),0) SSJRZZSHJ024004,nvl(sum(zzzs024004),0) zzzsHJ024004 ,case when sum(zzzs024004) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024004)/sum(zzzs024004))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024004,\n" + 
		"\t  nvl(sum(WSSJRS024005),0) WSSJRSHJ024005,nvl(sum(SSJRZZS024005),0) SSJRZZSHJ024005,nvl(sum(zzzs024005),0) zzzsHJ024005 ,case when sum(zzzs024005) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024005)/sum(zzzs024005))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024005,\n" + 
		"\t  nvl(sum(WSSJRS024006),0) WSSJRSHJ024006,nvl(sum(SSJRZZS024006),0) SSJRZZSHJ024006,nvl(sum(zzzs024006),0) zzzsHJ024006 ,case when sum(zzzs024006) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS024006)/sum(zzzs024006))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ024006,\n" + 
		"\t  nvl(sum(WSSJRS026001),0) WSSJRSHJ026001,nvl(sum(SSJRZZS026001),0) SSJRZZSHJ026001,nvl(sum(zzzs026001),0) zzzsHJ026001 ,case when sum(zzzs026001) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS026001)/sum(zzzs026001))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ026001,\n" + 
		"\t  nvl(sum(WSSJRS021007),0) WSSJRSHJ021007,nvl(sum(SSJRZZS021007),0) SSJRZZSHJ021007,nvl(sum(zzzs021007),0) zzzsHJ021007 ,case when sum(zzzs021007) = 0 then '-' else RTrim(to_char(round(nvl(round((sum(SSJRZZS021007)/sum(zzzs021007))*100,2),0), 2) || '', 'fm990.999'),'.')||'%'  end FGLHJ021007,\n" + 
		"\t  '',\n" + 
		"\t   nvl(sum(ALLWSSJRS),0) ALLWSSJRSHJ,nvl(sum(ALLSSJRZZS),0) ALLSSJRZZSHJ,nvl(sum(ALLzzzs),0) ALLzzzsHJ ,case when sum(ALLzzzs)= 0 then '-' else RTrim(to_char(round(nvl(round((sum(ALLSSJRZZS)/sum(ALLzzzs))*100,2),0), 2) || '', 'fm990.999'),'.')||'%' end ALLFGLHJ021001\n" + 
		"\t   ,'0' PX from xxx "+
		" ) order by PX ";
		
		try{
			cols = "SSWSMC,WSSJRS021001,SSJRZZS021001,ZZZS021001,SSJRL021001,WSSJRS021002,SSJRZZS021002," +
					"ZZZS021002,SSJRL021002,WSSJRS021003,SSJRZZS021003,ZZZS021003,SSJRL021003,WSSJRS021004," +
					"SSJRZZS021004,ZZZS021004,SSJRL021004,WSSJRS021005,SSJRZZS021005,ZZZS021005,SSJRL021005," +
					"WSSJRS022001,SSJRZZS022001,ZZZS022001,SSJRL022001,WSSJRS023001,SSJRZZS023001,ZZZS023001," +
					"SSJRL023001,WSSJRS024001,SSJRZZS024001,ZZZS024001,SSJRL024001,WSSJRS024002,SSJRZZS024002," +
					"ZZZS024002,SSJRL024002,WSSJRS024003,SSJRZZS024003,ZZZS024003,SSJRL024003,WSSJRS024004," +
					"SSJRZZS024004,ZZZS024004,SSJRL024004,WSSJRS024005,SSJRZZS024005,ZZZS024005,SSJRL024005," +
					"WSSJRS024006,SSJRZZS024006,ZZZS024006,SSJRL024006,WSSJRS026001,SSJRZZS026001,ZZZS026001," +
					"SSJRL026001,WSSJRS021007,SSJRZZS021007,ZZZS021007,SSJRL021007,MANUFACTURER_OBJID,ALLWSSJRS," +
					"ALLSSJRZZS,ALLZZZS,ALLSSJRL,PX";
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行按生产厂家统计输电监测装置运行状况时出错",e);
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
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();
		StringBuffer querySccj = new StringBuffer();
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					querySql.append(" and linkeddepws in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					querySql.append(" and monitoringtypes in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
					}
				} else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = null;
					String endTime = null;
					int filterVlength =filterValue.length();
					if(",".equals(filterValue.substring(0, 1))){
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}else{
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}
				}else if (Constants.SCCJ.equals(filterKey)){
					if("(空)".equals(filterValue)){
						querySql.append(" and MANUFACTURER is null ");
					}else{
						querySql.append(" and MANUFACTURER  LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}
				}else if (Constants.BDZMC.equals(filterKey)){
					querySql.append(" and LinkedStationName LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}else if (Constants.YXZT.equals(filterKey)){
					querySql.append(" and status in ('");
					querySql.append(filterValue);
					querySql.append("') ");
				}else if (Constants.SFSS.equals(filterKey)){
					if("all".equals(filterValue.toString())){
					querySql.append(" ");
					}else if("F".equals(filterValue.toString())){
						querySql.append(" and (sfss is null or sfss = 'F') ");
					}else{	
					querySql.append(" and SFSS = '");
					querySql.append(filterValue);
					querySql.append("' ");
					}
				}
			}
		}
	
		String sql = "SELECT t.LINKEDDEPWS,t.LinkedStationName,t.DEVICENAME,mt.typename DEVICECATEGORY_DISPLAY, '查看' LOOKUP,  \n"+
               " nvl(DECODE(di.SFSS,'T','是','F','否'),'否')ISRT,t.WSDEPMC,t.LinkedEquipmentName,t.DEVICEVOLTAGE,  \n"+
               " t.DEVICECATEGORY,t.DEVICEMODEL,t.MANUFACTURER,t.RUNDATE,  \n"+
               " DT.MONITORINGTYPE MONITORINGTYPES,mt.typecode,t.LINKEDDEP,t.linkedprovicedept, T.DEVICECODE,t.OBJ_ID,t.LinkedEquipment    \n"+
               " FROM MW_APP.CMSV_TRANSFDEVICE_XTF T, MW_APP.CMST_DEVICEMONITYPE DT,mw_app.cmst_monitoringtype mt,mw_app.cmsv_deviceused_info di    \n"+
               " WHERE t.WSDEPMC NOT LIKE '%电网%' AND t.WSDEPMC NOT LIKE '%分部%'  AND t.linkedprovicedept IS NOT NULL AND T.LINKEDEQUIPMENTDY IS NOT NULL AND T.DEVICECODE = DT.LINKEDDEVICE AND dt.monitoringtype = mt.typecode and    \n"+
               " di.ZZBM (+)= dt.linkeddevice AND di.JCLX (+)= dt.monitoringtype AND T.linkedprovicedept IS NOT NULL AND T.LINKEDEQUIPMENTDY IS NOT NULL "+querySql.toString();
		
		try{
			cols = "LINKEDPROVICEDEPT,LINKEDSTATIONNAME,DEVICENAME,DEVICECATEGORY_DISPLAY,LOOKUP,ISRT,WSDEPMC,LINKEDEQUIPMENTNAME,DEVICEVOLTAGE,DEVICECATEGORY,DEVICEMODEL,MANUFACTURER,RUNDATE";
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行按生产厂家统计输电检测装置时出错",e);
		}
		
		return null;
	}


	@Override
	public QueryResultObject getFGBDZList(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();
		StringBuffer querySccj = new StringBuffer();
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					
					querySql.append(" and t.linkedprovicedept in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and t.LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					querySql.append(" and monitoringtypes in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
					}
				} else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = null;
					String endTime = null;
					int filterVlength =filterValue.length();
					if(",".equals(filterValue.substring(0, 1))){
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}else{
					startTime = filterValue.split(",")[0];
					querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
					endTime = filterValue.split(",")[1];
					querySql.append(" and RUNDATE<=to_date('" + endTime
							+ "','yyyy-MM-dd HH24:Mi:ss')");
					}
				}else if (Constants.SCCJ.equals(filterKey)){
					querySql.append(" and MANUFACTURER  LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}else if (Constants.BDZMC.equals(filterKey)){
					querySql.append(" and t.LINKEDSTATIONNAME LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}else if (Constants.YXZT.equals(filterKey)){
					querySql.append(" and status in ('");
					querySql.append(filterValue);
					querySql.append("') ");
				}else if (Constants.SFSS.equals(filterKey)){
					if("all".equals(filterValue.toString())){
					querySql.append(" ");
					}else{
					querySql.append(" and di.SFSS = '");
					querySql.append(filterValue);
					querySql.append("' ");}
				}
			}
		}
		
		String sql ="select distinct (select n.linkedstation from mw_app.cmst_transfdevice n  \n"+
                        "  where t.linkedstation = n.linkedstation and rownum <= 1) linkedstation, \n"+
                       " (select n.linkedstationname from mw_app.cmst_transfdevice n \n"+
                       "   where t.linkedstation = n.linkedstation  and rownum <= 1) linkedstationname,  max(t0.dydjMC) MC, t.wsmc province_name \n"+
                        "  from (select linkedstation,wsmc,linkedequipmentdy,linkedprovicedept,monitoringtypes,MANUFACTURER,LINKEDEQUIPMENTNAME,RELEASEDATE,RUNDATE,linkedstationname  from mw_app.CMST_transfdevice t0,  MW_APP.cmst_zb_comm_wspz t1  \n"+
                       " where t0.linkedprovicedept = t1.wsid and t0.LINKEDEQUIPMENTDY IS NOT NULL) t, mw_app.CMST_SB_PZ_SBDYDJ t0 \n"+
                       " where t.linkedequipmentdy = t0.dydjbm "+querySql.toString()+" and t.wsmc is not null \n"+
                       " group by t.linkedstation, t.wsmc ";
		try{
			 cols = "LINKEDSTATION,LINKEDSTATIONNAME,MC,PROVINCE_NAME,";
				log.info(sql);
				result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
				return qro;
			} catch (Exception e) {
				log.info("执行sql时出错！", e);
				e.printStackTrace();
			}
		return null;
	}
		
}
