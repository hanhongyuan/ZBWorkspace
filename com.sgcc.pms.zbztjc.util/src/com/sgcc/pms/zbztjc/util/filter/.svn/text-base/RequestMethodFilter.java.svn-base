package com.sgcc.pms.zbztjc.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestMethodFilter implements Filter{

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			HttpServletRequest httpRequest = (HttpServletRequest) request;  
	        HttpServletResponse httpResponse = ((HttpServletResponse) response);   
	        String m=httpRequest.getMethod();  
	        if(!"GET".equals(m)&&!"POST".equals(m)){  
	            httpResponse.setHeader("Allow", "GET,POST");  
	            httpResponse.setStatus(405);  
	            return;  
	        }  
	        try{
	        	 chain.doFilter(request, response);  
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
