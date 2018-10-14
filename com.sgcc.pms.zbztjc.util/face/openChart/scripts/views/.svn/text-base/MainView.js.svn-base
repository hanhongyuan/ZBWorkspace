$ns("openChart.views");

$import("mx.editors.LinkEditor");
$import("mx.windows.WindowManager");
$import("mx.datacontrols.ComplexGrid");

openChart.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    base.init = me.init;
    me.init = function()
    {
        base.init();

        me.on("activate", me.controller._onactivate);
    };
    
    
    
    return me.endOfClass(arguments);
};