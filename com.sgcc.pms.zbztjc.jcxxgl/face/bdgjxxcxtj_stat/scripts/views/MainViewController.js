$ns("bdgjxxcxtj_stat.views");
$import("bdgjxxcxtj_stat.views.MainView");

bdgjxxcxtj_stat.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdgjxxcxtj_stat.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    };
    
    return me.endOfClass(arguments);
};