package com.sgcc.pms.zbztjc.jrxxgl.zzjrqk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.jrxxgl.zzjrqk.bizc.IZzjrqkcxBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/zzjrqkcx")
public class ZzjrqkcxController {
	@Autowired
	private IZzjrqkcxBizc zzjrqkcxBizc;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSdData")
    public @ItemResponseBody
    QueryResultObject loadsddata(@QueryRequestParam("params") RequestCondition params) {
		return zzjrqkcxBizc.sdquery(params);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getBdData")
    public @ItemResponseBody
    QueryResultObject loadbddata(@QueryRequestParam("params") RequestCondition params) {
		return zzjrqkcxBizc.bdquery(params);
		
	}
	
}	