<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.sgcc.uap.config.util.ApplicationConfigUtil" %>
<%@ page import="com.sgcc.pms.zbztjc.kqxt.spxxgl.bdvideo.bizc.BdvideoBizc" %> 

<%
		String uri = "http://"+ request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath();
		String ip=request.getRemoteAddr();
		
		String p_cabName = ApplicationConfigUtil.getString("ZTJC_CAB_NAME");
		String p_version = ApplicationConfigUtil.getString("ZTJC_CAB_VERSION");
		String p_HTTPAddr = ApplicationConfigUtil.getString("ZTJC_HTTPAddr");
		String p_HTTPPort = ApplicationConfigUtil.getString("ZTJC_HTTPPort");
		String p_serverUrl = ApplicationConfigUtil.getString("ZTJC_LocalServerAddr");
		String p_user = ApplicationConfigUtil.getString("ZTJC_USERNAME");
		String p_pwd = ApplicationConfigUtil.getString("ZTJC_PWD");
%>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  		<title>视频浏览</title>

  		
	</head>
	<link rel="stylesheet"	href="../showvideo/resources/css/imageVideo.css" />
	<script type="text/javascript" src="../showvideo/scripts/cms_videoPlatformControl.js"></script>
	<script type="text/javascript">
	var uri = "<%=uri%>";
	var p_cabName = "<%=p_cabName%>";
	var p_version = "<%=p_version%>";
	var p_HTTPAddr = "<%=p_HTTPAddr%>";
	var p_HTTPPort = "<%=p_HTTPPort%>";
	var p_serverUrl= "<%=p_serverUrl%>";
	var p_pwd = "<%=p_pwd%>";
	var p_user = "<%=p_user%>";
	var ip= "<%=ip%>";
	//alert(p_cabName+"||"+p_version+"||"+p_HTTPAddr+"||"+p_HTTPPort+"||"+p_serverUrl+"||"+p_password+"||"+p_user);
	var _height = 500;
	var _width  = 500;
	var _jczzGlobalId = "";
	var state = false;
	var p_camCode = null;
	var g_objGlobalIdMap = {};
	var  v_winNum = -1;//窗口号
	var videocode = "";
	var name = "";
	
	
	
	
		//窗口大小改变事件
		window.onload=function func(){
			
			 var context = location.search;						//获取URL
				if(context.indexOf("?") != -1){						//判断URL中是否包含参数
					var params = context.substring(1);				//获取参数字符串
					var splitParams = params.split("&");			//根据字符&分割成参数数字
					for(var i=0;i<splitParams.length;i++){
						if (splitParams[i].split("=")[0]=="videocode"){
							videocode = splitParams[i].split("=")[1];
						}else if(splitParams[i].split("=")[0]=="name"){
							name = splitParams[i].split("=")[1];
						}
					}
				}

		document.getElementById("mainvideo").style.width=document.body.clientWidth-200;
		document.getElementById("mainvideo").style.height=document.body.clientHeight-16;
		//document.getElementById("txtMinTime").value = new Date().addYears(-1).toString("yyyy-MM-dd");
		//document.getElementById("txtMaxTime").value = new Date().toString("yyyy-MM-dd");
		
			if(!state){
				_width =  document.body.clientWidth;
				_height  =  document.body.clientHeight;
				if (false){
					document.getElementById("mainvideo").innerHTML = '<table id="zhanshi" height="'+(_height/2)+'" width="'+'100%'+'" border="1" cellpadding="1" cellspacing="2" bgcolor="#000000"><tr><td id="video1"  height="50%" width="50%">&nbsp;</td><td id="video2"  height="50%" width="50%">&nbsp;</td></tr><tr><td id="video3"  height="50%" width="50%">&nbsp;</td><td id="video4" ;" height="50%" width="50%">&nbsp;</td></tr></table>';
				}else{ 
					document.getElementById("mainvideo").innerHTML ="<object id='playerV'  classid='CLSID:D71AE918-F986-413A-9BA7-2644172CA228' width='100%"
					+"' height='100%' codebase='assets/OcxPlayer/GridControl.cab#version="+p_version+"'></object>";
					if(document.getElementById("playerV").object==null){
						//document.location.reload();
						alert("请注册控件后重新刷新本页面");
					}else{
						var a =ocxInit();
						loginServer_dialog();
						recall();
						state = true; 
					}
				}
			}else{
				_height =  document.body.offsetHeight;
				_width  =  document.body.offsetWidth;
				
				if(_height>0 && _width>0){
					playerV.width = _width;
					playerV.height = _height;
				}
				ocxSetWindowSize(0,_height,_width);
			}
			setTimeout(function(){
					getDBVideoData(null,null);
			}, 1000);
}
		//根据选择时间段查询历史视频
		/* function execute_query(){
			$("selectVideo").innerHTML="";
			var txtMinTime = $("txtMinTime").value;
			var txtMaxTime = $("txtMaxTime").value;
			
			if(txtMinTime>txtMaxTime){
				alert("请选择正确的时间段");
				return;
			}
			
			var v_pStartTime = txtMinTime + "T00:00:00Z";
			var v_pEndTime = txtMaxTime + "T23:59:59Z";
			
			if(document.getElementById("playerV").object==null){
				document.location.reload();
				alert("请注册控件后重新刷新本页面");
			}else{
				var xml = findHistoryVideo(videocode,v_pStartTime,v_pEndTime);

			}
			getXMLInfo(xml);
		}
		
		function execute_query2(b_Time){
			var txtMaxTime = $("txtMaxTime").value;
			
			var v_pStartTime = b_Time;
			var v_pEndTime = txtMaxTime + "T23:59:59Z";

			var xml = findHistoryVideo(videocode,v_pStartTime,v_pEndTime);

			return xml;
		}
		
		function getXMLInfo2(_xml,sv){
			
	        	 var _xmlDoc = null;
	        	 _xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	 			 _xmlDoc.async = "false";
	 			 _xmlDoc.loadXML(_xml);
	 			 var _Item = _xmlDoc.getElementsByTagName("Item");
	 			 var RealNum = _xmlDoc.getElementsByTagName("SubList")[0].getAttribute("RealNum");
				 var SubNum = _xmlDoc.getElementsByTagName("SubList")[0].getAttribute("SubNum");
	 			 for (var j = 0; j < _Item.length; j++){
	 				 var bTime = _Item[j].getAttribute("BeginTime");
	 				 var b_Time = _Item[j].getAttribute("EndTime");
					 var _bTime = bTime.replace("T"," ");
					 var _time = _bTime.replace("Z","");
					 var _op = document.createElement("option");
			         _op.text = _time;
			         _op.value = _Item[j].getAttribute("FileUrl");
			         _op.className = _Item[j].getAttribute("DecoderTag");
			         sv.add(_op);
			         if(j==99 && (RealNum>SubNum)){
			        	 var _xml = execute_query2(b_Time);
			        	 getXMLInfo2(_xml,sv);
	 			 }
	         }
			
		}
		
		//解析查询后返回的报文
		function getXMLInfo(xml){
			if(xml==null||xml==""){
				alert("传入的xml为空");
				return;
			}
			try {
			var sv = document.getElementById("selectVideo");
			//xml解析
			var xmlDoc = null;
			xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.async = "false";
			xmlDoc.loadXML(xml);
			var Item = xmlDoc.getElementsByTagName("Item");
			var b_Time;
			var RealNum = xmlDoc.getElementsByTagName("SubList")[0].getAttribute("RealNum");//实际数据条数
			var SubNum = xmlDoc.getElementsByTagName("SubList")[0].getAttribute("SubNum");//本次返回条数
			if(Item.length==0){
				alert("该时间段内无历史视频");
				return;
			}else if(Item.length==100 && (RealNum>SubNum)){//返回数据条数最多为100，超出100的需要再次查询，以此类推
				 for (var i = 0; i < Item.length; i++) {
					 var bTime = Item[i].getAttribute("BeginTime");
					 var b_Time = Item[i].getAttribute("EndTime");
					 var _bTime = bTime.replace("T"," ");
					 var _time = _bTime.replace("Z","");
					 var op = document.createElement("option");
			         op.text = _time;
			         op.value = Item[i].getAttribute("FileUrl");
			         op.className = Item[i].getAttribute("DecoderTag");
			         sv.add(op);
			         if(i==99){//取第100条数据的结束时间为第二次查询的开始时间
			        	 var _xml = execute_query2(b_Time);
			        	 getXMLInfo2(_xml,sv);
			         }
				}
			}else{
				for (var i = 0; i < Item.length; i++) {
					 var bTime = Item[i].getAttribute("BeginTime");
					 var b_Time = Item[i].getAttribute("EndTime");
					 var _bTime = bTime.replace("T"," ");
					 var _time = _bTime.replace("Z","");
					 var op = document.createElement("option");
			         op.text = _time;
			         op.value = Item[i].getAttribute("FileUrl");
			         op.className = Item[i].getAttribute("DecoderTag");
			         sv.add(op);
				
			}
			
			}
			}catch (e) {
				alert("解析xml失败");
				return;
			}
			
		}
		
		//根据视频地址显示历史视频
		function showHisVideo(){
			var sv = document.getElementById("selectVideo");
			var v_pFileurl = sv.options[sv.selectedIndex].value;
			var v_sDecoderTag = sv.options[sv.selectedIndex].className;
			var v_pNotes = sv.options[sv.selectedIndex].innerHTML;
			if(document.getElementById("playerV").object==null){
				document.location.reload();
				alert("请注册控件后重新刷新本页面");
			}else{
				showHistoryVideo(v_pFileurl,v_sDecoderTag,v_pNotes);
			}
		}
		 */
		function refresh(){
			getDBVideoData(null,null);
		} 
		
function leftright() {
		var r=ocxSetWindowSize(1, 300, 200 + 242);


	}
function getDBVideoData(jczzGlobalId,p_type){
		initPara2(150,videocode,name);

}
//时间判断，起始时间不能大于终止时间
function data_judge(sender)
{
	var qs_data=txtMinTime.value;
	var zz_data=txtMaxTime.value;
	qs_data=qs_data.replace(/-/g,"/");
	zz_data=zz_data.replace(/-/g,"/");
	if(qs_data!=""&&zz_data!="")
	{
		if(Date.parse(qs_data)>Date.parse(zz_data))
		{
			alert("起始时间不能大于终止时间！");
			if(sender.id == "txtMinTime"){
				sender.value =txtMaxTime.value;
			}
			if(sender.id == "txtMaxTime"){
				sender.value =txtMinTime.value;
			}
		}
		
	}	
}

</script>
	<body  onbeforeunload="loginOut();"  >
	<table id="zhanshi" class="newmaintable" align="center" cellpadding="0" style="margin-top: 0px;padding-top: 0px" cellspacing="0" >
   		 <tr>
   		 <td id="mainvideo" class="mainvideo" style="background-color: #404040;"></td>
				<td id="onsee" class="onsee" style="background-color: #c8d0d4;width:200;padding: 0px;margin:0px">
					<div id="upsee" class="upsee" style="height:400;" align="center">
						<table class="imglist">
							<tr>
								<td colspan="8">
									<br />
								</td>
							</tr>
							<tr>
								
								<td rowspan="3" colspan="8" align="center">
									<img hidefocus="true"
										src="../showvideo/resources/images/bg_RoundHolder[1].png"
										alt="" width="112" height="112" border="0" usemap="#Map" />
									<map name="Map" id="Map">
										<area shape="circle" coords="57,21,13" href="#" alt="向上旋转"
											onmousedown="PTZControl(v_winNum,1);return false;"
											onmouseup="PTZControl(v_winNum,2);return false;" />
										<area shape="circle" coords="19,55,13" href="#" alt="向左旋转"
											onmousedown="PTZControl(v_winNum,5);return false;"
											onmouseup="PTZControl(v_winNum,6);return false;" />
										<area shape="circle" coords="56,94,13" href="#" alt="向下旋转"
											onmousedown="PTZControl(v_winNum,3);return false;"
											onmouseup="PTZControl(v_winNum,4);return false;" />
										<area shape="circle" coords="99,55,13" href="#" alt="向右旋转"
											onmousedown="PTZControl(v_winNum,7);return false;"
											onmouseup="PTZControl(v_winNum,8);return false;" />
									</map>
								</td>
							</tr>
							<tr><td colspan="8">
									<br />
								</td></tr>
							<tr><td colspan="8">
									<br />
								</td></tr>
							<tr><td colspan="8">
									<br />
								</td></tr>
							<tr><td colspan="8">
									<br />
								</td></tr>
							<tr>
								<td  colspan="4"  align="center">
									<img class="control" alt="拉近"
										src="../showvideo/resources/images/button_plus[1].png"
										width="32" height="30"
										onMouseDown="PTZControl(v_winNum,9);return false;"
										onMouseUp="PTZControl(v_winNum,10);return false;" />
								</td>
								<td colspan="4"  align="center">
									<img class="control" alt="拉远"
										src="../showvideo/resources/images/button_decrease[1].png"
										width="32" height="29"
										onMouseDown="PTZControl(v_winNum,11);return false;"
										onMouseUp="PTZControl(v_winNum,12);return false;" />
								</td>
							

							</tr>
							<tr>
								<td>
									
								</td>
								<td colspan="7">
									&nbsp;
								</td>
							</tr>
						</table>
						<br />
						<hr style="color: #B4DACC;" />
						<span>云台速度：</span><br />
						<select id="selectSpeed" name="select" class="allinputselect">
							<option value="1">
								1(最慢)
							</option>
							<option value="2">
								2
							</option>
							<option value="3">
								3
							</option>
							<option value="4">
								4
							</option>
							<option value="5" selected="selected">
								5(中速)
							</option>
							<option value="6">
								6
							</option>
							<option value="7">
								7
							</option>
							<option value="8">
								8
							</option>
							<option value="9">
								9(最快)
							</option>
						</select>
						<br />
						<hr style="color: #B4DACC;" />
					
						</div>
						

		<!--  <td id="videolist" class="videolist" style="background-color: #c8d0d4;" align="center">
			<div id="selectlist" class="selectlist" style="margin: 10px;">
				<span>选择历史视频日期：</span>
					<br />
				<input type="text" class=input  id="txtMinTime" onfocus="calenderShow(this);" onpropertychange="data_judge(this)" readonly style="width: 80;height:19px" /><br />
						&nbsp;至&nbsp;<br />
				<input type="text" class=input  id="txtMaxTime" onfocus="calenderShow(this);" onpropertychange="data_judge(this)" readonly style="width: 80;height:19px" /><br /><br />
				<button id="queryBtn" imageUrl="../showvideo/resources/images/magGlass.gif" onClick="execute_query()">查询历史</button>&nbsp;&nbsp;
				<button id="freshBtn" imageUrl="../showvideo/resources/images/magGlass.gif" onClick="refresh()">查看实时</button>
				<br />
				<span>历史视频列表：</span>
				<br />
				<select id="selectVideo" onchange="showHisVideo()" multiple="multiple" style="width: 85%;height: 60%">

				</select>
			</div>
		</td>-->
  		 </tr>
   </table>
	</body>
	
</html>