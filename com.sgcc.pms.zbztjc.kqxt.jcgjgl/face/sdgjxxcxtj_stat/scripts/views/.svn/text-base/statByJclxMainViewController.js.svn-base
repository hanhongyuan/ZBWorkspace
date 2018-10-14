$ns("sdgjxxcxtj_stat.views");
$import("sdgjxxcxtj_stat.views.statByJclxMainView");

sdgjxxcxtj_stat.views.statByJclxMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
    var gjxxctjDetailView = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdgjxxcxtj_stat.views.statByJclxMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
		if (me.view != null && typeof(me.view.dataGrid) != "undefined"){
	 	    me.view.dataGrid.load();
		}	
    };
    
    /**
     * 点击按电压等级统计按钮触发的事件 
     */
    me._jclxQueryBtn_click = function(){
    	me._searchBox = me.view.getSearchBox();				//获取SearchBox
    	var str = me._searchBox.getSearchParam();
    	var jclx = me._searchBox.editors.jclx.value;		//监测类型的值
    	hidetile(me.view.getDataGrid(),jclx);				//隐藏多余的列
    	me.view.getDataGrid().setFilter(str);
		me.view.getDataGrid().load();
    }
    
    function hidetile(dataGird,jclx){
    	dataGird.clearColumns();
		var columns = null;
		if(null == jclx || '' == jclx.trim()){
			columns = [
						{name: "OBJ_ID", caption: "所属单位编码",dataAlign:"center", editorType: "TextEditor" },
						{name: "WSDEPMC", caption: "所属地市", dataAlign:"center", editorType: "TextEditor"  },
						{ name: "group1", caption: "微气象监测",
							columns:[
								{ name: "CNT018001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT018001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT018001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE018001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
						},
						{ name: "group2", caption: "导线弧垂",
							columns:[
								{ name: "CNT013001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT013001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT013001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE013001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
						},
						{ name: "group3", caption: "导线温度",
							columns:[
										{ name: "CNT013002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT013002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT013002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE013002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
									         ]
						},
						{ name: "group4", caption: "微风振动",
							columns:[
										{ name: "CNT013003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT013003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT013003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE013003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
									         ]
						},
						{ name: "group5", caption: "风偏",
							columns:[
										{ name: "CNT013004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT013004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT013004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE013004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
									         ]
						},
						{ name: "group6", caption: "覆冰",
							columns:[
										{ name: "CNT013005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT013005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT013005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE013005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
						},
						{ name: "group7", caption: "导线舞动",
							columns:[
								{ name: "CNT013006", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT013006", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT013006", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE013006", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
				         ]
						},
						{ name: "group8", caption: "杆塔倾斜",
							columns:[
								{ name: "CNT012001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT012001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT012001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE012001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
						},
						{ name: "group9", caption: "现场污秽度",
							columns:[
								{ name: "CNT014001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT014001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT014001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE014001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
						},
						{name: "group10",caption: "合计",
							columns :[
								{ name: "ALLCNT", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "ALLHCNT", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "ALLNCNT", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "ALLHANDLERATE", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}
			    	];
	    	dataGird.appendColumns(columns);
		}else{
			var param = jclx.split(",");
			columns = [
						{name: "OBJ_ID", caption: "所属单位编码",dataAlign:"center", editorType: "TextEditor" },
						{name: "WSDEPMC", caption: "所属地市", dataAlign:"center", editorType: "TextEditor"  }
			    	];
			dataGird.appendColumns(columns);
			
			for(var i=0;i<param.length;i++){
				
				if("018001" == param[i]){
					columns = [
						{ name: "group1", caption: "微气象监测",
							columns:[
								{ name: "CNT018001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT018001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT018001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE018001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
						}
			        ];
					dataGird.appendColumns(columns);
				}else if("013001" == param[i]){
					columns = [
							{ name: "group2", caption: "导线弧垂",
								columns:[
									{ name: "CNT013001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT013001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT013001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE013001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
								         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("013002" == param[i]){
					columns = [
							{ name: "group3", caption: "导线温度",
								columns:[
											{ name: "CNT013002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT013002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT013002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE013002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("013003" == param[i]){
					columns = [
							{ name: "group4", caption: "微风振动",
								columns:[
											{ name: "CNT013003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT013003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT013003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE013003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("013004" == param[i]){
					columns = [
							{ name: "group5", caption: "风偏",
								columns:[
											{ name: "CNT013004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT013004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT013004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE013004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("013005" == param[i]){
					columns = [
							{ name: "group6", caption: "覆冰",
								columns:[
											{ name: "CNT013005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT013005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT013005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE013005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
								         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("013006" == param[i]){
					columns = [
								{ name: "group7", caption: "导线舞动",
									columns:[
										{ name: "CNT013006", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT013006", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT013006", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE013006", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
						         ]
								}
					    	];
					dataGird.appendColumns(columns);
				}else if("012001" == param[i]){
						columns = [
									{ name: "group8", caption: "杆塔倾斜",
										columns:[
											{ name: "CNT012001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT012001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT012001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE012001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
									}
						    	];
					dataGird.appendColumns(columns);
				}else if("014001" == param[i]){
						columns = [
									{ name: "group9", caption: "现场污秽度",
										columns:[
											{ name: "CNT014001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT014001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT014001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE014001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
									}
						    	];
					dataGird.appendColumns(columns);
				}
			}
			columns = [
						{name: "group14",caption: "合计",
						columns :[
							{ name: "ALLCNT", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "ALLHCNT", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "ALLNCNT", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "ALLHANDLERATE", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
						]	
					}
			    	];
			dataGird.appendColumns(columns);
		}
    }
    
    /**
     * 初始化DataGrid
     */
    me._initDataGrid = function(){
    	var _dataGrid = me.view.getDataGrid();
    	var _col = _dataGrid.$body.find("td").not("#OBJ_ID,#WSDEPMC,#ALLHANDLERATE,#HANDLERATE012001,#rownumber,#HANDLERATE013001,#HANDLERATE013002,#HANDLERATE013003,#HANDLERATE013004,#HANDLERATE013005,#HANDLERATE013006,#HANDLERATE014001,#HANDLERATE018001");
		
    	for(var i=0;i<_col.length;i++){
			if("0" != _col[i].innerText && "" != _col[i].innerText ){
	    		_col[i].setAttribute("class","ywjxLink");
	    	}
		}
		
    	_dataGrid.$body.find(".ywjxLink").click(function(e) {
			var item = $(this).parent().data();
			var colName = e.target.id;
			$(this)[0].style.color = "#999999";
			showJclxDetail(item, colName);
		});
    }
    
    
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	function showJclxDetail(item, colName) {

		if (gjxxctjDetailView == null) {
			var mvc = new sdgjxxDetail.views.gjxxDetailMainViewController();
			gjxxctjDetailView = mvc.getView();
		}
		
		var editor = me.view.controls[0].editors;
		
		var jclx =  editor.jclx.value;//= colName.substring(colName.length-6,colName.length);	//监测类型
		
		var xlmc = editor.xlmc.value;		//线路名称
		var gjjb = editor.gjjb.value;		//告警级别
		var gjsj = editor.gjsj.value;		//告警时间
		
		var ssdw = item.item.id;						//所属单位
		var ssdw_searchBox =  editor.ssdw.value;;		//查询框内的所属单位
		
		var dydj = editor.dydj.value;		//电压等级
		var type =null;// colName.substring(0,colName.length-6);
		
		var filter = new Array();
		
		if("ALL" == colName.substring(0,3)){
			type = colName.substring(3,colName.length);
			if("CNT" == type){			//如果是总数
				filter.push("isHandled = all");
			}else if("HCNT" == type){		//如果是已处理数
				filter.push("isHandled = T");
			}else{						//否则是未处理数
				filter.push("isHandled = F");
			}
		}else{
			type = colName.substring(0,colName.length-2);
			if("CNT" == type){
				filter.push("isHandled = all");
			}else if("HCNT" == type){
				filter.push("isHandled = T");
			}else{
				filter.push("isHandled = F");
			}
			
			jclx = colName.substring(colName.length-6,colName.length);		//电压等级
		}
		
		if(null != jclx && '' != jclx.trim()){		//如果监测类型不为空
    		filter.push("jclx = " + jclx);
    	}
		
		if(null != dydj && '' != dydj.trim()){		//如果电压等级不为空
    		filter.push("dydj = " + dydj);
    	}
		
		if(null != xlmc && '' != xlmc.trim()){		//如果线路名称不为空
    		filter.push("xlmc = " + xlmc);
    	}
		
		if(null != gjjb && '' != gjjb.trim()){		//如果告警级别不为空
    		filter.push("gjjb = " + gjjb);
    	}
		
		if(null != gjsj){		//如果告警时间不为空
    		filter.push("gjsj = " + gjsj);
    	}
		
		if("国家电网公司" == ssdw && '' != ssdw.trim()){		//如果所属单位为国家电网公司
			if(null != ssdw_searchBox && '' != ssdw_searchBox.trim()){
				filter.push("ssdw = " + ssdw_searchBox);
			}
    	}else{
    		filter.push("ssdw = " + ssdw);
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		
		gjxxctjDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		gjxxctjDetailView.dataGrid.load();
		me.view.detailWin.setWidth("900");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("输电告警信息列表");
		me.view.detailWin.setView(gjxxctjDetailView);
		me.view.detailWin.showDialog();
	}
    
    me._jclxExportBtn_click = function(){
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "输电告警信息按监测类型统计数据列表"
        });
    }
    
    return me.endOfClass(arguments);
};