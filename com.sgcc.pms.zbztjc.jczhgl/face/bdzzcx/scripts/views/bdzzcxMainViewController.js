$ns("bdzzcx.views");

$import("bdzzcx.views.bdzzcxMainView");
$import("bdzzcx.views.bdzzcxDetailViewController");
$import("openChart.views.MainViewController");
$include("~/com.sgcc.pms.zbztjc.util/enum/commonFunction.js");
bdzzcx.views.bdzzcxMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
	
     /**
     * 表单视图对象
     */
    var _detailView = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzzcx.views.bdzzcxMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
	   me.view.getDataGrid().load();
    };
	    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new bdzzcx.views.bdzzcxDetailViewController();
            _detailView = mvc.getView();
        }
    	return _detailView;
    }

    me._btnCx_onclick = function(){
    	var _seSearchBox = me.view.getSearchBox();
    	var bool = checkdate();
		if(bool){
			var _dataGrid = me.view.getDataGrid();
	    	var params = _seSearchBox.getSearchParam();
	        _dataGrid.setFilter(params);
			_dataGrid.load();
		}
    	
    };
    
    //时间判断，起始时间和终止时间都不能大于当前时间
	function checkdate(){
		var check = me.view.getSearchBox().editors.tyrq.value;
		if(check!=""&&check!=null){
			var date = new Date().toLocaleDateString();
			date = date.replace(/-/g,"/");
			if(check[0]!=""&&check[0]!=null ){
				var check0=check[0].replace(/-/g,"/");
				if(Date.parse(check0)>Date.parse(date))
				{
//					me.view.getSearchBox().editors.tyrq.reset();
					mx.indicate("warn", "开始时间不能大于当前时间!");
					return false;
				}
			}
			if(check[1]!=""&&check[1]!=null ){
				var check1=check[1].replace(/-/g,"/");
				if(Date.parse(check1)>Date.parse(date))
				{
//					me.view.getSearchBox().editors.tyrq.reset();
					mx.indicate("warn", "结束时间不能大于当前时间!");
					return false;
				}
			}
			return true;
		}else{
			return true;
		}
		
	}
    
    me._export_click = function(){
        try {
    		var _dataGrid = me.view.getDataGrid();
        	exportToExcel(_dataGrid,"变电监测装置查询详细列表");
		} catch (e) {
			mx.indicate("info","导出失败");
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
    
    me._btnlookup_click = function(){
    	_detailView = me._getDetailFromView();
    	_showDetailFormView(_detailView,"查看详情");
    }
    
     /**
     * 显示表单视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showDetailFormView(p_view,p_title){
    	var win = me.view.getDetailWindow();
    	if(typeof p_view != "undefined"){
    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    me.endOfClass(arguments);
    return me;
};