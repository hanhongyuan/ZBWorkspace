$ns("dlechartsdetail.views");
$import("dlechartsdetail.views.MainView");

dlechartsdetail.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dlechartsdetail.views.MainView({ controller: me });
        }
        return me.view;
    };
    

    return me.endOfClass(arguments);
};