package com.sgcc.pms.zbztjc.dlgjxxcxtj_stat;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.dlgjxxcxtj_stat.bizc.IDlgjxxcxtj_statBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/dlgjxxcxtj_stat")
public class Dlgjxxcxtj_statController {
	
	@Resource
	private IDlgjxxcxtj_statBizc dlgjxxcxtj_statBizc;

	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  dlgjxxcxtj_statBizc.statByDydj(params);
	}
	
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  dlgjxxcxtj_statBizc.statByJclx(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  dlgjxxcxtj_statBizc.getDetailList(params);
	}
	
}
