package com.sgcc.pms.zbztjc.bddevicequery.bizc;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;


/**
 * 单表场景逻辑构件
 *
 */
public interface IBddeviceBizc {


	   /**
	    * 获取未投运的设备列表
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject getWtysblb(RequestCondition params);
	   
	   /**
	    * 获取未投运的设备列表
	    * @param bdxxtj
	    * @return
	    */
	   public QueryResultObject gettjxx(RequestCondition params);
	   
	   

	   

}
