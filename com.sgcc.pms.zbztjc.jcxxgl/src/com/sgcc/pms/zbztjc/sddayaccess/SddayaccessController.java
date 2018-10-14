package com.sgcc.pms.zbztjc.sddayaccess;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.sddayaccess.bizc.ISddayaccessBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/sddayaccess")
public class SddayaccessController {
	@Autowired
	private ISddayaccessBizc sddayaccessBizc;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadtjxx")
    public @ItemResponseBody
    QueryResultObject loadtjxx(@QueryRequestParam("params") RequestCondition params) {
		return sddayaccessBizc.loadtjxx(params);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadyfxx")
    public @ItemResponseBody
    QueryResultObject loadyfxx(@QueryRequestParam("params") RequestCondition params) {
		return sddayaccessBizc.loadyfxx(params);
		
	}
	
}	