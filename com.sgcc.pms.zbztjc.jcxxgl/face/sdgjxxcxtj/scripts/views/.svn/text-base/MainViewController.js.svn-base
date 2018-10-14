$ns("sdgjxxcxtj.views");
$import("sdgjxxcxtj.views.MainView");

sdgjxxcxtj.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdgjxxcxtj.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    };
    
    
    return me.endOfClass(arguments);
};