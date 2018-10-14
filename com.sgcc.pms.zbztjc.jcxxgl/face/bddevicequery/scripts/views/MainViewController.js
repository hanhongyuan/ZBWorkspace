$ns("bddevicequery.views");
$import("bddevicequery.views.MainView");

$import("mx.rpc.RESTClient");
$import("mx.windows.Window");
$import("openChart.views.MainViewController");
$include("~/com.sgcc.pms.zbztjc.util/enum/commonFunction.js");
bddevicequery.views.MainViewController = function()
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
            me.view = new bddevicequery.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    /**
     * 导出
     */
        
        me._btnexport_onclick=function()
        {
        	 
        	/*var dydj = me.view.dataGrid.searchBox.editors.dydj.value;
        	var linkeddepws = me.view.dataGrid.searchBox.editors.linkeddepws.value;
        	var linkedstationname = me.view.dataGrid.searchBox.editors.linkedstationname.value;
        	var manufacturer = me.view.dataGrid.searchBox.editors.manufacturer.value;
        	var monitoringtype = me.view.dataGrid.searchBox.editors.monitoringtype.value;
        	var srundate = me.view.dataGrid.searchBox.editors.srundate.value;
        	var erundate = me.view.dataGrid.searchBox.editors.erundate.value;
        	var yxzt = me.view.dataGrid.searchBox.editors.yxzt.value;
        	
        
    	   var filter = new Array();
    	   //条件框的条件
    	    if(null!=dydj)
    	    {
    	    	filter.push("dydj=" + dydj);
    	    }
     	  	if(null!=linkeddepws)
    	    {
    	    	filter.push("linkeddept=" + linkeddepws);
    	    }
    	      if(""!=linkedstationname)
    	    {
    	    	filter.push("linkedstationname=" + linkedstationname);
    	    }
    	      if(""!=manufacturer)
    	    {
    	    	filter.push("manufacturer=" + manufacturer);
    	    }
    	      if(null!=monitoringtype)
    	    {
    	    	filter.push("monitoringtype=" + monitoringtype);
    	    }
    	      if(null!=yxzt)
  		    {
  		    	filter.push("yxzt=" + yxzt);
  		    }
    	      if(null!=srundate)
    	    {
    	    	filter.push("srundate=" + srundate);
    	    }
    	      if(null!=erundate)
    	    {
    	    	filter.push("erundate=" + erundate);
    	    }
    	    
    	   
    	     var str = "";
    	    for ( var i = 0; i < filter.length; i++) {
    			str += filter[i] + "&";
    		}
        	
        	
        	me.view.getDataGrid().setFilter(str.substring(0, str.length - 1));
             var _params = null;        
             var xls = new mxpms.utils.CommUtil();
             _params = me.view.getDataGrid().filter;
             me.view.getDataGrid().filter = "";
             xls.exportToExcel(me.view.getDataGrid(),{
                 params : {params : JSON.stringify({filter : _params})},
                 filename : "变电逻辑装置信息"
             });*/
        	try {
        		var _dataGrid = me.view.getDataGrid();
            	exportToExcel(_dataGrid,"变电监测信息查询");
            	var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
    			var transactionType = "状态监测-监测信息查询-变电监测信息查询";		//此处为页面的菜单路径信息
    			var result = "操作成功"								//此处为操作结果，二选一
    			var userlogg = new userlogger.views.MainViewController();
    			userlogg.updateUserLogger(czlx,transactionType,result);
    		} catch (e) {
    			mx.indicate("info","导出失败");
    			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
    			var transactionType = "状态监测-监测信息查询-变电监测信息查询";		//此处为页面的菜单路径信息
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