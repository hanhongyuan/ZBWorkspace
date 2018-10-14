package com.sgcc.pms.zbztjc.kqxt.jczzgl.bdzzcxtj.bizc;

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

public class BdzzcxtjBizc implements IBdzzcxtjBizc{
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(BdzzcxtjBizc.class);
	
	@SuppressWarnings({ "unchecked", "unused" })
	public QueryResultObject statByDydj(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		String dydj = "";
		String bdzzsColumnSql = "bdzzs37+bdzzs36+bdzzs35+ bdzzs34+bdzzs33+bdzzs86+bdzzs85+bdzzs84+bdzzs83+bdzzs82";
		String zzzsColumnSql = "zzzs37+zzzs36+zzzs35+zzzs34+zzzs33+zzzs86+zzzs85+zzzs84+zzzs83+zzzs82";
		String fgbdzColumnSql = "fgbdzs37+fgbdzs36+fgbdzs35+ fgbdzs34+fgbdzs33+fgbdzs86+fgbdzs85+fgbdzs84+fgbdzs83+fgbdzs82";
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				
				if (Constants.SSDW.equals(filterKey)) {
					querySq.append(" and wsid in ('");
					querySq.append(filterValue.replace(",", "','"));
					querySq.append("')");
					querySql.append(" and linkedprovicedept in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
					
				} else if (Constants.DYDJ.equals(filterKey)) {
					dydj = filterValue;
					zzzsColumnSql = zzzsColumnSql.substring(zzzsColumnSql.length());
					bdzzsColumnSql = bdzzsColumnSql.substring(bdzzsColumnSql.length());
					fgbdzColumnSql = "";
					//37,36,35
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					bdzzsColumnSql+="bdzzs"+filterValueArr[j]+"+";
					zzzsColumnSql +="zzzs"+filterValueArr[j]+"+";
					fgbdzColumnSql +="fgbdzs"+filterValueArr[j]+"+";
					}
					bdzzsColumnSql = bdzzsColumnSql.substring(0,bdzzsColumnSql.length()-1);
					zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
					fgbdzColumnSql = fgbdzColumnSql.substring(0,fgbdzColumnSql.length()-1);
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					querySql.append(" and MONITORINGTYPES in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				}
				else if (Constants.TYRQ.equals(filterKey)) {
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
				}
			}
		}
		System.out.println();
		System.out.println();
		System.out.println(bdzzsColumnSql);
		System.out.println(zzzsColumnSql);
		System.out.println(fgbdzColumnSql);
		System.out.println();
		System.out.println();
		
		
		
		String sql= "with xxx as (select temp.*,\n" +
				"    (" + zzzsColumnSql + ") allzzzs,\n" + 
				"    (" + fgbdzColumnSql + ") allfgbdzs,\n" + 
				"    ("+bdzzsColumnSql+") allbdzzs,\n" + 
				"    nvl(round(((" + fgbdzColumnSql + ")/decode(" + bdzzsColumnSql + ",0,null," + bdzzsColumnSql + "))*100,2),0)||'%' allfgl\n" + 
				"   from (\n" + 
				" select (select wsmc from MW_APP.cmst_zb_comm_wspz where wsid=linkedprovicedept) SSWSMC,\n" + 
				"  sum(zzzs37) zzzs37,(case when sum(fgbdzs37)< bdzzs37 then sum(fgbdzs37) else bdzzs37 end) fgbdzs37,nvl(bdzzs37,0) bdzzs37,nvl(round((((case when sum(fgbdzs37)< bdzzs37 then sum(fgbdzs37) else bdzzs37 end)/decode(bdzzs37,0,null,bdzzs37)*100)),2),0)||'%' fgl37,\n" + 
				"  sum(zzzs36) zzzs36,(case when sum(fgbdzs36)< bdzzs36 then sum(fgbdzs36) else bdzzs36 end) fgbdzs36,nvl(bdzzs36,0) bdzzs36,nvl(round((((case when sum(fgbdzs36)< bdzzs36 then sum(fgbdzs36) else bdzzs36 end)/decode(bdzzs36,0,null,bdzzs36)*100)),2),0)||'%' fgl36,\n" + 
				"  sum(zzzs35) zzzs35,(case when sum(fgbdzs35)< bdzzs35 then sum(fgbdzs35) else bdzzs35 end) fgbdzs35,nvl(bdzzs35,0) bdzzs35,nvl(round((((case when sum(fgbdzs35)< bdzzs35 then sum(fgbdzs35) else bdzzs35 end)/decode(bdzzs35,0,null,bdzzs35)*100)),2),0)||'%' fgl35,\n" + 
				"  sum(zzzs34) zzzs34,(case when sum(fgbdzs34)< bdzzs34 then sum(fgbdzs34) else bdzzs34 end) fgbdzs34,nvl(bdzzs34,0) bdzzs34,nvl(round((((case when sum(fgbdzs34)< bdzzs34 then sum(fgbdzs34) else bdzzs34 end)/decode(bdzzs34,0,null,bdzzs34)*100)),2),0)||'%' fgl34,\n" + 
				"  sum(zzzs33) zzzs33,(case when sum(fgbdzs33)< bdzzs33 then sum(fgbdzs33) else bdzzs33 end) fgbdzs33,nvl(bdzzs33,0) bdzzs33,nvl(round((((case when sum(fgbdzs33)< bdzzs33 then sum(fgbdzs33) else bdzzs33 end)/decode(bdzzs33,0,null,bdzzs33)*100)),2),0)||'%' fgl33,\n" + 
				"  sum(zzzs32) zzzs32,(case when sum(fgbdzs32)< bdzzs32 then sum(fgbdzs32) else bdzzs32 end) fgbdzs32,nvl(bdzzs32,0) bdzzs32,nvl(round((((case when sum(fgbdzs32)< bdzzs32 then sum(fgbdzs32) else bdzzs32 end)/decode(bdzzs32,0,null,bdzzs32)*100)),2),0)||'%' fgl32,\n" + 
				"  sum(zzzs30) zzzs30,(case when sum(fgbdzs30)< bdzzs30 then sum(fgbdzs30) else bdzzs30 end) fgbdzs30,nvl(bdzzs30,0) bdzzs30,nvl(round((((case when sum(fgbdzs30)< bdzzs30 then sum(fgbdzs30) else bdzzs30 end)/decode(bdzzs30,0,null,bdzzs30)*100)),2),0)||'%' fgl30,\n" + 
				"  sum(zzzs25) zzzs25,(case when sum(fgbdzs25)< bdzzs25 then sum(fgbdzs25) else bdzzs25 end) fgbdzs25,nvl(bdzzs25,0) bdzzs25,nvl(round((((case when sum(fgbdzs25)< bdzzs25 then sum(fgbdzs25) else bdzzs25 end)/decode(bdzzs25,0,null,bdzzs25)*100)),2),0)||'%' fgl25,\n" + 
				"  sum(zzzs80) zzzs80,(case when sum(fgbdzs80)< bdzzs80 then sum(fgbdzs80) else bdzzs80 end) fgbdzs80,nvl(bdzzs80,0) bdzzs80,nvl(round((((case when sum(fgbdzs80)< bdzzs80 then sum(fgbdzs80) else bdzzs80 end)/decode(bdzzs80,0,null,bdzzs80)*100)),2),0)||'%' fgl80,\n" + 
				"  sum(zzzs81) zzzs81,(case when sum(fgbdzs81)< bdzzs81 then sum(fgbdzs81) else bdzzs81 end) fgbdzs81,nvl(bdzzs81,0) bdzzs81,nvl(round((((case when sum(fgbdzs81)< bdzzs81 then sum(fgbdzs81) else bdzzs81 end)/decode(bdzzs81,0,null,bdzzs81)*100)),2),0)||'%' fgl81,\n" + 
				"  sum(zzzs86) zzzs86,(case when sum(fgbdzs86)< bdzzs86 then sum(fgbdzs86) else bdzzs86 end) fgbdzs86,nvl(bdzzs86,0) bdzzs86,nvl(round((((case when sum(fgbdzs86)< bdzzs86 then sum(fgbdzs86) else bdzzs86 end)/decode(bdzzs86,0,null,bdzzs86)*100)),2),0)||'%' fgl86,\n" + 
				"  sum(zzzs85) zzzs85,(case when sum(fgbdzs85)< bdzzs85 then sum(fgbdzs85) else bdzzs85 end) fgbdzs85,nvl(bdzzs85,0) bdzzs85,nvl(round((((case when sum(fgbdzs85)< bdzzs85 then sum(fgbdzs85) else bdzzs85 end)/decode(bdzzs85,0,null,bdzzs85)*100)),2),0)||'%' fgl85,\n" + 
				"  sum(zzzs84) zzzs84,(case when sum(fgbdzs84)< bdzzs84 then sum(fgbdzs84) else bdzzs84 end) fgbdzs84,nvl(bdzzs84,0) bdzzs84,nvl(round((((case when sum(fgbdzs84)< bdzzs84 then sum(fgbdzs84) else bdzzs84 end)/decode(bdzzs84,0,null,bdzzs84)*100)),2),0)||'%' fgl84,\n" + 
				"  sum(zzzs83) zzzs83,(case when sum(fgbdzs83)< bdzzs83 then sum(fgbdzs83) else bdzzs83 end) fgbdzs83,nvl(bdzzs83,0) bdzzs83,nvl(round((((case when sum(fgbdzs83)< bdzzs83 then sum(fgbdzs83) else bdzzs83 end)/decode(bdzzs83,0,null,bdzzs83)*100)),2),0)||'%' fgl83,\n" + 
				"  sum(zzzs82) zzzs82,(case when sum(fgbdzs82)< bdzzs82 then sum(fgbdzs82) else bdzzs82 end) fgbdzs82,nvl(bdzzs82,0) bdzzs82,nvl(round((((case when sum(fgbdzs82)< bdzzs82 then sum(fgbdzs82) else bdzzs82 end)/decode(bdzzs82,0,null,bdzzs82)*100)),2),0)||'%' fgl82\n" + 
				"  ,linkedprovicedept\n" + 
				"    ,(select cast(zdypx as varchar2(10)) PMS_XH from MW_APP.cmst_zb_comm_wspz where wsid=linkedprovicedept and rownum=1) xh\n" + 
				"  from (select\n" + 
				"    linkedprovicedept,(case dydj when '37' then fgbdzs else 0 end) fgbdzs37,\n" + 
				"                (case dydj when '37' then zzzs else 0 end) zzzs37,\n" + 
				"                (case dydj when '36' then fgbdzs else 0 end) fgbdzs36,\n" + 
				"                (case dydj when '36' then zzzs else 0 end) zzzs36,\n" + 
				"                (case dydj when '35' then fgbdzs else 0 end) fgbdzs35,\n" + 
				"                (case dydj when '35' then zzzs else 0 end) zzzs35,\n" + 
				"                (case dydj when '34' then fgbdzs else 0 end) fgbdzs34,\n" + 
				"                (case dydj when '34' then zzzs else 0 end) zzzs34,\n" + 
				"                (case dydj when '33' then fgbdzs else 0 end) fgbdzs33,\n" + 
				"                (case dydj when '33' then zzzs else 0 end) zzzs33,\n" + 
				"                (case dydj when '32' then fgbdzs else 0 end) fgbdzs32,\n" + 
				"                (case dydj when '32' then zzzs else 0 end) zzzs32,\n" + 
				"                (case dydj when '30' then fgbdzs else 0 end) fgbdzs30,\n" + 
				"                (case dydj when '30' then zzzs else 0 end) zzzs30,\n" + 
				"                (case dydj when '25' then fgbdzs else 0 end) fgbdzs25,\n" + 
				"                (case dydj when '25' then zzzs else 0 end) zzzs25,\n" + 
				"                (case dydj when '80' then fgbdzs else 0 end) fgbdzs80,\n" + 
				"                (case dydj when '80' then zzzs else 0 end) zzzs80,\n" + 
				"                (case dydj when '81' then fgbdzs else 0 end) fgbdzs81,\n" + 
				"                (case dydj when '81' then zzzs else 0 end) zzzs81,\n" + 
				"                (case dydj when '86' then fgbdzs else 0 end) fgbdzs86,\n" + 
				"                (case dydj when '86' then zzzs else 0 end) zzzs86,\n" + 
				"                (case dydj when '85' then fgbdzs else 0 end) fgbdzs85,\n" + 
				"                (case dydj when '85' then zzzs else 0 end) zzzs85,\n" + 
				"                (case dydj when '84' then fgbdzs else 0 end) fgbdzs84,\n" + 
				"                (case dydj when '84' then zzzs else 0 end) zzzs84,\n" + 
				"                (case dydj when '83' then fgbdzs else 0 end) fgbdzs83,\n" + 
				"                (case dydj when '83' then zzzs else 0 end) zzzs83,\n" + 
				"                (case dydj when '82' then fgbdzs else 0 end) fgbdzs82,\n" + 
				"                (case dydj when '82' then zzzs else 0 end) zzzs82,\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept and dydj='36' ) BDZZS36, --交流750kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='35' ) BDZZS35, --交流500kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='34' ) BDZZS34, --交流330kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='33' ) BDZZS33, --交流220kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='32' ) BDZZS32, --交流110kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='30' ) BDZZS30, --交流66kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='25' ) BDZZS25, --交流35kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='80' ) BDZZS80, --直流120kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='81' ) BDZZS81, --直流125kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='37' ) BDZZS37, --交流1000kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='86' ) BDZZS86, --直流1000kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='85' ) BDZZS85, --直流800kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='84' ) BDZZS84, --直流660kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='83' ) BDZZS83, --直流500kV条数\n" + 
				//"  (select count(distinct global_id) from mw_app.CMSMV_PMSAPP_UD_SBD_BDZ   where YXBM in (select CITY_OBJ_ID from mw_app.cmsmv_pmssys_pd_deps_city2ws where obj_id = KKK.linkedprovicedept) and dydj='82' ) BDZZS82 --直流400kV条数\n" + 
				"  (select count(distinct SBBM) from mw_app.cmst_sb_znyc_dz   where SSWS = KKK.linkedprovicedept  and dydj='82' ) BDZZS82 --直流400kV条数\n" + 
				"\n" + 
				"  from\n" + 
				"  (\n" + 

				" select count(distinct a.devicecode) zzzs,\n" +
				"\t        a.LINKEDEQUIPMENTDY dydj,\n" + 
				"          count(distinct a.linkedstation) fgbdzs,\n" + 
				"\t        deps.wsid linkedprovicedept from\n" + 
				"(select * from mw_app.CMSV_TransfDEVICEF\n" + 
				"where \n"+
				"    LINKEDEQUIPMENTDY IS NOT NULL and linkedprovicedept IS NOT NULL   and MONITORINGTYPES LIKE '02%'   \n" + querySql.toString()+
				"  ) a ,MW_APP.cmst_zb_comm_wspz deps where deps.wsid= a.linkedprovicedept(+) and deps.wsid is not null " + querySq.toString()+
				"\t  group by deps.wsid,a.LINKEDEQUIPMENTDY) KKK "+ 
				"  )\n" + 
				"  group by linkedprovicedept,bdzzs37,bdzzs36,bdzzs35,bdzzs34,bdzzs33,bdzzs32,bdzzs30,bdzzs25,bdzzs80,bdzzs81,bdzzs86,bdzzs85,bdzzs84,bdzzs83,bdzzs82 \n" + 
				"   \n" + 
				"    ) temp\n" + 
				"    where temp.SSWSMC is not null\n" + 
				" )\n" +
				"  select '国家电网公司' sswsmc,\n" + 
				"  nvl(sum(ZZZS37),0) ZZZS37,nvl(sum(FGBDZS37),0) FGBDZS37,nvl(sum(BDZZS37),0) BDZZS37 ,(case when sum(BDZZS37)=0 then '-' else to_char((sum(FGBDZS37) /sum(BDZZS37)*100),'990.99')||'%'end) FGL37,\n" + 																							
				"  nvl(sum(ZZZS36),0) ZZZS36,nvl(sum(FGBDZS36),0) FGBDZS36,nvl(sum(BDZZS36),0) BDZZS36 ,(case when sum(BDZZS36)=0 then '-' else to_char((sum(FGBDZS36) /sum(BDZZS36)*100),'990.99')||'%'end) FGL36,\n" + 
				"  nvl(sum(ZZZS35),0) ZZZS35,nvl(sum(FGBDZS35),0) FGBDZS35,nvl(sum(BDZZS35),0) BDZZS35 ,(case when sum(BDZZS35)=0 then '-' else to_char((sum(FGBDZS35) /sum(BDZZS35)*100),'990.99')||'%'end) FGL35,\n" + 
				"  nvl(sum(ZZZS34),0) ZZZS34,nvl(sum(FGBDZS34),0) FGBDZS34,nvl(sum(BDZZS34),0) BDZZS34 ,(case when sum(BDZZS34)=0 then '-' else to_char((sum(FGBDZS34) /sum(BDZZS34)*100),'990.99')||'%'end) FGL34,\n" + 
				"  nvl(sum(ZZZS33),0) ZZZS33,nvl(sum(FGBDZS33),0) FGBDZS33,nvl(sum(BDZZS33),0) BDZZS33 ,(case when sum(BDZZS33)=0 then '-' else to_char((sum(FGBDZS33) /sum(BDZZS33)*100),'990.99')||'%'end) FGL33,\n" + 
				"  nvl(sum(ZZZS32),0) ZZZS32,nvl(sum(FGBDZS32),0) FGBDZS32,nvl(sum(BDZZS32),0) BDZZS32 ,(case when sum(BDZZS32)=0 then '-' else to_char((sum(FGBDZS32) /sum(BDZZS32)*100),'990.99')||'%'end) FGL32,\n" + 
				"  nvl(sum(ZZZS30),0) ZZZS30,nvl(sum(FGBDZS30),0) FGBDZS30,nvl(sum(BDZZS30),0) BDZZS30 ,(case when sum(BDZZS30)=0 then '-' else to_char((sum(FGBDZS30) /sum(BDZZS30)*100),'990.99')||'%'end) FGL30,\n" + 
				"  nvl(sum(ZZZS25),0) ZZZS25,nvl(sum(FGBDZS25),0) FGBDZS25,nvl(sum(BDZZS25),0) BDZZS25 ,(case when sum(BDZZS25)=0 then '-' else to_char((sum(FGBDZS25) /sum(BDZZS25)*100),'990.99')||'%'end) FGL25,\n" + 
				"  nvl(sum(ZZZS80),0) ZZZS80,nvl(sum(FGBDZS80),0) FGBDZS80,nvl(sum(BDZZS80),0) BDZZS80 ,(case when sum(BDZZS80)=0 then '-' else to_char((sum(FGBDZS80) /sum(BDZZS80)*100),'990.99')||'%'end) FGL80,\n" + 
				"  nvl(sum(ZZZS81),0) ZZZS81,nvl(sum(FGBDZS81),0) FGBDZS81,nvl(sum(BDZZS81),0) BDZZS81 ,(case when sum(BDZZS81)=0 then '-' else to_char((sum(FGBDZS81) /sum(BDZZS81)*100),'990.99')||'%'end) FGL81,\n" + 
				"  nvl(sum(ZZZS86),0) ZZZS86,nvl(sum(FGBDZS86),0) FGBDZS86,nvl(sum(BDZZS86),0) BDZZS86 ,(case when sum(BDZZS86)=0 then '-' else to_char((sum(FGBDZS86) /sum(BDZZS86)*100),'990.99')||'%'end) FGL86,\n" + 
				"  nvl(sum(ZZZS85),0) ZZZS85,nvl(sum(FGBDZS85),0) FGBDZS85,nvl(sum(BDZZS85),0) BDZZS85 ,(case when sum(BDZZS85)=0 then '-' else to_char((sum(FGBDZS85) /sum(BDZZS85)*100),'990.99')||'%'end) FGL85,\n" + 
				"  nvl(sum(ZZZS84),0) ZZZS84,nvl(sum(FGBDZS84),0) FGBDZS84,nvl(sum(BDZZS84),0) BDZZS84 ,(case when sum(BDZZS84)=0 then '-' else to_char((sum(FGBDZS84) /sum(BDZZS84)*100),'990.99')||'%'end) FGL84,\n" + 
				"  nvl(sum(ZZZS83),0) ZZZS83,nvl(sum(FGBDZS83),0) FGBDZS83,nvl(sum(BDZZS83),0) BDZZS83 ,(case when sum(BDZZS83)=0 then '-' else to_char((sum(FGBDZS83) /sum(BDZZS83)*100),'990.99')||'%'end) FGL83,\n" + 
				"  nvl(sum(ZZZS82),0) ZZZS82,nvl(sum(FGBDZS82),0) FGBDZS82,nvl(sum(BDZZS82),0) BDZZS82 ,(case when sum(BDZZS82)=0 then '-' else to_char((sum(FGBDZS82) /sum(BDZZS82)*100),'990.99')||'%'end) FGL82,\n" + 
				"  '' linkedprovicedept,'-1' xh,\n" + 
				"   nvl(sum(ALLZZZS),0) ALLZZZS,nvl(sum(ALLFGBDZS),0) ALLFGBDZS,nvl(sum(ALLBDZZS),0) ALLBDZZS ,(case when sum(ALLBDZZS)=0 then '-' else to_char((sum(ALLFGBDZS) /sum(ALLBDZZS)*100),'990.99')||'%'end) ALLFGL\n" + 
				"   from xxx "+
				"   union all   "+
				"select sswsmc,zzzs37,fgbdzs37,bdzzs37,case when bdzzs37 = 0 then '-' else  to_char(substr(fgl37,0,length(fgl37)-1),'990.00')||'%'  end fgl37,\n" +
				"zzzs36,fgbdzs36,bdzzs36,case when bdzzs36 = 0 then '-' else  to_char(substr(fgl36,0,length(fgl36)-1),'990.00')||'%'  end fgl36,\n" + 
				"zzzs35,fgbdzs35,bdzzs35,case when bdzzs35 = 0 then '-' else  to_char(substr(fgl35,0,length(fgl35)-1),'990.00')||'%'  end fgl35,\n" + 
				"zzzs34,fgbdzs34,bdzzs34,case when bdzzs34 = 0 then '-' else  to_char(substr(fgl34,0,length(fgl34)-1),'990.00')||'%'  end fgl34,\n" + 
				"zzzs33,fgbdzs33,bdzzs33,case when bdzzs33 = 0 then '-' else  to_char(substr(fgl33,0,length(fgl33)-1),'990.00')||'%'  end fgl33,\n" +
				"zzzs32,fgbdzs32,bdzzs32,case when bdzzs32 = 0 then '-' else  to_char(substr(fgl32,0,length(fgl32)-1),'990.00')||'%'  end fgl32,\n" + 
				"zzzs30,fgbdzs30,bdzzs30,case when bdzzs30 = 0 then '-' else  to_char(substr(fgl30,0,length(fgl30)-1),'990.00')||'%'  end fgl30,\n" + 
				"zzzs25,fgbdzs25,bdzzs25,case when bdzzs25 = 0 then '-' else  to_char(substr(fgl25,0,length(fgl25)-1),'990.00')||'%'  end fgl25,\n" + 
				"zzzs80,fgbdzs80,bdzzs80,case when bdzzs80 = 0 then '-' else  to_char(substr(fgl80,0,length(fgl80)-1),'990.00')||'%'  end fgl80,\n" + 
				"zzzs81,fgbdzs81,bdzzs81,case when bdzzs81 = 0 then '-' else  to_char(substr(fgl81,0,length(fgl81)-1),'990.00')||'%'  end fgl81,\n" + 
				"zzzs86,fgbdzs86,bdzzs86,case when bdzzs86 = 0 then '-' else  to_char(substr(fgl86,0,length(fgl86)-1),'990.00')||'%'  end fgl86,\n" + 
				"zzzs85,fgbdzs85,bdzzs85,case when bdzzs85 = 0 then '-' else  to_char(substr(fgl85,0,length(fgl85)-1),'990.00')||'%'  end fgl85,\n" + 
				"zzzs84,fgbdzs84,bdzzs84,case when bdzzs84 = 0 then '-' else  to_char(substr(fgl84,0,length(fgl84)-1),'990.00')||'%'  end fgl84,\n" + 
				"zzzs83,fgbdzs83,bdzzs83,case when bdzzs83 = 0 then '-' else  to_char(substr(fgl83,0,length(fgl83)-1),'990.00')||'%'  end fgl83,\n" + 
				"zzzs82,fgbdzs82,bdzzs82,case when bdzzs82 = 0 then '-' else  to_char(substr(fgl82,0,length(fgl82)-1),'990.00')||'%'  end fgl82,\n" + 
				"linkedprovicedept,xh,\n" + 
				"allzzzs,allfgbdzs,allbdzzs,case when allzzzs = 0 then '-' else  to_char(substr(allfgl,0,length(allfgl)-1),'990.00')||'%'  end allfgl"+
				" from xxx order by xh\n";
		try{
			cols = "SSWSMC,ZZZS37,FGBDZS37,BDZZS37,FGL37,ZZZS36,FGBDZS36,BDZZS36,FGL36," +
					"ZZZS35,FGBDZS35,BDZZS35,FGL35,ZZZS34,FGBDZS34,BDZZS34,FGL34," +
					"ZZZS33,FGBDZS33,BDZZS33,FGL33,ZZZS32,FGBDZS32,BDZZS32,FGL32," +
					"ZZZS30,FGBDZS30,BDZZS30,FGL30,ZZZS25,FGBDZS25,BDZZS25,FGL25," +
					"ZZZS80,FGBDZS80,BDZZS80,FGL80,ZZZS81,FGBDZS81,BDZZS81,FGL81," +
					"ZZZS86,FGBDZS86,BDZZS86,FGL86,ZZZS85,FGBDZS85,BDZZS85,FGL85," +
					"ZZZS84,FGBDZS84,BDZZS84,FGL84,ZZZS83,FGBDZS83,BDZZS83,FGL83," +
					"ZZZS82,FGBDZS82,BDZZS82,FGL82,LINKEDPROVICEDEPT,XH,ALLZZZS,ALLFGBDZS,ALLBDZZS,ALLFGL";
					
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			   
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			log.info("执行按电压等级统计监测装置信息时出错！", e);
			e.printStackTrace();
		}
		
		return qro;
		
	}
	
	
	public QueryResultObject statByJclx(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		String jclx = "";
		String zzzsColumnSql = "";
		String fgbdzColumnSql = "";
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();

		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();

				if (Constants.SSDW.equals(filterKey)) {
					querySq.append(" and wsid in ('");
					querySq.append(filterValue.replace(",", "','"));
					querySq.append("')");
					querySql.append(" and linkeddepws in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					jclx = filterValue;
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
						zzzsColumnSql +="zzzs"+filterValueArr[j]+"+";
						}
						zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
					querySql.append(" and MONITORINGTYPES in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				}
				else if (Constants.TYRQ.equals(filterKey)) {
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
				}
			}
		}
		
		try{
			cols ="SSWSMC,ZZZS021001,ZZZS021002,ZZZS021003,ZZZS021004,ZZZS021005,ZZZS022001,ZZZS023001,ZZZS024001,ZZZS024002,ZZZS024003,ZZZS024004,ZZZS024005,ZZZS024006,ZZZS026001,ZZZS021007,LINKEDPROVICEDEPT,XH,ALLZZZS";	
			
		
			String sql ="with XXX as (select temp.*,\n" +
					"(zzzs021001+zzzs021002+zzzs021003+zzzs021004+zzzs021005+zzzs022001+zzzs023001+zzzs024001+zzzs024002+zzzs024003+zzzs024004+zzzs024005+zzzs024006+zzzs026001) ALLZZZS\n" + 
					"from ( select (select wsmc from MW_APP.cmst_zb_comm_wspz where wsid=linkedprovicedept) SSWSMC,\n" + 
					"    sum(zzzs021001) zzzs021001,\n" + 
					"    sum(zzzs021002) zzzs021002,\n" + 
					"    sum(zzzs021003) zzzs021003,\n" + 
					"    sum(zzzs021004) zzzs021004,\n" + 
					"    sum(zzzs021005) zzzs021005,\n" + 
					"    sum(zzzs022001) zzzs022001,\n" + 
					"    sum(zzzs023001) zzzs023001,\n" + 
					"    sum(zzzs024001) zzzs024001,\n" + 
					"    sum(zzzs024002) zzzs024002,\n" + 
					"    sum(zzzs024003) zzzs024003,\n" + 
					"    sum(zzzs024004) zzzs024004,\n" + 
					"    sum(zzzs024005) zzzs024005,\n" + 
					"    sum(zzzs024006) zzzs024006,\n" + 
					"    sum(zzzs026001) zzzs026001,\n" + 
					"    sum(zzzs021007) zzzs021007\n" + 
					"  ,linkedprovicedept\n" + 
					"    ,(select cast(zdypx as varchar2(10)) PMS_XH from MW_APP.cmst_zb_comm_wspz where wsid=linkedprovicedept and rownum=1) xh\n" + 
					"from (select T.linkedprovicedept,\n" + 
					"            T.monitoringtypes,\n" + 
					"            (decode(T.monitoringtypes, '021001', zzzs, 0)) zzzs021001,  --局部放电\n" + 
					"            (decode(T.monitoringtypes, '021002', zzzs, 0)) zzzs021002,  --油中溶解气体\n" + 
					"            (decode(T.monitoringtypes, '021003', zzzs, 0)) zzzs021003,  --微水\n" + 
					"            (decode(T.monitoringtypes, '021004', zzzs, 0)) zzzs021004,  --铁芯接地电流\n" + 
					"            (decode(T.monitoringtypes, '021005', zzzs, 0)) zzzs021005,  --顶层油温\n" + 
					"            (decode(T.monitoringtypes, '022001', zzzs, 0)) zzzs022001,  --绝缘监测\n" + 
					"            (decode(T.monitoringtypes, '023001', zzzs, 0)) zzzs023001,  --绝缘监测\n" + 
					"            (decode(T.monitoringtypes, '024001', zzzs, 0)) zzzs024001,  --局部放电\n" + 
					"            (decode(T.monitoringtypes, '024002', zzzs, 0)) zzzs024002,  --分合闸线圈电流波形\n" + 
					"            (decode(T.monitoringtypes, '024003', zzzs, 0)) zzzs024003,  --负荷电流波形\n" + 
					"            (decode(T.monitoringtypes, '024004', zzzs, 0)) zzzs024004,  --SF6气体压力\n" + 
					"            (decode(T.monitoringtypes, '024005', zzzs, 0)) zzzs024005,  --SF6气体水分\n" + 
					"            (decode(T.monitoringtypes, '024006', zzzs, 0)) zzzs024006,  --储能电机工作状态\n" + 
					"            (decode(T.monitoringtypes, '026001', zzzs, 0)) zzzs026001,  --变电视频监测\n" + 
					"            (decode(T.monitoringtypes, '021007', zzzs, 0)) zzzs021007  --变压器振动波谱\n" + 
					"       from ( "+
					"            select count(distinct a.devicecode) zzzs,\n" +
					"\t                deps.wsid linkedprovicedept,\n" + 
					"                  a.monitoringtypes from\n" + 
					"            (select * from mw_app.CMSV_TransfDEVICEF\n" + 
					"where  \n" + 
					"   LINKEDEQUIPMENTDY IS NOT NULL   and linkedprovicedept IS NOT NULL   and MONITORINGTYPES LIKE '02%'  \n" + querySql.toString()+
					"   ) a ,MW_APP.cmst_zb_comm_wspz deps where deps.wsid= a.linkedprovicedept(+)\n" + querySq.toString()+
					"\t  group by deps.wsid,a.monitoringtypes\n" + 	
					") T) H\n" + 
					"group by linkedprovicedept\n" + 
					"order by xh\n" + 
					"  ) temp\n" + 
					"   where temp.SSWSMC is not null )\n"+
					"select '国家电网公司' SSWSMC,\n" + 
					"   nvl(sum(zzzs021001),0) zzzs021001,\n" + 
					"    nvl(sum(zzzs021002),0) zzzs021002,\n" + 
					"    nvl(sum(zzzs021003),0) zzzs021003,\n" + 
					"    nvl(sum(zzzs021004),0) zzzs021004,\n" + 
					"    nvl(sum(zzzs021005),0) zzzs021005,\n" + 
					"    nvl(sum(zzzs022001),0) zzzs022001,\n" + 
					"    nvl(sum(zzzs023001),0) zzzs023001,\n" + 
					"    nvl(sum(zzzs024001),0) zzzs024001,\n" + 
					"    nvl(sum(zzzs024002),0) zzzs024002,\n" + 
					"    nvl(sum(zzzs024003),0) zzzs024003,\n" + 
					"    nvl(sum(zzzs024004),0) zzzs024004,\n" + 
					"    nvl(sum(zzzs024005),0) zzzs024005,\n" + 
					"    nvl(sum(zzzs024006),0) zzzs024006,\n" + 
					"    nvl(sum(zzzs026001),0) zzzs026001,\n" + 
					"    nvl(sum(zzzs021007),0) zzzs021007,\n" + 
					"    '' linkedprovicedept,'' xh ,nvl(sum(ALLZZZS),0) ALLZZZS from XXX "+
					"union all\n"+
					"   select * from XXX\n";
					
			log.info(sql);
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result,cols);
			//count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			   
			qro.setItems(result);
			//qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			log.info("执行按监测类型统计变电监测装置时出错！", e);
			e.printStackTrace();
		}

		return qro;
		
	}

    
	@SuppressWarnings("unchecked")
	public QueryResultObject statBySccj(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		
		String cols = "";
		String jclx = "";
		String zzzsColumnSql = "";
		String fgbdzColumnSql = "";
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();
	

		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();

				if (Constants.SSDW.equals(filterKey)) {
					querySq.append(" and wsid in ('");
					querySq.append(filterValue.replace(",", "','"));
					querySq.append("')");
					querySql.append(" and linkeddepws in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					jclx = filterValue;
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
						zzzsColumnSql +="zzzs"+filterValueArr[j]+"+";
						}
						zzzsColumnSql = zzzsColumnSql.substring(0,zzzsColumnSql.length()-1);
				
					querySql.append(" and MONITORINGTYPES in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				}
				else if (Constants.TYRQ.equals(filterKey)) {
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
				}
			}
		}
		
		String sql = "with XXX as(select temp.*, \n"+
"(zzzs021001+zzzs021002+zzzs021003+zzzs021004+zzzs021005+zzzs022001+zzzs023001+zzzs024001+zzzs024002+zzzs024003+zzzs024004+zzzs024005+zzzs024006+zzzs026001) ALLZZZS \n"+
 "from ( select decode(manufacturer,null,'(空)',manufacturer) SSWSMC, \n"+
    "sum(zzzs021001) zzzs021001, \n"+
    "sum(zzzs021002) zzzs021002, \n"+
    "sum(zzzs021003) zzzs021003, \n"+
    "sum(zzzs021004) zzzs021004, \n"+
    "sum(zzzs021005) zzzs021005, \n"+
    "sum(zzzs022001) zzzs022001, \n"+
    "sum(zzzs023001) zzzs023001, \n"+
    "sum(zzzs024001) zzzs024001, \n"+
    "sum(zzzs024002) zzzs024002, \n"+
    "sum(zzzs024003) zzzs024003, \n"+
    "sum(zzzs024004) zzzs024004, \n"+
    "sum(zzzs024005) zzzs024005, \n"+
    "sum(zzzs024006) zzzs024006, \n"+
    "sum(zzzs026001) zzzs026001, \n"+
    "sum(zzzs021007) zzzs021007, \n"+
    "(select OBJ_ID from mw_app.cmst_manufacturer_zb a where a.name=H.manufacturer and rownum=1) manufacturer_OBJID \n"+
"from (select decode(T.manufacturer,null,'(空)',T.manufacturer) manufacturer, \n"+
            "T.monitoringtypes, \n"+
            "(decode(T.monitoringtypes, '021001', zzzs, 0)) zzzs021001, \n"+  //局部放电
            "(decode(T.monitoringtypes, '021002', zzzs, 0)) zzzs021002, \n"+  //油中溶解气体
            "(decode(T.monitoringtypes, '021003', zzzs, 0)) zzzs021003, \n"+  //微水
            "(decode(T.monitoringtypes, '021004', zzzs, 0)) zzzs021004, \n"+  //铁芯接地电流
            "(decode(T.monitoringtypes, '021005', zzzs, 0)) zzzs021005, \n"+  //顶层油温
            "(decode(T.monitoringtypes, '022001', zzzs, 0)) zzzs022001, \n"+  //绝缘监测
            "(decode(T.monitoringtypes, '023001', zzzs, 0)) zzzs023001, \n"+  //绝缘监测
            "(decode(T.monitoringtypes, '024001', zzzs, 0)) zzzs024001, \n"+  //局部放电
            "(decode(T.monitoringtypes, '024002', zzzs, 0)) zzzs024002, \n"+  //分合闸线圈电流波形
            "(decode(T.monitoringtypes, '024003', zzzs, 0)) zzzs024003, \n"+  //负荷电流波形
            "(decode(T.monitoringtypes, '024004', zzzs, 0)) zzzs024004, \n"+  //SF6气体压力
            "(decode(T.monitoringtypes, '024005', zzzs, 0)) zzzs024005, \n"+  //SF6气体水分
            "(decode(T.monitoringtypes, '024006', zzzs, 0)) zzzs024006, \n"+  //储能电机工作状态
            "(decode(T.monitoringtypes, '026001', zzzs, 0)) zzzs026001, \n"+  //变电视频监测
            "(decode(T.monitoringtypes, '021007', zzzs, 0)) zzzs021007 \n"+  //变压器振动波谱
       "from (select count(a.devicecode) zzzs,a.manufacturer,a.monitoringtypes \n"+
       "from mw_app.cmsv_transfdevice_xtf a where LINKEDEQUIPMENTDY IS NOT NULL AND MONITORINGTYPES LIKE '02%' "+querySql.toString()+" \n"+  
       "and linkedprovicedept is not null and  WSDEPMC NOT LIKE '%电网%' AND  WSDEPMC NOT LIKE '%分部%' \n"+             
          "group by a.manufacturer, a.monitoringtypes) T) H \n"+
				"group by manufacturer \n"+
				"order by manufacturer)temp \n"+
				"where temp.SSWSMC is not null) \n"+
				"select '厂家合计' SSWSMC, \n"+
    "nvl(sum(zzzs021001),0) zzzs021001, \n"+
    "nvl(sum(zzzs021002),0) zzzs021002, \n"+
    "nvl(sum(zzzs021003),0) zzzs021003, \n"+
    "nvl(sum(zzzs021004),0) zzzs021004, \n"+
    "nvl(sum(zzzs021005),0) zzzs021005, \n"+
    "nvl(sum(zzzs022001),0) zzzs022001, \n"+
    "nvl(sum(zzzs023001),0) zzzs023001, \n"+
    "nvl(sum(zzzs024001),0) zzzs024001, \n"+
    "nvl(sum(zzzs024002),0) zzzs024002, \n"+
    "nvl(sum(zzzs024003),0) zzzs024003, \n"+
    "nvl(sum(zzzs024004),0) zzzs024004, \n"+
    "nvl(sum(zzzs024005),0) zzzs024005, \n"+
    "nvl(sum(zzzs024006),0) zzzs024006, \n"+
    "nvl(sum(zzzs026001),0) zzzs026001, \n"+
    "nvl(sum(zzzs021007),0) zzzs021007, \n"+
    "'' manufacturer_OBJID,nvl(sum(ALLZZZS),0) ALLZZZS from XXX union all \n"+
    "select * from XXX";
	
		try{
			cols = "SSWSMC,ZZZS021001,ZZZS021002,ZZZS021003,ZZZS021004,ZZZS021005,ZZZS022001,ZZZS023001," +
					"ZZZS024001,ZZZS024002,ZZZS024003,ZZZS024004,ZZZS024005,ZZZS024006,ZZZS026001,ZZZS021007,MANUFACTURER_OBJID,ALLZZZS";
			result = hibernateDao_ztjc.executeSqlQuery(sql);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行按生产厂家统计输电检测装置时出错",e);
		}
		
		return qro;
	}
	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	   private List<Map> transToColumns(List<Object[]> list, String columns)
	   {
	       List<Map> items = new ArrayList();
	       String[] cols = columns.split("\\,");
	       for (int i = 0; i < list.size(); i++){
	           Map map = new HashMap();
	           for (int m = 0; m < cols.length; m++){
	        	   map.put(cols[m].trim(), list.get(i)[m]);
	           }
	           items.add(map);
	       }
	       return items;
	   }


	@Override
	public QueryResultObject getDetailList(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		StringBuffer querySq = new StringBuffer();
		StringBuffer querySql = new StringBuffer();
		StringBuffer querySccj = new StringBuffer();
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					querySql.append(" and linkeddepws in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
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
				}else if (Constants.SCCJ.equals(filterKey)){
					if("(空)".equals(filterValue)){
						querySql.append(" and MANUFACTURER is null ");
					}else{
						querySql.append(" and MANUFACTURER  LIKE '%");
						querySql.append(filterValue);
						querySql.append("%' ");
					}
				}else if (Constants.BDZMC.equals(filterKey)){
					querySql.append(" and LinkedStationName LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}else if (Constants.YXZT.equals(filterKey)){
					querySql.append(" and status in ('");
					querySql.append(filterValue);
					querySql.append("') ");
				}else if (Constants.SFSS.equals(filterKey)){
					if("all".equals(filterValue.toString())){
					querySql.append(" ");
					}else if("F".equals(filterValue.toString())){
						querySql.append(" and (sfss is null or sfss = 'F') ");
					}else{	
					querySql.append(" and SFSS = '");
					querySql.append(filterValue);
					querySql.append("' ");
					}
				}
			}
		}
	
		String sql ="WITH tab AS( \n"+
				" SELECT T.*,'查看' LOOKUP  FROM MW_APP.CMSV_TransfDEVICEF T where T.WSDEPMC IS NOT NULL AND T.LINKEDEQUIPMENTDY IS NOT NULL  AND T.MONITORINGTYPES LIKE '02%' ) \n"+
				" SELECT tab.*,nvl(DECODE(SFSS,'T','是','F','否'),'否') ISRT FROM tab where 1=1"+querySql.toString();
				
				
				/*"SELECT t.LINKEDDEPWS,t.LinkedStationName,t.DEVICENAME,mt.typename DEVICECATEGORY_DISPLAY, '查看' LOOKUP,  \n"+
               " nvl(DECODE(di.SFSS,'T','是','F','否'),'否')ISRT,t.WSDEPMC,t.LinkedEquipmentName,t.DEVICEVOLTAGE,  \n"+
               " t.DEVICECATEGORY,t.DEVICEMODEL,t.MANUFACTURER,t.RUNDATE,  \n"+
               " DT.MONITORINGTYPE MONITORINGTYPES,mt.typecode,t.LINKEDDEP,t.linkedprovicedept, T.DEVICECODE,t.OBJ_ID,t.LinkedEquipment    \n"+
               " FROM MW_APP.CMSV_TRANSFDEVICE_XT T, MW_APP.CMST_DEVICEMONITYPE DT,mw_app.cmst_monitoringtype mt,mw_app.cmsv_deviceused_info di    \n"+
               " WHERE t.WSDEPMC NOT LIKE '%电网%' AND t.WSDEPMC NOT LIKE '%分部%'  AND t.linkedprovicedept IS NOT NULL AND T.LINKEDEQUIPMENTDY IS NOT NULL AND T.DEVICECODE = DT.LINKEDDEVICE AND dt.monitoringtype = mt.typecode and    \n"+
               " di.ZZBM (+)= dt.linkeddevice AND di.JCLX (+)= dt.monitoringtype AND T.linkedprovicedept IS NOT NULL AND T.LINKEDEQUIPMENTDY IS NOT NULL "+querySql.toString();*/
		
		try{
			cols = "OBJ_ID,LINKEDSTATION,LINKEDSTATIONOBJ,XTJC,XTMC,XTID,LINKEDDEP,SSBDZDYDJ,DEPMC,XH,LINKEDDEPWS,WSDEPMC,WSDEPJC,LINKEDEQUIPMENTDY,DEVICEVOLTAGE,DYDJGDJB,BZDM,LINKEDEQUIPMENT,LINKEDSTATIONZB,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,LINKEDCAC,LINKEDCAC_DISPLAY,DEVICECATEGORY,DEVICECATEGORYDM,DEVICECODE,DEVICENAME,MONITORINGTYPES,DEVICECATEGORY_DISPLAY,DEVICEMODEL,MANUFACTURER,RELEASEDATE,RUNDATE,RELEASENUMBER,ISACTIVATED,ISACTIVATED_DISPLAY,EQUIPMENTTYPE,EQUIPMENTTYPENAME,YXLS,REMARKS,JCXX,SFKQDW,LINKEDPROVICEDEPT,SFSS,TRANSFPHASE,LOOKUP,ISRT";
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex,pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));

			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行按电压等级统计变电监测装置时出错",e);
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
		StringBuffer queryDydj = new StringBuffer();
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					querySq.append(" and deps.wsid in ('");
					querySq.append(filterValue.replace(",", "','")); 
					querySq.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					queryDydj.append(" and t0.TDYDJ in('");
					queryDydj.append(filterValue.replace(",", "','"));
					queryDydj.append("')");
					querySql.append(" and LINKEDEQUIPMENTDY in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					querySql.append(" and (");
					for(int j=0;j<filterValueArr.length;j++){
						if(j==0){
							querySql.append(" monitoringtypes like '%");
							querySql.append(filterValueArr[j]);
							querySql.append("%'");
						}else{
							querySql.append(" or monitoringtypes like '%");
							querySql.append(filterValueArr[j]);
							querySql.append("%'");
							}
						querySql.append(")");
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
					querySql.append(" and MANUFACTURER  LIKE '%");
					querySql.append(filterValue);
					querySql.append("%' ");
				}else if (Constants.BDZMC.equals(filterKey)){
					queryDydj.append(" and BDZMC LIKE '%");
					queryDydj.append(filterValue);
					queryDydj.append("%' ");
				}
			}
		}
		

//		String sql =" select XTMC,SSDS,BDZMC,ZCXZ,DYDJ,OBJ_ID,SSXT,TYRQ,YXBM,GLOBAL_ID,TDYDJ,ZZ,SSDW,SSDWBM \n"+
// " from (SELECT (SELECT XT.XTMC FROM mw_app.cmst_kq_xt XT WHERE T.SSXT = XT.OBJ_ID) XTMC, \n"+
//       " (SELECT DP.MC FROM mw_app.CMSMV_PMSSYS_PD_DEPS DP WHERE DP.OBJ_ID = T.SSDS) SSDS, \n"+
//       " T.BDZZJ AS BDZMC, \n"+
//       " (SELECT A.DMMC FROM mw_app.CMSMV_PMSAPP_UD_GG_DM  A  WHERE DMLB = '004'  AND A.DM = T.ZCSSJB) ZCXZ, \n"+
//       " (SELECT GY.MC FROM mw_app.CMSMV_PMSAPP_GY_DYDJ  GY  WHERE GY.DM = T.DYDJ) DYDJ, \n"+
//       " OBJ_ID, SSXT, TYRQ, YXBM, GLOBAL_ID, T.DYDJ as TDYDJ, \n"+
//       " ZZ, (select A.MC from mw_app.cmsmv_pmssys_pd_deps_city2ws A  where A.CITY_OBJ_ID =T.YXBM) SSDW, \n"+
//       " (select A.OBJ_ID from mw_app.cmsmv_pmssys_pd_deps_city2ws A where A.CITY_OBJ_ID =T.YXBM) SSDWBM \n"+
//       " FROM mw_app.cmst_sb_znyc_dz T WHERE T.GLOBAL_ID IS NOT NULL) T \n"+
//       " WHERE 1 = 1 "+queryDydj.toString()+"  AND T.GLOBAL_ID in(  \n"+
//       " select Linkedstation from (select *  from MW_APP.CMSV_TRANSFDEVICE where linkedprovicedept IS NOT NULL \n"+
//       " and LINKEDEQUIPMENTDY IS NOT NULL "+querySql.toString()+") transf, MW_APP.CMST_PROVICE_DEPS deps \n"+
//       " where deps.province_id = transf.linkedprovicedept(+) "+querySq.toString()+" ) \n"+
//       " AND T.SSDW is not null ORDER BY TYRQ DESC";
		String sql =  " select XTMC,SSDS,BDZMC,ZCXZ,DYDJ,OBJ_ID,SSXT,TYRQ,SBBM,TDYDJ,ZZ,SSDW,SSDWBM  \n"+
				 " from (SELECT (SELECT XT.XTMC FROM mw_app.cmst_kq_xt XT WHERE T.SSXT = XT.OBJ_ID) XTMC,  \n"+
				 "  (select A.WSMC from mw_app.cmst_zb_comm_wspz A  where A.WSID =T.SSWS) SSDS,  \n"+
				 "  T.BDZMC,  \n"+
				 " (SELECT A.DMMC FROM mw_app.cmst_dw_bzzx_ggdmb  A  WHERE BZFLBM = '004'  AND A.DM = T.ZCBH) ZCXZ,  \n"+
				 " (SELECT GY.DYDJMC FROM mw_app.CMST_SB_PZ_SBDYDJ  GY  WHERE GY.DYDJBM = T.DYDJ) DYDJ,  \n"+
				 " OBJ_ID, SSXT, TYRQ, SBBM, T.DYDJ as TDYDJ,  \n"+
				 " ZZ, (SELECT DP.bmmc FROM mw_app.CMST_SPECIALORG_UNIT_LOCEXT DP WHERE DP.ISC_ID = T.SSWS) SSDW,  \n"+
				 " T.SSWS SSDWBM \n"+
				 " FROM mw_app.cmst_sb_znyc_dz T WHERE T.sbbm IS NOT NULL) t0  \n"+
				 " WHERE 1 = 1 "+queryDydj.toString()+"  AND t0.sbbm in(   \n"+
				 " select Linkedstation from (select *  from MW_APP.CMSV_TRANSFDEVICEF where linkedprovicedept IS NOT NULL  \n"+
				 " and LINKEDEQUIPMENTDY IS NOT NULL "+querySql.toString()+") transf, MW_APP.cmst_zb_comm_wspz deps  \n"+
				 " where deps.wsid = transf.linkedprovicedept(+)  "+querySq.toString()+"  )  \n"+
//				 " AND t0.SSDW is not null ORDER BY TYRQ DESC ";
				 " ORDER BY TYRQ DESC ";
		
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
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public QueryResultObject query(RequestCondition queryCondition) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		int pageSize = Integer.valueOf(queryCondition.getPageSize()); // 每页的数据量
		int pageIndex = Integer.valueOf(queryCondition.getPageIndex()); // 开始编号
		StringBuffer querySql = new StringBuffer();
		
		if (null != queryCondition.getFilter()) {
			String[] filter = queryCondition.getFilter().toString().split("&"); // 对传来的参数进行分割
			for (int i = 0; i < filter.length; i++) {
				// 判断投运日期,生产厂家,变电站名称
				String filterKey = filter[i].split("=")[0].trim();
				String filterValue = filter[i].split("=")[1].trim();
				if (Constants.SSDW.equals(filterKey)) {
					querySql.append(" and linkedprovicedept in ('");
					querySql.append(filterValue.replace(",", "','")); 
					querySql.append("')");
				} else if (Constants.DYDJ.equals(filterKey)) {
					querySql.append(" and LINKEDEQUIPMENTDY  in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
				} else if (Constants.JCLX.equals(filterKey)){
					String[] filterValueArr=filterValue.split(",");
					for(int j=0;j<filterValueArr.length;j++){
					querySql.append(" and MONITORINGTYPES in ('");
					querySql.append(filterValue.replace(",", "','"));
					querySql.append("')");
					}
				}
				else if (Constants.TYRQ.equals(filterKey)) {
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
				}
			}
		}
		
		String sql = "select OBJ_ID,WSDEPMC,XTMC,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,CAST('查看' AS VARCHAR(42)) LOOKUP,XH as JCXH,DEVICENAME,DEVICEMODEL,MANUFACTURER,DECODE(sfss,'T','是','F','否','','否',NULL,'否') SFSS,RUNDATE,linkedprovicedept,DEVICECODE,MONITORINGTYPES,LINKEDCAC_DISPLAY \n" +
				" FROM MW_APP.CMSV_TRANSFDEVICEF T \n" +
				" where 1 = 1 and t.WSDEPMC is not null and t.DEVICEVOLTAGE is not null \n" +
				" and MONITORINGTYPES LIKE '02%' "+querySql.toString()+" order by t.XH ";
		
		
		String cols  = "OBJ_ID,WSDEPMC,XTMC,LINKEDSTATIONNAME,LINKEDEQUIPMENTNAME,DEVICECATEGORY,DEVICECATEGORY_DISPLAY,LOOKUP,JCXH,DEVICENAME,DEVICEMODEL,MANUFACTURER,IFSS,RUNDATE"; 
		

		try{
			result = hibernateDao_ztjc.executeSqlQuery(sql,pageIndex, pageSize);
			result = transToColumns(result, cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + sql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行变电监测装置查询统计的初始化DataGrid的查询SQL中发生异常",e);
		}
		return qro;
	}
		
}
