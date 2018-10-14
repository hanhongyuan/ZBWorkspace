package com.sgcc.pms.zbztjc.devicegeneral;


import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sgcc.pms.zbztjc.devicegeneral.bizc.IDevicegeneralBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;



@Controller
@RequestMapping("/devicegeneral")

public class devicegeneralController {

	@Resource
	private IDevicegeneralBizc devicegeneralBizc;
	
	 /**
     * 装置概况查询
     * @param params
     * @return
     */
    @RequestMapping("/geddevicenum")
    public @ItemResponseBody
	QueryResultObject loadWtysblb() {
    	return devicegeneralBizc.getWtysblb();
    }
    
	 /**
     * 详细信息查询
     * @param params
     * @return
     */
    @RequestMapping("/getdetail")
    public @ItemResponseBody
	QueryResultObject loadtjxx(@QueryRequestParam("params") RequestCondition params) {
    	
    	return devicegeneralBizc.loaddetail(params);
    }
    }



