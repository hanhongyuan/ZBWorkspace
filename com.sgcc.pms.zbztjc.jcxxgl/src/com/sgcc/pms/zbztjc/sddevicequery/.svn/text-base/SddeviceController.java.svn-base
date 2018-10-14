package com.sgcc.pms.zbztjc.sddevicequery;



import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




import com.sgcc.pms.zbztjc.sddevicequery.bizc.ISddeviceBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;




@Controller
@RequestMapping("/sddevice")

public class SddeviceController {

	@Resource
	private ISddeviceBizc sddeviceBizc;
	
	 /**
     * 输电监测信息查询
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/getsddevice")
    public @ItemResponseBody
	QueryResultObject loadWtysblb(@QueryRequestParam("params") RequestCondition params) {
    	return sddeviceBizc.getWtysblb(params);
    }
	
    /**
     * 输电监测信息统计
     * @param params
     * @return
     */
    @RequestMapping("/loadtjxx")
    public @ItemResponseBody
	QueryResultObject loadtjxx(@QueryRequestParam("params") RequestCondition params) {
    	
    	return sddeviceBizc.gettjxx(params);
    }
    


}
