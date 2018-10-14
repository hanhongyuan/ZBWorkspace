package com.sgcc.pms.zbztjc.deptip.bizc;


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

import com.sgcc.pms.zbztjc.myUtils.Util;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 用户定义逻辑构件
 * 
 * @author Jarvis 
 * 
 */
public class DeptipBizc implements IDeptipBizc{

	@Autowired
	private IHibernateDao hibernateDao_ztjc;
	
	// 日志
	private final static Log logger = LogFactory.getLog(DeptipBizc.class);
	

	
	/**
	 * 保存更新
	 * 
	 * @param list
	 * @return 
	 */
	public List saveOrUpdate(List<Map> list) {
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			if(map.containsKey("OBJ_ID")){
				String id = (String)map.get("OBJ_ID");
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
			String sql = "INSERT INTO MW_SYS.CMST_DEPTIDIP(OBJ_ID, OBJ_DISPIDX, DEPTNAME, DEPTID, DEPTIP, BZ,IPPORT )" +
					"select mw_sys.newguid || '-' || substr('0000' || rownum, -5, 5),mw_sys.mwq_obj_dispidx.nextval," +
						dbValue(map.get("DEPTNAME")) +"," +
						dbValue(map.get("DEPTID")) +"," +
						dbValue(map.get("DEPTIP")) +"," +
						dbValue(map.get("BZ")) +"," +
						dbValue(map.get("IPPORT")) +" from dual";
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
	
			// 拼接sql
			for(int i = 0; i < keys.length; i++) {
				if(i > 0) set += ", ";
				set += keys[i].toString().toUpperCase();
				set += "="+ dbValue(values[i]);
			}
			String sql = "UPDATE MW_SYS.CMST_DEPTIDIP SET " + set + " WHERE OBJ_ID = ?";
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
				String sql = "DELETE FROM MW_SYS.CMST_DEPTIDIP WHERE OBJ_ID = ?";
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
					querySql = "SELECT CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID, DEPTNAME, DEPTID, DEPTIP, BZ,IPPORT " +
							"FROM MW_SYS.CMST_DEPTIDIP";
		
			List result = hibernateDao_ztjc.executeSqlQuery(querySql, pageIndex, pageSize);
			result = transToColumns(result, cols);
			List count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro;
		} catch (NumberFormatException e) {
			logger.info("根据输入条件查询记录出错：", e);
			e.printStackTrace();
		}
		return null;
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
			String sql = "SELECT CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID, DEPTNAME, DEPTID, DEPTIP, BZ,IPPORT " +
								"FROM MW_SYS.CMST_DEPTIDIP WHERE OBJ_ID = '" + id + "'";
			
			List result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result, "OBJ_ID,DEPTNAME,DEPTID,DEPTIP,BZ,IPPORT");
			qro.setItems(result);
			return qro;
		} catch (Exception e) {
			logger.info("错误信息", e);
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
			   return "'"+ value + Util.ternary(appendVal, StringUtils.EMPTY) +"'";
	   }
	   
	   else // 其他类型或为空 应该都不会有追加值的情况
		   return Util.ternary(value, "null");
	}
	public QueryResultObject initDict() {
		QueryResultObject query = new QueryResultObject();
		return query;
	}

	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}
}
