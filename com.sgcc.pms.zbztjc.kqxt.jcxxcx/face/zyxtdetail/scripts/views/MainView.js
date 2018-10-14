$ns("zyxtdetail.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

zyxtdetail.views.MainView = function()
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
    	var restUrl = "~/rest/zyxtdevice/getsddevice";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : zyxtdevicequery.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		/* 初始化 DataGrid */
		me.dataGrid = new mx.datacontrols.DataGrid({
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
			},{
				name : "SFSS",
				caption : "是否实时",
				dataAlign : "center",
				renderCell : function(p_item, $p_cell) {
					var value = p_item.getValue("SFSS"); // 获取 GENDER 字段的值。
					if("T"==value)
						{
						$p_cell.html("是").css({
							
							"color" : "green"
						});
						}
					else 
					{
						$p_cell.html("否").css({
							
							"color" : "red"
						});
						}
					
				}
			},
			{
				name : "RUNDATE",
				caption : "投运日期",
				dataAlign : "center"
			}
			],
			// displayPrimaryKey : true,
			displayRowNumber : true,
			displayCheckBox : false,
			rowNumberColWidth : 50,
			displayColSplitLine : true,
			entityContainer : gridEntityContainer,
			enableCellTip : true
		});
		me.addControl(me.dataGrid);
    }
    
    return me.endOfClass(arguments);
};