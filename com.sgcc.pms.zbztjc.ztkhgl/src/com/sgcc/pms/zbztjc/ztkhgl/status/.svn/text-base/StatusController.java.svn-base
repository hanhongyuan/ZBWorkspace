package com.sgcc.pms.zbztjc.ztkhgl.status;



import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.ztkhgl.status.bizc.IStatusBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;



@Controller
@RequestMapping("/khzt")

public class StatusController {

	@Resource
	private IStatusBizc statusBizc;
	
	 /**
     * 运行状态信息查询
     * @param params
     * @return
     */
    @RequestMapping("/getsdstatus")
    public @ItemResponseBody
	QueryResultObject loadstatusinfo(@QueryRequestParam("params") RequestCondition params) {
    	
    	return statusBizc.loadstatusinfo(params);
    }
    
    /**
     * 审核信息查询
     * @param params
     * @return
     */
    @RequestMapping("/getinfo/{id}")
    public @ItemResponseBody
	QueryResultObject loadinfo(@PathVariable String id) {
    	
    	return statusBizc.loadinfo(id);
    }

	 /**
     * 保存审核信息
     * @param params
     * @return
     */
    @RequestMapping("/save")
    public @ItemResponseBody
	QueryResultObject saveadui(@QueryRequestParam("params") RequestCondition params) {
    	
    	return statusBizc.saveadui(params);
    }

}
