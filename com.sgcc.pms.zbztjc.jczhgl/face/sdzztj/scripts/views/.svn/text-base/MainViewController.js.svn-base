$ns("sdzztj.views");
$import("sdzztj.views.MainView");

sdzztj.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdzztj.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    
    return me.endOfClass(arguments);
};