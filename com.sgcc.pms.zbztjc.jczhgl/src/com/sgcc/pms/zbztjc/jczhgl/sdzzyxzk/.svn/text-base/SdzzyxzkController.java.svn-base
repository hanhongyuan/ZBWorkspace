package com.sgcc.pms.zbztjc.jczhgl.sdzzyxzk;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.jczhgl.sdzzyxzk.bizc.ISdzzyxzkBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/sdzzyxzkcontrol")
public class SdzzyxzkController {
	@Resource
	private ISdzzyxzkBizc sdzzyxzkBizc;
	
	 /**
     * 输电监测装置运行状况查询
     * @param params
     * @return
     */
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return sdzzyxzkBizc.query(params);
	}
    
	/**
     * 输电监测装置运行状况按电压等级统计
     * @param params
     * @return
     */
	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzyxzkBizc.statByDydj(params);
	}
	/**
     * 输电监测装置运行状况按监测类型统计
     * @param params
     * @return
     */
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzyxzkBizc.statByJclx(params);
	}
	/**
     * 输电监测装置运行状况按生产厂家统计
     * @param params
     * @return
     */
	@RequestMapping("/sccjtj")
	public @ItemResponseBody
	QueryResultObject statBySccj(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzyxzkBizc.statBySccj(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzyxzkBizc.getDetailList(params);
	}

	
	@RequestMapping("/getFGBDZList")
	public @ItemResponseBody
	QueryResultObject getFGBDZList(@QueryRequestParam("params") RequestCondition params) {
		return  sdzzyxzkBizc.getFGBDZList(params);
	}
}
