$ns("dlgjxxcxtj_stat.views");

$import("mx.containers.TabControl");
$import("dlgjxxcxtj_stat.views.statByDydjMainViewController");
$import("dlgjxxcxtj_stat.views.statByJclxMainViewController");

dlgjxxcxtj_stat.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;

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
    	me.statByDydjController = dlgjxxcxtj_stat.views.statByDydjMainViewController();		//获取按电压等级统计的Controller
    	me.statByJclxController = dlgjxxcxtj_stat.views.statByJclxMainViewController();		//获取按监测类型统计的Controller
    	
    	me.statByDydjView = me.statByDydjController.getView();
    	me.statByJclxView = me.statByJclxController.getView();
    }
    
    function _initTabControl(){
    	_tabcontrol = new mx.containers.TabControl({
   		   pages:[
   		       { text: "按电压等级统计", name: "statByDydj", autoInit: true },
   			   { text: "按监测类型统计", name: "statByJclx", autoInit: true }
   		   ],
   		   width:"99.6%"
       	});
     	
     	_tabcontrol.pages[0].addControl(me.statByDydjView);
     	_tabcontrol.pages[1].addControl(me.statByJclxView);
     	
     	me.addControl(_tabcontrol);
    }
    
    return me.endOfClass(arguments);
};