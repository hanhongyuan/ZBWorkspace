$ns("dlechartsdetail.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

dlechartsdetail.views.MainView = function()
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
    	var restUrl = "~/rest/chart/querydlDetail";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : generaldetail.mappath(restUrl),
			loadMeta : false,
			iscID : "-1" // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			
		});
			me.dataGrid = new mx.datacontrols.DataGrid({
				// alias : "montringgrid",
				// 设置显示列。
				columns : [ 
				{
					name : "WSDEPMC",                                                                                            
					caption : "所属单位",
					dataAlign : "center",
					width : 150
				},
				{
					name : "LINKEDCABLEANDCHANNELNAME",                                                                                            
					caption : "电缆/通道名称",
					dataAlign : "center",
					width : 150
				},
				{
					name : "LINKEDEQUIPMENTNAME",                                                                                            
					caption : "一次设备名称",
					dataAlign : "center",
					width : 150
				},{
					name : "DEVICENAME",                                                                                            
					caption : "装置名称",
					dataAlign : "center",
					width : 150
				},{name : "ISRT",caption : "是否实时",dataAlign : "center",width : 80,
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
					name : "DEVICECATEGORY_DISPLAY",                                                                                            
					caption : "监测类型",
					dataAlign : "center",
					width : 150
				},
				{
					name : "DEVICEVOLTAGE",                                                                                            
					caption : "电压等级",
					dataAlign : "center",
					width : 150
				},{
					name : "MANUFACTURER",                                                                                            
					caption : "生产厂家",
					dataAlign : "center",
					width : 150
				},{
					name : "RUNDATE",                                                                                            
					caption : "投运日期",
					dataAlign : "center",
					width : 150
				}
				
				],
    		entityContainer : gridEntityContainer,
    		displayRowNumber : true,
    		displayPrimaryKey : false,
    		displayColSplitLine : true,
    		allowEditing : false,
    		rowNumberColWidth : 40,
    		pageSize : 18
    		});
			
        me.addControl(me.dataGrid);
 
    }
    //获取grid
    me.getDataGrid = function()
    {
    	return me.dataGrid;
    }
    
    return me.endOfClass(arguments);
};