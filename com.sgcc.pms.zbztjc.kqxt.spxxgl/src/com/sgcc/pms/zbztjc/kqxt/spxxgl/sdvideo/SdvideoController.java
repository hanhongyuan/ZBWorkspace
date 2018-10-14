package com.sgcc.pms.zbztjc.kqxt.spxxgl.sdvideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.kqxt.spxxgl.sdvideo.bizc.ISdvideoBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;


@Controller
@RequestMapping("/sdvideo") 
public class SdvideoController {
	
    @Autowired
    private ISdvideoBizc sdvideoBizc;
	

	
	/**
	 * 查询
	 * 
	 * @param params
	 * @return queryResult
	 */
	@RequestMapping("/loadsdvideo")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = sdvideoBizc.query(params);
		return queryResult;
	}
	

	


	/**
	 * 注入逻辑构件
	 * @param sdvideoBizc
	 */
	public void setSdvideoBizc(ISdvideoBizc sdvideoBizc) {
		this.sdvideoBizc = sdvideoBizc;
	}
}
