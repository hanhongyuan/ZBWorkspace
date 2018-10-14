package com.sgcc.pms.zbztjc.util.loggerSave.bizc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.TransactionRolledbackException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sgcc.pms.framework.base.util.UserInfoUtil;
import com.sgcc.pms.zbztjc.util.myUtils.Constants;
import com.sgcc.pms.zbztjc.util.myUtils.ConstantsDataBase;
import com.sgcc.pms.zbztjc.util.myUtils.Util;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.utils.StringUtils;
import com.sgcc.uap.utils.context.ClientContext;
import com.sgcc.uap.utils.context.ClientContextHolder;

public class LoggerSaveBizc implements ILoggerSaveBizc{
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	public void saveLogger(List<Map<String,String>> list,IHibernateDao hibernate){
		if(null == this.hibernateDao_ztjc){
			this.hibernateDao_ztjc = hibernate;
		}
		saveLogger(list);
	}
	
	@Override
	public List<Map<String, String>> saveLogger(List<Map<String,String>> list){
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
			
			String currentTime = Util.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String eventType = list.get(0).get("eventType");		//事件类型
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
			
			if(StringUtils.isNullOrEmpty(eventType)){			//如果为空，则为业务事件
				if(czlx.equals("登录") || czlx.equals("登出")){		//如果操作类型为登录或登出 则为系统事件
					eventType = "系统事件";
				}else{
					eventType = "业务事件";
				}
			}
			
			String objId = getObjId();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT into "+ ConstantsDataBase.SCHEMA +"MWT_USERLOGGER")
			   .append(" (OBJ_ID, OBJ_DISPIDX, USERID, USERNAME, userIP, CZLX, CZTIME, RESULT,cznr,CZHOUR,EVENTTYPE,DEPTNAME,TRANSACTIONTYPE)")
			   .append(" values('").append(objId).append("',0,'").append(userId)
			   .append("','").append(userName).append("','").append(userIp).append("','")
			   .append(czlx).append("',").append("to_date('").append(currentTime).append("','yyyy-mm-dd HH24:mi:ss'),'").append(result.toString())
			   .append("','").append(content).append("',to_char(sysdate,'hh24'),'").append(eventType).append("','上海电力','")
			   .append(transactionType).append("')");
			System.out.println("执行插入语句"+sql);
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
	
	public String getLoggerAlarm(){
		int queryResult = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select OBJ_ID ").append("  from "+ ConstantsDataBase.SCHEMA +"mwt_userlogger");
			queryResult = getCount(sql.toString());
			if (queryResult >= Constants.LOGMAXSIZE*Constants.WARNLIMIT) {			//如果超过了预警限制 ，最大值的80%
				if (queryResult >= Constants.LOGMAXSIZE*Constants.ALARMLIMIT) {			//如果超过了告警限制 ，最大值的95%
					return Constants.ALARM;				//告警
				}else{
					return Constants.WARM;				//预警
				}
			}else{
				return  Constants.NORMAL;				//正常
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  Constants.NORMAL;
	}
	
	/**
	 * 执行审计日志备份的方法
	 * 1、将日志表插入到备份表中
	 * 2、将日志表数据清空
	 * 3、将备份操作记录并插入到审计表中
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Boolean backup() {
		int backResult = 0;
		int deleteResult = 0;
		//将审计日志插入到日志备份表中  日志备份表CMST_USERLOGGERBACK
		try{
			StringBuffer backSql = new StringBuffer();
			backSql.append(" INSERT INTO  ").append(ConstantsDataBase.SCHEMA).append("CMST_USERLOGGERBACK")
			.append(" ( SELECT * FROM ").append(ConstantsDataBase.SCHEMA).append("MWT_USERLOGGER )");
			System.out.println("backSQL"+backSql);
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
	
	@SuppressWarnings("unchecked")
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
	
	
	public void updataUserlog(String czlx,String transactionType,String results) {
		try {
			ClientContext clientContext = ClientContextHolder.getInstance()
			        .getClientContext();
			Map<String, String> user = UserInfoUtil.getLoginUser();	//用户信息
			user.put("userId", user.get("userID"));
			user.put("userName", user.get("userName"));
			user.put("userIp", clientContext.getRequestIP());
			user.put("czlx", czlx);
			user.put("transactionType", transactionType);
			user.put("result", results);
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			list.add(user);
			saveLogger(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
