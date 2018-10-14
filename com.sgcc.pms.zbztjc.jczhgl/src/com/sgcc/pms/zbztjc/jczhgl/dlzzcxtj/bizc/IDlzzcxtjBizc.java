package com.sgcc.pms.zbztjc.jczhgl.dlzzcxtj.bizc;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IDlzzcxtjBizc {

	
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject query(RequestCondition queryCondition);
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);
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
	 * 查询电缆统计详细信息
	 * @param params
	 * @return
	 */
	public QueryResultObject getDetailList(RequestCondition queryCondition);
	/**
	 * 查询电缆统计覆盖线路及通道数详细信息
	 * @param params
	 * @return
	 */
	public QueryResultObject getFGBDZList(RequestCondition queryCondition);
	
}
