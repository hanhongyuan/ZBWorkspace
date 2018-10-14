package com.sgcc.pms.zbztjc.common.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;

public class CommonBizc implements ICommonBizc{

	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	private final static Log log = LogFactory.getLog(CommonBizc.class);
	
	@Override
	public QueryResultObject getDropDownEditorById(String sqlId) {
		QueryResultObject qro = new QueryResultObject();
		List resultList = null;
		try{
			String sql = "";
			if("dicts_ssdw".equals(sqlId)){
				sql="SELECT WSID ,WSMC FROM MW_APP.CMST_ZB_COMM_WSPZ ORDER BY ZDYPX";
			}else if("dicts_ssxt".equals(sqlId)){
				sql = "select CAST(obj_id AS VARCHAR2(42)) obj_id,xtmc from cmst_kq_xt order by xtmc";
			}else if("dicts_dydj".equals(sqlId)){
				sql = "SELECT DYDJBM,DYDJMC FROM CMST_SB_PZ_SBDYDJ order by DYDJBM";
			}else if("dicts_jclx".equals(sqlId)){
				sql = "select typecode dm,typename mc from mw_app.cmst_monitoringtype where typecode like '01%' and typecode not in ('018002','018003')";
			}else if("dicts_jclxBd".equals(sqlId)){
				sql = "SELECT typecode dm, typename mc from mw_app.cmst_monitoringtype where typecode like '02%' and typecode not in ('021007', '025001','026001')";
			}else if("dicts_jclxDl".equals(sqlId)){
				sql = "SELECT typecode dm, typename mc from mw_app.cmst_monitoringtype where typecode like '03%' and typecode not in ('032010', '032011')";
			}else if("dicts_gjjb".equals(sqlId)){
				sql = "SELECT '1' dm, CAST('报警' AS VARCHAR2(10)) mc FROM dual UNION ALL SELECT '2' dm, CAST('预警' AS VARCHAR2(10)) mc FROM dual";
			}else{
				log.info("所选择的下拉框URL未识别！");
			}
			resultList = hibernateDao_ztjc.executeSqlQuery(sql);
			if(null != resultList && 0 < resultList.size()){		//如果LIST不为空
				List<Map<String,String>> dicts = new ArrayList<Map<String,String>>();		//封装数据成一个元素为Map<String,String>的List
				for (Object result : resultList) {
					Object[] obj = (Object[]) result;
					Map<String,String> map = new HashMap<String, String>();
					map.put("value", String.valueOf(obj[0]));
					map.put("text", String.valueOf(obj[1]));
					dicts.add(map);
				}
				qro.setItems(dicts);
			}
			
			return qro;
		}catch(Exception e){
			e.printStackTrace();
			log.info("执行初始化下拉框时发生异常",e);
		}
		return null;
	}

}
