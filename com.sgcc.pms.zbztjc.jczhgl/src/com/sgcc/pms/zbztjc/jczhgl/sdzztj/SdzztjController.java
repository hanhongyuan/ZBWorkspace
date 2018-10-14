package com.sgcc.pms.zbztjc.jczhgl.sdzztj;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.jczhgl.sdzztj.bizc.ISdzztjBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/sdzztjcontrol")
public class SdzztjController {
	@Resource
	private ISdzztjBizc sdzztjBizc;

	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  sdzztjBizc.statByDydj(params);
	}
	
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  sdzztjBizc.statByJclx(params);
	}
	
	@RequestMapping("/sccjtj")
	public @ItemResponseBody
	QueryResultObject statBysccj(@QueryRequestParam("params") RequestCondition params) {
		return  sdzztjBizc.statBySccj(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  sdzztjBizc.getDetailList(params);
	}
	
	@RequestMapping("/getDetailLists")
	public @ItemResponseBody
	QueryResultObject getDetailLists(@QueryRequestParam("params") RequestCondition params) {
		return  sdzztjBizc.getDetailLists(params);
	}
	

	@RequestMapping("/getFGXLSList")
	public @ItemResponseBody
	QueryResultObject getFGXLSList(@QueryRequestParam("params") RequestCondition params) {
		return  sdzztjBizc.getFGXLSList(params);
	}

}
