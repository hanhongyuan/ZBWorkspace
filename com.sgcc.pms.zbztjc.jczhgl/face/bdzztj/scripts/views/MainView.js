$ns("bdzztj.views");

$import("mx.containers.TabControl");
$import("bdzztj.views.bdzztjByDydjMainViewController");
$import("bdzztj.views.bdzztjByJclxMainViewController");
$import("bdzztj.views.bdzztjBySccjMainViewController")

bdzztj.views.MainView = function()
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
    	_init_Control();
    	_initTabControl();
        me.on("activate", me.controller._onactivate);
    }
    
    
    /**
     * 
     */
    function _init_Control(){
    	me.bdzztjByDydjController = bdzztj.views.bdzztjByDydjMainViewController();		//获取按电压等级统计的Controller
    	me.bdzztjByJclxController = bdzztj.views.bdzztjByJclxMainViewController();		//获取按监测类型统计的Controller
    	me.bdzztjBySccjController = bdzztj.views.bdzztjBySccjMainViewController();
    	me.bdzztjByDydjView = me.bdzztjByDydjController.getView();
    	me.bdzztjByJclxView = me.bdzztjByJclxController.getView();
    	me.bdzztjBySccjView = me.bdzztjBySccjController.getView();
    }
    
    function _initTabControl(){
    	_tabcontrol = new mx.containers.TabControl({
   		   pages:[
   		       { text: "按电压等级统计", name: "statByDydj", autoInit: true },
   			   { text: "按监测类型统计", name: "statByJclx", autoInit: true },
   		       { text: "按生产厂家统计", name: "statBySccj", autoInit: true}
   		   ]
       	});
     	
     	_tabcontrol.pages[0].addControl(me.bdzztjByDydjView);
     	_tabcontrol.pages[1].addControl(me.bdzztjByJclxView);
     	_tabcontrol.pages[2].addControl(me.bdzztjBySccjView)
     	
     	
     	
     	me.addControl(_tabcontrol);
    }

    return me.endOfClass(arguments);
};