package com.sgcc.pms.zbztjc.ztkhgl.jczbcx;



import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.ztkhgl.changekhstatus.bizc.IChangekhstatusBizc;
import com.sgcc.pms.zbztjc.ztkhgl.jczbcx.bizc.IJczbcxBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;



@Controller
@RequestMapping("/jczbcxControl")

public class JczbcxController {

	@Resource
	private IJczbcxBizc jczbcxBizc;
	
	 /**
     * 运行状态信息查询
     * @param params
     * @return
     */
    @RequestMapping("/query")
    public @ItemResponseBody
	QueryResultObject loadstatusinfo(@QueryRequestParam("params") RequestCondition params) {
    	
    	return jczbcxBizc.loadinfo(params);
    }
    


}
