package com.sgcc.pms.zbztjc.dlgjxxcxtj_query;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.bdgjxxcxtj_query.bizc.IBdgjxxcxtj_queryBizc;
import com.sgcc.pms.zbztjc.dlgjxxcxtj_query.bizc.IDlgjxxcxtj_queryBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/dlgjxxcxtj_query")
public class Dlgjxxcxtj_queryController {

	@Resource
	private IDlgjxxcxtj_queryBizc dlgjxxcxtjBizc;
	
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return dlgjxxcxtjBizc.query(params);
	}
	
	/**
	 * 根据ID查看详细信息
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryDatailById/{id}")
	public @RawResponseBody List queryDatailById(@PathVariable String id) {
		List queryResult = dlgjxxcxtjBizc.queryDatailById(id);
		return queryResult;
	}
	
}
