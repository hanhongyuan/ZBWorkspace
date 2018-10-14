package com.sgcc.pms.zbztjc.kqxt.mainSystemgjxxcxtj_stat;

import javax.annotation.Resource;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.kqxt.mainSystemgjxxcxtj_stat.bizc.IMainSystemgjxxcxtj_statBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/mainSystemgjxxcxtj_stat")
public class mainSystemgjxxcxtj_statController {

	@Resource
	private IMainSystemgjxxcxtj_statBizc mainSystemgjxxcxtj_statBizc;
	
	private final Log logger = LogFactory.getLog(mainSystemgjxxcxtj_statController.class);
	/**
	 * 按所属系统统计
	 * @param params
	 * @return
	 */
	@RequestMapping("/statBySsxt")
	public @ItemResponseBody
	QueryResultObject statBySsxt(@QueryRequestParam("params") RequestCondition params) {
		logger.info("执行主要系统告警信息查询统计的统计方法！");
		return  mainSystemgjxxcxtj_statBizc.statBySsxt(params);
	}
	
	/**
	 * 按所属系统统计
	 * @param params
	 * @return
	 */
	@RequestMapping("/queryDetail")
	public @ItemResponseBody
	QueryResultObject queryDetail(@QueryRequestParam("params") RequestCondition params) {
		logger.info("执行主要系统告警信息查询统计的详细信息方法！");
		return  mainSystemgjxxcxtj_statBizc.queryDetail(params);
	}
}
