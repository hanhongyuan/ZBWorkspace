$ns("sdzzjrqk.views");
$import("sdzzjrqk.views.MainView");

sdzzjrqk.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdzzjrqk.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        //窗体激活时的逻辑
	if (me.view != null && typeof(me.view.dataGrid) != "undefined")
	{
 	    me.view.dataGrid.load();
	}	
    };
    
    
    /*
     * 导出
     */
    me._btnExport_onclick = function () {
		var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "输电装置接入情况"
        });
	};
        
    return me.endOfClass(arguments);
};