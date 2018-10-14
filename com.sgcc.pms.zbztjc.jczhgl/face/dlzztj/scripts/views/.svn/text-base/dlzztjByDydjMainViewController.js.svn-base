$ns("dlzztj.views");
$import("dlzztj.views.dlzztjByDydjMainView");
$import("dlzztj.views.dlzzxxTwoDetailViewController");
$import("dlzztj.views.dlzzxxDetailMainViewController");

dlzztj.views.dlzztjByDydjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dlzztj.views.dlzztjByDydjMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
		
    };
    me._initBdDydjDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#rownumber,#ALLZZZS,#ALLFGBDZS");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","dlzztjLink");
		    	}
			}
	    	_dataGrid.$body.find(".dlzztjLink").click(function(e) {
				var item = $(this).parent().data();
				var colName = e.target.id;
				$(this)[0].style.color = "#999999";
				showBdDydjDetail(item, colName);
			});
    }
    
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showBdDydjDetail = function(item, colName) {
		if(colName.substring(0,4)=="ZZZS"){
		dlzzcxtjDetailView = null;
		if (dlzzcxtjDetailView == null) {
			var mvc = new dlzztj.views.dlzzxxDetailMainViewController();
			dlzzcxtjDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var jclx = editor.jclx.value;		//监测类型的值
		var dlmc = editor.dlmc.value;		//变电站名称
		var sccj = editor.sccj.value;		//生产厂家
		var tyrq = editor.tyrq.value;		//投运日期
		var ssdw = item.item.values.SSWSMC;						//所属单位
//		dlzzcxtjDetailView.searchBox.editors[0].setValue(ssdw);
		var ssdw_searchBox =  editor.ssdw.value;;		//查询框内的所属单位
		var dydj = colName.substring(colName.length-2,colName.length);		//电压等级
		var type = null;

		var filter = new Array();
		
		
		if(null != jclx){		//如果监测类型不为空
    		filter.push("jclx = " + jclx);
    	}
		
		if(null != dydj){		//如果电压等级不为空
    		filter.push("dydj = " + dydj);
    	}
		
		if(null != dlmc && '' != dlmc.trim()){		//如果变电站名称不为空
    		filter.push("dlmc = " + dlmc);
    	}
		
		if(null != sccj && '' != sccj.trim()){		//如果生产厂家不为空
    		filter.push("sccj = " + sccj);
    	}
		
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if("国家电网公司" == ssdw ){		//如果所属单位为国家电网公司
			if(null != ssdw_searchBox && '' != ssdw_searchBox.trim()){
				filter.push("ssdw = " + ssdw_searchBox);
			}
//			dlzzcxtjDetailView.searchBox.editors[0].value=ssdw_searchBox;
    	}else{
    		ssdw = item.item.values.LINKEDPROVICEDEPT;
    		filter.push("ssdw = " + ssdw);
//    		dlzzcxtjDetailView.searchBox.editors[0].value=ssdw;
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		var dydjMap ={"25":"交流35kV","30":"交流66kV","32":"交流110kV","33":"交流220kV","34":"交流330kV","35":"交流500kV","36":"交流750kV","37":"交流1000kV","80":"直流120kV","81":"直流125kV","82":"直流400kV","83":"直流500kV","84":"直流660kV","85":"直流800kV","86":"直流1000kV"};
		var dydjmc = dydjMap[dydj];
//		dlzzcxtjDetailView.searchBox.editors[1].setValue(dydjmc);
//		dlzzcxtjDetailView.searchBox.editors[1].value=dydj;
//		dlzzcxtjDetailView.searchBox.editors[6].value=tyrq;
//		dlzzcxtjDetailView.searchBox.setFieldVisible("tyrq", false)
//		dlzzcxtjDetailView.searchBox.editors[0].setEnabled(false);
//		dlzzcxtjDetailView.searchBox.editors[1].setEnabled(false);
		dlzzcxtjDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		dlzzcxtjDetailView.dataGrid.load();
		me.view.detailWin.setWidth("1000");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("电缆监测装置信息列表");
		me.view.detailWin.setView(dlzzcxtjDetailView);
		me.view.detailWin.showDialog();
	}else if(colName.substring(0,5)=="FGBDZ"){
		var	dlzzTwoDetailView = null;
		if (dlzzTwoDetailView == null) {
			var mvc = new dlzztj.views.dlzzxxTwoDetailViewController();
			dlzzTwoDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var jclx = editor.jclx.value;		//监测类型的值
		var dlmc = editor.dlmc.value;		//线路名称
		var sccj = editor.sccj.value;		//生产厂家
		var tyrq = editor.tyrq.value;		//投运日期
		var ssdw = item.item.values.SSWSMC;						//所属单位
		var ssdw_searchBox =  editor.ssdw.value;;		//查询框内的所属单位
		var dydj = colName.substring(colName.length-2,colName.length);		//电压等级
		var type = null;

		var filter = new Array();
		
		
		if(null != jclx){		//如果监测类型不为空
    		filter.push("jclx = " + jclx);
    	}
		
		if(null != dydj){		//如果电压等级不为空
    		filter.push("dydj = " + dydj);
    	}
		
		if(null != dlmc && '' != dlmc.trim()){		//如果线路名称不为空
    		filter.push("dlmc = " + dlmc);
    	}
		
		if(null != sccj && '' != sccj.trim()){		//如果生产厂家不为空
    		filter.push("sccj = " + sccj);
    	}
		
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if("国家电网公司" == ssdw){		//如果所属单位为国家电网公司
			if(null != ssdw_searchBox && '' != dlmc.trim()){
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
		dlzzTwoDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		dlzzTwoDetailView.dataGrid.load();
		me.view.detailWin.setWidth("600");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("覆盖线路以及覆盖通道查询");
		me.view.detailWin.setView(dlzzTwoDetailView);
		me.view.detailWin.showDialog();
		
	}
}
	
    me._dydjExportBtn_click = function(){
    	
    	 try {
    		 var _dataGrid = me.view.getDataGrid();
    	    	var util = new mxpms.utils.FormDocumentUtil({
    	    		title:"",
    	    		view : _dataGrid.$grid,
    	    		fileName:"电缆监测装置按电压等级统计"
    	    	});
    	    	 util.exportExcel(); 
    	    	 var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
    				var transactionType = "状态监测-监测装置管理-电缆监测装置按电压等级统计";		//此处为页面的菜单路径信息
    				var result = "操作成功"								//此处为操作结果，二选一
    				var userlogg = new userlogger.views.MainViewController();
    				userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测装置管理-电缆监测装置按电压等级统计";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
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
	    	var dydj = _seSearchBox.editors.dydj.value;
		 	var params = _seSearchBox.getSearchParam();
	        _dataGrid.setFilter(params);
	    	hidetile(_dataGrid,dydj);
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
    function hidetile(_dataGrid,dydj){
 
    	if (null != dydj){
    		_dataGrid.clearColumns();
    		var dydjMap ={"25":"交流35kV","30":"交流66kV","32":"交流110kV","33":"交流220kV","34":"交流330kV","35":"交流500kV","36":"交流750kV","37":"交流1000kV","80":"直流120kV","81":"直流125kV","82":"直流400kV","83":"直流500kV","84":"直流660kV","85":"直流800kV","86":"直流1000kV"};
    		var dydjArr = dydj.split(",");
    		var cloumns =[
				{name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  }
			];
    		_dataGrid.appendColumns(cloumns);
			for (var i=0; i<dydjArr.length; i++){
				if(dydjMap[dydjArr[i]]=="交流35kV"){
    				var columns = [{name: "group8",caption: "交流35kV",
							columns :[
								{ name: "ZZZS25", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGBDZS25", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
							]	
						}]
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流66kV"){
    				 var cloumns = [{name: "group7",caption: "交流66kV",
							columns :[
								{ name: "ZZZS30", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGBDZS30", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
							]	
					 	}];
    				 _dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流110kV"){
    				var cloumns = [{name: "group6",caption: "交流110kV",
							columns :[
								{ name: "ZZZS32", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGBDZS32", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
							]	
						}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流220kV"){
    				var cloumns = [{name: "group5",caption: "交流220kV",
						columns :[
							{ name: "ZZZS33", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS33", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流330kV"){
    				var cloumns = [{name: "group4",caption: "交流330kV",
						columns :[
							{ name: "ZZZS34", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS34", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流500kV"){
    				var cloumns = [{name: "group3",caption: "交流500kV",
						columns :[
							{ name: "ZZZS35", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS35", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流750kV"){
    				var cloumns = [{name: "group2",caption: "交流750kV",
						columns :[
							{ name: "ZZZS36", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS36", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流1000kV"){
    				var cloumns = [{name: "group1",caption: "交流1000kV",
						columns :[
							{ name: "ZZZS37", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS37", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流1000kV"){
    				var cloumns = [{name: "group9",caption: "直流1000kV",
						columns :[
							{ name: "ZZZS86", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS86", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流800kV"){
    				var cloumns = [{name: "group10",caption: "直流800kV",
						columns :[
							{ name: "ZZZS85", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS85", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流660kV"){
    				var cloumns = [{name: "group11",caption: "直流660kV",
						columns :[
							{ name: "ZZZS84", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS84", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流500kV"){
    				var cloumns = [{name: "group12",caption: "直流500kV",
						columns :[
							{ name: "ZZZS83", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS83", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流400kV"){
    				var cloumns = [{name: "group13",caption: "直流400kV",
						columns :[
							{ name: "ZZZS82", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS82", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流125kV"){
    				var cloumns = [{name: "group14",caption: "直流125kV",
						columns :[
							{ name: "ZZZS81", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS81", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流120kV"){
    				var cloumns = [{name: "group15",caption: "直流120kV",
						columns :[
							{ name: "ZZZS80", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS80", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}
			}
			var cloumns = [{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLZZZS", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGBDZS", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
			}];
			_dataGrid.appendColumns(cloumns);
    	}
    	
    	
    	else {
    		_dataGrid.clearColumns();
    		var columnsall = [
				{name: "OBJ_ID", caption: "所属单位编码" },
				{name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
				{name: "group1",caption: "1000kV",
					columns :[
						{ name: "ZZZS37", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS37", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group2",caption: "750kV",
					columns :[
						{ name: "ZZZS36", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS36", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group3",caption: "500kV",
					columns :[
						{ name: "ZZZS35", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS35", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group4",caption: "330kV",
					columns :[
						{ name: "ZZZS34", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS34", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group5",caption: "220kV",
					columns :[
						{ name: "ZZZS33", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS33", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group6",caption: "110kV",
					columns :[
						{ name: "ZZZS32", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS32", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group7",caption: "66kV",
					columns :[
						{ name: "ZZZS30", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS30", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group8",caption: "35kV",
					columns :[
						{ name: "ZZZS25", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS25", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group9",caption: "±1000kV",
					columns :[
						{ name: "ZZZS86", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS86", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group10",caption: "±800kV",
					columns :[
						{ name: "ZZZS85", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS85", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]
				},
				{name: "group11",caption: "±660kV",
					columns :[
						{ name: "ZZZS84", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS84", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group12",caption: "±500kV",
					columns :[
						{ name: "ZZZS83", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS83", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group13",caption: "±400kV",
					columns :[
						{ name: "ZZZS82", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS82", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group14",caption: "±125kV",
					columns :[
						{ name: "ZZZS81", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS81", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group15",caption: "±120kV",
					columns :[
						{ name: "ZZZS80", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS80", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLZZZS", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGBDZS", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				}
			];
			_dataGrid.appendColumns(columnsall);
    	}
    }
    
    return me.endOfClass(arguments);
};