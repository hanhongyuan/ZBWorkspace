package com.sgcc.pms.zbztjc.jrxxgl.zzjrqk.bizc;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public interface IZzjrqkcxBizc {
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject sdquery(RequestCondition params);
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject bdquery(RequestCondition params);
	
}
