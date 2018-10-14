$ns("bdzzjrqk.views");
$import("bdzzjrqk.views.MainView");

bdzzjrqk.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzzjrqk.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
	if (me.view != null && typeof(me.view.dataGrid) != "undefined")
	{
 	    me.view.dataGrid.load();
	}	
    };
    
    me._btnExport_onclick = function () {
		var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "变电装置查询"
        });
	};
    
    return me.endOfClass(arguments);
};