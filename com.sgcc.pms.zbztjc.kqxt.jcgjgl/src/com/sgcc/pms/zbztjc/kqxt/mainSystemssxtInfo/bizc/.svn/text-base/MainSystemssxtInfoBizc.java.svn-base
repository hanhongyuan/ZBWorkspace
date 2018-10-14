package com.sgcc.pms.zbztjc.kqxt.mainSystemssxtInfo.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.kqxt.Constants;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class MainSystemssxtInfoBizc implements IMainSystemssxtInfoBizc{

	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(MainSystemssxtInfoBizc.class);
	
	@Override
	public QueryResultObject querySdInfo(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		if(null != params.getFilter()){
			System.out.println(params.getFilter().toString());
			String xtId = params.getFilter().toString().split("=")[1].trim();				//所属系统ID
			String filter = " AND ssxt = '"+xtId+"'";
			String cols = "OBJ_ID,XLMC,GJZS,ALLHCNT,ZS012001,ZS013001,ZS013002,ZS013003,ZS013004,ZS013005,ZS014001,ZS018001,ZS018002,ZS013006";
			String sql = 
				"select CAST(temp.GLOBAL_ID AS VARCHAR2(42)) XLID,temp.XLMC,\n" +
						"                   (ZS012001 + ZS013001 + ZS013002 + ZS013003  + ZS013004 +\n" + 
						"                   ZS013005 + ZS014001 + ZS018001 + ZS018002 + ZS013006) GJZS,\n" + 
						"                   temp.SSJRZZS as HCNT,temp.ZS012001,temp.ZS013001,temp.ZS013002,\n" + 
						"                   temp.ZS013003,temp.ZS013004,temp.ZS013005,\n" + 
						"                   temp.ZS014001,temp.ZS018001,temp.ZS018002,temp.ZS013006\n" + 
						"                   from (select global_id,\n" + 
						"                   xlmc,sum(ssjrzz) ssjrzzs,sum(gtqx) ZS012001,sum(dxhc) ZS013001,\n" + 
						"                   sum(dxwd) ZS013002,sum(wfzd) ZS013003,\n" + 
						"                   sum(fpjc) ZS013004,sum(fbjc) ZS013005,sum(jyzwh) ZS014001,\n" + 
						"                   sum(wqxjc) ZS018001,sum(txjc) ZS018002,sum(xlwd) ZS013006\n" + 
						"                   from (select xl.sbbm global_id,xl.xlmc,\n" + 
						"                   decode(ssjr.ishandled, 'T', count(monitoringtype), 0) ssjrzz,\n" + 
						"                   (case monitoringtype when '012001' then count(monitoringtype)\n" + 
						"                   else 0 end) gtqx, (case monitoringtype when '013001' then\n" + 
						"                   count(monitoringtype) else 0 end) dxhc,\n" + 
						"                   (case monitoringtype when '013002' then count(monitoringtype)\n" + 
						"                   else 0 end) dxwd,(case monitoringtype when '013003' then\n" + 
						"                   count(monitoringtype) else  0 end) wfzd,\n" + 
						"                   (case monitoringtype when '013004' then count(monitoringtype)\n" + 
						"                   else 0  end) fpjc, (case monitoringtype when '013005' then\n" + 
						"                   count(monitoringtype) else  0 end) fbjc,\n" + 
						"                   (case monitoringtype when '014001' then count(monitoringtype)\n" + 
						"                   else 0 end) jyzwh,(case monitoringtype\n" + 
						"                   when '018001' then count(monitoringtype) else 0 end) wqxjc,\n" + 
						"                   (case monitoringtype when '018002' then count(monitoringtype)\n" + 
						"                   else 0 end) txjc, (case ssjr.monitoringtype\n" + 
						"                   when '013006' then count(ssjr.monitoringtype) else 0 end) xlwd\n" + 
						"                   from (select distinct devicecode,linkedline\n" + 
						"                   from mw_app.cmsv_linedevicef l, mw_app.cmst_devicemonitype d\n" + 
						"                   where  l.devicecode = d.linkeddevice(+) and LINKEDEQUIPMENTDY IS NOT NULL\n" + 
						"                   and d.monitoringtype in ('012001','013001','013004','013005','013006','014001','018001','018002')\n" + 
						"                   and l.linkedprovicedept  is not null) dev,\n" + 
						"                   MW_APP.CMSV_ALARM_SDINFOF ssjr,\n" + 
						"                   mw_app.CMST_SB_ZWYC_XL xl\n" + 
						"                   where dev.devicecode = ssjr.DEVICECODE(+)\n" + 
						"                   and xl.sbbm = dev.linkedline(+)\n" ;
						sql += filter;
						sql += " group by xl.sbbm, xl.xlmc,ssjr.monitoringtype,ssjr.ishandled)\n" + 
						"                   group by global_id, xlmc) temp order by GJZS desc";
						
				try{
					List result = new ArrayList();
					List count = new ArrayList();
					result = hibernateDao_ztjc.executeSqlQuery(sql);
					result = transToColumns(result,cols);
					count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
					   
					qro.setItems(result);
					qro.setItemCount(((Number) (count.get(0))).intValue());
					
				}catch(Exception e){
					e.printStackTrace();
					log.info("执行输电查询时发生异常!",e);
				}
		}else{
			log.info("传入的系统ID为空!");
		}
		return qro;
	}

	@Override
	public QueryResultObject queryBdInfo(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		if(null != params.getFilter()){
			String xtId = params.getFilter().toString().split("=")[1].trim();					//所属系统ID
			String filter = " AND ssxt = '"+xtId+"'";
			String cols = "OBJ_ID,BDZMC,GJZS,ALLHCNT,ZS021001,ZS021002,ZS021003,ZS021004,ZS021005,ZS022001,ZS023001,ZS024001," +
					"ZS024002,ZS024003,ZS024004,ZS024005,ZS024006";
			String sql = "select CAST(temp.BDZID AS VARCHAR2(42)) OBJ_ID,temp.BDZMC,\n" +
							"                (zzzs021001 + zzzs021002 + zzzs021003 + zzzs021004 + zzzs021005 +\n" + 
							"                zzzs022001 + zzzs023001 + zzzs024001 + zzzs024002 + zzzs024003 +\n" + 
							"                zzzs024004 + zzzs024005 + zzzs024006  ) GJZS ,\n" + 
							"                BDSSJRZZS as HCNT,temp.ZZZS021001,temp.ZZZS021002,\n" + 
							"                temp.ZZZS021003,temp.ZZZS021004,temp.ZZZS021005,temp.ZZZS022001,\n" + 
							"                temp.ZZZS023001,temp.ZZZS024001,temp.ZZZS024002,temp.ZZZS024003,\n" + 
							"                temp.ZZZS024004,temp.ZZZS024005,temp.ZZZS024006\n" + 
							"                from (select bdzid,bdzmc,\n" + 
							"                sum(bdssjrzzs) bdssjrzzs,sum(zzzs021001) zzzs021001,\n" + 
							"                sum(zzzs021002) zzzs021002,sum(zzzs021003) zzzs021003,\n" + 
							"                sum(zzzs021004) zzzs021004,sum(zzzs021005) zzzs021005,\n" + 
							"                sum(zzzs022001) zzzs022001,sum(zzzs023001) zzzs023001,\n" + 
							"                sum(zzzs024001) zzzs024001,sum(zzzs024002) zzzs024002,\n" + 
							"                sum(zzzs024003) zzzs024003,sum(zzzs024004) zzzs024004,\n" + 
							"                sum(zzzs024005) zzzs024005,sum(zzzs024006) zzzs024006\n" + 
							"                from (select T.bdzid,T.bdzmc,T.MONITORINGTYPE,T.bdssjrzzs,\n" + 
							"                (decode(T.MONITORINGTYPE, '021001', zzzs, 0)) zzzs021001,\n" + 
							"                (decode(T.MONITORINGTYPE, '021002', zzzs, 0)) zzzs021002,\n" + 
							"                (decode(T.MONITORINGTYPE, '021003', zzzs, 0)) zzzs021003,\n" + 
							"                (decode(T.MONITORINGTYPE, '021004', zzzs, 0)) zzzs021004,\n" + 
							"                (decode(T.MONITORINGTYPE, '021005', zzzs, 0)) zzzs021005,\n" + 
							"                (decode(T.MONITORINGTYPE, '022001', zzzs, 0)) zzzs022001,\n" + 
							"                (decode(T.MONITORINGTYPE, '023001', zzzs, 0)) zzzs023001,\n" + 
							"                (decode(T.MONITORINGTYPE, '024001', zzzs, 0)) zzzs024001,\n" + 
							"                (decode(T.MONITORINGTYPE, '024002', zzzs, 0)) zzzs024002,\n" + 
							"                (decode(T.MONITORINGTYPE, '024003', zzzs, 0)) zzzs024003,\n" + 
							"                (decode(T.MONITORINGTYPE, '024004', zzzs, 0)) zzzs024004,\n" + 
							"                (decode(T.MONITORINGTYPE, '024005', zzzs, 0)) zzzs024005,\n" + 
							"                (decode(T.MONITORINGTYPE, '024006', zzzs, 0)) zzzs024006\n" + 
							"                from (select count(distinct info.OBJ_ID) zzzs,\n" + 
							"                decode(info.ishandled, 'T', count(info.OBJ_ID), 0) bdssjrzzs,\n" + 
							"                global_id bdzid,bdzmc,info.MONITORINGTYPE\n" + 
							"                from mw_app.CMST_SB_ZNYC_DZ bdz,mw_app.CMSV_ALARM_BDINFOF info\n" + 
							"                where info.LINKEDSTATION(+) = bdz.sbbm" ;
							sql += filter;
							sql += "	 group by sbbm, bdzmc, info.MONITORINGTYPE,info.ishandled) T) H\n" + 
							"                group by bdzid, bdzmc) temp order by GJZS desc";

						
				try{
					List result = new ArrayList();
					List count = new ArrayList();
					result = hibernateDao_ztjc.executeSqlQuery(sql);
					result = transToColumns(result,cols);
					count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
					   
					qro.setItems(result);
					qro.setItemCount(((Number) (count.get(0))).intValue());
					
				}catch(Exception e){
					e.printStackTrace();
					log.info("执行变电查询时发生异常!",e);
				}
		}else{
			log.info("传入的系统ID为空!");
		}
		return qro;
	}

	@Override
	public QueryResultObject queryDetailInfo(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		int pageSize = Integer.valueOf(params.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(params.getPageIndex()); // 开始编号
		String cols="OBJ_ID,LINKEDLINENAME,LINKEDPOLENAME,MONITORINGTYPENAME,MONITORINGTYPE,DEVICENAME,DEVICECODE,DEVICEVOLTAGE,ALARMMESSAGE,ALARMTIME,ALARMLEVEL";
		String ssxt = "";
		String type = "sd";
		if(null != params.getFilter()){
			StringBuffer filterSql = new StringBuffer();
			String sql = "";
				String[] filter = params.getFilter().toString().trim().split("&");	//对传来的参数进行分割
				
				for (int i = 0; i < filter.length; i++) {
					String filterKey = filter[i].split("=")[0].trim();			//变量名称
					String filterValue = filter[i].split("=")[1].trim();		//变量值
					if(null != filterValue && StringUtils.isNotBlank(filterValue)){
						if(Constants.JCLX.equals(filterKey)){						//监测类型
							filterSql.append(" AND MONITORINGTYPE IN('"+filterValue.replace(",","','")+"')");
						}else if(Constants.BDZ.equals(filterKey)){					//变电站编码
							filterSql.append(" AND LINKEDSTATION  IN('"+filterValue.replace(",","','")+"')");
						}else if(Constants.XLMC.equals(filterKey)){					//变电站编码
							filterSql.append(" AND LINKEDLINE IN('"+filterValue.replace(",","','")+"')");
						}else if(Constants.ISHANDLED.equalsIgnoreCase(filterKey)){	//是否处理
							if(Constants.T.equals(filterValue)){
								filterSql.append(" AND ISHANDLED = 'T' ");
							}
						}else if(Constants.SSXT.equalsIgnoreCase(filterKey)){
							ssxt = "ssws = '"+filterValue +"'";
						}else if("type".equalsIgnoreCase(filterKey)){
							type = filterValue;
						}
					}
				}
				
				if(Constants.SDNAME.equalsIgnoreCase(type)){
					
					if("" == ssxt){
						sql = "select CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,T.LINKEDLINENAME,t.LINKEDPOLENAME,t.TYPENAME,t.monitoringType, \n" + 
								" t.DEVICENAME,t.devicecode,t.DEVICEVOLTAGE,t.ALARMMESSAGE,TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME,\n" + 
								" t.ALARMLEVEL "+
								" from mw_app.CMSV_ALARM_SDINFOF t where T.ALARMSOURCE = 'E'\n" + 
								"     AND MONITORINGTYPE LIKE '01%' AND ALARMNUM IS NOT NULL \n"; 
						if(null != filterSql && "" != filter.toString()){
							sql += filterSql.toString();
						}
						
					}else{
						sql = 
								"select CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,T.LINKEDLINENAME,t.LINKEDPOLENAME,t.TYPENAME,t.monitoringType, \n" + 
										" t.DEVICENAME,t.devicecode,t.DEVICEVOLTAGE,t.ALARMMESSAGE,TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME,\n" + 
										" t.ALARMLEVEL "+
										" from mw_app.CMSV_ALARM_SDINFOF t where T.ALARMSOURCE = 'E'\n" + 
										"     AND MONITORINGTYPE LIKE '01%' AND ALARMNUM IS NOT NULL\n" + 
										"     AND t.linkedline in (select l.sbbm from CMST_SB_ZWYC_XL l where " ;
										sql += ssxt;
										sql += ") ";
										if(null != filterSql && "" != filter.toString()){
											sql += filterSql.toString();
										}
					}
					

				}else if (Constants.BDNAME.equalsIgnoreCase(type)){
					sql = 
							"select CAST(t.OBJ_ID AS VARCHAR2(42)) OBJ_ID,T.LINKEDSTATIONNAME,t.LINKEDEQUIPMENTNAME,t.TYPENAME,t.monitoringType, \n" +
									" t.DEVICENAME,t.devicecode,t.DEVICEVOLTAGE,t.ALARMMESSAGE,TO_CHAR(t.ALARMTIME,'YYYY-MM-DD HH:mm:ss') ALARMTIME,t.ALARMLEVEL \n" + 
									" from mw_app.CMSV_ALARM_BDINFOF t where T.ALARMSOURCE = 'E' AND MONITORINGTYPE LIKE '02%' AND ALARMNUM IS NOT NULL \n"; 
									if(null != filterSql && "" != filter.toString()){
										sql += filterSql.toString();
									}
				}
				
				sql += " AND ALARMLEVEL IS NOT NULL ORDER BY ALARMTIME DESC";
				
				try{
					result = hibernateDao_ztjc.executeSqlQuery(sql.toString(),pageIndex, pageSize);
					result = transToColumns(result, cols);
					count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql+ ")"));

					qro.setItems(result);
					qro.setItemCount(((Number) (count.get(0))).intValue());
					
				}catch(Exception e){
					e.printStackTrace();
					log.info("执行主要系统告警信息查询统计的初始化DataGrid的查询SQL中发生异常",e);
				}
				
		}else{
			log.info("传入的系统ID为空!");
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

}
