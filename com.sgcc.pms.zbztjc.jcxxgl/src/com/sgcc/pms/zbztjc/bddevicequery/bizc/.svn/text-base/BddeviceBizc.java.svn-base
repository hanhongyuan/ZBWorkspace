package com.sgcc.pms.zbztjc.bddevicequery.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.myUtils.ILoggerSaveBizc;
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

	@Resource
	private ILoggerSaveBizc loggerSaveBizc ;
	
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
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			   String cols = "LINKEDDEPWS,DEVICECODE,DEVICEVOLTAGE,MONITORINGTYPES,WSDEPMC,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,LINKEDCAC_DISPLAY,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,STATUS,RUNDATE,JCXX,ISRT";
			   String querySql = 
						"SELECT LINKEDDEPWS,DEVICECODE,DEVICEVOLTAGE,MONITORINGTYPES,WSDEPMC,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,\n" +
						"LINKEDCAC_DISPLAY,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,STATUS,RUNDATE,'查看' JCXX,\n" + 
						"decode(SFSS,'T','是','F','否', null,'否') ISRT FROM MW_APP.CMSV_TRANSFDEVICE_XTF T, (select distinct monitor.linkeddevice,monitor.monitoringtype,info.SFSS\n" + 
						"from mw_app.cmst_devicemonitype  monitor,mw_app.cmsv_deviceused_info info where monitor.linkeddevice = info.ZZBM(+) and monitor.monitoringtype = info.JCLX(+)) ssjr\n" + 
						"where t.devicecode = ssjr.linkeddevice  and t.WSDEPMC is not null and t.DEVICEVOLTAGE is not null and t.status != '00504' ";
			   
			  
			   if(null!=params.getFilter())
			   {
				   String[] qc = params.getFilter().toString().split("&");
				   String tjname = "";
				   String tjvalue = "";
				   int qclength = qc.length;
				   for(int i = 0; i < qclength; i++) {
					   tjname = qc[i].split("=")[0];
					   tjvalue = qc[i].split("=")[1];
					   if("linkedstationname".equals(tjname)) {
						 //线路名称条件
						   querySql += " AND linkedstationname like '%" + tjvalue + "%'";
					   }else if("manufacturer".equals(tjname)) {
						 //生产厂家条件
						   querySql += " AND MANUFACTURER like '%" + tjvalue + "%'";
					   }
					   else if("linkeddepws".equals(tjname)) {
						 //所属地市条件
						   String[] dept = tjvalue.split(",");
							  String deptString = "";
							   for (int j = 0; j < dept.length; j++) {
								   deptString = deptString+"'"+dept[j]+"'";
								if (j!=dept.length-1) {
									deptString = deptString+",";
								}
							}
							   querySql += " AND t.linkeddepws in ("+deptString+")";
					   }else if("yxzt".equals(tjname)) {
						   //运行状态
						   String[] yxzt = tjvalue.split(",");
							  String yxztString = "";
							   for (int j = 0; j < yxzt.length; j++) {
								   yxztString = yxztString+"'"+yxzt[j]+"'";
								if (j!=yxzt.length-1) {
									yxztString = yxztString+",";
								}
							}
							   
							   querySql += " AND t.status in ("+yxztString+")";
					   } 
					   else if("dydj".equals(tjname)) {
						   //电压等级
						   String[] dydj = tjvalue.split(",");
							  String dydjString = "";
							   for (int j = 0; j < dydj.length; j++) {
								   dydjString = dydjString+"'"+dydj[j]+"'";
								if (j!=dydj.length-1) {
									dydjString = dydjString+",";
								}
							}
							   
							   querySql += " AND t.LINKEDEQUIPMENTDY in ("+dydjString+")";
					   } else if("monitoringtype".equals(tjname))
					   {
						   //监测类型
						   String[] jclx = tjvalue.split(",");
						  String jclxString = "";
						   for (int j = 0; j < jclx.length; j++) {
							jclxString = jclxString+"'"+jclx[j]+"'";
							if (j!=jclx.length-1) {
								jclxString = jclxString+",";
							}
						}
						   querySql += " AND t.monitoringtypes in ("+jclxString+")";
					   }
					   
					   else if("srundate".equals(tjname))
					   {
						   querySql += " and to_char(rundate,'yyyy-mm-dd') >= '" + tjvalue + "'";
					   } else if("erundate".equals(tjname))
					   {
						   querySql += " and to_char(rundate,'yyyy-mm-dd') <= '" + tjvalue + "'";
					   }
					   else if("sfss".equals(tjname))
					   {
						   querySql += " and sfss= '" + tjvalue + "'";
					   }
					   else if("ssws".equals(tjname))
					   {
						   if(!"all".equals(tjvalue))
						   querySql += " and LINKEDDEPWS= '" + tjvalue + "'";
					   }
					   else if("moint".equals(tjname))
					   {
						   if("0256".equals(tjvalue))
						   {
							   querySql += " AND MONITORINGTYPE in ('025001','026001')";
						   }
						   else
							   querySql += " AND MONITORINGTYPE like '" + tjvalue + "%'";
					   }
				   }
			   }
			   querySql += " order by t.XH";
			   result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			   result = transToColumns(result,cols);
			   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			   
			   qro.setItems(result);
			   qro.setItemCount(((Number) (count.get(0))).intValue());
			   qro.addDicItems(wrapDictList());
			   
			   loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-变电监测信息查询", "操作成功");
			   return qro;
		   } catch (Exception e) {
			   logger.info("错误信息", e);
			   e.printStackTrace();
			   loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-变电监测信息查询", "操作失败");
		   }
		   return null;
	   }
	   
	   /**
	    * 变电监测信息统计
	    * @param bdxxtj
	    * @return
	    */
	   public QueryResultObject gettjxx(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		   List result = new ArrayList();
		   try {
			   String cols = "SSWS,SSWSMC,BYDKLJRS,BYDKLZS,DLGISLJRS,DLGISLZS,DRXLJRS,DRXLZS,JSYHWLJRS,JSYHWLZS,ZHLJRS,ZHLZS,JRSHJ,ZSHJ,SSJRL";
			   String ssdsfilter = getSsds(params);
			   String filterSqlString = getFilter(params);
			   String querySql =
					   "with tab as (\n" +
							   "select ssws,sswsmc,max(BYDKLJRS) BYDKLJRS,max(BYDKLZS) BYDKLZS,max(DLGISLJRS) DLGISLJRS,\n" + 
							   "max(DLGISLZS) DLGISLZS,max(DRXLJRS) DRXLJRS,max(DRXLZS) DRXLZS,  max(JSYHWLJRS) JSYHWLJRS,\n" + 
							   "max(JSYHWLZS) JSYHWLZS,max(ZHLJRS) ZHLJRS,max(ZHLZS) ZHLZS ,\n" + 
							   "max(BYDKLJRS)+max(JSYHWLJRS)+max(DLGISLJRS)+max(ZHLJRS)+max(DRXLJRS) jrshj,\n" + 
							   "max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(ZHLZS)+max(DRXLZS) zshj,\n" + 
							   "(case when (max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(ZHLZS)+max(DRXLZS)) = 0 then '-'\n" + 
							   "else to_char(((max(BYDKLJRS)+max(JSYHWLJRS)+max(DLGISLJRS)+max(ZHLJRS)+max(DRXLJRS)) / (max(BYDKLZS)+max(JSYHWLZS)+max(DLGISLZS)+max(ZHLZS)+max(DRXLZS)) * 100),'990.99')||'%' end) SSJRL\n" + 
							   "from (select ssws,sswsmc,pms_xh,(case jcdl when '变压器/电抗器类' then sum(zzzs) else 0 end) BYDKLZS,\n" + 
							   "(case jcdl when '断路器/GIS类' then sum(zzzs) else 0 end) DLGISLZS,(case jcdl when '电容型设备类' then sum(zzzs)else 0 end) DRXLZS,\n" + 
							   "(case jcdl when '金属氧化物避雷器类' then sum(zzzs)else 0 end) JSYHWLZS,  (case jcdl when '综合类' then sum(zzzs)else 0 end) ZHLZS,\n" + 
							   "(case jcdl when '综合类'then sum(ssjrzzs)else 0 end) ZHLJRS,(case jcdl when '变压器/电抗器类' then sum(ssjrzzs)else 0 end) BYDKLJRS,\n" + 
							   "(case jcdl when '断路器/GIS类'then sum(ssjrzzs)else 0 end) DLGISLJRS,(case jcdl when '电容型设备类'then sum(ssjrzzs)else 0 end) DRXLJRS,\n" + 
							   "(case jcdl when '金属氧化物避雷器类'then sum(ssjrzzs)else 0 end) JSYHWLJRS\n" + 
							   "from (  select tab1.*, decode(tab2.ssjrzzs,null,0,tab2.ssjrzzs) ssjrzzs from\n" + 
							   "(  select d.wsid ssws,d.wsmc sswsmc,d.zdypx pms_xh,w.jczy,w.zzzs,w.jcdl from mw_app.CMST_ZB_COMM_WSPZ d ,\n" + 
							   "(SELECT LINKEDPROVICEDEPT,DEVICEMONITYPE.MONITORINGTYPE jczy,count(distinct(LINEDEVICE.DEVICECODE)) zzzs,\n" + 
							   "(case DEVICEMONITYPE.MONITORINGTYPE when '021001'then '变压器/电抗器类'\n" + 
							   "when'021002'then'变压器/电抗器类'when'021003'then'变压器/电抗器类'\n" + 
							   "when'021004'then'变压器/电抗器类'when'021005'then'变压器/电抗器类'\n" + 
							   "when'022001'then'电容型设备类'when'023001'then'金属氧化物避雷器类'\n" + 
							   "when'024001'then  '断路器/GIS类'when'024002'then'断路器/GIS类'\n" + 
							   "when'024004'then'断路器/GIS类'when'024005'then'断路器/GIS类'\n" + 
							   "when'024006'then'断路器/GIS类'when '024003'then'断路器/GIS类'\n" + 
							   "when'026001'then'综合类'when'025001'then'综合类'  else '' end) jcdl\n" + 
							   "FROM (select * from mw_app.cmst_transfdevice where LINKEDEQUIPMENTDY IS NOT NULL and status = '00501') LINEDEVICE,\n" + 
							   "MW_APP.CMST_DEVICEMONITYPE DEVICEMONITYPE  WHERE LINEDEVICE.DEVICECODE = DEVICEMONITYPE.LINKEDDEVICE and\n" + 
							   "(DeviceCategory = 'sensor' OR DEVICECATEGORY = 'embed') "+filterSqlString+"\n" + 
							   "group by LINKEDPROVICEDEPT, DEVICEMONITYPE.MONITORINGTYPE) w\n" + 
							   "where d.wsid = w.LINKEDPROVICEDEPT(+) "+ssdsfilter+") tab1 ,\n" + 
							   "(  select LINKEDPROVICEDEPT,monitor.monitoringtype jclx, count(LINEDEVICE.DEVICECODE) ssjrzzs\n" + 
							   "from (select *  from mw_app.cmst_transfdevice  where LINKEDEQUIPMENTDY IS NOT NULL and status = '00501') LINEDEVICE,\n" + 
							   "mw_app.cmst_devicemonitype monitor  WHERE LINEDEVICE.DEVICECODE = monitor.linkeddevice\n" + 
							   "and (monitor.linkeddevice) in (select zzbm from mw_app.cmsv_deviceused_info where sfss = 'T') "+filterSqlString+"\n" + 
							   "group by LINKEDPROVICEDEPT, monitor.monitoringtype) tab2\n" + 
							   "where tab1.ssws = tab2.LINKEDPROVICEDEPT(+) and tab1.jczy = tab2.jclx(+)  )\n" + 
							   "group by ssws, sswsmc,pms_xh, jcdl) group by ssws, sswsmc,pms_xh order by pms_xh )\n" + 
							   "select 'all' ssws,'国家电网公司' sswsmc,sum(tab.BYDKLJRS) BYDKLJRS,sum(tab.BYDKLZS) BYDKLZS,\n" + 
							   "sum(tab.DLGISLJRS) DLGISLJRS,sum(tab.DLGISLZS) DLGISLZS, sum(tab.DRXLJRS) DRXLJRS,sum(tab.DRXLZS) DRXLZS,\n" + 
							   "sum(tab.JSYHWLJRS) JSYHWLJRS,sum(tab.JSYHWLZS) JSYHWLZS,sum(tab.ZHLJRS) ZHLJRS,sum(tab.ZHLZS) ZHLZS,\n" + 
							   "sum(tab.jrshj) jrshj,sum(tab.zshj) zshj,\n" + 
							   "(case when (sum(tab.zshj)) = 0 then '-' else to_char(((sum(tab.jrshj)) / (sum(tab.zshj)) * 100),'990.99')||'%' end) SSJRL\n" + 
							   "from tab union all select * from tab";
			   result = hibernateDao_ztjc.executeSqlQuery(querySql);
			   result = transToColumns(result,cols);
			   
			   qro.setItems(result);
			   qro.addDicItems(wrapDictList());
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
	   

 //查询页面下拉框
	   private List<DicItems> wrapDictList() {
			List<DicItems> dicts = new ArrayList<DicItems>();
			String yxztsql = "select codename,code from mw_app.cmst_commoncode e where e.parentcode = '005'";
			dicts.add(translateFromDBT("yxzt",yxztsql));
			String jclxsql = "SELECT TYPENAME,TYPECODE FROM MW_APP.CMST_MONITORINGTYPE WHERE TYPECODE LIKE '02%'";
			dicts.add(translateFromDBT("monitoringtype",jclxsql));
			String deptsqlString = "SELECT wsmc,wsid FROM MW_APP.CMST_ZB_COMM_WSPZ order by zdypx";
		    dicts.add(translateFromDBT("linkeddepws",deptsqlString));
	        dicts.add(translateFromFile("dydj", "DYDJ"));
	        
	        return dicts;
		}
		
		// 从属性文件中查询字典
		private DicItems translateFromFile(String fieldName, String dicId) {
			List<Map<String, String>> list = dataDictionaryBizC.translateFromFile(
					dicId, "value", "text");
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
	    	
	    	return ssdsfilter;
	    }
	   //获取统计条件 
	    private String getFilter(RequestCondition params) {
			String filterString = "";
			   if(null!=params.getFilter())
			   {
				   String[] qc = params.getFilter().toString().split("&");
				   String tjname = "";
				   String tjvalue = "";
				   int qclength = qc.length;
				   for(int i = 0; i < qclength; i++) {
					   tjname = qc[i].split("=")[0];
					   tjvalue = qc[i].split("=")[1];
					   if("linkedstationname".equals(tjname)) {
						 //线路名称条件
						   filterString += " AND LINEDEVICE.linkedstationname like '%" + tjvalue + "%'";
					   }else if("manufacturer".equals(tjname)) {
						 //生产厂家条件
						   filterString += " AND LINEDEVICE.MANUFACTURER like '%" + tjvalue + "%'";
					   }
					  
					   else if("dydj".equals(tjname)) {
						   //电压等级
						   String[] dydj = tjvalue.split(",");
							  String dydjString = "";
							   for (int j = 0; j < dydj.length; j++) {
								   dydjString = dydjString+"'"+dydj[j]+"'";
								if (j!=dydj.length-1) {
									dydjString = dydjString+",";
								}
							}
							   filterString += " AND LINEDEVICE.LINKEDEQUIPMENTDY in ("+dydjString+")";
					   }else if("yxzt".equals(tjname)) {
						   //运行状态
						   String[] yxzt = tjvalue.split(",");
							  String yxztString = "";
							   for (int j = 0; j < yxzt.length; j++) {
								   yxztString = yxztString+"'"+yxzt[j]+"'";
								if (j!=yxzt.length-1) {
									yxztString = yxztString+",";
								}
							}
							   filterString += " AND status in ("+yxztString+")";
					   } else if("monitoringtype".equals(tjname))
					   {
						   //监测信息
						   String[] jclx = tjvalue.split(",");
						  String jclxString = "";
						   for (int j = 0; j < jclx.length; j++) {
							jclxString = jclxString+"'"+jclx[j]+"'";
							if (j!=jclx.length-1) {
								jclxString = jclxString+",";
							}
						}
						   filterString += " AND LINEDEVICE.monitoringtypes in ("+jclxString+")";
					   }
					   
					   else if("srundate".equals(tjname))
					   {
						   filterString += " and to_char(rundate,'yyyy-mm-dd') >= '" + tjvalue + "'";
					   } else if("erundate".equals(tjname))
					   {
						   filterString += " and to_char(rundate,'yyyy-mm-dd') <= '" + tjvalue + "'";
					   }
				   }
			   }
			return filterString;
		}
}

