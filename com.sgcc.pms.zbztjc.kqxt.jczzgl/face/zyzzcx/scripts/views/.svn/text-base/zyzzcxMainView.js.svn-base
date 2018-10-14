$ns("zyzzcx.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");

zyzzcx.views.zyzzcxMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    


	/**
	 * 初始化日期处理对象
	 */
	var dateutil = new mxpms.utils.DateUtil();
	var _seSearchBox =null;
	/**
	 * 查询区高度,默认为120px
	 */
	var searchBoxheight = "120";
	//数据区
	var _dataGrid= null;
    
    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	// 垂直方向的分割控件
		 _hSplit = new mx.containers.HSplit({
			rows : "85px, auto",
			borderThick : 0
		});
		
		// 初始化查询区
		_initSearchBox();
		
		// 初始化工具栏
		//_initToolBar();
		// 初始化表格
		_initDataGrid();
		// 初始化公共弹窗
		//_initDetailWindow();
		_hSplit.addControl(me._searchBox,1);
		_hSplit.addControl(_dataGrid,1);
		me.addControl(_hSplit);
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
			alias: "zyzzcxSearchBox",
			// 条件选择窗口的设备分组id，跟配置页面的导航树中分组的id一致
			rootGroupId : "2c902a8249bb3f6a0149bbaa7bfb0011",
			itemNumOfRow:4,
			fields : [{
						name : "ssxt",
						caption : "所属系统",
						displayCheckBox : true,
						hint:"---请选择---",
						editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.kqxt.jczzgl/rest/zbjczzglUtil/dropDownEditor/dicts_ssxt"
					}, {
						name : "dydj",
						caption : "电压等级",
						displayCheckBox : true,
						hint:"---请选择---",
						width: "180",
						editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.kqxt.jczzgl/rest/zbjczzglUtil/dropDownEditor/dicts_dydj"
					}, {
						name : "jclx",
						caption : "监测类型",
						displayCheckBox : true,
						hint:"---请选择---",
						width: 180,
						editorType : "DropDownEditor",
						url : "/com.sgcc.pms.zbztjc.kqxt.jczzgl/rest/zbjczzglUtil/dropDownEditor/dicts_zbjclx"
					}, {
						name : "tyrq",
						caption : "投运日期",
						hint:"---请选择---",
						width: 180,
						formatString : "yyyy-MM-dd",
						editorType : "DateTimeEditor"
					}, {
						name : "ssdw",
						caption : "所属单位",
						displayCheckBox : true,
						hint:"---请选择---",
						width: "180px",
						editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.kqxt.jczzgl/rest/zbjczzglUtil/dropDownEditor/dicts_ssdw"
					},{
						name : "xlmc",
						caption : "线路名称",
						displayCheckBox : false,
						hint:"---请选择---",
						width: 180,
						displayCloseButton : false,
						editorType : "TextEditor"
					},{
						name : "bdzmc",
						caption : "变电站名称",
						displayCheckBox : false,
						hint:"---请选择---",
						width: 180,
						displayCloseButton : false,
						editorType : "TextEditor"
					}]
		});
		// 添加查询区按钮
		_dataGridappendButton();
		//查询区隐藏处理设置
		var _img = _seSearchBox.$("img");
		_img.on("click", function() {
					if (_seSearchBox._isHidden) {
						_hSplit.$panel2.css("top", "28px");
					} else {
						_hSplit.$panel2.css("top", "85px");
					}
				});
		_hSplit.addControl(_seSearchBox, 0);
	}
	
	 /**
	 * 添加统计按钮
	 */
	function _dataGridappendButton() {
		var $searchBox = me.getSearchBox();
		// 隐藏按钮栏
		$searchBox.$("#btnTr").hide();
		var $tbody = $searchBox.$("tbody");
		$tbody.append("<tr><td class = 'newButtonTd1' colspan=9 style='text-align: right; padding-right: 5px;'></td></tr>");
		var $newButtonTd1 = $searchBox.$(".newButtonTd1");
		
		//$tbody
		//		.append("<tr><td class = 'newButtonTd2' colspan=9 style='text-align: right; padding-right: 5px;height:28px'></td></tr>");
		//var $newButtonTd2 = $searchBox.$(".newButtonTd2");
		me.CxBtn = new mxpms.controls.ImageButton({
			text : "查询",
			onclick : me.controller._btnCx_onclick,
			name : "zyzzcx",
			width : 65
		});
		$newButtonTd1.append($("<div class=space>"));
		$newButtonTd1.append(me.CxBtn.$e);
		// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "0px";
		
		
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
	 * 初始化表格
	 */
	function _initDataGrid() {
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
					baseUrl : zyzzcx.mappath("~/rest/zbzyzzcxtjControl/query"),
					iscID : "-1",
					loadMeta : false,
					primaryKey: "OBJ_ID"
				});
		/* 初始化 DataGrid */
		_dataGrid = new mx.datacontrols.DataGrid({
			alias : "zyzzcxzyzzcxMainViewDataGrid",
			// TODO: 设置显示列。
			columns : [{
						name : "OBJ_ID",
						caption : "OBJ_ID",
						editorType : "TextEditor"
					},{
						name : "WSDEPMC",
						caption : "所属单位",
						editorType : "TextEditor"
					},{
						name : "XTMC",
						caption : "所属系统",
						editorType : "TextEditor"
					},{
						name : "LINKEDLINENAME",
						caption : "线路名称/变电站名称",
						editorType : "TextEditor"
					},{
						name : "LINKEDPOLENAME",
						caption : "杆塔名称/设备名称",
						editorType : "TextEditor"
					},{
						name : "DEVICEVOLTAGE",
						caption : "电压等级",
						editorType : "TextEditor"
					},{
						name : "DEVICECATEGORY",
						caption : "接入方式",
						editorType : "TextEditor"
					},{
						name : "DEVICECATEGORY_DISPLAY",
						caption : "监测类型",
						editorType : "TextEditor"
					},{
						name : "LOOKUP",
						caption : "监测信息",
						editorType : "TextEditor",
						renderCell : function(p_item, $p_cell) {
							var value = p_item.getValue("LOOKUP")
							if("查看" == value){
								$p_cell.html("<a href=\"javascript: void(0);\" style=\"color:blue;text-decoration:none\">"+value+"</a>"); 
    						}else{
    							$p_cell.html("<a href=\"javascript: void(0);\" style=\"color:red;text-decoration:none\">"+value+"</a>"); 
    						}
						}
					},{
						name : "DEVICENAME",
						caption : "装置名称",
						editorType : "TextEditor"
					},{
						name : "DEVICEMODEL",
						caption : "装置型号",
						editorType : "TextEditor"
					},{
						name : "MANUFACTURER",
						caption : "生产厂家",
						editorType : "TextEditor"
					},{
						name : "RUNDATE",
						caption : "投运日期",
						editorType : "TextEditor"
					}
					
					],
					displayCheckBox: false,
					displayPrimaryKey:false,//列表是否显示主键
					pageSize : 20,
					entityContainer : gridEntityContainer,
					onitemdoubleclick : me.controller._dataGrid_onitemdoubleclick
				});
				_dataGrid.load();
				// 隐藏表格的放大镜
				_dataGrid.$("#toggleShowBtn").hide();
			}

    me.getSearchBox = function(){
    	return _seSearchBox;
    }
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
    return me.endOfClass(arguments);
};