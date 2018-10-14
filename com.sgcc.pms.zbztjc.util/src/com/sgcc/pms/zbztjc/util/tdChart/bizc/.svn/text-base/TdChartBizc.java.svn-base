package com.sgcc.pms.zbztjc.util.tdChart.bizc;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.client.Client;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sgcc.pms.zbztjc.util.myUtils.ConstantsDataBase;
import com.sgcc.uap.persistence.IHibernateDao;

public class TdChartBizc implements ITdChartBizc{
	
	
	
	private final Log logger = LogFactory.getLog(TdChartBizc.class);
	
	@Resource
	private IHibernateDao hibernateDao_ztjc;


	
	 public void setHibernateDao_ztjc(IHibernateDao hibernateDao_ztjc) {
		this.hibernateDao_ztjc = hibernateDao_ztjc;
	}
	  /**
	    * 电缆数据信息查询
	    * @param params
	    * @return
	    */
	   public List queryDataList(String w_sql,String other_sql) {
		   List<Map<String, Object>> list = new  ArrayList<Map<String, Object>>();
//		   List<Object[]> list = new ArrayList<Object[]>();
		   try {
				Client client = new Client(new URL("http://172.16.222.102:8080/BusinessModelNew/services/CMDataService?wsdl"));
				client.setTimeout(20000);
		       	Object[] objects = client.invoke("getZttCableData", new Object[] {w_sql,other_sql });
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
	                Map<String,Object> datamap = new HashMap<String, Object>();
	                
	                while (itt.hasNext()) {
	                    Element jclxChild = (Element) itt.next();
	                    Object[] aaa = new Object[]{};
	                    System.out.println("节点名：" + jclxChild.getName() + "--节点值：" + jclxChild.getStringValue());
	                    jclxlist.add(jclxChild.getStringValue());
	                    aaa = jclxlist.toArray();
	                    datamap.put(jclxChild.getName(), jclxChild.getStringValue());
	                }
	                list.add(datamap);
	                System.out.println("=====结束=====");
	            }
	            System.out.println(list.toString());
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
		   /*
		   
			sql.append("SELECT E.MONITORINGTYPES,");
			sql.append("   E.PHASE,");
			sql.append("   D.ALARMLEVELCODE,");
			sql.append("   E.LINKEDEQUIPMENT,");
			sql.append("   E.LINKEDEQUIPMENTNAME,");
			sql.append("    E.LINKEDCABLEANDCHANNEL,");
			sql.append("    E.LINKEDCABLEANDCHANNELNAME,");
			sql.append("    E.DEVICECODE,");
			sql.append("    E.LINKEDEQUIPMENTTYPE,");
			sql.append("    E.TYPENAME,");
			sql.append("    E.MONITORINGDATATABLE,");
			sql.append("   '' as MONITORINGDATA");
			sql.append("	  FROM (SELECT V.DEVICECODE,");
			sql.append("           V.MONITORINGTYPE,");
			sql.append("           MIN(DECODE(V.ALARMLEVEL, '报警', 1, '预警', 2)) AS ALARMLEVELCODE");
			sql.append("       FROM "+ ConstantsDataBase.SCHEMA +"cmsv_alarm_cableinfo_standard V");
			sql.append("     where V.ISHANDLED IS NULL");
			sql.append("     GROUP BY V.DEVICECODE, V.MONITORINGTYPE) D ");
			sql.append(" RIGHT JOIN (SELECT T.LINKEDEQUIPMENTNAME,");
			sql.append("                T.LINKEDEQUIPMENTTYPE,");
			sql.append("                 T.PHASE,");
			sql.append("                 T.LINKEDEQUIPMENT,");
			sql.append("                 T.LINKEDCABLEANDCHANNEL,");
			sql.append("                 T.LINKEDCABLEANDCHANNELNAME,");
			sql.append("                 T.DEVICECODE,");
			sql.append("                 T.MONITORINGTYPES,");
			sql.append("                 T.TYPENAME,");
			sql.append("                T.MONITORINGDATATABLE");
			sql.append("           FROM "+ ConstantsDataBase.SCHEMA +"cmsv_cabledevice_logic T");
			sql.append("          WHERE T.MONITORINGDATATABLE IS NOT NULL");
			sql.append("             AND T.MONITORINGSPECIALTY = '00203'");
			sql.append("             AND T.MONITORINGTYPES not like '033%'");
			sql.append("             AND T.DEVICECATEGORY IN ('sensor', 'cac')");
			sql.append("           GROUP BY T.LINKEDEQUIPMENTNAME,");
			sql.append("                   T.LINKEDEQUIPMENTTYPE,");
			sql.append("                   T.LINKEDEQUIPMENT,");
			sql.append("                   T.PHASE,");
			sql.append("                   T.LINKEDCABLEANDCHANNEL,");
			sql.append("                   T.LINKEDCABLEANDCHANNELNAME,");
			sql.append("                   T.DEVICECODE,");
			sql.append("                   T.MONITORINGTYPES,");
			sql.append("                   T.TYPENAME,");
			sql.append("                   T.MONITORINGDATATABLE) E ON D.DEVICECODE =");
			sql.append("	                                           E.DEVICECODE");
			sql.append("	                                        AND D.MONITORINGTYPE =");
			sql.append("	                                            E.MONITORINGTYPES");
			if (StringUtils.isNotEmpty(w_sql)) {
				sql.append(" WHERE ");
				sql.append(w_sql);
			}
			System.out.println(sql);*/
			 try {
//				   List<Map<String, Object>> list = hibernateDao_ztjc.queryForListWithSql(sql.toString());
				   
				   List<Map<String, Object>> dt2 = generateDatatable(list);

				   return dt2;
			   } catch (Exception e) {
				   logger.info("ERROR", e);
				   e.printStackTrace();
			   }
			
			return null;
	   }
	  /**
	   * 拼接历史数据和单位
	   */ 
	   private List<Map<String, Object>> generateDatatable(List<Map<String, Object>> dt) {
			
		   if (dt==null || dt.size()==0) 
				return null;
			String tableName = "";
			String deviceCode = "";
			String moniType = "";
			String allmoniType = (String)dt.get(0).get("MONITORINGTYPES");
			String columnamesql ="select paracode from cmst_monitoringtypepara where monitoringtype = ? ";
			List<String> columnlist = hibernateDao_ztjc.executeSqlQuery(columnamesql, new Object[] {allmoniType});
			Object[] colnames = columnlist.toArray();
			String colname = StringUtils.join(colnames,",");
			StringBuffer sql = new StringBuffer();
			StringBuffer moniDataString = new StringBuffer();
			for (int i=0; i < dt.size(); i++){
				
				tableName = ((String) dt.get(i).get("MONITORINGDATATABLE")).replaceAll("(?i)mw_app.", "UAP_APP.");
				deviceCode = (String)dt.get(i).get("DEVICECODE");
				moniType = (String)dt.get(i).get("MONITORINGTYPES");
				if (moniType.equals("032010") || moniType.equals("032011"))
					continue;
				List<Map<String, Object>> list = new  ArrayList<Map<String, Object>>();
				try {
					Client client = new Client(new URL("http://172.16.222.102:8080/BusinessModelNew/services/CMDataService?wsdl"));
					client.setTimeout(20000);
			       	Object[] objects = client.invoke("getLastTimeList", new Object[] {colname,tableName,deviceCode,moniType,"ACQUISITIONTIME" });
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
		                Map<String,Object> datamap = new HashMap<String, Object>();
		                
		                while (itt.hasNext()) {
		                    Element jclxChild = (Element) itt.next();
		                    Object[] aaa = new Object[]{};
		                    System.out.println("节点名：" + jclxChild.getName() + "--节点值：" + jclxChild.getStringValue());
		                    jclxlist.add(jclxChild.getStringValue());
		                    aaa = jclxlist.toArray();
		                    datamap.put(jclxChild.getName(), jclxChild.getStringValue());
		                }
		                list.add(datamap);
		                System.out.println("=====结束=====");
		            }
		            System.out.println(list.toString());
		       } catch (Exception e) {
		           e.printStackTrace();
		       }
				
//				sql.setLength(0);
//				sql.append("SELECT * FROM ")
//					.append(tableName)
//					.append(" WHERE DEVICECODE = '").append(deviceCode)
//					.append("' AND  ACQUISITIONTIME = (SELECT LASTTIME  FROM ")
//					.append(" "+ ConstantsDataBase.SCHEMA +"cmsv_deviceused_info where DEVICECODE='").append(deviceCode)
//					.append("' and MONITORINGTYPE='"+moniType+"' and rownum =1) ");
//					
//				List<Map<String, Object>> dtMino = hibernateDao_ztjc.queryForListWithSql(sql.toString());
				
				
				
				try {
					sql.setLength(0);
					sql.append("SELECT * FROM "+ ConstantsDataBase.SCHEMA +"CMST_MONITORINGTYPEPARA WHERE MONITORINGTYPE='").append(moniType).append("' AND ISMONITORINGPARA='T' order by paracode");
					List<Map<String, Object>> dtPara = hibernateDao_ztjc.queryForListWithSql(sql.toString());
					moniDataString.setLength(0);
					for (int j=0; j< dtPara.size(); j++){
						Map<String, Object> dr2 = dtPara.get(j);
						if ((dr2.get("PARACODE").toString().equals("ISHANDLED")) //是否处理
							|| (dr2.get("PARACODE").toString().equals("DEVICECODE"))//装置ID
							|| (dr2.get("PARACODE").toString().equals("PHASE"))//相别
							|| (dr2.get("PARACODE").toString().equals("DISCHARGEWAVEFORM"))//波形图
							|| (dr2.get("PARACODE").toString().equals("LINKEDDEVICE"))	//被监测设备
							|| (dr2.get("PARACODE").toString().equals("LINKEDDEVICE"))	//被监测设备
							|| (dr2.get("PARACODE").toString().equals("PDUNIT"))	//放电量单位
							|| (dr2.get("PARACODE").toString().equals("DTSTYPE"))	//DTS光端机型号参数
							|| (dr2.get("PARACODE").toString().equals("SUBMARINECABLEAIS"))
							|| (dr2.get("PARACODE").toString().equals("SUBMARINECABLERADARMAP"))
							|| (dr2.get("PARACODE").toString().equals("BRILLOUINTEMPERATURE"))
							|| (dr2.get("PARACODE").toString().equals("SUBMARINECABLEHYROLOGY"))
							|| (dr2.get("PARACODE").toString().equals("SUBMARINECABLEBRILLOUINSTRAIN"))
						)
						  continue;
						if (list.size()==0 || list.get(0).get((String)dr2.get("PARACODE"))==null){
							moniDataString.append(dr2.get("PARANAME")).append(":  ").append("无数据@@");
						}else{
							moniDataString.append(dr2.get("PARANAME")).append(":  ");
							if(list.get(0).get((String)dr2.get("PARACODE"))==null
									||list.get(0).get((String)dr2.get("PARACODE"))=="null"
									||list.get(0).get((String)dr2.get("PARACODE"))==""){
								moniDataString.append("无数据@@");
							}
							else{
								Object obj = dr2.get("PARACODE");
								if(obj.toString().equalsIgnoreCase("PDTYPE")){//局放类型
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("0")){
										moniDataString.append("内部放电").append(" @@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("1")){
										moniDataString.append("沿面放电").append("@@");
									}else{
										moniDataString.append("电晕放电").append("@@");
									}
								}else if(obj.toString().equalsIgnoreCase("ACQUISITIONTIME")){//监测时间
									moniDataString.append((list.get(0).get((String)dr2.get("PARACODE"))).toString().substring(0, 19)).append("@@");
								}else if(obj.toString().equalsIgnoreCase("PDLEVEL")){//局放状态
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("0")){
										moniDataString.append("正常状态").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("1")){
										moniDataString.append("异常状态").append("@@");
									}else{
										moniDataString.append("故障状态").append("@@");
									}
								}else if(obj.toString().equalsIgnoreCase("EARTHCURRENTSAMPLETYPE")){//护层接地电流采样方式
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("0")){
										moniDataString.append("有效值").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("1")){
										moniDataString.append("瞬时值").append("@@");
									}
								}else if(obj.toString().equalsIgnoreCase("LOADCURRENTLEVEL")){//载流状态
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("0")){
										moniDataString.append("正常状态").append(" @@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("1")){
										moniDataString.append("满载状态").append("@@");
									}else{
										moniDataString.append("过载状态").append("@@");
									}
								}else if(obj.toString().equalsIgnoreCase("SETTINGLOCATION")){//测温类型
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("0")){
										moniDataString.append("表皮").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("1")){
										moniDataString.append("金属护套内").append("@@");
									}else{
										moniDataString.append("缆芯").append(" @@");
									}
								}else if(obj.toString().equalsIgnoreCase("ELECTRONICMANHOLECOVER")){//井盖
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("3")){
										moniDataString.append("非法打开").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("2")){
										moniDataString.append("关").append("@@");
									}else{
										moniDataString.append("开").append(" @@");
									}
								}else if(obj.toString().equalsIgnoreCase("ECFAULTTYPE")){//护层电流故障监测类型
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("0")){
										moniDataString.append("接地错误").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("1")){
										moniDataString.append("接地箱进水").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("2")){
										moniDataString.append("外护套破损").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("3")){
										moniDataString.append("隔板击穿").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("4")){
										moniDataString.append("保护器击穿").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("5")){
										moniDataString.append("接地网脱落").append("@@");
									}else{
										moniDataString.append("--").append("@@");
									}
								}else if(obj.toString().equalsIgnoreCase("DEVICESTATE")//监测装置自诊断
										||obj.toString().equalsIgnoreCase("FANFAULTSTATE")
										||obj.toString().equalsIgnoreCase("WATERPUMPFAULTSTATE")
										||obj.toString().equalsIgnoreCase("FIREWARNINGFAULT")
										||obj.toString().equalsIgnoreCase("ELECTRONICMANHOLECOVERFAULT")
										||obj.toString().equalsIgnoreCase("TUNNELENTRANCEFAULT")
										||obj.toString().equalsIgnoreCase("SUBMARINECABLERADARALERT")
										||obj.toString().equalsIgnoreCase("SUBCABLETHERMOGRAPHYALERT")
										||obj.toString().equalsIgnoreCase("BRILLOUINTEMPERATUREALERT")
										||obj.toString().equalsIgnoreCase("BRILLOUINSTRAINALERT")
										){
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("0")){
										moniDataString.append("正常").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("1")){
										moniDataString.append("异常").append("@@");
									}
								}else if(obj.toString().equalsIgnoreCase("OPTICALFIBERDAMAGESTATE")){//光纤损坏报警
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("0")){
										moniDataString.append("正常").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("1")){
										moniDataString.append("报警").append("@@");
									}
								}else if(obj.toString().equalsIgnoreCase("PDCAPACITY")){//放电量
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append(list.get(0).get("PDUNIT")).append("@@");
								}else if(obj.toString().equalsIgnoreCase("O2")//氧气浓度
										||obj.toString().equalsIgnoreCase("HUMIDITY")//湿度
										){
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().length()!=0)
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append("%@@");
									else
									moniDataString.append("--@@");
								}else if(obj.toString().equalsIgnoreCase("H2S")//硫化氢浓度
										||obj.toString().equalsIgnoreCase("CH4")//甲烷浓度
										||obj.toString().equalsIgnoreCase("CO")//一氧化碳浓度
										||obj.toString().equalsIgnoreCase("CO2")//二氧化碳
										){
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().length()!=0)
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append("ppm@@");
									else
									moniDataString.append("--@@");
								}else if(obj.toString().equalsIgnoreCase("PDLOCATION")//局放距离
										||obj.toString().equalsIgnoreCase("MAXTEMPERATURELOCATION")//电缆最高温度位置
										||obj.toString().equalsIgnoreCase("MINTEMPERATURELOCATION")//电缆最低温度位置
										||obj.toString().equalsIgnoreCase("TEMPERATUREDIFFERENCEDISTANCE")//电缆差温距离报警
										||obj.toString().equalsIgnoreCase("OPTICALFIBERDAMAGELOCATION")//光纤损坏位置
										||obj.toString().equalsIgnoreCase("WATERLEVEL")//水位值
										){
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().length()!=0)
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append("m@@");
									else
									moniDataString.append("--@@");
								}else if(obj.toString().equalsIgnoreCase("MAXCABLETEMPERATURE")//电缆最高温度
										||obj.toString().equalsIgnoreCase("MINCABLETEMPERATURE")//电缆最低温度
										||obj.toString().equalsIgnoreCase("CABLESURFACETEMPERATURE")//电缆表面温度
										||obj.toString().equalsIgnoreCase("TEMPERATUREDIFFERENCE")//电缆差温报警值
										||obj.toString().equalsIgnoreCase("CABLEINNERTEMPERATURE")
										||obj.toString().equalsIgnoreCase("TEMPERATURE")){//温度
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().length()!=0)
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append("℃@@");
									else
										moniDataString.append("--@@");
								}else if(obj.toString().equalsIgnoreCase("LOADCURRENT")//载流实测值
										||obj.toString().equalsIgnoreCase("EARTHCURRENTVALUE")//护层接地电流实测值
										||obj.toString().equalsIgnoreCase("SAFEMAXCURRENT")//安全载流量
										){
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().length()!=0)
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append("A@@");
									else
										moniDataString.append("--@@");
								}else if(obj.toString().equalsIgnoreCase("OILPRESSURE")){//油压值
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().length()!=0)
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append("MPa@@");
									else
										moniDataString.append("--@@");
								}else if(obj.toString().equalsIgnoreCase("TUNNELALTITUDEFALL")){//隧道沉降
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().length()!=0)
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append("mm@@");
									else
										moniDataString.append("--@@");
								}else if(obj.toString().equalsIgnoreCase("SUBMARINECABLEVIBRATION")){//摩擦震动
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().length()!=0)
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append("dB@@");
									else
										moniDataString.append("--@@");
								}else if(obj.toString().equalsIgnoreCase("SUBMARINECABLEANCHORDAMAGE")){//锚害区域
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().length()!=0)
									moniDataString.append(list.get(0).get((String)dr2.get("PARACODE"))).append("@@");
									else
										moniDataString.append("--@@");
								}
								else if(obj.toString().equalsIgnoreCase("LOSSFACTOR")){
									double warn = (Double)list.get(0).get((String)dr2.get("PARACODE"));
									moniDataString.append(warn).append("%@@");
								}
								else{
									if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("0")){
										moniDataString.append("关").append("@@");
									}else if(list.get(0).get((String)dr2.get("PARACODE")).toString().equals("1")){
										moniDataString.append("开").append("@@");
									}
								}
							}
						}
						
					}
					dt.get(i).put("MONITORINGDATA",moniDataString.toString());
					return dt;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			return null;
		}
}
