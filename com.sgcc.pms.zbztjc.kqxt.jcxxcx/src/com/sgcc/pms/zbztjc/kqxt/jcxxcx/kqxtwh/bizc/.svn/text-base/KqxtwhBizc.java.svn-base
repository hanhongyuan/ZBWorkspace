package com.sgcc.pms.zbztjc.kqxt.jcxxcx.kqxtwh.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;



/**
 * 用户定义逻辑构件
 * 
 * @author Administrator 
 * 
 */
public class KqxtwhBizc implements IKqxtwhBizc{


	private final Log logger = LogFactory.getLog(KqxtwhBizc.class);
	@Resource
	private IDataDictionaryBizC dataDictionaryBizC;
	@Resource
	private IHibernateDao hibernateDao_ztjc;


	
	 public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}


	/**
	    * 变电监测信息查询
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject getKqxtInfo(RequestCondition params) {
		   QueryResultObject qro = new QueryResultObject();
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			  // cols = params.getColumns().toString();
			   cols = "OBJ_ID,XTJC,XTMC";
			   String querySql = " select OBJ_ID,XTJC,XTMC from mw_app.cmst_kq_xt ";
			   result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
		   } catch (Exception e) {
			   logger.info("变电装置查询管理，加载设备列表出错", e);
			   e.printStackTrace();
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


	    @Transactional
		@Override
		public List<Map<String, String>> saveOrUpdate(
				List<Map<String, String>> list) {
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

	    
		private void save(Map map) {
			logger.info("保存记录：");
			try {
				String insertSql = "INSERT INTO MW_APP.CMST_KQ_XT\n" +
					    " (OBJ_ID,XTMC,XTJC)\n" + 
					    "  SELECT mw_sys.newguid || '-' || substr('0000' || rownum, -5, 5),\n" + 
						"'',''" + 
						" FROM dual ";
				hibernateDao_ztjc.executeSqlUpdate(insertSql);
			} catch (Exception e) {
				logger.info("保存记录出错：", e);
				e.printStackTrace();
			}
		}
		
		//更新记录
		private void update(Map map, String id) {
			logger.info("更新记录：");
			try {
				Object[] keys = map.keySet().toArray();
				Object[] values = map.values().toArray();
				String set = "";
				for(int i = 0; i < keys.length; i++) {
					if(i > 0) set += ", ";
					set += keys[i] + "=" + dbValue(values[i]);
				}
//				set = set.replace("OBJ_ID", "OBJ_ID").toUpperCase();
				// hibernate会自动给参数加上单引号 很贴心
				String sql = "UPDATE MW_APP.cmst_kq_xt SET " + set + " WHERE OBJ_ID = ?";
				hibernateDao_ztjc.executeSqlUpdate(sql, new Object[]{ id });
			} catch (Exception e) {
				logger.info("更新记录出错：", e);
				e.printStackTrace();
			}
		}
		
		private Object dbValue(Object value) {
			return dbValue(value, null);
		}
		   
		private Object dbValue(Object value, Object appendVal) {
			   // 判断类型
		   if(value instanceof Integer) // 数字类型
			   return ((Integer)value) +(appendVal==null ? 0 : (Integer)appendVal);
		   
		   else if(value instanceof String) // 字符串类型
			   return "'"+ value +(appendVal==null ? StringUtils.EMPTY : appendVal) +"'";
		   
		   else // 其他类型或为空 应该都不会有追加值的情况
			   return value==null ? "null" : value;
	   }

		
}

