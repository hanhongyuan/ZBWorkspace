package com.sgcc.pms.zbztjc.myUtils;

import org.apache.commons.lang.StringUtils;

class Ternary {

	public static Object getNotNull(Object variable, Object tTrue) {
		return isNull(variable) ? tTrue : variable;
	}
	
	private static boolean isNull(Object variable) {
		// 判断类型
		if(variable instanceof Integer) // 数字类型
		   return ((Integer)variable) == 0;
		else // 字符串类型
		   return variable == null || variable.toString().equals(StringUtils.EMPTY);
	}
}
