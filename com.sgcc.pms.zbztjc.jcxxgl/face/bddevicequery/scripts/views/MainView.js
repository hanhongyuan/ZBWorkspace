$ns("bddevicequery.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.ComplexGrid");
$import("mx.rpc.RESTClient");
$import("mx.containers.TabControl");
bddevicequery.views.MainView = function() {
	var me = $extend(mx.views.View);
	var base = {};

	me.dataGrid = null;
	var rest = new mx.rpc.RESTClient();
	
	
	base.init = me.init;
	me.init = function() {
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
						{text : "统计",name : "statistics",autoInit : true}				
					]
				});
		// 设置TabControl的样式
    	_tabView.$("div#head a").css({"width":"132px","height":"30px"});
    	_tabView.$("a#statistics span").css("height","20px");
    	_tabView.$("span.tab-text").css({"width":"132px","height":"20px","display":"inline-block","line-height":"20px","text-align":"center"});
 
		
		me.addControl(_tabView);
		
    }
	
	
	//初始化表格
	function _initControls() {
		var restUrl = "~/rest/bddevicequery/getbddeviceifo";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : bddevicequery.mappath(restUrl),
			loadMeta : false,
			iscID : "-1" // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			
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
				name : "LINKEDSTATIONNAME",
				caption : "所属变电站",
				dataAlign : "center"
				
			}, {
				name : "LINKEDEQUIPMENTNAME",
				caption : "所属一次设备",
				dataAlign : "center"
				
			},
			{
				name : "DEVICEVOLTAGE",
				caption : "电压等级",
				dataAlign : "center"
				
			},{
				name : "DEVICECATEGORY",
				caption : "接入方式",
				dataAlign : "center"
			},{
				name : "DEVICECATEGORY_DISPLAY",
				caption : "监测类型",
				dataAlign : "center"
			},   {
				name : "JCXX",
				caption : "查看",
				dataAlign : "center",
				//visible:false,
				renderCell : function(p_item, $p_cell) {
					var value = "查看"; // 获取 GENDER 字段的值。 // 获取 GENDER 字段的值。
					var deptws = p_item.getValue("LINKEDDEPWS");
					var devicecode = p_item.getValue("DEVICECODE");
					var monitor = p_item.getValue("MONITORINGTYPES");
					$p_cell.html(value).css({
						"text-decoration" : "underline",
						"color" : "blue"
					}).mouseover(function() {
						$p_cell.css("cursor", "pointer");
					}).on("click", function() {
						$p_cell.css("color", "grey");
						me.controller._btnTest(devicecode,monitor,deptws);
					});
						}
			}, {
				name : "DEVICENAME",
				caption : "装置名称",
				dataAlign : "center"
			}, {
				name : "DEVICEMODEL",
				caption : "装置型号",
				dataAlign : "center"
			},
			 {
				name : "MANUFACTURER",
				caption : "生产厂家",
				dataAlign : "center"
			},{
				name : "ISRT",
				caption : "是否实时",
				dataAlign : "center",
				renderCell : function(p_item, $p_cell) {
					var value = p_item.getValue("ISRT"); // 获取 GENDER 字段的值。
					if("是"==value)
						{
						$p_cell.html(value).css({
							
							"color" : "green"
						});
						}
					else 
					{
						$p_cell.html(value).css({
							
							"color" : "red"
						});
						}

				}
			},{
				name : "STATUS",
				caption : "运行状态",
				dataAlign : "center",
				renderCell : function(p_item, $p_cell) {
					var value = p_item.getValue("STATUS"); // 获取 GENDER 字段的值。
					switch(value){
					case "00501":
						$p_cell.html("在运");
						break;
					case "00502":
						$p_cell.html("检修/调试");
						break;
					case "00503":
						$p_cell.html("退役");
						break;
					}
				}
			},{
				name : "RUNDATE",
				caption : "投运日期",
				dataAlign : "center"
			}

			],
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
					name : "linkedstationname",
					caption : "变电站名称",
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
				},{
					name : "yxzt",
					caption : "运行状态",
					editorType : "DropDownEditor",
					width : 180,
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				}, {
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

		
		_resetToolBar();
		me.dataGrid.toolBar.$e.insertAfter(me.dataGrid.searchBox.$e);
		_tabView.pages["query"].addControl(me.dataGrid);
		me.dataGrid.searchBox.editors["yxzt"].setValue("00501", true);
		me.dataGrid.load();
		me.statistics =new bddevicetj.views.MainViewController().getView().getDataGrid();
		_tabView.pages["statistics"].addControl(me.statistics);
		me.statistics.searchBox.editors["yxzt"].setValue("00501", true);
		me.statistics.load();
		
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
		var linkedstationname = me.dataGrid.searchBox.editors.linkedstationname.value;
		
		if(linkedstationname!=null&&linkedstationname!="")
		{
			if(linkedstationname.length>50|| !pattern1.test(linkedstationname)){
				me.dataGrid.searchBox.editors.linkedstationname.reset();
				mx.indicate("warn", "变电站名称长度不能超50且不能为特殊字符!");
			}
				
		}	
	}
	
	/**
	 * 加载按钮
	 */
	function _resetToolBar() {
		me.dataGrid.toolBar.clearItems();

		me.dataGrid.toolBar.insertItem(0, {
			name : "export",
			text : "导出",
			toolTip : "导出",
			imageUrl : "~/com.sgcc.pms.zbztjc.jcxxgl/resource/icons/daochu.png",
			onclick : me.controller._btnexport_onclick
		}, true);
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
	

	/**
	 * 获取DataGrid网格列表对象
	 */
	me.getDataGrid = function() {
		return me.dataGrid;
	}

	return me.endOfClass(arguments);
};