package com.sgcc.pms.zbztjc.bdgjxxcxtj_stat;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.bdgjxxcxtj_stat.bizc.IBdgjxxcxtj_statBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/bdgjxxcxtj_stat")
public class Bdgjxxcxtj_statController {

	@Resource
	private IBdgjxxcxtj_statBizc bdgjxxcxtj_statBizc;

	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  bdgjxxcxtj_statBizc.statByDydj(params);
	}
	
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  bdgjxxcxtj_statBizc.statByJclx(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  bdgjxxcxtj_statBizc.getDetailList(params);
	}

	
}
