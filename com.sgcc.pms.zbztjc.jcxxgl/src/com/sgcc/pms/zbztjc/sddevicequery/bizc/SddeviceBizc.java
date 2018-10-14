package com.sgcc.pms.zbztjc.sddevicequery.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;

import javax.annotation.Resource;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;



import com.sgcc.pms.zbztjc.myUtils.ILoggerSaveBizc;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;

import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.util.SqlFileUtil;
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

	@Resource
	private ILoggerSaveBizc loggerSaveBizc ;
	
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
		   String cols = "LINKEDDEP,DEPMC,XH,LINKEDDEPWS,WSDEPMC,LINKEDEQUIPMENTDY,DEVICEVOLTAGE,LINKEDPOLE,LINKEDLINE,LINKEDLINENAME,LINKEDPOLENAME,DEVICECATEGORY,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,STATUS,RELEASEDATE,RUNDATE,JCXX,ISRT";
		   try {
			   int pageSize = Integer.valueOf(params.getPageSize());
			   int pageIndex = Integer.valueOf(params.getPageIndex());
			   
			   String querySql = 
					   "SELECT LINKEDDEP,WSDEPMC DEPMC, XH, LINKEDDEPWS, WSDEPMC, dydj LINKEDEQUIPMENTDY, DEVICEVOLTAGE,\n" +
							   "LINKEDPOLE,LINKEDLINE,LINKEDLINENAME,LINKEDPOLENAME, DEVICECATEGORY,DEVICECODE,DEVICENAME,\n" + 
							   "MONITORINGTYPE MONITORINGTYPES,typename DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,STATUS,RELEASEDATE,RUNDATE,'查看' JCXX,\n" + 
							   "decode(mw_app.cmsv_deviceused_info.SFSS,'T','是','F','否',null,'否') ISRT FROM MW_APP.CMSV_LINEDEVICE_LOGIC_XTF t left join mw_app.cmsv_deviceused_info on mw_app.cmsv_deviceused_info.zzbm =\n" + 
							   " t.Devicecode and t.MONITORINGTYPE = mw_app.cmsv_deviceused_info.jclx  where  t.WSDEPMC is not null and t.status != '00504' and t.DEVICEVOLTAGE is not null AND dydj IS NOT NULL";

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
							   
							   querySql += " AND t.dydj in ("+dydjString+")";
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
							   
							   querySql += " AND t.status in ("+yxztString+")";
					   }  else if("monitoringtype".equals(qc[i].split("=")[0]))
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
						   querySql += " AND t.monitoringtype in ("+jclxString+")";
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
						   querySql += " and mw_app.cmsv_deviceused_info.sfss= '" + qc[i].split("=")[1] + "'";
					   }
					   else if("ssws".equals(qc[i].split("=")[0]))
					   {
						   if(!"all".equals(qc[i].split("=")[1]))
						   querySql += " and LINKEDDEPWS= '" + qc[i].split("=")[1] + "'";
					   }
					   else if("jclx".equals(qc[i].split("=")[0]))
					   {
						   
						   querySql += " and MONITORINGTYPE= '" + qc[i].split("=")[1] + "'";
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
			   loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-输电监测信息查询", "操作成功");
			   return qro;
			   
		   } catch (Exception e) {
			   logger.info("错误信息", e);
			   e.printStackTrace();
			   loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-输电监测信息查询", "操作失败");
		   }
		   return null;
	   }
	   
	   /**
	    * 输电监测信息统计
	    * @param sdxxtj
	    * @return
	    */
	   public QueryResultObject gettjxx(RequestCondition params) {
		   
		   QueryResultObject qro = new QueryResultObject();
		  
		   List result = new ArrayList();
		   try {
			   String cols = "SSWS,SSWSMC,ZS012001,ZS013001,ZS013002,ZS013003,ZS013004,ZS013005,ZS013006,ZS014001,ZS018001,ZS018002,ZS018003,JR012001,JR013001,JR013002,JR013003,JR013004,JR013005,JR013006,JR014001,JR018001,JR018002,JR018003,ZSHJ,JRSHJ,SSJRL";
			   String ssdsfilter = getSsds(params);
			   String filterSqlString = getFilter(params);
			   String querySql = 
					   "with tab as(select ssws,sswsmc,max(zs012001) zs012001,max(zs013001) zs013001,max(zs013002) zs013002,max(zs013003) zs013003,max(zs013004) zs013004,\n" +
							   "max(zs013005) zs013005,max(zs013006) zs013006,max(zs014001) zs014001,max(zs018001) zs018001,max(zs018002) zs018002,max(zs018003) zs018003,\n" + 
							   "max(jr012001) jr012001,max(jr013001) jr013001,max(jr013002) jr013002,max(jr013003) jr013003,max(jr013004) jr013004,\n" + 
							   "max(jr013005) jr013005,max(jr013006) jr013006,max(jr014001) jr014001,max(jr018001) jr018001,max(jr018002) jr018002,max(jr018003) jr018003,\n" + 
							   "max(zs012001)+max(zs013001)+max(zs013002)+max(zs013003)+max(zs013004)+\n" + 
							   "max(zs013005)+max(zs013006)+max(zs014001)+max(zs018001)+max(zs018002)+max(zs018003) zshj,\n" + 
							   "max(jr012001)+max(jr013001)+max(jr013002)+max(jr013003)+max(jr013004)+\n" + 
							   "max(jr013005)+max(jr013006)+max(jr014001)+max(jr018001)+max(jr018002)+max(jr018003) jrshj,\n" + 
							   "(case when (max(zs012001)+max(zs013001)+max(zs013002)+max(zs013003)+max(zs013004)+\n" + 
							   "max(zs013005)+max(zs013006)+max(zs014001)+max(zs018001)+max(zs018002)+max(zs018003)) = 0 then '-'\n" + 
							   "else to_char(((max(jr012001)+max(jr013001)+max(jr013002)+max(jr013003)+max(jr013004)+\n" + 
							   "max(jr013005)+max(jr013006)+max(jr014001)+max(jr018001)+max(jr018002)+max(jr018003))/\n" + 
							   "(max(zs012001)+max(zs013001)+max(zs013002)+max(zs013003)+max(zs013004)+\n" + 
							   "max(zs013005)+max(zs013006)+max(zs014001)+max(zs018001)+max(zs018002)+max(zs018003))*100),'990.99')||'%' end) SSJRL\n" + 
							   "from (select ssws,sswsmc, pms_xh,\n" + 
							   "  (case jczy when '012001' then sum(zzzs) else 0 end) zs012001,\n" + 
							   "  (case jczy when '013001' then sum(zzzs) else 0 end) zs013001,\n" + 
							   "  (case jczy when '013002' then sum(zzzs) else 0 end) zs013002,\n" + 
							   "  (case jczy when '013003' then sum(zzzs) else 0 end) zs013003,\n" + 
							   "  (case jczy when '013004' then sum(zzzs) else 0 end) zs013004,\n" + 
							   "  (case jczy when '013005' then sum(zzzs) else 0 end) zs013005,\n" + 
							   "  (case jczy when '013006' then sum(zzzs) else 0 end) zs013006,\n" + 
							   "  (case jczy when '014001' then sum(zzzs) else 0 end) zs014001,\n" + 
							   "  (case jczy when '018001' then sum(zzzs) else 0 end) zs018001,\n" + 
							   "  (case jczy when '018002' then sum(zzzs) else 0 end) zs018002,\n" + 
							   "  (case jczy when '018003' then sum(zzzs) else 0 end) zs018003,\n" + 
							   "  (case jczy when '012001' then sum(ssjrzzs) else 0 end) jr012001,\n" + 
							   "  (case jczy when '013001' then sum(ssjrzzs) else 0 end) jr013001,\n" + 
							   "  (case jczy when '013002' then sum(ssjrzzs) else 0 end) jr013002,\n" + 
							   "  (case jczy when '013003' then sum(ssjrzzs) else 0 end) jr013003,\n" + 
							   "  (case jczy when '013004' then sum(ssjrzzs) else 0 end) jr013004,\n" + 
							   "  (case jczy when '013005' then sum(ssjrzzs) else 0 end) jr013005,\n" + 
							   "  (case jczy when '013006' then sum(ssjrzzs) else 0 end) jr013006,\n" + 
							   "  (case jczy when '014001' then sum(ssjrzzs) else 0 end) jr014001,\n" + 
							   "  (case jczy when '018001' then sum(ssjrzzs) else 0 end) jr018001,\n" + 
							   "  (case jczy when '018002' then sum(ssjrzzs) else 0 end) jr018002,\n" + 
							   "  (case jczy when '018003' then sum(ssjrzzs) else 0 end) jr018003\n" + 
							   "   from (select tab1.*, decode(tab2.ssjrzzs, null, 0, tab2.ssjrzzs) ssjrzzs\n" + 
							   "  from (select d.wsid   ssws,d.wsmc sswsmc,d.zdypx pms_xh,w.jczy,\n" + 
							   "    w.zzzs from mw_app.CMST_ZB_COMM_WSPZ d,(SELECT LINKEDPROVICEDEPT,\n" + 
							   "     MONITORINGTYPE jczy, count(DEVICECODE) zzzs FROM (select *\n" + 
							   "    from mw_app.cmsv_linedevice_logic_xtf where   dydj IS NOT NULL )     LINEDEVICE where 1=1 "+filterSqlString+"\n" + 
							   "   group by LINKEDPROVICEDEPT,MONITORINGTYPE) w\n" + 
							   "    where d.wsid = w.LINKEDPROVICEDEPT(+) "+ssdsfilter+") tab1,\n" + 
							   " (select LINKEDPROVICEDEPT, monitoringtype jclx,\n" + 
							   " count(DEVICECODE) ssjrzzs from (select *\n" + 
							   " from mw_app.cmsv_linedevice_logic_xtf where  dydj IS NOT NULL )   LINEDEVICE\n" + 
							   " WHERE ISRT = '是' "+filterSqlString+" group by LINKEDPROVICEDEPT, monitoringtype) tab2\n" + 
							   " where tab1.ssws = tab2.LINKEDPROVICEDEPT(+)\n" + 
							   "   and tab1.jczy = tab2.jclx(+) )group by ssws, sswsmc, pms_xh,jczy) group by ssws, sswsmc, pms_xh order by pms_xh)\n" + 
							   "   select 'all' ssws,'国家电网公司' sswsmc,sum(zs012001) zs012001,sum(zs013001) zs013001,sum(zs013002) zs013002,sum(zs013003) zs013003,sum(zs013004) zs013004,\n" + 
							   "sum(zs013005) zs013005,sum(zs013006) zs013006,sum(zs014001) zs014001,sum(zs018001) zs018001,sum(zs018002) zs018002,sum(zs018003) zs018003,\n" + 
							   "sum(jr012001) jr012001,sum(jr013001) jr013001,sum(jr013002) jr013002,sum(jr013003) jr013003,sum(jr013004) jr013004,\n" + 
							   "sum(jr013005) jr013005,sum(jr013006) jr013006,sum(jr014001) jr014001,sum(jr018001) jr018001,sum(jr018002) jr018002,sum(jr018003) jr018003,\n" + 
							   "   sum(tab.zshj) zshj,sum(tab.jrshj) jrshj,\n" + 
							   "    (case when sum(tab.zshj) = 0 then '-' else to_char((sum(tab.jrshj) / sum(tab.zshj) * 100),'990.99')||'%' end) SSJRL  from tab\n" + 
							   "    union all   select * from tab where sswsmc not like '国网运行公司'";

						
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
							filterString += " AND dydj in ("+dydjString+")";
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
					   }  else if("monitoringtype".equals(qc[i].split("=")[0]))
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
			return filterString;
		}
	   private List<DicItems> wrapDictList() {
			List<DicItems> dicts = new ArrayList<DicItems>();
			String yxztsql = "select codename,code from mw_app.cmst_commoncode e where e.parentcode = '005'";
			dicts.add(translateFromDBT("yxzt",yxztsql));
    
			String jclxsql = "SELECT TYPENAME,TYPECODE FROM MW_APP.CMST_MONITORINGTYPE WHERE TYPECODE LIKE '01%'";
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
		    
}

