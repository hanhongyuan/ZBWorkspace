package com.sgcc.pms.zbztjc.kqxt.sdgjxxcxtj_stat.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.kqxt.Constants;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class Sdgjxxcxtj_statBizc implements ISdgjxxcxtj_statBizc{
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
		String xlmc = null;
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
				}else if(Constants.XLMC.equals(filterKey)){
					xlmc = filterValue;
					querySql.append(" AND LINKEDLINENAME LIKE '%" + xlmc + "%'");
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
			//if((null != xlmc && StringUtils.isNotBlank(xlmc)) || null != gjsj || null != gjjb){
						sql+="(SELECT T.LINKEDDEPWS,T.WSDEPMC,T.XH,\n" +
						"       T.DEVICEVOLTAGEBZDM,T.MONITORINGTYPE,T.alarmlevelmc,T.LINKEDLINENAME,\n" + 
						"       COUNT(*) CNT,sum(DECODE(t.ishandled,'T',1,0)) HCNT,\n" + 
						"       MIN(to_char(t.ALARMTIME,'yyyy-mm-dd')) alarmTime,T.ALARMNUM,\n" + 
						"       SYSDATE statTime,'L' flag\n" + 
						"  FROM MW_APP.CMSV_ALARM_SDINFOF T\n" + 
						"  WHERE t.WSDEPMC IS NOT NULL\n" + 
						"  AND T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '01%' AND ALARMLEVEL IS NOT NULL\n" + 
						"  GROUP BY T.LINKEDDEPWS,T.WSDEPMC,T.XH,T.DEVICEVOLTAGEBZDM,T.MONITORINGTYPE,T.alarmlevelmc,T.LINKEDLINENAME,T.ALARMNUM,to_char(t.ALARMTIME,'yyyy-mm-dd'))";
					//}else{
					//	sql+="MW_APP.CMST_ALARMSTATISTIC_KQ \n";
					//}
					sql+="T,MW_APP.CMST_SB_PZ_SBDYDJ DY,MW_APP.CMST_MONITORINGTYPE MT\n" + 
					" WHERE T.DEVICEVOLTAGEBZDM = DY.DYDJBM AND MT.TYPECODE = T.MONITORINGTYPE AND t.flag = 'L'\n";
				
					if(null != querySql && "" != querySql.toString()){
						sql+=querySql.toString();
					}
					
					sql+="GROUP BY t.linkeddepws,t.WSDEPMC,t.xh ORDER BY t.xh\n" + 
					")\n" + 
					"\n" + 
					"SELECT * FROM (\n" + 
					"\n" + 
					"SELECT d.wsid obj_id,d.wsmc wsdepmc,d.zdypx xh,NVL(cnt,0) cnt,NVL(thcnt,0) thcnt,\n" + 
					"  NVL(allcnt,0)allcnt,NVL(allhcnt,0)allhcnt,(NVL(allcnt,0)-NVL(allhcnt,0)) allncnt,DECODE(NVL(allcnt,0),0,'-',to_char(ROUND(allhcnt/allcnt*100,2),'fm9999999990.00')||'%') allHANDLERATE," +
					"  NVL(CNT37,0)CNT37,NVL(HCNT37,0)HCNT37,(NVL(CNT37,0)-NVL(HCNT37,0)) NCNT37,DECODE(NVL(CNT37,0),0,'-',to_char(ROUND(HCNT37/CNT37*100,2),'fm9999999990.00')||'%') HANDLERATECNT37,\n" + 
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
					"  FROM tab,mw_app.CMST_ZB_COMM_WSPZ d WHERE d.wsid = tab.linkeddepws(+) \n";// AND d.mc NOT LIKE '%国网运行%'
				
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
					")  where xh <> '52' ORDER BY xh";
					
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
		String dydj = null;
		String jclx = null;
		String xlmc = null;
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
					dydj = filterValue;
					querySql.append(" AND DEVICEVOLTAGEBZDM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.JCLX.equals(filterKey)){
					jclx = filterValue;
					querySql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.XLMC.equals(filterKey)){
					xlmc = filterValue;
					querySql.append(" AND LINKEDLINENAME LIKE '%" + xlmc + "%'");
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
			cols ="OBJ_ID,WSDEPMC,XH,CNT,ALLCNT,ALLHCNT,ALLNCNT,ALLHANDLERATE,CNT012001,HCNT012001,NCNT012001,HANDLERATE012001,CNT013001,HCNT013001,NCNT013001,HANDLERATE013001,CNT013002,HCNT013002,NCNT013002,HANDLERATE013002,CNT013003,HCNT013003,NCNT013003,HANDLERATE013003,CNT013004,HCNT013004,NCNT013004,HANDLERATE013004," +
					"CNT013005,HCNT013005,NCNT013005,HANDLERATE013005,CNT013006,HCNT013006,NCNT013006,HANDLERATE013006,CNT014001,HCNT014001,NCNT014001,HANDLERATE014001,CNT018001,HCNT018001,NCNT018001,HANDLERATE018001";
			
			String sql =  "WITH tab AS(\n" +
					"SELECT t.linkeddepws,t.WSDEPMC,t.xh,SUM(t.cnt) cnt,SUM(t.hcnt) hcnt,\n" + 
					"\n" + 
					"sum(t.cnt) AS ALLCNT,SUM(t.hcnt) AS ALLHCNT,"+
					"sum(DECODE (t.monitoringtype,'012001',t.cnt,0)) AS CNT012001,\n" + 
					"sum(CASE WHEN t.monitoringtype = '012001' then t.hcnt else 0 END) AS HCNT012001,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'013001',t.cnt,0)) AS CNT013001,\n" + 
					"sum(CASE WHEN t.monitoringtype = '013001' then t.hcnt else 0 END) AS HCNT013001,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'013002',t.cnt,0)) AS CNT013002,\n" + 
					"sum(CASE WHEN t.monitoringtype = '013002' then t.hcnt else 0 END) AS HCNT013002,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'013003',t.cnt,0)) AS CNT013003,\n" + 
					"sum(CASE WHEN t.monitoringtype = '013003' then t.hcnt else 0 END) AS HCNT013003,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'013004',t.cnt,0)) AS CNT013004,\n" + 
					"sum(CASE WHEN t.monitoringtype = '013004' then t.hcnt else 0 END) AS HCNT013004,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'013005',t.cnt,0)) AS CNT013005,\n" + 
					"sum(CASE WHEN t.monitoringtype = '013005' then t.hcnt else 0 END) AS HCNT013005,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'013006',t.cnt,0)) AS CNT013006,\n" + 
					"sum(CASE WHEN t.monitoringtype = '013006' then t.hcnt else 0 END) AS HCNT013006,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'014001',t.cnt,0)) AS CNT014001,\n" + 
					"sum(CASE WHEN t.monitoringtype = '014001' then t.hcnt else 0 END) AS HCNT014001,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'018001',t.cnt,0)) AS CNT018001,\n" + 
					"sum(CASE WHEN t.monitoringtype = '018001' then t.hcnt else 0 END) AS HCNT018001\n" + 
					"FROM \n"; 
					//if($("__lineFilter").value != 0 && $("__lineFilter").value != "" && $("__lineFilter").value !="--请输入名称--"||__sele_tyrq.checked == true||$("__alarmLvFilter").idValue != -1){
						sql+="(SELECT T.LINKEDDEPWS,T.WSDEPMC,T.XH,\n" +
						"       T.DEVICEVOLTAGEBZDM,T.MONITORINGTYPE,T.alarmlevelmc,T.LINKEDLINENAME,\n" + 
						"       COUNT(*) CNT,sum(DECODE(t.ishandled,'T',1,0)) HCNT,\n" + 
						"       MIN(to_char(t.ALARMTIME,'yyyy-mm-dd')) alarmTime,T.ALARMNUM,\n" + 
						"       SYSDATE statTime,'L' flag\n" + 
						"  FROM MW_APP.CMSV_ALARM_SDINFOF T\n" + 
						"  WHERE t.WSDEPMC IS NOT NULL\n" + 
						"  AND T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '01%' AND ALARMLEVEL IS NOT NULL\n" + 
						"  GROUP BY T.LINKEDDEPWS,T.WSDEPMC,T.XH,T.DEVICEVOLTAGEBZDM,T.MONITORINGTYPE,T.alarmlevelmc,T.LINKEDLINENAME,T.ALARMNUM,to_char(t.ALARMTIME,'yyyy-mm-dd'))";
					//}else{
						//sql+="MW_APP.CMST_ALARMSTATISTIC_KQ \n";
					//}
					sql+=" T,\n" + 
					"       MW_APP.CMST_SB_PZ_SBDYDJ DY,\n" + 
					"       MW_APP.CMST_MONITORINGTYPE MT\n" + 
					" WHERE T.DEVICEVOLTAGEBZDM = DY.DYDJBM AND MT.TYPECODE = T.MONITORINGTYPE AND t.flag = 'L'\n";	
					
					if(null != querySql && "" != querySql.toString()){
						sql+=querySql.toString();
					}
					
					sql +="GROUP BY t.linkeddepws,t.WSDEPMC,t.xh ORDER BY t.xh)\n" + 
					"\n" + 
					"SELECT * FROM (\n" + 
					"select d.wsid obj_id,d.wsmc wsdepmc,d.zdypx xh,NVL(cnt,0) cnt,\n" + 
					"NVL(ALLCNT,0)ALLCNT,NVL(ALLHCNT,0)ALLHCNT,(NVL(ALLCNT,0)-NVL(ALLHCNT,0)) ALLNCNT,DECODE(NVL(ALLCNT,0),0,'-',to_char(ROUND(ALLHCNT/ALLCNT*100,2),'fm9999999990.00')||'%') ALLHANDLERATE,"+
					"NVL(CNT012001,0)CNT012001,NVL(HCNT012001,0)HCNT012001,(NVL(CNT012001,0)-NVL(HCNT012001,0)) NCNT012001,DECODE(NVL(CNT012001,0),0,'-',to_char(ROUND(HCNT012001/CNT012001*100,2),'fm9999999990.00')||'%') HANDLERATE012001,\n" + 
					"NVL(CNT013001,0)CNT013001,NVL(HCNT013001,0)HCNT013001,(NVL(CNT013001,0)-NVL(HCNT013001,0)) NCNT013001,DECODE(NVL(CNT013001,0),0,'-',to_char(ROUND(HCNT013001/CNT013001*100,2),'fm9999999990.00')||'%') HANDLERATE013001,\n" + 
					"NVL(CNT013002,0)CNT013002,NVL(HCNT013002,0)HCNT013002,(NVL(CNT013002,0)-NVL(HCNT013002,0)) NCNT013002,DECODE(NVL(CNT013002,0),0,'-',to_char(ROUND(HCNT013002/CNT013002*100,2),'fm9999999990.00')||'%') HANDLERATE013002,\n" + 
					"NVL(CNT013003,0)CNT013003,NVL(HCNT013003,0)HCNT013003,(NVL(CNT013003,0)-NVL(HCNT013003,0)) NCNT013003,DECODE(NVL(CNT013003,0),0,'-',to_char(ROUND(HCNT013003/CNT013003*100,2),'fm9999999990.00')||'%') HANDLERATE013003,\n" + 
					"NVL(CNT013004,0)CNT013004,NVL(HCNT013004,0)HCNT013004,(NVL(CNT013004,0)-NVL(HCNT013004,0)) NCNT013004,DECODE(NVL(CNT013004,0),0,'-',to_char(ROUND(HCNT013004/CNT013004*100,2),'fm9999999990.00')||'%') HANDLERATE013004,\n" + 
					"NVL(CNT013005,0)CNT013005,NVL(HCNT013005,0)HCNT013005,(NVL(CNT013005,0)-NVL(HCNT013005,0)) NCNT013005,DECODE(NVL(CNT013005,0),0,'-',to_char(ROUND(HCNT013005/CNT013005*100,2),'fm9999999990.00')||'%') HANDLERATE013005,\n" + 
					"NVL(CNT013006,0)CNT013006,NVL(HCNT013006,0)HCNT013006,(NVL(CNT013006,0)-NVL(HCNT013006,0)) NCNT013006,DECODE(NVL(CNT013006,0),0,'-',to_char(ROUND(HCNT013006/CNT013006*100,2),'fm9999999990.00')||'%') HANDLERATE013006,\n" + 
					"NVL(CNT014001,0)CNT014001,NVL(HCNT014001,0)HCNT014001,(NVL(CNT014001,0)-NVL(HCNT014001,0)) NCNT014001,DECODE(NVL(CNT014001,0),0,'-',to_char(ROUND(HCNT014001/CNT014001*100,2),'fm9999999990.00')||'%') HANDLERATE014001,\n" + 
					"NVL(CNT018001,0)CNT018001,NVL(HCNT018001,0)HCNT018001,(NVL(CNT018001,0)-NVL(HCNT018001,0)) NCNT018001,DECODE(NVL(CNT018001,0),0,'-',to_char(ROUND(HCNT018001/CNT018001*100,2),'fm9999999990.00')||'%') HANDLERATE018001 " +
					"FROM tab,mw_app.CMST_ZB_COMM_WSPZ d WHERE d.wsid = tab.linkeddepws(+)\n";// AND d.mc NOT LIKE '%国网运行%'
				
					if(null != ssdw && StringUtils.isNotBlank(ssdw)){
						sql+= " and obj_id IN('"+ssdw.replace(",","','")+"')";
					}
					
					sql +="  AND d.wsmc not like '%运行%' " + 
					"\n"+
					"UNION ALL\n" + 
						"\n" + 
						"(select '国家电网公司','国家电网公司',0,NVL(SUM(cnt),0),\n" + 
						"NVL(SUM(ALLCNT),0),NVL(SUM(ALLHCNT),0),(NVL(SUM(ALLCNT),0)-NVL(SUM(ALLHCNT),0)),DECODE(NVL(SUM(ALLHCNT),0),0,'-',to_char(ROUND(SUM(ALLHCNT)/SUM(ALLCNT)*100,2),'fm9999999990.00')||'%'),"+
						"NVL(SUM(CNT012001),0),NVL(SUM(HCNT012001),0),(NVL(SUM(CNT012001),0)-NVL(SUM(HCNT012001),0)),DECODE(NVL(SUM(HCNT012001),0),0,'-',to_char(ROUND(SUM(HCNT012001)/SUM(CNT012001)*100,2),'fm9999999990.00')||'%'),\n" + 
						"NVL(SUM(CNT013001),0),NVL(SUM(HCNT013001),0),(NVL(SUM(CNT013001),0)-NVL(SUM(HCNT013001),0)),DECODE(NVL(SUM(HCNT013001),0),0,'-',to_char(ROUND(SUM(HCNT013001)/SUM(CNT013001)*100,2),'fm9999999990.00')||'%'),\n" + 
						"NVL(SUM(CNT013002),0),NVL(SUM(HCNT013002),0),(NVL(SUM(CNT013002),0)-NVL(SUM(HCNT013002),0)),DECODE(NVL(SUM(HCNT013002),0),0,'-',to_char(ROUND(SUM(HCNT013002)/SUM(CNT013002)*100,2),'fm9999999990.00')||'%'),\n" + 
						"NVL(SUM(CNT013003),0),NVL(SUM(HCNT013003),0),(NVL(SUM(CNT013003),0)-NVL(SUM(HCNT013003),0)),DECODE(NVL(SUM(HCNT013003),0),0,'-',to_char(ROUND(SUM(HCNT013003)/SUM(CNT013003)*100,2),'fm9999999990.00')||'%'),\n" + 
						"NVL(SUM(CNT013004),0),NVL(SUM(HCNT013004),0),(NVL(SUM(CNT013004),0)-NVL(SUM(HCNT013004),0)),DECODE(NVL(SUM(HCNT013004),0),0,'-',to_char(ROUND(SUM(HCNT013004)/SUM(CNT013004)*100,2),'fm9999999990.00')||'%'),\n" + 
						"NVL(SUM(CNT013005),0),NVL(SUM(HCNT013005),0),(NVL(SUM(CNT013005),0)-NVL(SUM(HCNT013005),0)),DECODE(NVL(SUM(HCNT013005),0),0,'-',to_char(ROUND(SUM(HCNT013005)/SUM(CNT013005)*100,2),'fm9999999990.00')||'%'),\n" + 
						"NVL(SUM(CNT013006),0),NVL(SUM(HCNT013006),0),(NVL(SUM(CNT013006),0)-NVL(SUM(HCNT013006),0)),DECODE(NVL(SUM(HCNT013006),0),0,'-',to_char(ROUND(SUM(HCNT013006)/SUM(CNT013006)*100,2),'fm9999999990.00')||'%'),\n" + 
						"NVL(SUM(CNT014001),0),NVL(SUM(HCNT014001),0),(NVL(SUM(CNT014001),0)-NVL(SUM(HCNT014001),0)),DECODE(NVL(SUM(HCNT014001),0),0,'-',to_char(ROUND(SUM(HCNT014001)/SUM(CNT014001)*100,2),'fm9999999990.00')||'%'),\n" + 
						"NVL(SUM(CNT018001),0),NVL(SUM(HCNT018001),0),(NVL(SUM(CNT018001),0)-NVL(SUM(HCNT018001),0)),DECODE(NVL(SUM(HCNT018001),0),0,'-',to_char(ROUND(SUM(HCNT018001)/SUM(CNT018001)*100,2),'fm9999999990.00')||'%')\n" + 
						"FROM tab)"+
					")  where xh <> '52' ORDER BY xh";
					
			log.info(sql);
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
		
		String cols = "OBJ_ID,LINKEDLINENAME,LINKEDPOLENAME,TYPENAME,WSDEPMC,XTMC,DEVICEVOLTAGE,ALARMMESSAGE,ALARMLEVEL,ALARMTIME";
		
		StringBuffer querySql = new StringBuffer();
		
		String sql = "select CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,T.LINKEDLINENAME,t.LINKEDPOLENAME,t.TYPENAME,t.WSDEPMC,t.XTMC," +
				"t.DEVICEVOLTAGE,t.ALARMMESSAGE,t.ALARMLEVEL,TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME\n" +
		" FROM mw_app.CMSV_ALARM_SDINFOF t where T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '01%' AND ALARMNUM IS NOT NULL ";
		
		querySql.append(sql);
		
		if(null != queryCondition.getFilter()){
			System.out.println(queryCondition.getFilter());
			String[] filter = queryCondition.getFilter().toString().trim().split("&");	//对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				String filterKey = filter[i].split("=")[0].trim();			//变量名称
				String filterValue = filter[i].split("=")[1].trim();		//变量值
				if(Constants.SSDW.equals(filterKey)){
					querySql.append(" AND LINKEDDEPWS IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DYDJ.equals(filterKey)){
					querySql.append(" AND DEVICEVOLTAGEBZDM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.JCLX.equals(filterKey)){
					querySql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.XLMC.equals(filterKey)){
					if(null != filterValue && StringUtils.isNotBlank(filterValue)){
						querySql.append(" AND LINKEDLINENAME LIKE '%"+filterValue.trim()+"%'");
					}
				}else if(Constants.ISHANDLED.equalsIgnoreCase(filterKey)){
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
		
		querySql.append(" AND ALARMLEVEL IS NOT NULL AND WSDEPMC IS NOT NULL  ORDER BY ALARMTIME DESC");
		
		try{
			result = hibernateDao_ztjc.executeSqlQuery(querySql.toString(),pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql.toString() + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行输电告警信息查询统计的初始化DataGrid的查询SQL中发生异常",e);
		}
		
		return qro;
	}

}

