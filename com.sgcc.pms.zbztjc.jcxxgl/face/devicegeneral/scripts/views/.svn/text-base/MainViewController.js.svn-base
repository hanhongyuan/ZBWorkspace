$ns("devicegeneral.views");
$import("devicegeneral.views.MainView");
$import("mx.containers.HtmlContainer");
devicegeneral.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    me.url = "~/rest/chart/queryDeviceGeneral";
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new devicegeneral.views.MainView({ controller: me });
        }
        return me.view;
    };
      
    me._onactivate = function()
    {
		me.view.drawPage();
		
    	me.view.initChart();

    	me.loadChart();
		
    };
    // hsplit拖动时触发
    me._resChar = function()
    {
    	me.view.chart.chart.resize();
    }
    
    // hsplit拖动时触发
    me._resChara = function(e)
    {
    	me.view.chart.chart.resize();
    }
     
    me.loadChart = function() {
    	me.view.loadChart(me.url);
    };
    
    /**
     * 初始化DataGrid
     */
    me._initDataGrid = function(){
    	var _dataGrid = me.view.getDataGrid();
    	_col = _dataGrid.$body.find("td").not("#DWBM,#DWMC,#SDSSJRL,#rownumber,#BDSSJRL,#DLSSJRL");
		//统计值添加方法
    	for(var i=0;i<_col.length;i++){
			if("0" != _col[i].innerText && "" != _col[i].innerText ){
	    		_col[i].setAttribute("class","ywjxLink");
	    	}
		}
    	
    	_dataGrid.$body.find(".ywjxLink").click(function(e) {
			var item = $(this).parent().data();
			var colName = e.target.id;
			$(this)[0].style.color = "#999999";
			
			showDetail(item, colName);
		});
    }
    
    showDetail = function(item, colName)
    {
    	var DWBM = item.item.id;
	    var ssdw = "DWBM=" + DWBM;
	    
	   
	   if("ZYS"==colName.substring(colName.length-3,colName.length))
	   {
	   		var sta = "st=00501" ;
	   }
	   else  if("TYS"==colName.substring(colName.length-3,colName.length))
	   {
	   		var sta = "st=00502" ;
	   } 
	   else  if("JXS"==colName.substring(colName.length-3,colName.length))
	   {
	   		var sta = "st=00504" ;
	   } 
	   else 
	   {
		   var sta = "st=00501" ;
	   }
	   if("SD"==colName.substring(0,2))
	   {
	   		var lx = "lx=sd";
	   }
	   else if("BDZS"==colName)
	   {
		   var lx = "lx=dz";
	   }
	   else if("DLZS"==colName)
	   {
		   var lx = "lx=dls";
	   }
	   else if("BD"==colName.substring(0,2))
	   {
	   		var lx = "lx=bd";
	   }
	   else if("DL"==colName.substring(0,2))
	   {
	   		var lx = "lx=dl";
	   }
	   else if("XLS"==colName)
	   {
		   var lx = "lx=xl";
	   }
	   if("SSJRZZS"==colName.substring(colName.length-7,colName.length))
	   {
		   var sfss= "sfss=T";
		   var sta = "st=00501";
	   }
	   else
	   {
		   var sfss= "sfss=all";
	   }
    	var parm = ssdw+"&"+sta+"&"+lx+"&"+sfss;

    	var mvc = new generaldetail.views.MainViewController();
    	var detailview = mvc.getView();
    	var window = new mx.windows.WindowManager().create({
	    		resizable: false
	    	});	

	   
    	detailview.dataGrid.setFilter(parm);
	    	
		detailview.dataGrid.load();
		window.setWidth("800");
    	window.setHeight("500");
    	window.setTitle("详细信息列表");
		window.setView(detailview);
		window.showDialog();


    }
    
    
    return me.endOfClass(arguments);
};