$ns("zyzztj.views");

$import("zyzztj.views.sdzzxxTwoDetailView");

zyzztj.views.sdzzxxTwoDetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new zyzztj.views.sdzzxxTwoDetailView({controller: me});
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    };
    
    /**
     * 点击导出的方法
     */
    me._export_clicks = function(){
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "覆盖线路数查询-详细信息列表"
        });
    }
    return me.endOfClass(arguments);
};