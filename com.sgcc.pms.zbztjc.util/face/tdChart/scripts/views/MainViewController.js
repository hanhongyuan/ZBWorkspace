$ns("tdChart.views");
$import("tdChart.views.MainView");

tdChart.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new tdChart.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    return me.endOfClass(arguments);
};