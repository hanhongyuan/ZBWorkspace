package com.sgcc.pms.zbztjc.util.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.uap.utils.StringUtils; 	

public class UrlCheckFilter implements Filter {
	private static final Log log = LogFactory.getLog(UrlCheckFilter.class);
	private static final String injectionStr = "&gt;@&lt;@&lt;/script@&lt;script@script&gt;@script:@style=@alert@location@window@input@focus@ like @and @ exec @ sleep @ insert @prompt@confirm@ select @ delete @ update @ count @ chr @ mid @ master @ truncate @ char @ declare @ or @or '@or%@or%20'@or'@and'@and%@and%20'@--";
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if(checkURISecurity(httpRequest, httpResponse)){
			httpResponse.setHeader("Allow", "请求地址中参数包含特殊字符！");  
            httpResponse.setStatus(400);  
			return;
		}
		
		if(checkParameterSecurity(httpRequest, httpResponse)){
			httpResponse.setHeader("Allow", "请求参数包含特殊字符！");  
            httpResponse.setStatus(400);  
			return;
        }else{
        	try{
        		chain.doFilter(request, response);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private boolean checkParameterSecurity(HttpServletRequest request,HttpServletResponse response) {
		Map map = request.getParameterMap();
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; ++i) {
			String key = keys[i].toString().replaceAll(" ", "");
			if("rnd".equalsIgnoreCase(key) || ("_".equalsIgnoreCase(key))){
				continue;
			}
			boolean flag1 = checkParameter(key, request, response);

			String[] values = (String[]) map.get(keys[i].toString());
			if (values.length <= 0){
				continue;
			}
			
			boolean flag2 = checkParameter(values[0].toLowerCase(), request, response);
			if(flag1 || flag2){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkURISecurity(HttpServletRequest request,HttpServletResponse response) {
		String requestURI = request.getRequestURI();			//用户	请求信息
		boolean flag = checkParameter(requestURI, request, response);
		if(flag){
			return true;
		}
		return false;
	}

	private boolean checkParameter(String text, HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = false;
		if (isInjectionStr(text) || text.indexOf("<") != -1 || text.indexOf(">") != -1) {
			flag = true;
		}
		return flag;
	}
	
	private boolean isInjectionStr(String text) {
		if (StringUtils.isNullOrEmpty(injectionStr)) {
			return false;
		}
		String[] injectionArray = StringUtils.split(injectionStr, '@');
		for (String str : injectionArray) {
			if (text.toLowerCase().indexOf(str.toLowerCase()) != -1) {
				log.info("===========源字符串发现注入字符============");
				return true;
			}
		}
		return false;
	}

	public static String stripXSSAndSql(String value) {
		if (value != null) {  
        	  value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
              value = value.replaceAll("eval\\((.*)\\)", "");
              value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
              value = value.replaceAll("script", "");
      		  value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
    		  value = value.replaceAll("'", "&#39;");           
		}
		return value;
	}
	
}
