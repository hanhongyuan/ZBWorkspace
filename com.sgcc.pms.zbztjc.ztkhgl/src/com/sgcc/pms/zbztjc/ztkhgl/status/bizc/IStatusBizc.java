package com.sgcc.pms.zbztjc.ztkhgl.status.bizc;


import org.springframework.web.bind.annotation.PathVariable;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;


/**
 * 单表场景逻辑构件
 *
 */
public interface IStatusBizc {


	   /**
	    * 获取运行状态的设备列表
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject loadstatusinfo(RequestCondition params);

	   /**
	    * 获取审核详细
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject loadinfo(String id);
	   
	   /**
	    * 保存审核信息
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject saveadui(RequestCondition params);

	   
	   

}
