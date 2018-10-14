$ns("userlogger.views");
$import("userlogger.views.MainView");
$import("userlogger.views.QueryViewController");
$import("userlogger.views.LimitSetViewController");
$include("~/com.sgcc.pms.zbztjc.util/enum/commonFunction.js");

userlogger.views.MainViewController = function() {
	var me = $extend(mx.views.ViewController);
	me.orderColumn = "CZTIME";
	me.order = false;				//false指倒序
	var base = {};
	me.typeName = "userlogger.views.MainViewController";

	me.getView = function() {
		if (me.view == null) {
			me.view = new userlogger.views.MainView({
						controller : me
					});
		}
		return me.view;
	};

	me._onactivate = function(e) {
		// TODO: 窗体激活时的逻辑。

		Queryinfo();
	};

	function Queryinfo() {
		// 加个数量限制
		var client1 = new mx.rpc.RESTClient(); // 初始化RESTClient
		var restUrl = "~/rest/userlogger/getLoggerAlarm"; // 要执行的Controller方法
		client1.send(userlogger.mappath(restUrl), "GET", null, false, function(
				p_context) {
			if (p_context.successful && (p_context.resultValue.items["0"] == "warn" || p_context.resultValue.items["0"] == "alarm")) {
				var czlx = "告警";
				var transactionType = "安全审计信息告警";
				var result = "操作成功";
				me.updateAuticUserLogger(czlx, transactionType, result);
				alert("安全审计信息告警！");
			}
		});
		var searchBox = me.view.getSearchBox();
		var str = searchBox.getSearchParam();
		me.view.getDataGrid().setFilter(str);
		me.view.getDataGrid().load();
	}

	me.loadData = function(e) {
		var searchBox = me.view.getSearchBox();
		var resetSeachBox = me.view.getSearchBox().resetButton;
		var str = searchBox.getSearchParam();
		me.view.getDataGrid().setFilter(str);
		var czlx = "查询";
		var transactionType = "状态监测-系统管理-状态监测审计";
		var result = "操作成功";
		me.updateAuticUserLogger(czlx, transactionType, result);
		me.view.getDataGrid().load();
	}

	me.exportData = function() {
		
		try{
			var _dataGrid = me.view.getDataGrid();
            //导出文档的工具类
    		var util = new mxpms.utils.FormDocumentUtil({
               //title为导出文档内容的标题，标题可为“”字符串
               title:"",
               //view为页面视图或支持类型的jquery对象
            view:_dataGrid.$grid,
               //fileName为导出的文档名称
            fileName:"审计信息"
            });
         	util.exportExcel();
	        var czlx = "导出";
			var transactionType = "状态监测-系统管理-状态监测审计";
			var result = "操作成功";
			me.updateAuticUserLogger(czlx,transactionType,result);
			mx.indicate("info","导出成功");
		}catch(e){
			var czlx = "导出";
    		var transactionType = "状态监测-系统管理-状态监测审计";
    		var result = "操作失败";
    		me.updateAuticUserLogger(czlx,transactionType,result);
    		mx.indicate("info","导出失败");
		}
	}

	me.orderQuery = function(e){
		if(me.orderColumn != e.id){		//如果排序字段不同，即第一次点击，则正序排列
			me.order = true;
		}else{							//如果是字段相同，则顺序颠倒
			me.order =!me.order; 
		}
		me.orderColumn = e.id;			//将排序字段赋给全局变量
		
		var searchBox = me.view.getSearchBox();
		var resetSeachBox = me.view.getSearchBox().resetButton;
		var str = searchBox.getSearchParam();
		str += "&orderColumn="+me.orderColumn+"&order="+me.order;
		me.view.getDataGrid().setFilter(str);
		me.view.getDataGrid().load();
		var czlx = "排序";
		var transactionType = "状态监测-系统管理-状态监测审计";
		var result = "操作成功";
		me.updateAuticUserLogger(czlx,transactionType,result);
	}
	
	me.updateUserLogger = function(czlx, transactionType, result) {
		var userInfo = new mxpms.utils.UserInfoUtil().getLoginUser();
		var client = new mx.rpc.RESTClient(); // 初始化RESTClient

		var params = {};
		params.items = new Array();
		params.items.push({
					"userId" : userInfo.userID,
					"userName" : userInfo.userName,
					"userIp" : userInfo.ip,
					"czlx" : czlx,
					"transactionType" : transactionType,
					"result" : result
				});
		// 对缺陷进行检查，在同一缺陷类别下不能又相同的缺陷编码
		var restUrl = "~/rest/loggerSave/saveLogger"; // 要执行的Controller方法
		try{
			client.send(userlogger.mappath(restUrl), "POST",
				JSON.stringify(params), false, function(p_context) {
				});
		}catch(e){
			return;
		}
	}
	/**
	 * 审计页面的操作保存
	 */
	me.updateAuticUserLogger = function(czlx, transactionType, result) {
		var userInfo = new mxpms.utils.UserInfoUtil().getLoginUser();
		var client = new mx.rpc.RESTClient(); // 初始化RESTClient

		var params = {};
		params.items = new Array();
		params.items.push({
					"userId" : userInfo.userID,
					"userName" : userInfo.userName,
					"userIp" : userInfo.ip,
					"czlx" : czlx,
					"transactionType" : transactionType,
					"result" : result,
					"eventType" : "系统事件"
				});
		// 对缺陷进行检查，在同一缺陷类别下不能又相同的缺陷编码
		var restUrl = "~/rest/userlogger/updateUserLogger"; // 要执行的Controller方法
		try{
			client.send(userlogger.mappath(restUrl), "POST",
					JSON.stringify(params), false, function(p_context) {
					});
		}catch(e){
			return;
		}
	}

	// 审计查阅
	var detailView = null;
	me.tjfxOnclick = function(e) {
		if ($isEmpty(detailView)) {
			var mvc = new userlogger.views.QueryViewController();
			detailView = mvc.getView();
		}
		me.view.detailWin.setWidth("850");
		me.view.detailWin.setHeight("530");
		me.view.detailWin.setTitle("统计分析");
		me.view.detailWin.setView(detailView);
		me.view.detailWin.showDialog();
	}
	
	var limitSetView = null;
	me.limitSetClick = function(){
		if($isEmpty(limitSetView)){
			var mvc = new userlogger.views.LimitSetViewController();
			limitSetView = mvc.getView();
			limitSetView.objID = "userLoggerLimit";
        	limitSetView.getForm().load("userLoggerLimit");
		}
		me.view.detailWin.setWidth("350");
    	me.view.detailWin.setHeight("200");
    	me.view.detailWin.setTitle("审计容量上限设定");
		me.view.detailWin.setView(limitSetView);
		me.view.detailWin.showDialog();
	}

	return me.endOfClass(arguments);
};