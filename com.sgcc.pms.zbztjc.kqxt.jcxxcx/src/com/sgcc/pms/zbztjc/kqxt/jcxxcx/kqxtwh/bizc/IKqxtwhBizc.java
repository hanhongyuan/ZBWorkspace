package com.sgcc.pms.zbztjc.kqxt.jcxxcx.kqxtwh.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;


/**
 * 单表场景逻辑构件
 *
 */
public interface IKqxtwhBizc {


	   /**
	    * 获取跨区系统信息
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject getKqxtInfo(RequestCondition params);
	   
	   /**
	    * 保存跨区系统信息
	    * @param bdxxcx
	    * @return
	    */
	   public List<Map<String,String>> saveOrUpdate(List<Map<String,String>> list);
	   

	   
	   

	   

}
