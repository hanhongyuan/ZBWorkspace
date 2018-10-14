package com.sgcc.pms.zbztjc.kqxt.jcxxcx.zyxtdevice.bizc;


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
public class ZyxtdeviceBizc implements IZyxtdeviceBizc{


	private final Log logger = LogFactory.getLog(ZyxtdeviceBizc.class);
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
	   public QueryResultObject getWtysblb(RequestCondition params) {
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "WSDEPMC,XTMC,XLMC,GTMC,SFSS,DEVICEVOLTAGE,DEVICECATEGORY,DEVICECODE,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,LX";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			   String zy = "cx";
			   String querySql = "";
			   if(null!=params.getFilter())
			   {
				   String bdfilter = getbdFilter(params,zy);
				   String sdfilter = getsdFilter(params,zy);
				   String[] qc = params.getFilter().toString().split("&");
				   for(int i = 0; i < qc.length; i++) {
					   
					   if("lx".equals(qc[i].split("=")[0])) {
						  String lx =  qc[i].split("=")[1];
						  if("all".equals(lx))
						  querySql = 
								"SELECT WSDEPMC,XTMC,T.LINKEDLINENAME xlmc, T.LINKEDPOLENAME gtmc,SFSS, DEVICEVOLTAGE,DEVICECATEGORY,DEVICECODE,MONITORINGTYPE MONITORINGTYPES,\n" +
								"typename DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,'sd' lx\n" + 
								"FROM MW_APP.CMSV_LINEDEVICEf t,mw_app.cmst_monitoringtype m ,mw_app.cmst_devicemonitype d\n" + 
								"where XTMC is not null and WSDEPMC is not null and d.linkeddevice = t.DEVICECODE and m.typecode=d.monitoringtype and m.typecode != '018003' and DEVICEVOLTAGE is not null "+sdfilter+"  AND LINKEDEQUIPMENTDY IS NOT NULL\n" + 
								"UNION ALL\n" + 
								"SELECT WSDEPMC,XTMC,E.LinkedStationNAME xlmc, E.LinkedEquipmentName gtmc,SFSS,DEVICEVOLTAGE,DEVICECATEGORY,DEVICECODE,MONITORINGTYPES,\n" + 
								"DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,'bd' lx\n" + 
								"FROM MW_APP.CMSV_TRANSFDEVICEF E  where XTMC is not null and WSDEPMC is not null and MONITORINGTYPES != '026001' and DEVICEVOLTAGE is not null "+bdfilter+" AND LINKEDEQUIPMENTDY IS NOT NULL";

						  else if("L".equals(lx))
						  querySql = 
						  		"SELECT WSDEPMC,XTMC,T.LINKEDLINENAME xlmc, T.LINKEDPOLENAME gtmc,SFSS, DEVICEVOLTAGE,DEVICECATEGORY,DEVICECODE,MONITORINGTYPE MONITORINGTYPES,\n" +
								"typename DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,'sd' lx\n" + 
								"FROM MW_APP.CMSV_LINEDEVICEf t,mw_app.cmst_monitoringtype m ,mw_app.cmst_devicemonitype d\n" + 
								"where XTMC is not null and WSDEPMC is not null and d.linkeddevice = t.DEVICECODE and m.typecode=d.monitoringtype and m.typecode != '018003' and DEVICEVOLTAGE is not null "+sdfilter+" AND LINKEDEQUIPMENTDY IS NOT NULL";
						  else if("T".equals(lx))
							  querySql = 
						  		"SELECT WSDEPMC,XTMC,E.LinkedStationNAME xlmc, E.LinkedEquipmentName gtmc,SFSS,DEVICEVOLTAGE,DEVICECATEGORY,DEVICECODE,MONITORINGTYPES,\n" + 
								"DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,'bd' lx\n" + 
								"FROM MW_APP.CMSV_TRANSFDEVICEF E  where XTMC is not null and WSDEPMC is not null and MONITORINGTYPES != '026001' and DEVICEVOLTAGE is not null "+bdfilter+" AND LINKEDEQUIPMENTDY IS NOT NULL";
					   }
				   }
			   }
			   result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
			   qro.addDicItems(wrapDictList());
			   
			   
		   } catch (Exception e) {
			   logger.info("输电装置查询管理，加载设备列表出错", e);
			   e.printStackTrace();
		   }
		   return qro;
	   }
	   
	   /**
	    * 输电监测信息统计
	    * @param sdxxtj
	    * @return
	    */
	   public QueryResultObject gettjxx(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "";
		   String zy = "tj";
		   try {
			   cols = "SSXTID,SSXTMC,QXJCLJRS_SD,QXJCLZS_SD,DXJCLJRS_SD,DXJCLZS_SD,GTJCLJRS_SD,GTJCLZS_SD,JYZJCLJRS_SD,JYZJCLZS_SD,JRSHJ_SD,ZSHJ_SD,SSJRL,BYDKLJRS_BD,BYDKLZS_BD,DLGISLJRS_BD,DLGISLZS_BD,DRXLJRS_BD,DRXLZS_BD,JSYHWLJRS_BD,JSYHWLZS_BD,BDJRSHJ_BD,BDZSHJ_BD,BDSSJRL";
			   
			   String bdfilter = getbdFilter(params,zy);
			   String sdfilter = getsdFilter(params,zy);
			   String ssxt = getssxt(params);
			   String querySql = 
						"SELECT 'all' SSxtid,'合计' SSxtmc,sum(qxjcljrs) qxjcljrs,sum(qxjclzs) qxjclzs,\n" +
						"sum(dxjcljrs) dxjcljrs,sum(dxjclzs) dxjclzs,sum(gtjcljrs) gtjcljrs,sum(gtjclzs) gtjclzs,\n" + 
						"sum(jyzjcljrs) jyzjcljrs,sum(jyzjclzs) jyzjclzs,sum(jrshj) jrshj,\n" + 
						"sum(zshj) zshj,(case when sum(zshj) = 0 then '-' else\n" + 
						"to_char((sum(jrshj) / sum(zshj) * 100),'990.99')||'%' end) SSJRL,\n" + 
						"sum(BYDKLJRS) BYDKLJRS,sum(BYDKLZS) BYDKLZS,\n" + 
						"sum(DLGISLJRS) DLGISLJRS,sum(DLGISLZS) DLGISLZS, sum(DRXLJRS) DRXLJRS,sum(DRXLZS) DRXLZS,\n" + 
						"sum(JSYHWLJRS) JSYHWLJRS,sum(JSYHWLZS) JSYHWLZS, sum(BDjrshj) BDjrshj,sum(BDzshj) BDzshj,\n" + 
						"(case when (sum(BDzshj)) = 0 then '-' else to_char(((sum(BDjrshj)) / (sum(BDzshj)) * 100),'990.99')||'%' end) BDSSJRL FROM\n" + 
						"(select NVL(QXJCLJRS,0) QXJCLJRS,NVL(QXJCLZS,0) QXJCLZS,NVL(DXJCLJRS,0) DXJCLJRS,NVL(DXJCLZS,0) DXJCLZS,\n" + 
						"NVL(GTJCLJRS,0) GTJCLJRS,NVL(GTJCLZS,0) GTJCLZS,NVL(JYZJCLJRS,0) JYZJCLJRS, NVL(JYZJCLZS,0) JYZJCLZS,\n" + 
						"NVL(JRSHJ,0) JRSHJ,NVL(ZSHJ,0) ZSHJ,NVL(SSJRL,0) SSJRL, NVL(table2.BYDKLJRS,0) BYDKLJRS,\n" + 
						"NVL(table2.BYDKLZS,0) BYDKLZS, NVL(table2.dlgisljrs,0) dlgisljrs,NVL(table2.dlgislzs,0) dlgislzs,\n" + 
						"NVL(table2.drxljrs,0) drxljrs,NVL(table2.drxlzs,0) drxlzs, NVL(table2.jsyhwljrs,0) jsyhwljrs,NVL(table2.jsyhwlzs,0) jsyhwlzs,\n" + 
						" NVL(table2.bdjrshj,0) bdjrshj,NVL(table2.bdzshj,0) bdzshj,NVL(table2.bdssjrl,0) bdssjrl,\n" + 
						"xt.obj_id ssxtid,xt.xtmc ssxtmc from (with tab as (select xtid,xtmc,max(qxjcljrs) qxjcljrs,\n" + 
						"max(qxjclzs) qxjclzs,max(dxjcljrs) dxjcljrs,\n" + 
						"max(dxjclzs) dxjclzs,max(gtjcljrs) gtjcljrs, max(gtjclzs) gtjclzs,\n" + 
						"max(jyzjcljrs) jyzjcljrs, max(jyzjclzs) jyzjclzs,\n" + 
						"max(qxjcljrs)+max(dxjcljrs)+max(gtjcljrs)+max(jyzjcljrs) jrshj,\n" + 
						"max(qxjclzs)+max(dxjclzs)+max(gtjclzs)+max(jyzjclzs) zshj,\n" + 
						"(case when (max(qxjclzs)+max(dxjclzs)+max(gtjclzs)+max(jyzjclzs)) = 0 then '-' else\n" + 
						"to_char(((max(qxjcljrs)+max(dxjcljrs)+max(gtjcljrs)+max(jyzjcljrs)) / (max(qxjclzs)+\n" + 
						"max(dxjclzs)+max(gtjclzs)+max(jyzjclzs)) * 100),'990.99')||'%' end) SSJRL  from\n" + 
						"(select xtid,xtmc,(case jcdl when '导线监测类' then sum(zzzs) else 0 end) dxjclzs,\n" + 
						"(case jcdl when '杆塔监测类' then sum(zzzs) else 0 end) gtjclzs,(case jcdl when '绝缘子监测类' then sum(zzzs) else 0 end) jyzjclzs,\n" + 
						"(case jcdl when '气象环境监测类' then sum(zzzs) else 0 end) qxjclzs,\n" + 
					
						"(case jcdl when '导线监测类' then sum(ssjrzzs) else 0 end) dxjcljrs, (case jcdl when '杆塔监测类' then sum(ssjrzzs) else 0 end)\n" + 
						"gtjcljrs,  (case jcdl when '绝缘子监测类' then sum(ssjrzzs) else 0 end) jyzjcljrs,(case jcdl when '气象环境监测类'\n" + 
						"then sum(ssjrzzs) else 0 end) qxjcljrs from (select tab1.*, decode(tab2.ssjrzzs, null, 0, tab2.ssjrzzs) ssjrzzs\n" + 
						"from (select xtid,xtmc,w.jczy,w.zzzs,w.jcdl from\n" + 
						"(SELECT DEVICEMONITYPE.MONITORINGTYPE jczy,count(distinct(LINEDEVICE.DEVICECODE)) zzzs,xtid,xtmc,\n" + 
						"(case DEVICEMONITYPE.MONITORINGTYPE when '012001' then '杆塔监测类' when '013001' then '导线监测类'\n" + 
						"when '013002' then '导线监测类' when '013003' then '导线监测类' when '013004' then '导线监测类'\n" + 
						"when '013005' then '导线监测类' when '013006' then '导线监测类' when '014001' then '绝缘子监测类'\n" + 
						"when '018001' then '气象环境监测类' when '018002' then '气象环境监测类' when '018003' then '视频类'\n" + 
						"else '' end) jcdl FROM (select * from mw_app.cmsv_linedevicef\n" + 
						"where   LINKEDEQUIPMENTDY IS NOT NULL AND DEPMC IS NOT NULL  )  LINEDEVICE,\n" + 
						"MW_APP.CMST_DEVICEMONITYPE DEVICEMONITYPE\n" + 
						"WHERE LINEDEVICE.DEVICECODE = DEVICEMONITYPE.LINKEDDEVICE "+sdfilter+"\n" + 
						"group by DEVICEMONITYPE.MONITORINGTYPE,xtid,xtmc) w\n" + 
						") tab1, (select monitor.monitoringtype jclx,count(distinct LINEDEVICE.DEVICECODE) ssjrzzs,xtid\n" + 
						"from (select * from mw_app.cmsv_linedevicef where  LINKEDEQUIPMENTDY IS NOT NULL AND DEPMC IS NOT NULL )     LINEDEVICE,\n" + 
						"mw_app.cmst_devicemonitype monitor\n" + 
						"WHERE LINEDEVICE.DEVICECODE = monitor.linkeddevice\n" + 
						"and (monitor.linkeddevice, monitor.monitoringtype) in\n" + 
						"(select zzbm, jclx from mw_app.cmsv_deviceused_info where sfss = 'T') "+sdfilter+"\n" + 
						"group by  monitor.monitoringtype,xtid) tab2\n" + 
						"where tab1.jczy = tab2.jclx(+) and tab1.xtid = tab2.xtid(+))group by xtid,xtmc, jcdl)\n" + 
						"group by xtid,xtmc order by xtmc )\n" + 
						"select * from tab) table1,\n" + 
						"(with tabs as (select xtid,xtmc,max(BYDKLJRS) BYDKLJRS,max(BYDKLZS) BYDKLZS,max(DLGISLJRS) DLGISLJRS,\n" + 
						"max(DLGISLZS) DLGISLZS,max(DRXLJRS) DRXLJRS,max(DRXLZS) DRXLZS,  max(JSYHWLJRS) JSYHWLJRS,\n" + 
						"max(JSYHWLZS) JSYHWLZS,max(BYDKLJRS)+max(JSYHWLJRS)+max(DLGISLJRS)+max(DRXLJRS) BDjrshj,\n" + 
						"max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(DRXLZS) BDzshj,\n" + 
						"(case when (max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(DRXLZS)) = 0 then '-' else\n" + 
						"to_char(((max(BYDKLJRS)+max(JSYHWLJRS)+max(DLGISLJRS)+max(DRXLJRS)) / (max(BYDKLZS)+max(JSYHWLZS)\n" + 
						"+max(DLGISLZS)+max(DRXLZS)) * 100),'990.99')||'%' end) BDSSJRL\n" + 
						"from (select xtid,xtmc,(case jcdl when '变压器/电抗器类' then sum(zzzs) else 0 end) BYDKLZS,\n" + 
						"(case jcdl when '断路器/GIS类' then sum(zzzs) else 0 end) DLGISLZS,(case jcdl when '电容型设备类' then sum(zzzs)else 0 end) DRXLZS,\n" + 
						"(case jcdl when '金属氧化物避雷器类' then sum(zzzs)else 0 end) JSYHWLZS,\n" + 
						"(case jcdl when '变压器/电抗器类' then sum(ssjrzzs)else 0 end) BYDKLJRS,(case jcdl when '断路器/GIS类'then sum(ssjrzzs)else 0 end)\n" + 
						"DLGISLJRS,(case jcdl when '电容型设备类'then sum(ssjrzzs)else 0 end) DRXLJRS,\n" + 
						"(case jcdl when '金属氧化物避雷器类'then sum(ssjrzzs)else 0 end) JSYHWLJRS\n" + 
						"from (  select tab1.*, decode(tab2.ssjrzzs,null,0,tab2.ssjrzzs) ssjrzzs from\n" + 
						"(  select xtid,xtmc,w.jczy,w.zzzs,w.jcdl from\n" + 
						"(SELECT DEVICEMONITYPE.MONITORINGTYPE jczy,count(distinct(LINEDEVICE.DEVICECODE)) zzzs,xtid,xtmc,\n" + 
						"(case DEVICEMONITYPE.MONITORINGTYPE when '021001'then '变压器/电抗器类'when'021002'then'变压器/电抗器类'\n" + 
						"when'021003'then'变压器/电抗器类'when'021004'then'变压器/电抗器类'when'021005'then'变压器/电抗器类'\n" + 
						"when'021007'then'变压器/电抗器类'when'022001'then'电容型设备类'when'023001'then'金属氧化物避雷器类'\n" + 
						"when'024001'then  '断路器/GIS类'when'024002'then'断路器/GIS类'when'024004'then'断路器/GIS类'\n" + 
						"when'024005'then'断路器/GIS类'when'024006'then'断路器/GIS类'when '024003'then'断路器/GIS类'when'026001'then'综合类' else '' end) jcdl\n" + 
						"FROM (select * from mw_app.cmsv_transfdevicef where LINKEDEQUIPMENTDY IS NOT NULL ) LINEDEVICE\n" + 
						",MW_APP.CMST_DEVICEMONITYPE DEVICEMONITYPE  WHERE LINEDEVICE.DEVICECODE = DEVICEMONITYPE.LINKEDDEVICE "+bdfilter+"\n" + 
						"group by  DEVICEMONITYPE.MONITORINGTYPE,xtid,xtmc) w ) tab1 ,(  select xtid,monitor.monitoringtype jclx,\n" + 
						"count(LINEDEVICE.DEVICECODE) ssjrzzs from (select *  from mw_app.cmsv_transfdevicef  where LINKEDEQUIPMENTDY IS NOT NULL  ) LINEDEVICE,\n" + 
						"mw_app.cmst_devicemonitype monitor  WHERE LINEDEVICE.DEVICECODE = monitor.linkeddevice\n" + 
						"and (monitor.linkeddevice) in (select zzbm from mw_app.cmsv_deviceused_info where sfss = 'T') "+bdfilter+"\n" + 
						"group by  monitor.monitoringtype,xtid) tab2 where tab1.jczy = tab2.jclx(+) and tab1.xtid = tab2.xtid(+) )\n" + 
						"group by xtid,xtmc, jcdl) group by xtid,xtmc order by xtmc )\n" + 
						"select * from tabs ) table2, mw_app.cmst_kq_xt xt\n" + 
						"where xt.obj_id = table2.xtid(+) and xt.obj_id = table1.xtid(+) "+ssxt+"\n" + 
						"order by XT.XTMC)\n" + 
						"UNION ALL SELECT\n" + 
						"ssxtid, SSxtmc,qxjcljrs qxjcljrs,qxjclzs qxjclzs,\n" + 
						"dxjcljrs dxjcljrs,dxjclzs dxjclzs,gtjcljrs gtjcljrs,gtjclzs gtjclzs,\n" + 
						"jyzjcljrs jyzjcljrs,jyzjclzs jyzjclzs,  jrshj jrshj,\n" + 
						"zshj zshj,SSJRL,\n" + 
						"BYDKLJRS BYDKLJRS,BYDKLZS BYDKLZS,\n" + 
						"DLGISLJRS DLGISLJRS,DLGISLZS DLGISLZS, DRXLJRS DRXLJRS,DRXLZS DRXLZS,\n" + 
						"JSYHWLJRS JSYHWLJRS,JSYHWLZS JSYHWLZS,BDjrshj BDjrshj,BDzshj BDzshj,\n" + 
						"BDSSJRL\n" + 
						"FROM\n" + 
						"(select NVL(QXJCLJRS,0) QXJCLJRS,NVL(QXJCLZS,0) QXJCLZS,NVL(DXJCLJRS,0) DXJCLJRS,NVL(DXJCLZS,0) DXJCLZS,\n" + 
						"NVL(GTJCLJRS,0) GTJCLJRS,NVL(GTJCLZS,0) GTJCLZS,NVL(JYZJCLJRS,0) JYZJCLJRS, NVL(JYZJCLZS,0) JYZJCLZS,\n" + 
						"NVL(JRSHJ,0) JRSHJ,NVL(ZSHJ,0) ZSHJ,NVL(SSJRL,0) SSJRL, NVL(table2.BYDKLJRS,0) BYDKLJRS,\n" + 
						"NVL(table2.BYDKLZS,0) BYDKLZS, NVL(table2.dlgisljrs,0) dlgisljrs,NVL(table2.dlgislzs,0) dlgislzs,\n" + 
						"NVL(table2.drxljrs,0) drxljrs,NVL(table2.drxlzs,0) drxlzs, NVL(table2.jsyhwljrs,0) jsyhwljrs,NVL(table2.jsyhwlzs,0) jsyhwlzs,\n" + 
						"NVL(table2.bdjrshj,0) bdjrshj,NVL(table2.bdzshj,0) bdzshj,NVL(table2.bdssjrl,0) bdssjrl,\n" + 
						"xt.obj_id ssxtid,xt.xtmc ssxtmc from (with tab as (select xtid,xtmc,max(qxjcljrs) qxjcljrs,\n" + 
						"max(qxjclzs) qxjclzs,max(dxjcljrs) dxjcljrs,\n" + 
						"max(dxjclzs) dxjclzs,max(gtjcljrs) gtjcljrs, max(gtjclzs) gtjclzs,\n" + 
						"max(jyzjcljrs) jyzjcljrs, max(jyzjclzs) jyzjclzs,\n" + 
						"max(qxjcljrs)+max(dxjcljrs)+max(gtjcljrs)+max(jyzjcljrs) jrshj,\n" + 
						"max(qxjclzs)+max(dxjclzs)+max(gtjclzs)+max(jyzjclzs) zshj,\n" + 
						"(case when (max(qxjclzs)+max(dxjclzs)+max(gtjclzs)+max(jyzjclzs)) = 0 then '-' else\n" + 
						"to_char(((max(qxjcljrs)+max(dxjcljrs)+max(gtjcljrs)+max(jyzjcljrs)) / (max(qxjclzs)+\n" + 
						"max(dxjclzs)+max(gtjclzs)+max(jyzjclzs)) * 100),'990.99')||'%' end) SSJRL  from\n" + 
						"(select xtid,xtmc,(case jcdl when '导线监测类' then sum(zzzs) else 0 end) dxjclzs,\n" + 
						"(case jcdl when '杆塔监测类' then sum(zzzs) else 0 end) gtjclzs,(case jcdl when '绝缘子监测类' then sum(zzzs) else 0 end) jyzjclzs,\n" + 
						"(case jcdl when '气象环境监测类' then sum(zzzs) else 0 end) qxjclzs,\n" + 
						"  (case jcdl when '导线监测类' then sum(ssjrzzs) else 0 end) dxjcljrs, (case jcdl when '杆塔监测类' then sum(ssjrzzs) else 0 end)\n" + 
						"gtjcljrs,  (case jcdl when '绝缘子监测类' then sum(ssjrzzs) else 0 end) jyzjcljrs,(case jcdl when '气象环境监测类'\n" + 
						"then sum(ssjrzzs) else 0 end) qxjcljrs from (select tab1.*, decode(tab2.ssjrzzs, null, 0, tab2.ssjrzzs) ssjrzzs\n" + 
						"from (select xtid,xtmc,w.jczy,w.zzzs,w.jcdl from\n" + 
						"(SELECT DEVICEMONITYPE.MONITORINGTYPE jczy,count(distinct(LINEDEVICE.DEVICECODE)) zzzs,xtid,xtmc,\n" + 
						"(case DEVICEMONITYPE.MONITORINGTYPE\n" + 
						"when '012001' then '杆塔监测类' when '013001' then '导线监测类' when '013002' then '导线监测类'\n" + 
						"when '013003' then '导线监测类' when '013004' then '导线监测类' when '013005' then '导线监测类'\n" + 
						"when '013006' then '导线监测类' when '014001' then '绝缘子监测类' when '018001' then '气象环境监测类'\n" + 
						"when '018002' then '气象环境监测类' else '' end) jcdl\n" + 
						"FROM (select * from mw_app.cmsv_linedevicef\n" + 
						"where   LINKEDEQUIPMENTDY IS NOT NULL AND DEPMC IS NOT NULL )  LINEDEVICE,\n" + 
						"MW_APP.CMST_DEVICEMONITYPE DEVICEMONITYPE\n" + 
						"WHERE LINEDEVICE.DEVICECODE = DEVICEMONITYPE.LINKEDDEVICE\n" + 
						"group by DEVICEMONITYPE.MONITORINGTYPE,xtid,xtmc) w\n" + 
						") tab1, (select monitor.monitoringtype jclx,count(distinct LINEDEVICE.DEVICECODE) ssjrzzs,xtid\n" + 
						"from (select * from mw_app.cmsv_linedevicef where  LINKEDEQUIPMENTDY IS NOT NULL AND DEPMC IS NOT NULL )     LINEDEVICE,\n" + 
						"mw_app.cmst_devicemonitype monitor WHERE LINEDEVICE.DEVICECODE = monitor.linkeddevice\n" + 
						"and (monitor.linkeddevice, monitor.monitoringtype) in\n" + 
						"(select zzbm, jclx from mw_app.cmsv_deviceused_info where sfss = 'T')\n" + 
						"group by  monitor.monitoringtype,xtid) tab2\n" + 
						"where tab1.jczy = tab2.jclx(+) and tab1.xtid = tab2.xtid(+))group by xtid,xtmc, jcdl)\n" + 
						"group by xtid,xtmc order by xtmc ) select * from tab) table1,\n" + 
						"(with tabs as (select xtid,xtmc,max(BYDKLJRS) BYDKLJRS,max(BYDKLZS) BYDKLZS,max(DLGISLJRS) DLGISLJRS,\n" + 
						"max(DLGISLZS) DLGISLZS,max(DRXLJRS) DRXLJRS,max(DRXLZS) DRXLZS,  max(JSYHWLJRS) JSYHWLJRS,\n" + 
						"max(JSYHWLZS) JSYHWLZS,max(BYDKLJRS)+max(JSYHWLJRS)+max(DLGISLJRS)+max(DRXLJRS) BDjrshj,\n" + 
						"max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(DRXLZS) BDzshj, \n" + 
						"(case when (max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(DRXLZS)) = 0 then '-' else\n" + 
						"to_char(((max(BYDKLJRS)+max(JSYHWLJRS)+max(DLGISLJRS)+max(DRXLJRS)) / (max(BYDKLZS)+max(JSYHWLZS)\n" + 
						"+max(DLGISLZS)+max(DRXLZS)) * 100),'990.99')||'%' end) BDSSJRL\n" + 
						"from (select xtid,xtmc,(case jcdl when '变压器/电抗器类' then sum(zzzs) else 0 end) BYDKLZS,\n" + 
						"(case jcdl when '断路器/GIS类' then sum(zzzs) else 0 end) DLGISLZS,(case jcdl when '电容型设备类' then sum(zzzs)else 0 end) DRXLZS,\n" + 
						"(case jcdl when '金属氧化物避雷器类' then sum(zzzs)else 0 end) JSYHWLZS, \n" + 
						"(case jcdl when '变压器/电抗器类' then sum(ssjrzzs)else 0 end) BYDKLJRS,(case jcdl when '断路器/GIS类'then sum(ssjrzzs)else 0 end)\n" + 
						"DLGISLJRS,(case jcdl when '电容型设备类'then sum(ssjrzzs)else 0 end) DRXLJRS,\n" + 
						"(case jcdl when '金属氧化物避雷器类'then sum(ssjrzzs)else 0 end) JSYHWLJRS\n" + 
						"from (  select tab1.*, decode(tab2.ssjrzzs,null,0,tab2.ssjrzzs) ssjrzzs from\n" + 
						"(  select xtid,xtmc,w.jczy,w.zzzs,w.jcdl from\n" + 
						"(SELECT DEVICEMONITYPE.MONITORINGTYPE jczy,count(distinct(LINEDEVICE.DEVICECODE)) zzzs,xtid,xtmc,\n" + 
						"(case DEVICEMONITYPE.MONITORINGTYPE when '021001'then '变压器/电抗器类'when'021002'then'变压器/电抗器类'\n" + 
						"when'021003'then'变压器/电抗器类'when'021004'then'变压器/电抗器类'when'021005'then'变压器/电抗器类'\n" + 
						"when'021007'then'变压器/电抗器类'when'022001'then'电容型设备类'when'023001'then'金属氧化物避雷器类'\n" + 
						"when'024001'then  '断路器/GIS类'when'024002'then'断路器/GIS类'when'024004'then'断路器/GIS类'\n" + 
						"when'024005'then'断路器/GIS类'when'024006'then'断路器/GIS类'when '024003'then'断路器/GIS类' else '' end) jcdl\n" + 
						"FROM (select * from mw_app.cmsv_transfdevicef where LINKEDEQUIPMENTDY IS NOT NULL ) LINEDEVICE\n" + 
						",MW_APP.CMST_DEVICEMONITYPE DEVICEMONITYPE  WHERE LINEDEVICE.DEVICECODE = DEVICEMONITYPE.LINKEDDEVICE\n" + 
						"group by  DEVICEMONITYPE.MONITORINGTYPE,xtid,xtmc) w ) tab1 ,(  select xtid,monitor.monitoringtype jclx,\n" + 
						"count(LINEDEVICE.DEVICECODE) ssjrzzs from (select *  from mw_app.cmsv_transfdevicef  where LINKEDEQUIPMENTDY IS NOT NULL  ) LINEDEVICE,\n" + 
						"mw_app.cmst_devicemonitype monitor  WHERE LINEDEVICE.DEVICECODE = monitor.linkeddevice\n" + 
						"and (monitor.linkeddevice) in (select zzbm from mw_app.cmsv_deviceused_info where sfss = 'T')\n" + 
						"group by  monitor.monitoringtype,xtid) tab2 where tab1.jczy = tab2.jclx(+) and tab1.xtid = tab2.xtid(+) )\n" + 
						"group by xtid,xtmc, jcdl) group by xtid,xtmc order by xtmc )\n" + 
						"select * from tabs ) table2, mw_app.cmst_kq_xt xt\n" + 
						"where xt.obj_id = table2.xtid(+) and xt.obj_id = table1.xtid(+)"+ssxt+"  order by XT.XTMC)";

						
			   result = hibernateDao_ztjc.executeSqlQuery(querySql);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
			   qro.addDicItems(wrapDictList());
			  
			   
		   } catch (Exception e) {
			   logger.info("输电装置查询管理，加载设备列表出错", e);
			   e.printStackTrace();
		   }
		   return qro;
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
	   private String getsdFilter(RequestCondition params,String zy)
	   {
		   String filter = "";
		   String[] qc = params.getFilter().toString().split("&");
		   for(int i = 0; i < qc.length; i++) {
			   if("cx".equals(zy))
			   {
				   if("ssxt".equals(qc[i].split("=")[0])) {
					 //所属系统
					   String[] dept = qc[i].split("=")[1].split(",");
						  String deptString = "";
						   for (int j = 0; j < dept.length; j++) {
							   deptString = deptString+"'"+dept[j]+"'";
							if (j!=dept.length-1) {
								deptString = deptString+",";
							}
						}
					 filter += " AND xtid in ("+deptString+")";
				   }
			   }
			   if("linkeddepws".equals(qc[i].split("=")[0])) {
					 //所属地市条件
					   String[] dept = qc[i].split("=")[1].split(",");
						  String deptString = "";
						   for (int j = 0; j < dept.length; j++) {
							   deptString = deptString+"'"+dept[j]+"'";
							if (j!=dept.length-1) {
								deptString = deptString+",";
							}
						}
					 filter += " AND linkeddepws in ("+deptString+")";
				   }
			   else if("linkedlinename".equals(qc[i].split("=")[0])) {
				 //线路名称条件
				   filter += " AND linkedlinename like '%" + qc[i].split("=")[1] + "%'";
			   }
			   else if("dydj".equals(qc[i].split("=")[0])) {
				   //电压等级
				   String[] dydj = qc[i].split("=")[1].split(",");
					  String dydjString = "";
					   for (int j = 0; j < dydj.length; j++) {
						   dydjString = dydjString+"'"+dydj[j]+"'";
						if (j!=dydj.length-1) {
							dydjString = dydjString+",";
						}
					}
					   filter += " AND linkedequipmentdy in ("+dydjString+")";
			   } else if("monitoringtype".equals(qc[i].split("=")[0]))
			   {
				   //监测类型
				   String[] jclx = qc[i].split("=")[1].split(",");
				  String jclxString = "";
				   for (int j = 0; j < jclx.length; j++) {
					jclxString = jclxString+"'"+jclx[j]+"'";
					if (j!=jclx.length-1) {
						jclxString = jclxString+",";
					}
				}
				   filter += " AND MONITORINGTYPE in ("+jclxString+")";
			   }
			   else if("srundate".equals(qc[i].split("=")[0]))
			   {
				   filter += " and to_char(rundate,'yyyy-mm-dd') >= '" + qc[i].split("=")[1] + "'";
			   } else if("erundate".equals(qc[i].split("=")[0]))
			   {
				   filter += " and to_char(rundate,'yyyy-mm-dd') <= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("jclx".equals(qc[i].split("=")[0]))
			   {
				   
				   if("018".equals(qc[i].split("=")[1]))
				   {
					   filter +=  " AND MONITORINGTYPE in ('018001','018002')";
				   }
				   
				   else
					   filter += " AND MONITORINGTYPE like '" + qc[i].split("=")[1] + "%'";
			   }
			   
			   else if("sfss".equals(qc[i].split("=")[0]))
			   {
				   filter += " and sfss= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("ssxtid".equals(qc[i].split("=")[0]))
			   {
				   if(!"all".equals(qc[i].split("=")[1]))
					   filter += " and xtid= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("ssxl".equals(qc[i].split("=")[0]))
			   {
				  
					   filter += " and linkedline= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("jczy".equals(qc[i].split("=")[0]))
			   {
				  
					   filter += " and ssjr.monitoringtype= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("sfss".equals(qc[i].split("=")[0]))
			   {
				  
					   filter += " and ssjr.sfss= '" + qc[i].split("=")[1] + "'";
			   }
		   }
		   return filter;
	   }
	   //获取所属系统ID
	   private String getssxt(RequestCondition params)
	   {
		   String filter = "";
		   String[] qc = params.getFilter().toString().split("&");
		   for(int i = 0; i < qc.length; i++) {

				   if("ssxt".equals(qc[i].split("=")[0])) {
			   
					 //所属系统
					   String[] dept = qc[i].split("=")[1].split(",");
						  String deptString = "";
						   for (int j = 0; j < dept.length; j++) {
							   deptString = deptString+"'"+dept[j]+"'";
							if (j!=dept.length-1) {
								deptString = deptString+",";
							}
						}
					 filter += " AND xt.obj_id in ("+deptString+")";
				   }
				   
				   else if("xtid".equals(qc[i].split("=")[0])) {
					   
					   filter += " and ssxt= '" + qc[i].split("=")[1] + "'";
					   }
		
		   }
		   return filter;
	   }
	   
	   
	 //获取变电查询条件
	   private String getbdFilter(RequestCondition params,String zy)
	   {
		   String filter = "";
		   String[] qc = params.getFilter().toString().split("&");
		   for(int i = 0; i < qc.length; i++) {
			   if("cx".equals(zy))
			   {
				   if("ssxt".equals(qc[i].split("=")[0])) {
			   
					 //所属系统
					   String[] dept = qc[i].split("=")[1].split(",");
						  String deptString = "";
						   for (int j = 0; j < dept.length; j++) {
							   deptString = deptString+"'"+dept[j]+"'";
							if (j!=dept.length-1) {
								deptString = deptString+",";
							}
						}
					 filter += " AND xtid in ("+deptString+")";
				   }
			   }
			   if("linkeddepws".equals(qc[i].split("=")[0])) {
					 //所属地市条件
					   String[] dept = qc[i].split("=")[1].split(",");
						  String deptString = "";
						   for (int j = 0; j < dept.length; j++) {
							   deptString = deptString+"'"+dept[j]+"'";
							if (j!=dept.length-1) {
								deptString = deptString+",";
							}
						}
					 filter += " AND linkeddepws in ("+deptString+")";
				   }
			   else if("linkedstationname".equals(qc[i].split("=")[0])) {
				 //线路名称条件
				   filter += " AND linkedstationname like '%" + qc[i].split("=")[1] + "%'";
			   }
			   else if("dydj".equals(qc[i].split("=")[0])) {
				   //电压等级
				   String[] dydj = qc[i].split("=")[1].split(",");
					  String dydjString = "";
					   for (int j = 0; j < dydj.length; j++) {
						   dydjString = dydjString+"'"+dydj[j]+"'";
						if (j!=dydj.length-1) {
							dydjString = dydjString+",";
						}
					}
					   filter += " AND linkedequipmentdy in ("+dydjString+")";
			   } else if("monitoringtype".equals(qc[i].split("=")[0]))
			   {
				   //监测类型
				   String[] jclx = qc[i].split("=")[1].split(",");
				  String jclxString = "";
				   for (int j = 0; j < jclx.length; j++) {
					jclxString = jclxString+"'"+jclx[j]+"'";
					if (j!=jclx.length-1) {
						jclxString = jclxString+",";
					}
				}
				   filter += " AND monitoringtypes in ("+jclxString+")";
			   }
			   else if("srundate".equals(qc[i].split("=")[0]))
			   {
				   filter += " and to_char(rundate,'yyyy-mm-dd') >= '" + qc[i].split("=")[1] + "'";
			   } else if("erundate".equals(qc[i].split("=")[0]))
			   {
				   filter += " and to_char(rundate,'yyyy-mm-dd') <= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("jclx".equals(qc[i].split("=")[0]))
			   {
					   filter += " AND MONITORINGTYPEs like '" + qc[i].split("=")[1] + "%'";
			   }
			   else if("sfss".equals(qc[i].split("=")[0]))
			   {
				   filter += " and sfss= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("ssxtid".equals(qc[i].split("=")[0]))
			   {
				   if(!"all".equals(qc[i].split("=")[1]))
					   filter += " and xtid= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("ssbdz".equals(qc[i].split("=")[0]))
			   {
					   filter += " and bdz.global_id= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("jczy".equals(qc[i].split("=")[0]))
			   {
					   filter += " and a.monitoringtypes= '" + qc[i].split("=")[1] + "'";
			   }
			   else if("ISRT".equals(qc[i].split("=")[0]))
			   {
					   filter += " and ssjr.sfss= '" + qc[i].split("=")[1] + "'";
			   }
			   
			   
		   }
		   return filter;
	   }
	   
	   private List<DicItems> wrapDictList() {
			List<DicItems> dicts = new ArrayList<DicItems>();
			 
//	        
			String jclxsql = 
					"select typename||'(输电)' mc,typecode dm from mw_app.cmst_monitoringtype  where typecode like '01%' and typecode !='018003' union\n" +
					"select typename||'(变电)' mc,typecode dm from mw_app.cmst_monitoringtype  where typecode like '02%' and typecode !='021007' and typecode != '025001' and typecode != '026001' order by dm";

			dicts.add(translateFromDBT("monitoringtype",jclxsql));
			
			
			String deptsqlString = "SELECT wsmc,wsid FROM MW_APP.CMST_ZB_COMM_WSPZ order by zdypx";
		     dicts.add(translateFromDBT("linkeddepws",deptsqlString));
		     
		     String ssxtsqlString = "select XTMC,CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID from mw_app.cmst_kq_xt ORDER BY XTMC";
		     dicts.add(translateFromDBT("ssxt",ssxtsqlString));	       
	        
	        dicts.add(translateFromFile("dydj", "DYDJ"));
	        
	        return dicts;
		}
		
		// 从数据库中查询字典
		private DicItems translateFromDB(String fieldName, String poName,String keyField, String valueField,String sqlConditon) {
			List<Map<String, String>> list = dataDictionaryBizC.translateFromDB(
					poName, "value", "text", keyField, valueField,sqlConditon);
			
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
	    
	    /**
		    * 主要系统输电
		    * @param sdxxtj
		    * @return
		    */
		   public QueryResultObject loadxtsd(RequestCondition params) {
			   
			   QueryResultObject qro = new QueryResultObject();
			  
			   List result = new ArrayList();
			   List count = new ArrayList();
			   String cols = "";
			 
			   try {
				   cols = "XLID,XLMC,ZZZS,SSJRZZS,ZS012001,ZS013001,ZS013002,ZS013003,ZS013004,ZS013005,ZS014001,ZS018001,ZS018002,ZS013006";
				
				   String ssxt = getssxt(params);
				   String querySql = 
						   "select temp.GLOBAL_ID XLID,temp.XLMC,\n" +
								   "(ZS012001 + ZS013001 + ZS013002 + ZS013003  + ZS013004 +\n" + 
								   "ZS013005 + ZS014001 + ZS018001 + ZS018002 + ZS013006) ZZZS,\n" + 
								   "temp.SSJRZZS,temp.ZS012001,temp.ZS013001,temp.ZS013002,\n" + 
								   "temp.ZS013003,temp.ZS013004,temp.ZS013005,\n" + 
								   "temp.ZS014001,temp.ZS018001,temp.ZS018002,temp.ZS013006\n" + 
								   "from (select global_id,\n" + 
								   "xlmc,sum(ssjrzz) ssjrzzs,sum(gtqx) ZS012001,sum(dxhc) ZS013001,\n" + 
								   "sum(dxwd) ZS013002,sum(wfzd) ZS013003,\n" + 
								   "sum(fpjc) ZS013004,sum(fbjc) ZS013005,sum(jyzwh) ZS014001,\n" + 
								   "sum(wqxjc) ZS018001,sum(txjc) ZS018002,sum(xlwd) ZS013006\n" + 
								   "from (select xl.sbbm global_id,xl.xlmc,\n" + 
								   "decode(ssjr.SFSS, 'T', count(monitoringtype), 0) ssjrzz,\n" + 
								   "(case monitoringtype when '012001' then count(monitoringtype)\n" + 
								   "else 0 end) gtqx, (case monitoringtype when '013001' then\n" + 
								   "count(monitoringtype) else 0 end) dxhc,\n" + 
								   "(case monitoringtype when '013002' then count(monitoringtype)\n" + 
								   "else 0 end) dxwd,(case monitoringtype when '013003' then\n" + 
								   "count(monitoringtype) else  0 end) wfzd,\n" + 
								   "(case monitoringtype when '013004' then count(monitoringtype)\n" + 
								   "else 0  end) fpjc, (case monitoringtype when '013005' then\n" + 
								   "count(monitoringtype) else  0 end) fbjc,\n" + 
								   "(case monitoringtype when '014001' then count(monitoringtype)\n" + 
								   "else 0 end) jyzwh,(case monitoringtype\n" + 
								   "when '018001' then count(monitoringtype) else 0 end) wqxjc,\n" + 
								   "(case monitoringtype when '018002' then count(monitoringtype)\n" + 
								   "else 0 end) txjc, (case ssjr.monitoringtype\n" + 
								   "when '013006' then count(ssjr.monitoringtype) else 0 end) xlwd\n" + 
								   "from (select distinct devicecode,linkedline\n" + 
								   "from mw_app.cmsv_linedevicef l, mw_app.cmst_devicemonitype d\n" + 
								   "where  l.devicecode = d.linkeddevice(+) and LINKEDEQUIPMENTDY IS NOT NULL\n" + 
								   "and d.monitoringtype in ('012001','013001','013004','013005','013006','014001','018001','018002')\n" + 
								   "and l.linkedprovicedept  is not null) dev,\n" + 
								   "(select monitor.linkeddevice,\n" + 
								   "monitor.monitoringtype,info.SFSS\n" + 
								   "from mw_app.cmst_devicemonitype  monitor,\n" + 
								   "mw_app.cmsv_deviceused_info info\n" + 
								   "where monitor.linkeddevice = info.ZZBM(+)\n" + 
								   "and monitor.monitoringtype = info.JCLX(+)) ssjr,\n" + 
								   "mw_app.CMST_SB_ZWYC_XL xl\n" + 
								   "where dev.devicecode = ssjr.linkeddevice(+)\n" + 
								   "and xl.sbbm = dev.linkedline(+)  "+ssxt+"\n" + 
								     
								   " group by xl.sbbm, xl.xlmc,ssjr.monitoringtype,ssjr.SFSS)\n" + 
								   "group by global_id, xlmc) temp order by zzzs desc";

				   result = hibernateDao_ztjc.executeSqlQuery(querySql);
				   result = transToColumns(result,cols);
				   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
				   
				   qro.setItems(result);
				   qro.setItemCount(((Number) (count.get(0))).intValue());
				   qro.addDicItems(wrapDictList());
				  
				   
			   } catch (Exception e) {
				   logger.info("输电装置查询管理，加载设备列表出错", e);
				   e.printStackTrace();
			   }
			   return qro;
		   }
	    
		   /**
		    * 主要系统输电cx
		    * @param sdxxtj
		    * @return
		    */
		   public QueryResultObject loadcx(RequestCondition params) {
			   
			   QueryResultObject qro = new QueryResultObject();
			  
			   List result = new ArrayList();
			   List count = new ArrayList();
			   String querySql = "";
			   String cols = "";
			   String zy = "cx";
			   String lx = "";
			   String[] qc = params.getFilter().toString().split("&");
			   for(int i = 0; i < qc.length; i++) {
				 if("lx".equals(qc[i].split("=")[0])) {
						   lx += qc[i].split("=")[1];
						   }
			
			   }
			   try {
				   String ssxt = getssxt(params);
				   String filter = getsdFilter(params,zy);
				   String bdfilter = getbdFilter(params,zy);
				   if("sd".equals(lx)||"".equals(lx))
				   {
					   cols = "PROVINCE_ID,PROVINCE_NAME,LINKEDLINE,LINKEDLINENAME,DEVICENAME,DEVICECATEGORY_DISPLAY,MONITORINGTYPE,LINKEDPOLE,LINKEDPOLENAME,LINKEDEQUIPMENTDY,DEVICECODE,DEVICEVOLTAGE,DEVICECATEGORY,DEVICEMODEL,MANUFACTURER,RUNDATE,ISRT,LOOKUP";
				  
				   
				  querySql = 
							"select distinct deps.wsid PROVINCE_ID,deps.wsmc PROVINCE_NAME,dev.linkedline,\n" +
							"dev.linkedlinename,dev.devicename,\n" + 
							"(select m.typename from mw_app.cmst_monitoringtype m\n" + 
							"where m.typecode = ssjr.monitoringtype) DEVICECATEGORY_DISPLAY,\n" + 
							"ssjr.monitoringtype, dev.linkedpole,dev.linkedpolename,\n" + 
							"dev.linkedequipmentdy,dev.devicecode,dev.DEVICEVOLTAGE,dev.DEVICECATEGORY,\n" + 
							"dev.devicemodel,dev.manufacturer, dev.rundate,DECODE(ssjr.SFSS,null,'否','T','是','F','否') ISRT,\n" + 
							"'查看' LOOKUP from (select *  from mw_app.cmsv_linedevicef l, mw_app.cmst_devicemonitype d\n" + 
							"where l.devicecode = d.linkeddevice(+) and LINKEDEQUIPMENTDY IS NOT NULL\n" + 
							"and d.monitoringtype in ('012001','013001','013004','013005','013006','014001','018002','018001')\n" + 
							") dev,(select monitor.linkeddevice, monitor.monitoringtype, info.SFSS\n" + 
							"from mw_app.cmst_devicemonitype  monitor,mw_app.cmsv_deviceused_info info\n" + 
							"where monitor.linkeddevice = info.ZZBM(+) and monitor.monitoringtype = info.JCLX(+)  and monitor.monitoringtype !='018003') ssjr,\n" + 
							"mw_app.CMST_SB_ZWYC_XL xl, mw_app.CMST_ZB_COMM_WSPZ deps\n" + 
							"where dev.devicecode = ssjr.linkeddevice and dev.linkedprovicedept = deps.wsid(+)\n" + 
							"and xl.sbbm = dev.linkedline AND INSTRB(dev.monitoringtypes, ssjr.monitoringtype) != 0\n" + 
							"and  deps.wsid  is not null "+ssxt+filter+"\n";
							
				   }
				   else if("bd".equals(lx))
				   {
					   cols = "PROVINCE_NAME,PROVINCE_ID,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,DEVICENAME,DEVICECODE,DEVICECATEGORY_DISPLAY,MONITORINGTYPE,ISRT,LOOKUP,DEVICEVOLTAGE,DEVICECATEGORY,DEVICEMODEL,MANUFACTURER,RUNDATE";
				  
				   
				   querySql = 
							"select distinct deps.wsmc province_name, deps.wsid province_id,\n" +
							"a.linkedstationname,a.linkedequipmentname,a.DEVICENAME,\n" + 
							"a.DEVICECODE,(select typename from mw_app.cmst_monitoringtype m\n" + 
							"where m.typecode = a.monitoringtypes) DEVICECATEGORY_DISPLAY,\n" + 
							"a.monitoringtypes monitoringtype,DECODE(info.SFSS,null,'否','T','是','F','否') ISRT,\n" + 
							"'查看' LOOKUP,a.DEVICEVOLTAGE,a.DEVICECATEGORY,a.Devicemodel,a.MANUFACTURER,\n" + 
							"a.RUNDATE from (select * from mw_app.cmsv_transfdevicef where LINKEDEQUIPMENTDY IS NOT NULL\n" + 
							"and monitoringtypes in ('021001','021002','021003','021004','021005','021007','022001','023001','024001','024002','024004','024005','024006','024003')\n" + 
							") a,mw_app.cmsv_deviceused_info info,mw_app.CMST_SB_ZNYC_DZ bdz,\n" + 
							"mw_app.CMST_ZB_COMM_WSPZ deps where bdz.sbbm = a.linkedstation(+)\n" + 
							"and a.linkedprovicedept = deps.wsid(+) and a.devicecode = info.ZZBM(+)  and  deps.province_id  is not null "+ssxt+bdfilter;				
				   }
				   result = hibernateDao_ztjc.executeSqlQuery(querySql);
				   result = transToColumns(result,cols);
				   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
				   
				   qro.setItems(result);
				   qro.setItemCount(((Number) (count.get(0))).intValue());
				   qro.addDicItems(wrapDictList());
				  
				   
			   } catch (Exception e) {
				   logger.info("输电装置查询管理，加载设备列表出错", e);
				   e.printStackTrace();
			   }
			   return qro;
		   }
		   
		   /**
		    * 主要系统变电
		    * @param sdxxtj
		    * @return
		    */
		   public QueryResultObject loadxtbd(RequestCondition params) {
			   
			   QueryResultObject qro = new QueryResultObject();
			  
			   List result = new ArrayList();
			   List count = new ArrayList();
			   String cols = "";
			 
			   try {
				   cols = "BDZID,BDZMC,ZZZS,SSJRZZS,ZZZS021001,ZZZS021002,ZZZS021003,ZZZS021004,ZZZS021005,ZZZS022001,ZZZS023001,ZZZS024001,ZZZS024002,ZZZS024003,ZZZS024004,ZZZS024005,ZZZS024006";
				
				   String ssxt = getssxt(params);
				   String querySql = 
								"select temp.BDZID,temp.BDZMC,\n" +
								"(zzzs021001 + zzzs021002 + zzzs021003 + zzzs021004 + zzzs021005 +\n" + 
								"zzzs022001 + zzzs023001 + zzzs024001 + zzzs024002 + zzzs024003 +\n" + 
								"zzzs024004 + zzzs024005 + zzzs024006  ) ZZZS,\n" + 
								"BDSSJRZZS SSJRZZS,temp.ZZZS021001,temp.ZZZS021002,\n" + 
								"temp.ZZZS021003,temp.ZZZS021004,temp.ZZZS021005,temp.ZZZS022001,\n" + 
								"temp.ZZZS023001,temp.ZZZS024001,temp.ZZZS024002,temp.ZZZS024003,\n" + 
								"temp.ZZZS024004,temp.ZZZS024005,temp.ZZZS024006\n" + 
								"from (select bdzid,bdzmc,\n" + 
								"sum(bdssjrzzs) bdssjrzzs,sum(zzzs021001) zzzs021001,\n" + 
								"sum(zzzs021002) zzzs021002,sum(zzzs021003) zzzs021003,\n" + 
								"sum(zzzs021004) zzzs021004,sum(zzzs021005) zzzs021005,\n" + 
								"sum(zzzs022001) zzzs022001,sum(zzzs023001) zzzs023001,\n" + 
								"sum(zzzs024001) zzzs024001,sum(zzzs024002) zzzs024002,\n" + 
								"sum(zzzs024003) zzzs024003,sum(zzzs024004) zzzs024004,\n" + 
								"sum(zzzs024005) zzzs024005,sum(zzzs024006) zzzs024006\n" + 
								"from (select T.bdzid,T.bdzmc,T.monitoringtypes,T.bdssjrzzs,\n" + 
								"(decode(T.monitoringtypes, '021001', zzzs, 0)) zzzs021001,\n" + 
								"(decode(T.monitoringtypes, '021002', zzzs, 0)) zzzs021002,\n" + 
								"(decode(T.monitoringtypes, '021003', zzzs, 0)) zzzs021003,\n" + 
								"(decode(T.monitoringtypes, '021004', zzzs, 0)) zzzs021004,\n" + 
								"(decode(T.monitoringtypes, '021005', zzzs, 0)) zzzs021005,\n" + 
								"(decode(T.monitoringtypes, '022001', zzzs, 0)) zzzs022001,\n" + 
								"(decode(T.monitoringtypes, '023001', zzzs, 0)) zzzs023001,\n" + 
								"(decode(T.monitoringtypes, '024001', zzzs, 0)) zzzs024001,\n" + 
								"(decode(T.monitoringtypes, '024002', zzzs, 0)) zzzs024002,\n" + 
								"(decode(T.monitoringtypes, '024003', zzzs, 0)) zzzs024003,\n" + 
								"(decode(T.monitoringtypes, '024004', zzzs, 0)) zzzs024004,\n" + 
								"(decode(T.monitoringtypes, '024005', zzzs, 0)) zzzs024005,\n" + 
								"(decode(T.monitoringtypes, '024006', zzzs, 0)) zzzs024006\n" + 
								"from (select count(distinct a.devicecode) zzzs,\n" + 
								"count(distinct(decode(a.linkedequipmentdy,null,null,\n" + 
								"decode(info.SFSS,'T',a.devicecode,null)))) as bdssjrzzs,\n" + 
								"sbbm bdzid,bdzmc bdzmc,a.monitoringtypes\n" + 
								"from (select *from mw_app.cmsv_transfdevicef t\n" + 
								"where LINKEDEQUIPMENTDY IS NOT NULL\n" + 
								"and monitoringtypes in ('021001','021002','021003','021004','021005','021007','022001','023001','024001','024002','024004','024005','024006','024003')\n" + 
								"and linkedprovicedept  is not null) a,mw_app.CMST_SB_ZNYC_DZ bdz,mw_app.cmsv_deviceused_info info\n" + 
								"where a.devicecode = info.ZZBM(+)   "+ssxt+"\n" + 
								"and a.LinkedStation(+) = bdz.sbbm group by sbbm, bdzmc, a.monitoringtypes) T) H\n" + 
								"group by bdzid, bdzmc) temp order by zzzs desc";


				   result = hibernateDao_ztjc.executeSqlQuery(querySql);
				   result = transToColumns(result,cols);
				   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
				   
				   qro.setItems(result);
				   qro.setItemCount(((Number) (count.get(0))).intValue());
				   qro.addDicItems(wrapDictList());
				  
				   
			   } catch (Exception e) {
				   logger.info("输电装置查询管理，加载设备列表出错", e);
				   e.printStackTrace();
			   }
			   return qro;
		   }
		   
		    
}

