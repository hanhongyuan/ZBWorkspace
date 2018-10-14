package com.sgcc.pms.zbztjc.sdgjxxcxtj_stat;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.sdgjxxcxtj_stat.bizc.ISdgjxxcxtj_statBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/sdgjxxcxtj_stat")
public class Sdgjxxcxtj_statController {
	@Resource
	private ISdgjxxcxtj_statBizc sdgjxxcxtj_statBizc;

	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  sdgjxxcxtj_statBizc.statByDydj(params);
	}
	
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  sdgjxxcxtj_statBizc.statByJclx(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  sdgjxxcxtj_statBizc.getDetailList(params);
	}

}
