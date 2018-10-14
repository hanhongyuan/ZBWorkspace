$ns("devicegeneral.views");

$import('generalchar.utils.Chart');
$import("mx.controls.ToolBar");
$import("mx.datacontrols.GroupHeaderGrid");
$import("mx.containers.HSplit");
devicegeneral.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initHsplit();
        _initControls();
        _initChart();
        me.on("activate", me.controller._onactivate);
        
    };
    
    //上下50%分割
    function _initHsplit()
    {
    	me.hSplit = new mx.containers.HSplit({
//    		 rows:"50%, 50%",
//    		 
//    		 resizable:true,
//    		 onresize:me.controller._resChar
//    		 
    		rows:"100%, 0%",
   		 
   		   resizable:false,
   		   onresize:me.controller._resChar
   		  
    		 
    	});
    	me.addControl(me.hSplit);
    }
        
    //初始化上方表格数据
    function _initControls()
    {
    	var restUrl = "~/rest/devicegeneral/geddevicenum";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : devicegeneral.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "DWBM"
		});
		
		me.dataGrid = new mx.datacontrols.GroupHeaderGrid({
    		width : "100%",
    		height : "auto",
    		position : "absolute",
    		bottom : "0px",
    		top : "0px",
    		columns: [
    		{name: "DWBM", caption: "DWBM" ,visible : false},
    		{ name: "DWMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
    		{
	    		name: "group1",
	    		caption: "输电",
	    		columns: [
			    		{ name: "SDZYS", caption: "在运装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "SDTYS", caption: "停运装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "SDJXS", caption: "调试装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "XLS", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "SDSSJRZZS", caption: "实时在线装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "SDSSJRL", caption: "实时在线率", dataAlign:"center",editorType: "TextEditor" }
			    		]
    		},
    		{
        		name: "group2",
        		caption: "变电",
        		columns: [
						{ name: "BDZYS", caption: "在运装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDTYS", caption: "检修/调试装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZS", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDSSJRZZS", caption: "实时在线装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDSSJRL", caption: "实时在线率", dataAlign:"center",editorType: "TextEditor" }
						]
        	},
        	{
        		name: "group3",
        		caption: "电缆",
        		columns: [
		        		{ name: "DLZYS", caption: "在运装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "DLTYS", caption: "检修/调试装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "DLZS", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "DLSSJRZZS", caption: "实时在线装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "DLSSJRL", caption: "实时在线率", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}],
        	
    		entityContainer : gridEntityContainer,
    		displayCheckBox : false,
    		displayRowNumber : true,
    		allowPaging : false,
    		displayPrimaryKey : false,
    		displayColSplitLine : true,
    		allowEditing : false,
    		rowNumberColWidth : 40,
    		onload:me.controller._initDataGrid
    		});
		me.dataGrid.load();
		me.hSplit.addControl(me.dataGrid, 0);		
    }
 
   //初始化 图表
    function _initChart()
    {
    	// 页面中心
    	//me.container = new mx.containers.Container();
    	

    	//me.container.onresize();
    	//me.hSplit.addControl(me.container, 1);	
    	// 图表
    	me.chartView = new mx.containers.Container({id: "chartView" ,left:"1%",top:"1%"});
    	
    	me.hSplit.addControl(me.chartView,1);
    }
    
   //绘制图表
    me.initChart = function() {
    	me.chart = new generalchar.utils.Chart(me.chartView.id);
    };
    
    me.loadChart = function(url) {
    	me.chart.loadChart(url);
    }; 
    
    
    
    //页面样式修改
    me.drawPage = function() {
    	
//    	me.container.setCss({
//    		
//    	    width: '100%',
//	        height: '100%',
//	        margin: 'auto'
//    	});
    	
    	me.chartView.setCss({ 
    		border: '1px solid blue',
    		//border-radius: '5px',
    		
    		width: '98%', 
    		height: '98%',
    	    display: 'inline-block',
    	    margin: 'auto'
    	});
   
    }
    //获取grid
    me.getDataGrid = function()
    {
    	return me.dataGrid;
    }
    return me.endOfClass(arguments);
};