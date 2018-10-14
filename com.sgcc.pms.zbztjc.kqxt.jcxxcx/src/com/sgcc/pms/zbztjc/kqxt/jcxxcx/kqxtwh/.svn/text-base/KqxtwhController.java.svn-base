package com.sgcc.pms.zbztjc.kqxt.jcxxcx.kqxtwh;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import com.sgcc.pms.zbztjc.kqxt.jcxxcx.bddevice.bizc.IBddeviceBizc;
import com.sgcc.pms.zbztjc.kqxt.jcxxcx.kqxtwh.bizc.IKqxtwhBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;



@Controller
@RequestMapping("/KqxtwhControl")

public class KqxtwhController {

	@Resource
	private IKqxtwhBizc kqxtwhBizc;
	// 日志
	private final static Log logger = LogFactory.getLog(KqxtwhController.class);
	 /**
     * 变电监测信息查询
     * @param params
     * @return
     */
    @RequestMapping("/")
    public @ItemResponseBody
	QueryResultObject loadKqxtInfo(@QueryRequestParam("params") RequestCondition params) {
    	
    	return kqxtwhBizc.getKqxtInfo(params);
    }
    
    /**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<Map<String,String>> paramSave(@ItemsRequestBody List<Map<String,String>> list){ 
		logger.info("执行保存操作（新增/修改）");
		try{
			return kqxtwhBizc.saveOrUpdate(list);
		}catch(Exception e){
			throw new RestClientException("保存方法异常",e);
		}
	}


}
