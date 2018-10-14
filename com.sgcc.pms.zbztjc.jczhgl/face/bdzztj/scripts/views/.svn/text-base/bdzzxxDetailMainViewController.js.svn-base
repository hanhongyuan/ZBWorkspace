$ns("bdzztj.views");
$import("bdzztj.views.bdzzxxDetailMainView");
$import("bdzztj.views.bdzztjByDydjMainViewController");
$import("openChart.views.MainViewController");

bdzztj.views.bdzzxxDetailMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzztj.views.bdzzxxDetailMainView({ controller: me });
        }
        return me.view;
    };
    
    
    
    me._onactivate = function(e)
    {
	        // TODO: 窗体激活时的逻辑。
		if (me.view != null && typeof(me.view.dataGrid) != "undefined")
		{
	 	    me.view.dataGrid.load();
		}	
    };
    
    /**
     * 点击查询的方法
     */
    me._queryBtn_click = function(){
    	me._searchBox = me.view.getSearchBox();				//获取SearchBox
    	var params = me._searchBox.getSearchParam();
		me.view.getDataGrid().setFilter(params);
		me.view.getDataGrid().load();
    }
    /**
     * 点击导出的方法
     */
    me._export_click = function(){
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "变电站数查询-详细信息列表"
        });
    }
    /**
     * 点击查看组态图的方法
     */
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