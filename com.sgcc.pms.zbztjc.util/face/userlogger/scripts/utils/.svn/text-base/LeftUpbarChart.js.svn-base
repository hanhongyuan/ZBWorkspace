$ns("userlogger.utils");

$import("userlogger.views.MainViewController");

userlogger.utils.LeftUpbarChart = function()
{
	var me = $extend(MXObject);
	var base = {};
	
	me.chart;
	_detailView = null;
	base._ = me._;
	me._ = function(id)
	{
		if (me.canConstruct())
		{
			base._(id);
			
			// 初始化 echarts
			me.chart = echarts.init(document.getElementById(id));
			me.client = new mx.rpc.RESTClient();
		}
	};
	
	me._getDetailView = function(){
    	if (null == _detailView)
        {
            var _detailView = new userlogger.views.MainViewController();
        }
    	return _detailView;
	}
	
	// 封装的加载 echarts
	// url 访问后台方法的地址 例：~/rest/chart/querySubstationWeather
	// params 条件参数 用以访问方法时需要的参数 例：{ filter: '3 || 7 || 30 || (startDate,endDate)', columns: 'column0,column1,column2...' }
	// filter: 时间段  3代表3天内 以此类推
	// columns: 要查的监测量
	me.loadChart = function(url, params) {
		me.params = params;
		me.restUrl = userlogger.mappath(url);
		var userloggersss = me._getDetailView();
		me.client.post(me.restUrl, {params: JSON.stringify(params)}, function(result){
			if (result.successful)
			{
				// 结果可能为空 
				//me.clear();
				var czlx = "查询";
				var transactionType = "状态监测审计统计分析图像展示";
				var resultA = "操作成功";
				userloggersss.updateAuticUserLogger(czlx,transactionType,resultA);
			    me.drawChart(result.resultValue.items);
			}
			else
			{
				var czlx = "查询";
				var transactionType = "状态监测审计统计分析图像展示";
				var resultA = "操作失败";
				userloggersss.updateAuticUserLogger(czlx,transactionType,resultA);
			    alert("REST 服务调用失败，请参考: " + result.resultHint);
			    console.log(result);
			}
		});
	};
	
	// 封装的画 echarts
	// data 要画在 echarts 上的数据 
	// 格式: [ ['监测量0数据', '监测量1数据', ..., '日期'], ['监测量0数据', '监测量1数据', ..., '日期'], ..., (names)['name0', 'name2', ...] ] 多行数据
	me.drawChart = function(data) {
		
		var se = {};
		// 判断 dataZoom 是否已经初始化 并且是否在放大查看
		if($notEmpty(me.chart.getOption()) && 
				me.chart.getOption().dataZoom[0].end-me.chart.getOption().dataZoom[0].start!=100) {
			
			// 回填 dataZoom 的开头和结尾位置
			se.start = me.chart.getOption().dataZoom[0].start;
			se.end = me.chart.getOption().dataZoom[0].end;
		} else {
			// 判断 data 的长度 来控制 dataZoom 的开头和结尾位置
			if(data.length > 10) {
				se.start = 5;
				se.end = 15;
			}
		}
		
		
		// 获取监测量名称 (一级集合最后一个位置存的集合为监测量名称集合)
		var xData = [];
			// 将多行数据转换成多组数据  *
		var series = { type: "bar",symbolSize: 0.5, data: []//,barWidth:1000
			}
			// 根据监测量名称循环二级集合取出对应的数据放到 series 中对应的对象的data集合里  *
			for(var i = 0; i < data.length; i++) {
			//第i个数据的长度
			 var dtlength = data[i].length;
			// 获取各公司名称
			xData[i] = data[i].TJLX;
			// 如果一级集合循环第一次 根据取监测量名称创建 series 中的每一个    json 对象(有几个名字，就创建几个对象)
			// 根据监测量名称循环二级集合取出对应的数据放到 series 中对应的对象的data集合里  *
			series.data.push(data[i].CZNUM);
			} // 前提 监测量名称集合里的名称的顺序跟二级集合里存储的数据顺序一致
		
		// 指定图表的配置项和数据
		var option = {
		    xAxis: {type: 'category', data: xData },
		    yAxis: {type : 'value',name:'操作数(次)'},
		    tooltip: { trigger: 'item' },
		    dataZoom: [se, {
		    	type: 'inside'
		    }],
		    series: series
		};
		
		// 使用刚指定的配置项和数据显示图表。true 表示不跟之前的数据合并
		me.chart.setOption(option,true);
	};
	
	
	
	me.clear = function() {
		me.chart.clear();
	};
	me.dispose = function() {
		me.chart.dispose();
	};

	return me.endOfClass(arguments);
};
