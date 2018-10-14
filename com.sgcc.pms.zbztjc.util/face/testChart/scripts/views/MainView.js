$ns("testChart.views");

$import("mx.datacontainers.TreeEntityContainer");
$import("mx.datacontrols.DataTree");
$import("mx.editors.LinkEditor");

testChart.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        
        _initControls();
    };
    
    function _initControls()
    {
    	var linkEditor = new mx.editors.LinkEditor({
	    	"type" : "text",//指定链接的类型。
	    	"width" : "90px",//指定控件宽度。
	    	"imageKey" : "1",//指定图标名称。
	    	"text" : "打开组态图"
    	});
    	linkEditor.on("click", me.controller._btnTest);

        me.addControl(linkEditor);
        me.on("activate", me.controller._onactivate);
    }
    
    return me.endOfClass(arguments);
};