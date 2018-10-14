package com.sgcc.pms.zbztjc.bbcxgl.sdzzjrqk;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.bbcxgl.sdzzjrqk.bizc.ISdzzjrqkBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/sdzzjrqk")
public class SdzzjrqkController {
	@Resource
	private ISdzzjrqkBizc sdzzjrqkBizc;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSdData")
    public @ItemResponseBody
    QueryResultObject loadsddata(@QueryRequestParam("params") RequestCondition params) {
		return sdzzjrqkBizc.sdquery(params);
		
	}

}
