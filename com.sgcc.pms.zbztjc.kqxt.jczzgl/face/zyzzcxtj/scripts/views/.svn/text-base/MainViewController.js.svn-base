$ns("zyzzcxtj.views");
$import("zyzzcxtj.views.MainView");

zyzzcxtj.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new zyzzcxtj.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        // TODO: 窗体激活时的逻辑。
	
    };
    
    return me.endOfClass(arguments);
};