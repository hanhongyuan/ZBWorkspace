$ns("sdkhzb.views");

$import("mx.datacontrols.GroupHeaderGrid");

sdkhzb.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.objID = null;
    
    me.form = null;
    
    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	// 初始化查询区
		_initSearchBox();
		// 初始化表格
		_initDataGrid();
		_initSdDetailWindow();
		_initDataDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    /**
	 * 初始化查询区
	 */
	function _initSearchBox() {
		_seSearchBox = new mxpms.datacontrols.AdvancedSearchBox({
			reset:reset,
			initHidden : false,
			//pagedControl: _treeListView,
			// 控件的唯一标识
			alias: "sdzzyxzkcxSearchBox",
			// 条件选择窗口的设备分组id，跟配置页面的导航树中分组的id一致
			rootGroupId : "2c902a8249bb3f6a0149bbaa7bfb0011",
			fields : [{
						name : "ssdw",
						caption : "所属单位",
						displayCheckBox : true,
						hint:"---请选择---",
						width: "180px",
						editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_ssdw"
					},{
						name : "tyrq",
						caption : "指标月份",
						hint:"---请选择---",
						width: 180,
						formatString : "yyyy-MM",
						editorType : "DateTimeEditor"
					}],
					height : "10%"
		});
		//_seSearchBox.editors[5].beginEditor.hide();
		_seSearchBox.editors[1].$e["0"].childNodes[1].hidden = true;
		_seSearchBox.editors[1].endEditor.hide();
		
		_seSearchBox.editors.ssdw.setCss({width:280});
		_seSearchBox.editors.tyrq.setCss({width:200});
		// 添加查询区按钮
		_dataGridappendButton();
		
		me.addControl(_seSearchBox);
	}
	
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
    	$newButtonTd1.append(me.queryBtn.$e);
    	// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "5px";
		$searchBox.$("#reset")[0].style.marginLeft = "5px";
    	
    	//定义一个按监测类型统计的按钮
//    	me.exportBtn = new mxpms.controls.ImageButton({
//    		text:"导出",
//    		name:"exportBtn",
//    		onclick:me.controller._dydjExportBtn_click
//    	});
//    	
//    	$newButtonTd1.append(me.exportBtn.$e);
    }
	
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
		var restUrl = "~/rest/sdkhzbControl/query";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sdkhzb.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"jczztjByjclxDataGrid",
			columns: [
						{name: "OBJ_ID", caption: "所属单位编码" },
						{name: "WSDEPMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
						{name: "group1",caption: "气象环境监测类",
							columns :[
								{ name: "WQXJC", caption: "微气象监测", dataAlign:"center",editorType: "TextEditor" },
								{ name: "TXJC", caption: "图像监测", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group2",caption: "导线监测类",
							columns :[
								{ name: "DXHCJC", caption: "导线弧垂监测", dataAlign:"center",editorType: "TextEditor" },
								{ name: "DXWDJC", caption: "导线温度监测", dataAlign:"center",editorType: "TextEditor" },
								{ name: "WFZDJC", caption: "微风振动监测", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FPJC", caption: "风偏监测", dataAlign:"center",editorType: "TextEditor" },
								{ name: "DXWD", caption: "导线舞动监测", dataAlign:"center",editorType: "TextEditor" },
								{ name: "FBJC", caption: "覆冰监测", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group3",caption: "杆塔倾斜类",
							columns :[
								{ name: "GTQXJC", caption: "杆塔倾斜监测", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group4",caption: "绝缘子监测类",
							columns :[
								{ name: "XCWHDJC", caption: "现场污秽度监测", dataAlign:"center",editorType: "TextEditor" }
							]	
						},
						{name: "group5",caption: "合计",
							columns :[
								{ name: "HJ", caption: "所有监测装置", dataAlign:"center",editorType: "TextEditor" }
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
			onload:me.controller._initSdkhzbDataGrid,
			rowNumberColWidth : 40
			
		});
		me.addControl(_dataGrid);
		_dataGrid.load();
	}
	
    /**
	 * 初始化表单视图窗口对象
	 */
	function _initSdDetailWindow() {
		me.detailWin = mxpms.utils.PortalUtil.getGlobalWindowManage().create({
			title : "详细列表",
			movable : true,
			reusable : true,
			resizable : false,
			top : "center",
			left : "center"
		});
	}
	
	
	function _initDataDetailWindow() {
		me._detailWin = mxpms.utils.PortalUtil.getGlobalWindowManage().create({
			title : "详细列表",
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
		return _seSearchBox;
	}

	/**
	 * 获取DataGrid
	 */
	me.getDataGrid = function() {
		return _dataGrid;
	}	
    
    return me.endOfClass(arguments);
};