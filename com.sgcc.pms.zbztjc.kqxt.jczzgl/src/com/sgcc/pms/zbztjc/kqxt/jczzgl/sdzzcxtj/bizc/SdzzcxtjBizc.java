package com.sgcc.pms.zbztjc.kqxt.jczzgl.sdzzcxtj.bizc;

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
/**
 * 输电装置查询
 * 
 * @author wml
 * 
 */
public class SdzzcxtjBizc implements ISdzzcxtjBizc{


	private final Log log = LogFactory.getLog(SdzzcxtjBizc.class);
	@Resource
	private IHibernateDao hibernateDao_ztjc;


	
	 public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}
	 
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
		public QueryResultObject query(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
			int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
			
			StringBuffer querySql = new StringBuffer();
			
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

			String sql = "WITH tab AS(SELECT T.*, (CASE WHEN (SELECT COUNT(1) FROM MW_APP.CMSV_DEVICEUSED_INFO DI \n"+
					" WHERE DI.ZZBM = T.DEVICECODE  AND DI.SFSS = 'T') > 0 THEN 'T' ELSE 'F' END) SFSS \n"+
					" FROM (SELECT OBJ_ID, LINKEDDEP, DEPMC, cast('查看' as varchar(42)) LOOKUP, XH, LINKEDDEPWS, WSDEPMC, \n"+
					" LINKEDEQUIPMENTDY, DEVICEVOLTAGE,  DYDJGDJB, LINKEDPOLE, LINKEDLINE, LINKEDLINENAME, \n"+
					" LINKEDPOLENAME, DEVICECATEGORY, DEVICECATEGORYDM, DEVICECODE, DEVICENAME, \n"+
					" MONITORINGTYPES, DEVICECATEGORY_DISPLAY, DEVICEMODEL,  MANUFACTURER,   RELEASEDATE, \n"+
					" RUNDATE, RELEASENUMBER, REMARKS, JCXX, ISACTIVATED, XTMC, linkedprovicedept \n"+
					" FROM MW_APP.CMSV_LINEDEVICEf t  where 1 = 1 and t.WSDEPMC is not null and t.DEVICEVOLTAGE is not null"+querySql.toString()+" \n"+
					" order by t.XH) T \n"+
					" where t.WSDEPMC NOT LIKE '%电网%' AND t.WSDEPMC NOT LIKE '%分部%'AND t.linkedprovicedept IS NOT NULL \n"+
					" AND T.LINKEDEQUIPMENTDY IS NOT NULL AND T.MONITORINGTYPES LIKE '01%') \n"+
					" SELECT tab.*, DECODE(SFSS, 'T', '是', 'F', '否') ISRT FROM tab where 1 = 1";
			
			try{
				//String cols = "OBJ_ID,WSDEPMC,LINKEDLINENAME,LINKEDPOLENAME,DEVICEVOLTAGE,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,LOOKUP,DEVICENAME,DEVICEMODEL,MANUFACTURER,SFSS,RUNDATE";
				String cols = "OBJ_ID,LINKEDDEP,DEPMC,LOOKUP,XH,LINKEDDEPWS,WSDEPMC,LINKEDEQUIPMENTDY,DEVICEVOLTAGE,DYDJGDJB,LINKEDPOLE,LINKEDLINE,LINKEDLINENAME,LINKEDPOLENAME," +
						"DEVICECATEGORY,DEVICECATEGORYDM,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER," +
						"REMARKS,JCXX,ISACTIVATED,XTMC,LINKEDPROVICEDEPT,SFSS,ISRT";
				
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
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
		public QueryResultObject statByDydj(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			String xlzsColumnSql = "XLzs37+XLzs36+XLzs35+XLzs34+XLzs33+XLzs86+XLzs85+XLzs84+XLzs83+XLzs82";
			String zzzsColumnSql = "zzzs37+zzzs36+zzzs35+zzzs34+zzzs33+zzzs86+zzzs85+zzzs84+zzzs83+zzzs82";
			String fgXLzsColumnSql = "fgXLzs37+fgXLzs36+fgXLzs35+fgXLzs34+fgXLzs33+fgXLzs86+fgXLzs85+fgXLzs84+fgXLzs83+fgXLzs82";
			StringBuffer querySq = new StringBuffer();
			StringBuffer querySql = new StringBuffer();
			StringBuffer jclxFilterSql =new StringBuffer();
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
						
						xlzsColumnSql = xlzsColumnSql.substring(xlzsColumnSql.length());
						zzzsColumnSql = zzzsColumnSql.substring(zzzsColumnSql.length());
						fgXLzsColumnSql = "";
						//37,36,35
						String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
						xlzsColumnSql+="XLzs"+filterValueArr[j]+"+";
						zzzsColumnSql +="zzzs"+filterValueArr[j]+"+";
						fgXLzsColumnSql +="fgXLzs"+filterValueArr[j]+"+";
						}
						xlzsColumnSql = xlzsColumnSql.substring(0,xlzsColumnSql.length()-1);
						zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
						fgXLzsColumnSql = fgXLzsColumnSql.substring(0,fgXLzsColumnSql.length()-1);
						querySql.append(" and linkedequipmentdy in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						querySql.append(" and (");
						for(int j=0;j<filterValueArr.length;j++){
							if(j==0){
								querySql.append(" monitoringtypes like '%");
								querySql.append(filterValueArr[j]);
								querySql.append("%'");
							}else{
								querySql.append(" or monitoringtypes like '%");
								querySql.append(filterValueArr[j]);
								querySql.append("%'");
								}
							querySql.append(")");
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
			
			String sql = "with tmp AS(select temp.*,(" + zzzsColumnSql + ") allzzzs,\n" +
					"(" + fgXLzsColumnSql + ") allfgXLzs,\n" + 
					"("+xlzsColumnSql+") allxlzs,\n" + 
					"nvl(round(((" + fgXLzsColumnSql + ")/\n" + 
					"decode("+xlzsColumnSql+",0,null,\n" + 
					""+xlzsColumnSql+"))*100,2),0)||'%' allfgl" +
					" from (select wsmc SSWSMC,linkedprovicedept," +
					"(select cast(zdypx as varchar2(10)) pms_xh from mw_app.cmst_zb_comm_wspz d where d.wsid = linkedprovicedept) pms_xh," + 
					"NVL(sum(zzzs37),0) ZZZS37,NVL((case when sum(fgxlzs37)< xlzs37 then sum(fgxlzs37) else xlzs37 end),0) FGXLZS37,NVL(xlzs37,0) XLZS37,NVL(round((((case when sum(fgxlzs37)< xlzs37 then sum(fgxlzs37) else xlzs37 end)/decode(xlzs37,0,null,xlzs37))*100),2),0)||'%' FGL37," + 
					"NVL(sum(zzzs36),0) ZZZS36,NVL((case when sum(fgxlzs36)< xlzs36 then sum(fgxlzs36) else xlzs36 end),0) FGXLZS36,NVL(xlzs36,0) XLZS36,NVL(round((((case when sum(fgxlzs36)< xlzs36 then sum(fgxlzs36) else xlzs36 end)/decode(xlzs36,0,null,xlzs36))*100),2),0)||'%' FGL36," +
					"NVL(sum(zzzs35),0) ZZZS35,NVL((case when sum(fgxlzs35)< xlzs35 then sum(fgxlzs35) else xlzs35 end),0) FGXLZS35,NVL(xlzs35,0) XLZS35,NVL(round((((case when sum(fgxlzs35)< xlzs35 then sum(fgxlzs35) else xlzs35 end)/decode(xlzs35,0,null,xlzs35))*100),2),0)||'%' FGL35," + 
					"NVL(sum(zzzs34),0) ZZZS34,NVL((case when sum(fgxlzs34)< xlzs34 then sum(fgxlzs34) else xlzs34 end),0) FGXLZS34,NVL(xlzs34,0) XLZS34,NVL(round((((case when sum(fgxlzs34)< xlzs34 then sum(fgxlzs34) else xlzs34 end)/decode(xlzs34,0,null,xlzs34))*100),2),0)||'%' FGL34," + 
					"NVL(sum(zzzs33),0) ZZZS33,NVL((case when sum(fgxlzs33)< xlzs33 then sum(fgxlzs33) else xlzs33 end),0) FGXLZS33,NVL(xlzs33,0) XLZS33,NVL(round((((case when sum(fgxlzs33)< xlzs33 then sum(fgxlzs33) else xlzs33 end)/decode(xlzs33,0,null,xlzs33))*100),2),0)||'%' FGL33," + 
					"NVL(sum(zzzs32),0) ZZZS32,NVL((case when sum(fgxlzs32)< xlzs32 then sum(fgxlzs32) else xlzs32 end),0) FGXLZS32,NVL(xlzs32,0) XLZS32,NVL(round((((case when sum(fgxlzs32)< xlzs32 then sum(fgxlzs32) else xlzs32 end)/decode(xlzs32,0,null,xlzs32))*100),2),0)||'%' FGL32," + 
					"NVL(sum(zzzs30),0) ZZZS30,NVL((case when sum(fgxlzs30)< xlzs30 then sum(fgxlzs30) else xlzs30 end),0) FGXLZS30,NVL(xlzs30,0) XLZS30,NVL(round((((case when sum(fgxlzs30)< xlzs30 then sum(fgxlzs30) else xlzs30 end)/decode(xlzs30,0,null,xlzs30))*100),2),0)||'%' FGL30," +
					"NVL(sum(zzzs25),0) ZZZS25,NVL((case when sum(fgxlzs25)< xlzs25 then sum(fgxlzs25) else xlzs25 end),0) FGXLZS25,NVL(xlzs25,0) XLZS25,NVL(round((((case when sum(fgxlzs25)< xlzs25 then sum(fgxlzs25) else xlzs25 end)/decode(xlzs25,0,null,xlzs25))*100),2),0)||'%' FGL25," + 
					"NVL(sum(zzzs86),0) ZZZS86,NVL((case when sum(fgxlzs86)< xlzs86 then sum(fgxlzs86) else xlzs86 end),0) FGXLZS86,NVL(xlzs86,0) XLZS86,NVL(round((((case when sum(fgxlzs86)< xlzs86 then sum(fgxlzs86) else xlzs86 end)/decode(xlzs86,0,null,xlzs86))*100),2),0)||'%' FGL86," + 
					"NVL(sum(zzzs85),0) ZZZS85,NVL((case when sum(fgxlzs85)< xlzs85 then sum(fgxlzs85) else xlzs85 end),0) FGXLZS85,NVL(xlzs85,0) XLZS85,NVL(round((((case when sum(fgxlzs85)< xlzs85 then sum(fgxlzs85) else xlzs85 end)/decode(xlzs85,0,null,xlzs85))*100),2),0)||'%' FGL85," + 
					"NVL(sum(zzzs84),0) ZZZS84,NVL((case when sum(fgxlzs84)< xlzs84 then sum(fgxlzs84) else xlzs84 end),0) FGXLZS84,NVL(xlzs84,0) XLZS84,NVL(round((((case when sum(fgxlzs84)< xlzs84 then sum(fgxlzs84) else xlzs84 end)/decode(xlzs84,0,null,xlzs84))*100),2),0)||'%' FGL84," + 
					"NVL(sum(zzzs83),0) ZZZS83,NVL((case when sum(fgxlzs83)< xlzs83 then sum(fgxlzs83) else xlzs83 end),0) FGXLZS83,NVL(xlzs83,0) XLZS83,NVL(round((((case when sum(fgxlzs83)< xlzs83 then sum(fgxlzs83) else xlzs83 end)/decode(xlzs83,0,null,xlzs83))*100),2),0)||'%' FGL83," + 
					"NVL(sum(zzzs82),0) ZZZS82,NVL((case when sum(fgxlzs82)< xlzs82 then sum(fgxlzs82) else xlzs82 end),0) FGXLZS82,NVL(xlzs82,0) XLZS82,NVL(round((((case when sum(fgxlzs82)< xlzs82 then sum(fgxlzs82) else xlzs82 end)/decode(xlzs82,0,null,xlzs82))*100),2),0)||'%' FGL82," + 
					"NVL(sum(zzzs81),0) ZZZS81,NVL((case when sum(fgxlzs81)< xlzs81 then sum(fgxlzs81) else xlzs81 end),0) FGXLZS81,NVL(xlzs81,0) XLZS81,NVL(round((((case when sum(fgxlzs81)< xlzs81 then sum(fgxlzs81) else xlzs81 end)/decode(xlzs81,0,null,xlzs81))*100),2),0)||'%' FGL81," + 
					"NVL(sum(zzzs80),0) ZZZS80,NVL((case when sum(fgxlzs80)< xlzs80 then sum(fgxlzs80) else xlzs80 end),0) FGXLZS80,NVL(xlzs80,0) XLZS80,NVL(round((((case when sum(fgxlzs80)< xlzs80 then sum(fgxlzs80) else xlzs80 end)/decode(xlzs80,0,null,xlzs80))*100),2),0)||'%' FGL80 " + 
					" from ( select linkedprovicedept,wsmc,(case dydj when '37' then fgxlzs else 0 end) fgxlzs37," + 
					"(case dydj when '37' then zzzs else 0 end) zzzs37,(case dydj when '34' then fgxlzs else 0 end) fgxlzs34," + 
					"(case dydj when '36' then fgxlzs else 0 end) fgxlzs36,(case dydj when '34' then zzzs else 0 end) zzzs34," +
					"(case dydj when '36' then zzzs else 0 end) zzzs36,(case dydj when '33' then fgxlzs else 0 end) fgxlzs33," + 
					"(case dydj when '35' then fgxlzs else 0 end) fgxlzs35,(case dydj when '33' then zzzs else 0 end) zzzs33," +
					"(case dydj when '35' then zzzs else 0 end) zzzs35,(case dydj when '32' then fgxlzs else 0 end) fgxlzs32," +
					"(case dydj when '32' then zzzs else 0 end) zzzs32,(case dydj when '30' then fgxlzs else 0 end) fgxlzs30," + 
					"(case dydj when '30' then zzzs else 0 end) zzzs30,(case dydj when '86' then fgxlzs else 0 end) fgxlzs86," + 
					"(case dydj when '86' then zzzs else 0 end) zzzs86,(case dydj when '85' then fgxlzs else 0 end) fgxlzs85," + 
					"(case dydj when '85' then zzzs else 0 end) zzzs85,(case dydj when '84' then fgxlzs else 0 end) fgxlzs84," + 
					"(case dydj when '84' then zzzs else 0 end) zzzs84,(case dydj when '83' then fgxlzs else 0 end) fgxlzs83," + 
					"(case dydj when '83' then zzzs else 0 end) zzzs83,(case dydj when '82' then fgxlzs else 0 end) fgxlzs82," + 
					"(case dydj when '82' then zzzs else 0 end) zzzs82,(case dydj when '25' then fgxlzs else 0 end) fgxlzs25," + 
					"(case dydj when '25' then zzzs else 0 end) zzzs25,(case dydj when '81' then zzzs else 0 end) zzzs81," +
					"(case dydj when '80' then zzzs else 0 end) zzzs80," + 
					"(case dydj when '81' then fgxlzs else 0 end) fgxlzs81,(case dydj when '80' then fgxlzs else 0 end) fgxlzs80, " + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and  dydj='36' ) xlzs36,\n" +
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and  dydj='35' ) xlzs35,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and  dydj='34' ) xlzs34,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and  dydj='33' ) xlzs33,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and  dydj='32' ) xlzs32,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and  dydj='30' ) xlzs30,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and  dydj='25' ) xlzs25,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and  dydj='37' ) xlzs37,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and  dydj='86' ) xlzs86,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and dydj='85' ) xlzs85,\n" + 
					"             	  (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and dydj='84' ) xlzs84,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and dydj = '83') xlzs83,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and dydj = '82') xlzs82,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and dydj = '81') xlzs81,\n" + 
					"                 (select count(distinct sbbm)  from mw_app.cmst_sb_zwyc_xl where ssws = KKK.linkedprovicedept  and dydj = '80') xlzs80\n" + 
					"                   from (\n" + 
					"                         select count(distinct(line.devicecode)) zzzs,line.LINKEDEQUIPMENTDY dydj,\n" + 
					"                            count(distinct(linkedline)) fgxlzs, deps.wsid linkedprovicedept ,deps.wsmc\n" + 
					"                            from (select * from mw_app.cmsv_linedevicef where linkedprovicedept IS  NOT NULL and LINKEDEQUIPMENTDY IS NOT  NULL and MONITORINGTYPES LIKE '01%' \n" + querySql.toString()+
					"                             )line,MW_APP.cmst_zb_comm_wspz deps where deps.wsid = line.linkedprovicedept(+) and deps.wsid is not null\n" + 
					querySq.toString() + "               group by  deps.wsid,deps.wsmc,line.LINKEDEQUIPMENTDY\n" + 
					"                 )  KKK\n" + 
					"           ) group by linkedprovicedept,wsmc,xlzs37,xlzs36,xlzs35,xlzs34,xlzs33,xlzs32,xlzs30,xlzs25,xlzs86,xlzs85,xlzs84,xlzs83,xlzs82,xlzs81,xlzs80 "+
					" )temp)" +
					" select * from ( " +
					"SELECT SSWSMC,linkedprovicedept,pms_xh," +
					"ZZZS37,FGXLZS37,XLZS37,(case when XLZS37=0 then '-' else to_char(substr(FGL37,0,length(FGL37)-1),'990.99')||'%'end) FGL37,\n" + 
					"ZZZS36,FGXLZS36,XLZS36,(case when XLZS36=0 then '-' else to_char(substr(FGL36,0,length(FGL36)-1),'990.99')||'%'end) FGL36,\n" + 
					"ZZZS35,FGXLZS35,XLZS35,(case when XLZS35=0 then '-' else to_char(substr(FGL35,0,length(FGL35)-1),'990.99')||'%'end) FGL35,\n" + 
					"ZZZS34,FGXLZS34,XLZS34,(case when XLZS34=0 then '-' else to_char(substr(FGL34,0,length(FGL34)-1),'990.99')||'%'end) FGL34,\n" + 
					"ZZZS33,FGXLZS33,XLZS33,(case when XLZS33=0 then '-' else to_char(substr(FGL33,0,length(FGL33)-1),'990.99')||'%'end) FGL33,\n" + 
					"ZZZS32,FGXLZS32,XLZS32,(case when XLZS32=0 then '-' else to_char(substr(FGL32,0,length(FGL32)-1),'990.99')||'%'end) FGL32,\n" + 
					"ZZZS30,FGXLZS30,XLZS30,(case when XLZS30=0 then '-' else to_char(substr(FGL30,0,length(FGL30)-1),'990.99')||'%'end) FGL30,\n" + 
					"ZZZS25,FGXLZS25,XLZS25,(case when XLZS25=0 then '-' else to_char(substr(FGL25,0,length(FGL25)-1),'990.99')||'%'end) FGL25,\n" + 
					"ZZZS86,FGXLZS86,XLZS86,(case when XLZS86=0 then '-' else to_char(substr(FGL86,0,length(FGL86)-1),'990.99')||'%'end) FGL86,\n" + 
					"ZZZS85,FGXLZS85,XLZS85,(case when XLZS85=0 then '-' else to_char(substr(FGL85,0,length(FGL85)-1),'990.99')||'%'end) FGL85,\n" + 
					"ZZZS84,FGXLZS84,XLZS84,(case when XLZS84=0 then '-' else to_char(substr(FGL84,0,length(FGL84)-1),'990.99')||'%'end) FGL84,\n" + 
					"ZZZS83,FGXLZS83,XLZS83,(case when XLZS83=0 then '-' else to_char(substr(FGL83,0,length(FGL83)-1),'990.99')||'%'end) FGL83,\n" + 
					"ZZZS82,FGXLZS82,XLZS82,(case when XLZS82=0 then '-' else to_char(substr(FGL82,0,length(FGL82)-1),'990.99')||'%'end) FGL82,\n" + 
					"ZZZS81,FGXLZS81,XLZS81,(case when XLZS81=0 then '-' else to_char(substr(FGL81,0,length(FGL81)-1),'990.99')||'%'end) FGL81,\n" + 
					"ZZZS80,FGXLZS80,XLZS80,(case when XLZS80=0 then '-' else to_char(substr(FGL80,0,length(FGL80)-1),'990.99')||'%'end) FGL80 \n," +
					"allzzzs,allfgXLzs,allxlzs,(case when allxlzs=0 then '-' else to_char(substr(allfgl,0,length(allfgl)-1),'990.99')||'%' end)allfgl\n" +
					" ,'1' PX FROM TMP where SSWSMC not like '%国网运行公司%'\n" +
					"union all\n" + 
					"select '国家电网公司' sswsmc,'' linkedprovicedept,'' pms_xh,\n" + 
					"(sum(ZZZS37)) zzzs37,(sum(FGXLZS37)) FGXLZS37,(sum(XLZS37)) XLZS37,(case when sum(XLZS37)=0 then '-' else to_char((sum(fgxlzs37) /sum(xlzs37)*100),'990.99')||'%'end) FGL37,\n" +
					"(sum(ZZZS36)) zzzs36,(sum(FGXLZS36)) FGXLZS36,(sum(XLZS36)) XLZS36,(case when sum(XLZS36)=0 then '-' else to_char((sum(fgxlzs36) /sum(xlzs36)*100),'990.99')||'%'end) FGL36,\n" + 
					"(sum(ZZZS35)) zzzs35,(sum(FGXLZS35)) FGXLZS35,(sum(XLZS35)) XLZS35,(case when sum(XLZS35)=0 then '-' else to_char((sum(fgxlzs35) /sum(xlzs35)*100),'990.99')||'%'end) FGL35,\n" + 
					"(sum(ZZZS34)) zzzs34,(sum(FGXLZS34)) FGXLZS34,(sum(XLZS34)) XLZS34,(case when sum(XLZS34)=0 then '-' else to_char((sum(fgxlzs34) /sum(xlzs34)*100),'990.99')||'%'end) FGL34,\n" + 
					"(sum(ZZZS33)) zzzs33,(sum(FGXLZS33)) FGXLZS33,(sum(XLZS33)) XLZS33,(case when sum(XLZS33)=0 then '-' else to_char((sum(fgxlzs33) /sum(xlzs33)*100),'990.99')||'%'end) FGL33,\n" + 
					"(sum(ZZZS32)) zzzs32,(sum(FGXLZS32)) FGXLZS32,(sum(XLZS32)) XLZS32,(case when sum(XLZS32)=0 then '-' else to_char((sum(fgxlzs32) /sum(xlzs32)*100),'990.99')||'%'end) FGL32,\n" + 
					"(sum(ZZZS30)) zzzs30,(sum(FGXLZS30)) FGXLZS30,(sum(XLZS30)) XLZS30,(case when sum(XLZS30)=0 then '-' else to_char((sum(fgxlzs30) /sum(xlzs30)*100),'990.99')||'%'end) FGL30,\n" + 
					"(sum(ZZZS25)) zzzs25,(sum(FGXLZS25)) FGXLZS25,(sum(XLZS25)) XLZS25,(case when sum(XLZS25)=0 then '-' else to_char((sum(fgxlzs25) /sum(xlzs25)*100),'990.99')||'%'end) FGL25,\n" + 
					"(sum(ZZZS86)) zzzs86,(sum(FGXLZS86)) FGXLZS86,(sum(XLZS86)) XLZS86,(case when sum(XLZS86)=0 then '-' else to_char((sum(fgxlzs86) /sum(xlzs86)*100),'990.99')||'%'end) FGL86,\n" + 
					"(sum(ZZZS85)) zzzs85,(sum(FGXLZS85)) FGXLZS85,(sum(XLZS85)) XLZS85,(case when sum(XLZS85)=0 then '-' else to_char((sum(fgxlzs85) /sum(xlzs85)*100),'990.99')||'%'end) FGL85,\n" + 
					"(sum(ZZZS84)) zzzs84,(sum(FGXLZS84)) FGXLZS84,(sum(XLZS84)) XLZS84,(case when sum(XLZS84)=0 then '-' else to_char((sum(fgxlzs84) /sum(xlzs84)*100),'990.99')||'%'end) FGL84,\n" + 
					"(sum(ZZZS83)) zzzs83,(sum(FGXLZS83)) FGXLZS83,(sum(XLZS83)) XLZS83,(case when sum(XLZS83)=0 then '-' else to_char((sum(fgxlzs83) /sum(xlzs83)*100),'990.99')||'%'end) FGL83,\n" + 
					"(sum(ZZZS82)) zzzs82,(sum(FGXLZS82)) FGXLZS82,(sum(XLZS82)) XLZS82,(case when sum(XLZS82)=0 then '-' else to_char((sum(fgxlzs82) /sum(xlzs82)*100),'990.99')||'%'end) FGL82,\n" + 
					"(sum(ZZZS81)) zzzs30,(sum(FGXLZS81)) FGXLZS30,(sum(XLZS81)) XLZS30,(case when sum(XLZS81)=0 then '-' else to_char((sum(fgxlzs81) /sum(xlzs81)*100),'990.99')||'%'end) FGL81,\n" + 
					"(sum(ZZZS80)) zzzs30,(sum(FGXLZS80)) FGXLZS30,(sum(XLZS80)) XLZS30,(case when sum(XLZS80)=0 then '-' else to_char((sum(fgxlzs80) /sum(xlzs80)*100),'990.99')||'%'end) FGL80,\n" + 
					"nvl(sum(allzzzs),0) allzzzs,nvl(sum(allfgxlzs),0) allfgxlzs,nvl(sum(allxlzs),0) allxlzs,\n" + 
					"(case when sum(allxlzs)=0 then '-' else to_char((sum(allfgxlzs) /sum(allxlzs)*100),'990.99')||'%'end)" +
					" ,'0' PX from tmp "+
					" ) order by PX,pms_xh";
			
			
	
			try {
				cols = "SSWSMC,LINKEDPROVICEDEPT,PMS_XH,ZZZS37,FGXLZS37,XLZS37,FGL37,ZZZS36,FGXLZS36,XLZS36,FGL36,ZZZS35,"
						+ " FGXLZS35,XLZS35,FGL35,ZZZS34,FGXLZS34,XLZS34,FGL34,ZZZS33,FGXLZS33,XLZS33,FGL33,ZZZS32,FGXLZS32,XLZS32,FGL32,ZZZS30,FGXLZS30,XLZS30,FGL30,ZZZS25,FGXLZS25,XLZS25,FGL25,"
						+ " ZZZS86,FGXLZS86,XLZS86,FGL86,ZZZS85,FGXLZS85,XLZS85,FGL85,ZZZS84,FGXLZS84,XLZS84,FGL84,ZZZS83,FGXLZS83,XLZS83,FGL83,ZZZS82,FGXLZS82,XLZS82,FGL82,ZZZS81,FGXLZS81,XLZS81,FGL81,ZZZS80,FGXLZS80,XLZS80,FGL80,ALLZZZS,ALLFGXLZS,ALLXLZS,ALLFGL,PX";

				log.info(sql);
				result = hibernateDao_ztjc.executeSqlQuery(sql);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc
						.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());

			} catch (Exception e) {
				log.info("执行按电压等级统计输电告警信息时出错！", e);
				e.printStackTrace();
			}

			return qro;

		}

		//按监测类型统计
		public QueryResultObject statByJclx(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";

			StringBuffer querySq = new StringBuffer();
			StringBuffer querySql = new StringBuffer();
			StringBuffer jclxFilterSql =new StringBuffer();
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
						querySql.append(" and linkedequipmentdy in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						/*String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
						jclxFilterSql.append( " and monitoringtype in('");
						jclxFilterSql.append(filterValue.replace(",", "','"));
						jclxFilterSql.append("')");
						querySql.append(" and monitoringtypes in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
						}*/
						String[] filterValueArr=filterValue.split(",");
						querySql.append(" and (");
						for(int j=0;j<filterValueArr.length;j++){
							jclxFilterSql.append( " and monitoringtype in('");
							jclxFilterSql.append(filterValue.replace(",", "','"));
							jclxFilterSql.append("')");
							if(j==0){
								querySql.append(" monitoringtypes like '%");
								querySql.append(filterValueArr[j]);
								querySql.append("%'");
							}else{
								querySql.append(" or monitoringtypes like '%");
								querySql.append(filterValueArr[j]);
								querySql.append("%'");
								}
							querySql.append(")");
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
			
			String sql = "with tmp as(select temp.*,(ZS012001+ZS013001+ZS013002+ZS013003+ZS018003+ZS013004+ZS013005+ZS014001+ZS018001+ZS018002+ZS013006) allzzzs " +
					" from (select linkedprovicedept,wsmc SSWSMC," +
					"(select cast(zdypx as varchar2(10)) pms_xh from mw_app.cmst_zb_comm_wspz d where d.wsid = linkedprovicedept) pms_xh," + 
					"sum(gtqx) ZS012001,sum(dxhc) ZS013001,sum(dxwd) ZS013002," + 
					"sum(wfzd) ZS013003,sum(spjc) ZS018003,sum(fpjc) ZS013004,sum(fbjc) ZS013005,sum(jyzwh) ZS014001," +
					"sum(wqxjc) ZS018001,sum(txjc) ZS018002,sum(xlwd) ZS013006" + 
					" from ( "+
					"			   select deps.wsid linkedprovicedept,wsmc,(case monitoringtype when '012001' then count(monitoringtype) else 0 end) gtqx,\n" +
					"              (case monitoringtype when '013001' then count(monitoringtype) else 0 end) dxhc,\n" + 
					"              (case monitoringtype when '013002' then count(monitoringtype) else 0 end) dxwd,\n" + 
					"              (case monitoringtype when '013003' then count(monitoringtype) else 0 end) wfzd,\n" + 
					"              (case monitoringtype when '018003' then count(monitoringtype) else 0 end) spjc,\n" + 
					"              (case monitoringtype when '013004' then count(monitoringtype) else 0 end) fpjc,\n" + 
					"              (case monitoringtype when '013005' then count(monitoringtype) else 0 end) fbjc,\n" + 
					"              (case monitoringtype when '014001' then count(monitoringtype) else 0 end) jyzwh,\n" + 
					"              (case monitoringtype when '018001' then count(monitoringtype) else 0 end) wqxjc,\n" + 
					"              (case monitoringtype when '018002' then count(monitoringtype) else 0 end) txjc,\n" + 
					"              (case monitoringtype when '013006' then count(monitoringtype) else 0 end) xlwd\n" + 
					"              from (select * from mw_app.cmsv_linedevicef where  LINKEDEQUIPMENTDY IS NOT NULL AND linkedprovicedept IS  NOT NULL  and MONITORINGTYPES LIKE '01%' \n" +querySql.toString()+
					"               )dev,(select * from mw_app.cmst_devicemonitype where 1=1 "+jclxFilterSql.toString()+") monitor,MW_APP.cmst_zb_comm_wspz deps\n" + 
					"               where dev.devicecode = monitor.linkeddevice(+) and deps.wsid = dev.linkedprovicedept(+)\n" + 
					querySq.toString()+" group by deps.wsid,deps.wsmc,monitoringtype) "+
					"group by linkedprovicedept,wsmc order by pms_xh) temp)" +
					" select * from ( " +
					"select tmp.*,'1' PX from tmp where SSWSMC not like '%国网运行公司%'\n" +
					" union all\n" + 
					" select '' linkedprovicedept,'国家电网公司' SSWSMC,'' PMS_XH,\n" + 
					" sum(ZS012001) ZS012001, sum(ZS013001) ZS013001, sum(ZS013002) ZS013002,sum(ZS013003) ZS013003,sum(ZS018003) ZS018003,\n" + 
					" sum(ZS013004) ZS013004,sum(ZS013005) ZS013005,sum(ZS014001) ZS014001,sum(ZS018001) ZS018001,sum(ZS018002) ZS018002,\n" + 
					" sum(ZS013006) ZS013006,sum(allzzzs) allzzzs ,'0' PX  from tmp "+
					" ) order by PX,PMS_XH ";
			
			/*String sql = "with tmp as(select temp.*,(ZS012001+ZS013001+ZS013002+ZS013003+ZS018003+ZS013004+ZS013005+ZS014001+ZS018001+ZS018002+ZS013006) allzzzs   \n"
					+ "from (select linkedprovicedept,province_name SSWSMC,(select pms_xh from mw_app.cmst_provice_deps d where d.province_id = linkedprovicedept) pms_xh, \n"
					+ "sum(gtqx) ZS012001,sum(dxhc) ZS013001,sum(dxwd) ZS013002,sum(wfzd) ZS013003,sum(spjc) ZS018003,sum(fpjc) ZS013004,sum(fbjc) ZS013005,sum(jyzwh) ZS014001,sum(wqxjc) ZS018001,sum(txjc) ZS018002,sum(xlwd) ZS013006 \n"
					+ "from (select deps.province_id linkedprovicedept,province_name, \n"
					+ "(case monitoringtype when '012001' then count(monitoringtype) else 0 end) gtqx, \n"
					+ "(case monitoringtype when '013001' then count(monitoringtype) else 0 end) dxhc, \n"
					+ "(case monitoringtype when '013002' then count(monitoringtype) else 0 end) dxwd, \n"
					+ "(case monitoringtype when '013003' then count(monitoringtype) else 0 end) wfzd, \n"
					+ "(case monitoringtype when '018003' then count(monitoringtype) else 0 end) spjc, \n"
					+ "(case monitoringtype when '013004' then count(monitoringtype) else 0 end) fpjc, \n"
					+ "(case monitoringtype when '013005' then count(monitoringtype) else 0 end) fbjc, \n"
					+ "(case monitoringtype when '014001' then count(monitoringtype) else 0 end) jyzwh, \n"
					+ "(case monitoringtype when '018001' then count(monitoringtype) else 0 end) wqxjc, \n"
					+ "(case monitoringtype when '018002' then count(monitoringtype) else 0 end) txjc, \n"
					+ "(case monitoringtype when '013006' then count(monitoringtype) else 0 end) xlwd  \n"
					+ "from (select * from mw_app.CMSV_LINEDEVICE_LOGIC_XT where 1=1 "+querySql.toString()+")dev,MW_APP.CMST_PROVICE_DEPS deps \n"
					+ "where deps.province_id = dev.linkedprovicedept(+) "+querySq.toString()+" group by deps.province_id,province_name,monitoringtype) \n"
					+ "group by linkedprovicedept,province_name order by pms_xh) temp) \n"
					+ "select * from ( select tmp.*,'1' PX from tmp where SSWSMC not like '%国网运行公司%' \n"
					+ "union all \n"
					+ "select '' linkedprovicedept,'国家电网公司' SSWSMC,'' PMS_XH, \n"
					+ "sum(ZS012001) ZS012001, sum(ZS013001) ZS013001, sum(ZS013002) ZS013002,sum(ZS013003) ZS013003,sum(ZS018003) ZS018003, \n"
					+ "sum(ZS013004) ZS013004,sum(ZS013005) ZS013005,sum(ZS014001) ZS014001,sum(ZS018001) ZS018001,sum(ZS018002) ZS018002, \n"
					+ "sum(ZS013006) ZS013006,sum(allzzzs) allzzzs ,'0' PX  from tmp  ) order by PX,PMS_XH ";*/
			log.info(sql);
			try {
				cols = "LINKEDPROVICEDEPT,SSWSMC,PMS_XH,ZS012001,ZS013001,ZS013002,ZS013003,ZS018003,ZS013004,ZS013005,ZS014001,ZS018001,ZS018002,ZS013006,ALLZZZS,PX";

				
				
				result = hibernateDao_ztjc.executeSqlQuery(sql);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());

			} catch (Exception e) {
				log.info("执行按监测类型统计输电检测装置时出错！", e);
				e.printStackTrace();
			}

			return qro;

		}


		//按生产厂家统计
		@SuppressWarnings("unchecked")
		public QueryResultObject statBySccj(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();

			String cols = "";
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
						querySq.append(" and linkedprovicedept in ('");
						querySq.append(filterValue.replace(",", "','"));
						querySq.append("')");
						querySql.append(" and linkeddepws in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					} else if (Constants.DYDJ.equals(filterKey)) {
						querySql.append(" and linkedequipmentdy in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
						jclxFilterSql.append( " and monitoringtype in('");
						jclxFilterSql.append(filterValue.replace(",", "','"));
						jclxFilterSql.append("')");
						querySql.append(" and monitoringtype in ('");
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
			
			String sql = "with tmp as(select temp.*,(ZS012001+ZS013001+ZS013002+ZS013003+ZS018003+ZS013004+ZS013005+ZS014001+ZS018001+ZS018002+ZS013006) allzzzs " +
					" from (select manufacturer SSWSMC,NVL(sum(gtqx),0) ZS012001,NVL(sum(dxhc),0) ZS013001,NVL(sum(dxwd),0) ZS013002," + 
					"NVL(sum(wfzd),0) ZS013003,NVL(sum(spjc),0) ZS018003,NVL(sum(fpjc),0) ZS013004,NVL(sum(fbjc),0) ZS013005,NVL(sum(jyzwh),0) ZS014001," +
					"NVL(sum(wqxjc),0) ZS018001,NVL(sum(txjc),0) ZS018002,NVL(sum(xlwd),0) ZS013006" + 
					" from (select decode(dev.manufacturer,null,' 其他',dev.manufacturer) manufacturer," +
					"(case monitoringtype when '012001' then count(monitoringtype) else 0 end) gtqx," + 
					"(case monitoringtype when '013001' then count(monitoringtype) else 0 end) dxhc," + 
					"(case monitoringtype when '013002' then count(monitoringtype) else 0 end) dxwd," + 
					"(case monitoringtype when '013003' then count(monitoringtype) else 0 end) wfzd," +
					"(case monitoringtype when '018003' then count(monitoringtype) else 0 end) spjc," +
					"(case monitoringtype when '013004' then count(monitoringtype) else 0 end) fpjc," +
					"(case monitoringtype when '013005' then count(monitoringtype) else 0 end) fbjc," +
					"(case monitoringtype when '014001' then count(monitoringtype) else 0 end) jyzwh," +
					"(case monitoringtype when '018001' then count(monitoringtype) else 0 end) wqxjc," +
					"(case monitoringtype when '018002' then count(monitoringtype) else 0 end) txjc," +
					"(case monitoringtype when '013006' then count(monitoringtype) else 0 end) xlwd" +
					" from (select * from mw_app.cmsv_linedevicef where  LINKEDEQUIPMENTDY IS NOT NULL  and   linkedprovicedept IS NOT NULL and MONITORINGTYPES LIKE '01%'  " +querySql.toString()+
					"  )dev,(select * from mw_app.cmst_devicemonitype where 1=1 "+jclxFilterSql.toString()+" )monitor" +
					" where dev.devicecode = monitor.linkeddevice " +
					querySq.toString() + " group by dev.manufacturer,monitor.monitoringtype) group by manufacturer order by manufacturer) temp)" +
					" select * from ( " +
					"select tmp.*,'1' PX from tmp\n" +
					" union all\n" + 
					" select '厂家合计' SSWSMC,\n" + 
					" sum(ZS012001) ZS012001, sum(ZS013001) ZS013001, sum(ZS013002) ZS013002,sum(ZS013003) ZS013003,sum(ZS018003) ZS018003,\n" + 
					" sum(ZS013004) ZS013004,sum(ZS013005) ZS013005,sum(ZS014001) ZS014001,sum(ZS018001) ZS018001,sum(ZS018002) ZS018002,\n" + 
					" sum(ZS013006) ZS013006,sum(allzzzs) allzzzs ,'0' PX from tmp "+
					" ) order by PX";
			
/*			String sql = " SELECT t0.*,ROWNUM AS count_id FROM (with tmp as(select temp.*, \n" +
					" (ZS012001+ZS013001+ZS013002+ZS013003+ZS018003+ZS013004+ZS013005+ZS014001+ZS018001+ZS018002+ZS013006) allzzzs  \n" +
					" from (select manufacturer SSWSMC,NVL(sum(gtqx),0) ZS012001,NVL(sum(dxhc),0) ZS013001,NVL(sum(dxwd),0) ZS013002, \n" +
					" NVL(sum(wfzd),0) ZS013003,NVL(sum(spjc),0) ZS018003,NVL(sum(fpjc),0) ZS013004,NVL(sum(fbjc),0) ZS013005, \n" +
					" NVL(sum(jyzwh),0) ZS014001,NVL(sum(wqxjc),0) ZS018001,NVL(sum(txjc),0) ZS018002,NVL(sum(xlwd),0) ZS013006  \n" +
					" from (select decode(dev.manufacturer,null,'(空)',dev.manufacturer) manufacturer,(case monitoringtype when '012001' then count(monitoringtype) else 0 end) gtqx, \n" +
					" (case monitoringtype when '013001' then count(monitoringtype) else 0 end) dxhc,(case monitoringtype when '013002' then count(monitoringtype) else 0 end) dxwd, \n" +
					" (case monitoringtype when '013003' then count(monitoringtype) else 0 end) wfzd,(case monitoringtype when '018003' then count(monitoringtype) else 0 end) spjc, \n" +
					" (case monitoringtype when '013004' then count(monitoringtype) else 0 end) fpjc,(case monitoringtype when '013005' then count(monitoringtype) else 0 end) fbjc, \n" +
					" (case monitoringtype when '014001' then count(monitoringtype) else 0 end) jyzwh,(case monitoringtype when '018001' then count(monitoringtype) else 0 end) wqxjc, \n" +
					" (case monitoringtype when '018002' then count(monitoringtype) else 0 end) txjc,(case monitoringtype when '013006' then count(monitoringtype) else 0 end) xlwd  \n" +
					" from (select * from mw_app.cmsv_linedevice_logic_xt where  1=1 "+querySql.toString()+" )dev where 1=1 "+querySq.toString()+" group by dev.manufacturer,monitoringtype) group by manufacturer order by manufacturer) temp)  \n" +
					" select * from ( select tmp.*,'1' PX from tmp  \n" +
					" union all  \n" +
					" select '厂家合计' SSWSMC,  \n" +
					" sum(ZS012001) ZS012001, sum(ZS013001) ZS013001, sum(ZS013002) ZS013002,sum(ZS013003) ZS013003,sum(ZS018003) ZS018003, \n" +
					" sum(ZS013004) ZS013004,sum(ZS013005) ZS013005,sum(ZS014001) ZS014001,sum(ZS018001) ZS018001,sum(ZS018002) ZS018002,  \n" +
					" sum(ZS013006) ZS013006,sum(allzzzs) allzzzs ,'0' PX from tmp  ) order by PX) t0 ";
*/
			try {
				cols = "SSWSMC,ZS012001,ZS013001,ZS013002,ZS013003,ZS018003,ZS013004,ZS013005,ZS014001,ZS018001,ZS018002,ZS013006,ALLZZZS,PX,COUNT_ID";

				result = hibernateDao_ztjc.executeSqlQuery(sql);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc
						.executeSqlQuery("SELECT COUNT(*) FROM ("
								+ sql+ ")"));

				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());

			} catch (Exception e) {
				e.printStackTrace();
				log.info("执行按生产厂家统计输电检测装置时出错", e);
			}

			return qro;
		}
		 /**
			 * 处理结果，将List中的index 转换成对应的列名
			 * 
			 * @param list
			 * @param columns
			 * @return
			 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private List<Map> transToColumns(List<Object[]> list, String columns) {
			List<Map> items = new ArrayList();
			String[] cols = columns.split("\\,");
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				for (int m = 0; m < cols.length; m++) {
					map.put(cols[m].trim(), list.get(i)[m]);
				}
				items.add(map);
			}
			return items;
		}

		@SuppressWarnings("unchecked")
		@Override
		public QueryResultObject getDetailList(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
			int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
			StringBuffer querySql = new StringBuffer();
			
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
						querySql.append(" and LinkedEquipmentDY in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						querySql.append(" and (");
						for(int j=0;j<filterValueArr.length;j++){
							if(j==0){
								querySql.append(" monitoringtypes like '%");
								querySql.append(filterValueArr[j]);
								querySql.append("%'");
							}else{
								querySql.append(" or monitoringtypes like '%");
								querySql.append(filterValueArr[j]);
								querySql.append("%'");
								}
							querySql.append(")");
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
					}else if (Constants.YXZT.equals(filterKey)){
						querySql.append(" and status in ('");
						querySql.append(filterValue);
						querySql.append("') ");
					}else if (Constants.SFSS.equals(filterKey)){
						if("all".equals(filterValue.toString())){
						querySql.append(" ");
						}else if("F".equals(filterValue)){
							querySql.append(" and (u.sfss is null or u.sfss ='");
							querySql.append(filterValue);
							querySql.append("')");
						}else{
						querySql.append(" and t.SFSS = '");
						querySql.append(filterValue);
						querySql.append("'");
						}
					}
				}
			}
			
			String sql = "SELECT T.*, '查看' LOOKUP,  nvl(DECODE(SFSS, 'T', '是', 'F', '否'), '否') ISRT  \n " +
					" FROM MW_APP.CMSV_LINEDEVICEf T where  T.WSDEPMC IS NOT NULL \n"+
					" AND T.LINKEDEQUIPMENTDY IS NOT NULL \n"+
					" AND T.MONITORINGTYPES LIKE '01%' "+querySql.toString();
			
		
		       
/*			String sql = "SELECT t.LINKEDDEPWS,t.LINKEDLINENAME,t.DEVICENAME,(select typename from mw_app.cmst_monitoringtype where typecode = d.MONITORINGTYPE) DEVICECATEGORY_DISPLAY,'查看' LOOKUP,nvl(DECODE(u.SFSS,'T','是','F','否'),'否')ISRT,\n" +
						" t.LINKEDPOLENAME,t.DEVICEVOLTAGE,t.DEVICECATEGORY,t.DEVICEMODEL,t.MANUFACTURER,t.RUNDATE, \n" + 
						" (select xtmc from mw_app.cmsmv_pmsapp_ud_kq_xt where obj_id = t.xtid) XTMC \n"+
						"  FROM mw_app.cmsv_linedevice T,mw_app.cmst_devicemonitype d,mw_app.cmsv_deviceused_info u \n"+
						" WHERE t.devicecode(+) = linkeddevice and LINKEDEQUIPMENTDY IS NOT NULL and T.MONITORINGTYPES LIKE '01%'  \n"+ 
						"  AND u.ZZBM(+) = linkeddevice and d.monitoringtype = u.JCLX(+)  and T.WSDEPMC IS NOT NULL"+querySql.toString();*/
						
			try {
				cols = "LINKEDDEPWS,LINKEDLINENAME,DEVICENAME,DEVICECATEGORY_DISPLAY,LOOKUP,ISRT,LINKEDPOLENAME,,XTMC,DEVICEVOLTAGE,DEVICECATEGORY,DEVICEMODEL,MANUFACTURER,RUNDATE";
				cols = "OBJ_ID,XTJC,XTMC,XTID,LINKEDDEP,DEPMC,XH,LINKEDDEPWS,WSDEPMC,WSDEPJC,LINKEDEQUIPMENTDY,DEVICEVOLTAGE,DYDJGDJB,BZDM,LINKEDPOLE,LINKEDPOLEOBJ,LINKEDLINE,LINKEDLINEZB,LINKEDPOLEZB,LINKEDLINENAME,LINKEDPOLENAME,LINKEDCMA,LINKEDCMA_DISPLAY,DEVICECATEGORY,DEVICECATEGORYDM,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER,REMARKS,JCXX,ISACTIVATED,LINKEDPROVICEDEPT,SFSS,LOOKUP,ISRT";
				result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM ("+ sql+ ")"));
				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
			} catch (Exception e) {
				e.printStackTrace();
				log.info("执行输电监测装置详细信息出错", e);
			}
			return qro;
		}

		@Override
		public QueryResultObject getFGXLSList(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
			int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
			StringBuffer queryS = new StringBuffer();
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
						querySql.append(" and linkedprovicedept in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					} else if (Constants.DYDJ.equals(filterKey)) {
						querySq.append(" and T.TDYDJ in('");
						querySq.append(filterValue);
						querySq.append("')");
						queryS.append(" and LINKEDEQUIPMENTDY in ('");
						queryS.append(filterValue);
						queryS.append("')");
						querySql.append(" and LINKEDEQUIPMENTDY in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						querySql.append(" and (");
						for(int j=0;j<filterValueArr.length;j++){
							if(j==0){
								querySql.append(" monitoringtypes like '%");
								querySql.append(filterValueArr[j]);
								querySql.append("%'");
							}else{
								querySql.append(" or monitoringtypes like '%");
								querySql.append(filterValueArr[j]);
								querySql.append("%'");
								}
							querySql.append(")");
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
						querySccj.append(" and cjname  LIKE '%");
						querySccj.append(filterValue);
						querySccj.append("%' ");
					}else if (Constants.XLMC.equals(filterKey)){
						querySql.append(" and t.LINKEDLINENAME LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}
				}
			}
			

//			String sql = " select XTMC,ZCXZ,DYDJ,SSXT,TDYDJ,XLCD,GLOBAL_ID,XLMC,SSSJ,JSFS,DLXZ,OBJ_ID,YXDW,TYRQ,SSDW from (SELECT \n" +
//					" (SELECT XT.XTMC FROM mw_app.cmsmv_pmsapp_ud_kq_xt XT  WHERE T.SSXT = XT.OBJ_ID) XTMC, \n" +
//	               " (SELECT A.DMMC FROM mw_app.CMSMV_PMSAPP_UD_GG_DM A WHERE DMLB = '004' AND A.DM = T.ZCXZ) ZCXZ, \n" +
//	               " (SELECT A.MC FROM mw_app.CMSMV_PMSAPP_GY_DYDJ A WHERE A.DM = T.DYDJ) DYDJ, \n" +
//	                " SSXT,DYDJ AS TDYDJ,XLCD,GLOBAL_ID,XLMC,SSSJ,JSFS,DLXZ,OBJ_ID, \n" +
//	               " (SELECT A.BMQC FROM mw_app.cmsmv_pmssys_pd_deps A  WHERE A.OBJ_ID = T.SSSJ) YXDW,TYRQ, \n" +
//	               " (SELECT A.mc  FROM mw_app.cmsmv_pmssys_pd_deps_city2ws A WHERE T.sssj = A.city_obj_id and rownum = 1) SSDW \n" +
//	                	" FROM mw_app.cmsmv_pmsapp_ud_sbd_xlxx T \n" +
//	                	" WHERE T.GLOBAL_ID IS NOT NULL) T \n" +
//	                	" WHERE 1 = 1 and T.TDYDJ in('"+querySq.toString()+"')  AND T.GLOBAL_ID IN(  \n" +
//	                			" select linkedline from (select * from mw_app.cmsv_linedevice  \n" +
//	                			"	where linkedprovicedept IS NOT NULL  and LINKEDEQUIPMENTDY IS NOT NULL and LINKEDEQUIPMENTDY in ('"+querySq.toString()+"')  \n" +
//	                				" ) line,MW_APP.CMST_PROVICE_DEPS deps \n" +
//	                				" where deps.province_id = line.linkedprovicedept(+)" +querySql.toString()+"\n" +
//	                				" )  AND T.SSDW is not null  \n" +
//	                				" ORDER BY TYRQ DESC";
			String sql = "select XTMC,ZCXZ,DYDJ,SSXT,TDYDJ,XLCD,SBBM,XLMC,SSSJ,JSFS,OBJ_ID,YXDW,TYRQ,SSDW from (SELECT\n" +
				       " (SELECT XT.XTMC FROM mw_app.cmst_kq_xt XT  WHERE T.SSKQXT = XT.OBJ_ID) XTMC, \n" +
				       " (SELECT A.DMMC FROM mw_app.cmst_dw_bzzx_ggdmb  A  WHERE BZFLBM = '004'  AND A.DM = T.ZCBH) ZCXZ, \n" +
				       " (SELECT A.DYDJMC FROM mw_app.CMST_SB_PZ_SBDYDJ A WHERE A.DYDJBM = T.DYDJ) DYDJ, \n" +
				       " T.SSKQXT AS SSXT,DYDJ AS TDYDJ,XLZCD AS XLCD,SBBM,XLMC,T.SSWS AS SSSJ,JSFS,OBJ_ID, \n" +
				       " (SELECT A.SSYWDWMC FROM mw_app.CMST_SPECIALORG_UNIT_LOCEXT A  WHERE A.isc_id = T.ssws) YXDW,TYRQ, \n" +
				       " (SELECT A.wsmc  FROM mw_app.cmst_zb_comm_wspz A WHERE T.ssws = A.wsid and rownum = 1) SSDW \n" +
				       "  FROM mw_app.cmst_sb_zwyc_xl T WHERE T.SBBM IS NOT NULL) T WHERE 1 = 1  "+querySq.toString()+"  AND T.SBBM IN(  \n" +
				       "  select linkedline from (select * from mw_app.cmsv_linedevicef  \n" +
				       "  where linkedprovicedept IS NOT NULL  and LINKEDEQUIPMENTDY IS NOT NULL "+querySql.toString()+" ) line,MW_APP.cmst_zb_comm_wspz deps  \n" +
				       "  where deps.wsid = line.linkedprovicedept(+) " +querySql.toString()+" )  AND T.SSDW is not null  ORDER BY TYRQ DESC";

			
			
			try{
			 cols = "XTMC,ZCXZ,DYDJ,SSXT,TDYDJ,XLCD,SBBM,XLMC,SSSJ,JSFS,OBJ_ID,YXDW,TYRQ,SSDW";
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());

		} catch (Exception e) {
			log.info("执行覆盖线路统计输电装置时出错！", e);
			e.printStackTrace();
		}

		return qro;
		}
 
		
		@SuppressWarnings("unchecked")
		@Override
		public QueryResultObject getJclxDetailList(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
			int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
			StringBuffer querySql = new StringBuffer();
			
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
						querySql.append(" and LinkedEquipmentDY in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
						querySql.append(" and MONITORINGTYPE in ('");
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
					}else if (Constants.YXZT.equals(filterKey)){
						querySql.append(" and status in ('");
						querySql.append(filterValue);
						querySql.append("') ");
					}else if (Constants.SFSS.equals(filterKey)){
						if("all".equals(filterValue.toString())){
						querySql.append(" ");
						}else if("F".equals(filterValue)){
							querySql.append(" and (u.sfss is null or u.sfss ='");
							querySql.append(filterValue);
							querySql.append("')");
						}else{
						querySql.append(" and t.SFSS = '");
						querySql.append(filterValue);
						querySql.append("'");
						}
					}
				}
			}
			
		/*	String sql = "SELECT T.*, '查看' LOOKUP,  nvl(DECODE(SFSS, 'T', '是', 'F', '否'), '否') ISRT  \n " +
					" FROM MW_APP.CMSV_LINEDEVICE T where  T.WSDEPMC IS NOT NULL \n"+
					" AND T.LINKEDEQUIPMENTDY IS NOT NULL \n"+
					" AND T.MONITORINGTYPES LIKE '01%' "+querySql.toString();*/
		       
			String sql = "SELECT  t.LINKEDDEPWS, t.LINKEDLINENAME, t.DEVICENAME,   (select typename from mw_app.cmst_monitoringtype where typecode = d.MONITORINGTYPE) DEVICECATEGORY_DISPLAY,cast('查看' as varchar(42)) LOOKUP, \n"+
					"   nvl(DECODE(U.SFSS, 'T', '是', 'F', '否'), '否') ISRT,  t.WSDEPMC, (select xtmc  from mw_app.cmst_kq_xt  where obj_id = t.xtid) XTMC , t.LINKEDPOLENAME,  t.DEVICEVOLTAGE, t.DEVICEMODEL,t.MANUFACTURER,  t.RUNDATE, \n"+
					" t.linkedprovicedept,  t.LINKEDDEP,   \n"+
				      " T.DEVICECODE,  d.monitoringtype MONITORINGTYPES, \n"+
				      " t.OBJ_ID,  t.LINKEDPOLE \n"+
				      " 	FROM mw_app.cmsv_linedevicef T,mw_app.cmst_devicemonitype d,mw_app.cmsv_deviceused_info u \n"+
				      " 	WHERE t.devicecode(+) = linkeddevice and LINKEDEQUIPMENTDY IS NOT NULL and T.MONITORINGTYPES LIKE '01%'   \n"+
					" AND u.ZZBM(+) = linkeddevice and d.monitoringtype = u.JCLX(+)  and T.WSDEPMC IS NOT NULL "+querySql.toString(); 
			
			try {
				cols = "LINKEDDEPWS,LINKEDLINENAME,DEVICENAME,DEVICECATEGORY_DISPLAY,LOOKUP,ISRT,WSDEPMC,XTMC,LINKEDPOLENAME,DEVICEVOLTAGE,DEVICEMODEL,MANUFACTURER,RUNDATE,LINKEDPROVICEDEPT,LINKEDDEP,DEVICECODE,MONITORINGTYPES,OBJ_ID,LINKEDPOLE";

				result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM ("+ sql+ ")"));
				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
			} catch (Exception e) {
				e.printStackTrace();
				log.info("执行输电监测装置详细信息出错", e);
			}
			return qro;
		}
		
		
		
}

