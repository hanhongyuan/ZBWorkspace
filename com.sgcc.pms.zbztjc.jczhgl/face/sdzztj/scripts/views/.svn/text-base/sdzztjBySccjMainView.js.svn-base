$ns("sdzztj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");

sdzztj.views.sdzztjBySccjMainView = function()
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
		_initSdSccjDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    function _initSearchBox() {
		_searchBox = new mxpms.datacontrols.AdvancedSearchBox(
				{
					alias : "sdzztj_sdzztjByJclx_searchbox",
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
								url : "/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_jclx"
							}, {
								name : "sccj",
								caption : "生产厂家",
								displayCheckBox : false,
								hint : "---请输入---",
								onchanged:_paramChanged,
								displayCloseButton : false,
								editorType : "TextEditor"
							}, {
								name : "xlmc",
								caption : "线路名称",
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
		_searchBox.editors.xlmc.setCss({width:280});
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
		$searchBox.$("#reset")[0].style.marginRight = "5px";
		$searchBox.$("#reset")[0].style.marginLeft = "5px";
		// 定义一个按监测类型统计的按钮
		me.exportBtn = new mxpms.controls.ImageButton({
			text : "导出",
			name : "exportBtn",
			onclick : me.controller._sccjExportBtn_click
		});

		$newButtonTd1.append(me.exportBtn.$e);
	}

	function _initDataGrid() {
		var restUrl = "~/rest/sdzztjcontrol/sccjtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sdzztj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		_dataGrid = new mx.datacontrols.DataGrid({
			columns : [ {
				name : "SSWSMC",
				caption : "生产厂家",
				dataAlign : "center",
				editorType : "TextEditor"
			}, {
				name : "ZS012001",
				caption : "杆塔倾斜",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013001",
				caption : "导线弧垂",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013002",
				caption : "导线温度",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013003",
				caption : "微风振动",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS018003",
				caption : "视频",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013004",
				caption : "风偏振动",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013005",
				caption : "覆冰",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS014001",
				caption : "现场污秽度监测",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS018001",
				caption : "微气象",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS018002",
				caption : "图像",
				dataAlign : "center",
				editorType : "TextEditor"
			}, {
				name : "ZS013006",
				caption : "导线舞动",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ALLZZZS",
				caption : "所有监测装置",
				dataAlign : "center",
				editorType : "TextEditor"

			} ],
			// 设置查询条件。
			height : "90%",
			entityContainer : gridEntityContainer,
			displayCheckBox : false,
			displayRowNumber : true,
			allowPaging : false,
			displayPrimaryKey : false,
			displayColSplitLine : true,
			allowEditing : false,
			onload:me.controller._initSdSccjDataGrid,
			rowNumberColWidth : 40
			
		});
		me.addControl(_dataGrid);
		_dataGrid.load();
	}
	    /**
	 * 初始化表单视图窗口对象
	 */
	function _initSdSccjDetailWindow(){
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
		var xlmc = _searchBox.editors.xlmc.value;
		
		if(xlmc!=null&&xlmc!="")
		{
			if(xlmc.length>50|| !pattern1.test(xlmc)){
				_searchBox.editors.xlmc.reset();
				mx.indicate("warn", "线路名称长度不能超50且不能为特殊字符!");
			}
				
		}	
	}
	/**
	 * 获取DataGrid
	 */
	me.getDataGrid = function() {
		return _dataGrid;
	}
    return me.endOfClass(arguments);
};