$ns("dldevicequery.views");
$import("dldevicequery.views.MainView");

$import("mx.rpc.RESTClient");
$import("mx.windows.Window");
$import("openChart.views.MainViewController");
dldevicequery.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    var params = null;
    var pageSize = 20;
    var pageIndex = 1;
    
    me.items = null;
    me.itemCount = null;
    var rest = new mx.rpc.RESTClient();
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dldevicequery.views.MainView({ controller: me });
        }
        return me.view;
    };
    /**
     * 导出
     */
        
        me._btnexport_onclick=function()
        {
        	 
        	 
         	try {
         		var _dataGrid = me.view.getDataGrid();
                //导出文档的工具类
        		var util = new mxpms.utils.FormDocumentUtil({
                   //title为导出文档内容的标题，标题可为“”字符串
                   title:"",
                   //view为页面视图或支持类型的jquery对象
                view:_dataGrid.$grid,
                   //fileName为导出的文档名称
                fileName:"电缆监测信息查询数据"
                });
             	util.exportExcel();
             	var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
				var transactionType = "状态监测-监测信息查询-电缆监测信息查询数据";		//此处为页面的菜单路径信息
				var result = "操作成功"								//此处为操作结果，二选一
				var userlogg = new userlogger.views.MainViewController();
				userlogg.updateUserLogger(czlx,transactionType,result);
			} catch (e) {
				mx.indicate("info","导出失败");
				var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
				var transactionType = "状态监测-监测信息查询-电缆监测信息查询数据";		//此处为页面的菜单路径信息
				var result = "操作失败"								//此处为操作结果，二选一
				var userlogg = new userlogger.views.MainViewController();
				userlogg.updateUserLogger(czlx,transactionType,result);	
			}
        }

    /**
     * 查看 
     * */
    me._btnTest = function(devicecode,monitortype,deptws)
        {
	    	if("021051"==monitortype){
	    		mx.indicate("info", "油枕油位组态图暂无!");
	    		return;
	    	}else if ("026001"==monitortype){
	        	mx.indicate("info", "视频请在视频页面统一查看!");
	    		return;
	        }
	        
	    	var parm = "?MONITORINGTYPE=" + monitortype+"&"+"DEVICECODE=" + devicecode;
	    	//通道类的监测类型
	    	var tdjclx =["032002",
					"032003",
					"032004",
					"032005",
					"032006",
					"032007",
					"032008",
					"032009",
					"032010",
					"032011",
					"032012",
					"032013",
					"032014",
					"032015",
					"032016",
					"032017",
					"032018"];
		    	var lx = "";
		    	var title = "";
			    //判断是否是电缆通道类的
    			if(tdjclx.indexOf(monitortype)!=-1) {
    				lx = "tdchartztt";
			    	title = "电缆组态图";
    			} else {
	    				if(monitortype == '018002' || monitortype == '032010') {
		    				lx = "imageztt";
			    			title = "图像组态图";
	    				} else {
	    					lx = "chartztt";
			    			title = "组态图";
	    				}
    			}
        		//穿透页面参数
		    	var params = {
		    		lx : lx, 
		    		dw : deptws,
		    		filter : parm,
		    		title : title
		    	};
    	    	var openchartController = new openChart.views.MainViewController();
		    	// 穿透方法
		    	openchartController.showGbfxDialogWS(params, false);
		    	
        	}
 
    
    return me.endOfClass(arguments);
};