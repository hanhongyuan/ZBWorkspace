package com.sgcc.pms.zbztjc.util.manufacturerZb;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import com.sgcc.pms.zbztjc.util.manufacturerZb.bizc.IManufacturerZbBizc;
import com.sgcc.uap.rest.annotation.IdRequestBody;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.VoidResponseBody;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/cmstmanufacturerzb")
public class ManufacturerZbController {
	
    @Autowired
    private IManufacturerZbBizc manufacturerZbBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
//    @Transactional
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<Map> save(@ItemsRequestBody List<Map> list){ 
		try {
			return manufacturerZbBizc.saveOrUpdate(list);
		} catch (Exception e) {
			throw new RestClientException("保存方法异常",e);
		}
	}
	
	/**
	 * 删除操作
	 * 
	 * @param list包含map对象
	 *            ，封装ids主键值数组和idName主键名称
	 * @return null
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @VoidResponseBody
	Object delete(@IdRequestBody IDRequestObject idObject) {
		manufacturerZbBizc.remove(idObject);
		return null;
	}
	
	/**
	 * 查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return manufacturerZbBizc.query(params);
	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param id
	 *            url中传递的值
	 */
	@RequestMapping("/{id}")
	public @ItemResponseBody
	QueryResultObject query(@PathVariable String id) {
		if(id.equals("null")) return initDictValues(); // 如果id为空 直接返回数据字典
		return manufacturerZbBizc.queryById(id);
	}
	
	/**
	 * 初始化字典
	 * 
	 * @return QueryResultObject
	 */
	@RequestMapping("/new")
	public @ItemResponseBody
	QueryResultObject initDictValues() {
		return manufacturerZbBizc.initDict();
	}
	
	/**
	 * 注入逻辑构件
	 * @param manufacturerZbBizc
	 */
	public void setManufacturerZbBizc(IManufacturerZbBizc manufacturerZbBizc) {
		this.manufacturerZbBizc = manufacturerZbBizc;
	}
}
