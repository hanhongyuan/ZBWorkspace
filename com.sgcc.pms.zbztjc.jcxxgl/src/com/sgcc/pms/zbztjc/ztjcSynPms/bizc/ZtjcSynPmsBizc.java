package com.sgcc.pms.zbztjc.ztjcSynPms.bizc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgcc.pms.zbztjc.Constants;
import com.sgcc.uap.persistence.IHibernateDao;

/**
 * @author gtd
 *
 */
@Service
public class ZtjcSynPmsBizc implements IZtjcSynPmsBizc{
	private static final Log log = LogFactory.getLog(ZtjcSynPmsBizc.class);
	
	/**
	 * 状态监测数据实例
	 */
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	/**
	 * 总部PMS数据实例
	 */
	@Resource
	private IHibernateDao hibernateDao;
	
	public static String synType = null;				//同步类型，全量同步或增量同步
	
	
	@SuppressWarnings("rawtypes")
	public synchronized List synService(String type) {
		
		try {
			synType = type;
			List<Map<String,String>> resultMapList = new ArrayList<Map<String,String>>();
			Map<String,String> resultMap = new HashMap<String,String>();
			java.util.Date currentDate = new java.util.Date();
			//查询要同步的表名
			List<Map<String,String>> tableNameList = getTableName();
			String ztjcTableName= "";				//状态监测表名
			String pmsTableName = "";				//PMS表名
			String columnsName = "";				//要同步的列名
			String increamentColumns = null;		//增量抽取的依据字段
			String lastSynTime = "";				//最后同步时间
			//对表名进行循环，然后进行同步数据
			//for(Map<String,String> tableNameMap:tableNameList){
			for(int i=0;i<tableNameList.size();i++){
				ztjcTableName = tableNameList.get(i).get("ZTJCTABLENAME");			//获取状态监测表名
				pmsTableName = tableNameList.get(i).get("PMSTABLENAME");			//获取PMS表名
				columnsName = tableNameList.get(i).get("COLUMNSNAME");				//获取列名
				increamentColumns = tableNameList.get(i).get("INCREMENTCOLUMNS");	//获取增量同步的依据字段
				lastSynTime = tableNameList.get(i).get("LASTSYNTIME");				//获取最后同步时间字段
				int cycleNum = getCycleNum(pmsTableName,increamentColumns,lastSynTime);					//获取循环次数
				//进入数据的方法
				int dataNum = insertData( pmsTableName, ztjcTableName, columnsName,cycleNum,increamentColumns,lastSynTime);
				boolean updateResult = updateDataRecord(dataNum,ztjcTableName);			//更新数据记录的方法，更新表的最后修改时间
				if(!updateResult){													//如果更新失败
					resultMap.put("message", "同步失败！");
					resultMapList.add(resultMap);
					break;
				}
			}
			java.util.Date newcurrentDate = new java.util.Date();
			System.out.println("执行的开始时间！"+currentDate);
			System.out.println("执行的结束时间！"+newcurrentDate);
			if(0 == resultMapList.size()){
				resultMap.put("message", "同步成功！");
				resultMapList.add(resultMap);
			}
			
			return resultMapList;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * 获取状态监测和PMS表名的方法
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getTableName(){
		List<Map<String,String>> tableNameList = null;
		String queryTableNameSql = "SELECT ZTJCTABLENAME,PMSTABLENAME,COLUMNSNAME,INCREMENTCOLUMNS,TO_CHAR(LASTSYNTIME,'YYYY-MM-DD HH24:mI:ss') LASTSYNTIME FROM MW_APP.CMST_ZTJCSYNPMS";
		try{
			tableNameList = hibernateDao_ztjc.queryForListWithSql(queryTableNameSql);	
			return tableNameList;
		}catch (Exception e) {
			log.info("获取状态监测和PMS表名出错！");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 更新
	 * @param dataNum
	 * @param ztjcTableName
	 * @return
	 */
	public boolean updateDataRecord(int dataNum,String ztjcTableName){
		
		try {
			StringBuffer updateSql = new StringBuffer();
			String currentTime = getCurrentTime();
			updateSql.append("UPDATE MW_APP.CMST_ZTJCSYNPMS SET LASTSYNTIME = ")
			.append("TO_DATE('").append(currentTime).append("', 'YYYY-MM-DD hh24:mi:ss') ,")
			.append(" SYNDATANUM = ").append(dataNum).append(" WHERE ZTJCTABLENAME = '")
			.append(ztjcTableName).append("'");
			log.info("执行更新的SQL为:"+updateSql.toString());
			int result = hibernateDao_ztjc.updateWithSql(updateSql.toString());
			if(0<result){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	/**
	 * 获取循环次数
	 * @param pmsTableName
	 * @return
	 */
	public int getCycleNum(String pmsTableName,String increamentColumns,String lastSynTime){
		
		try {
			int dataSize = 0;
			StringBuffer queryDataSizeSql = new StringBuffer();
			if(Constants.INCREAMENT.equalsIgnoreCase(synType) && null != increamentColumns && "" != increamentColumns.trim()){			//如果是增量抽取
				if(pmsTableName.equalsIgnoreCase("T_ZB_COMM_WSPZ")){					//地市表多加了一个字段WSJC
					queryDataSizeSql.append(" SELECT COUNT(1) FROM SCYW.").append(pmsTableName);
					queryDataSizeSql.append(" WHERE ").append(" TO_CHAR(").append(increamentColumns).append(",'YYYY-MM-DD HH24:MI:SS') >'").
					append(lastSynTime).append("'");
				}else if(pmsTableName.equalsIgnoreCase("ISC_SPECIALORG_UNIT_LOCEXT")){
					queryDataSizeSql.append(" SELECT COUNT(1) FROM SCYW.").append(pmsTableName).append(" WHERE DWJB IN('3','4') ");
					queryDataSizeSql.append(" AND ").append(" TO_CHAR(").append(increamentColumns).append(",'YYYY-MM-DD HH24:MI:SS') >'").
					append(lastSynTime).append("'");
				}else{
					queryDataSizeSql.append(" SELECT COUNT(1) FROM SCYW.").append(pmsTableName);
					queryDataSizeSql.append(" WHERE ").append(" TO_CHAR(").append(increamentColumns).append(",'YYYY-MM-DD HH24:MI:SS') >'").
					append(lastSynTime).append("'");
				}
			}else{
				queryDataSizeSql.append("SELECT COUNT(1) FROM SCYW.").append(pmsTableName);
			}
			
			dataSize = Integer.parseInt(hibernateDao.executeSqlQuery(queryDataSizeSql.toString()).get(0).toString());
			int cycleNum = dataSize/(Constants.ONETIMESYNNUMBER);					//取两者相除的商值
			int remainder = dataSize%(Constants.ONETIMESYNNUMBER);					//取两者相除的阈值
			if(0 < remainder){														//如果余数大于1，则向上取整
				cycleNum+=1;
			}
			return cycleNum;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	/**
	 * 
	 * @param pmsTableName
	 * @param ztjcTableName
	 * @param columnsName
	 * @param cycleNum
	 * @param type
	 * @param increamentColumns
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public int insertData(String pmsTableName,String ztjcTableName,String columnsName,int cycleNum,String increamentColumns,String lastSynTime){
			List<String> insertSqlList = new ArrayList<String>();
			List pmsDataList = null;												//PMS数据值列表
			int dataNum = 0;														//插入的数据量
			//在插入之前先执行删除语句
			try{
				boolean deleteResult = deleteZtjcData(ztjcTableName,increamentColumns,lastSynTime);					
				if(deleteResult){														//如果删除成功
					for(int i=0;i<cycleNum;i++){										//循环取数据
						System.out.println(ztjcTableName+   "当前循环："+i);
						pmsDataList = getPmsData(i,pmsTableName,columnsName,increamentColumns,lastSynTime);		//根据表名获取数据值
						insertSqlList = getInsertSqlList(pmsDataList,ztjcTableName,columnsName);	//获取插入sql列表
						System.out.println("当前执行插入SQL的条数为:"+insertSqlList.size());
						int[] insertDataNum = hibernateDao_ztjc.batchUpdateWithSql((String[])insertSqlList.toArray(new String[insertSqlList.size()]));
						for(int j=0;j<insertDataNum.length;j++){
							dataNum += insertDataNum[j];
						}
					}
				}
				return dataNum;
			}catch(Exception e){
				e.printStackTrace();
			}
			return 0;
	}
	/**
	 * 获取PMS的数据
	 * @param cycleNum
	 * @param pmsTableName
	 * @param columnsName
	 * @return PMS数据列
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> getPmsData(int cycleNum,String pmsTableName,String columnsName,String increamentColumns,String lastSynTime){
		List pmsDataList = null;
		StringBuffer queryDataSql = new StringBuffer();
		//拼接查询语句
		if(Constants.INCREAMENT.equalsIgnoreCase(synType) && null != increamentColumns && "" != increamentColumns.trim()){			//如果是增量抽取
			if(pmsTableName.equalsIgnoreCase("T_ZB_COMM_WSPZ")){					//地市表多加了一个字段WSJC
				columnsName = columnsName.substring(0, columnsName.length()-5);								//去掉字段WSJC
				queryDataSql.append(" SELECT ").append(columnsName).append(" FROM SCYW.").append(pmsTableName);
				queryDataSql.append(" WHERE ").append(" TO_CHAR(").append(increamentColumns).append(",'YYYY-MM-DD HH24:MI:SS') >'").
				append(lastSynTime).append("'");
			}else if(pmsTableName.equalsIgnoreCase("ISC_SPECIALORG_UNIT_LOCEXT")){
				queryDataSql.append(" SELECT ").append(columnsName).append(" FROM SCYW.").append(pmsTableName).append(" WHERE DWJB IN('3','4') ");
				queryDataSql.append(" AND ").append(" TO_CHAR(").append(increamentColumns).append(",'YYYY-MM-DD HH24:MI:SS') >'").
				append(lastSynTime).append("'");
			}else{
				queryDataSql.append(" SELECT ").append(columnsName).append(" FROM SCYW.").append(pmsTableName);
				queryDataSql.append(" WHERE ").append(" TO_CHAR(").append(increamentColumns).append(",'YYYY-MM-DD HH24:MI:SS') >'").
				append(lastSynTime).append("'");
			}
		}else{
			if(pmsTableName.equalsIgnoreCase("T_ZB_COMM_WSPZ")){					//地市表多加了一个字段WSJC
				columnsName = columnsName.substring(0, columnsName.length()-5);								//去掉字段WSJC
				queryDataSql.append(" SELECT ").append(columnsName).append(" FROM SCYW.").append(pmsTableName);
			}else if(pmsTableName.equalsIgnoreCase("ISC_SPECIALORG_UNIT_LOCEXT")){
				queryDataSql.append(" SELECT ").append(columnsName).append(" FROM SCYW.").append(pmsTableName).append(" WHERE DWJB IN('3','4') ");
			}else{
				queryDataSql.append(" SELECT ").append(columnsName).append(" FROM SCYW.").append(pmsTableName);
			}
		}
		log.info("查询PMS数据的sql为"+queryDataSql.toString());
		try{
			pmsDataList = hibernateDao.executeSqlQuery(queryDataSql.toString(),cycleNum+1,Constants.ONETIMESYNNUMBER);
			return pmsDataList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取插入语句列
	 * @param pmsDataList
	 * @param ztjcTableName
	 * @param columnsName
	 * @return	插入语句列
	 */
	public List<String> getInsertSqlList(List<Object[]> pmsDataList,String ztjcTableName,String columnsName){
		log.info("根据PMS数据拼接SQL列表");
		List<String> insertSqlList = new ArrayList<String>();
		StringBuffer inserSql = null;
		String[] columnsNameArr = columnsName.split("\\,");				//对列名进行分割，成字符串数组
		int timeLocation = 0;											//记录创建时间或登记时间字段的位置
		for(int m=0;m<columnsNameArr.length;m++){						//获取创建时间或登记时间的位置
			if(Constants.CJSJ.equalsIgnoreCase(columnsNameArr[m]) || Constants.DJSJ.equalsIgnoreCase(columnsNameArr[m])){
				timeLocation = m;
			}
		}
		int pmsDataSize = pmsDataList.size();
		for(int i=0;i<pmsDataSize;i++){
			//拼接插入SQL
			inserSql = new StringBuffer(" INSERT INTO MW_APP.");
			inserSql.append(ztjcTableName).append("(").append(columnsName).append(")").append(" VALUES(");
			if(pmsDataList.get(i).length > columnsName.split("\\,").length){		//如果值的数量大于字段的数量，说明增加了rownum字段
				for(int j=0;j<(columnsName.split("\\,")).length;j++){
					if(null == pmsDataList.get(i)[j]){								//如果说字段内容为空，则插入空字符
						inserSql.append("''").append(",");
					}else{
						if(0 != timeLocation && timeLocation == j){										//如果要同步的字段中有时间类型的，则对数值进行转换
							inserSql.append(" TO_DATE('").append(pmsDataList.get(i)[j].toString()).append("', 'YYYY-MM-DD:HH24:MI:SS')").append(",");
						}else{
							inserSql.append("'").append(pmsDataList.get(i)[j].toString()).append("',");
						}
					}
				}
			}else{																	//否则证明数据量不足以分页
				for(int j=0;j<pmsDataList.get(i).length;j++){
					if(null == pmsDataList.get(i)[j]){						//如果说字段内容为空，则插入空字符
						inserSql.append("''").append(",");
					}else{
						if(0 != timeLocation && timeLocation == j){
							inserSql.append(" TO_DATE('").append(pmsDataList.get(i)[j].toString()).append("', 'YYYY-MM-DD:HH24:MI:SS')").append(",");
						}else{
							inserSql.append("'").append(pmsDataList.get(i)[j].toString()).append("',");
						}
					}
				}
			}
			if(ztjcTableName.equalsIgnoreCase("CMST_ZB_COMM_WSPZ")){			//地市表要多加了一个字段WSJC
				for(int k=0;k<columnsNameArr.length;k++){						//取得WSJC所在数组位置
					if ("WSMC".equalsIgnoreCase(columnsNameArr[k])) {
						String wsmc = pmsDataList.get(i)[k].toString();					//取得网省名称
						//删除多余字符，格式化
						String wsjc = wsmc.replaceAll("国家", "").replaceAll("国网", "").replaceAll("公司", "").replaceAll("电力", "").replaceAll("黑", "").replaceAll("（管理库测试环境）", "");
						inserSql.append("'").append(wsjc).append("'");
					}
				}
				insertSqlList.add(inserSql+")");
			}else{
				insertSqlList.add(inserSql.substring(0, inserSql.length()-1)+")");
			}
		}
		return insertSqlList;
	}
	/**
	 * 获取当前时间的方法
	 * @return
	 */
	public String getCurrentTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//定义时间格式为 2016-10-24
		java.util.Date currentDate = new java.util.Date();
		String currentYearDate = df.format(currentDate);
		return currentYearDate;
	}
	/**
	 * 从状态监测库中删除数据的方法
	 * @param ztjcTableName
	 * @return
	 */
	public boolean deleteZtjcData(String ztjcTableName,String increamentColumns,String lastSynTime){
		
		try {
			String deleteSql = "DELETE FROM MW_APP."+ztjcTableName;
			//如果是增量抽取，则删除最后一次抽取时间以后的数据
			if(Constants.INCREAMENT.equalsIgnoreCase(synType) && null != increamentColumns && "" != increamentColumns.trim()){
				deleteSql = deleteSql + " WHERE TO_CHAR("+increamentColumns +",'YYYY-MM-DD HH24:Mi:SS') > '" +lastSynTime+"'";
			}
			int result =  hibernateDao_ztjc.executeSqlUpdate(deleteSql);
			if(0<=result){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
}
	