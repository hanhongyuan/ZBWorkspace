$ns("zztjfxcharts.utils");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.ComplexGrid");

zztjfxcharts.utils.UpbarChart = function()
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
	me.loadChart = function(url) {
		me.restUrl = zztjfxcharts.mappath(url);
		
		me.client.post(me.restUrl, function(result){
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
		
		var se = {};
		// 判断 dataZoom 是否已经初始化 并且是否在放大查看
		if($notEmpty(me.chart.getOption()) && 
				me.chart.getOption().dataZoom[0].end-me.chart.getOption().dataZoom[0].start!=100) {
			
			// 回填 dataZoom 的开头和结尾位置
			se.start = me.chart.getOption().dataZoom[0].start;
			se.end = me.chart.getOption().dataZoom[0].end;
		} else {
			// 判断 data 的长度 来控制 dataZoom 的开头和结尾位置
			if(data.length > 60) {
				se.start = 20;
				se.end = 40;
			}
		}
		// 获取监测量名称 (一级集合最后一个位置存的集合为监测量名称集合)
		var xData = [];
		var series = { type: "bar",
					itemStyle: {  normal: {
　　　　　　　　　　　　　　//好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
                        color: function(params) {
                            // build a color map as your need.
                            var colorList = [
                              '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                               '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                               '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
                               '#C1232C','#B5A334','#FCBE10','#E87D40','#27727E',
                               '#FB8463','#9ACA63','#FDD860','#F3D43B','#60C0BB',
                               '#D7504A','#C6C579','#F4B001'
                            ];
                            return colorList[params.dataIndex]
                        },label: {
                            show: true,
                            position: 'top',
                            formatter: '{c}'
                        }
               		}
				},
					symbolSize: 0.5, data: [],barWidth:1000
			}
		for(var i = 0; i < data.length; i++) {
			//第i个数据的长度
			 var dtlength = data[i].length;
			// 获取各公司名称
			xData[i] = data[i][dtlength-1].substring(2,4);
			if(data[i][dtlength-1]=="国网黑龙江电力"){
				xData[i]="龙江"
			}
			if(data[i][dtlength-1]=="国网运行公司"){
				xData[i]="运行"
			}
				// 如果一级集合循环第一次 根据取监测量名称创建 series 中的每一个    json 对象(有几个名字，就创建几个对象)
				// 根据监测量名称循环二级集合取出对应的数据放到 series 中对应的对象的data集合里  *
			series.data.push(data[i][0]);
		} // 前提 监测量名称集合里的名称的顺序跟二级集合里存储的数据顺序一致
		
		// 指定图表的配置项和数据
		var option = {
		   /* legend: {
		        data : xData
		    },*/
		    xAxis: {type: 'category', data: xData },
		    yAxis: {type : 'value',name:'监测装置数量统计(套)'},
		    tooltip: { trigger: 'item' },
		    grid:{
				    x:60,
				    x2:40

				  },
		    dataZoom: [se, {
		    	type: 'inside',
		    	startValue: "",
    			endValue: ""
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
