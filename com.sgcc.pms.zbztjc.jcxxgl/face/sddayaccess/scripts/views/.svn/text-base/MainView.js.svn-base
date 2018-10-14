$ns("sddayaccess.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.GroupHeaderGrid")
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.controls.Label");
$import("mx.containers.Container");
sddayaccess.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth()+1;
    var day = myDate.getDate();
    var tjtime = year.toString()+"-"+month.toString()+"-"+day.toString();
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
        /* 初始化 ToolBar */
    	var restUrl = "~/rest/sddayaccess/loadtjxx";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sddayaccess.mappath(restUrl),
			loadMeta : false,
			iscID : "-1"
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
    		caption: "杆塔倾斜监测",
    		columns: [
    		{ name: "JR012001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "ZS012001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    		]
    		},
    		{
        		name: "group2",
        		caption: "导线弧垂监测",
        		columns: [
        		{ name: "JR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group3",
        		caption: "导线温度监测",
        		columns: [
        		{ name: "JR013002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013002", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group4",
        		caption: "微风振动监测",
        		columns: [
        		{ name: "JR013003", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013003", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group5",
        		caption: "风偏监测",
        		columns: [
        		{ name: "JR013004", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013004", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},{
        		name: "group6",
        		caption: "覆冰监测",
        		columns: [
        		{ name: "JR013005", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013005", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group7",
        		caption: "导线舞动",
        		columns: [
        		{ name: "JR013006", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013006", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group8",
        		caption: "现场污秽度监测",
        		columns: [
        		{ name: "JR014001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS014001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group9",
        		caption: "微气象监测",
        		columns: [
        		{ name: "JR018001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS018001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group10",
        		caption: "图像监测",
        		columns: [
        		{ name: "JR018002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS018002", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group11",
        		caption: "视频监测",
        		columns: [
        		{ name: "JR018003", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS018003", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group12",
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
					name : "savetime",
					caption : "指标日期",
					editorType : "DateTimeEditor",
					width : 180,
					formatString: "yyyy-MM-dd",
					allowEditing : false,
					displayCheckBox : true
					
				}
				],
				itemNumOfRow : 4
			}),
			entityContainer : gridEntityContainer,
    		displayCheckBox : false,
    		displayRowNumber : true,
    		allowPaging : false,
    		displayPrimaryKey : false,
    		displayColSplitLine : true,
    		allowEditing : false,
    		rowNumberColWidth : 40
    		});
		_initCx();
		me.addControl(me.dataGrid);
		
		me.dataGrid.searchBox.editors["savetime"].setValue(tjtime);
		me.dataGrid.load();
    }
 
//重写查询重置
	function _initCx() {
		var rows = me.dataGrid.searchBox.$e[0].childNodes[0].childNodes[0].rows;
		var row = rows.item(rows.length - 1);
		rows.item(rows.length - 1).cells[4].childNodes[0].innerHTML="<input type='button' value='查询' >";
		rows.item(rows.length - 1).cells[4].childNodes[0].onclick = me.controller._btnStatistics_onclick;
		
		rows.item(rows.length - 1).cells[4].childNodes[2].innerHTML="<input type='button' value='导出'>";
		rows.item(rows.length - 1).cells[4].childNodes[2].onclick = me.controller._btnexport_onclick;
	}
	
    //获取grid
    me.getDataGrid = function()
    {
    	return me.dataGrid;
    }
    
    
    return me.endOfClass(arguments);
};