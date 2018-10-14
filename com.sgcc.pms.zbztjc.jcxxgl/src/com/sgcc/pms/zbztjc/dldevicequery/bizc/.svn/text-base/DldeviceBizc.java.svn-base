package com.sgcc.pms.zbztjc.dldevicequery.bizc;


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
public class DldeviceBizc implements IDldeviceBizc{


	private final Log logger = LogFactory.getLog(DldeviceBizc.class);
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
	    * 电缆监测信息查询
	    * @param dlxxcx
	    * @return
	    */
	   public QueryResultObject getWtysblb(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   List count = new ArrayList();
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			   String cols = "LINKEDDEPT,DEPMC,XH,LINKEDDEPWS,WSDEPMC,LINKEDEQUIPMENTDY,DEVICEVOLTAGE,DYDJGDJB,LINKEDEQUIPMENT,LINKEDCABLEANDCHANNEL,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME,DEVICECATEGORY,DEVICECATEGORYDM,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,STATUS,DEVICEMODEL,MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER,REMARKS,JCXX,ISACTIVATED,ISRT";
			   String querySql = 
						"SELECT LINKEDDEPT,DEPMC,XH,LINKEDDEPWS,depmc WSDEPMC,VOLTAGEGRADE LINKEDEQUIPMENTDY,\n" +
						"DEVICEVOLTAGE,DYDJGDJB,LINKEDEQUIPMENT,LINKEDCABLEANDCHANNEL,LINKEDCABLEANDCHANNELNAME,LINKEDEQUIPMENTNAME,\n" + 
						"DEVICECATEGORY,DEVICECATEGORYDM,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,STATUS,\n" + 
						"DEVICEMODEL,MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER,REMARKS,JCXX,'' ISACTIVATED,\n" + 
						"decode(t.SFSS, 'T', '是', 'F', '否', null, '否') ISRT FROM mw_app.CMSV_CABLEDEVICE_XTf t\n" + 
						"where  t.depmc is not null and LINKEDDEPWS is not null and VOLTAGEGRADE is not null ";

			   
			  
			   if(null!=params.getFilter())
			   {
				   String[] qc = params.getFilter().toString().split("&");
				   int qclength = qc.length;
				   String tjname ="";
				   String tjvalue = "";
				   for(int i = 0; i < qclength; i++) {
					   tjname = qc[i].split("=")[0];
					   tjvalue = qc[i].split("=")[1];
					   if("LINKEDCABLEANDCHANNELNAME".equals(tjname)) {
						 //线路名称条件
						   querySql += " AND LINKEDCABLEANDCHANNELNAME like '%" + tjvalue + "%'";
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
							   querySql += " AND t.linkeddepws in ('',"+deptString+")";
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
							   querySql += " AND t.VOLTAGEGRADE in ("+dydjString+")";
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
						   querySql += " and LINKEDDEPWS in('','" + tjvalue + "')";
					   }
					   else if("moint".equals(tjname))
					   {
							   querySql += " AND monitoringtypes like '" + tjvalue + "%'";
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
			   loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-电缆监测信息查询", "操作成功");
			   return qro;
		   } catch (Exception e) {
			   logger.info("错误信息", e);
			   e.printStackTrace();
			   loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-电缆监测信息查询", "操作失败");
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
		   String cols = "";
		   try {
			   cols = "SSWS,SSWSMC,DLJCLJRS,DLJCLZS,DLTDJCLJRS,DLTDJCLZS,DLTDKZLJRS,DLTDKZLZS,JRSHJ,ZSHJ,SSJRL,DLJCLSSJRL,DLTDJCLSSJRL";
			   String ssdsfilter = getSsds(params);
			   String filterSqlString = getFilter(params);
			   String querySql =
							"with tab as (select ssws,sswsmc,max(dljcljrs) dljcljrs, max(dljclzs) dljclzs,max(dltdjcljrs) dltdjcljrs, max(dltdjclzs) dltdjclzs,\n" +
							"max(dltdkzljrs) dltdkzljrs, max(dltdkzlzs) dltdkzlzs, max(dljcljrs)+max(dltdjcljrs)+max(dltdkzljrs) jrshj,max(dljclzs)+max(dltdjclzs)+max(dltdkzlzs) zshj,\n" + 
							"(case when (max(dljclzs)+max(dltdjclzs)+max(dltdkzlzs)) = 0 then '-' else to_char(((max(dljcljrs)+max(dltdjcljrs)+max(dltdkzljrs)) / (max(dljclzs)+max(dltdjclzs)+max(dltdkzlzs)) * 100),'990.99')||'%' end) SSJRL,\n" + 
							"(case  when (max(dljclzs)) = 0 then   '-' else  to_char(((max(dljcljrs)) / (max(dljclzs)) * 100),   '990.99') || '%'  end) DLJCLSSJRL,\n" + 
							"(case  when (max(dltdjclzs)) = 0 then   '-'  else  to_char(((max(dltdjcljrs)) / (max(dltdjclzs)) * 100),   '990.99') || '%' end) DLTDJCLSSJRL\n" + 
							"from(select ssws,sswsmc, pms_xh,(case jcdl when '电缆监测' then sum(zzzs) else 0 end) dljclzs,\n" + 
							"(case jcdl when '电缆通道监测' then sum(zzzs) else 0 end) dltdjclzs,(case jcdl when '电缆通道控制' then sum(zzzs) else 0 end) dltdkzlzs,\n" + 
							"(case jcdl when '电缆监测' then sum(ssjrzzs) else 0 end) dljcljrs, (case jcdl when '电缆通道监测' then sum(ssjrzzs) else 0 end) dltdjcljrs,\n" + 
							"(case jcdl when '电缆通道控制' then sum(ssjrzzs) else 0 end) dltdkzljrs from (select tab1.*, decode(tab2.ssjrzzs, null, 0, tab2.ssjrzzs) ssjrzzs\n" + 
							"from (select d.wsid   ssws,d.wsmc sswsmc,d.zdypx pms_xh,w.jczy,w.zzzs,w.jcdl\n" + 
							"from mw_app.CMST_ZB_COMM_WSPZ d,(SELECT LINKEDDEPT,MONITORINGTYPES jczy,\n" + 
							"count(distinct(LINEDEVICE.DEVICECODE)) zzzs,\n" + 
							"(case MONITORINGTYPES  when '031001' then\n" + 
							"'电缆监测'  when '031002' then\n" + 
							"'电缆监测'  when '031003' then\n" + 
							"'电缆监测'  when '031004' then\n" + 
							"'电缆监测'  when '032001' then\n" + 
							"'电缆通道监测'   when '032002' then\n" + 
							"'电缆通道监测'   when '032003' then\n" + 
							"'电缆通道监测'   when '032004' then\n" + 
							"'电缆通道监测'   when '032005' then\n" + 
							"'电缆通道监测'   when '032006' then\n" + 
							"'电缆通道监测'   when '032007' then\n" + 
							"'电缆通道监测'   when '032008' then\n" + 
							"'电缆通道监测'   when '032009' then\n" + 
							"'电缆通道监测'   when '032010' then\n" + 
							"'电缆通道监测'   when '032011' then\n" + 
							"'电缆通道监测'   when '032012' then\n" + 
							"'电缆通道监测'   when '032013' then\n" + 
							"'电缆通道监测'   when '032014' then\n" + 
							"'电缆通道监测'   else  ''  end) jcdl\n" + 
							"FROM (select * from mw_app.CMSV_CABLEDEVICE_XTf\n" + 
							" )     LINEDEVICE  where  LINKEDDEPWS is not null  and VOLTAGEGRADE is not null  "+filterSqlString+"\n" + 
							"group by LINKEDDEPT, MONITORINGTYPES) w\n" + 
							"where d.wsid = w.LINKEDDEPT(+) "+ssdsfilter+") tab1,\n" + 
							"(select LINKEDDEPT,\n" + 
							"monitoringtypes jclx,\n" + 
							"count(distinct LINEDEVICE.DEVICECODE) ssjrzzs\n" + 
							"from (select *\n" + 
							"from  mw_app.CMSV_CABLEDEVICE_XTf\n" + 
							")     LINEDEVICE\n" + 
							
							"WHERE LINKEDDEPWS is not null   and VOLTAGEGRADE is not null  and \n" + 
							"LINEDEVICE.Sfss ='T'\n" + 
							filterSqlString+
							" group by LINKEDDEPT,monitoringtypes) tab2\n" + 
							"where tab1.ssws = tab2.LINKEDDEPT(+)\n" + 
							"and tab1.jczy = tab2.jclx(+) )group by ssws, sswsmc, pms_xh, jcdl) group by ssws, sswsmc, pms_xh order by pms_xh )\n" + 
							"select 'all' ssws,'国家电网公司' sswsmc,sum(tab.dljcljrs) dljcljrs,sum(tab.dljclzs) dljclzs,sum(tab.dltdjcljrs) dltdjcljrs,\n" + 
							"sum(tab.dltdjclzs) dltdjclzs,sum(tab.dltdkzljrs) dltdkzljrs,sum(tab.dltdkzlzs) dltdkzlzs,   sum(tab.jrshj) jrshj,sum(tab.zshj) zshj,\n" + 
							"(case when sum(tab.zshj) = 0 then '-' else to_char((sum(tab.jrshj) / sum(tab.zshj) * 100),'990.99')||'%' end) SSJRL,\n" + 
							"(case when sum(tab.dljclzs) = 0 then '-' else to_char((sum(tab.dljcljrs) / sum(tab.dljclzs) * 100),'990.99')||'%' end) DLJCLSSJRL,\n" + 
							"(case when sum(tab.dltdjclzs) = 0 then '-' else to_char((sum(tab.dltdjcljrs) / sum(tab.dltdjclzs) * 100),'990.99')||'%' end) DLTDJCLSSJRL\n" + 
							" from tab union all   select * from tab where sswsmc not like '国网运行公司'";

 
					  

			  
			   result = hibernateDao_ztjc.executeSqlQuery(querySql);
			   result = transToColumns(result,cols);
			   
			   qro.setItems(result);
			   qro.addDicItems(wrapDictList());
			   return qro;
			   
		   } catch (Exception e) {
			   logger.info("变电装置查询管理，加载设备列表出错", e);
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
			String jclxsql = "SELECT TYPENAME,TYPECODE FROM MW_APP.CMST_MONITORINGTYPE WHERE TYPECODE LIKE '03%'";
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
				   for(int i = 0; i < qc.length; i++) {
					   
					   if("LINKEDCABLEANDCHANNELNAME".equals(qc[i].split("=")[0])) {
						 //线路名称条件
						   filterString += " AND LINEDEVICE.LINKEDCABLEANDCHANNELNAME like '%" + qc[i].split("=")[1] + "%'";
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
							   filterString += " AND LINEDEVICE.VOLTAGEGRADE in ("+dydjString+")";
					   }else if("yxzt".equals(qc[i].split("=")[0])) {
						   //运行状态
						   String[] yxzt = qc[i].split("=")[1].split(",");
							  String yxztString = "";
							   for (int j = 0; j < yxzt.length; j++) {
								   yxztString = yxztString+"'"+yxzt[j]+"'";
								if (j!=yxzt.length-1) {
									yxztString = yxztString+",";
								}
							}
							   filterString += " AND status in ("+yxztString+")";
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
			return filterString;
		}
}

