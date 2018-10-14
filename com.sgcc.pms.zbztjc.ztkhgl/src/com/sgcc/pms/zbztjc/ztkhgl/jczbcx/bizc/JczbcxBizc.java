package com.sgcc.pms.zbztjc.ztkhgl.jczbcx.bizc;


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
public class JczbcxBizc implements IJczbcxBizc{

	private final Log log = LogFactory.getLog(JczbcxBizc.class);
	@Resource
	private IHibernateDao hibernateDao_ztjc;


	
	 public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}


	/**
	    * 审核信息查询
	    * @param bdxxcx
	    * @return
	    */
	   @SuppressWarnings("unchecked")
	public QueryResultObject loadinfo(RequestCondition params) {
		   QueryResultObject qro = new QueryResultObject();
		   List result = new ArrayList();
		   String cols  = "OBJ_ID,DWMC,SDJRL,SDLXX,SDYXX,SDCLL,BZKYYL,BDJRL,BDLXX,BDYXX,BDCLL,TJSJ"; 

		   String sql = "select * from(select CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID,DWMC,SDJRL,SDLXX,SDYXX,SDCLL,BZKYYL,BDJRL,BDLXX,BDYXX,BDCLL,TJSJ from (select distinct tp.wsmc as dwmc,ta.obj_id,ta.sdjrl,ta.bdjrl,ta.sdlxx,ta.bdlxx,ta.sdyxx,ta.bdyxx,ta.sdcll,ta.bdcll,ta.bzkyyl,ta.tjsj,tp.zdypx  "+
					" from mw_app.cmst_target ta,mw_app.cmst_zb_comm_wspz tp where 1=1 and tp.wsid = ta.dwbm and ta.tjsj is not null order by ta.dwbm) where 1=1 and tjsj is not null  order by zdypx asc)  ";
					
				try{
				result = hibernateDao_ztjc.executeSqlQuery(sql);
				result = transToColumns(result, cols);
				qro.setItems(result);
				return qro;
			}catch(Exception e){
				e.printStackTrace();
				log.info("执行检查指标查询SQL中发生异常",e);
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
			public List<Map> transToColumns(List<Object[]> list, String columns) {
				List<Map> items = new ArrayList();
				String[] cols = columns.split("\\,");
				for (int i = 0; i < list.size(); i++) {
					Map map = new HashMap();
					for (int m = 0; m < cols.length; m++) {
						map.put(cols[m], list.get(i)[m]);
					}
					items.add(map);
				}
				return items;
			}
	   

	  
	   
}

