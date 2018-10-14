package com.sgcc.pms.zbztjc.kqxt.mainSystemgjxxcxtj_query;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.kqxt.mainSystemgjxxcxtj_query.bizc.IMainSystemgjxxcxtj_queryBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/mainSystemgjxxcxtj_query")
public class MainSystemgjxxcxtj_queryController {

	@Resource
	private IMainSystemgjxxcxtj_queryBizc mainSystemgjxxcxtjBizc_query;
	
	private final Log logger = LogFactory.getLog(MainSystemgjxxcxtj_queryController.class);
	
	 /**
     * 变电监测信息查询
     * @param params
     * @return
     */
    @RequestMapping("/query")
    public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
    	logger.info("执行查询主要系统告警查询系统查询码的方法");
    	return mainSystemgjxxcxtjBizc_query.query(params);
    }
	
}
