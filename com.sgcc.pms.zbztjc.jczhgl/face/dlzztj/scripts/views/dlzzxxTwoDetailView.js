$ns("dlzztj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.containers.TabControl");


dlzztj.views.dlzzxxTwoDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
      * 表单对象
     */
    var _dataGrid = null;
	    /**
     * 表单对象id
     */
    me.objID = null;
    

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
	
    function _initControls()
    {	
	    //_initToolBar();
        _initDataGrid();
		me.on("activate", me.controller._onactivate);
    }
	

    

	
    function _initToolBar(){
    	me.toolBar = new mx.controls.ToolBar({
            alias:"sdzzxxTowDetailViewToolBar",
            width: "100%",
           	items: [
         			{ name: "export", text:"导出", toolTip:"导出",imageKey:"export", onclick:me._export_clicks
         				}
					]
        });
        me.addControl(me.toolBar);
    }

	

    
    function _initDataGrid(){
    
    	var restUrl = "~/rest/zbdlzzcxtjController/getFGBDZList";	//要执行的Controller方法
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : dlzztj.mappath(restUrl),
            loadMeta:false,
            iscID : "-1",
            primaryKey: "LINKEDLINE"
        });
        
        me.dataGrid = new mx.datacontrols.DataGrid({   
			alias: "dlzzxxMainViewDataGrid",
			displayColSplitLine: true,
			columns:[
						{name: "LINKEDCABLEANDCHANNEL", caption: "变电站编码" , editorType: "TextEditor" ,visible:false},
						{name: "PROVINCE_NAME", caption: "所属单位" , editorType: "TextEditor"},
						{name: "LINKEDCABLEANDCHANNELNAME", caption: "电力电缆及通道名称" , editorType: "TextEditor"},
						{name: "MC", caption: "电压等级" , editorType: "TextEditor"}
					],
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 10,
            entityContainer: gridEntityContainer
        });
        me.addControl(me.dataGrid);
    }
	 
	    me.getDataGrid = function(){
			return me.dataGrid;
	    }
		
	    /**
	     * 获取工具条
	     */
	    me.getToolBar = function(){
			return me.toolBar;
	    }
	    
	me.endOfClass(arguments)
    return me;
};