//******************************************************************
//系统名称：PMS2.0
//模块名称：过滤器
//版本信息：
//版本             日期                                 作者              备注
//1.0     2013/08/12      巩传龙　      创建
//Copyright(C)2014-2022 NARI Information and Communication Technology Branch. All rights reserved.
//#作者:巩传龙$权重:100%$ 手机:18651872564#
//******************************************************************
package com.sgcc.pms.zbztjc.util.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.sgcc.pms.framework.base.util.MsgUtil;
import com.sgcc.pms.framework.base.util.UserInfoUtil;
import com.sgcc.pms.zbztjc.util.loggerSave.bizc.LoggerSaveBizc;
import com.sgcc.uap.config.util.PlatformConfigUtil;
import com.sgcc.uap.kernel.helper.ModuleSRModelHelper;
import com.sgcc.uap.kernel.util.ModuleUtils;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.impl.HibernateDaoImpl;
import com.sgcc.uap.utils.StringUtils;
import com.sgcc.uap.utils.context.ClientContext;
import com.sgcc.uap.utils.context.ClientContextHolder;

/**
 * <b>概述</b>：<br>
 * 过滤器
 * <p>
 * <b>功能</b>：<br>
 * 判断当前用户请求的Url是否在受控资源注册Url的集合里面<br>
 * 在当前用户允许访问的功能权限集合里，允许访问，<br>
 * 其他转向登陆页面<br>
 * 
 * @author GCL
 */
public class ExcessAuthorityFilter implements Filter {
	/**
	 * filter过滤器初始化
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * filter过滤器逻辑事件
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		String resturl = servletRequest.getRequestURI();
		if (resturl.contains("/com.sgcc.pms.zbztjc.util/userlogger/scripts")
				|| resturl.contains("/com.sgcc.pms.zbztjc.util/enum/")
				|| resturl
						.contains("/com.sgcc.pms.zbztjc.util/userlogger/resources")) { // 如果调用的是审计记录方法
			try{
				chain.doFilter(servletRequest, servletResponse); // 则允许执行
			}catch(Exception e){
				e.printStackTrace();
			}
			return;
		}
		Map<String,String> userSessionMap = (Map<String, String>) session.getAttribute("user");
		String userName = userSessionMap.get("name");		//当前登录用户名
		// // 获得用户请求的URI 依赖的页面
		String path = servletRequest.getHeader("referer");
		// 登陆页面无需过滤
		if (!StringUtils.isNullOrEmpty(path)) {
			if (path.indexOf("/portal/default.jsp") > -1
					|| path.indexOf("/portal/login.jsp") > -1
					|| path.indexOf("/portal/mainNew/index.jsp") > -1
					|| path.indexOf("/portal/zbmain/index.jsp") > -1
					|| path.indexOf("/portal/userlogin/index.jsp") > -1
					|| path.indexOf("/portal/zbmainUser/index.jsp") > -1
					|| path.indexOf("/portal/main/index.jsp") > -1
					|| path.indexOf("/mx/servlets/mxframework") > -1) {
				try{
					chain.doFilter(servletRequest, servletResponse);
					return;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		if (!StringUtils.isNullOrEmpty(resturl) && !StringUtils.isNullOrEmpty(userName)) {		//如果用户名和访问地址不为空
			try {
				//如果是审计用户
				String userLoggerName = StringUtils.isNullOrEmpty(PlatformConfigUtil.getString("USERLOGGERUSER"))?"ztjczbsj":PlatformConfigUtil.getString("USERLOGGERUSER");	//ztjczbsj
				if(userName.equals(userLoggerName)){
					if(resturl.contains("/com.sgcc.pms.zbztjc.util/userlogger/") || resturl.contains("/mx/servlets/mxframework") || resturl.contains("/com.sgcc.pms.zbztjc.util/enum") || resturl.contains("/com.sgcc.pms.zbztjc.util/rest/userlogger/") || resturl.contains("/pmsframework/rest/DocumentController/") ){		//如果是审计菜单
						try{
							chain.doFilter(servletRequest, servletResponse);
						}catch(Exception e){
							e.printStackTrace();
						}
						return;
					}else{		//如果不是审计菜单
						// 如果不是审计用户请求不再转发直接返回
						RequestDispatcher rd = request.getRequestDispatcher("/com.sgcc.pms.zbztjc.util/error.jsp");
						rd.forward(servletRequest, servletResponse);
						saveLogger("越权", "状态监测-越权审计", "操作失败");
					}
				}else{			//如果不是审计用户
					if(resturl.contains("/com.sgcc.pms.zbztjc.util/userlogger/index.jsp") || resturl.contains("/com.sgcc.pms.zbztjc.util/rest/userlogger/") || resturl.contains("/pmsframework/rest/excel/download") || resturl.contains("/pmsframework/rest/DocumentController/documentLoad/WORD")
							|| resturl.contains("/pmsframework/rest/DocumentController/documentLoad/PDF") || resturl.contains("/pmsframework/rest/DocumentController/documentLoad/HTML")){		//如果时审计菜单
						RequestDispatcher rd = request.getRequestDispatcher("/com.sgcc.pms.zbztjc.util/error.jsp");
						rd.forward(servletRequest, servletResponse);
						saveLogger("越权", "状态监测-越权审计", "操作失败");
					}else{		//如果不是审计菜单
						try{
							chain.doFilter(servletRequest, servletResponse);
						}catch(Exception e){
							e.printStackTrace();
						}
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			RequestDispatcher rd = request.getRequestDispatcher("/com.sgcc.pms.zbztjc.util/error.jsp");
			rd.forward(servletRequest, servletResponse);
			saveLogger("越权", "状态监测-越权审计", "操作失败");
		}
	}
	
	private void saveLogger(String czlx, String transactionType, String result) {
		IHibernateDao hibernate = getHibernateDao("hibernateDaoService_ztjc");
		LoggerSaveBizc loggerSave = new LoggerSaveBizc();
		List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
		Map<String, String> paramMap = new HashMap<String, String>();
		ClientContext clientContext = ClientContextHolder.getInstance()
				.getClientContext();
		Map<String, String> user = UserInfoUtil.getLoginUser(); // 用户信息
		paramMap.put("czlx", czlx);
		paramMap.put("transactionType", transactionType);
		paramMap.put("result", result);
		paramMap.put("userId", user.get("userID"));
		paramMap.put("userName", user.get("userName"));
		paramMap.put("userIp", clientContext.getRequestIP());
		paramMap.put("eventType", "系统事件");
		paramList.add(paramMap);
		loggerSave.saveLogger(paramList,hibernate);
		//translate.saveLogger(paramList);
	}
	
	private IHibernateDao getHibernateDao(String dateSource){
		BundleContext bundleContext = ModuleUtils.getBundleContext(MsgUtil.class);
 	    // 获取实现javax.sql.DataSource接口的所有服务
 	    ServiceReference[] sfAarray = ModuleSRModelHelper.getServiceReferences(
 	                "com.sgcc.uap.persistence.IHibernateDao", bundleContext);
 	    // 查找指定id的数据源服务
 	    for (ServiceReference sf : sfAarray) {
             String serviceIdname = sf.getProperty("platform_service_idname")
                     .toString();
             if (StringUtils.equals(dateSource, serviceIdname)) {
            	 return (HibernateDaoImpl) bundleContext.getService(sf);
             }
         }
 	    return null;
	}
	
	/*
	 * destroy filter
	 * 
	 * @see
	 */
	public void destroy() {
	}

}
