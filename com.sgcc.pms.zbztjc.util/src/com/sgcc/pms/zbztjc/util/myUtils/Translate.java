package com.sgcc.pms.zbztjc.util.myUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.DicItems;

class Translate {

	@Autowired
	private static IDataDictionaryBizC dataDictionaryBizC;

	@Autowired
	private static IHibernateDao hibernateDao_ztjc;

	public static String getDms(String dydjfw) {
		if(Util.isEmpty(dydjfw)) return null;
		
		List<Map<String, String>> translates = translateFromFile("dm", "DYDJ.DM").getValues();
		String[] dydjs = dydjfw.split(",");
		int i = 0;
		for(String dydj : dydjs) {
			for(Map<String, String> map : translates) {
				if(map.get("text").equals(dydj)) {
					dydjs[i] = map.get("value");
					i++;
					break;
				}
			}
		}
		return Util.arrayToString(dydjs);
	}

	public static String getDydj(String dm) {
		if(Util.isEmpty(dm)) return null;
		
		List<Map<String, String>> translates = translateFromFile("dm", "DYDJ.DM").getValues();
		for(Map<String, String> map : translates) {
			if(map.get("value").equals(dm)) {
				dm = map.get("text");
				break;
			}
		}
		return dm;
	}

	// 从属性文件中查询字典
	public static DicItems translateFromFile(String fieldName, String dicId) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromFile(
				dicId, "value", "text");
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	// 从数据库中查询字典
	public static DicItems translateFromDB(String fieldName, String tableName, String keyField, String valueField, String where) {
		
		try {
			List<Map<String, String>> aList = new LinkedList<Map<String, String>>();
			
			String sql = "SELECT "+ valueField +", CAST("+ keyField +" AS VARCHAR2(42)) FROM "+ tableName + (where==null ? StringUtils.EMPTY : " WHERE "+ where)
					+" ORDER BY "+ valueField;
			List lt = hibernateDao_ztjc.executeSqlQuery(sql);
			Iterator iterator = lt.iterator();
			while (iterator.hasNext()) {
		    	Object[] keyvalue = (Object[])iterator.next();
		            if (keyvalue.length == 2) {
		              Map<String, String> map = keyvalueToMap("text", "value", String.valueOf(keyvalue[0]), String.valueOf(keyvalue[1]));
		              aList.add(map);
		            }
		    }
			
			DicItems dict = new DicItems();
			dict.setName(fieldName);
			dict.setValues(aList);
			return dict;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
    
	/**
	 *将查询结果封装成DropDownEditor需要格式的map
	*/
	private static Map<String, String> keyvalueToMap(String key, String value, String realkey, String realvalue){
	    Map map = new HashMap();
	    map.put(key, realkey);
	    map.put(value, realvalue);
	    return map;
	}
	
	/**
	 * 注入数据字典构件
	 * @param dataDictionaryBizC
	 */
	public static void setDataDictionaryBizC(IDataDictionaryBizC dataDictionaryBizC) {
		Translate.dataDictionaryBizC = dataDictionaryBizC;
	}
	
	public static void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		Translate.hibernateDao_ztjc = hibernateDao_ztjc;
	}

}
