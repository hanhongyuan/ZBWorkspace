$ns("sddevicetj.views");
$import("sddevicetj.views.MainView");

sddevicetj.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sddevicetj.views.MainView({ controller: me });
        }
        return me.view;
    };
    me._onactive = function(e){
    	me._btnStatistics_onclick();
    };
    me._btnStatistics_onclick = function(){
    	var _dataGrid = me.view.getDataGrid();
    	
    	_dataGrid.clearColumns();
    	hidetile(_dataGrid);
    	
    	_dataGrid.load();
    	
    }
    /**
     * 导出
     */
    me._btnexport_onclick= function()
    {
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "装置统计数据列表"
        });
    }
    /**
     * 动态展示列
     */
    function hidetile(dataGird){
    	
    	var jclx = dataGird.searchBox.editors[3].value;
    	var qxhjl = false;
    	var dxjcl = false;
    	var jyzjcl = false;
    	var gtjcl = false;
    	var spjcl = false;
    	
    	var jcdlMap={"018001":"气象环境监测类","018003":"气象环境监测类","018002":"气象环境监测类","013001":"导线监测类","013002":"导线监测类",
    			"013003":"导线监测类","013004":"导线监测类","013005":"导线监测类","013006":"导线监测类","014001":"绝缘子监测类","012001":"杆塔监测类",
    			"018003":"视频监测类"};
    
    	if (jclx != "" &&jclx != null)
		{
    		
    		var cloumns = [{name: "SSWS", caption: "SSWS" ,visible : false},
    		       		{ name: "SSWSMC", caption: "所属网省名称", dataAlign:"center", editorType: "TextEditor"  }];
    		dataGird.appendColumns(cloumns);
			var jclxArr = jclx.split(",");
			
			for(var i=0; i<jclxArr.length; i++)
			{
				
				if(jcdlMap[jclxArr[i]]=="气象环境监测类"){
					qxhjl = true;
					
				}
				if(jcdlMap[jclxArr[i]]=="导线监测类"){
					
					dxjcl = true;
					
				}
				if(jcdlMap[jclxArr[i]]=="绝缘子监测类"){
					jyzjcl = true;
					
				}
				if(jcdlMap[jclxArr[i]]=="杆塔监测类"){
					gtjcl = true;
			    	
					
				}
				if(jcdlMap[jclxArr[i]]=="视频监测类"){
					
					spjcl = true;
					
				}
				
			}
			
			 if (qxhjl == true)
			 {
				 cloumns = [{
		    		name: "group1",
		    		caption: "气象环境监测类",
		    		columns: [
						{ name: "QXJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "QXJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		    		]
		    		}];
				dataGird.appendColumns(cloumns);
				}
	    	 if (dxjcl == true)
	    	 {
	    		 cloumns = [{
	         		name: "group2",
	        		caption: "导线监测类",
	        		columns: [
	        		{ name: "DXJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
	        		{ name: "DXJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
	        		]
	        	}];
					dataGird.appendColumns(cloumns);
	    	 }
	    	 if (jyzjcl == true)
	    	 {
	    		 cloumns = [{
	         		name: "group4",
	        		caption: "绝缘子监测类",
	        		columns: [
	        		{ name: "JYZJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
	        		{ name: "JYZJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
	        		]
	        	}];
					dataGird.appendColumns(cloumns);
	    	 }
	    	 if (gtjcl == true)
	    	 {
	    		 cloumns = [{
	         		name: "group3",
	        		caption: "杆塔监测类",
	        		columns: [
	        		{ name: "GTJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
	        		{ name: "GTJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
	        		]
	        	}];
					dataGird.appendColumns(cloumns);
	    	 }
	    	 if (spjcl == true)
	    	 {
	    		 cloumns = [{
	         		name: "group5",
	        		caption: "视频类",
	        		columns: [
	        		{ name: "SPJCRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
	        		{ name: "SPLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
	        		]
	        	}];
					dataGird.appendColumns(cloumns);
	    	 }
				
			cloumns = [{
        		name: "group6",
        		caption: "合计",
        		columns: [
        		{ name: "JRSHJ", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZSHJ", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "SSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
			dataGird.appendColumns(cloumns);
		}

    	else if   (jclx == "" ||jclx == null)
		{
    		var cloumns = [{name: "SSWS", caption: "SSWS" ,visible : false},
    		       		{ name: "SSWSMC", caption: "所属网省名称", dataAlign:"center", editorType: "TextEditor"  },
    		       		{name: "group1",caption: "气象环境监测类",
			    		columns: [
			    		{ name: "QXJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "QXJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
			    		]
			    		},
			    		{
			        		name: "group2",
			        		caption: "导线监测类",
			        		columns: [
			        		{ name: "DXJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "DXJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},
			        	{
			        		name: "group3",
			        		caption: "杆塔监测类",
			        		columns: [
			        		{ name: "GTJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "GTJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},
			        	{
			        		name: "group4",
			        		caption: "绝缘子监测类",
			        		columns: [
			        		{ name: "JYZJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "JYZJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},
			        	{
			        		name: "group5",
			        		caption: "视频类",
			        		columns: [
			        		{ name: "SPJCRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "SPLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},
			        	{
			        		name: "group6",
			        		caption: "合计",
			        		columns: [
			        		{ name: "JRSHJ", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZSHJ", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "SSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	}];
			       		dataGird.appendColumns(cloumns);
					}
	}

    /**
     * 初始化DataGrid
     */
    me._initDataGrid = function(){
    	var _dataGrid = me.view.getDataGrid();
    	_col = _dataGrid.$body.find("td").not("#SSWS,#SSWSMC,#SSJRL,#rownumber");
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
    	var dydj = me.view.dataGrid.searchBox.editors.dydj.value;
    	var linkeddepws = me.view.dataGrid.searchBox.editors.linkeddepws.value;
    	var linkedlinename = me.view.dataGrid.searchBox.editors.linkedlinename.value;
    	var manufacturer = me.view.dataGrid.searchBox.editors.manufacturer.value;
    	var monitoringtype = me.view.dataGrid.searchBox.editors.monitoringtype.value;
    	var srundate = me.view.dataGrid.searchBox.editors.srundate.value;
    	var erundate = me.view.dataGrid.searchBox.editors.erundate.value;
    	
    	var ssws = item.item.id;
    	var mvc = new sdtjdetail.views.MainViewController();
    	var detailview = mvc.getView();
    	var window = new mx.windows.WindowManager().create({
	    		resizable: false
	    	});	
	   var filter = new Array();
	 //条件框的条件
	    if(null!=dydj)
	    {
	    	filter.push("dydj=" + dydj);
	    }
	  	if(null!=linkeddepws)
	    {
	    	filter.push("linkeddepws=" + linkeddepws);
	    }
	      if(""!=linkedlinename)
	    {
	    	filter.push("linkedlinename=" + linkedlinename);
	    }
	      if(""!=manufacturer)
	    {
	    	filter.push("manufacturer=" + manufacturer);
	    }
	      if(null!=monitoringtype)
	    {
	    	filter.push("monitoringtype=" + monitoringtype);
	    }
	      if(null!=srundate)
	    {
	    	filter.push("srundate=" + srundate);
	    }
	      if(null!=erundate)
	    {
	    	filter.push("erundate=" + erundate);
	    }
	    if(null!=ssws)
	    {
	    	filter.push("ssws=" + ssws);
	    }
	   //具体数据的条件
	   //是否实时
	    if("RS"==colName.substring(colName.length-2,colName.length)||"JRS"==colName.substring(0,3))
		   {
		   		filter.push("sfss=T" );
		   }
	   //每个大类
	   if("QXJCL"==colName.substring(0,5))
	   {
	   		filter.push("jclx=018" );
	   }
	   if("JYZJCL"==colName.substring(0,6))
	   {
	   		filter.push("jclx=014" );
	   }
	   if("DXJCL"==colName.substring(0,5))
	   {
	   		filter.push("jclx=013" );
	   }
	   if("GTJCL"==colName.substring(0,5))
	   {
	   		filter.push("jclx=012" );
	   }
	   if("SP"==colName.substring(0,2))
	   {
	   		filter.push("jclx=018003" );
	   }
    	
    	var str = "";
 	    for ( var i = 0; i < filter.length; i++) {
 			str += filter[i] + "&";
 		}
     	detailview.dataGrid.setFilter(str.substring(0, str.length - 1));
 	    	
 		detailview.dataGrid.load();
 		window.setWidth("900");
     	window.setHeight("500");
     	window.setTitle("输电监测信息列表");
 		window.setView(detailview);
 		window.showDialog();
    }
    
    
    return me.endOfClass(arguments);
};