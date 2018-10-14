$ns("bdzzcx.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


bdzzcx.views.bdzzcxDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
	 me.bdz = null;
    /**
      * 表单对象
     */
    var _dataGrid = null;

    

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

    me.load = function()
    {
    	//加载表单信息
    	_dataGrid.load();
    }

    function _initControls()
    {
	    _initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
	

	
  function _initDataGrid()
    {
        var restUrl = "~/rest/cmstphysicsdevice/bdzxx";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : bdzzcx.mappath(restUrl),
            loadMeta : false,
			iscID : "-1" // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
        });
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "bdzbdzMainViewDataGrid",
			columns:[
	        {	name: "LINKEDDEPTNAME", caption: "所属地市名称" , editorType: "TextEditor" }, 
	        {	name: "LINKEDSTATIONNAME", caption: "所属变电站" , editorType: "TextEditor"	},
	        {	name: "EQUIPMENTTYPE", caption: "所属一次设备名称" , editorType: "TextEditor"	},
	        {	name: "LINKEDEQUIPMENTNAME", caption: "一次设备类型" , editorType: "TextEditor" },
	        {	name: "DEVICENAME", caption: "装置名称" , editorType: "TextEditor"	},
	        {	name: "DEVICECODE", caption: "装置编码" , editorType: "TextEditor"	},
	        {	name: "LINKEDCAC_DISPLAY", caption: "所属CAC" , editorType: "TextEditor" },
            {	name: "RELEASEDATE", caption: "出厂日期" , editorType: "TextEditor"} ,
            {	name: "RUNDATE", caption: "投运日期" , editorType: "DropDownEditor" },
            {	name: "DEVICECATEGORY_DISPLAY", caption: "装置类型" , editorType: "TextEditor"	},
            {	name: "MANUFACTURER_DISPLAY", caption: "生产厂家" , editorType: "TextEditor" },
            {	name: "VOLTAGEGRADE_DISPLAY", caption: "电压等级" , editorType: "TextEditor" },
            {	name: "STATUS_DISPLAY", caption: "运行状态" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox:true,
            allowEditing:false, //列表默认不可编辑
	        pageSize : 10,
            entityContainer: gridEntityContainer
        });
        item_click = me.controller._item_click;
        me.addControl(_dataGrid);
    }

    /**
     * 获取表单对象
     */
    me.getDataGrid = function(){
		return _dataGrid;
    }
    
	me.endOfClass(arguments)
    return me;
};