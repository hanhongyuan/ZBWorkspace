package com.sgcc.pms.zbztjc.dlgjxxcxtj_stat.bizc;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public interface IDlgjxxcxtj_statBizc {

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
	 * 获取详细信息列表
	 * @param params
	 * @return
	 */
	public QueryResultObject getDetailList(RequestCondition params);
	
}
