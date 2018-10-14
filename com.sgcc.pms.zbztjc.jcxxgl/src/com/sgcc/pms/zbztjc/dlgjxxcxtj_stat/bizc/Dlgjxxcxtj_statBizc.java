package com.sgcc.pms.zbztjc.dlgjxxcxtj_stat.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.Constants;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class Dlgjxxcxtj_statBizc implements IDlgjxxcxtj_statBizc{

	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(Dlgjxxcxtj_statBizc.class);
	
	@Override
	public QueryResultObject statByDydj(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		
		String ssdw = null;
		String dlmc = null;
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
					querySql.append(" AND LINKEDDEPWS IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DYDJ.equals(filterKey)){
					querySql.append(" AND VOLTAGEGRADE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.JCLX.equals(filterKey)){
					querySql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DLMC.equals(filterKey)){
					if(null != filterValue && StringUtils.isNotBlank(filterValue)){
						dlmc = filterValue;
						querySql.append(" and LINKEDCABLEANDCHANNELNAME LIKE '%" + filterValue + "%'");
					}
				}else if(Constants.GJJB.equals(filterKey)){
					gjjb = filterValue;
					querySql.append(" AND ALARMNUM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.ISHANDLED.equals(filterKey)){
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
					"SUM(CASE WHEN t.VOLTAGEGRADE IN('37','36','35','34','33','32','30','25','80','81','82','83','84','85','86') then t.cnt else 0 END) AS allCNT,"+
					"SUM(CASE WHEN t.VOLTAGEGRADE IN('37','36','35','34','33','32','30','25','80','81','82','83','84','85','86') then t.hcnt else 0 END) AS allHCNT,"+
					"sum(DECODE (t.VOLTAGEGRADE,'37',t.cnt,0)) AS aCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '37' then t.hcnt else 0 END) AS aHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'36',t.cnt,0)) AS bCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '36' then t.hcnt else 0 END) AS bHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'35',t.cnt,0)) AS cCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '35' then t.hcnt else 0 END) AS cHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'34',t.cnt,0)) AS dCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '34' then t.hcnt else 0 END) AS dHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'33',t.cnt,0)) AS eCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '33' then t.hcnt else 0 END) AS eHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'32',t.cnt,0)) AS fCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '32' then t.hcnt else 0 END) AS fHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'30',t.cnt,0)) AS gCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '30' then t.hcnt else 0 END) AS gHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'25',t.cnt,0)) AS hCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '25' then t.hcnt else 0 END) AS hHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'86',t.cnt,0)) AS iCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '86' then t.hcnt else 0 END) AS iHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'85',t.cnt,0)) AS jCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '85' then t.hcnt else 0 END) AS jHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'84',t.cnt,0)) AS kCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '84' then t.hcnt else 0 END) AS kHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'83',t.cnt,0)) AS lCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '83' then t.hcnt else 0 END) AS lHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'82',t.cnt,0)) AS mCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '82' then t.hcnt else 0 END) AS mHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'81',1,0)) AS nCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '81' then t.hcnt else 0 END) AS nHCNT,\n" + 
					"\n" + 
					"sum(DECODE (t.VOLTAGEGRADE,'80',t.cnt,0)) AS oCNT,\n" + 
					"sum(CASE WHEN t.VOLTAGEGRADE = '80' then t.hcnt else 0 END) AS oHCNT \n" + 
					"FROM \n";
					sql+="(SELECT T.LINKEDDEPWS,T.WSDEPMC,T.XH,\n" +
						" T.VOLTAGEGRADE,T.MONITORINGTYPE,T.LINKEDCABLEANDCHANNELNAME,\n" + 
						" COUNT(*) CNT,sum(DECODE(t.ishandled,'T',1,0)) HCNT,\n" + 
						" T.ALARMTIME,T.ALARMLEVEL,T.ALARMNUM,\n" + 
						" SYSDATE statTime,'T' flag\n" + 
						" FROM MW_APP.cmsv_alarm_cableinfo_xtf T\n" + 
						" WHERE t.WSDEPMC IS NOT NULL\n" + 
						" AND T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '03%' AND ALARMLEVEL IS NOT NULL AND t.WSDEPMC NOT LIKE '%分部%'\n" + 
						" GROUP BY T.LINKEDDEPWS,T.WSDEPMC,T.XH,t.VOLTAGEGRADE,T.MONITORINGTYPE,T.LINKEDCABLEANDCHANNELNAME,T.ALARMTIME,T.ALARMLEVEL,T.ALARMNUM,to_char(t.ALARMTIME,'yyyy-mm-dd'))";
				
					sql+=" T,MW_APP.CMST_SB_PZ_SBDYDJ DY,MW_APP.CMST_MONITORINGTYPE MT\n" + 
							" WHERE T.VOLTAGEGRADE = DY.DYDJBM AND MT.TYPECODE = T.MONITORINGTYPE AND t.flag = 'T'\n";
					
					if(null != querySql && "" != querySql.toString()){
						sql+=querySql.toString();
					}
					
					sql+=" GROUP BY t.linkeddepws,t.WSDEPMC,t.xh ORDER BY t.xh\n" + 
							")\n" + 
							"\n" + 
							"SELECT * FROM (\n" + 
							"\n" + 
							"SELECT d.wsid obj_id,d.wsmc wsdepmc,d.zdypx xh,NVL(cnt,0) cnt,NVL(thcnt,0) thcnt,\n" + 
							"  NVL(allcnt,0)allcnt,NVL(allhcnt,0)allhcnt,(NVL(allcnt,0)-NVL(allhcnt,0)) allncnt,DECODE(NVL(allcnt,0),0,'-',to_char(ROUND(allhcnt/allcnt*100,2),'fm9999999990.00')||'%') allHANDLERATE," +
							"  NVL(acnt,0)acnt,NVL(ahcnt,0)ahcnt,(NVL(acnt,0)-NVL(ahcnt,0)) ancnt,DECODE(NVL(acnt,0),0,'-',to_char(ROUND(ahcnt/acnt*100,2),'fm9999999990.00')||'%') aHANDLERATE,\n" + 
							"  NVL(bcnt,0)bcnt,NVL(bhcnt,0)bhcnt,(NVL(bcnt,0)-NVL(bhcnt,0)) bncnt,DECODE(NVL(bcnt,0),0,'-',to_char(ROUND(bhcnt/bcnt*100,2),'fm9999999990.00')||'%') bHANDLERATE,\n" + 
							"  NVL(ccnt,0)ccnt,NVL(chcnt,0)chcnt,(NVL(ccnt,0)-NVL(chcnt,0)) cncnt,DECODE(NVL(ccnt,0),0,'-',to_char(ROUND(chcnt/ccnt*100,2),'fm9999999990.00')||'%') cHANDLERATE,\n" + 
							"  NVL(dcnt,0)dcnt,NVL(dhcnt,0)dhcnt,(NVL(dcnt,0)-NVL(dhcnt,0)) dncnt,DECODE(NVL(dcnt,0),0,'-',to_char(ROUND(dhcnt/dcnt*100,2),'fm9999999990.00')||'%') dHANDLERATE,\n" + 
							"  NVL(ecnt,0)ecnt,NVL(ehcnt,0)ehcnt,(NVL(ecnt,0)-NVL(ehcnt,0)) encnt,DECODE(NVL(ecnt,0),0,'-',to_char(ROUND(ehcnt/ecnt*100,2),'fm9999999990.00')||'%') eHANDLERATE,\n" + 
							"  NVL(fcnt,0)fcnt,NVL(fhcnt,0)fhcnt,(NVL(fcnt,0)-NVL(fhcnt,0)) fncnt,DECODE(NVL(fcnt,0),0,'-',to_char(ROUND(fhcnt/fcnt*100,2),'fm9999999990.00')||'%') fHANDLERATE,\n" + 
							"  NVL(gcnt,0)gcnt,NVL(ghcnt,0)ghcnt,(NVL(gcnt,0)-NVL(ghcnt,0)) gncnt,DECODE(NVL(gcnt,0),0,'-',to_char(ROUND(ghcnt/gcnt*100,2),'fm9999999990.00')||'%') gHANDLERATE,\n" + 
							"  NVL(hcnt,0)hcnt,NVL(hhcnt,0)hhcnt,(NVL(hcnt,0)-NVL(hhcnt,0)) hncnt,DECODE(NVL(hcnt,0),0,'-',to_char(ROUND(hhcnt/hcnt*100,2),'fm9999999990.00')||'%') hHANDLERATE,\n" + 
							"  NVL(icnt,0)icnt,NVL(ihcnt,0)ihcnt,(NVL(icnt,0)-NVL(ihcnt,0)) incnt,DECODE(NVL(icnt,0),0,'-',to_char(ROUND(ihcnt/icnt*100,2),'fm9999999990.00')||'%') iHANDLERATE,\n" + 
							"  NVL(jcnt,0)jcnt,NVL(jhcnt,0)jhcnt,(NVL(jcnt,0)-NVL(jhcnt,0)) jncnt,DECODE(NVL(jcnt,0),0,'-',to_char(ROUND(jhcnt/jcnt*100,2),'fm9999999990.00')||'%') jHANDLERATE,\n" + 
							"  NVL(kcnt,0)kcnt,NVL(khcnt,0)khcnt,(NVL(kcnt,0)-NVL(khcnt,0)) kncnt,DECODE(NVL(kcnt,0),0,'-',to_char(ROUND(khcnt/kcnt*100,2),'fm9999999990.00')||'%') kHANDLERATE,\n" + 
							"  NVL(lcnt,0)lcnt,NVL(lhcnt,0)lhcnt,(NVL(lcnt,0)-NVL(lhcnt,0)) lncnt,DECODE(NVL(lcnt,0),0,'-',to_char(ROUND(lhcnt/lcnt*100,2),'fm9999999990.00')||'%') lHANDLERATE,\n" + 
							"  NVL(mcnt,0)mcnt,NVL(mhcnt,0)mhcnt,(NVL(mcnt,0)-NVL(mhcnt,0)) mncnt,DECODE(NVL(mcnt,0),0,'-',to_char(ROUND(mhcnt/mcnt*100,2),'fm9999999990.00')||'%') mHANDLERATE,\n" + 
							"  NVL(ncnt,0)ncnt,NVL(nhcnt,0)nhcnt,(NVL(ncnt,0)-NVL(nhcnt,0)) nncnt,DECODE(NVL(ncnt,0),0,'-',to_char(ROUND(nhcnt/ncnt*100,2),'fm9999999990.00')||'%') nHANDLERATE,\n" + 
							"  NVL(ocnt,0)ocnt,NVL(ohcnt,0)ohcnt,(NVL(ocnt,0)-NVL(ohcnt,0)) oncnt,DECODE(NVL(ocnt,0),0,'-',to_char(ROUND(ohcnt/ocnt*100,2),'fm9999999990.00')||'%') oHANDLERATE\n" + 
							"  FROM tab,mw_app.cmst_zb_comm_wspz d WHERE d.wsid = tab.linkeddepws(+) AND d.wsmc NOT LIKE '%分部%'\n";
					
					if(null != ssdw && StringUtils.isNotBlank(ssdw)){
						sql+= " and obj_id IN('"+ssdw.replace(",","','")+"')";
					}
					
					sql+="  AND d.wsmc not like '%运行%' " + 
							"\n"+
							"UNION ALL\n" + 
								"(\n" + 
								"select '国家电网公司','国家电网公司',0,NVL(SUM(CNT),0),NVL(SUM(THCNT),0),\n" + 
								" NVL(SUM(allCNT),0),NVL(SUM(allHCNT),0),(NVL(SUM(allcnt),0)-NVL(SUM(allhcnt),0)),DECODE(NVL(SUM(allHCNT),0),0,'-',to_char(ROUND(SUM(allHCNT)/SUM(allCNT)*100,2),'fm9999999990.00')||'%'),"+
								" NVL(SUM(aCNT),0),NVL(SUM(aHCNT),0),(NVL(SUM(acnt),0)-NVL(SUM(ahcnt),0)),DECODE(NVL(SUM(aHCNT),0),0,'-',to_char(ROUND(SUM(aHCNT)/SUM(aCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(bCNT),0),NVL(SUM(bHCNT),0),(NVL(SUM(bcnt),0)-NVL(SUM(bhcnt),0)),DECODE(NVL(SUM(bHCNT),0),0,'-',to_char(ROUND(SUM(bHCNT)/SUM(bCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(cCNT),0),NVL(SUM(cHCNT),0),(NVL(SUM(ccnt),0)-NVL(SUM(chcnt),0)),DECODE(NVL(SUM(cHCNT),0),0,'-',to_char(ROUND(SUM(cHCNT)/SUM(cCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(dCNT),0),NVL(SUM(dHCNT),0),(NVL(SUM(dcnt),0)-NVL(SUM(dhcnt),0)),DECODE(NVL(SUM(dHCNT),0),0,'-',to_char(ROUND(SUM(dHCNT)/SUM(dCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(eCNT),0),NVL(SUM(eHCNT),0),(NVL(SUM(ecnt),0)-NVL(SUM(ehcnt),0)),DECODE(NVL(SUM(eHCNT),0),0,'-',to_char(ROUND(SUM(eHCNT)/SUM(eCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(fCNT),0),NVL(SUM(fHCNT),0),(NVL(SUM(fcnt),0)-NVL(SUM(fhcnt),0)),DECODE(NVL(SUM(fHCNT),0),0,'-',to_char(ROUND(SUM(fHCNT)/SUM(fCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(gCNT),0),NVL(SUM(gHCNT),0),(NVL(SUM(gcnt),0)-NVL(SUM(ghcnt),0)),DECODE(NVL(SUM(gHCNT),0),0,'-',to_char(ROUND(SUM(gHCNT)/SUM(gCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(hCNT),0),NVL(SUM(hHCNT),0),(NVL(SUM(hcnt),0)-NVL(SUM(hhcnt),0)),DECODE(NVL(SUM(hHCNT),0),0,'-',to_char(ROUND(SUM(hHCNT)/SUM(hCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(iCNT),0),NVL(SUM(iHCNT),0),(NVL(SUM(icnt),0)-NVL(SUM(ihcnt),0)),DECODE(NVL(SUM(iHCNT),0),0,'-',to_char(ROUND(SUM(iHCNT)/SUM(iCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(jCNT),0),NVL(SUM(jHCNT),0),(NVL(SUM(jcnt),0)-NVL(SUM(jhcnt),0)),DECODE(NVL(SUM(jHCNT),0),0,'-',to_char(ROUND(SUM(jHCNT)/SUM(jCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(kCNT),0),NVL(SUM(kHCNT),0),(NVL(SUM(kcnt),0)-NVL(SUM(khcnt),0)),DECODE(NVL(SUM(kHCNT),0),0,'-',to_char(ROUND(SUM(kHCNT)/SUM(kCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(lCNT),0),NVL(SUM(lHCNT),0),(NVL(SUM(lcnt),0)-NVL(SUM(lhcnt),0)),DECODE(NVL(SUM(lHCNT),0),0,'-',to_char(ROUND(SUM(lHCNT)/SUM(lCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(mCNT),0),NVL(SUM(mHCNT),0),(NVL(SUM(mcnt),0)-NVL(SUM(mhcnt),0)),DECODE(NVL(SUM(mHCNT),0),0,'-',to_char(ROUND(SUM(mHCNT)/SUM(mCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(nCNT),0),NVL(SUM(nHCNT),0),(NVL(SUM(ncnt),0)-NVL(SUM(nhcnt),0)),DECODE(NVL(SUM(nHCNT),0),0,'-',to_char(ROUND(SUM(nHCNT)/SUM(nCNT)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(oCNT),0),NVL(SUM(oHCNT),0),(NVL(SUM(ocnt),0)-NVL(SUM(ohcnt),0)),DECODE(NVL(SUM(oHCNT),0),0,'-',to_char(ROUND(SUM(oHCNT)/SUM(oCNT)*100,2),'fm9999999990.00')||'%') \n" + 
								"\n" + 
								"  FROM tab)"+
							")ORDER BY xh";
					
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			   
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			log.info("执行按电压等级统计电缆告警信息时出错！", e);
			e.printStackTrace();
		}
		return null;
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
		String dlmc = null;
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
					querySql.append(" AND LINKEDDEPWS IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DYDJ.equals(filterKey)){
					dydj = filterValue;
					querySql.append(" AND VOLTAGEGRADE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.JCLX.equals(filterKey)){
					jclx = filterValue;
					querySql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DLMC.equals(filterKey)){
					dlmc = filterValue;
					querySql.append(" AND LINKEDCABLEANDCHANNELNAME LIKE '%" + dlmc + "%'");
				}else if(Constants.GJJB.equals(filterKey)){
					gjjb = filterValue;
					querySql.append(" AND ALARMNUM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.ISHANDLED.equals(filterKey)){
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
			cols ="OBJ_ID,WSDEPMC,XH,CNT,ALLCNT,ALLHCNT,ALLNCNT,ALLHANDLERATE,CNT031001,HCNT031001,NCNT031001,HANDLERATE031001,CNT031002,HCNT031002,NCNT031002,HANDLERATE031002,CNT031003,HCNT031003,NCNT031003,HANDLERATE031003," +
					"CNT031004,HCNT031004,NCNT031004,HANDLERATE031004,CNT032001,HCNT032001,NCNT032001,HANDLERATE032001,CNT032002,hCNT032002,NCNT032002,HANDLERATE032002,CNT032003,HCNT032003,NCNT032003,HANDLERATE032003," +
					"CNT032004,HCNT032004,NCNT032004,HANDLERATE032004,CNT032005,HCNT032005,NCNT032005,HANDLERATE032005,CNT032006,HCNT032006,NCNT032006,HANDLERATE032006,CNT032007,HCNT032007,NCNT032007,HANDLERATE032007," +
					"CNT032008,HCNT032008,NCNT032008,HANDLERATE032008,CNT032009,HCNT032009,NCNT032009,HANDLERATE032009,CNT032012,HCNT032012,NCNT032012,HANDLERATE032012,CNT032013,HCNT032013,NCNT032013,HANDLERATE032013," +
					"CNT032014,HCNT032014,NCNT032014,HANDLERATE032014,CNT032015,HCNT032015,NCNT032015,HANDLERATE032015,CNT032016,HCNT032016,NCNT032016,HANDLERATE032016,CNT032017,HCNT032017,NCNT032017,HANDLERATE032017,CNT032018,HCNT032018,NCNT032018,HANDLERATE032018";
			
			String sql = " WITH tab AS(\n" +
					"SELECT t.linkeddepws,t.WSDEPMC,t.xh,SUM(t.cnt) cnt,SUM(t.hcnt) hcnt,\n" + 
					"\n" + 
					"sum(t.cnt) AS ALLCNT,SUM(t.hcnt) AS ALLHCNT,"+
					"sum(DECODE (t.monitoringtype,'031001',t.cnt,0)) AS CNT031001,\n" + 
					"sum(CASE WHEN t.monitoringtype = '031001' then t.hcnt else 0 END) AS HCNT031001,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'031002',t.cnt,0)) AS CNT031002,\n" + 
					"sum(CASE WHEN t.monitoringtype = '031002' then t.hcnt else 0 END) AS HCNT031002,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'031003',t.cnt,0)) AS CNT031003,\n" + 
					"sum(CASE WHEN t.monitoringtype = '031003' then t.hcnt else 0 END) AS HCNT031003,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'031004',t.cnt,0)) AS CNT031004,\n" + 
					"sum(CASE WHEN t.monitoringtype = '031004' then t.hcnt else 0 END) AS HCNT031004,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032001',t.cnt,0)) AS CNT032001,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032001' then t.hcnt else 0 END) AS HCNT032001,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032002',t.cnt,0)) AS CNT032002,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032002' then t.hcnt else 0 END) AS HCNT032002,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032003',t.cnt,0)) AS CNT032003,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032003' then t.hcnt else 0 END) AS HCNT032003,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032004',t.cnt,0)) AS CNT032004,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032004' then t.hcnt else 0 END) AS HCNT032004,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032005',t.cnt,0)) AS CNT032005,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032005' then t.hcnt else 0 END) AS HCNT032005,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032006',t.cnt,0)) AS CNT032006,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032006' then t.hcnt else 0 END) AS HCNT032006,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032007',t.cnt,0)) AS CNT032007,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032007' then t.hcnt else 0 END) AS HCNT032007,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032008',t.cnt,0)) AS CNT032008,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032008' then t.hcnt else 0 END) AS HCNT032008,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032009',t.cnt,0)) AS CNT032009,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032009' then t.hcnt else 0 END) AS HCNT032009,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032012',t.cnt,0)) AS CNT032012,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032012' then t.hcnt else 0 END) AS HCNT032012,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032013',t.cnt,0)) AS CNT032013,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032013' then t.hcnt else 0 END) AS HCNT032013,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032014',t.cnt,0)) AS CNT032014,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032014' then t.hcnt else 0 END) AS HCNT032014,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032015',t.cnt,0)) AS CNT032015,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032015' then t.hcnt else 0 END) AS HCNT032015,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032016',t.cnt,0)) AS CNT032016,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032016' then t.hcnt else 0 END) AS HCNT032016,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032017',t.cnt,0)) AS CNT032017,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032017' then t.hcnt else 0 END) AS HCNT032017,\n" + 
					"\n" + 
					"sum(DECODE (t.monitoringtype,'032018',t.cnt,0)) AS CNT032018,\n" + 
					"sum(CASE WHEN t.monitoringtype = '032018' then t.hcnt else 0 END) AS HCNT032018 \n" + 			
					"FROM \n";
					sql+=" (SELECT T.LINKEDDEPWS,T.WSDEPMC,T.XH,\n" +
						" T.VOLTAGEGRADE,T.MONITORINGTYPE,T.LINKEDCABLEANDCHANNELNAME,\n" + 
						" COUNT(*) CNT,sum(DECODE(t.ishandled,'T',1,0)) HCNT,\n" + 
						" T.ALARMTIME,T.ALARMLEVEL,T.ALARMNUM,\n" + 
						" SYSDATE statTime,'T' flag\n" + 
						" FROM MW_APP.cmsv_alarm_cableinfo_xtf T\n" + 
						" WHERE t.WSDEPMC IS NOT NULL\n" + 
						" AND T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '03%' AND ALARMLEVEL IS NOT NULL AND t.WSDEPMC NOT LIKE '%分部%'\n" + 
						" GROUP BY T.LINKEDDEPWS,T.WSDEPMC,T.XH,t.VOLTAGEGRADE,T.MONITORINGTYPE,T.LINKEDCABLEANDCHANNELNAME,T.ALARMTIME,T.ALARMLEVEL,T.ALARMNUM,to_char(t.ALARMTIME,'yyyy-mm-dd'))";
						sql+=" T,\n" + 
							" MW_APP.CMST_MONITORINGTYPE MT\n" + 
							" WHERE MT.TYPECODE = T.MONITORINGTYPE AND T.MONITORINGTYPE not in('032010','032011') AND t.flag = 'T'\n";
					
						if(null != querySql && "" != querySql.toString()){
							sql+=querySql.toString();
						}
						
						sql+=" GROUP BY t.linkeddepws,t.WSDEPMC,t.xh ORDER BY t.xh)\n" + 
							"(\n" + 
							" select d.wsid obj_id,d.wsmc wsdepmc,d.zdypx xh,NVL(cnt,0) cnt,\n" + 
							" NVL(ALLCNT,0)ALLCNT,NVL(ALLHCNT,0)ALLHCNT,(NVL(ALLCNT,0)-NVL(ALLHCNT,0)) ALLNCNT,DECODE(NVL(ALLCNT,0),0,'-',to_char(ROUND(ALLHCNT/ALLCNT*100,2),'fm9999999990.00')||'%') ALLHANDLERATE,"+
							" NVL(CNT031001,0)CNT031001,NVL(HCNT031001,0)HCNT031001,(NVL(CNT031001,0)-NVL(HCNT031001,0)) NCNT031001,DECODE(NVL(CNT031001,0),0,'-',to_char(ROUND(HCNT031001/CNT031001*100,2),'fm9999999990.00')||'%') HANDLERATE031001,\n" + 
							" NVL(CNT031002,0)CNT031002,NVL(HCNT031002,0)HCNT031002,(NVL(CNT031002,0)-NVL(HCNT031002,0)) NCNT031002,DECODE(NVL(CNT031002,0),0,'-',to_char(ROUND(HCNT031002/CNT031002*100,2),'fm9999999990.00')||'%') HANDLERATE031002,\n" + 
							" NVL(CNT031003,0)CNT031003,NVL(HCNT031003,0)HCNT031003,(NVL(CNT031003,0)-NVL(HCNT031003,0)) NCNT031003,DECODE(NVL(CNT031003,0),0,'-',to_char(ROUND(HCNT031003/CNT031003*100,2),'fm9999999990.00')||'%') HANDLERATE031003,\n" + 
							" NVL(CNT031004,0)CNT031004,NVL(HCNT031004,0)HCNT031004,(NVL(CNT031004,0)-NVL(HCNT031004,0)) NCNT031004,DECODE(NVL(CNT031004,0),0,'-',to_char(ROUND(HCNT031004/CNT031004*100,2),'fm9999999990.00')||'%') HANDLERATE031004,\n" + 
							" NVL(CNT032001,0)CNT032001,NVL(HCNT032001,0)HCNT032001,(NVL(CNT032001,0)-NVL(HCNT032001,0)) NCNT032001,DECODE(NVL(CNT032001,0),0,'-',to_char(ROUND(HCNT032001/CNT032001*100,2),'fm9999999990.00')||'%') HANDLERATE032001,\n" + 
							" NVL(CNT032002,0)CNT032002,NVL(HCNT032002,0)HCNT032002,(NVL(CNT032002,0)-NVL(HCNT032002,0)) NCNT032002,DECODE(NVL(CNT032002,0),0,'-',to_char(ROUND(HCNT032002/CNT032002*100,2),'fm9999999990.00')||'%') HANDLERATE032002,\n" + 
							" NVL(CNT032003,0)CNT032003,NVL(HCNT032003,0)HCNT032003,(NVL(CNT032003,0)-NVL(HCNT032003,0)) NCNT032003,DECODE(NVL(CNT032003,0),0,'-',to_char(ROUND(HCNT032003/CNT032003*100,2),'fm9999999990.00')||'%') HANDLERATE032003,\n" + 
							" NVL(CNT032004,0)CNT032004,NVL(HCNT032004,0)HCNT032004,(NVL(CNT032004,0)-NVL(HCNT032004,0)) NCNT032004,DECODE(NVL(CNT032004,0),0,'-',to_char(ROUND(HCNT032004/CNT032004*100,2),'fm9999999990.00')||'%') HANDLERATE032004,\n" + 
							" NVL(CNT032005,0)CNT032005,NVL(HCNT032005,0)HCNT032005,(NVL(CNT032005,0)-NVL(HCNT032005,0)) NCNT032005,DECODE(NVL(CNT032005,0),0,'-',to_char(ROUND(HCNT032005/CNT032005*100,2),'fm9999999990.00')||'%') HANDLERATE032005,\n" + 
							" NVL(CNT032006,0)CNT032006,NVL(HCNT032006,0)HCNT032006,(NVL(CNT032006,0)-NVL(HCNT032006,0)) NCNT032006,DECODE(NVL(CNT032006,0),0,'-',to_char(ROUND(HCNT032006/CNT032006*100,2),'fm9999999990.00')||'%') HANDLERATE032006,\n" + 
							" NVL(CNT032007,0)CNT032007,NVL(HCNT032007,0)HCNT032007,(NVL(CNT032007,0)-NVL(HCNT032007,0)) NCNT032007,DECODE(NVL(CNT032007,0),0,'-',to_char(ROUND(HCNT032007/CNT032007*100,2),'fm9999999990.00')||'%') HANDLERATE032007,\n" + 
							" NVL(CNT032008,0)CNT032008,NVL(HCNT032008,0)HCNT032008,(NVL(CNT032008,0)-NVL(HCNT032008,0)) NCNT032008,DECODE(NVL(CNT032008,0),0,'-',to_char(ROUND(HCNT032008/CNT032008*100,2),'fm9999999990.00')||'%') HANDLERATE032008,\n" + 
							" NVL(CNT032009,0)CNT032009,NVL(HCNT032009,0)HCNT032009,(NVL(CNT032009,0)-NVL(HCNT032009,0)) NCNT032009,DECODE(NVL(CNT032009,0),0,'-',to_char(ROUND(HCNT032009/CNT032009*100,2),'fm9999999990.00')||'%') HANDLERATE032009,\n" + 
							" NVL(CNT032012,0)CNT032012,NVL(HCNT032012,0)HCNT032012,(NVL(CNT032012,0)-NVL(HCNT032012,0)) NCNT032012,DECODE(NVL(CNT032012,0),0,'-',to_char(ROUND(HCNT032012/CNT032012*100,2),'fm9999999990.00')||'%') HANDLERATE032012,\n" + 
							" NVL(CNT032013,0)CNT032013,NVL(HCNT032013,0)HCNT032013,(NVL(CNT032013,0)-NVL(HCNT032013,0)) NCNT032013,DECODE(NVL(CNT032013,0),0,'-',to_char(ROUND(HCNT032013/CNT032013*100,2),'fm9999999990.00')||'%') HANDLERATE032013,\n" +
							" NVL(CNT032014,0)CNT032014,NVL(HCNT032014,0)HCNT032014,(NVL(CNT032014,0)-NVL(HCNT032014,0)) NCNT032014,DECODE(NVL(CNT032014,0),0,'-',to_char(ROUND(HCNT032014/CNT032014*100,2),'fm9999999990.00')||'%') HANDLERATE032014,\n" +
							" NVL(CNT032015,0)CNT032015,NVL(HCNT032015,0)HCNT032015,(NVL(CNT032015,0)-NVL(HCNT032015,0)) NCNT032015,DECODE(NVL(CNT032015,0),0,'-',to_char(ROUND(HCNT032015/CNT032015*100,2),'fm9999999990.00')||'%') HANDLERATE032015,\n" +
							" NVL(CNT032016,0)CNT032016,NVL(HCNT032016,0)HCNT032016,(NVL(CNT032016,0)-NVL(HCNT032016,0)) NCNT032016,DECODE(NVL(CNT032016,0),0,'-',to_char(ROUND(HCNT032016/CNT032016*100,2),'fm9999999990.00')||'%') HANDLERATE032016,\n" +
							" NVL(CNT032017,0)CNT032017,NVL(HCNT032017,0)HCNT032017,(NVL(CNT032017,0)-NVL(HCNT032017,0)) NCNT032017,DECODE(NVL(CNT032017,0),0,'-',to_char(ROUND(HCNT032017/CNT032017*100,2),'fm9999999990.00')||'%') HANDLERATE032017,\n" +
							" NVL(CNT032018,0)CNT032018,NVL(HCNT032018,0)HCNT032018,(NVL(CNT032018,0)-NVL(HCNT032018,0)) NCNT032018,DECODE(NVL(CNT032018,0),0,'-',to_char(ROUND(HCNT032018/CNT032018*100,2),'fm9999999990.00')||'%') HANDLERATE032018 \n" + 
							" FROM tab,mw_app.cmst_zb_comm_wspz d WHERE d.wsid = tab.linkeddepws(+) AND d.wsmc NOT LIKE '%分部%'\n";
						
						if(null != ssdw && StringUtils.isNotBlank(ssdw)){
							sql+= " and obj_id IN('"+ssdw.replace(",","','")+"')";
						}
						
						sql+=" and d.wsmc not like '%运行%' " + 
							"\n"+
							" UNION ALL\n" + 
								"\n" + 
								" (select '国家电网公司','国家电网公司',0,NVL(SUM(cnt),0),\n" + 
								" NVL(SUM(ALLCNT),0),NVL(SUM(ALLHCNT),0),(NVL(SUM(ALLCNT),0)-NVL(SUM(ALLHCNT),0)),DECODE(NVL(SUM(ALLHCNT),0),0,'-',to_char(ROUND(SUM(ALLHCNT)/SUM(ALLCNT)*100,2),'fm9999999990.00')||'%'),"+
								" NVL(SUM(CNT031001),0),NVL(SUM(HCNT031001),0),(NVL(SUM(CNT031001),0)-NVL(SUM(HCNT031001),0)),DECODE(NVL(SUM(HCNT031001),0),0,'-',to_char(ROUND(SUM(HCNT031001)/SUM(CNT031001)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT031002),0),NVL(SUM(HCNT031002),0),(NVL(SUM(CNT031002),0)-NVL(SUM(HCNT031002),0)),DECODE(NVL(SUM(HCNT031002),0),0,'-',to_char(ROUND(SUM(HCNT031002)/SUM(CNT031002)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT031003),0),NVL(SUM(HCNT031003),0),(NVL(SUM(CNT031003),0)-NVL(SUM(HCNT031003),0)),DECODE(NVL(SUM(HCNT031003),0),0,'-',to_char(ROUND(SUM(HCNT031003)/SUM(CNT031003)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT031004),0),NVL(SUM(HCNT031004),0),(NVL(SUM(CNT031004),0)-NVL(SUM(HCNT031004),0)),DECODE(NVL(SUM(HCNT031004),0),0,'-',to_char(ROUND(SUM(HCNT031004)/SUM(CNT031004)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032001),0),NVL(SUM(HCNT032001),0),(NVL(SUM(CNT032001),0)-NVL(SUM(HCNT032001),0)),DECODE(NVL(SUM(HCNT032001),0),0,'-',to_char(ROUND(SUM(HCNT032001)/SUM(CNT032001)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032002),0),NVL(SUM(HCNT032002),0),(NVL(SUM(CNT032002),0)-NVL(SUM(HCNT032002),0)),DECODE(NVL(SUM(HCNT032002),0),'-',to_char(ROUND(SUM(HCNT032002)/SUM(CNT032002)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032003),0),NVL(SUM(HCNT032003),0),(NVL(SUM(CNT032003),0)-NVL(SUM(HCNT032003),0)),DECODE(NVL(SUM(HCNT032003),0),0,'-',to_char(ROUND(SUM(HCNT032003)/SUM(CNT032003)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032004),0),NVL(SUM(HCNT032004),0),(NVL(SUM(CNT032004),0)-NVL(SUM(HCNT032004),0)),DECODE(NVL(SUM(HCNT032004),0),0,'-',to_char(ROUND(SUM(HCNT032004)/SUM(CNT032004)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032005),0),NVL(SUM(HCNT032005),0),(NVL(SUM(CNT032005),0)-NVL(SUM(HCNT032005),0)),DECODE(NVL(SUM(HCNT032005),0),0,'-',to_char(ROUND(SUM(HCNT032005)/SUM(CNT032005)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032006),0),NVL(SUM(HCNT032006),0),(NVL(SUM(CNT032006),0)-NVL(SUM(HCNT032006),0)),DECODE(NVL(SUM(HCNT032006),0),0,'-',to_char(ROUND(SUM(HCNT032006)/SUM(CNT032006)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032007),0),NVL(SUM(HCNT032007),0),(NVL(SUM(CNT032007),0)-NVL(SUM(HCNT032007),0)),DECODE(NVL(SUM(HCNT032007),0),0,'-',to_char(ROUND(SUM(HCNT032007)/SUM(CNT032007)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032008),0),NVL(SUM(HCNT032008),0),(NVL(SUM(CNT032008),0)-NVL(SUM(HCNT032008),0)),DECODE(NVL(SUM(HCNT032008),0),0,'-',to_char(ROUND(SUM(HCNT032008)/SUM(CNT032008)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032009),0),NVL(SUM(HCNT032009),0),(NVL(SUM(CNT032009),0)-NVL(SUM(HCNT032009),0)),DECODE(NVL(SUM(HCNT032009),0),0,'-',to_char(ROUND(SUM(HCNT032009)/SUM(CNT032009)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032012),0),NVL(SUM(HCNT032012),0),(NVL(SUM(CNT032012),0)-NVL(SUM(HCNT032012),0)),DECODE(NVL(SUM(HCNT032012),0),0,'-',to_char(ROUND(SUM(HCNT032012)/SUM(CNT032012)*100,2),'fm9999999990.00')||'%'),\n" + 
								" NVL(SUM(CNT032013),0),NVL(SUM(HCNT032013),0),(NVL(SUM(CNT032013),0)-NVL(SUM(HCNT032013),0)),DECODE(NVL(SUM(HCNT032013),0),0,'-',to_char(ROUND(SUM(HCNT032013)/SUM(CNT032013)*100,2),'fm9999999990.00')||'%'),\n" +
								" NVL(SUM(CNT032014),0),NVL(SUM(HCNT032014),0),(NVL(SUM(CNT032014),0)-NVL(SUM(HCNT032014),0)),DECODE(NVL(SUM(HCNT032014),0),0,'-',to_char(ROUND(SUM(HCNT032014)/SUM(CNT032014)*100,2),'fm9999999990.00')||'%'),\n" +
								" NVL(SUM(CNT032015),0),NVL(SUM(HCNT032015),0),(NVL(SUM(CNT032015),0)-NVL(SUM(HCNT032015),0)),DECODE(NVL(SUM(HCNT032015),0),0,'-',to_char(ROUND(SUM(HCNT032015)/SUM(CNT032015)*100,2),'fm9999999990.00')||'%'),\n" +
								" NVL(SUM(CNT032016),0),NVL(SUM(HCNT032016),0),(NVL(SUM(CNT032016),0)-NVL(SUM(HCNT032016),0)),DECODE(NVL(SUM(HCNT032016),0),0,'-',to_char(ROUND(SUM(HCNT032016)/SUM(CNT032016)*100,2),'fm9999999990.00')||'%'),\n" +
								" NVL(SUM(CNT032017),0),NVL(SUM(HCNT032017),0),(NVL(SUM(CNT032017),0)-NVL(SUM(HCNT032017),0)),DECODE(NVL(SUM(HCNT032017),0),0,'-',to_char(ROUND(SUM(HCNT032017)/SUM(CNT032017)*100,2),'fm9999999990.00')||'%'),\n" +
								" NVL(SUM(CNT032018),0),NVL(SUM(HCNT032018),0),(NVL(SUM(CNT032018),0)-NVL(SUM(HCNT032018),0)),DECODE(NVL(SUM(HCNT032018),0),0,'-',to_char(ROUND(SUM(HCNT032018)/SUM(CNT032018)*100,2),'fm9999999990.00')||'%') \n" + 
								" FROM tab)"+
							" )ORDER BY xh";
					
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			   
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			log.info("执行按监测类型统计电缆告警信息时出错！", e);
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public QueryResultObject getDetailList(RequestCondition params) {

		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		int pageSize = Integer.valueOf(params.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(params.getPageIndex()); // 开始编号
		
		String cols = "OBJ_ID,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME,TYPENAME,JCXX,WSDEPMC,DEVICEVOLTAGE,ALARMMESSAGE,ALARMLEVEL,ALARMTIME,LINKEDDEPWS,DEVICECODE,MONITORINGTYPE";
		String colsTransf = "CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME,TYPENAME,CAST('查看' AS VARCHAR2(10)) JCXX,WSDEPMC,DEVICEVOLTAGE,ALARMMESSAGE,ALARMLEVEL," +
				"TO_CHAR(ALARMTIME,'YYYY-MM-DD HH24:MI:SS') ALARMTIME,LINKEDDEPWS,DEVICECODE,MONITORINGTYPE";
		
		StringBuffer querySql = new StringBuffer();
		
		querySql.append(" SELECT ");
		querySql.append(colsTransf);
		querySql.append(" FROM mw_app.cmsv_alarm_cableinfo_xtf t ");
		querySql.append(" WHERE T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '03%' ");
		
		if(null != params.getFilter()){
			String[] filter = params.getFilter().toString().trim().split("&");	//对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				String filterKey = filter[i].split("=")[0].trim();			//变量名称
				String filterValue = filter[i].split("=")[1].trim();		//变量值
				if(Constants.SSDW.equals(filterKey)){
					querySql.append(" AND LINKEDDEPWS IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DYDJ.equals(filterKey)){
					querySql.append(" AND VOLTAGEGRADE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.JCLX.equals(filterKey)){
					querySql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.BDZ.equals(filterKey)){
					if(null != filterValue && StringUtils.isNotBlank(filterValue)){
						querySql.append(" AND LINKEDCABLEANDCHANNELNAME LIKE '%"+filterValue.trim()+"%'");
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
		
		querySql.append(" AND ALARMLEVEL IS NOT NULL AND WSDEPMC IS NOT NULL  ORDER BY ALARMTIME DESC");
		
		try{
			result = hibernateDao_ztjc.executeSqlQuery(querySql.toString(),pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql.toString() + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行变电告警信息查询统计的初始化DataGrid的查询SQL中发生异常",e);
		}
		
		return null;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	   private List<Map> transToColumns(List<Object[]> list, String columns){
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
}
