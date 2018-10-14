package com.sgcc.pms.zbztjc.kqxt.jczzgl.sdzzyxzk.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.kqxt.jczzgl.util.Constants;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class SdzzyxzkBizc implements ISdzzyxzkBizc{
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(SdzzyxzkBizc.class);
	
	
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
					querySql.append(" and linkedequipmentdy in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					querySql.append(" and monitoringtypes  in ('");
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
		
		String sql = " WITH tab AS (SELECT T.*, \n"+
			         " (CASE \n"+
			         "  WHEN (SELECT COUNT(1) FROM MW_APP.CMSV_DEVICEUSED_INFO DI  \n"+
			        	"	   WHERE DI.ZZBM = T.DEVICECODE  AND DI.SFSS = 'T') > 0 THEN  'T'  ELSE  'F'  END) SFSS \n"+
			        	"	   	FROM (SELECT OBJ_ID,  LINKEDDEP, DEPMC, CAST('查看' AS VARCHAR(42)) LOOKUP, XH, LINKEDDEPWS,  WSDEPMC, \n"+
			             "    LINKEDEQUIPMENTDY, DEVICEVOLTAGE,  DYDJGDJB, LINKEDPOLE, LINKEDLINE,  LINKEDLINENAME, LINKEDPOLENAME, \n"+
			              "   DEVICECATEGORY, DEVICECATEGORYDM, DEVICECODE,  DEVICENAME, MONITORINGTYPES, \n"+
			               "  DEVICECATEGORY_DISPLAY,  DEVICEMODEL,  MANUFACTURER, RELEASEDATE,  RUNDATE, \n"+
			                " RELEASENUMBER, REMARKS, JCXX, ISACTIVATED, XTMC, linkedprovicedept \n"+
			           " FROM MW_APP.CMSV_LINEDEVICEf t \n"+
			           " where 1 = 1  and t.WSDEPMC is not null and t.DEVICEVOLTAGE is not null "+querySql.toString()+" \n"+
			           " order by t.XH) T \n"+
			   " where t.WSDEPMC NOT LIKE '%电网%' \n"+
			    " AND t.WSDEPMC NOT LIKE '%分部%' \n"+
			    " AND t.linkedprovicedept IS NOT NULL \n"+
			    " AND T.LINKEDEQUIPMENTDY IS NOT NULL \n"+
			    " AND T.MONITORINGTYPES LIKE '01%') \n"+
			" SELECT tab.*, DECODE(SFSS, 'T', '是', 'F', '否') ISRT FROM tab where 1 = 1";
				
		try{
			//cols = "OBJ_ID,WSDEPMC,DEVICECODE,LINKEDPROVICEDEPT,LINKEDLINENAME,LINKEDPOLENAME,LINKEDPOLE,DEVICEVOLTAGE,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,LOOKUP,MANUFACTURER,RUNDATE,DATAACCESSRATE,DATAARACCURACYRATE,BREAKDOWNSTIME,NUMA,ALLSCORED";
			
			cols = "OBJ_ID,LINKEDDEP,DEPMC,LOOKUP,XH,LINKEDDEPWS,WSDEPMC,LINKEDEQUIPMENTDY,DEVICEVOLTAGE,DYDJGDJB,LINKEDPOLE,LINKEDLINE,LINKEDLINENAME,LINKEDPOLENAME,DEVICECATEGORY,DEVICECATEGORYDM,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER,REMARKS,JCXX,ISACTIVATED,XTMC,LINKEDPROVICEDEPT,SFSS,ISRT";
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行输电监测装置查询统计的初始化DataGrid的查询SQL中发生异常",e);
		}
		
		return qro;
	}
 
	
	@SuppressWarnings({ "unchecked", "unused" })
	public QueryResultObject statByDydj(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		String dydj = "";
		String ssjrzsColumnSql = "SSJRZS37+SSJRZS36+SSJRZS35+ SSJRZS34+SSJRZS33+SSJRZS86+SSJRZS85+SSJRZS84+SSJRZS83+SSJRZS82";
		String zzzsColumnSql = "zzzs012001+zzzs013001+zzzs013002+zzzs013003+zzzs018003+zzzs013004+zzzs013005+zzzs014001+zzzs018001+zzzs018002+zzzs013006";
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();
		StringBuffer jclxFilterSql = new StringBuffer();
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
					ssjrzsColumnSql+="SSJRZS"+filterValueArr[j]+"+";
					zzzsColumnSql +="KHzzs"+filterValueArr[j]+"+";
					}
					ssjrzsColumnSql = ssjrzsColumnSql.substring(0,ssjrzsColumnSql.length()-1);
					zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					jclxFilterSql.append(" and MONITORINGTYPES in ('");
					jclxFilterSql.append(filterValue.replace(",", "','"));
					jclxFilterSql.append("')");
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
		
		
		String sql = "with tmp as (select temp.*,\n" +
				"     (ZZZS37+ZZZS36+ZZZS35+ ZZZS34+ZZZS33+ZZZS86+ZZZS85+ZZZS84+ZZZS83+ZZZS82) allZZZS,\n" + 
				"    (SSJRZS37+SSJRZS36+SSJRZS35+ SSJRZS34+SSJRZS33+SSJRZS86+SSJRZS85+SSJRZS84+SSJRZS83+SSJRZS82) allSSJRZS,\n" + 
				"    nvl(round(((SSJRZS37+SSJRZS36+SSJRZS35+ SSJRZS34+SSJRZS33+SSJRZS86+SSJRZS85+SSJRZS84+SSJRZS83+SSJRZS82)/decode(ZZZS37+ZZZS36+ZZZS35+ ZZZS34+ZZZS33+ZZZS86+ZZZS85+ZZZS84+ZZZS83+ZZZS82,0,null,ZZZS37+ZZZS36+ZZZS35+ ZZZS34+ZZZS33+ZZZS86+ZZZS85+ZZZS84+ZZZS83+ZZZS82))*100,2),0)||'%' allSSJRL,\n" + 
				"(ZZZS37+ZZZS36+ZZZS35+ ZZZS34+ZZZS33+ZZZS86+ZZZS85+ZZZS84+ZZZS83+ZZZS82)-(SSJRZS37+SSJRZS36+SSJRZS35+ SSJRZS34+SSJRZS33+SSJRZS86+SSJRZS85+SSJRZS84+SSJRZS83+SSJRZS82) allWSSJRS\n" + 
				"from (select a.linkedprovicedept,a.wsmc SSWSMC,NVl(sum(zzzs37),0) zzzs37,NVl(sum(zzzs36),0) zzzs36,\n" +
					"(select cast(zdypx as varchar2(10)) pms_xh from mw_app.cmst_zb_comm_wspz where wsid = a.linkedprovicedept) pms_xh,\n" + 
					"NVl(sum(zzzs35),0) zzzs35,NVl(sum(zzzs34),0) zzzs34,NVl(sum(zzzs33),0) zzzs33,\n" +
					"NVl(sum(zzzs32),0) zzzs32,NVl(sum(zzzs30),0) zzzs30,NVl(sum(zzzs25),0) zzzs25,\n" + 
					"NVl(sum(zzzs86),0) zzzs86,NVl(sum(zzzs85),0) zzzs85,\n" +
					"NVl(sum(zzzs84),0) zzzs84,NVl(sum(zzzs83),0) zzzs83,NVl(sum(zzzs82),0) zzzs82,NVl(sum(zzzs81),0) zzzs81,NVl(sum(zzzs80),0) zzzs80,\n" +
					"NVl(sum(ssjrzs37),0) ssjrzs37,NVl(sum(ssjrzs36),0) ssjrzs36,NVl(sum(ssjrzs35),0) ssjrzs35,\n" +
					"NVl(sum(ssjrzs34),0) ssjrzs34,NVl(sum(ssjrzs33),0) ssjrzs33,NVl(sum(ssjrzs32),0) ssjrzs32,\n" +
					"NVl(sum(ssjrzs30),0) ssjrzs30,NVl(sum(ssjrzs25),0) ssjrzs25,NVl(sum(ssjrzs86),0) ssjrzs86,NVl(sum(ssjrzs85),0) ssjrzs85,\n" +
					"NVl(sum(ssjrzs84),0) ssjrzs84,NVl(sum(ssjrzs83),0) ssjrzs83,NVl(sum(ssjrzs82),0) ssjrzs82,\n" +
					"NVl(sum(ssjrzs81),0) ssjrzs81,NVl(sum(ssjrzs80),0) ssjrzs80,\n" +
					"NVl(round((sum(ssjrzs37)/decode(sum(zzzs37),0,null,sum(zzzs37))*100),2),0)||'%' ssjrl37,\n" +
					"NVl(round((sum(ssjrzs36)/decode(sum(zzzs36),0,null,sum(zzzs36))*100),2),0)||'%' ssjrl36,\n" +
					"NVl(round((sum(ssjrzs35)/decode(sum(zzzs35),0,null,sum(zzzs35))*100),2),0)||'%' ssjrl35,\n" +
					"NVl(round((sum(ssjrzs34)/decode(sum(zzzs34),0,null,sum(zzzs34))*100),2),0)||'%' ssjrl34,\n" +
					"NVl(round((sum(ssjrzs33)/decode(sum(zzzs33),0,null,sum(zzzs33))*100),2),0)||'%' ssjrl33,\n" +
					"NVl(round((sum(ssjrzs32)/decode(sum(zzzs32),0,null,sum(zzzs32))*100),2),0)||'%' ssjrl32,\n" +
					"NVl(round((sum(ssjrzs30)/decode(sum(zzzs30),0,null,sum(zzzs30))*100),2),0)||'%' ssjrl30,\n" +
					"NVl(round((sum(ssjrzs25)/decode(sum(zzzs25),0,null,sum(zzzs25))*100),2),0)||'%' ssjrl25,\n" +
					"NVl(round((sum(ssjrzs86)/decode(sum(zzzs86),0,null,sum(zzzs86))*100),2),0)||'%' ssjrl86,\n" +
					"NVl(round((sum(ssjrzs85)/decode(sum(zzzs85),0,null,sum(zzzs85))*100),2),0)||'%' ssjrl85,\n" +
					"NVl(round((sum(ssjrzs84)/decode(sum(zzzs84),0,null,sum(zzzs84))*100),2),0)||'%' ssjrl84,\n" +
					"NVl(round((sum(ssjrzs83)/decode(sum(zzzs83),0,null,sum(zzzs83))*100),2),0)||'%' ssjrl83,\n" +
					"NVl(round((sum(ssjrzs82)/decode(sum(zzzs82),0,null,sum(zzzs82))*100),2),0)||'%' ssjrl82,\n" +
					"NVl(round((sum(ssjrzs81)/decode(sum(zzzs81),0,null,sum(zzzs81))*100),2),0)||'%' ssjrl81,\n" +
					"NVl(round((sum(ssjrzs80)/decode(sum(zzzs80),0,null,sum(zzzs80))*100),2),0)||'%' ssjrl80,\n" +
					"NVL((sum(zzzs37)-sum(ssjrzs37)),0) wssjr37,NVL((sum(zzzs36)-sum(ssjrzs36)),0) wssjr36,\n" +
					"NVL((sum(zzzs35)-sum(ssjrzs35)),0) wssjr35,NVL((sum(zzzs34)-sum(ssjrzs34)),0) wssjr34,\n" +
					"NVL((sum(zzzs33)-sum(ssjrzs33)),0) wssjr33,NVL((sum(zzzs32)-sum(ssjrzs32)),0) wssjr32,\n" +
					"NVL((sum(zzzs30)-sum(ssjrzs30)),0) wssjr30,NVL((sum(zzzs25)-sum(ssjrzs25)),0) wssjr25,\n\n" +
					"NVL((sum(zzzs86)-sum(ssjrzs86)),0) wssjr86," +
					"NVL((sum(zzzs85)-sum(ssjrzs85)),0) wssjr85,NVL((sum(zzzs84)-sum(ssjrzs84)),0) wssjr84,\n" +
					"NVL((sum(zzzs83)-sum(ssjrzs83)),0) wssjr83,NVL((sum(zzzs82)-sum(ssjrzs82)),0) wssjr82,\n" +
					"NVL((sum(zzzs81)-sum(ssjrzs81)),0) wssjr81,NVL((sum(zzzs80)-sum(ssjrzs80)),0) wssjr80\n" +
					" from \n" +
					"(select deps.wsid linkedprovicedept,linkedequipmentdy,wsmc,\n" +
					"(case linkedequipmentdy when '37' then count(distinct(devicecode)) else 0 end) zzzs37,\n" +
					"(case linkedequipmentdy when '36' then count(distinct(devicecode)) else 0 end) zzzs36,\n" +
					"(case linkedequipmentdy when '35' then count(distinct(devicecode)) else 0 end) zzzs35,\n" +
			        "(case linkedequipmentdy when '34' then count(distinct(devicecode)) else 0 end) zzzs34,\n" +
			        "(case linkedequipmentdy when '33' then count(distinct(devicecode)) else 0 end) zzzs33,\n" +
			        "(case linkedequipmentdy when '32' then count(distinct(devicecode)) else 0 end) zzzs32,\n" +
			        "(case linkedequipmentdy when '30' then count(distinct(devicecode)) else 0 end) zzzs30,\n" +
			        "(case linkedequipmentdy when '25' then count(distinct(devicecode)) else 0 end) zzzs25,\n" +
			        "(case linkedequipmentdy when '86' then count(distinct(devicecode)) else 0 end) zzzs86,\n" +
			        "(case linkedequipmentdy when '85' then count(distinct(devicecode)) else 0 end) zzzs85,\n" +
			        "(case linkedequipmentdy when '84' then count(distinct(devicecode)) else 0 end) zzzs84,\n" +
			        "(case linkedequipmentdy when '83' then count(distinct(devicecode)) else 0 end) zzzs83,\n" +
			        "(case linkedequipmentdy when '82' then count(distinct(devicecode)) else 0 end) zzzs82,\n" +
			        "(case linkedequipmentdy when '81' then count(distinct(devicecode)) else 0 end) zzzs81,\n" +
			        "(case linkedequipmentdy when '80' then count(distinct(devicecode)) else 0 end) zzzs80\n" +
					" from (select * from mw_app.cmsv_linedevicef where  LINKEDEQUIPMENTDY IS NOT NULL and linkedprovicedept IS NOT NULL and MONITORINGTYPES LIKE '01%' \n" +querySql.toString()+
					"  )dev,mw_app.cmst_zb_comm_wspz deps where dev.linkedprovicedept(+) = deps.wsid\n" +
					querySq.toString() + " group by deps.wsid,deps.wsmc,dev.linkedequipmentdy) a\n" +
					" left join \n" +
					"(select deps.wsid linkedprovicedept,linkedequipmentdy,wsmc,\n" +
			        "(case linkedequipmentdy when '37' then count(distinct(devicecode)) else 0 end) ssjrzs37,\n" +
			        "(case linkedequipmentdy when '36' then count(distinct(devicecode)) else 0 end) ssjrzs36,\n" +
			        "(case linkedequipmentdy when '35' then count(distinct(devicecode)) else 0 end) ssjrzs35,\n" +
			        "(case linkedequipmentdy when '34' then count(distinct(devicecode)) else 0 end) ssjrzs34,\n" +
			        "(case linkedequipmentdy when '33' then count(distinct(devicecode)) else 0 end) ssjrzs33,\n" +
			        "(case linkedequipmentdy when '32' then count(distinct(devicecode)) else 0 end) ssjrzs32,\n" +
			        "(case linkedequipmentdy when '30' then count(distinct(devicecode)) else 0 end) ssjrzs30,\n" +
			        "(case linkedequipmentdy when '25' then count(distinct(devicecode)) else 0 end) ssjrzs25,\n" +
			        "(case linkedequipmentdy when '86' then count(distinct(devicecode)) else 0 end) ssjrzs86,\n" +
			        "(case linkedequipmentdy when '85' then count(distinct(devicecode)) else 0 end) ssjrzs85,\n" +
			        "(case linkedequipmentdy when '84' then count(distinct(devicecode)) else 0 end) ssjrzs84,\n" +
			        "(case linkedequipmentdy when '83' then count(distinct(devicecode)) else 0 end) ssjrzs83,\n" +
			        "(case linkedequipmentdy when '82' then count(distinct(devicecode)) else 0 end) ssjrzs82,\n" +
			        "(case linkedequipmentdy when '81' then count(distinct(devicecode)) else 0 end) ssjrzs81,\n" +
			        "(case linkedequipmentdy when '80' then count(distinct(devicecode)) else 0 end) ssjrzs80\n" +
					" from (select * from mw_app.cmsv_linedevicef where  LINKEDEQUIPMENTDY IS NOT NULL and linkedprovicedept IS NOT NULL and MONITORINGTYPES LIKE '01%' \n" +querySql.toString()+
					" )dev,mw_app.cmst_devicemonitype monitor\n" +
					",mw_app.cmst_zb_comm_wspz deps where monitor.linkeddevice = dev.devicecode and dev.linkedprovicedept = deps.wsid \n" +
					" and monitor.linkeddevice in(select zzbm from mw_app.cmsv_deviceused_info where sfss = 'T')\n" + querySq.toString() +
					"  group by deps.wsid,dev.linkedequipmentdy,wsmc) b\n" +
					" on a.linkedprovicedept = b.linkedprovicedept and a.linkedequipmentdy = b.linkedequipmentdy\n" +
					" group by a.linkedprovicedept,a.wsmc order by PMS_XH) temp)\n " +
					" select * from ( "+
					"select linkedprovicedept,SSWSMC,pms_xh,\n" + 
					"zzzs37,zzzs36,\n" + 
					"zzzs35,zzzs34,zzzs33,zzzs32,zzzs30,zzzs25,zzzs86,zzzs85,zzzs84,zzzs83,zzzs82,zzzs81,zzzs80,\n" + 
					"ssjrzs37,ssjrzs36,ssjrzs35,ssjrzs34,ssjrzs33,ssjrzs32,ssjrzs30,ssjrzs25,ssjrzs86,\n" + 
					"ssjrzs85,ssjrzs84,ssjrzs83,ssjrzs82,ssjrzs81,ssjrzs80,\n" + 
					"(case when zzzs37=0 then '-' else to_char(substr(ssjrl37,0,length(ssjrl37)-1),'990.99')||'%'end) ssjrl37,\n" + 
					"(case when zzzs36=0 then '-' else to_char(substr(ssjrl36,0,length(ssjrl36)-1),'990.99')||'%'end) ssjrl36,\n" + 
					"(case when zzzs35=0 then '-' else to_char(substr(ssjrl35,0,length(ssjrl35)-1),'990.99')||'%'end) ssjrl35,\n" + 
					"(case when zzzs34=0 then '-' else to_char(substr(ssjrl34,0,length(ssjrl34)-1),'990.99')||'%'end) ssjrl34,\n" + 
					"(case when zzzs33=0 then '-' else to_char(substr(ssjrl33,0,length(ssjrl33)-1),'990.99')||'%'end) ssjrl33,\n" + 
					"(case when zzzs32=0 then '-' else to_char(substr(ssjrl32,0,length(ssjrl32)-1),'990.99')||'%'end) ssjrl32,\n" + 
					"(case when zzzs30=0 then '-' else to_char(substr(ssjrl30,0,length(ssjrl30)-1),'990.99')||'%'end) ssjrl30,\n" + 
					"(case when zzzs25=0 then '-' else to_char(substr(ssjrl25,0,length(ssjrl25)-1),'990.99')||'%'end) ssjrl25,\n" + 
					"(case when zzzs86=0 then '-' else to_char(substr(ssjrl86,0,length(ssjrl86)-1),'990.99')||'%'end) ssjrl86,\n" + 
					"(case when zzzs85=0 then '-' else to_char(substr(ssjrl85,0,length(ssjrl85)-1),'990.99')||'%'end) ssjrl85,\n" + 
					"(case when zzzs84=0 then '-' else to_char(substr(ssjrl84,0,length(ssjrl84)-1),'990.99')||'%'end) ssjrl84,\n" + 
					"(case when zzzs83=0 then '-' else to_char(substr(ssjrl83,0,length(ssjrl83)-1),'990.99')||'%'end) ssjrl83,\n" + 
					"(case when zzzs82=0 then '-' else to_char(substr(ssjrl82,0,length(ssjrl82)-1),'990.99')||'%'end) ssjrl82,\n" +
					"(case when zzzs81=0 then '-' else to_char(substr(ssjrl81,0,length(ssjrl81)-1),'990.99')||'%'end) ssjrl81,\n" +
					"(case when zzzs80=0 then '-' else to_char(substr(ssjrl80,0,length(ssjrl80)-1),'990.99')||'%'end) ssjrl80,\n" +
					"wssjr37,wssjr36,wssjr35,wssjr34,wssjr33,wssjr32,wssjr30,wssjr25,wssjr86,wssjr85,wssjr84,wssjr83,wssjr82,wssjr81,wssjr80, " +
					"allZZZS,allSSJRZS,(case when allZZZS=0 then '-' else to_char(substr(allSSJRL,0,length(allSSJRL)-1),'990.00')||'%'end) allSSJRL,allWSSJRS \n" +
			
					" ,'1' PX from tmp where SSWSMC not like '%国网运行公司%'\n" +
					" union all\n" + 
					" select '' linkedprovicedept,'国家电网公司' SSWSMC,'' pms_xh,\n" + 
					"NVl(sum(zzzs37), 0) zzzs37,NVl(sum(zzzs36), 0) zzzs36,NVl(sum(zzzs35), 0) zzzs35,\n" +
					"NVl(sum(zzzs34), 0) zzzs34,NVl(sum(zzzs33), 0) zzzs33,NVl(sum(zzzs32), 0) zzzs32,\n" + 
					"NVl(sum(zzzs30), 0) zzzs30,NVl(sum(zzzs25), 0) zzzs25,NVl(sum(zzzs86), 0) zzzs86,NVl(sum(zzzs85), 0) zzzs85,\n" + 
					"NVl(sum(zzzs84), 0) zzzs84,NVl(sum(zzzs83), 0) zzzs83,NVl(sum(zzzs82), 0) zzzs82,\n" + 
					"NVl(sum(zzzs81), 0) zzzs81,NVl(sum(zzzs80), 0) zzzs80," +
					"NVl(sum(ssjrzs37), 0) ssjrzs37,NVl(sum(ssjrzs36), 0) ssjrzs36,NVl(sum(ssjrzs35), 0) ssjrzs35,\n" + 
					"NVl(sum(ssjrzs34), 0) ssjrzs34,NVl(sum(ssjrzs33), 0) ssjrzs33,NVl(sum(ssjrzs32), 0) ssjrzs32,\n" + 
					"NVl(sum(ssjrzs30), 0) ssjrzs30,NVl(sum(ssjrzs25), 0) ssjrzs25,NVl(sum(ssjrzs86), 0) ssjrzs86,NVl(sum(ssjrzs85), 0) ssjrzs85,\n" + 
					"NVl(sum(ssjrzs84), 0) ssjrzs84,NVl(sum(ssjrzs83), 0) ssjrzs83, NVl(sum(ssjrzs82), 0) ssjrzs82,\n" + 
					" NVl(sum(ssjrzs81), 0) ssjrzs81, NVl(sum(ssjrzs80), 0) ssjrzs80," +
					"(case when sum(zzzs37)=0 then '-' else to_char((sum(ssjrzs37) /sum(zzzs37)*100),'990.99')||'%'end) ssjrl37,\n" + 
					"(case when sum(zzzs36)=0 then '-' else to_char((sum(ssjrzs36) /sum(zzzs36)*100),'990.99')||'%'end) ssjrl36,\n" + 
					"(case when sum(zzzs35)=0 then '-' else to_char((sum(ssjrzs35) /sum(zzzs35)*100),'990.99')||'%'end) ssjrl35,\n" + 
					"(case when sum(zzzs34)=0 then '-' else to_char((sum(ssjrzs34) /sum(zzzs34)*100),'990.99')||'%'end) ssjrl34,\n" + 
					"(case when sum(zzzs33)=0 then '-' else to_char((sum(ssjrzs33) /sum(zzzs33)*100),'990.99')||'%'end) ssjrl33,\n" + 
					"(case when sum(zzzs32)=0 then '-' else to_char((sum(ssjrzs32) /sum(zzzs32)*100),'990.99')||'%'end) ssjrl32,\n" + 
					"(case when sum(zzzs30)=0 then '-' else to_char((sum(ssjrzs30) /sum(zzzs30)*100),'990.99')||'%'end) ssjrl30,\n" + 
					"(case when sum(zzzs25)=0 then '-' else to_char((sum(ssjrzs25) /sum(zzzs25)*100),'990.99')||'%'end) ssjrl25,\n" + 
					"(case when sum(zzzs86)=0 then '-' else to_char((sum(ssjrzs86) /sum(zzzs86)*100),'990.99')||'%'end) ssjrl86,\n" + 
					"(case when sum(zzzs85)=0 then '-' else to_char((sum(ssjrzs85) /sum(zzzs85)*100),'990.99')||'%'end) ssjrl85,\n" + 
					"(case when sum(zzzs84)=0 then '-' else to_char((sum(ssjrzs84) /sum(zzzs84)*100),'990.99')||'%'end) ssjrl84,\n" + 
					"(case when sum(zzzs83)=0 then '-' else to_char((sum(ssjrzs83) /sum(zzzs83)*100),'990.99')||'%'end) ssjrl83,\n" + 
					"(case when sum(zzzs82)=0 then '-' else to_char((sum(ssjrzs82) /sum(zzzs82)*100),'990.99')||'%'end) ssjrl82,\n" + 
					"(case when sum(zzzs81)=0 then '-' else to_char((sum(ssjrzs81) /sum(zzzs81)*100),'990.99')||'%'end) ssjrl81,\n" + 
					"(case when sum(zzzs80)=0 then '-' else to_char((sum(ssjrzs80) /sum(zzzs80)*100),'990.99')||'%'end) ssjrl80,\n" + 
					"NVL((sum(zzzs37) - sum(ssjrzs37)), 0) wssjr37,NVL((sum(zzzs36) - sum(ssjrzs36)), 0) wssjr36,\n" + 
					"NVL((sum(zzzs35) - sum(ssjrzs35)), 0) wssjr35,NVL((sum(zzzs34) - sum(ssjrzs34)), 0) wssjr34,\n" + 
					"NVL((sum(zzzs33) - sum(ssjrzs33)), 0) wssjr33,NVL((sum(zzzs32) - sum(ssjrzs32)), 0) wssjr32,\n" + 
					"NVL((sum(zzzs30) - sum(ssjrzs30)), 0) wssjr30,NVL((sum(zzzs25) - sum(ssjrzs25)), 0) wssjr25,NVL((sum(zzzs86) - sum(ssjrzs86)), 0) wssjr86,\n" + 
					"NVL((sum(zzzs85) - sum(ssjrzs85)), 0) wssjr85,NVL((sum(zzzs84) - sum(ssjrzs84)), 0) wssjr84,\n" + 
					"NVL((sum(zzzs83) - sum(ssjrzs83)), 0) wssjr83,NVL((sum(zzzs82) - sum(ssjrzs82)), 0) wssjr82,\n" + 
					"NVL((sum(zzzs81) - sum(ssjrzs81)), 0) wssjr81,NVL((sum(zzzs80) - sum(ssjrzs80)), 0) wssjr80," +
					"nvl(sum(allZZZS),0) allZZZS,nvl(sum(allSSJRZS),0) allSSJRZS,\n" + 
					"(case when sum(allZZZS)=0 then '-' else to_char((sum(allSSJRZS) /sum(allZZZS)*100),'990.99')||'%'end)  allssjrl,\n" + 
					"nvl(sum(allWSSJRS),0) allWSSJRS\n" + 
					" ,'0' PX from tmp "+
					" ) order by PX,pms_xh ";
		try{

			cols = "LINKEDPROVICEDEPT,SSWSMC,PMS_XH,ZZZS37,ZZZS36,ZZZS35,ZZZS34,ZZZS33,ZZZS32,ZZZS30,ZZZS25,ZZZS86,ZZZS85,ZZZS84,ZZZS83,ZZZS82,ZZZS81,ZZZS80,SSJRZS37,SSJRZS36,SSJRZS35,SSJRZS34,SSJRZS33,SSJRZS32,SSJRZS30,SSJRZS25,SSJRZS86,SSJRZS85,SSJRZS84,SSJRZS83,SSJRZS82,SSJRZS81,SSJRZS80,SSJRL37,SSJRL36,SSJRL35,SSJRL34,SSJRL33,SSJRL32,SSJRL30,SSJRL25,SSJRL86,SSJRL85,SSJRL84,SSJRL83,SSJRL82,SSJRL81,SSJRL80,WSSJR37,WSSJR36,WSSJR35,WSSJR34,WSSJR33,WSSJR32,WSSJR30,WSSJR25,WSSJR86,WSSJR85,WSSJR84,WSSJR83,WSSJR82,WSSJR81,WSSJR80,ALLZZZS,ALLSSJRZS,ALLSSJRL,ALLWSSJRS,PX";
			
			
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			log.info("执行按电压等级统计监测装置运行状况时出错！", e);
			e.printStackTrace();
		}
		
		return qro;
		
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
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println(zzzsColumnSql);
						System.out.println();
						System.out.println();
						System.out.println();
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
			
			
			cols ="LINKEDPROVICEDEPT,SSWSMC,PMS_XH,ZZZS012001,ZZZS013001,ZZZS013002,ZZZS013003," +
					"ZZZS018003,ZZZS013004,ZZZS013005,ZZZS014001,ZZZS018001,ZZZS018002,ZZZS013006," +
					"SSJR012001,SSJR013001,SSJR013002,SSJR013003,SSJR018003,SSJR013004,SSJR013005," +
					"SSJR014001,SSJR018001,SSJR018002,SSJR013006,DF012001,DF013001,DF013002,DF013003," +
					"DF018003,DF013004,DF013005,DF014001,DF018001,DF018002,DF013006,SJJRL012001,SJJRL013001," +
					"SJJRL013002,SJJRL013003,SJJRL018003,SJJRL013004,SJJRL013005,SJJRL014001,SJJRL018001," +
					"SJJRL018002,SJJRL013006,ZQL012001,ZQL013001,ZQL013002,ZQL013003,ZQL018003,ZQL013004," +
					"ZQL013005,ZQL014001,ZQL018001,ZQL018002,ZQL013006,JRL012001,JRL013001,JRL013002,JRL013003," +
					"JRL018003,JRL013004,JRL013005,JRL014001,JRL018001,JRL018002,JRL013006,ALLZZZS,ALLSSJR,ALLJRL,PX";
			
		String	sql ="with tmp as(select temp.*,"
				+ "(zzzs012001+zzzs013001+zzzs013002+zzzs013003+zzzs018003+zzzs013004+zzzs013005+zzzs014001+zzzs018001+zzzs018002+zzzs013006) allzzzs,"
				+ "(ssjr012001+ssjr013001+ssjr013002+ssjr013003+ssjr018003+ssjr013004+ssjr013005+ssjr014001+ssjr018001+ssjr018002+ssjr013006) allssjr,"
				+ "(nvl(round((((ssjr012001+ssjr013001+ssjr013002+ssjr013003+ssjr018003+ssjr013004+ssjr013005+ssjr014001+ssjr018001+ssjr018002+ssjr013006)/"
				+ "decode((zzzs012001+zzzs013001+zzzs013002+zzzs013003+zzzs018003+zzzs013004+zzzs013005+zzzs014001+zzzs018001+zzzs018002+zzzs013006),0,null,"
				+ "(zzzs012001+zzzs013001+zzzs013002+zzzs013003+zzzs018003+zzzs013004+zzzs013005+zzzs014001+zzzs018001+zzzs018002+zzzs013006)))*100),2),0))||'%' alljrl"
				+ " from (select a.linkedprovicedept,a.wsmc sswsmc,"
				+ "(select cast(zdypx as varchar2(10)) pms_xh from mw_app.cmst_zb_comm_wspz where wsid = a.linkedprovicedept) pms_xh,"
				+ "sum(zzzs012001) zzzs012001,sum(zzzs013001) zzzs013001,sum(zzzs013002) zzzs013002,"
				+ "sum(zzzs013003) zzzs013003,sum(zzzs018003) zzzs018003,sum(zzzs013004) zzzs013004,"
				+ "sum(zzzs013005) zzzs013005,sum(zzzs014001) zzzs014001,"
				+ "sum(zzzs018001) zzzs018001,sum(zzzs018002) zzzs018002,sum(zzzs013006) zzzs013006,"
				+ "NVL(sum(ssjr012001),0) ssjr012001,NVL(sum(ssjr013001),0) ssjr013001,NVL(sum(ssjr013002),0) ssjr013002,"
				+ "NVL(sum(ssjr013003),0) ssjr013003,NVL(sum(ssjr018003),0) ssjr018003,NVL(sum(ssjr013004),0) ssjr013004,NVL(sum(ssjr013005),0) ssjr013005,"
				+ "NVL(sum(ssjr014001),0) ssjr014001,NVL(sum(ssjr018001),0) ssjr018001,NVL(sum(ssjr018002),0) ssjr018002,NVL(sum(ssjr013006),0) ssjr013006,\n"+

				"NVL(sum(sjjrl012001),0) sjjrls012001,NVL(sum(sjjrl013001),0) sjjrls013001,NVL(sum(sjjrl013002),0) sjjrls013002, \n" +
				"NVL(sum(sjjrl013003),0) sjjrls013003,NVL(sum(sjjrl018003),0) sjjrls018003,NVL(sum(sjjrl013004),0) sjjrls013004, \n" + 
				"NVL(sum(sjjrl013005),0) sjjrls013005,NVL(sum(sjjrl014001),0) sjjrls014001,NVL(sum(sjjrl018001),0) sjjrls018001, \n" + 
				"NVL(sum(sjjrl018002),0) sjjrls018002,NVL(sum(sjjrl013006),0) sjjrls013006, \n" + 
				" \n" + 
				"NVL(sum(zql012001),0) zqls012001,NVL(sum(zql013001),0) zqls013001,NVL(sum(zql013002),0) zqls013002, \n" + 
				"NVL(sum(zql013003),0) zqls013003,NVL(sum(zql018003),0) zqls018003,NVL(sum(zql013004),0) zqls013004, \n" + 
				"NVL(sum(zql013005),0) zqls013005,NVL(sum(zql014001),0) zqls014001,NVL(sum(zql018001),0) zqls018001, \n" + 
				"NVL(sum(zql018002),0) zqls018002,NVL(sum(zql013006),0) zqls013006, \n" + 
				" \n" + 

				"NVL(sum(df012001),0) dfzs012001,NVL(sum(df013001),0) dfzs013001,NVL(sum(df013002),0) dfzs013002, \n" +
				"NVL(sum(df013003),0) dfzs013003,NVL(sum(df018003),0) dfzs018003,NVL(sum(df013004),0) dfzs013004, \n" + 
				"NVL(sum(df013005),0) dfzs013005,NVL(sum(df014001),0) dfzs014001,NVL(sum(df018001),0) dfzs018001, \n" + 
				"NVL(sum(df018002),0) dfzs018002,NVL(sum(df013006),0) dfzs013006, \n" + 
				"NVl(round(sum(df012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001)),2),0) df012001,NVl(round(sum(df013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001)),2),0) df013001,NVl(round(sum(df013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002)),2),0) df013002, \n" + 
				"NVl(round(sum(df013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003)),2),0) df013003,NVl(round(sum(df018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003)),2),0) df018003,NVl(round(sum(df013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004)),2),0) df013004, \n" + 
				"NVl(round(sum(df013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005)),2),0) df013005,NVl(round(sum(df014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001)),2),0) df014001,NVl(round(sum(df018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001)),2),0) df018001, \n" + 
				"NVl(round(sum(df018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002)),2),0) df018002,NVl(round(sum(df013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006)),2),0) df013006,\n"


				+ "NVL(round((sum(ssjr012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001))*100),2),0)||'%' JRL012001,"
				+ "NVL(round((sum(ssjr013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001))*100),2),0)||'%' JRL013001,"
				+ "NVL(round((sum(ssjr013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002))*100),2),0)||'%' JRL013002,"
				+ "NVL(round((sum(ssjr013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003))*100),2),0)||'%' JRL013003,"
				+ "NVL(round((sum(ssjr018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003))*100),2),0)||'%' JRL018003,"
				+ "NVL(round((sum(ssjr013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004))*100),2),0)||'%' JRL013004,"
				+ "NVL(round((sum(ssjr013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005))*100),2),0)||'%' JRL013005,"
				+ "NVL(round((sum(ssjr014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001))*100),2),0)||'%' JRL014001,"
				+ "NVL(round((sum(ssjr018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001))*100),2),0)||'%' JRL018001,"
				+ "NVL(round((sum(ssjr018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002))*100),2),0)||'%' JRL018002,"
				+ "NVL(round((sum(ssjr013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006))*100),2),0)||'%' JRL013006,"+

				"NVL(round((sum(sjjrl012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001))*100),2),0)||'%' sjjrl012001, \n" +
				" NVL(round((sum(sjjrl013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001))*100),2),0)||'%' sjjrl013001, \n" + 
				" NVL(round((sum(sjjrl013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002))*100),2),0)||'%' sjjrl013002, \n" + 
				" NVL(round((sum(sjjrl013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003))*100),2),0)||'%' sjjrl013003, \n" + 
				" NVL(round((sum(sjjrl018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003))*100),2),0)||'%' sjjrl018003, \n" + 
				" NVL(round((sum(sjjrl013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004))*100),2),0)||'%' sjjrl013004, \n" + 
				" NVL(round((sum(sjjrl013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005))*100),2),0)||'%' sjjrl013005, \n" + 
				" NVL(round((sum(sjjrl014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001))*100),2),0)||'%' sjjrl014001, \n" + 
				" NVL(round((sum(sjjrl018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001))*100),2),0)||'%' sjjrl018001, \n" + 
				" NVL(round((sum(sjjrl018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002))*100),2),0)||'%' sjjrl018002, \n" + 
				" NVL(round((sum(sjjrl013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006))*100),2),0)||'%' sjjrl013006, \n" + 
				" \n" + 
				" NVL(round((sum(zql012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001))*100),2),0)||'%' zql012001, \n" + 
				" NVL(round((sum(zql013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001))*100),2),0)||'%' zql013001, \n" + 
				" NVL(round((sum(zql013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002))*100),2),0)||'%' zql013002, \n" + 
				" NVL(round((sum(zql013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003))*100),2),0)||'%' zql013003, \n" + 
				" NVL(round((sum(zql018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003))*100),2),0)||'%' zql018003, \n" + 
				" NVL(round((sum(zql013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004))*100),2),0)||'%' zql013004, \n" + 
				" NVL(round((sum(zql013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005))*100),2),0)||'%' zql013005, \n" + 
				" NVL(round((sum(zql014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001))*100),2),0)||'%' zql014001, \n" + 
				" NVL(round((sum(zql018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001))*100),2),0)||'%' zql018001, \n" + 
				" NVL(round((sum(zql018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002))*100),2),0)||'%' zql018002, \n" + 
				" NVL(round((sum(zql013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006))*100),2),0)||'%' zql013006\n"

				+ " from "
				+ "(select  deps.wsid linkedprovicedept,monitoringtype,wsmc,\n"+


				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '012001') zzzs012001, \n" +
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013001') zzzs013001, \n" + 
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013002') zzzs013002, \n" + 
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013003') zzzs013003, \n" + 
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '018003') zzzs018003, \n" + 
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013004') zzzs013004, \n" + 
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013005') zzzs013005, \n" + 
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '014001') zzzs014001, \n" + 
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '018001') zzzs018001, \n" + 
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '018002') zzzs018002, \n" + 
				" (select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013006') zzzs013006,\n"+



				"(case monitoringtype when '012001' then sum(dataaccessrate) else 0 end) sjjrl012001, \n" +
				"  (case monitoringtype when '013001' then sum(dataaccessrate) else 0 end) sjjrl013001, \n" + 
				"  (case monitoringtype when '013002' then sum(dataaccessrate) else 0 end) sjjrl013002, \n" + 
				"  (case monitoringtype when '013003' then sum(dataaccessrate) else 0 end) sjjrl013003, \n" + 
				"  (case monitoringtype when '018003' then sum(dataaccessrate) else 0 end) sjjrl018003, \n" + 
				"  (case monitoringtype when '013004' then sum(dataaccessrate) else 0 end) sjjrl013004, \n" + 
				"  (case monitoringtype when '013005' then sum(dataaccessrate) else 0 end) sjjrl013005, \n" + 
				"  (case monitoringtype when '014001' then sum(dataaccessrate) else 0 end) sjjrl014001, \n" + 
				"  (case monitoringtype when '018001' then sum(dataaccessrate) else 0 end) sjjrl018001, \n" + 
				"  (case monitoringtype when '018002' then sum(dataaccessrate) else 0 end) sjjrl018002, \n" + 
				"  (case monitoringtype when '013006' then sum(dataaccessrate) else 0 end) sjjrl013006, \n" + 
				" \n" + 
				"  (case monitoringtype when '012001' then sum(dataaraccuracyrate) else 0 end) zql012001, \n" + 
				"  (case monitoringtype when '013001' then sum(dataaraccuracyrate) else 0 end) zql013001, \n" + 
				"  (case monitoringtype when '013002' then sum(dataaraccuracyrate) else 0 end) zql013002, \n" + 
				"  (case monitoringtype when '013003' then sum(dataaraccuracyrate) else 0 end) zql013003, \n" + 
				"  (case monitoringtype when '018003' then sum(dataaraccuracyrate) else 0 end) zql018003, \n" + 
				"  (case monitoringtype when '013004' then sum(dataaraccuracyrate) else 0 end) zql013004, \n" + 
				"  (case monitoringtype when '013005' then sum(dataaraccuracyrate) else 0 end) zql013005, \n" + 
				"  (case monitoringtype when '014001' then sum(dataaraccuracyrate) else 0 end) zql014001, \n" + 
				"  (case monitoringtype when '018001' then sum(dataaraccuracyrate) else 0 end) zql018001, \n" + 
				"  (case monitoringtype when '018002' then sum(dataaraccuracyrate) else 0 end) zql018002, \n" + 
				"  (case monitoringtype when '013006' then sum(dataaraccuracyrate) else 0 end) zql013006, \n" + 
				" \n" + 
				"  (case monitoringtype when '012001' then sum(allscored) else 0 end) df012001, \n" + 
				"  (case monitoringtype when '013001' then sum(allscored) else 0 end) df013001, \n" + 
				"  (case monitoringtype when '013002' then sum(allscored) else 0 end) df013002, \n" + 
				"  (case monitoringtype when '013003' then sum(allscored) else 0 end) df013003, \n" + 
				"  (case monitoringtype when '018003' then sum(allscored) else 0 end) df018003, \n" + 
				"  (case monitoringtype when '013004' then sum(allscored) else 0 end) df013004, \n" + 
				"  (case monitoringtype when '013005' then sum(allscored) else 0 end) df013005, \n" + 
				"  (case monitoringtype when '014001' then sum(allscored) else 0 end) df014001, \n" + 
				"  (case monitoringtype when '018001' then sum(allscored) else 0 end) df018001, \n" + 
				"  (case monitoringtype when '018002' then sum(allscored) else 0 end) df018002, \n" + 
				"  (case monitoringtype when '013006' then sum(allscored) else 0 end) df013006\n" 
				

				+ " from (select l.*, dataaccessrate,dataaraccuracyrate,allscored from mw_app.cmsv_linedevice_logic_xtf l ,\n"+
				 "mw_app.cmsv_index_scored i where l.devicecode = i.devicecode and i.monitoringtype = l.MONITORINGTYPE \n"
				+ querySql
				+ " )dev,mw_app.wsid deps"
				+ "  where deps.wsid = dev.linkedprovicedept(+) "
				+ querySq
				+ "  group by  deps.wsid,dev.monitoringtype,wsmc) a"
				+ " left join "
				+ "(select  deps.wsid linkedprovicedept,monitoringtype,wsmc,"
				+ "       (case monitoringtype when '012001' then count(monitoringtype) else 0 end) ssjr012001,"
				+ "       (case monitoringtype when '013001' then count(monitoringtype) else 0 end) ssjr013001,"
				+ "       (case monitoringtype when '013002' then count(monitoringtype) else 0 end) ssjr013002,"
				+ "       (case monitoringtype when '013003' then count(monitoringtype) else 0 end) ssjr013003,"
				+ "       (case monitoringtype when '018003' then count(monitoringtype) else 0 end) ssjr018003,"
				+ "       (case monitoringtype when '013004' then count(monitoringtype) else 0 end) ssjr013004,"
				+ "       (case monitoringtype when '013005' then count(monitoringtype) else 0 end) ssjr013005,"
				+ "       (case monitoringtype when '014001' then count(monitoringtype) else 0 end) ssjr014001,"
				+ "       (case monitoringtype when '018001' then count(monitoringtype) else 0 end) ssjr018001,"
				+ "       (case monitoringtype when '018002' then count(monitoringtype) else 0 end) ssjr018002,"
				+ "       (case monitoringtype when '013006' then count(monitoringtype) else 0 end) ssjr013006"
				+ " from (select l.* from mw_app.cmsv_linedevice_logic_xtf l ,\n"
				+" mw_app.cmsv_index_scored i where l.devicecode = i.devicecode and i.monitoringtype = l.MONITORINGTYPE\n  "
				+ querySql
				+ "   )dev,"
				+ " mw_app.cmst_zb_comm_wspz deps where deps.wsid = dev.linkedprovicedept"
				+ " and (dev.devicecode,dev.monitoringtype) in(select zzbm,jclx from mw_app.cmsv_deviceused_info where sfss = 'T')"
				+ querySq
				+ "  group by  deps.wsid,dev.monitoringtype,deps.wsmc) b"
				+ " on a.linkedprovicedept = b.linkedprovicedept and a.monitoringtype = b.monitoringtype "
				+ " group by a.linkedprovicedept,a.wsmc order by PMS_XH) temp)"
				+ " select * from ("
				+ " select linkedprovicedept,sswsmc,pms_xh,"
				+ "zzzs012001,zzzs013001,zzzs013002,zzzs013003,zzzs018003,zzzs013004,\n"
				+ "zzzs013005,zzzs014001,zzzs018001,zzzs018002,zzzs013006,\n"
				+ "ssjr012001,ssjr013001,ssjr013002,ssjr013003,ssjr018003,\n"
				+ "ssjr013004,ssjr013005,ssjr014001,ssjr018001,ssjr018002,ssjr013006,\n"+

				"df012001,df013001,df013002,df013003,df018003, \n" +
				"df013004,df013005,df014001,df018001,df018002,df013006,\n"+
				
				"(case when zzzs012001=0 then '-' else to_char(substr(sjJRL012001,0,length(sjJRL012001)-1),'990.99')||'%'end) sjJRL012001, \n" +
				"(case when zzzs013001=0 then '-' else to_char(substr(sjJRL013001,0,length(sjJRL013001)-1),'990.99')||'%'end) sjJRL013001, \n" + 
				"(case when zzzs013002=0 then '-' else to_char(substr(sjJRL013002,0,length(sjJRL013002)-1),'990.99')||'%'end) sjJRL013002, \n" + 
				"(case when zzzs013003=0 then '-' else to_char(substr(sjJRL013003,0,length(sjJRL013003)-1),'990.99')||'%'end) sjJRL013003, \n" + 
				"(case when zzzs018003=0 then '-' else to_char(substr(sjJRL018003,0,length(sjJRL018003)-1),'990.99')||'%'end) sjJRL018003, \n" + 
				"(case when zzzs013004=0 then '-' else to_char(substr(sjJRL013004,0,length(sjJRL013004)-1),'990.99')||'%'end) sjJRL013004, \n" + 
				"(case when zzzs013005=0 then '-' else to_char(substr(sjJRL013005,0,length(sjJRL013005)-1),'990.99')||'%'end) sjJRL013005, \n" + 
				"(case when zzzs014001=0 then '-' else to_char(substr(sjJRL014001,0,length(sjJRL014001)-1),'990.99')||'%'end) sjJRL014001, \n" + 
				"(case when zzzs018001=0 then '-' else to_char(substr(sjJRL018001,0,length(sjJRL018001)-1),'990.99')||'%'end) sjJRL018001, \n" + 
				"(case when zzzs018002=0 then '-' else to_char(substr(sjJRL018002,0,length(sjJRL018002)-1),'990.99')||'%'end) sjJRL018002, \n" + 
				"(case when zzzs013006=0 then '-' else to_char(substr(sjJRL013006,0,length(sjJRL013006)-1),'990.99')||'%'end) sjJRL013006, \n" + 
				" \n" + 
				"(case when zzzs012001=0 then '-' else to_char(substr(zql012001,0,length(zql012001)-1),'990.99')||'%'end) zql012001, \n" + 
				"(case when zzzs013001=0 then '-' else to_char(substr(zql013001,0,length(zql013001)-1),'990.99')||'%'end) zql013001, \n" + 
				"(case when zzzs013002=0 then '-' else to_char(substr(zql013002,0,length(zql013002)-1),'990.99')||'%'end) zql013002, \n" + 
				"(case when zzzs013003=0 then '-' else to_char(substr(zql013003,0,length(zql013003)-1),'990.99')||'%'end) zql013003, \n" + 
				"(case when zzzs018003=0 then '-' else to_char(substr(zql018003,0,length(zql018003)-1),'990.99')||'%'end) zql018003, \n" + 
				"(case when zzzs013004=0 then '-' else to_char(substr(zql013004,0,length(zql013004)-1),'990.99')||'%'end) zql013004, \n" + 
				"(case when zzzs013005=0 then '-' else to_char(substr(zql013005,0,length(zql013005)-1),'990.99')||'%'end) zql013005, \n" + 
				"(case when zzzs014001=0 then '-' else to_char(substr(zql014001,0,length(zql014001)-1),'990.99')||'%'end) zql014001, \n" + 
				"(case when zzzs018001=0 then '-' else to_char(substr(zql018001,0,length(zql018001)-1),'990.99')||'%'end) zql018001, \n" + 
				"(case when zzzs018002=0 then '-' else to_char(substr(zql018002,0,length(zql018002)-1),'990.99')||'%'end) zql018002, \n" + 
				"(case when zzzs013006=0 then '-' else to_char(substr(zql013006,0,length(zql013006)-1),'990.99')||'%'end) zql013006,\n"


				+ "(case when zzzs012001=0 then '-' else to_char(substr(JRL012001,0,length(JRL012001)-1),'990.99')||'%'end) JRL012001,\n"
				+ "(case when zzzs013001=0 then '-' else to_char(substr(JRL013001,0,length(JRL013001)-1),'990.99')||'%'end) JRL013001,\n"
				+ "(case when zzzs013002=0 then '-' else to_char(substr(JRL013002,0,length(JRL013002)-1),'990.99')||'%'end) JRL013002,\n"
				+ "(case when zzzs013003=0 then '-' else to_char(substr(JRL013003,0,length(JRL013003)-1),'990.99')||'%'end) JRL013003,\n"
				+ "(case when zzzs018003=0 then '-' else to_char(substr(JRL018003,0,length(JRL018003)-1),'990.99')||'%'end) JRL018003,\n"
				+ "(case when zzzs013004=0 then '-' else to_char(substr(JRL013004,0,length(JRL013004)-1),'990.99')||'%'end) JRL013004,\n"
				+ "(case when zzzs013005=0 then '-' else to_char(substr(JRL013005,0,length(JRL013005)-1),'990.99')||'%'end) JRL013005,\n"
				+ "(case when zzzs014001=0 then '-' else to_char(substr(JRL014001,0,length(JRL014001)-1),'990.99')||'%'end) JRL014001,\n"
				+ "(case when zzzs018001=0 then '-' else to_char(substr(JRL018001,0,length(JRL018001)-1),'990.99')||'%'end) JRL018001,\n"
				+ "(case when zzzs018002=0 then '-' else to_char(substr(JRL018002,0,length(JRL018002)-1),'990.99')||'%'end) JRL018002,\n"
				+ "(case when zzzs013006=0 then '-' else to_char(substr(JRL013006,0,length(JRL013006)-1),'990.99')||'%'end) JRL013006,\n"
				
				+ "allzzzs,allssjr,(case when allzzzs=0 then '-' else to_char(substr(alljrl,0,length(alljrl)-1),'990.99')||'%'end) alljrl \n"
				+" ,'1' PX from tmp  where SSWSMC not like '%国网运行公司%' "
					+ " union all select '' linkedprovicedept,'国家电网公司' sswsmc,'' pms_xh,"
					+ "sum(zzzs012001) zzzs012001,sum(zzzs013001) zzzs013001,sum(zzzs013002) zzzs013002,"
					+ "sum(zzzs013003) zzzs013003,sum(zzzs018003) zzzs018003,sum(zzzs013004) zzzs013004,"
					+ "sum(zzzs013005) zzzs013005,sum(zzzs014001) zzzs014001,"
					+ "sum(zzzs018001) zzzs018001,sum(zzzs018002) zzzs018002,sum(zzzs013006) zzzs013006,"
					+ "NVL(sum(ssjr012001),0) ssjr012001,NVL(sum(ssjr013001),0) ssjr013001,NVL(sum(ssjr013002),0) ssjr013002,"
					+ "NVL(sum(ssjr013003),0) ssjr013003,NVL(sum(ssjr018003),0) ssjr018003,NVL(sum(ssjr013004),0) ssjr013004,NVL(sum(ssjr013005),0) ssjr013005,"
					+ "NVL(sum(ssjr014001),0) ssjr014001,NVL(sum(ssjr018001),0) ssjr018001,NVL(sum(ssjr018002),0) ssjr018002,NVL(sum(ssjr013006),0) ssjr013006,\n"+


					"NVl(round(sum(dfzs012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001)),2),0) df012001,NVl(round(sum(dfzs013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001)),2),0) df013001,NVl(round(sum(dfzs013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002)),2),0) df013002, \n" +
					"NVl(round(sum(dfzs013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003)),2),0) df013003,NVl(round(sum(dfzs018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003)),2),0) df018003,NVl(round(sum(dfzs013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004)),2),0) df013004, \n" + 
					"NVl(round(sum(dfzs013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005)),2),0) df013005,NVl(round(sum(dfzs014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001)),2),0) df014001,NVl(round(sum(dfzs018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001)),2),0) df018001, \n" + 
					"NVl(round(sum(dfzs018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002)),2),0) df018002,NVl(round(sum(dfzs013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006)),2),0) df013006,\n"+


					
					

					"(case when sum(zzzs012001)=0 then '-' else to_char((sum(sjjrls012001) /sum(zzzs012001)*100),'990.99')||'%'end) sjjrl012001, \n" +
					"(case when sum(zzzs013001)=0 then '-' else to_char((sum(sjjrls013001) /sum(zzzs013001)*100),'990.99')||'%'end) sjjrl013001, \n" + 
					"(case when sum(zzzs013002)=0 then '-' else to_char((sum(sjjrls013002) /sum(zzzs013002)*100),'990.99')||'%'end) sjjrl013002, \n" + 
					"(case when sum(zzzs013003)=0 then '-' else to_char((sum(sjjrls013003) /sum(zzzs013003)*100),'990.99')||'%'end) sjjrl013003, \n" + 
					"(case when sum(zzzs018003)=0 then '-' else to_char((sum(sjjrls018003) /sum(zzzs018003)*100),'990.99')||'%'end) sjjrl018003, \n" + 
					"(case when sum(zzzs013004)=0 then '-' else to_char((sum(sjjrls013004) /sum(zzzs013004)*100),'990.99')||'%'end) sjjrl013004, \n" + 
					"(case when sum(zzzs013005)=0 then '-' else to_char((sum(sjjrls013005) /sum(zzzs013005)*100),'990.99')||'%'end) sjjrl013005, \n" + 
					"(case when sum(zzzs014001)=0 then '-' else to_char((sum(sjjrls014001) /sum(zzzs014001)*100),'990.99')||'%'end) sjjrl014001, \n" + 
					"(case when sum(zzzs018001)=0 then '-' else to_char((sum(sjjrls018001) /sum(zzzs018001)*100),'990.99')||'%'end) sjjrl018001, \n" + 
					"(case when sum(zzzs018002)=0 then '-' else to_char((sum(sjjrls018002) /sum(zzzs018002)*100),'990.99')||'%'end) sjjrl018002, \n" + 
					"(case when sum(zzzs013006)=0 then '-' else to_char((sum(sjjrls013006) /sum(zzzs013006)*100),'990.99')||'%'end) sjjrl013006, \n" + 
					" \n" + 
					"(case when sum(zzzs012001)=0 then '-' else to_char((sum(zqls012001) /sum(zzzs012001)*100),'990.99')||'%'end) zql012001, \n" + 
					"(case when sum(zzzs013001)=0 then '-' else to_char((sum(zqls013001) /sum(zzzs013001)*100),'990.99')||'%'end) zql013001, \n" + 
					"(case when sum(zzzs013002)=0 then '-' else to_char((sum(zqls013002) /sum(zzzs013002)*100),'990.99')||'%'end) zql013002, \n" + 
					"(case when sum(zzzs013003)=0 then '-' else to_char((sum(zqls013003) /sum(zzzs013003)*100),'990.99')||'%'end) zql013003, \n" + 
					"(case when sum(zzzs018003)=0 then '-' else to_char((sum(zqls018003) /sum(zzzs018003)*100),'990.99')||'%'end) zql018003, \n" + 
					"(case when sum(zzzs013004)=0 then '-' else to_char((sum(zqls013004) /sum(zzzs013004)*100),'990.99')||'%'end) zql013004, \n" + 
					"(case when sum(zzzs013005)=0 then '-' else to_char((sum(zqls013005) /sum(zzzs013005)*100),'990.99')||'%'end) zql013005, \n" + 
					"(case when sum(zzzs014001)=0 then '-' else to_char((sum(zqls014001) /sum(zzzs014001)*100),'990.99')||'%'end) zql014001, \n" + 
					"(case when sum(zzzs018001)=0 then '-' else to_char((sum(zqls018001) /sum(zzzs018001)*100),'990.99')||'%'end) zql018001, \n" + 
					"(case when sum(zzzs018002)=0 then '-' else to_char((sum(zqls018002) /sum(zzzs018002)*100),'990.99')||'%'end) zql018002, \n" + 
					"(case when sum(zzzs013006)=0 then '-' else to_char((sum(zqls013006) /sum(zzzs013006)*100),'990.99')||'%'end) zql013006,\n"

					+ "(case when sum(zzzs012001)=0 then '-' else to_char((sum(ssjr012001) /sum(zzzs012001)*100),'990.99')||'%'end) JRL012001,\n"
					+ "(case when sum(zzzs013001)=0 then '-' else to_char((sum(ssjr013001) /sum(zzzs013001)*100),'990.99')||'%'end) JRL013001,\n"
					+ "(case when sum(zzzs013002)=0 then '-' else to_char((sum(ssjr013002) /sum(zzzs013002)*100),'990.99')||'%'end) JRL013002,\n"
					+ "(case when sum(zzzs013003)=0 then '-' else to_char((sum(ssjr013003) /sum(zzzs013003)*100),'990.99')||'%'end) JRL013003,\n"
					+ "(case when sum(zzzs018003)=0 then '-' else to_char((sum(ssjr018003) /sum(zzzs018003)*100),'990.99')||'%'end) JRL018003,\n"
					+ "(case when sum(zzzs013004)=0 then '-' else to_char((sum(ssjr013004) /sum(zzzs013004)*100),'990.99')||'%'end) JRL013004,\n"
					+ "(case when sum(zzzs013005)=0 then '-' else to_char((sum(ssjr013005) /sum(zzzs013005)*100),'990.99')||'%'end) JRL013005,\n"
					+ "(case when sum(zzzs014001)=0 then '-' else to_char((sum(ssjr014001) /sum(zzzs014001)*100),'990.99')||'%'end) JRL014001,\n"
					+ "(case when sum(zzzs018001)=0 then '-' else to_char((sum(ssjr018001) /sum(zzzs018001)*100),'990.99')||'%'end) JRL018001,\n"
					+ "(case when sum(zzzs018002)=0 then '-' else to_char((sum(ssjr018002) /sum(zzzs018002)*100),'990.99')||'%'end) JRL018002,\n"
					+ "(case when sum(zzzs013006)=0 then '-' else to_char((sum(ssjr013006) /sum(zzzs013006)*100),'990.99')||'%'end) JRL013006,\n"
					+ "sum(allzzzs) allzzzs,sum(allssjr) allssjr,"
					+ "(case when sum(allzzzs)=0 then '-' else to_char((sum(allssjr) /sum(allzzzs)*100),'990.99')||'%' end) alljrl"
				
					+ " ,'0' PX from tmp "
					+ " ) order by PX,pms_xh ";

					
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			//count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			   
			qro.setItems(result);
			//qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			log.info("执行按监测类型统计输电监测装置运行状况时出错！", e);
			e.printStackTrace();
		}

		return qro;
		
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
		
		String sql = "with tmp as(select temp.*,"
				+ "(zzzs012001+zzzs013001+zzzs013002+zzzs013003+zzzs018003+zzzs013004+zzzs013005+zzzs014001+zzzs018001+zzzs018002+zzzs013006) allzzzs,"
				+ "(ssjr012001+ssjr013001+ssjr013002+ssjr013003+ssjr018003+ssjr013004+ssjr013005+ssjr014001+ssjr018001+ssjr018002+ssjr013006) allssjr,"
				+ "(nvl(round((((ssjr012001+ssjr013001+ssjr013002+ssjr013003+ssjr018003+ssjr013004+ssjr013005+ssjr014001+ssjr018001+ssjr018002+ssjr013006)/"
				+ "decode((zzzs012001+zzzs013001+zzzs013002+zzzs013003+zzzs018003+zzzs013004+zzzs013005+zzzs014001+zzzs018001+zzzs018002+zzzs013006),0,null,"
				+ "(zzzs012001+zzzs013001+zzzs013002+zzzs013003+zzzs018003+zzzs013004+zzzs013005+zzzs014001+zzzs018001+zzzs018002+zzzs013006)))*100),2),0))||'%' alljrl"
				
				+ " from (select decode(a.manufacturer,null,'(空)',a.manufacturer) sswsmc,"
				+ "sum(zzzs012001) zzzs012001,sum(zzzs013001) zzzs013001,sum(zzzs013002) zzzs013002,"
				+ "sum(zzzs013003) zzzs013003,sum(zzzs018003) zzzs018003,sum(zzzs013004) zzzs013004,"
				+ "sum(zzzs013005) zzzs013005,sum(zzzs014001) zzzs014001,"
				+ "sum(zzzs018001) zzzs018001,sum(zzzs018002) zzzs018002,sum(zzzs013006) zzzs013006,"
				+ "NVL(sum(ssjr012001),0) ssjr012001,NVL(sum(ssjr013001),0) ssjr013001,NVL(sum(ssjr013002),0) ssjr013002,"
				+ "NVL(sum(ssjr013003),0) ssjr013003,NVL(sum(ssjr018003),0) ssjr018003,NVL(sum(ssjr013004),0) ssjr013004,NVL(sum(ssjr013005),0) ssjr013005,\n"+
				"NVL(sum(sjjrl012001),0) sjjrls012001,NVL(sum(sjjrl013001),0) sjjrls013001,NVL(sum(sjjrl013002),0) sjjrls013002, \n" +
				"NVL(sum(sjjrl013003),0) sjjrls013003,NVL(sum(sjjrl018003),0) sjjrls018003,NVL(sum(sjjrl013004),0) sjjrls013004, \n" + 
				"NVL(sum(sjjrl013005),0) sjjrls013005,NVL(sum(sjjrl014001),0) sjjrls014001,NVL(sum(sjjrl018001),0) sjjrls018001, \n" + 
				"NVL(sum(sjjrl018002),0) sjjrls018002,NVL(sum(sjjrl013006),0) sjjrls013006, \n" + 
				" \n" + 
				"NVL(sum(zql012001),0) zqls012001,NVL(sum(zql013001),0) zqls013001,NVL(sum(zql013002),0) zqls013002, \n" + 
				"NVL(sum(zql013003),0) zqls013003,NVL(sum(zql018003),0) zqls018003,NVL(sum(zql013004),0) zqls013004, \n" + 
				"NVL(sum(zql013005),0) zqls013005,NVL(sum(zql014001),0) zqls014001,NVL(sum(zql018001),0) zqls018001, \n" + 
				"NVL(sum(zql018002),0) zqls018002,NVL(sum(zql013006),0) zqls013006, \n" + 
				" \n" + 

				"NVL(sum(df012001),0) dfzs012001,NVL(sum(df013001),0) dfzs013001,NVL(sum(df013002),0) dfzs013002, \n" +
				"NVL(sum(df013003),0) dfzs013003,NVL(sum(df018003),0) dfzs018003,NVL(sum(df013004),0) dfzs013004, \n" + 
				"NVL(sum(df013005),0) dfzs013005,NVL(sum(df014001),0) dfzs014001,NVL(sum(df018001),0) dfzs018001, \n" + 
				"NVL(sum(df018002),0) dfzs018002,NVL(sum(df013006),0) dfzs013006, \n" + 
				"NVl(round(sum(df012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001)),2),0) df012001,NVl(round(sum(df013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001)),2),0) df013001,NVl(round(sum(df013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002)),2),0) df013002, \n" + 
				"NVl(round(sum(df013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003)),2),0) df013003,NVl(round(sum(df018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003)),2),0) df018003,NVl(round(sum(df013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004)),2),0) df013004, \n" + 
				"NVl(round(sum(df013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005)),2),0) df013005,NVl(round(sum(df014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001)),2),0) df014001,NVl(round(sum(df018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001)),2),0) df018001, \n" + 
				"NVl(round(sum(df018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002)),2),0) df018002,NVl(round(sum(df013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006)),2),0) df013006,\n"

				+ "NVL(sum(ssjr014001),0) ssjr014001,NVL(sum(ssjr018001),0) ssjr018001,NVL(sum(ssjr018002),0) ssjr018002,NVL(sum(ssjr013006),0) ssjr013006,"
				+ "NVL(round((sum(ssjr012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001))*100),2),0)||'%' JRL012001,"
				+ "NVL(round((sum(ssjr013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001))*100),2),0)||'%' JRL013001,"
				+ "NVL(round((sum(ssjr013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002))*100),2),0)||'%' JRL013002,"
				+ "NVL(round((sum(ssjr013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003))*100),2),0)||'%' JRL013003,"
				+ "NVL(round((sum(ssjr018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003))*100),2),0)||'%' JRL018003,"
				+ "NVL(round((sum(ssjr013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004))*100),2),0)||'%' JRL013004,"
				+ "NVL(round((sum(ssjr013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005))*100),2),0)||'%' JRL013005,"
				+ "NVL(round((sum(ssjr014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001))*100),2),0)||'%' JRL014001,"
				+ "NVL(round((sum(ssjr018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001))*100),2),0)||'%' JRL018001,"
				+ "NVL(round((sum(ssjr018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002))*100),2),0)||'%' JRL018002,"
				+ "NVL(round((sum(ssjr013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006))*100),2),0)||'%' JRL013006,\n"+
				"NVL(round((sum(sjjrl012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001))*100),2),0)||'%' sjjrl012001, \n" +
				" NVL(round((sum(sjjrl013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001))*100),2),0)||'%' sjjrl013001, \n" + 
				" NVL(round((sum(sjjrl013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002))*100),2),0)||'%' sjjrl013002, \n" + 
				" NVL(round((sum(sjjrl013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003))*100),2),0)||'%' sjjrl013003, \n" + 
				" NVL(round((sum(sjjrl018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003))*100),2),0)||'%' sjjrl018003, \n" + 
				" NVL(round((sum(sjjrl013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004))*100),2),0)||'%' sjjrl013004, \n" + 
				" NVL(round((sum(sjjrl013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005))*100),2),0)||'%' sjjrl013005, \n" + 
				" NVL(round((sum(sjjrl014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001))*100),2),0)||'%' sjjrl014001, \n" + 
				" NVL(round((sum(sjjrl018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001))*100),2),0)||'%' sjjrl018001, \n" + 
				" NVL(round((sum(sjjrl018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002))*100),2),0)||'%' sjjrl018002, \n" + 
				" NVL(round((sum(sjjrl013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006))*100),2),0)||'%' sjjrl013006, \n" + 
				" \n" + 
				" NVL(round((sum(zql012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001))*100),2),0)||'%' zql012001, \n" + 
				" NVL(round((sum(zql013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001))*100),2),0)||'%' zql013001, \n" + 
				" NVL(round((sum(zql013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002))*100),2),0)||'%' zql013002, \n" + 
				" NVL(round((sum(zql013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003))*100),2),0)||'%' zql013003, \n" + 
				" NVL(round((sum(zql018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003))*100),2),0)||'%' zql018003, \n" + 
				" NVL(round((sum(zql013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004))*100),2),0)||'%' zql013004, \n" + 
				" NVL(round((sum(zql013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005))*100),2),0)||'%' zql013005, \n" + 
				" NVL(round((sum(zql014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001))*100),2),0)||'%' zql014001, \n" + 
				" NVL(round((sum(zql018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001))*100),2),0)||'%' zql018001, \n" + 
				" NVL(round((sum(zql018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002))*100),2),0)||'%' zql018002, \n" + 
				" NVL(round((sum(zql013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006))*100),2),0)||'%' zql013006\n"
				+ " from "
				+ "(select manufacturer,monitoringtype,\n"+

				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '012001') zzzs012001, \n" +
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013001') zzzs013001, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013002') zzzs013002, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013003') zzzs013003, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '018003') zzzs018003, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013004') zzzs013004, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013005') zzzs013005, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '014001') zzzs014001, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '018001') zzzs018001, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '018002') zzzs018002, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where ((l.manufacturer = dev.manufacturer and dev.manufacturer is not null) or (l.manufacturer is null and dev.manufacturer is null)) and l.MONITORINGTYPE != '018003'  and ischeck = '1' and dev.monitoringtype =l.monitoringtype and l.monitoringtype = '013006') zzzs013006,\n"+

				"(case monitoringtype when '012001' then sum(dataaccessrate) else 0 end) sjjrl012001, \n" +
				"  (case monitoringtype when '013001' then sum(dataaccessrate) else 0 end) sjjrl013001, \n" + 
				"  (case monitoringtype when '013002' then sum(dataaccessrate) else 0 end) sjjrl013002, \n" + 
				"  (case monitoringtype when '013003' then sum(dataaccessrate) else 0 end) sjjrl013003, \n" + 
				"  (case monitoringtype when '018003' then sum(dataaccessrate) else 0 end) sjjrl018003, \n" + 
				"  (case monitoringtype when '013004' then sum(dataaccessrate) else 0 end) sjjrl013004, \n" + 
				"  (case monitoringtype when '013005' then sum(dataaccessrate) else 0 end) sjjrl013005, \n" + 
				"  (case monitoringtype when '014001' then sum(dataaccessrate) else 0 end) sjjrl014001, \n" + 
				"  (case monitoringtype when '018001' then sum(dataaccessrate) else 0 end) sjjrl018001, \n" + 
				"  (case monitoringtype when '018002' then sum(dataaccessrate) else 0 end) sjjrl018002, \n" + 
				"  (case monitoringtype when '013006' then sum(dataaccessrate) else 0 end) sjjrl013006, \n" + 
				" \n" + 
				"  (case monitoringtype when '012001' then sum(dataaraccuracyrate) else 0 end) zql012001, \n" + 
				"  (case monitoringtype when '013001' then sum(dataaraccuracyrate) else 0 end) zql013001, \n" + 
				"  (case monitoringtype when '013002' then sum(dataaraccuracyrate) else 0 end) zql013002, \n" + 
				"  (case monitoringtype when '013003' then sum(dataaraccuracyrate) else 0 end) zql013003, \n" + 
				"  (case monitoringtype when '018003' then sum(dataaraccuracyrate) else 0 end) zql018003, \n" + 
				"  (case monitoringtype when '013004' then sum(dataaraccuracyrate) else 0 end) zql013004, \n" + 
				"  (case monitoringtype when '013005' then sum(dataaraccuracyrate) else 0 end) zql013005, \n" + 
				"  (case monitoringtype when '014001' then sum(dataaraccuracyrate) else 0 end) zql014001, \n" + 
				"  (case monitoringtype when '018001' then sum(dataaraccuracyrate) else 0 end) zql018001, \n" + 
				"  (case monitoringtype when '018002' then sum(dataaraccuracyrate) else 0 end) zql018002, \n" + 
				"  (case monitoringtype when '013006' then sum(dataaraccuracyrate) else 0 end) zql013006, \n" + 
				" \n" + 
				"  (case monitoringtype when '012001' then sum(allscored) else 0 end) df012001, \n" + 
				"  (case monitoringtype when '013001' then sum(allscored) else 0 end) df013001, \n" + 
				"  (case monitoringtype when '013002' then sum(allscored) else 0 end) df013002, \n" + 
				"  (case monitoringtype when '013003' then sum(allscored) else 0 end) df013003, \n" + 
				"  (case monitoringtype when '018003' then sum(allscored) else 0 end) df018003, \n" + 
				"  (case monitoringtype when '013004' then sum(allscored) else 0 end) df013004, \n" + 
				"  (case monitoringtype when '013005' then sum(allscored) else 0 end) df013005, \n" + 
				"  (case monitoringtype when '014001' then sum(allscored) else 0 end) df014001, \n" + 
				"  (case monitoringtype when '018001' then sum(allscored) else 0 end) df018001, \n" + 
				"  (case monitoringtype when '018002' then sum(allscored) else 0 end) df018002, \n" + 
				"  (case monitoringtype when '013006' then sum(allscored) else 0 end) df013006\n" 
				
				+ " from (select l.*, dataaccessrate,dataaraccuracyrate,allscored from mw_app.cmsv_linedevice_logic_xtf l ,\n"
				+ " mw_app.cmsv_index_scored i where l.devicecode = i.devicecode and i.monitoringtype = l.MONITORINGTYPE "
				+ querySql
				+ " ) dev where 1=1 "
				+ querySq
				+ " group by dev.manufacturer,dev.monitoringtype) a"
				+ " left join "
				+ "	(select manufacturer,monitoringtype,"
				+ "	       (case monitoringtype when '012001' then count(monitoringtype) else 0 end) ssjr012001,"
				+ "	       (case monitoringtype when '013001' then count(monitoringtype) else 0 end) ssjr013001,"
				+ "	       (case monitoringtype when '013002' then count(monitoringtype) else 0 end) ssjr013002,"
				+ "	       (case monitoringtype when '013003' then count(monitoringtype) else 0 end) ssjr013003,"
				+ "	       (case monitoringtype when '018003' then count(monitoringtype) else 0 end) ssjr018003,"
				+ "	       (case monitoringtype when '013004' then count(monitoringtype) else 0 end) ssjr013004,"
				+ "	       (case monitoringtype when '013005' then count(monitoringtype) else 0 end) ssjr013005,"
				+ "	       (case monitoringtype when '014001' then count(monitoringtype) else 0 end) ssjr014001,"
				+ "	       (case monitoringtype when '018001' then count(monitoringtype) else 0 end) ssjr018001,"
				+ "	       (case monitoringtype when '018002' then count(monitoringtype) else 0 end) ssjr018002,"
				+ "	       (case monitoringtype when '013006' then count(monitoringtype) else 0 end) ssjr013006 "
				+ "	 from (select l.* from mw_app.cmsv_linedevice_logic_xtf l ,\n"
				+ " mw_app.cmsv_index_scored i where l.devicecode = i.devicecode and i.monitoringtype = l.MONITORINGTYPE "
				+ querySql
				+ " ) dev"
				+ "	where"
				+ "  (dev.devicecode,dev.monitoringtype) in(select zzbm,jclx from mw_app.cmsv_deviceused_info where sfss = 'T')"
				+ querySq
				+ "	  group by dev.manufacturer,dev.monitoringtype) b"
				+ "	 on a.manufacturer = b.manufacturer and a.monitoringtype = b.monitoringtype"
				+ "	 group by a.manufacturer order by a.manufacturer) temp)"
				+ " select * from ( "
				+ " select sswsmc,"
				+ "zzzs012001,zzzs013001,zzzs013002,zzzs013003,zzzs018003,zzzs013004,\n"
				+ "zzzs013005,zzzs014001,zzzs018001,zzzs018002,zzzs013006,\n"
				+ "ssjr012001,ssjr013001,ssjr013002,ssjr013003,ssjr018003,\n"
				+ "ssjr013004,ssjr013005,ssjr014001,ssjr018001,ssjr018002,ssjr013006,\n"+
				"df012001,df013001,df013002,df013003,df018003, \n" +
				"df013004,df013005,df014001,df018001,df018002,df013006,\n"+
				
				"(case when zzzs012001=0 then '-' else to_char(substr(sjJRL012001,0,length(sjJRL012001)-1),'990.99')||'%'end) sjJRL012001, \n" +
				"(case when zzzs013001=0 then '-' else to_char(substr(sjJRL013001,0,length(sjJRL013001)-1),'990.99')||'%'end) sjJRL013001, \n" + 
				"(case when zzzs013002=0 then '-' else to_char(substr(sjJRL013002,0,length(sjJRL013002)-1),'990.99')||'%'end) sjJRL013002, \n" + 
				"(case when zzzs013003=0 then '-' else to_char(substr(sjJRL013003,0,length(sjJRL013003)-1),'990.99')||'%'end) sjJRL013003, \n" + 
				"(case when zzzs018003=0 then '-' else to_char(substr(sjJRL018003,0,length(sjJRL018003)-1),'990.99')||'%'end) sjJRL018003, \n" + 
				"(case when zzzs013004=0 then '-' else to_char(substr(sjJRL013004,0,length(sjJRL013004)-1),'990.99')||'%'end) sjJRL013004, \n" + 
				"(case when zzzs013005=0 then '-' else to_char(substr(sjJRL013005,0,length(sjJRL013005)-1),'990.99')||'%'end) sjJRL013005, \n" + 
				"(case when zzzs014001=0 then '-' else to_char(substr(sjJRL014001,0,length(sjJRL014001)-1),'990.99')||'%'end) sjJRL014001, \n" + 
				"(case when zzzs018001=0 then '-' else to_char(substr(sjJRL018001,0,length(sjJRL018001)-1),'990.99')||'%'end) sjJRL018001, \n" + 
				"(case when zzzs018002=0 then '-' else to_char(substr(sjJRL018002,0,length(sjJRL018002)-1),'990.99')||'%'end) sjJRL018002, \n" + 
				"(case when zzzs013006=0 then '-' else to_char(substr(sjJRL013006,0,length(sjJRL013006)-1),'990.99')||'%'end) sjJRL013006, \n" + 
				" \n" + 
				"(case when zzzs012001=0 then '-' else to_char(substr(zql012001,0,length(zql012001)-1),'990.99')||'%'end) zql012001, \n" + 
				"(case when zzzs013001=0 then '-' else to_char(substr(zql013001,0,length(zql013001)-1),'990.99')||'%'end) zql013001, \n" + 
				"(case when zzzs013002=0 then '-' else to_char(substr(zql013002,0,length(zql013002)-1),'990.99')||'%'end) zql013002, \n" + 
				"(case when zzzs013003=0 then '-' else to_char(substr(zql013003,0,length(zql013003)-1),'990.99')||'%'end) zql013003, \n" + 
				"(case when zzzs018003=0 then '-' else to_char(substr(zql018003,0,length(zql018003)-1),'990.99')||'%'end) zql018003, \n" + 
				"(case when zzzs013004=0 then '-' else to_char(substr(zql013004,0,length(zql013004)-1),'990.99')||'%'end) zql013004, \n" + 
				"(case when zzzs013005=0 then '-' else to_char(substr(zql013005,0,length(zql013005)-1),'990.99')||'%'end) zql013005, \n" + 
				"(case when zzzs014001=0 then '-' else to_char(substr(zql014001,0,length(zql014001)-1),'990.99')||'%'end) zql014001, \n" + 
				"(case when zzzs018001=0 then '-' else to_char(substr(zql018001,0,length(zql018001)-1),'990.99')||'%'end) zql018001, \n" + 
				"(case when zzzs018002=0 then '-' else to_char(substr(zql018002,0,length(zql018002)-1),'990.99')||'%'end) zql018002, \n" + 
				"(case when zzzs013006=0 then '-' else to_char(substr(zql013006,0,length(zql013006)-1),'990.99')||'%'end) zql013006,\n"
				+ "(case when zzzs012001=0 then '-' else to_char(substr(JRL012001,0,length(JRL012001)-1),'990.99')||'%'end) JRL012001,\n"
				+ "(case when zzzs013001=0 then '-' else to_char(substr(JRL013001,0,length(JRL013001)-1),'990.99')||'%'end) JRL013001,\n"
				+ "(case when zzzs013002=0 then '-' else to_char(substr(JRL013002,0,length(JRL013002)-1),'990.99')||'%'end) JRL013002,\n"
				+ "(case when zzzs013003=0 then '-' else to_char(substr(JRL013003,0,length(JRL013003)-1),'990.99')||'%'end) JRL013003,\n"
				+ "(case when zzzs018003=0 then '-' else to_char(substr(JRL018003,0,length(JRL018003)-1),'990.99')||'%'end) JRL018003,\n"
				+ "(case when zzzs013004=0 then '-' else to_char(substr(JRL013004,0,length(JRL013004)-1),'990.99')||'%'end) JRL013004,\n"
				+ "(case when zzzs013005=0 then '-' else to_char(substr(JRL013005,0,length(JRL013005)-1),'990.99')||'%'end) JRL013005,\n"
				+ "(case when zzzs014001=0 then '-' else to_char(substr(JRL014001,0,length(JRL014001)-1),'990.99')||'%'end) JRL014001,\n"
				+ "(case when zzzs018001=0 then '-' else to_char(substr(JRL018001,0,length(JRL018001)-1),'990.99')||'%'end) JRL018001,\n"
				+ "(case when zzzs018002=0 then '-' else to_char(substr(JRL018002,0,length(JRL018002)-1),'990.99')||'%'end) JRL018002,\n"
				+ "(case when zzzs013006=0 then '-' else to_char(substr(JRL013006,0,length(JRL013006)-1),'990.99')||'%'end) JRL013006,\n"
				
				+ "allzzzs,allssjr,(case when allzzzs=0 then '-' else to_char(substr(alljrl,0,length(alljrl)-1),'990.99')||'%'end) alljrl \n"
				+ " ,'1' PX from tmp union all select '厂家合计' sswsmc,"
				+ "sum(zzzs012001) zzzs012001,sum(zzzs013001) zzzs013001,sum(zzzs013002) zzzs013002,"
				+ "sum(zzzs013003) zzzs013003,sum(zzzs018003) zzzs018003,sum(zzzs013004) zzzs013004,"
				+ "sum(zzzs013005) zzzs013005,sum(zzzs014001) zzzs014001,"
				+ "sum(zzzs018001) zzzs018001,sum(zzzs018002) zzzs018002,sum(zzzs013006) zzzs013006,"
				+ "NVL(sum(ssjr012001),0) ssjr012001,NVL(sum(ssjr013001),0) ssjr013001,NVL(sum(ssjr013002),0) ssjr013002,"
				+ "NVL(sum(ssjr013003),0) ssjr013003,NVL(sum(ssjr018003),0) ssjr018003,NVL(sum(ssjr013004),0) ssjr013004,NVL(sum(ssjr013005),0) ssjr013005,"
				+ "NVL(sum(ssjr014001),0) ssjr014001,NVL(sum(ssjr018001),0) ssjr018001,NVL(sum(ssjr018002),0) ssjr018002,NVL(sum(ssjr013006),0) ssjr013006,"+

				"NVl(round(sum(dfzs012001)/decode(sum(zzzs012001),0,null,sum(zzzs012001)),2),0) df012001,NVl(round(sum(dfzs013001)/decode(sum(zzzs013001),0,null,sum(zzzs013001)),2),0) df013001,NVl(round(sum(dfzs013002)/decode(sum(zzzs013002),0,null,sum(zzzs013002)),2),0) df013002, \n" +
				"NVl(round(sum(dfzs013003)/decode(sum(zzzs013003),0,null,sum(zzzs013003)),2),0) df013003,NVl(round(sum(dfzs018003)/decode(sum(zzzs018003),0,null,sum(zzzs018003)),2),0) df018003,NVl(round(sum(dfzs013004)/decode(sum(zzzs013004),0,null,sum(zzzs013004)),2),0) df013004, \n" + 
				"NVl(round(sum(dfzs013005)/decode(sum(zzzs013005),0,null,sum(zzzs013005)),2),0) df013005,NVl(round(sum(dfzs014001)/decode(sum(zzzs014001),0,null,sum(zzzs014001)),2),0) df014001,NVl(round(sum(dfzs018001)/decode(sum(zzzs018001),0,null,sum(zzzs018001)),2),0) df018001, \n" + 
				"NVl(round(sum(dfzs018002)/decode(sum(zzzs018002),0,null,sum(zzzs018002)),2),0) df018002,NVl(round(sum(dfzs013006)/decode(sum(zzzs013006),0,null,sum(zzzs013006)),2),0) df013006,\n"+

				
				"(case when sum(zzzs012001)=0 then '-' else to_char((sum(sjjrls012001) /sum(zzzs012001)*100),'990.99')||'%'end) sjjrl012001, \n" +
				"(case when sum(zzzs013001)=0 then '-' else to_char((sum(sjjrls013001) /sum(zzzs013001)*100),'990.99')||'%'end) sjjrl013001, \n" + 
				"(case when sum(zzzs013002)=0 then '-' else to_char((sum(sjjrls013002) /sum(zzzs013002)*100),'990.99')||'%'end) sjjrl013002, \n" + 
				"(case when sum(zzzs013003)=0 then '-' else to_char((sum(sjjrls013003) /sum(zzzs013003)*100),'990.99')||'%'end) sjjrl013003, \n" + 
				"(case when sum(zzzs018003)=0 then '-' else to_char((sum(sjjrls018003) /sum(zzzs018003)*100),'990.99')||'%'end) sjjrl018003, \n" + 
				"(case when sum(zzzs013004)=0 then '-' else to_char((sum(sjjrls013004) /sum(zzzs013004)*100),'990.99')||'%'end) sjjrl013004, \n" + 
				"(case when sum(zzzs013005)=0 then '-' else to_char((sum(sjjrls013005) /sum(zzzs013005)*100),'990.99')||'%'end) sjjrl013005, \n" + 
				"(case when sum(zzzs014001)=0 then '-' else to_char((sum(sjjrls014001) /sum(zzzs014001)*100),'990.99')||'%'end) sjjrl014001, \n" + 
				"(case when sum(zzzs018001)=0 then '-' else to_char((sum(sjjrls018001) /sum(zzzs018001)*100),'990.99')||'%'end) sjjrl018001, \n" + 
				"(case when sum(zzzs018002)=0 then '-' else to_char((sum(sjjrls018002) /sum(zzzs018002)*100),'990.99')||'%'end) sjjrl018002, \n" + 
				"(case when sum(zzzs013006)=0 then '-' else to_char((sum(sjjrls013006) /sum(zzzs013006)*100),'990.99')||'%'end) sjjrl013006, \n" + 
				" \n" + 
				"(case when sum(zzzs012001)=0 then '-' else to_char((sum(zqls012001) /sum(zzzs012001)*100),'990.99')||'%'end) zql012001, \n" + 
				"(case when sum(zzzs013001)=0 then '-' else to_char((sum(zqls013001) /sum(zzzs013001)*100),'990.99')||'%'end) zql013001, \n" + 
				"(case when sum(zzzs013002)=0 then '-' else to_char((sum(zqls013002) /sum(zzzs013002)*100),'990.99')||'%'end) zql013002, \n" + 
				"(case when sum(zzzs013003)=0 then '-' else to_char((sum(zqls013003) /sum(zzzs013003)*100),'990.99')||'%'end) zql013003, \n" + 
				"(case when sum(zzzs018003)=0 then '-' else to_char((sum(zqls018003) /sum(zzzs018003)*100),'990.99')||'%'end) zql018003, \n" + 
				"(case when sum(zzzs013004)=0 then '-' else to_char((sum(zqls013004) /sum(zzzs013004)*100),'990.99')||'%'end) zql013004, \n" + 
				"(case when sum(zzzs013005)=0 then '-' else to_char((sum(zqls013005) /sum(zzzs013005)*100),'990.99')||'%'end) zql013005, \n" + 
				"(case when sum(zzzs014001)=0 then '-' else to_char((sum(zqls014001) /sum(zzzs014001)*100),'990.99')||'%'end) zql014001, \n" + 
				"(case when sum(zzzs018001)=0 then '-' else to_char((sum(zqls018001) /sum(zzzs018001)*100),'990.99')||'%'end) zql018001, \n" + 
				"(case when sum(zzzs018002)=0 then '-' else to_char((sum(zqls018002) /sum(zzzs018002)*100),'990.99')||'%'end) zql018002, \n" + 
				"(case when sum(zzzs013006)=0 then '-' else to_char((sum(zqls013006) /sum(zzzs013006)*100),'990.99')||'%'end) zql013006,\n"
				+ "(case when sum(zzzs012001)=0 then '-' else to_char((sum(ssjr012001) /sum(zzzs012001)*100),'990.99')||'%'end) JRL012001,\n"
				+ "(case when sum(zzzs013001)=0 then '-' else to_char((sum(ssjr013001) /sum(zzzs013001)*100),'990.99')||'%'end) JRL013001,\n"
				+ "(case when sum(zzzs013002)=0 then '-' else to_char((sum(ssjr013002) /sum(zzzs013002)*100),'990.99')||'%'end) JRL013002,\n"
				+ "(case when sum(zzzs013003)=0 then '-' else to_char((sum(ssjr013003) /sum(zzzs013003)*100),'990.99')||'%'end) JRL013003,\n"
				+ "(case when sum(zzzs018003)=0 then '-' else to_char((sum(ssjr018003) /sum(zzzs018003)*100),'990.99')||'%'end) JRL018003,\n"
				+ "(case when sum(zzzs013004)=0 then '-' else to_char((sum(ssjr013004) /sum(zzzs013004)*100),'990.99')||'%'end) JRL013004,\n"
				+ "(case when sum(zzzs013005)=0 then '-' else to_char((sum(ssjr013005) /sum(zzzs013005)*100),'990.99')||'%'end) JRL013005,\n"
				+ "(case when sum(zzzs014001)=0 then '-' else to_char((sum(ssjr014001) /sum(zzzs014001)*100),'990.99')||'%'end) JRL014001,\n"
				+ "(case when sum(zzzs018001)=0 then '-' else to_char((sum(ssjr018001) /sum(zzzs018001)*100),'990.99')||'%'end) JRL018001,\n"
				+ "(case when sum(zzzs018002)=0 then '-' else to_char((sum(ssjr018002) /sum(zzzs018002)*100),'990.99')||'%'end) JRL018002,\n"
				+ "(case when sum(zzzs013006)=0 then '-' else to_char((sum(ssjr013006) /sum(zzzs013006)*100),'990.99')||'%'end) JRL013006,\n"
				
				+ "sum(allzzzs) allzzzs,sum(allssjr) allssjr,"
				+ "(case when sum(allzzzs)=0 then '-' else to_char((sum(allssjr) /sum(allzzzs)*100),'990.99')||'%' end) alljrl"
		
				+ " ,'0' PX from tmp " + " ) order by PX,sswsmc ";
		
		try{
			cols = "SSWSMC,ZZZS012001,ZZZS013001,ZZZS013002,ZZZS013003,ZZZS018003,ZZZS013004,ZZZS013005," +
					"ZZZS014001,ZZZS018001,ZZZS018002,ZZZS013006,SSJR012001,SSJR013001,SSJR013002," +
					"SSJR013003,SSJR018003,SSJR013004,SSJR013005,SSJR014001,SSJR018001,SSJR018002," +
					"SSJR013006,DF012001,DF013001,DF013002,DF013003,DF018003,DF013004,DF013005,DF014001," +
					"DF018001,DF018002,DF013006,SJJRL012001,SJJRL013001,SJJRL013002,SJJRL013003,SJJRL018003," +
					"SJJRL013004,SJJRL013005,SJJRL014001,SJJRL018001,SJJRL018002,SJJRL013006,ZQL012001," +
					"ZQL013001,ZQL013002,ZQL013003,ZQL018003,ZQL013004,ZQL013005,ZQL014001,ZQL018001," +
					"ZQL018002,ZQL013006,JRL012001,JRL013001,JRL013002,JRL013003,JRL018003,JRL013004," +
					"JRL013005,JRL014001,JRL018001,JRL018002,JRL013006,ALLZZZS,ALLSSJR,ALLJRL,PX";
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行按生产厂家统计输电监测装置运行状况时出错",e);
		}
		
		return qro;
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
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行按生产厂家统计输电检测装置时出错",e);
		}
		
		return qro;
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
                       "   where t.linkedstation = n.linkedstation  and rownum <= 1) linkedstationname,  max(t3.djdyMC) MC, t1.wsmc PROVINCE_NAME \n"+
                        "  from (select linkedstation,wsmc,linkedequipmentdy,linkedprovicedept,monitoringtypes,MANUFACTURER,LINKEDEQUIPMENTNAME,RELEASEDATE,RUNDATE,linkedstationname  from mw_app.CMST_transfdevice t0,  MW_APP.cmst_zb_comm_wspz t1  \n"+
                       " where t0.linkedprovicedept = t1.wsid and t0.LINKEDEQUIPMENTDY IS NOT NULL) t, mw_app.CMST_SB_PZ_SBDYDJ t3 \n"+
                       " where t.linkedequipmentdy = t3.dydjbm "+querySql.toString()+" and t1.wsmc is not null \n"+
                       " group by t.linkedstation, t1.wsmc ";
		try{
			 cols = "LINKEDSTATION,LINKEDSTATIONNAME,MC,PROVINCE_NAME,";
				log.info(sql);
				result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
			} catch (Exception e) {
				log.info("执行sql时出错！", e);
				e.printStackTrace();
			}
		return qro;
	}
		
}
