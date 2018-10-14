$ns("dltjdetail.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

dltjdetail.views.MainView = function()
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
    	var restUrl = "~/rest/dldevicequery/getdldeviceifo";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : dldevicequery.mappath(restUrl),
			loadMeta : false,
			iscID : "-1"
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
				name : "LINKEDCABLEANDCHANNELNAME",
				caption : "电力电缆及通道名称",
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