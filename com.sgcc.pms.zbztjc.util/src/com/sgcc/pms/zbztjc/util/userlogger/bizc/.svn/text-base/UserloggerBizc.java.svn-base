package com.sgcc.pms.zbztjc.util.userlogger.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.TransactionRolledbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sgcc.pms.framework.base.util.UserInfoUtil;
import com.sgcc.pms.zbztjc.util.myUtils.Constants;
import com.sgcc.pms.zbztjc.util.myUtils.ConstantsDataBase;
import com.sgcc.pms.zbztjc.util.myUtils.Util;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.RestUtils;
import com.sgcc.uap.utils.StringUtils;
import com.sgcc.uap.utils.context.ClientContext;
import com.sgcc.uap.utils.context.ClientContextHolder;
/**
 * 用户操作信息查询业务层实现
 * @author gaotiedun
 *
 */
public class UserloggerBizc implements IUserloggerBizc{
		private static Log logger = LogFactory.getLog(UserloggerBizc.class);
		
		@Resource
		private IHibernateDao hibernateDao_ztjc;
		
		@SuppressWarnings("unchecked")
		@Override
		public QueryResultObject loadData(RequestCondition params){
			if(params == null){
				logger.info("前端请求参数传递有误！");
				return null;
			}
			try {
				List<QueryFilter> list = params.getQueryFilter();
				int pageIndex = Integer.parseInt(params.getPageIndex());
				int pageSize = Integer.parseInt(params.getPageSize());
				
				String czlx = null;				//操作类型
				String minTime = "";
				String maxTime = "";
				String eventType =null;			//事件类型
				String userName = null;			//用户名
				String userIP = null;			//用户IP
				String orderColumn = null;		//排序列
				String order = null ;			//排序顺序，true--正序；false---倒序
				for(int i=0;i<list.size();i++){
					if("czlx".equals(list.get(i).getFieldName())){
						if(Constants.MODIFY.equals(list.get(i).getValue().toString())){
							czlx = "修改";
						}else if(Constants.DELETE.equals(list.get(i).getValue().toString())){
							czlx = "删除";
						}else if(Constants.ADD.equals(list.get(i).getValue().toString())){
							czlx = "新增";
						}else if(Constants.EXPORT.equals(list.get(i).getValue().toString())){
							czlx = "导出";
						}else if(Constants.QUERY.equals(list.get(i).getValue().toString())){
							czlx = "查询";
						}else if(Constants.STAT.equals(list.get(i).getValue().toString())){
							czlx = "统计";
						}else if(Constants.CREATE.equals(list.get(i).getValue().toString())){
							czlx = "生成";
						}else if(Constants.ALARM.equals(list.get(i).getValue().toString())){
							czlx = "告警";
						}else if(Constants.SORT.equals(list.get(i).getValue().toString())){
							czlx = "排序";
						}else if(Constants.EXCESSAUTHORITY.equals(list.get(i).getValue().toString())){
							czlx = "越权";
						}else if(Constants.LOGIN.equals(list.get(i).getValue().toString())){
							czlx = "登录";
						}else if(Constants.LOGOUT.equals(list.get(i).getValue().toString())){
							czlx = "登出";
						}
					}else if("minTime".equals(list.get(i).getFieldName())){
						minTime = list.get(i).getValue().toString();
					}else if("maxTime".equals(list.get(i).getFieldName())){
						maxTime = list.get(i).getValue().toString();
					}else if("eventType".equals(list.get(i).getFieldName())){
						if(Constants.TRANSACTION.equals(list.get(i).getValue().toString())){
							eventType = "业务事件";
						}else if(Constants.SYSTEM.equals(list.get(i).getValue().toString())){
							eventType = "系统事件";
						}
					}else if("userName".equals(list.get(i).getFieldName())){
						userName = list.get(i).getValue().toString();
					}else if("userIP".equals(list.get(i).getFieldName())){
						userIP = list.get(i).getValue().toString();
					}else if("orderColumn".equals(list.get(i).getFieldName())){
						orderColumn = list.get(i).getValue().toString();
					}else if("order".equals(list.get(i).getFieldName())){
						order = list.get(i).getValue().toString();
					}
				}
				StringBuffer sql = new StringBuffer();
				sql.append("select userid, username, czlx, to_char(cztime, 'yyyy-mm-dd hh24:mi:ss') CZTIME,")
				   .append(" userip, result, cznr,EVENTTYPE,DEPTNAME,TRANSACTIONTYPE")
				   .append(" from "+ ConstantsDataBase.SCHEMA +"MWT_USERLOGGER")
				   .append(" where cztime between to_date('"+minTime+"', 'yyyy-mm-dd hh24:mi:ss') and to_date('"+maxTime+"', 'yyyy-mm-dd hh24:mi:ss')+1");
				if(!StringUtils.isNullOrEmpty(czlx)){		//如果非空
					sql.append(" AND czlx ='"+czlx+"' ");
				}
				if(!StringUtils.isNullOrEmpty(eventType)){		//如果非空
					sql.append(" AND EVENTTYPE ='"+eventType+"' ");
				}
				
				if(!StringUtils.isNullOrEmpty(userName)){		//如果非空
					sql.append(" AND USERNAME LIKE '%"+userName+"%' ");
				}
				
				if(!StringUtils.isNullOrEmpty(userIP)){			//如果非空
					sql.append(" AND USERIP LIKE '%"+userIP+"%' ");
				}
				
				if(null != orderColumn){
					sql.append(" order by ").append(orderColumn).append((order.equals("true"))? " ASC":" DESC");
				}else{
					sql.append(" order by cztime desc");			//如果无排序
				}
				List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
				List<Object[]> listObj = hibernateDao_ztjc.executeSqlQuery(sql.toString(),pageIndex,pageSize);
				for(Object[] a : listObj){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("USERID", a[0]);
					map.put("USERNAME", a[1]);
					map.put("CZLX", a[2]);
					map.put("CZTIME", a[3]);
					map.put("USERIP", a[4]);
					map.put("RESULT", a[5]);
					map.put("CZNR", a[6]);
					map.put("EVENTTYPE", a[7]);
					map.put("DEPTNAME", a[8]);
					map.put("TRANSACTIONTYPE", a[9]);
					listMap.add(map);
				}
				int count = getCount(sql.toString());
				return RestUtils.wrappQueryResult(listMap, count);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new QueryResultObject();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public int getCount(String sql) {
			int count = 0;
			try {
				List<BigDecimal> listCount = hibernateDao_ztjc.executeSqlQuery("select count(1) from ("+sql+")");
				if(listCount == null){
					count = 0;
				}else{
					count = listCount.get(0).intValue();
				}
				return count;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}
		
		@Override
		public List<Map<String, String>> updateUserLogger(List<Map<String,String>> list){
			if(!isOpenUserLogger()){
				return new ArrayList<Map<String,String>>();
			}
			List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
			Map<String, String> map = new HashMap<String, String>();
			/*if(!checkBck()){								//判断审计日志是否超标，如果超标则备份，备份不成功
				map.put("result", "fail");
				listMap.add(map);
				return listMap;
			}*/
			ClientContext clientContext = ClientContextHolder.getInstance().getClientContext();
			Map<String, String> user = UserInfoUtil.getLoginUser(); // 用户信息
			try {
				if(null == list || 0 == list.size()){
					logger.error("执行审计时发生异常！传参为空");
					return null;
				}
				String userId = list.get(0).get("userId");				//用户ID
				if(null == userId){
					userId = user.get("userID");
				}
				String userName =list.get(0).get("userName");			//用户名
				if(null == userName){
					userName = user.get("userName");
				}
				String userIp = list.get(0).get("userIp");				//用户IP
				if(null == userIp){
					userIp = clientContext.getRequestIP();
				}
				
				System.out.println("用户ID"+userId+"用户名"+userName+"用户IP"+userIp);
				String czlx = list.get(0).get("czlx");					//用户操作类型。例如新建,修改，删除操作
				String result = list.get(0).get("result");				//用户操作结果
				String transactionType = list.get(0).get("transactionType");	//业务类型
				String eventType = list.get(0).get("eventType");				//业务类型
				if(StringUtils.isNullOrEmpty(eventType)){			//如果为空，则为业务事件
					eventType = "业务事件";
				}
				String currentTime = Util.getCurrentTime("yyyy-MM-dd HH:mm:ss");
				StringBuffer content = new StringBuffer();
				if (!StringUtils.isNullOrEmpty(userName)) {				//在审计内容中加入用户名
					content.append(userName);
				}
				
				if (!StringUtils.isNullOrEmpty(userIp)) {				//在审计内容中加入用户IP
					content.append("在").append(userIp).append("IP");
				}
				
				if (!StringUtils.isNullOrEmpty(currentTime)) {				//在审计内容中加入时间
					content.append("在").append(currentTime).append("时间");
				}
				
				if (!StringUtils.isNullOrEmpty(transactionType)) {				//在审计内容中加入菜单信息
					content.append(transactionType);
				}
				
				if (!StringUtils.isNullOrEmpty(czlx)) {				//在审计内容中加入菜单信息
					content.append("执行了").append(czlx).append("操作");
				}
				
				if (!StringUtils.isNullOrEmpty(result)) {				//在审计内容中加入执行结果
					content.append(",操作结果为").append(result);
				}
				
				/*if(StringUtils.isNullOrEmpty(userId) || StringUtils.isNullOrEmpty(userName)){
					map.put("result", "error");
					listMap.add(map);
					return listMap;
				}*/
				String objId = getObjId();
				StringBuffer sql = new StringBuffer();
				sql.append("INSERT into "+ ConstantsDataBase.SCHEMA +"MWT_USERLOGGER")
				   .append(" (OBJ_ID, OBJ_DISPIDX, USERID, USERNAME, userIP, CZLX, CZTIME, RESULT,cznr,CZHOUR,EVENTTYPE,DEPTNAME,TRANSACTIONTYPE)")
				   .append(" values('").append(objId).append("',0,'").append(userId)
				   .append("','").append(userName).append("','").append(userIp).append("','")
				   .append(czlx).append("',").append("to_date('").append(currentTime).append("','yyyy-mm-dd HH24:mi:ss'),'").append(result.toString())
				   .append("','").append(content).append("',to_char(sysdate,'hh24'),'").append(eventType).append("','上海电力','")
				   .append(transactionType).append("')");
				int n = hibernateDao_ztjc.executeSqlUpdate(sql.toString());
				if(n > 0){
					map.put("result", "success");
				}else{
					map.put("result", "fail");
				}
				listMap.add(map);
				return listMap;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ArrayList<Map<String,String>>();
		}
		/**
		 * 获取OBJ_ID值
		 * @return
		 */
		private String getObjId(){
			String objid = "";
			String sql = "SELECT "+ ConstantsDataBase.SCHEMA +"NEWGUID||'-00001' FROM DUAL";
			try {
				@SuppressWarnings("unchecked")
				List<String> listObj = hibernateDao_ztjc.executeSqlQuery(sql);
				objid = listObj.get(0);
				return objid;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return objid;
		}
		@SuppressWarnings("unchecked")
		@Override
		public QueryResultObject queryInfo(RequestCondition params){
			if(params == null){
				logger.info("前端请求参数传递有误！");
				return null;
			}
			try {
				List<QueryFilter> list = params.getQueryFilter();
				int pageIndex = Integer.parseInt(params.getPageIndex());
				int pageSize = Integer.parseInt(params.getPageSize());
				String czlx = null;				//操作类型
				String minTime = "";
				String maxTime = "";
				String eventType =null;			//事件类型
				String userName = null;			//用户名
				String userIP = null;			//用户IP
				for(int i=0;i<list.size();i++){
					if("czlx".equals(list.get(i).getFieldName())){
						if(Constants.MODIFY.equals(list.get(i).getValue().toString())){
							czlx = "修改";
						}else if(Constants.DELETE.equals(list.get(i).getValue().toString())){
							czlx = "删除";
						}else if(Constants.ADD.equals(list.get(i).getValue().toString())){
							czlx = "新增";
						}else if(Constants.EXPORT.equals(list.get(i).getValue().toString())){
							czlx = "导出";
						}else if(Constants.QUERY.equals(list.get(i).getValue().toString())){
							czlx = "查询";
						}else if(Constants.STAT.equals(list.get(i).getValue().toString())){
							czlx = "统计";
						}else if(Constants.CREATE.equals(list.get(i).getValue().toString())){
							czlx = "生成";
						}else if(Constants.ALARM.equals(list.get(i).getValue().toString())){
							czlx = "告警";
						}else if(Constants.SORT.equals(list.get(i).getValue().toString())){
							czlx = "排序";
						}
					}else if("minTime".equals(list.get(i).getFieldName())){
						minTime = list.get(i).getValue().toString();
					}else if("maxTime".equals(list.get(i).getFieldName())){
						maxTime = list.get(i).getValue().toString();
					}else if("eventType".equals(list.get(i).getFieldName())){
						if(Constants.TRANSACTION.equals(list.get(i).getValue().toString())){
							eventType = "业务事件";
						}else if(Constants.SYSTEM.equals(list.get(i).getValue().toString())){
							eventType = "系统事件";
						}
					}else if("userName".equals(list.get(i).getFieldName())){
						userName = list.get(i).getValue().toString();
					}else if("userIP".equals(list.get(i).getFieldName())){
						userIP = list.get(i).getValue().toString();
					}
				}
				
				StringBuffer sql = new StringBuffer();
				sql.append("select username,czlx,eventtype, cznum")
				   .append(" from (select username,czlx,eventtype, count(1) cznum")
				   .append("  from "+ ConstantsDataBase.SCHEMA +"mwt_userlogger")
				   .append("  where cztime >= to_date('"+minTime+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss')")
				   .append("  and cztime <= to_date('"+maxTime+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss')");
				   
				   if(!StringUtils.isNullOrEmpty(czlx)){		//如果非空
						sql.append(" AND czlx ='"+czlx+"' ");
					}
					if(!StringUtils.isNullOrEmpty(eventType)){		//如果非空
						sql.append(" AND EVENTTYPE ='"+eventType+"' ");
					}
					
					if(!StringUtils.isNullOrEmpty(userName)){		//如果非空
						sql.append(" AND USERNAME LIKE '%"+userName+"%' ");
					}
					
					if(!StringUtils.isNullOrEmpty(userIP)){			//如果非空
						sql.append(" AND USERIP LIKE '%"+userIP+"%' ");
					}
				   
					sql.append("  group by username,czlx,eventtype )");
				
				List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
				List<Object[]> listObj = hibernateDao_ztjc.executeSqlQuery(sql.toString(),pageIndex,pageSize);
				for(Object[] a : listObj){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("USERNAME", a[0]);
					map.put("CZLX", a[1]);
					map.put("EVENTTYPE", a[2]);
					map.put("CZNUM", a[3]);
					listMap.add(map);
				}
				int count = getCount(sql.toString());
				return RestUtils.wrappQueryResult(listMap, count);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new QueryResultObject();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public QueryResultObject querytj(RequestCondition params){
			if(params == null){
				logger.info("前端请求参数传递有误！");
				return null;
			}
			try {
				List<QueryFilter> list = params.getQueryFilter();
				String minTime = "";
				String maxTime = "";
				String cols = "";
				for(int i=0;i<list.size();i++){
					if("minTime".equals(list.get(i).getFieldName())){
						minTime = list.get(i).getValue().toString();
					}else if("maxTime".equals(list.get(i).getFieldName())){
						maxTime = list.get(i).getValue().toString();
					}else if("tjlx".equals(list.get(i).getFieldName())){
						String tjlx = list.get(i).getValue().toString();
						if("uname".equals(tjlx)){
							cols="USERNAME";
						}else if("uip".equals(tjlx)){
							cols="USERIP";
						}else if("czlx".equals(tjlx)){
							cols="CZLX";
						}else if("etype".equals(tjlx)){
							cols="EVENTTYPE";
						}else {
							cols="USERNAME";
						}
						
					}
				}
				
				StringBuffer sql = new StringBuffer();
				sql.append("select ").append(cols).append(", count(1) cznum")
				   .append("  from "+ ConstantsDataBase.SCHEMA +"mwt_userlogger")
				   .append("  where cztime >= to_date('"+minTime+" 00:00:00', 'yyyy-mm-dd hh24:mi:ss')")
				   .append("  and cztime <= to_date('"+maxTime+" 23:59:59', 'yyyy-mm-dd hh24:mi:ss')")
				   .append("  group by ").append(cols).append(" ");
				
				List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
				List<Object[]> listObj = hibernateDao_ztjc.executeSqlQuery(sql.toString());
				for(Object[] a : listObj){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("TJLX", a[0]);
					map.put("CZNUM", a[1]);
					listMap.add(map);
				}
				return RestUtils.wrappQueryResult(listMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new QueryResultObject();
		}
		
		@Override
		public String getLoggerAlarm(){
			String result = Constants.NORMAL;
			double limitSet = 500.00;
			double userLoggerSize = 0.00;
			try {
				//查询设定的审计上限值
				String queryLimitSet = "SELECT CONFIGVALUE FROM "+ConstantsDataBase.SCHEMA+"CMST_CONFIGURE WHERE CONFIGNAME ='userLoggerLimit'";
				List limitSetList = hibernateDao_ztjc.executeSqlQuery(queryLimitSet);
				if (null != limitSetList && 0 != limitSetList.size()) {				//如果查询结果集不为空
					limitSet = Double.parseDouble(limitSetList.get(0).toString());
				}
				//查询审计表的大小
				StringBuilder queryUserloggerSizeSql = new StringBuilder(500);
				queryUserloggerSizeSql.append(" select t.table_name , tt.num_rows,   a.BYTES/1024/1024 USERLOGGERSIZE ") 
						.append(" from USER_tab_comments t, (select sum(bytes) bytes,segment_name from  user_segments ") 
						.append(" group by segment_name) a,  user_tables tt where  t.table_name = a.segment_name ")
						.append(" and tt.TABLE_NAME = t.table_name and t.table_name = 'MWT_USERLOGGER'");
				System.out.println("queryUserloggerSizeSql:"+queryUserloggerSizeSql);
				List<Map<String,Object>> userLoggerSizeList = hibernateDao_ztjc.queryForListWithSql(queryUserloggerSizeSql.toString());
				//如果结果集不为空
				if (null != userLoggerSizeList && 0  != userLoggerSizeList.size()) {
					userLoggerSize = Double.parseDouble(userLoggerSizeList.get(0).get("USERLOGGERSIZE").toString());
				}
				double alarmLimit = (BigDecimal.valueOf(limitSet).multiply(BigDecimal.valueOf(0.80))).doubleValue();
				//比较审计表的大小和审计表的上限报警值
				if (userLoggerSize >= alarmLimit) {			//如果超过了预警限制 ，最大值的80%
					result =  Constants.ALARM;				//告警
				}else{
					result = Constants.NORMAL;				//正常
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		/**
		 * 审计的检查备份
		 * @return
		 */
		private boolean checkBck(){
			if(getLoggerAlarm().equals(Constants.ALARM)){		//如果告警
				return backup();								//进行备份，如果备份成功，返回true
			}
			return true;									//如果没告警，则返回true；
		}
		
		/**
		 * 执行审计日志备份的方法
		 * 1、将日志表插入到备份表中
		 * 2、将日志表数据清空
		 * 3、将备份操作记录并插入到审计表中
		 */
		@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
		@Override
		public Boolean backup() {
			int backResult = 0;
			int deleteResult = 0;
			//将审计日志插入到日志备份表中  日志备份表CMST_USERLOGGERBACK
			try{
				StringBuffer backSql = new StringBuffer();
				backSql.append(" INSERT INTO  ").append(ConstantsDataBase.SCHEMA).append("CMST_USERLOGGERBACK")
				.append(" ( SELECT * FROM ").append(ConstantsDataBase.SCHEMA).append("MWT_USERLOGGER )");
				backResult =  hibernateDao_ztjc.executeSqlUpdate(backSql.toString());
				
				if(backResult > 0){							//如果备份成功,删除审计表的数据
					String deleteSql = "DELETE FROM "+ConstantsDataBase.SCHEMA+"MWT_USERLOGGER";
					deleteResult = hibernateDao_ztjc.executeSqlUpdate(deleteSql);
				}
			}catch(Exception e){
				e.printStackTrace();
				try {
					throw new TransactionRolledbackException();
				} catch (TransactionRolledbackException e1) {
					e1.printStackTrace();
				}
			}
			if(backResult >0 && deleteResult >0){
				return true;
			}
			return false;
		}
		
		public boolean isOpenUserLogger() {
			try {
				String querySql = "SELECT CONFIGVALUE FROM "+ConstantsDataBase.SCHEMA+"CMST_CONFIGURE WHERE CONFIGNAME = 'isopenuserlogger'";
				List resultList = hibernateDao_ztjc.executeSqlQuery(querySql);
				if (null == resultList || 0 == resultList.size()) {
					return false;
				}else {
					if ("true".equalsIgnoreCase(resultList.get(0).toString())) {
						return true;
					}
				}
			} catch (Exception e) {
				return false;
			}
			return false;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public List queryLimitSetForm(String formId) {
			List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
			try {
				if (StringUtils.isNotEmpty(formId)) {
					String querySql = "SELECT OBJ_ID,CONFIGNAME,CONFIGVALUE LIMITSET,'80%' ALARMLIMIT FROM "+ConstantsDataBase.SCHEMA+"CMST_CONFIGURE WHERE CONFIGNAME = '"+formId+"'";
					System.out.println(querySql);
					resultList = hibernateDao_ztjc.queryForListWithSql(querySql);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return resultList;
			}
			return resultList;
		}
		
		@Override
		public Boolean updateLimitSet(Map<String,String> params) {
			int updateResult = 0;
			try{
				String objId = params.get("objId").toString();				//修改数据的OBJ_ID
				String limitSet = params.get("limitSet").toString();		//审计上限设定值
				if (Integer.parseInt(limitSet) > 10240 || 0 >= Integer.parseInt(limitSet) ) {
					return false;
				}
				
				StringBuilder updateSql = new StringBuilder(100);
				updateSql.append(" UPDATE ").append(ConstantsDataBase.SCHEMA).append("CMST_CONFIGURE")
				.append(" SET CONFIGVALUE =  '").append(limitSet).append("' WHERE OBJ_ID = '").append(objId).append("'");
				updateResult =  hibernateDao_ztjc.executeSqlUpdate(updateSql.toString());
				//如果更新成功
				if(updateResult >0){
					return true;
				}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return false;
		}
}
