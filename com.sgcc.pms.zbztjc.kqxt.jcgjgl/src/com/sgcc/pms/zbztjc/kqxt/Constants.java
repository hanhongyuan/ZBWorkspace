package com.sgcc.pms.zbztjc.kqxt;

public class Constants {
	public static final String BDNAME = "bd"; // 定义一个常量，代表变电名称

	public static final String SDNAME = "sd"; // 定义一个常量，代表输电名称

	public static final String CABLENAME = "cable"; // 定义一个常量，代表电缆

	public static final String DEPTNAME = "dept"; // 定义一个常量，代表所属地市

	public static final String ZGS = "mostCompany"; // 定义一个常量，代表总公司

	public static final String BDZ = "bdz";			//定义一个变量，代表变电站
	
	public static final String SSXT = "ssxt";		//定义一个变量，代表所属系统
	
	public static final String SSDW = "ssdw";		//定义一个变量，代表所属单位

	public static final String LINE = "linkedLine"; // 定义一个常量，代表所属线路

	public static final String BDZANDLINE = "bdz-ssxl"; // 定义一个常量，代表变电站和所属线路

	public static final String ROOT = "root"; // 定义一个常量，代表根节点

	public static final String GJSJSTART = "gjsjstart"; // 告警开始时间

	public static final String GJSJEND = "gjsjend"; // 告警结束时间

	public static final String XZXL = "xzxl"; // 选择线路

	public static final String JCLX = "jclx"; // 监测类型

	public static final String ISHANDLED = "ishandled"; // 是否处理

	public static final String ISXC = "isxc"; // 是否消除

	public static final String GJSJ = "gjsj"; // 告警时间

	public static final String TJXM = "tjxm"; // 统计项目

	public static final String ALL = "all"; // 全部

	public static final String T = "T"; // 是

	public static final String F = "F"; // 否

	public static final String DYDJ = "dydj"; // 电压等级

	public static final String XLMC = "xlmc"; // 线路名称

	public static final String GJJB = "gjjb"; // 告警级别

	public static final String SHI = "是"; // 是

	public static final String FOU = "否"; // 否

	public static final String MONITORINGTYPE = "MONITORINGTYPE"; // 监测类型

	public static final String VOLTAGEGRADE = "VOLTAGEGRADE"; // 电压等级

	public static final String ALARMLEVEL = "ALARMLEVEL"; // 告警级别

	public static final String ABS = "ABS"; // 如果是监测类型SF6气体压力的绝对气体压力

	public static final String REL = "REL"; // 如果是新的监测类型SF6气体压力的相对产气速率

	public static final String SD_PREFIX = "01"; // 输电监测类型开头

	public static final String BD_PREFIX = "02"; // 变电监测类型开头

	public static final String CABLE_PREFIX = "03"; // 电缆监测类型开头

	public static final String CLEAR = "clear"; // 如果是清除告警

	public static final String SAVE = "save"; // 如果是保存告警 即处理告警

	public static final String BATCH = "batch"; // 如果是批量处理告警

	public static final String TYPE_PREFIX = "type_prefix"; // 监测类型的开头

	public static final String EQUIPMENTTYPE = "equipmentType"; // 监测类型中的检测设备类型
}
