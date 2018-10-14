$ns("ztjcSynPms.views");
$import("ztjcSynPms.views.MainView");

ztjcSynPms.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new ztjcSynPms.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    
    me.synServiceAll_click = function(){
    	var client = new mx.rpc.RESTClient();	//初始化RESTClient
       	var restUrl = "~/rest/ztjcSynPmsBizc/ztjcSynPmsService";	//要执行的Controller方法
       	mx.indicate("info", "正在同步中，请稍后~~~");
       	client.get(ztjcSynPms.mappath(restUrl), {}, function(p_context) {
			/*mx.indicate("info", "同步结束成功");*/
   		});
    }
    
    me.synServiceIncrement_click = function(){
    	var client = new mx.rpc.RESTClient();	//初始化RESTClient
       	var restUrl = "~/rest/ztjcSynPmsBizc/ztjcSynPmsIncreamentService";	//要执行的Controller方法
       	mx.indicate("info", "正在同步中，请稍后~~~");
       	client.get(ztjcSynPms.mappath(restUrl), {}, function(p_context) {
			mx.indicate("info", "同步结束成功");
   		});
    }
    
    return me.endOfClass(arguments);
};