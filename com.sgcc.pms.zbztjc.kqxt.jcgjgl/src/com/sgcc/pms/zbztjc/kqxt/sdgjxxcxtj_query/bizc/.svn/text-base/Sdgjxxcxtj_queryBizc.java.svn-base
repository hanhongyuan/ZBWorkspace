package com.sgcc.pms.zbztjc.kqxt.sdgjxxcxtj_query.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.RestClientException;

import com.sgcc.pms.zbztjc.kqxt.Constants;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class Sdgjxxcxtj_queryBizc implements ISdgjxxcxtj_queryBizc{
	
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(Sdgjxxcxtj_queryBizc.class);
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public QueryResultObject query(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		
		StringBuffer querySql = new StringBuffer();
		
		String cols = "OBJ_ID,LINKEDPROVICEDEPT,LINKEDDEPMC,WSDEPMC,LINKEDLINENAME,XTMC,LINKEDPOLENAME,DEVICEVOLTAGE," +
				"DEVICENAME,TYPENAME,ALARMTIME,ALARMLEVEL,CONTINUANCETIME,ALARMMESSAGE,ishandledName,ishandled";
		
		String sql = "select CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,t.linkeddep,t.LINKEDDEPWS,t.WSDEPMC,T.LINKEDLINENAME,t.XTMC,t.LINKEDPOLENAME," +
				"t.DEVICEVOLTAGE,t.DEVICENAME,t.TYPENAME,TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME,t.ALARMLEVEL,nvl(CONTINUANCETIME,0) CONTINUANCETIME,t.ALARMMESSAGE,\n" +
		" DECODE(ishandled,'T','是','F','否','','否',NULL,'否')ishandledName,ishandled"+
		" from mw_app.CMSV_ALARM_SDINFOF t where T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '01%' AND ALARMNUM IS NOT NULL ";
		
		querySql.append(sql);
		
		if(null != queryCondition.getFilter()){
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
					querySql.append(" AND LINKEDLINENAME LIKE '%"+filterValue.trim()+"%'");
				}else if(Constants.GJJB.equals(filterKey)){
					querySql.append(" AND ALARMNUM IN('"+filterValue.replace(",","','")+"')");
				}else if(Constants.ISHANDLED.equalsIgnoreCase(filterKey)){
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
	
	/**
	 * 根据ID查询详细信息
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List queryDatailById(String id) {
		List resultList = null;
		StringBuffer querySql = new StringBuffer();
		querySql.append(" SELECT DISTINCT CAST(a.obj_id AS VARCHAR2(42)) objId,a.devicecode,a.linkedline,l.linkedlinename,a.MONITORINGTYPE,m.typename,a.alarmtime,");
		querySql.append(" a.alarmlevel,a.alarmmessage,a.ishandled,a.linkedpole,l.linkedpolename,decode(trim(a.alarmsource), 'E', '一次设备告警', 'M', '监测装置告警') alarmsource,a.alarmconstrue,a.disposaladvice,");
		querySql.append(" a.disposaltime,a.disposalresult,a.transactor,a.alarmendtime,a.continuancetime,");
		querySql.append(" a.remarks FROM cmst_alarmlog  a left join cmst_monitoringtype m on m.TYPECODE = a.MONITORINGTYPE left join cmst_linedevice l on l.linkedline = a.linkedline  AND l.linkedpole = a.linkedpole  where a.MONITORINGTYPE LIKE '01%' and a.obj_id = '");
		querySql.append(id);
		querySql.append("'");
		log.info(querySql.toString());
		try{
			resultList =  hibernateDao_ztjc.queryForListWithSql(querySql.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行查询详细信息时发生时发生异常，告警ID为"+id,e);
		}
		return resultList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List queryMonitorRecordById(String id) {
		StringBuffer queryTabelNameSql = new StringBuffer();
		queryTabelNameSql.append("select m.monitoringdatatable,m.typecode from cmst_monitoringtype m ");
		queryTabelNameSql.append("where m.typecode = ");
		queryTabelNameSql.append("(select a.monitoringtype from cmst_alarmlog a where a.obj_id = '");
		queryTabelNameSql.append(id);
		queryTabelNameSql.append("') and m.typecode like '01%' ");
		List resultList = null;
		log.info(queryTabelNameSql.toString());
		try{
			List<Object[]> list = hibernateDao_ztjc.executeSqlQuery(queryTabelNameSql.toString());
			String typeCode = list.get(0)[1].toString();
			String tableName = list.get(0)[0].toString();
			StringBuffer querySql = new StringBuffer();
			if("013004".equals(typeCode)){			//如果是风偏监测
				querySql.append("SELECT ACQUISITION_TIME,WINDAGE_YAW_ANGLE,DEFLECTION_ANGLE,LEAST_CLEARANCE FROM ");
			}else if("012001".equals(typeCode)){		//如果是杆塔倾斜监测
				querySql.append("SELECT ACQUISITION_TIME,INCLINATION,INCLINATION_X,INCLINATION_Y,ANGLE_X,ANGLE_Y FROM  ");
			}else if("013001".equals(typeCode)){		//如果是导线弧垂监测
				querySql.append("SELECT ACQUISITION_TIME,CONDUCTOR_SAG,TOGROUND_DISTANCE FROM  ");
			}else if("013002".equals(typeCode)){		//如果是导线温度监测
				querySql.append("SELECT ACQUISITION_TIME,LINE_TEMPERATURE1,LINE_TEMPERATURE2 FROM  ");
			}else if("013003".equals(typeCode)){		//如果是微风振动监测
				querySql.append("SELECT ACQUISITION_TIME,VIBRATION_AMPLITUDE,VIBRATION_FREQUENCY FROM  ");
			}else if("013005".equals(typeCode)){		//如果是覆冰监测
				querySql.append("SELECT ACQUISITION_TIME,EQUAL_ICETHICKNESS,TENSION,TENSION_DIFFERENCE,WINDAGE_YAW_ANGLE,DEFLECTION_ANGLE FROM  ");
			}else if("013006".equals(typeCode)){		//如果是导线舞动监测
				querySql.append("SELECT ACQUISITION_TIME,U_GALLOP_AMPLITUDE,U_VERTICAL_AMPLITUDE,U_HORIZONTAL_AMPLITUDE,U_ANGLETOVERTICAL,U_GALLOP_FREQUENCY FROM  ");
			}else if("014001".equals(typeCode)){		//如果是现场污秽度检测
				querySql.append("SELECT ACQUISITION_TIME,NSDD,ESDD,DAILY_MIN_TEMPERATURE,DAILY_MAX_TEMPERATURE,DAILY_MAX_HUMIDITY,DAILY_MIN_HUMIDITY FROM  ");
			}else if("018001".equals(typeCode)){		//如果是微气象监测
				querySql.append("SELECT ACQUISITION_TIME,AIR_TEMPERATURE,HUMIDITY,AVERAGE_WINDSPEED_10MIN,AVERAGE_WINDDIRECTION_10MIN,MAX_WINDSPEED,EXTREME_WINDSPEED,STANDARD_WINDSPEED,AIR_PRESSURE,PRECIPITATION,PRECIPITATION_INTENSITY,RADIATION_INTENSITY FROM  ");
			}
			
			querySql.append(tableName);
			querySql.append(" w,Cmst_Alarmlog a ");
			querySql.append("WHERE w.DEVICECODE = a.DEVICECODE ");
			querySql.append(" and ACQUISITION_TIME >= a.alarmtime and ACQUISITION_TIME <= NVL(a.alarmendtime,sysdate) ");
			querySql.append("and a.obj_id ='"+id+"'");
			log.info(querySql.toString());
			resultList = hibernateDao_ztjc.queryForListWithSql(querySql.toString());
			resultList.add(typeCode);
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			throw new RestClientException("监测类型表名不存在", e);
		}
	}

}
