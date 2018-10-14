$ns("dldevicetj.views");
$import("dldevicetj.views.MainView");

dldevicetj.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dldevicetj.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactive = function(e){
    	me._btnStatistics_onclick();
    };
    
    me._btnStatistics_onclick = function(){
    	try {
			var _dataGrid = me.view.getDataGrid();
	    	
	    	_dataGrid.clearColumns();
	    	hidetile(_dataGrid);
	    	
	    	_dataGrid.load();
	    	var czlx = "统计";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测信息查询-电缆监测信息按监测类型统计";		//此处为页面的菜单路径信息
			var result = "操作成功"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			var czlx = "统计";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测信息查询-电缆监测信息按监测类型统计";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		}
    }
    /**
     * 导出
     */
    me._btnexport_onclick= function()
    {
       	
   try {
	   var _dataGrid = me.view.getDataGrid();
       //导出文档的工具类
    var util = new mxpms.utils.FormDocumentUtil(
      {
          //title为导出文档内容的标题，标题可为“”字符串
          title:"",
          //view为页面视图或支持类型的jquery对象
       view:_dataGrid.$grid,
          //fileName为导出的文档名称
       fileName:"变电按监测类型统计"
       });
  util.exportExcel(); 
  var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
	var transactionType = "状态监测-监测信息查询-变电按监测类型统计";		//此处为页面的菜单路径信息
	var result = "操作成功"								//此处为操作结果，二选一
	var userlogg = new userlogger.views.MainViewController();
	userlogg.updateUserLogger(czlx,transactionType,result);
} catch (e) {
	mx.indicate("info","导出失败");
	var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
	var transactionType = "状态监测-监测信息查询-变电按监测类型统计";		//此处为页面的菜单路径信息
	var result = "操作失败"								//此处为操作结果，二选一
	var userlogg = new userlogger.views.MainViewController();
	userlogg.updateUserLogger(czlx,transactionType,result);	
}
    }
    /**
     * 动态展示列
     */
    function hidetile(dataGird){
    	var jclx = dataGird.searchBox.editors[3].value;
    	var dljcl = false;
    	var dltdjcl = false;
    	
    	
    
    	if (jclx != null)
		{
    		
    		var cloumns = [{name: "SSWS", caption: "SSWS" ,visible : false},
    		       		{ name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  }];
    		dataGird.appendColumns(cloumns);
			var jclxArr = jclx.split(",");
			
			for (var i=0; i<jclxArr.length; i++)
			{
				if (jclxArr[i].split("031").length > 1)
				{
					dljcl = true;
				}
				if (jclxArr[i].split("032").length > 1)
				{
					dltdjcl = true;
				}
				
			}

			
			 if (dljcl == true)
			 {
				 cloumns =[{
				    		name: "group1",
				    		caption: "电缆本体监测",
				    		columns: [
				    		{ name: "DLJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
				    		{ name: "DLJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
				    		{ name: "DLJCLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
				    		]
				    		}];
				dataGird.appendColumns(cloumns);
				}
	    	 if (dltdjcl == true)
	    	 {
	    		 cloumns = [{
			         		name: "group2",
			        		caption: "电缆通道监测",
			        		columns: [
			        		{ name: "DLTDJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "DLTDJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "DLTDJCLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
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
    		var cloumns = [
    		       		{name: "SSWS", caption: "SSWS" ,visible : false},
			    		{ name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
			    		{
			    		name: "group1",
			    		caption: "电缆本体监测",
			    		columns: [
			    		{ name: "DLJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "DLJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
			    		{ name: "DLJCLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
			    		]
			    		},
			    		{
			        		name: "group2",
			        		caption: "电缆通道监测",
			        		columns: [
			        		{ name: "DLTDJCLJRS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "DLTDJCLZS", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "DLTDJCLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
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
    	_col = _dataGrid.$body.find("td").not("#SSWS,#SSWSMC,#SSJRL,#DLJCLSSJRL,#DLTDJCLSSJRL,#rownumber");
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
    	var LINKEDCABLEANDCHANNELNAME = me.view.dataGrid.searchBox.editors.LINKEDCABLEANDCHANNELNAME.value;
    	var manufacturer = me.view.dataGrid.searchBox.editors.manufacturer.value;
    	var monitoringtype = me.view.dataGrid.searchBox.editors.monitoringtype.value;
    	var srundate = me.view.dataGrid.searchBox.editors.srundate.value;
    	var erundate = me.view.dataGrid.searchBox.editors.erundate.value;
    	var yxzt = me.view.dataGrid.searchBox.editors.yxzt.value;
    	
    	var ssws = item.item.id;
    	var mvc = new dltjdetail.views.MainViewController();
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
	    	filter.push("linkeddept=" + linkeddepws);
	    }
	      if(""!=LINKEDCABLEANDCHANNELNAME)
	    {
	    	filter.push("LINKEDCABLEANDCHANNELNAME=" + LINKEDCABLEANDCHANNELNAME);
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
	      if(null!=yxzt)
		    {
		    	filter.push("yxzt=" + yxzt);
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
	   if("JRS"==colName.substring(colName.length-3,colName.length)||"JRS"==colName.substring(0,3))
	   {
	   		filter.push("sfss=T" );
	   }
	   //每个大类
	   if("DLJCL"==colName.substring(0,5))
	   {
	   		filter.push("moint=031" );
	   }
	   if("DLTDJCL"==colName.substring(0,7))
	   {
	   		filter.push("moint=032" );
	   }
	 

	     var str = "";
	    for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
	    	
	   
    	detailview.dataGrid.setFilter(str.substring(0, str.length - 1));
	    	
		detailview.dataGrid.load();
		window.setWidth("900");
    	window.setHeight("500");
    	window.setTitle("电缆监测信息列表");
		window.setView(detailview);
		window.showDialog();
    	
    }
    
    return me.endOfClass(arguments);
};