$ns("zyxtdevicetj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");
$import("mx.datacontrols.GroupHeaderGrid");
zyxtdevicetj.views.MainView = function()
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
    	var restUrl = "~/rest/zyxtdevice/loadtjxx";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : zyxtdevicetj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "SSXTID"
		});
		
		me.dataGrid = new mx.datacontrols.GroupHeaderGrid({
    		width : "100%",
    		height : "auto",
    		position : "absolute",
    		bottom : "0px",
    		top : "0px",
    		columns: [
    		{name: "SSXTID", caption: "SSXTID" ,visible : false},
    		{ name: "SSXTMC", caption: "所属系统名称", dataAlign:"center", editorType: "TextEditor"  },
    		{name:"sd",
    		caption:"输电",
    		columns:[
    		{
    		name: "group1",
    		caption: "气象环境监测类",
    		columns: [
    		{ name: "QXJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "QXJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    		]
    		},
    		{
        		name: "group2",
        		caption: "导线监测类",
        		columns: [
        		{ name: "DXJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "DXJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group3",
        		caption: "杆塔监测类",
        		columns: [
        		{ name: "GTJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "GTJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group4",
        		caption: "绝缘子监测类",
        		columns: [
        		{ name: "JYZJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "JYZJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group6",
        		caption: "合计",
        		columns: [
        		{ name: "JRSHJ_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZSHJ_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "SSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}]},
        	{name:"bd",
        	 caption:"变电",
        	 columns:[
        	{
        		name: "group7",
        		caption: "变压器/电抗器类",
        		columns: [
        		{ name: "BYDKLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "BYDKLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        		},
        		{
            		name: "group8",
            		caption: "断路器/GIS类",
            		columns: [
            		{ name: "DLGISLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
            		{ name: "DLGISLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
            		]
            	},
            	{
            		name: "group9",
            		caption: "电容型设备类",
            		columns: [
            		{ name: "DRXLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
            		{ name: "DRXLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
            		]
            	},
            	{
            		name: "group10",
            		caption: "金属氧化物避雷器类",
            		columns: [
            		{ name: "JSYHWLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
            		{ name: "JSYHWLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
            		]
            	},
            	{
            		name: "group12",
            		caption: "合计",
            		columns: [
            		{ name: "BDJRSHJ_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
            		{ name: "BDZSHJ_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
            		{ name: "BDSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
            		]
            	}]}
        	],
        	// 设置查询条件。
        	searchBox : new mx.datacontrols.DataGridSearchBox({
				fields : [ {
					name : "ssxt",
					caption : "所属系统",
					editorType : "DropDownEditor",
					allowEditing : false,
					displayCheckBox : true,
					width : 180,
					hint : "--请选择--"
				}, {
					name : "linkeddepws",
					caption : "所属单位",
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
					name : "monitoringtype",
					caption : "监测类型",
					editorType : "DropDownEditor",
					width : 180,
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				},{
					name : "linkedlinename",
					caption : "线路名称",
					editorType : "TextEditor",
					width : 180,
					hint : "--请输入--"
				},  {
					name : "linkedstationname",
					caption : "变电站名称",
					editorType : "TextEditor",
					width : 180,
					hint : "--请输入--"
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
				}, {
					name : "lx",
					caption : "专业",
					editorType : "CheckListEditor",
					//maxLength : 200,
					width : 180,
					border : "0px",
					allowEditing : false,
					type:"radio",
				    items:[
				        {text: "全部", value: "all",checked : true},
				        {text: "输电", value: "L"},
				        {text: "变电", value: "T"}
				        ]
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
		rows.item(rows.length - 1).cells[2].childNodes[0].innerHTML="<input type='button' value='按所属系统' >";
		rows.item(rows.length - 1).cells[2].childNodes[0].onclick = me.controller._btnStatistics_onclick;
		
		rows.item(rows.length - 1).cells[2].childNodes[2].innerHTML="<input type='button' value='导出'>";
		rows.item(rows.length - 1).cells[2].childNodes[2].onclick = me.controller._btnexport_onclick;
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