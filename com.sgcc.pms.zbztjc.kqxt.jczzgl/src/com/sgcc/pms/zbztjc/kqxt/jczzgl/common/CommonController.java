package com.sgcc.pms.zbztjc.kqxt.jczzgl.common;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;

import com.sgcc.pms.zbztjc.kqxt.jczzgl.common.bizc.ICommonBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.support.QueryResultObject;

@Controller
@RequestMapping("/zbjczzglUtil")
public class CommonController {

	@Resource
	private ICommonBizc commonBizc;
	
	private final static Log log = LogFactory.getLog(CommonController.class);
	
	@RequestMapping("/dropDownEditor/{sqlId}")
	public @ItemResponseBody QueryResultObject getDropDownEditorById(@PathVariable String sqlId){
		if(StringUtils.isNotBlank(sqlId)){		//如果参数为空
			return commonBizc.getDropDownEditorById(sqlId);
		}
		log.info("初始化下拉框时传入的SQLID为空");
		throw new RestClientException("初始化下拉框时传入的SQLID为空");
		
	}
}
