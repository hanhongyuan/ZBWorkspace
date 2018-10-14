$ns("zyxtdevicequery.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.ComplexGrid");
$import("mx.containers.TabControl");
zyxtdevicequery.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initTabControlView();
        _initControls();
    };
    
  //初始化tab
	function _initTabControlView(){
    	_tabView = new mx.containers.TabControl({
					onselectionchanging : me.controller._tabSelection_changing,
					onselectionchanged : me.controller._selection_changed,
					pages : [
						{text : "查询",name : "query",autoInit : true},
						{text : "统计",name : "statistics",autoInit : true},
						{text : "跨区系统维护",name : "kqxtwh",autoInit : true},
						{text : "关联系统",name : "xldzgl",autoInit : true}
					]
				});
		// 设置TabControl的样式
    	_tabView.$("div#head a").css({"width":"132px","height":"30px"});
    	_tabView.$("a#statistics span").css("height","20px");
    	_tabView.$("span.tab-text").css({"width":"132px","height":"20px","display":"inline-block","line-height":"20px","text-align":"center"});
 
		
		me.addControl(_tabView);
		
    }
    
    function _initControls()
    {
    	var restUrl = "~/rest/zyxtdevice/getsddevice";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : zyxtdevicequery.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		/* 初始化 DataGrid */
		me.dataGrid = new mx.datacontrols.ComplexGrid({
			// alias : "montringgrid",
			// 设置显示列。
			columns : [
			{
				name : "WSDEPMC",
				caption : "所属单位",
				dataAlign : "center"
			},
			{
				name : "XTMC",
				caption : "所属系统",
				dataAlign : "center"
			},
			 {
				name : "XLMC",
				caption : "线路名称/变电站名称",
				dataAlign : "center"
			}, {
				name : "GTMC",
				caption : "杆塔名称/一次设备",
				dataAlign : "center"
			}, {
				name : "DEVICEVOLTAGE",
				caption : "电压等级",
				dataAlign : "center"
			}, {
				name : "DEVICECATEGORY",
				caption : "接入方式",
				dataAlign : "center"
			},{
				name : "DEVICECATEGORY_DISPLAY",
				caption : "装置类型",
				dataAlign : "center"
			}, {
				name : "JCXX",
				caption : "监测信息",
				dataAlign : "center",
				renderCell : function(p_item, $p_cell) {
					var value = "查看"; // 获取 GENDER 字段的值。
					$p_cell.html(value).css({
						"text-decoration" : "underline",
						"color" : "blue"
					}).mouseover(function() {
						$p_cell.css("cursor", "pointer");
					}).on("click", function() {
						$p_cell.css("color", "grey");
						me.controller._btnTest();
					});
				}
			},  {
				name : "DEVICENAME",
				caption : "装置名称",
				width : 180,
				dataAlign : "center"
			}, {
				name : "DEVICEMODEL",
				caption : "装置型号",
				dataAlign : "center"
			},{
				name : "MANUFACTURER",
				caption : "生产厂家"
			},
			{
				name : "RUNDATE",
				caption : "投运日期",
				dataAlign : "center"
			}
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
				onsearchparamchanged : _searchParamChanged
			}),
			// displayPrimaryKey : true,
			displayRowNumber : true,
			displayCheckBox : false,
			rowNumberColWidth : 50,
			displayColSplitLine : true,
			entityContainer : gridEntityContainer,
			enableCellTip : true

		});

		// _initCx();
		_resetToolBar();
		me.dataGrid.toolBar.$e.insertAfter(me.dataGrid.searchBox.$e);
		_tabView.pages["query"].addControl(me.dataGrid);
		me.dataGrid.load();
		me.statistics =new zyxtdevicetj.views.MainViewController().getView().getDataGrid();
		_tabView.pages["statistics"].addControl(me.statistics);
		me.statistics.load();
		me.kqxtwh =new kqxtwh.views.MainViewController().getView();
		_tabView.pages["kqxtwh"].addControl(me.kqxtwh);
		me.xldzgl =new xldzgl.views.MainViewController().getView();
		_tabView.pages["xldzgl"].addControl(me.xldzgl);
    }
    
    /**
	 * 加载按钮
	 */
	function _resetToolBar() {
		me.dataGrid.toolBar.clearItems();

		me.dataGrid.toolBar.insertItem(0, {
			name : "export",
			text : "导出",
			toolTip : "导出"
		}, true);
	}


//时间判断，起始时间不能大于终止时间
	function _searchParamChanged()
	{
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
	/**
	 * 获取DataGrid网格列表对象
	 */
	me.getDataGrid = function() {
		return me.dataGrid;
	}
    return me.endOfClass(arguments);
};