package com.sgcc.pms.zbztjc.spxxgl.bdvideo.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.pms.framework.base.util.UserInfoUtil;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.util.SqlFileUtil;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
/**
 * 用户定义逻辑构件
 * 
 * @author SWD 
 * 
 */
public class BdvideoBizc implements IBdvideoBizc{

	@Resource
	private IHibernateDao hibernateDao_ztjc;
	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}
	
	private final Log logger = LogFactory.getLog(BdvideoBizc.class);
	
	public String getUserString ()
	 {
		 Map<String, String> uesr = UserInfoUtil.getLoginUser();
		 String useridString = uesr.get("userID");
		 //String usernameString = uesr.get("userName");
		 return useridString;
	 }
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param params
	 * @return
	 */
	public QueryResultObject query(RequestCondition params) {

		   QueryResultObject qro = new QueryResultObject();
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			   cols = params.getColumns().toString();
			   String querySql =  
					   "select cast(tv.obj_id as varchar2(42)),\n" +
							   "       t.WSDEPMC ssdw,\n" + 
							   "       t.LINKEDstationname,\n" + 
							   "       t.DEVICENAME,\n" + 
							   "       t.MANUFACTURER,\n" + 
							   "       decode(tv.videostate,\n" + 
							   "              'online',\n" + 
							   "              '在线',\n" + 
							   "              'offline',\n" + 
							   "              '离线',\n" + 
							   "              'invalid',\n" + 
							   "              '无效') videostate,\n" + 
							   "       cast('查看' as varchar2(42)) JCXX,\n" + 
							   "       to_char(T.RUNDATE, 'yyyy-MM-dd HH24:mm:ss') rundate,\n" + 
							   "       tv.videocode,\n" + 
							   "       tv.sftypt,\n" + 
							   "       tv.devicecode\n" + 
							   "  from mw_app.cmsv_transfdevice_xtf t\n" + 
							   " right join mw_app.cmst_videostatusinfo_xt tv\n" + 
							   "    on t.DEVICECODE = tv.devicecode\n" + 
							   " where tv.type = 'bd'\n" + 
							   "   and t.WSDEPMC is not null";


			   
			  
			   if(null!=params.getFilter())
			   {
				   String[] qc = params.getFilter().toString().split("&");
				   for(int i = 0; i < qc.length; i++) {
					   if("ssdw".equals(qc[i].split("=")[0])) {
						   String[] dept = qc[i].split("=")[1].split(",");
							  String deptString = "";
							   for (int j = 0; j < dept.length; j++) {
								   deptString = deptString+"'"+dept[j]+"'";
								if (j!=dept.length-1) {
									deptString = deptString+",";
								}
							}
							   querySql += " AND t.LINKEDPROVICEDEPT in ("+deptString+")";
					   } else if("linkedstation".equals(qc[i].split("=")[0])) {
						   querySql += " AND linkedstationname like'%" + qc[i].split("=")[1] + "%'";
					   } else if("videostate".equals(qc[i].split("=")[0])) {
						   String state = qc[i].split("=")[1];
						   if(state.equals("all")){
							   
						   }else{
							   querySql += " AND videostate = '"+state+"' ";
						   }
						   
					   } else if("manufacturer".equals(qc[i].split("=")[0])) {
						   querySql += " AND manufacturer like'%" + qc[i].split("=")[1] + "%'";
					   } else if("srundate".equals(qc[i].split("=")[0])) {
						   querySql += " and to_char(rundate,'yyyy-mm-dd') >= '" + qc[i].split("=")[1] + "'";
					   } else if("erundate".equals(qc[i].split("=")[0])) {
						   querySql += " and to_char(rundate,'yyyy-mm-dd') <= '" + qc[i].split("=")[1] + "'";
					   }
				   }
			   }

			   querySql += " order by ssdw,linkedstationname ";
			   result = hibernateDao_ztjc.executeSqlQuery(querySql, pageIndex, pageSize);
			   logger.info(querySql);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
			   qro.addDicItems(wrapDictList());
			   return qro;
		   } catch (Exception e) {
			   logger.info("加载设备列表出错", e);
			   e.printStackTrace();
		   }
		   return null;

		
		
	}
	
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
	   
	   
	   private List<DicItems> wrapDictList() {
			List<DicItems> dicts = new ArrayList<DicItems>();

			String deptsqlString = " SELECT WSMC,WSID FROM MW_APP.cmst_zb_comm_wspz order by ZDYPX ";
		     dicts.add(translateFromDBT("ssdw",deptsqlString));
			
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
//		          return retlist;
		          List<Map<String, String>> list = retlist;
		      

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
	
		    public  static String  validate(Object s){
				   return (String) s;
			   }


}
