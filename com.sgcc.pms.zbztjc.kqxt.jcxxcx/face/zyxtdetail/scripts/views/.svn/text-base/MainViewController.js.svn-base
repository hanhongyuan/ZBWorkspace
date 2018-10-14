$ns("zyxtdetail.views");
$import("zyxtdetail.views.MainView");

zyxtdetail.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new zyxtdetail.views.MainView({ controller: me });
        }
        return me.view;
    };
    
   
    
    return me.endOfClass(arguments);
};