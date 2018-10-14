$ns("sdgjxxcxtj_stat.views");
$import("sdgjxxcxtj_stat.views.statByDydjMainView");

sdgjxxcxtj_stat.views.statByDydjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
    var gjxxctjDetailView = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdgjxxcxtj_stat.views.statByDydjMainView({ controller: me });
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
    
    /**
     * 点击按电压等级统计按钮触发的事件 
     */
    me._dydjQueryBtn_click = function(){
    	var param = me.view.getSearchBox().getSearchParam();				//获取SearchBox
    	var dydj = me.view.getSearchBox().editors.dydj.value;				//电压等级的值
		hidetile(me.view.getDataGrid(),dydj);
		me.view.getDataGrid().setFilter(param);
		me.view.getDataGrid().load();
    }
    
    function hidetile(dataGird,dydj){
    	dataGird.clearColumns();
    	var columns = null;
    	if(null != dydj && '' != dydj.trim()){
    		var param = dydj.split(",");
    		columns = [
					{name: "OBJ_ID", caption: "所属单位编码",dataAlign:"center", editorType: "TextEditor" },
					{name: "WSDEPMC", caption: "所属地市", dataAlign:"center", editorType: "TextEditor"  }
		    	];
		    	dataGird.appendColumns(columns);
		    for(var i=0;i<param.length;i++){
				if("37" == param[i]){
					columns = [
						{name: "group1",caption: "1000kV",
							columns :[
								{ name: "CNT37", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT37", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT37", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE37", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}
			        ];
					dataGird.appendColumns(columns);
				}else if("36" == param[i]){
					columns = [
						{name: "group2",caption: "750kV",
							columns :[
								{ name: "CNT36", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT36", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT36", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE36", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}
			    	];
					dataGird.appendColumns(columns);
				}else if("35" == param[i]){
					columns = [
						{name: "group3",caption: "500kV",
							columns :[
								{ name: "CNT35", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT35", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT35", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE35", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}
			    	];
					dataGird.appendColumns(columns);
				}else if("34" == param[i]){
					columns = [
						{name: "group4",caption: "330kV",
							columns :[
								{ name: "CNT34", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT34", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT34", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE34", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}
			    	];
					dataGird.appendColumns(columns);
				}else if("33" == param[i]){
					columns = [
						{name: "group5",caption: "220kV",
							columns :[
								{ name: "CNT33", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT33", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT33", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE33", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}
			    	];
					dataGird.appendColumns(columns);
				}else if("32" == param[i]){
					columns = [
						{name: "group6",caption: "110kV",
							columns :[
								{ name: "CNT32", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT32", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT32", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE32", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						}
			    	];
					dataGird.appendColumns(columns);
				}else if("30" == param[i]){
					columns = [
								{name: "group7",caption: "66kV",
									columns :[
										{ name: "CNT30", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT30", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT30", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE30", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
									]	
								}
					    	];
					dataGird.appendColumns(columns);
				}else if("25" == param[i]){
						columns = [
									{name: "group8",caption: "35kV",
										columns :[
											{ name: "CNT25", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT25", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT25", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE25", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
									}
						    	];
					dataGird.appendColumns(columns);
				}else if("86" == param[i]){
						columns = [
									{name: "group9",caption: "±1000kV",
										columns :[
											{ name: "CNT86", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT86", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT86", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE86", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
									}
						    	];
					dataGird.appendColumns(columns);
				}else if("85" == param[i]){
							columns = [
										{name: "group10",caption: "±800kV",
											columns :[
												{ name: "CNT85", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HCNT85", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "NCNT85", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HANDLERATE85", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
											]
										}
							    	];
				dataGird.appendColumns(columns);
				}else if("84" == param[i]){
							columns = [
										{name: "group11",caption: "±660kV",
											columns :[
												{ name: "CNT84", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HCNT84", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "NCNT84", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HANDLERATE84", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
											]	
										}
							    	];
					dataGird.appendColumns(columns);
				}else if("83" == param[i]){
							columns = [
										{name: "group12",caption: "±500kV",
											columns :[
												{ name: "CNT83", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HCNT83", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "NCNT83", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HANDLERATE83", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
											]	
										}
							    	];
					dataGird.appendColumns(columns);
				}else if("82" == param[i]){
						columns = [
									{name: "group13",caption: "±400kV",
										columns :[
											{ name: "CNT82", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT82", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT82", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE82", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
									}
						    	];
					dataGird.appendColumns(columns);
				} else if("81" == param[i]){
						columns = [
									{name: "group14",caption: "±125kV",
										columns :[
											{ name: "CNT81", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT81", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT81", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE81", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
									}
						    	];
					dataGird.appendColumns(columns);
				}else if("80" == param[i]){
						columns = [
									{name: "group15",caption: "±120kV",
										columns :[
											{ name: "CNT80", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT80", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT80", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE80", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
									}
						    	];
					dataGird.appendColumns(columns);
				}
			}
    	}else{
    		columns = [
						{name: "OBJ_ID", caption: "所属单位编码",dataAlign:"center", editorType: "TextEditor" },
						{name: "WSDEPMC", caption: "所属地市", dataAlign:"center", editorType: "TextEditor"  },
						{name: "group1",caption: "1000kV",
							columns :[
								{ name: "CNT37", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT37", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT37", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE37", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group2",caption: "750kV",
							columns :[
								{ name: "CNT36", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT36", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT36", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE36", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group3",caption: "500kV",
							columns :[
								{ name: "CNT35", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT35", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT35", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE35", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group4",caption: "330kV",
							columns :[
								{ name: "CNT34", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT34", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT34", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE34", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group5",caption: "220kV",
							columns :[
								{ name: "CNT33", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT33", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT33", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE33", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group6",caption: "110kV",
							columns :[
								{ name: "CNT32", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT32", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT32", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE32", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group7",caption: "66kV",
									columns :[
										{ name: "CNT30", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HCNT30", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "NCNT30", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
										{ name: "HANDLERATE30", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
									]	
						},
						{name: "group8",caption: "35kV",
										columns :[
											{ name: "CNT25", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT25", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT25", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE25", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
						},
						{name: "group9",caption: "±1000kV",
										columns :[
											{ name: "CNT86", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT86", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT86", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE86", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
						},
						{name: "group10",caption: "±800kV",
										columns :[
											{ name: "CNT85", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT85", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT85", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE85", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]
						},
						{name: "group11",caption: "±660kV",
											columns :[
												{ name: "CNT84", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HCNT84", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "NCNT84", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HANDLERATE84", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
											]	
						},
						{name: "group12",caption: "±500kV",
											columns :[
												{ name: "CNT83", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HCNT83", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "NCNT83", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
												{ name: "HANDLERATE83", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
											]	
						},
						{name: "group13",caption: "±400kV",
										columns :[
											{ name: "CNT82", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT82", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT82", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE82", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
						},
						{name: "group14",caption: "±125kV",
										columns :[
											{ name: "CNT81", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT81", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT81", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE81", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
						},
						{name: "group15",caption: "±120kV",
										columns :[
											{ name: "CNT80", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HCNT80", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "NCNT80", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "HANDLERATE80", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
										]	
						}
			    	];
	    	dataGird.appendColumns(columns);
    	}
		
		
		columns = [
					{name: "group16",caption: "合计",
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
    
    /**
     * 初始化DataGrid
     */
    me._initDataGrid = function(){
    	var _dataGrid = me.view.getDataGrid();
    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#WSDEPMC,#ALLHANDLERATE,#HANDLERATE37,#rownumber,#HANDLERATE36,#HANDLERATE35,#HANDLERATE34,#HANDLERATE33,#HANDLERATE32,#HANDLERATE30,#HANDLERATE25,#HANDLERATE86,#HANDLERATE85,#HANDLERATE84,#HANDLERATE83,#HANDLERATE82,#HANDLERATE81,#HANDLERATE80");
		
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
    
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showDetail = function(item, colName) {
		gjxxctjDetailView = null;
		var mvc = new sdgjxxDetail.views.gjxxDetailMainViewController();
		gjxxctjDetailView = mvc.getView();
		
		var editor = me.view.controls[0].editors;
		var jclx = editor.jclx.value;		//监测类型的值
		var xlmc = editor.xlmc.value;		//线路名称
		var gjjb = editor.gjjb.value;		//告警级别
		var gjsj = editor.gjsj.value;		//告警时间
		var ssdw = item.item.id;						//所属单位
		var ssdw_searchBox =  editor.ssdw.value;;		//查询框内的所属单位
		var dydj = null;
		var type = null;

		var filter = new Array();
		
		if("ALL" == colName.substring(0,3)){
			type = colName.substring(3,colName.length);
			if("CNT" == type){
				filter.push("isHandled = all");
			}else if("HCNT" == type){
				filter.push("isHandled = T");
			}else{
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
			
			dydj = colName.substring(colName.length-2,colName.length);		//电压等级
		}
		
		if(null != jclx){		//如果监测类型不为空
    		filter.push("jclx = " + jclx);
    	}
		
		if(null != dydj){		//如果电压等级不为空
    		filter.push("dydj = " + dydj);
    	}
		
		if(null != xlmc && '' != xlmc.trim()){		//如果线路名称不为空
    		filter.push("xlmc = " + xlmc);
    	}
		
		if(null != gjjb){		//如果告警级别不为空
    		filter.push("gjjb = " + gjjb);
    	}
		
		if(null != gjsj){		//如果告警时间不为空
    		filter.push("gjsj = " + gjsj);
    	}
		
		if("国家电网公司" == ssdw){		//如果所属单位为国家电网公司
			if(null != ssdw_searchBox){
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
	/**
	 * 导出事件
	 */
    me._dydjExportBtn_click = function(){
    	
    	 try {
    		 var _dataGrid = me.view.getDataGrid();
    	    	var util = new mxpms.utils.FormDocumentUtil({
    	    		title:"",
    	    		view : _dataGrid.$grid,
    	    		filename : "输电告警信息按电压等级统计"
    	    	});
    	    	 util.exportExcel(); 
    	    	 var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
 				var transactionType = "状态监测-监测信息查询-输电告警信息按电压等级统计";		//此处为页面的菜单路径信息
 				var result = "操作成功"								//此处为操作结果，二选一
 				var userlogg = new userlogger.views.MainViewController();
 				userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			mx.indicate("info","导出失败");
			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测信息查询-输电告警信息按电压等级统计";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);	
		}

    }
    
    return me.endOfClass(arguments);
};