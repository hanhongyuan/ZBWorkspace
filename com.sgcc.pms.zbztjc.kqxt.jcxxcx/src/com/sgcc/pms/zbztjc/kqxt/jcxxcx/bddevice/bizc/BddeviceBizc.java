package com.sgcc.pms.zbztjc.kqxt.jcxxcx.bddevice.bizc;

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
public class BddeviceBizc implements IBddeviceBizc{


	private final Log logger = LogFactory.getLog(BddeviceBizc.class);
	@Resource
	private IDataDictionaryBizC dataDictionaryBizC;
	@Resource
	private IHibernateDao hibernateDao_ztjc;


	
	 public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}


	/**
	    * 变电监测信息查询
	    * @param bdxxcx
	    * @return
	    */
	   public QueryResultObject getWtysblb(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			  // cols = params.getColumns().toString();
			   cols = "XTMC,WSDEPMC,JCXH,DEVICEVOLTAGE,SFSS,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,LINKEDCAC_DISPLAY,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICEMODEL,MANUFACTURER,RUNDATE,LINKEDDEPWS";
			   String querySql = 
						"SELECT XTMC,WSDEPMC,XH as JCXH,DEVICEVOLTAGE,SFSS,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,\n" +
						"       LINKEDCAC_DISPLAY,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,DEVICECODE,DEVICENAME,\n" + 
						"       MONITORINGTYPES,DEVICEMODEL,MANUFACTURER,RUNDATE,linkeddepws\n" + 
						"  FROM MW_APP.CMSV_TRANSFDEVICEF T\n" + 
						" where 1 = 1 and t.WSDEPMC is not null and t.DEVICEVOLTAGE is not null";

			  
			   if(null!=params.getFilter())
			   {
				   String[] qc = params.getFilter().toString().split("&");
				   for(int i = 0; i < qc.length; i++) {
					   
					   if("linkedstationname".equals(qc[i].split("=")[0])) {
						 //线路名称条件
						   querySql += " AND linkedstationname like '%" + qc[i].split("=")[1] + "%'";
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
							   
							   querySql += " AND t.LINKEDEQUIPMENTDY in ("+dydjString+")";
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
						   querySql += " AND t.monitoringtypes in ("+jclxString+")";
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
					   else if("moint".equals(qc[i].split("=")[0]))
					   {
						   if("0256".equals(qc[i].split("=")[1]))
						   {
							   querySql += " AND MONITORINGTYPEs in ('025001','026001')";
						   }
						   else
							   querySql += " AND MONITORINGTYPEs like '" + qc[i].split("=")[1] + "%'";
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
			   logger.info("变电装置查询管理，加载设备列表出错", e);
			   e.printStackTrace();
		   }
		   return qro;
	   }
	   
	   /**
	    * 变电监测信息统计
	    * @param bdxxtj
	    * @return
	    */
	   public QueryResultObject gettjxx(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		   List result = new ArrayList();
		   List count = new ArrayList();
		   String cols = "";
		   try {
			   cols = "SSWS,SSWSMC,BYDKLJRS,BYDKLZS,DLGISLJRS,DLGISLZS,DRXLJRS,DRXLZS,JSYHWLJRS,JSYHWLZS,ZHLRS,ZHLZS,JRSHJ,ZSHJ,SSJRL";
			   String ssdsfilter = getSsds(params);
			   String filterSqlString = getFilter(params);
			   String querySql =
						"with tab as (select ssws,sswsmc,max(BYDKLJRS) BYDKLJRS,max(BYDKLZS) BYDKLZS,max(DLGISLJRS) DLGISLJRS,max(DLGISLZS) DLGISLZS,\n" +
						"max(DRXLJRS) DRXLJRS,max(DRXLZS) DRXLZS,  max(JSYHWLJRS) JSYHWLJRS,max(JSYHWLZS) JSYHWLZS,max(ZHLRS) ZHLRS,max(ZHLZS) ZHLZS ,\n" + 
						"max(BYDKLJRS)+max(JSYHWLJRS)+max(DLGISLJRS)+max(ZHLRS)+max(DRXLJRS) jrshj,max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(ZHLZS)+max(DRXLZS) zshj,\n" + 
						"(case when (max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(ZHLZS)+max(DRXLZS)) = 0 then '-' else to_char(((max(BYDKLJRS)+max(JSYHWLJRS)+max(DLGISLJRS)+max(ZHLRS)+max(DRXLJRS)) / (max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(ZHLZS)+max(DRXLZS)) * 100),'990.99')||'%' end) SSJRL\n" + 
						"from (select ssws,sswsmc,pms_xh,\n" + 
						"(case jcdl when '变压器/电抗器类' then sum(zzzs) else 0 end) BYDKLZS,\n" + 
						"(case jcdl when '断路器/GIS类' then sum(zzzs) else 0 end) DLGISLZS,(case jcdl when '电容型设备类' then sum(zzzs)else 0 end) DRXLZS,\n" + 
						"(case jcdl when '金属氧化物避雷器类' then sum(zzzs)else 0 end) JSYHWLZS,  (case jcdl when '综合类' then sum(zzzs)else 0 end) ZHLZS,\n" + 
						"(case jcdl when '变压器/电抗器类' then sum(ssjrzzs)else 0 end) BYDKLJRS,(case jcdl when '断路器/GIS类'then sum(ssjrzzs)else 0 end) DLGISLJRS,\n" + 
						"(case jcdl when '电容型设备类'then sum(ssjrzzs)else 0 end) DRXLJRS,  (case jcdl when '金属氧化物避雷器类'then sum(ssjrzzs)else 0 end) JSYHWLJRS,\n" + 
						"(case jcdl when '综合类' then sum(ssjrzzs) else  0  end) ZHLRS from (  select tab1.*, decode(tab2.ssjrzzs,null,0,tab2.ssjrzzs) ssjrzzs\n" + 
						"from (  select d.wsid ssws,d.wsmc sswsmc,d.zdypx pms_xh,w.jczy,w.zzzs,w.jcdl from mw_app.CMST_ZB_COMM_WSPZ d ,\n" + 
						"(SELECT linkeddepws LINKEDPROVICEDEPT,DEVICEMONITYPE.MONITORINGTYPE jczy,count(distinct(LINEDEVICE.DEVICECODE)) zzzs,\n" + 
						"(case DEVICEMONITYPE.MONITORINGTYPE when '021001'then '变压器/电抗器类'when'021002'then'变压器/电抗器类'when'021003'then'变压器/电抗器类'\n" + 
						"when'021004'then'变压器/电抗器类'when'021005'then'变压器/电抗器类'when'021007'then'变压器/电抗器类'when'022001'then'电容型设备类'\n" + 
						"when'023001'then'金属氧化物避雷器类'when'024001'then  '断路器/GIS类'when'024002'then'断路器/GIS类'when'024004'then'断路器/GIS类'\n" + 
						"when'024005'then'断路器/GIS类'when'024006'then'断路器/GIS类'when '024003'then'断路器/GIS类'when'026001'then'综合类' else '' end) jcdl\n" + 
						"FROM (select * from mw_app.cmsv_transfdevicef where LINKEDEQUIPMENTDY IS NOT NULL  ) LINEDEVICE,MW_APP.CMST_DEVICEMONITYPE DEVICEMONITYPE\n" + 
						"WHERE LINEDEVICE.DEVICECODE = DEVICEMONITYPE.LINKEDDEVICE and (DeviceCategorydm = 'sensor' OR DEVICECATEGORYdm = 'embed') "+filterSqlString+ " group by linkeddepws,\n" + 
						"DEVICEMONITYPE.MONITORINGTYPE) w where d.wsid = w.LINKEDPROVICEDEPT(+)  "+ssdsfilter+") tab1 ,(  select linkeddepws LINKEDPROVICEDEPT,\n" + 
						"monitor.monitoringtype jclx, count(LINEDEVICE.DEVICECODE) ssjrzzs from (select *  from mw_app.cmsv_transfdevicef\n" + 
						"where LINKEDEQUIPMENTDY IS NOT NULL) LINEDEVICE,mw_app.cmst_devicemonitype monitor  WHERE LINEDEVICE.DEVICECODE = monitor.linkeddevice\n" + 
						"and (monitor.linkeddevice) in (select zzbm from mw_app.cmsv_deviceused_info where sfss = 'T')"+filterSqlString+ "\n" + 
						"group by linkeddepws, monitor.monitoringtype) tab2 where tab1.ssws = tab2.LINKEDPROVICEDEPT(+) and tab1.jczy = tab2.jclx(+)  )\n" + 
						"group by ssws, sswsmc,pms_xh, jcdl) group by ssws, sswsmc,pms_xh order by pms_xh )  select 'all' ssws,'国家电网公司' sswsmc,\n" + 
						"sum(tab.BYDKLJRS) BYDKLJRS,sum(tab.BYDKLZS) BYDKLZS,sum(tab.DLGISLJRS) DLGISLJRS,sum(tab.DLGISLZS) DLGISLZS, sum(tab.DRXLJRS) DRXLJRS,\n" + 
						"sum(tab.DRXLZS) DRXLZS,sum(tab.JSYHWLJRS) JSYHWLJRS,sum(tab.JSYHWLZS) JSYHWLZS,sum(tab.ZHLRS) ZHLRS,sum(tab.ZHLZS) ZHLZS,\n" + 
						"sum(tab.jrshj) jrshj,sum(tab.zshj) zshj,(case when (sum(tab.zshj)) = 0 then '-' else to_char(((sum(tab.jrshj)) / (sum(tab.zshj)) * 100),'990.99')||'%' end) SSJRL\n" + 
						"from tab union all   select * from tab";

 
					  

			  
			   result = hibernateDao_ztjc.executeSqlQuery(querySql);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
			   qro.addDicItems(wrapDictList());
			  
			   
		   } catch (Exception e) {
			   logger.info("变电装置查询管理，加载设备列表出错", e);
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
	   

 //查询页面下拉框
	   private List<DicItems> wrapDictList() {
			List<DicItems> dicts = new ArrayList<DicItems>();

			String jclxsql = "SELECT TYPENAME,TYPECODE FROM MW_APP.CMST_MONITORINGTYPE WHERE TYPECODE LIKE '02%'";
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
					   
					   if("linkedstationname".equals(qc[i].split("=")[0])) {
						 //线路名称条件
						   filterString += " AND LINEDEVICE.linkedstationname like '%" + qc[i].split("=")[1] + "%'";
					   }else if("manufacturer".equals(qc[i].split("=")[0])) {
						 //生产厂家条件
						   filterString += " AND LINEDEVICE.MANUFACTURER like '%" + qc[i].split("=")[1] + "%'";
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
							   
							   filterString += " AND LINEDEVICE.LINKEDEQUIPMENTDY in ("+dydjString+")";
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
						   System.out.println(jclxString);
						   filterString += " AND LINEDEVICE.monitoringtypes in ("+jclxString+")";
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
}

