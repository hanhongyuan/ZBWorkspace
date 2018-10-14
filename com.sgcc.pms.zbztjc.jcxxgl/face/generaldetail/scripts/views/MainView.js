$ns("generaldetail.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

generaldetail.views.MainView = function()
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
    	var restUrl = "~/rest/devicegeneral/getdetail";
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
					name : "LINKEDDEPTNAME",
					caption : "所属地市",
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
        
        me.on("activate", me.controller._onactivate);
    }
    
    return me.endOfClass(arguments);
};