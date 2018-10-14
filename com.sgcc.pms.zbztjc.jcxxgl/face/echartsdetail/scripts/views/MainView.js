$ns("echartsdetail.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.TabControl");
echartsdetail.views.MainView = function()
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
						{text : "输电详细信息列表",name : "sdquery",autoInit : true},
						{text : "变电详细信息列表",name : "bdquery",autoInit : true},
						{text : "电缆详细信息列表",name : "dlquery",autoInit : true}
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
        
    	var restUrl = "~/rest/chart/queryDetail";
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
					name : "LINKEDLINENAME",                                                                                            
					caption : "线路名称",
					dataAlign : "center",
					width : 150
				},
				{
					name : "LINKEDPOLENAME",                                                                                            
					caption : "杆塔名称",
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
			_tabView.pages["sdquery"].addControl(me.dataGrid);
        
    }
    
    return me.endOfClass(arguments);
};