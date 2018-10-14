$ns("bddevicequery.views");
$import("bddevicequery.views.MainView");

bddevicequery.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bddevicequery.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
  	
    };
    
  
    /**
     * 查看 
     * */
    me._btnTest = function()
    {


    }
    
    return me.endOfClass(arguments);
};