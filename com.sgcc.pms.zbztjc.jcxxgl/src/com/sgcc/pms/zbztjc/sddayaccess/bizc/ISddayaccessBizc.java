package com.sgcc.pms.zbztjc.sddayaccess.bizc;


import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public interface ISddayaccessBizc {
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject loadtjxx(RequestCondition params);
	
	
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject loadyfxx(RequestCondition params);
	
	
	
}
