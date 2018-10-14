$ns("chart.views");

$import('ztjcChart.utils.Chart');
$import("mx.controls.Label");
$import("mx.controls.Button");
$import("mx.containers.Container");
$import("mx.editors.TextEditor");
$import("mx.editors.LinkEditor");
$import('mx.editors.DateTimeEditor');
$import("mx.editors.CheckListEditor");

chart.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        
        _initControls();
    };
    
    function _initControls()
    {
    	_initViews();
        _initEvents();

        me.on("activate", me.controller._onactivate);
    }
    
    function _initViews() {
    	// 页面标题
    	me.label = new mx.editors.LinkEditor({
    			    "type" : "text",  //指定链接的类型。
    			    "text" : "?",
    			    onclick: me.controller._labelClick
    	});
    	// 搜索工具栏
    	me.toolBar = new mx.containers.Container({ id: "toolBarView" });
    	
    	// 日期条件
    	var checkListEditor = new mx.editors.CheckListEditor({
    		type: "radio",
    		items: [
    		    {text: "近3天", value: "3", width: 65, checked: true},
    		    {text: "近1周", value: "7", width: 65},
    		    {text: "近1月", value: "30", width: 65},
    		    {text: "时间范围:", value: "x"}
    		],
    		columns: 4,
		    onitemchanged: me.controller._serachChanged
    	});
    	var date = new Date();
    	var eDate = date.toLocaleDateString().replace(/\//g, '-');
    	date.setDate(date.getDate()-3);
    	var sDate = date.toLocaleDateString().replace(/\//g, '-');
    	// 开始日期
    	var startDate = new mx.editors.DateTimeEditor({
    	    value: sDate,
    	    readOnly: true,
    	    formatString: "yyyy-MM-dd"
    	});
    	// 结束日期
    	var endDate = new mx.editors.DateTimeEditor({
    	    value: eDate,
    	    readOnly: true,
    	    formatString: "yyyy-MM-dd"
    	});
    	var labelEditor = new mx.controls.Label({ text: "至" });
    	
    	var queryButton = new mx.controls.Button({
    	    text: "查询"
    	});
    	
    	me.toolBar.addControls([checkListEditor, startDate, labelEditor, endDate, queryButton]);
    	
    	// 页面中心
    	me.container = new mx.containers.Container();
    	
    	// 图表
    	me.chartView = new mx.containers.Container({id: "chartView" });
    	
    	
    	me.container.addControls([me.chartView]);
    	
    	// 监测量
    	me.monitorCheckbox = new mx.editors.CheckListEditor({
    		type: "checkbox",
    		items: [],
    		columns: 4,
    		onitemchanged: me.controller._monitorChanged
    	});
        
    	addControl([ me.label, me.toolBar, me.container, me.monitorCheckbox ]);
    }
    
    function _initEvents() {
    	me.getControl("查询按钮").on("click", me.controller._queryClick);
    	
    }
    
    me.drawPage = function() {
    	me.label.setCss({
//    		height: '4%',
//	        'text-align': 'right'
    	    'margin-left': '2%'
    	});
    	me.label.$e[0].firstChild.firstChild.style.fontSize = '20px';
    	
    	me.toolBar.setCss({ 
    		width: '95%',
        	height: '10%',
        	margin: 'auto'
    	});
    	
    	me.getControl("查询框").setCss({
    		width: '42%',
	        height: 'auto',
	        top: '20%',
	        position: 'relative',
	        display: 'inline-block'
    	});
    	
    	me.getControl("开始日期").setCss({ width: '15%' });
    	
    	me.getControl("至").setCss({ width: '3%', 'text-align': 'center' });
    	
    	me.getControl("结束日期").setCss({ width: '15%' });
    	
    	me.getControl("查询按钮").setCss({ 'margin-left': '5%' });
    	
    	me.container.setCss({
    	    width: '95%',
	        height: '77%',
	        margin: 'auto'
    	});

    	me.chartView.setCss({ 
    		width: '95%', 
    		height: '100%',
    		'float': 'left'
    	});
    	
    	me.monitorCheckbox.setCss({ 
    		width: '95%',
    		margin: 'auto',
    	    'margin-top': '1%'
    	});
    	
    	me.setCss({
    	    overflow: 'auto'
    	});
    };
    
    me.initChart = function() {
    	me.chart = new ztjcChart.utils.Chart(me.chartView.id);
    };
    
    me.loadChart = function(url, params) {
    	me.chart.loadChart(url, params);
    };
    
    me.clearChart = function() {
    	me.chart.clear();
    };
    
    me.getValue = function(name) {
    	return me.getControl(name).value;
    };
    
    me.getControl = function(name) {
    	switch (name) {
			case "查询按钮":
				return me.toolBar.controls[4];
				break;
			case "查询框":
				return me.toolBar.controls[0];
				break;
			case "开始日期":
				return me.toolBar.controls[1];
				break;
			case "结束日期":
				return me.toolBar.controls[3];
				break;
			case "图表":
				return me.container.controls[0];
				break;
			case "至":
				return me.toolBar.controls[2];
				break;

			default:
				break;
		}
    };
    
    function addControl(controls) {
    	for(var i = 0; i < controls.length; i++) {
    		 me.addControl(controls[i]);
    	}
    }
    
    return me.endOfClass(arguments);
};