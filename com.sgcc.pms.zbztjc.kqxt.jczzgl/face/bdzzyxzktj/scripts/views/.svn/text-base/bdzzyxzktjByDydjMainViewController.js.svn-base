$ns("bdzzyxzktj.views");
$import("bdzzyxzktj.views.bdzzyxzktjByDydjMainView");
$import("bdzzyxzktj.views.bdzzyxzkDetailMainViewController");


bdzzyxzktj.views.bdzzyxzktjByDydjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzzyxzktj.views.bdzzyxzktjByDydjMainView({ controller: me });
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
    
    me._initBdDydjDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#ALLFGL,#ALLZZZS,#ALLWSSJRS,#ALLSSJRZZS,#ALLSSJRL,#SSJRL37,#rownumber,#SSJRL36,#SSJRL35,#SSJRL34,#SSJRL33,#SSJRL32,#SSJRL30,#SSJRL25,#SSJRL86,#SSJRL85,#SSJRL84,#SSJRL83,#SSJRL82,#SSJRL81,#SSJRL80,#WSSJRS37,#WSSJRS36,#WSSJRS35,#WSSJRS34,#WSSJRS33,#WSSJRS32,#WSSJRS30,#WSSJRS25,#WSSJRS86,#WSSJRS85,#WSSJRS84,#WSSJRS83,#WSSJRS82,#WSSJRS81,#WSSJRS80");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","bdzzyxzktjLink");
		    	}
			}
	    	_dataGrid.$body.find(".bdzzyxzktjLink").click(function(e) {
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
			var mvc = new bdzzyxzktj.views.bdzzyxzkDetailMainViewController();
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
		
		if("国家电网公司" == ssdw){		//如果所属单位为国家电网公司
			if(null != ssdw_searchBox){
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
	}
}
    
	
    /**
     * 点击按电压等级统计按钮触发的事件 
     */
    me._dydjQueryBtn_click = function(){
    	var _dataGrid = me.view.getDataGrid();
    	var _seSearchBox = me.view.getSearchBox();
    	var params = _seSearchBox.getSearchParam();
		var dydj = _seSearchBox.editors.dydj.value;
        _dataGrid.setFilter(params);
    	//hidetile(_dataGrid,dydj);
    	_dataGrid.load();
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
								{ name: "ZZZS25", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGXLZS25", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "XLZS25", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGL25", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}]
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流66kV"){
    				 var cloumns = [{name: "group7",caption: "交流66kV",
							columns :[
								{ name: "ZZZS30", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGXLZS30", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "XLZS30", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGL30", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
							]	
					 	}];
					_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流110kV"){
    				var cloumns = [{name: "group6",caption: "交流110kV",
							columns :[
								{ name: "ZZZS32", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGXLZS32", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "XLZS32", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FGL32", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流220kV"){
    				var cloumns = [{name: "group5",caption: "交流220kV",
						columns :[
							{ name: "ZZZS33", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS33", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS33", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL33", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流330kV"){
    				var cloumns = [{name: "group4",caption: "交流330kV",
						columns :[
							{ name: "ZZZS34", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS34", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS34", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL34", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流500kV"){
    				var cloumns = [{name: "group3",caption: "交流500kV",
						columns :[
							{ name: "ZZZS35", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS35", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS35", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL35", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流750kV"){
    				var cloumns = [{name: "group2",caption: "交流750kV",
						columns :[
							{ name: "ZZZS36", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS36", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS36", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL36", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="交流1000kV"){
    				var cloumns = [{name: "group1",caption: "交流1000kV",
						columns :[
							{ name: "ZZZS37", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS37", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS37", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL37", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
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
							{ name: "ZZZS85", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS85", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS85", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL85", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流660kV"){
    				var cloumns = [{name: "group11",caption: "直流660kV",
						columns :[
							{ name: "ZZZS84", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS84", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS84", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL84", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流500kV"){
    				var cloumns = [{name: "group12",caption: "直流500kV",
						columns :[
							{ name: "ZZZS83", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS83", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS83", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL83", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流400kV"){
    				var cloumns = [{name: "group13",caption: "直流400kV",
						columns :[
							{ name: "ZZZS82", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS82", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS82", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL82", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流125kV"){
    				var cloumns = [{name: "group14",caption: "直流125kV",
						columns :[
							{ name: "ZZZS81", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS81", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS81", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL81", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}else if (dydjMap[dydjArr[i]]=="直流120kV"){
    				var cloumns = [{name: "group15",caption: "直流120kV",
						columns :[
							{ name: "ZZZS80", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGXLZS80", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "XLZS80", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "FGL80", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}];
    				_dataGird.appendColumns(cloumns);
    			}
			}
			var cloumns = [{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLZZZS", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGXLZS", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLXLZS", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGL", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
			}];
    		_dataGird.appendColumns(cloumns);
    	}  else{
    		_dataGird.clearColumns();
    		var cloumnsall = [
				{name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
				{name: "group1",caption: "1000kV",
					columns :[
						{ name: "ZZZS37", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS37", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS37", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL37", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group2",caption: "750kV",
					columns :[
						{ name: "ZZZS36", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS36", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS36", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL36", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group3",caption: "500kV",
					columns :[
						{ name: "ZZZS35", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS35", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS35", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL35", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group4",caption: "330kV",
					columns :[
						{ name: "ZZZS34", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS34", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS34", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL34", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group5",caption: "220kV",
					columns :[
						{ name: "ZZZS33", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS33", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS33", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL33", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group6",caption: "110kV",
					columns :[
						{ name: "ZZZS32", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS32", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS32", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL32", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group7",caption: "66kV",
					columns :[
						{ name: "ZZZS30", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS30", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS30", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL30", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group8",caption: "35kV",
					columns :[
						{ name: "ZZZS25", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS25", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS25", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL25", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group9",caption: "±1000kV",
					columns :[
						{ name: "ZZZS86", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS86", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS86", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL86", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group10",caption: "±800kV",
					columns :[
						{ name: "ZZZS85", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS85", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS85", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL85", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]
				},
				{name: "group11",caption: "±660kV",
					columns :[
						{ name: "ZZZS84", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS84", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS84", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL84", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group12",caption: "±500kV",
					columns :[
						{ name: "ZZZS83", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS83", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS83", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL83", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group13",caption: "±400kV",
					columns :[
						{ name: "ZZZS82", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS82", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS82", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL82", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group14",caption: "±125kV",
					columns :[
						{ name: "ZZZS81", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS81", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS81", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL81", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group15",caption: "±120kV",
					columns :[
						{ name: "ZZZS80", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGXLZS80", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "XLZS80", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGL80", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLZZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGXLZS", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLXLZS", caption: "线路总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGL", caption: "覆盖率", dataAlign:"center",editorType: "TextEditor" }
					]	
				}
			];
			_dataGird.appendColumns(cloumnsall);
    	}
    	
    }
    
    return me.endOfClass(arguments);
};