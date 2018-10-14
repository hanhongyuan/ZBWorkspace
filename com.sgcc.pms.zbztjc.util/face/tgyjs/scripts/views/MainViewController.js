$ns("tgyjs.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("tgyjs.views.DetailViewController");
$import("tgyjs.views.MainView");

tgyjs.views.MainViewController = function()
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
            me.view = new tgyjs.views.MainView({ controller: me });
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
        	me.view.getDataGrid().load();
        } else if(type == "bdz") {
        	me.view.getDataGrid().setFilter("LINKEDSTATION = " + selectedId); // 可以避免在点下一页的时候条件未带上
        	me.view.getDataGrid().load();
        }
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new tgyjs.views.DetailViewController();
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
//		me.view.getDetailWindow().hide();
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