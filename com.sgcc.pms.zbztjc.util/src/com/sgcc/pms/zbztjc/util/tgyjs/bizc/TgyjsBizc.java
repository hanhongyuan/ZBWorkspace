package com.sgcc.pms.zbztjc.util.tgyjs.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.util.myUtils.Util;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class TgyjsBizc implements ITgyjsBizc {
	
	// 日志
	private final static Log logger = LogFactory.getLog(TgyjsBizc.class);

	@Resource
	private IHibernateDao hibernateDao_ztjc;

	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}

	@Override
	public QueryResultObject query(RequestCondition params) {
		logger.info("根据输入条件查询记录：");
		QueryResultObject qro = new QueryResultObject();
		
		try {
			int pageIndex = Integer.valueOf(params.getPageIndex()),
				pageSize = Integer.valueOf(params.getPageSize());
			String cols = params.getColumns(),
			querySql = "SELECT CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID, LINKEDSTATIONNAME, DEVICENAME, DEVICECATEGORY_DISPLAY, CAST('查看' AS VARCHAR2(42)) LOOKUP, " +
					"SFSS, WSDEPMC, DEVICEVOLTAGE, LINKEDEQUIPMENTNAME, DEVICECODE, DEVICECATEGORY, TRANSFPHASE, DEVICEMODEL, MANUFACTURER, RUNDATE " +
					"FROM MW_APP.CMSV_TRANSFDEVICEF T " +
					"WHERE T.WSDEPMC IS NOT NULL AND T.LINKEDEQUIPMENTDY IS NOT NULL AND T.MONITORINGTYPES LIKE '02%' AND MONITORINGTYPES != '026001'";

			if(null != params.getFilter()) {
				String filter = getFilter(params);
				querySql += filter;
			}
//			querySql += " ORDER BY XH";
			
			List result = hibernateDao_ztjc.executeSqlQuery(querySql, pageIndex, pageSize);
			result = transToColumns(result, cols);
			List count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro.addDicItems(wrapDictList());
		} catch (NumberFormatException e) {
			logger.info("根据输入条件查询记录出错：", e);
			e.printStackTrace();
		}
		return null;
	}
	
	private String getFilter(RequestCondition params) {
		List<QueryFilter> qlist = params.getQueryFilter();
		String filter = StringUtils.EMPTY;
		for(QueryFilter q : qlist){
			filter += " AND " + q.getFieldName() + " = " + dbValue(q.getValue());
		}
		return filter;
	}

	@Override
	public QueryResultObject initDict() {
		QueryResultObject query = new QueryResultObject();
		return query.addDicItems(wrapDictList());
	}

	// 将字典对象封装为list
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();

        dicts.add(Util.translateFromFile("sfss", "tgyjs.sfss"));
        dicts.add(Util.translateFromFile("bzdm", "tgyjs.bzdm"));
        
        dicts.add(Util.translateFromDB("xtmc", "MW_APP.CMST_KQ_XT", "OBJ_ID", "XTMC"));
        dicts.add(Util.translateFromDB("monitoringtypes", "MW_APP.CMST_MONITORINGTYPE", "TYPECODE", "TYPENAME", "TYPECODE LIKE '02%' AND TYPECODE NOT IN ('021007', '025001')"));
        dicts.add(Util.translateFromDB("linkedprovicedept", "MW_APP.CMST_ZB_COMM_WSPZ", "WSID", "WSMC"));
		return dicts;
	}
	
	/**
	    * 处理结果，将List中的index 转换成对应的列名
	    * 
	    * @param list
	    * @param columns
	    * @return
	    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   private List<Map> transToColumns(List<Object[]> list, String columns){
       List<Map> items = new ArrayList();
       String[] cols = columns.split("\\,");
       for (int i = 0; i < list.size(); i++){
           Map map = new HashMap();
           for (int m = 0; m < cols.length; m++){
        	   map.put(cols[m], list.get(i)[m]);
           }
           items.add(map);
       }
       return items;
   }
   
   private String strColumns(String strColumns) {
	   String[] columns = strColumns.split(",");
	   arrayColumns(columns);
	   return Util.arrayToString(columns);
   }
   
   // 为了方便调用传参数(例如从map获取的数组) 采用Object类型
   // 利用引用类型的特性 不返回处理后的参数
   private void arrayColumns(Object[] columns) {
		// 用来匹配大写字母
		Pattern aPattern = Pattern.compile("[A-Z]");
		
		// 转换列
		for(int i = 0; i < columns.length; i++) {
			// 通过正则来判断列名是否需要加下划线 因此前台名字要遵循下划线后的字母大写的规则 OBJ_ID => objId
			Matcher aMatcher = aPattern.matcher(columns[i].toString());
			if(aMatcher.find()) {
				String str = aMatcher.group();
				columns[i] = columns[i].toString().replace(str, "_"+ str).toUpperCase();
			} else 
				columns[i] = columns[i].toString().toUpperCase();
		}
   }
   
	private Object dbValue(Object value) {
	   return dbValue(value, null);
	}
	
	private Object dbValue(Object value, Object appendVal) {
	   // 判断类型
	   if(value instanceof Integer) // 数字类型
		   return (Integer)value;
	   
	   else if(value instanceof String) { // 字符串类型
		   String[] strs = ((String) value).split("-");
		   if(strs.length == 3 && Util.isNumeric(strs[1])) //日期
			   return "TO_DATE('"+ value +"', 'yyyy-mm-dd')";
		   else
			   return "'"+ value + (appendVal == null ? StringUtils.EMPTY : appendVal) +"'";
	   }
	   
	   else // 其他类型或为空 应该都不会有追加值的情况
		   return (value == null ? "null" : value);
	}

}
