package com.sgcc.pms.zbztjc.ztkhgl.changekhstatus;



import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.ztkhgl.changekhstatus.bizc.IChangekhstatusBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;



@Controller
@RequestMapping("/khztControl")

public class ChangekhstatusController {

	@Resource
	private IChangekhstatusBizc changekhstatusBizc;
	
	 /**
     * 运行状态信息查询
     * @param params
     * @return
     */
    @RequestMapping("/getsdstatus")
    public @ItemResponseBody
	QueryResultObject loadstatusinfo(@QueryRequestParam("params") RequestCondition params) {
    	
    	return changekhstatusBizc.loadstatusinfo(params);
    }
    
    /**
     * 审核信息查询
     * @param params
     * @return
     */
    @RequestMapping("/getinfo/{id}")
    public @ItemResponseBody
	QueryResultObject loadinfo(@PathVariable String id) {
    	
    	return changekhstatusBizc.loadinfo(id);
    }

	 /**
     * 保存审核信息
     * @param params
     * @return
     */
    @RequestMapping("/save")
    public @ItemResponseBody
	QueryResultObject saveadui(@QueryRequestParam("params") RequestCondition params) {
    	
    	return changekhstatusBizc.saveadui(params);
    }

}
