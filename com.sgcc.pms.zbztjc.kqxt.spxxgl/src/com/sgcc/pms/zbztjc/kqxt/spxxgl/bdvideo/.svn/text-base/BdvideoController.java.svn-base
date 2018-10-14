package com.sgcc.pms.zbztjc.kqxt.spxxgl.bdvideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.kqxt.spxxgl.bdvideo.bizc.IBdvideoBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;


@Controller
@RequestMapping("/bdvideo") 
public class BdvideoController {
	
    @Autowired
    private IBdvideoBizc bdvideoBizc;
	

	
	/**
	 * 查询
	 * 
	 * @param params
	 * @return queryResult
	 */
	@RequestMapping("/loadbdvideo")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = bdvideoBizc.query(params);
		return queryResult;
	}
	

	


	/**
	 * 注入逻辑构件
	 * @param bdvideoBizc
	 */
	public void setBdvideoBizc(IBdvideoBizc bdvideoBizc) {
		this.bdvideoBizc = bdvideoBizc;
	}
}
