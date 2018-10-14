package com.sgcc.pms.zbztjc.bbcxgl.zyxtjczzjrqk;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.bbcxgl.zyxtjczzjrqk.bizc.IZyxtjczzjrqkBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/zyxtjczzjrqk")
public class ZyxtjczzjrqkController {
	@Resource
	private IZyxtjczzjrqkBizc zyxtjczzjrqkBizc;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getZyxtData")
    public @ItemResponseBody
    QueryResultObject loadzyxtzzjrqkdata(@QueryRequestParam("params") RequestCondition params) {
		return zyxtjczzjrqkBizc.zyxtzzjrqkquery(params);
		
	}

}
