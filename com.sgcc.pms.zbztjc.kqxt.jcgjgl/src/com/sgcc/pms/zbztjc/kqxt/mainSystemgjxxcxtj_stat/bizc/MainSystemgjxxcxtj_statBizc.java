package com.sgcc.pms.zbztjc.kqxt.mainSystemgjxxcxtj_stat.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.kqxt.Constants;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class MainSystemgjxxcxtj_statBizc implements IMainSystemgjxxcxtj_statBizc{
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(MainSystemgjxxcxtj_statBizc.class);
	
	@Override
	public QueryResultObject statBySsxt(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		
		String bdzSql = null;
		String lineSql = null;
		String ssxt = null;
		
		StringBuffer filterSql = new StringBuffer();
		
		if(null != queryCondition.getFilter()){	//如果参数不为空
			String[] filter = queryCondition.getFilter().toString().trim().split("&");	//对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if(Constants.GJSJ.equals(filterKey)){
					String startTime = null;
					String endTime = null;
					if(",".equals(filterValue.substring(0, 1))){			//如果是以，开头，则说明开始时间为空，
						endTime = filterValue.split(",")[1];
						filterSql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') <= '");
						filterSql.append(endTime);
						filterSql.append("'");
					}else if(",".equals(filterValue.substring(filterValue.length()-1, filterValue.length()))){	//如果结束时间为空
						startTime = filterValue.split(",")[0];
						filterSql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') >= '");
						filterSql.append(startTime);
						filterSql.append("'");
					}else{			//如果开始时间和结束时间都不为空
						startTime = filterValue.split(",")[0];
						endTime = filterValue.split(",")[1];
						filterSql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') BETWEEN '");
						filterSql.append(startTime);
						filterSql.append("' AND '");
						filterSql.append(endTime);
						filterSql.append("'");
					}
				}else if(Constants.BDZ.equals(filterKey)){		//变电站名称
					lineSql = " AND BXNAME  like '%"+filterValue.trim()+"%'";
				}else if(Constants.XLMC.equals(filterKey)){										//线路名称
					bdzSql = " AND BXNAME  like '%"+filterValue.trim()+"%'";		
				}else if(Constants.JCLX.equals(filterKey)){		//监测类型
					filterSql.append(" AND t.MONITORINGTYPE in ('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DYDJ.equals(filterKey)){
					filterSql.append(" AND DEVICEVOLTAGEBZDM in ('"+filterValue.replace(",","','")+"')");
				}else if(Constants.SSXT.equals(filterKey)){
					ssxt = "'"+filterValue.replace(",","','")+"'";
					filterSql.append(" AND xtid in ('"+filterValue.replace(",","','")+"')");
				}else if(Constants.SSDW.equals(filterKey)){
					filterSql.append(" AND linkeddepws in ('"+filterValue.replace(",","','")+"')");
				}
			}
		}
		
		try{
			cols ="OBJ_ID,WSDEPMC,CNT01,HCNT01,NCNT01,HANDLERATE01,CNT02,HCNT02,NCNT02,HANDLERATE02";
			
			String sql ="SELECT\n" +
					"     T1.OBJ_ID XTID,T1.XTMC,\n" + 
					"     T1.allcnt as ACNT,T1.allhcnt as AHCNT,T1.allncnt as ANCNT,T1.allHANDLERATE as AHANDLERATE,\n" + 
					"     T2.allcnt as BCNT,T2.allhcnt as BHCNT,T2.allncnt as BNCNT,T2.allHANDLERATE as BHANDLERATE\n" + 
					"  FROM (\n" + 
					"         WITH tab1 AS\n" + 
					"         (SELECT t.xtid,t.xtmc,\n" + 
					"               SUM(t.cnt) cnt,\n" + 
					"               SUM(t.hcnt) thcnt,\n" + 
					"               SUM(CASE WHEN t.DEVICEVOLTAGEBZDM IN ('37', '36', '35', '34', '33', '82', '83', '84', '85', '86') then t.cnt else 0 END) AS allCNT,\n" + 
					"               SUM(CASE WHEN t.DEVICEVOLTAGEBZDM IN ('37', '36', '35', '34', '33', '82', '83', '84', '85', '86') then t.hcnt else 0 END) AS allHCNT\n" + 
					"          FROM MW_APP.CMST_ALARMSTATISTIC_KQ_ZYGC T,\n" + 
					"               MW_APP.CMST_SB_PZ_SBDYDJ DY,\n" + 
					"               MW_APP.CMST_MONITORINGTYPE MT\n" + 
					"          WHERE T.DEVICEVOLTAGEBZDM = DY.DYDJBM\n" + 
					"               AND MT.TYPECODE = T.MONITORINGTYPE\n" + 
					"               AND t.flag = 'L'\n"; 
					if(null != filterSql && "" != filterSql.toString()){
						sql += filterSql;
					}
					if(null != lineSql && "" != lineSql){
						sql += lineSql;
					}
					sql+="         GROUP BY t.xtid, t.xtmc)\n" + 
					"         select 'GW'  as obj_id,\n" + 
					"               '合计' as xtmc,\n" + 
					/*"               '0' as ssdw,\n" + */
					"               NVL(SUM(CNT), 0) as cnt,\n" + 
					"               NVL(SUM(THCNT), 0) as thcnt,\n" + 
					"               NVL(SUM(allCNT), 0) as allcnt,\n" + 
					"               NVL(SUM(allHCNT), 0) as allhcnt,\n" + 
					"               (NVL(SUM(allcnt), 0) - NVL(SUM(allhcnt), 0)) as allncnt,\n" + 
					"               DECODE(NVL(SUM(allHCNT), 0),0,'-',to_char(ROUND(SUM(allHCNT) / SUM(allCNT) * 100,2),'fm9999999990.00') || '%') as allHANDLERATE\n" + 
					"         FROM tab1\n" + 
					"         UNION ALL(\n" + 
					"         SELECT * FROM\n" + 
					"         (SELECT d.obj_id,d.xtmc," +
			/*		"d.ssdw,\n" + */
					"               NVL(cnt, 0),\n" + 
					"               NVL(thcnt, 0),\n" + 
					"               NVL(allcnt, 0),\n" + 
					"               NVL(allhcnt, 0),\n" + 
					"               (NVL(allcnt, 0) - NVL(allhcnt, 0)),\n" + 
					"               DECODE(NVL(allcnt, 0),0,'-',to_char(ROUND(allhcnt / allcnt * 100, 2),'fm9999999990.00') || '%')\n" + 
					"               FROM tab1, mw_app.cmst_kq_xt d\n" + 
					"          WHERE d.obj_id = tab1.xtid(+)\n" + 
					"              and d.obj_id is not null\n"; 
					if(null != ssxt && "" != ssxt){					//按所属系统的分组
						sql += " AND obj_id in(" + ssxt + ")";
					}
					sql+="          order by d.xtmc)\n" + 
					"          )\n" + 
					"    ) T1\n" + 
					"full join(\n" + 
					"   WITH tab2 AS\n" + 
					"        (SELECT t.xtid,t.xtmc,\n" + 
					"               SUM(t.cnt) cnt,\n" + 
					"               SUM(t.hcnt) thcnt,\n" + 
					"               SUM(CASE WHEN t.DEVICEVOLTAGEBZDM IN('37','36','35','34','33','82','83','84','85','86') then t.cnt else 0 END) AS allCNT,\n" + 
					"               SUM(CASE WHEN t.DEVICEVOLTAGEBZDM IN('37','36','35','34','33','82','83','84','85','86') then t.hcnt else 0 END) AS allHCNT\n" + 
					"         FROM MW_APP.CMST_ALARMSTATISTIC_KQ_ZYGC T,MW_APP.CMST_SB_PZ_SBDYDJ DY,MW_APP.CMST_MONITORINGTYPE MT\n" + 
					"         WHERE T.DEVICEVOLTAGEBZDM = DY.DYDJBM\n" + 
					"               AND MT.TYPECODE = T.MONITORINGTYPE\n" + 
					"               AND t.flag = 'T'\n"; 
					if(null != filterSql && "" != filterSql.toString()){
						sql += filterSql;
					}
					if(null != bdzSql && "" != bdzSql){
						sql += bdzSql;
					}
					sql+="               GROUP BY t.xtid, t.xtmc)\n" + 
					"         select 'GW' as obj_id,\n" + 
					"               '合计' as xtmc,\n" + 
				/*	"               '0' as ssdw,\n" + */
					"               NVL(SUM(CNT), 0) as cnt,\n" + 
					"               NVL(SUM(THCNT), 0) as thcnt,\n" + 
					"               NVL(SUM(allCNT), 0) as allcnt,\n" + 
					"               NVL(SUM(allHCNT), 0) as allhcnt,\n" + 
					"               (NVL(SUM(allcnt), 0) - NVL(SUM(allhcnt), 0)) as allncnt,\n" + 
					"               DECODE(NVL(SUM(allHCNT), 0),0,'-',to_char(ROUND(SUM(allHCNT)/SUM(allCNT) * 100,2),'fm9999999990.00') || '%') as allHANDLERATE\n" + 
					"          FROM tab2\n" + 
					"          UNION ALL(\n" + 
					"          SELECT * FROM\n" + 
					"          (SELECT d.obj_id ,d.xtmc," +
		/*			"d.ssdw,\n" + */
					"                 NVL(cnt, 0),\n" + 
					"                 NVL(thcnt, 0),\n" + 
					"                 NVL(allcnt, 0),\n" + 
					"                 NVL(allhcnt, 0),\n" + 
					"                 (NVL(allcnt, 0) - NVL(allhcnt, 0)),\n" + 
					"                 DECODE(NVL(allcnt, 0),0,'-',to_char(ROUND(allhcnt / allcnt * 100, 2),'fm9999999990.00') || '%')\n" + 
					"           FROM tab2, mw_app.cmst_kq_xt d\n" + 
					"           WHERE d.obj_id = tab2.xtid(+)\n" + 
					"                 and d.obj_id is not null\n"; 
					if(null != ssxt && "" != ssxt){
						sql += " AND obj_id in(" + ssxt + ")";
					}
					sql+="           order by d.xtmc)\n" + 
					"          )\n" + 
					"     ) T2\n" + 
					"on T1.obj_id = T2.obj_id";
					
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			   
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			log.info("执行按所属系统统计主要系统告警信息时出错！", e);
			e.printStackTrace();
		}
		return qro;
	}
	
	@Override
	public QueryResultObject queryDetail(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		String ssxt = "";
		StringBuffer filterSql = new StringBuffer();
		String type = "";
		
		if(null != queryCondition.getFilter()){	//如果参数不为空
			System.out.println(queryCondition.getFilter());
			String[] filter = queryCondition.getFilter().toString().trim().split("&");	//对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if(Constants.GJSJ.equals(filterKey)){
					String startTime = null;
					String endTime = null;
					if(",".equals(filterValue.substring(0, 1))){			//如果是以，开头，则说明开始时间为空，
						endTime = filterValue.split(",")[1];
						filterSql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') <= '");
						filterSql.append(endTime);
						filterSql.append("'");
					}else if(",".equals(filterValue.substring(filterValue.length()-1, filterValue.length()))){	//如果结束时间为空
						startTime = filterValue.split(",")[0];
						filterSql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') >= '");
						filterSql.append(startTime);
						filterSql.append("'");
					}else{			//如果开始时间和结束时间都不为空
						startTime = filterValue.split(",")[0];
						endTime = filterValue.split(",")[1];
						filterSql.append(" AND TO_CHAR(ALARMTIME,'YYYY-MM-DD') BETWEEN '");
						filterSql.append(startTime);
						filterSql.append("' AND '");
						filterSql.append(endTime);
						filterSql.append("'");
					}
				}else if(Constants.BDZ.equals(filterKey)){				//变电站名称
					filterSql.append(" AND LINKEDSTATION LIKE '%"+filterValue.trim()+"%' ");
				}else if(Constants.XLMC.equals(filterKey)){				//线路名称
					filterSql.append(" AND LINKEDLINENAME LIKE '%"+filterValue.trim()+"%' ");
				}else if(Constants.JCLX.equals(filterKey)){				//监测类型
					filterSql.append(" AND t.MONITORINGTYPE in ('"+filterValue.replace(",","','")+"')");
				}else if(Constants.DYDJ.equals(filterKey)){
					filterSql.append(" AND DEVICEVOLTAGEBZDM in ('"+filterValue.replace(",","','")+"')");
				}else if(Constants.SSXT.equals(filterKey)){
					ssxt = filterValue;
				}else if(Constants.SSDW.equals(filterKey)){
					filterSql.append(" AND linkeddepws in ('"+filterValue.replace(",","','")+"')");
				}else if("type".equalsIgnoreCase(filterKey)){
					type = filterValue;
				}else if(Constants.ISHANDLED.equalsIgnoreCase(filterKey)){
					if(Constants.T.equals(filterValue)){
						filterSql.append(" AND ISHANDLED = 'T' ");
					}else if(Constants.F.equals(filterValue)){
						filterSql.append(" AND (ISHANDLED = '' OR ISHANDLED = 'F' OR ISHANDLED IS NULL OR ISHANDLED = 'N')");
					}
				}
			}
			cols = "OBJ_ID,linkeddep,LINKEDDEPWS,WSDEPMC,LINKEDLINENAME,XTMC,LINKEDPOLENAME,DEVICEVOLTAGE,DEVICECODE,DEVICENAME," +
					"MONITORINGTYPE,TYPENAME,ALARMTIME,ALARMLEVEL,CONTINUANCETIME,ALARMMESSAGE,ishandledName,ishandled";
			String sql = "";
			if(Constants.SDNAME.equalsIgnoreCase(type)){			//如果是输电
				sql  = "select CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,t.linkeddep,t.LINKEDDEPWS,t.WSDEPMC,T.LINKEDLINENAME,t.XTMC,t.LINKEDPOLENAME," +
						"t.DEVICEVOLTAGE,t.DEVICECODE,t.DEVICENAME,t.MONITORINGTYPE,t.TYPENAME,TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME,t.ALARMLEVEL,nvl(CONTINUANCETIME,0) CONTINUANCETIME,t.ALARMMESSAGE,\n" +
						" DECODE(ishandled,'T','是','F','否','','否',NULL,'否') ishandledName,ishandled"+
						" from mw_app.CMSV_ALARM_SDINFOF t where T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '01%' AND ALARMNUM IS NOT NULL ";
				if(null != filterSql && "" != filterSql.toString()){		//如果搜索条件不为空，则将条件拼入SQL
					sql += filterSql.toString();
				}
				if(null != ssxt && "" != ssxt){								//如果所属系统编码不为空，则拼入SQL
					sql += " AND t.linkedline in (select l.sbbm from CMST_SB_ZWYC_XL l where ssws = '"+ssxt+"') ";
				}
			}else if(Constants.BDNAME.equalsIgnoreCase(type)){		//如果是变电
				sql = "select CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,t.linkeddep,t.LINKEDDEPWS,t.WSDEPMC,T.LINKEDSTATIONNAME,t.XTMC,t.LINKEDEQUIPMENTNAME," +
				"t.DEVICEVOLTAGE,t.DEVICECODE,t.DEVICENAME,t.MONITORINGTYPE,t.TYPENAME,TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME,t.ALARMLEVEL,nvl(CONTINUANCETIME,0) CONTINUANCETIME,t.ALARMMESSAGE,\n" +
				" DECODE(ishandled,'T','是','F','否','','否',NULL,'否') ishandledName,ishandled"+
				" from mw_app.CMSV_ALARM_BDINFOF t where T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '02%' AND ALARMNUM IS NOT NULL";
				if(null != filterSql && "" != filterSql.toString()){
					sql += filterSql.toString();
				}
				
				if(null != ssxt && "" != ssxt){
					sql += " AND t.LINKEDSTATION IN (select sbbm bdzid"+
		                " from mw_app.CMST_SB_ZNYC_DZ bdz,mw_app.CMSV_ALARM_BDINFOF info"+
		                " where info.LINKEDSTATION(+) = bdz.sbbm " +
		                " AND ssxt = '"+ssxt+"'"+
		                " group by sbbm, bdzmc, info.MONITORINGTYPE,info.ishandled) ";
				}
			}
			
			sql += " AND ALARMLEVEL IS NOT NULL AND WSDEPMC IS NOT NULL  ORDER BY ALARMTIME DESC ";
			
			try {
				result = hibernateDao_ztjc.executeSqlQuery(sql , Integer.parseInt(queryCondition.getPageIndex()),Integer.parseInt(queryCondition.getPageSize()));
				count = result = transToColumns(result,cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
				   
				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
			} catch (Exception e) {
				log.info("执行按所属系统统计主要系统告警信息-详细信息时出错！", e);
				e.printStackTrace();
			}
			
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
	   private List<Map> transToColumns(List<Object[]> list, String columns)
	   {
	       List<Map> items = new ArrayList();

	       String[] cols = columns.split("\\,");

	       for (int i = 0; i < list.size(); i++)
	       {
	           Map map = new HashMap();
	           for (int m = 0; m < cols.length; m++)
	           {
	        	   map.put(cols[m], list.get(i)[m]);
	           }
	           items.add(map);
	       }
	       return items;
	   }
}
