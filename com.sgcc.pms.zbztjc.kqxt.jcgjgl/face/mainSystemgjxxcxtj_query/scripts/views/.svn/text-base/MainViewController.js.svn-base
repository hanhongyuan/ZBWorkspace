$ns("mainSystemgjxxcxtj_query.views");
$import("mainSystemgjxxcxtj_query.views.MainView");

mainSystemgjxxcxtj_query.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new mainSystemgjxxcxtj_query.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    
    /**
     * 查询操作
     */
    me._btnQuery_click = function(){
    	var searchBox = me.view.getSearchBox();
		var str = searchBox.getSearchParam();
		
		me.view.getDataGrid().setFilter(str);
		me.view.getDataGrid().load();
    }
    
    /**
     * 导出操作
     */
    me._btnExport_click = function(){
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "变电告警信息查询数据"
        });
    }
    
    return me.endOfClass(arguments);
};