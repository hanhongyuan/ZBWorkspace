package com.sgcc.pms.zbztjc.kqxt.jcxxcx.xldzgl.bizc;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

import javax.annotation.Resource;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class XldzglBizc implements IXldzglBizc{


	private final Log logger = LogFactory.getLog(XldzglBizc.class);
	@Resource
	private IDataDictionaryBizC dataDictionaryBizC;
	@Resource
	private IHibernateDao hibernateDao_ztjc;


	
	 public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}

	
	/**
	    * 获取输电监测查询列表
	    * @param bgsqdid
	    * @return
	    */
	   public QueryResultObject loadinfo(RequestCondition params) {
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "OBJ_ID,XLDZMC,LX,SSXT,WSMC,DYDJ";
		   String sdFilter = "";
		   String bdFilter = "";
		   if(null!=params.getFilter())
		   { 
			   sdFilter = getsdFilter(params);
			   bdFilter = getbdFilter(params);
		   }
		   String querySql = "";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			   String querySqlxl = 
					   "SELECT xl.obj_id obj_id,xlmc xldzmc,'xl' LX,(SELECT xtmc FROM mw_app.cmst_kq_xt xt where xt.obj_id = sskqxt) ssxt,zb.wsmc,(SELECT dydjmc FROM mw_app.CMST_SB_PZ_SBDYDJ p where p.dydjbm=xl.dydj) dydj FROM mw_app.cmst_sb_zwyc_xl xl, mw_app.cmst_zb_comm_wspz zb\n" +
							   "where xl.ssws = zb.wsid \n";
			   String querySqldz = 
							   "SELECT dz.obj_id obj_id,bdzmc xldzmc,'dz' LX,(SELECT xtmc FROM mw_app.cmst_kq_xt xt where xt.obj_id = ssxt) ssxt,zb.wsmc,(SELECT dydjmc FROM mw_app.CMST_SB_PZ_SBDYDJ p where p.dydjbm=dz.dydj) dydj FROM mw_app.cmst_sb_znyc_dz dz, mw_app.cmst_zb_comm_wspz zb\n" + 
							   "where dz.ssws = zb.wsid  and dzlx in ('zf01','zf02')";
			   
			   if(""!=sdFilter)
			   {
				   querySqlxl += sdFilter;
				   querySqldz += bdFilter;
			   }

			   querySql = querySqlxl+" union all\n "+querySqldz;
			   
			   result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());

			   
			   
		   } catch (Exception e) {
			   logger.info("输电装置查询管理，加载设备列表出错", e);
			   e.printStackTrace();
		   }
		   return qro;
	   }
	   /**
	    * 获取xtmc询列表
	    * @param bgsqdid
	    * @return
	    */
	   public QueryResultObject loadxt(RequestCondition params) {
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "OBJ_ID,XTJC,XTMC";
		   String sdFilter = "";
		   String bdFilter = "";
		   if(null!=params.getFilter())
		   { 
			   sdFilter = getsdFilter(params);
			   bdFilter = getbdFilter(params);
		   }
		   String querySql = "SELECT obj_id,xtjc,xtmc FROM mw_app.cmst_kq_xt";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			  
			   
			   result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());

			   
			   
		   } catch (Exception e) {
			   logger.info("xtmccx", e);
			   e.printStackTrace();
		   }
		   return qro;
	   }
	   
	   
	   public void unioninfo(String xlid,String xtid) {
		   
		   String id[] = xlid.split("&");
		   int length = id.length;
		   String ids = "";
		   for (int i=0;i<length;i++)
			   ids = ids+"'"+id[i]+"',";
		   	   ids=ids.substring(0, ids.length()-1);
			String xlsql = "update mw_app.CMST_SB_ZWYC_XL set sskqxt = ? where obj_id in ("+ids+")";
			String dzsql = "update mw_app.CMST_SB_ZNYC_DZ set ssxt = ? where obj_id in ("+ids+")";
		   try
		   {
			   hibernateDao_ztjc.executeSqlUpdate(xlsql, new Object[] { xtid });
			   hibernateDao_ztjc.executeSqlUpdate(dzsql, new Object[] { xtid });
			   
		   }catch (Exception e) {
			   logger.info("关联失败", e);
			   e.printStackTrace();
		   }
			
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
	   
	 //获取输电查询条件
	   private String getsdFilter(RequestCondition params)
	   {
		   String filter = "";
		   String[] qc = params.getFilter().toString().split("&");
		   for(int i = 0; i < qc.length; i++) {
			   if("xldzmc".equals(qc[i].split("=")[0])) {
				 //线路名称条件
				   filter += " AND xlmc like '%" + qc[i].split("=")[1] + "%'";
			   }
			   if("sfkq".equals(qc[i].split("=")[0])) {
					 //线路名称条件
					   filter += " AND sfkq ='" + qc[i].split("=")[1] + "'";
				   } 
		   }
		   return filter;
	   }
	   //获取输电查询条件
	   private String getbdFilter(RequestCondition params)
	   {
		   String filter = "";
		   String[] qc = params.getFilter().toString().split("&");
		   for(int i = 0; i < qc.length; i++) {
			   if("xldzmc".equals(qc[i].split("=")[0])) {
				 //线路名称条件
				   filter += " AND bdzmc like '%" + qc[i].split("=")[1] + "%'";
			   }
			   if("sfkq".equals(qc[i].split("=")[0])) {
					 //线路名称条件
					   filter += " AND sfkq ='" + qc[i].split("=")[1] + "'";
				   } 
		   }
		   return filter;
	   }
	
	    
}

