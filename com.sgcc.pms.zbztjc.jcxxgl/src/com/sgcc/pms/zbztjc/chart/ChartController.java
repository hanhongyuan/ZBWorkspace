package com.sgcc.pms.zbztjc.chart;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.chart.bizc.IChartBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/chart")
public class ChartController {

	@Resource
	private IChartBizc chartBizc;

	/**
	 * 查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/queryDeviceGeneral")
	public @ItemResponseBody
	QueryResultObject queryDeviceGeneral(
			@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = chartBizc.queryDeviceGeneral(params);
		return queryResult;
	}
	
	/**
	 * 概况柱状图详细--输电
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/queryDetail")
	public @ItemResponseBody
	QueryResultObject queryDetail(
			@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = chartBizc.queryDetail(params);
		return queryResult;
	}
	
	/**
	 * 概况柱状图详细--变电
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/querybdDetail")
	public @ItemResponseBody
	QueryResultObject querybdDetail(
			@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = chartBizc.querybdDetail(params);
		return queryResult;
	}
	
	/**
	 * 概况柱状图详细--电缆
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/querydlDetail")
	public @ItemResponseBody
	QueryResultObject querydlDetail(
			@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject queryResult = chartBizc.querydlDetail(params);
		return queryResult;
	}
	
}
