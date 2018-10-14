package com.sgcc.pms.zbztjc.util.userlogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.util.userlogger.bizc.IUserloggerBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 用户审计类
 * 
 * @author gaotiedun
 * 
 */
@Controller
@RequestMapping("/userlogger")
public class UserloggerController {
	@Resource
	private IUserloggerBizc userloggerBizc;

	/**
	 * 加载用户操作信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/loadData")
	public @ItemResponseBody
	QueryResultObject loadData(
			@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject result = userloggerBizc.loadData(params);
		return result;
	}

	@RequestMapping("/updateUserLogger")
	public @ItemResponseBody
	List updateUserLogger(@ItemsRequestBody List<Map<String,String>> list) {
		return userloggerBizc.updateUserLogger(list);
	}
	
	/**
	 * 加载审计查阅信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/queryInfo")
	public @ItemResponseBody
	QueryResultObject queryInfo(
			@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject result = userloggerBizc.queryInfo(params);
		return result;
	}
	
	/**
	 * 加载审计查阅信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/querytj")
	public @ItemResponseBody
	QueryResultObject querytj(
			@QueryRequestParam("params") RequestCondition params) {
		QueryResultObject result = userloggerBizc.querytj(params);
		return result;
	}
	
	
	/**
	 * 加载审计查阅信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/getLoggerAlarm")
	public  @ItemResponseBody List queryCount() {
		List  resultList = new ArrayList();
		resultList.add(userloggerBizc.getLoggerAlarm());
		return resultList;
	}
	/**
	 * 备份审计日志
	 * @return
	 */
	@RequestMapping("/backup")
	public @ItemResponseBody List backup(){
		List  resultList = new ArrayList();
		resultList.add(userloggerBizc.backup());
		return resultList;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryLimitSetForm/{formId}")
	public @ItemResponseBody List queryLimitSetForm(@PathVariable String formId){
		if ("meta".equals(formId)) {
			return null;
		}
		return userloggerBizc.queryLimitSetForm(formId);
	}
	
	@SuppressWarnings({ "unchecked", "null", "rawtypes" })
	@RequestMapping("/updateLimitSet")
	public @ItemResponseBody List updateLimitSet(@ItemsRequestBody List<Map<String,String>> list){
		List  resultList = new ArrayList();
		if (null != list || 0 != list.size()) {
			resultList.add(userloggerBizc.updateLimitSet(list.get(0)));
		}
		return resultList;
	}
}
