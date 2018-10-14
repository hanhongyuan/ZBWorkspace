$ns("sdgjxxcxtj_query.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.containers.TabControl");
$import("mx.datacontrols.DataGrid");
$import("mx.rpc.RESTClient");
$import("mx.datacontainers.GridEntityContainer");


sdgjxxcxtj_query.views.sdgjxxcxtj_queryDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * 表单对象id
     */
    me.objID = null;
    /**
      * 表单对象
     */
    var _form = null;
    /**
     * 工具条
     */
    var _toolBar = null;

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

    me.load = function()
    {
    	//加载表单信息
    	_form.load(me.objID);
    }

    function _initControls()
    {
        _initToolBar();
        _initDataForm();
        _initTab();
        me.on("activate", me.controller._onactivate);
    }
	
    function _initToolBar()
    {
    	_toolBar = new mx.controls.ToolBar({
            width: "100%",
            items: [
                    { name: "save", text:"保存", toolTip:"保存",imageKey:"save",enabled:false, onclick: me.controller._btnSave_onclick},
                    "-",
                    { name: "refresh", text:"刷新", toolTip:"刷新",imageKey:"refresh", onclick: me.controller._btnRefresh_onclick},
                    "-",
                    { name: "revoke", text:"撤销", toolTip:"撤销",imageKey:"revoke", enabled:false,onclick: me.controller._btnRevoke_onclick},
                    "-",
                    { name: "print", text:"打印", toolTip:"打印",imageKey:"print", onclick:function(){_form.printForm()}},
                    "-"
                    ]
        });
    }
	
    function _initDataForm()
    {
    	var restUrl = "~/rest/sdgjxxcxtj_query/query";
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : sdgjxxcxtj_query.mappath(restUrl),
            loadMeta:false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "objId"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"sdgjxxcxtjsdgjxxcxtjDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "objId", caption: "编号", editorType: "TextEditor", visible:false},
	        {	name: "linkedlineName", caption: "所属线路", editorType: "TextEditor"},
	        {	name: "linkedpoleName", caption: "所属杆塔", editorType: "TextEditor"},
	        {	name: "devicecode", caption: "监测装置", editorType: "TextEditor"},
	        {	name: "alarmtime", caption: "告警时间", editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
	        {	name: "alarmlevel", caption: "告警级别", editorType: "TextEditor"},
	        {	name: "MONITORINGTYPE", caption: "监测类型编码", editorType: "TextEditor",visible:false},
	        {	name: "monitoringtypeName", caption: "监测类型", editorType: "TextEditor"},
	        {	name: "alarmmessage", caption: "告警信息", editorType: "TextEditor"},
	        {	name: "alarmsource", caption: "告警来源", editorType: "TextEditor"},
	        {	name: "alarmconstrue", caption: "告警分析", editorType: "TextEditor"},
	        {	name: "disposaladvice", caption: "告警建议", editorType: "TextEditor"},
	        {	name: "disposaltime", caption: "处理时间", editorType: "TextEditor"},
	        {	name: "disposalresult", caption: "处理结果", editorType: "TextEditor"},
	        {	name: "transactor", caption: "处理人", editorType: "TextEditor"},
	        {	name: "alarmendtime", caption: "告警结束时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd HH:mm:ss",readOnly:true},
	        {	name: "continuancetime", caption: "持续时间（分钟）", editorType: "TextEditor"},
	        {	name: "ishandled", caption: "是否处理", editorType: "TextEditor"},
	        {	name: "remarks", caption: "备注", editorType: "TextEditor"}
		    ],
            entityContainer: formEntityContainer,
            width:600,
            left:-20,
            displayPrimaryKey:false,
            enabled:false
        });
    }
    
    
    function _initTab()
    {
    	_tabcontrol = new mx.containers.TabControl({
 		   pages:[
 		       { text: "详细信息", name: "detail", autoInit: true },
 			   { text: "我的主页", name: "monitoringtype", autoInit: true }
 		   ],
 		  onselectionchanging: _selection_changing
     	});
    	
    	
    	function _selection_changing(e){
    		if("monitoringtype" == e.page.name){
    			var sdgjxxcxId = _form.editors["objId"].value;		//获取编号ID
    			//监测类型加载时执行的函数
    		    var client = new mx.rpc.RESTClient();	//初始化RESTClient
    		    var restUrl2 = "~/rest/sdgjxxcxtj_query/queryMonitorRecordById/"+sdgjxxcxId;	//要执行的Controller方法
    		    client.get(sdgjxxcxtj_query.mappath(restUrl2), {"objId":sdgjxxcxId}, function(p) {
    			
    			var typeCode = p[p.length-1];		//取出监测类型的编码
    			p.pop();				//从数组中删除监测类型编码，净化数据
    			
				var dgc = new mx.datacontainers.GridEntityContainer({
					"type":"local",
					"loadMeta" : false,
					"data":p
				});
				
				switch (typeCode) {		//根据监测类型，赋值监测记录
					case "013004":		//如果是风偏监测类型
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITION_TIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "WINDAGE_YAW_ANGLE", caption: "风偏角（°）" , editorType: "TextEditor"},
									{name: "DEFLECTION_ANGLE", caption: "倾斜角（°）" , editorType: "TextEditor"},
									{name: "LEAST_CLEARANCE", caption: "最小电气间隙参数(M)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
					break;
					case "012001":		//如果是杆塔倾斜监测
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITION_TIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "INCLINATION", caption: "倾斜度(mm/m)" , editorType: "TextEditor"},
									{name: "INCLINATION_X", caption: "顺线倾斜度(mm/m)" , editorType: "TextEditor"},
									{name: "INCLINATION_Y", caption: "横向倾斜度(mm/m)" , editorType: "TextEditor"},
									{name: "ANGLE_X", caption: "顺线倾斜角(°)" , editorType: "TextEditor"},
									{name: "ANGLE_Y", caption: "横向倾斜角(°)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
						break;
						
					case "013001":		//如果是导线弧垂监测
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITION_TIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "CONDUCTOR_SAG", caption: "导线弧垂(m)" , editorType: "TextEditor"},
									{name: "TOGROUND_DISTANCE", caption: "对地距离(m)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"            
						});
						
						break;
						
					case "013002":		//如果是导线温度监测
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITION_TIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "LINE_TEMPERATURE1", caption: "线温1（°C）" , editorType: "TextEditor"},
									{name: "LINE_TEMPERATURE2", caption: "线温2（°C）" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
						break;
						
					case "013003":		//如果是微风振动监测
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITION_TIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "VIBRATION_AMPLITUDE", caption: "振动幅值(με)" , editorType: "TextEditor"},
									{name: "VIBRATION_FREQUENCY", caption: "振动频率(Hz)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
						break;
						
					case "013005":		//如果是覆冰监测
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITION_TIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "EQUAL_ICETHICKNESS", caption: "等值覆冰厚度(Mm)" , editorType: "TextEditor"},
									{name: "TENSION", caption: "综合悬挂载荷(N)" , editorType: "TextEditor"},
									{name: "TENSION_DIFFERENCE", caption: "不均衡张力差(N)" , editorType: "TextEditor"},
									{name: "WINDAGE_YAW_ANGLE", caption: "绝缘子串风偏角（°）" , editorType: "TextEditor"},
									{name: "DEFLECTION_ANGLE", caption: "绝缘子串倾斜角（°）" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"            
						});
						
						break;
					
					case "013006":		//如果是导线舞动监测
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITION_TIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "U_GALLOP_AMPLITUDE", caption: "舞动幅值(m)" , editorType: "TextEditor"},
									{name: "U_VERTICAL_AMPLITUDE", caption: "垂直舞动幅值(m)" , editorType: "TextEditor"},
									{name: "U_HORIZONTAL_AMPLITUDE", caption: "水平舞动幅值(m)" , editorType: "TextEditor"},
									{name: "U_ANGLETOVERTICAL", caption: "舞动椭圆倾斜角(°)" , editorType: "TextEditor"},
									{name: "U_GALLOP_FREQUENCY", caption: "舞动频率(Hz)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
						break;
						
					case "014001":		//如果是现场污秽度检测
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITION_TIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "NSDD", caption: "盐密(mg/cm²)" , editorType: "TextEditor"},
									{name: "ESDD", caption: "灰密(mg/cm²)" , editorType: "TextEditor"},
									{name: "DAILY_MIN_TEMPERATURE", caption: "日最低温度(℃)" , editorType: "TextEditor"},
									{name: "DAILY_MAX_TEMPERATURE", caption: "日最高温度(℃)" , editorType: "TextEditor"},
									{name: "DAILY_MAX_HUMIDITY", caption: "日最大湿度(%RH)" , editorType: "TextEditor"},
									{name: "DAILY_MIN_HUMIDITY", caption: "日最小湿度(%RH)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"            
						});
						
						break;
						
					case "018001":		//如果是微气象监测
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITION_TIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "AIR_TEMPERATURE", caption: "气温（°C）" , editorType: "TextEditor"},
									{name: "HUMIDITY", caption: "湿度(%RH)" , editorType: "TextEditor"},
									{name: "AVERAGE_WINDSPEED_10MIN", caption: "风速(m/s)" , editorType: "TextEditor"},
									{name: "AVERAGE_WINDDIRECTION_10MIN", caption: "风向(°)" , editorType: "TextEditor"},
									{name: "MAX_WINDSPEED", caption: "最大风速(m/s)" , editorType: "TextEditor"},
									{name: "EXTREME_WINDSPEED", caption: "极大风速(m/s)" , editorType: "TextEditor"},
									{name: "STANDARD_WINDSPEED", caption: "标准风速(m/s)" , editorType: "TextEditor"},
									{name: "AIR_PRESSURE", caption: "气压(hPa)" , editorType: "TextEditor"},
									{name: "PRECIPITATION", caption: "降雨量(mm)" , editorType: "TextEditor"},
									{name: "PRECIPITATION_INTENSITY", caption: "降雨强度(mm/min)" , editorType: "TextEditor"},
									{name: "RADIATION_INTENSITY", caption: "辐射强度(W/m²)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"         
						});
							
					break;
				}
				e.page.append(me._TabControldataGrid);
				me._TabControldataGrid.load();
    		});
    		}
    		
    	}
    	
    	_tabcontrol.pages[0].addControl(_toolBar);
    	_tabcontrol.pages[0].addControl(_form);
		me.addControl(_tabcontrol);
    	
    }

    /**
     * 获取表单对象
     */
    me.getForm = function(){
		return _form;
    }
	
    /**
     * 获取工具条
     */
    me.getToolBar = function(){
		return _toolBar;
    }
    
    /**
     * 获取分页
     */
    me.getTabcontrol = function(){
    	return _tabcontrol;
    }
    
	me.endOfClass(arguments)
    return me;
};