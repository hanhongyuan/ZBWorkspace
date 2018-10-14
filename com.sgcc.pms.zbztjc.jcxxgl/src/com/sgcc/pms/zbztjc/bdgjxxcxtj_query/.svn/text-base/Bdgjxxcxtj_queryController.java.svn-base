package com.sgcc.pms.zbztjc.bdgjxxcxtj_query;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.bdgjxxcxtj_query.bizc.IBdgjxxcxtj_queryBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/bdgjxxcxtj_query")
public class Bdgjxxcxtj_queryController {

	@Resource
	private IBdgjxxcxtj_queryBizc bdgjxxcxtjBizc;
	
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return bdgjxxcxtjBizc.query(params);
	}
	
	@RequestMapping("/query/null")
	public @ItemResponseBody
	QueryResultObject queryNull(@QueryRequestParam("params") RequestCondition params) {
		return null;
	}
	/**
	 * 根据ID查看详细信息
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryDatailById/{id}")
	public @RawResponseBody List queryDatailById(@PathVariable String id) {
		List queryResult = bdgjxxcxtjBizc.queryDatailById(id);
		return queryResult;
	}
	
	/**
	 * 根据Id查找告警记录
	 * 
	 * @param id
	 *            url中传递的值
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryMonitorRecordById/{id}")
	public @RawResponseBody List queryMonitorRecordById(@PathVariable String id) {
		List queryResult = bdgjxxcxtjBizc.queryMonitorRecordById(id);
		return queryResult;
	}
}
