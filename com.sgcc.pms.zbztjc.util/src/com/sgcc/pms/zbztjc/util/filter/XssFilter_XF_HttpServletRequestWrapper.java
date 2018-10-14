//******************************************************************   
//系统名称：PMS2.0
//Copyright(C)2014-2022 NARI Information and Communication Technology Branch. All rights reserved.
//版本信息:1.0    
//#作者:fxs$权重：90%$手机：13575710225#    
//#作者:翟锐$权重:10%$手机:13675132204#
//******************************************************************
package com.sgcc.pms.zbztjc.util.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author llm
 * 
 *         xssFilter_XF（xss过滤器功能实现逻辑类）
 * 
 *         北京会议：于6月份版本中完善XSS过滤器；
 * 
 *         实现逻辑： 1、XssXF_Manager类中的过滤器常量默认为关闭（false）状态；
 * 
 *         2、现场需在全局参数配置表中配置GLBCFG_KEY为"IF_XSS_OPEN"的数据信息，当现场不配置该变量时过滤器默认为关闭状态；
 * 
 *         3、现场单节点操作：在运行监控页面提供开启和关闭XSS过滤器功能； 
 *         I、当数据库中“IF_XSS_OPEN”配置的GLBCFG_VALUE值为“T”时，表示开启xss过滤器；这时点击页面的“开启本地过滤器”，程序会刷新
 *            本地XssXF_Manager类中的缓存变量为“true”；过滤器获取该缓存变量，从而启用过滤器功能；
 *         II、当 数据库中“IF_XSS_OPEN”配置的GLBCFG_VALUE值为“F”时，表示关闭xss过滤器；这时点击页面的“关闭本地过滤器”，程序会刷新
 *            本地XssXF_Manager类中的缓存变量为“false”；过滤器获取该缓存变量，从而关闭过滤器功能；
 * 
 *         4、现场多节点操作：在运行监控页面提供开启和关闭XSS过滤器功能； 
 *         I、当数据库中“IF_XSS_OPEN”配置的GLBCFG_VALUE值为“T”时，表示开启xss过滤器；这时点击页面的“开启节点过滤器”，程序会刷新
 *         	   本地XssXF_Manager类中的缓存变量为“true”，过滤器获取该缓存变量，从而开启过滤器功能；
 *         II、当数据库中“IF_XSS_OPEN”配置的GLBCFG_VALUE值为“F”时，表示关闭xss过滤器；这时点击页面的“关闭节点过滤器”，程序会刷新
 *             本地XssXF_Manager类中的缓存变量为“false”，过滤器获取该缓存变量，从而关闭过滤器功能；
 * 
 *         5、现场需在全局参数配置表中配置GLBCFG_KEY值为"SERVERIP_XSS"的数据信息，该数据信息用于存在各服务器节点的ip地址和端口号信息，
 *         各节点间用“,”分割；（如：【172.16.222.11:9000，172.16.222.12:9000】）
 * 
 *         2018年5月18日
 * 
 */
public class XssFilter_XF_HttpServletRequestWrapper extends
		HttpServletRequestWrapper {

	HttpServletRequest orgRequest = null;

	public XssFilter_XF_HttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		orgRequest = request;
	}

	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss  <br/>
	 * 如果需要获得原始的值，则通过super.getParameter(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(xssEncode(name));
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * 覆盖getParameterValues方法，将参数名和参数值都做xss & sql过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodeValues = new String[count];
		for (int i = 0; i < count; i++) {
//			System.out.println("values=="+values[i]);
			encodeValues[i] = xssEncode(values[i]);
		}
		return encodeValues;
	}

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做xss & sql过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
	 * getHeaderNames 也可能需要覆盖
	 */
	@Override
	public String getHeader(String name) {

		String value = super.getHeader(xssEncode(name));
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * 将容易引起xss & sql漏洞的半角字符直接替换成全角字符
	 * 
	 * @param s
	 * @return
	 */
	private static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		} else {
			s = stripXSSAndSql(s);
		}
		return s;
	}

	/**
	 * 获取最原始的request
	 * 
	 * @return
	 */
	public HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	/**
	 * 获取最原始的request的静态方法
	 * 
	 * @return
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
		if (req instanceof XssFilter_XF_HttpServletRequestWrapper) {
			return ((XssFilter_XF_HttpServletRequestWrapper) req)
					.getOrgRequest();
		}

		return req;
	}

	/**
	 * 防止xss跨脚本攻击（替换，根据实际情况调整）
	 * 
	 * [\\\"\\\'] 表示 \"\' ；
	 * [\\s]*  表示空白符；
	 * (.*)  匹配任意数量的非回车的所有字符
	 * 
	 * <、  >     替换为“&lt;” 、“&gt;”
	 * eval()    替换为""
	 * javascript  替换为""
	 * script     替换为""
	 * (         替换为  &#40;
	 * )         替换为 &#41;
	 * '         替换为&#39
	 * ;&gt;@&lt;@&lt;/script@&lt;script@script&gt;
	 * @script:@style=@alert@location@window@input@focus
	 * @script@ like @and @ exec @ sleep @ insert 
	 * @prompt@confirm@ select @ delete @ update @ count 
	 * @ chr @ mid @ master @ truncate @ char @ declare @ or @";

	 */

	public static String stripXSSAndSql(String value) {
		if (value != null) {  
        	  value = value.replaceAll("<", " ").replaceAll(">", " ");
              value = value.replaceAll("eval\\((.*)\\)", " ");
              value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
              value = value.replaceAll("script", " ");
      		  value = value.replaceAll("\\(", " ").replaceAll("\\)", " ");
    		  value = value.replaceAll("'", " ");  
    		  value = value.replaceAll(" select ", " ");
    		  value = value.replaceAll(" delete ", " ");
    		  value = value.replaceAll(" update ", " ");
    		 // value = value.replaceAll(" count( ", " ");
    		  value = value.replaceAll(" chr ", " ");
    		  value = value.replaceAll(" master ", " ");
    		  value = value.replaceAll(" truncate ", " ");
    		  value = value.replaceAll(" char ", " ");
    		  value = value.replaceAll(" or ", " ");
    		  value = value.replaceAll(" exec ", " ");
    		  value = value.replaceAll(" like ", " ");
    		  value = value.replaceAll("and ", " ");
    		  value = value.replaceAll(" insert ", " ");
    		  
    		  value = value.replaceAll("location", " ");
    		  value = value.replaceAll("window", " ");
    		  value = value.replaceAll("input", " ");
    		  value = value.replaceAll("focus", " ");
    		  value = value.replaceAll("alert", " ");
    		  value = value.replaceAll("style=", " ");
		}
		return value;
	}
	
}
