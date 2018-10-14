$ns("zztjfxcharts.utils");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.ComplexGrid");

zztjfxcharts.utils.DownJclxChart = function()
{
	var me = $extend(MXObject);
	var base = {};
	
	me.chart;

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
	
	// 封装的加载 echarts
	// url 访问后台方法的地址 例：~/rest/chart/querySubstationWeather
	// params 条件参数 用以访问方法时需要的参数 例：{ filter: '3 || 7 || 30 || (startDate,endDate)', columns: 'column0,column1,column2...' }
	// filter: 时间段  3代表3天内 以此类推
	// columns: 要查的监测量
	me.loadChart = function(url, params) {
		me.params = params;
		me.restUrl = zztjfxcharts.mappath(url);
		
		me.client.post(me.restUrl, {params: JSON.stringify(params)}, function(result){
			if (result.successful)
			{
				// 结果可能为空 
				//me.clear();
			    me.drawChart(result.resultValue.items);
			}
			else
			{
			    alert("REST 服务调用失败，请参考: " + result.resultHint);
			    console.log(result);
			}
		});
	};
	
	// 封装的画 echarts
	// data 要画在 echarts 上的数据 
	// 格式: [ ['监测量0数据', '监测量1数据', ..., '日期'], ['监测量0数据', '监测量1数据', ..., '日期'], ..., (names)['name0', 'name2', ...] ] 多行数据
	me.drawChart = function(data) {

		// 获取监测量名称 (一级集合最后一个位置存的集合为监测量名称集合)
		var names = "";
		var values = "";
		var series = [];
		for(var i = 0; i < data.length; i++) {
			//第i个数据的长度
			var dtlength = data[i].length;
			
			// 将多行数据转换成多组数据  *
			for(var j = 0; j < dtlength-2; j++) {
				values = data[i][j];
				names = data[i][j+2];
				if(i==0){
					series = {name: names, radius : '58%',type: "pie", data: []};
				}
				// 如果一级集合循环第一次 根据取监测量名称创建 series 中的每一个    json 对象(有几个名字，就创建几个对象)
//				str = "{name:"+names+",value:"+value+"}";
			var	str = {value:values,name:names};
				series.data.push(str);
				//Data.push(str);
				// 根据监测量名称循环二级集合取出对应的数据放到 series 中对应的对象的data集合里  *
			}
			//0:"null{name:装置产生无效数据,value:5}"
		} // 前提 监测量名称集合里的名称的顺序跟二级集合里存储的数据顺序一致
		// 指定图表的配置项和数据
		var option = {
		    title: {
		    	text: "按监测类型统计（％）"
		    },
		    tooltip : {
        		trigger: 'item',formatter: "{b}</br>{c}套({d}%)"
    		},
		    legend: {
    		},
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
