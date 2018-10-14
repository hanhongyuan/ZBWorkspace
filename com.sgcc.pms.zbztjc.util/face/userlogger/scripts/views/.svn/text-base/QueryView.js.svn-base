$ns("userlogger.views");

$import("mx.containers.TabControl");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");
$import("userlogger.views.ChartViewController");

userlogger.views.QueryView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.treeView = null;
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initTabControlView();
        _initControls();
    };
    
    
  //初始化tab
	function _initTabControlView(){
    	_tabView = new mx.containers.TabControl({
					onselectionchanging : me.controller._tabSelection_changing,
					onselectionchanged : me.controller._selection_changed,
					pages : [
						{text : "表格展示",name : "query",autoInit : true},
						{text : "图形展示",name : "statistics",autoInit : true}				
					]
				});
		// 设置TabControl的样式
    	_tabView.$("div#head a").css({"width":"132px","height":"30px"});
    	_tabView.$("a#statistics span").css("height","20px");
    	_tabView.$("span.tab-text").css({"width":"132px","height":"20px","display":"inline-block","line-height":"20px","text-align":"center"});
		me.addControl(_tabView);
    }
    
    
    function _initControls()
    {
    	var restUrl = "~/rest/userlogger/queryInfo";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : userlogger.mappath(restUrl),
			loadMeta : false,
			iscID : "-1" // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			
		});

		/* 初始化 DataGrid */
		me.dataGrid = new mx.datacontrols.DataGrid({
			// alias : "montringgrid",
			// 设置显示列。
			columns : [{name:"USERNAME",caption:"用户名称",editorType:"TextEditor",dataAlign:"center",width:"190px"},
			           {name:"CZLX",caption:"操作类型",editorType:"TextEditor",dataAlign:"center",width:"190px"},
			           {name:"EVENTTYPE",caption:"事件类型",editorType:"TextEditor",dataAlign:"center",width:"190px"},
			           {name:"CZNUM",caption:"操作次数",editorType:"TextEditor",dataAlign:"center",width:"190px"}
			           ],
			// 设置查询条件。
			searchBox : new mx.datacontrols.DataGridSearchBox({
				fields : [
						    {name:"userName",caption:"用户",editorType:"TextEditor",width : 180},
						    {name:"userIP",caption:"客户端IP",editorType:"TextEditor",width : 180},
						    {name:"czlx",caption:"操作类型",editorType:"DropDownEditor",
						    	items:[{text:"--请选择--",value:"default"},{text:"生成",value:"create"},
						    	       {text:"新增",value:"add"},{text:"修改",value:"modify"},
						    	       {text:"删除",value:"delete"},{text:"查询",value:"query"},
						    	       {text:"告警",value:"alarm"},{text:"导出",value:"export"},
						    	       {text:"统计",value:"stat"},{text:"排序",value:"sort"}],width : 180},
							{name:"eventType",caption:"事件类型",editorType:"DropDownEditor",
								items:[{text:"--请选择--",value:"default"},{text:"业务事件",value:"transaction"},
								       {text:"系统事件",value:"system"}],width : 180},
						    {name:"minTime",caption:"开始时间",editorType:"DateTimeEditor",formatString:"yyyy-MM-dd",onchanged:_searchParamChanged,width : 180},
						    {name:"maxTime",caption:"结束时间",editorType:"DateTimeEditor",formatString:"yyyy-MM-dd",onchanged:_searchParamChanged,width : 180}
						],
				itemNumOfRow : 3
			}),
			displayRowNumber : true,
			displayCheckBox : false,
			rowNumberColWidth : 50,
			displayColSplitLine : true,
			entityContainer : gridEntityContainer,
			enableCellTip : true
		});
		var but = me.dataGrid.searchBox.$e.find(".buttonTd")["0"].width="100px";
		for(var i=0;i<me.dataGrid.searchBox.editors.length;i++){
			me.dataGrid.searchBox.editors[i].$e.find("#deleteContainer").hide();
		}
		me.dataGrid.searchBox.editors["czlx"].setValue("default");
		me.dataGrid.searchBox.editors["eventType"].setValue("default");
		me.maxTime = new Date();
		me.minTime = new Date(me.maxTime.getTime()-7*24*60*60*1000);
		me.dataGrid.searchBox.editors["minTime"].setValue(me.minTime);
		me.dataGrid.searchBox.editors["maxTime"].setValue(me.maxTime);
		
		me.dataGrid.searchBox.resetButton.setVisible(false);
		me.dataGrid.searchBox.searchButton.off("click");
		me.dataGrid.searchBox.searchButton.on("click",me.controller.Queryinfo);
		_tabView.pages["query"].addControl(me.dataGrid);
		me.dataGrid.load();
		me.statistics =new userlogger.views.ChartViewController().getView();
		_tabView.pages["statistics"].addControl(me.statistics);
    }
    
	//时间判断，起始时间不能大于终止时间
	function _searchParamChanged()
	{
		
		var editors = me.dataGrid.searchBox.editors;
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
    
    /**
	 * 获取DataGrid网格列表对象
	 */
	me.getDataGrid = function() {
		return me.dataGrid;
	}
    
    return me.endOfClass(arguments);
};