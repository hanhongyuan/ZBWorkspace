package com.sgcc.pms.zbztjc.util.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CMSServletFilter implements Filter{

	public void destroy() {
		
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		//referer验证
		 String method = ((HttpServletRequest) request).getMethod();  
	        if(!"GET".equals(method)&&!"POST".equals(method)&&!"HEAD".equals(method))  
	        {  
	             return;
	        }  
		 
		String[] keyWords = {"'","delete","drop","insert","update","truncate","http:","ascii","chr"};
		Enumeration<String> e =  request.getParameterNames();
		String url = "";
		HttpServletRequest hsr = (HttpServletRequest)request;
		while (e.hasMoreElements()){
			String element = e.nextElement();
			url += request.getParameter(element);
		}
		System.out.println("----------------------------URL:"+url);
		if (hsr.getRequestURI()!= null && hsr.getRequestURI().contains(".jsp")){
			chain.doFilter(request, response);
			return ;
		}
		for (int i = 0; i < keyWords.length; ++i) {
		  boolean res = url.contains(keyWords[i]);
		  boolean res2 = url.contains(keyWords[i].toUpperCase());
		  if (res || res2){
			  System.out.println("--------------错误的URI格式[]"+keyWords[i]);
			  throw new RuntimeException("错误的URL格式");
		  }
		}
		chain.doFilter(request, response);
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
