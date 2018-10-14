$ns("dlzztj.views");

$import("mx.containers.TabControl");
$import("dlzztj.views.dlzztjByDydjMainViewController");
$import("dlzztj.views.dlzztjByJclxMainViewController");
$import("dlzztj.views.dlzztjBySccjMainViewController");

dlzztj.views.MainView = function()
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
	    	me.dlzztjByDydjController = dlzztj.views.dlzztjByDydjMainViewController();		//获取按电压等级统计的Controller
	    	me.dlzztjByJclxController = dlzztj.views.dlzztjByJclxMainViewController();		//获取按监测类型统计的Controller
	    	me.dlzztjBySccjController = dlzztj.views.dlzztjBySccjMainViewController();
	    	me.dlzztjByDydjView = me.dlzztjByDydjController.getView();
	    	me.dlzztjByJclxView = me.dlzztjByJclxController.getView();
	    	me.dlzztjBySccjView = me.dlzztjBySccjController.getView();
	    }
	    
	    function _initTabControl(){
	    	_tabcontrol = new mx.containers.TabControl({
	   		   pages:[
	   		       { text: "按电压等级统计", name: "statByDydj", autoInit: true },
	   			   { text: "按监测类型统计", name: "statByJclx", autoInit: true },
	   		       { text: "按生产厂家统计", name: "statBySccj", autoInit: true}
	   		   ]
	       	});
	     	
	     	_tabcontrol.pages[0].addControl(me.dlzztjByDydjView);
	     	_tabcontrol.pages[1].addControl(me.dlzztjByJclxView);
	     	_tabcontrol.pages[2].addControl(me.dlzztjBySccjView)
	     	
	     	
	     	
	     	me.addControl(_tabcontrol);
	    }

    
    return me.endOfClass(arguments);
};