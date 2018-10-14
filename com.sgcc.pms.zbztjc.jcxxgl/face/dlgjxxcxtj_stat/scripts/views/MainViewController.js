$ns("dlgjxxcxtj_stat.views");
$import("dlgjxxcxtj_stat.views.MainView");

dlgjxxcxtj_stat.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dlgjxxcxtj_stat.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    
    return me.endOfClass(arguments);
};