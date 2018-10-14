$ns("ztjcChart.utils");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.ComplexGrid");

ztjcChart.utils.Chart = function(id, hasHistory)
{
	var me = $extend(MXObject);
	var base = {};
	
	me.chart;

	base._ = me._;
	me._ = function()
	{
		if (me.canConstruct())
		{
			base._();

			// 初始化 echarts
			me.chart = echarts.init(document.getElementById(id), "dark");
			me.client = new mx.rpc.RESTClient();
			// 默认有历史数据
			hasHistory = $notEmpty(hasHistory) ? hasHistory : true;
		}
	};
	
	// 封装的加载 echarts
	// url 访问后台方法的地址 例：~/rest/chart/querySubstationWeather
	// params 条件参数 用以访问方法时需要的参数 例：{ filter: '3 || 7 || 30 || (startDate,endDate)', columns: 'column0,column1,column2...' }
	// filter: 时间段  3代表3天内 以此类推
	// columns: 要查的监测量
	me.loadChart = function(url, params) {
		me.filter = params.filter;
		me.restUrl = ztjcChart.mappath(url);
		
		me.client.post(me.restUrl, {params: JSON.stringify(params)}, function(result) {
			if (result.successful)
			{
				// 结果可能为空 
			    me.drawChart(result.resultValue.items);
			}
			else
			{
	    		mx.indicate("info", "程序出错，详细信息请看控制台。");
	    		console.log(result.resultHint);
			}
		});
		mx.indicate("info", "折线图加载中，请稍后。");
	};
	
	// 封装的画 echarts
	// data 要画在 echarts 上的数据 
	// 格式: [ ['监测量0数据', '监测量1数据', ..., '日期'], ['监测量0数据', '监测量1数据', ..., '日期'], ..., (names)['name0', 'name2', ...] ] 多行数据
	me.drawChart = function(data) {
		// data 一个集合 每一个位置里存的又是一个集合
		
		var se = {};
		// 判断 dataZoom 是否已经初始化 并且是否在放大查看
		if($notEmpty(me.chart.getOption()) && 
				me.chart.getOption().dataZoom[0].end - me.chart.getOption().dataZoom[0].start != 100) {
			
			// 回填 dataZoom 的开头和结尾位置
			se.start = me.chart.getOption().dataZoom[0].start;
			se.end = me.chart.getOption().dataZoom[0].end;
		} else {
			// 判断 data 的长度 来控制 dataZoom 的开头和结尾位置
			if(data.length > 60) {
				se.start = 20;
				se.end = 80;
			}
		}
		// 获取监测量名称 (一级集合最后一个位置存的集合为监测量名称集合)
		var names = data[data.length-1];
		// times
		var xData = [];
		var series = [];
		for(var i = 0; i < data.length-1; i++) {
			// 获取日期 (除了最后一个 每一个二级集合的最后一个位置存的是日期)
			xData[i] = data[i][data[i].length-1];
			// 将多行数据转换成多组数据  *
			for(var j = 0; j < names.length; j++) {
				// 如果一级集合循环第一次 根据取监测量名称创建 series 中的每一个    json 对象(有几个名字，就创建几个对象)
				if(i == 0)
					series[j] = {name: names[j], type: "line", symbolSize: 0.5, data: []};
				
				// 根据监测量名称循环二级集合取出对应的数据放到 series 中对应的对象的data集合里  *
				series[j].data.push(data[i][j]);
			}
		} // 前提 监测量名称集合里的名称的顺序跟二级集合里存储的数据顺序一致
		
		// 指定图表的配置项和数据
		var option = {
		    title: {},
		    legend: {},
		    toolbox: {
		        show: true,
		        feature: hasHistory ? {
		            dataView: {
		            	title: "历史数据      ",
		            	lang: ["历史数据", "关闭"],
		            	readOnly: true,
		            	optionToContent: optionToContent
		            }
		        } : null
		    },
		    xAxis: { data: xData, boundaryGap : false },
		    yAxis: {},
		    tooltip: { trigger: "axis" },
		    dataZoom: [se, {
		    	type: "inside"
		    }],
		    series: series
		};
		
		// 使用刚指定的配置项和数据显示图表。true 表示不跟之前的数据合并
		me.chart.setOption(option, true);
	};
	
	function optionToContent(opt) {
		// 用来展示历史数据
	    var result = me.client.sendSync(me.restUrl+"/../getColumns", "POST");
	    if (result.successful) {
			var items = result.resultValue.items;
			
			var dc = new mx.datacontainers.GridEntityContainer({
	            "baseUrl" : me.restUrl+"/../getHistoryData",
	            "loadMeta": false // 不加载元数据
			});
			var grid = new mx.datacontrols.ComplexGrid({
				"alias": "historyDataMainViewDataGrid",
				"entityContainer": dc,
				"displayToolBar": false,
				"columns": items,
		        "displayRowNumber": true,
		        "rowNumberColWidth": '40',
	            "displayColSplitLine": true // 表格列分割线
			});
			grid.setFilter(me.filter);
			grid.pageNaviBar.$e.css('overflow', 'auto');
			grid.load();
			return grid.$e[0];
		} else {
//    		mx.indicate("info", "程序出错，详细信息请看控制台。");
    		console.log(result.resultHint);
		    return "<div>内部服务器错误，详细信息请看控制台。</div>";
		}
	}
	
	me.clear = function() {
		me.chart.clear();
	};
	me.dispose = function() {
		me.chart.dispose();
	};
	me.resize = function() {
		me.chart.resize();
	};

	return me.endOfClass(arguments);
};
