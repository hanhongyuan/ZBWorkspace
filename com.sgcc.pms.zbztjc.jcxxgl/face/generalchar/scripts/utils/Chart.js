$ns("generalchar.utils");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.ComplexGrid");

generalchar.utils.Chart = function()
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

			me.chart = echarts.init(document.getElementById(id), 'dark');
			me.client = new mx.rpc.RESTClient();
		}
	};
	
	me.loadChart = function(url) {
		var restUrl = generalchar.mappath(url);
		
		me.client.post(restUrl,'',function(result){
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
	
	me.drawChart = function(option) {
		// option 一个集合 每一个位置里存的又是一个集合
		
		// 获取监测量名称 (一级集合最后一个位置存的集合为监测量名称集合)
		var names = ["实时装置数","总装置数"];
		var colors = ['#d14a61','#5793f3'];
		//times
		var xData = [];
		var idData = [];
		var series = [];
		for(var i = 0; i < option.length-1; i++) {
			// 获取日期 (除了最后一个 每一个二级集合的最后一个位置存的是日期)
			
			xData[i] = option[i+1][0];
			idData[i] = option[i+1][1];
			// 将多行数据转换成多组数据  *
			for(var j = 0; j < names.length; j++) {
				// 如果一级集合循环第一次 根据取监测量名称创建 series 中的每一个 json 对象(有几个名字，就创建几个对象)
				if(i == 0)
					series[j] = {name: names[j], type: "bar", data: []};

				// 根据监测量名称循环二级集合取出对应的数据放到 series 中对应的对象的data集合里  *
				series[j].data.push(option[i+1][j+2]);
			}
		} // 前提 监测量名称集合里的名称的顺序跟二级集合里存储的数据顺序一致
		
		// 指定图表的配置项和数据
		var option = {
			//backgroundColor: '#000000',
		    title: {text:'输变电及电缆设备状态监测系统装置运行状况',
		    	x: 'right'
		    		},
		    color: colors,
		    xAxis: { data: xData,type : 'category', 
		    	boundaryGap : true ,
		    	axisTick: {
                alignWithLabel: false
                }
		    },
		    grid: {
		        right: '2%',
		        left:'4%' ,
		        height:'auto'
		    },
		    legend: {
		        data : names,
		        x: 'left'
		    },
		    yAxis: {},
		    tooltip: { 
		    	show : false,
		    	trigger: 'axis',
		    	axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    series: series
		};
		
		me.chart.on('click', function (params) {
		   var depts = idData[params.dataIndex];
		   
		   var index = params.seriesIndex;
		   
		   var parm = depts+"&"+index;
		   var mvc = new echartsdetail.views.MainViewController();
	    	var detailview = mvc.getView();
	    	
	    	var window = new mx.windows.WindowManager().create({
	    		resizable: false
	    	});	
	    	
	    	detailview.dataGrid.setFilter(parm);
	    	
			detailview.dataGrid.load();
			window.setWidth("900");
	    	window.setHeight("500");
	    	window.setTitle("监测信息列表");
			window.setView(detailview);
			window.showDialog();
		});
		
		// 使用刚指定的配置项和数据显示图表。true 表示不跟之前的数据合并
		me.chart.setOption(option, true);
	};
	
	
	me.clear = function() {
		me.chart.clear();
	};
	me.dispose = function() {
		me.chart.dispose();
	};

	return me.endOfClass(arguments);
};
