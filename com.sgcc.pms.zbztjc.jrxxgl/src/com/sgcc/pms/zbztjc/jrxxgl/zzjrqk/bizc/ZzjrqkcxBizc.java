package com.sgcc.pms.zbztjc.jrxxgl.zzjrqk.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.util.SqlFileUtil;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class ZzjrqkcxBizc implements IZzjrqkcxBizc{
	
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	
	private final Log logger = LogFactory.getLog(ZzjrqkcxBizc.class);

	@SuppressWarnings("unchecked")
	public QueryResultObject sdquery(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		try{
			int pageSize = Integer.valueOf(params.getPageSize());
			int pageIndex = Integer.valueOf(params.getPageIndex());
			cols = params.getColumns().toString();
			String querySql = SqlFileUtil.get("SDZZJRQK");
			if(null!=params.getFilter()){
				List<QueryFilter> qlist = params.getQueryFilter();
				Map<String,Object> paraMap = new HashMap<String, Object>();
				String str =  null;
				for(QueryFilter q : qlist){
					paraMap.put(q.getFieldName(), q.getValue());
					str = ((String) q.getValue()).replace(",", "','");
					if("linkedprovicedept".equals(q.getFieldName())){
						querySql +=" and linkedprovicedept in ( '" +  str + "' )  ";
					}else if("monitoringtypes".equals(q.getFieldName())){
						querySql +=" and monitoringtypes in ( '" +  str + "' ) ";
					}else if ("bzdm".equals(q.getFieldName())){
						querySql +=" and bzdm in ( '" + str + "' ) ";
					}else if("manufacturer".equals(q.getFieldName())){
						querySql +=" and manufacturer  like '%" + str + "%' ";
					}else if ("rundate".equals(q.getFieldName())){
						querySql +=" and rundate = to_date('" + str + "','YYYY-MM-DD') ";
					}/*else if ("jsdate".equals(q.getFieldName())){
						querySql +=" and  to_date('" + str + "','YYYY-MM-DD') ";
					}*/else if ("linkedlinename".equals(q.getFieldName())){
						querySql +=" and linkedlinename like '%" + str + "%' ";
					}else if ("sfss".equals(q.getFieldName())){
						querySql +=" and sfss in ('" + str + "') ";
					}
				}
				querySql +=" order by XH";
			}else{
				querySql = querySql;
			}
			result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro.addDicItems(wrapSDDictList());
		} catch(NumberFormatException e){
			logger.info("查询输电装置出错", e);
			e.printStackTrace();
		} catch(Exception e){
			logger.info("查询输电装置出错", e);
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public QueryResultObject bdquery(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		try{
			int pageSize = Integer.valueOf(params.getPageSize());
			int pageIndex = Integer.valueOf(params.getPageIndex());
			cols = params.getColumns().toString();
			String querySql = SqlFileUtil.get("BDZZJRQK");
			if(null!=params.getFilter()){
				List<QueryFilter> qlist = params.getQueryFilter();
				Map<String,Object> paraMap = new HashMap<String, Object>();
				String str =  null;
				for(QueryFilter q : qlist){
					paraMap.put(q.getFieldName(), q.getValue());
					str = ((String) q.getValue()).replace(",", "','");
					if("linkedprovicedept".equals(q.getFieldName())){
						querySql +=" and linkedprovicedept in ( '" +  str + "' )  ";
					}else if("monitoringtypes".equals(q.getFieldName())){
						querySql +=" and monitoringtypes in ( '" +  str + "' ) ";
					}else if ("bzdm".equals(q.getFieldName())){
						querySql +=" and bzdm in ( '" + str + "' ) ";
					}else if("manufacturer".equals(q.getFieldName())){
						querySql +=" and manufacturer in ( '" + str + "' ) ";
					}else if ("rundate".equals(q.getFieldName())){
						querySql +=" and rundate = to_date('" + str + "','YYYY-MM-DD') ";
					}/*else if ("jsdate".equals(q.getFieldName())){
						querySql +=" and  to_date('" + str + "','YYYY-MM-DD') ";
					}*/else if ("linkedstationname".equals(q.getFieldName())){
						querySql +=" and linkedstationname like '%" + str + "%' ";
					}else if ("sfss".equals(q.getFieldName())){
						querySql +=" and sfss in ('" + str + "') ";
					}
				}
				querySql +=" order by XH";
			}else{
				querySql = querySql;
			}
			result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro.addDicItems(wrapBDDictList());
		} catch(NumberFormatException e){
			logger.info("查询输电装置出错", e);
			e.printStackTrace();
		} catch(Exception e){
			logger.info("查询变电装置出错", e);
			e.printStackTrace();
		}
		
		return null;
	}

	
	// 将字典对象封装为list
	private List<DicItems> wrapSDDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
		
		String deptsqlString = "SELECT WSMC, WSID FROM MW_APP.CMST_ZB_COMM_WSPZ order by ZDYPX";
	    dicts.add(translateFromDBT("linkedprovicedept",deptsqlString));
		dicts.add(translateFromFile("monitoringtypes", "Linedevice.MONITORINGTYPES"));
        dicts.add(translateFromFile("bzdm", "Linedevice.VOLTAGEGRADE"));
        dicts.add(translateFromFile("sfss", "Linedevice.ISIPV6"));
		return dicts;
	}
	
	//将变电的数据字典封装成list
	private List<DicItems> wrapBDDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
		
		String deptsqlString = "SELECT WSMC, WSID FROM MW_APP.CMST_ZB_COMM_WSPZ order by ZDYPX";
	    dicts.add(translateFromDBT("linkedprovicedept",deptsqlString));
		dicts.add(translateFromFile("monitoringtypes", "physics.MONITORINGTYPES"));
        dicts.add(translateFromFile("bzdm", "Linedevice.VOLTAGEGRADE"));
        dicts.add(translateFromFile("sfss", "Linedevice.ISIPV6"));
		return dicts;
	}
	
	/**
	 * 通过自定义sql获取数据字典
	 * */
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
	
	// 从属性文件中查询字典
	private DicItems translateFromFile(String fieldName, String dicId) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromFile(
				dicId, "value", "text");
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
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
	public void setDataDictionaryBizC(IDataDictionaryBizC dataDictionaryBizC) {
		this.dataDictionaryBizC = dataDictionaryBizC;
	}
	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}

}
