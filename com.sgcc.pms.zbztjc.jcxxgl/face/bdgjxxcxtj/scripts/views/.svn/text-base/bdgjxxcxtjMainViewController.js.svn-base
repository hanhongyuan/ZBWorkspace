$ns("bdgjxxcxtj.views");
$import("bdgjxxcxtj.views.bdgjxxcxtjMainView");

bdgjxxcxtj.views.bdgjxxcxtjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdgjxxcxtj.views.bdgjxxcxtjMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    };
    
    return me.endOfClass(arguments);
};