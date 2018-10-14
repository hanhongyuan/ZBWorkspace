package com.sgcc.pms.zbztjc.util.tgyjs.bizc;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public interface ITgyjsBizc {
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject query(RequestCondition queryCondition);

	/**
	 * 初始化字典值
	 * @return QueryResultObject
	 */
	public QueryResultObject initDict();
	
}
