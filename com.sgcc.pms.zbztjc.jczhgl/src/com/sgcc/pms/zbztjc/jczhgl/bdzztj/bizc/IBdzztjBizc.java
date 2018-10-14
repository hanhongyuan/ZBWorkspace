package com.sgcc.pms.zbztjc.jczhgl.bdzztj.bizc;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public interface IBdzztjBizc {
	/**
	 * 按电压等级统计
	 * @param params
	 * @return
	 */
	public QueryResultObject statByDydj(RequestCondition params);
	
	/**
	 * 按监测类型统计
	 * @param params
	 * @return
	 */
	public QueryResultObject statByJclx(RequestCondition params);
	
	/**
	 * 按生产厂家统计
	 * @param params
	 * @return
	 */
	public QueryResultObject statBySccj(RequestCondition params);
	
	/**
	 * 查询变电统计详细信息
	 * @param params
	 * @return
	 */
	public QueryResultObject getDetailList(RequestCondition params);
	/**
	 * 查询变电统计覆盖变电站数详细信息
	 * @param params
	 * @return
	 */
	public QueryResultObject getFGBDZList(RequestCondition params);
	
	
	
	
}
