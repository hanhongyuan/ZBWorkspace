$ns("bdtjdetail.views");
$import("bdtjdetail.views.MainView");

bdtjdetail.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdtjdetail.views.MainView({ controller: me });
        }
        return me.view;
    };
    

    return me.endOfClass(arguments);
};