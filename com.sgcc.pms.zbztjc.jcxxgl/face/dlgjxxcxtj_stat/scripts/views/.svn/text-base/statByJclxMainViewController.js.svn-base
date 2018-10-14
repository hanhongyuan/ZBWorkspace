$ns("dlgjxxcxtj_stat.views");

$import("dlgjxxcxtj_stat.views.statByJclxMainView");

dlgjxxcxtj_stat.views.statByJclxMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
    var gjxxctjDetailView = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dlgjxxcxtj_stat.views.statByJclxMainView({ controller: me });
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
    	var param = me.view.getSearchBox().getSearchParam();				//获取SearchBox
    	var jclx = me.view.getSearchBox().editors.jclx.value;				//监测类型的值
		hidetile(me.view.getDataGrid(),jclx);
		me.view.getDataGrid().setFilter(param);
		me.view.getDataGrid().load();
    }
    
    function hidetile(dataGird,jclx){
    	dataGird.clearColumns();
    	var columns = null;
    	if(null != jclx && '' != jclx.trim()){
    		var param = jclx.split(",");
    		columns = [
					{name: "OBJ_ID", caption: "所属单位编码",dataAlign:"center", editorType: "TextEditor" },
					{name: "WSDEPMC", caption: "所属地市", dataAlign:"center", editorType: "TextEditor"  }
		    	];
			dataGird.appendColumns(columns);
			for(var i=0;i<param.length;i++){
				if("031001" == param[i]){
					columns = [
						{ name: "group1", caption: "电缆局部放电",
							columns:[
								{ name: "CNT031001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT031001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT031001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE031001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
						}
			        ];
					dataGird.appendColumns(columns);
				}else if("031002" == param[i]){
					columns = [
							{ name: "group2", caption: "护层接地电流",
								columns:[
									{ name: "CNT031002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT031002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT031002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE031002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
						         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("031003" == param[i]){
					columns = [
							{ name: "group3", caption: "分布式光纤测温",
								columns:[
									{ name: "CNT031003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT031003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT031003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE031003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
						         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("031004" == param[i]){
					columns = [
							{ name: "group4", caption: "电缆油压",
								columns:[
									{ name: "CNT031004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT031004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT031004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE031004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
						         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("032001" == param[i]){
					columns = [
							{ name: "group5", caption: "水位",
								columns:[
									{ name: "CNT032001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT032001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT032001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE032001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							    ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("032002" == param[i]){
					columns = [
							{ name: "group6", caption: "供电",
								columns:[
									{ name: "CNT032002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT032002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT032002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE032002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							     ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("032003" == param[i]){
					columns = [
							{ name: "group7", caption: "通风",
								columns:[
									{ name: "CNT032003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT032003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT032003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE032003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
				         		]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("032004" == param[i]){
					columns = [
							{ name: "group8", caption: "排水",
								columns:[
									{ name: "CNT032004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT032004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT032004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE032004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
								         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("032005" == param[i]){
					columns = [
						{ name: "group9", caption: "照明",
							columns:[
								{ name: "CNT032005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
		         			]
						}
					  ];
					dataGird.appendColumns(columns);
				}else if("032006" == param[i]){
					columns = [
						{ name: "group10", caption: "沉降",
							columns:[
								{ name: "CNT032006", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032006", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032006", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032006", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
						}
					  ];
					dataGird.appendColumns(columns);
				}else if("032007" == param[i]){
					columns = [
							{ name: "group11", caption: "气体",
								columns:[
									{ name: "CNT032007", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT032007", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT032007", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE032007", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
						         ]
							}
					 ];
					dataGird.appendColumns(columns);
				}else if("032008" == param[i]){
					columns = [
							{ name: "group12", caption: "环境温度",
								columns:[
									{ name: "CNT032008", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT032008", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT032008", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE032008", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
						         ]
							}
			    	];
					dataGird.appendColumns(columns);
				}else if("032009" == param[i]){
					columns = [
							{ name: "group13", caption: "电子井盖",
								columns:[
									{ name: "CNT032009", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HCNT032009", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "NCNT032009", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
									{ name: "HANDLERATE032009", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
								 ]
							 }
					];
					dataGird.appendColumns(columns);
				}else if("032012" == param[i]){
					columns = [
						{ name: "group14", caption: "隧道门禁",
							columns:[
								{ name: "CNT032012", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032012", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032012", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032012", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
						}
					];
					dataGird.appendColumns(columns);
				}else if("032013" == param[i]){
					columns = [
						{ name: "group15", caption: "防火阀",
							columns:[
								{ name: "CNT032013", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032013", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032013", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032013", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
						}
					];
					dataGird.appendColumns(columns);
				}else if("032014" == param[i]){
					columns = [
						{ name: "group16", caption: "红外对射",
							columns:[
								{ name: "CNT032014", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032014", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032014", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032014", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
						     ]
						}
					];
					dataGird.appendColumns(columns);
				}else if("032015" == param[i]){
					columns = [
						{ name: "group17", caption: "声光报警",
							columns:[
								{ name: "CNT032015", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032015", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032015", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032015", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
						}
					];
					dataGird.appendColumns(columns);
				}else if("032016" == param[i]){
					columns = [
						{ name: "group18", caption: "火灾报警",
							columns:[
								{ name: "CNT032016", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032016", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032016", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032016", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
				         	]
						}
					];
					dataGird.appendColumns(columns);
				}else if("032017" == param[i]){
					columns = [
						{ name: "group19", caption: "灭火装置",
							columns:[
								{ name: "CNT032017", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032017", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032017", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032017", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
				         	]
						}
					];
					dataGird.appendColumns(columns);
				}else if("032018" == param[i]){
					columns = [
						{ name: "group20", caption: "防火门",
							columns:[
								{ name: "CNT032018", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032018", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032018", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032018", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
						}
					];
					dataGird.appendColumns(columns);
				}
			}
    	}else{
    		columns = [
				{name: "OBJ_ID", caption: "所属单位编码" },
				{name: "WSDEPMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
				{ name: "group1", caption: "电缆局部放电",
					columns:[
						{ name: "CNT031001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT031001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT031001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE031001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group2", caption: "护层接地电流",
					columns:[
						{ name: "CNT031002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT031002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT031002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE031002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{ name: "group3", caption: "分布式光纤测温",
					columns:[
								{ name: "CNT031003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT031003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT031003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE031003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group4", caption: "电缆油压",
					columns:[
								{ name: "CNT031004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT031004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT031004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE031004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group5", caption: "水位",
					columns:[
								{ name: "CNT032001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group6", caption: "供电",
					columns:[
								{ name: "CNT032002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT032002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT032002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE032002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
			
				{ name: "group7", caption: "通风",
					columns:[
						{ name: "CNT032003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
			
				{ name: "group8", caption: "排水",
					columns:[
						{ name: "CNT032004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group9", caption: "照明",
					columns:[
						{ name: "CNT032005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group10", caption: "沉降",
					columns:[
						{ name: "CNT032006", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032006", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032006", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032006", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group11", caption: "气体",
					columns:[
						{ name: "CNT032007", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032007", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032007", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032007", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group12", caption: "环境温度",
					columns:[
						{ name: "CNT032008", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032008", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032008", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032008", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group13", caption: "电子井盖",
					columns:[
						{ name: "CNT032009", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032009", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032009", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032009", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{ name: "group14", caption: "隧道门禁",
					columns:[
						{ name: "CNT032012", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032012", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032012", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032012", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{ name: "group15", caption: "防火阀",
					columns:[
						{ name: "CNT032013", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032013", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032013", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032013", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{ name: "group16", caption: "红外对射",
					columns:[
						{ name: "CNT032014", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032014", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032014", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032014", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{ name: "group17", caption: "声光报警",
					columns:[
						{ name: "CNT032015", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032015", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032015", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032015", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{ name: "group18", caption: "火灾报警",
					columns:[
						{ name: "CNT032016", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032016", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032016", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032016", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{ name: "group19", caption: "灭火装置",
					columns:[
						{ name: "CNT032017", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032017", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032017", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032017", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{ name: "group20", caption: "防火门",
					columns:[
						{ name: "CNT032018", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT032018", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT032018", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE032018", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				}
			];
			dataGird.appendColumns(columns);
    	}
		
		columns = [
					{name: "group21",caption: "合计",
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
    	var _col = _dataGrid.$body.find("td").not("#OBJ_ID,#rownumber,#WSDEPMC,#HANDLERATE031001,#HANDLERATE031002,#HANDLERATE031003,#HANDLERATE031004,#HANDLERATE032001,#HANDLERATE032002,#HANDLERATE032003,#HANDLERATE032004,#HANDLERATE032005,#HANDLERATE032006,#HANDLERATE032007,#HANDLERATE032008,#HANDLERATE032009,#HANDLERATE032012,#HANDLERATE032013,#HANDLERATE032014,#HANDLERATE032015,#HANDLERATE032016,#HANDLERATE032017,#HANDLERATE032018,#ALLHANDLERATE");
		
    	for(var i=0;i<_col.length;i++){
			if("0" != _col[i].innerText && "" != _col[i].innerText ){
	    		_col[i].setAttribute("class","ywjxLinkDl");
	    	}
		}
		
    	_dataGrid.$body.find(".ywjxLinkDl").click(function(e) {
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
		gjxxctjDetailView = null;
		var mvc = new dlgjxxDetail.views.gjxxDetailMainViewController();
		gjxxctjDetailView = mvc.getView();
	
		var editor = me.view.controls[0].editors;
		
		var dlmc = editor.dlmc.value;					//电力电缆及通道名称
		var gjjb = editor.gjjb.value;					//告警级别
		var gjsj = editor.gjsj.value;					//告警时间
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
		
		if(null != dlmc && '' != dlmc.trim()){		//如果电力电缆及通道名称不为空
    		filter.push("dlmc = " + dlmc);
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
    	me.view.detailWin.setTitle("电缆告警信息列表");
		me.view.detailWin.setView(gjxxctjDetailView);
		me.view.detailWin.showDialog();
	}
    /**
     * 导出
     */
    me._jclxExportBtn_click = function(){
    	 
    	 try {
    		 var _dataGrid = me.view.getDataGrid();
    	    	var util = new mxpms.utils.FormDocumentUtil({
    	    		title:"",
    	    		view : _dataGrid.$grid,
    	    		filename : "电缆告警信息按监测类型统计"
    	    	});
    	    	 util.exportExcel();
    	    	 var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
 				var transactionType = "状态监测-监测信息查询-电缆告警信息按监测类型统计";		//此处为页面的菜单路径信息
 				var result = "操作成功"								//此处为操作结果，二选一
 				var userlogg = new userlogger.views.MainViewController();
 				userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			mx.indicate("info","导出失败");
			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测信息查询-电缆告警信息按监测类型统计";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);	
		}
    }
    
    return me.endOfClass(arguments);
};