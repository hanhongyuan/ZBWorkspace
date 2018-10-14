$ns("tgypz.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.datacontainers.TreeEntityContainer");
$import("mx.datacontrols.DataTree");
$import("mx.containers.VSplit");

tgypz.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
    
    var _tree = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initTree();
	    _initDataGrid();
    	_initDetailWindow();
    	_initVSplit();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/tgypz/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : tgypz.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "xh",
            loadMeta: false // 不加载元数据
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "tgypzMainViewDataGrid",
			searchBox: new mx.datacontrols.DataGridSearchBox({
			
				fields: [
				]
			}),
			
			columns:[
	        {	name: "sblx", caption: "设备类型", editorType: "DropDownEditor"	},
	        {	name: "scmc", caption: "设备名称", editorType: "TextEditor"	},
	        {	name: "monitoringtype", caption: "监测类型" , editorType: "DropDownEditor"	},
	        {	name: "xh", caption: "型号" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
	        create: me.controller._btnNew_onclick,
            remove: me.controller._btnDelete_onclick,
            onload: me.controller._complexGrid_onload
        });
        
	    //重置toolBar按钮项
        _resetToolBarItems();
    }
    
    /**
     * 重置按钮项
     */
    function _resetToolBarItems(){
    	//去除保存按钮
		_dataGrid.toolBar.removeByIndex(1);
		_dataGrid.toolBar.removeByName("save");
		//插入编辑按钮
		_dataGrid.toolBar.insertItem(2,"-",true);
		_dataGrid.toolBar.insertItem(3,{ name: "edit", text: mx.msg("EDIT"), toolTip: mx.msg("EDIT"), imageKey : "edit", onclick: me.controller._btnEdit_onclick},true);
    }

    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = tgypz.context.windowManager.create({
			reusable: true,//是否复用
			width:640,
			height:480,
			title:"表单维护"
		});
    }
    
    function _initTree() {
    	_tree = new mx.datacontrols.DataTree({
            baseUrl: tgypz.mappath("~/rest/tgypz/tree"),
            
            displayCheckBox: false, // 是否需要在树中显示选中框
            onselectionchanged: me.controller._tree_selectionchanged
        });
    }
    
    function _initVSplit() {
    	me.vsplit = new mx.containers.VSplit({
    		cols:"20%, 80%", resizable:true, borderThick:1 // ,activePanelIndex:1
		});
    	me.vsplit.addControl(_tree, 0);
    	me.vsplit.addControl(_dataGrid, 1);
        me.addControl(me.vsplit);
    }

    /**
     * 获取表单视图窗口对象
     */
    me.getDetailWindow = function(){
    	return _detailWin;
    }
    
    /**
     * 获取DataGrid网格列表对象
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
    me.getTree = function() {
    	return _tree;
    }
    
	me.endOfClass(arguments)
    return me;
};