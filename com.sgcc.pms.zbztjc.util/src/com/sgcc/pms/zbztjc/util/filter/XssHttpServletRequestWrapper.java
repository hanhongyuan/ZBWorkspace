//******************************************************************   
//系统名称：PMS2.0
//Copyright(C)2014-2022 NARI Information and Communication Technology Branch. All rights reserved.
//版本信息:1.0    
//#作者:fxs$权重：100%$手机：13575710225#     
//******************************************************************
package com.sgcc.pms.zbztjc.util.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;

import com.sgcc.uap.mxframework.taglib.utils.StringUtils;
/**
 * <b>概述</b>：xssServlet的包装类<br/>
 * <b>功能</b>提供xssServlet的包装逻辑<br/>
 * 
 * @author fxs
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper{
	/*
	 * HttpServletRequestWrapper方法
	 */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {  
        super(request);  
    }
    
    /** 
     * 覆盖getParameterValues方法，将参数值都做xss过滤。<br/> 
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/> 
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖 
     */
    @Override
    public String[] getParameterValues(String parameter){
    	
    	String[] values= super.getParameterValues(parameter);
    	if(values==null){
    		return null;
    	}else{
    		int count=values.length;
        	String[] encodeValues=new String[count];
        	for(int i=0;i<count;i++){
        		encodeValues[i] = xssEncode(values[i]);
        	}
        	return encodeValues;
    	}
    }

    /** 
     * 覆盖getParameter方法，将参数名做xss过滤。<br/> 
     * 如果需要获得原始的值，则通过super.getParameter(name)来获取<br/> 
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖 
     */  
    @Override  
    public String getParameter(String parameter) {  
        String value = super.getParameter(parameter.trim());  
        if (value == null) {  
        	return null;
        }else{
        	return xssEncode(value);
        }
    }
    
    /** 
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/> 
     * 如果需要获得原始的值，则通过super.getHeaders(parameter)来获取<br/> 
     * getHeaderNames 也可能需要覆盖 
     */  
/*    @Override
    public String getHeader(String parameter) {  
    	String value = super.getHeader(parameter);
    	if(value==null){
    		return null;
    	}else{
    		return value;
    	}
    }
    */
    /** 
     * 将容易引起xss漏洞的特殊字符转义成16进制
     *  
     * @param value 
     * @return 
     */ 
    public String xssEncode(String value){
    	//You'll need to remove the spaces from the html entities below
        if (value == null || StringUtils.isEmpty(value)) {  
        	return value;
        }else{
//    		String result = HtmlUtils.htmlEscapeHex(value);
    		String result = StringEscapeUtils.escapeHtml(value);
//    		System.out.println(result);
    		return result;
        }
    }
    
}
