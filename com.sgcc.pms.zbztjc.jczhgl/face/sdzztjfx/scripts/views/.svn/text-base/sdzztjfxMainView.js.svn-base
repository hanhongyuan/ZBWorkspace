$ns("sdzztjfx.views");

sdzztjfx.views.sdzztjfxMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.objID = null;
    
    me.form = null;
    
    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	_initHSplit();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
    	me.hSplit = new mx.containers.HSplit({
    		id: "hSplit_up",
    		alias:"bdqxtj",
			rows : "38%, auto",
			resizable:false
    	});
		// 最新数据展示
    	me.latest = new mx.containers.Container();
    	// 图表
    	me.UpchartView = new mx.containers.Container({id: "UpchartView" });
    	// 图表
    	me.DownJclxchartView = new mx.containers.Container({id: "DownJclxchartView" });
    	me.DownDydjchartView = new mx.containers.Container({id: "DownDydjchartView" });
    	me.DownSccjchartView = new mx.containers.Container({id: "DownSccjchartView" });
    	me.hSplit.addControl(me.UpchartView,0);
    	me.hSplit.addControl(me.latest,1);
    	me.hSplit.addControl(me.DownJclxchartView,1);
    	me.hSplit.addControl(me.DownDydjchartView,1);
    	me.hSplit.addControl(me.DownSccjchartView,1);
    	me.addControl(me.hSplit);	
    }
    
    
    
    me.initChart = function() {
      	me.Upchart = new zztjfxcharts.utils.UpbarChart(me.UpchartView.id);
      	me.DownJclxchart = new zztjfxcharts.utils.DownJclxChart(me.DownJclxchartView.id);
        me.DownDydjchart = new zztjfxcharts.utils.DownDydjChart(me.DownDydjchartView.id);
      	me.DownSccjchart = new zztjfxcharts.utils.DownSccjChart(me.DownSccjchartView.id);
    };
    
    me.loadUpChart = function(url) {
      	me.Upchart.loadChart(url);
    };
    me.loadDownJclxChart = function(url, params) {
      	me.DownJclxchart.loadChart(url, params);
    };
    me.loadDownDydjChart = function(url, params) {
      	me.DownDydjchart.loadChart(url, params);
    };
    me.loadDownSccjChart = function(url, params) {
      	me.DownSccjchart.loadChart(url, params);
    };
    me.drawLatest = function(text) {
    	me.latest.append($("<div id='tjfxtext' style='font-size: 36px;text-align:center;'></div>"));
    	me.latest.$e.find("#tjfxtext")[0].innerHTML =text;
    }
  //页面样式绘制
    me.drawPage = function() {	
    	me.UpchartView.setCss({ 
    		width: '98%', 
    		height: '98%',
    	    margin: '0 auto'
    	});
    	me.DownJclxchartView.setCss({
    		width: '474px',
    		height: '474px',
    	    display:'inline-block',
    	    border:'1px solid #000'
    	});
    	me.DownDydjchartView.setCss({ 
    		width: '474px',
    		height: '474px',
    	    display:'inline-block',
    	    border:'1px solid #000'
    	});
    	me.DownSccjchartView.setCss({ 
    		width: '474px',
    		height: '474px',
    	    display:'inline-block',
    	    border:'1px solid #000'
    	});
    	me.latest.setCss({
    		width: '98%',
        	height: '50px',
        	margin: '0 auto'
    	});
    }
    
    me.getUpchart = function (){
    	return me.Upchart;
    }
    
    return me.endOfClass(arguments);
};