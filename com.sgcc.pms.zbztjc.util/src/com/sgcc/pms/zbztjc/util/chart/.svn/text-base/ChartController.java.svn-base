package com.sgcc.pms.zbztjc.util.chart;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sgcc.pms.zbztjc.util.chart.bizc.IChartBizc;
import com.sgcc.pms.zbztjc.util.myUtils.ImgCompress;
import com.sgcc.pms.zbztjc.util.myUtils.Util;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.WrappedResult;

@Controller
@RequestMapping("/chart")
// 根据po类名生成
public class ChartController {

	@Resource
	private IChartBizc chartBizc;
	
	private ImgCompress imgCompress = new ImgCompress();
	
	private String monitoringtype, devicecode ,deptws;
	
	@RequestMapping("/getDeviceName")
	public @ItemResponseBody
	QueryResultObject getDeviceName(@QueryRequestParam("params") RequestCondition params) {
		initData(params);
		
		QueryResultObject queryResult = chartBizc.getDeviceName(monitoringtype, devicecode);
		return queryResult;
	}
	
	private void initData(RequestCondition params) {
		
		String[] filters = params.getFilter().toString().split(",");
		this.monitoringtype = filters[0];
		this.devicecode = filters[1];
		this.deptws = filters[2];
		// 加一个清空表名、监测量、参数隶属表的方法 不知是否会存在同时两个页面的情况
		chartBizc.clearData();
	}

	@RequestMapping("/getMonitoringPara")
	public @ItemResponseBody
	QueryResultObject getMonitoringPara() {
		QueryResultObject queryResult = new QueryResultObject();
		List<Map> list = new ArrayList<Map>();
		
		Map<String, String> map = chartBizc.getMonitoringPara(monitoringtype, devicecode);
		list.add(map);
		queryResult.setItems(list);
		return queryResult;
	}
	//获取wsPmsIP
	@RequestMapping("/getPzInfoByWsID")
	public @ResponseBody WrappedResult getPzInfoByWsID(String wsId) {
		return WrappedResult.successWrapedResult(chartBizc.getPzInfoByWsID(wsId));
	}
	
//	通用方式获取组态图数据(16)
	@RequestMapping("/getChart")
	public @ItemResponseBody
	QueryResultObject getChart(
			@QueryRequestParam("params") RequestCondition params) {
//		// 去除空格
//		String columnsStr = params.getColumns().replaceAll(" ", "");

		QueryResultObject queryResult = chartBizc.getChart(params, monitoringtype, devicecode,deptws);

		return queryResult;
	}
	
	@RequestMapping("/getColumns")
	public @ItemResponseBody
	QueryResultObject getColumns(
			@QueryRequestParam("params") RequestCondition params) {

		QueryResultObject queryResult = chartBizc.getColumns(params, monitoringtype, devicecode);

		return queryResult;
	}
	
	@RequestMapping("/getHistoryData")
	public @ItemResponseBody
	QueryResultObject getHistoryData(
			@QueryRequestParam("params") RequestCondition params) {

		QueryResultObject queryResult = chartBizc.getHistoryData(params, monitoringtype, devicecode);

		return queryResult;
	}

	@RequestMapping("/getLatest")
	public @ItemResponseBody
	QueryResultObject getLatest(@QueryRequestParam("params") RequestCondition params) {
		
//		QueryResultObject queryResult = chartBizc.getLatest(monitoringtype, devicecode);
//		return queryResult;
		QueryResultObject aaa = new QueryResultObject();
		return aaa;
	}
	
	@RequestMapping("/newOrOld")
	public @ItemResponseBody
	int newOrOld(@QueryRequestParam("params") RequestCondition params) {

		int number = chartBizc.newOrOld(params);
		return number != 0 ? 1 : number;
	}

	@RequestMapping("/queryPDLatestImg")
	public void queryPDLatestImg(HttpServletRequest request,HttpServletResponse response) {
		String objId = request.getParameter("objId");
		byte[] buffer;
		InputStream inputStream =null;
		BufferedInputStream bis=null;
		OutputStream out=null;
		try {
			buffer = chartBizc.queryPDLatestImg(objId);
			if(buffer != null) {
				out = response.getOutputStream();
				out.write(buffer);
				out.flush();
			} else {
				String path = "http://"+request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath();
				URL url  = new URL(path.replace("\\", "/")+"/partDischarge/resource/noWave.jpg");
				URLConnection conn = url.openConnection();
				
				inputStream  = conn.getInputStream();
				bis = new BufferedInputStream(inputStream);
				BufferedImage originalImage = ImageIO.read(bis);
				out = response.getOutputStream();
				ImageIO.write(originalImage, "JPG", out);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			Util.free(inputStream, out, bis);
		}
	}

	@RequestMapping("/queryCEImg")
	public void queryCEImg(HttpServletRequest request,HttpServletResponse response) throws IOException, Exception {
		String objId = request.getParameter("objId");
		String strW = request.getParameter("w"), strH = request.getParameter("h");
		Blob blob =null;
		InputStream inputStream =null;
		OutputStream out = null;
		try {
			blob = chartBizc.queryCEImg(objId);
			out = response.getOutputStream();
			if(blob != null) {
				inputStream = blob.getBinaryStream();
				if(Util.isEmpty(strW) || Util.isEmpty(strH)){
					Util.writeImg(inputStream, out);
				}else{
					Util.resizeTo(inputStream, Integer.valueOf(strW), Integer.valueOf(strH), out);
				}
			} else {
				String path = "http://"+request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath();
				URL url  = new URL(path.replace("\\", "/")+"/ztjcmx/resource/noWave.jpg");
				URLConnection conn = url.openConnection();
				inputStream  = conn.getInputStream();
				if(Util.isEmpty(strW) || Util.isEmpty(strH))
					Util.writeImg(inputStream, out);
				else
					Util.resizeTo(inputStream, Integer.valueOf(strW), Integer.valueOf(strH), out);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Util.free(inputStream,out,null);
		}
	}
	
	@RequestMapping("/getImage")
	public @ItemResponseBody
	QueryResultObject getImage(
			@QueryRequestParam("params") RequestCondition params) {
		String[] columnsStr = { "监测时间", "通道", "预置位" };

		QueryResultObject queryResult = chartBizc.queryImage(params, monitoringtype, devicecode,deptws);
		List result = queryResult.getItems();
		result.add(columnsStr);

		return queryResult;
	}

	@RequestMapping("/getImgLatest")
	public @ItemResponseBody
	QueryResultObject getImgLatest(@QueryRequestParam("params") RequestCondition params) {
		String[] columnsStr = { "时间", "通道", "预置位" };
		
		QueryResultObject queryResult = chartBizc.queryImgLatest(params, monitoringtype, devicecode);
		List result = queryResult.getItems();
		result.add(columnsStr);

		return queryResult;
	}

	@RequestMapping("/getImgOne")
	public void getImgOne(HttpServletRequest request,HttpServletResponse response) {
		String objId = request.getParameter("objId"),
				strW = request.getParameter("w"), strH = request.getParameter("h");
		OutputStream out = null;
		InputStream inputStream =null;
		Blob blob = null;
		try {
			out = response.getOutputStream();
			blob = chartBizc.queryImgOne(objId, monitoringtype, devicecode,deptws);
			if(blob != null || blob.length()!=0) {
//				byte[] aaaa = blob.getBytes(0,(int)blob.length());
				inputStream = blob.getBinaryStream();
				if (inputStream.available() != 0) {
					System.out.println("不为空");
				}
				 
				 byte[] buf = new byte[(int)blob.length()];
				
				int aa = inputStream.read(buf);
				while(aa != -1){
					System.out.println("...");
				}
				BufferedImage bufimg= ImageIO.read(new ByteArrayInputStream(buf));
				System.out.println(bufimg.getHeight()+"^^^^^^^^^^^^^^^^^^^"+bufimg.getWidth());
				System.out.println(strW+"----------------"+strH);
				/*if(Util.isEmpty(strW) || Util.isEmpty(strH)){
					String names[] = ImageIO.getReaderFormatNames();
					System.out.println("245----"+names.toString());
					if(ImageIO.read(inputStream) == null){
						System.out.println("图片输入流为空");
					}
					imgCompress.writeImg(inputStream, out);
				}else{
					if(ImageIO.read(inputStream) == null){
						System.out.println("有宽高的情况下图片为空");
					}
					imgCompress.resizeTo(inputStream, Integer.valueOf(strW), Integer.valueOf(strH), out);
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Util.free(inputStream, out, null);
		}
	}

	/**
	 * 查询一次设备ID
	 * */
	@RequestMapping("/querysbid")
    public @RawResponseBody JSONArray querysbid(@QueryRequestParam("params") RequestCondition params) {
    	
    	String devicecode = params.getFilter().toString();
		return chartBizc.querysbid(devicecode);
	}
	
	/**
	 * 查询一次设备ID
	 * */
	@RequestMapping("/querylastobjid")
    public @RawResponseBody JSONArray querylastobjid(@QueryRequestParam("params") RequestCondition params) {
		
    	String devicecode = params.getFilter().toString();
		return chartBizc.querylastobjid(devicecode);
	}
	/**
	 * 注入逻辑构件
	 * 
	 * @param chartBizc
	 */
	public void setChartBizc(IChartBizc chartBizc) {
		this.chartBizc = chartBizc;
	}
}
