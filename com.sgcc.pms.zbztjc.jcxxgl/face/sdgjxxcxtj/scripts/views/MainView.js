$ns("sdgjxxcxtj.views");

$import("sdgjxxcxtj_query.views.MainViewController");
$import("sdgjxxcxtj_stat.views.MainViewController");

sdgjxxcxtj.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.objID = null;
    
    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
        _initControl();
        _initTabControl();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initControl(){
    	me.queryController = sdgjxxcxtj_query.views.MainViewController();		//获取按电压等级统计的Controller
    	me.statController = sdgjxxcxtj_stat.views.MainViewController();		//获取按监测类型统计的Controller
    	
    	me.queryView = me.queryController.getView();
    	me.statView = me.statController.getView();
    }
    
    function _initTabControl(){
    	_tabcontrol = new mx.containers.TabControl({
    		   pages:[
    		       { text: "查询", name: "query", autoInit: true },
    			   { text: "统计", name: "stat", autoInit: true }
    		   ]
        	});
      	
      	_tabcontrol.pages[0].addControl(me.queryView);
      	_tabcontrol.pages[1].addControl(me.statView);
      	
      	me.addControl(_tabcontrol);
    }
    
    return me.endOfClass(arguments);
};