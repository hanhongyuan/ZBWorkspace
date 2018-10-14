package com.sgcc.pms.zbztjc.jczhgl.bdzztj;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.jczhgl.bdzztj.bizc.IBdzztjBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/bdzztjcontrol")
public class BdzztjController {
	@Resource
	private IBdzztjBizc bdzztjBizc;

	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  bdzztjBizc.statByDydj(params);
	}
	
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  bdzztjBizc.statByJclx(params);
	}
	
	@RequestMapping("/sccjtj")
	public @ItemResponseBody
	QueryResultObject statBySccj(@QueryRequestParam("params") RequestCondition params) {
		return  bdzztjBizc.statBySccj(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  bdzztjBizc.getDetailList(params);
	}

	
	@RequestMapping("/getFGBDZList")
	public @ItemResponseBody
	QueryResultObject getFGBDZList(@QueryRequestParam("params") RequestCondition params) {
		return  bdzztjBizc.getFGBDZList(params);
	}
}
