package com.sgcc.pms.zbztjc.util.chart.bizc;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.xfire.client.Client;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Hibernate;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import com.sgcc.pms.zbztjc.util.loggerSave.bizc.ILoggerSaveBizc;
import com.sgcc.pms.zbztjc.util.myUtils.ConstantsDataBase;
import com.sgcc.pms.zbztjc.util.myUtils.Util;
import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.RestUtils;

public class ChartBizc implements IChartBizc {

	@Resource
	private IHibernateDao hibernateDao_ztjc;
	
	@Resource
	private ILoggerSaveBizc loggerSaveBizc ;
	
	private String aTable, aCslsb;
	private Map<String, String> monitoringPara;

	private final String TIME = " 00:00:00";
    // 设置要获取到什么样的时间
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}

	@Override
	public QueryResultObject getDeviceName(String monitoringtype, String devicecode) {
		
		try {
			String sql = "SELECT DEVICENAME FROM "+ ConstantsDataBase.SCHEMA +"? WHERE DEVICECODE = '" + devicecode + "'";
			// 判断装置输变电性质
			if(monitoringtype.indexOf("01") == 0) {
				sql = sql.replace("?", "CMST_LINEDEVICE");
			} else if(monitoringtype.indexOf("02") == 0) {
				sql = sql.replace("?", "CMST_TRANSFDEVICE");
			} else {
				sql = sql.replace("?", "CMST_CABLEDEVICE_LOGIC");
			}
			List<?> list = hibernateDao_ztjc.executeSqlQuery(sql);
			return RestUtils.wrappQueryResult(list);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
/*	@Override
	public QueryResultObject getPzInfoByWsID(String wsid) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select IPPORT from mw_sys.cmst_deptidip where DEPTID='");
			sb.append(wsid);
			sb.append("' ");
			List list = hibernateDao_ztjc.executeSqlQuery(sb.toString());
			return RestUtils.wrappQueryResult(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	public Map<String, String> getPzInfoByWsID(String wsId) {
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		sb.append("select IPPORT from mw_sys.cmst_deptidip where DEPTID= ? ");
		try {
			List<String> lis = hibernateDao_ztjc.executeSqlQuery(sb.toString(),new Object[]{wsId});
			if(lis.size() > 0){
				map.put("PMSIP", lis.get(0) == null?"":lis.get(0).toString());
			}
			else {
				map.put("PMSIP", "");
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 组态图公共方法
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public QueryResultObject getChart(RequestCondition params, String monitoringtype, String devicecode,String deptws) {
		QueryResultObject resultVal = new QueryResultObject();
		List<String> itemVal = new ArrayList<String>();
		// TODO 通用方式获取折线图数据
		String[] paraName = params.getColumns().split(",");
		String coluname = params.getColumns();
		String where = (String) params.getFilter();
		String[] filters = where.split(",");
		boolean isRange = filters.length == 2;
		
		String moniTime = getMonitoringTime(monitoringtype);
		//假数据 013004
		devicecode = "29M00000000865344";
		List<Object[]> list = new ArrayList<Object[]>();
//		条件拼接
		if (isRange) {
			// 自定义时间查看(区间) 格式：开始日期，结束日期
			where = "DEVICECODE = '" + devicecode + 
					"' AND TRUNC("+ moniTime +") BETWEEN DATE'" + filters[0] + 
					"' AND DATE'" + filters[1] + "'";
		} else {
			// 选定时间查看(最近一段时间) 格式：3 || 7 || 30
			where = "DEVICECODE = '" + devicecode + 
					"' AND TRUNC(" + moniTime + ") > TRUNC(SYSDATE)-" + filters[0];
		}
		
//		sql拼接
		String table = getMonitypeTable(monitoringtype, devicecode);
//		if(null != table){
//			table=table.replaceAll("(?i)mw_app.", "UAP_APP.");
//		}
		//获取网省的接口地址
		String urlVal = getServiceUrl(deptws);
		System.out.println(urlVal);
		if(!urlVal.contains("?wsdl")){
			urlVal +="?wsdl";
		}
		try {
			System.out.println(urlVal);
			Client client = new Client(new URL(urlVal));
			client.setTimeout(20000);
	       	Object[] objects = client.invoke("getZTT", new Object[] {coluname,table,where,moniTime });
	       	Document doc = null;
	       	String xml = objects[0].toString();
	       	System.out.println(xml);
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            System.out.println(xml);
            Element rootElt = doc.getRootElement(); // 获取根节点
            Element monitordata = rootElt.element("ResultValue");
            System.out.println(monitordata.asXML());
            if(monitordata.isTextOnly()){
            	itemVal.add(monitordata.getText());
            	resultVal.setItems(itemVal);
    			return resultVal;
    		}
    		Element datanode = monitordata.element("DataTable");
    		System.out.println(datanode.asXML());
    		Element row = datanode.element("Rows");
    		System.out.println(row.asXML());
    		Iterator it = row.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                Element jclxdata = (Element) it.next();
                System.out.println(jclxdata.asXML());
                List<Attribute> dataAttrs = jclxdata.attributes();
                for (Attribute attr : dataAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值："
                            + attr.getValue());
                }
                Iterator itt = jclxdata.elementIterator();
                List jclxlist = new ArrayList();
                Object[] aaa = new Object[]{};
                while (itt.hasNext()) {
                    Element jclxChild = (Element) itt.next();
                    System.out.println("节点名：" + jclxChild.getName() + "--节点值：" + jclxChild.getStringValue());
                    jclxlist.add(jclxChild.getStringValue());
                    aaa = jclxlist.toArray();
                }
                list.add(aaa);
                System.out.println("=====结束=====");
            }
            System.out.println(list.toString());
         // 塞入中文名称
    		Map<String, String> para = getMonitoringPara(monitoringtype, devicecode);
    		for(int i = 0; i < paraName.length; i++) {
    			paraName[i] = para.get(paraName[i]);
    		}
    		list.add(paraName);
    		loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-组态图查询", "操作成功");
    		return RestUtils.wrappQueryResult(list);
       } catch (Exception e) {
           e.printStackTrace();
           loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-组态图查询", "操作失败");
       }
		return null;
	}

	/**
	 * 历史数据公共方法
	 */
	@Override
	public QueryResultObject getHistoryData(RequestCondition params,
			String monitoringtype, String devicecode) {
//		logger.info("根据输入条件查询记录：");
		QueryResultObject qro = new QueryResultObject();
		
		String[] filters = params.getFilter().toString().split(",");
		boolean isRange = filters.length == 2;
		
		String moniTime = getMonitoringTime(monitoringtype),
				table = getMonitypeTable(monitoringtype, devicecode);
		
		try {
			int pageSize = Integer.valueOf(params.getPageSize());
			int pageIndex = Integer.valueOf(params.getPageIndex());
			String cols = params.getColumns().toString();
			String querySql = "SELECT "+ cols.replace(moniTime, "TO_CHAR("+ moniTime +", 'yyyy-MM-dd HH24:MI:SS')") + " FROM "+ table;

//			条件拼接
			if (isRange) {
				// 自定义时间查看(区间) 格式：开始日期，结束日期
				querySql += " WHERE DEVICECODE = '" + devicecode + 
					"' AND TRUNC("+ moniTime +") BETWEEN DATE'" + filters[0] + 
					"' AND DATE'" + filters[1] + "'";
			} else {
				// 选定时间查看(最近一段时间) 格式：3 || 7 || 30
				querySql += " WHERE DEVICECODE = '" + devicecode + 
					"' AND TRUNC(" + moniTime + ") > TRUNC(SYSDATE)-" + filters[0];
			}
			querySql += " ORDER BY "+ moniTime +" DESC";
			System.out.println("aaaaaaaa 183");
			List result = hibernateDao_ztjc.executeSqlQuery(querySql, pageIndex, pageSize);
			result = transToColumns(result, cols);
			List count = (List<Number>) (hibernateDao_ztjc.executeSqlQuery("SELECT COUNT(*) FROM (" + querySql + ")"));
			qro.setItems(result);
			qro.setItemCount(((Number) count.get(0)).intValue());
			return qro;
		} catch (Exception e) {
//			logger.info("根据输入条件查询记录出错：", e);
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 最新数据公共方法
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public QueryResultObject getLatest(String monitoringtype, String devicecode) {
		// TODO 通用方式获取最新数据
		String moniTime = getMonitoringTime(monitoringtype),
				table = getMonitypeTable(monitoringtype, devicecode);
		if(null != table){
			table=table.replaceAll("(?i)mw_app.", "UAP_APP.");
		}
		Map<String, String> para = getMonitoringPara(monitoringtype, devicecode);
		String columname = Util.arrayToString(para.keySet().toArray());
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			Client client = new Client(new URL("http://172.16.222.102:8080/BusinessModelNew/services/CMDataService?wsdl"));
			client.setTimeout(20000);
	       	Object[] objects = client.invoke("getLastTimeList", new Object[] {columname,table,devicecode,monitoringtype,moniTime });
	       	Document doc = null;
	       	String xml = objects[0].toString();
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            System.out.println(xml);
            Element rootElt = doc.getRootElement(); // 获取根节点
            Element monitordata = rootElt.element("ResultValue");
            System.out.println(monitordata.asXML());
    		Element datanode = monitordata.element("DataTable");
    		System.out.println(datanode.asXML());
    		Element row = datanode.element("Rows");
    		System.out.println(row.asXML());
    		Iterator it = row.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                Element jclxdata = (Element) it.next();
                System.out.println(jclxdata.asXML());
                List<Attribute> dataAttrs = jclxdata.attributes();
                for (Attribute attr : dataAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值："
                            + attr.getValue());
                }
                Iterator itt = jclxdata.elementIterator();
                List jclxlist = new ArrayList();
                Object[] aaa = new Object[]{};
                while (itt.hasNext()) {
                    Element jclxChild = (Element) itt.next();
                    System.out.println("节点名：" + jclxChild.getName() + "--节点值：" + jclxChild.getStringValue());
                    jclxlist.add(jclxChild.getStringValue());
                    aaa = jclxlist.toArray();
                }
                list.add(aaa);
                System.out.println("=====结束=====");
            }
            System.out.println(list.toString());
            list.add(para.values().toArray());
    		
    		return RestUtils.wrappQueryResult(list);
       } catch (Exception e) {
           e.printStackTrace();
       }
		
//		获取最新数据
//		String getLatestSql = "SELECT " + Util.arrayToString(para.keySet().toArray()) + 
//				", TO_CHAR(" + moniTime + ", 'yyyy-MM-dd HH24:MI:SS') " + moniTime + " FROM " + table + 
//				" WHERE DEVICECODE = ? AND " + moniTime + " = (" + 
//						 "SELECT ZHYCSJSCSJ FROM "+ ConstantsDataBase.SCHEMA +"CMSV_DEVICEUSED_INFO WHERE ZZBM = ? AND JCLX = ?)";
//		System.out.println("bbbbbbbbbbbbbbb"+getLatestSql);
		
		
//		list = hibernateDao_ztjc.executeSqlQuery(getLatestSql, new Object[] { devicecode, devicecode, monitoringtype });
		
		System.out.println("aaaaaaaa 215");
		
		return null;
	}

	@Override
	public QueryResultObject getColumns(RequestCondition params,
			String monitoringtype, String devicecode) {
		Map<String, String> para = getMonitoringPara(monitoringtype, devicecode);
		List <Map<String, String>> columns = new ArrayList<Map<String,String>>();
		System.out.println("aaaaaaaa 271");
		for(String key : para.keySet()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", key); map.put("caption", para.get(key));
			columns.add(map);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", getMonitoringTime(monitoringtype)); map.put("caption", "采集时间");
		columns.add(map);
		return RestUtils.wrappQueryResult(columns);
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

	// 获取监测类型表
	private String getMonitypeTable(String monitoringtype, String devicecode) {
		if(Util.isEmpty(this.aTable)) {
			this.aTable = queryMonitypeTable(monitoringtype, devicecode);
		}
		return this.aTable;
	}

	// 获取监测量
	public Map<String, String> getMonitoringPara(String monitoringtype, String devicecode) {
		if(this.monitoringPara.size() == 0)
			queryMonitoringPara(monitoringtype);
		return this.monitoringPara;
	}

	@Override
	public void clearData() {
		this.aCslsb = null;
		this.aTable = null;
		if(this.monitoringPara == null)
			this.monitoringPara = new HashMap<String, String>();
		else
			this.monitoringPara.clear();
	}
	
	@SuppressWarnings("unchecked")
	private String queryMonitypeTable(String monitoringtype, String devicecode) {
//		获取历史数据表名
		String getTableSql = "SELECT MONITORINGDATATABLE FROM "+ ConstantsDataBase.SCHEMA +"CMST_MONITORINGTYPE WHERE TYPECODE = ?";
		List<String> tableList = hibernateDao_ztjc.executeSqlQuery(getTableSql, new Object[] { monitoringtype });
		String table = tableList.get(0).replaceAll("(?i)mw_app.", ConstantsDataBase.SCHEMA);
		
		return table;
	}

	// 获取监测量
	private void queryMonitoringPara(String monitoringtype) {
		String getParaSql = "SELECT CAST(ISMONITORING AS VARCHAR2(42)) ISMONITORINGPARA, PARACODE, PARANAME FROM "+ ConstantsDataBase.SCHEMA +"CMST_MONITORINGTYPEPARA WHERE MONITORINGTYPE = ?";
		List<Object[]> listPara = hibernateDao_ztjc.executeSqlQuery(getParaSql, new Object[] { monitoringtype });
		for(int i = 0; i < listPara.size(); i++) {
			Object[] para = listPara.get(i);
			if( para[0] != null && para[0].toString().trim().equals("T") ) {
				
				// 如果为监测量 就是要查的列
				this.monitoringPara.put(para[1].toString(), para[2].toString());
			}
		}
	}
	
	private String getMonitoringTime(String monitoringtype) {
		if(monitoringtype.indexOf("01") == 0)
			return "ACQUISITION_TIME";
		else
			return "ACQUISITIONTIME";
	}
	
	private String getServiceUrl(String str) {
			String url = "";
			StringBuffer sb = new StringBuffer();
			if(null == str){
				return "false";
			}
			sb.append("select DEPTIP from mw_sys.cmst_deptidip where DEPTID='");
			sb.append(str);
			sb.append("' ");
			List depturl = hibernateDao_ztjc.executeSqlQuery(sb.toString());
			if(depturl.size()>0){
				url = depturl.get(0).toString();
			}
			return url;

	}

	@Override
	public byte[] queryPDLatestImg(String objId) throws Exception {
		List list = hibernateDao_ztjc.executeSqlQuery("SELECT DISCHARGEWAVEFORM FROM "+ ConstantsDataBase.SCHEMA +"CMST_PARTDISCHARGE WHERE OBJ_ID = '" + objId + "'");
		if (objId == null) {
			throw new Exception(
					"获取放电波形图片出错,obj_id为空");
		}
		byte[] buffer = null;
		if(list.size() > 0) {
			Blob blob = (Blob)list.get(0);
			if(blob != null)
				buffer = blob.getBytes(1, (int)blob.length());
		}
		return buffer;
	}

	@Override
	public Blob queryCEImg(String objId) throws Exception {
		if (Util.isEmpty(objId)) throw new Exception("获取放电波形图片出错,obj_id为空");
		
		List list = hibernateDao_ztjc.executeSqlQuery("SELECT LOADWAVEFORM FROM "+ ConstantsDataBase.SCHEMA +"CMST_CHARGEELECTRICITY WHERE OBJ_ID = '" + objId + "'");
		Blob blob = null;
		if(list.size() > 0) {
			blob = (Blob)list.get(0);
		}
		return blob;
	}

	@Override
	public QueryResultObject queryImage(RequestCondition params, String monitoringtype, String devicecode,String deptws) {
		QueryResultObject resultVal = new QueryResultObject();
		List<String> itemVal = new ArrayList<String>();
		String where = (String) params.getFilter();
		String[] filters = where.split(",");
		String table = getMonitypeTable(monitoringtype, devicecode);
		devicecode = "09M00000000117158";
		String moniTime = getMonitoringTime(monitoringtype);
		if (filters.length == 2) {
			// 自定义时间查看(区间) 格式：开始日期,结束日期
			where = "DEVICECODE = '" + devicecode + 
					"' AND TRUNC("+moniTime+") BETWEEN DATE'" + filters[0] + 
					"' AND DATE'" + filters[1] + "'";
		} else {
			// 选定时间查看(最近一段时间) 格式：3 || 7 || 30
			where = "DEVICECODE = '" +devicecode +"' AND TRUNC("+moniTime+") >= TRUNC(SYSDATE)-" +filters[0];
		}
		String coluname = " TO_CHAR("+moniTime+", 'yyyy-MM-dd HH24:MI:SS') times, CHANNELNO, PRESETPOSITION, CAST(OBJ_ID AS VARCHAR2(42)) OBJ_ID";
		//假数据
		
		List<Object[]> list = new ArrayList<Object[]>();
		//获取网省的接口地址
		String urlVal = getServiceUrl(deptws);
		System.out.println(urlVal);
		if("null".equals(urlVal) || "".equals(urlVal)){
			return resultVal;
		}
		if(!urlVal.contains("?wsdl")){
			urlVal +="?wsdl";
		}
		try{
		System.out.println(urlVal);
		Client client = new Client(new URL(urlVal));
		client.setTimeout(20000);
       	Object[] objects = client.invoke("getZTT", new Object[] {coluname,table,where,moniTime });
       	Document doc = null;
       	String xml = objects[0].toString();
       	System.out.println(xml);
        doc = DocumentHelper.parseText(xml); // 将字符串转为XML
        System.out.println(xml);
        Element rootElt = doc.getRootElement(); // 获取根节点
        Element monitordata = rootElt.element("ResultValue");
        System.out.println(monitordata.asXML());
        if(monitordata.isTextOnly()){
        	itemVal.add(monitordata.getText());
        	resultVal.setItems(itemVal);
			return resultVal;
		}
		Element datanode = monitordata.element("DataTable");
		System.out.println(datanode.asXML());
		Element row = datanode.element("Rows");
		System.out.println(row.asXML());
		Iterator it = row.elementIterator();
        // 遍历迭代器，获取根节点中的信息（书籍）
        while (it.hasNext()) {
            Element jclxdata = (Element) it.next();
            System.out.println(jclxdata.asXML());
            List<Attribute> dataAttrs = jclxdata.attributes();
            for (Attribute attr : dataAttrs) {
                System.out.println("属性名：" + attr.getName() + "--属性值："
                        + attr.getValue());
            }
            Iterator itt = jclxdata.elementIterator();
            List jclxlist = new ArrayList();
            Object[] aaa = new Object[]{};
            while (itt.hasNext()) {
                Element jclxChild = (Element) itt.next();
                System.out.println("节点名：" + jclxChild.getName() + "--节点值：" + jclxChild.getStringValue());
                jclxlist.add(jclxChild.getStringValue());
                aaa = jclxlist.toArray();
            }
            list.add(aaa);
            System.out.println("=====结束=====");
        }
        System.out.println(list.toString());
     // 塞入中文名称
//		Map<String, String> para = getMonitoringPara(monitoringtype, devicecode);
//		for(int i = 0; i < paraName.length; i++) {
//			paraName[i] = para.get(paraName[i]);
//		}
//		list.add(paraName);
		
//		List list = hibernateDao_ztjc
//				.executeSqlQuery("SELECT TO_CHAR(ACQUISITION_TIME, 'yyyy-MM-dd HH24:MI:SS'), CHANNELNO, PRESETPOSITION, CAST(OBJ_ID AS VARCHAR2(42)) " +
//						"FROM "+ table +" WHERE " + where + " ORDER BY ACQUISITION_TIME");
        loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-图像组态图查询", "操作成功");
		return RestUtils.wrappQueryResult(list);
		} catch (Exception e) {
	        e.printStackTrace();
	        loggerSaveBizc.updataUserlog("查询", "状态监测-监测信息查询-图像组态图查询", "操作失败");
	    }
		return null;
		
	}

	@Override
	public QueryResultObject queryImgLatest(RequestCondition params, String monitoringtype, String devicecode) {
		String table = getMonitypeTable(monitoringtype, devicecode);
		table=table.replaceAll("(?i)mw_app.", "UAP_APP.");
		String[] columnsStr = { "时间", "通道", "预置位" };
		String moniTime = getMonitoringTime(monitoringtype);
		String columname ="CHANNELNO, PRESETPOSITION";
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			Client client = new Client(new URL("http://172.16.222.102:8080/BusinessModelNew/services/CMDataService?wsdl"));
			client.setTimeout(20000);
	       	Object[] objects = client.invoke("getLastTimeList", new Object[] {columname,table,devicecode,monitoringtype,moniTime });
	       	Document doc = null;
	       	String xml = objects[0].toString();
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            System.out.println(xml);
            Element rootElt = doc.getRootElement(); // 获取根节点
            Element monitordata = rootElt.element("ResultValue");
            System.out.println(monitordata.asXML());
    		Element datanode = monitordata.element("DataTable");
    		System.out.println(datanode.asXML());
    		Element row = datanode.element("Rows");
    		System.out.println(row.asXML());
    		Iterator it = row.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                Element jclxdata = (Element) it.next();
                System.out.println(jclxdata.asXML());
                List<Attribute> dataAttrs = jclxdata.attributes();
                for (Attribute attr : dataAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值："
                            + attr.getValue());
                }
                Iterator itt = jclxdata.elementIterator();
                List jclxlist = new ArrayList();
                Object[] aaa = new Object[]{};
                while (itt.hasNext()) {
                    Element jclxChild = (Element) itt.next();
                    System.out.println("节点名：" + jclxChild.getName() + "--节点值：" + jclxChild.getStringValue());
                    jclxlist.add(jclxChild.getStringValue());
                    aaa = jclxlist.toArray();
                }
                list.add(aaa);
                System.out.println("=====结束=====");
            }
            System.out.println(list.toString());
            
            list.add(columnsStr);
    		return RestUtils.wrappQueryResult(list);
       } catch (Exception e) {
           e.printStackTrace();
       }
		
//		String sql = "SELECT TO_CHAR(ACQUISITION_TIME, 'yyyy-MM-dd HH24:MI:SS'), CHANNELNO, PRESETPOSITION, CAST(OBJ_ID AS VARCHAR2(42)) " +
//					"FROM " +table +" WHERE DEVICECODE = '" +devicecode +"' AND ACQUISITION_TIME = (" +
//						"SELECT LASTTIME FROM " +ConstantsDataBase.SCHEMA +"CMSV_DEVICEUSED_INFO WHERE DEVICECODE = '" +devicecode +
//						"' AND MONITORINGTYPE = '" +monitoringtype +
//					"') AND ROWNUM = 1";
//		
//		List list = hibernateDao_ztjc.executeSqlQuery(sql);
		return null;
	}

	@Override
	public Blob queryImgOne(String objId, String monitoringtype, String devicecode ,String deptws) throws Exception {
		
		try {
			if(Util.isEmpty(objId)) throw new Exception("获取图像出错, OBJ_ID为空");
			Blob blob = null;
			List<String> itemVal = new ArrayList<String>();
			String moniTime = getMonitoringTime(monitoringtype);
			//获取网省的接口地址
			String urlVal = getServiceUrl(deptws);
			System.out.println("ImgOne"+urlVal);
			if("null".equals(urlVal) || "".equals(urlVal)){
				return null;
			}
			if(!urlVal.contains("?wsdl")){
				urlVal +="?wsdl";
			}
			String table = getMonitypeTable(monitoringtype, devicecode);
			System.out.println("ImgOne"+urlVal);
			Client client = new Client(new URL(urlVal));
			client.setTimeout(20000);
	       	Object[] objects = client.invoke("getZTT", new Object[] {"IMAGEDATA",table," OBJ_ID = '" + objId + "' ",moniTime });
	    	String xml = String.valueOf(objects[0]);
	       	SAXReader reader = new SAXReader();  
			Document doc = reader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
	        Element rootElt = doc.getRootElement(); // 获取根节点
	        Element monitordata = rootElt.element("ResultValue");
	        if(monitordata.isTextOnly()){
				return null;
			}
			Element datanode = monitordata.element("DataTable");
//			System.out.println(datanode.asXML());
			Element row = datanode.element("Rows");
//			System.out.println(row.asXML());
			Iterator it = row.elementIterator();
	        // 遍历迭代器，获取根节点中的信息（书籍）
	        while (it.hasNext()) {
	            Element jclxdata = (Element) it.next();
//	            System.out.println(jclxdata.asXML());
	            Iterator itt = jclxdata.elementIterator();
	            while (itt.hasNext()) {
	                Element jclxChild = (Element) itt.next();
	                System.out.println("节点名：" + jclxChild.getName());
	                if("IMAGEDATA".equals(jclxChild.getName())){
	                	blob = Hibernate.createBlob(jclxChild.getText().getBytes());
	                	return blob;
	                }
	            }
	            System.out.println("=====结束=====");
	        }
//			List list = hibernateDao_ztjc.executeSqlQuery("SELECT IMAGEDATA FROM "+ table +" WHERE OBJ_ID = '" + objId + "'");
//			Blob blob = null;
//			if(list.size() > 0) {
//				blob = (Blob)list.get(0);
//			}
//			return blob;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int newOrOld(RequestCondition params) {
		
		try {
			String monitorType = params.getFilter().toString().split("&")[0].split("=")[1],
					devicecode = params.getFilter().toString().split("&")[1].split("=")[1];
			
			String sql = "SELECT MONITORINGDATATABLE FROM "+ ConstantsDataBase.SCHEMA +"CMST_MONITORINGTYPE WHERE TYPECODE = " + monitorType;
			List list = hibernateDao_ztjc.executeSqlQuery(sql);
			String table = ((String) list.get(0)).replaceAll("(?i)mw_app.", ConstantsDataBase.SCHEMA); // 查出来的表名自带SCHEMA 所以要替换
			sql = "SELECT COUNT(0) FROM " + table + "_N WHERE DEVICECODE = '" + devicecode + "'";
			list = hibernateDao_ztjc.executeSqlQuery(sql);
			return ((BigDecimal) list.get(0)).intValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public JSONArray querysbid(String devicecode)
	{
		
		try {
			JSONArray members = new JSONArray();
			String sbidString = "未找到一次设备ID";
			String sql = "SELECT p.EQUIPMENTOBJ FROM "+ ConstantsDataBase.SCHEMA +"cmst_transfdevice t,"+ ConstantsDataBase.SCHEMA +"cmst_physicsdevice p where t.physicdevice = p.obj_id and t.devicecode = '"+devicecode+"'";
			
			List<Map<String,Object>> sbiddataList = hibernateDao_ztjc.queryForListWithSql(sql);
			
			if(sbiddataList.size()>0)
			{
				if(sbiddataList.get(0).get("EQUIPMENTOBJ")!=null)
				{
					sbidString = String.valueOf(sbiddataList.get(0).get("EQUIPMENTOBJ"));
				}
			}
			
			members.add(0, sbidString);
			
			return members;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public JSONArray querylastobjid(String devicecode)
	{
		System.out.println("aaaaaaaa 432");
		/*try {
			Client client = new Client(new URL("http://172.16.221.15:19001/BusinessModelNew/services/CMDataService?wsdl"));
			client.setTimeout(20000);
	       	Object[] objects = client.invoke("getZTT", new Object[] {coluname,table,where,moniTime });
	       	Document doc = null;
	       	String xml = objects[0].toString();
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            System.out.println(xml);
            Element rootElt = doc.getRootElement(); // 获取根节点
            Element monitordata = rootElt.element("ResultValue");
            System.out.println(monitordata.asXML());
    		Element datanode = monitordata.element("DataTable");
    		System.out.println(datanode.asXML());
    		Element row = datanode.element("Rows");
    		System.out.println(row.asXML());
    		Iterator it = row.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                Element jclxdata = (Element) it.next();
                System.out.println(jclxdata.asXML());
                List<Attribute> dataAttrs = jclxdata.attributes();
                for (Attribute attr : dataAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值："
                            + attr.getValue());
                }
                Iterator itt = jclxdata.elementIterator();
                while (itt.hasNext()) {
                    Element jclxChild = (Element) itt.next();
                    System.out.println("节点名：" + jclxChild.getName() + "--节点值：" + jclxChild.getStringValue());
                    list.add(new Object[]{jclxChild.getStringValue()});
                }
                System.out.println("=====结束=====");
            }
            System.out.println(list.toString());
		*/
		
		try {
			JSONArray members = new JSONArray();
			String sbidString = "未找到最新数据";
			String lasttime = "未找到最新数据时间";
			//判断是否是实时库
				String sql = "SELECT s.OBJ_ID,t.LASTTIME FROM  "+ ConstantsDataBase.SCHEMA +"cmst_transfdevice_lasttime t , "+ ConstantsDataBase.SCHEMA +"cmst_solublegasinoil s where t.devicecode = s.devicecode and t.lasttime = s.acquisitiontime and t.devicecode = '"+devicecode+"'";
				
				List<Map<String,Object>> sbiddataList = hibernateDao_ztjc.queryForListWithSql(sql);
				
				if(sbiddataList.size()>0)
				{
					if(sbiddataList.get(0).get("OBJ_ID")!=null)
					{
						sbidString = String.valueOf(sbiddataList.get(0).get("OBJ_ID"));
						lasttime = String.valueOf(sbiddataList.get(0).get("LASTTIME"));
					}
			}
			members.add(0, sbidString);
			members.add(1, lasttime);
			
			return members;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
