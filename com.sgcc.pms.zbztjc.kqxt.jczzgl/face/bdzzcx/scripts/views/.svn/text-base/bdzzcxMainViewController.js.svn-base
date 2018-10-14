$ns("bdzzcx.views");

$import("bdzzcx.views.bdzzcxMainView");
$import("bdzzcx.views.bdzzcxDetailViewController");

bdzzcx.views.bdzzcxMainViewController = function()
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
            me.view = new bdzzcx.views.bdzzcxMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
	   me.view.getDataGrid().load();
    };
	    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new bdzzcx.views.bdzzcxDetailViewController();
            _detailView = mvc.getView();
        }
    	return _detailView;
    }

    me._btnCx_onclick = function(){
    	var _seSearchBox = me.view.getSearchBox();
    	var _dataGrid = me.view.getDataGrid();
    	var params = _seSearchBox.getSearchParam();
        _dataGrid.setFilter(params);
		_dataGrid.load();
    };
    
    me._btnlookup_click = function(){
    	_detailView = me._getDetailFromView();
    	_showDetailFormView(_detailView,"查看详情");
    }
    
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
    me.endOfClass(arguments);
    return me;
};