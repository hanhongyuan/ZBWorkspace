$ns("sdtjdetail.views");
$import("sdtjdetail.views.MainView");

sdtjdetail.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdtjdetail.views.MainView({ controller: me });
        }
        return me.view;
    };
    
   
    
    return me.endOfClass(arguments);
};