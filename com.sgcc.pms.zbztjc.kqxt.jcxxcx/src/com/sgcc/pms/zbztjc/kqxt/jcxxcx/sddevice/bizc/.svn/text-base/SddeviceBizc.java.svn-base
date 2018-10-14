package com.sgcc.pms.zbztjc.kqxt.jcxxcx.sddevice.bizc;

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
public class SddeviceBizc implements ISddeviceBizc{


	private final Log logger = LogFactory.getLog(SddeviceBizc.class);
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
		   String cols = "XTMC,LINKEDDEP,DEPMC,LINKEDDEPWS,WSDEPMC,SFSS,LINKEDEQUIPMENTDY,DEVICEVOLTAGE,DYDJGDJB,LINKEDPOLE,LINKEDLINE,LINKEDLINENAME,LINKEDPOLENAME,DEVICECATEGORY,DEVICECATEGORYDM,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER,REMARKS,JCXX,ISACTIVATED";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			   
			   String querySql = 
						"SELECT xtmc,LINKEDDEP,DEPMC,LINKEDDEPWS,WSDEPMC,SFSS,LINKEDEQUIPMENTDY,\n" +
						"DEVICEVOLTAGE,DYDJGDJB,LINKEDPOLE,LINKEDLINE,LINKEDLINENAME,LINKEDPOLENAME,DEVICECATEGORY,\n" + 
						"DEVICECATEGORYDM,DEVICECODE,DEVICENAME, monitoringtype MONITORINGTYPES,typename DEVICECATEGORY_DISPLAY,DEVICEMODEL,\n" + 
						"MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER,t.REMARKS,JCXX,ISACTIVATED FROM MW_APP.CMSV_LINEDEVICEf t ,MW_APP.CMST_DEVICEMONITYPE d,\n" + 
						"mw_app.cmst_monitoringtype m  WHERE t.DEVICECODE = d.LINKEDDEVICE and d.monitoringtype = m.typecode\n" + 
						"and t.WSDEPMC is not null and t.DEVICEVOLTAGE is not null AND LINKEDEQUIPMENTDY IS NOT NULL";

			   
			   if(null!=params.getFilter())
			   {
				   String[] qc = params.getFilter().toString().split("&");
				   for(int i = 0; i < qc.length; i++) {
					   
					   if("linkedlinename".equals(qc[i].split("=")[0])) {
						 //线路名称条件
						   querySql += " AND linkedlinename like '%" + qc[i].split("=")[1] + "%'";
					   }else if("manufacturer".equals(qc[i].split("=")[0])) {
						 //生产厂家条件
						   querySql += " AND MANUFACTURER like '%" + qc[i].split("=")[1] + "%'";
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
							   
							   querySql += " AND t.linkeddepws in ("+deptString+")";
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
							   
							   querySql += " AND t.BZDM in ("+dydjString+")";
					   } else if("monitoringtype".equals(qc[i].split("=")[0]))
					   {
						   //监测信息
						   String[] jclx = qc[i].split("=")[1].split(",");
						  String jclxString = "";
						   for (int j = 0; j < jclx.length; j++) {
							jclxString = jclxString+"'"+jclx[j]+"'";
							if (j!=jclx.length-1) {
								jclxString = jclxString+",";
							}
						}						   
						   querySql += " AND d.monitoringtype in ("+jclxString+")";
					   }
					   
					   else if("srundate".equals(qc[i].split("=")[0]))
					   {
						   querySql += " and to_char(rundate,'yyyy-mm-dd') >= '" + qc[i].split("=")[1] + "'";
					   } else if("erundate".equals(qc[i].split("=")[0]))
					   {
						   querySql += " and to_char(rundate,'yyyy-mm-dd') <= '" + qc[i].split("=")[1] + "'";
					   }
					   else if("sfss".equals(qc[i].split("=")[0]))
					   {
						   querySql += " and sfss= '" + qc[i].split("=")[1] + "'";
					   }
					   else if("ssws".equals(qc[i].split("=")[0]))
					   {
						   if(!"all".equals(qc[i].split("=")[1]))
						   querySql += " and LINKEDDEPWS= '" + qc[i].split("=")[1] + "'";
					   }
					   else if("jclx".equals(qc[i].split("=")[0]))
					   {
						   
						   if("018".equals(qc[i].split("=")[1]))
						   {
							   querySql +=  " AND MONITORINGTYPE in ('018001','018002')";
						   }
						   else if("018003".equals(qc[i].split("=")[1]))
						   {
							   querySql += " AND MONITORINGTYPE in ('018003')";
						   }
						   else
							   querySql += " AND MONITORINGTYPE like '" + qc[i].split("=")[1] + "%'";
					   }
					   
				   }
			   }
			   
			   else {
				querySql = querySql;
			}
			  querySql += " order by t.XH";
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
		   try {
			   cols = "SSWS,SSWSMC,QXJCLJRS,QXJCLZS,DXJCLJRS,DXJCLZS,GTJCLJRS,GTJCLZS,JYZJCLJRS,JYZJCLZS,SPJCRS,SPLZS,JRSHJ,ZSHJ,SSJRL";
			   String ssdsfilter = getSsds(params);
			   String filterSqlString = getFilter(params);
			   String querySql = 
						"with tab as (select ssws,sswsmc,max(qxjcljrs) qxjcljrs, max(qxjclzs) qxjclzs,max(dxjcljrs) dxjcljrs, max(dxjclzs) dxjclzs,\n" +
						"max(gtjcljrs) gtjcljrs, max(gtjclzs) gtjclzs,  max(jyzjcljrs) jyzjcljrs, max(jyzjclzs) jyzjclzs,max(spjcrs) spjcrs,max(splzs) splzs,\n" + 
						"max(qxjcljrs)+max(dxjcljrs)+max(gtjcljrs)+max(jyzjcljrs)+max(spjcrs) jrshj,max(qxjclzs)+max(dxjclzs)+max(gtjclzs)+max(jyzjclzs)+max(splzs) zshj,\n" + 
						"(case when (max(qxjclzs)+max(dxjclzs)+max(gtjclzs)+max(jyzjclzs)+max(splzs)) = 0 then '-' else to_char(((max(qxjcljrs)+max(dxjcljrs)+max(gtjcljrs)+max(jyzjcljrs)+max(spjcrs)) / (max(qxjclzs)+max(dxjclzs)+max(gtjclzs)+max(jyzjclzs)+max(splzs)) * 100),'990.99')||'%' end) SSJRL\n" + 
						"from(select ssws,sswsmc, pms_xh,(case jcdl when '导线监测类' then sum(zzzs) else 0 end) dxjclzs,\n" + 
						"(case jcdl when '杆塔监测类' then sum(zzzs) else 0 end) gtjclzs,(case jcdl when '绝缘子监测类' then sum(zzzs) else 0 end) jyzjclzs,\n" + 
						"(case jcdl when '气象环境监测类' then sum(zzzs) else 0 end) qxjclzs,(case jcdl when '视频类' then sum(zzzs) else 0 end) splzs,\n" + 
						"(case jcdl when '导线监测类' then sum(ssjrzzs) else 0 end) dxjcljrs, (case jcdl when '杆塔监测类' then sum(ssjrzzs) else 0 end) gtjcljrs,\n" + 
						"(case jcdl when '绝缘子监测类' then sum(ssjrzzs) else 0 end) jyzjcljrs,(case jcdl when '气象环境监测类' then sum(ssjrzzs) else 0 end) qxjcljrs,\n" + 
						"(case jcdl when '视频类' then  sum(ssjrzzs)  else       0  end) spjcrs from (select tab1.*, decode(tab2.ssjrzzs, null, 0, tab2.ssjrzzs) ssjrzzs\n" + 
						"from (select d.wsid   ssws,d.wsmc sswsmc, d.zdypx pms_xh, w.jczy,w.zzzs,w.jcdl\n" + 
						"from mw_app.CMST_ZB_COMM_WSPZ d,(SELECT linkeddepws LINKEDPROVICEDEPT,DEVICEMONITYPE.MONITORINGTYPE jczy,\n" + 
						"count(distinct(LINEDEVICE.DEVICECODE)) zzzs,(case DEVICEMONITYPE.MONITORINGTYPE\n" + 
						"when '012001' then '杆塔监测类' when '013001' then '导线监测类'\n" + 
						"when '013002' then '导线监测类' when '013003' then '导线监测类'\n" + 
						"when '013004' then '导线监测类' when '013005' then '导线监测类'\n" + 
						"when '013006' then '导线监测类' when '014001' then '绝缘子监测类'\n" + 
						"when '018001' then '气象环境监测类' when '018002' then '气象环境监测类'\n" + 
						"when '018003' then '视频类' else '' end) jcdl FROM (select *\n" + 
						"from mw_app.cmsv_linedevicef where   LINKEDEQUIPMENTDY IS NOT NULL ) LINEDEVICE,\n" + 
						"MW_APP.CMST_DEVICEMONITYPE DEVICEMONITYPE WHERE LINEDEVICE.DEVICECODE = DEVICEMONITYPE.LINKEDDEVICE\n" + 
						"and (DEVICECATEGORYdm = 'sensor' OR DEVICECATEGORYdm = 'embed') "+filterSqlString+" group by linkeddepws, DEVICEMONITYPE.MONITORINGTYPE) w\n" + 
						"where d.wsid = w.LINKEDPROVICEDEPT(+) "+ssdsfilter+") tab1,(select linkeddepws LINKEDPROVICEDEPT,monitor.monitoringtype jclx,\n" + 
						"count(distinct LINEDEVICE.DEVICECODE) ssjrzzs from (select * from mw_app.cmsv_linedevicef where  LINKEDEQUIPMENTDY IS NOT NULL ) LINEDEVICE,\n" + 
						"mw_app.cmst_devicemonitype monitor WHERE LINEDEVICE.DEVICECODE = monitor.linkeddevice and (monitor.linkeddevice, monitor.monitoringtype) in\n" + 
						"(select zzbm, jclx from mw_app.cmsv_deviceused_info where sfss = 'T') "+filterSqlString+"\n" + 
						"group by linkeddepws, monitor.monitoringtype) tab2\n" + 
						"where tab1.ssws = tab2.LINKEDPROVICEDEPT(+)\n" + 
						"and tab1.jczy = tab2.jclx(+) )group by ssws, sswsmc, pms_xh, jcdl) where ssws!='45997CA6-2658-4F42-A809-95811C1E75A6-00004' group by ssws, sswsmc, pms_xh order by pms_xh )\n" + 
						"select 'all' ssws,'国家电网公司' sswsmc,sum(tab.qxjcljrs) qxjcljrs,sum(tab.qxjclzs) qxjclzs,sum(tab.dxjcljrs) dxjcljrs,sum(tab.dxjclzs) dxjclzs,sum(tab.gtjcljrs) gtjcljrs,\n" + 
						"sum(tab.gtjclzs) gtjclzs,sum(tab.jyzjcljrs) jyzjcljrs,sum(tab.jyzjclzs) jyzjclzs,sum(tab.spjcrs) spjcrs,sum(tab.splzs) splzs,  sum(tab.jrshj) jrshj,sum(tab.zshj) zshj,\n" + 
						"(case when sum(tab.zshj) = 0 then '-' else to_char((sum(tab.jrshj) / sum(tab.zshj) * 100),'990.99')||'%' end) SSJRL  from tab union all  select * from tab";

						
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
	 //获取地市信息  
	    private String getSsds(RequestCondition params) 
	    {
	    	String ssdsfilter = "";
	    	 if(null!=params.getFilter())
			   {
				   String[] qc = params.getFilter().toString().split("&");
				   for(int i = 0; i < qc.length; i++) {
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
							   ssdsfilter += " AND d.wsid in ("+deptString+")";
					   }
				   }
			   }
			   
			   else {
				   
				   ssdsfilter = ssdsfilter;
			}
	    	
	    	return ssdsfilter;
	    }
	   //获取统计条件 
	    private String getFilter(RequestCondition params) {
			String filterString = "";
			   if(null!=params.getFilter())
			   {
				   String[] qc = params.getFilter().toString().split("&");
				   for(int i = 0; i < qc.length; i++) {
					   
					   if("linkedlinename".equals(qc[i].split("=")[0])) {
						 //线路名称条件
						   filterString += " AND linkedlinename like '%" + qc[i].split("=")[1] + "%'";
					   }else if("manufacturer".equals(qc[i].split("=")[0])) {
						 //生产厂家条件
						   filterString += " AND MANUFACTURER like '%" + qc[i].split("=")[1] + "%'";
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
							   
							   filterString += " AND linkedequipmentdy in ("+dydjString+")";
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
						   filterString += " AND monitoringtype in ("+jclxString+")";
					   }
					   
					   else if("srundate".equals(qc[i].split("=")[0]))
					   {
						   filterString += " and to_char(rundate,'yyyy-mm-dd') >= '" + qc[i].split("=")[1] + "'";
					   } else if("erundate".equals(qc[i].split("=")[0]))
					   {
						   filterString += " and to_char(rundate,'yyyy-mm-dd') <= '" + qc[i].split("=")[1] + "'";
					   }
					   
				   }
			   }
			   else {
				   filterString = filterString;
			}
			return filterString;
		}
	   private List<DicItems> wrapDictList() {
			List<DicItems> dicts = new ArrayList<DicItems>();
			 
//	        
			String jclxsql = "SELECT TYPENAME,TYPECODE FROM MW_APP.CMST_MONITORINGTYPE WHERE TYPECODE LIKE '01%'";
			dicts.add(translateFromDBT("monitoringtype",jclxsql));
			
			
			String deptsqlString = "SELECT wsmc,wsid FROM MW_APP.CMST_ZB_COMM_WSPZ order by zdypx";
		     dicts.add(translateFromDBT("linkeddepws",deptsqlString));
		     	       
	        
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
//	          return retlist;
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
		    
}

