package com.sgcc.pms.zbztjc.jczhgl.sdzzyxzk.bizc;

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

public class SdzzyxzkBizc implements ISdzzyxzkBizc{
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(SdzzyxzkBizc.class);
	@Resource
	private ILoggerSaveBizc loggerSaveBizc ;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryResultObject query(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		String cols ="";
		StringBuffer querySql = new StringBuffer();
		StringBuffer querySq = new StringBuffer();
		StringBuffer queryS = new StringBuffer();

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
					querySql.append(" and dydj in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					querySql.append(" and l.MONITORINGTYPE in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
					}
				} else if (Constants.TYRQ.equals(filterKey)) {
					String startTime = filterValue.split(",")[0];
					querySq.append(" and to_char(a.alarmtime,'yyyy-mm') = '");
					querySq.append(startTime);
					querySq.append("'");
					queryS.append(" and TJMONTH ='");
					queryS.append(startTime);
					queryS.append("'");
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
		String sql = "select l.obj_id,l.WSDEPMC,l.devicecode,\n" +
				"       l.linkedprovicedept,\n" + 
				"       l.LINKEDLINENAME,\n" + 
				"       l.LINKEDPOLENAME,l.LINKEDPOLE,\n" + 
				"       l.DEVICEVOLTAGE,\n" + 
				"       l.typecode MONITORINGTYPES,\n" + 
				"       l.typename DEVICECATEGORY_DISPLAY,\n" + 
				"       CAST('查看' AS VARCHAR(42)) LOOKUP,\n" + 
				"       l.MANUFACTURER,\n" + 
				"       l.RUNDATE,\n" + 
				"     case when DATAACCESSRATE = 0 then '0'||'%'\n" + 
				"    else DATAACCESSRATE*100||'%'\n" + 
				"      end DATAACCESSRATE,\n"+
				" case when DATAARACCURACYRATE = 0 then '0'||'%'\n" + 
				"    else DATAARACCURACYRATE*100||'%'\n" + 
				"    end DATAARACCURACYRATE,\n" + 
				"       d.breakdownstime,\n" + 
				"(select count(*)\n" +
				"          from mw_app.cmst_alarmlog a\n" + 
				"         where a.devicecode = l.devicecode\n" + 
				"           and a.monitoringtype = l.MONITORINGTYPE "+querySq+" ) num,\n"+
				"       d.allscored\n" + 
				"  from (select * from mw_app.cmsv_linedevice_logic_xtf where dydj is not null and monitoringtype != '018003' and ischeck != '0') l,\n" + 
				"    (select * from  mw_app.CMSV_index_scored  where 1=1 "+queryS+") d\n" + 
				" where l.DEVICECODE(+) = d.devicecode\n" + 
				"   and l.typecode(+) = d.monitoringtype and dydj is not null "+querySql.toString();
				
	
		try{
			cols = "OBJ_ID,WSDEPMC,DEVICECODE,LINKEDPROVICEDEPT,LINKEDLINENAME,LINKEDPOLENAME,LINKEDPOLE,DEVICEVOLTAGE,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,LOOKUP,MANUFACTURER,RUNDATE,DATAACCESSRATE,DATAARACCURACYRATE,BREAKDOWNSTIME,NUMA,ALLSCORED";
			
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			loggerSaveBizc.updataUserlog("查询", "状态监测-报表查询-输电装置运行状况查询", "操作成功");
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行输电监测装置查询统计的初始化DataGrid的查询SQL中发生异常",e);
			loggerSaveBizc.updataUserlog("查询", "状态监测-报表查询-输电装置运行状况查询", "操作失败");
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
		String ssjrzsColumnSql = "SSJRZS37+SSJRZS36+SSJRZS35+ SSJRZS34+SSJRZS33+SSJRZS86+SSJRZS85+SSJRZS84+SSJRZS83+SSJRZS82";
		String zzzsColumnSql = "KHzzs37+KHzzs36+KHzzs35+khzzs34+KHzzs33+KHzzs86+KHzzs85+KHzzs84+KHzzs83+KHzzs82";
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
					ssjrzsColumnSql+="SSJRZS"+filterValueArr[j]+"+";
					zzzsColumnSql +="KHzzs"+filterValueArr[j]+"+";
					}
					ssjrzsColumnSql = ssjrzsColumnSql.substring(0,ssjrzsColumnSql.length()-1);
					zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
					querySql.append(" and DYDJ in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					querySql.append(" and l.MONITORINGTYPE in ('");
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

		
		String sql = "with tmp as (select temp.*,\n"
				+ "     (" + zzzsColumnSql + ") allKHZZS,\n"
				+ "    (" + ssjrzsColumnSql + ") allSSJRZS,\n"
				+ "    nvl(round(((" + ssjrzsColumnSql + ")/decode(" + zzzsColumnSql + ",0,null," + zzzsColumnSql + "))*100,2),0)||'%' allSSJRL\n"
				
				+ "from (select a.linkedprovicedept,a.wsmc SSWSMC,NVl(sum(KHzzs37),0) KHzzs37,NVl(sum(KHzzs36),0) KHzzs36,\n"
				+ "(select cast(zdypx as varchar2(10)) pms_xh from mw_app.cmst_zb_comm_wspz where wsid = a.linkedprovicedept) pms_xh,\n"
				+ "NVl(sum(KHzzs35),0) khzzs35,NVl(sum(KHzzs34),0) khzzs34,NVl(sum(khzzs33),0) khzzs33,\n"
				+ "NVl(sum(KHzzs32),0) khzzs32,NVl(sum(KHzzs30),0) khzzs30,NVl(sum(khzzs25),0) khzzs25,\n"
				+ "NVl(sum(KHzzs86),0) khzzs86,NVl(sum(KHzzs85),0) khzzs85,\n"
				+ "NVl(sum(KHzzs84),0) khzzs84,NVl(sum(KHzzs83),0) khzzs83,NVl(sum(KHzzs82),0) khzzs82,NVl(sum(KHzzs81),0) khzzs81,NVl(sum(KHzzs80),0) khzzs80,\n"
				+ "NVl(sum(ssjrzs37),0) ssjrzs37,NVl(sum(ssjrzs36),0) ssjrzs36,NVl(sum(ssjrzs35),0) ssjrzs35,\n"
				+ "NVl(sum(ssjrzs34),0) ssjrzs34,NVl(sum(ssjrzs33),0) ssjrzs33,NVl(sum(ssjrzs32),0) ssjrzs32,\n"
				+ "NVl(sum(ssjrzs30),0) ssjrzs30,NVl(sum(ssjrzs25),0) ssjrzs25,NVl(sum(ssjrzs86),0) ssjrzs86,NVl(sum(ssjrzs85),0) ssjrzs85,\n"
				+ "NVl(sum(ssjrzs84),0) ssjrzs84,NVl(sum(ssjrzs83),0) ssjrzs83,NVl(sum(ssjrzs82),0) ssjrzs82,\n"
				+ "NVl(sum(ssjrzs81),0) ssjrzs81,NVl(sum(ssjrzs80),0) ssjrzs80,\n"+

				"NVl(sum(df37),0) dfzs37,NVl(sum(df36),0) dfzs36,NVl(sum(df35),0) dfzs35, \n" +
				"NVl(sum(df34),0) dfzs34,NVl(sum(df33),0) dfzs33,NVl(sum(df32),0) dfzs32, \n" + 
				"NVl(sum(df30),0) dfzs30,NVl(sum(df25),0) dfzs25,NVl(sum(df86),0) dfzs86,NVl(sum(df85),0) dfzs85, \n" + 
				"NVl(sum(df84),0) dfzs84,NVl(sum(df83),0) dfzs83,NVl(sum(df82),0) dfzs82, \n" + 
				"NVl(sum(df81),0) dfzs81,NVl(sum(df80),0) dfzs80,\n"+
	
				"NVl(sum(jrl37),0) jrls37,NVl(sum(jrl36),0) jrls36,NVl(sum(jrl35),0) jrls35, \n" +
				"NVl(sum(jrl34),0) jrls34,NVl(sum(jrl33),0) jrls33,NVl(sum(jrl32),0) jrls32, \n" + 
				"NVl(sum(jrl30),0) jrls30,NVl(sum(jrl25),0) jrls25,NVl(sum(jrl86),0) jrls86,NVl(sum(jrl85),0) jrls85, \n" + 
				"NVl(sum(jrl84),0) jrls84,NVl(sum(jrl83),0) jrls83,NVl(sum(jrl82),0) jrls82, \n" + 
				"NVl(sum(jrl81),0) jrls81,NVl(sum(jrl80),0) jrls80, \n" + 
				
				"NVl(sum(zql37),0) zqls37,NVl(sum(zql36),0) zqls36,NVl(sum(zql35),0) zqls35, \n" + 
				"NVl(sum(zql34),0) zqls34,NVl(sum(zql33),0) zqls33,NVl(sum(zql32),0) zqls32, \n" + 
				"NVl(sum(zql30),0) zqls30,NVl(sum(zql25),0) zqls25,NVl(sum(zql86),0) zqls86,NVl(sum(zql85),0) zqls85, \n" + 
				"NVl(sum(zql84),0) zqls84,NVl(sum(zql83),0) zqls83,NVl(sum(zql82),0) zqls82, \n" + 
				"NVl(sum(zql81),0) zqls81,NVl(sum(zql80),0) zqls80, \n" + 
				

				"NVl(round(sum(df37)/decode(sum(khzzs37),0,null,sum(khzzs37)),2),0) df37,NVl(round(sum(df36)/decode(sum(khzzs36),0,null,sum(khzzs36)),2),0) df36,NVl(round(sum(df35)/decode(sum(khzzs35),0,null,sum(khzzs35)),2),0) df35, \n" +
				"NVl(round(sum(df34)/decode(sum(khzzs34),0,null,sum(khzzs34)),2),0) df34,NVl(round(sum(df33)/decode(sum(khzzs33),0,null,sum(khzzs33)),2),0) df33,NVl(round(sum(df32)/decode(sum(khzzs32),0,null,sum(khzzs32)),2),0) df32, \n" + 
				"NVl(round(sum(df30)/decode(sum(khzzs30),0,null,sum(khzzs30)),2),0) df30,NVl(round(sum(df25)/decode(sum(khzzs25),0,null,sum(khzzs25)),2),0) df25,NVl(round(sum(df86)/decode(sum(khzzs86),0,null,sum(khzzs86)),2),0) df86,NVl(round(sum(df85)/decode(sum(khzzs85),0,null,sum(khzzs85)),2),0) df85, \n" + 
				"NVl(round(sum(df84)/decode(sum(khzzs84),0,null,sum(khzzs84)),2),0) df84,NVl(round(sum(df83)/decode(sum(khzzs83),0,null,sum(khzzs83)),2),0) df83,NVl(round(sum(df82)/decode(sum(khzzs82),0,null,sum(khzzs82)),2),0) df82, \n" + 
				"NVl(round(sum(df81)/decode(sum(khzzs81),0,null,sum(khzzs81)),2),0) df81,NVl(round(sum(df80)/decode(sum(khzzs80),0,null,sum(khzzs80)),2),0) df80,\n" 


				+ "NVl(round((sum(ssjrzs37)/decode(sum(khzzs37),0,null,sum(khzzs37))*100),2),0)||'%' ssjrl37,\n"
				+ "NVl(round((sum(ssjrzs36)/decode(sum(khzzs36),0,null,sum(khzzs36))*100),2),0)||'%' ssjrl36,\n"
				+ "NVl(round((sum(ssjrzs35)/decode(sum(khzzs35),0,null,sum(khzzs35))*100),2),0)||'%' ssjrl35,\n"
				+ "NVl(round((sum(ssjrzs34)/decode(sum(khzzs34),0,null,sum(khzzs34))*100),2),0)||'%' ssjrl34,\n"
				+ "NVl(round((sum(ssjrzs33)/decode(sum(khzzs33),0,null,sum(khzzs33))*100),2),0)||'%' ssjrl33,\n"
				+ "NVl(round((sum(ssjrzs32)/decode(sum(khzzs32),0,null,sum(khzzs32))*100),2),0)||'%' ssjrl32,\n"
				+ "NVl(round((sum(ssjrzs30)/decode(sum(khzzs30),0,null,sum(khzzs30))*100),2),0)||'%' ssjrl30,\n"
				+ "NVl(round((sum(ssjrzs25)/decode(sum(khzzs25),0,null,sum(khzzs25))*100),2),0)||'%' ssjrl25,\n"
				+ "NVl(round((sum(ssjrzs86)/decode(sum(khzzs86),0,null,sum(khzzs86))*100),2),0)||'%' ssjrl86,\n"
				+ "NVl(round((sum(ssjrzs85)/decode(sum(khzzs85),0,null,sum(khzzs85))*100),2),0)||'%' ssjrl85,\n"
				+ "NVl(round((sum(ssjrzs84)/decode(sum(khzzs84),0,null,sum(khzzs84))*100),2),0)||'%' ssjrl84,\n"
				+ "NVl(round((sum(ssjrzs83)/decode(sum(khzzs83),0,null,sum(khzzs83))*100),2),0)||'%' ssjrl83,\n"
				+ "NVl(round((sum(ssjrzs82)/decode(sum(khzzs82),0,null,sum(khzzs82))*100),2),0)||'%' ssjrl82,\n"
				+ "NVl(round((sum(ssjrzs81)/decode(sum(khzzs81),0,null,sum(khzzs81))*100),2),0)||'%' ssjrl81,\n"
				+ "NVl(round((sum(ssjrzs80)/decode(sum(khzzs80),0,null,sum(khzzs80))*100),2),0)||'%' ssjrl80,\n"+

				"NVl(round((sum(jrl37)/decode(sum(khzzs37),0,null,sum(khzzs37))*100),2),0)||'%' jrl37, \n" +
				"NVl(round((sum(jrl36)/decode(sum(khzzs36),0,null,sum(khzzs36))*100),2),0)||'%' jrl36, \n" + 
				"NVl(round((sum(jrl35)/decode(sum(khzzs35),0,null,sum(khzzs35))*100),2),0)||'%' jrl35, \n" + 
				"NVl(round((sum(jrl34)/decode(sum(khzzs34),0,null,sum(khzzs34))*100),2),0)||'%' jrl34, \n" + 
				"NVl(round((sum(jrl33)/decode(sum(khzzs33),0,null,sum(khzzs33))*100),2),0)||'%' jrl33, \n" + 
				"NVl(round((sum(jrl32)/decode(sum(khzzs32),0,null,sum(khzzs32))*100),2),0)||'%' jrl32, \n" + 
				"NVl(round((sum(jrl30)/decode(sum(khzzs30),0,null,sum(khzzs30))*100),2),0)||'%' jrl30, \n" + 
				"NVl(round((sum(jrl25)/decode(sum(khzzs25),0,null,sum(khzzs25))*100),2),0)||'%' jrl25, \n" + 
				"NVl(round((sum(jrl86)/decode(sum(khzzs86),0,null,sum(khzzs86))*100),2),0)||'%' jrl86, \n" + 
				"NVl(round((sum(jrl85)/decode(sum(khzzs85),0,null,sum(khzzs85))*100),2),0)||'%' jrl85, \n" + 
				"NVl(round((sum(jrl84)/decode(sum(khzzs84),0,null,sum(khzzs84))*100),2),0)||'%' jrl84, \n" + 
				"NVl(round((sum(jrl83)/decode(sum(khzzs83),0,null,sum(khzzs83))*100),2),0)||'%' jrl83, \n" + 
				"NVl(round((sum(jrl82)/decode(sum(khzzs82),0,null,sum(khzzs82))*100),2),0)||'%' jrl82, \n" + 
				"NVl(round((sum(jrl81)/decode(sum(khzzs81),0,null,sum(khzzs81))*100),2),0)||'%' jrl81, \n" + 
				"NVl(round((sum(jrl80)/decode(sum(khzzs80),0,null,sum(khzzs80))*100),2),0)||'%' jrl80, \n" + 
				
				"NVl(round((sum(zql37)/decode(sum(khzzs37),0,null,sum(khzzs37))*100),2),0)||'%' zql37, \n" + 
				"NVl(round((sum(zql36)/decode(sum(khzzs36),0,null,sum(khzzs36))*100),2),0)||'%' zql36, \n" + 
				"NVl(round((sum(zql35)/decode(sum(khzzs35),0,null,sum(khzzs35))*100),2),0)||'%' zql35, \n" + 
				"NVl(round((sum(zql34)/decode(sum(khzzs34),0,null,sum(khzzs34))*100),2),0)||'%' zql34, \n" + 
				"NVl(round((sum(zql33)/decode(sum(khzzs33),0,null,sum(khzzs33))*100),2),0)||'%' zql33, \n" + 
				"NVl(round((sum(zql32)/decode(sum(khzzs32),0,null,sum(khzzs32))*100),2),0)||'%' zql32, \n" + 
				"NVl(round((sum(zql30)/decode(sum(khzzs30),0,null,sum(khzzs30))*100),2),0)||'%' zql30, \n" + 
				"NVl(round((sum(zql25)/decode(sum(khzzs25),0,null,sum(khzzs25))*100),2),0)||'%' zql25, \n" + 
				"NVl(round((sum(zql86)/decode(sum(khzzs86),0,null,sum(khzzs86))*100),2),0)||'%' zql86, \n" + 
				"NVl(round((sum(zql85)/decode(sum(khzzs85),0,null,sum(khzzs85))*100),2),0)||'%' zql85, \n" + 
				"NVl(round((sum(zql84)/decode(sum(khzzs84),0,null,sum(khzzs84))*100),2),0)||'%' zql84, \n" + 
				"NVl(round((sum(zql83)/decode(sum(khzzs83),0,null,sum(khzzs83))*100),2),0)||'%' zql83, \n" + 
				"NVl(round((sum(zql82)/decode(sum(khzzs82),0,null,sum(khzzs82))*100),2),0)||'%' zql82, \n" + 
				"NVl(round((sum(zql81)/decode(sum(khzzs81),0,null,sum(khzzs81))*100),2),0)||'%' zql81, \n" + 
				"NVl(round((sum(zql80)/decode(sum(khzzs80),0,null,sum(khzzs80))*100),2),0)||'%' zql80\n"

				+ " from \n"
				+ "(select deps.wsid linkedprovicedept,dydj,wsmc,\n"+

				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '37') khzzs37, \n" +
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '36') khzzs36, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '35') khzzs35, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '34') khzzs34, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '33') khzzs33, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '32') khzzs32, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '30') khzzs30, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '25') khzzs25, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '86') khzzs86, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '85') khzzs85, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '84') khzzs84, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '83') khzzs83, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '82') khzzs82, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '81') khzzs81, \n" + 
				"(select count(devicecode) from mw_app.cmsv_linedevice_logic_xtf l where l.LINKEDDEPWS = wsid and l.MONITORINGTYPE != '018003' and  ischeck = '1' and l.dydj = dev.dydj and l.dydj = '80') khzzs80, \n" +

				"(case dydj when '37' then sum(dataaccessrate) else 0 end) jrl37, \n" +
				"(case dydj when '36' then sum(dataaccessrate) else 0 end) jrl36, \n" + 
				"(case dydj when '35' then sum(dataaccessrate) else 0 end) jrl35, \n" + 
				"(case dydj when '34' then sum(dataaccessrate) else 0 end) jrl34, \n" + 
				"(case dydj when '33' then sum(dataaccessrate) else 0 end) jrl33, \n" + 
				"(case dydj when '32' then sum(dataaccessrate) else 0 end) jrl32, \n" + 
				"(case dydj when '30' then sum(dataaccessrate) else 0 end) jrl30, \n" + 
				"(case dydj when '25' then sum(dataaccessrate) else 0 end) jrl25, \n" + 
				"(case dydj when '86' then sum(dataaccessrate) else 0 end) jrl86, \n" + 
				"(case dydj when '85' then sum(dataaccessrate) else 0 end) jrl85, \n" + 
				"(case dydj when '84' then sum(dataaccessrate) else 0 end) jrl84, \n" + 
				"(case dydj when '83' then sum(dataaccessrate) else 0 end) jrl83, \n" + 
				"(case dydj when '82' then sum(dataaccessrate) else 0 end) jrl82, \n" + 
				"(case dydj when '81' then sum(dataaccessrate) else 0 end) jrl81, \n" + 
				"(case dydj when '80' then sum(dataaccessrate) else 0 end) jrl80, \n" + 
			
				"(case dydj when '37' then sum(dataaraccuracyrate) else 0 end) zql37, \n" + 
				"(case dydj when '36' then sum(dataaraccuracyrate) else 0 end) zql36, \n" + 
				"(case dydj when '35' then sum(dataaraccuracyrate) else 0 end) zql35, \n" + 
				"(case dydj when '34' then sum(dataaraccuracyrate) else 0 end) zql34, \n" + 
				"(case dydj when '33' then sum(dataaraccuracyrate) else 0 end) zql33, \n" + 
				"(case dydj when '32' then sum(dataaraccuracyrate) else 0 end) zql32, \n" + 
				"(case dydj when '30' then sum(dataaraccuracyrate) else 0 end) zql30, \n" + 
				"(case dydj when '25' then sum(dataaraccuracyrate) else 0 end) zql25, \n" + 
				"(case dydj when '86' then sum(dataaraccuracyrate) else 0 end) zql86, \n" + 
				"(case dydj when '85' then sum(dataaraccuracyrate) else 0 end) zql85, \n" + 
				"(case dydj when '84' then sum(dataaraccuracyrate) else 0 end) zql84, \n" + 
				"(case dydj when '83' then sum(dataaraccuracyrate) else 0 end) zql83, \n" + 
				"(case dydj when '82' then sum(dataaraccuracyrate) else 0 end) zql82, \n" + 
				"(case dydj when '81' then sum(dataaraccuracyrate) else 0 end) zql81, \n" + 
				"(case dydj when '80' then sum(dataaraccuracyrate) else 0 end) zql80, \n" + 
				
				"(case dydj when '37' then sum(allscored) else 0 end) df37, \n" + 
				"(case dydj when '36' then sum(allscored) else 0 end) df36, \n" + 
				"(case dydj when '35' then sum(allscored) else 0 end) df35, \n" + 
				"(case dydj when '34' then sum(allscored) else 0 end) df34, \n" + 
				"(case dydj when '33' then sum(allscored) else 0 end) df33, \n" + 
				"(case dydj when '32' then sum(allscored) else 0 end) df32, \n" + 
				"(case dydj when '30' then sum(allscored) else 0 end) df30, \n" + 
				"(case dydj when '25' then sum(allscored) else 0 end) df25, \n" + 
				"(case dydj when '86' then sum(allscored) else 0 end) df86, \n" + 
				"(case dydj when '85' then sum(allscored) else 0 end) df85, \n" + 
				"(case dydj when '84' then sum(allscored) else 0 end) df84, \n" + 
				"(case dydj when '83' then sum(allscored) else 0 end) df83, \n" + 
				"(case dydj when '82' then sum(allscored) else 0 end) df82, \n" + 
				"(case dydj when '81' then sum(allscored) else 0 end) df81, \n" + 
				"(case dydj when '80' then sum(allscored) else 0 end) df80\n"
				
				+ " from (select l.*,dataaccessrate,dataaraccuracyrate,allscored from mw_app.cmsv_linedevice_logic_xtf l ,\n"
				+" mw_app.cmsv_index_scored i where l.devicecode = i.devicecode and i.monitoringtype = l.MONITORINGTYPE \n"
				+ querySql
				+ "  )dev,mw_app.cmst_zb_comm_wspz deps where dev.linkedprovicedept(+) = deps.wsid\n"
				+ querySq
				+ " group by deps.wsid,wsmc,dev.dydj) a\n"
				+ " left join \n"
				+ "(select deps.wsid linkedprovicedept,dydj,wsmc,\n"
				+ "(case dydj when '37' then count(devicecode) else 0 end) ssjrzs37,\n"
				+ "(case dydj when '36' then count(devicecode) else 0 end) ssjrzs36,\n"
				+ "(case dydj when '35' then count(devicecode) else 0 end) ssjrzs35,\n"
				+ "(case dydj when '34' then count(devicecode) else 0 end) ssjrzs34,\n"
				+ "(case dydj when '33' then count(devicecode) else 0 end) ssjrzs33,\n"
				+ "(case dydj when '32' then count(devicecode) else 0 end) ssjrzs32,\n"
				+ "(case dydj when '30' then count(devicecode) else 0 end) ssjrzs30,\n"
				+ "(case dydj when '25' then count(devicecode) else 0 end) ssjrzs25,\n"
				+ "(case dydj when '86' then count(devicecode) else 0 end) ssjrzs86,\n"
				+ "(case dydj when '85' then count(devicecode) else 0 end) ssjrzs85,\n"
				+ "(case dydj when '84' then count(devicecode) else 0 end) ssjrzs84,\n"
				+ "(case dydj when '83' then count(devicecode) else 0 end) ssjrzs83,\n"
				+ "(case dydj when '82' then count(devicecode) else 0 end) ssjrzs82,\n"
				+ "(case dydj when '81' then count(devicecode) else 0 end) ssjrzs81,\n"
				+ "(case dydj when '80' then count(devicecode) else 0 end) ssjrzs80\n"

			

				+ " from (select l.* from mw_app.cmsv_linedevice_logic_xtf l ,\n"
				+" mw_app.cmsv_index_scored i where l.devicecode = i.devicecode and i.monitoringtype = l.MONITORINGTYPE \n"
				+ querySql
				+ " )dev\n"
				+ ",mw_app.cmst_zb_comm_wspz deps where   dev.linkedprovicedept = deps.wsid \n"
				+ " and dev.devicecode in(select zzbm from mw_app.cmsv_deviceused_info where sfss = 'T')\n"
				+ querySq
				+ "  group by deps.wsid,dev.dydj,wsmc) b\n"
				+ " on a.linkedprovicedept = b.linkedprovicedept and a.dydj = b.dydj\n"
				+ " group by a.linkedprovicedept,a.wsmc order by PMS_XH) temp)\n "
				+ " select * from ( "
				+ "select linkedprovicedept,SSWSMC,pms_xh,\n"
				+ "khzzs37,khzzs36,\n"
				+ "khzzs35,khzzs34,khzzs33,khzzs32,khzzs30,khzzs25,khzzs86,khzzs85,khzzs84,khzzs83,khzzs82,khzzs81,khzzs80,\n"
				+ "ssjrzs37,ssjrzs36,ssjrzs35,ssjrzs34,ssjrzs33,ssjrzs32,ssjrzs30,ssjrzs25,ssjrzs86,\n"
				+ "ssjrzs85,ssjrzs84,ssjrzs83,ssjrzs82,ssjrzs81,ssjrzs80,\n"+

				"df37,df36,df35,df34,df33,df32,df30,df25,df86, \n" +
				"df85,df84,df83,df82,df81,df80,\n"

				+ "(case when khzzs37=0 then '-' else to_char(substr(ssjrl37,0,length(ssjrl37)-1),'990.99')||'%'end) ssjrl37,\n"
				+ "(case when khzzs36=0 then '-' else to_char(substr(ssjrl36,0,length(ssjrl36)-1),'990.99')||'%'end) ssjrl36,\n"
				+ "(case when khzzs35=0 then '-' else to_char(substr(ssjrl35,0,length(ssjrl35)-1),'990.99')||'%'end) ssjrl35,\n"
				+ "(case when khzzs34=0 then '-' else to_char(substr(ssjrl34,0,length(ssjrl34)-1),'990.99')||'%'end) ssjrl34,\n"
				+ "(case when khzzs33=0 then '-' else to_char(substr(ssjrl33,0,length(ssjrl33)-1),'990.99')||'%'end) ssjrl33,\n"
				+ "(case when khzzs32=0 then '-' else to_char(substr(ssjrl32,0,length(ssjrl32)-1),'990.99')||'%'end) ssjrl32,\n"
				+ "(case when khzzs30=0 then '-' else to_char(substr(ssjrl30,0,length(ssjrl30)-1),'990.99')||'%'end) ssjrl30,\n"
				+ "(case when khzzs25=0 then '-' else to_char(substr(ssjrl25,0,length(ssjrl25)-1),'990.99')||'%'end) ssjrl25,\n"
				+ "(case when khzzs86=0 then '-' else to_char(substr(ssjrl86,0,length(ssjrl86)-1),'990.99')||'%'end) ssjrl86,\n"
				+ "(case when khzzs85=0 then '-' else to_char(substr(ssjrl85,0,length(ssjrl85)-1),'990.99')||'%'end) ssjrl85,\n"
				+ "(case when khzzs84=0 then '-' else to_char(substr(ssjrl84,0,length(ssjrl84)-1),'990.99')||'%'end) ssjrl84,\n"
				+ "(case when khzzs83=0 then '-' else to_char(substr(ssjrl83,0,length(ssjrl83)-1),'990.99')||'%'end) ssjrl83,\n"
				+ "(case when khzzs82=0 then '-' else to_char(substr(ssjrl82,0,length(ssjrl82)-1),'990.99')||'%'end) ssjrl82,\n"
				+ "(case when khzzs81=0 then '-' else to_char(substr(ssjrl81,0,length(ssjrl81)-1),'990.99')||'%'end) ssjrl81,\n"
				+ "(case when khzzs80=0 then '-' else to_char(substr(ssjrl80,0,length(ssjrl80)-1),'990.99')||'%'end) ssjrl80,\n"+

				"(case when khzzs37=0 then '-' else to_char(substr(jrl37,0,length(jrl37)-1),'990.99')||'%'end) jrl37, \n" +
				"(case when khzzs36=0 then '-' else to_char(substr(jrl36,0,length(jrl36)-1),'990.99')||'%'end) jrl36, \n" + 
				"(case when khzzs35=0 then '-' else to_char(substr(jrl35,0,length(jrl35)-1),'990.99')||'%'end) jrl35, \n" + 
				"(case when khzzs34=0 then '-' else to_char(substr(jrl34,0,length(jrl34)-1),'990.99')||'%'end) jrl34, \n" + 
				"(case when khzzs33=0 then '-' else to_char(substr(jrl33,0,length(jrl33)-1),'990.99')||'%'end) jrl33, \n" + 
				"(case when khzzs32=0 then '-' else to_char(substr(jrl32,0,length(jrl32)-1),'990.99')||'%'end) jrl32, \n" + 
				"(case when khzzs30=0 then '-' else to_char(substr(jrl30,0,length(jrl30)-1),'990.99')||'%'end) jrl30, \n" + 
				"(case when khzzs25=0 then '-' else to_char(substr(jrl25,0,length(jrl25)-1),'990.99')||'%'end) jrl25, \n" + 
				"(case when khzzs86=0 then '-' else to_char(substr(jrl86,0,length(jrl86)-1),'990.99')||'%'end) jrl86, \n" + 
				"(case when khzzs85=0 then '-' else to_char(substr(jrl85,0,length(jrl85)-1),'990.99')||'%'end) jrl85, \n" + 
				"(case when khzzs84=0 then '-' else to_char(substr(jrl84,0,length(jrl84)-1),'990.99')||'%'end) jrl84, \n" + 
				"(case when khzzs83=0 then '-' else to_char(substr(jrl83,0,length(jrl83)-1),'990.99')||'%'end) jrl83, \n" + 
				"(case when khzzs82=0 then '-' else to_char(substr(jrl82,0,length(jrl82)-1),'990.99')||'%'end) jrl82, \n" + 
				"(case when khzzs81=0 then '-' else to_char(substr(jrl81,0,length(jrl81)-1),'990.99')||'%'end) jrl81, \n" + 
				"(case when khzzs80=0 then '-' else to_char(substr(jrl80,0,length(jrl80)-1),'990.99')||'%'end) jrl80, \n" + 
				
				"(case when khzzs37=0 then '-' else to_char(substr(zql37,0,length(zql37)-1),'990.99')||'%'end) zql37, \n" + 
				"(case when khzzs36=0 then '-' else to_char(substr(zql36,0,length(zql36)-1),'990.99')||'%'end) zql36, \n" + 
				"(case when khzzs35=0 then '-' else to_char(substr(zql35,0,length(zql35)-1),'990.99')||'%'end) zql35, \n" + 
				"(case when khzzs34=0 then '-' else to_char(substr(zql34,0,length(zql34)-1),'990.99')||'%'end) zql34, \n" + 
				"(case when khzzs33=0 then '-' else to_char(substr(zql33,0,length(zql33)-1),'990.99')||'%'end) zql33, \n" + 
				"(case when khzzs32=0 then '-' else to_char(substr(zql32,0,length(zql32)-1),'990.99')||'%'end) zql32, \n" + 
				"(case when khzzs30=0 then '-' else to_char(substr(zql30,0,length(zql30)-1),'990.99')||'%'end) zql30, \n" + 
				"(case when khzzs25=0 then '-' else to_char(substr(zql25,0,length(zql25)-1),'990.99')||'%'end) zql25, \n" + 
				"(case when khzzs86=0 then '-' else to_char(substr(zql86,0,length(zql86)-1),'990.99')||'%'end) zql86, \n" + 
				"(case when khzzs85=0 then '-' else to_char(substr(zql85,0,length(zql85)-1),'990.99')||'%'end) zql85, \n" + 
				"(case when khzzs84=0 then '-' else to_char(substr(zql84,0,length(zql84)-1),'990.99')||'%'end) zql84, \n" + 
				"(case when khzzs83=0 then '-' else to_char(substr(zql83,0,length(zql83)-1),'990.99')||'%'end) zql83, \n" + 
				"(case when khzzs82=0 then '-' else to_char(substr(zql82,0,length(zql82)-1),'990.99')||'%'end) zql82, \n" + 
				"(case when khzzs81=0 then '-' else to_char(substr(zql81,0,length(zql81)-1),'990.99')||'%'end) zql81, \n" + 
				"(case when khzzs80=0 then '-' else to_char(substr(zql80,0,length(zql80)-1),'990.99')||'%'end) zql80,\n"

				+ "allkhZZS,allSSJRZS,(case when allkhZZS=0 then '-' else to_char(substr(allSSJRL,0,length(allSSJRL)-1),'990.00')||'%'end) allSSJRL \n"
				+

				" ,'1' PX from tmp where SSWSMC not like '%国网运行公司%'\n"
					+ " union all\n"
					+ " select '' linkedprovicedept,'国家电网公司' SSWSMC,'' pms_xh,\n"
					+ "NVl(sum(khzzs37), 0) khzzs37,NVl(sum(khzzs36), 0) khzzs36,NVl(sum(khzzs35), 0) khzzs35,\n"
					+ "NVl(sum(khzzs34), 0) khzzs34,NVl(sum(khzzs33), 0) khzzs33,NVl(sum(khzzs32), 0) khzzs32,\n"
					+ "NVl(sum(khzzs30), 0) khzzs30,NVl(sum(khzzs25), 0) khzzs25,NVl(sum(khzzs86), 0) khzzs86,NVl(sum(khzzs85), 0) khzzs85,\n"
					+ "NVl(sum(khzzs84), 0) khzzs84,NVl(sum(khzzs83), 0) khzzs83,NVl(sum(khzzs82), 0) khzzs82,\n"
					+ "NVl(sum(khzzs81), 0) khzzs81,NVl(sum(khzzs80), 0) khzzs80,"
					+ "NVl(sum(ssjrzs37), 0) ssjrzs37,NVl(sum(ssjrzs36), 0) ssjrzs36,NVl(sum(ssjrzs35), 0) ssjrzs35,\n"
					+ "NVl(sum(ssjrzs34), 0) ssjrzs34,NVl(sum(ssjrzs33), 0) ssjrzs33,NVl(sum(ssjrzs32), 0) ssjrzs32,\n"
					+ "NVl(sum(ssjrzs30), 0) ssjrzs30,NVl(sum(ssjrzs25), 0) ssjrzs25,NVl(sum(ssjrzs86), 0) ssjrzs86,NVl(sum(ssjrzs85), 0) ssjrzs85,\n"
					+ "NVl(sum(ssjrzs84), 0) ssjrzs84,NVl(sum(ssjrzs83), 0) ssjrzs83, NVl(sum(ssjrzs82), 0) ssjrzs82,\n"
					+ " NVl(sum(ssjrzs81), 0) ssjrzs81, NVl(sum(ssjrzs80), 0) ssjrzs80,\n"+


					"NVl(round(sum(dfzs37)/decode(sum(khzzs37),0,null,sum(khzzs37)),2),0) df37,NVl(round(sum(dfzs36)/decode(sum(khzzs36),0,null,sum(khzzs36)),2),0) df36,NVl(round(sum(dfzs35)/decode(sum(khzzs35),0,null,sum(khzzs35)),2),0) df35, \n" +
					"NVl(round(sum(dfzs34)/decode(sum(khzzs34),0,null,sum(khzzs34)),2),0) df34,NVl(round(sum(dfzs33)/decode(sum(khzzs33),0,null,sum(khzzs33)),2),0) df33,NVl(round(sum(dfzs32)/decode(sum(khzzs32),0,null,sum(khzzs32)),2),0) df32,NVl(round(sum(dfzs30)/decode(sum(khzzs30),0,null,sum(khzzs30)),2),0) df30, \n" + 
					"NVl(round(sum(dfzs25)/decode(sum(khzzs25),0,null,sum(khzzs25)),2),0) df25,NVl(round(sum(dfzs86)/decode(sum(khzzs86),0,null,sum(khzzs86)),2),0) df86,NVl(round(sum(dfzs85)/decode(sum(khzzs85),0,null,sum(khzzs85)),2),0) df85, \n" + 
					"NVl(round(sum(dfzs84)/decode(sum(khzzs84),0,null,sum(khzzs84)),2),0) df84,NVl(round(sum(dfzs83)/decode(sum(khzzs83),0,null,sum(khzzs83)),2),0) df83,NVl(round(sum(dfzs82)/decode(sum(khzzs82),0,null,sum(khzzs82)),2),0) df82, \n" + 
					"NVl(round(sum(dfzs81)/decode(sum(khzzs81),0,null,sum(khzzs81)),2),0) df81,NVl(round(sum(dfzs80)/decode(sum(khzzs80),0,null,sum(khzzs80)),2),0) df80,\n"


					+ "(case when sum(khzzs37)=0 then '-' else to_char((sum(ssjrzs37) /sum(khzzs37)*100),'990.99')||'%'end) ssjrl37,\n"
					+ "(case when sum(khzzs36)=0 then '-' else to_char((sum(ssjrzs36) /sum(khzzs36)*100),'990.99')||'%'end) ssjrl36,\n"
					+ "(case when sum(khzzs35)=0 then '-' else to_char((sum(ssjrzs35) /sum(khzzs35)*100),'990.99')||'%'end) ssjrl35,\n"
					+ "(case when sum(khzzs34)=0 then '-' else to_char((sum(ssjrzs34) /sum(khzzs34)*100),'990.99')||'%'end) ssjrl34,\n"
					+ "(case when sum(khzzs33)=0 then '-' else to_char((sum(ssjrzs33) /sum(khzzs33)*100),'990.99')||'%'end) ssjrl33,\n"
					+ "(case when sum(khzzs32)=0 then '-' else to_char((sum(ssjrzs32) /sum(khzzs32)*100),'990.99')||'%'end) ssjrl32,\n"
					+ "(case when sum(khzzs30)=0 then '-' else to_char((sum(ssjrzs30) /sum(khzzs30)*100),'990.99')||'%'end) ssjrl30,\n"
					+ "(case when sum(khzzs25)=0 then '-' else to_char((sum(ssjrzs25) /sum(khzzs25)*100),'990.99')||'%'end) ssjrl25,\n"
					+ "(case when sum(khzzs86)=0 then '-' else to_char((sum(ssjrzs86) /sum(khzzs86)*100),'990.99')||'%'end) ssjrl86,\n"
					+ "(case when sum(khzzs85)=0 then '-' else to_char((sum(ssjrzs85) /sum(khzzs85)*100),'990.99')||'%'end) ssjrl85,\n"
					+ "(case when sum(khzzs84)=0 then '-' else to_char((sum(ssjrzs84) /sum(khzzs84)*100),'990.99')||'%'end) ssjrl84,\n"
					+ "(case when sum(khzzs83)=0 then '-' else to_char((sum(ssjrzs83) /sum(khzzs83)*100),'990.99')||'%'end) ssjrl83,\n"
					+ "(case when sum(khzzs82)=0 then '-' else to_char((sum(ssjrzs82) /sum(khzzs82)*100),'990.99')||'%'end) ssjrl82,\n"
					+ "(case when sum(khzzs81)=0 then '-' else to_char((sum(ssjrzs81) /sum(khzzs81)*100),'990.99')||'%'end) ssjrl81,\n"
					+ "(case when sum(khzzs80)=0 then '-' else to_char((sum(ssjrzs80) /sum(khzzs80)*100),'990.99')||'%'end) ssjrl80,\n"+

					"(case when sum(khzzs37)=0 then '-' else to_char((sum(jrls37) /sum(khzzs37)*100),'990.99')||'%'end) jrl37, \n" +
					"(case when sum(khzzs36)=0 then '-' else to_char((sum(jrls36) /sum(khzzs36)*100),'990.99')||'%'end) jrl36, \n" + 
					"(case when sum(khzzs35)=0 then '-' else to_char((sum(jrls35) /sum(khzzs35)*100),'990.99')||'%'end) jrl35, \n" + 
					"(case when sum(khzzs34)=0 then '-' else to_char((sum(jrls34) /sum(khzzs34)*100),'990.99')||'%'end) jrl34, \n" + 
					"(case when sum(khzzs33)=0 then '-' else to_char((sum(jrls33) /sum(khzzs33)*100),'990.99')||'%'end) jrl33, \n" + 
					"(case when sum(khzzs32)=0 then '-' else to_char((sum(jrls32) /sum(khzzs32)*100),'990.99')||'%'end) jrl32, \n" + 
					"(case when sum(khzzs30)=0 then '-' else to_char((sum(jrls30) /sum(khzzs30)*100),'990.99')||'%'end) jrl30, \n" + 
					"(case when sum(khzzs25)=0 then '-' else to_char((sum(jrls25) /sum(khzzs25)*100),'990.99')||'%'end) jrl25, \n" + 
					"(case when sum(khzzs86)=0 then '-' else to_char((sum(jrls86) /sum(khzzs86)*100),'990.99')||'%'end) jrl86, \n" + 
					"(case when sum(khzzs85)=0 then '-' else to_char((sum(jrls85) /sum(khzzs85)*100),'990.99')||'%'end) jrl85, \n" + 
					"(case when sum(khzzs84)=0 then '-' else to_char((sum(jrls84) /sum(khzzs84)*100),'990.99')||'%'end) jrl84, \n" + 
					"(case when sum(khzzs83)=0 then '-' else to_char((sum(jrls83) /sum(khzzs83)*100),'990.99')||'%'end) jrl83, \n" + 
					"(case when sum(khzzs82)=0 then '-' else to_char((sum(jrls82) /sum(khzzs82)*100),'990.99')||'%'end) jrl82, \n" + 
					"(case when sum(khzzs81)=0 then '-' else to_char((sum(jrls81) /sum(khzzs81)*100),'990.99')||'%'end) jrl81, \n" + 
					"(case when sum(khzzs80)=0 then '-' else to_char((sum(jrls80) /sum(khzzs80)*100),'990.99')||'%'end) jrl80, \n" + 
					" \n" + 
					"(case when sum(khzzs37)=0 then '-' else to_char((sum(zqls37) /sum(khzzs37)*100),'990.99')||'%'end) zql37, \n" + 
					"(case when sum(khzzs36)=0 then '-' else to_char((sum(zqls36) /sum(khzzs36)*100),'990.99')||'%'end) zql36, \n" + 
					"(case when sum(khzzs35)=0 then '-' else to_char((sum(zqls35) /sum(khzzs35)*100),'990.99')||'%'end) zql35, \n" + 
					"(case when sum(khzzs34)=0 then '-' else to_char((sum(zqls34) /sum(khzzs34)*100),'990.99')||'%'end) zql34, \n" + 
					"(case when sum(khzzs33)=0 then '-' else to_char((sum(zqls33) /sum(khzzs33)*100),'990.99')||'%'end) zql33, \n" + 
					"(case when sum(khzzs32)=0 then '-' else to_char((sum(zqls32) /sum(khzzs32)*100),'990.99')||'%'end) zql32, \n" + 
					"(case when sum(khzzs30)=0 then '-' else to_char((sum(zqls30) /sum(khzzs30)*100),'990.99')||'%'end) zql30, \n" + 
					"(case when sum(khzzs25)=0 then '-' else to_char((sum(zqls25) /sum(khzzs25)*100),'990.99')||'%'end) zql25, \n" + 
					"(case when sum(khzzs86)=0 then '-' else to_char((sum(zqls86) /sum(khzzs86)*100),'990.99')||'%'end) zql86, \n" + 
					"(case when sum(khzzs85)=0 then '-' else to_char((sum(zqls85) /sum(khzzs85)*100),'990.99')||'%'end) zql85, \n" + 
					"(case when sum(khzzs84)=0 then '-' else to_char((sum(zqls84) /sum(khzzs84)*100),'990.99')||'%'end) zql84, \n" + 
					"(case when sum(khzzs83)=0 then '-' else to_char((sum(zqls83) /sum(khzzs83)*100),'990.99')||'%'end) zql83, \n" + 
					"(case when sum(khzzs82)=0 then '-' else to_char((sum(zqls82) /sum(khzzs82)*100),'990.99')||'%'end) zql82, \n" + 
					"(case when sum(khzzs81)=0 then '-' else to_char((sum(zqls81) /sum(khzzs81)*100),'990.99')||'%'end) zql81, \n" + 
					"(case when sum(khzzs80)=0 then '-' else to_char((sum(zqls80) /sum(khzzs80)*100),'990.99')||'%'end) zql80,\n"

					+ "nvl(sum(allKHZZS),0) allKHZZS,nvl(sum(allSSJRZS),0) allSSJRZS,\n"
					+ "(case when sum(allKHZZS)=0 then '-' else to_char((sum(allSSJRZS) /sum(allKHZZS)*100),'990.99')||'%'end)  allssjrl\n"
					
					+ " ,'0' PX from tmp "
				    + " ) order by PX,pms_xh ";
		try{

			cols = "LINKEDPROVICEDEPT,SSWSMC,PMS_XH,KHZZS37,KHZZS36,KHZZS35,KHZZS34,KHZZS33,KHZZS32,KHZZS30," +
					"KHZZS25,KHZZS86,KHZZS85,KHZZS84,KHZZS83,KHZZS82,KHZZS81,KHZZS80,SSJRZS37,SSJRZS36,SSJRZS35," +
					"SSJRZS34,SSJRZS33,SSJRZS32,SSJRZS30,SSJRZS25,SSJRZS86,SSJRZS85,SSJRZS84,SSJRZS83,SSJRZS82," +
					"SSJRZS81,SSJRZS80,DF37,DF36,DF35,DF34,DF33,DF32,DF30,DF25,DF86,DF85,DF84,DF83,DF82,DF81,DF80," +
					"SSJRL37,SSJRL36,SSJRL35,SSJRL34,SSJRL33,SSJRL32,SSJRL30,SSJRL25,SSJRL86,SSJRL85,SSJRL84," +
					"SSJRL83,SSJRL82,SSJRL81,SSJRL80,JRL37,JRL36,JRL35,JRL34,JRL33,JRL32,JRL30,JRL25,JRL86,JRL85," +
					"JRL84,JRL83,JRL82,JRL81,JRL80,ZQL37,ZQL36,ZQL35,ZQL34,ZQL33,ZQL32,ZQL30,ZQL25,ZQL86,ZQL85," +
					"ZQL84,ZQL83,ZQL82,ZQL81,ZQL80,ALLKHZZS,ALLSSJRZS,ALLSSJRL,PX";
			
			
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-输电装置运行状况按电压等级统计", "操作成功");
			return qro;
		}catch(Exception e){
			log.info("执行按电压等级统计监测装置运行状况时出错！", e);
			e.printStackTrace();
			loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-输电装置运行状况按电压等级统计", "操作失败");
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
					querySql.append(" and DYDJ in ('");
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
					querySql.append(" and l.MONITORINGTYPE  in ('");
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
				+ " )dev,mw_app.cmst_zb_comm_wspz deps"
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
			loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-输电装置运行状况按监测类型统计", "操作成功");
			return qro;
		}catch(Exception e){
			log.info("执行按监测类型统计输电监测装置运行状况时出错！", e);
			e.printStackTrace();
			loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-输电装置运行状况按监测类型统计", "操作失败");
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
					querySql.append(" and DYDJ in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					jclx = filterValue;
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
						zzzsColumnSql +="zzzs"+filterValueArr[j]+"+";
						}
						zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
				
					querySql.append(" and l.MONITORINGTYPE in ('");
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
			loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-输电装置运行状况按生产厂家统计", "操作成功");
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行按生产厂家统计输电监测装置运行状况时出错",e);
			loggerSaveBizc.updataUserlog("统计", "状态监测-装置管理-输电装置运行状况按生产厂家统计", "操作失败");
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
                       "   where t.linkedstation = n.linkedstation  and rownum <= 1) linkedstationname,  max(t3.dydjMC) MC, t.wsmc province_name \n"+
                        "  from (select linkedstation,wsmc,linkedequipmentdy,linkedprovicedept,monitoringtypes,MANUFACTURER,LINKEDEQUIPMENTNAME,RELEASEDATE,RUNDATE,linkedstationname  from mw_app.CMST_transfdevice t0,  MW_APP.cmst_zb_comm_wspz t1  \n"+
                       " where t0.linkedprovicedept = t1.wsid and t0.LINKEDEQUIPMENTDY IS NOT NULL) t, mw_app.CMST_SB_PZ_SBDYDJ t3 \n"+
                       " where t.linkedequipmentdy = t3.dydjbm "+querySql.toString()+" and t.wsmc is not null \n"+
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
