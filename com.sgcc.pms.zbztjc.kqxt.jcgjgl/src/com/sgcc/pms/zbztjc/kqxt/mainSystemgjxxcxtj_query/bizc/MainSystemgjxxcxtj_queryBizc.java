package com.sgcc.pms.zbztjc.kqxt.mainSystemgjxxcxtj_query.bizc;

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

public class MainSystemgjxxcxtj_queryBizc implements IMainSystemgjxxcxtj_queryBizc{
	
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	private final Log logger = LogFactory.getLog(MainSystemgjxxcxtj_queryBizc.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public QueryResultObject query(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		StringBuffer filterSql = new StringBuffer();
		List resultList = null;
		List count = null;
		int pageSize = Integer.valueOf(queryCondition.getPageSize());
	    int pageIndex = Integer.valueOf(queryCondition.getPageIndex());
	    String bdzSql = "";					//变电站的值  ->带入SQL
		String lineSql = "";				//线路的值 ->带入SQL
	    
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
				}else if(Constants.BDZ.equals(filterKey)){		//变电站名称
					bdzSql = " AND LINKEDSTATIONNAME like '%"+filterValue.trim()+"%'";
				}else if(Constants.XLMC.equals(filterKey)){										//线路名称
					lineSql = " AND LINKEDLINENAME like '%"+filterValue.trim()+"%'";		
				}else if(Constants.JCLX.equals(filterKey)){		//监测类型
					filterSql.append(" AND t.MONITORINGTYPE in ('"+filterValue.replace(",","','")+"')");
				}else if(Constants.ISHANDLED.equalsIgnoreCase(filterKey)){	//是否出来
					if(("all").equals(filterValue)){
						//如果是全部，则不进行判断，即取全部数据
					}else if(("F").equals(filterValue)){
						filterSql.append(" AND (t.ISHANDLED = 'F' or t.ISHANDLED  is null or t.ISHANDLED = ' ')");
					}else{
						filterSql.append(" AND t.ISHANDLED = 'T' ");
					}
				}else if(Constants.DYDJ.equals(filterKey)){
					filterSql.append(" AND DEVICEVOLTAGEBZDM in ('"+filterValue.replace(",","','")+"')");
				}else if(Constants.SSXT.equals(filterKey)){
					filterSql.append(" AND xtid in ('"+filterValue.replace(",","','")+"')");
				}else if(Constants.SSDW.equals(filterKey)){
					filterSql.append(" AND linkeddepws in ('"+filterValue.replace(",","','")+"')");
				}
			}
		}
		
		String cols = "XTMC,XTID,DEVICEVOLTAGEBZDM,DEVICECODE,OBJ_ID,MONITORINGTYPE,linkeddep,LINKEDDEPWS,linkeddepmc,WSDEPMC" +
				"LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,YCSBNAME,DEVICEVOLTAGE,DEVICENAME,TYPENAME,ALARMTIME,ALARMLEVEL,CONTINUANCETIME" +
				"ALARMMESSAGE,ishandledName";
	    
	    String querySql = "SELECT * FROM (select t.XTMC,\n" +
		"           t.XTID,\n" + 
		"           t.DEVICEVOLTAGEBZDM,\n" + 
		"           t.DEVICECODE,\n" + 
		"           CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,\n" + 
		"           t.MONITORINGTYPE,\n" + 
		"           t.linkeddep,\n" + 
		"           t.LINKEDDEPWS,\n" + 
		"           t.linkeddepmc,\n" + 
		"           t.WSDEPMC,\n" + 
		"           T.LINKEDSTATIONNAME as LINKEDLINENAME,\n" + 
		"           t.LINKEDEQUIPMENTNAME as LINKEDPOLENAME,\n" + 
		"           t.LINKEDSTATIONNAME || ' ' || t.LINKEDEQUIPMENTNAME YCSBNAME,\n" + 
		"           t.DEVICEVOLTAGE,\n" + 
		"           t.DEVICENAME,\n" + 
		"           t.TYPENAME,\n" + 
		"           TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME,\n" + 
		"           t.ALARMLEVELMC,\n" + 
		"           nvl(CONTINUANCETIME, 0) CONTINUANCETIME,\n" + 
		"           t.ALARMMESSAGE,\n" + 
		"           DECODE(ishandled, 'T', '是', 'F', '否', '', '否', NULL, '否') ishandledName\n" + 
		"      from mw_app.CMSV_ALARM_BDINFOF t\n" + 
		"     where T.ALARMSOURCE = 'E'\n" + 
		"       AND MONITORINGTYPE LIKE '02%'\n" + 
		"       AND ALARMLEVEL IS NOT NULL\n" + 
		"       AND WSDEPMC IS NOT NULL\n";
	    if(null != bdzSql && "" != bdzSql){				//将变电站条件加到SQL中
	    	querySql += bdzSql;
	    }
	    if(null != filterSql ){							//将查询条件加到SQL中
	    	querySql += filterSql;
	    }
	    querySql += "     union all(\n" + 
		"       select t.XTMC,\n" + 
		"           t.XTID,\n" + 
		"           t.DEVICEVOLTAGEBZDM,\n" + 
		"           t.DEVICECODE,\n" + 
		"           CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,\n" + 
		"           t.MONITORINGTYPE,\n" + 
		"           t.linkeddep,\n" + 
		"           t.LINKEDDEPWS,\n" + 
		"           t.linkeddepmc,\n" + 
		"           t.WSDEPMC,\n" + 
		"           t.LINKEDLINENAME,\n" + 
		"           t.LINKEDPOLENAME,\n" + 
		"           t.LINKEDLINENAME || ' ' || t.LINKEDPOLENAME YCSBNAME,\n" + 
		"           t.DEVICEVOLTAGE,\n" + 
		"           t.DEVICENAME,\n" + 
		"           t.TYPENAME,\n" + 
		"           TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME,\n" + 
		"           t.ALARMLEVELMC,\n" + 
		"           nvl(CONTINUANCETIME, 0) CONTINUANCETIME,\n" + 
		"           t.ALARMMESSAGE,\n" + 
		"           DECODE(ishandled, 'T', '是', 'F', '否', '', '否', NULL, '否') ishandledName\n" + 
		"      from mw_app.CMSV_ALARM_SDINFOF t\n" + 
		"     where T.ALARMSOURCE = 'E'\n" + 
		"       AND MONITORINGTYPE LIKE '01%'\n" + 
		"       AND ALARMLEVEL IS NOT NULL\n" + 
		"       AND WSDEPMC IS NOT NULL";
	    if(null != lineSql && "" != lineSql){			//将线路条件加到SQL中
	    	   querySql += lineSql;
	    }
	    if(null != filterSql ){							//将查询条件加到SQL中
	    	querySql += filterSql;
	    }
	    querySql += ")) ORDER BY ALARMTIME DESC";
	    try {
			logger.info("执行主要系统告警信息查询统计的查询方法执行的SQL为:"+querySql);
			resultList = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			resultList = transToColumns(resultList,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			
			qro.setItems(resultList);
			qro.setItemCount(((Number) (count.get(0))).intValue());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("执行主要系统告警信息查询统计的查询方法时发生异常！");
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
