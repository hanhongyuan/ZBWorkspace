package com.sgcc.pms.zbztjc.dldevicequery;


import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sgcc.pms.zbztjc.dldevicequery.bizc.IDldeviceBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;



@Controller
@RequestMapping("/dldevicequery")

public class DldeviceController {

	@Resource
	private IDldeviceBizc dldeviceBizc;
	
	 /**
     * 变电监测信息查询
     * @param params
     * @return
     */
    @RequestMapping("/getdldeviceifo")
    public @ItemResponseBody
	QueryResultObject loadWtysblb(@QueryRequestParam("params") RequestCondition params) {
    	
    	return dldeviceBizc.getWtysblb(params);
    }
    
	 /**
     * 变电监测信息统计
     * @param params
     * @return
     */
    @RequestMapping("/loadtjxx")
    public @ItemResponseBody
	QueryResultObject loadtjxx(@QueryRequestParam("params") RequestCondition params) {
    	
    	return dldeviceBizc.gettjxx(params);
    }


}
