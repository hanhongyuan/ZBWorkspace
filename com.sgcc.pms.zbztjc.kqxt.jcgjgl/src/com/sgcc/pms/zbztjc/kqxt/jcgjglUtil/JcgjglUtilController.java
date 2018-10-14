package com.sgcc.pms.zbztjc.kqxt.jcgjglUtil;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;

import com.sgcc.pms.zbztjc.kqxt.jcgjglUtil.bizc.IJcgjglUtilBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.support.QueryResultObject;

@Controller
@RequestMapping("/jcgjglUtil")
public class JcgjglUtilController {
	@Resource
	private IJcgjglUtilBizc jcgjglUtilbizc;
	
	private final static Log logger = LogFactory.getLog(JcgjglUtilController.class);
	
	@RequestMapping("/dropDownEditor/{sqlId}")
	public @ItemResponseBody QueryResultObject getDropDownEditorById(@PathVariable String sqlId){
		if(StringUtils.isNotBlank(sqlId)){		//如果参数为空
			
			return jcgjglUtilbizc.getDropDownEditorById(sqlId);
		}
		logger.info("初始化下拉框时传入的SQLID为空");
		throw new RestClientException("初始化下拉框时传入的SQLID为空");
		
	}
	
}
