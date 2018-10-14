package com.sgcc.pms.zbztjc.util.manufacturerZb.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 单表场景逻辑构件
 *
 */
public interface IManufacturerZbBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List saveOrUpdate(List<Map> ll);
	
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
	 * 初始化字典值
	 * @return QueryResultObject
	 */
	public QueryResultObject initDict();

	public void remove(IDRequestObject idObject);
}
