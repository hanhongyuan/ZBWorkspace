package com.sgcc.pms.zbztjc.ztkhgl.jczbcx.bizc;


import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;


/**
 * 单表场景逻辑构件
 *
 */
public interface IJczbcxBizc {


	   /**
	    * 获取指标信息
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject loadinfo(RequestCondition params);


	   
	   

}
