$ns("changekhstatus.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.ComplexGrid");

changekhstatus.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;
    
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        
        _initControls();
    };
    
    function _initControls()
    {
    	var restUrl = "~/rest/khztControl/getsdstatus";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : changekhstatus.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		/* 初始化 DataGrid */
		me.dataGrid = new mx.datacontrols.ComplexGrid({
			// alias : "montringgrid",
			// 设置显示列。
			columns : [ {
				name : "OBJ_ID",
				caption : "唯一标识",
				dataAlign : "center"
			},
			{
				name : "PROVINCE_NAME",
				caption : "所属单位",
				dataAlign : "center"
			},
			 {
				name : "LINKEDDEPTNAME",
				caption : "所属地市",
				dataAlign : "center"
			}, {
				name : "DEVICENAME",
				caption : "装置名称",
				dataAlign : "center"
			}, {
				name : "LINKEDDEVICE",
				caption : "装置编码",
				dataAlign : "center"
			}, {
				name : "TYPENAME",
				caption : "监测类型",
				readOnly : true,
				dataAlign : "center"
			}, {
				name : "MONITOREDCOMPONENT",
				caption : "被监测单元",
				readOnly : true,
				dataAlign : "center"
			},{
				name : "CHANGECAUSE",
				caption : "变更原因"
			},{
				name : "APPLYTIME",
				caption : "申请时间",
				dataAlign : "center"
			}, {
				name : "APPLICANT",
				caption : "申请人",
				dataAlign : "center"
			},{
				name : "AUDITSTATUS",
				caption : "审核状态",
				dataAlign : "center",
				renderCell : function(p_item, $p_cell) {
					var value = p_item.getValue("AUDITSTATUS");
					var OBJ_ID = p_item.getValue("OBJ_ID");
					if("0"==value)
						{
						$p_cell.html("未审核").css({
							"text-decoration" : "underline",
							"color" : "blue",
							"cursor" : "pointer"
						}).on("click", function() {
							me.controller._btnAudi(OBJ_ID,value);
						});
						}
					else if("1"==value)
						{
						$p_cell.html("同意").css({
							"text-decoration" : "underline",
							"color" : "black",
							"cursor" : "pointer"
						}).on("click", function() {
							me.controller._btnAudi(OBJ_ID,value);
						});
						}
					else if("2"==value)
						{
						$p_cell.html("不同意").css({
							"text-decoration" : "underline",
							"color" : "red",
							"cursor" : "pointer"
						}).on("click", function() {
							me.controller._btnAudi(OBJ_ID,value);
						});
						}
					
				}
			},  {
				name : "AUDITVIEW",
				caption : "审核意见"
			}, {
				name : "AUDITTIME",
				caption : "审核时间",
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
					name : "auditStatus",
					caption : "审核状态",
					editorType : "DropDownEditor",
					width : 180,
					displayMember: "name",
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--",
				    items: [
				            { name: "未审核", value: "0" },
				            { name: "同意", value: "1" },
				            { name: "不同意", value: "2" }
				        ]
				}, {
					name : "srundate",
					caption : "申请日期",
					editorType : "DateTimeEditor",
					width : 180,
					onchanged:_searchParamChanged,
					formatString: "yyyy-MM-dd",
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				}, {
					name : "erundate",
					caption : "至",
					editorType : "DateTimeEditor",
					width : 180,
					onchanged:_searchParamChanged,
					formatString: "yyyy-MM-dd",
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				}
				],
				itemNumOfRow : 4
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
        me.addControl(me.dataGrid);
        me.dataGrid.load();
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
			onclick : me.controller._export
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
	
	 //获取grid
    me.getDataGrid = function()
    {
    	return me.dataGrid;
    }
	
    return me.endOfClass(arguments);
};