$ns("userlogger.views");

userlogger.views.DetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    base.init = me.init;
    me.init = function(){
    	me.permissionID = "-1";
        base.init();
        _initControls();
    };
    
    //----声明mx组件变量------
	var _DataGrid1 = null;
	var _Window = null;
	
    function _initControls(){
    	_init_DataGrid1();
    	
        me.on("activate", me.controller._onactivate);
    }
	
    function _init_DataGrid1(){
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl:userlogger.mappath("~/rest/userlogger/queryInfo"),
			iscID:"-1",
			loadMeta:false
		});
		
		_DataGrid1 = new mx.datacontrols.DataGrid({
			searchBox:new mx.datacontrols.DataGridSearchBox({
				itemNumOfRow:8,
				height:"35",
				width:"100%",
				captionColumnWidth:60,
				fields:[
				    {name:"minTime",caption:"时间",editorType:"DateTimeEditor",formatString:"yyyy-MM-dd",width:"120px",onchanged:checkTime},
				    {name:"maxTime",caption:"至",editorType:"DateTimeEditor",formatString:"yyyy-MM-dd",width:"120px",onchanged:checkTime}
				]
			}),
			columns:[
				{name:"CZLX",caption:"用户操作类型",editorType:"TextEditor",dataAlign:"center"},
				{name:"CZNUM",caption:"用户操作次数",editorType:"TextEditor",dataAlign:"center"}
			],
			
			id:"DataGrid1",
			height:"100%",
			width:"100%",
			displayRowNumber:true,
	        rowNumberColWidth:40,
	        enableCellTip:true,
			pageSize:10,
			allowPaging:true,
			pageIndex:1,
			pageNaviBar:new mx.datacontrols.PageNaviBar({
				id:"PageNaviBar1",
				pageSize:10,
				pageIndex:1
			}),
			entityContainer: gridEntityContainer
		});
		for(var i=0;i<_DataGrid1.searchBox.editors.length;i++){
			_DataGrid1.searchBox.editors[i].$e.find("#deleteContainer").hide();
		}
		var gridColumns = _DataGrid1.columns;
        for ( var i = 0; i < gridColumns.length; i++) {
            gridColumns[i].setAlign("center");
        }
		me.maxTime = new Date();
		me.minTime = new Date(me.maxTime.getTime()-7*24*60*60*1000);
		_DataGrid1.searchBox.editors["minTime"].setValue(me.minTime);
		_DataGrid1.searchBox.editors["maxTime"].setValue(me.maxTime);
		
		_DataGrid1.searchBox.resetButton.setVisible(false);
		_DataGrid1.searchBox.searchButton.off("click");
		_DataGrid1.searchBox.searchButton.on("click",me.controller.Queryinfo);
		
		me.addControl(_DataGrid1);
	}
	
	me.getDataGrid1 = function(){
		return _DataGrid1;
	}
	//时间校验
    function checkTime(){
		var editors = _DataGrid1.searchBox.editors;
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
	
	me.endOfClass(arguments)
    return me;
};