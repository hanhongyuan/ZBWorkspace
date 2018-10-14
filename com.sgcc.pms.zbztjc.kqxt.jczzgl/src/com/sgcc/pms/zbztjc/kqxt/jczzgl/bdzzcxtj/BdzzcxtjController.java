package com.sgcc.pms.zbztjc.kqxt.jczzgl.bdzzcxtj;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.kqxt.jczzgl.bdzzcxtj.bizc.IBdzzcxtjBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/zbbdzzcxtjControl")
public class BdzzcxtjController {
	@Resource
	private IBdzzcxtjBizc bdzzcxtjBizc;

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
		return bdzzcxtjBizc.query(params);
	}
	
	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzcxtjBizc.statByDydj(params);
	}
	
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzcxtjBizc.statByJclx(params);
	}
	
	@RequestMapping("/sccjtj")
	public @ItemResponseBody
	QueryResultObject statBySccj(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzcxtjBizc.statBySccj(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzcxtjBizc.getDetailList(params);
	}

	
	@RequestMapping("/getFGBDZList")
	public @ItemResponseBody
	QueryResultObject getFGBDZList(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzcxtjBizc.getFGBDZList(params);
	}
}
