$ns("sdkhzb.views");
$import("sdkhzb.views.MainView");
$import("sdkhzb.views.sdkhzbDetailMainViewController");
$import("sdkhzb.views.sddataDetailMainViewController");

sdkhzb.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdkhzb.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    
    me._initSdkhzbDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#rownumber,#OBJ_ID,#WSDEPMC,#HJ");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","sdkhzbLink");
		    	}
			}
	    	_dataGrid.$body.find(".sdkhzbLink").click(function(e) {
				var item = $(this).parent().data();
				var colName = e.target.id;
				$(this)[0].style.color = "#999999";
				showSdkhzbDetail(item, colName);
			});
    }
    
    /**
	 * 信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
    showSdkhzbDetail = function(item, colName) {
		debugger;
			sdkhzbDetailView = null;
			sddataDetailView = null;
		if (sdkhzbDetailView == null) {
			var mvc = new sdkhzb.views.sdkhzbDetailMainViewController();
			sdkhzbDetailView = mvc.getView();
		}
		
		if (sddataDetailView == null) {
			var mvc = new sdkhzb.views.sddataDetailMainViewController();
			sddataDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var tyrq = editor.tyrq.value;		//投运日期
//		debugger;
//		alert(tyrq);
		var ssdw = item.item.values.WSDEPMC;						//所属单位
		var ssdw_searchBox =  editor.ssdw.value;;		//查询框内的所属单位
		var jczy = "";
		switch(colName){
		case "WQXJC":
			jczy = "018001";
			break;
		case "TXJC":
			jczy = "018002";
			break;
		case "DXHCJC":
			jczy = "013001";
			break;
		case "DXWDJC":
			jczy = "013002";
			break;
		case "FPJC":
			jczy = "013004";
			break;
		case "FBJC":
			jczy = "013005";
			break;
		case "XCWHDJC":
			jczy = "014001";
			break;
		case "DXWD":
			jczy = "013006";
			break;
		case "WFZDJC":
			jczy = "013003";
			break;
		case "GTQXJC":
			jczy = "012001";
			break;
		}
		
		var type = null;

		var filter = new Array();
		
		if(null != jczy){		//如果监测类型不为空
    		filter.push("jclx = " + jczy);
    	}
		
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if("国家电网公司" == ssdw){		//如果所属单位为国家电网公司
			if(null != ssdw_searchBox){
				filter.push("ssdw = " + ssdw_searchBox);
			}
    	}else{
    		ssdw = item.item.values.LINKEDPROVICEDEPT;
    		filter.push("ssdw = " + ssdw);
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		
		if(colName=="TXJC"){
			sdkhzbDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
			sdkhzbDetailView.dataGrid.load();
			me.view.detailWin.setWidth("900");
	    	me.view.detailWin.setHeight("500");
	    	me.view.detailWin.setTitle("所属线路查询");
			me.view.detailWin.setView(sdkhzbDetailView);
			me.view.detailWin.showDialog();
		}else{
			sddataDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
			sddataDetailView.dataGrid.load();
			me.view._detailWin.setWidth("900");
	    	me.view._detailWin.setHeight("500");
	    	me.view._detailWin.setTitle("所属线路查询");
			me.view._detailWin.setView(sddataDetailView);
			me.view._detailWin.showDialog();
		}
		
		
	
}
    
    me._dydjQueryBtn_click = function(){
    	
		
		try {
			var _seSearchBox = me.view.getSearchBox();
			var bool = checkdate();
			if(bool){
				var _dataGrid = me.view.getDataGrid();
		    	var params = _seSearchBox.getSearchParam();
		        _dataGrid.setFilter(params);
				_dataGrid.load();
			}
	    	
			
	    	var czlx = "统计";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-报表查询-输电装置考核指标计算";		//此处为页面的菜单路径信息
			var result = "操作成功"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			var czlx = "统计";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-报表查询-输电装置考核指标计算";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		}
    };
    
  //时间判断，起始时间和终止时间都不能大于当前时间
	function checkdate(){
		var check = me.view.getSearchBox().editors.tyrq.value;
		var date = new Date();
		var year =date.getFullYear();
		var month =date.getMonth()+1;
		var rdate =year +"/"+ month;
		
		if(check!=""&&check!=null){
			if(check[0]!=""&&check[0]!=null ){
				var check0=check[0].replace(/-/g,"/");
				if(Date.parse(check0)>Date.parse(date))
				{
//					me.view.getSearchBox().editors.tyrq.reset();
					mx.indicate("warn", "时间不能大于当前时间!");
					return false;
				}
			}
			return true;
		}else{
			return true;
		}
	}
    
    return me.endOfClass(arguments);
};