package com.sgcc.pms.zbztjc.kqxt.mainSystemssxtInfo;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.kqxt.mainSystemssxtInfo.bizc.IMainSystemssxtInfoBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/mainSystemssxtInfo")
public class MainSystemssxtInfoController {

	@Resource
	private IMainSystemssxtInfoBizc mainSystemssxtInfoBizc;
	
	private final Log logger = LogFactory.getLog(MainSystemssxtInfoController.class);
	
	@RequestMapping("/querySdInfo")
	public @ItemResponseBody
	QueryResultObject querySdInfo(@QueryRequestParam("params") RequestCondition params) {
		logger.info("执行主要系统告警信息查询统计的统计方法！");
		return  mainSystemssxtInfoBizc.querySdInfo(params);
	}
	
	@RequestMapping("/queryBdInfo")
	public @ItemResponseBody
	QueryResultObject queryBdInfo(@QueryRequestParam("params") RequestCondition params) {
		logger.info("执行主要系统告警信息查询统计的统计方法！");
		return  mainSystemssxtInfoBizc.queryBdInfo(params);
	}
	
	@RequestMapping("/queryDetailInfo")
	public @ItemResponseBody
	QueryResultObject queryDetailInfo(@QueryRequestParam("params") RequestCondition params) {
		logger.info("执行主要系统告警信息查询统计的统计方法！");
		return  mainSystemssxtInfoBizc.queryDetailInfo(params);
	}
}
