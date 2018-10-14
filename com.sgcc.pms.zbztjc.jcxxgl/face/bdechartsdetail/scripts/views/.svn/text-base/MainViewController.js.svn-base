$ns("bdechartsdetail.views");
$import("bdechartsdetail.views.MainView");

bdechartsdetail.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdechartsdetail.views.MainView({ controller: me });
        }
        return me.view;
    };
    
   
    
    return me.endOfClass(arguments);
};