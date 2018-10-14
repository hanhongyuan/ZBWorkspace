package com.sgcc.pms.zbztjc.jczhgl.bdzzcx.bizc;

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
/**
 * 用户定义逻辑构件
 * 
 * @author admin 
 * 
 */
public class BdzzcxBizc implements IBdzzcxBizc{
	private final Log log = LogFactory.getLog(BdzzcxBizc.class);
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}

	@Resource
	private ILoggerSaveBizc loggerSaveBizc ;
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
					querySql.append(" and LINKEDEQUIPMENTDY  in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					//String[] filterValueArr=filterValue.split(",");
					//for(int j=0;j<filterValueArr.length;j++){
					querySql.append(" and MONITORINGTYPES in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
					//}
				}else if (Constants.YXZT.equals(filterKey)) {
					querySql.append(" and STATUS  in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				}else if (Constants.TYRQ.equals(filterKey)) {
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
		
		
		String cols  = "OBJ_ID,WSDEPMC,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,DEVICEVOLTAGE,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,LOOKUP,LINKEDDEPWS,MONITORINGTYPES,DEVICECODE,DEVICENAME,DEVICEMODEL,MANUFACTURER,IFSS,STATUS,RUNDATE"; 
		String colsTransf = " OBJ_ID,linkedprovicedept,'查看' LOOKUP,XH,LINKEDDEPWS,WSDEPMC,LINKEDEQUIPMENTDY," +
				"DEVICEVOLTAGE,LINKEDSTATIONNAME,LINKEDCAC_DISPLAY,LINKEDEQUIPMENTNAME,DEVICECATEGORY,DEVICECODE,DEVICENAME," +
				"MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,STATUS,RUNDATE ";
		
		String sql=" SELECT t0.OBJ_ID,t0.WSDEPMC,t0.LINKEDSTATIONNAME,t0.LINKEDEQUIPMENTNAME,t0.DEVICEVOLTAGE,t0.DEVICECATEGORY,t0.DEVICECATEGORY_DISPLAY,cast(t0.LOOKUP as varchar2(42)) LOOKUP,t0.LINKEDDEPWS,t0.MONITORINGTYPES,t0.DEVICECODE,t0.DEVICENAME,t0.DEVICEMODEL,t0.MANUFACTURER,t0.IFSS,t0.STATUS,t0.RUNDATE,ROWNUM AS count_id FROM \n" +
				   " (WITH tab AS(SELECT s.*,(CASE WHEN (SELECT COUNT(1) \n"+
				   " FROM MW_APP.CMSV_DEVICEUSED_INFO DI WHERE DI.ZZBM = s.DEVICECODE AND DI.SFSS = 'T')>0 THEN 'T' ELSE 'F' END) SFSS \n"+
				   " FROM (SELECT "+colsTransf+" FROM MW_APP.CMSV_TRANSFDEVICE_XTF t \n"+
				   " where 1 = 1 and t.WSDEPMC is not null and t.DEVICEVOLTAGE is not null and t.status !='00504' "+querySql.toString()+" order by t.XH) s \n"+
				   " where s.WSDEPMC NOT LIKE '%电网%' AND s.WSDEPMC NOT LIKE '%分部%' AND s.MONITORINGTYPES LIKE '02%' AND s.linkedprovicedept IS NOT NULL) \n"+
				   " SELECT tab.*,DECODE(SFSS,'T','是','F','否') IFSS FROM tab where 1 = 1) t0 ";
			try{
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			loggerSaveBizc.updataUserlog("查询", "状态监测-装置管理-变电监测装置查询", "操作成功");
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行变电监测装置查询统计的初始化DataGrid的查询SQL中发生异常",e);
			loggerSaveBizc.updataUserlog("查询", "状态监测-装置管理-变电监测装置查询", "操作失败");
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
		
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		
		return null;
	}
}
