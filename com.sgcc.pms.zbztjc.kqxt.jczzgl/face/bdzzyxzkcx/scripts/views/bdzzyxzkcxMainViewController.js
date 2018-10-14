$ns("bdzzyxzkcx.views");
$import("bdzzyxzkcx.views.bdzzyxzkcxMainView");

bdzzyxzkcx.views.bdzzyxzkcxMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzzyxzkcx.views.bdzzyxzkcxMainView({ controller: me });
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