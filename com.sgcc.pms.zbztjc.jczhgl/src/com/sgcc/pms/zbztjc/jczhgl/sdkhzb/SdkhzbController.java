package com.sgcc.pms.zbztjc.jczhgl.sdkhzb;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.sgcc.pms.zbztjc.jczhgl.sdkhzb.bizc.ISdkhzbBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/sdkhzbControl")
public class SdkhzbController {
	@Resource
	private ISdkhzbBizc sdkhzbBizc;
	
	 /**
     * 输电监测装置运行状况查询
     * @param params
     * @return
     */
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return sdkhzbBizc.query(params);
	}
    
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  sdkhzbBizc.getDetailList(params);
	}
	
	@RequestMapping("/getdataDetailList")
	public @ItemResponseBody
	QueryResultObject getdataDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  sdkhzbBizc.getdataDetailList(params);
	}
}
