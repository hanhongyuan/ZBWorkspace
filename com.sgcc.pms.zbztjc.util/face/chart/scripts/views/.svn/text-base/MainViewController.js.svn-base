$ns('chart.views');

$import('chart.views.MainView');
$import("mx.containers.HtmlContainer");
chart.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    
    me.url = "~/rest/chart/getChart";
    me.monitor = [];
    me.params = { };
    me.sbid = "";
    me.lastobjid = "";
    me.lasttime = "";
    var client = new mx.rpc.RESTClient();
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new chart.views.MainView( { controller: me } );
        }
        
        return me.view;
    };
    
    me._onactivate = function()
    {
    	initParams();
//    	if(me.params.MONITORINGTYPE.substring(0,2)=="02")
//		{
//    		querysbid();
//		}
//    	if(me.params.MONITORINGTYPE=="021002")
//		{
//    		querylastobjid();
//		}
    	
    	
		drawPage();
		
    	me.view.initChart();

//    	me.loadChart();
		
//		me.loadLatest();
    };
    
    me._labelClick = function() {
    	// wait to go...
    };
    
    me._queryClick = function() {
    	me.loadChart();
    };
    
    me._monitorChanged = function(e) {
    	if(e.item.checked) {
    		me.monitor.push(e.item.value);
    	} else {
    		me.monitor.remove(e.item.value);
    	}
    	
    	me.loadChart();
    };
    
    me._serachChanged = function(e) {
    	var stratDateControl = me.view.getControl("开始日期");
    	var endDateControl = me.view.getControl("结束日期")
    	if(e.item.getValue() == 'x') {
    		stratDateControl.setReadOnly(false);
    		endDateControl.setReadOnly(false);
    	} else {
    		stratDateControl.setReadOnly(true);
	    	endDateControl.setReadOnly(true);
    	}
    };
    
    me.loadChart = function() {
    	var filter = me.view.getValue("查询框");
    	if(filter == 'x') {
        	var startDate = me.view.getValue("开始日期");
        	var endDate = me.view.getValue("结束日期");
        	filter = Date.parse(startDate)>Date.parse(endDate) ? "结束日期不能小于开始日期" : 
        		((Date.parse(endDate)-Date.parse(startDate))/60000/60/24>90 ? "日期区间不能大于90天" : startDate + ',' + endDate);
    	}
    	if(filter.length > 2 && filter.split(',').length <= 1) {
    		alert(filter);
    		return;
    	}
    	
    	var columns = me.monitor.join(",");
    	columns = $notEmpty(columns) ? columns : me.getView().monitorCheckbox.items[0].getValue();
    	
    	me.view.loadChart(me.url, { filter: filter, columns: columns });
    };
    
    
    function drawPage() {
		me.view.drawPage();
		var params = { filter: me.params.MONITORINGTYPE + "," + me.params.DEVICECODE + "," + me.params.LINKEDDEPWS};
		
		var result = client.getSync(ztjcChart.mappath('~/rest/chart/getDeviceName'), {params: JSON.stringify(params)});
		if(result.successful) {
			var items = result.resultValue.items;
			var text = '无名称';
			if($notEmpty(items) && items.length > 0) {
				text = items[0];
			}
			me.view.label.setText(text);
		} else
			error(result);
	
		
		client.post(ztjcChart.mappath('~/rest/chart/getMonitoringPara'), function(result) {
			if(result.successful) {
				var items = result.resultValue.items;
				var map;
				if($notEmpty(items) && items.length > 0) {
					map = items[0];
				}
				var i = 0;
				for(var key in map) {
					var item = me.getView().monitorCheckbox.appendItem(map[key], key);
					if(i == 0) {
						me.getView().monitorCheckbox.checkItem(item);
						i++;
					}
				}
				me.view.chart.resize();
			} else
    			error(result);
		});
    }
    
    function initParams() {
    	var context = location.search;
		if(context.indexOf("?") != -1) {
			var params = context.split("?")[1].split("&");
			for(var i = 0; i < params.length; i++) {
				me.params[params[i].split("=")[0]] = params[i].split("=")[1];
			}
		} else {
    		mx.indicate("info", "未传 DEVICECODE、MONITORINGTYPE、LINKEDDEPWS 参数");
		}
    }
    
    function error(result) {
    	mx.indicate("info", "获取数据出错，详细信息请看控制台。");
		console.log(result);
    }
    
    Array.prototype.remove = function(val) {
    	var index = this.indexOf(val);
    	if(index > -1) this.splice(index, 1);
    }
    
    //查询设备ID
    function querysbid()
    {
    	var filter = me.params.DEVICECODE;
    	var params ={filter: filter};
    	var client = new mx.rpc.RESTClient();
    	me.restUrl = ztjcChart.mappath("~/rest/chart/querysbid");
		client.post(me.restUrl, {params: JSON.stringify(params)}, function(result){
			me.sbid = result[0];
		});
    }
    //打开实验报告
    open_sybg =function()
    {
//    	me.sbid = "50022004";
    	if(me.sbid=="未找到一次设备ID")
    		{
    		alert(me.sbid);
    		return;
    		}
    	var parm = "objid="+encodeURIComponent(me.sbid);
    	var url = "~/com.sgcc.pms.zhyw.sygl.intf/pages/index.jsp?"+parm;
    	
    	var window = new mx.windows.WindowManager().create({
    		title: '实验报告',
    		resizable: false,
    		width: '1170px',
    		height: '550px'
    		
    	});
    	me.htmlContainer = new mx.containers.HtmlContainer({
    		url: url,
    		height: "100%",
    		width: "100%",
    		type: "Iframe"
    	});
    	window.addControl(me.htmlContainer);
    	window.showDialog();
    }
    
    function querylastobjid()
    {
    	var filter = me.params.DEVICECODE;
    	var params ={filter: filter};
    	var client = new mx.rpc.RESTClient();
    	me.restUrl = ztjcChart.mappath("~/rest/chart/querylastobjid");
		client.post(me.restUrl, {params: JSON.stringify(params)}, function(result){
			me.lastobjid = result[0];
			me.lasttime = result[1];
		});
    }
  //打开油色谱
    open_ysp =function()
    {
//    	var obj_id = "6263BAFF-DDAB-7F9B-E050-007F0100571E-00001";未找到最新数据
    	if(me.lastobjid=="未找到最新数据")
		{
			alert(me.lastobjid);
			return;
		}
    	
		var mvc = new GasInOil.views.MainViewController();
    	var detailview = mvc.getView(me.lastobjid,me.lasttime,me.params.DEVICECODE);

    	var window = new mx.windows.WindowManager().create({
	    		title: '油色谱分析',
	    		resizable: false,
	    		width: '1150px',
	    		height: '650px'
	    	});	
    	 
		window.setWidth("1150");
    	window.setHeight("650");
    	window.setTitle("油色谱分析");
		window.setView(detailview);
		window.showDialog();
    }
    return me.endOfClass(arguments);
};