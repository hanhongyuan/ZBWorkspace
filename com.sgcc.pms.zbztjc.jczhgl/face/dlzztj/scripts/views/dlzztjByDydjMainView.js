$ns("dlzztj.views");

$import("mx.datacontrols.GroupHeaderGrid");

dlzztj.views.dlzztjByDydjMainView = function()
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
    	_initBdDydjDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initSearchBox(){
    	_searchBox = new mxpms.datacontrols.AdvancedSearchBox({
			alias:"dlzztjBdlydj_searchbox",
			rootGroupId : "402881704230292b0142316493220006",
			PagedControl:_dataGrid,
			//MONITORINGTYPES(jclx)MANUFACTURER(生产厂家)LINKEDSTATIONNAME(变电站名称)
			fields : [ {name : "ssdw",caption : "所属单位",hint:"---请选择---",displayCheckBox:true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_ssdw"
			},
			{	name : "dydj",caption : "电压等级",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_dydj"
			},//
			{	name : "jclx",caption : "监测类型",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_jclxDl"
			}, {
				name : "sccj",caption : "生产厂家",displayCheckBox : false,onchanged:_paramChanged,hint:"---请输入---",displayCloseButton : false,editorType : "TextEditor"
			}, {
				name : "dlmc",caption : "电力电缆及通道名称",displayCheckBox : false,onchanged:_nameChanged,hint:"---请输入---",displayCloseButton : false,editorType : "TextEditor"
			}, {
				name : "tyrq",caption : "投运日期",hint:"---请选择---",formatString : "yyyy-MM-dd",editorType : "DateTimeEditor"
			}]
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
    	var $tbody =  $searchBox.$("#btnTr");
    	$tbody.append("<td class='newButtonTd1' colspan=9 style='text-align: right; padding-right: 5px;'></td>");
    	var $newButtonTd1 = $searchBox.$(".newButtonTd1");
    	//定义一个按电压等级统计的按钮
    	me.queryBtn = new mxpms.controls.ImageButton({
    		text:"统计",
    		name:"queryBtn",
    		onclick:me.controller._dydjQueryBtn_click
    		
    	});
    	$newButtonTd1.append($("<div class=space>"));
    	$newButtonTd1.append(me.queryBtn.$e);
    	// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "5px";
		$searchBox.$("#reset")[0].style.marginLeft = "5px";
		
    	
    	//定义一个按监测类型统计的按钮
    	me.exportBtn = new mxpms.controls.ImageButton({
    		text:"导出",
    		name:"exportBtn",
    		onclick:me.controller._dydjExportBtn_click
    	});
    	$newButtonTd1.append(me.exportBtn.$e);
    }
    
    
    function _initDataGrid(){
    	var restUrl = "~/rest/zbdlzzcxtjController/dydjtj";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : dlzztj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});
		
		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"dlzztjByDydjDataGrid",
			columns: [
				{name: "OBJ_ID", caption: "所属单位编码" },
				{name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
				{name: "group1",caption: "1000kV",
					columns :[
						{ name: "ZZZS37", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS37", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group2",caption: "750kV",
					columns :[
						{ name: "ZZZS36", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS36", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group3",caption: "500kV",
					columns :[
						{ name: "ZZZS35", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS35", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group4",caption: "330kV",
					columns :[
						{ name: "ZZZS34", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS34", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group5",caption: "220kV",
					columns :[
						{ name: "ZZZS33", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS33", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group6",caption: "110kV",
					columns :[
						{ name: "ZZZS32", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS32", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group7",caption: "66kV",
					columns :[
						{ name: "ZZZS30", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS30", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group8",caption: "35kV",
					columns :[
						{ name: "ZZZS25", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS25", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group9",caption: "±1000kV",
					columns :[
						{ name: "ZZZS86", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS86", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group10",caption: "±800kV",
					columns :[
						{ name: "ZZZS85", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS85", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]
				},
				{name: "group11",caption: "±660kV",
					columns :[
						{ name: "ZZZS84", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS84", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group12",caption: "±500kV",
					columns :[
						{ name: "ZZZS83", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS83", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group13",caption: "±400kV",
					columns :[
						{ name: "ZZZS82", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS82", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group14",caption: "±125kV",
					columns :[
						{ name: "ZZZS81", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS81", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group15",caption: "±120kV",
					columns :[
						{ name: "ZZZS80", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "FGBDZS80", caption: "覆盖线路及通道数", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLZZZS", caption: "装置总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLFGBDZS", caption: "覆盖线路及通道总数", dataAlign:"center",editorType: "TextEditor" }
					]	
				}
			],
			// 设置查询条件。
			height : "90%",
			entityContainer : gridEntityContainer,
			displayCheckBox : false,
			displayRowNumber : true,
			allowPaging : false,
			displayPrimaryKey : false,
			allowEditing : false,
			onload:me.controller._initBdDydjDataGrid,
			rowNumberColWidth : 40
		});
		me.addControl(_dataGrid);
		_dataGrid.load();
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
     /**
	 * 初始化表单视图窗口对象
	 */
	function _initBdDydjDetailWindow() {
		me.detailWin = mxpms.utils.PortalUtil.getGlobalWindowManage().create({
			title : "详细列表",
			width : 1000,
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
    	return _searchBox;
    }
    /**
     * 获取DataGrid
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
    return me.endOfClass(arguments);
};