package com.sgcc.pms.zbztjc.util.tgypz.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.pms.zbztjc.util.manufacturerZb.bizc.ManufacturerZbBizc;
import com.sgcc.pms.zbztjc.util.myUtils.Util;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;
import com.sgcc.uap.rest.support.DicItems;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.RestUtils;
/**
 * 用户定义逻辑构件
 * 
 * @author Jarvis 
 * 
 */
public class TgypzBizc implements ITgypzBizc{

	@Autowired
	private IHibernateDao hibernateDao_ztjc;
	
	// 日志
	private final static Log logger = LogFactory.getLog(ManufacturerZbBizc.class);
	
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List saveOrUpdate(List<Map> list) {
		 
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			if(!map.containsKey("mxVirtualId")){
				String id = (String)map.get("xh");
				update(map, id);
			} else {
				save(map);
			}
		}
		return list;
	 }
	 
	//保存记录
	private void save(Map map) {
		logger.info("保存记录：");
		try {
			String sql = "INSERT INTO MW_APP.CMST_EQUIPMENT_DISPOSE(SBLX, YXBH, MONITORINGTYPE, SBLXMC, SCMC, TYPENAME, LINKEDSTATION, XH) " +
					"values(" +
						dbValue(map.get("sblx")) +"," +
						dbValue(map.get("yxbh")) +"," +
						dbValue(map.get("monitoringtype")) +"," +
						dbValue(map.get("sblxmc")) +"," +
						dbValue(map.get("scmc")) +"," +
						dbValue(map.get("typename")) +"," +
						dbValue(map.get("linkedstation")) +"," +
						dbValue(map.get("xh")) +
					")";
//			System.out.println("测试: "+ sql);
			hibernateDao_ztjc.executeSqlUpdate(sql);
		} catch (Exception e) {
			logger.info("保存记录出错：", e);
			e.printStackTrace();
		}
	}
	
	//更新记录
	private void update(Map map, String id) {
		logger.info("更新记录：");
		try {
			Object[] keys = map.keySet().toArray();
			Object[] values = map.values().toArray();
			String set = "";
			// 用来匹配大写字母
			Pattern aPattern = Pattern.compile("[A-Z]");
			
			// 拼接sql
			for(int i = 0; i < keys.length; i++) {
				if(i > 0) set += ", ";
				
				// 通过正则来判断列名是否需要加下划线 因此前台名字要遵循下划线后的字母大写的规则 OBJ_ID => objId
				Matcher aMatcher = aPattern.matcher(keys[i].toString());
				if(aMatcher.find()) {
					String str = aMatcher.group();
					set += keys[i].toString().replace(str, "_"+ str).toUpperCase();
				} else 
					set += keys[i].toString().toUpperCase();
				set += "="+ dbValue(values[i]);
			}
			String sql = "UPDATE MW_APP.CMST_EQUIPMENT_DISPOSE SET " + set + " WHERE XH = ?";
			hibernateDao_ztjc.executeSqlUpdate(sql, new Object[]{ id });
		} catch (Exception e) {
			logger.info("更新记录出错：", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject) {
		logger.info("删除: ");
		String[] ids = idObject.getIds();
		try {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				String sql = "DELETE FROM MW_APP.CMST_EQUIPMENT_DISPOSE WHERE XH = ?";
				hibernateDao_ztjc.executeSqlUpdate(sql, new Object[] { id });
			}
		} catch (Exception e) {
			logger.info("删除出错: ", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition params) {
		logger.info("根据输入条件查询记录：");
		QueryResultObject qro = new QueryResultObject();
		
		try {
			int pageIndex = Integer.valueOf(params.getPageIndex()),
				pageSize = Integer.valueOf(params.getPageSize());
			String cols = params.getColumns(),
			querySql = "SELECT SBLX, SCMC, MONITORINGTYPE, XH " +
					"FROM MW_APP.CMST_EQUIPMENT_DISPOSE WHERE 1 = 1";

			if(null != params.getFilter()) {
				String filter = (String) params.getFilter();
				querySql += filter==null ? StringUtils.EMPTY : " AND " + filter;
			}
			querySql += " ORDER BY XH";
			
			List result = hibernateDao_ztjc.executeSqlQuery(querySql, pageIndex, pageSize);
			result = transToColumns(result, cols);
			List count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) (count.get(0))).intValue());
			return qro.addDicItems(wrapDictList());
		} catch (NumberFormatException e) {
			logger.info("根据输入条件查询记录出错：", e);
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		logger.info("查询单条记录：");
		Object[] objs = null;
		QueryResultObject qro = new QueryResultObject();
		try {
			String sql = "SELECT SBLX, YXBH, MONITORINGTYPE, LINKEDSTATION, XH " +
								"FROM MW_APP.CMST_EQUIPMENT_DISPOSE WHERE XH = '" + id + "'";
			
			List result = hibernateDao_ztjc.executeSqlQuery(sql);
			objs = (Object[]) result.get(0); // 用来存储原本查出来的值
			result = transToColumns(result, "sblx,yxbh,monitoringtype,linkedstation,xh");
			qro.setItems(result);
			String sblx = (String) objs[0];
			String yxbh = (String) objs[1];
			String linkedStation = (String) objs[3];
			return qro.addDicItems(editDictList(sblx, yxbh, linkedStation));
		} catch (Exception e) {
			logger.info("查询单条记录出错：", e);
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
	   
	private Object dbValue(Object value) {
	   return dbValue(value, null);
	}
	
	private Object dbValue(Object value, Object appendVal) {
	   // 判断类型
	   if(value instanceof Integer) // 数字类型
		   return (Integer)value;
	   
	   else if(value instanceof String) { // 字符串类型
		   String[] strs = ((String) value).split("-");
		   if(strs.length == 3 && Util.isNumeric(strs[1])) //日期
			   return "TO_DATE('"+ value +"', 'yyyy-mm-dd')";
		   else
			   return "'"+ value + (appendVal == null ? StringUtils.EMPTY : appendVal) +"'";
	   }
	   
	   else // 其他类型或为空 应该都不会有追加值的情况
		   return (value == null ? "null" : value);
	}

	/**
	 * 初始化字典值
	 * 
	 * @return QueryResultObject
	 */
	public QueryResultObject initDict() {
		QueryResultObject query = new QueryResultObject();
		return query.addDicItems(wrapDictList());
	}
	
	// 编辑时的数据字典
	private List<DicItems> editDictList(String sblx, String yxbh, String linkedStation) {
		List<DicItems> dicts = new ArrayList<DicItems>();

		// 变电站编码: 32M00000005749094, 32M00000005749090, 32M00000005749092
        dicts.add(Util.translateFromDB("sblx", "MW_APP.CMST_DW_BZZX_SBFL", "SBLXBM", "SBLX", 
        		"SBLXBM IN (SELECT T.EQUIPMENTTYPE FROM MW_APP.CMSV_TRANSFDEVICEF T"
				+" WHERE T.LINKEDSTATION = '"+ linkedStation +"' AND T.EQUIPMENTTYPE IS NOT NULL"
				+" GROUP BY T.EQUIPMENTTYPE)"));
        dicts.add(Util.translateFromDB("yxbh", getSbTable(sblx) + " SB, MW_APP.CMSV_TRANSFDEVICEF A", 
        		"SB.SBBM", "SB.SBMC", 
        		// 根据变电站过滤逻辑装置
        		"A.LINKEDSTATION = '"+ linkedStation +
        		// 根据逻辑装置过滤一次设备
        		"' AND A.LINKEDEQUIPMENT = SB.SBBM " +
        		"GROUP BY SB.SBBM, SB.SBMC"));
        dicts.add(Util.translateFromDB("monitoringtype", "MW_APP.CMST_MONITORINGTYPE M", "TYPECODE", "TYPENAME", 
        		"M.TYPECODE IN (SELECT T.MONITORINGTYPES FROM MW_APP.CMSV_TRANSFDEVICEF T, " + getSbTable(sblx) + " SB "
				+" WHERE T.LINKEDEQUIPMENT = SB.SBBM AND T.LINKEDSTATION = '"+ linkedStation +
				"' AND SB.SBBM = '"+ yxbh +"' GROUP BY T.MONITORINGTYPES)"));
        return dicts;
	}

	// 将字典对象封装为list 展示数据时的数据字典
	private List<DicItems> wrapDictList() {
		List<DicItems> dicts = new ArrayList<DicItems>();
		
        dicts.add(Util.translateFromDB("sblx", "MW_APP.CMST_DW_BZZX_SBFL", "SBLXBM", "SBLX"));
        dicts.add(Util.translateFromDB("monitoringtype", "MW_APP.CMST_MONITORINGTYPE", "TYPECODE", "TYPENAME"));
		return dicts;
	}

	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}

	@Override
	public QueryResultObject selectSblx(String linkedStation) {
		QueryResultObject query = new QueryResultObject();
		
		List<DicItems> dicts = new ArrayList<DicItems>();
		// 该字典是根据变电站下的逻辑装置过滤设备类型
        dicts.add(Util.translateFromDB("sblx", "MW_APP.CMST_DW_BZZX_SBFL", "SBLXBM", "SBLX", 
        		// 根据逻辑装置过滤设备类型
        		"SBLXBM IN (SELECT T.EQUIPMENTTYPE FROM MW_APP.CMSV_TRANSFDEVICEF T"
        		// 根据变电站过滤逻辑装置
				+" WHERE T.LINKEDSTATION = '"+ linkedStation +"' AND T.EQUIPMENTTYPE IS NOT NULL"
				+" GROUP BY T.EQUIPMENTTYPE)"));
		return query.addDicItems(dicts);
	}

	@Override
	public QueryResultObject selectSbmc(RequestCondition params) {
		QueryResultObject query = new QueryResultObject();
		List<DicItems> dicts = new ArrayList<DicItems>();
		
		String filter = (String) params.getFilter();
		String[] filters = filter.split(",");
		// 查出所选设备类型对应的表
		String sbTable = getSbTable(filters[1]);
		// 该字典是根据所选设备类型和变电站下的逻辑装置过滤一次设备
        dicts.add(Util.translateFromDB("yxbh", sbTable + " SB, MW_APP.CMSV_TRANSFDEVICEF A", 
        		"SB.SBBM", "SB.SBMC", 
        		// 根据变电站过滤逻辑装置
        		"A.LINKEDSTATION = '"+ filters[0] +
        		// 根据逻辑装置过滤一次设备
        		"' AND A.LINKEDEQUIPMENT = SB.SBBM " +
        		"GROUP BY SB.SBBM, SB.SBMC"));
        //
		return query.addDicItems(dicts);
	}

	@Override
	public QueryResultObject selectJclx(RequestCondition params) {
		QueryResultObject query = new QueryResultObject();
		List<DicItems> dicts = new ArrayList<DicItems>();
		
		String filter = (String) params.getFilter();
		String[] filters = filter.split(",");
        dicts.add(Util.translateFromDB("monitoringtype", "MW_APP.CMST_MONITORINGTYPE M", "TYPECODE", "TYPENAME", 
        		"M.TYPECODE IN (SELECT T.MONITORINGTYPES FROM MW_APP.CMSV_TRANSFDEVICEF T, " + getSbTable(filters[1]) + " SB "
				+" WHERE T.LINKEDEQUIPMENT = SB.SBBM AND T.LINKEDSTATION = '"+ filters[0] +
				"' AND SB.SBBM = '"+ filters[2] +"' GROUP BY T.MONITORINGTYPES)"));
		return query.addDicItems(dicts);
	}

	private String getSbTable(String sblx) {
		
		
		try {
			String sql = "SELECT TZBM FROM CMST_DW_BZZX_SBFL WHERE SBLXBM = ?";
			sql = hibernateDao_ztjc.executeSqlQuery(sql, new Object[] { sblx }).get(0).toString();
			return "CMS" + sql;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
