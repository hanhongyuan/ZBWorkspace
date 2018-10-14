$ns("zyzztj.views");

$import("mx.containers.TabControl");
$import("zyzztj.views.zyzztjBySsxtMainViewController");

zyzztj.views.MainView = function()
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
    	me.zyzztjBySsxtController = zyzztj.views.zyzztjBySsxtMainViewController();		//获取按电压等级统计的Controller
    	me.zyzztjBySsxtView = me.zyzztjBySsxtController.getView();
    }
    
    function _initTabControl(){
    	_tabcontrol = new mx.containers.TabControl({
   		   pages:[
   		       { text: "按所属系统统计", name: "statBySsxt", autoInit: true }
   		   ]
       	});
     	
     	_tabcontrol.pages[0].addControl(me.zyzztjBySsxtView);
     	
     	me.addControl(_tabcontrol);
    }

    return me.endOfClass(arguments);
};