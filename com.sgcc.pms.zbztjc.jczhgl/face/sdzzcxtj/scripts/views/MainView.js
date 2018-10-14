$ns("sdzzcxtj.views");

$import("sdzzcx.views.sdzzcxMainViewController");
$import("sdzztj.views.MainViewController");

sdzzcxtj.views.MainView = function()
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
    	_initControl();
    	_initTabControl(); 
        me.on("activate", me.controller._onactivate);
    }
    
    function _initControl(){
    	me.queryController = sdzzcx.views.sdzzcxMainViewController();
    	me.statController = sdzztj.views.MainViewController();
    	
    	me.queryView = me.queryController.getView();
    	me.statView = me.statController.getView();
    }
    
    function _initTabControl(){
    	_tabcontrol = new mx.containers.TabControl({
    		   pages:[
    		       { text: "物理装置查询", name: "query", autoInit: true },
    			   { text: "逻辑装置统计", name: "stat", autoInit: true }
    		   ]
        	});
      	
      	_tabcontrol.pages[0].addControl(me.queryView);
      	_tabcontrol.pages[1].addControl(me.statView);
      	//_tabcontrol.pages[1].resizeTo(document.body.offsetWidth -10, document.body.scrollHeight - 50 );
      	_tabcontrol.pages[1].setCss({
      		'overflow-x': 'hidden',
      		'overflow-y': 'auto'
      	});
      	me.addControl(_tabcontrol);
    }
    return me.endOfClass(arguments);
};