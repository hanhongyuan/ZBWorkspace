package com.sgcc.pms.zbztjc.kqxt.jczzgl.sdzzcxtj;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.kqxt.jczzgl.sdzzcxtj.bizc.ISdzzcxtjBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/zbsdzzcxtjControl")

public class SdzzcxtjController {

	@Resource
	private ISdzzcxtjBizc sdzzcxtjBizc;
	
	 /**
     * 输电监测装置查询
     * @param params
     * @return
     */
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return sdzzcxtjBizc.query(params);
	}
    
	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzcxtjBizc.statByDydj(params);
	}
	
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzcxtjBizc.statByJclx(params);
	}
	
	@RequestMapping("/sccjtj")
	public @ItemResponseBody
	QueryResultObject statBysccj(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzcxtjBizc.statBySccj(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzcxtjBizc.getDetailList(params);
	}
	

	@RequestMapping("/getFGXLSList")
	public @ItemResponseBody
	QueryResultObject getFGXLSList(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzcxtjBizc.getFGXLSList(params);
	}
	
	@RequestMapping("/getJclxDetailList")
	public @ItemResponseBody
	QueryResultObject getJclxDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzcxtjBizc.getJclxDetailList(params);
	}


}
