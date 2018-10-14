$ns("bdzzyxzktj.views");

$import("mx.containers.TabControl");
$import("bdzzyxzktj.views.bdzzyxzktjByDydjMainViewController");
$import("bdzzyxzktj.views.bdzzyxzktjByJclxMainViewController");

bdzzyxzktj.views.MainView = function()
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
    	me.bdzzyxzktjByDydjController = bdzzyxzktj.views.bdzzyxzktjByDydjMainViewController();		//获取按电压等级统计的Controller
    	me.bdzzyxzktjByJclxController = bdzzyxzktj.views.bdzzyxzktjByJclxMainViewController();		//获取按监测类型统计的Controller
    	me.bdzzyxzktjByDydjView = me.bdzzyxzktjByDydjController.getView();
    	me.bdzzyxzktjByJclxView = me.bdzzyxzktjByJclxController.getView();
    }
    
    function _initTabControl(){
    	_tabcontrol = new mx.containers.TabControl({
   		   pages:[
   		       { text: "按电压等级统计", name: "statByDydj", autoInit: true },
   			   { text: "按监测类型统计", name: "statByJclx", autoInit: true }
   		   ]
       	});
     	
     	_tabcontrol.pages[0].addControl(me.bdzzyxzktjByDydjView);
     	_tabcontrol.pages[1].addControl(me.bdzzyxzktjByJclxView);
     	
     	
     	
     	me.addControl(_tabcontrol);
    }

    return me.endOfClass(arguments);
};