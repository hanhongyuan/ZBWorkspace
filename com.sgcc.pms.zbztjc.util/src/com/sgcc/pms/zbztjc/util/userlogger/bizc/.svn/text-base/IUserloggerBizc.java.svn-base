package com.sgcc.pms.zbztjc.util.userlogger.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

/**
 * 用户操作信息查询业务层接口
 * @author gtd
 *
 */
public interface IUserloggerBizc {
		/**
		 * 加载用户操作信息
		 * @param params
		 * @return
		 */
		public QueryResultObject loadData(RequestCondition params);
		/**
		 * 获取记录数
		 * @param sql
		 * @return
		 */
		public int getCount(String sql);
		/**
		 * 更新用户操作日志
		 * @param userId
		 * @param userName
		 * @return
		 */
		public List<Map<String, String>> updateUserLogger(List<Map<String,String>> list);
		/**
		 * 加载审计查阅信息
		 * @param params
		 * @return
		 */
		public QueryResultObject queryInfo(RequestCondition params);
		/**
		 * 加载审计查阅信息
		 * @param params
		 * @return
		 */
		public QueryResultObject querytj(RequestCondition params);
		
		public String getLoggerAlarm();
		
		/**
		 * 执行备份审计日志的方法
		 * @return
		 */
		public Boolean backup();
		
		public Boolean updateLimitSet(Map<String,String> params);
		
		public List queryLimitSetForm(String formId);
}
