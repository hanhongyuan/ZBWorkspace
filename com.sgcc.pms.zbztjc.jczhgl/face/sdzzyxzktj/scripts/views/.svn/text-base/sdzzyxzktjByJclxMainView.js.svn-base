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
								hint : "---请输入---",
								displayCloseButton : false,
								onchanged:_paramChanged,
								editorType : "TextEditor"
							}, {
								name : "xlmc",
								caption : "线路名称",
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
		
		//定义一个按监测类型统计的按钮
    	me.exportBtn = new mxpms.controls.ImageButton({
    		text:"导出",
    		name:"exportBtn",
    		onclick:me.controller._jclxExportBtn_click
    	});
    	
    	$newButtonTd1.append(me.exportBtn.$e);
    	me.exportBtn.$e[0].style.marginBottom = "0px";
    	me.exportBtn.$e[0].style.marginRight = "0px";
		me.exportBtn.$e[0].style.marginLeft = "5px";
		
		// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "0px";
		$searchBox.$("#reset")[0].style.marginLeft = "5px";
		
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
		var restUrl = "~/rest/sdzzyxzkcontrol/jclxtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sdzzyxzktj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1" // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
		});

		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"jczztjByjclxDataGrid",
			columns : [ 
			{
				name : "SSWSMC",
				caption : "所属单位",
				dataAlign : "center",
				editorType : "TextEditor"
			},{
				name : "group12001",
				caption : "杆塔倾斜",
				columns :[
						{ name: "ZZZS012001", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR012001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL012001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL012001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL012001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF012001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "group013001",
				caption : "导线弧垂",
				columns :[
						{ name: "ZZZS013001", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL013001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF013001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF013001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "group013002",
				caption : "导线温度",
				columns :[
						{ name: "ZZZS013002", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013002", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL013002", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL013002", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF013002", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "group013003",
				caption : "微风振动",
				columns :[
						{ name: "ZZZS013003", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013003", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013003", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL013003", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL013003", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF013003", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "group013004",
				caption : "风偏振动",
				columns :[
						{ name: "ZZZS013004", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013004", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013004", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL013004", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL013004", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF013004", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "group013005",
				caption : "覆冰",
				columns :[
						{ name: "ZZZS013005", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013005", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013005", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL013005", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL013005", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF013005", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "group013006",
				caption : "导线舞动",
				columns :[
						{ name: "ZZZS013006", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR013006", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL013006", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL013006", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL013006", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF013006", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "group014001",
				caption : "现场污秽度监测",
				columns :[
						{ name: "ZZZS014001", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR014001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL014001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL014001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL014001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF014001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "group018001",
				caption : "微气象",
				columns :[
						{ name: "ZZZS018001", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR018001", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL018001", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL018001", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL018001", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF018001", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "group018002",
				caption : "图像",
				columns :[
						{ name: "ZZZS018002", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJR018002", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "JRL018002", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL018002", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF018002", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF018002", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
			},{
				name : "groupALLZZZS",
				caption : "所有监测装置",
				columns :[
						{ name: "ALLZZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLSSJR", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
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
			onload:me.controller._initSdJclxDataGrid,
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