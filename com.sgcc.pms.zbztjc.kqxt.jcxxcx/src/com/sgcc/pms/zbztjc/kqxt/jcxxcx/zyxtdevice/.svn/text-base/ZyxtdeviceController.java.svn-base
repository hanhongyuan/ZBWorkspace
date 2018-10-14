package com.sgcc.pms.zbztjc.kqxt.jcxxcx.zyxtdevice;


import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.sgcc.pms.zbztjc.kqxt.jcxxcx.zyxtdevice.bizc.IZyxtdeviceBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/zyxtdevice")

public class ZyxtdeviceController {

	@Resource
	private IZyxtdeviceBizc zyxtdeviceBizc;
	
	 /**
     * 输电监测信息查询
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/getsddevice")
    public @ItemResponseBody
	QueryResultObject loadWtysblb(@QueryRequestParam("params") RequestCondition params) {
    	return zyxtdeviceBizc.getWtysblb(params);
    }
	
    /**
     * 输电监测信息统计
     * @param params
     * @return
     */
    @RequestMapping("/loadtjxx")
    public @ItemResponseBody
	QueryResultObject loadtjxx(@QueryRequestParam("params") RequestCondition params) {
    	
    	return zyxtdeviceBizc.gettjxx(params);
    }
    
    /**
     * 主要系统输电
     * @param params
     * @return
     */
    @RequestMapping("/loadxtsd")
    public @ItemResponseBody
	QueryResultObject loadxtsd(@QueryRequestParam("params") RequestCondition params) {
    	
    	return zyxtdeviceBizc.loadxtsd(params);
    }
    /**
     * 主要系统变电
     * @param params
     * @return
     */
    @RequestMapping("/loadxtbd")
    public @ItemResponseBody
	QueryResultObject loadxtbd(@QueryRequestParam("params") RequestCondition params) {
    	
    	return zyxtdeviceBizc.loadxtbd(params);
    }

    /**
     * 主要系统信息查询
     * @param params
     * @return
     */
    @RequestMapping("/loadcx")
    public @ItemResponseBody
	QueryResultObject loadsdcx(@QueryRequestParam("params") RequestCondition params) {
    	
    	return zyxtdeviceBizc.loadcx(params);
    }
    
 
}