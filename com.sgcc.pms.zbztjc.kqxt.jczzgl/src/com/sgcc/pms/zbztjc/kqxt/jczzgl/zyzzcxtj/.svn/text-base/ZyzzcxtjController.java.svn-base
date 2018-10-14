package com.sgcc.pms.zbztjc.kqxt.jczzgl.zyzzcxtj;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.kqxt.jczzgl.zyzzcxtj.bizc.IZyzzcxtjBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/zbzyzzcxtjControl")

public class ZyzzcxtjController {

	@Resource
	private IZyzzcxtjBizc zyzzcxtjBizc;
	
	 /**
     * 主要系统装置查询
     * @param params
     * @return
     */
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return zyzzcxtjBizc.query(params);
	}
    
	@RequestMapping("/ssxttj")
	public @ItemResponseBody
	QueryResultObject statBySsxt(@QueryRequestParam("params") RequestCondition params) {
		return  zyzzcxtjBizc.statBySsxt(params);
	}
	

	@RequestMapping("/getFGXLSList")
	public @ItemResponseBody
	QueryResultObject getFGXLSList(@QueryRequestParam("params") RequestCondition params) {
		return  zyzzcxtjBizc.getFGXLSList(params);
	}
	
	@RequestMapping("/getSdDetailList")
	public @ItemResponseBody
	QueryResultObject getSdDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  zyzzcxtjBizc.getSdDetailList(params);
	}

	@RequestMapping("/getBdDetailList")
	public @ItemResponseBody
	QueryResultObject getBdDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  zyzzcxtjBizc.getBdDetailList(params);
	}
	
	@RequestMapping("/getFGBDZList")
	public @ItemResponseBody
	QueryResultObject getFGBDZList(@QueryRequestParam("params") RequestCondition params) {
		return  zyzzcxtjBizc.getFGBDZList(params);
	}
	
	 /**
     * 主要系统输电
     * @param params
     * @return
     */
    @RequestMapping("/loadxtsd")
    public @ItemResponseBody
	QueryResultObject loadxtsd(@QueryRequestParam("params") RequestCondition params) {
    	
    	return zyzzcxtjBizc.loadxtsd(params);
    }
    
    /**
     * 主要系统变电
     * @param params
     * @return
     */
    @RequestMapping("/loadxtbd")
    public @ItemResponseBody
	QueryResultObject loadxtbd(@QueryRequestParam("params") RequestCondition params) {
    	
    	return zyzzcxtjBizc.loadxtbd(params);
    }
    /**
     * 主要系统信息查询
     * @param params
     * @return
     */
    @RequestMapping("/loadcx")
    public @ItemResponseBody
	QueryResultObject loadsdcx(@QueryRequestParam("params") RequestCondition params) {
    	
    	return zyzzcxtjBizc.loadcx(params);
    }

}
