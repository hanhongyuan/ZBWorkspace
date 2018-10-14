package com.sgcc.pms.zbztjc.bbcxgl.zyxtjczzjrqk.bizc;

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
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class ZyxtjczzjrqkBizc implements IZyxtjczzjrqkBizc{
	
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	@Resource
	private IDataDictionaryBizC dataDictionaryBizC;
	
	private final Log logger = LogFactory.getLog(ZyxtjczzjrqkBizc.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryResultObject zyxtzzjrqkquery(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		try{
			int pageSize = Integer.valueOf(params.getPageSize());
			int pageIndex = Integer.valueOf(params.getPageIndex());
			cols = params.getColumns().toString();
			String sd_sql ="and 1=1";
			String bd_sql ="and 1=1";
			
			if(null!=params.getFilter()){
				List<QueryFilter> qlist = params.getQueryFilter();
				Map<String,Object> paraMap = new HashMap<String, Object>();
				String str =  null;
				for(QueryFilter q : qlist){
					paraMap.put(q.getFieldName(), q.getValue());
					str = ((String) q.getValue()).replace(",", "','");
					if("linkedprovicedept".equals(q.getFieldName())){
						sd_sql +="and  linkedprovicedept in ( '" +  str + "' )";
						bd_sql +="and  linkedprovicedept in ( '" +  str + "' )";
					}else if ("monitoringtypes".equals(q.getFieldName())){
						sd_sql  +=" and monitoringtypes in ( '" +  str + "' ) ";
						bd_sql  +=" and monitoringtypes in ( '" +  str + "' ) ";
					}else if ("linkedstationname".equals(q.getFieldName())){
						bd_sql +=" and linkedstationname like  '%" +  str + "%'  ";
					}else if ("sfss".equals(q.getFieldName())){
						sd_sql +=" and sfss in ( '" +  str + "' ) ";
						bd_sql +=" and sfss in ( '" +  str + "' ) ";
					}else if ("linkedlinename".equals(q.getFieldName())){
						sd_sql +=" and linkedlinename like  '%" +  str + "%'  ";
					}else if ("bzdm".equals(q.getFieldName())){
						sd_sql +=" and bzdm in ( '" +  str + "' ) ";
						bd_sql +=" and bzdm in ( '" +  str + "' ) ";
					} else if("xtmc".equals(q.getFieldName())) {
						sd_sql +=" and xtid in ( '" +  str + "' ) ";
						bd_sql +=" and xtid in ( '" +  str + "' ) ";
					}
				}
				sd_sql += " order by XH";
				bd_sql += " order by XH";
			}else {
				sd_sql += " order by XH";
				bd_sql += " order by XH";
			}
			String querySql =
					"(select * from (select linkedprovicedept,CAST(XTID AS VARCHAR2(42)),LINKEDLINENAME,LINKEDPOLENAME,bzdm,monitoringtypes,DEVICENAME,MANUFACTURER,ATIME,DAYS,SFSS from mw_app.CMSV_ZTJCAPP_SD_DEVINFO_KQV t where t.XTID IS NOT NULL "+sd_sql+" ) UNION ALL\n" +
					" select * from (select linkedprovicedept,CAST(XTID AS VARCHAR2(42)),linkedstationname,linkedequipmentname,bzdm,monitoringtypes,DEVICENAME,MANUFACTURER,ATIME,DAYS,SFSS from MW_APP.CMSV_ZTJCAPP_BD_DEVINFO_KQV t where t.XTID IS NOT NULL "+bd_sql+" ))";
			result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
		} catch(NumberFormatException e) {
			logger.info("主要系统装置接入情况,加载设备列表出错", e);
		} catch(Exception e) {
			logger.info("主要系统装置接入情况,加载设备列表出错", e);
		}
		return qro.addDicItems(wrapDictList());
	}
	
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
		String _sql=" select typename,typecode  from MW_APP.cmst_monitoringtype where  typecode like '01%' or typecode like '02%' ";
		dicts.add(translateFromDBT("monitoringtypes", _sql));
		String deptSql =" select WSMC, WSID from MW_APP.CMST_ZB_COMM_WSPZ";
		dicts.add(translateFromDBT("linkedprovicedept", deptSql));
		String xtmcSql = "select XTMC,CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID from mw_app.cmst_kq_xt ORDER BY XTMC";
		dicts.add(translateFromDBT("xtmc", xtmcSql));
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
	    //String str = realvalue.substring(0,2);
	    if("01".equals(realvalue.substring(0, 2))){
	    	map.put(key, realkey+"(输电)");
	    }else if ("02".equals(realvalue.substring(0, 2))){
	    	map.put(key, realkey+"(变电)");
	    }else{
	    	map.put(key, realkey);
	    }
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
