$ns("bdzzyxzkcx.views");
$import("openChart.views.MainViewController");
$import("bdzzyxzkcx.views.bdzzyxzkcxMainView");

bdzzyxzkcx.views.bdzzyxzkcxMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzzyxzkcx.views.bdzzyxzkcxMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
 
    };
    
    me._btnCx_onclick = function(){
    	var _seSearchBox = me.view.getSearchBox();
    	var _dataGrid = me.view.getDataGrid();
    	var params = _seSearchBox.getSearchParam();
        _dataGrid.setFilter(params);
		_dataGrid.load();
    };
    
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