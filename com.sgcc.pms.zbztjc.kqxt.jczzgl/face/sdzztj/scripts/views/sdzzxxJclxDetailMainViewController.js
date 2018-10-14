$ns("sdzztj.views");
$import("sdzztj.views.sdzzxxJclxDetailMainView");
$import("sdzztj.views.sdzztjByDydjMainViewController");

sdzztj.views.sdzzxxJclxDetailMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdzztj.views.sdzzxxJclxDetailMainView({ controller: me });
        }
        return me.view;
    };
    
    
    
    me._onactivate = function(e)
    {
	        // TODO: 窗体激活时的逻辑。
		if (me.view != null && typeof(me.view.dataGrid) != "undefined")
		{
	 	    me.view.dataGrid.load();
		}	
    };
    
    /**
     * 点击查询的方法
     */
    me._queryBtn_click = function(){
    	me._searchBox = me.view.getSearchBox();				//获取SearchBox
    	var params = me._searchBox.getSearchParam();
		me.view.getDataGrid().setFilter(params);
		me.view.getDataGrid().load();
    }
    /**
     * 点击导出的方法
     */
    me._export_click = function(){
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "线路数查询-详细信息列表"
        });
    }
    /**
     * 点击查看组态图的方法
     */
    me._queryMonitoringInfo_click = function(){
    	//alert("组态图");
    }
    
    return me.endOfClass(arguments);
};