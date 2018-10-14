$ns("sdzzyxzktj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.GroupHeaderGrid");

sdzzyxzktj.views.sdzzyxzktjByJclxMainView = function() {
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
		_initSdJclxDetailWindow();
		me.on("activate", me.controller._onactivate);
	}

	function _initSearchBox() {
		_searchBox = new mxpms.datacontrols.AdvancedSearchBox(
				{
					alias : "sdzzyxzktj_sdzzyxzktjByJclx_searchbox",
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
								hint : "---请选择---",
								displayCloseButton : false,
								editorType : "TextEditor"
							}, {
								name : "xlmc",
								caption : "线路名称",
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
		
		
		/*// 定义一个按监测类型统计的按钮
		me.exportBtn = new mxpms.controls.ImageButton({
			text : "导出",
			name : "exportBtn",
			onclick : me.controller._jclxExportBtn_click,
			border : "1px solid white"
		});

		$newButtonTd1.append(me.exportBtn.$e);*/
	}
	
	 /**
	 * 重置
	 */
	function reset()
	{
		var editor = null;
		for (var i = 0; i < _seSearchBox.editors.length; i++)
        {
			editor = _seSearchBox.editors[i];
			editor.reset(); //调用editor的原生方法重置
			if(editor.editorType && editor.editorType == "DropDownTreeEditor") { //如果是下拉树，选中的项没有被清除，在此清除
				var nodes = editor.dataTree.getCheckedNodes();
				for(var j = 0; j<nodes.length; j++)
				{
					nodes[j].setChecked(false);
				}
				delete editor.defaultValue; //这里是必要的
			}
			editor.setValue(null);
        }
	}

	function _initDataGrid() {
		var restUrl = "~/rest/zbsdzzyxzkControl/jclxtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sdzzyxzktj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"jczztjByjclxDataGrid",
			columns : [{name: "OBJ_ID", caption: "所属单位编码" }, 
			{
				name : "SSWSMC",
				caption : "所属单位",
				dataAlign : "center",
				editorType : "TextEditor"
			}, {
				name : "group12001",
				caption : "杆塔倾斜",
				columns :[
						{ name: "ZZZS012001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR012001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL012001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL012001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL012001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF012001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}, {
				name : "group013001",
				caption : "导线弧垂",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}

			, {
				name : "group013002",
				caption : "导线温度",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}

			, {
				name : "group013003",
				caption : "微风振动",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}

			, {
				name : "group018003",
				caption : "视频",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}

			, {
				name : "group013004",
				caption : "风偏振动",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}

			, {
				name : "group013005",
				caption : "覆冰",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}

			, {
				name : "group014001",
				caption : "现场污秽度监测",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}

			, {
				name : "group018001",
				caption : "微气象",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}

			, {
				name : "group018002",
				caption : "图像",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}, {
				name : "group013006",
				caption : "导线舞动",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ZQL013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			}

			, {
				name : "groupALLZZZS",
				caption : "所有监测装置",
				columns :[
						{ name: "ZZZS013001", caption: "考核装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SJJRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
					]

			} ],
			// 设置查询条件。
			height : "90%",
			entityContainer : gridEntityContainer,
			displayCheckBox : false,
			displayRowNumber : true,
			allowPaging : false,
			displayPrimaryKey : false,
			allowEditing : false,
			//onload:me.controller._initSdJclxDataGrid,
			rowNumberColWidth : 40
			
		});
		me.addControl(_dataGrid);
		_dataGrid.load();
	}
	
	    /**
	 * 初始化表单视图窗口对象
	 */
	function _initSdJclxDetailWindow(){
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