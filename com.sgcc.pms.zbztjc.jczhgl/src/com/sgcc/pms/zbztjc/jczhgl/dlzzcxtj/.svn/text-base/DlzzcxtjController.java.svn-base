package com.sgcc.pms.zbztjc.jczhgl.dlzzcxtj;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.jczhgl.dlzzcxtj.bizc.IDlzzcxtjBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/zbdlzzcxtjController") //根据po类名生成
public class DlzzcxtjController {
	
    @Resource
    private IDlzzcxtjBizc dlzzcxtjBizc;
	
	

	
	/**
	 * 查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/query")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return dlzzcxtjBizc.query(params);
		
		
		
	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param id
	 *            url中传递的值
	 */
	@RequestMapping("/{id}")
	public @ItemResponseBody
	QueryResultObject query(@PathVariable String id) {
		return dlzzcxtjBizc.queryById(id);
	}
	

	@RequestMapping("/dydjtj")
	public @ItemResponseBody
	QueryResultObject statByDydj(@QueryRequestParam("params") RequestCondition params) {
		return  dlzzcxtjBizc.statByDydj(params);
	}
	
	@RequestMapping("/jclxtj")
	public @ItemResponseBody
	QueryResultObject statByJclx(@QueryRequestParam("params") RequestCondition params) {
		return  dlzzcxtjBizc.statByJclx(params);
	}
	
	@RequestMapping("/sccjtj")
	public @ItemResponseBody
	QueryResultObject statBySccj(@QueryRequestParam("params") RequestCondition params) {
		return  dlzzcxtjBizc.statBySccj(params);
	}
	
	@RequestMapping("/getDetailList")
	public @ItemResponseBody
	QueryResultObject getDetailList(@QueryRequestParam("params") RequestCondition params) {
		return  dlzzcxtjBizc.getDetailList(params);
	}

	
	@RequestMapping("/getFGBDZList")
	public @ItemResponseBody
	QueryResultObject getFGBDZList(@QueryRequestParam("params") RequestCondition params) {
		return  dlzzcxtjBizc.getFGBDZList(params);
	}


}
