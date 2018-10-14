package com.sgcc.pms.zbztjc.bbcxgl.bdzzjrqk;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.bbcxgl.bdzzjrqk.bizc.IBdzzjrqkBizc;
import com.sgcc.pms.zbztjc.bbcxgl.sdzzjrqk.bizc.ISdzzjrqkBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/bdzzjrqk")
public class BdzzjrqkController {
	@Resource
	private IBdzzjrqkBizc bdzzjrqkBizc;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getBdData")
    public @ItemResponseBody
    QueryResultObject loadbddata(@QueryRequestParam("params") RequestCondition params) {
		return bdzzjrqkBizc.bdquery(params);
		
	}

}
