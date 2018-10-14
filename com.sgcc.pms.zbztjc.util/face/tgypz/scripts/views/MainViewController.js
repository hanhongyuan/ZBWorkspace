$ns("tgypz.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("tgypz.views.DetailViewController");
$import("tgypz.views.MainView");

tgypz.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
	
    /**
     * 表单视图对象
     */
    var _detailView = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new tgypz.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    // 联动
    me._tree_selectionchanged = function(e)
    {
    	var selectedId = e.selection.id; // 节点id
        var type = e.selection.itemType; // 节点类型
        if(type == "root") {
        	me.view.getDataGrid().setFilter(null); // 清空条件
        	me.view.getDataGrid().load(function() {
        		me.view.getDataGrid().toolBar.getItem("new").setEnabled(false);
        	});
        } else if(type == "bdz") {
        	me.view.getDataGrid().setFilter("LINKEDSTATION = '" + selectedId + "'"); // 可以避免在点下一页的时候条件未带上
        	me.view.getDataGrid().load();
        }
    };
    
    // 新建及删除后也会触发
    me._complexGrid_onload = function(e) {
    	if($notEmpty(e.target.filter)) {
    		// 当导航树点到变电站时
        	var data = e.target.entityContainer.data;
    		var xh = data[data.length-1].xh;
    		
    		xh = xh.replace(/\d+/, Number(xh.match(/\d+/)[0]) +1);
    		me._getDetailFromView().controller.data.xh = xh; // 每次都重置 保持主键的最新及唯一性
    	}
    }

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new tgypz.views.DetailViewController();
            _detailView = mvc.getView();

			_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
        }
    	return _detailView;
    }

	// 加载列表数据。
	me._loadDataGrid = function(e)
	{
    	me.view.getDataGrid().load();
	}

	// 表单视图保存后刷新列表数据。
	me._refreshDataGrid = function(e)
	{
		me.view.getDetailWindow().hide();
    	me.view.getDataGrid().load();
	}

    me._onactivate = function(e)
    {
        // iscID 是界面的统一权限功能编码，默认值为 "-1" ，表示不应用权限设置。
    	var permission = new mx.permissions.Permission({iscID:"-1"});
        // 根据“统一权限”设置组件的可见和只读等属性
    	// me.view 是当前页面的view页面，可根据需要传入其他需要权限控制页面元素
        mx.permissions.PermissionAgent.setPermission(permission, me.view);
        
	    // 加载树
	    me.view.getTree().load();
        //加载数据
	    me.view.getDataGrid().load();
    };
    
    /**
     * 新增
     */
    me._btnNew_onclick = function()
    {
    	var tree = me.view.getTree();
    	var linkedStation = tree.selection.id;
    	
	    var _detailView = me._getDetailFromView();
	    _detailView.controller.data.linkedStation = linkedStation;
        // TODO： 此处新增的数据需要将服务端返回的 id 值设置到 GridItem 上。
	    //设置对象id
        _detailView.objID = "New: "+ linkedStation;
        _showDetailFormView(_detailView, "表单填写");
    };
    
    /**
     * 删除
     */
    me._btnDelete_onclick = function()
    {
	    var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.getCheckedIDs().length == 0)
        {
	     mx.indicate("info", "请至少勾选一条待删除记录。");
             return;
        }
		if (confirm("您确认删除数据吗？"))
		{
		     v_dataGrid.removeItems(v_dataGrid.getCheckedIDs());
		}
    };
    
    /**
     * 保存
     */
    me._btnSave_onclick = function()
    {
        me.view.getDataGrid().entityContainer.save();
    };
    
    /**
     * 编辑
     */
    me._btnEdit_onclick = function()
    {
        var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.getCheckedIDs().length == 0)
        {
             mx.indicate("info", "请勾选一条待编辑记录。");
             return;
        }
        //多选框勾选记录，判断是否选择多条
    	if(v_dataGrid.getCheckedIDs().length > 1)
    	{
    	       mx.indicate("info", "选定的记录条数不能超过一条。");
    	       return;
    	}

    	var _detailView = me._getDetailFromView();
    	var checkedItem = v_dataGrid.getCheckedItems()[0];
    	_detailView.controller.data.monitoringtype = checkedItem.values.monitoringtype;
    	_detailView.controller.data.scmc = checkedItem.values.scmc;
    	
    	_detailView.objID = v_dataGrid.getCheckedIDs()[0];
	    //显示详细信息页面
    	_showDetailFormView(_detailView, "表单编辑");
    };

    
    /**
     * 显示表单视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showDetailFormView(p_view, p_title){
    	var win = me.view.getDetailWindow();
    	if(typeof p_view != "undefined"){
    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
    me.endOfClass(arguments);
    return me;
};