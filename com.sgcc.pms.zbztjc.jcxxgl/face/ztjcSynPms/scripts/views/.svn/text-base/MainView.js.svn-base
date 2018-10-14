$ns("ztjcSynPms.views");

$import("mx.controls.ToolBar");

ztjcSynPms.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    base.init = me.init;
    me.toolBar = null;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	me.toolBar = new mx.controls.ToolBar({
            width: "100%",
            items:[
                   {name: "syn", text:"初始同步数据按钮", imageKey : "save", onclick: me.controller.synServiceAll_click},
                   {name: "synIncreament", text:"增量同步数据按钮", imageKey : "save", onclick: me.controller.synServiceIncrement_click}
	    	]
        });
		me.addControl(me.toolBar);
        me.on("activate", me.controller._onactivate);
    }
    
    return me.endOfClass(arguments);
};