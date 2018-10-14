package com.sgcc.pms.zbztjc.jczhgl.bdzzcx;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.jczhgl.bdzzcx.bizc.IBdzzcxBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/cmsvtransfdevicext") //根据po类名生成
public class BdzzcxController {
	
    @Resource
    private IBdzzcxBizc bdzzcxBizc;
	
	

	
	/**
	 * 查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return bdzzcxBizc.query(params);
		
		
		
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
		return bdzzcxBizc.queryById(id);
	}
	



	/**
	 * 注入逻辑构件
	 * @param bdzzcxBizc
	 */
	public void setBdzzcxBizc(IBdzzcxBizc bdzzcxBizc) {
		this.bdzzcxBizc = bdzzcxBizc;
	}
}
