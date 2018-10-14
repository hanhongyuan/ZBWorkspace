$ns("manufacturerZb.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");


manufacturerZb.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;

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
        var restUrl = "~/rest/cmstmanufacturerzb/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : manufacturerZb.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "objId",
            loadMeta: false
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "manufacturerZbMainViewDataGrid",
			searchBox: new mx.datacontrols.DataGridSearchBox({
			
				fields: [
	            {	name: "name", caption: "厂家名称", editorType: "TextEditor", width: 200	},
	            {	name: "displayName", caption: "厂家显示名称", editorType: "TextEditor", width: 200	},
	            {	name: "creatProvince", caption: "提交网省", editorType: "TextEditor", width: 200	},
		        {	name: "code", caption: "厂家编码", editorType: "TextEditor", width: 200	}
				],
				displayButton: true,
				itemNumOfRow: 4
			}),
			
			columns:[
	        { name: "objId", caption: "对象的唯一标识符", editorType: "TextEditor" },
	        { name: "name", caption: "厂家名称", editorType: "TextEditor" },
	        { name: "contact", caption: "联系方式", editorType: "TextEditor" },
	        { name: "address", caption: "厂址", editorType: "TextEditor" },
	        { name: "displayName", caption: "厂家显示名称", editorType: "TextEditor" },
	        { name: "everName", caption: "厂家曾用名", editorType: "TextEditor" },
	        { name: "creatTime", caption: "创建时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd" },
	        { name: "lastmodifyTime", caption: "最后一次修改时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd" },
	        { name: "status", caption: "厂商状态是否可用", editorType: "DropDownEditor",
		      	renderCell: function(s, p) {
  		      		var status = s.values.status;
		      		if("Y" == status){
		      			p.html("是");
		      			p.css("color", "blue");
		      		} else if("N" == status) {
		      			p.html("否");
		      			p.css("color", "red");
		      		}
  		      	} },
	        { name: "creatProvince", caption: "提交网省", editorType: "TextEditor" },
	        { name: "code", caption: "厂家编码", editorType: "TextEditor" }
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
            displayColSplitLine: true, // 表格列分割线
	        displayPrimaryKey: false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
	        create: me.controller._btnNew_onclick,
            remove: me.controller._btnDelete_onclick
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
		toolBar.insertItem(1,{ name: "edit", text: '修改厂家', toolTip: mx.msg("EDIT"), imageKey : "edit", onclick: me.controller._btnEdit_onclick},true);
		// 插入导出按钮
//		toolBar.insertItem(5,"-",true);
//		toolBar.insertItem(6,{ name: "excel", text: '导出', onclick: me.controller._btnExcel_onclick},true);
		
		toolBar.getItem('new').setText('新增厂家');
    }

    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = manufacturerZb.context.windowManager.create({
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
    
	me.endOfClass(arguments)
    return me;
};