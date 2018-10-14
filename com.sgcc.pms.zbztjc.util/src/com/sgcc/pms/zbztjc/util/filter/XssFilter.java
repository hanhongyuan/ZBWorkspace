//******************************************************************   
//系统名称：PMS2.0
//Copyright(C)2014-2022 NARI Information and Communication Technology Branch. All rights reserved.
//版本信息:1.0    
//#作者:fxs$权重：90%$手机：13575710225#    
//#作者:翟锐$权重:10%$手机:13675132204#
//******************************************************************
package com.sgcc.pms.zbztjc.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * <b>概述</b>：xssFilter的逻辑类<br/>
 * <b>功能</b>提供xss过滤器的逻辑操作方法<br/>
 * 
 * @author fxs
 */
public class XssFilter implements Filter {

	/*
	 * filter过滤器初始化
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	/*
	 * filter过滤器逻辑事件
	 * @see 
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
       
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(  
                (HttpServletRequest) request);  
        chain.doFilter(xssRequest, response);
	}
	
	/*
	 * destroy filter
	 * @see 
	 */
	public void destroy() {
	}

}
