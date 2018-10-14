$ns("deptip.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");

deptip.views.MainView = function()
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
    	 _initDataGrid();
     	_initDetailWindow();
         me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/deptip/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : deptip.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "objId",
            loadMeta: false
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "deptipMainViewDataGrid",
			searchBox: new mx.datacontrols.DataGridSearchBox({
				
				fields: [
	            {	name: "name", caption: "网省名称", editorType: "TextEditor", width: 200	}
				],
				displayButton: true,
				itemNumOfRow: 4
			}),
			columns:[
	        { name: "objId", caption: "对象的唯一标识符", editorType: "TextEditor" },
	        { name: "DEPTNAME", caption: "网省名称", editorType: "TextEditor" },
	        { name: "DEPTID", caption: "网省ID", editorType: "TextEditor",width:"320"},
	        { name: "DEPTIP", caption: "网省接口地址", editorType: "TextEditor",width:"420" },
	        { name: "BZ", caption: "备注", editorType: "TextEditor" },
	        { name: "IPPORT", caption: "IP端口", editorType: "TextEditor",width:"320" }
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
            displayColSplitLine: true, // 表格列分割线
	        displayPrimaryKey: false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
	        create: me.controller._btnNew_onclick
        });
        
	    //重置toolBar按钮项
        _resetToolBarItems();
        me.addControl(_dataGrid);
    }
    
    /**
     * 重置按钮项
     */
    function _resetToolBarItems(){
    	var toolBar = _dataGrid.toolBar;
    	// 将工具栏移至搜索框下方
    	toolBar.$e.insertAfter(_dataGrid.searchBox.$e);
    	
    	// 去除保存按钮
		toolBar.removeByName("save");
		// 去除上移下移按钮
		toolBar.removeByIndex(4);
		toolBar.removeByName("movedown");
		toolBar.removeByName("moveup");
		// 插入编辑按钮
		toolBar.insertItem(1,{ name: "edit", text: '修改网省接口信息', toolTip: mx.msg("EDIT"), imageKey : "edit", onclick: me.controller._btnEdit_onclick},true);
		// 插入新增按钮
		toolBar.getItem('new').setText('新增网省接口信息');
    }

    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = deptip.context.windowManager.create({
			reusable: true,//是否复用
			width:640,
			height:480,
			title:"表单维护"
		});
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
    
    return me.endOfClass(arguments);
};