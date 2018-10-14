$ns("userlogger.views");

$import("mx.datacontrols.DataGridSearchBox");
userlogger.views.MainView=function(){
	var me = $extend(mx.views.View);
	var base = {};
	me.DataGrid = null;
	me.searchBox = null;
	base.init = me.init;
	me.init = function () {
		me.permissionID = "-1";
		base.init();
		_initControls();
	};
	
	//----声明mx组件变量------
	var _HSplit1 = null;
	var _HSplit1Area0 = null;
	var _ToolBar = null;
	var _HSplit1Area1 = null;
	var _Window = null;
	
	function _initControls(){
		//---调用初始化函数-----
		_init_HSplit1();
		_init_HSplit1Area0();
		_init_searchBox();
		_init_ToolBar();
		_init_HSplit1Area1();
		_init_DataGrid1();
		_initDetailWindow();
	  
		me.on("activate", me.controller._onactivate);
	}
	
	//-----定义初始化函数-----
	function _init_HSplit1(){
		_HSplit1=new mx.containers.HSplit({
			id:"HSplit1",
			orientation:"horizontal",
			rows:"100,auto",
			height:"100%",
			width:"100%",
			borderThick:"0px"
		});
		me.addControl(_HSplit1);
	}
	
	function _init_HSplit1Area0(){
		_HSplit1Area0 = new mx.containers.Container({
			id:"HSplit1Area0",
			layout:"mx.layouts.AbsoluteLayout"
		});
		
		_HSplit1.addControl(_HSplit1Area0, 0);
	}
	function _init_searchBox(){
		me.searchBox = new mx.datacontrols.DataGridSearchBox({
			itemNumOfRow:3,
			height:"100",
			captionColumnWidth:60,
			fields:[
			    {name:"userName",caption:"用户",editorType:"TextEditor",width : 180},
			    {name:"userIP",caption:"客户端IP",editorType:"TextEditor",width : 180},
			    {name:"czlx",caption:"操作类型",editorType:"DropDownEditor",
			    	items:[{text:"--请选择--",value:"default"},{text:"生成",value:"create"},
			    	       {text:"新增",value:"add"},{text:"修改",value:"modify"},
			    	       {text:"删除",value:"delete"},{text:"查询",value:"query"},
			    	       {text:"告警",value:"alarm"},{text:"导出",value:"export"},
			    	       {text:"统计",value:"stat"},{text:"排序",value:"sort"},
			    	       {text:"登录",value:"login"},{text:"登出",value:"logout"},
			    	       {text:"越权",value:"excessAuthority"}],width : 180},
				{name:"eventType",caption:"事件类型",editorType:"DropDownEditor",
					items:[{text:"--请选择--",value:"default"},{text:"业务事件",value:"transaction"},
					       {text:"系统事件",value:"system"}],width : 180},
			    {name:"minTime",caption:"开始时间",editorType:"DateTimeEditor",formatString:"yyyy-MM-dd",onchanged:checkTime,width : 180},
			    {name:"maxTime",caption:"结束时间",editorType:"DateTimeEditor",formatString:"yyyy-MM-dd",onchanged:checkTime,width : 180}
			]
		});
		me.searchBox.setFields();
		var but = me.searchBox.$e.find(".buttonTd")["0"].width="100px";
		for(var i=0;i<me.searchBox.editors.length;i++){
			me.searchBox.editors[i].$e.find("#deleteContainer").hide();
		}
		me.searchBox.editors["czlx"].setValue("default");
		me.searchBox.editors["eventType"].setValue("default");
		var now = new Date();
		me.minTime = formatDate(now.addYears(-1),"yyyy-MM-dd");
		me.maxTime = formatDate(now,"yyyy-MM-dd");
		me.searchBox.editors["minTime"].setValue(me.minTime);
		me.searchBox.editors["maxTime"].setValue(me.maxTime);
		//me.searchBox.searchButton.setLeft("-200px");
		me.searchBox.resetButton.setText("导出");
		me.searchBox.searchButton.off("click");
		me.searchBox.searchButton.on("click",me.controller.loadData);
		
		me.searchBox.resetButton.off("click");
		me.searchBox.resetButton.on("click",me.controller.exportData);
		
		_HSplit1Area0.addControl(me.searchBox);
	}
	function _init_ToolBar(){		
		_ToolBar = new mx.controls.ToolBar({
			id:"ToolBar",
			height:"30",
			direction:"horizontal",
			width:"100%",
			layoutConfigs:{left:0,top:60},
			itemAlign:"left",
			items:[
				{id:"ToolBarItem",text:"审计查阅",width:"80",imageUrl:userlogger.mappath("~/userlogger/resources/icons/query.png"),
					onclick:me.controller.tjfxOnclick},
				{id:"ToolBarItem",text:"审计上限设定",width:"100",imageUrl:userlogger.mappath("~/userlogger/resources/icons/set.png"),
					onclick:me.controller.limitSetClick}
			]
		});
		
		_HSplit1Area0.addControl(_ToolBar);
	}
	
	function _init_HSplit1Area1(){
		_HSplit1Area1 = new mx.containers.Container({
			id:"HSplit1Area1",
			layout:"mx.layouts.AbsoluteLayout"
		});
		
		_HSplit1.addControl(_HSplit1Area1, 1);
	}
	
	function _init_DataGrid1(){
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl:userlogger.mappath("~/rest/userlogger/loadData"),
			iscID:"-1",
			loadMeta:false
		});
		
		me.DataGrid = new mx.datacontrols.DataGrid({
			columns:[
				{name:"USERID",caption:"用户ID",editorType:"TextEditor",dataAlign:"center",visible:false},
				{name:"USERNAME",caption:"用户名称",editorType:"TextEditor",dataAlign:"center"},
				{name:"DEPTNAME",caption:"部门名称",editorType:"TextEditor",dataAlign:"center",width:"140px"},
				{name:"USERIP",caption:"用户登录IP",editorType:"TextEditor",dataAlign:"center",width:"100px"},
				{name:"TRANSACTIONTYPE",caption:"业务分类",editorType:"TextEditor",dataAlign:"center",width:"250px"},
				{name:"CZLX",caption:"操作类型",editorType:"TextEditor",dataAlign:"center",width:"80px"},
				{name:"EVENTTYPE",caption:"事件类型",editorType:"TextEditor",dataAlign:"center",width:"100px"},
				{name:"CZNR",caption:"操作内容",editorType:"TextEditor",dataAlign:"center",width:"250px"},
				{name:"CZTIME",caption:"操作时间",editorType:"TextEditor",dataAlign:"center",width:"140px"},
				{name:"RESULT",caption:"操作结果",editorType:"TextEditor",dataAlign:"center"}
			],
			id:"me.DataGrid",
			//sorter: "DEPTNAME ASC, CZLX DESC",
			height:"100%",
			width:"100%",
			displayRowNumber:true,
	        rowNumberColWidth:40,
	        enableCellTip:true,
            autoWrap: true,
			pageSize:20,
			allowPaging:true,
			allowSorting:true,//列允许排序 
			//sortOrder:true,
			pageIndex:1,
			pageNaviBar:new mx.datacontrols.PageNaviBar({
				id:"PageNaviBar1",
				pageSize:20,
				pageIndex:1
			}),
			entityContainer: gridEntityContainer
		});
		var gridColumns = me.DataGrid.columns;
        for ( var i = 0; i < gridColumns.length; i++) {
            gridColumns[i].setAlign("center");
        }
        
        me.DataGrid.$e.find(".headTable").find("td").click(function(){
        	me.controller.orderQuery(this);
        });
        
		_HSplit1Area1.addControl(me.DataGrid);
	}
	
	me.getDataGrid = function(){
		return me.DataGrid;
	}
	
	me.getSearchBox = function(){
		return me.searchBox
	}
	function checkTime(){
		var editors = me.searchBox.editors;
		var now = formatDate(new Date(),"yyyy-MM-dd");
		//日期时间段
		if(editors["minTime"].value > now){
			mx.indicate("info","开始时间选择不能晚于当前时间!");
			editors["minTime"].setValue(me.minTime);
		}
		if(editors["maxTime"].value > now){
			mx.indicate("info","结束时间选择不能晚于当前时间!");
			editors["maxTime"].setValue(me.maxTime);
		}
		if(editors["minTime"].value > editors["maxTime"].value){
			mx.indicate("info","开始时间不能晚于结束时间!");
			editors["minTime"].setValue(editors["maxTime"].value);
		}
	}
	//格式化日期
	function formatDate(date,format){
    	var paddNum = function(num){
    		num += "";
   			return num.replace(/^(\d)$/,"0$1");
        }
		//指定格式字符
        var cfg = {
        	yyyy : date.getFullYear(), //年 : 4位
          	yy : date.getFullYear().toString().substring(2),//年 : 2位
          	M  : date.getMonth() + 1,  //月 : 如果1位的时候不补0
          	MM : paddNum(date.getMonth() + 1), //月 : 如果1位的时候补0
          	d  : date.getDate(),   //日 : 如果1位的时候不补0
          	dd : paddNum(date.getDate()),//日 : 如果1位的时候补0
          	hh : paddNum(date.getHours()),  //时
          	mm : paddNum(date.getMinutes()), //分
          	ss : date.getSeconds() //秒
		}
        format || (format = "yyyy-MM-dd hh:mm:ss");
        return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
	}
	
	function _init_Window() {		
		if(_Window == null || ((_Window.reusable==false) && _Window.disposed==true)) {
			_Window = userlogger.context.windowManager.create({
				entry:true
			});
		}
		_Window.on("activate", function() {
			_Window.setView(me);
		});
		_Window.on("close", function(e){
		    $.each(_Window.controls, function(i, o){
				o.$e.detach();
			});
		});
	}
	
	me.getWindow = function() {
		_init_Window();
		return _Window;
	};
	/**
	 * 初始化公共弹窗
	 */
	function _initDetailWindow() {
		me.detailWin = mxpms.utils.PortalUtil.getGlobalWindowManage().create({
			reusable : true,// 是否复用
			width : 900,
			height : 500,
			resizable : false,
			top : "center",
			left : "center",
			title : "故障信息"
		});
	}
	
	me.findControlById = function(controlId){
		try{
			return me.findControl("id", controlId);
		} catch(err) {
			mx.indicate("info","未找到对应的mx控件:    "+ err.message);
			return null;
		}	
	};
    return me.endOfClass(arguments);
};