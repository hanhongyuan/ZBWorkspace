package com.sgcc.pms.zbztjc.myUtils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.rest.support.DicItems;

class Translate {

	@Autowired
	private static IDataDictionaryBizC dataDictionaryBizC;
	
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
	public static DicItems translateFromDB(String fieldName, String poName,String keyField, String valueField) {
		List<Map<String, String>> list = dataDictionaryBizC.translateFromDB(
				poName, "value", "text", keyField, valueField,"1=1");
		DicItems dict = new DicItems();
		dict.setName(fieldName);
		dict.setValues(list);
		return dict;
	}
	
	/**
	 * 注入数据字典构件
	 * @param dataDictionaryBizC
	 */
	public static void setDataDictionaryBizC(IDataDictionaryBizC dataDictionaryBizC) {
		Translate.dataDictionaryBizC = dataDictionaryBizC;
	}

}
