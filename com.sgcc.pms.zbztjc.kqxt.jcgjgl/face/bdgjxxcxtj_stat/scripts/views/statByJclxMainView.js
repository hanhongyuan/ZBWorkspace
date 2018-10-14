$ns("bdgjxxcxtj_stat.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

bdgjxxcxtj_stat.views.statByJclxMainView = function()
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
			alias:"bdgjxxcxtj_statByJclx_searchbox",
			rootGroupId : "402881704230292b0142316493220006",
			PagedControl:me.dataGrid,
			height:"13%",
			itemNumOfRow:4,
			fields : [ {name : "ssdw",caption : "所属单位",hint:"---请选择---",displayCheckBox:true,editorType : "DropDownEditor",
				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_ssdw_mainSystem"
			},
			{	name : "dydj",caption : "电压等级",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_dydj"
			},
			{	name : "jclx",caption : "监测类型",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_jclx_bd"
			},
			{	name : "gjjb",caption : "告警级别",displayCheckBox: true,hint : "--请选择--",editorType : "DropDownEditor",
				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_gjjb"
			},
			{	name : "bdz",caption : "变电站名称",hint : "--请选择--",editorType : "TextEditor"
			},
			{name : "gjsj",caption : "告警时间",displayCheckBox: true,formatString:"yyyy-MM-dd",editorType : "DateTimeEditor"}
			]
		});

		_dataGridappendButton();
		
		me.addControl(me.searchBox);
    }
    
    /**
     * 给SearchBox添加按钮的方法
     */
    function _dataGridappendButton() {
    	var $searchBox = me.searchBox;
    	
    	$searchBox.$("#btnTd").hide();
    	var $tbody =  $searchBox.$("#btnTr");
    	$tbody.append("<td class='newButtonTd1' colspan=8 style='padding-left:80%; '></td>");
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
    }
    
    function initDataGrid(){
    	var restUrl = "~/rest/bdgjxxcxtj_stat/jclxtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : bdgjxxcxtj_stat.mappath(restUrl),
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
				{ name: "group4", caption: "微水",
					columns:[
								{ name: "CNT021003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT021003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT021003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE021003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group5", caption: "铁芯接地电流",
					columns:[
								{ name: "CNT021004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT021004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT021004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE021004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group6", caption: "顶层油温",
					columns:[
								{ name: "CNT021005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT021005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT021005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE021005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group7", caption: "绝缘监测",
					columns:[
								{ name: "CNT022001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT022001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT022001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE022001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
			
				{ name: "group8", caption: "金属氧化物避雷器",
					columns:[
						{ name: "CNT023001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT023001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT023001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE023001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
			
				{ name: "group9", caption: "断路器局部放电",
					columns:[
						{ name: "CNT024001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT024001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT024001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE024001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group10", caption: "分合闸线圈电流波形",
					columns:[
						{ name: "CNT024002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT024002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT024002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE024002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group11", caption: "负荷电流波形",
					columns:[
						{ name: "CNT024003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT024003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT024003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE024003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group12", caption: "SF6气体压力",
					columns:[
						{ name: "CNT024004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT024004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT024004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE024004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group13", caption: "SF6气体水分",
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