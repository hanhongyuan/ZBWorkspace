$ns("bdzztj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");

bdzztj.views.bdzztjBySccjMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
	var _searchBox = null;
	var _dataGrid = null;
    
    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	_initSearchBox();
		_initDataGrid();
		_initBdSccjDetailWindow();
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
								hint : "---请输入---",
								displayCloseButton : false,
								onchanged:_paramChanged,
								editorType : "TextEditor"
							}, {
								name : "bdzmc",
								caption : "变电站名称",
								displayCheckBox : false,
								hint : "---请输入---",
								onchanged:_nameChanged,
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
		_searchBox.editors.ssdw.setCss({width:280});
		_searchBox.editors.dydj.setCss({width:280});
		_searchBox.editors.jclx.setCss({width:280});
		_searchBox.editors.sccj.setCss({width:280});
		_searchBox.editors.bdzmc.setCss({width:280});
		_searchBox.editors.tyrq.setCss({width:280});
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
			onclick : me.controller._sccjQueryBtn_click

		});
		$newButtonTd1.append(me.queryBtn.$e);
		// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "5px";
		$searchBox.$("#reset")[0].style.marginLeft = "5px";
		// 定义一个按生产厂家统计的按钮
		me.exportBtn = new mxpms.controls.ImageButton({
			text : "导出",
			name : "exportBtn",
			onclick : me.controller._sccjExportBtn_click
		});

		$newButtonTd1.append(me.exportBtn.$e);
	}

	function _initDataGrid() {
		var restUrl = "~/rest/bdzztjcontrol/sccjtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : bdzztj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"bdzztjBySccjDataGrid",
			columns: [
			    		{name: "SSWS", caption: "SSWS" ,visible : false},
			    		{ name: "SSWSMC", caption: "生产厂家", dataAlign:"center", editorType: "TextEditor"  },
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
			onload:me.controller._initBdSccjDataGrid,
			rowNumberColWidth : 40
		});
		me.addControl(_dataGrid);
		_dataGrid.load();
	}
  /**
	 * 初始化表单视图窗口对象
	 */
	function _initBdSccjDetailWindow(){
		me.detailWin = mxpms.utils.PortalUtil.getGlobalWindowManage().create({
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
	function _paramChanged()
	{
		var sccj = _searchBox.editors.sccj.value;
		
		if(sccj!=null&&sccj!="")
		{
			if(sccj.length>50|| !pattern1.test(sccj)){
				_searchBox.editors.sccj.reset();
				mx.indicate("warn", "生产厂家长度不能超50且不能为特殊字符!");
			}
				
		}	
	}
	
	function _nameChanged()
	{
		var bdzmc = _searchBox.editors.bdzmc.value;
		
		if(bdzmc!=null&&bdzmc!="")
		{
			if(bdzmc.length>50|| !pattern1.test(bdzmc)){
				_searchBox.editors.bdzmc.reset();
				mx.indicate("warn", "变电站名称长度不能超50且不能为特殊字符!");
			}
				
		}	
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