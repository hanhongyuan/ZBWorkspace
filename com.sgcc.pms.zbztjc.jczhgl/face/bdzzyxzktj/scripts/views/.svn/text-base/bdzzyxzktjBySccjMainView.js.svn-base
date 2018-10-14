$ns("bdzzyxzktj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");

bdzzyxzktj.views.bdzzyxzktjBySccjMainView = function()
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
					alias : "bdzzyxzktj_bdzzyxzktjByJclx_searchbox",
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
								editorType : "TextEditor"
							}, {
								name : "bdzmc",
								caption : "变电站名称",
								displayCheckBox : false,
								hint : "---请输入---",
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
			onclick : me.controller._jclxQueryBtn_click
		});
		$newButtonTd1.append(me.queryBtn.$e);
		// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "0px";
		$searchBox.$("#reset")[0].style.marginLeft = "5px";
		/*// 定义一个按监测类型统计的按钮
		me.exportBtn = new mxpms.controls.ImageButton({
			text : "导出",
			name : "exportBtn",
			onclick : me.controller._jclxExportBtn_click,
			border : "1px solid white"
		});

		$newButtonTd1.append(me.exportBtn.$e);*/
	}

	function _initDataGrid() {
		var restUrl = "~/rest/bdzzyxzkcontrol/sccjtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : bdzzyxzktj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			
			columns: [
			    		{name: "LINKEDPROVICEDEPT", caption: "LINKEDPROVICEDEPT" ,visible : false},
			    		{ name: "SSWSMC", caption: "生产厂家", dataAlign:"center", editorType: "TextEditor"  },
			    		{name: "group1",caption: "变压器/电抗器类",
			    			columns: [
			    			     {name: "group021001", caption: "局部放电",
									columns :[
											{ name: "ZZZS021001", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS021001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL021001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS021001", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] },
			    			     { name: "group021002", caption: "油中溶解气体", columns :[
											{ name: "ZZZS021002", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS021002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL021002", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS021002", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] },
			    			     { name: "group021003", caption: "微水",columns :[
											{ name: "ZZZS021003", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS021003", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL021003", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS021003", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] },
			    			     { name: "group021004", caption: "铁芯接地电流",columns :[
											{ name: "ZZZS021004", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS021004", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL021004", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS021004", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] },
			    			     { name: "group021005", caption: "顶层油温",columns :[
											{ name: "ZZZS021005", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS021005", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL021005", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS021005", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] }
			    		]},{
			        		name: "group2",
			        		caption: "断路器/GIS类",
			        		columns: [
			        		{ name: "group022001", caption: "局部放电",columns :[
											{ name: "ZZZS022001", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS022001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL022001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS022001", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] },
			        		{ name: "group023001", caption: "分合闸线圈电流波形",columns :[
											{ name: "ZZZS023001", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS023001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL023001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS023001", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] },
			        		{ name: "group024001", caption: "负荷电流波形",columns :[
											{ name: "ZZZS024001", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS024001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL024001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS024001", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] },
			        		{ name: "group024002", caption: "SF6气体压力",columns :[
											{ name: "ZZZS024002", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS024002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL024002", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS024002", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] },
			        		{ name: "group024003", caption: "SF6气体水分",columns :[
											{ name: "ZZZS024003", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS024003", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL024003", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS024003", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] },
			        		{ name: "group024004", caption: "储能电机工作状态",columns :[
											{ name: "ZZZS024004", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS024004", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL024004", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS024004", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] }
			        		]
			        	},
			        	{
			        		name: "group3",
			        		caption: "电容型设备类",
			        		columns: [
			        		{ name: "group024005", caption: "绝缘监测",columns :[
											{ name: "ZZZS024005", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS024005", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL024005", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS024005", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] }
			        		]
			        	},
			        	{
			        		name: "group4",
			        		caption: "金属氧化物避雷器类",
			        		columns: [
			        		{ name: "group024006", caption: "绝缘监测",columns :[
											{ name: "ZZZS024006", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS024006", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL024006", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS024006", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] }
			        		]
			        	},
			        	{
			        		name: "group5",
			        		caption: "视频类",
			        		columns: [
			        		{ name: "group026001", caption: "变电视频监测",columns :[
											{ name: "ZZZS026001", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRZZS026001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "SSJRL026001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "WSSJRS026001", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] }
			        		]
			        	},
			        	{
			        		name: "group6",
			        		caption: "合计",
			        		columns: [
			        		{ name: "groupALL", caption: "所有监测装置",columns :[
											{ name: "ALLZZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "ALLSSJRZZS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
											{ name: "ALLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
											//{ name: "ALLWSSJRS", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						] }
			        		]
			        	}],
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