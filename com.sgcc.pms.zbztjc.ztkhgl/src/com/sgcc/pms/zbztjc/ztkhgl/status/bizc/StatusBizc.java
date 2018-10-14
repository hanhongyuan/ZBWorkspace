package com.sgcc.pms.zbztjc.ztkhgl.status.bizc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.util.loggerSave.bizc.ILoggerSaveBizc;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;



/**
 * 用户定义逻辑构件
 * 
 * @author Administrator 
 * 
 */
public class StatusBizc implements IStatusBizc{


	private final Log logger = LogFactory.getLog(StatusBizc.class);
	@Resource
	private IDataDictionaryBizC dataDictionaryBizC;
	@Resource
	private IHibernateDao hibernateDao_ztjc;

	@Resource
	private ILoggerSaveBizc loggerSaveBizc ;
	
	 public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}


	/**
	    * 审核信息查询
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject loadstatusinfo(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			  // cols = params.getColumns().toString();
			   cols = "OBJ_ID,PROVINCE_NAME,LINKEDDEPTNAME,JCLX,DEVICENAME,DEVICECODE,CHANGECAUSE,APPLYTIME,APPLICANT,AUDITSTATUS,AUDITVIEW,AUDITTIME";
			   String querySql = "select CAST(C.OBJ_ID AS VARCHAR2(42)) OBJ_ID,c.province_name,c.linkeddeptname,jclx,c.devicename,c.devicecode,c.changecause,c.applytime,c.applicant, c.auditstatus,c.auditview,c.audittime from mw_app.cmst_changestatus c where 1=1 ";
	  
			   if(null!=params.getFilter())
			   {
				   String[] qc = params.getFilter().toString().split("&");
				   for(int i = 0; i < qc.length; i++) {
					   
					   if("auditStatus".equals(qc[i].split("=")[0])) {
						 //审核状态
						   String[] audi = qc[i].split("=")[1].split(",");
							  String audiString = "";
							   for (int j = 0; j < audi.length; j++) {
								   audiString = audiString+"'"+audi[j]+"'";
								if (j!=audi.length-1) {
									audiString = audiString+",";
								}
							}
							   querySql += " AND auditStatus in ("+audiString+")";
					   }  
					   else if("linkeddepws".equals(qc[i].split("=")[0])) {
						 //所属地市条件
						   String[] dept = qc[i].split("=")[1].split(",");
							  String deptString = "";
							   for (int j = 0; j < dept.length; j++) {
								   deptString = deptString+"'"+dept[j]+"'";
								if (j!=dept.length-1) {
									deptString = deptString+",";
								}
							}
							   querySql += " AND province_id in ("+deptString+")";
					   }  
					   else if("srundate".equals(qc[i].split("=")[0]))
					   {
						   querySql += " and to_char(applytime,'yyyy-mm-dd') >= '" + qc[i].split("=")[1] + "'";
					   } else if("erundate".equals(qc[i].split("=")[0]))
					   {
						   querySql += " and to_char(applytime,'yyyy-mm-dd') <= '" + qc[i].split("=")[1] + "'";
					   }
				   }
			   }
			   
			   else {
				   
				querySql = querySql;
			}
			   querySql += " order by province_id,auditStatus ";
			   result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
			   qro.addDicItems(wrapDictList());
			   loggerSaveBizc.updataUserlog("查询", "状态监测-装置管理-装置运行状态管理", "操作成功");
			   return qro;
		   } catch (Exception e) {
			   logger.info("考核状态列表管理，加载设备列表出错", e);
			   e.printStackTrace();
			   loggerSaveBizc.updataUserlog("查询", "状态监测-装置管理-装置运行状态管理", "操作失败");
		   }
		   return null;
	   }
	  
	   
	   /**
	    * 审核信息查询
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject loadinfo(String id) {
		   
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   String cols = "";
		   try {
			  
			  // cols = params.getColumns().toString();
			   cols = "OBJ_ID,PROVINCE_NAME,LINKEDDEPTNAME,JCLX,DEVICENAME,DEVICECODE,FJ,LINKEDLINENAME,LINKEDPOLENAME,RUNDATE,APPLICANT,OLDSTATUS,NEWSTATUE,AUDITSTATUS,APPLYTIME,CHANGECAUSE,AUDITVIEW";
			   String querySql = 
					   "select CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID,PROVINCE_NAME,LINKEDDEPTNAME,JCLX,DEVICENAME,DEVICECODE,FJ,\n" +
							   "       LINKEDLINENAME,LINKEDPOLENAME,RUNDATE,APPLICANT,\n" + 
							   "       OLDSTATUS,NEWSTATUE,decode(auditstatus,0,1,1,1,2,2) AUDITSTATUS,APPLYTIME,CHANGECAUSE,AUDITVIEW\n" + 
							   "        from mw_app.cmst_changestatus where obj_id = '"+id+"'";

			   
			   result = hibernateDao_ztjc.executeSqlQuery(querySql);
			   result = transToColumns(result,cols);
			   
			   qro.setItems(result);
			   qro.addDicItems(wrapDictList());
			   return qro;
			   
		   } catch (Exception e) {
			   logger.info("审核详情管理，加载设备列表出错", e);
			   e.printStackTrace();
		   }
		   return null;
	   }
	   
	   /**
	    * 审核信息查询
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject saveadui(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		   String[] filter = ((String) params.getFilter()).split(",");
		  String AUDITSTATUS ="";
		  String AUDITVIEW = "";
		  String querySql ="";
		  String obj_id = "";
		   int result = 0;
		   try {
		
				   AUDITSTATUS = filter[0];
				   AUDITVIEW = filter[1];
				   obj_id = filter[2];
				   
				   querySql = "update mw_app.cmst_changestatus set AUDITSTATUS = '"+AUDITSTATUS+"' , AUDITVIEW = '"+AUDITVIEW+"',AUDITTIME = sysdate  where obj_id='"+obj_id+"'";
				 
				   result = hibernateDao_ztjc.executeSqlUpdate(querySql);
				   
				   qro.setItemCount(result);
				   return qro;
		   } catch (Exception e) {
			   logger.info("保存审核信息出错", e);
			   e.printStackTrace();
		   }
		   return null;
	   }
	   /**
	    * 处理结果，将List中的index 转换成对应的列名
	    * 
	    * @param list
	    * @param columns
	    * @return
	    */
	   @SuppressWarnings({ "unchecked", "rawtypes" })
	   private List<Map> transToColumns(List<Object[]> list, String columns)
	   {
	       List<Map> items = new ArrayList();

	       String[] cols = columns.split("\\,");

	       for (int i = 0; i < list.size(); i++)
	       {
	           Map map = new HashMap();
	           for (int m = 0; m < cols.length; m++)
	           {
	        	   map.put(cols[m], list.get(i)[m]);
	           }
	           items.add(map);
	       }

	       return items;
	   }
	   

 //查询页面下拉框
	   private List<DicItems> wrapDictList() {
			List<DicItems> dicts = new ArrayList<DicItems>();
			String deptsqlString = "SELECT wsmc,wsid FROM MW_APP.CMST_ZB_COMM_WSPZ order by zdypx";
		    dicts.add(translateFromDBT("linkeddepws",deptsqlString));
		    
		    dicts.add(translateFromFile("OLDSTATUS", "OLDSTATUS"));
		    dicts.add(translateFromFile("NEWSTATUE", "NEWSTATUE"));
		    dicts.add(translateFromFile("AUDITSTATUS", "AUDITSTATUS"));  
	        return dicts;
		}
		

	/**
	 * 通过自定义sql获取数据字典
	 * */
		private DicItems translateFromDBT(String fieldName,String sql) {
	        List retlist = new LinkedList();
	          List lt = hibernateDao_ztjc.executeSqlQuery(sql);
	          Iterator iterator = lt.iterator();
	          while (iterator.hasNext()) {
	            Object[] keyvalue = (Object[])iterator.next();
	            if (keyvalue.length == 2) {
	              Map map = keyvalueToMap( "text","value",String.valueOf(keyvalue[0]), String.valueOf(keyvalue[1]));
	              retlist.add(map);
	            }
	          }
	          List<Map<String, String>> list = retlist;
	      DicItems dict = new DicItems();
	      dict.setName(fieldName);
	      dict.setValues(list);
	      return dict;
	    }
	    
		// 从属性文件中查询字典
		private DicItems translateFromFile(String fieldName, String dicId) {
			//System.out.println("22");
			List<Map<String, String>> list = dataDictionaryBizC.translateFromFile(
					dicId, "value", "text");
			//System.out.println("11");
			DicItems dict = new DicItems();
			dict.setName(fieldName);
			dict.setValues(list);
			return dict;

	}
		
	    /**
	     *将查询结果封装成DropDownEditor需要格式的map
	     * */
	    private Map<String, String> keyvalueToMap(String key,String value,String realkey, String realvalue)
			  {
			    Map map = new HashMap();
			    map.put(key, realkey);
			    map.put(value, realvalue);
			    return map;
			  }
	   
}

