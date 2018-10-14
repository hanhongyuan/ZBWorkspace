package com.sgcc.pms.zbztjc.kqxt.bdgjxxcxtj_stat.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.kqxt.Constants;
import com.sgcc.pms.zbztjc.kqxt.sdgjxxcxtj_stat.bizc.Sdgjxxcxtj_statBizc;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class Bdgjxxcxtj_statBizc implements IBdgjxxcxtj_statBizc{

	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(Sdgjxxcxtj_statBizc.class);
	
	@Override
	public QueryResultObject statByDydj(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		
		String ssdw = null;
		String bdz = null;
		String gjjb = null;
		String ishandled = null;
		String gjsj = null;
		StringBuffer querySql = new StringBuffer();
		
		if(null != params.getFilter() && StringUtils.isNotBlank(params.getFilter().toString())){				//如果参数不为空，获取各个参数的值
			String[] filter = params.getFilter().toString().trim().split("&");	//对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				String filterKey = filter[i].split("=")[0].trim();			//变量名称
				String filterValue = filter[i].split("=")[1].trim();		//变量值
				if(Constants.SSDW.equals(filterKey)){
					ssdw = filterValue;
					querySql.append(" AND linkeddepws IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DYDJ.equals(filterKey)){
					querySql.append(" AND DEVICEVOLTAGEBZDM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.JCLX.equals(filterKey)){
					querySql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.BDZ.equals(filterKey)){
					if(null != filterValue && StringUtils.isNotBlank(filterValue)){
						bdz = filterValue;
						querySql.append(" and LINKEDSTATIONNAME LIKE '%" + filterValue + "%'");
					}
				}else if(Constants.GJJB.equals(filterKey)){
					gjjb = filterValue;
					querySql.append(" AND ALARMNUM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.ISHANDLED.equalsIgnoreCase(filterKey)){
					ishandled = filterValue;
					if(Constants.T.equals(ishandled)){
						querySql.append(" and ishandled = 'T'");
					}else if(Constants.F.equals(ishandled)){
						querySql.append(" and (ishandled is null or ishandled = 'F')");
					}
				}else if(Constants.GJSJ.equals(filterKey)){
					gjsj = filterValue;
					String startTime = null;
					String endTime = null;
					
					if(",".equals(filterValue.substring(0, 1))){			//如果告警开始时间为空
						endTime = filterValue.split(",")[1];
						querySql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') <= '");
						querySql.append(endTime);
						querySql.append("'");
					}else if(",".equals(filterValue.substring(filterValue.length()-1, filterValue.length()))){		//如果告警结束时间为空
						startTime = filterValue.split(",")[0];
						querySql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') >= '");
						querySql.append(startTime);
						querySql.append("'");
					}else{					//如果告警开始时间和结束时间都不为空
						startTime = filterValue.split(",")[0];
						endTime = filterValue.split(",")[1];
						querySql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') BETWEEN '");
						querySql.append(startTime);
						querySql.append("' AND '");
						querySql.append(endTime);
						querySql.append("'");
					}
				}
			}
		}
		
		try{
			cols ="OBJ_ID,WSDEPMC,XH,CNT,THCNT,ALLCNT,ALLHCNT,ALLNCNT,ALLHANDLERATE,CNT37,HCNT37,NCNT37,HANDLERATE37,CNT36,HCNT36,NCNT36,HANDLERATE36,CNT35,HCNT35,NCNT35,HANDLERATE35,CNT34,HCNT34,NCNT34,HANDLERATE34,CNT33,HCNT33,NCNT33,HANDLERATE33," +
					"CNT32,HCNT32,NCNT32,HANDLERATE32,CNT30,HCNT30,NCNT30,HANDLERATE30,CNT25,HCNT25,NCNT25,HANDLERATE25,CNT86,HCNT86,NCNT86,HANDLERATE86,CNT85,HCNT85,NCNT85,HANDLERATE85,CNT84,HCNT84,NCNT84,HANDLERATE84,CNT83,HCNT83,NCNT83,HANDLERATE83," +
					"CNT82,HCNT82,NCNT82,HANDLERATE82,CNT81,HCNT81,NCNT81,HANDLERATE81,CNT80,HCNT80,NCNT80,HANDLERATE80";
			
			String sql = "WITH tab AS(\n" +
					"SELECT t.linkeddepws,t.WSDEPMC,t.xh,SUM(t.cnt) cnt,SUM(t.hcnt) thcnt,\n" + 
					"\n" + 
					"SUM(CASE WHEN t.DEVICEVOLTAGEBZDM IN('37','36','35','34','33','82','83','84','85','86') then t.cnt else 0 END) AS allCNT,"+
					"SUM(CASE WHEN t.DEVICEVOLTAGEBZDM IN('37','36','35','34','33','82','83','84','85','86') then t.hcnt else 0 END) AS allHCNT,"+
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'37',t.cnt,0)) AS CNT37,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '37' then t.hcnt else 0 END) AS HCNT37,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'36',t.cnt,0)) AS CNT36,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '36' then t.hcnt else 0 END) AS HCNT36,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'35',t.cnt,0)) AS CNT35,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '35' then t.hcnt else 0 END) AS HCNT35,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'34',t.cnt,0)) AS CNT34,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '34' then t.hcnt else 0 END) AS HCNT34,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'33',t.cnt,0)) AS CNT33,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '33' then t.hcnt else 0 END) AS HCNT33,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'32',t.cnt,0)) AS CNT32,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '32' then t.hcnt else 0 END) AS HCNT32,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'30',t.cnt,0)) AS CNT30,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '30' then t.hcnt else 0 END) AS HCNT30,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'25',t.cnt,0)) AS CNT25,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '25' then t.hcnt else 0 END) AS HCNT25,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'86',t.cnt,0)) AS CNT86,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '86' then t.hcnt else 0 END) AS HCNT86,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'85',t.cnt,0)) AS CNT85,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '85' then t.hcnt else 0 END) AS HCNT85,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'84',t.cnt,0)) AS CNT84,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '84' then t.hcnt else 0 END) AS HCNT84,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'83',t.cnt,0)) AS CNT83,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '83' then t.hcnt else 0 END) AS HCNT83,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'82',t.cnt,0)) AS CNT82,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '82' then t.hcnt else 0 END) AS HCNT82,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'81',1,0)) AS CNT81,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '81' then t.hcnt else 0 END) AS HCNT81,\n" + 
					"\n" + 
					"sum(DECODE (t.DEVICEVOLTAGEBZDM,'80',t.cnt,0)) AS CNT80,\n" + 
					"sum(CASE WHEN t.DEVICEVOLTAGEBZDM = '80' then t.hcnt else 0 END) AS HCNT80\n" + 
					"FROM \n";		    
			//if((null != bdz && StringUtils.isNotBlank(bdz)) || null != gjsj || null != gjjb){
						sql+="(SELECT TB.LINKEDDEPWS,TB.WSDEPMC,TB.XH,\n" +
						"       TB.DEVICEVOLTAGEBZDM,TB.MONITORINGTYPE,TB.alarmlevelmc,TB.LINKEDSTATIONNAME,\n" + 
						"       COUNT(*) CNT,sum(DECODE(TB.ishandled,'T',1,0)) HCNT,\n" + 
						"       MIN(to_char(TB.ALARMTIME,'yyyy-mm-dd')) alarmTime,TB.ALARMNUM,\n" + 
						"       SYSDATE statTime,'T' flag\n" + 
						"   FROM MW_APP.CMSV_ALARM_BDINFOF TB\n" + 
						"   WHERE TB.WSDEPMC IS NOT NULL\n" + 
						"   AND TB.ALARMSOURCE = 'E' AND TB.MONITORINGTYPE LIKE '02%' AND TB.ALARMLEVEL IS NOT NULL\n" + 
						"   GROUP BY TB.LINKEDDEPWS,TB.WSDEPMC,TB.XH,TB.DEVICEVOLTAGEBZDM,TB.MONITORINGTYPE,TB.alarmlevelmc,TB.LINKEDSTATIONNAME,TB.ALARMNUM,to_char(TB.ALARMTIME,'yyyy-mm-dd'))";
					//}
					//此处有疑问  2017年3月6日
					/*else{
						sql+="MW_APP.CMST_ALARMSTATISTIC_KQ \n";
					}*/
					sql+=" T,MW_APP.CMST_SB_PZ_SBDYDJ DY,MW_APP.CMST_MONITORINGTYPE MT \n" + 
					" WHERE T.DEVICEVOLTAGEBZDM = DY.DYDJBM AND MT.TYPECODE = T.MONITORINGTYPE AND t.flag = 'T' \n";
				
					if(null != querySql && "" != querySql.toString()){
						sql+=querySql.toString();
					}
					
					sql+=" GROUP BY t.linkeddepws,t.WSDEPMC,t.xh ORDER BY t.xh\n" + 
					")\n" + 
					"SELECT * FROM (\n" + 
					"\n" + 
					"SELECT d.wsid obj_id,d.wsmc wsdepmc,d.zdypx xh,NVL(cnt,0) cnt,NVL(thcnt,0) thcnt,\n" + 
					"  NVL(allcnt,0)allcnt,NVL(allhcnt,0)allhcnt,(NVL(allcnt,0)-NVL(allhcnt,0)) allncnt,DECODE(NVL(allcnt,0),0,'-',to_char(ROUND(allhcnt/allcnt*100,2),'fm9999999990.00')||'%') allHANDLERATE," +
					"  NVL(CNT37,0)CNT37,NVL(HCNT37,0)HCNT37,(NVL(CNT37,0)-NVL(HCNT37,0)) NCNT37,DECODE(NVL(CNT37,0),0,'-',to_char(ROUND(HCNT37/CNT37*100,2),'fm9999999990.00')||'%') HANDLERATE37,\n" + 
					"  NVL(CNT36,0)CNT36,NVL(HCNT36,0)HCNT36,(NVL(CNT36,0)-NVL(HCNT36,0)) NCNT36,DECODE(NVL(CNT36,0),0,'-',to_char(ROUND(HCNT36/CNT36*100,2),'fm9999999990.00')||'%') HANDLERATE36,\n" + 
					"  NVL(CNT35,0)CNT35,NVL(HCNT35,0)HCNT35,(NVL(CNT35,0)-NVL(HCNT35,0)) NCNT35,DECODE(NVL(CNT35,0),0,'-',to_char(ROUND(HCNT35/CNT35*100,2),'fm9999999990.00')||'%') HANDLERATE35,\n" + 
					"  NVL(CNT34,0)CNT34,NVL(HCNT34,0)HCNT34,(NVL(CNT34,0)-NVL(HCNT34,0)) NCNT34,DECODE(NVL(CNT34,0),0,'-',to_char(ROUND(HCNT34/CNT34*100,2),'fm9999999990.00')||'%') HANDLERATE34,\n" + 
					"  NVL(CNT33,0)CNT33,NVL(HCNT33,0)HCNT33,(NVL(CNT33,0)-NVL(HCNT33,0)) NCNT33,DECODE(NVL(CNT33,0),0,'-',to_char(ROUND(HCNT33/CNT33*100,2),'fm9999999990.00')||'%') HANDLERATE33,\n" + 
					"  NVL(CNT32,0)CNT32,NVL(HCNT32,0)HCNT32,(NVL(CNT32,0)-NVL(HCNT32,0)) NCNT32,DECODE(NVL(CNT32,0),0,'-',to_char(ROUND(HCNT32/CNT32*100,2),'fm9999999990.00')||'%') HANDLERATE32,\n" + 
					"  NVL(CNT30,0)CNT30,NVL(HCNT30,0)HCNT30,(NVL(CNT30,0)-NVL(HCNT30,0)) NCNT30,DECODE(NVL(CNT30,0),0,'-',to_char(ROUND(HCNT30/CNT30*100,2),'fm9999999990.00')||'%') HANDLERATE30,\n" + 
					"  NVL(CNT25,0)CNT25,NVL(HCNT25,0)HCNT25,(NVL(CNT25,0)-NVL(HCNT25,0)) NCNT25,DECODE(NVL(CNT25,0),0,'-',to_char(ROUND(HCNT25/CNT25*100,2),'fm9999999990.00')||'%') HANDLERATE25,\n" + 
					"  NVL(CNT86,0)CNT86,NVL(HCNT86,0)HCNT86,(NVL(CNT86,0)-NVL(HCNT86,0)) NCNT86,DECODE(NVL(CNT86,0),0,'-',to_char(ROUND(HCNT86/CNT86*100,2),'fm9999999990.00')||'%') HANDLERATE86,\n" + 
					"  NVL(CNT85,0)CNT85,NVL(HCNT85,0)HCNT85,(NVL(CNT85,0)-NVL(HCNT85,0)) NCNT85,DECODE(NVL(CNT85,0),0,'-',to_char(ROUND(HCNT85/CNT85*100,2),'fm9999999990.00')||'%') HANDLERATE85,\n" + 
					"  NVL(CNT84,0)CNT84,NVL(HCNT84,0)HCNT84,(NVL(CNT84,0)-NVL(HCNT84,0)) NCNT84,DECODE(NVL(CNT84,0),0,'-',to_char(ROUND(HCNT84/CNT84*100,2),'fm9999999990.00')||'%') HANDLERATE84,\n" + 
					"  NVL(CNT83,0)CNT83,NVL(HCNT83,0)HCNT83,(NVL(CNT83,0)-NVL(HCNT83,0)) NCNT83,DECODE(NVL(CNT83,0),0,'-',to_char(ROUND(HCNT83/CNT83*100,2),'fm9999999990.00')||'%') HANDLERATE83,\n" + 
					"  NVL(CNT82,0)CNT82,NVL(HCNT82,0)HCNT82,(NVL(CNT82,0)-NVL(HCNT82,0)) NCNT82,DECODE(NVL(CNT82,0),0,'-',to_char(ROUND(HCNT82/CNT82*100,2),'fm9999999990.00')||'%') HANDLERATE82,\n" + 
					"  NVL(CNT81,0)CNT81,NVL(HCNT81,0)HCNT81,(NVL(CNT81,0)-NVL(HCNT81,0)) NCNT81,DECODE(NVL(CNT81,0),0,'-',to_char(ROUND(HCNT81/CNT81*100,2),'fm9999999990.00')||'%') HANDLERATE81,\n" + 
					"  NVL(CNT80,0)CNT80,NVL(HCNT80,0)HCNT80,(NVL(CNT80,0)-NVL(HCNT80,0)) NCNT80,DECODE(NVL(CNT80,0),0,'-',to_char(ROUND(HCNT80/CNT80*100,2),'fm9999999990.00')||'%') HANDLERATE80\n" + 
					"  FROM tab,mw_app.CMST_ZB_COMM_WSPZ d WHERE d.wsid = tab.linkeddepws(+)\n";
				
					if(null != ssdw && StringUtils.isNotBlank(ssdw)){
						sql+= " and obj_id IN('"+ssdw.replace(",","','")+"')";
					}
			
					sql+="  AND d.wsmc not like '%运行%' " + 
					"\n"+
					"UNION ALL\n" + 
						"(\n" + 
						"select '国家电网公司','国家电网公司',0,NVL(SUM(CNT),0),NVL(SUM(THCNT),0),\n" + 
						" NVL(SUM(allCNT),0),NVL(SUM(allHCNT),0),(NVL(SUM(allcnt),0)-NVL(SUM(allhcnt),0)),DECODE(NVL(SUM(allHCNT),0),0,'-',to_char(ROUND(SUM(allHCNT)/SUM(allCNT)*100,2),'fm9999999990.00')||'%'),"+
						" NVL(SUM(CNT37),0),NVL(SUM(HCNT37),0),(NVL(SUM(CNT37),0)-NVL(SUM(HCNT37),0)),DECODE(NVL(SUM(HCNT37),0),0,'-',to_char(ROUND(SUM(HCNT37)/SUM(CNT37)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT36),0),NVL(SUM(HCNT36),0),(NVL(SUM(CNT36),0)-NVL(SUM(HCNT36),0)),DECODE(NVL(SUM(HCNT36),0),0,'-',to_char(ROUND(SUM(HCNT36)/SUM(CNT36)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT35),0),NVL(SUM(HCNT35),0),(NVL(SUM(CNT35),0)-NVL(SUM(HCNT35),0)),DECODE(NVL(SUM(HCNT35),0),0,'-',to_char(ROUND(SUM(HCNT35)/SUM(CNT35)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT34),0),NVL(SUM(HCNT34),0),(NVL(SUM(CNT34),0)-NVL(SUM(HCNT34),0)),DECODE(NVL(SUM(HCNT34),0),0,'-',to_char(ROUND(SUM(HCNT34)/SUM(CNT34)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT33),0),NVL(SUM(HCNT33),0),(NVL(SUM(CNT33),0)-NVL(SUM(HCNT33),0)),DECODE(NVL(SUM(HCNT33),0),0,'-',to_char(ROUND(SUM(HCNT33)/SUM(CNT33)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT32),0),NVL(SUM(HCNT32),0),(NVL(SUM(CNT32),0)-NVL(SUM(HCNT32),0)),DECODE(NVL(SUM(HCNT32),0),0,'-',to_char(ROUND(SUM(HCNT32)/SUM(CNT32)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT30),0),NVL(SUM(HCNT30),0),(NVL(SUM(CNT30),0)-NVL(SUM(HCNT30),0)),DECODE(NVL(SUM(HCNT30),0),0,'-',to_char(ROUND(SUM(HCNT30)/SUM(CNT30)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT25),0),NVL(SUM(HCNT25),0),(NVL(SUM(CNT25),0)-NVL(SUM(HCNT25),0)),DECODE(NVL(SUM(HCNT25),0),0,'-',to_char(ROUND(SUM(HCNT25)/SUM(CNT25)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT86),0),NVL(SUM(HCNT86),0),(NVL(SUM(CNT86),0)-NVL(SUM(HCNT86),0)),DECODE(NVL(SUM(HCNT86),0),0,'-',to_char(ROUND(SUM(HCNT86)/SUM(CNT86)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT85),0),NVL(SUM(HCNT85),0),(NVL(SUM(CNT85),0)-NVL(SUM(HCNT85),0)),DECODE(NVL(SUM(HCNT85),0),0,'-',to_char(ROUND(SUM(HCNT85)/SUM(CNT85)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT84),0),NVL(SUM(HCNT84),0),(NVL(SUM(CNT84),0)-NVL(SUM(HCNT84),0)),DECODE(NVL(SUM(HCNT84),0),0,'-',to_char(ROUND(SUM(HCNT84)/SUM(CNT84)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT83),0),NVL(SUM(HCNT83),0),(NVL(SUM(CNT83),0)-NVL(SUM(HCNT83),0)),DECODE(NVL(SUM(HCNT83),0),0,'-',to_char(ROUND(SUM(HCNT83)/SUM(CNT83)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT82),0),NVL(SUM(HCNT82),0),(NVL(SUM(CNT82),0)-NVL(SUM(HCNT82),0)),DECODE(NVL(SUM(HCNT82),0),0,'-',to_char(ROUND(SUM(HCNT82)/SUM(CNT82)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT81),0),NVL(SUM(HCNT81),0),(NVL(SUM(CNT81),0)-NVL(SUM(HCNT81),0)),DECODE(NVL(SUM(HCNT81),0),0,'-',to_char(ROUND(SUM(HCNT81)/SUM(CNT81)*100,2),'fm9999999990.00')||'%'),\n" + 
						" NVL(SUM(CNT80),0),NVL(SUM(HCNT80),0),(NVL(SUM(CNT80),0)-NVL(SUM(HCNT80),0)),DECODE(NVL(SUM(HCNT80),0),0,'-',to_char(ROUND(SUM(HCNT80)/SUM(CNT80)*100,2),'fm9999999990.00')||'%') \n" + 
						"\n" + 
						"  FROM tab)"+
					")ORDER BY xh";
					
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			   
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			log.info("执行按电压等级统计输电告警信息时出错！", e);
			e.printStackTrace();
		}
		
		return qro;
		
	}
	
	
	@Override
	public QueryResultObject statByJclx(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		
		String ssdw = null;
		String bdz = null;
		String gjjb = null;
		String ishandled = null;
		String gjsj = null;
		
		StringBuffer querySql = new StringBuffer();
		
		if(null != params.getFilter() && StringUtils.isNotBlank(params.getFilter().toString())){				//如果参数不为空，获取各个参数的值
			System.out.println(params.getFilter());
			String[] filter = params.getFilter().toString().trim().split("&");	//对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				String filterKey = filter[i].split("=")[0].trim();			//变量名称
				String filterValue = filter[i].split("=")[1].trim();		//变量值
				if(Constants.SSDW.equals(filterKey)){
					ssdw = filterValue;
					querySql.append(" AND linkeddepws IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DYDJ.equals(filterKey)){
					querySql.append(" AND DEVICEVOLTAGEBZDM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.JCLX.equals(filterKey)){
					querySql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.BDZ.equals(filterKey)){
					bdz = filterValue;
					querySql.append(" AND LINKEDSTATIONNAME LIKE '%" + bdz + "%'");
				}else if(Constants.GJJB.equals(filterKey)){
					gjjb = filterValue;
					querySql.append(" AND ALARMNUM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.ISHANDLED.equalsIgnoreCase(filterKey)){
					ishandled = filterValue;
					if(Constants.T.equals(ishandled)){
						querySql.append(" and ishandled = 'T'");
					}else if(Constants.F.equals(ishandled)){
						querySql.append(" and (ishandled is null or ishandled = 'F')");
					}
				}else if(Constants.GJSJ.equals(filterKey)){
					gjsj = filterValue;
					String startTime = null;
					String endTime = null;
					
					if(",".equals(filterValue.substring(0, 1))){			//如果告警开始时间为空
						endTime = filterValue.split(",")[1];
						querySql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') <= '");
						querySql.append(endTime);
						querySql.append("'");
					}else if(",".equals(filterValue.substring(filterValue.length()-1, filterValue.length()))){		//如果告警结束时间为空
						startTime = filterValue.split(",")[0];
						querySql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') >= '");
						querySql.append(startTime);
						querySql.append("'");
					}else{					//如果告警开始时间和结束时间都不为空
						startTime = filterValue.split(",")[0];
						endTime = filterValue.split(",")[1];
						querySql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') BETWEEN '");
						querySql.append(startTime);
						querySql.append("' AND '");
						querySql.append(endTime);
						querySql.append("'");
					}
				}
			}
		}
		
		try{
			cols ="OBJ_ID,WSDEPMC,XH,CNT,ALLCNT,ALLHCNT,ALLNCNT,ALLHANDLERATE,CNT021001,HCNT021001,NCNT021001,HANDLERATE021001,CNT021002,HCNT021002,NCNT021002,HANDLERATE021002,CNT021003,HCNT021003,NCNT021003,HANDLERATE021003,CNT021004,HCNT021004,NCNT021004,HANDLERATE021004,CNT021005,HCNT021005,NCNT021005,HANDLERATE021005," +
					"CNT022001,HCNT022001,NCNT022001,HANDLERATE022001,CNT023001,HCNT023001,NCNT023001,HANDLERATE023001,CNT024001,HCNT024001,NCNT024001,HANDLERATE024001,CNT024002,HCNT024002,NCNT024002,HANDLERATE024002," +
					"CNT024003,HCNT024003,NCNT024003,HANDLERATE024003,CNT024004,HCNT024004,NCNT024004,HANDLERATE024004,CNT024005,HCNT024005,NCNT024005,HANDLERATE024005,CNT024006,HCNT024006,NCNT024006,HANDLERATE024006";
			
			String sql = "WITH tab AS(\n" +
			"SELECT t.linkeddepws,t.WSDEPMC,t.xh,SUM(t.cnt) cnt,SUM(t.hcnt) hcnt,\n" + 
			"\n" + 
			"sum(t.cnt) AS ALLCNT,SUM(t.hcnt) AS ALLHCNT,"+
			"sum(DECODE (t.monitoringtype,'021001',t.cnt,0)) AS CNT021001,\n" + 
			"sum(CASE WHEN t.monitoringtype = '021001' then t.hcnt else 0 END) AS HCNT021001,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'021002',t.cnt,0)) AS CNT021002,\n" + 
			"sum(CASE WHEN t.monitoringtype = '021002' then t.hcnt else 0 END) AS HCNT021002,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'021003',t.cnt,0)) AS CNT021003,\n" + 
			"sum(CASE WHEN t.monitoringtype = '021003' then t.hcnt else 0 END) AS HCNT021003,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'021004',t.cnt,0)) AS CNT021004,\n" + 
			"sum(CASE WHEN t.monitoringtype = '021004' then t.hcnt else 0 END) AS HCNT021004,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'021005',t.cnt,0)) AS CNT021005,\n" + 
			"sum(CASE WHEN t.monitoringtype = '021005' then t.hcnt else 0 END) AS HCNT021005,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'022001',t.cnt,0)) AS CNT022001,\n" + 
			"sum(CASE WHEN t.monitoringtype = '022001' then t.hcnt else 0 END) AS HCNT022001,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'023001',t.cnt,0)) AS CNT023001,\n" + 
			"sum(CASE WHEN t.monitoringtype = '023001' then t.hcnt else 0 END) AS HCNT023001,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'024001',t.cnt,0)) AS CNT024001,\n" + 
			"sum(CASE WHEN t.monitoringtype = '024001' then t.hcnt else 0 END) AS HCNT024001,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'024002',t.cnt,0)) AS CNT024002,\n" + 
			"sum(CASE WHEN t.monitoringtype = '024002' then t.hcnt else 0 END) AS HCNT024002,\n" + 
			"\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'024003',t.cnt,0)) AS CNT024003,\n" + 
			"sum(CASE WHEN t.monitoringtype = '024003' then t.hcnt else 0 END) AS HCNT024003,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'024004',t.cnt,0)) AS CNT024004,\n" + 
			"sum(CASE WHEN t.monitoringtype = '024004' then t.hcnt else 0 END) AS HCNT024004,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'024005',t.cnt,0)) AS CNT024005,\n" + 
			"sum(CASE WHEN t.monitoringtype = '024005' then t.hcnt else 0 END) AS HCNT024005,\n" + 
			"\n" + 
			"sum(DECODE (t.monitoringtype,'024006',t.cnt,0)) AS CNT024006,\n" + 
			"sum(CASE WHEN t.monitoringtype = '024006' then t.hcnt else 0 END) AS HCNT024006\n" + 
			"FROM ";
			//if((null != bdz && StringUtils.isNotBlank(bdz)) || null != gjsj || null != gjjb){
				sql+="(SELECT TB.LINKEDDEPWS,TB.WSDEPMC,TB.XH,\n" +
					"       TB.DEVICEVOLTAGEBZDM,TB.MONITORINGTYPE,TB.alarmlevelmc,TB.LINKEDSTATIONNAME,\n" + 
					"       COUNT(*) CNT,sum(DECODE(TB.ishandled,'T',1,0)) HCNT,\n" + 
					"       MIN(to_char(TB.ALARMTIME,'yyyy-mm-dd')) alarmTime,TB.ALARMNUM,\n" + 
					"       SYSDATE statTime,'T' flag\n" + 
					"   FROM MW_APP.CMSV_ALARM_BDINFOF TB\n" + 
					"   WHERE TB.WSDEPMC IS NOT NULL\n" + 
					"   AND TB.ALARMSOURCE = 'E' AND TB.MONITORINGTYPE LIKE '02%' AND TB.ALARMLEVEL IS NOT NULL\n" + 
					"   GROUP BY TB.LINKEDDEPWS,TB.WSDEPMC,TB.XH,TB.DEVICEVOLTAGEBZDM,TB.MONITORINGTYPE,TB.alarmlevelmc,TB.LINKEDSTATIONNAME,TB.ALARMNUM,to_char(TB.ALARMTIME,'yyyy-mm-dd'))";

			//}
			//此处有疑问   ---2017年3月6日
			/*else{
				sql+=" MW_APP.CMST_ALARMSTATISTIC_KQ ";
			}*/
			sql+=" T,\n" + 
			"       MW_APP.CMST_SB_PZ_SBDYDJ DY,\n" + 
			"       MW_APP.CMST_MONITORINGTYPE MT\n" + 
			" WHERE T.DEVICEVOLTAGEBZDM = DY.DYDJBM AND MT.TYPECODE = T.MONITORINGTYPE AND t.flag = 'T'\n";
		
			if(null != querySql && "" != querySql.toString()){
				sql+=querySql.toString();
			}
			
			sql+="GROUP BY t.linkeddepws,t.WSDEPMC,t.xh ORDER BY t.xh)\n" + 
			"(\n" + 
			"select d.wsid obj_id,d.wsmc wsdepmc,d.zdypx xh,NVL(cnt,0) cnt,\n" + 
			"NVL(ALLCNT,0)ALLCNT,NVL(ALLHCNT,0)ALLHCNT,(NVL(ALLCNT,0)-NVL(ALLHCNT,0)) ALLNCNT,DECODE(NVL(ALLCNT,0),0,'-',to_char(ROUND(ALLHCNT/ALLCNT*100,2),'fm9999999990.00')||'%') ALLHANDLERATE,"+
			"NVL(CNT021001,0)CNT021001,NVL(HCNT021001,0)HCNT021001,(NVL(CNT021001,0)-NVL(HCNT021001,0)) NCNT021001,DECODE(NVL(CNT021001,0),0,'-',to_char(ROUND(HCNT021001/CNT021001*100,2),'fm9999999990.00')||'%') HANDLERATE021001,\n" + 
			"NVL(CNT021002,0)CNT021002,NVL(HCNT021002,0)HCNT021002,(NVL(CNT021002,0)-NVL(HCNT021002,0)) NCNT021002,DECODE(NVL(CNT021002,0),0,'-',to_char(ROUND(HCNT021002/CNT021002*100,2),'fm9999999990.00')||'%') HANDLERATE021002,\n" + 
			"NVL(CNT021003,0)CNT021003,NVL(HCNT021003,0)HCNT021003,(NVL(CNT021003,0)-NVL(HCNT021003,0)) NCNT021003,DECODE(NVL(CNT021003,0),0,'-',to_char(ROUND(HCNT021003/CNT021003*100,2),'fm9999999990.00')||'%') HANDLERATE021003,\n" + 
			"NVL(CNT021004,0)CNT021004,NVL(HCNT021004,0)HCNT021004,(NVL(CNT021004,0)-NVL(HCNT021004,0)) NCNT021004,DECODE(NVL(CNT021004,0),0,'-',to_char(ROUND(HCNT021004/CNT021004*100,2),'fm9999999990.00')||'%') HANDLERATE021004,\n" + 
			"NVL(CNT021005,0)CNT021005,NVL(HCNT021005,0)HCNT021005,(NVL(CNT021005,0)-NVL(HCNT021005,0)) NCNT021005,DECODE(NVL(CNT021005,0),0,'-',to_char(ROUND(HCNT021005/CNT021005*100,2),'fm9999999990.00')||'%') HANDLERATE021005,\n" + 
			"NVL(CNT022001,0)CNT022001,NVL(HCNT022001,0)HCNT022001,(NVL(CNT022001,0)-NVL(HCNT022001,0)) NCNT022001,DECODE(NVL(CNT022001,0),0,'-',to_char(ROUND(HCNT022001/CNT022001*100,2),'fm9999999990.00')||'%') HANDLERATE022001,\n" + 
			"NVL(CNT023001,0)CNT023001,NVL(HCNT023001,0)HCNT023001,(NVL(CNT023001,0)-NVL(HCNT023001,0)) NCNT023001,DECODE(NVL(CNT023001,0),0,'-',to_char(ROUND(HCNT023001/CNT023001*100,2),'fm9999999990.00')||'%') HANDLERATE023001,\n" + 
			"NVL(CNT024001,0)CNT024001,NVL(HCNT024001,0)HCNT024001,(NVL(CNT024001,0)-NVL(HCNT024001,0)) NCNT024001,DECODE(NVL(CNT024001,0),0,'-',to_char(ROUND(HCNT024001/CNT024001*100,2),'fm9999999990.00')||'%') HANDLERATE024001,\n" + 
			"NVL(CNT024002,0)CNT024002,NVL(HCNT024002,0)HCNT024002,(NVL(CNT024002,0)-NVL(HCNT024002,0)) NCNT024002,DECODE(NVL(CNT024002,0),0,'-',to_char(ROUND(HCNT024002/CNT024002*100,2),'fm9999999990.00')||'%') HANDLERATE024002,\n" + 
			"\n" + 
			"NVL(CNT024003,0)CNT024003,NVL(HCNT024003,0)HCNT024003,(NVL(CNT024003,0)-NVL(HCNT024003,0)) NCNT024003,DECODE(NVL(CNT024003,0),0,'-',to_char(ROUND(HCNT024003/CNT024003*100,2),'fm9999999990.00')||'%') HANDLERATE024003,\n" + 
			"NVL(CNT024004,0)CNT024004,NVL(HCNT024004,0)HCNT024004,(NVL(CNT024004,0)-NVL(HCNT024004,0)) NCNT024004,DECODE(NVL(CNT024004,0),0,'-',to_char(ROUND(HCNT024004/CNT024004*100,2),'fm9999999990.00')||'%') HANDLERATE024004,\n" + 
			"NVL(CNT024005,0)CNT024005,NVL(HCNT024005,0)HCNT024005,(NVL(CNT024005,0)-NVL(HCNT024005,0)) NCNT024005,DECODE(NVL(CNT024005,0),0,'-',to_char(ROUND(HCNT024005/CNT024005*100,2),'fm9999999990.00')||'%') HANDLERATE024005,\n" + 
			"NVL(CNT024006,0)CNT024006,NVL(HCNT024006,0)HCNT024006,(NVL(CNT024006,0)-NVL(HCNT024006,0)) NCNT024006,DECODE(NVL(CNT024006,0),0,'-',to_char(ROUND(HCNT024006/CNT024006*100,2),'fm9999999990.00')||'%') HANDLERATE024006\n" + 
			" FROM tab,mw_app.CMST_ZB_COMM_WSPZ d WHERE d.wsid = tab.linkeddepws(+) \n";
			
			if(null != ssdw && StringUtils.isNotBlank(ssdw)){
				sql+= " and obj_id IN('"+ssdw.replace(",","','")+"')";
			}
			
			sql+="  AND d.wsmc not like '%运行%' " + 
			"\n"+			
			"UNION ALL\n" + 
				"\n" + 
				"(select '国家电网公司','国家电网公司',0,NVL(SUM(cnt),0),\n" + 
				"NVL(SUM(ALLCNT),0),NVL(SUM(ALLHCNT),0),(NVL(SUM(ALLCNT),0)-NVL(SUM(ALLHCNT),0)),DECODE(NVL(SUM(ALLHCNT),0),0,'-',to_char(ROUND(SUM(ALLHCNT)/SUM(ALLCNT)*100,2),'fm9999999990.00')||'%'),"+
				"NVL(SUM(CNT021001),0),NVL(SUM(HCNT021001),0),(NVL(SUM(CNT021001),0)-NVL(SUM(HCNT021001),0)),DECODE(NVL(SUM(HCNT021001),0),0,'-',to_char(ROUND(SUM(HCNT021001)/SUM(CNT021001)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT021002),0),NVL(SUM(HCNT021002),0),(NVL(SUM(CNT021002),0)-NVL(SUM(HCNT021002),0)),DECODE(NVL(SUM(HCNT021002),0),0,'-',to_char(ROUND(SUM(HCNT021002)/SUM(CNT021002)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT021003),0),NVL(SUM(HCNT021003),0),(NVL(SUM(CNT021003),0)-NVL(SUM(HCNT021003),0)),DECODE(NVL(SUM(HCNT021003),0),0,'-',to_char(ROUND(SUM(HCNT021003)/SUM(CNT021003)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT021004),0),NVL(SUM(HCNT021004),0),(NVL(SUM(CNT021004),0)-NVL(SUM(HCNT021004),0)),DECODE(NVL(SUM(HCNT021004),0),0,'-',to_char(ROUND(SUM(HCNT021004)/SUM(CNT021004)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT021005),0),NVL(SUM(HCNT021005),0),(NVL(SUM(CNT021005),0)-NVL(SUM(HCNT021005),0)),DECODE(NVL(SUM(HCNT021005),0),0,'-',to_char(ROUND(SUM(HCNT021005)/SUM(CNT021005)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT022001),0),NVL(SUM(HCNT022001),0),(NVL(SUM(CNT022001),0)-NVL(SUM(HCNT022001),0)),DECODE(NVL(SUM(HCNT022001),0),'-',to_char(ROUND(SUM(HCNT022001)/SUM(CNT022001)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT023001),0),NVL(SUM(HCNT023001),0),(NVL(SUM(CNT023001),0)-NVL(SUM(HCNT023001),0)),DECODE(NVL(SUM(HCNT023001),0),0,'-',to_char(ROUND(SUM(HCNT023001)/SUM(CNT023001)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT024001),0),NVL(SUM(HCNT024001),0),(NVL(SUM(CNT024001),0)-NVL(SUM(HCNT024001),0)),DECODE(NVL(SUM(HCNT024001),0),0,'-',to_char(ROUND(SUM(HCNT024001)/SUM(CNT024001)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT024002),0),NVL(SUM(HCNT024002),0),(NVL(SUM(CNT024002),0)-NVL(SUM(HCNT024002),0)),DECODE(NVL(SUM(HCNT024002),0),0,'-',to_char(ROUND(SUM(HCNT024002)/SUM(CNT024002)*100,2),'fm9999999990.00')||'%'),\n" + 
				"\n" + 
				"NVL(SUM(CNT024003),0),NVL(SUM(HCNT024003),0),(NVL(SUM(CNT024003),0)-NVL(SUM(HCNT024003),0)),DECODE(NVL(SUM(HCNT024003),0),0,'-',to_char(ROUND(SUM(HCNT024003)/SUM(CNT024003)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT024004),0),NVL(SUM(HCNT024004),0),(NVL(SUM(CNT024004),0)-NVL(SUM(HCNT024004),0)),DECODE(NVL(SUM(HCNT024004),0),0,'-',to_char(ROUND(SUM(HCNT024004)/SUM(CNT024004)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT024005),0),NVL(SUM(HCNT024005),0),(NVL(SUM(CNT024005),0)-NVL(SUM(HCNT024005),0)),DECODE(NVL(SUM(HCNT024005),0),0,'-',to_char(ROUND(SUM(HCNT024005)/SUM(CNT024005)*100,2),'fm9999999990.00')||'%'),\n" + 
				"NVL(SUM(CNT024006),0),NVL(SUM(HCNT024006),0),(NVL(SUM(CNT024006),0)-NVL(SUM(HCNT024006),0)),DECODE(NVL(SUM(HCNT024006),0),0,'-',to_char(ROUND(SUM(HCNT024006)/SUM(CNT024006)*100,2),'fm9999999990.00')||'%')\n" + 
				"FROM tab)"+
			")ORDER BY xh";
					
			System.out.println(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			   
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			log.info("执行按监测类型统计输电告警信息时出错！", e);
			e.printStackTrace();
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
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		
		String cols = "OBJ_ID,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,TYPENAME,WSDEPMC,XTMC,DEVICEVOLTAGE,ALARMMESSAGE,ALARMLEVEL,ALARMTIME";
		
		StringBuffer querySql = new StringBuffer();
		
		String sql = "select CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,T.LINKEDSTATIONNAME,t.LINKEDEQUIPMENTNAME,t.TYPENAME,t.WSDEPMC,t.XTMC,t.DEVICEVOLTAGE," +
				"t.ALARMMESSAGE,t.ALARMLEVEL,TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME"
		+" from mw_app.CMSV_ALARM_BDINFOF t where ALARMNUM IS NOT NULL AND WSDEPMC IS NOT NULL";
		
		querySql.append(sql);
		
		if(null != queryCondition.getFilter()){
			String[] filter = queryCondition.getFilter().toString().trim().split("&");	//对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				String filterKey = filter[i].split("=")[0].trim();			//变量名称
				String filterValue = filter[i].split("=")[1].trim();		//变量值
				if(Constants.SSDW.equals(filterKey)){
					querySql.append(" AND LINKEDDEPWS IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DYDJ.equals(filterKey)){
					querySql.append(" AND DEVICEVOLTAGEDM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.JCLX.equals(filterKey)){
					querySql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.BDZ.equals(filterKey)){
					if(null != filterValue && StringUtils.isNotBlank(filterValue)){
						querySql.append(" AND LINKEDSTATIONNAME LIKE '%"+filterValue.trim()+"%'");
					}
				}else if(Constants.ISHANDLED.equals(filterKey)){
					if(Constants.ALL.equals(filterValue)){
						
					}else if(Constants.T.equals(filterValue)){
						querySql.append(" AND ISHANDLED = 'T' ");
					}else{
						querySql.append(" AND (ISHANDLED = '' OR ISHANDLED = 'F' OR ISHANDLED IS NULL OR ISHANDLED = 'N')");
					}
					
				}else if(Constants.GJJB.equals(filterKey)){
					querySql.append("AND ALARMNUM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.GJSJ.equals(filterKey)){
					String startTime = null;
					String endTime = null;
					
					if(",".equals(filterValue.substring(0, 1))){			//如果告警开始时间为空
						endTime = filterValue.split(",")[1];
						querySql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') <= '");
						querySql.append(endTime);
						querySql.append("'");
					}else if(",".equals(filterValue.substring(filterValue.length()-1, filterValue.length()))){		//如果告警结束时间为空
						startTime = filterValue.split(",")[0];
						querySql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') >= '");
						querySql.append(startTime);
						querySql.append("'");
					}else{					//如果告警开始时间和结束时间都不为空
						startTime = filterValue.split(",")[0];
						endTime = filterValue.split(",")[1];
						querySql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') BETWEEN '");
						querySql.append(startTime);
						querySql.append("' AND '");
						querySql.append(endTime);
						querySql.append("'");
					}
				}
				
			}
		}
		
		querySql.append(" AND  monitoringtype like '02%' and ALARMSOURCE = 'E' ORDER BY ALARMTIME DESC");
		
		try{
			result = hibernateDao_ztjc.executeSqlQuery(querySql.toString(),pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql.toString() + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行变电告警信息查询统计的初始化DataGrid的查询SQL中发生异常",e);
		}
		
		return qro;
	}
}

