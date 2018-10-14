package com.sgcc.pms.zbztjc.jczhgl.bdzztjfx;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.jczhgl.bdzztjfx.bizc.IBdzztjfxBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/bdzztjfxcontrol") //根据po类名生成
public class BdzztjfxController {
	
    @Resource
    private IBdzztjfxBizc bdzztjfxBizc;
	
	/**
	 * 根据dydj查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/queryBydydj")
	public @ItemResponseBody
	QueryResultObject queryBydydj(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = bdzztjfxBizc.queryBydydj(params);
		
		return queryResult;
	}
	
	/**
	 * 根据jclx查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/queryByjclx")
	public @ItemResponseBody
	QueryResultObject queryByjclx(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = bdzztjfxBizc.queryByjclx(params);
		
		return queryResult;
	}
	
	/**
	 * 根据sccj查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/queryBysccj")
	public @ItemResponseBody
	QueryResultObject queryBysccj(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = bdzztjfxBizc.queryBysccj(params);
		
		return queryResult;
	}
	
	/**
	 * 根据qxly查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/queryByssdw")
	public @ItemResponseBody
	QueryResultObject queryByssdw(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = bdzztjfxBizc.queryByssdw(params);
		return queryResult;
	}
	/**
	 * 注入逻辑构件
	 * @param bdqxtjBizc
	 */
	public void setBdqxtjBizc(IBdzztjfxBizc bdqxtjBizc) {
		this.bdzztjfxBizc = bdqxtjBizc;
	}
}
