package com.sgcc.pms.zbztjc.util.manufacturerZb.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.pms.zbztjc.util.myUtils.Util;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 用户定义逻辑构件
 * 
 * @author Jarvis 
 * 
 */
public class ManufacturerZbBizc implements IManufacturerZbBizc{

	@Autowired
	private IHibernateDao hibernateDao_ztjc;
	
	// 日志
	private final static Log logger = LogFactory.getLog(ManufacturerZbBizc.class);
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 * @return 
	 */
	public List saveOrUpdate(List<Map> list) {
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			if(map.containsKey("objId")){
				String id = (String)map.get("objId");
				update(map, id);
			} else {
				save(map);
			}
		}
		return list;
	}

	// 保存记录
	private void save(Map map) {
		logger.info("保存记录：");
		try {
			String sql = "INSERT INTO MW_APP.CMST_MANUFACTURER_ZB(OBJ_ID, OBJ_DISPIDX, NAME, CONTACT, " +
					"ADDRESS, DISPLAY_NAME, EVER_NAME, CREAT_TIME, LASTMODIFY_TIME, STATUS, CREAT_PROVINCE, CODE) " +
					"values(" +
						dbValue(map.get("mxVirtualId"), "-10000") +"," +
						"03579888," +
						dbValue(map.get("name")) +"," +
						dbValue(map.get("contact")) +"," +
						dbValue(map.get("address")) +"," +
						dbValue(map.get("displayName")) +"," +
						dbValue(map.get("everName")) +"," +
						dbValue(map.get("creatTime")) +"," +
						dbValue(map.get("lastmodifyTime")) +"," +
						dbValue(map.get("status")) +"," +
						dbValue(map.get("creatProvince")) +"," +
						dbValue(map.get("code")) +
					")";
			hibernateDao_ztjc.executeSqlUpdate(sql);
		} catch (Exception e) {
			logger.info("保存记录出错：", e);
			e.printStackTrace();
		}
	}
	
	// 更新记录
	private void update(Map map, String id) {
		logger.info("更新记录：");
		try {
			Object[] keys = map.keySet().toArray();
			Object[] values = map.values().toArray();
			String set = "";
			// 用来匹配大写字母
			Pattern aPattern = Pattern.compile("[A-Z]");
			
			// 拼接sql
			for(int i = 0; i < keys.length; i++) {
				if(i > 0) set += ", ";
				
				// 通过正则来判断列名是否需要加下划线 因此前台名字要遵循下划线后的字母大写的规则 OBJ_ID => objId
				Matcher aMatcher = aPattern.matcher(keys[i].toString());
				if(aMatcher.find()) {
					String str = aMatcher.group();
					set += keys[i].toString().replace(str, "_"+ str).toUpperCase();
				} else 
					set += keys[i].toString().toUpperCase();
				set += "="+ dbValue(values[i]);
			}
			String sql = "UPDATE MW_APP.CMST_MANUFACTURER_ZB SET " + set + " WHERE OBJ_ID = ?";
			hibernateDao_ztjc.executeSqlUpdate(sql, new Object[]{ id });
		} catch (Exception e) {
			logger.info("更新记录出错：", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject) {
		logger.info("删除: ");
		String[] ids = idObject.getIds();
		try {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				String sql = "DELETE FROM MW_APP.CMST_MANUFACTURER_ZB WHERE OBJ_ID = ?";
				hibernateDao_ztjc.executeSqlUpdate(sql, new Object[] { id });
			}
		} catch (Exception e) {
			logger.info("删除出错: ", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition params) {
		logger.info("根据输入条件查询记录：");
		QueryResultObject qro = new QueryResultObject();
		
		try {
			int pageIndex = Integer.valueOf(params.getPageIndex()),
				pageSize = Integer.valueOf(params.getPageSize());
			String cols = params.getColumns(),
					querySql = "SELECT CAST(OBJ_ID AS VARCHAR2(42)), NAME, CONTACT, ADDRESS, DISPLAY_NAME, " +
							"EVER_NAME, CREAT_TIME, LASTMODIFY_TIME, STATUS, CREAT_PROVINCE, CODE " +
							"FROM MW_APP.CMST_MANUFACTURER_ZB WHERE 1 = 1";

			if(null != params.getFilter()) {
				String filter = getFilter(params);
				querySql += filter;
			}
//			querySql += " ORDER BY TYPENAME";
			
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
			Object value = dbValue(q.getValue());
			if(!value.toString().contains("'"))
				filter += " AND " + dbColumn(q.getFieldName()) + " = " + value;
			else {
				value = value.toString().replace("'", "%");
				filter += " AND " + dbColumn(q.getFieldName()) + " like '" + value + "'";
			}
		}
		return filter;
	}
	   
	private String dbColumn(String column) {
		// 用来匹配大写字母
		Pattern aPattern = Pattern.compile("[A-Z]");
		
		// 转换列
		// 通过正则来判断列名是否需要加下划线 因此前台名字要遵循下划线后的字母大写的规则 OBJ_ID => objId
		Matcher aMatcher = aPattern.matcher(column);
		if(aMatcher.find()) {
			String str = aMatcher.group();
			column = column.replace(str, "_"+ str).toUpperCase();
		} else 
			column = column.toUpperCase();
		return column;
	}
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		logger.info("查询单条记录：");
		QueryResultObject qro = new QueryResultObject();
		
		try {
			String sql = "SELECT CAST(OBJ_ID AS VARCHAR2(42)), NAME, CONTACT, ADDRESS, DISPLAY_NAME, " +
								"EVER_NAME, CREAT_TIME, LASTMODIFY_TIME, STATUS, CREAT_PROVINCE, CODE " +
								"FROM MW_APP.CMST_MANUFACTURER_ZB WHERE OBJ_ID = '" + id + "'";
			
			List result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result, "objId,name,contact,address,displayName,everName,creatTime,lastmodifyTime,status,creatProvince,code");
			qro.setItems(result);
			return qro.addDicItems(wrapDictList());
		} catch (Exception e) {
			logger.info("查询单条记录出错：", e);
			e.printStackTrace();
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

	/**
	 * 初始化字典值
	 * 
	 * @return QueryResultObject
	 */
	public QueryResultObject initDict() {
		QueryResultObject query = new QueryResultObject();
		return query.addDicItems(wrapDictList());
	}

	// 将字典对象封装为list
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();

        dicts.add(Util.translateFromFile("status", "manufacturerZb.status"));
		return dicts;
	}

	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}
}
