package com.sgcc.pms.zbztjc.jczhgl.common.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;

public class CommonBizc implements ICommonBizc{

	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(CommonBizc.class);
	
	
	@SuppressWarnings("rawtypes")
	public QueryResultObject getDropDownEditorById(String sqlId) {
		QueryResultObject qro = new QueryResultObject();
		List resultList = null;
		try{
			resultList = hibernateDao_ztjc.executeSqlQueryByID(sqlId);
			if(null != resultList && 0 < resultList.size()){		//如果LIST不为空
				List<Map<String,String>> dicts = new ArrayList<Map<String,String>>();		//封装数据成一个元素为Map<String,String>的List
				for (Object result : resultList) {
					Object[] obj = (Object[]) result;
					Map<String,String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(obj[0]));
					map.put("text", String.valueOf(obj[1]));
					dicts.add(map);
				}
				qro.setItems(dicts);
			}
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行初始化下拉框时发生异常",e);
		}
		
		
		return null;
	}

}
