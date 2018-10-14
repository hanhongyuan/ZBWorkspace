package com.sgcc.pms.zbztjc.jczhgl.sdzztjfx.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.sgcc.pms.zbztjc.jczhgl.gg.Constants;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
/**
 * 用户定义逻辑构件
 * 
 * @author admin 
 * 
 */
public class SdzztjfxBizc implements ISdzztjfxBizc{

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
			
			 sql = " SELECT COUNT(*) CNT,LINKEDEQUIPMENTDY ,DEVICEVOLTAGE mc " +
			 		" FROM MW_APP.CMSV_LINEDEVICE_XTf \n"+
			 		" WHERE 1 = 1 \n" +
			 		" AND WSDEPMC IS NOT NULL  AND DEVICEVOLTAGE IS NOT NULL "+querySql.toString()+"\n"+
			 		" GROUP BY DEVICEVOLTAGE, DYDJGDJB, LINKEDEQUIPMENTDY \n" +
			 		" ORDER BY DYDJGDJB";
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
			

		
			sql = "SELECT COUNT(*) CNT,monitor.monitoringtype,\n" +
			"       (SELECT TYPENAMEJC\n" + 
			"          FROM MW_APP.CMST_MONITORINGTYPE\n" + 
			"         WHERE TYPECODE = monitor.monitoringtype) mc\n" + 
			"  FROM MW_APP.CMSV_LINEDEVICE_XTf  xt,\n" + 
			"       mw_app.cmst_devicemonitype monitor,\n" + 
			"       MW_APP.cmst_zb_comm_wspz   deps\n" + 
			" WHERE 1 = 1\n" + 
			"   and WSDEPMC IS NOT NULL\n" + 
			"   AND DEVICEVOLTAGE IS NOT NULL\n" + 
			"   and xt.devicecode = monitor.linkeddevice(+)\n" + 
			"   and deps.wsid = xt.linkedprovicedept(+)\n" + 
			"   and instr(xt.MONITORINGTYPES, monitor.monitoringtype) != 0\n" + 
			"   and xt.LinkedEquipmentDY is not null " +querySql.toString()+"\n"+
			" GROUP BY monitor.monitoringtype\n" + 
			" ORDER BY monitor.monitoringtype";		
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
							 querySql.append(" and dev.linkeddepws is not null "); 
						 }else{
							 QueryResultObject ssdwid = idByssdw(filterValue);
							 List list = ssdwid.getItems();
							 if (!list.isEmpty()) {
								 //for (int j = 0; j < list.size(); j++) {
									 p_obj = list.get(0).toString();
								//}
							}
							 querySql.append(" and dev.linkeddepws ='");
							 querySql.append(p_obj);
							 querySql.append("' ");
						 }
					}
				 }
			 }
			
			sql ="select *\n" + 
					"  from (select count(monitor.monitoringtype) CNT,dev.manufacturer MC \n" + 
					"          from (select * \n" + 
					"                  from mw_app.cmsv_linedevice_xtf xt\n" + 
					"  where LINKEDEQUIPMENTDY IS NOT NULL and WSDEPMC NOT LIKE '%电网%' \n" + 
					"  AND WSDEPMC NOT LIKE '%分部%') dev, mw_app.cmst_devicemonitype monitor \n" + 
					"  where dev.devicecode = monitor.linkeddevice and dev.MANUFACTURER is not null "+querySql.toString()+"\n" +  
					"  group by dev.manufacturer\n" + 
					"   order by CNT desc)\n" + 
					" where rownum < 11";
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
			String sql = " select count(distinct(decode(t.linkedequipmentdy, null, null, t.devicecode))) as CNT, de.wsmc  \n"+
	            "  from mw_app.cmst_zb_comm_wspz de,  \n"+
	            "  (select *    from mw_app.cmst_linedevice  \n"+
	            " where LINKEDEQUIPMENTDY IS NOT NULL) t,  \n"+
	            " (select monitor.linkeddevice, monitor.monitoringtype, info.SFSS  \n"+
	            " from mw_app.cmst_devicemonitype  monitor, mw_app.cmsv_deviceused_info info  \n"+
	            " where monitor.linkeddevice = info.ZZBM(+) and monitor.monitoringtype = info.JCLX(+)) ssjr  \n"+
	            " where t.devicecode = ssjr.linkeddevice(+)  and de.wsid = t.linkedprovicedept(+)  \n"+
	            " and de.wsid != '45997CA6-2658-4F42-A809-95811C1E75A6-00004'  \n"+
	            " group by de.wsmc, de.zdypx  \n"+
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
			String sql = " select cast(wsid as varchar2(42)) obj_id from mw_app.cmst_zb_comm_wspz dep where wsmc ='"+ssdwmc+"' and rownum =1 ";
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			qro.setItems(result);
			return qro;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	
}
