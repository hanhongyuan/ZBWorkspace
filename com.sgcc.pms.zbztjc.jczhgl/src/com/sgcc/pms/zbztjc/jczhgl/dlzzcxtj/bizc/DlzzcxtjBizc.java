package com.sgcc.pms.zbztjc.jczhgl.dlzzcxtj.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.jczhgl.gg.Constants;
import com.sgcc.pms.zbztjc.util.loggerSave.bizc.ILoggerSaveBizc;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
/**
 * 用户定义逻辑构件
 * 
 * @author admin 
 * 
 */
public class DlzzcxtjBizc implements IDlzzcxtjBizc{
	private final Log log = LogFactory.getLog(DlzzcxtjBizc.class);
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}
	@Resource
	private ILoggerSaveBizc loggerSaveBizc ;

	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public QueryResultObject query(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		StringBuffer querySql = new StringBuffer();
		String querySq = "";
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					querySql.append(" and linkeddept in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and VOLTAGEGRADE  in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					querySq += "and (";
					for(int j=0;j<filterValueArr.length;j++){
						querySq +="  monitoringtypes like '%" + filterValueArr[j] + "%' or";
					}
					querySq = querySq.substring(0, querySq.length()-2);
					querySql.append(querySq+") ");
				}else if (Constants.YXZT.equals(filterKey)) {
					querySql.append(" and STATUS  in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				}else if (Constants.TYRQ.equals(filterKey)) {
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
				}else if (Constants.DLMC.equals(filterKey)){
					querySql.append(" and LINKEDCABLEANDCHANNELName LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}
			}
		}
		
		
		String cols  = "OBJ_ID,LINKEDDEPWS,DEPMC,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,MONITORINGTYPES,DEVICECODE,JCXX,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,STATUS,SFSS,IFSS"; 
		 String sql ="WITH tab AS(SELECT  T.OBJ_ID,T.LINKEDDEPWS,T.DEPMC,T.LINKEDCABLEANDCHANNELName,T.LinkedEquipmentName,T.DEVICECATEGORY,T.DEVICECATEGORY_DISPLAY,T.MONITORINGTYPES,T.DEVICECODE,T.JCXX,T.DEVICENAME,T.DEVICEMODEL,T.MANUFACTURER,T.RUNDATE,T.STATUS,(CASE WHEN (SELECT COUNT(1) FROM MW_APP.CMSV_DEVICEUSED_INFO DI WHERE \n"+
				" DI.ZZBM = T.DEVICECODE AND DI.SFSS = 'T')>0 THEN 'T' ELSE 'F' END) SFSS FROM \n"+
				" (SELECT OBJ_ID,DEPMC,cast(t.JCXX as varchar2(42)) JCXX,linkeddept,LINKEDDEPWS,DEVICECODE,MONITORINGTYPES,LINKEDCABLEANDCHANNEL,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENT,LINKEDEQUIPMENTNAME,\n"+
						" LINKEDCAC_DISPLAY,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,STATUS \n"+
						" FROM mw_app.cmsv_cabledevice_xtf t  where 1 = 1 and t.DEPMC is not null and LINKEDDEPWS is not null "+querySql.toString()+"  order by t.XH ) T where t.DEPMC NOT LIKE '%电网%' AND t.DEPMC NOT LIKE '%分部%' \n"+ 
						"  AND MONITORINGTYPES LIKE '03%' AND t.linkeddept IS NOT NULL)  SELECT tab.*,DECODE(SFSS,'T','是','F','否') IFSS\n"+
						"   FROM tab where 1 = 1";
		
		try{
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			loggerSaveBizc.updataUserlog("查询", "状态监测-装置管理-电缆监测装置查询", "操作成功");
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行电缆监测装置查询统计的初始化DataGrid的查询SQL中发生异常",e);
			loggerSaveBizc.updataUserlog("查询", "状态监测-装置管理-电缆监测装置查询", "操作失败");
		}
		return null;
	}
	
	 /**
		 * 处理结果，将List中的index 转换成对应的列名
		 * 
		 * @param list
		 * @param columns
		 * @return
		 */
		public List<Map> transToColumns(List<Object[]> list, String columns) {
			List<Map> items = new ArrayList();
			String[] cols = columns.split("\\,");
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				for (int m = 0; m < cols.length; m++) {
					map.put(cols[m], list.get(i)[m]);
				}
				items.add(map);
			}
			return items;
		}
		
		@SuppressWarnings({ "unchecked", "unused" })
		public QueryResultObject statByDydj(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			String dydj = "";
			String zzzsColumnSql = "ZZZS37+ZZZS36+ZZZS35+ZZZS34+ZZZS33+ZZZS32+ZZZS30+ZZZS25+ZZZS80+ZZZS81+ZZZS86+ZZZS85+ZZZS84+ZZZS83+ZZZS82";
			String fgbdzColumnSql = "FGBDZS37+FGBDZS36+FGBDZS35+FGBDZS34+FGBDZS33+FGBDZS32+FGBDZS30+FGBDZS25+FGBDZS80+FGBDZS81+FGBDZS86+FGBDZS85+FGBDZS84+FGBDZS83+FGBDZS82";
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
						fgbdzColumnSql = "";
						//37,36,35
						String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
						zzzsColumnSql +="zzzs"+filterValueArr[j]+"+";
						fgbdzColumnSql +="fgbdzs"+filterValueArr[j]+"+";
						}
						zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
						fgbdzColumnSql = fgbdzColumnSql.substring(0,fgbdzColumnSql.length()-1);
						querySql.append(" and VOLTAGEGRADE in ('");
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
					}else if (Constants.DLMC.equals(filterKey)){
						querySql.append(" and LINKEDCABLEANDCHANNELName LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}
				}
			}
			System.out.println();
			System.out.println();
			System.out.println(zzzsColumnSql);
			System.out.println(fgbdzColumnSql);
			System.out.println();
			System.out.println();
			
			
			
			String sql= "with xxx as (select temp.*,\n" +
					"    (" + zzzsColumnSql + ") allzzzs,\n" + 
					"    (" + fgbdzColumnSql + ") allfgbdzs\n" + 
					"   from (\n" + 
					" select (select wsmc from MW_APP.cmst_zb_comm_wspz where wsid=linkedprovicedept) SSWSMC,\n" + 
					"  sum(zzzs37) zzzs37, sum(fgbdzs37) fgbdzs37,\n" + 
					"  sum(zzzs36) zzzs36, sum(fgbdzs36) fgbdzs36,\n" + 
					"  sum(zzzs35) zzzs35, sum(fgbdzs35) fgbdzs35,\n" + 
					"  sum(zzzs34) zzzs34, sum(fgbdzs34) fgbdzs34,\n" + 
					"  sum(zzzs33) zzzs33, sum(fgbdzs33) fgbdzs33,\n" + 
					"  sum(zzzs32) zzzs32, sum(fgbdzs32) fgbdzs32,\n" + 
					"  sum(zzzs30) zzzs30, sum(fgbdzs30) fgbdzs30,\n" + 
					"  sum(zzzs25) zzzs25, sum(fgbdzs25) fgbdzs25,\n" + 
					"  sum(zzzs80) zzzs80, sum(fgbdzs80) fgbdzs80,\n" + 
					"  sum(zzzs81) zzzs81, sum(fgbdzs81) fgbdzs81,\n" + 
					"  sum(zzzs86) zzzs86, sum(fgbdzs86) fgbdzs86,\n" + 
					"  sum(zzzs85) zzzs85, sum(fgbdzs85) fgbdzs85,\n" + 
					"  sum(zzzs84) zzzs84, sum(fgbdzs84) fgbdzs84,\n" + 
					"  sum(zzzs83) zzzs83, sum(fgbdzs83) fgbdzs83,\n" + 
					"  sum(zzzs82) zzzs82, sum(fgbdzs82) fgbdzs82\n" + 
					"  ,linkedprovicedept\n" + 
					"    ,(select cast(zdypx as varchar2(10)) PMS_XH from MW_APP.cmst_zb_comm_wspz where wsid=linkedprovicedept and rownum=1) xh\n" + 
					"  from (select\n" + 
					"    linkedprovicedept,(case dydj when '37' then fgbdzs else 0 end) fgbdzs37,\n" + 
					"                (case dydj when '37' then zzzs else 0 end) zzzs37,\n" + 
					"                (case dydj when '36' then fgbdzs else 0 end) fgbdzs36,\n" + 
					"                (case dydj when '36' then zzzs else 0 end) zzzs36,\n" + 
					"                (case dydj when '35' then fgbdzs else 0 end) fgbdzs35,\n" + 
					"                (case dydj when '35' then zzzs else 0 end) zzzs35,\n" + 
					"                (case dydj when '34' then fgbdzs else 0 end) fgbdzs34,\n" + 
					"                (case dydj when '34' then zzzs else 0 end) zzzs34,\n" + 
					"                (case dydj when '33' then fgbdzs else 0 end) fgbdzs33,\n" + 
					"                (case dydj when '33' then zzzs else 0 end) zzzs33,\n" + 
					"                (case dydj when '32' then fgbdzs else 0 end) fgbdzs32,\n" + 
					"                (case dydj when '32' then zzzs else 0 end) zzzs32,\n" + 
					"                (case dydj when '30' then fgbdzs else 0 end) fgbdzs30,\n" + 
					"                (case dydj when '30' then zzzs else 0 end) zzzs30,\n" + 
					"                (case dydj when '25' then fgbdzs else 0 end) fgbdzs25,\n" + 
					"                (case dydj when '25' then zzzs else 0 end) zzzs25,\n" + 
					"                (case dydj when '80' then fgbdzs else 0 end) fgbdzs80,\n" + 
					"                (case dydj when '80' then zzzs else 0 end) zzzs80,\n" + 
					"                (case dydj when '81' then fgbdzs else 0 end) fgbdzs81,\n" + 
					"                (case dydj when '81' then zzzs else 0 end) zzzs81,\n" + 
					"                (case dydj when '86' then fgbdzs else 0 end) fgbdzs86,\n" + 
					"                (case dydj when '86' then zzzs else 0 end) zzzs86,\n" + 
					"                (case dydj when '85' then fgbdzs else 0 end) fgbdzs85,\n" + 
					"                (case dydj when '85' then zzzs else 0 end) zzzs85,\n" + 
					"                (case dydj when '84' then fgbdzs else 0 end) fgbdzs84,\n" + 
					"                (case dydj when '84' then zzzs else 0 end) zzzs84,\n" + 
					"                (case dydj when '83' then fgbdzs else 0 end) fgbdzs83,\n" + 
					"                (case dydj when '83' then zzzs else 0 end) zzzs83,\n" + 
					"                (case dydj when '82' then fgbdzs else 0 end) fgbdzs82,\n" + 
					"                (case dydj when '82' then zzzs else 0 end) zzzs82\n" + 
					
					"\n" + 
					"  from\n" + 
					"  (\n" + 

					" select count(distinct a.devicecode) zzzs,\n" +
					"\t        a.VOLTAGEGRADE dydj,\n" + 
					"          count(distinct a.linkedcableandchannel) fgbdzs,\n" + 
					"\t        deps.wsid linkedprovicedept from\n" + 
					"(select * from mw_app.cmsv_cabledevice_xtf \n" + 
					"where \n"+
					"     linkeddept IS NOT NULL  AND MONITORINGTYPES LIKE '03%' and  DEPMC NOT LIKE '%电网%' AND DEPMC NOT LIKE '%分部%'    \n" + querySql.toString()+
					"  ) a ,MW_APP.cmst_zb_comm_wspz deps where deps.wsid= a.linkeddept(+) " + querySq.toString()+
					"\t  group by deps.wsid,a.VOLTAGEGRADE) "+ 
					"  )\n" + 
					"  group by linkedprovicedept\n" +
				    
					"  order by xh\n" + 
					"    ) temp\n" + 
					"    where temp.SSWSMC is not null\n" + 
					" )\n" +
					"  select '国家电网公司' sswsmc,\n" + 
					"  nvl(sum(ZZZS37),0) ZZZS37,nvl(sum(FGBDZS37),0) FGBDZS37,\n" + 																							
					"  nvl(sum(ZZZS36),0) ZZZS36,nvl(sum(FGBDZS36),0) FGBDZS36,\n" + 
					"  nvl(sum(ZZZS35),0) ZZZS35,nvl(sum(FGBDZS35),0) FGBDZS35,\n" + 
					"  nvl(sum(ZZZS34),0) ZZZS34,nvl(sum(FGBDZS34),0) FGBDZS34,\n" + 
					"  nvl(sum(ZZZS33),0) ZZZS33,nvl(sum(FGBDZS33),0) FGBDZS33,\n" + 
					"  nvl(sum(ZZZS32),0) ZZZS32,nvl(sum(FGBDZS32),0) FGBDZS32,\n" + 
					"  nvl(sum(ZZZS30),0) ZZZS30,nvl(sum(FGBDZS30),0) FGBDZS30,\n" + 
					"  nvl(sum(ZZZS25),0) ZZZS25,nvl(sum(FGBDZS25),0) FGBDZS25,\n" + 
					"  nvl(sum(ZZZS80),0) ZZZS80,nvl(sum(FGBDZS80),0) FGBDZS80,\n" + 
					"  nvl(sum(ZZZS81),0) ZZZS81,nvl(sum(FGBDZS81),0) FGBDZS81,\n" + 
					"  nvl(sum(ZZZS86),0) ZZZS86,nvl(sum(FGBDZS86),0) FGBDZS86,\n" + 
					"  nvl(sum(ZZZS85),0) ZZZS85,nvl(sum(FGBDZS85),0) FGBDZS85,\n" + 
					"  nvl(sum(ZZZS84),0) ZZZS84,nvl(sum(FGBDZS84),0) FGBDZS84,\n" + 
					"  nvl(sum(ZZZS83),0) ZZZS83,nvl(sum(FGBDZS83),0) FGBDZS83,\n" + 
					"  nvl(sum(ZZZS82),0) ZZZS82,nvl(sum(FGBDZS82),0) FGBDZS82,\n" + 
					"  '' linkedprovicedept,'' xh,\n" + 
					"   nvl(sum(ALLZZZS),0) ALLZZZS,nvl(sum(ALLFGBDZS),0) ALLFGBDZS\n" + 
					"   from xxx "+
					"   union all   "+
					"select sswsmc,zzzs37,fgbdzs37,\n" +
					"zzzs36,fgbdzs36,\n" + 
					"zzzs35,fgbdzs35,\n" + 
					"zzzs34,fgbdzs34,\n" + 
					"zzzs33,fgbdzs33,\n" +
					"zzzs32,fgbdzs32,\n" + 
					"zzzs30,fgbdzs30,\n" + 
					"zzzs25,fgbdzs25,\n" + 
					"zzzs80,fgbdzs80,\n" + 
					"zzzs81,fgbdzs81,\n" + 
					"zzzs86,fgbdzs86,\n" + 
					"zzzs85,fgbdzs85,\n" + 
					"zzzs84,fgbdzs84,\n" + 
					"zzzs83,fgbdzs83,\n" + 
					"zzzs82,fgbdzs82,\n" + 
					"linkedprovicedept,xh,\n" + 
					"allzzzs,allfgbdzs"+
					" from xxx\n";
			try{
				cols = "SSWSMC,ZZZS37,FGBDZS37,ZZZS36,FGBDZS36," +
						"ZZZS35,FGBDZS35,ZZZS34,FGBDZS34," +
						"ZZZS33,FGBDZS33,ZZZS32,FGBDZS32," +
						"ZZZS30,FGBDZS30,ZZZS25,FGBDZS25," +
						"ZZZS80,FGBDZS80,ZZZS81,FGBDZS81," +
						"ZZZS86,FGBDZS86,ZZZS85,FGBDZS85," +
						"ZZZS84,FGBDZS84,ZZZS83,FGBDZS83," +
						"ZZZS82,FGBDZS82,LINKEDPROVICEDEPT,XH,ALLZZZS,ALLFGBDZS";
						
				log.info(sql);
				result = hibernateDao_ztjc.executeSqlQuery(sql);
				result = transToColumns(result,cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
				   
				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
				loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-电缆监测装置按电压等级统计", "操作成功");
				return qro;
			}catch(Exception e){
				log.info("执行按电压等级统计监测装置信息时出错！", e);
				loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-电缆监测装置按电压等级统计", "操作失败");
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
						querySql.append(" and VOLTAGEGRADE in ('");
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
					}else if (Constants.DLMC.equals(filterKey)){
						querySql.append(" and LINKEDCABLEANDCHANNELName LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}
				}
			}
			
			try{
				cols ="SSWSMC,ZZZS031001,ZZZS031002,ZZZS031003,ZZZS031004,ZZZS032001,ZZZS032002," +
						"ZZZS032003,ZZZS032004,ZZZS032005,ZZZS032006,ZZZS032007,ZZZS032008,ZZZS032009," +
						"ZZZS032010,ZZZS032011,ZZZS032012,ZZZS032013,ZZZS032014,ZZZS032015,ZZZS032016,ZZZS032017,ZZZS032018,LINKEDPROVICEDEPT,XH,ALLZZZS";			
				String sql ="with XXX as (select temp.*,\n" +
						"(zzzs031001+zzzs031002+zzzs031003+zzzs031004+zzzs032001+zzzs032002+zzzs032003+zzzs032004+zzzs032005+zzzs032006+zzzs032007+zzzs032008+zzzs032009 \n" +
						"+zzzs032010+zzzs032011+zzzs032012+zzzs032013+zzzs032014+zzzs032015+zzzs032016+zzzs032017+zzzs032018) ALLZZZS\n" + 
						"from ( select (select wsmc from MW_APP.cmst_zb_comm_wspz where wsid=linkedprovicedept) SSWSMC,\n" + 
						"    sum(zzzs031001) zzzs031001,\n" + 
						"    sum(zzzs031002) zzzs031002,\n" + 
						"    sum(zzzs031003) zzzs031003,\n" + 
						"    sum(zzzs031004) zzzs031004,\n" + 
						"    sum(zzzs032001) zzzs032001,\n" + 
						"    sum(zzzs032002) zzzs032002,\n" + 
						"    sum(zzzs032003) zzzs032003,\n" + 
						"    sum(zzzs032004) zzzs032004,\n" + 
						"    sum(zzzs032005) zzzs032005,\n" + 
						"    sum(zzzs032006) zzzs032006,\n" + 
						"    sum(zzzs032007) zzzs032007,\n" + 
						"    sum(zzzs032008) zzzs032008,\n" + 
						"    sum(zzzs032009) zzzs032009,\n" + 
						"    sum(zzzs032010) zzzs032010,\n" + 
						"    sum(zzzs032011) zzzs032011,\n" + 
						"    sum(zzzs032012) zzzs032012,\n" + 
						"    sum(zzzs032013) zzzs032013,\n" + 
						"    sum(zzzs032014) zzzs032014,\n" +
						"    sum(zzzs032015) zzzs032015,\n" +
						"    sum(zzzs032016) zzzs032016,\n" +
						"    sum(zzzs032017) zzzs032017,\n" +
						"    sum(zzzs032018) zzzs032018\n" + 
						"  ,linkedprovicedept\n" + 
						"    ,(select cast(zdypx as varchar2(10)) PMS_XH from MW_APP.cmst_zb_comm_wspz where wsid=linkedprovicedept and rownum=1) xh\n" + 
						"from (select T.linkedprovicedept,\n" + 
						"            T.monitoringtypes,\n" +                
						"            (decode(T.monitoringtypes, '031001', zzzs, 0)) zzzs031001,  --电缆局部放电\n" + 
						"            (decode(T.monitoringtypes, '031002', zzzs, 0)) zzzs031002,  --护层接地电流\n" + 
						"            (decode(T.monitoringtypes, '031003', zzzs, 0)) zzzs031003,  --分布式光纤测温\n" + 
						"            (decode(T.monitoringtypes, '031004', zzzs, 0)) zzzs031004,  --电缆油压\n" + 
						"            (decode(T.monitoringtypes, '032001', zzzs, 0)) zzzs032001,  --水位\n" + 
						"            (decode(T.monitoringtypes, '032002', zzzs, 0)) zzzs032002,  --供电\n" + 
						"            (decode(T.monitoringtypes, '032003', zzzs, 0)) zzzs032003,  --通风\n" + 
						"            (decode(T.monitoringtypes, '032004', zzzs, 0)) zzzs032004,  --排水\n" + 
						"            (decode(T.monitoringtypes, '032005', zzzs, 0)) zzzs032005,  --照明\n" + 
						"            (decode(T.monitoringtypes, '032006', zzzs, 0)) zzzs032006,  --沉降\n" + 
						"            (decode(T.monitoringtypes, '032007', zzzs, 0)) zzzs032007,  --气体\n" + 
						"            (decode(T.monitoringtypes, '032008', zzzs, 0)) zzzs032008,  --环境温度\n" + 
						"            (decode(T.monitoringtypes, '032009', zzzs, 0)) zzzs032009,  --电子井盖\n" + 
						"            (decode(T.monitoringtypes, '032010', zzzs, 0)) zzzs032010,  --图像\n" + 
						"            (decode(T.monitoringtypes, '032011', zzzs, 0)) zzzs032011,  --视频\n" + 
						"            (decode(T.monitoringtypes, '032012', zzzs, 0)) zzzs032012,  --门禁\n" + 
						"            (decode(T.monitoringtypes, '032013', zzzs, 0)) zzzs032013,  --防火阀\n" + 
						"            (decode(T.monitoringtypes, '032014', zzzs, 0)) zzzs032014,  --红外对射\n" +
						"            (decode(T.monitoringtypes, '032015', zzzs, 0)) zzzs032015,  --声光报警\n" +
						"            (decode(T.monitoringtypes, '032016', zzzs, 0)) zzzs032016,  --火灾报警\n" +
						"            (decode(T.monitoringtypes, '032017', zzzs, 0)) zzzs032017,  --灭火装置\n" +
						"            (decode(T.monitoringtypes, '032018', zzzs, 0)) zzzs032018   --防火门\n" + 
						"       from ( "+
						"            select count(distinct a.devicecode) zzzs,\n" +
						"\t                deps.wsid linkedprovicedept,\n" + 
						"                  a.monitoringtypes from\n" + 
						"            (select * from mw_app.cmsv_cabledevice_xtf\n" + 
						"where  \n" + 
						"     linkeddept IS NOT NULL  AND MONITORINGTYPES LIKE '03%' and  DEPMC NOT LIKE '%电网%' AND DEPMC NOT LIKE '%分部%'   \n" + querySql.toString()+
						"   ) a ,MW_APP.cmst_zb_comm_wspz deps where deps.wsid= a.linkeddept(+)\n" + querySq.toString()+
						"\t  group by deps.wsid,a.monitoringtypes\n" + 	
						") T) H\n" + 
						"group by linkedprovicedept\n" + 
						"order by xh\n" + 
						"  ) temp\n" + 
						"   where temp.SSWSMC is not null )\n"+
						"select '国家电网公司' SSWSMC,\n" + 
						"   nvl(sum(zzzs031001),0) zzzs031001,\n" + 
						"    nvl(sum(zzzs031002),0) zzzs031002,\n" + 
						"    nvl(sum(zzzs031003),0) zzzs031003,\n" + 
						"    nvl(sum(zzzs031004),0) zzzs031004,\n" + 
						"    nvl(sum(zzzs032001),0) zzzs032001,\n" + 
						"    nvl(sum(zzzs032002),0) zzzs032002,\n" + 
						"    nvl(sum(zzzs032003),0) zzzs032003,\n" + 
						"    nvl(sum(zzzs032004),0) zzzs032004,\n" + 
						"    nvl(sum(zzzs032005),0) zzzs032005,\n" + 
						"    nvl(sum(zzzs032006),0) zzzs032006,\n" + 
						"    nvl(sum(zzzs032007),0) zzzs032007,\n" + 
						"    nvl(sum(zzzs032008),0) zzzs032008,\n" + 
						"    nvl(sum(zzzs032009),0) zzzs032009,\n" + 
						"    nvl(sum(zzzs032010),0) zzzs032010,\n" + 
						"    nvl(sum(zzzs032011),0) zzzs032011,\n" + 
						"    nvl(sum(zzzs032012),0) zzzs032012,\n" + 
						"    nvl(sum(zzzs032013),0) zzzs032013,\n" + 
						"    nvl(sum(zzzs032014),0) zzzs032014,\n" + 
						"    nvl(sum(zzzs032015),0) zzzs032015,\n" + 
						"    nvl(sum(zzzs032016),0) zzzs032016,\n" + 
						"    nvl(sum(zzzs032017),0) zzzs032017,\n" + 
						"    nvl(sum(zzzs032018),0) zzzs032018,\n" + 
						"    '' linkedprovicedept,'' xh ,nvl(sum(ALLZZZS),0) ALLZZZS from XXX "+
						"union all\n"+
						"   select * from XXX\n";

						
				log.info(sql);
				result = hibernateDao_ztjc.executeSqlQuery(sql);
				result = transToColumns(result,cols);
				//count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
				   
				qro.setItems(result);
				//qro.setItemCount(((Number) (count.get(0))).intValue());
				loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-电缆监测装置按监测类型统计", "操作成功");
				return qro;
			}catch(Exception e){
				log.info("执行按监测类型统计变电监测装置时出错！", e);
				e.printStackTrace();
				loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-电缆监测装置按监测类型统计", "操作失败");
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
			String fgbdzColumnSql = "";
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
						querySql.append(" and VOLTAGEGRADE in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						jclx = filterValue;
						String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
							zzzsColumnSql +="zzzs"+filterValueArr[j]+"+";
							querySql.append(" monitoringtypes like '%" + filterValueArr[i] + "%' or");
							}
							zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
							querySql.substring(0, querySql.length()-2);
							querySql.append(") ");
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
					}else if (Constants.DLMC.equals(filterKey)){
						querySql.append(" and LINKEDCABLEANDCHANNELName LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}
				}
			}
			
			String sql ="with XXX as(select temp.*,\n" +
					"(zzzs031001+zzzs031002+zzzs031003+zzzs031004+zzzs032001+zzzs032002+zzzs032003+zzzs032004+zzzs032005+zzzs032006+zzzs032007+zzzs032008+zzzs032009 \n" +
				"+zzzs032010+zzzs032011+zzzs032012+zzzs032013+zzzs032014+zzzs032015+zzzs032016+zzzs032017+zzzs032018) ALLZZZS\n" + 
					" from ( select decode(manufacturer,null,'(空)',manufacturer) SSWSMC,\n" + 
				"    sum(zzzs031001) zzzs031001,\n" + 
				"    sum(zzzs031002) zzzs031002,\n" + 
				"    sum(zzzs031003) zzzs031003,\n" + 
				"    sum(zzzs031004) zzzs031004,\n" + 
				"    sum(zzzs032001) zzzs032001,\n" + 
				"    sum(zzzs032002) zzzs032002,\n" + 
				"    sum(zzzs032003) zzzs032003,\n" + 
				"    sum(zzzs032004) zzzs032004,\n" + 
				"    sum(zzzs032005) zzzs032005,\n" + 
				"    sum(zzzs032006) zzzs032006,\n" + 
				"    sum(zzzs032007) zzzs032007,\n" + 
				"    sum(zzzs032008) zzzs032008,\n" + 
				"    sum(zzzs032009) zzzs032009,\n" + 
				"    sum(zzzs032010) zzzs032010,\n" + 
				"    sum(zzzs032011) zzzs032011,\n" + 
				"    sum(zzzs032012) zzzs032012,\n" + 
				"    sum(zzzs032013) zzzs032013,\n" + 
				"    sum(zzzs032014) zzzs032014,\n" + 
				"    sum(zzzs032015) zzzs032015,\n" + 
				"    sum(zzzs032016) zzzs032016,\n" + 
				"    sum(zzzs032017) zzzs032017,\n" + 
				"    sum(zzzs032018) zzzs032018\n" + 
					"    ,(select OBJ_ID from mw_app.cmst_manufacturer_zb a where a.name=H.manufacturer and rownum=1) manufacturer_OBJID\n" + 
					"from (select decode(T.manufacturer,null,'(空)',T.manufacturer) manufacturer,\n" + 
					"            T.monitoringtypes,\n" + 
					"            (decode(T.monitoringtypes, '031001', zzzs, 0)) zzzs031001,  --电缆局部放电\n" + 
				"            (decode(T.monitoringtypes, '031002', zzzs, 0)) zzzs031002,  --护层接地电流\n" + 
				"            (decode(T.monitoringtypes, '031003', zzzs, 0)) zzzs031003,  --分布式光纤测温\n" + 
				"            (decode(T.monitoringtypes, '031004', zzzs, 0)) zzzs031004,  --电缆油压\n" + 
				"            (decode(T.monitoringtypes, '032001', zzzs, 0)) zzzs032001,  --水位\n" + 
				"            (decode(T.monitoringtypes, '032002', zzzs, 0)) zzzs032002,  --供电\n" + 
				"            (decode(T.monitoringtypes, '032003', zzzs, 0)) zzzs032003,  --通风\n" + 
				"            (decode(T.monitoringtypes, '032004', zzzs, 0)) zzzs032004,  --排水\n" + 
				"            (decode(T.monitoringtypes, '032005', zzzs, 0)) zzzs032005,  --照明\n" + 
				"            (decode(T.monitoringtypes, '032006', zzzs, 0)) zzzs032006,  --沉降\n" + 
				"            (decode(T.monitoringtypes, '032007', zzzs, 0)) zzzs032007,  --气体\n" + 
				"            (decode(T.monitoringtypes, '032008', zzzs, 0)) zzzs032008,  --环境温度\n" + 
				"            (decode(T.monitoringtypes, '032009', zzzs, 0)) zzzs032009,  --电子井盖\n" + 
				"            (decode(T.monitoringtypes, '032010', zzzs, 0)) zzzs032010,  --图像\n" + 
				"            (decode(T.monitoringtypes, '032011', zzzs, 0)) zzzs032011,  --视频\n" + 
				"            (decode(T.monitoringtypes, '032012', zzzs, 0)) zzzs032012,  --门禁\n" + 
				"            (decode(T.monitoringtypes, '032013', zzzs, 0)) zzzs032013,  --防火阀\n" + 
				"            (decode(T.monitoringtypes, '032014', zzzs, 0)) zzzs032014,  --红外对射\n" +
				"            (decode(T.monitoringtypes, '032015', zzzs, 0)) zzzs032015,  --声光报警\n" +
				"            (decode(T.monitoringtypes, '032016', zzzs, 0)) zzzs032016,  --火灾报警\n" +
				"            (decode(T.monitoringtypes, '032017', zzzs, 0)) zzzs032017,  --灭火装置\n" +
				"            (decode(T.monitoringtypes, '032018', zzzs, 0)) zzzs032018   --防火门\n" + 
					"       from (select count(a.devicecode) zzzs,\n" + 
					"                    a.manufacturer,\n" + 
					"                    a.monitoringtypes\n" + 
					"\n" + 
					"               from mw_app.cmsv_cabledevice_xtf a where   MONITORINGTYPES LIKE '03%' " +querySql.toString()+
					" \n" +
					"        \n" + 
					"        " +
					"		and linkeddept is not null "+
					"and  DEPMC NOT LIKE '%电网%' "+
			        "AND  DEPMC NOT LIKE '%分部%' "+
					"            group by a.manufacturer, a.monitoringtypes) T) H\n" + 
					"group by manufacturer\n" + 
					"order by manufacturer\n" + 
					")temp\n" + 
					"where temp.SSWSMC is not null\n" + 
					")\n" + 
					"select '厂家合计' SSWSMC,\n" + 
					"   nvl(sum(zzzs031001),0) zzzs031001,\n" + 
				"    nvl(sum(zzzs031002),0) zzzs031002,\n" + 
				"    nvl(sum(zzzs031003),0) zzzs031003,\n" + 
				"    nvl(sum(zzzs031004),0) zzzs031004,\n" + 
				"    nvl(sum(zzzs032001),0) zzzs032001,\n" + 
				"    nvl(sum(zzzs032002),0) zzzs032002,\n" + 
				"    nvl(sum(zzzs032003),0) zzzs032003,\n" + 
				"    nvl(sum(zzzs032004),0) zzzs032004,\n" + 
				"    nvl(sum(zzzs032005),0) zzzs032005,\n" + 
				"    nvl(sum(zzzs032006),0) zzzs032006,\n" + 
				"    nvl(sum(zzzs032007),0) zzzs032007,\n" + 
				"    nvl(sum(zzzs032008),0) zzzs032008,\n" + 
				"    nvl(sum(zzzs032009),0) zzzs032009,\n" + 
				"    nvl(sum(zzzs032010),0) zzzs032010,\n" + 
				"    nvl(sum(zzzs032011),0) zzzs032011,\n" + 
				"    nvl(sum(zzzs032012),0) zzzs032012,\n" + 
				"    nvl(sum(zzzs032013),0) zzzs032013,\n" + 
				"    nvl(sum(zzzs032014),0) zzzs032014,\n" + 
				"    nvl(sum(zzzs032015),0) zzzs032015,\n" + 
				"    nvl(sum(zzzs032016),0) zzzs032016,\n" + 
				"    nvl(sum(zzzs032017),0) zzzs032017,\n" + 
				"    nvl(sum(zzzs032018),0) zzzs032018,\n" + 
					"    '' manufacturer_OBJID,nvl(sum(ALLZZZS),0) ALLZZZS from XXX "+
					"union all\n"+
					" select * from XXX\n";
		
			try{
				cols = "SSWSMC,ZZZS031001,ZZZS031002,ZZZS031003,ZZZS031004,ZZZS032001,ZZZS032002,ZZZS032003," +
						"ZZZS032004,ZZZS032005,ZZZS032006,ZZZS032007,ZZZS032008,ZZZS032009,ZZZS032010,ZZZS032011," +
						"ZZZS032012,ZZZS032013,ZZZS032014,ZZZS032015,ZZZS032016,ZZZS032017,ZZZS032018,MANUFACTURER_OBJID,ALLZZZS";
				result = hibernateDao_ztjc.executeSqlQuery(sql);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
				loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-电缆监测装置按生产厂家统计", "操作成功");
				return qro;
			}catch(Exception e){
				e.printStackTrace();
				log.info("执行按生产厂家统计电缆监测装置时出错",e);
				loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-电缆监测装置按生产厂家统计", "操作失败");
			}
			
			return null;
		}

	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		
		return null;
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
					querySql.append(" and linkeddept in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and VOLTAGEGRADE in ('");
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
	 
		String sql = " SELECT T.LINKEDCABLEANDCHANNELName,T.LinkedEquipmentName,T.DEVICECATEGORY,T.DEVICECATEGORY_DISPLAY,T.DEVICENAME,T.DEVICEMODEL,T.MANUFACTURER,T.RUNDATE,T.STATUS, CAST('查看' AS VARCHAR2(42)) LOOKUP, nvl(DECODE(SFSS, 'T', '是', 'F', '否'), '否') ISRT  " +
				"  FROM mw_app.cmsv_cabledevice_xtf T  where   T.LINKEDDEPWS IS NOT NULL " +
				" AND T.MONITORINGTYPES LIKE '03%' "+querySql.toString();
		
		try{
			cols = "LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,STATUS,LOOKUP,ISRT";
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
					querySql.append(" and t0.linkeddepws in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySq.append(" and t.VOLTAGEGRADE in ('");
					querySq.append(filterValue.replace(",", "','"));
					querySq.append("')");
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
				}else if (Constants.DLMC.equals(filterKey)){
					querySql.append(" and t.LINKEDCABLEANDCHANNELName LIKE '%");
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
		
		String sql = " (select distinct(select n.linkedcableandchannel from mw_app.cmsv_cabledevice_xtf n "+
			 " where t.linkedcableandchannel = n.linkedcableandchannel and rownum <= 1)  linkedcableandchannel,(select n.linkedcableandchannelname from mw_app.cmsv_cabledevice_xtf n where t.linkedcableandchannelname = n.linkedcableandchannelname and rownum <= 1)  linkedcableandchannelname, max(t3.dydjMC) MC,t.wsmc province_name "+
			 " from (select * from mw_app.cmsv_cabledevice_xtf t0, MW_APP.cmst_zb_comm_wspz t1 "+
			 " where t0.linkeddepws = t1.wsid  "+querySql.toString()+" ) t,mw_app.CMST_SB_PZ_SBDYDJ t3 "+
			" where t.voltagegrade = t3.dydjbm "+querySq.toString()+" and t.wsmc is not null group by t.linkedcableandchannel ,t.linkedcableandchannelname, t.wsmc) ";
		
		
		try{
			 cols = "LINKEDCABLEANDCHANNEL,LINKEDCABLEANDCHANNELNAME,MC,PROVINCE_NAME,";
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
