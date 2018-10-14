package com.sgcc.pms.zbztjc.jczhgl.sdkhzb.bizc;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public interface ISdkhzbBizc {
	
	/**
	 * 初始化的查询方法
	 * @param params
	 * @return
	 */
	public QueryResultObject query(RequestCondition params);
	

	/**
	 * 查询输电考核指标详细信息
	 * @param params
	 * @return
	 */
	public QueryResultObject getDetailList(RequestCondition params);
	
	public QueryResultObject getdataDetailList(RequestCondition params);
	
}
