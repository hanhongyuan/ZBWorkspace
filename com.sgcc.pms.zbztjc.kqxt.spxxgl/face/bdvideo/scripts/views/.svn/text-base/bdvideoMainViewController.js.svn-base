$ns("bdvideo.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("bdvideo.views.bdvideoMainView");
$import("mx.containers.HtmlContainer");
$import("mx.windows.WindowManager");
$import("mx.rpc.RESTClient");

bdvideo.views.bdvideoMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
	
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdvideo.views.bdvideoMainView({ controller: me });
        }
        return me.view;
    };



	// 加载列表数据。
	me._loadDataGrid = function(e)
	{
    	me.view.getDataGrid().load();
	}


    me._onactivate = function(e)
    {
        // iscID 是界面的统一权限功能编码，默认值为 "-1" ，表示不应用权限设置。
    	var permission = new mx.permissions.Permission({iscID:"-1"});
        // 根据“统一权限”设置组件的可见和只读等属性
    	// me.view 是当前页面的view页面，可根据需要传入其他需要权限控制页面元素
        mx.permissions.PermissionAgent.setPermission(permission, me.view);
        //加载数据
	    me.view.getDataGrid().load();
    };

    
    /**
     * 查看视频
     * @param videocode : 视频编码
     * @param name : 装置名称
     */
    me._btnLookOne = function(param){
    	
    	var params = param.split("_");
    	var videocode = params[0];
    	var name = params[1];
    	
    	
    	var url = "../showvideo/index.jsp?type=bd&videocode="+videocode+"&name="+name;
    	var d_width = "950";
    	var d_height = "450";
    	showView(url,name,d_width,d_height);
    }
    
    
    function showView(path,title,d_width,d_height){
    	
    	me._detailWinMana = new mx.windows.WindowManager({});
    	var _detailWin = me._detailWinMana.create({
    		reusable:true,
    		width:d_width,
    		height:d_height,
    		title:title,
    		resizable: false
    	});
    	
    	me.htmlContainer = new mx.containers.HtmlContainer({
    		url:path,
    		height:"100%",
    		width:"100%",
    		type:"Iframe"
    			
    	});
    	
    	_detailWin.addControl(me.htmlContainer);
    	_detailWin.showDialog();
    }
    
    
    me.endOfClass(arguments);
    return me;
};