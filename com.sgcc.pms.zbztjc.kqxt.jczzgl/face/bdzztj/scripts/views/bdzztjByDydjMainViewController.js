$ns("bdzztj.views");
$import("bdzztj.views.bdzztjByDydjMainView");
$import("bdzztj.views.bdzzxxTwoDetailViewController");
$import("bdzztj.views.bdzzxxDetailMainViewController");

bdzztj.views.bdzztjByDydjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzztj.views.bdzztjByDydjMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
		
    };
    
    me._initBdDydjDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#ALLFGL,#ALLZZZS,#ALLFGBDZS,#ALLBDZZS,#rownumber,#FGL37,#FGL36,#FGL35,#FGL34,#FGL33,#FGL32,#FGL30,#FGL25,#FGL86,#FGL85,#FGL84,#FGL83,#FGL82,#FGL81,#FGL80,#BDZZS37,#BDZZS36,#BDZZS35,#BDZZS34,#BDZZS33,#BDZZS32,#BDZZS30,#BDZZS25,#BDZZS86,#BDZZS85,#BDZZS84,#BDZZS83,#BDZZS82,#BDZZS81,#BDZZS80");
			//_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#ALLFGL,#FGL37,#FGXLZS37,#rownumber,#FGL36,#FGXLZS36,#FGL35,#FGXLZS35,#FGL34,#FGXLZS34,#FGL33,#FGXLZS33,#FGL32,#FGXLZS32,#FGL30,#FGXLZS30,#FGL25,#FGXLZS25,#FGL86,#FGXLZS86,#FGL85,#FGXLZS85,#FGL84,#FGXLZS84,#FGL83,#FGXLZS83,#FGL82,#FGXLZS82,#FGL81,#FGXLZS81,#FGL80,#FGXLZS80");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","bdzztjLink");
		    	}
			}
	    	_dataGrid.$body.find(".bdzztjLink").click(function(e) {
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
		bdzzcxtjDetailView = null;
		if (bdzzcxtjDetailView == null) {
			var mvc = new bdzztj.views.bdzzxxDetailMainViewController();
			bdzzcxtjDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var jclx = editor.jclx.value;		//监测类型的值
		var bdzmc = editor.bdzmc.value;		//变电站名称
		var sccj = editor.sccj.value;		//生产厂家
		var tyrq = editor.tyrq.value;		//投运日期
		var ssdw = item.item.values.SSWSMC;						//所属单位
		//bdzzcxtjDetailView.searchBox.editors[0].setValue(ssdw);
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
		
		if(null != bdzmc && '' != bdzmc.trim()){		//如果变电站名称不为空
    		filter.push("bdzmc = " + bdzmc);
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
			//bdzzcxtjDetailView.searchBox.editors[0].value=ssdw_searchBox;
    	}else{
    		ssdw = item.item.values.LINKEDPROVICEDEPT;
    		filter.push("ssdw = " + ssdw);
    		//bdzzcxtjDetailView.searchBox.editors[0].value=ssdw;
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		var dydjMap ={"25":"交流35kV","30":"交流66kV","32":"交流110kV","33":"交流220kV","34":"交流330kV","35":"交流500kV","36":"交流750kV","37":"交流1000kV","80":"直流120kV","81":"直流125kV","82":"直流400kV","83":"直流500kV","84":"直流660kV","85":"直流800kV","86":"直流1000kV"};
		var dydjmc = dydjMap[dydj];
		//bdzzcxtjDetailView.searchBox.editors[1].setValue(dydjmc);
		//bdzzcxtjDetailView.searchBox.editors[1].value=dydj;
		//bdzzcxtjDetailView.searchBox.editors[6].value=tyrq;
		//bdzzcxtjDetailView.searchBox.setFieldVisible("tyrq", false)
		//bdzzcxtjDetailView.searchBox.editors[0].setEnabled(false);
		//bdzzcxtjDetailView.searchBox.editors[1].setEnabled(false);
		bdzzcxtjDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		bdzzcxtjDetailView.dataGrid.load();
		me.view.detailWin.setWidth("900");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("变电监测装置信息列表");
		me.view.detailWin.setView(bdzzcxtjDetailView);
		me.view.detailWin.showDialog();
	}else if(colName.substring(0,5)=="FGBDZ"){
		var	bdzzTwoDetailView = null;
		if (bdzzTwoDetailView == null) {
			var mvc = new bdzztj.views.bdzzxxTwoDetailViewController();
			bdzzTwoDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var jclx = editor.jclx.value;		//监测类型的值
		var bdzmc = editor.bdzmc.value;		//线路名称
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
		
		if(null != bdzmc && '' != bdzmc.trim()){		//如果线路名称不为空
    		filter.push("bdzmc = " + bdzmc);
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
		bdzzTwoDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		bdzzTwoDetailView.dataGrid.load();
		me.view.detailWin.setWidth("900");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("覆盖变电站查询");
		me.view.detailWin.setView(bdzzTwoDetailView);
		me.view.detailWin.showDialog();
		
	}
}

    /**
     * 点击导出的方法
     */
    me._dydjExportBtn_click= function(){
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "变电监测装置按电压等级统计详情"
        });
    }

    /**
     * 点击按电压等级统计按钮触发的事件 
     */
    me._dydjQueryBtn_click = function(){
    	var _dataGrid = me.view.getDataGrid();
    	var _seSearchBox = me.view.getSearchBox();
    	var dydj = _seSearchBox.editors.dydj.value;
	 	var params = _seSearchBox.getSearchParam();
        _dataGrid.setFilter(params);
        
    	hidetile(_dataGrid,dydj);
    	
    	_dataGrid.load();
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
    				var cloumns = [{name: "group8",caption: "交流35kV",
							columns :[
								{ name: "ZZZS25", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGBDZS25", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "BDZZS25", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGL25", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流66kV"){
    				 var cloumns = [{name: "group7",caption: "交流66kV",
							columns :[
								{ name: "ZZZS30", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGBDZS30", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "BDZZS30", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGL30", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
							]	
					 	}];
    				 _dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流110kV"){
    				var cloumns = [{name: "group6",caption: "交流110kV",
							columns :[
								{ name: "ZZZS32", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGBDZS32", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "BDZZS32", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGL32", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流220kV"){
    				var cloumns = [{name: "group5",caption: "交流220kV",
						columns :[
							{ name: "ZZZS33", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS33", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS33", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL33", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流330kV"){
    				var cloumns = [{name: "group4",caption: "交流330kV",
						columns :[
							{ name: "ZZZS34", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS34", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS34", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL34", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流500kV"){
    				var cloumns = [{name: "group3",caption: "交流500kV",
						columns :[
							{ name: "ZZZS35", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS35", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS35", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL35", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流750kV"){
    				var cloumns = [{name: "group2",caption: "交流750kV",
						columns :[
							{ name: "ZZZS36", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS36", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS36", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL36", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流1000kV"){
    				var cloumns = [{name: "group1",caption: "交流1000kV",
						columns :[
							{ name: "ZZZS37", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS37", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS37", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL37", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流1000kV"){
    				var cloumns = [{name: "group9",caption: "直流1000kV",
						columns :[
							{ name: "ZZZS86", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS86", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS86", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL86", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流800kV"){
    				var cloumns = [{name: "group10",caption: "直流800kV",
						columns :[
							{ name: "ZZZS85", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS85", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS85", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL85", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流660kV"){
    				var cloumns = [{name: "group11",caption: "直流660kV",
						columns :[
							{ name: "ZZZS84", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS84", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS84", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL84", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流500kV"){
    				var cloumns = [{name: "group12",caption: "直流500kV",
						columns :[
							{ name: "ZZZS83", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS83", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS83", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL83", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流400kV"){
    				var cloumns = [{name: "group13",caption: "直流400kV",
						columns :[
							{ name: "ZZZS82", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS82", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS82", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL82", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流125kV"){
    				var cloumns = [{name: "group14",caption: "直流125kV",
						columns :[
							{ name: "ZZZS81", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS81", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS81", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL81", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流120kV"){
    				var cloumns = [{name: "group15",caption: "直流120kV",
						columns :[
							{ name: "ZZZS80", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGBDZS80", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "BDZZS80", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL80", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGrid.appendColumns(cloumns);
    			}
			}
			var cloumns = [{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLZZZS", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGBDZS", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLBDZZS", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGL", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
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
						{ name: "FGBDZS37", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS37", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL37", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group2",caption: "750kV",
					columns :[
						{ name: "ZZZS36", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS36", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS36", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL36", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group3",caption: "500kV",
					columns :[
						{ name: "ZZZS35", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS35", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS35", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL35", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group4",caption: "330kV",
					columns :[
						{ name: "ZZZS34", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS34", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS34", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL34", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group5",caption: "220kV",
					columns :[
						{ name: "ZZZS33", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS33", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS33", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL33", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group6",caption: "110kV",
					columns :[
						{ name: "ZZZS32", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS32", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS32", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL32", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group7",caption: "66kV",
					columns :[
						{ name: "ZZZS30", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS30", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS30", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL30", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group8",caption: "35kV",
					columns :[
						{ name: "ZZZS25", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS25", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS25", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL25", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group9",caption: "±1000kV",
					columns :[
						{ name: "ZZZS86", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS86", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS86", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL86", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group10",caption: "±800kV",
					columns :[
						{ name: "ZZZS85", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS85", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS85", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL85", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]
				},
				{name: "group11",caption: "±660kV",
					columns :[
						{ name: "ZZZS84", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS84", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS84", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL84", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group12",caption: "±500kV",
					columns :[
						{ name: "ZZZS83", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS83", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS83", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL83", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group13",caption: "±400kV",
					columns :[
						{ name: "ZZZS82", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS82", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS82", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL82", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group14",caption: "±125kV",
					columns :[
						{ name: "ZZZS81", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS81", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS81", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL81", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group15",caption: "±120kV",
					columns :[
						{ name: "ZZZS80", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS80", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "BDZZS80", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL80", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLZZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGBDZS", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLBDZZS", caption: "变电站总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGL", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				}
			];
			_dataGrid.appendColumns(columnsall);
    	}
    }
    
    return me.endOfClass(arguments);
};