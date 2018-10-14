$ns("dldevicetj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.GroupHeaderGrid")
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.controls.Label");
$import("mx.containers.Container");
dldevicetj.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;
    

    base.init = me.init;
    me.init = function()
    {
        base.init();
        
        _initControls();
        me.on("activate",me.controller._onactive);
    };
    
    function _initControls()
    {
       
    	var restUrl = "~/rest/dldevicequery/loadtjxx";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : dldevicetj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "SSWS"
		});
		
		me.dataGrid = new mx.datacontrols.GroupHeaderGrid({
    		width : "100%",
    		height : "auto",
    		position : "absolute",
    		bottom : "0px",
    		top : "0px",
    		columns: [
    		{name: "SSWS", caption: "SSWS" ,visible : false},
    		{ name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
    		{
    		name: "group1",
    		caption: "电缆本体监测",
    		columns: [
    		{ name: "DLJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "DLJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "DLJCLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
    		]
    		},
    		{
        		name: "group2",
        		caption: "电缆通道监测",
        		columns: [
        		{ name: "DLTDJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "DLTDJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "DLTDJCLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	
        	{
        		name: "group6",
        		caption: "合计",
        		columns: [
        		{ name: "JRSHJ", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZSHJ", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "SSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}],
        	// 设置查询条件。
        	searchBox : new mx.datacontrols.DataGridSearchBox({
				fields : [ {
					name : "linkeddepws",
					caption : "所属单位",
					editorType : "DropDownEditor",
					width : 180,
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				},{
					name : "LINKEDCABLEANDCHANNELNAME",
					caption : "电力电缆及通道名称",
					editorType : "TextEditor",
					width : 180,
					onchanged:_nameChanged,
					hint : "--请输入--"
				}, {
					name : "manufacturer",
					caption : "生产厂家",
					editorType : "TextEditor",
					width : 180,
					onchanged:_paramChanged,
					hint : "--请输入--"
				}, {
					name : "monitoringtype",
					caption : "监测类型",
					editorType : "DropDownEditor",
					width : 180,
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				}, {
					name : "dydj",
					caption : "电压等级",
					editorType : "DropDownEditor",
					width : 180,
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				}, {
					name : "yxzt",
					caption : "运行状态",
					editorType : "DropDownEditor",
					width : 180,
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				},   {
					name : "srundate",
					caption : "投运日期",
					editorType : "DateTimeEditor",
					//maxLength : 200,
					width : 180,
					onchanged:_searchParamChanged,
					//oninput : data_judge(this),
					formatString: "yyyy-MM-dd",
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				}, {
					name : "erundate",
					caption : "至",
					editorType : "DateTimeEditor",
					//maxLength : 200,
					width : 180,
					onchanged:_searchParamChanged,
					//oninput : data_judge(this),
					formatString: "yyyy-MM-dd",
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				}
				],
				itemNumOfRow : 4,
				captionColumnWidth : 100,
				onsearchparamchanged : _searchParamChanged
			}),
    		entityContainer : gridEntityContainer,
    		displayCheckBox : false,
    		displayRowNumber : true,
    		allowPaging : false,
    		displayPrimaryKey : false,
    		displayColSplitLine : true,
    		allowEditing : false,
    		rowNumberColWidth : 40,
    		onload:me.controller._initDataGrid
    		});
		
		_initCx();
//		_resetToolBar();
//		me.dataGrid.toolBar.$e.insertAfter(me.dataGrid.searchBox.$e);
		me.addControl(me.dataGrid);
		//me.dataGrid.load();
    }
    //重写查询重置
	function _initCx() {
		var rows = me.dataGrid.searchBox.$e[0].childNodes[0].childNodes[0].rows;
		var row = rows.item(rows.length - 1);
		rows.item(rows.length - 1).cells[8].childNodes[0].innerHTML="<input type='button' value='按监测类型统计' >";
		rows.item(rows.length - 1).cells[8].childNodes[0].onclick = me.controller._btnStatistics_onclick;
		
		rows.item(rows.length - 1).cells[8].childNodes[2].innerHTML="<input type='button' value='导出'>";
		rows.item(rows.length - 1).cells[8].childNodes[2].onclick = me.controller._btnexport_onclick;
	}
 
    //获取grid
    me.getDataGrid = function()
    {
    	return me.dataGrid;
    }
    
    var pattern1 = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]+$");
	function _paramChanged()
	{
		var manufacturer = me.dataGrid.searchBox.editors.manufacturer.value;
		
		if(manufacturer!=null&&manufacturer!="")
		{
			if(manufacturer.length>50|| !pattern1.test(manufacturer)){
				me.dataGrid.searchBox.editors.manufacturer.reset();
				mx.indicate("warn", "生产厂家长度不能超50且不能为特殊字符!");
			}
				
		}	
	}
	
	function _nameChanged()
	{
		var LINKEDCABLEANDCHANNELNAME = me.dataGrid.searchBox.editors.LINKEDCABLEANDCHANNELNAME.value;
		
		if(LINKEDCABLEANDCHANNELNAME!=null&&LINKEDCABLEANDCHANNELNAME!="")
		{
			if(LINKEDCABLEANDCHANNELNAME.length>50|| !pattern1.test(LINKEDCABLEANDCHANNELNAME)){
				me.dataGrid.searchBox.editors.LINKEDCABLEANDCHANNELNAME.reset();
				mx.indicate("warn", "电缆/通道名称长度不能超50且不能为特殊字符!");
			}
				
		}	
	}
	
	//时间判断，起始时间不能大于终止时间，且都不能大于当前时间
	function _searchParamChanged()
	{
		var stime = me.dataGrid.searchBox.editors.srundate.value;
		var etime = me.dataGrid.searchBox.editors.erundate.value;
		var date = new Date().toLocaleDateString();
		date = date.replace(/-/g,"/");
		
		if(stime!=""&&stime!=null){
			stime=stime.replace(/-/g,"/");
			if(Date.parse(stime)>Date.parse(date))
			{
				me.dataGrid.searchBox.editors.srundate.reset();
				mx.indicate("warn", "开始时间不能大于当前时间!");
			}
		}
		
		if(etime!=""&&etime!=null){
			etime=etime.replace(/-/g,"/");
			if(Date.parse(etime)>Date.parse(date))
			{
				me.dataGrid.searchBox.editors.erundate.reset();
				mx.indicate("warn", "结束时间不能大于当前时间!");
			}
		}
		
		if(stime!=""&&etime!=""&&stime!=null&&etime!=null)
		{
			stime=stime.replace(/-/g,"/");
			etime=etime.replace(/-/g,"/");
			if(Date.parse(stime)>Date.parse(etime))
			{
				me.dataGrid.searchBox.editors.srundate.reset();
				mx.indicate("warn", "开始时间不能大于结束时间!");
			}
		}	

		
	}
    return me.endOfClass(arguments);
};