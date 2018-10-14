package com.sgcc.pms.zbztjc.jczhgl.bdzzyxzk;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.jczhgl.bdzzyxzk.bizc.IBdzzyxzkBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/bdzzyxzkcontrol")
public class BdzzyxzkController {
	@Resource
	private IBdzzyxzkBizc bdzzyxzkBizc;
	
	 /**
     * 输电监测装置运行状况查询
     * @param params
     * @return
     */
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return bdzzyxzkBizc.query(params);
	}
    

	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzyxzkBizc.statByDydj(params);
	}
	
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzyxzkBizc.statByJclx(params);
	}
	
	@RequestMapping("/sccjtj")
	public @ItemResponseBody
	QueryResultObject statBySccj(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzyxzkBizc.statBySccj(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzyxzkBizc.getDetailList(params);
	}

	
	@RequestMapping("/getFGBDZList")
	public @ItemResponseBody
	QueryResultObject getFGBDZList(@QueryRequestParam("params") RequestCondition params) {
		return  bdzzyxzkBizc.getFGBDZList(params);
	}
}
