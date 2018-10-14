package com.sgcc.pms.zbztjc.chart.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.bddevicequery.bizc.BddeviceBizc;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.RestUtils;

/**
 * 用户定义逻辑构件
 * 
 * @author Jarvis
 * 
 */
public class ChartBizc implements IChartBizc {

	private static final Log log = LogFactory.getLog(ChartBizc.class);
	private final Log logger = LogFactory.getLog(BddeviceBizc.class);
	@Resource
	private IHibernateDao hibernateDao_ztjc;

	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}

	/**
	 * 根据输入条件查询记录
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject queryDeviceGeneral(
			RequestCondition queryCondition) {
		
		try {
			String querySql = 
					"select (select decode(de.wsjc,'黑龙江','龙江','国网运行','运行',de.wsjc) PROVINCE_SIMPNAME from mw_APP.cmst_zb_comm_wspz de\n" +
					"            where de.wsid = l.dwbm ) sswsmc,l.dwbm SSWS,l.sdssjrzzs+l.bdssjrzzs+l.dlssjrzzs jrzzs,\n" + 
					"            l.sdzzs+l.bdzzs+l.dlzzs zzs,case when l.sdzzs+l.bdzzs+l.dlzzs=0 then '-' else\n" + 
					"            RTrim(to_char(round((l.sdssjrzzs+l.bdssjrzzs+l.dlssjrzzs)/(l.sdzzs+l.bdzzs+l.dlzzs)*100, 2) || '', 'fm990.999'),'.') end jrl\n" + 
					"            from (with wstj as (select d.province_id DWBM,d.province_name DWMC,d.SDZZS,d.XLS,d.SDSSJRZZS,d2.BDZZS,d2.BDZS,d2.BDSSJRZZS,\n" + 
					"            d3.DLZZS,d3.DLZS,d3.DLSSJRZZS from (select de.wsmc province_name,de.wsid province_id,de.zdypx xh,count(decode(t.devicecode, null, null,\n" + 
					"            decode(status,'00501',t.devicecode,'00502',t.devicecode,'00503',t.devicecode,'',t.devicecode,null))) as SDZZS,count(distinct t.LINKEDLINE) XLS,\n" + 
					"            count(decode(t.linkedequipmentdy,null,null,decode(ssjr.SFSS,'T',t.devicecode,null))) as SDSSJRZZS from mw_app.cmst_zb_comm_wspz de,\n" + 
					"            (select status,devicecode,linkedprovicedept,linkedequipmentdy,LINKEDLINE,monitoringtype from mw_app.cmst_linedevice l, mw_app.cmst_devicemonitype m\n" + 
					"            where l.devicecode(+) = m.linkeddevice and m.monitoringtype is not null and linkedline is not null and linkedprovicedept is not null\n" + 
					"            and status in ('00501','00502') and linkedequipmentdy is not null) t,\n" + 
					"            (select monitor.linkeddevice,monitor.monitoringtype, info.SFSS from mw_app.cmst_devicemonitype  monitor,mw_app.cmsv_deviceused_info info\n" + 
					"            where monitor.linkeddevice =info.ZZBM(+)and monitor.monitoringtype =info.JCLX(+)) ssjr where t.devicecode = ssjr.linkeddevice(+)\n" + 
					"            and de.wsid = t.linkedprovicedept(+)and t.monitoringtype = ssjr.monitoringtype(+) group by de.wsmc,\n" + 
					"            de.wsid,de.zdypx) d,(select de.wsmc province_name,de.wsid province_id,de.zdypx xh,count(decode(t.devicecode, null, null,\n" + 
					"            decode(status,'00501',t.devicecode,'00502',t.devicecode,'00503',t.devicecode,'',t.devicecode,null))) as BDZZS,\n" + 
					"            count(distinct t.linkedstation) BDZS,count(decode(t.linkedequipmentdy,null,null,decode(ssjr.SFSS,'T',t.devicecode,null))) as BDSSJRZZS\n" + 
					"            from (select * from mw_app.cmst_transfdevice where LINKEDEQUIPMENTDY IS NOT NULL and linkedprovicedept is not null\n" + 
					"            and LINKEDEQUIPMENTDY is not null and status in ('00501','00502') and MONITORINGTYPES LIKE '02%') t,\n" + 
					"            mw_app.cmst_zb_comm_wspz de,(select monitor.linkeddevice,monitor.monitoringtype,info.SFSS from mw_app.cmst_devicemonitype  monitor,\n" + 
					"            mw_app.cmsv_deviceused_info info where monitor.linkeddevice = info.ZZBM(+) and monitor.monitoringtype = info.JCLX(+)) ssjr\n" + 
					"            where t.devicecode = ssjr.linkeddevice(+) and de.wsid = t.linkedprovicedept(+) group by de.wsmc,\n" + 
					"            de.wsid,de.zdypx) d2,(select de.wsmc province_name,de.wsid province_id,de.zdypx xh,count(decode(t.devicecode, null, null,\n" + 
					"            decode(status,'00501',t.devicecode,'00502',t.devicecode,'00503',t.devicecode,'',t.devicecode,null))) as DLZZS,\n" + 
					"            count(distinct t.LINKEDCABLEANDCHANNEL) DLZS,count(decode(t.devicecode,null,null,decode(t.SFSS,'T',t.devicecode,null))) as DLSSJRZZS\n" + 
					"            from (select * from mw_app.cmsv_cabledevice_xtf where LINKEDDEPWS is not null  and DEVICEVOLTAGE is not null and status in ('00501','00502')and MONITORINGTYPES LIKE '03%') t,\n" + 
					"            mw_app.cmst_zb_comm_wspz de where de.wsid = t.LINKEDDEPWS(+) group by de.wsmc,de.wsid,de.zdypx) d3\n" + 
					"            where d.xh = d2.xh and d2.xh = d3.xh order by d.XH)\n" + 
					"            select xx.DWBM,xx.DWMC,xx.SDZZS,xx.XLS,xx.SDSSJRZZS,case when xx.SDZZS = 0 then '-' else round(xx.SDSSJRZZS / xx.SDZZS, 2) * 100 || '%' end as SDSSJRL,\n" + 
					"            xx.BDZZS,xx.BDZS,xx.BDSSJRZZS,case when xx.BDZZS = 0 then '-' else round(xx.BDSSJRZZS / xx.BDZZS, 2) * 100 || '%' end as BDSSJRL,\n" + 
					"            xx.DLZZS, xx.DLZS,xx.DLSSJRZZS, case when xx.DLZZS = 0 then '-' else round(xx.DLSSJRZZS / xx.DLZZS, 2) * 100 || '%'end as DLSSJRL\n" + 
					"            from (select '63EBEC8E-E766-40D7-ACF4-FEA945102112-00042' dwbm,'国家电网公司' dwmc, sum(wstj.SDZZS) SDZZS,sum(wstj.XLS) XLS,\n" + 
					"            sum(wstj.SDSSJRZZS) SDSSJRZZS,sum(wstj.BDZZS) BDZZS,sum(wstj.BDZS) BDZS,sum(wstj.BDSSJRZZS) BDSSJRZZS,sum(wstj.DLZZS) DLZZS,sum(wstj.DLZS) DLZS,\n" + 
					"            sum(wstj.DLSSJRZZS) DLSSJRZZS from wstj union all select * from wstj) xx)  l";


			List<?> list = hibernateDao_ztjc.executeSqlQuery(querySql);

			return RestUtils.wrappQueryResult(list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	/**
	    * 柱状图详细 --输电
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject queryDetail(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String[] qc = params.getFilter().toString().split("&");
		   String depts = qc[0];
		   String index = qc[1];
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			  // cols = params.getColumns().toString();
			   String cols = "DEVICECATEGORY_DISPLAY,ISRT,WSDEPMC,LINKEDLINENAME,LINKEDPOLENAME,DEVICEVOLTAGE,DEVICECATEGORY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE";
			   String querySql = 
					   "SELECT mt.typename DEVICECATEGORY_DISPLAY,nvl(DECODE(di.SFSS,'T','是','F','否'),'否')ISRT,\n" +
							   "       t.WSDEPMC,t.LINKEDLINENAME,t.LINKEDPOLENAME,t.DEVICEVOLTAGE,t.DEVICECATEGORY,t.DEVICENAME,t.DEVICEMODEL,\n" + 
							   "       t.MANUFACTURER,t.RUNDATE\n" + 
							   " FROM MW_APP.CMSV_LINEDEVICE_XTf T, MW_APP.CMST_DEVICEMONITYPE DT,mw_app.cmst_monitoringtype mt,mw_app.cmsv_deviceused_info di\n" + 
							   " WHERE t.WSDEPMC NOT LIKE '%电网%' AND t.WSDEPMC NOT LIKE '%分部%'  AND t.linkedprovicedept IS NOT NULL AND T.LINKEDEQUIPMENTDY IS NOT NULL  AND T.DEVICECODE = DT.LINKEDDEVICE AND dt.monitoringtype = mt.typecode and\n" + 
							   " di.ZZBM (+)= dt.linkeddevice AND di.JCLX (+)= dt.monitoringtype AND T.linkedprovicedept IS NOT NULL AND\n" + 
							   " T.LINKEDEQUIPMENTDY IS NOT NULL  AND T.MONITORINGTYPES LIKE '01%' AND LINKEDLINE IS NOT NULL and linkeddepws in('"+depts+"') and status in('00501','00502')";
			   
			   if ("0".equals(index))
			   {
				   querySql += "and di.sfss = 'T'";
			   }
					
			  
			   
			   result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
			   return qro;
		   } catch (Exception e) {
			   logger.info("错误信息", e);
			   e.printStackTrace();
		   }
		   return null;
	   }
	
	   
	   /**
	    * 柱状图详细 --变电
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject querybdDetail(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String[] qc = params.getFilter().toString().split("&");
		   String depts = qc[0];
		   String index = qc[1];
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			   String cols = "DEVICECATEGORY_DISPLAY,ISRT,WSDEPMC,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,DEVICEVOLTAGE,DEVICECATEGORY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE";
			   String querySql = 
						"SELECT mt.typename DEVICECATEGORY_DISPLAY,nvl(DECODE(di.SFSS,'T','是','F','否'),'否')ISRT,\n" +
						"t.WSDEPMC,t.LinkedStationName,t.LinkedEquipmentName,t.DEVICEVOLTAGE,\n" + 
						"t.DEVICECATEGORY,t.DEVICENAME,t.DEVICEMODEL,t.MANUFACTURER,t.RUNDATE\n" + 
						"FROM MW_APP.CMSV_TRANSFDEVICE_XTF T, MW_APP.CMST_DEVICEMONITYPE DT,mw_app.cmst_monitoringtype mt,mw_app.cmsv_deviceused_info di\n" + 
						"WHERE t.WSDEPMC NOT LIKE '%电网%' AND t.WSDEPMC NOT LIKE '%分部%'  AND t.linkedprovicedept IS NOT NULL AND T.LINKEDEQUIPMENTDY IS NOT NULL\n" + 
						"AND T.DEVICECODE = DT.LINKEDDEVICE AND dt.monitoringtype = mt.typecode and\n" + 
						"di.ZZBM (+)= dt.linkeddevice AND di.JCLX (+)= dt.monitoringtype AND T.linkedprovicedept IS NOT NULL AND\n" + 
						"T.LINKEDEQUIPMENTDY IS NOT NULL  and linkeddepws in('"+depts+"') and status in('00501','00502')";
   
			   
			   if ("0".equals(index))
			   {
				   querySql += "and di.sfss = 'T'";
			   }
			   
			   result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
			   return qro;
		   } catch (Exception e) {
			   logger.info("输电装置查询管理，加载设备列表出错", e);
			   e.printStackTrace();
		   }
		   return null;
	   }
	   /**
	    * 柱状图详细 --电缆
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject querydlDetail(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String[] qc = params.getFilter().toString().split("&");
		   String depts = qc[0];
		   String index = qc[1];
		   String cols = "";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			  // cols = params.getColumns().toString();
			   cols = "DEVICECATEGORY_DISPLAY,ISRT,WSDEPMC,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME,DEVICEVOLTAGE,DEVICECATEGORY,DEVICENAME,DEVICEMODEL,DEVICECODE,MANUFACTURER,RUNDATE";
			   String querySql = 
						"SELECT mt.typename DEVICECATEGORY_DISPLAY,nvl(DECODE(di.SFSS,'T','是','F','否'),'否')ISRT,\n" +
						"t.DEPMC,t.LINKEDCABLEANDCHANNELNAME,t.LinkedEquipmentName,t.DEVICEVOLTAGE,\n" + 
						"t.DEVICECATEGORY,t.DEVICENAME,t.DEVICEMODEL,T.DEVICECODE,t.MANUFACTURER,t.RUNDATE\n" + 
						"FROM mw_app.cmsv_cabledevice_xtf T,mw_app.cmst_monitoringtype mt,mw_app.cmsv_deviceused_info di\n" + 
						"WHERE t.DEPMC NOT LIKE '%电网%' AND t.DEPMC NOT LIKE '%分部%'  AND  t.LINKEDDEPWS IS NOT NULL  AND t.monitoringtypes = mt.typecode and\n" + 
						"di.ZZBM (+)= t.devicecode AND di.JCLX (+)= t.monitoringtypes AND T.linkeddept IS NOT NULL AND t.DEVICEVOLTAGE is not null and\n" + 
						"T.MONITORINGTYPES LIKE '03%'  and linkeddept in('"+depts+"') and status in('00501','00502')";

			   
			   if ("0".equals(index))
			   {
				   querySql += "and di.sfss = 'T'";
			   }
			   result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
			 
			   return qro;
			   
		   } catch (Exception e) {
			   logger.info("输电装置查询管理，加载设备列表出错", e);
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
}
