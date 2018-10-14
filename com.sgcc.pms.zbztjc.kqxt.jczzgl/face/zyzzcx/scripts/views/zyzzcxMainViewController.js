$ns("zyzzcx.views");
$import("zyzzcx.views.zyzzcxMainView");

zyzzcx.views.zyzzcxMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new zyzzcx.views.zyzzcxMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
     
    };
    
    me._btnCx_onclick = function(){
    	var _seSearchBox = me.view.getSearchBox();
    	var _dataGrid = me.view.getDataGrid();
    	var params = _seSearchBox.getSearchParam();
        _dataGrid.setFilter(params);
		_dataGrid.load();
    };
    
    return me.endOfClass(arguments);
};