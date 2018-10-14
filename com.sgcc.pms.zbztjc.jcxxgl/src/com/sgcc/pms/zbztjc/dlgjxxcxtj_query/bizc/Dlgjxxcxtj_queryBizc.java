package com.sgcc.pms.zbztjc.dlgjxxcxtj_query.bizc;

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


public class Dlgjxxcxtj_queryBizc implements IDlgjxxcxtj_queryBizc{
	
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(Dlgjxxcxtj_queryBizc.class);

	@Override
	public QueryResultObject query(RequestCondition queryCondition) {

		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		
		StringBuffer querySql = new StringBuffer();
		String colsTransf = "CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID,LINKEDDEPWS AS LINKEDPROVICEDEPT,LINKEDDEPWS,WSDEPMC,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME," +
				"DEVICEVOLTAGE,DEVICENAME,TYPENAME,TO_CHAR(ALARMTIME,'YYYY-MM-DD HH24:MI:SS') ALARMTIME,ALARMLEVEL,nvl(CONTINUANCETIME,0) CONTINUANCETIME,ALARMMESSAGE," +
				" DECODE(ishandled,'T','是','F','否','','否',NULL,'否') ishandledName";
		String cols = "OBJ_ID,LINKEDPROVICEDEPT,LINKEDDEPWS,WSDEPMC,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME,DEVICEVOLTAGE," +
				"DEVICENAME,TYPENAME,ALARMTIME,ALARMLEVEL,CONTINUANCETIME,ALARMMESSAGE,ishandledName";
		
		
		querySql.append(" SELECT ");
		querySql.append(colsTransf);
		querySql.append(" FROM mw_app.cmsv_alarm_cableinfo_xtf t ");
		querySql.append(" WHERE T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '03%' AND MONITORINGTYPE not in('032010','032011') AND WSDEPMC IS NOT NULL ");
		
		if(null != queryCondition.getFilter()){
			String[] filter = queryCondition.getFilter().toString().trim().split("&");	//对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				String filterKey = filter[i].split("=")[0].trim();			//变量名称
				String filterValue = filter[i].split("=")[1].trim();		//变量值
				if(null != filterValue && StringUtils.isNotBlank(filterValue)){
					if(Constants.SSDW.equals(filterKey)){
							querySql.append(" AND LINKEDDEPWS IN('"+filterValue.replace(",","','")+"')");
					}else if(Constants.DYDJ.equals(filterKey)){
						querySql.append(" AND VOLTAGEGRADE IN('"+filterValue.replace(",","','")+"')");
					}else if(Constants.JCLX.equals(filterKey)){
						querySql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
					}else if(Constants.DLMC.equals(filterKey)){
						if(null != filterValue && StringUtils.isNotBlank(filterValue)){
							querySql.append(" AND LINKEDCABLEANDCHANNELNAME LIKE '%"+filterValue.trim()+"%'");
						}
					}else if(Constants.GJJB.equals(filterKey)){
						querySql.append("AND ALARMNUM IN('"+filterValue.replace(",","','")+"')");
					}else if(Constants.ISHANDLED.equals(filterKey)){
						if(Constants.ALL.equals(filterValue)){
							
						}else if(Constants.T.equals(filterValue)){
							querySql.append(" AND ISHANDLED = 'T' ");
						}else{
							querySql.append(" AND (ISHANDLED = '' OR ISHANDLED = 'F' OR ISHANDLED IS NULL OR ISHANDLED = 'N')");
						}
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
		}
		querySql.append(" ORDER BY ALARMTIME DESC");
		
		try{
			result = hibernateDao_ztjc.executeSqlQuery(querySql.toString(),pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql.toString() + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行电缆告警信息查询统计的初始化DataGrid的查询SQL中发生异常",e);
		}
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryDatailById(String id) {
		List resultList = null;
		StringBuffer querySql = new StringBuffer();
		querySql.append(" SELECT CAST(b.OBJ_ID AS VARCHAR2(42)) OBJ_ID,b.LINKEDCABLEANDCHANNELNAME,b.LINKEDEQUIPMENTNAME,b.DEVICECODE,b.MONITORINGTYPE,");
		querySql.append(" b.TYPENAME,b.ALARMTIME,b.ALARMENDTIME,b.CONTINUANCETIME,b.ALARMLEVEL,b.ALARMMESSAGE,");
		querySql.append(" b.ALARMRULE,decode(trim(b.ALARMSOURCE), 'E', '一次设备告警', 'M', '监测装置告警') ALARMSOURCE,");
		querySql.append(" b.ALARMCONSTRUE,b.DISPOSALADVICE,b.DISPOSALTIME,b.DISPOSALRESULT,");
		querySql.append(" b.TRANSACTOR,b.CHECKTIME,b.CHECKEDBY,b.ISHANDLED,b.REMARKS FROM mw_app.cmsv_alarm_cableinfo_xtf b where b.MONITORINGTYPE LIKE '03%' and b.obj_id = '");
		querySql.append(id);
		querySql.append("'");
		log.info(querySql.toString());
		try{
			resultList =  hibernateDao_ztjc.queryForListWithSql(querySql.toString());
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行电缆查询详细信息时发生时发生异常，告警ID为"+id,e);
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

}
