package com.sgcc.pms.zbztjc.devicegeneral.bizc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;





/**
 * 用户定义逻辑构件
 * 
 * @author Administrator 
 * 
 */
public class DevicegeneralBizc implements IDevicegeneralBizc{


	private final Log logger = LogFactory.getLog(DevicegeneralBizc.class);
	@Resource
	private IHibernateDao hibernateDao_ztjc;


	
	 public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}
	   
	   /**
	    * 变电监测信息统计
	    * @param bdxxtj
	    * @return
	    */
	   public QueryResultObject getWtysblb() {
		   
		   QueryResultObject qro = new QueryResultObject();
		   List result = new ArrayList();
		   String cols = "";
		   try {
			   cols = "DWBM,DWMC,SDZZS,SDZYS,SDTYS,SDJXS,XLS,SDSSJRZZS,SDSSJRL,BDZZS,BDZYS,BDTYS,BDZS,BDSSJRZZS,BDSSJRL,DLZZS,DLZYS,DLTYS,DLZS,DLSSJRZZS,DLSSJRL";
			   String querySql =
				"SELECT DWBM,DWMC,SDZZS,SDZYS,SDTYS,SDJXS,XLS,SDSSJRZZS,SDSSJRL,BDZZS,BDZYS,BDTYS,BDZS,BDSSJRZZS,BDSSJRL,DLZZS,DLZYS,DLTYS,DLZS,DLSSJRZZS,DLSSJRL\n" +
				"FROM (SELECT DWBM,DWMC,SDZZS,SDZYS,SDTYS,SDJXS, XLS,SDSSJRZZS,SDSSJRL,BDZZS,BDZYS,BDTYS,BDZS,BDSSJRZZS, BDSSJRL,DLZZS,DLZYS,DLTYS,DLZS,DLSSJRZZS,DLSSJRL\n" + 
				"FROM (with wstj as (select d.province_id DWBM,d.province_name DWMC,d.SDZZS,d.SDZYS,d.SDTYS,d.SDJXS,d.XLS,d.SDSSJRZZS,d2.BDZZS,\n" + 
				"d2.BDZYS, d2.BDTYS, d2.BDZS, d2.BDSSJRZZS, d3.DLZZS, d3.DLZYS,d3.DLTYS,d3.DLZS,d3.DLSSJRZZS from (select de.wsmc province_name,\n" + 
				"de.wsid province_id,de.zdypx xh,count(decode(t.devicecode,null,null,decode(status,'00501',t.devicecode,'00502',t.devicecode,'00503',t.devicecode,'',t.devicecode,null))) as SDZZS,\n" + 
				"count(decode(t.devicecode,null,null,decode(status,'00501',t.devicecode,'',t.devicecode,null))) as SDZYS,sum(decode(status, '00502', 1, 0)) as SDTYS,sum(decode(status, '00504', 1, 0)) as SDJXS,\n" + 
				"count(distinct t.LINKEDLINE) XLS,count(decode(t.linkedequipmentdy,null, null,decode(ssjr.SFSS,'T',t.devicecode,null))) as SDSSJRZZS from mw_app.CMST_ZB_COMM_WSPZ de,\n" + 
				"(select status,devicecode,linkedprovicedept,linkedequipmentdy,LINKEDLINE,monitoringtype from mw_app.cmst_linedevice l,\n" + 
				"mw_app.cmst_devicemonitype m where l.devicecode(+) = m.linkeddevice\n" + 
				"and m.monitoringtype is not null and linkedline is not null and linkedprovicedept is not null\n" + 
				"and linkedequipmentdy is not null) t,(select distinct monitor.linkeddevice,monitor.monitoringtype,\n" + 
				"info.SFSS from mw_app.cmst_devicemonitype  monitor,mw_app.cmsv_deviceused_info info\n" + 
				"where monitor.linkeddevice = info.ZZBM(+) and monitor.monitoringtype = info.JCLX(+)) ssjr\n" + 
				"where t.devicecode = ssjr.linkeddevice(+) and de.wsid = t.linkedprovicedept(+) and t.monitoringtype = ssjr.monitoringtype(+)\n" + 
				"group by de.wsmc,de.wsid,de.zdypx) d, (select de.wsmc province_name,de.wsid province_id,de.zdypx xh,count(decode(t.devicecode,\n" + 
				"null,null,decode(status,'00501',t.devicecode,'00502',t.devicecode,'00503',t.devicecode,'',t.devicecode,null))) as BDZZS,\n" + 
				"count(decode(t.devicecode,null,null,decode(status,'00501',t.devicecode,'',t.devicecode,null))) as BDZYS,\n" + 
				"sum(decode(status,'00502',1,0))as BDTYS,count(distinct t.linkedstation) BDZS,count(decode(t.linkedequipmentdy,\n" + 
				"null,null,decode(ssjr.SFSS,'T',t.devicecode,null))) as BDSSJRZZS from (select * from mw_app.cmst_transfdevice\n" + 
				"where LINKEDEQUIPMENTDY IS NOT NULL and linkedprovicedept is not null) t,\n" + 
				"mw_app.CMST_ZB_COMM_WSPZ de,(select distinct monitor.linkeddevice,monitor.monitoringtype,info.SFSS from mw_app.cmst_devicemonitype  monitor,\n" + 
				"mw_app.cmsv_deviceused_info info where monitor.linkeddevice =info.ZZBM(+) and monitor.monitoringtype = info.JCLX(+)) ssjr\n" + 
				"where t.devicecode = ssjr.linkeddevice(+) and de.wsid = t.linkedprovicedept(+) group by de.wsmc,\n" + 
				"de.wsid,de.zdypx) d2,(select de.wsmc province_name,de.wsid province_id,de.zdypx xh,count(decode(t.devicecode,null,null,\n" + 
				"decode(status,'00501',t.devicecode,'00502',t.devicecode,'00503',t.devicecode,'',t.devicecode,null))) as DLZZS,\n" + 
				"count(decode(t.devicecode,null,null,decode(status,'00501',t.devicecode,'',t.devicecode,null))) as DLZYS,\n" + 
				"sum(decode(status,'00502',1,0)) as DLTYS,count(distinct t.LINKEDCABLEANDCHANNEL) DLZS,\n" + 
				"count(decode(SFSS,'T',t.devicecode,null)) as DLSSJRZZS from (select * from mw_app.cmsv_cabledevice_xtf\n" + 
				"where 1 = 1 and LINKEDDEPWS is not null and DYDJGDJB is not null) t,mw_app.CMST_ZB_COMM_WSPZ de where 1 = 1 and de.wsid = t.LINKEDDEPWS(+)\n" + 
				"group by de.wsmc,de.wsid,de.zdypx) d3  where \n" + 
				" d.xh = d2.xh and d2.xh = d3.xh order by d.XH) select xx.DWBM,xx.DWMC,xx.SDZZS,xx.SDZYS,xx.SDTYS,xx.SDJXS,xx.XLS,\n" + 
				"xx.SDSSJRZZS,case when xx.SDZZS = 0 then '-' else round(xx.SDSSJRZZS / xx.SDZYS, 2) * 100 || '%'\n" + 
				"end as SDSSJRL,xx.BDZZS,xx.BDZYS,xx.BDTYS,xx.BDZS,xx.BDSSJRZZS,case when xx.BDZZS = 0 then '-' else\n" + 
				"round(xx.BDSSJRZZS / xx.BDZYS, 2) * 100 || '%' end as BDSSJRL,xx.DLZZS,xx.DLZYS,xx.DLTYS,xx.DLZS,xx.DLSSJRZZS,\n" + 
				"case when xx.DLZZS = 0 then '-' else round(xx.DLSSJRZZS / xx.DLZYS, 2) * 100 || '%' end as DLSSJRL\n" + 
				"from (select '63EBEC8E-E766-40D7-ACF4-FEA945102112-00042' dwbm,'国家电网公司' dwmc, sum(wstj.SDZZS) SDZZS,sum(wstj.SDZYS) SDZYS,sum(wstj.SDTYS) SDTYS,sum(wstj.SDJXS) SDJXS,sum(wstj.XLS) XLS,\n" + 
				"sum(wstj.SDSSJRZZS) SDSSJRZZS,sum(wstj.BDZZS) BDZZS,sum(wstj.BDZYS) BDZYS,sum(wstj.BDTYS) BDTYS,sum(wstj.BDZS) BDZS,sum(wstj.BDSSJRZZS) BDSSJRZZS,\n" + 
				"sum(wstj.DLZZS) DLZZS,sum(wstj.DLZYS) DLZYS,sum(wstj.DLTYS) DLTYS,sum(wstj.DLZS) DLZS,sum(wstj.DLSSJRZZS) DLSSJRZZS from wstj union all select * from wstj) xx))";

			   result = hibernateDao_ztjc.executeSqlQuery(querySql);
			   result = transToColumns(result,cols);
			   qro.setItems(result);
			   return qro;
		   } catch (Exception e) {
			   logger.info("错误信息", e);
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

	   /**
	    * 详细信息查询
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject loaddetail(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		   System.out.println(params.getFilter().toString());
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "";
		   String[] qc = params.getFilter().toString().split("&");
		   String dwbm = qc[0].split("=")[1];
		   String st = qc[1].split("=")[1];
		   String lx = qc[2].split("=")[1];
		   String sfss = qc[3].split("=")[1];
		   String querySql = "";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			   
			   if("sd".equals(lx))
			   { 
				   cols = "DEPMC,XH,DEVICEVOLTAGE,STATUS,LINKEDLINENAME,LINKEDPOLENAME,DEVICENAME,ISRT,DEVICECATEGORY_DISPLAY,MANUFACTURER,RUNDATE";
				   querySql = 
							"SELECT WSDEPMC DEPMC, XH,DEVICEVOLTAGE,decode(status,'00501','在运','00502','停运','00504','调试') status,\n" +
							"LINKEDLINENAME,LINKEDPOLENAME, DEVICENAME, decode(SFSS,'T','是','F','否',null,'否') ISRT,\n" + 
							"typename DEVICECATEGORY_DISPLAY,MANUFACTURER,RUNDATE\n" + 
							" FROM MW_APP.CMSV_LINEDEVICE_LOGIC_XTf t \n" + 
							" where  t.WSDEPMC is not null  and t.DEVICEVOLTAGE is not null AND dydj IS NOT NULL \n"+
							"and status = '"+st+"'";
				   if(!"63EBEC8E-E766-40D7-ACF4-FEA945102112-00042".equals(dwbm))
				   {
					   querySql += " and linkedprovicedept = '"+dwbm+"'";
				   }
				   if("T".equals(sfss))
				   {
					   querySql += " and SFSS ='T'"; 
				   }
			   }
			   else if("bd".equals(lx))
			   {
				   cols = "WSDEPMC,XH,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,DEVICEVOLTAGE,STATUS,DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,ISRT";
				   querySql = 
						"SELECT WSDEPMC,XH,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,DEVICEVOLTAGE,decode(status,'00501','在运','00502','停运','00504','调试') status,DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,\n" +
						"decode(SFSS,'T','是','F','否', null,'否') ISRT FROM MW_APP.CMSV_TRANSFDEVICE_XTF T, (select distinct monitor.linkeddevice,monitor.monitoringtype,info.SFSS\n" + 
						"from mw_app.cmst_devicemonitype  monitor,mw_app.cmsv_deviceused_info info where monitor.linkeddevice = info.ZZBM(+) and monitor.monitoringtype = info.JCLX(+)) ssjr\n" + 
						"where t.devicecode = ssjr.linkeddevice  and t.WSDEPMC is not null and t.DEVICEVOLTAGE is not null and t.status = '"+st+"'";

				   if(!"63EBEC8E-E766-40D7-ACF4-FEA945102112-00042".equals(dwbm))
				   {
					   querySql += " and LINKEDDEPWS = '"+dwbm+"'";
				   }
				   if("T".equals(sfss))
				   {
					   querySql += " and SFSS ='T'"; 
				   }
			   }
			   else if("dl".equals(lx))
			   {
				   cols = "DEPMC,XH,DEVICEVOLTAGE,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME,DEVICENAME,DEVICECATEGORY_DISPLAY,MANUFACTURER,STATUS,ISRT";
				   querySql = 
						"select wsmc depmc,xh,t.devicevoltage,t.linkedcableandchannelname,t.linkedequipmentname,devicename,t.devicecategory_display,t.manufacturer,\n" +
						"decode(status,'00501','在运','00502','停运','00504','调试') status,decode(SFSS,'T','是','F','否',null,'否') ISRT\n" + 
						" from (select * from mw_app.cmsv_cabledevice_xtf where 1 = 1 and LINKEDDEPWS is not null and DYDJGDJB is not null) t,\n" + 
						" mw_app.CMST_ZB_COMM_WSPZ de where 1 = 1 and de.wsid = t.LINKEDDEPWS(+) and status = '"+st+"'";


				   if(!"63EBEC8E-E766-40D7-ACF4-FEA945102112-00042".equals(dwbm))
				   {
					   querySql += " and LINKEDDEPT = '"+dwbm+"'";
				   }
				   if("T".equals(sfss))
				   {
					   querySql += " and SFSS ='T'"; 
				   }
			   }
			   else if("xl".equals(lx))
			   {
				   cols = "LINKEDLINE,LINKEDLINENAME,LINKEDEQUIPMENTDY,DWMC,XH";
				   
				   if(!"63EBEC8E-E766-40D7-ACF4-FEA945102112-00042".equals(dwbm))
				   {
					   querySql = 
							"select LINKEDLINE,LINKEDLINENAME,MC LINKEDEQUIPMENTDY,province_name DWMC,XH\n" +
							"  from (select (select linkedline from mw_app.CMSt_LINEDEVICE n where n.linkedline = t.linkedline\n" + 
							" and rownum <= 1) linkedline, (select linkedlinename from mw_app.CMSt_LINEDEVICE n where n.linkedline = t.linkedline\n" + 
							"  and rownum <= 1) linkedlinename, max(t0.dydjmc) MC,zdypx xh, (select wsmc from MW_APP.CMST_ZB_COMM_WSPZ D\n" + 
							"where wsid = '"+dwbm+"') province_name from MW_APP.CMSt_LINEDEVICE t,\n" + 
							"mw_app.CMST_SB_PZ_SBDYDJ t0,CMST_ZB_COMM_WSPZ t1  where t.linkedequipmentdy = t0.dydjbm\n" + 
							" and t.LINKEDEQUIPMENTDY IS NOT NULL and t.linkedprovicedept = t1.wsid\n" + 
							"and t.linkedprovicedept ='"+dwbm+"' group by t.linkedline,zdypx) where province_name is not null";

				   }
				   else 
					   
					   querySql = 
							"select LINKEDLINE,LINKEDLINENAME,mc LINKEDEQUIPMENTDY,province_name DWMC,pms_xh XH from (select distinct(select linkedline from mw_app.CMSt_LINEDEVICE n where n.linkedline = t.linkedline and rownum <= 1)\n" +
							"linkedline ,(select linkedlinename from mw_app.CMSt_LINEDEVICE n where n.linkedline = t.linkedline and rownum <= 1) linkedlinename ,\n" + 
							"max(t0.dydjMC) MC, zdypx pms_xh, wsmc province_name from (select * from MW_APP.CMSt_LINEDEVICE t0,\n" + 
							"MW_APP.CMST_ZB_COMM_WSPZ t1 where t0.linkedprovicedept = t1.wsid and t0.LINKEDEQUIPMENTDY IS NOT NULL\n" + 
							") t, mw_app.CMST_SB_PZ_SBDYDJ t0 where t.linkedequipmentdy = t0.dydjbm group by t.LINKEDLINE, t.wsmc,zdypx)";

			   }
			   else if("dz".equals(lx))
			   {
				   cols = "LINKEDSTATION,LINKEDSTATIONNAME,MC,XH,PROVINCE_NAME";
				   
				   if(!"63EBEC8E-E766-40D7-ACF4-FEA945102112-00042".equals(dwbm))
				   {
					   querySql = 
							"select * from (select distinct (select n.linkedstation from mw_app.cmst_transfdevice n where t.linkedstation = n.linkedstation\n" +
							"and rownum <= 1) linkedstation,(select n.linkedstationname from mw_app.cmst_transfdevice n\n" + 
							"where t.linkedstation = n.linkedstation  and rownum <= 1) linkedstationname, max(t0.dydjMC) MC, zdypx xh,\n" + 
							"(select wsmc  from MW_APP.CMST_ZB_COMM_WSPZ D where wsid = '"+dwbm+"') province_name\n" + 
							"  from MW_APP.CMST_transfdevice t, mw_app.CMST_SB_PZ_SBDYDJ t0 ,CMST_ZB_COMM_WSPZ t1\n" + 
							" where t.linkedequipmentdy = t0.dydjbm  and t.linkedprovicedept = t1.wsid\n" + 
							" and t.linkedprovicedept = '"+dwbm+"' group by t.linkedstation ,zdypx)";


				   }
				   else 
					   
					   querySql = 
							"select * from (select distinct (select n.linkedstation from mw_app.cmst_transfdevice n where t.linkedstation = n.linkedstation\n" +
							"and rownum <= 1) linkedstation,(select n.linkedstationname from mw_app.cmst_transfdevice n\n" + 
							"where t.linkedstation = n.linkedstation  and rownum <= 1) linkedstationname, max(t0.MC) MC, zdypx xh, wsmc province_name\n" + 
							"  from MW_APP.CMST_transfdevice t, mw_app.CMST_SB_PZ_SBDYDJ t0 ,CMST_ZB_COMM_WSPZ t1\n" + 
							" where t.linkedequipmentdy = t0.dydjbm  and t.linkedprovicedept = t1.wsid\n" + 
							" and t.linkedprovicedept is not null group by t.linkedstation ,wsmc,zdypx)";

			   }
			   else if("dls".equals(lx))
			   {
				   cols = "LINKEDCABLEANDCHANNEL,LINKEDLINENAME,MC,XH,PROVINCE_NAME";
				   
				   if(!"63EBEC8E-E766-40D7-ACF4-FEA945102112-00042".equals(dwbm))
				   {
					   querySql = 
							"select * from (select distinct(LINKEDCABLEANDCHANNEL),LINKEDCABLEANDCHANNELNAME LINKEDLINENAME,max(DEVICEVOLTAGE) MC,zdypx xh,\n" +
							"t1.wsmc province_name from mw_app.cmsv_cabledevice_xtf t0,MW_APP.CMST_ZB_COMM_WSPZ t1\n" + 
							"where t0.LINKEDDEPWS = t1.wsid and LINKEDCABLEANDCHANNEL is not null\n" + 
							"and LINKEDDEPWS = '"+dwbm+"' and t0.Status = '00501' and DEVICEVOLTAGE is not null\n" + 
							"group by t0.LINKEDCABLEANDCHANNEL,t0.LINKEDCABLEANDCHANNELNAME,t1.wsmc,zdypx)";

				   }
				   else 
					   
					   querySql = 
						"select * from (select distinct(LINKEDCABLEANDCHANNEL),LINKEDCABLEANDCHANNELNAME LINKEDLINENAME,max(DEVICEVOLTAGE) MC,zdypx xh,\n" +
								"t1.wsmc province_name from mw_app.cmsv_cabledevice_xtf t0,MW_APP.CMST_ZB_COMM_WSPZ t1\n" + 
								"where t0.LINKEDDEPWS = t1.wsid and LINKEDCABLEANDCHANNEL is not null\n" + 
								"and t0.Status = '00501' and DEVICEVOLTAGE is not null\n" + 
								"group by t0.LINKEDCABLEANDCHANNEL,t0.LINKEDCABLEANDCHANNELNAME,t1.wsmc,zdypx)";

			   }
			   
			
			   querySql += " order by XH";
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
	   
}

