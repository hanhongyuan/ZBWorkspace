package com.sgcc.pms.zbztjc.util.filter;

/**
 * 
 * @author llm
 * 
 * xss过滤器开关变量处理类
 * 
 * 2018年5月18日
 *
 */
public class XssXF_Manager {
	
	private static boolean idmon = true;

	public static boolean getIdmon() {
		return idmon;
	}

	public static void setIdmon(boolean idmon) {
		XssXF_Manager.idmon = idmon;
	}
}
