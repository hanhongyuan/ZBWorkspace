$ns("bdzztj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.GroupHeaderGrid");

bdzztj.views.bdzztjByJclxMainView = function() {
	var me = $extend(mx.views.View);
	var base = {};

	var _searchBox = null;
	var _dataGrid = null;

	base.init = me.init;
	me.init = function() {
		base.init();
		_initControls();
	};

	function _initControls() {
		_initSearchBox();
		_initDataGrid();
		_initBdJclxDetailWindow();
		me.on("activate", me.controller._onactivate);
	}

	function _initSearchBox() {
		_searchBox = new mxpms.datacontrols.AdvancedSearchBox(
				{
					alias : "bdzztj_bdzztjByJclx_searchbox",
					rootGroupId : "402881704230292b0142316493220006",
					PagedControl : _dataGrid,
					fields : [
							{
								name : "ssdw",
								caption : "所属单位",
								hint : "---请选择---",
								displayCheckBox : true,
								editorType : "DropDownEditor",
								url : "/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_ssdw"
							},
							{
								name : "dydj",
								caption : "电压等级",
								hint : "--请选择--",
								displayCheckBox : true,
								editorType : "DropDownEditor",
								url : "/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_dydj"
							},
							{
								name : "jclx",
								caption : "监测类型",
								hint : "--请选择--",
								displayCheckBox : true,
								editorType : "DropDownEditor",
								url : "/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_jclxBd"
							}, {
								name : "sccj",
								caption : "生产厂家",
								displayCheckBox : false,
								hint : "---请选择---",
								displayCloseButton : false,
								editorType : "TextEditor"
							}, {
								name : "bdzmc",
								caption : "变电站名称",
								displayCheckBox : false,
								hint : "---请选择---",
								displayCloseButton : false,
								editorType : "TextEditor"
							}, {
								name : "tyrq",
								caption : "投运日期",
								hint : "---请选择---",
								formatString : "yyyy-MM-dd",
								editorType : "DateTimeEditor"
							} ]
				});

		_dataGridappendButton();

		me.addControl(_searchBox);
	}

	/**
	 * 给SearchBox添加按钮的方法
	 */
	function _dataGridappendButton() {
		var $searchBox = me.getSearchBox();

		$searchBox.$("#btnTd").hide();
		var $tbody = $searchBox.$("#btnTr");
		$tbody
				.append("<td class='newButtonTd1' colspan=9 style='text-align: right; padding-right: 5px;'></td>");
		var $newButtonTd1 = $searchBox.$(".newButtonTd1");
		// 定义一个按电压等级统计的按钮
		me.queryBtn = new mxpms.controls.ImageButton({
			text : "统计",
			name : "queryBtn",
			onclick : me.controller._jclxQueryBtn_click,
			border : "1px solid white"

		});
		$newButtonTd1.append(me.queryBtn.$e);
		// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "0px";
		$searchBox.$("#reset")[0].style.marginLeft = "5px";
		// 定义一个按监测类型统计的导出按钮
		/*me.exportBtn = new mxpms.controls.ImageButton({
			text : "导出",
			name : "exportBtn",
			onclick : me.controller._jclxExportBtn_click,
			border : "1px solid white"
		});
		$newButtonTd1.append(me.exportBtn.$e);*/
	}

	function _initDataGrid() {
		var restUrl = "~/rest/zbbdzzcxtjControl/jclxtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : bdzztj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "SSWS"
		});

		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"bdzztjByJclxDataGrid",
			columns: [
			    		{name: "SSWS", caption: "单位编码" ,visible : false},
			    		{ name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
			    		{name: "group1",caption: "变压器/电抗器类",
			    			columns: [
			    			     { name: "ZZZS021001", caption: "局部放电", dataAlign:"center",editorType: "TextEditor" },
			    			     { name: "ZZZS021002", caption: "油中溶解气体", dataAlign:"center",editorType: "TextEditor" },
			    			     { name: "ZZZS021003", caption: "微水", dataAlign:"center",editorType: "TextEditor" },
			    			     { name: "ZZZS021004", caption: "铁芯接地电流", dataAlign:"center",editorType: "TextEditor" },
			    			     { name: "ZZZS021005", caption: "顶层油温", dataAlign:"center",editorType: "TextEditor" }
			    		]},{
			        		name: "group2",
			        		caption: "断路器/GIS类",
			        		columns: [
			        		{ name: "ZZZS024001", caption: "局部放电", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS024002", caption: "分合闸线圈电流波形", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS024003", caption: "负荷电流波形", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS024004", caption: "SF6气体压力", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS024005", caption: "SF6气体水分", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS024006", caption: "储能电机工作状态", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},
			        	{
			        		name: "group3",
			        		caption: "电容型设备类",
			        		columns: [
			        		{ name: "ZZZS022001", caption: "绝缘监测", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},
			        	{
			        		name: "group4",
			        		caption: "金属氧化物避雷器类",
			        		columns: [
			        		{ name: "ZZZS023001", caption: "绝缘监测", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},
			        	{
			        		name: "group5",
			        		caption: "视频类",
			        		columns: [
			        		{ name: "ZZZS026001", caption: "变电视频监测", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},
			        	{
			        		name: "group6",
			        		caption: "合计",
			        		columns: [
			        		{ name: "ALLZZZS", caption: "所有监测装置", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	}],
			// 设置查询条件。
			height : "90%",
			entityContainer : gridEntityContainer,
			displayCheckBox : false,
			displayRowNumber : true,
			allowPaging : false,
			displayPrimaryKey : false,
			displayColSplitLine : true,
			allowEditing : false,
			onload : me.controller._initBdJclxDataGrid,
			rowNumberColWidth : 40
			
		});
		me.addControl(_dataGrid);
		_dataGrid.load();
	}
	 /**
	 * 初始化表单视图窗口对象
	 */
	function _initBdJclxDetailWindow() {
		me.detailWin = mxpms.utils.PortalUtil.getGlobalWindowManage().create({
			title : "详细列表",
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
	me.getSearchBox = function() {
		return _searchBox;
	}

	/**
	 * 获取DataGrid
	 */
	me.getDataGrid = function() {
		return _dataGrid;
	}

	return me.endOfClass(arguments);
};