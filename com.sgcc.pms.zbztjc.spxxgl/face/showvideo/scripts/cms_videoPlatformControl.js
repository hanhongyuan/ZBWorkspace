/**
 * 统一平台视频操作方法
 */
/**
 * 控件初始化 对计算机资源空间的申请
 */
function ocxInit() {
	//debugger;
	var iplist=playerV.ocxGetLocalIPList();
	var iplists=iplist.split(";");
	var localIP="";
	for(var i=0;i<iplists.length;i++){
		if(iplists[i].indexOf(".")>0){
			localIP=iplists[i];
			break;
		}
	 }
	return playerV.ocxInitLocalAddr(localIP,0,localIP,0);
	//return playerV.ocxInit();
}
/**
 * 判断控件是否加载成功
 * 
 * @returns 错误代码
 */
function ocxOcxIsInit() {
	return playerV.ocxOcxIsInit();
}
/**
 * 用户登录
 * 
 * @param v_pServerAddr
 * @param v_sServerPort
 * @param v_pUserName
 * @param v_pPassword
 * @param v_sEncType
 *            登录密码加密方式（0：明文方式；1：MD5加密方式）
 * @param v_sSystemType
 * @returns
 */
function ocxLogin(v_pServerAddr, v_sServerPort, v_pUserName, v_pPassword,
		v_sEncType, v_sSystemType) {
	return playerV.ocxLogin(v_pServerAddr, v_sServerPort, v_pUserName,
			v_pPassword, v_sEncType, v_sSystemType);
}
function test111(){
	 
}
/**
 * 判定是否成功登录
 */
function ocxIsLogin() {
	return playerV.ocxIsLogin();
}
/**
 * 创建控件窗口
 * 
 * @param v_sMaxWinNums
 *            最大显示子窗口数量
 * @param v_sDefaultWinNums
 *            默认显示子窗口数量
 * @param v_sShowWindow
 *            1创建时显示该主窗口 0 创建时隐藏该主窗口；
 * 
 * @returns
 */
function ocxCreateWindow(v_sMaxWinNums, v_sDefaultWinNums, v_sShowWindow) {
	return playerV.ocxCreateWindow(v_sMaxWinNums, v_sDefaultWinNums,
			v_sShowWindow);
}
/**
 * 调阅实时视频画面
 * 
 * @param v_sMainWndIndex
 *            主窗口索引
 * @param v_sSubWndNo
 *            子窗口编号
 * @param v_sDecoderTag
 *            设备摄像机解码标签
 * @param v_pCode
 *            设备摄像机编码
 * @param v_pNotes
 *            设备摄像机备注信息
 * @returns
 */
function ocxVideoStart(v_sMainWndIndex, v_sSubWndNo, v_sDecoderTag, v_pCode,
		v_pNotes) {
		
		//alert("79playerV"+playerV);
	return playerV.ocxVideoStart(v_sMainWndIndex, v_sSubWndNo, v_sDecoderTag,
			v_pCode, v_pNotes);
}
/**
 * 停止视频画面显示
 * 
 * @param _sMainWndIndex
 *            主窗口索引（ocxCreateWindow的返回值)
 * @param v_sSubWndNo
 *            子窗口编号 -1为当前选中画面
 * @returns
 */
function ocxVideoStop(_sMainWndIndex, v_sSubWndNo) {
	return playerV.ocxVideoStop(_sMainWndIndex, v_sSubWndNo);
}
/**
 * 停止所有视频画面显示
 * 
 * @param _sMainWndIndex
 *            主窗口索引
 * @returns
 */
function ocxVideoStopAll(_sMainWndIndex){
	//alert("103playerV"+playerV);
	return playerV.ocxVideoStopAll(_sMainWndIndex);
}
/**
 * 设置控件大小
 * 
 * @param v_sMainWndIndex
 *            主窗口索引
 * @param v_sWidth
 * @param v_sHeight
 * @returns
 */
function ocxSetWindowSize(v_sMainWndIndex, v_sWidth, v_sHeight) {
	return playerV.ocxSetWindowSize(v_sMainWndIndex, v_sWidth, v_sHeight);
}
/**
 * 视频多画面显示数量设置
 * 
 * @param v_sMainWndIndex
 *            主窗口索引
 * @param v_sWndNums
 * @returns
 */
function ocxSetWndNums(v_sMainWndIndex, v_sWndNums) {
	return playerV.ocxSetWndNums(v_sMainWndIndex, v_sWndNums);
}
/**
 * 获取当前聚焦的窗口编号
 * 
 * @param v_sMainWndIndex
 *            主窗口索引
 * @returns
 */
function ocxGetActiveWndNo(v_sMainWndIndex) {
	return playerV.ocxGetActiveWndNo(v_sMainWndIndex);
}
/**
 * 云镜控制
 * 
 * @param v_sMainWndIndex
 *            主窗口索引
 * @param v_sSubWndNo
 *            子窗口编号 -1当前选中画面
 * @param v_sPTZType
 *            类型
 * @param v_sPTZSpeed
 *            速度
 * @returns
 */
function ocxPTZControl(v_sMainWndIndex, v_sSubWndNo, v_sPTZType, v_sPTZSpeed) {
	return playerV.ocxPTZControl(v_sMainWndIndex, v_sSubWndNo, v_sPTZType,
			v_sPTZSpeed);
}
/**
 * 预置位设置
 * 
 * @param v_sMainWndIndex
 *            主窗口索引
 * @param v_pCode
 *            前端设备编码
 * @param v_sPresetNo
 *            预置位编号
 * @returns
 */
function ocxPresetSet(v_sMainWndIndex, v_pCode, v_sPresetNo) {
	return playerV.ocxPresetSet(v_sMainWndIndex, v_pCode, v_sPresetNo);
}
/**
 * 预置位调用
 * 
 * @param v_sMainWndIndex
 *            主窗口索引
 * @param v_pCode
 *            前端设备编码
 * @param v_sPresetNo
 *            预置位编号
 * @returns
 */
function ocxPresetGet(v_sMainWndIndex, v_pCode, v_sPresetNo) {
	return playerV.ocxPresetGet(v_sMainWndIndex, v_pCode, v_sPresetNo);
}
/**
 * 预置位删除
 * 
 * @param v_sMainWndIndex
 *            主窗口索引
 * @param v_pCode
 *            前端设备编码
 * @param v_sPresetNo
 *            预置位编号
 * @returns
 */
function ocxPresetDelete(v_sMainWndIndex, v_pCode, v_sPresetID) {
	return playerV.ocxPresetDelete(v_sMainWndIndex, v_pCode, v_sPresetID);
}
/**
 * 界面设置颜色
 * 
 * @param v_sType
 *            0：设置窗口未打开视频时的背景 1：设置窗口未选中时边框 2：设备窗口选中时的边框
 * 
 * @param v_uRGB
 *            颜色值
 * @param v_sReserver1
 *            保留值，填-1
 * @param v_sReserver2
 *            保留值，填-1
 * @returns
 */
function ocxSetWndColor(v_sType, v_uRGB, v_sReserver1, v_sReserver2) {
	return playerV.ocxSetWndColor(v_sType, v_uRGB, v_sReserver1, v_sReserver2);
}
/**
 * 销毁控件主窗口
 * 
 * @param v_sMainWndIndex
 *            主窗口索引（ocxCreateWindow的返回值）
 * @returns
 */
function ocxDestroyWindow(v_sMainWndIndex){
	return playerV.ocxDestroyWindow( v_sMainWndIndex);
}
/**
 * 用户注销
 * 
 * @returns
 */
function ocxLogout(){
	return playerV.ocxLogout();
}
function ocxUnInit(){
	return playerV.ocxUnInit();
}

/**
 * 
 * @param v_sRecordType
 * 			录像类型 0：所有类型 1: 报警录像 2：定时录像 3：手动录像
 * @param v_pCode
 * 			前端系统或设备编码
 * @param v_pStartTime
 * 			开始时间 （时间格式：xxxxxxTxxxxxxZ，如20101209T091054Z）
 * @param v_pEndTime
 * 			结束时间 （时间格式：xxxxxxTxxxxxxZ，如20101209T091054Z）
 * @returns
 */
function ocxRemoteFileQuery(v_sRecordType,v_pCode,v_pStartTime, v_pEndTime){
	return playerV.ocxRemoteFileQuery(v_sRecordType,v_pCode,v_pStartTime, v_pEndTime);
	debugger;
}

/**
 * 开始历史视频显示
 * @param v_sMainWndIndex
 * 			主窗口索引
 * @param v_sSubWndNo
 * 			子窗口编号
 * @param v_pFileurl
 * 			录像文件URL
 * @param v_sDecoderTag
 * 			解码标签
 * @param v_pNotes
 * 			备注信息
 * @returns
 */
function ocxRemoteFilePlayStart(v_sMainWndIndex,v_sSubWndNo,v_pFileurl,v_sDecoderTag,v_pNotes){
	return playerV.ocxRemoteFilePlayStart(v_sMainWndIndex,v_sSubWndNo,v_pFileurl,v_sDecoderTag,v_pNotes);
}

var errorMap = {
	"0" : "无错误",
	"1" : "参数输入错误",
	"2" : "连接服务器失败",
	"3" : "控件未加载成功",
	"4" : "视频未打开",
	"5" : "音频未打开",
	"6" : "内存空间申请失败",
	"7" : "SIP端口打开失败",
	"8" : "HTTP端口打开失败",
	"9" : "解码函数调用失败",
	"10" : "请求节点不存在",
	"11" : "指定设备不在线",
	"12" : "权限不足",
	"13" : "平台内部错误",
	"162" : "解码器未找到",
	"163" : "解码器打开失败",
	"166" : "超时",
	"167" : "视频已存在",
	"168" : "拒绝访问",
	"169" : "访问对象不存在",
	"170" : "服务器内部错误",
	"171" : "负荷满",
	"172" : "流媒体不存在",
	"173" : "流媒体返回的错误",
	"174" : "设备不在线",
	"175" : "设备返回的错误",
	"176" : "权限低，资源已被用户占用",
	"177" : "会话不存在",
	"180" : "密码错误",
	"255" : "未定义错误"

};
// /////////////////////////////////视频业务方法/////////////////////////////////////////
// 初始化，登录视频服务器
function loginServer() {
	if (isNaN(p_HTTPPort)|| p_HTTPPort == "") {
		alert("服务器参数配置不正确！");
	} else {
		//alert("p_HTTPAddr:"+p_HTTPAddr);
		//alert("p_HTTPPort:"+p_HTTPPort);
		//alert("user:"+p_user);
		//alert("password:"+p_pwd);
		 var returnValue = ocxLogin(p_HTTPAddr,parseInt(p_HTTPPort),p_user,p_pwd,0,10);
		//alert("returnValue"+returnValue);
		 var e = ocxCreateWindow(4,4,1);
	}
}
//公司总部输变电弹出框视频登陆
function loginServer_dialog() {
	// var userID = mw.Portal.currentUser.id;// 当前用户的ID
	if (isNaN(p_HTTPPort)|| p_HTTPPort == "") {
		alert("服务器参数配置不正确！");
	} else {
		alert("p_HTTPAddr:"+p_HTTPAddr+"|| p_HTTPPort:"+p_HTTPPort+"|| user:"+p_user+"|| password:"+p_pwd);
		//alert("p_HTTPPort:"+p_HTTPPort);
		//alert("user:"+p_user);
		//alert("password:"+p_pwd);
		 var returnValue = ocxLogin(p_HTTPAddr,parseInt(p_HTTPPort),p_user,p_pwd,1,10);
		 //debugger;
		alert("登陆："+returnValue);
		 var e = ocxCreateWindow(1,1,1);
		 //alert("主窗口编号："+e);
	}
}
// 调阅视频
function startVideo(p_DecodeTag,p_cameraCode){	
	if(p_DecodeTag=="" || p_cameraCode==""||p_DecodeTag==null|| p_cameraCode==null){
		alert("请配置台帐扩展参数！")
	}else if(isNaN(p_DecodeTag)){
		alert("参数配置不正确！");
	}else{
		if (document.getElementById("playerV")!= null ){
			 
			var e=ocxVideoStart(20000,v_winNum,parseInt(p_DecodeTag),p_cameraCode,10);
		}
	}
	
}
// 调阅视频2
function startVideo2(p_DecodeTag,p_cameraCode,p_name,v_winNum){	
	if(p_DecodeTag=="" || p_cameraCode==""||p_DecodeTag==null|| p_cameraCode==null){
		alert("请配置台帐扩展参数！")
	}else if(isNaN(p_DecodeTag)){
		alert("参数配置不正确！");
	}else{
		if (document.getElementById("playerV")!= null){
			var e=ocxVideoStart(20000,v_winNum,parseInt(p_DecodeTag),p_cameraCode,p_name);
			alert("调阅视频："+e);
		}
	}
	
}

//查找历史视频文件
function findHistoryVideo(v_pCode,v_pStartTime,v_pEndTime){
	if(v_pCode==""||v_pCode==null){
		alert("装置设备编码为空！");
	}else{
		if (document.getElementById("playerV")!= null ){
			var e=ocxRemoteFileQuery(-1,v_pCode,v_pStartTime,v_pEndTime);
			return e;
		}
	}
}

//开始历史视频显示
function showHistoryVideo(v_pFileurl,v_sDecoderTag,v_pNotes){
	if(v_pFileurl==""||v_sDecoderTag==""||v_pFileurl==null||v_sDecoderTag==null){
		alert("历史视频文件地址或解码为空！");
	}else{
		if (document.getElementById("playerV")!= null ){
			ocxVideoStop(20000,0);
			var e=ocxRemoteFilePlayStart(20000,v_winNum,v_pFileurl,v_sDecoderTag,v_pNotes);
			//alert("历史："+e);
		}
	}
}



// 调用预置位
	function getPreset(){
		var presetList = document.getElementById('sjlb_selector');
	if(presetList.value!=-1 && presetList.value!=""){
		var _preset = presetList.value.split("@");
		ocxPresetGet(20000,_preset[1],parseInt(_preset[0]));
	}
	// presetList.options[0].selected = true;
 }
	 // 删除预置位
	 function delPreset(){
		 var presetList = document.getElementById('sjlb_selector');
			if(presetList.length>0){
				var _preset = presetList.value.split("@");
				var dataTable = ztjc.ZtjcServiceAgent.getDataGeneral("getPreset","where presetid="+_preset[0]);
				if(dataTable.rows.length>0){
					var businessDataCollection = new mw.businesslogic.BusinessDataCollection();
				    businessDataCollection.serializeChangesOnly = false;
				    var result = ztjc.ZtjcServiceAgent.loadEntity(dataTable.rows[0]["OBJ_ID"].value,'9B00D9A0-0530-4DC5-A864-5DB08CAFFF44');
					businessDataCollection.addBusinessData(result);
					ztjc.ZtjcServiceAgent.deleteEntity(businessDataCollection,'9B00D9A0-0530-4DC5-A864-5DB08CAFFF44');
					ocxPresetDelete(20000,_preset[1],parseInt(_preset[0]));
				}
				
			}
			initSelectList(_jczzGlobalId);
	 }
	 function recall(){
		 v_winNum = 0;
			function OnClickActiveWnd (ttt,winNum,camcode)
			{
				v_winNum = winNum;
				/*
				// var p_camCode = playerV.ocxGetWndCallnum(0);//获取摄像机18位编码;
				p_camCode = camcode;
				if(p_camCode!=null && p_camCode!='' && p_camCode!="0"){
					var sql_fp = "SELECT * FROM mw_app.cmst_presetmapping  where  remarks='"+p_camCode+"'";
					var presetDatatable = mw.data.DataAccessAgent.executeQuery(sql_fp);
					var o = document.getElementById('sjlb_selector');
					o.innerHTML = "";
					var   inserto   =   '';
					inserto =   new   Option( '-请选择- ',-1);
					o.add(inserto);
					if(presetDatatable.rows.length>0){
						for (var i = 0; i < presetDatatable.rows.length; i++) {
							inserto   =   new   Option( presetDatatable.rows[i].PRESETNAME.value,presetDatatable.rows[i].PRESETID.value+"@"+presetDatatable.rows[i].REMARKS.value); 
							o.add(inserto);
				  		 }
					}

					// getPreset();
				}
				else
				{
					var o = document.getElementById('sjlb_selector');
					o.innerHTML = "";
				}*/
			}
		}
	// 设置预置位 获取摄像机的编码
		function setPreset(){
			var _jczzid = "";
			// var p_camCode = playerV.ocxGetWndCallnum(0);//获取摄像机18位编码
			var condition="paravalue='"+p_camCode+"'";  
			var videoData =  ztjc.ZtjcServiceAgent.getLineSensorExtend(condition);
			if(videoData.rows.length>0){
				_jczzid=videoData.rows[0].LINKEDDEVICE.value;// 装置ID
			}
			if(_jczzid!="" && _jczzid!=null){
				var poleDatas =  ztjc.ZtjcServiceAgent.getDataGeneral("getXLJCZZ","where devicecode='"+_jczzid+"'");
				if(poleDatas.rows.length>0){
					var p_pole = poleDatas.rows[0].LINKEDPOLE.value;// 杆塔ID
					var p_presetName = document.getElementById("WND_PST_SET").value;
					if(p_presetName==null || p_presetName =="undefined" || p_presetName ==""){
						alert("请输入预置位名称!");
					}else{
						
						presetSet(p_pole,_jczzid,p_presetName,userID,p_camCode);
						initSelectList(_jczzGlobalId);
					}
				}
			}
		}	
		// 保存预置位信息到数据库
		function presetSet(p_pole,p_jczzid,p_presetName,p_userId,p_camCode){
			var _presetid = 1;
			var isHave = true;// 判断此预置位是否被占用
			var isExist = false;// 判断预置位是否存在，true为存在 false为不存在
			var idsData = ztjc.ZtjcServiceAgent.getDataGeneral("getPreset","where 1=1 order by presetid");
			var _length = idsData.rows.length;
			if(_length>0){
				id = idsData.rows;
				maxId = id[_length-1].PRESETID.value;// 最大预置位号
				for(var i = 1;i<=maxId;i++){
					isExist = false;
					for(var j=0;j<_length;j++){
						if(i == id[j].PRESETID.value){
							isExist = true;
							break;
						}
					}
					if(!isExist){
						_presetid = i;
						break;
					}
				}
				if(isExist){
					_presetid = _length+1;
				}
			}
			while(isHave){
				// 设置预置位，成功返回0
			
				var isSucess = ocxPresetSet(20000,p_camCode,parseInt(_presetid));
				if(isSucess == 0 || isSucess == "0"){
					break;
				}
				_presetid = _presetid +1;
			}
			 var cslId = '9B00D9A0-0530-4DC5-A864-5DB08CAFFF44';// 预置位映射表
			    var pEntity = ztjc.ZtjcServiceAgent.createEntity(cslId);
				 	pEntity.setAttribute("LINKEDPOLE", p_pole);// 杆塔id
				    pEntity.setAttribute("DEVICECODE", p_jczzid);// 装置id
				    pEntity.setAttribute("PRESETID", _presetid);// 设置位id
				    pEntity.setAttribute("PRESETNAME", p_presetName);// 设置位名称
				    pEntity.setAttribute("USERID", p_userId);// 用户id
				    pEntity.setAttribute("REMARKS", p_camCode);// 备注
				    ztjc.ZtjcServiceAgent.saveEntity(pEntity,cslId);
				    alert("设置成功!");
		}
		// 云台控制
		 function PTZControl(p_winNo,p_PTZType){
			if (document.getElementById("playerV")!= null ){
				var p_PTZSpeed = document.getElementById("selectSpeed").value;
			  	p_PTZSpeed =  parseInt(p_PTZSpeed);
				//alert("子窗口编号:"+p_winNo);
				ocxPTZControl(20000,p_winNo,p_PTZType,p_PTZSpeed);
			}
			  	
		 }
		// 设置窗口显示个数
		 function pchenge(p_winNo){
			 if (document.getElementById("playerV")!= null ){
				 ocxSetWndNums(20000,p_winNo);
			 }
			
		 } 
		// 初始化预置位下拉列表
		  	function initSelectList(p_jczzGlobalId){
				if(p_jczzGlobalId!=null && p_jczzGlobalId!=''){
					var sql_fp = "SELECT * FROM mw_app.cmst_presetmapping  where  devicecode='"+p_jczzGlobalId+"'";
					var presetDatatable = mw.data.DataAccessAgent.executeQuery(sql_fp);
					var o = document.getElementById('sjlb_selector');
					o.innerHTML = "";
					var   inserto   =   '';
					inserto =   new   Option( '-请选择- ',-1);
					o.add(inserto);
					if(presetDatatable.rows.length>0){
						for (var i = 0; i < presetDatatable.rows.length; i++) {
							inserto   =   new   Option( presetDatatable.rows[i].PRESETNAME.value,presetDatatable.rows[i].PRESETID.value+"@"+presetDatatable.rows[i].REMARKS.value); 
							o.add(inserto);
				  		 }
					}
					// getPreset();
				}
		  	}
		  // 初始化参数
			function initPara(p_DecodeTag,p_cameraCode,p_jczzGlobalId){
				_jczzGlobalId = p_jczzGlobalId;
				startVideo(p_DecodeTag,p_cameraCode);
				
				// initSelectList(p_jczzGlobalId);
			}
		  // 初始化参数2
			function initPara2(p_DecodeTag,p_cameraCode,p_name,v_winNum){
				
				startVideo2(p_DecodeTag,p_cameraCode,p_name,v_winNum);
				
				// initSelectList(p_jczzGlobalId);
			}
			function loginOut(){
				ocxVideoStopAll(20000);
				ocxDestroyWindow(20000);
				ocxLogout();
				var a = ocxUnInit();
			}