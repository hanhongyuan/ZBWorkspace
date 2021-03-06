$ns("zyzztj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.containers.TabControl");


zyzztj.views.sdzzxxTwoDetailView = function()
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
    
    	var restUrl = "~/rest/zbzyzzcxtjControl/getFGXLSList";	//要执行的Controller方法
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : zyzztj.mappath(restUrl),
            loadMeta:false,
            iscID : "-1",
            primaryKey: "OBJ_ID"
        });
        
        me.dataGrid = new mx.datacontrols.DataGrid({   
			alias: "sdzzxxMainViewDataGrid",
			displayColSplitLine: true,
			columns:[
						{name: "OBJ_ID", caption: "编码" , editorType: "TextEditor" ,visible:false},
						{name: "SSDW", caption: "所属单位" , editorType: "TextEditor"},
						{name: "XTMC", caption: "所属系统" , editorType: "TextEditor"},
						{name: "YXDW", caption: "运行单位" , editorType: "TextEditor"},
						{name: "XLMC", caption: "线路名称" , editorType: "TextEditor"},
						{name: "DYDJ", caption: "电压等级" , editorType: "TextEditor"},
						{name: "XLCD", caption: "线路全长(KM)" , editorType: "TextEditor"},
						{name: "DLXZ", caption: "电流性质" , editorType: "TextEditor"},
						{name: "JSFS", caption: "架设方式" , editorType: "TextEditor"},
						{name: "ZCXZ", caption: "资产性质" , editorType: "TextEditor"},
						{name: "TYRQ", caption: "投运日期" , editorType: "TextEditor"}
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