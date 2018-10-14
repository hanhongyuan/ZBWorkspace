$ns("sdzzyxzktj.views");
$import("sdzzyxzktj.views.sdzzyxzktjByDydjMainView");
$import("sdzzyxzktj.views.sdzzxxDetailMainViewController");
$import("sdzzyxzktj.views.sdzzyxzktjDetailMainViewController");
$include("~/com.sgcc.pms.zbztjc.util/enum/commonFunction.js");

sdzzyxzktj.views.sdzzyxzktjByDydjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdzzyxzktj.views.sdzzyxzktjByDydjMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
	if (me.view != null && typeof(me.view.dataGrid) != "undefined")
	{
 	    me.view.dataGrid.load();
	}	
    };
    
    me._initSdDydjDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
//	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#rownumber,#SSWSMC,#SSJRZS37,#SSJRZS36,#SSJRZS35,#SSJRZS34,#SSJRZS33,#SSJRZS32,#SSJRZS30,#SSJRZS25,#SSJRZS86,#SSJRZS85,#SSJRZS84,#SSJRZS83,#SSJRZS82,#SSJRZS81,#SSJRZS80,#SSJRL37,#SSJRL36,#SSJRL35,#SSJRL34,#SSJRL33,#SSJRL32,#SSJRL30,#SSJRL25,#SSJRL86,#SSJRL85,#SSJRL84,#SSJRL83,#SSJRL82,#SSJRL81,#SSJRL80,#ALLKHZZS,#ALLSSJRZS,#ALLSSJRL");
	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#rownumber,#SSWSMC,#SSJRL37,#SSJRL36,#SSJRL35,#SSJRL34,#SSJRL33,#SSJRL32,#SSJRL30,#SSJRL25,#SSJRL86,#SSJRL85,#SSJRL84,#SSJRL83,#SSJRL82,#SSJRL81,#SSJRL80,#ALLSSJRL");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","sdzzyxzktjLink");
		    	}
			}
	    	_dataGrid.$body.find(".sdzzyxzktjLink").click(function(e) {
				var item = $(this).parent().data();
				var colName = e.target.id;
				$(this)[0].style.color = "#999999";
				showSdDydjDetail(item, colName);
			});
    }
    
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showSdDydjDetail = function(item, colName) {
		if(colName.indexOf("SSJRZS") != -1 ){
			sdzzTwoDetailView = null;
		if (sdzzTwoDetailView == null) {
			var mvc = new sdzzyxzktj.views.sdzzxxDetailMainViewController();
			sdzzTwoDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var jclx = editor.jclx.value;		//监测类型的值
		var xlmc = editor.xlmc.value;		//线路名称
		var sccj = editor.sccj.value;		//生产厂家
		var tyrq = editor.tyrq.value;		//投运日期
		var ssdw = item.item.values.SSWSMC;						//所属单位
		var dydj = colName.substring(colName.length-2,colName.length);		//电压等级
		var type = null;

		var filter = new Array();//可以searchBox查询内容可简化为for循环
		filter.push("sfss = T");
		
		if(null != jclx){		//如果监测类型不为空
    		filter.push("jclx = " + jclx);
    	}
		
		if(null != dydj){		//如果电压等级不为空
			if("ZS" != dydj){
    		filter.push("dydj = " + dydj);
    		}
    	}
		
		if(null != xlmc && '' != xlmc.trim()){		//如果线路名称不为空
    		filter.push("xlmc = " + xlmc);
    	}
		
		if(null != sccj && '' != sccj.trim()){		//如果生产厂家不为空
    		filter.push("sccj = " + sccj);
    	}
		
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if("国家电网公司" == ssdw){		//如果所属单位为国家电网公司
			if(null != ssdw_searchBox){
				filter.push("ssdw = " + ssdw_searchBox);
			}
    	}else{
    		ssdw = item.item.values.LINKEDPROVICEDEPT;
    		filter.push("ssdw = " + ssdw);
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		sdzzTwoDetailView.getDataGrid().setFilter(str.substring(0, str.length - 1));
		sdzzTwoDetailView.dataGrid.load();
		me.view.detailWin.setWidth("900");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("所属线路查询");
		me.view.detailWin.setView(sdzzTwoDetailView);
		me.view.detailWin.showDialog();
		}else if(colName.indexOf("KHZZS") != -1){
		
		sdzzyxzktjDetailView = null;
		if (sdzzyxzktjDetailView == null) {
			var mvc = new sdzzyxzktj.views.sdzzyxzktjDetailMainViewController();
			sdzzyxzktjDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var jclx = editor.jclx.value;		//监测类型的值
		var xlmc = editor.xlmc.value;		//线路名称
		var sccj = editor.sccj.value;		//生产厂家
		var tyrq = editor.tyrq.value;		//投运日期
		var ssdw = item.item.values.SSWSMC;						//所属单位
//		sdzzyxzktjDetailView.searchBox.editors[0].setValue(ssdw);
		var ssdw_searchBox =  editor.ssdw.value;;		//查询框内的所属单位
		var dydj = colName.substring(colName.length-2,colName.length);		//电压等级
		var type = null;

		var filter = new Array();
		
		
		if(null != jclx){		//如果监测类型不为空
    		filter.push("jclx = " + jclx);
    	}
		
		if(null != dydj){		//如果电压等级不为空
			if("ZS" != dydj){
			filter.push("dydj = " + dydj);
			}
    	}
		
		if(null != xlmc && '' != xlmc.trim()){		//如果线路名称不为空
    		filter.push("xlmc = " + xlmc);
    	}
		
		if(null != sccj && '' != sccj.trim()){		//如果生产厂家不为空
    		filter.push("sccj = " + sccj);
    	}
		
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if("国家电网公司" == ssdw){		//如果所属单位为国家电网公司
			if(null != ssdw_searchBox && '' != ssdw_searchBox.trim()){
				filter.push("ssdw = " + ssdw_searchBox);
			}
//			sdzzyxzktjDetailView.searchBox.editors[0].value=ssdw_searchBox;
    	}else{
    		ssdw = item.item.values.LINKEDPROVICEDEPT;
    		filter.push("ssdw = " + ssdw);
//    		sdzzyxzktjDetailView.searchBox.editors[0].value=ssdw;
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		var dydjMap ={"25":"交流35kV","30":"交流66kV","32":"交流110kV","33":"交流220kV","34":"交流330kV","35":"交流500kV","36":"交流750kV","37":"交流1000kV","80":"直流120kV","81":"直流125kV","82":"直流400kV","83":"直流500kV","84":"直流660kV","85":"直流800kV","86":"直流1000kV"};
		var dydjmc = dydjMap[dydj];
//		sdzzyxzktjDetailView.searchBox.editors[1].setValue(dydjmc);
//		sdzzyxzktjDetailView.searchBox.editors[1].value=dydj;
//		sdzzyxzktjDetailView.searchBox.editors[6].value=tyrq;
//		sdzzyxzktjDetailView.searchBox.setFieldVisible("tyrq", false)
//		sdzzyxzktjDetailView.searchBox.editors[0].setEnabled(false);
//		sdzzyxzktjDetailView.searchBox.editors[1].setEnabled(false);
		sdzzyxzktjDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		sdzzyxzktjDetailView.dataGrid.load();
		me.view.detailWin.setWidth("900");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("输电监测装置信息列表");
		me.view.detailWin.setView(sdzzyxzktjDetailView);
		me.view.detailWin.showDialog();
	}
}
    
	
    /**
     * 点击按电压等级统计按钮触发的事件 
     */
    me._dydjQueryBtn_click = function(){
    	var _dataGrid = me.view.getDataGrid();
    	var bool = checkdate();
		if(bool){
			var _seSearchBox = me.view.getSearchBox();
	    	var params = _seSearchBox.getSearchParam();
			var dydj = _seSearchBox.editors.dydj.value;
	        _dataGrid.setFilter(params);
	    	//hidetile(_dataGrid,dydj);
	    	_dataGrid.load();
		}
    	
    }
//时间判断，起始时间和终止时间都不能大于当前时间
	function checkdate(){
		var check = me.view.getSearchBox().editors.tyrq.value;
		if(check!=""&&check!=null){
			var date = new Date().toLocaleDateString();
			date = date.replace(/-/g,"/");
			if(check[0]!=""&&check[0]!=null ){
				var check0=check[0].replace(/-/g,"/");
				if(Date.parse(check0)>Date.parse(date))
				{
//					me.view.getSearchBox().editors.tyrq.reset();
					mx.indicate("warn", "开始时间不能大于当前时间!");
					return false;
				}
			}
			if(check[1]!=""&&check[1]!=null ){
				var check1=check[1].replace(/-/g,"/");
				if(Date.parse(check1)>Date.parse(date))
				{
//					me.view.getSearchBox().editors.tyrq.reset();
					mx.indicate("warn", "结束时间不能大于当前时间!");
					return false;
				}
			}
			return true;
		}else{
			return true;
		}
		
	}
    function hidetile(_dataGird,dydj){
    	var _seSearchBox = me.view.getSearchBox();
    	if (dydj != "" && dydj != null){
    		_dataGird.clearColumns();
    		var dydjMap ={"25":"交流35kV","30":"交流66kV","32":"交流110kV","33":"交流220kV","34":"交流330kV","35":"交流500kV","36":"交流750kV","37":"交流1000kV","80":"直流120kV","81":"直流125kV","82":"直流400kV","83":"直流500kV","84":"直流660kV","85":"直流800kV","86":"直流1000kV"};
    		var dydjArr = dydj.split(",");
    		var cloumns =[
				{name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  }
			];
			_dataGird.appendColumns(cloumns);
			for (var i=0; i<dydjArr.length; i++){
				if(dydjMap[dydjArr[i]]=="交流35kV"){
    				var columns = [{name: "group8",caption: "交流35kV",
							columns :[
						{ name: "KHZZS25", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS25", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL25", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL25", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL25", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF25", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
						}]
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流66kV"){
    				 var cloumns = [{name: "group7",caption: "交流66kV",
							columns :[
							{ name: "KHZZS35", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "SSJRZS35", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "SSJRL35", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
							{ name: "JRL35", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
							{ name: "ZQL35", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
							{ name: "DF35", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
					 }];
					_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流110kV"){
    				var cloumns = [{name: "group6",caption: "交流110kV",
							columns :[
						{ name: "KHZZS34", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS34", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL34", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL34", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL34", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF34", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]		
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流220kV"){
    				var cloumns = [{name: "group5",caption: "交流220kV",
						columns :[
						{ name: "KHZZS33", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS33", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL33", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL33", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL33", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF33", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流330kV"){
    				var cloumns = [{name: "group4",caption: "交流330kV",
						columns :[
						{ name: "KHZZS34", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS34", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL34", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL34", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL34", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF34", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流500kV"){
    				var cloumns = [{name: "group3",caption: "交流500kV",
						columns :[
						{ name: "KHZZS35", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS35", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL35", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL35", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL35", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF35", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流750kV"){
    				var cloumns = [{name: "group2",caption: "交流750kV",
						columns :[
						{ name: "KHZZS36", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS36", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL36", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL36", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL36", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF36", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流1000kV"){
    				var cloumns = [{name: "group1",caption: "交流1000kV",
						columns :[
							{ name: "KHZZS37", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "SSJRZS37", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "SSJRL37", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
							{ name: "JRL37", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
							{ name: "ZQL37", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
							{ name: "DF37", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流1000kV"){
    				var cloumns = [{name: "group9",caption: "直流1000kV",
						columns :[
							{ name: "ZZZS86", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS86", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS86", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL86", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流800kV"){
    				var cloumns = [{name: "group10",caption: "直流800kV",
						columns :[
						{ name: "KHZZS85", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS85", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL85", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL85", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL85", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF85", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流660kV"){
    				var cloumns = [{name: "group11",caption: "直流660kV",
						columns :[
						{ name: "KHZZS84", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS84", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL84", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL84", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL84", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF84", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流500kV"){
    				var cloumns = [{name: "group12",caption: "直流500kV",
						columns :[
							{ name: "KHZZS83", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS83", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL83", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL83", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL83", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF83", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流400kV"){
    				var cloumns = [{name: "group13",caption: "直流400kV",
						columns :[
						{ name: "KHZZS82", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS82", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL82", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL82", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL82", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF82", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流125kV"){
    				var cloumns = [{name: "group14",caption: "直流125kV",
						columns :[
						{ name: "KHZZS81", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS81", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL81", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL81", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL81", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF81", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流120kV"){
    				var cloumns = [{name: "group15",caption: "直流120kV",
						columns :[
						{ name: "KHZZS80", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS80", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL80", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL80", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL80", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF80", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}
			}
			var cloumns = [{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLKHZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLSSJRZS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
					]	
			}];
    		_dataGird.appendColumns(cloumns);
    	}  else{
    		_dataGird.clearColumns();
    		var cloumnsall = [
				{name: "OBJ_ID", caption: "所属单位编码" },
				{name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
				{name: "group1",caption: "1000kV",
					columns :[
						{ name: "KHZZS37", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS37", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL37", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL37", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL37", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF37", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group2",caption: "750kV",
					columns :[
						{ name: "KHZZS36", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS36", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL36", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL36", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL36", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF36", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group3",caption: "500kV",
					columns :[
						{ name: "KHZZS35", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS35", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL35", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL35", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL35", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF35", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group4",caption: "330kV",
					columns :[
						{ name: "KHZZS34", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS34", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL34", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL34", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL34", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF34", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group5",caption: "220kV",
					columns :[
					{ name: "KHZZS33", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS33", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL33", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL33", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL33", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF33", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group6",caption: "110kV",
					columns :[
						{ name: "KHZZS32", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS32", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL32", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL32", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL32", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF32", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group7",caption: "66kV",
					columns :[
					{ name: "KHZZS30", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS30", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL30", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL30", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL30", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF30", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group8",caption: "35kV",
					columns :[
					{ name: "KHZZS25", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS25", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL25", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL25", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL25", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF25", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group9",caption: "±1000kV",
					columns :[
					{ name: "KHZZS86", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS86", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL86", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL86", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL86", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF86", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group10",caption: "±800kV",
					columns :[
						{ name: "KHZZS85", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS85", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL85", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL85", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL85", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF85", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
				},
				{name: "group11",caption: "±660kV",
					columns :[
						{ name: "KHZZS84", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS84", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL84", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL84", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL84", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF84", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group12",caption: "±500kV",
					columns :[
							{ name: "KHZZS83", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS83", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL83", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL83", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL83", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF83", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group13",caption: "±400kV",
					columns :[
							{ name: "KHZZS82", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS82", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL82", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL82", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL82", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF82", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group14",caption: "±125kV",
					columns :[
						{ name: "KHZZS81", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS81", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL81", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL81", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL81", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF81", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group15",caption: "±120kV",
					columns :[
						{ name: "KHZZS80", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS80", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL80", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL80", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL80", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF80", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLKHZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLSSJRZS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
					]	
				}
			];
			_dataGird.appendColumns(cloumnsall);
    	}
    	
    }
    
    me._dydjExportBtn_click = function (){
    	try {
    		/*var _params = null;        
        	var xls = new mxpms.utils.CommUtil();
        	_params = me.view.getDataGrid().filter;
        	xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "输电装置运行状况按电压等级统计"
        	});*/
    		var _dataGrid = me.view.getDataGrid();
        	exportToExcel(_dataGrid,"输电装置运行状况按电压等级统计");
        	var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-报表查询-输电装置运行状况按电压等级统计";		//此处为页面的菜单路径信息
			var result = "操作成功"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			mx.indicate("info","导出失败");
			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测信息查询-输电装置运行状况按电压等级统计";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		}
    };
    
    return me.endOfClass(arguments);
};