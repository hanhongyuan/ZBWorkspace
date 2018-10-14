package com.sgcc.pms.zbztjc.jczhgl.sdzzcx;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.jczhgl.sdzzcx.bizc.ISdzzcxBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/cmsvlinedevicelogicxt")

public class SdzzcxController {

	@Resource
	private ISdzzcxBizc sdzzcxBizc;
	
	 /**
     * 输电监测装置查询
     * @param params
     * @return
     */
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return sdzzcxBizc.query(params);
	}
    



}
