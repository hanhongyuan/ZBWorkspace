$ns("dlzztj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");

dlzztj.views.dlzztjBySccjMainView = function()
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
					alias : "dlzztj_dlzztjByJclx_searchbox",
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
								name : "dlmc",
								caption : "电力电缆及通道名称",
								displayCheckBox : false,
								hint : "---请输入---",
								displayCloseButton : false,
								onchanged:_nameChanged,
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
		_searchBox.editors.dlmc.setCss({width:280});
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
		var restUrl = "~/rest/zbdlzzcxtjController/sccjtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : dlzztj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"dlzztjBySccjDataGrid",
			columns: [
			    		{name: "SSWS", caption: "SSWS" ,visible : false},
			    		{ name: "SSWSMC", caption: "生产厂家", dataAlign:"center", editorType: "TextEditor"  },
			    		{name: "group1",caption: "电缆监测",
			    			columns: [
			    			     { name: "ZZZS031001", caption: "电缆局部放电", dataAlign:"center",editorType: "TextEditor" },
			    			     { name: "ZZZS031002", caption: "护层接地电流", dataAlign:"center",editorType: "TextEditor" },
			    			     { name: "ZZZS031003", caption: "分布式光纤测温", dataAlign:"center",editorType: "TextEditor" },
			    			     { name: "ZZZS031004", caption: "电缆油压", dataAlign:"center",editorType: "TextEditor" }
			    		]},{
			        		name: "group2",
			        		caption: "电缆通道监测",
			        		columns: [
			        		{ name: "ZZZS032001", caption: "水位", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032002", caption: "供电", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032003", caption: "通风", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032004", caption: "排水", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032005", caption: "照明", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032006", caption: "沉降", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032007", caption: "气体", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032008", caption: "环境温度", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032009", caption: "电子井盖", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032010", caption: "图像", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032011", caption: "视频", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032012", caption: "门禁", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032013", caption: "防火阀", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032014", caption: "红外对射", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032015", caption: "声光报警", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032016", caption: "火灾报警", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032017", caption: "灭火装置", dataAlign:"center",editorType: "TextEditor" },
			        		{ name: "ZZZS032018", caption: "防火门", dataAlign:"center",editorType: "TextEditor" }
			        		]
			        	},{
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
			width : 1000,
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
		var dlmc = _searchBox.editors.dlmc.value;
		
		if(dlmc!=null&&dlmc!="")
		{
			if(dlmc.length>50|| !pattern1.test(dlmc)){
				_searchBox.editors.dlmc.reset();
				mx.indicate("warn", "电缆/通道名称长度不能超50且不能为特殊字符!");
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