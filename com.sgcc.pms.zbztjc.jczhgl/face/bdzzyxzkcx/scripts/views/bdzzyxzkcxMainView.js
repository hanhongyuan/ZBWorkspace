$ns("bdzzyxzkcx.views");

bdzzyxzkcx.views.bdzzyxzkcxMainView = function()
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
			alias: "bdzzyxzkcxSearchBox",
			// 条件选择窗口的设备分组id，跟配置页面的导航树中分组的id一致
			rootGroupId : "2c902a8249bb3f6a0149bbaa7bfb0011",
			fields : [{
						name : "ssdw",
						caption : "所属单位",
						displayCheckBox : true,
						hint:"---请选择---",
						editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_ssdw"
					}, {
						name : "dydj",
						caption : "电压等级",
						displayCheckBox : true,
						hint:"---请选择---",
						editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_dydj"
					}, {
						name : "jclx",
						caption : "监测类型",
						displayCheckBox : true,
						hint:"---请选择---",
						editorType : "DropDownEditor",
						url : "/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_jclxBd"
					}, {
						name : "sccj",
						caption : "生产厂家",
						displayCheckBox : false,
						hint:"---请输入---",
						displayCloseButton : false,
						editorType : "TextEditor"
					}, {
						name : "bdzmc",
						caption : "变电站名称",
						displayCheckBox : false,
						hint:"---请输入---",
						displayCloseButton : false,
						editorType : "TextEditor"
					}, {
						name : "tyrq",
						caption : "投运日期",
						hint:"---请选择---",
						formatString : "yyyy-MM-dd",
						editorType : "DateTimeEditor"
					}]
		});
		// 添加查询区按钮
		_dataGridappendButton();
		_seSearchBox.editors.ssdw.setCss({width:280});
		_seSearchBox.editors.dydj.setCss({width:280});
		_seSearchBox.editors.jclx.setCss({width:280});
		_seSearchBox.editors.sccj.setCss({width:280});
		_seSearchBox.editors.bdzmc.setCss({width:280});
		_seSearchBox.editors.tyrq.setCss({width:280});
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
			name : "bdzzyxzkcx"
		});
		$newButtonTd1.append($("<div class=space>"));
		$newButtonTd1.append(me.CxBtn.$e);
		// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "0px";
		$searchBox.$("#reset")[0].style.marginLeft = "5px";
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
					baseUrl : bdzzyxzkcx.mappath("~/rest/bdzzyxzkcontrol/query"),
					iscID : "-1",
					loadMeta : false,
					primaryKey: "OBJ_ID"
				});
		/* 初始化 DataGrid */
		_dataGrid = new mx.datacontrols.DataGrid({
			alias : "bdzzyxzkcxMainViewDataGrid",
			// TODO: 设置显示列。
			columns : [{
						name : "OBJ_ID",
						caption : "OBJ_ID",
						dataAlign : "center"
						
					},{
						name : "WSDEPMC",
						caption : "所属单位",
						dataAlign : "center"
					},{
						name : "LINKEDSTATIONNAME",
						caption : "所属变电站",
						width:'180px',
						dataAlign : "center"
					}, {
						name : "LINKEDEQUIPMENTNAME",
						caption : "所属一次设备",
						dataAlign : "center"
					},{
						name : "DEVICECATEGORY",
						caption : "接入方式",
						dataAlign : "center"
					},{
						name : "DEVICECATEGORY_DISPLAY",
						caption : "监测类型",
						dataAlign : "center"
					},{
						name : "JCXX",
						caption : "监测信息",
						width:'80px',
						dataAlign : "center",
						visible:false,
						renderCell : function(p_item, $p_cell) {
							var value = "查看"; // 获取 GENDER 字段的值。 // 获取 GENDER 字段的值。
							var deptws =  p_item.getValue("LINKEDDEPWS")
							var devicecode = p_item.getValue("DEVICECODE");
							var monitor = p_item.getValue("MONITORINGTYPES");
							$p_cell.html(value).css({
							"text-decoration" : "underline",
							"color" : "blue"
							}).mouseover(function() {
								$p_cell.css("cursor", "pointer");
							}).on("click", function() {
								$p_cell.css("color", "grey");
								me.controller._btnTest(devicecode,monitor,deptws);
							});
						}
					},{
						name : "DEVICENAME",
						caption : "装置名称",
						width:'250px',
						dataAlign : "center"
					},{
						name : "DEVICEMODEL",
						caption : "装置型号",
						dataAlign : "center"
					},{
						name : "MANUFACTURER",
						caption : "生产厂家",
						dataAlign : "center"
					},{
						name : "RUNDATE",
						caption : "投运日期",
						width:'180px',
						dataAlign : "center"
					}],
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