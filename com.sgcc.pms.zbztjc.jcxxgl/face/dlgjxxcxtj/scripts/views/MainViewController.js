$ns("dlgjxxcxtj.views");
$import("dlgjxxcxtj.views.MainView");

dlgjxxcxtj.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dlgjxxcxtj.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    
    return me.endOfClass(arguments);
};