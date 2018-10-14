$ns("xldzgl.views");
$import("xldzgl.views.MainView");

xldzgl.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new xldzgl.views.MainView({ controller: me });
        }
        return me.view;
    };

    
    return me.endOfClass(arguments);
};