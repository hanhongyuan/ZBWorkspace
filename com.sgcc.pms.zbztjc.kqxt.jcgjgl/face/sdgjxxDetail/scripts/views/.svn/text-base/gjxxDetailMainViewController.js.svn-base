$ns("sdgjxxDetail.views");
$import("sdgjxxDetail.views.gjxxDetailMainView");

sdgjxxDetail.views.gjxxDetailMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdgjxxDetail.views.gjxxDetailMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    /**
     * 点击查看组态图的方法
     */
    me._queryMonitoringInfo_click = function(){
    	alert("组态图");
    }
    
    return me.endOfClass(arguments);
};