$ns("openChart.views");

$import("openChart.views.MainView");
$import("mx.containers.HtmlContainer");
$import("mx.rpc.RESTClient");

openChart.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new openChart.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    
    /**
	 * 三级页面打开弹出窗公共调用方法
	 * @param args {lx:"zgz",dw:"",title:"",filter:"fliter"}
	 * @param isHomepage 是否调用一级页面
	 * @author wangyanning
	 */
	me.showGbfxDialogWS = function(args, isHomepage) 
	{
	//打开窗口参数、配网运维PMS2.0总部穿透
	var pwywCrossUrl = "/com.sgcc.pms.dwzy.gg/comlogin/index.jsp?#/sgpms";
	
	//获取总部版本号、是否是首页
	//	var cfg = publicModulejd.common.commonFunction;
	//	var isHomepage = $notEmpty(isHomepage)? true : false;
	
	//解析传递的参数，获取页面地址
	if(!!args.lx && $notEmpty(args.lx)) 
	{
		/*---------------------------------------总部功能 一级页面处理 start ----------------------------------*/	
		var ssdw = $notEmpty(args.dw)?args.dw:"",
		    page = args.lx,
		    filter = args.filter,
		    ejcjtitle = $notEmpty(args.title)?args.title:"";
		
		var wsPmsIP,   //网省PMS路径
			wsRst; 	   //REST服务返回结果
		wsRest = new mx.rpc.RESTClient({
			baseUrl : mx.mappath("~/com.sgcc.pms.zbztjc.util/rest/chart")
		});
		wsRst = wsRest.getSync("/getPzInfoByWsID",{wsId : ssdw});
		if(wsRst.successful) 
		{
			var rs = wsRst.resultValue;
			wsPmsIP = rs.PMSIP;
		}
		
		/*---------------------------------------总部功能 一级页面处理 ----------------------------------*/	
		if(isHomepage)
		{
			
			
		}
		/*---------------------------------------总部功能 二级页面处理  ----------------------------------*/	
		
		//具体页面路径
		var pg = "";
		
		//窗口展示
		var win = openChart.context.windowManager.create({
			reusable : true,
			width : "95%",
			height : "100%",
			title : ejcjtitle
		});
		switch(page) 
		{
			case "chartztt"://组态图
				pg = "/com.sgcc.pms.ztjc.util/chart/index.jsp";
				break;
			case "imageztt"://图片组态图
				pg = "/com.sgcc.pms.ztjc.util/image/index.jsp";
				break;
			case "tdchartztt"://电缆通道组态图
				pg = "/com.sgcc.pms.ztjc.util/tdChart/index.jsp";
				break;
			default :
				mx.indicate("info","二级页面配置出错。");
				break;
		}
		if($notEmpty(pg)) 
		{
			var htmlContainer = new mx.containers.HtmlContainer({
		           url : (wsPmsIP+pwywCrossUrl+pg+filter),
		           height : "100%",
		           width : "100%",
		           type : "Iframe"
		    });
			win.addControl(htmlContainer);
			win.showDialog();
		}
	}
}
    
    return me.endOfClass(arguments);
};