package com.sgcc.pms.zbztjc.jczhgl.bdzztjfx.bizc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
/**
 * 用户定义逻辑构件
 * 
 * @author admin 
 * 
 */
public class BdzztjfxBizc implements IBdzztjfxBizc{

	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}

	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject queryBydydj(RequestCondition queryCondition) {
		
		try {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			StringBuffer querySql = new StringBuffer();
			String sql = "";
			String p_obj = "";
			if(null!=queryCondition.getFilter()){
				 String[] filter = queryCondition.getFilter().toString().split(",");
				 for (int i = 0; i < filter.length; i++) {
					String filterKey = filter[i].split("=")[0].trim();
					String filterValue = filter[i].split("=")[1].trim();
					if ("ssdw".equals(filterKey)) {
						 if("63EBEC8E-E766-40D7-ACF4-FEA945102112-00042".equals(filterValue)){
							 querySql.append(" "); 
						 }else{
							 QueryResultObject ssdwid = idByssdw(filterValue);
							 List list = ssdwid.getItems();
							 if (!list.isEmpty()) {
								 //for (int j = 0; j < list.size(); j++) {
									 p_obj = list.get(0).toString();
								//}
							}
							 querySql.append(" and linkedprovicedept = '");
							 querySql.append(p_obj);
							 querySql.append("' ");
						 }
					}
				 }
			 }
				sql = " SELECT COUNT(*) CNT, LINKEDEQUIPMENTDY, DEVICEVOLTAGE mc \n"+
						"   FROM MW_APP.CMSV_TRANSFDEVICE_XTF \n"+
						"  WHERE 1 = 1 \n"+
						"    AND WSDEPMC IS NOT NULL \n"+
						"   AND DEVICEVOLTAGE IS NOT NULL "+querySql.toString()+"\n"+
						"  GROUP BY DEVICEVOLTAGE, DYDJGDJB, LINKEDEQUIPMENTDY \n"+
						"  ORDER BY DYDJGDJB ";
			result = hibernateDao_ztjc.executeSqlQuery(sql.toString());
			qro.setItems(result);
			return qro;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject queryByjclx(RequestCondition queryCondition) {
		
		try {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			String sql = "";
			String p_obj = "";
			StringBuffer querySql = new StringBuffer();
			if(null!=queryCondition.getFilter()){
				 String[] filter = queryCondition.getFilter().toString().split(",");
				 for (int i = 0; i < filter.length; i++) {
					String filterKey = filter[i].split("=")[0].trim();
					String filterValue = filter[i].split("=")[1].trim();
					if ("ssdw".equals(filterKey)) {
						 if("63EBEC8E-E766-40D7-ACF4-FEA945102112-00042".equals(filterValue)){
							 querySql.append(" "); 
						 }else{
							 QueryResultObject aaa = idByssdw(filterValue);
							 List list = aaa.getItems();
							 if (!list.isEmpty()) {
								 //for (int j = 0; j < list.size(); j++) {
									 p_obj = list.get(0).toString();
								//}
							}
							 querySql.append(" and linkedprovicedept = '");
							 querySql.append(p_obj);
							 querySql.append("' ");
						 }
					}
				 }
			 }
		
				sql = " SELECT COUNT(*) CNT, MONITORINGTYPEs, \n"+
						" (SELECT TYPENAMEJC FROM MW_APP.CMST_MONITORINGTYPE  \n"+
						"   WHERE TYPECODE = MONITORINGTYPEs) MC  \n"+
						"  FROM (select *  from mw_app.cmst_transfdevice  \n"+
						" where LINKEDEQUIPMENTDY IS NOT NULL ) a,  MW_APP.CMST_ZB_COMM_WSPZ deps  \n"+
						"  WHERE 1 = 1 and deps.wsid = a.linkedprovicedept(+) " +querySql.toString()+" \n"+
						"  GROUP BY MONITORINGTYPES  \n"+
						"  ORDER BY MONITORINGTYPES ";
			
			 
			result = hibernateDao_ztjc.executeSqlQuery(sql.toString());
			qro.setItems(result);
			return qro;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject queryBysccj(RequestCondition queryCondition) {
		
		try {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			StringBuffer querySql = new StringBuffer();
			String sql = "";
			String p_obj = "";
			if(null!=queryCondition.getFilter()){
				 String[] filter = queryCondition.getFilter().toString().split(",");
				 for (int i = 0; i < filter.length; i++) {
					String filterKey = filter[i].split("=")[0].trim();
					String filterValue = filter[i].split("=")[1].trim();
					if ("ssdw".equals(filterKey)) {
						 if("63EBEC8E-E766-40D7-ACF4-FEA945102112-00042".equals(filterValue)){
							 //querySql.append(" "); 
							 querySql.append(" and t.LINKEDDEPWS is not null ");
						 }else{
							 QueryResultObject ssdwid = idByssdw(filterValue);
							 List list = ssdwid.getItems();
							 if (!list.isEmpty()) {
								 //for (int j = 0; j < list.size(); j++) {
									 p_obj = list.get(0).toString();
								//}
							}
							 //querySql.append(p_obj);
							 querySql.append("and t.LINKEDDEPWS ='");
							 querySql.append(p_obj);
							 querySql.append("' ");
						 }
					}
				 }
			 }
				/*sql = " select * from (select count(obj_id) cnt, MANUFACTURER MC \n"+
	                  " from MW_APP.CMSV_TRANSFDEVICE_XT t \n"+
	                  " where t.DEVICEVOLTAGE IS NOT NULL \n"+
	                  " AND T.WSDEPMC IS NOT NULL AND t.MANUFACTURER IS NOT NULL \n"+
					  " and exists (select obj_id from (select obj_id \n"+
	                  " from mw_app.cmsmv_pmssys_pd_deps dep \n"+
	                   " where dep.dwjb IN ('3', '4') \n"+
	                       " connect by prior dep.obj_id = dep.sjbm \n"+
	                        " start with dep.obj_id ='"+querySql.toString()+"') d \n"+
	                  " where d.obj_id = t.linkeddep) \n"+
					  "  group by t.MANUFACTURER \n"+
					  " order by cnt desc) \n"+
					  " where rownum < 11 ";*/
				
				
				sql = "  select * from (select count(monitor.monitoringtype) cnt, MANUFACTURER MC \n"+ 
	                 "  from MW_APP.CMSV_TRANSFDEVICE_XTF t , mw_app.cmst_devicemonitype monitor  \n"+
	                 	" where t.devicecode = monitor.linkeddevice \n"+
	                  " and t.DEVICEVOLTAGE IS NOT NULL  \n"+
	                  " AND T.WSDEPMC IS NOT NULL AND t.MANUFACTURER IS NOT NULL "+querySql.toString()+" \n"+
	                  " group by t.MANUFACTURER  \n"+
	                  " order by cnt desc)  \n"+
	                  " where rownum < 11 ";
				
				
			result = hibernateDao_ztjc.executeSqlQuery(sql.toString());
			qro.setItems(result);
			return qro;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject queryByssdw(RequestCondition queryCondition) {
		
		try {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			String sql = " select count(distinct(decode(t.linkedequipmentdy, null, null, t.devicecode))) as CNT, \n "+
	        " de.wsmc mc  from mw_app.CMST_ZB_COMM_WSPZ de, \n "+
	        " (select *  from mw_app.cmst_transfdevice  where LINKEDEQUIPMENTDY IS NOT NULL) t,  \n "+
	        " (select monitor.linkeddevice, monitor.monitoringtype, info.SFSS  from mw_app.cmst_devicemonitype  monitor,  mw_app.cmsv_deviceused_info info \n "+ 
	        " where monitor.linkeddevice = info.ZZBM(+) and monitor.monitoringtype = info.JCLX(+)) ssjr  \n "+
			" where t.devicecode = ssjr.linkeddevice(+) and de.wsid = t.linkedprovicedept(+)  \n "+
			" group by de.wsmc, de.zdypx, de.wsid  \n "+
			" order by de.zdypx";
			result = hibernateDao_ztjc.executeSqlQuery(sql.toString());
			qro.setItems(result);
			return qro;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject idByssdw(String ssdwmc) {
		
		try {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			String sql = " select cast(wsid as varchar2(42)) obj_id from mw_app.CMST_ZB_COMM_WSPZ dep where wsmc = '"+ssdwmc+"' and rownum =1 ";
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			qro.setItems(result);
			return qro;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	
}
