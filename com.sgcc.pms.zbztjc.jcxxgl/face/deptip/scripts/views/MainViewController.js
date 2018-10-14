$ns("deptip.views");
$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("deptip.views.DetailViewController");
$import("deptip.views.MainView");

deptip.views.MainViewController = function()
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
	            me.view = new deptip.views.MainView({ controller: me });
	        }
	        return me.view;
	    };

	    /**
	     * 获取表单视图对象
	     */
	    me._getDetailFromView = function(){
	    	if (_detailView == null)
	        {
	            var mvc = new deptip.views.DetailViewController();
	            _detailView = mvc.getView();

				_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
	            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
	        }
	    	return _detailView;
	    }



		// 表单视图保存后刷新列表数据。
		me._refreshDataGrid = function(e)
		{
			me.view.getDetailWindow().hide();
	    	me.view.getDataGrid().load();
		}

	    me._onactivate = function(e)
	    {
	        
		    me.view.getDataGrid().load();

	    };
	    
	    /**
	     * 新增网省接口信息
	     */
	    me._btnNew_onclick = function()
	    {
	        //此处新增的数据需要将服务端返回的 id 值设置到 GridItem 上。
		    var _detailView = me._getDetailFromView();
		    //设置对象id
	        _detailView.objID = null;
	        _showDetailFormView(_detailView,"表单填写");
	    };
	 
	    
	    /**
	     * 修改接口信息
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
	    	_detailView.objID = v_dataGrid.getCheckedIDs()[0];
		    //显示详细信息页面
	    	_showDetailFormView(_detailView,"表单编辑");
	    };
 
	    /**
	     * 显示表单视图
	     * @param p_view : 需要显示的视图对象
	     * @param p_title : 对话框的标题
	     */
	    function _showDetailFormView(p_view,p_title){
	    	var win = me.view.getDetailWindow();
	    	if(typeof p_view != "undefined"){
	    		p_view.load();
	    		//设置显示视图、标题信息
	    		win.setView(p_view);
	    		win.setTitle(p_title ? p_title : win.title);
	    	}
	    	win.showDialog();
	    }
    
    return me.endOfClass(arguments);
};