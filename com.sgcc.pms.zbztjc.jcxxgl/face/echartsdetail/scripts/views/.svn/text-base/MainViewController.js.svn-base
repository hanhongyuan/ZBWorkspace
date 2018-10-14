$ns("echartsdetail.views");
$import("echartsdetail.views.MainView");

echartsdetail.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new echartsdetail.views.MainView({ controller: me });
        }
        return me.view;
    };
    me._selection_changed = function(e)
    {
    	if("bdquery"==e.page.name)
    	{
    		_tabView.pages["bdquery"].clear();
    		var filter = me.view.dataGrid.filter;
    		me.bdechartsdetail =new bdechartsdetail.views.MainViewController().getView().getDataGrid();
    		me.bdechartsdetail.setFilter(filter);
    		_tabView.pages["bdquery"].addControl(me.bdechartsdetail);
    		me.bdechartsdetail.load();
    		
    	}
    	if("dlquery"==e.page.name)
    	{
    		_tabView.pages["dlquery"].clear();
    		var filter = me.view.dataGrid.filter;
    		
    		me.dlechartsdetail =new dlechartsdetail.views.MainViewController().getView().getDataGrid();
    		me.dlechartsdetail.setFilter(filter);
    		_tabView.pages["dlquery"].addControl(me.dlechartsdetail);
    		me.dlechartsdetail.load();
    		
    	}
    }
   
    
    return me.endOfClass(arguments);
};