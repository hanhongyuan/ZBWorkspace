package com.sgcc.pms.zbztjc.util.chart.bizc;

import java.sql.Blob;
import java.util.Map;

import net.sf.json.JSONArray;

import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

public interface IChartBizc {

	public byte[] queryPDLatestImg(String objId) throws Exception;

	public Blob queryCEImg(String objId) throws Exception;

	public QueryResultObject queryImage(RequestCondition params, String monitoringtype, String devicecode,String deptws);

	public QueryResultObject queryImgLatest(RequestCondition params, String monitoringtype, String devicecode);

	public Blob queryImgOne(String objId, String monitoringtype, String devicecode ,String deptws) throws Exception;

	public int newOrOld(RequestCondition params);

//	 通用方式获取组态图数据
	public QueryResultObject getChart(RequestCondition params, String monitoringtype, String devicecode,String deptws);

	public QueryResultObject getDeviceName(String monitoringtype, String devicecode);

	public QueryResultObject getLatest(String monitoringtype, String devicecode);
	
	public Map<String, String> getMonitoringPara(String monitoringtype, String devicecode);

	public void clearData();

	public QueryResultObject getColumns(RequestCondition params,
			String monitoringtype, String devicecode);

	public QueryResultObject getHistoryData(RequestCondition params,
			String monitoringtype, String devicecode);
	
	public JSONArray querysbid(String devicecode);
	
	public JSONArray querylastobjid(String devicecode);

	public Map<String, String> getPzInfoByWsID(String wsid);

}
