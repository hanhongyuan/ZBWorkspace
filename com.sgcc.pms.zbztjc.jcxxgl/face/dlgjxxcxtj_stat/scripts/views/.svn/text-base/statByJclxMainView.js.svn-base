$ns("dlgjxxcxtj_stat.views");

$import("mx.datacontrols.GroupHeaderGrid");
$import("mx.datacontrols.DataGrid");

dlgjxxcxtj_stat.views.statByJclxMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.searchBox = null;
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initSearchBox();
    	-initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initSearchBox(){
    	me.searchBox = new mxpms.datacontrols.AdvancedSearchBox({
			alias:"dlgjxxcxtj_statByJclx_searchbox",
			rootGroupId : "402881704230292b0142316493220006",
			PagedControl:me.dataGrid,
			height:"13%",
			itemNumOfRow:4,
			fields : [ {name : "ssdw",caption : "所属单位",hint:"---请选择---",displayCheckBox:true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_ssdw"
			},
			{	name : "dydj",caption : "电压等级",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_dydj"
			},
			{	name : "jclx",caption : "监测类型",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_jclxDl"
			},
			{	name : "dlmc",caption : "电力电缆及通道名称",hint : "--请选择--",onchanged:_nameChanged,editorType : "TextEditor"
			},
			{	name : "gjjb",caption : "告警级别",displayCheckBox: true,hint : "--请选择--",editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_gjjb"
			},
			{name : "gjsj",caption : "告警时间",displayCheckBox: true,formatString:"yyyy-MM-dd",editorType : "DateTimeEditor"}
			]
		});

		_dataGridappendButton();
		
		me.searchBox.editors.ssdw.setWidth("180px");
		me.searchBox.editors.dydj.setWidth("180px");
		me.searchBox.editors.jclx.setWidth("180px");
		me.searchBox.editors.dlmc.setWidth("180px");
		me.searchBox.editors.gjjb.setWidth("180px");
		me.searchBox.editors.gjsj.setWidth("180px");
		
		me.addControl(me.searchBox);
    }
    
    /**
     * 给SearchBox添加按钮的方法
     */
    function _dataGridappendButton() {
    	var $searchBox = me.searchBox;
    	
    	$searchBox.$("#btnTd").hide();
    	var $tbody =  $searchBox.$("#btnTr");
    	$tbody.append("<td class='newButtonTd1' colspan=8 style='padding-left:85%; '></td>");
    	var $newButtonTd1 = $searchBox.$(".newButtonTd1");
    	//定义一个按电压等级统计的按钮
    	me.queryBtn = new mxpms.controls.ImageButton({
    		text:"统计",
    		name:"queryBtn",
    		onclick:me.controller._jclxQueryBtn_click,
    		width:80,
    		border:"1px solid white"
    		
    	});
    	$newButtonTd1.append(me.queryBtn.$e);
    	//定义一个按监测类型统计的按钮
    	me.exportBtn = new mxpms.controls.ImageButton({
    		text:"导出",
    		name:"exportBtn",
    		onclick:me.controller._jclxExportBtn_click,
    		width:80,
    		border:"1px solid white"
    	});
    	
    	$newButtonTd1.append(me.exportBtn.$e);
    	
    	$searchBox.$(".newButtonTd1")[0].children[0].style.marginRight = "5px";
    }
    
    function initDataGrid(){
    	var restUrl = "~/rest/dlgjxxcxtj_stat/jclxtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : dlgjxxcxtj_stat.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});
		
		me.dataGrid = new mx.datacontrols.GroupHeaderGrid({
			width : "100%",
			height : "87%",
			alias:"jclxDataGrid",
			columns: [
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
				},
				
				
				{name: "group21",caption: "合计",
					columns :[
						{ name: "ALLCNT", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLHCNT", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLNCNT", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLHANDLERATE", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				}
			],
			// 设置查询条件。
			entityContainer : gridEntityContainer,
			displayCheckBox : false,
			displayRowNumber : true,
			allowPaging : false,
			displayPrimaryKey : false,
			displayColSplitLine : true,
			allowEditing : false,
			rowNumberColWidth : 40,
			onload:me.controller._initDataGrid
		});
		me.addControl(me.dataGrid);
		me.dataGrid.load();
    }
    
    /**
	 * 初始化表单视图窗口对象
	 */
	function _initDetailWindow() {
		me.detailWin = mxpms.utils.PortalUtil.getGlobalWindowManage().create({
			title : "列表",
			width : 900,
			height : 500,
			movable : true,
			reusable : true,
			resizable : false,
			top : "center",
			left : "center"
		});
	}
	var pattern1 = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]+$");
    function _nameChanged()
	{
		var dlmc = me.searchBox.editors.dlmc.value;
		
		if(dlmc!=null&&dlmc!="")
		{
			if(dlmc.length>50|| !pattern1.test(dlmc)){
				me.searchBox.editors.dlmc.reset();
				mx.indicate("warn", "电缆/通道名称长度不能超50且不能为特殊字符!");
			}
				
		}	
	}
	  /**
     * 获取SearchBox
     */
    me.getSearchBox = function(){
    	return me.searchBox;
    }
    
    /**
     * 获取DataGrid
     */
    me.getDataGrid = function(){
    	return me.dataGrid;
    }
    
    return me.endOfClass(arguments);
};