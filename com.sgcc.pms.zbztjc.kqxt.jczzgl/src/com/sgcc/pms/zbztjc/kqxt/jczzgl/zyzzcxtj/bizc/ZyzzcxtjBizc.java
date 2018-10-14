package com.sgcc.pms.zbztjc.kqxt.jczzgl.zyzzcxtj.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.pms.zbztjc.kqxt.jczzgl.util.Constants;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
/**
 * 输电装置查询
 * 
 * @author wml
 * 
 */
public class ZyzzcxtjBizc implements IZyzzcxtjBizc{


	private final Log log = LogFactory.getLog(ZyzzcxtjBizc.class);
	@Resource
	private IHibernateDao hibernateDao_ztjc;


	
	 public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}
	 
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
		public QueryResultObject query(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
			int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
			
			StringBuffer SdquerySql = new StringBuffer();
			StringBuffer BdquerySql = new StringBuffer();
			if (null != queryCondition.getFilter()) {
				String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
				for (int i = 0; i < filter.length; i++) {
					// 判断投运日期,生产厂家,变电站名称
					String filterKey = filter[i].split("=")[0].trim();
					String filterValue = filter[i].split("=")[1].trim();
					if ("ssxt".equals(filterKey)) {
						SdquerySql.append(" and xtid in ('");
						SdquerySql.append(filterValue.replace(",", "','")); 
						SdquerySql.append("')");
						BdquerySql.append(" and xtid in ('");
						BdquerySql.append(filterValue.replace(",", "','")); 
						BdquerySql.append("')");
					} else if (Constants.SSDW.equals(filterKey)) {
						SdquerySql.append(" and LINKEDDEPWS in ('");
						SdquerySql.append(filterValue.replace(",", "','")); 
						SdquerySql.append("')");
						BdquerySql.append(" and LINKEDDEPWS in ('");
						BdquerySql.append(filterValue.replace(",", "','")); 
						BdquerySql.append("')");
					} else if (Constants.DYDJ.equals(filterKey)) {
						SdquerySql.append(" and LINKEDEQUIPMENTDY in ('");
						SdquerySql.append(filterValue.replace(",", "','"));
						SdquerySql.append("')");
						BdquerySql.append(" and LINKEDEQUIPMENTDY in ('");
						BdquerySql.append(filterValue.replace(",", "','"));
						BdquerySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
							SdquerySql.append(" and MONITORINGTYPES in ('");
							SdquerySql.append(filterValue.replace(",", "','"));
							SdquerySql.append("')");
						}
					} else if (Constants.TYRQ.equals(filterKey)) {
						String startTime = null;
						String endTime = null;
						int filterVlength =filterValue.length();
						if(",".equals(filterValue.substring(0, 1))){
						endTime = filterValue.split(",")[1];
						SdquerySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
						startTime = filterValue.split(",")[0];
						SdquerySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}else{
						startTime = filterValue.split(",")[0];
						SdquerySql.append(" and RUNDATE>=to_date('" + startTime
									+ "','yyyy-MM-dd HH24:Mi:ss')");
						endTime = filterValue.split(",")[1];
						SdquerySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}
					}else if (Constants.BDZMC.equals(filterKey)){
						BdquerySql.append(" and LINKEDSTATIONNAME LIKE '%");
						BdquerySql.append(filterValue);
						BdquerySql.append("%' ");
					}else if (Constants.XLMC.equals(filterKey)){
						SdquerySql.append(" and LINKEDLINENAME LIKE '%");
						SdquerySql.append(filterValue);
						SdquerySql.append("%' ");
					}
				}
			}

			String sql="select * from (" +
					"SELECT OBJ_ID,LINKEDDEPWS,\n" + 
					"     WSDEPMC,\n" + 
					"      LINKEDLINENAME ||' '||LINKEDPOLENAME YCSBNAME,\n" + 
					"     LINKEDLINENAME,\n" +
					"     LINKEDPOLENAME,\n" +
					"     DEVICEVOLTAGE,\n" + 
					"     DEVICECATEGORY,\n" + 
					"     DEVICECATEGORY_DISPLAY,\n" + 
					"     DEVICENAME,\n" + 
					"     DEVICEMODEL,\n" + 
					"     MANUFACTURER,\n" + 
					"     RUNDATE,cast('查看' as varchar(42)) LOOKUP,\n" + 
					"     xtid,xtmc,DEVICECODE,MONITORINGTYPES,"+
					"     'SD' TYPE,\n" + 
					"   LINKEDPOLE LINKEDPOLE "+
					"FROM MW_APP.CMSV_LINEDEVICEf t where 1=1 \n" + SdquerySql.toString()+
					"  order by XH ) "+
					"union all\n" + 
					"select * from ("+
					"  SELECT OBJ_ID,LINKEDDEPWS,\n" + 
					"  WSDEPMC,\n" + 
					"  LINKEDSTATIONNAME||' '||LINKEDEQUIPMENTNAME  YCSBNAME,\n" + 
					"   LINKEDSTATIONNAME LINKEDLINENAME,\n" + 
					"   LINKEDEQUIPMENTNAME LINKEDPOLENAME,\n" + 
					"   DEVICEVOLTAGE,\n" + 
					"  DEVICECATEGORY,\n" + 
					"  DEVICECATEGORY_DISPLAY,\n" + 
					"    DEVICENAME,\n" + 
					"     DEVICEMODEL,\n" + 
					"     MANUFACTURER,\n" + 
					"     RUNDATE,cast('查看' as varchar(42)) LOOKUP,\n" + 
					"     xtid,xtmc,DEVICECODE,MONITORINGTYPES,"+
					"     'BD' TYPE,\n" +
					"  'BD' LINKEDPOLE "+
					"FROM MW_APP.cmsv_transfdevicef t where 1=1 "+BdquerySql.toString()+
					"  order by XH) ";
			try{
				String cols = "OBJ_ID,LINKEDDEPWS,WSDEPMC,YCSBNAME,LINKEDLINENAME,LINKEDPOLENAME,DEVICEVOLTAGE,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,DEVICENAME,DEVICEMODEL,MANUFACTURER,RUNDATE,LOOKUP,XTID,XTMC,DEVICECODE,MONITORINGTYPES,TYPE,LINKEDPOLE";
				result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex, pageSize);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
				
			}catch(Exception e){
				e.printStackTrace();
				log.info("执行输电监测装置查询统计的初始化DataGrid的查询SQL中发生异常",e);
			}
			
			return qro;
		}
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
		public QueryResultObject statBySsxt(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			StringBuffer SdquerySql = new StringBuffer();
			StringBuffer BdquerySql = new StringBuffer();
			StringBuffer querySql = new StringBuffer();
			StringBuffer querySq = new StringBuffer();
			if (null != queryCondition.getFilter()) {
				String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
				for (int i = 0; i < filter.length; i++) {
					// 判断投运日期,生产厂家,变电站名称
					String filterKey = filter[i].split("=")[0].trim();
					String filterValue = filter[i].split("=")[1].trim();
					if (Constants.SSXT.equals(filterKey)) {
						querySq.append(" and kkk.obj_id  in ('");
						querySq.append(filterValue.replace(",", "','")); 
						querySq.append("')");
						querySql.append(" and XTID  in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					} else if (Constants.DYDJ.equals(filterKey)) {
						String[] filterValueArr=filterValue.split(",");
						querySql.append(" and linkedequipmentdy in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.SSDW.equals(filterKey)) {
						querySql.append(" and linkeddepws in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
						querySql.append(" and monitoringtypes in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
						}
					} else if (Constants.TYRQ.equals(filterKey)) {
						String startTime = null;
						String endTime = null;
						int filterVlength =filterValue.length();
						if(",".equals(filterValue.substring(0, 1))){
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}else{
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
									+ "','yyyy-MM-dd HH24:Mi:ss')");
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}
					}else if (Constants.BDZMC.equals(filterKey)){
						BdquerySql.append(" and LINKEDSTATIONNAME LIKE '%");
						BdquerySql.append(filterValue);
						BdquerySql.append("%' ");
					}else if (Constants.XLMC.equals(filterKey)){
						SdquerySql.append(" and LINKEDLINENAME LIKE '%");
						SdquerySql.append(filterValue);
						SdquerySql.append("%' ");
					}
				}
			}
			
			String  tjsql =  "select '' dwbm,\n" +
					"       '合计' dwmc,\n" + 
					"       d.SDZZS,\n" + 
					"       d.XLS,\n" + 
					"       d.SDSSJRZZS,\n" + 
					"       d.SDSSJRL,\n" + 
					"       d2.BDZZS,\n" + 
					"       d2.BDZS,\n" + 
					"       d2.BDSSJRZZS,\n" + 
					"       d2.BDSSJRL\n" + 
					"  from (select count(distinct t.devicecode) as SDZZS,\n" + 
					"               count(distinct t.linkedline) xls,\n" + 
					"               count(distinct(decode(ssjr.SFSS, 'T', t.devicecode, null))) as SDSSJRZZS,\n" + 
					"               case\n" + 
					"                 when count(distinct t.devicecode) = 0 then\n" + 
					"                  '-'\n" + 
					"                 else\n" + 
					"                  round(count(distinct(decode(ssjr.SFSS,\n" + 
					"                                              'T',\n" + 
					"                                              t.devicecode,\n" + 
					"                                              null))) /\n" + 
					"                        count(distinct t.devicecode),\n" + 
					"                        2) * 100 || '%'\n" + 
					"               end as SDSSJRL\n" + 
					"          from (select * from mw_app.cmst_kq_xt ) de,\n" + 
					"               (select *\n" + 
					"                  from mw_app.cmsv_linedevicef\n" + 
					"                 where  LINKEDEQUIPMENTDY IS NOT NULL  AND linkedprovicedept IS  NOT NULL "+querySql.toString()+" "+SdquerySql.toString() +" ) t,\n" + 
					"             (select monitor.linkeddevice,\n" + 
					"                                 monitor.monitoringtype,\n" + 
					"                                 info.SFSS\n" + 
					"                            from mw_app.cmst_devicemonitype  monitor,\n" + 
					"                                 mw_app.cmsv_deviceused_info info\n" + 
					"                           where monitor.linkeddevice = info.ZZBM(+)\n" + 
					"                             and monitor.monitoringtype = info.JCLX(+)) ssjr\n" + 
					"         where t.devicecode = ssjr.linkeddevice(+)\n" + 
					"           and t.LINKEDEQUIPMENTDY is not null\n" + 
					"           and de.obj_id = t.xtID(+)) d,\n" + 
					"       (select count(distinct t.devicecode) as BDZZS,\n" + 
					"               count(distinct(decode(ssjr.SFSS, 'T', t.devicecode, null))) as BDSSJRZZS,\n" + 
					"               count(distinct t.linkedstation) bdzs,\n" + 
					"               case\n" + 
					"                 when count(distinct t.devicecode) = 0 then\n" + 
					"                  '-'\n" + 
					"                 else\n" + 
					"                  round(count(distinct(decode(ssjr.SFSS,\n" + 
					"                                              'T',\n" + 
					"                                              t.devicecode,\n" + 
					"                                              null))) /\n" + 
					"                        count(distinct t.devicecode),\n" + 
					"                        2) * 100 || '%'\n" + 
					"               end as BDSSJRL\n" + 
					"          from (select * from mw_app.cmst_kq_xt) de,\n" + 
					"               (select *\n" + 
					"                  from mw_app.cmsv_transfdevicef\n" + 
					"                 where  LINKEDEQUIPMENTDY IS NOT NULL AND linkedprovicedept IS  NOT NULL "+querySql.toString()+" "+BdquerySql.toString()+" ) t,\n" + 
					"               (select monitor.linkeddevice,\n" + 
					"                                 monitor.monitoringtype,\n" + 
					"                                 info.SFSS\n" + 
					"                            from mw_app.cmst_devicemonitype  monitor,\n" + 
					"                                 mw_app.cmsv_deviceused_info info\n" + 
					"                           where monitor.linkeddevice = info.ZZBM(+)\n" + 
					"                             and monitor.monitoringtype = info.JCLX(+)) ssjr\n" + 
					"         where t.devicecode = ssjr.linkeddevice(+)\n" + 
					"           and t.LINKEDEQUIPMENTDY is not null\n" + 
					"           and de.obj_id = t.xtID(+)) d2\n" + 
					"UNION ALL\n" +
					" select * from ( "+
					"SELECT   DWBM, kkk.xtmc DWMC, SDZZS, XLS,SDSSJRZZS, SDSSJRL,   BDZZS, BDZS,  BDSSJRZZS, BDSSJRL \n" + 
					"  FROM (select d.obj_id   DWBM,\n" + 
					"               d.xtmc DWMC,\n" + 
					"               d.SDZZS,\n" + 
					"               d.XLS,\n" + 
					"               d.SDSSJRZZS,\n" + 
					"               d.SDSSJRL,\n" + 
					"               d2.BDZZS,\n" + 
					"               d2.BDZS,\n" + 
					"               d2.BDSSJRZZS,\n" + 
					"               d2.BDSSJRL\n" + 
					"          from (SELECT ss.*,\n" + 
					"                       case\n" + 
					"                         when ss.SDZZS = 0 then\n" + 
					"                          '-'\n" + 
					"                         else\n" + 
					"                          round(ss.SDSSJRZZS / ss.SDZZS, 2) * 100 || '%'\n" + 
					"                       end as SDSSJRL\n" + 
					"                  from (select de.xtmc,\n" + 
					"                               de.obj_id,\n" + 
					"                               count(distinct(decode(t.linkedequipmentdy,\n" + 
					"                                                     null,\n" + 
					"                                                     null,\n" + 
					"                                                     t.devicecode))) as SDZZS,\n" + 
					"                               count(distinct t.LINKEDLINE) XLS,\n" + 
					"                               count(distinct(decode(t.linkedequipmentdy,\n" + 
					"                                                     null,\n" + 
					"                                                     null,\n" + 
					"                                                     decode(ssjr.SFSS,\n" + 
					"                                                            'T',\n" + 
					"                                                            t.devicecode,\n" + 
					"                                                            null)))) as SDSSJRZZS\n" + 
					"\n" + 
					"                          from (select xt.* from mw_app.cmst_kq_xt xt) de,\n" + 
					"                               (select *\n" + 
					"                                  from mw_app.cmsv_linedevicef\n" + 
					"                                 where  LINKEDEQUIPMENTDY IS NOT NULL AND linkedprovicedept IS  NOT NULL "+querySql.toString()+" "+SdquerySql.toString()+" ) t,\n" + 
					"                               (select monitor.linkeddevice,\n" + 
					"                                 monitor.monitoringtype,\n" + 
					"                                 info.SFSS\n" + 
					"                            from mw_app.cmst_devicemonitype  monitor,\n" + 
					"                                 mw_app.cmsv_deviceused_info info\n" + 
					"                           where monitor.linkeddevice = info.ZZBM(+)\n" + 
					"                             and monitor.monitoringtype = info.JCLX(+)) ssjr\n" + 
					"                         where t.devicecode = ssjr.linkeddevice(+)\n" + 
					"                           and de.obj_id = t.xtID(+)\n" +
					"                         group by de.xtmc, de.obj_id ) ss) d,\n" + 
					"               (select ss.*,\n" + 
					"                       case\n" + 
					"                         when ss.BDZZS = 0 then\n" + 
					"                          '-'\n" + 
					"                         else\n" + 
					"                          round(ss.BDSSJRZZS / ss.BDZZS, 2) * 100 || '%'\n" + 
					"                       end as BDSSJRL\n" + 
					"                  from (select de.xtmc,\n" + 
					"                               de.obj_id,\n" + 
					"                               count(distinct(decode(t.linkedequipmentdy,\n" + 
					"                                                     null,\n" + 
					"                                                     null,\n" + 
					"                                                     t.devicecode))) as BDZZS,\n" + 
					"                               count(distinct t.linkedstation) BDZS,\n" + 
					"                               count(distinct(decode(t.linkedequipmentdy,\n" + 
					"                                                     null,\n" + 
					"                                                     null,\n" + 
					"                                                     decode(ssjr.SFSS,\n" + 
					"                                                            'T',\n" + 
					"                                                            t.devicecode,\n" + 
					"                                                            null)))) as BDSSJRZZS\n" + 
					"                          from (select *\n" + 
					"                                  from mw_app.cmsv_transfdevicef\n" + 
					"                                 where  LINKEDEQUIPMENTDY IS NOT NULL AND linkedprovicedept IS  NOT NULL "+querySql.toString()+" "+BdquerySql.toString()+" ) t,\n" + 
					"                               (select xt.* from mw_app.cmst_kq_xt xt) de,\n" + 
					"                               (select monitor.linkeddevice,\n" + 
					"                                 monitor.monitoringtype,\n" + 
					"                                 info.SFSS\n" + 
					"                            from mw_app.cmst_devicemonitype  monitor,\n" + 
					"                                 mw_app.cmsv_deviceused_info info\n" + 
					"                           where monitor.linkeddevice = info.ZZBM(+)\n" + 
					"                             and monitor.monitoringtype = info.JCLX(+)) ssjr\n" + 
					"                         where t.devicecode = ssjr.linkeddevice(+)\n" + 
					"                           and de.obj_id = t.xtID(+)\n" +
					"                         group by de.xtmc, de.obj_id ) ss) d2\n" + 
					"    where   d.obj_id = d2.obj_id\n" + 
					"   ) temp, "+
					"  mw_app.cmst_kq_xt  kkk  where kkk.xtmc=temp.dwmc(+) \n"+querySq.toString()+
					" order by kkk.xtmc )";
			String sql ="SELECT DWBM,DWMC,SDZZS,XLS,SDSSJRZZS,SDSSJRL,BDZZS,BDZS,BDSSJRZZS,BDSSJRL FROM ("+tjsql+") ";
	
			try {
				cols = "DWBM,DWMC,SDZZS,XLS,SDSSJRZZS,SDSSJRL,BDZZS,BDZS,BDSSJRZZS,BDSSJRL";
				log.info(sql);
				result = hibernateDao_ztjc.executeSqlQuery(sql);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc
						.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());

			} catch (Exception e) {
				log.info("执行按电压等级统计输电告警信息时出错！", e);
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
		private List<Map> transToColumns(List<Object[]> list, String columns) {
			List<Map> items = new ArrayList();
			String[] cols = columns.split("\\,");
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				for (int m = 0; m < cols.length; m++) {
					map.put(cols[m].trim(), list.get(i)[m]);
				}
				items.add(map);
			}
			return items;
		}

		@SuppressWarnings("unchecked")
		@Override
		public QueryResultObject getSdDetailList(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
			int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
			StringBuffer querySql = new StringBuffer();
			
			if (null != queryCondition.getFilter()) {
				String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
				for (int i = 0; i < filter.length; i++) {
					// 判断投运日期,生产厂家,变电站名称
					String filterKey = filter[i].split("=")[0].trim();
					String filterValue = filter[i].split("=")[1].trim();
					if (Constants.SSXT.equals(filterKey)) {
						querySql.append(" and XTID in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					}else if (Constants.SSDW.equals(filterKey)) {
						querySql.append(" and linkeddepws in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					} else if (Constants.DYDJ.equals(filterKey)) {
						querySql.append(" and LinkedEquipmentDY in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
						querySql.append(" and MONITORINGTYPE in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
						}
					} else if (Constants.TYRQ.equals(filterKey)) {
						String startTime = null;
						String endTime = null;
						int filterVlength =filterValue.length();
						if(",".equals(filterValue.substring(0, 1))){
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}else{
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
									+ "','yyyy-MM-dd HH24:Mi:ss')");
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}
					}else if (Constants.SCCJ.equals(filterKey)){
						querySql.append(" and MANUFACTURER LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}else if (Constants.XLMC.equals(filterKey)){
						querySql.append(" and LINKEDLINENAME LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}else if (Constants.YXZT.equals(filterKey)){
						querySql.append(" and status in ('");
						querySql.append(filterValue);
						querySql.append("') ");
					}else if (Constants.SFSS.equals(filterKey)){
						if("all".equals(filterValue.toString())){
						querySql.append(" ");
						}else if("F".equals(filterValue)){
							querySql.append(" and (u.sfss is null or u.sfss ='");
							querySql.append(filterValue);
							querySql.append("')");
						}else{
						querySql.append(" and t.SFSS = '");
						querySql.append(filterValue);
						querySql.append("'");
						}
					}
				}
			}
			
			String sql = "SELECT T.*, '查看' LOOKUP,  nvl(DECODE(SFSS, 'T', '是', 'F', '否'), '否') ISRT  \n " +
					" FROM MW_APP.CMSV_LINEDEVICEf T where  T.WSDEPMC IS NOT NULL \n"+
					" AND T.LINKEDEQUIPMENTDY IS NOT NULL \n"+
					" AND T.MONITORINGTYPES LIKE '01%'  and XTID IS NOT NULL "+querySql.toString();
			
		
		       
/*			String sql = "SELECT t.LINKEDDEPWS,t.LINKEDLINENAME,t.DEVICENAME,(select typename from mw_app.cmst_monitoringtype where typecode = d.MONITORINGTYPE) DEVICECATEGORY_DISPLAY,'查看' LOOKUP,nvl(DECODE(u.SFSS,'T','是','F','否'),'否')ISRT,\n" +
						" t.LINKEDPOLENAME,t.DEVICEVOLTAGE,t.DEVICECATEGORY,t.DEVICEMODEL,t.MANUFACTURER,t.RUNDATE, \n" + 
						" (select xtmc from mw_app.cmsmv_pmsapp_ud_kq_xt where obj_id = t.xtid) XTMC \n"+
						"  FROM mw_app.cmsv_linedevice T,mw_app.cmst_devicemonitype d,mw_app.cmsv_deviceused_info u \n"+
						" WHERE t.devicecode(+) = linkeddevice and LINKEDEQUIPMENTDY IS NOT NULL and T.MONITORINGTYPES LIKE '01%'  \n"+ 
						"  AND u.ZZBM(+) = linkeddevice and d.monitoringtype = u.JCLX(+)  and T.WSDEPMC IS NOT NULL"+querySql.toString();*/
						
			try {
				cols ="OBJ_ID,XTJC,XTMC,XTID,LINKEDDEP,DEPMC,XH,LINKEDDEPWS,WSDEPMC,WSDEPJC,LINKEDEQUIPMENTDY,DEVICEVOLTAGE,DYDJGDJB,BZDM,LINKEDPOLE,LINKEDPOLEOBJ,LINKEDLINE,LINKEDLINEZB,LINKEDPOLEZB,LINKEDLINENAME,LINKEDPOLENAME,LINKEDCMA,LINKEDCMA_DISPLAY,DEVICECATEGORY,DEVICECATEGORYDM,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER,REMARKS,JCXX,ISACTIVATED,LINKEDPROVICEDEPT,SFSS,LOOKUP,ISRT"; 
				result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM ("+ sql+ ")"));
				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
			} catch (Exception e) {
				e.printStackTrace();
				log.info("执行输电监测装置详细信息出错", e);
			}
			return qro;
		}

		@Override
		public QueryResultObject getFGXLSList(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
			int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
			StringBuffer querySq = new StringBuffer();
			StringBuffer querySql = new StringBuffer();
			StringBuffer ssdwquerySql = new StringBuffer();
			if (null != queryCondition.getFilter()) {
				String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
				for (int i = 0; i < filter.length; i++) {
					// 判断投运日期,生产厂家,变电站名称
					String filterKey = filter[i].split("=")[0].trim();
					String filterValue = filter[i].split("=")[1].trim();
					if (Constants.SSXT.equals(filterKey)) {
						querySql.append(" and T.SSXT in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					} else if (Constants.SSDW.equals(filterKey)) {
						ssdwquerySql.append(" and deps.wsid in ('");
						ssdwquerySql.append(filterValue.replace(",", "','")); 
						ssdwquerySql.append("')");
					} else if (Constants.DYDJ.equals(filterKey)) {
						querySql.append(" and T.TDYDJ in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
						querySq.append(" and LINKEDEQUIPMENTDY in ('");
						querySq.append(filterValue.replace(",", "','"));
						querySq.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						querySq.append(" and (");
						for(int j=0;j<filterValueArr.length;j++){
							if(j==0){
								querySq.append(" monitoringtypes like '%");
								querySq.append(filterValueArr[j]);
								querySq.append("%'");
							}else{
								querySq.append(" or monitoringtypes like '%");
								querySq.append(filterValueArr[j]);
								querySq.append("%'");
								}
							querySq.append(")");
						}
					} else if (Constants.TYRQ.equals(filterKey)) {
						String startTime = null;
						String endTime = null;
						int filterVlength =filterValue.length();
						if(",".equals(filterValue.substring(0, 1))){
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}else{
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
									+ "','yyyy-MM-dd HH24:Mi:ss')");
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}
					}else if (Constants.XLMC.equals(filterKey)){
						querySql.append(" and XLMC LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}
				}
			}
			
			String sql = "select XTMC,ZCXZ,DYDJ,SSXT,TDYDJ,XLCD,SBBM,XLMC,SSSJ,JSFS,OBJ_ID,YXDW,TYRQ,SSDW from (SELECT\n" +
				       " (SELECT XT.XTMC FROM mw_app.cmst_kq_xt XT  WHERE T.SSKQXT = XT.OBJ_ID) XTMC, \n" +
				       " (SELECT A.DMMC FROM mw_app.cmst_dw_bzzx_ggdmb  A  WHERE BZFLBM = '004'  AND A.DM = T.ZCBH) ZCXZ, \n" +
				       " (SELECT A.DYDJMC FROM mw_app.CMST_SB_PZ_SBDYDJ A WHERE A.DYDJBM = T.DYDJ) DYDJ, \n" +
				       " T.SSKQXT AS SSXT,DYDJ AS TDYDJ,XLZCD AS XLCD,SBBM,XLMC,T.SSWS AS SSSJ,JSFS,OBJ_ID, \n" +
				       " (SELECT A.SSYWDWMC FROM mw_app.CMST_SPECIALORG_UNIT_LOCEXT A  WHERE A.isc_id = T.ssws) YXDW,TYRQ, \n" +
				       " (SELECT DP.bmmc FROM mw_app.CMST_SPECIALORG_UNIT_LOCEXT DP WHERE DP.ISC_ID = T.SSWS) SSDW \n" +
				       "  FROM mw_app.cmst_sb_zwyc_xl T WHERE T.SBBM IS NOT NULL) T WHERE 1 = 1  "+querySq.toString()+"  AND T.SBBM IN(  \n" +
				       "  select linkedline from (select * from mw_app.cmsv_linedevicef  \n" +
				       "  where linkedprovicedept IS NOT NULL  and LINKEDEQUIPMENTDY IS NOT NULL "+querySq.toString()+" ) line,MW_APP.cmst_zb_comm_wspz deps  \n" +
				       "  where deps.wsid = line.linkedprovicedept(+) " +querySql.toString()+" )  AND T.SSDW is not null  ORDER BY TYRQ DESC";
			
			try{
			 cols = "XTMC,ZCXZ,DYDJ,SSXT,TDYDJ,XLCD,SBBM,XLMC,SSSJ,JSFS,DLXZ,OBJ_ID,YXDW,TYRQ,SSDW";
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());

		} catch (Exception e) {
			log.info("执行覆盖线路统计输电装置时出错！", e);
			e.printStackTrace();
		}

		return qro;
		}
 
		
		@SuppressWarnings("unchecked")
		@Override
		public QueryResultObject getBdDetailList(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
			int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
			StringBuffer querySql = new StringBuffer();
			
			if (null != queryCondition.getFilter()) {
				String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
				for (int i = 0; i < filter.length; i++) {
					// 判断投运日期,生产厂家,变电站名称
					String filterKey = filter[i].split("=")[0].trim();
					String filterValue = filter[i].split("=")[1].trim();
					if (Constants.SSXT.equals(filterKey)) {
						querySql.append(" and XTID in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					} else if (Constants.SSDW.equals(filterKey)) {
						querySql.append(" and linkeddepws in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					} else if (Constants.DYDJ.equals(filterKey)) {
						querySql.append(" and LinkedEquipmentDY in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						for(int j=0;j<filterValueArr.length;j++){
						querySql.append(" and MONITORINGTYPE in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
						}
					} else if (Constants.TYRQ.equals(filterKey)) {
						String startTime = null;
						String endTime = null;
						int filterVlength =filterValue.length();
						if(",".equals(filterValue.substring(0, 1))){
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}else{
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
									+ "','yyyy-MM-dd HH24:Mi:ss')");
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}
					}else if (Constants.SCCJ.equals(filterKey)){
						querySql.append(" and MANUFACTURER LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}else if (Constants.BDZMC.equals(filterKey)){
						querySql.append(" and LINKEDSTATIONNAME LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}else if (Constants.YXZT.equals(filterKey)){
						querySql.append(" and status in ('");
						querySql.append(filterValue);
						querySql.append("') ");
					}else if (Constants.SFSS.equals(filterKey)){
						if("all".equals(filterValue.toString())){
						querySql.append(" ");
						}else if("F".equals(filterValue)){
							querySql.append(" and (u.sfss is null or u.sfss ='");
							querySql.append(filterValue);
							querySql.append("')");
						}else{
						querySql.append(" and t.SFSS = '");
						querySql.append(filterValue);
						querySql.append("'");
						}
					}
				}
			}
			
		/*	String sql = "SELECT T.*, '查看' LOOKUP,  nvl(DECODE(SFSS, 'T', '是', 'F', '否'), '否') ISRT  \n " +
					" FROM MW_APP.CMSV_LINEDEVICE T where  T.WSDEPMC IS NOT NULL \n"+
					" AND T.LINKEDEQUIPMENTDY IS NOT NULL \n"+
					" AND T.MONITORINGTYPES LIKE '01%' "+querySql.toString();*/
		       
	String sql ="WITH tab AS( SELECT T.*,'查看' LOOKUP  FROM MW_APP.CMSV_TransfDEVICEF T where T.WSDEPMC IS NOT NULL AND T.LINKEDEQUIPMENTDY IS NOT NULL  AND T.MONITORINGTYPES LIKE '02%'  ) \n"+
			" SELECT tab.*,nvl(DECODE(SFSS,'T','是','F','否'),'否') ISRT FROM tab where 1=1  and XTID IS NOT NULL"+querySql.toString();
			
			try {
				cols = "OBJ_ID,LINKEDSTATION,LINKEDSTATIONOBJ,XTJC,XTMC,XTID,LINKEDDEP,SSBDZDYDJ,DEPMC,XH,LINKEDDEPWS,WSDEPMC,WSDEPJC,LINKEDEQUIPMENTDY,DEVICEVOLTAGE,DYDJGDJB,BZDM,LINKEDEQUIPMENT,LINKEDSTATIONZB,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,LINKEDCAC,LINKEDCAC_DISPLAY,DEVICECATEGORY,DEVICECATEGORYDM,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER,ISACTIVATED,ISACTIVATED_DISPLAY,EQUIPMENTTYPE,EQUIPMENTTYPENAME,YXLS,REMARKS,JCXX,SFKQDW,LINKEDPROVICEDEPT,SFSS,TRANSFPHASE,LOOKUP,ISRT";

				result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
				result = transToColumns(result, cols);
				count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM ("+ sql+ ")"));
				qro.setItems(result);
				qro.setItemCount(((Number) (count.get(0))).intValue());
			} catch (Exception e) {
				e.printStackTrace();
				log.info("执行输电监测装置详细信息出错", e);
			}
			return qro;
		}
		
		@Override
		public QueryResultObject getFGBDZList(RequestCondition queryCondition) {
			QueryResultObject qro = new QueryResultObject();
			List result = new ArrayList();
			List count = new ArrayList();
			String cols = "";
			int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
			int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
			StringBuffer querySq = new StringBuffer();
			StringBuffer querySql = new StringBuffer();
			StringBuffer ssdwquerySql = new StringBuffer();
			if (null != queryCondition.getFilter()) {
				String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
				for (int i = 0; i < filter.length; i++) {
					// 判断投运日期,生产厂家,变电站名称
					String filterKey = filter[i].split("=")[0].trim();
					String filterValue = filter[i].split("=")[1].trim();
					if (Constants.SSXT.equals(filterKey)) {
						querySql.append(" and T.SSXT in ('");
						querySql.append(filterValue.replace(",", "','")); 
						querySql.append("')");
					} else if (Constants.SSDW.equals(filterKey)) {
						ssdwquerySql.append(" and deps.wsid in ('");
						ssdwquerySql.append(filterValue.replace(",", "','")); 
						ssdwquerySql.append("')");
					} else if (Constants.DYDJ.equals(filterKey)) {
						querySql.append(" and T.TDYDJ in ('");
						querySql.append(filterValue.replace(",", "','"));
						querySql.append("')");
						querySq.append(" and LINKEDEQUIPMENTDY in ('");
						querySq.append(filterValue.replace(",", "','"));
						querySq.append("')");
					} else if (Constants.JCLX.equals(filterKey)){
						String[] filterValueArr=filterValue.split(",");
						querySq.append(" and (");
						for(int j=0;j<filterValueArr.length;j++){
							if(j==0){
								querySq.append(" monitoringtypes like '%");
								querySq.append(filterValueArr[j]);
								querySq.append("%'");
							}else{
								querySq.append(" or monitoringtypes like '%");
								querySq.append(filterValueArr[j]);
								querySq.append("%'");
								}
							querySq.append(")");
						}
					} else if (Constants.TYRQ.equals(filterKey)) {
						String startTime = null;
						String endTime = null;
						int filterVlength =filterValue.length();
						if(",".equals(filterValue.substring(0, 1))){
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						} else if(",".equals(filterValue.substring(filterVlength-1, filterVlength))){
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}else{
						startTime = filterValue.split(",")[0];
						querySql.append(" and RUNDATE>=to_date('" + startTime
									+ "','yyyy-MM-dd HH24:Mi:ss')");
						endTime = filterValue.split(",")[1];
						querySql.append(" and RUNDATE<=to_date('" + endTime
								+ "','yyyy-MM-dd HH24:Mi:ss')");
						}
					}else if (Constants.BDZMC.equals(filterKey)){
						querySql.append(" and BDZMC LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}
				}
			}
			
//			String sql ="SELECT * FROM (SELECT (SELECT XT.XTMC\n" +
//					"          FROM mw_app.cmsmv_pmsapp_ud_kq_xt   XT\n" + 
//					"         WHERE T.SSXT = XT.OBJ_ID) XTMC,\n" + 
//					"       (SELECT DP.MC\n" + 
//					"          FROM mw_app.CMSMV_PMSSYS_PD_DEPS    DP\n" + 
//					"         WHERE DP.OBJ_ID = T.SSDS) SSDS,\n" + 
//					"       T.BDZZJ AS BDZMC,\n" + 
//					"       (SELECT A.DMMC\n" + 
//					"          FROM mw_app.CMSMV_PMSAPP_UD_GG_DM   A\n" + 
//					"         WHERE DMLB = '004'\n" + 
//					"           AND A.DM = T.ZCSSJB) ZCXZ,\n" + 
//					"       (SELECT GY.MC\n" + 
//					"          FROM mw_app.CMSMV_PMSAPP_GY_DYDJ  GY\n" + 
//					"         WHERE GY.DM = T.DYDJ) DYDJ,\n" + 
//					"       OBJ_ID,\n" + 
//					"       SSXT,\n" + 
//					"       TYRQ,\n" + 
//					"       YXBM,\n" + 
//					"       GLOBAL_ID,\n" + 
//					"       T.DYDJ as TDYDJ,\n" + 
//					"       ZZ,\n" + 
//					"       (select A.MC\n" + 
//					"          from mw_app.cmsmv_pmssys_pd_deps_city2ws A\n" + 
//					"         where A.CITY_OBJ_ID =T.YXBM) SSDW,\n" + 
//					"       (select A.OBJ_ID\n" + 
//					"          from mw_app.cmsmv_pmssys_pd_deps_city2ws A\n" + 
//					"         where A.CITY_OBJ_ID =T.YXBM) SSDWBM\n" +
//					"  FROM mw_app.CMSMV_PMSAPP_UD_SBD_BDZ T WHERE T.GLOBAL_ID IS NOT NULL\n"+
//					") T\n" + 
//					" WHERE 1 = 1 AND T.XTMC is not null "+querySql.toString()+" AND T.GLOBAL_ID IN(  \n"+
//					" select Linkedstation from (select * from mw_app.CMSV_TRANSFDEVICE  \n"+
//					" where linkedprovicedept IS NOT NULL  and LINKEDEQUIPMENTDY IS NOT NULL  \n"+querySq.toString()+
//					" ) transf,MW_APP.CMST_PROVICE_DEPS deps  \n"+
//					" where deps.province_id = transf.linkedprovicedept(+) "+ssdwquerySql.toString()+" ) ORDER BY TYRQ DESC ";
			String sql =  " select XTMC,SSDS,BDZMC,ZCXZ,DYDJ,OBJ_ID,SSXT,TYRQ,SBBM,TDYDJ,ZZ,SSDW,SSDWBM  \n"+
					 " from (SELECT (SELECT XT.XTMC FROM mw_app.cmst_kq_xt XT WHERE T.SSXT = XT.OBJ_ID) XTMC,  \n"+
					 " (select A.WSMC from mw_app.cmst_zb_comm_wspz A  where A.WSID =T.SSWS) SSDS,  \n"+
					 "  T.BDZMC,  \n"+
					 " (SELECT A.DMMC FROM mw_app.cmst_dw_bzzx_ggdmb  A  WHERE BZFLBM = '004'  AND A.DM = T.ZCBH) ZCXZ,  \n"+
					 " (SELECT GY.DYDJMC FROM mw_app.CMST_SB_PZ_SBDYDJ  GY  WHERE GY.DYDJBM = T.DYDJ) DYDJ,  \n"+
					 " OBJ_ID, SSXT, TYRQ, SBBM, T.DYDJ as TDYDJ,  \n"+
					 " ZZ, (SELECT DP.bmmc FROM mw_app.CMST_SPECIALORG_UNIT_LOCEXT DP WHERE DP.ISC_ID = T.SSWS) SSDW,  \n"+
					 " T.SSWS SSDWBM \n"+
					 " FROM mw_app.cmst_sb_znyc_dz T WHERE T.sbbm IS NOT NULL) T  \n"+
					 " WHERE 1 = 1 "+querySql.toString()+"  AND T.sbbm in(   \n"+
					 " select Linkedstation from (select *  from MW_APP.CMSV_TRANSFDEVICE where linkedprovicedept IS NOT NULL  \n"+
					 " and LINKEDEQUIPMENTDY IS NOT NULL "+querySq.toString()+") transf, MW_APP.cmst_zb_comm_wspz deps  \n"+
					 " where deps.wsid = transf.linkedprovicedept(+) "+ssdwquerySql.toString()+" )  \n"+
					 " AND T.SSDW is not null ORDER BY TYRQ DESC ";

			try{
				 cols = "XTMC,SSDS,BDZMC,ZCXZ,DYDJ,OBJ_ID,SSXT,TYRQ,SBBM,TDYDJ,ZZ,SSDW,SSDWBM";
					log.info(sql);
					result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
					result = transToColumns(result, cols);
					count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
					qro.setItems(result);
					qro.setItemCount(((Number) (count.get(0))).intValue());
				} catch (Exception e) {
					log.info("执行sql时出错！", e);
					e.printStackTrace();
				}
			return qro;
		}
		
		 /**
		    * 主要系统输电
		    * @param sdxxtj
		    * @return
		    */
		   @SuppressWarnings("unchecked")
		public QueryResultObject loadxtsd(RequestCondition params) {
			   QueryResultObject qro = new QueryResultObject();
				List result = new ArrayList();
				List count = new ArrayList();
				StringBuffer querySq = new StringBuffer();
				StringBuffer querySql = new StringBuffer();
				StringBuffer ssdwquerySql = new StringBuffer();
				if (null != params.getFilter()) {
					String[] filter = params.getFilter().toString().split("&"); // 对传来的参数进行分割
					for (int i = 0; i < filter.length; i++) {
						// 判断投运日期,生产厂家,变电站名称
						String filterKey = filter[i].split("=")[0].trim();
						String filterValue = filter[i].split("=")[1].trim();
						if (Constants.SSXT.equals(filterKey)) {
							querySql.append(" and SSKQXT in ('");
							querySql.append(filterValue.replace(",", "','")); 
							querySql.append("')");
						}
					}
				}
			 
			   try {
				String cols = "XLID,XLMC,ZZZS,SSJRZZS,ZS012001,ZS013001,ZS013002,ZS013003,ZS013004,ZS013005,ZS014001,ZS018001,ZS018002,ZS013006";
				
				   String Sql = 
						   "select temp.sbbm XLID,temp.XLMC,\n" +
								   "(ZS012001 + ZS013001 + ZS013002 + ZS013003  + ZS013004 +\n" + 
								   "ZS013005 + ZS014001 + ZS018001 + ZS018002 + ZS013006) ZZZS,\n" + 
								   "temp.SSJRZZS,temp.ZS012001,temp.ZS013001,temp.ZS013002,\n" + 
								   "temp.ZS013003,temp.ZS013004,temp.ZS013005,\n" + 
								   "temp.ZS014001,temp.ZS018001,temp.ZS018002,temp.ZS013006\n" + 
								   "from (select sbbm,\n" + 
								   "xlmc,sum(ssjrzz) ssjrzzs,sum(gtqx) ZS012001,sum(dxhc) ZS013001,\n" + 
								   "sum(dxwd) ZS013002,sum(wfzd) ZS013003,\n" + 
								   "sum(fpjc) ZS013004,sum(fbjc) ZS013005,sum(jyzwh) ZS014001,\n" + 
								   "sum(wqxjc) ZS018001,sum(txjc) ZS018002,sum(xlwd) ZS013006\n" + 
								   "from (select xl.sbbm,xl.xlmc,\n" + 
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
								   "mw_app.cmst_sb_zwyc_xl xl\n" + 
								   "where dev.devicecode = ssjr.linkeddevice(+)\n" + 
								   "and xl.sbbm = dev.linkedline(+)  "+querySql.toString()+" \n" + 
								     
								   " group by xl.sbbm, xl.xlmc,ssjr.monitoringtype,ssjr.SFSS)\n" + 
								   "group by sbbm, xlmc) temp order by zzzs desc";

				   result = hibernateDao_ztjc.executeSqlQuery(Sql);
				   result = transToColumns(result,cols);
				   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + Sql + ")"));
				   
				   qro.setItems(result);
				   qro.setItemCount(((Number) (count.get(0))).intValue());
				  
				   
			   } catch (Exception e) {
				   e.printStackTrace();
				   log.info("输电装置查询管理，加载设备列表出错", e);
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
				String cols = "";
				String Sql = "";
				int pageSize = Integer.valueOf(params.getPageSize()); // 每页的数据量
				int pageIndex = Integer.valueOf(params.getPageIndex()); // 开始编号
				StringBuffer querySq = new StringBuffer();
				StringBuffer querySql = new StringBuffer();
				StringBuffer ssdwquerySql = new StringBuffer();
				if (null != params.getFilter()) {
					String[] filter = params.getFilter().toString().split("&"); // 对传来的参数进行分割
					for (int i = 0; i < filter.length; i++) {
						// 判断投运日期,生产厂家,变电站名称
						String filterKey = filter[i].split("=")[0].trim();
						String filterValue = filter[i].split("=")[1].trim();
						if (Constants.SSXT.equals(filterKey)) {
							querySql.append(" and SSKQXT in ('");
							querySql.append(filterValue.replace(",", "','")); 
							querySql.append("')");
						} else if ("ssxl".equals(filterKey)) {
							querySql.append(" and linkedline in ('");
							querySql.append(filterValue.replace(",", "','"));
							querySql.append("')");
							querySq.append(" and LINKEDEQUIPMENTDY in ('");
							querySq.append(filterValue.replace(",", "','"));
							querySq.append("')");
						} else if ("jclxsd".equals(filterKey)){
							String[] filterValueArr=filterValue.split(",");
							for(int j=0;j<filterValueArr.length;j++){
								querySql.append(" and ssjr.monitoringtype ='");
								querySql.append(filterValue);
								querySql.append("'");
							}
						} else if ("jclxbd".equals(filterKey)){
							String[] filterValueArr=filterValue.split(",");
							for(int j=0;j<filterValueArr.length;j++){
								querySql.append(" and a.monitoringtypes ='");
								querySql.append(filterValue);
								querySql.append("'");
							}
						} else if ("ssbdz".equals(filterKey)){
							querySql.append(" and bdz.sbbm = '");
							querySql.append(filterValue);
							querySql.append("' ");
						} else if("sd".equals(filterValue)||"".equals(filterValue))
						   {
							   cols = "PROVINCE_ID,PROVINCE_NAME,LINKEDLINE,LINKEDLINENAME,DEVICENAME,DEVICECATEGORY_DISPLAY,MONITORINGTYPE,LINKEDPOLE,LINKEDPOLENAME,LINKEDEQUIPMENTDY,DEVICECODE,DEVICEVOLTAGE,DEVICECATEGORY,DEVICEMODEL,MANUFACTURER,RUNDATE,ISRT,LOOKUP";
							   Sql = "select distinct deps.wsid PROVINCE_ID,deps.wsmc PROVINCE_NAME,dev.linkedline,\n" +
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
									"mw_app.cmst_sb_zwyc_xl xl, mw_app.cmst_zb_comm_wspz deps\n" + 
									"where dev.devicecode = ssjr.linkeddevice and dev.linkedprovicedept = deps.wsid(+)\n" + 
									"and xl.sbbm = dev.linkedline AND INSTRB(dev.monitoringtypes, ssjr.monitoringtype) != 0\n" + 
									"and  deps.wsid  is not null ";
									
						   }
						   else if("bd".equals(filterValue))
						   {
							   cols = "PROVINCE_NAME,PROVINCE_ID,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,DEVICENAME,DEVICECODE,DEVICECATEGORY_DISPLAY,MONITORINGTYPE,ISRT,LOOKUP,DEVICEVOLTAGE,DEVICECATEGORY,DEVICEMODEL,MANUFACTURER,RUNDATE";
							   Sql = 
									"select distinct deps.wsmc as province_name, deps.wsid as province_id,\n" +
									"a.linkedstationname,a.linkedequipmentname,a.DEVICENAME,\n" + 
									"a.DEVICECODE,(select typename from mw_app.cmst_monitoringtype m\n" + 
									"where m.typecode = a.monitoringtypes) DEVICECATEGORY_DISPLAY,\n" + 
									"a.monitoringtypes monitoringtype,DECODE(info.SFSS,null,'否','T','是','F','否') ISRT,\n" + 
									"'查看' LOOKUP,a.DEVICEVOLTAGE,a.DEVICECATEGORY,a.Devicemodel,a.MANUFACTURER,\n" + 
									"a.RUNDATE from (select * from mw_app.cmsv_transfdevicef where LINKEDEQUIPMENTDY IS NOT NULL\n" + 
									"and monitoringtypes in ('021001','021002','021003','021004','021005','021007','022001','023001','024001','024002','024004','024005','024006','024003')\n" + 
									") a,mw_app.cmsv_deviceused_info info,mw_app.cmst_sb_znyc_dz bdz,\n" + 
									"mw_app.cmst_zb_comm_wspz deps where bdz.sbbm = a.linkedstation(+)\n" + 
									"and a.linkedprovicedept = deps.wsid(+) and a.devicecode = info.ZZBM(+)  and  deps.wsid  is not null ";				
						   }
					}
				}
				
			   try {
				   Sql = Sql+querySql.toString();
				   result = hibernateDao_ztjc.executeSqlQuery(Sql);
				   result = transToColumns(result,cols);
				   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + Sql + ")"));
				   
				   qro.setItems(result);
				   qro.setItemCount(((Number) (count.get(0))).intValue());
				  
				   
			   } catch (Exception e) {
				   log.info("装置查询管理，加载设备列表出错", e);
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
				StringBuffer querySq = new StringBuffer();
				StringBuffer querySql = new StringBuffer();
				StringBuffer ssdwquerySql = new StringBuffer();
				if (null != params.getFilter()) {
					String[] filter = params.getFilter().toString().split("&"); // 对传来的参数进行分割
					for (int i = 0; i < filter.length; i++) {
						// 判断投运日期,生产厂家,变电站名称
						String filterKey = filter[i].split("=")[0].trim();
						String filterValue = filter[i].split("=")[1].trim();
						if (Constants.SSXT.equals(filterKey)) {
							querySql.append(" and SSKQXT in ('");
							querySql.append(filterValue.replace(",", "','")); 
							querySql.append("')");
						} 
					}
				}
			 
			   try {
				   cols = "BDZID,BDZMC,ZZZS,SSJRZZS,ZZZS021001,ZZZS021002,ZZZS021003,ZZZS021004,ZZZS021005,ZZZS022001,ZZZS023001,ZZZS024001,ZZZS024002,ZZZS024003,ZZZS024004,ZZZS024005,ZZZS024006";
				
				   String Sql = 
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
								"global_id bdzid,bdzzj bdzmc,a.monitoringtypes\n" + 
								"from (select * from mw_app.cmsv_transfdevicef t\n" + 
								"where LINKEDEQUIPMENTDY IS NOT NULL\n" + 
								"and monitoringtypes in ('021001','021002','021003','021004','021005','021007','022001','023001','024001','024002','024004','024005','024006','024003')\n" + 
								"and linkedprovicedept  is not null) a,mw_app.cmst_sb_znyc_dz bdz,mw_app.cmsv_deviceused_info info\n" + 
								"where a.devicecode = info.ZZBM(+)   "+querySql.toString()+"\n" + 
								"and a.LinkedStation(+) = bdz.sbbm group by sbbm, bdzzj, a.monitoringtypes) T) H\n" + 
								"group by bdzid, bdzmc) temp order by zzzs desc";


				   result = hibernateDao_ztjc.executeSqlQuery(Sql);
				   result = transToColumns(result,cols);
				   count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + Sql + ")"));
				   
				   qro.setItems(result);
				   qro.setItemCount(((Number) (count.get(0))).intValue());
				  
				   
			   } catch (Exception e) {
				   log.info("输电装置查询管理，加载设备列表出错", e);
				   e.printStackTrace();
			   }
			   return qro;
		   }		
		
		
}

