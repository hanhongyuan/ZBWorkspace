$ns("sdzzyxzktj.views");

$import("mx.containers.TabControl");
$import("sdzzyxzktj.views.sdzzyxzktjByDydjMainViewController");
$import("sdzzyxzktj.views.sdzzyxzktjByJclxMainViewController");
$import("sdzzyxzktj.views.sdzzyxzktjBySccjMainViewController")

sdzzyxzktj.views.MainView = function()
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
    	me.sdzzyxzktjByDydjController = sdzzyxzktj.views.sdzzyxzktjByDydjMainViewController();		//获取按电压等级统计的Controller
    	me.sdzzyxzktjByJclxController = sdzzyxzktj.views.sdzzyxzktjByJclxMainViewController();		//获取按监测类型统计的Controller
    	me.sdzzyxzktjBySccjController = sdzzyxzktj.views.sdzzyxzktjBySccjMainViewController();
    	me.sdzzyxzktjByDydjView = me.sdzzyxzktjByDydjController.getView();
    	me.sdzzyxzktjByJclxView = me.sdzzyxzktjByJclxController.getView();
    	me.sdzzyxzktjBySccjView = me.sdzzyxzktjBySccjController.getView();
    }
    
    function _initTabControl(){
    	_tabcontrol = new mx.containers.TabControl({
   		   pages:[
   		       { text: "按电压等级统计", name: "statByDydj", autoInit: true },
   			   { text: "按监测类型统计", name: "statByJclx", autoInit: true },
   		       { text: "按生产厂家统计", name: "statBySccj", autoInit: true}
   		   ]
       	});
     	
     	_tabcontrol.pages[0].addControl(me.sdzzyxzktjByDydjView);
     	_tabcontrol.pages[1].addControl(me.sdzzyxzktjByJclxView);
     	_tabcontrol.pages[2].addControl(me.sdzzyxzktjBySccjView)
     	
     	
     	
     	me.addControl(_tabcontrol);
    }

    return me.endOfClass(arguments);
};