$ns("sdgjxxDetail.views");
$import("sdgjxxDetail.views.gjxxDetailMainView");
$import("openChart.views.MainViewController");
sdgjxxDetail.views.gjxxDetailMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    me._searchBox = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdgjxxDetail.views.gjxxDetailMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
		if (me.view != null && typeof(me.view.dataGrid) != "undefined"){
	 	    me.view.dataGrid.load();
		}	
    };
    /**
     * 点击查询的方法
     */
    me._queryBtn_click = function(){
    	me._searchBox = me.view.getSearchBox();				//获取SearchBox
    	var ssdw = me._searchBox.editors.ssdw.value;		//所属单位的值
    	var dydj = me._searchBox.editors.dydj.value;		//电压等级的值
    	var jclx = me._searchBox.editors.jclx.value;		//监测类型的值
    	var gjjb = me._searchBox.editors.gjjb.value;		//告警级别的值
    	var isHandled = me._searchBox.editors.isHandled.value;		//是否处理的值
    	var gjsj = me._searchBox.editors.gjsj.value;		//告警时间的值
    	
    	var filter = new Array();
    	if(null != ssdw && '' != ssdw.trim()){
    		filter.push("ssdw = " + ssdw);
    	}
    	
    	if(null != dydj && '' != dydj.trim()){
    		filter.push("dydj = " + dydj);
    	}
    	
    	if(null != jclx && '' != jclx.trim()){
    		filter.push("jclx = " + jclx);
    	}
    	
    	if(null != gjjb && '' != gjjb.trim()){
    		filter.push("gjjb = " + gjjb);
    	}
    	
    	if(null != isHandled && '' != isHandled.trim()){
    		filter.push("isHandled = " + isHandled);
    	}
    	
    	if(null != gjsj){
    		filter.push("gjsj = " + gjsj);
    	}
    	
    	var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		
		me.view.getDataGrid().setFilter(str.substring(0, str.length - 1));
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
            filename : "输电告警信息查询-详细信息列表"
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