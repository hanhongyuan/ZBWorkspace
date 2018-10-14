$ns("bdgjxxcxtj_stat.views");
$import("bdgjxxcxtj_stat.views.statByJclxMainView");

bdgjxxcxtj_stat.views.statByJclxMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
    var gjxxctjDetailView = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdgjxxcxtj_stat.views.statByJclxMainView({ controller: me });
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
    	var jclx = me._searchBox.editors.jclx.value;		//电压等级的值
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
					{ name: "group1", caption: "局部放电",
						columns:[
							{ name: "CNT021001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "HCNT021001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "NCNT021001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
							{ name: "HANDLERATE021001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
						         ]
					},
					{ name: "group2", caption: "油中溶解气体",
							columns:[
								{ name: "CNT021002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT021002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT021002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE021002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
					},
					{ name: "group3", caption: "微水",
							columns:[
										{ name: "CNT021003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT021003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT021003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE021003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
									         ]
					},
					{ name: "group4", caption: "铁芯接地电流",
						columns:[
									{ name: "CNT021004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT021004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT021004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE021004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
								         ]
					},
					{ name: "group5", caption: "顶层油温",
							columns:[
										{ name: "CNT021005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT021005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT021005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE021005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
									         ]
					},
					{ name: "group6", caption: "绝缘监测",
							columns:[
										{ name: "CNT022001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT022001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT022001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE022001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
					},
					{ name: "group7", caption: "金属氧化物避雷器",
								columns:[
									{ name: "CNT023001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT023001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT023001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE023001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
					},
					{ name: "group8", caption: "断路器局部放电",
									columns:[
										{ name: "CNT024001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT024001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT024001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE024001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
									         ]
					},
					{ name: "group9", caption: "分合闸线圈电流波形",
									columns:[
										{ name: "CNT024002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT024002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT024002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE024002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
									         ]
					},
					{ name: "group10", caption: "负荷电流波形",
										columns:[
											{ name: "CNT024003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT024003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT024003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE024003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
					},
					{ name: "group11", caption: "SF6气体压力",
										columns:[
											{ name: "CNT024004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT024004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT024004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE024004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
					},
					{ name: "group12", caption: "SF6气体水分",
										columns:[
											{ name: "CNT024005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT024005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT024005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE024005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
					},
					{ name: "group13", caption: "储能电机工作状态",
									columns:[
										{ name: "CNT024006", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT024006", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT024006", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE024006", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
									         ]
					},
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
		}else{
			var param = jclx.split(",");
	    	
			columns = [
						{name: "OBJ_ID", caption: "所属单位编码",dataAlign:"center", editorType: "TextEditor" },
						{name: "WSDEPMC", caption: "所属地市", dataAlign:"center", editorType: "TextEditor"  }
			    	];
			dataGird.appendColumns(columns);
			
			for(var i=0;i<param.length;i++){
				
				if("021001" == param[i]){
					columns = [
						{ name: "group1", caption: "局部放电",
							columns:[
								{ name: "CNT021001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT021001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT021001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE021001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
						}
			        ];
					dataGird.appendColumns(columns);
				}else if("021002" == param[i]){
					columns = [
							{ name: "group2", caption: "油中溶解气体",
								columns:[
									{ name: "CNT021002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT021002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT021002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE021002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
								         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("021003" == param[i]){
					columns = [
							{ name: "group3", caption: "微水",
								columns:[
											{ name: "CNT021003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT021003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT021003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE021003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("021004" == param[i]){
					columns = [
							{ name: "group4", caption: "铁芯接地电流",
								columns:[
											{ name: "CNT021004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT021004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT021004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE021004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("021005" == param[i]){
					columns = [
							{ name: "group5", caption: "顶层油温",
								columns:[
											{ name: "CNT021005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT021005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT021005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE021005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("022001" == param[i]){
					columns = [
							{ name: "group6", caption: "绝缘监测",
								columns:[
											{ name: "CNT022001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT022001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT022001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE022001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
								         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("023001" == param[i]){
					columns = [
								{ name: "group7", caption: "金属氧化物避雷器",
									columns:[
										{ name: "CNT023001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT023001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT023001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE023001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
						         ]
								}
					    	];
					dataGird.appendColumns(columns);
				}else if("024001" == param[i]){
						columns = [
									{ name: "group8", caption: "断路器局部放电",
										columns:[
											{ name: "CNT024001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT024001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT024001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE024001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
									}
						    	];
					dataGird.appendColumns(columns);
				}else if("024002" == param[i]){
						columns = [
									{ name: "group9", caption: "分合闸线圈电流波形",
										columns:[
											{ name: "CNT024002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT024002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT024002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE024002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
										         ]
									}
						    	];
					dataGird.appendColumns(columns);
				}else if("024003" == param[i]){
							columns = [
										{ name: "group10", caption: "负荷电流波形",
											columns:[
												{ name: "CNT024003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HCNT024003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "NCNT024003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HANDLERATE024003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
											         ]
										}
							    	];
				dataGird.appendColumns(columns);
				}else if("024004" == param[i]){
							columns = [
										{ name: "group11", caption: "SF6气体压力",
											columns:[
												{ name: "CNT024004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HCNT024004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "NCNT024004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HANDLERATE024004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
											         ]
										}
							    	];
					dataGird.appendColumns(columns);
				}else if("024005" == param[i]){
							columns = [
										{ name: "group12", caption: "SF6气体水分",
											columns:[
												{ name: "CNT024005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HCNT024005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "NCNT024005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HANDLERATE024005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
											         ]
										}
							    	];
					dataGird.appendColumns(columns);
				}else if("024006" == param[i]){
						columns = [
									{ name: "group13", caption: "储能电机工作状态",
										columns:[
											{ name: "CNT024006", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT024006", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT024006", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE024006", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
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
    	var _col = _dataGrid.$body.find("td").not("#OBJ_ID,#WSDEPMC,#ALLHANDLERATE,#HANDLERATE012001,#rownumber,#HANDLERATE021001,#HANDLERATE021002,#HANDLERATE021003,#HANDLERATE021004,#HANDLERATE021005,#HANDLERATE022001,#HANDLERATE023001,#HANDLERATE024001,#HANDLERATE024002,#HANDLERATE024003,#HANDLERATE024004,#HANDLERATE024005,#HANDLERATE024006");
		
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
			var mvc = new bdgjxxDetail.views.gjxxDetailMainViewController();
			gjxxctjDetailView = mvc.getView();
		}
		
		var editor = me.view.controls[0].editors;
		
		var bdz = editor.bdz.value;			//变电站名称
		var gjjb = editor.gjjb.value;		//告警级别
		var gjsj = editor.gjsj.value;		//告警时间
		
		var ssdw = item.item.id;						//所属单位
		var ssdw_searchBox =  editor.ssdw.value;;		//查询框内的所属单位
		
		var dydj = editor.dydj.value;		//电压等级
		var type = null;
		var jclx = editor.jclx.value;
		
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
		
		if(null != bdz && '' != bdz.trim()){		//如果变电站名称不为空
    		filter.push("bdz = " + bdz);
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
    	me.view.detailWin.setTitle("变电告警信息列表");
		me.view.detailWin.setView(gjxxctjDetailView);
		me.view.detailWin.showDialog();
	}
    /**
     * 导出
     */
    me._jclxExportBtn_click = function(){
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "变电告警信息按监测类型统计统计"
        });
    }
    
    return me.endOfClass(arguments);
};