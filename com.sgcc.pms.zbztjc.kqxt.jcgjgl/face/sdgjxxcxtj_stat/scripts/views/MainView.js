$ns("sdgjxxcxtj_stat.views");

$import("mx.containers.TabControl");
$import("sdgjxxcxtj_stat.views.statByDydjMainViewController");
$import("sdgjxxcxtj_stat.views.statByJclxMainViewController");

sdgjxxcxtj_stat.views.MainView = function()
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
    	me.statByDydjController = sdgjxxcxtj_stat.views.statByDydjMainViewController();		//获取按电压等级统计的Controller
    	me.statByJclxController = sdgjxxcxtj_stat.views.statByJclxMainViewController();		//获取按监测类型统计的Controller
    	
    	me.statByDydjView = me.statByDydjController.getView();
    	me.statByJclxView = me.statByJclxController.getView();
    }
    
    function _initTabControl(){
    	_tabcontrol = new mx.containers.TabControl({
   		   pages:[
   		       { text: "按电压等级统计", name: "statByDydj", autoInit: true },
   			   { text: "按监测类型统计", name: "statByJclx", autoInit: true }
   		   ]
       	});
     	
     	_tabcontrol.pages[0].addControl(me.statByDydjView);
     	_tabcontrol.pages[1].addControl(me.statByJclxView);
     	
     	
     	
     	me.addControl(_tabcontrol);
    }

    return me.endOfClass(arguments);
};