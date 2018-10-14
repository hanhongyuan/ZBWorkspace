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
    	try {
			var _dataGrid = me.view.getDataGrid();
	    	
	    	_dataGrid.clearColumns();
	    	hidetile(_dataGrid);
	    	
	    	_dataGrid.load();
	    	var czlx = "统计";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测信息查询-输电监测信息按监测类型统计";		//此处为页面的菜单路径信息
			var result = "操作成功"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			var czlx = "统计";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测信息查询-输电监测信息按监测类型统计";		//此处为页面的菜单路径信息
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
       fileName:"输电按监测类型统计"
       });
  util.exportExcel(); 
  var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
	var transactionType = "状态监测-监测信息查询-输电按监测类型统计";		//此处为页面的菜单路径信息
	var result = "操作成功"								//此处为操作结果，二选一
	var userlogg = new userlogger.views.MainViewController();
	userlogg.updateUserLogger(czlx,transactionType,result);
} catch (e) {
	mx.indicate("info","导出失败");
	var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
	var transactionType = "状态监测-监测信息查询-输电按监测类型统计";		//此处为页面的菜单路径信息
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
    	gtqxjc = false;
		dxhcjc = false;
		dxwdjc = false;
		wfzdjc = false;
		fpjc = false;
		fbjc = false;
		dxwd = false;
		whdjc = false;
		wqxjc = false;
		txjc = false;
		spjc = false;
	if (jclx != null)
	{	
		var cloumns = [{name: "SSWS", caption: "SSWS" ,visible : false},
		       		{ name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  }];
   		dataGird.appendColumns(cloumns);
		var jclxArr = jclx.split(",");
		//判断哪些监测类显示
		for (var i=0; i<jclxArr.length; i++)
		{
			if (jclxArr[i].split("012001").length > 1 )
			{
				gtqxjc = true;
			}
			if (jclxArr[i].split("013001").length > 1 )
			{
				dxhcjc = true;
			}
			if (jclxArr[i].split("013002").length > 1 )
			{
				dxwdjc = true;
			}
			if (jclxArr[i].split("013003").length > 1 )
			{
				wfzdjc = true;
			}
			if (jclxArr[i].split("013004").length > 1 )
			{
				fpjc = true;
			}
			if (jclxArr[i].split("013005").length > 1 )
			{
				fbjc = true;
			}
			if (jclxArr[i].split("013006").length > 1 )
			{
				dxwd = true;
			}
			if (jclxArr[i].split("014001").length > 1 )
			{
				whdjc = true;
			}
			if (jclxArr[i].split("018001").length > 1 )
			{
				wqxjc = true;
			}
			if (jclxArr[i].split("018002").length > 1 )
			{
				txjc = true;
			}
			if (jclxArr[i].split("018003").length > 1 )
			{
				spjc = true;
			}
		}
		//显示选择的监测类
		if (gtqxjc)
		{
			 cloumns = [{
		    		name: "group1",
		    		caption: "杆塔倾斜监测",
		    		columns: [
		    		{ name: "JR012001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		    		{ name: "ZS012001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		    		]
		    		}];
					dataGird.appendColumns(cloumns);
		}
		if (dxhcjc)
		{
			cloumns = [{
        		name: "group2",
        		caption: "导线弧垂监测",
        		columns: [
        		{ name: "JR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		if (dxwdjc)
		{
			cloumns = [{
        		name: "group3",
        		caption: "导线温度监测",
        		columns: [
        		{ name: "JR013002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013002", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		if (wfzdjc)
		{
			cloumns = [{
        		name: "group4",
        		caption: "微风振动监测",
        		columns: [
        		{ name: "JR013003", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013003", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		if (fpjc)
		{
			cloumns = [{
        		name: "group5",
        		caption: "风偏监测",
        		columns: [
        		{ name: "JR013004", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013004", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		if (fbjc)
		{
			cloumns = [{
        		name: "group6",
        		caption: "覆冰监测",
        		columns: [
        		{ name: "JR013005", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013005", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		if (dxwd)
		{
			cloumns = [{
        		name: "group7",
        		caption: "导线舞动",
        		columns: [
        		{ name: "JR013006", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013006", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		if (whdjc)
		{
			cloumns = [{
        		name: "group8",
        		caption: "现场污秽度监测",
        		columns: [
        		{ name: "JR014001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS014001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		if (wqxjc)
		{
			cloumns = [{
        		name: "group9",
        		caption: "微气象监测",
        		columns: [
        		{ name: "JR018001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS018001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		if (txjc)
		{
			cloumns = [{
        		name: "group10",
        		caption: "图像监测",
        		columns: [
        		{ name: "JR018002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS018002", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		if (spjc)
		{
			cloumns = [{
        		name: "group11",
        		caption: "视频监测",
        		columns: [
        		{ name: "JR018003", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS018003", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}];
					dataGird.appendColumns(cloumns);
		}
		cloumns = [{
    		name: "group12",
    		caption: "合计",
    		columns: [
    		{ name: "JRSHJ", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "ZSHJ", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "SSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
    		]
    	}];
		dataGird.appendColumns(cloumns);
	}
	else
	{
		var cloumns = [{name: "SSWS", caption: "SSWS" ,visible : false},
		       		{ name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
		    		{
		    		name: "group1",
		    		caption: "杆塔倾斜监测",
		    		columns: [
		    		{ name: "JR012001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		    		{ name: "ZS012001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		    		]
		    		},
		    		{
		        		name: "group2",
		        		caption: "导线弧垂监测",
		        		columns: [
		        		{ name: "JR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS013001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},
		        	{
		        		name: "group3",
		        		caption: "导线温度监测",
		        		columns: [
		        		{ name: "JR013002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS013002", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},
		        	{
		        		name: "group4",
		        		caption: "微风振动监测",
		        		columns: [
		        		{ name: "JR013003", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS013003", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},
		        	{
		        		name: "group5",
		        		caption: "风偏监测",
		        		columns: [
		        		{ name: "JR013004", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS013004", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},{
		        		name: "group6",
		        		caption: "覆冰监测",
		        		columns: [
		        		{ name: "JR013005", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS013005", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},
		        	{
		        		name: "group7",
		        		caption: "导线舞动",
		        		columns: [
		        		{ name: "JR013006", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS013006", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},
		        	{
		        		name: "group8",
		        		caption: "现场污秽度监测",
		        		columns: [
		        		{ name: "JR014001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS014001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},
		        	{
		        		name: "group9",
		        		caption: "微气象监测",
		        		columns: [
		        		{ name: "JR018001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS018001", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},
		        	{
		        		name: "group10",
		        		caption: "图像监测",
		        		columns: [
		        		{ name: "JR018002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS018002", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},
		        	{
		        		name: "group11",
		        		caption: "视频监测",
		        		columns: [
		        		{ name: "JR018003", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
		        		{ name: "ZS018003", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
		        		]
		        	},
		        	{
		        		name: "group12",
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
    	var yxzt = me.view.dataGrid.searchBox.editors.yxzt.value;
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
	    	filter.push("linkedstationname=" + linkedlinename);
	    }
	      if(""!=manufacturer)
	    {
	    	filter.push("manufacturer=" + manufacturer);
	    }
	      if(null!=monitoringtype)
	    {
	    	filter.push("monitoringtype=" + monitoringtype);
	    }
	      if(null!=yxzt)
		    {
		    	filter.push("yxzt=" + yxzt);
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
	   if("JR"==colName.substring(0,2))
	   {
	   		filter.push("sfss=T" );
	   }
       if("HJ"!=colName.substring(colName.length-2,colName.length))
    	{
    		var jclx = colName.substring(colName.length-6,colName.length);
    		filter.push("jclx="+jclx);
    	}
    	
    	var str = "";
 	    for ( var i = 0; i < filter.length; i++) {
 			str += filter[i] + "&";
 		}
     	detailview.dataGrid.setFilter(str.substring(0, str.length - 1));
 	    	
 		detailview.dataGrid.load();
 		window.setWidth("900");
     	window.setHeight("500");
     	window.setTitle("输电电监测信息列表");
 		window.setView(detailview);
 		window.showDialog();
    }
    
    
    return me.endOfClass(arguments);
};