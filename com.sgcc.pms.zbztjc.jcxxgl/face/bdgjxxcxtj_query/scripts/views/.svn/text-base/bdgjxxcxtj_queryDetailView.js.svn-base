$ns("bdgjxxcxtj_query.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.containers.TabControl");
$import("mx.datacontrols.DataGrid");
$import("mx.rpc.RESTClient");
$import("mx.datacontainers.GridEntityContainer");

bdgjxxcxtj_query.views.bdgjxxcxtj_queryDetailView = function()
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
    	_form.load();
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
    	 var restUrl = "~/rest/bdgjxxcxtj_query/query";
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : bdgjxxcxtj_query.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "objId"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"bdgjxxcxtj_querybdgjxxcxtj_queryDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "objId", caption: "编号", editorType: "TextEditor", visible:false},
	        {	name: "LINKEDSTATIONNAME", caption: "所属变电站", editorType: "TextEditor"},
	        {	name: "LINKEDEQUIPMENTNAME", caption: "所属设备", editorType: "TextEditor"},
	        {	name: "DEVICECODE", caption: "监测装置", editorType: "TextEditor"},
	        {	name: "MONITORINGTYPE", caption: "监测类型编码", editorType: "TextEditor",visible:false},
	        {	name: "monitoringTypeName", caption: "监测类型", editorType: "TextEditor"},
	        {	name: "ALARMTIME", caption: "告警时间", editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
	        {	name: "ALARMENDTIME", caption: "告警结束时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd HH:mm:ss",readOnly:true},
	        {	name: "CONTINUANCETIME", caption: "持续时间（分钟）", editorType: "TextEditor"},
	        {	name: "ALARMLEVEL", caption: "告警级别", editorType: "TextEditor"},
	        {	name: "ALARMMESSAGE", caption: "告警信息", editorType: "TextEditor"},
	        {	name: "ALARMRULE", caption: "告警规则", editorType: "TextEditor"},
	        {	name: "ALARMSOURCE", caption: "告警来源", editorType: "TextEditor"},
	        {	name: "ALARMCONSTRUE", caption: "告警分析", editorType: "TextEditor"},
	        {	name: "DISPOSALADVICE", caption: "告警建议", editorType: "TextEditor"},
	        {	name: "DISPOSALTIME", caption: "处理时间", editorType: "TextEditor"},
	        {	name: "DISPOSALRESULT", caption: "处理结果", editorType: "TextEditor"},
	        {	name: "TRANSACTOR", caption: "处理人", editorType: "TextEditor"},
	        {	name: "CHECKTIME", caption: "审核时间", editorType: "TextEditor"},
	        {	name: "CHECKEDBY", caption: "审核人", editorType: "TextEditor"},
	        {	name: "ISHANDLED", caption: "是否处理", editorType: "TextEditor"},
	        {	name: "REMARKS", caption: "备注", editorType: "TextEditor"}
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
    			var bdgjxxcxId = _form.editors["objId"].value;		//获取编号ID
    			//监测类型加载时执行的函数
    		    var client = new mx.rpc.RESTClient();	//初始化RESTClient
    		    var restUrl2 = "~/rest/bdgjxxcxtj_query/queryMonitorRecordById/"+bdgjxxcxId;	//要执行的Controller方法
    		    client.get(bdgjxxcxtj_query.mappath(restUrl2), {"objId":bdgjxxcxId}, function(p) {
	    			if(p.length == 0){
	    				alert("监测记录不存在！")
	    				return;
	    			}
    			var typeCode = p[p.length-1];		//取出监测类型的编码
    			p.pop();				//从数组中删除监测类型编码，净化数据
    			
				var dgc = new mx.datacontainers.GridEntityContainer({
					"type":"local",
					"loadMeta" : false,
					"data":p
				});
				
				switch (typeCode) {		//根据监测类型，赋值监测记录
					case "021001":		//如果是变压器局部放电
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITIONTIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "PHASE", caption: "设备相别" , editorType: "TextEditor"},
									{name: "DISCHARGECAPACITY", caption: "放电量(pC)" , editorType: "TextEditor"},
									{name: "DISCHARGEPOSITION", caption: "放电位置" , editorType: "TextEditor"},
									{name: "PULSECOUNT", caption: "脉冲个数" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
					break;
					
					case "021002":		//如果是油中溶解气体
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITIONTIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "PHASE", caption: "设备相别" , editorType: "TextEditor"},
									{name: "H2", caption: "氢气(μL/L)" , editorType: "TextEditor"},
									{name: "CH4", caption: "甲烷(μL/L)" , editorType: "TextEditor"},
									{name: "C2H6", caption: "乙烷(μL/L)" , editorType: "TextEditor"},
									{name: "C2H4", caption: "乙烯(μL/L)" , editorType: "TextEditor"},{name: "CH4", caption: "甲烷(μL/L)" , editorType: "TextEditor"},
									{name: "C2H2", caption: "乙烷(μL/L)" , editorType: "TextEditor"},
									{name: "CO", caption: "一氧化碳(μL/L)" , editorType: "TextEditor"},{name: "CH4", caption: "甲烷(μL/L)" , editorType: "TextEditor"},
									{name: "CO2", caption: "氧气(μL/L)" , editorType: "TextEditor"},
									{name: "O2", caption: "氧气(μL/L)" , editorType: "TextEditor"},
									{name: "N2", caption: "氮气(μL/L)" , editorType: "TextEditor"},
									{name: "TOTALHYDROCARBON", caption: "总烃(μL/L)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
						break;
						
					case "021003":		//如果是微水
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITIONTIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "PHASE", caption: "设备相别" , editorType: "TextEditor"},
									{name: "MOISTURE", caption: "水分(μL/L)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"            
						});
						
						break;
						
					case "021004":		//如果是铁芯接地电流
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITIONTIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "PHASE", caption: "设备相别" , editorType: "TextEditor"},
									{name: "TOTALCORECURRENT", caption: "铁芯全电流(mA)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
						break;
						
					case "021005":		//如果是顶层油温
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITIONTIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "PHASE", caption: "设备相别" , editorType: "TextEditor"},
									{name: "OILTEMPERATURE", caption: "顶层油温(℃)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
						break;
						
					case "023001":		//如果是金属氧化物避雷器
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITIONTIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "PHASE", caption: "设备相别" , editorType: "TextEditor"},
									{name: "TOTALCURRENT", caption: "全电流(mA)" , editorType: "TextEditor"},
									{name: "SYSTEMVOLTAGE", caption: "系统电压(kV)" , editorType: "TextEditor"},
									{name: "RESISTIVECURRENT", caption: "阻性电流(mA)" , editorType: "TextEditor"},
									{name: "ACTIONCOUNT", caption: "计数器动作次数" , editorType: "TextEditor"},
									{name: "LASTACTIONTIME", caption: "最后一次动作时间" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"            
						});
						
						break;
						
					case "024003":		//如果是负荷电流波形
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITIONTIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "PHASE", caption: "设备相别" , editorType: "TextEditor"},
									{name: "ACTION", caption: "动作" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"	            
						});
						
						break;
						
					case "024004":		//如果是SF6气体压力
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITIONTIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "TEMPERATURE", caption: "温度(℃)" , editorType: "TextEditor"},
									{name: "ABSOLUTEPRESSURE", caption: "绝对压力(Mpa)" , editorType: "TextEditor"},
									{name: "DENSITY", caption: "密度(kg/m3)" , editorType: "TextEditor"},
									{name: "PRESSURE20C", caption: "压力（20℃）(Mpa)" , editorType: "TextEditor"}
							            ],
				            entityContainer: dgc,
				            pageSize : 10,
				            height:"100%"            
						});
						
						break;
						
					case "024005":		//如果是SF6气体水分
						me._TabControldataGrid = new mx.datacontrols.DataGrid({
							columns:[
									{name: "ACQUISITIONTIME", caption: "监测时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
									{name: "TEMPERATURE", caption: "温度(℃)" , editorType: "TextEditor"},
									{name: "MOISTURE", caption: "水分(μL/L)" , editorType: "TextEditor"}
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