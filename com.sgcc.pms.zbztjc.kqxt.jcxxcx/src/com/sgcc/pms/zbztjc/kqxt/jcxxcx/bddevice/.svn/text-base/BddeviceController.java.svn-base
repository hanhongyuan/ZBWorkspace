package com.sgcc.pms.zbztjc.kqxt.jcxxcx.bddevice;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.kqxt.jcxxcx.bddevice.bizc.IBddeviceBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;



@Controller
@RequestMapping("/bddevicequery")

public class BddeviceController {

	@Resource
	private IBddeviceBizc bddeviceBizc;
	
	 /**
     * 变电监测信息查询
     * @param params
     * @return
     */
    @RequestMapping("/getbddeviceifo")
    public @ItemResponseBody
	QueryResultObject loadWtysblb(@QueryRequestParam("params") RequestCondition params) {
    	
    	return bddeviceBizc.getWtysblb(params);
    }
    
	 /**
     * 变电监测信息统计
     * @param params
     * @return
     */
    @RequestMapping("/loadtjxx")
    public @ItemResponseBody
	QueryResultObject loadtjxx(@QueryRequestParam("params") RequestCondition params) {
    	
    	return bddeviceBizc.gettjxx(params);
    }


}
