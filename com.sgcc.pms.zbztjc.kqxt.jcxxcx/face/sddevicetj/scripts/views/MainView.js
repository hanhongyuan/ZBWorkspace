$ns("sddevicetj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.GroupHeaderGrid")
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.controls.Label");
$import("mx.containers.Container");

sddevicetj.views.MainView = function()
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
    	var restUrl = "~/rest/sddevice/loadtjxx";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sddevicetj.mappath(restUrl),
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
    		caption: "气象环境监测类",
    		columns: [
    		{ name: "QXJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "QXJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    		]
    		},
    		{
        		name: "group2",
        		caption: "导线监测类",
        		columns: [
        		{ name: "DXJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "DXJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group3",
        		caption: "杆塔监测类",
        		columns: [
        		{ name: "GTJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "GTJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group4",
        		caption: "绝缘子监测类",
        		columns: [
        		{ name: "JYZJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "JYZJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group5",
        		caption: "视频类",
        		columns: [
        		{ name: "SPJCRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "SPLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
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
				}, {
					name : "linkedlinename",
					caption : "线路名称",
					editorType : "TextEditor",
					width : 180,
					hint : "--请输入--"
				}, {
					name : "manufacturer",
					caption : "生产厂家",
					editorType : "TextEditor",
					width : 180,
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
					name : "srundate",
					caption : "投运日期",
					editorType : "DateTimeEditor",
					width : 180,
					formatString: "yyyy-MM-dd",
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				}, {
					name : "erundate",
					caption : "至",
					editorType : "DateTimeEditor",
					width : 180,
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
		me.addControl(me.dataGrid);
    }
    //重写查询重置
	function _initCx() {
		var rows = me.dataGrid.searchBox.$e[0].childNodes[0].childNodes[0].rows;
		var row = rows.item(rows.length - 1);
		rows.item(rows.length - 1).cells[6].childNodes[0].innerHTML="<input type='button' value='按监测类型统计' >";
		rows.item(rows.length - 1).cells[6].childNodes[0].onclick = me.controller._btnStatistics_onclick;
		
		rows.item(rows.length - 1).cells[6].childNodes[2].innerHTML="<input type='button' value='导出'>";
		rows.item(rows.length - 1).cells[6].childNodes[2].onclick = me.controller._btnexport_onclick;
	}
 
	//时间判断，起始时间不能大于终止时间
	function _searchParamChanged()
	{
		//alert("11");
		var stime = me.dataGrid.searchBox.editors.srundate.value;
		var etime = me.dataGrid.searchBox.editors.erundate.value;
		
		
		if(stime!=""&&etime!=""&&stime!=null&&etime!=null)
		{
			stime=stime.replace(/-/g,"/");
			etime=etime.replace(/-/g,"/");
			if(Date.parse(stime)>Date.parse(etime))
			{
				alert("起始时间不能大于终止时间！");

			}

		}	

		
	}
    //获取grid
    me.getDataGrid = function()
    {
    	return me.dataGrid;
    }
    
    return me.endOfClass(arguments);
};