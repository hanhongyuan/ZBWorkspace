$ns("userlogger.views");
$import("mx.datacontrols.DataGridSearchBox");
$import("userlogger.utils.LeftUpbarChart");
$import("mx.containers.HSplit");
$import("mx.controls.Label");
$import("mx.controls.Button");
$import("mx.containers.Container");
$import("mx.editors.TextEditor");
$import("mx.editors.LinkEditor");
$import("mx.editors.DateTimeEditor");
$import("mx.utils.DateUtil")
$import("mx.editors.CheckListEditor");

userlogger.views.ChartView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    me.leftchartUp = null;
   
    base.init = me.init;
    me.init = function()
    {
        base.init();
        
        _initControls();
    };
    
    function _initControls()
    {	
     _initHSplit();
     _initSearch();
     drawPage();
	 me.on("activate", me.controller._onactivate);
    };
    
      //上下布局
    function  _initHSplit(){
    	me.hSplit = new mx.containers.HSplit({
    		id: "hSplit_up",
			rows : "60px, auto",
			height:"100%",width:"100%",borderThick:"0px"
    	});
    	me.addControl(me.hSplit);
    }  

    function _initSearch()
    {	
    	me.searchBox = new mx.datacontrols.DataGridSearchBox({
		itemNumOfRow:3,
		captionColumnWidth:60,
		fields:[
		    {name:"minTime",caption:"开始时间",editorType:"DateTimeEditor",formatString:"yyyy-MM-dd",width : 180,onchanged:checkTime},
		    {name:"maxTime",caption:"结束时间",editorType:"DateTimeEditor",formatString:"yyyy-MM-dd",width : 180,onchanged:checkTime},
		    {name:"tjlx",caption:"统计类型",editorType:"DropDownEditor",width : 180,
				items:[
				       {text:"用户名",value:"uname"},{text:"IP地址",value:"uip"},
					{text:"操作类型",value:"czlx"},{text:"事件类型",value:"etype"}]}
		]
	});
    	me.searchBox.setFields();
		for(var i=0;i<me.searchBox.editors.length;i++){
			me.searchBox.editors[i].$e.find("#deleteContainer").hide();
		}
		me.searchBox.editors["tjlx"].setValue("uname");
		var now = new Date();
		me.minTime = formatDate(now.addYears(-1),"yyyy-MM-dd");
		me.maxTime = formatDate(now,"yyyy-MM-dd");
		me.searchBox.editors["minTime"].setValue(me.minTime);
		me.searchBox.editors["maxTime"].setValue(me.maxTime);
		me.searchBox.resetButton.setVisible(false);
		me.searchBox.searchButton.off("click");
		me.searchBox.searchButton.on("click",me.controller._query);
		
		me.hSplit.addControl(me.searchBox,0);
      	// 图表
    	me.chartView = new mx.containers.Container({id: "chartView" });
    	//加入图表区
    	me.hSplit.addControl(me.chartView,1);

    }
    
  //时间校验
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
	
  
  //页面样式绘制
    function drawPage() {	
    	me.chartView.setCss({ 
    		width: '95%', 
    		height: '95%',
    	    display: 'inline-block',
    	    margin: 'auto'
    	});
    	
    }
    
	me.getSearchBox = function(){
		return me.searchBox
	}
    
	me.endOfClass(arguments)
    return me;
};