package com.sgcc.pms.zbztjc.bbcxgl.bdzzjrqk.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class BdzzjrqkBizc implements IBdzzjrqkBizc{
	
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	@Resource
	private IDataDictionaryBizC dataDictionaryBizC;
	
	private final Log logger = LogFactory.getLog(BdzzjrqkBizc.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryResultObject bdquery(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		try{
			int pageSize = Integer.valueOf(params.getPageSize());
			int pageIndex = Integer.valueOf(params.getPageIndex());
			cols = params.getColumns().toString();
			String querySql =" select linkedprovicedept,linkedstationname,linkedequipmentname,bzdm,monitoringtypes,DEVICENAME,MANUFACTURER,ATIME,DAYS,SFSS from MW_APP.CMSV_ZTJCAPP_BD_DEVINFO_KQV   where 1=1 ";
			if(null!=params.getFilter()){
				List<QueryFilter> qlist = params.getQueryFilter();
				Map<String,Object> paraMap = new HashMap<String, Object>();
				String str =  null;
				for(QueryFilter q : qlist){
					paraMap.put(q.getFieldName(), q.getValue());
					str = ((String) q.getValue()).replace(",", "','");
					if("linkedprovicedept".equals(q.getFieldName())){
						querySql +=" and linkedprovicedept in ( '" +  str + "' ) ";
					}else if ("monitoringtypes".equals(q.getFieldName())){
						querySql +=" and monitoringtypes in ( '" +  str + "' ) ";
					}else if ("manufacturer".equals(q.getFieldName())){
						querySql +=" and manufacturer like  '%" +  str + "%'  ";
					}else if ("sfss".equals(q.getFieldName())){
						querySql +=" and sfss in ( '" +  str + "' ) ";
					}else if ("linkedstationname".equals(q.getFieldName())){
						querySql +=" and linkedstationname like  '%" +  str + "%'  ";
					}else if ("bzdm".equals(q.getFieldName())){
						querySql +=" and bzdm in ( '" +  str + "' ) ";
					}
				}
				querySql += " order by XH";
			}else {
				querySql = querySql+" order by XH";
			}
			result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
		} catch(NumberFormatException e) {
			logger.info("变电装置接入情况,加载设备列表出错", e);
		} catch(Exception e) {
			logger.info("变电装置接入情况,加载设备列表出错", e);
		}
		return qro.addDicItems(wrapDictList());
	}
	
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
		String _sql=" select typename,typecode  from MW_APP.cmst_monitoringtype where typecode  like '02%'";
		dicts.add(translateFromDBT("monitoringtypes", _sql));
		String deptSql =" select WSMC, WSID from MW_APP.CMST_ZB_COMM_WSPZ";
		dicts.add(translateFromDBT("linkedprovicedept", deptSql));
		dicts.add(translateFromFile("bzdm", "Linedevice.VOLTAGEGRADE"));
		dicts.add(translateFromFile("sfss", "Linedevice.SFSS"));
		return dicts;
	}
	
	private DicItems translateFromFile(String fieldName, String dicId) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromFile(
				dicId, "value", "text");
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	private DicItems translateFromDBT(String fieldName,String sql) {
		List retlist = new LinkedList();
		List lt = hibernateDao_ztjc.executeSqlQuery(sql);
		Iterator iterator = lt.iterator();
	    while (iterator.hasNext()) {
	    	Object[] keyvalue = (Object[])iterator.next();
	            if (keyvalue.length == 2) {
	              Map map = keyvalueToMap( "text","value",String.valueOf(keyvalue[0]), String.valueOf(keyvalue[1]));
	              retlist.add(map);
	            }
	    }
	    List<Map<String, String>> list = retlist;
	      

      DicItems dict = new DicItems();
      dict.setName(fieldName);
      dict.setValues(list);
      return dict;
	}
	    
	    
    /**
     *将查询结果封装成DropDownEditor需要格式的map
     * */
    private Map<String, String> keyvalueToMap(String key,String value,String realkey, String realvalue){
	    Map map = new HashMap();
	    map.put(key, realkey);
	    map.put(value, realvalue);
	    return map;
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
	   
	   /**
		 *注入数据字典构件
		 * 
		 * @param dataDictionaryBizC
		 */
		public void setDataDictionaryBizC(IDataDictionaryBizC dataDictionaryBizC) {
			this.dataDictionaryBizC = dataDictionaryBizC;
		}
		
		public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
			this.hibernateDao_ztjc = hibernateDao_ztjc;
		}

}
