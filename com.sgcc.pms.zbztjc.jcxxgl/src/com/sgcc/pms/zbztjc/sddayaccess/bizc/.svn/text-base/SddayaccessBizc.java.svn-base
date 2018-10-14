package com.sgcc.pms.zbztjc.sddayaccess.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public class SddayaccessBizc implements ISddayaccessBizc{
	
	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	
	
	private final Log logger = LogFactory.getLog(SddayaccessBizc.class);

	@SuppressWarnings("unchecked")
	public QueryResultObject loadtjxx(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		try{
			 cols = "SSWS,SSWSMC,ZS012001,ZS013001,ZS013002,ZS013003,ZS013004,ZS013005,ZS013006,ZS014001,ZS018001,ZS018002,ZS018003,JR012001,JR013001,JR013002,JR013003,JR013004,JR013005,JR013006,JR014001,JR018001,JR018002,JR018003,ZSHJ,JRSHJ,SSJRL";
			String querySql = 
					"SELECT SSWS,SSWSMC,ZS012001,ZS013001,ZS013002,ZS013003,ZS013004,ZS013005,ZS013006,ZS014001,\n" +
							"ZS018001,ZS018002,ZS018003,JR012001,JR013001,JR013002,JR013003,JR013004,JR013005,\n" + 
							"JR013006,JR014001,JR018001,JR018002,JR018003,ZSHJ,JRSHJ,SSJRL FROM MW_APP.CMST_SBPROVINCEACCESSRATE where 1=1 ";


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
							   querySql += " AND SSWS in ("+deptString+")";
					   }
					  
					   
					   else if("savetime".equals(qc[i].split("=")[0]))
					   {
						   querySql += " and savetime = '" + qc[i].split("=")[1] + "'";
					   }
					  
					   
				   }
			   }
			  querySql += " order by XH";
			result = hibernateDao_ztjc.executeSqlQuery(querySql);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro.addDicItems(wrapDictList());
		} catch(Exception e){
			logger.info("错误信息", e);
			e.printStackTrace();
		}
		
		return null;
	}

	public QueryResultObject loadyfxx(RequestCondition params) {
		QueryResultObject qro = new QueryResultObject();
		List result = new ArrayList();
		List count = new ArrayList();
		String cols = "";
		String year = "";
		try{
			int pageSize = Integer.valueOf(params.getPageSize());
			int pageIndex = Integer.valueOf(params.getPageIndex());
			 cols = "SSWS,SSWSMC,ZSHJ1,ZSHJ2,ZSHJ3,ZSHJ4,ZSHJ5,ZSHJ6,ZSHJ7,ZSHJ8,ZSHJ9,ZSHJ10,ZSHJ11,ZSHJ12";
			 
			 String[] qc1 = params.getFilter().toString().split("&");
			   for(int i = 0; i < qc1.length; i++) {
				   
				  
				   if("savetime".equals(qc1[i].split("=")[0])) {
					 //查询年份
					    year = qc1[i].split("=")[1];
						  
				   }
			   }
			String querySql =
					"select l.ssws,l.sswsmc,\n" +
							"       decode(zshj1, null, '-', zshj1) zshj1,\n" + 
							"       decode(zshj2, null, '-', zshj2) zshj2,\n" + 
							"       decode(zshj3, null, '-', zshj3) zshj3,\n" + 
							"       decode(zshj4, null, '-', zshj4) zshj4,\n" + 
							"       decode(zshj5, null, '-', zshj5) zshj5,\n" + 
							"       decode(zshj6, null, '-', zshj6) zshj6,\n" + 
							"       decode(zshj7, null, '-', zshj7) zshj7,\n" + 
							"       decode(zshj8, null, '-', zshj8) zshj8,\n" + 
							"       decode(zshj9, null, '-', zshj9) zshj9,\n" + 
							"       decode(zshj10, null, '-', zshj10) zshj10,\n" + 
							"       decode(zshj11, null, '-', zshj11) zshj11,\n" + 
							"       decode(zshj12, null, '-', zshj12) zshj12\n" + 
							"  from (SELECT distinct ssws, sswsmc, xh FROM MW_APP.CMST_SBPROVINCEACCESSRATE) l,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj1, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-01-21') l1,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj2, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-02-21') l2,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj3, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-03-21') l3,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj4, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-04-21') l4,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj5, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-05-21') l5,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj6, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-06-21') l6,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj7, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-07-21') l7,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj8, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-08-21') l8,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj9, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-09-21') l9,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj10, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-10-21') l10,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj11, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-11-21') l11,\n" + 
							"       (SELECT ssws, sswsmc, zshj zshj12, xh\n" + 
							"          FROM MW_APP.CMST_SBPROVINCEACCESSRATE\n" + 
							"         where savetime = '"+year+"-12-21') l12\n" + 
							" where l.ssws = l1.ssws(+)\n" + 
							"   and l.ssws = l2.ssws(+)\n" + 
							"   and l.ssws = l3.ssws(+)\n" + 
							"   and l.ssws = l4.ssws(+)\n" + 
							"   and l.ssws = l5.ssws(+)\n" + 
							"   and l.ssws = l6.ssws(+)\n" + 
							"   and l.ssws = l7.ssws(+)\n" + 
							"   and l.ssws = l8.ssws(+)\n" + 
							"   and l.ssws = l9.ssws(+)\n" + 
							"   and l.ssws = l10.ssws(+)\n" + 
							"   and l.ssws = l11.ssws(+)\n" + 
							"   and l.ssws = l12.ssws(+)\n";
							
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
							   querySql += " AND l.SSWS in ("+deptString+")";
					   }
				   }
			   }
			  querySql += " order by l.XH";
			result = hibernateDao_ztjc.executeSqlQuery(querySql,pageIndex,pageSize);
			result = transToColumns(result,cols);
			count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro.addDicItems(wrapDictList());
		} catch(Exception e){
			logger.info("错误信息", e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	//将变电的数据字典封装成list
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
		
		String deptsqlString = "select sswsmc,ssws from (SELECT distinct xh,ssws,sswsmc from MW_APP.CMST_SBPROVINCEACCESSRATE) order by xh";
	    dicts.add(translateFromDBT("linkeddepws",deptsqlString));
		
		return dicts;
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
    private Map<String, String> keyvalueToMap(String key,String value,String realkey, String realvalue){
	    Map map = new HashMap();
	    map.put(key, realkey);
	    map.put(value, realvalue);
	    return map;
	}
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 private List<Map> transToColumns(List<Object[]> list, String columns){
	       List<Map> items = new ArrayList();
	       String[] cols = columns.split("\\,");
	       for (int i = 0; i < list.size(); i++){
	           Map map = new HashMap();
	           for (int m = 0; m < cols.length; m++){
	        	   map.put(cols[m], list.get(i)[m]);
	           }
	           items.add(map);
	       }
	       return items;
	   }
	public void setDataDictionaryBizC(IDataDictionaryBizC dataDictionaryBizC) {
		this.dataDictionaryBizC = dataDictionaryBizC;
	}
	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}

}
