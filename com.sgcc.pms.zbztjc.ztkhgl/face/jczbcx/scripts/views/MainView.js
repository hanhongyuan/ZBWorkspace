$ns("jczbcx.views");

$import("mx.datacontrols.GroupHeaderGrid");

jczbcx.views.MainView = function()
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
    	// 初始化工具栏
		_initToolBar();
    	_initDataGrid();

        me.on("activate", me.controller._onactivate);
    }
    
    /**
	 * 加载按钮
	 */
    function _initToolBar()
    {
    	_toolBar = new mx.controls.ToolBar({
            width: "100%",
            items: [
                    { name: "save", text:"导出", toolTip:"导出",imageKey:"export", onclick: me.controller._ExportBtn_click
                    	}]
        });
         me.addControl(_toolBar);
    }
    
    
    /*
     * 初始化并加装树视图。
     */
    function _initDataGrid() {
		var restUrl = "~/rest/jczbcxControl/query";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : jczbcx.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		me.dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"jczbcxDataGrid",
			
			columns: [
			    		{name: "OBJ_ID", caption: "编码" ,visible : false},
			    		{name: "DWMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
			    		{name: "group1",caption: "输电",
			    			columns: [
			    			     
											{ name: "SDJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SDLXX", caption: "数据连续性", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SDYXX", caption: "数据有效性", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SDCLL", caption: "告警处理率", dataAlign:"center",editorType: "TextEditor" }
							]
			    		},
			    		{
			        		name: "group2",
			        		caption: "变电",
			        		columns: [
			        		
											{ name: "BDJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
											{ name: "BDLXX", caption: "数据连续性", dataAlign:"center",editorType: "TextEditor" },
											{ name: "BDYXX", caption: "数据有效性", dataAlign:"center",editorType: "TextEditor" },
											{ name: "BDCLL", caption: "告警处理率", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},
			        	{ name: "BZKYYL", caption: "厂家标准库应用率", dataAlign:"center",editorType: "TextEditor" },
			        	{ name: "TJSJ", caption: "统计时间", dataAlign:"center",editorType: "TextEditor" }
			        	],
			
			// 设置查询条件。
			entityContainer : gridEntityContainer,
			displayCheckBox : false,
			displayRowNumber : true,
			rowNumberColWidth : 30,
			allowPaging : false,
			displayPrimaryKey : false,
			allowEditing : false			
		});
		me.addControl(me.dataGrid);
		me.dataGrid.load();
	}
    /**
     * 获取DataGrid
     */
    me.getDataGrid = function(){
    	return me.dataGrid;
    }
    return me.endOfClass(arguments);
};