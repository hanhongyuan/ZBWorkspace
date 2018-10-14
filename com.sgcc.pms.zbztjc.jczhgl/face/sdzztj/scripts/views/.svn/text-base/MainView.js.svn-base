$ns("sdzztj.views");

$import("mx.containers.TabControl");
$import("sdzztj.views.sdzztjByDydjMainViewController");
$import("sdzztj.views.sdzztjByJclxMainViewController");
$import("sdzztj.views.sdzztjBySccjMainViewController")

sdzztj.views.MainView = function()
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
    	me.sdzztjByDydjController = sdzztj.views.sdzztjByDydjMainViewController();		//获取按电压等级统计的Controller
    	me.sdzztjByJclxController = sdzztj.views.sdzztjByJclxMainViewController();		//获取按监测类型统计的Controller
    	me.sdzztjBySccjController = sdzztj.views.sdzztjBySccjMainViewController();
    	me.sdzztjByDydjView = me.sdzztjByDydjController.getView();
    	me.sdzztjByJclxView = me.sdzztjByJclxController.getView();
    	me.sdzztjBySccjView = me.sdzztjBySccjController.getView();
    }
    
    function _initTabControl(){
    	_tabcontrol = new mx.containers.TabControl({
   		   pages:[
   		       { text: "按电压等级统计", name: "statByDydj", autoInit: true },
   			   { text: "按监测类型统计", name: "statByJclx", autoInit: true },
   		       { text: "按生产厂家统计", name: "statBySccj", autoInit: true}
   		   ]
       	});
     	
     	_tabcontrol.pages[0].addControl(me.sdzztjByDydjView);
     	_tabcontrol.pages[1].addControl(me.sdzztjByJclxView);
     	_tabcontrol.pages[2].addControl(me.sdzztjBySccjView);
     	//_tabcontrol.pages[0].resizeTo(document.body.offsetWidth -15, document.body.scrollHeight );
     	me.addControl(_tabcontrol);
    }

    return me.endOfClass(arguments);
};