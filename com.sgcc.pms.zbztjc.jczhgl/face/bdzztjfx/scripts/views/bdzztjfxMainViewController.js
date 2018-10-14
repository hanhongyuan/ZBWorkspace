$ns("bdzztjfx.views");
$import("bdzztjfx.views.bdzztjfxMainView");
$import("zztjfxcharts.utils.DownJclxChart");
$import("zztjfxcharts.utils.DownDydjChart");
$import("zztjfxcharts.utils.DownSccjChart");
$import("zztjfxcharts.utils.UpbarChart");


bdzztjfx.views.bdzztjfxMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    var text = "以下为(全网)监测装置统计";
    me.urlUp = "~/rest/bdzztjfxcontrol/queryByssdw";
    me.urlDownJclx = "~/rest/bdzztjfxcontrol/queryByjclx";
    me.urlDownDydj = "~/rest/bdzztjfxcontrol/queryBydydj";
    me.urlDownSccj = "~/rest/bdzztjfxcontrol/queryBysccj";
    var filter = "ssdw=63EBEC8E-E766-40D7-ACF4-FEA945102112-00042";
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzztjfx.views.bdzztjfxMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    	me.view.drawPage();
    	me.view.initChart();
    	me.loadUpChart1();
    	me.loadLatest1(text);
    	me.loadDownJclxChartControl(filter);
    	me.loadDownDydjChartControl(filter);
    	me.loadDownSccjChartControl(filter);
    };
    me.loadUpChart1 = function() {
    	me.view.loadUpChart(me.urlUp);
    	me.view.getUpchart().chart.on("click", eConsole);
    };
    me.loadDownJclxChartControl = function() {
    	//var filter = "ssdw=63EBEC8E-E766-40D7-ACF4-FEA945102112-00042";//给初始条件
    	me.view.loadDownJclxChart(me.urlDownJclx, { filter: filter});
    };
    me.loadDownDydjChartControl = function() {
    	me.view.loadDownDydjChart(me.urlDownDydj, { filter: filter});
    };
    me.loadDownSccjChartControl = function() {
    	me.view.loadDownSccjChart(me.urlDownSccj,{ filter: filter});
    };
    me.loadLatest1 = function() {
    	me.view.drawLatest(text);
    };
    
    function eConsole(param) {  
        if (typeof param.seriesIndex == 'undefined') {  
            return;  
        }  
        if (param.type == 'click') {  
        	if(param.name=="龙江"){
        		filter = "ssdw=国网黑龙江电力";
        		param.name="国网黑龙江电力";
        	}else if(param.name=="运行"){
        		filter = "ssdw=国网运行公司";
        		param.name="国网运行公司";
        	}else{
        		filter = "ssdw=国网"+param.name+"电力";
        		param.name="国网"+param.name+"电力";
        	}
       		//filter = "ssdw="+param.name;
       		text = "以下为 ("+param.name+")监测装置统计"
       		me.loadLatest1();
            me.view.loadDownJclxChart(me.urlDownJclx, { filter: filter});
            me.view.loadDownDydjChart(me.urlDownDydj, { filter: filter});
            me.view.loadDownSccjChart(me.urlDownSccj, { filter: filter});
        }  
    }

    return me.endOfClass(arguments);
};