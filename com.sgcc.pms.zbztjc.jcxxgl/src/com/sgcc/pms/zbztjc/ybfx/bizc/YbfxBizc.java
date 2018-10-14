package com.sgcc.pms.zbztjc.ybfx.bizc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.isc.common.jms.util.UserInfoUtil;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.util.SqlFileUtil;
/**
 * 用户定义逻辑构件
 * 
 * @author SWD 
 * 
 */
public class YbfxBizc implements IYbfxBizc{


	/**
	 * 状态监测数据源
	 */
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}
	
	
	private final Log logger = LogFactory.getLog(YbfxBizc.class);


	
	
	/**
	 * 查询线路总数
	 * 
	 * @return
	 */
	
	public List querySumLine() {
		String querySql =  SqlFileUtil.get("querySumLine");
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql);
		   } catch (Exception e) {
			   logger.info("加载线路总数出错", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}
	

	
	/**
	 * 查询表数据01
	 * 
	 * @return
	 */
	
	public List queryTable_01() {
		String querySql =  SqlFileUtil.get("queryTable_01");
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql);
		   } catch (Exception e) {
			   logger.info("加载表数据出错_01", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}

	
	/**
	 * 查询表数据02
	 * 
	 * @return
	 */
	
	public List queryTable_02() {
		String querySql =  SqlFileUtil.get("queryTable_02");
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql);
		   } catch (Exception e) {
			   logger.info("加载表数据出错_02", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}
	
	
	/**
	 * 查询表数据03
	 * 
	 * @return
	 */
	
	public List queryTable_03() {
		String querySql =  SqlFileUtil.get("queryTable_03");
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql);
		   } catch (Exception e) {
			   logger.info("加载表数据出错_03", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}

	
	/**
	 * 查询表数据04
	 * 
	 * @return
	 */
	
	public List queryTable_04(String textTime) {
		StringBuffer querySql = new StringBuffer();
		querySql.append(" WITH TAB AS (select s.OBJ_ID, s.DEVICECODE,  (CASE WHEN a.AVGJRL > 0.6 THEN 1 ELSE 0 END) JRL,  ");
		querySql.append(" (CASE WHEN s.DATAACCESSRATE > 0.6 and s.DATAARACCURACYRATE > 0.9  THEN 1 ELSE 0 END) WHL,  "); 
		querySql.append(" s.ALLSCORED,s.DATAACCESSRATE,s.DATAARACCURACYRATE,s.LINKEDPROVICEDEPT  "); 
		querySql.append(" from mw_app.cmst_dataindex_scored s,mw_app.cmst_avgjrl a where s.TJMONTH = '"+textTime+"'  ");
		querySql.append(" union all (  select s.OBJ_ID,s.DEVICECODE, (CASE WHEN a.AVGJRL > 0.6 THEN 1 ELSE 0 END) JRL,  "); 
		querySql.append(" (CASE WHEN s.DATAACCESSRATE > 0.6 and s.IMAGEEFFECTIVERATE > 0.9 THEN 1 ELSE 0 END) WHL,  "); 
		querySql.append(" s.ALLSCORED, s.DATAACCESSRATE, s.IMAGEEFFECTIVERATE as DATAARACCURACYRATE, s.LINKEDPROVICEDEPT  ");
		querySql.append(" from mw_app.cmst_imageindex_scored s,mw_app.cmst_avgjrl a where s.TJMONTH = '"+textTime+"'))  "); 
		querySql.append(SqlFileUtil.get("queryTable_04"));
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql.toString());
		   } catch (Exception e) {
			   logger.info("加载表数据出错_04", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}
	
	/**
	 * 查询表数据05
	 * 
	 * @return
	 */
	
	public List queryTable_05(String textTime) {
		StringBuffer querySql = new StringBuffer();
		querySql.append(" select c.typename TYPENAME,count(1) ZZZU,Rtrim(to_char(((select count(1) from mw_app.cmst_dataindex_scored s  ");
		querySql.append(" where s.dataaccessrate >0.6 and s.monitoringtype=d.monitoringtype and s.TJMONTH='"+textTime+"')/count(1))*100,'fm99999990.99'),'.')||'%' ZZJRL,  "); 
		querySql.append(" Rtrim(to_char((sum(decode(DATAACCESSRATE,null,0,DATAACCESSRATE))/count(1))*100,'fm99999990.99'),'.')||'%' SJJRL,  "); 
		querySql.append(" Rtrim(to_char((sum(decode(DATAARACCURACYRATE,null,0,DATAARACCURACYRATE))/count(1))*100,'fm99999990.99'),'.')||'%' SJZQL,  ");
		querySql.append(" Rtrim(to_char((sum(decode(DEVICEERRORRATE,null,0,DEVICEERRORRATE))/count(1))*100,'fm99999990.99'),'.')||'%' SJBCL ,  "); 
		querySql.append(" sum(decode(BREAKDOWNSTIME,null,0,BREAKDOWNSTIME)) ZZGZSJ, Rtrim(to_char(sum(decode(ALLSCORED,null,0,ALLSCORED))/count(1),'fm9999990.9'),'.') PJF  ");
		querySql.append(" from mw_app.CMST_DATAINDEX_SCORED d, (select typename,typecode from mw_app.cmst_monitoringtype  ");
		querySql.append(" where typecode like '01%' and typecode not in ('018002','018003')) c where d.TJMONTH='"+textTime+"'  "); 
		querySql.append(" and c.typecode=d.monitoringtype group by d.monitoringtype,c.typename ");
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql.toString());
		   } catch (Exception e) {
			   logger.info("加载表数据出错_05", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}
	
	/**
	 * 查询数据01
	 * 
	 * @return
	 */
	
	public List queryData_01() {
		StringBuffer querySql = new StringBuffer();
		querySql.append(" select t.typename from mw_app.cmst_monitoringtype t where t.typecode like '01%' and t.typecode not in ('018002','018003')  ");
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql.toString());
		   } catch (Exception e) {
			   logger.info("加载数据出错_01", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}
	
	
	/**
	 * 查询表数据06
	 * 
	 * @return
	 */
	
	public List queryTable_06(String textTime) {
		StringBuffer querySql = new StringBuffer();
		querySql.append(" select c.typename TYPENAME,count(1) ZZZU, Rtrim(to_char(((select count(1) from mw_app.CMST_IMAGEINDEX_SCORED s  ");
		querySql.append(" where s.DATAACCESSRATE > 0.6 and s.monitoringtype = d.monitoringtype  "); 
		querySql.append(" and s.TJMONTH = '"+textTime+"') / count(1)) * 100,'fm99999990.99'),'.') || '%' ZZJRL,  "); 
		querySql.append(" Rtrim(to_char((sum(decode(DATAACCESSRATE, null, 0, DATAACCESSRATE)) / count(1)) * 100,'fm99999990.99'),'.') || '%' SJJRL,  ");
		querySql.append(" Rtrim(to_char((sum(decode(IMAGEEFFECTIVERATE,null,0,IMAGEEFFECTIVERATE)) / count(1)) * 100,'fm99999990.99'),'.') || '%' SJZQL,  "); 
		querySql.append(" sum(decode(breakdownstime, null, 0, breakdownstime)) ZZGZSJ,  ");
		querySql.append(" Rtrim(to_char(sum(decode(ALLSCORED, null, 0, ALLSCORED)) / count(1),'fm9999990.9'),'.') PJF  "); 
		querySql.append(" from mw_app.CMST_IMAGEINDEX_SCORED d, (select typename, typecode from mw_app.cmst_monitoringtype  ");
		querySql.append(" where typecode = '018002') c where d.TJMONTH = '"+textTime+"' and c.typecode = d.monitoringtype  "); 
		querySql.append(" group by d.monitoringtype, c.typename ");
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql.toString());
		   } catch (Exception e) {
			   logger.info("加载表数据出错_06", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}
	
	/**
	 * 查询表数据07
	 * 
	 * @return
	 */
	public List queryTable_07(String textTime) {
		String querySql = 
				" with tab as \n" +
					    "  (select p.WSID, p.WSMC,GZZZ, GZZSJ, ZS, \n" +
					    "          XQ,GZCGQ,GZYJ,GZRJ, \n" +
					    "          GZTX,GZDY,GZQT \n" +
					    "     from (select WSID,WSMC,ZDYPX,\n" +
					    "                  (select count(distinct devicecode) from mw_app.cmst_defectlog) GZZZ,\n" +
					    "                  sum(to_char(d.CONTINUANCETIME/60,'99999'))  GZZSJ, \n" +
					    "                  sum(decode(ISELIMINATED, 'T', 1, '', 0, null, 0, 0)) XQ, \n" +
					    "                  sum(decode(d.Obj_id, null, 0, '', 0, 1)) ZS,      \n" +               
					    "                  sum(decode(DEFECTCONSTRUE, '传感器故障', 1, 0)) GZCGQ, \n" +
					    "                  sum(decode(DEFECTCONSTRUE, '监测装置硬件故障', 1, 0)) GZYJ, \n" +
					    "                  sum(decode(DEFECTCONSTRUE, '监测装置软件故障', 1, 0)) GZRJ, \n" +
					    "                  sum(decode(DEFECTCONSTRUE, '通讯故障', 1, 0)) GZTX, \n" +
					    "                  sum(decode(DEFECTCONSTRUE, '电源故障', 1, 0)) GZDY, \n" +
					    "                  sum(decode(DEFECTCONSTRUE, '其他原因', 1, 0)) GZQT \n" +
					    "            from MW_APP.Cmst_Defectlog     d, \n" +
					    "                 MW_APP.cmst_linedevice a, \n" +
					    "                 mw_app.cmst_zb_comm_wspz  p \n" +
					    "           where d.devicecode = a.devicecode(+) \n" +
					    "             and d.linkedprovicedept(+) = p.WSID \n" +
					    "             and (a.status in ('00501', '00502') or a.status is null) \n" +
					    "             and d.TJMONTH = '"+textTime+"' \n" +
					    "           group by WSID, WSMC, ZDYPX) l, \n" +
					    "         mw_app.cmst_zb_comm_wspz p \n" +
					    "   where l.WSID(+) = p.WSID \n" +
					    "   and p.WSID!='8a812897493378a001495675eff36612'\n" + 
					    "   order by p.ZDYPX) \n" +
					    " select '国家电网公司' MC, \n" +
					    "      nvl(sum(gzzz), 0) GZZZ,nvl(sum(gzzsj), 0) GZZSJ,\n" +
					    "       nvl(sum(xq), 0) XQ, nvl(sum(zs), 0) ZS,\n" +
					    "       nvl(sum(gzcgq), 0) GZCGQ,nvl(sum(gzyj), 0) GZYJ, \n" +
					    "       nvl(sum(gzrj), 0) GZRJ, nvl(sum(gztx), 0) GZTX, \n" +
					    "       nvl(sum(gzdy), 0) GZDY,nvl(sum(gzqt), 0) GZQT \n" +
					    "  from tab \n" +
					    " union all \n" +
					    " select WSMC MC, nvl(gzzz, 0) GZZZ,nvl(gzzsj, 0) GZZSJ,\n" +
					    "      nvl(xq, 0) XQ,nvl(zs, 0) ZS, nvl(gzcgq, 0) GZCGQ, \n" +
					    "       nvl(gzyj, 0) GZYJ,nvl(gzrj, 0) GZRJ, \n" +
					    "       nvl(gztx, 0) GZTX,nvl(gzdy, 0) GZDY, \n" +
					    "       nvl(gzqt, 0) GZQT \n" +
					    "  from tab";
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql);
		   } catch (Exception e) {
			   logger.info("加载表数据出错_07", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}
	
	/**
	 * 查询表数据08
	 * 
	 * @return
	 */
	public List queryTable_08(String textTime) {
		String querySql = 
				"select '' as province_id,\n" +
						"       '国家电网公司' as province_name,\n" + 
						"       count(*) as gjcs,\n" + 
						"       nvl(sum(decode(f.disposalresult, '告警误报', 1, 0)),0) wbcs,\n" + 
						"       nvl(sum(decode(f.disposalresult, '故障漏报', 1, 0)),0) lbcs,\n" + 
						"       count(distinct devicecode) as gjzzs,\n" + 
						"       nvl(sum((select count(distinct l.devicecode)\n" + 
						"                 from MW_APP.Cmst_Defectlog l\n" + 
						"                where l.disposalresult = '故障漏报')),\n" + 
						"           0) as lbzzs\n" + 
						"  from MW_APP.Cmst_Defectlog f\n" + 
						"union all (select *\n" + 
						"             from (with tab as (select d.linkedprovicedept,\n" + 
						"                                       d.linkeddeptname,\n" + 
						"                                       d.devicecode,\n" + 
						"                                       disposalresult,\n" + 
						"                                       count(1) gjcs,\n" + 
						"                                       decode(d.disposalresult,\n" + 
						"                                              '告警误报',\n" + 
						"                                              1,\n" + 
						"                                              0) wbcs,\n" + 
						"                                       decode(d.disposalresult,\n" + 
						"                                              '故障漏报',\n" + 
						"                                              1,\n" + 
						"                                              0) lbcs\n" + 
						"                                  from MW_APP.Cmst_Defectlog  d,\n" + 
						"                                       MW_APP.cmst_linedevice a\n" + 
						"                                 where d.devicecode = a.devicecode(+)\n" + 
						"                                   and (a.status = '00501' or a.status is null)\n" + 
						"                                   and d.TJMONTH = '"+textTime+"'\n" + 
						"                                 group by d.linkedprovicedept,\n" + 
						"                                          d.linkeddeptname,\n" + 
						"                                          d.disposalresult,\n" + 
						"                                          d.devicecode,\n" + 
						"                                          disposalresult)\n" + 
						"                    select WSID,\n" + 
						"                           WSMC,\n" + 
						"                           nvl(sum(gjcs), 0) as gjcs,\n" + 
						"                           nvl(sum(wbcs), 0) as wbcs,\n" + 
						"                           nvl(sum(lbcs), 0) as lbcs,\n" + 
						"                           count(distinct devicecode) as gjzzs,\n" + 
						"                           nvl(sum((select count(distinct l.devicecode)\n" + 
						"                                     from MW_APP.Cmst_Defectlog l\n" + 
						"                                    where l.linkedprovicedept = WSID\n" + 
						"                                      and disposalresult = '故障漏报')),\n" + 
						"                               0) as lbzzs\n" + 
						"                      from tab, mw_app.cmst_zb_comm_wspz p\n" + 
						"                     where linkedprovicedept(+) = p.WSID\n" + 
					    "   and p.WSID!='8a812897493378a001495675eff36612'\n" + 
						"                     group by WSID, WSMC, ZDYPX\n" + 
						"                     order by ZDYPX)\n" + 
						"           )";
		   try {
			   return hibernateDao_ztjc.queryForListWithSql(querySql);
		   } catch (Exception e) {
			   logger.info("加载表数据出错_08", e);
			   e.printStackTrace();
		   }
		   return null;
		
	}
	
	
}
