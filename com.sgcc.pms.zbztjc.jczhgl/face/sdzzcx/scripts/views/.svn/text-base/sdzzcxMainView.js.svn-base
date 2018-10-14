$ns("sdzzcx.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");

sdzzcx.views.sdzzcxMainView = function()
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
			alias: "sdzzcxSearchBox",
			itemNumOfRow:4,
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
					}, {
						name : "dydj",
						caption : "电压等级",
						displayCheckBox : true,
						hint:"---请选择---",
						width: "180",
						editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_dydj"
					}, {
						name : "jclx",
						caption : "监测类型",
						displayCheckBox : true,
						hint:"---请选择---",
						width: 180,
						editorType : "DropDownEditor",
						url : "/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_jclx"
					},
					{	name : "yxzt",
						caption : "运行状态",
						displayCheckBox : true,
						hint : "--请选择--",
						editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_yxzt"
					}, {
						name : "sccj",
						caption : "生产厂家",
						displayCheckBox : false,
						hint:"---请输入---",
						width: 180,
						onchanged:_paramChanged,
						displayCloseButton : false,
						editorType : "TextEditor"
					}, {
						name : "xlmc",
						caption : "线路名称",
						displayCheckBox : false,
						hint:"---请输入---",
						width: 180,
						onchanged:_nameChanged,
						displayCloseButton : false,
						editorType : "TextEditor"
					}, {
						name : "tyrq",
						caption : "投运日期",
						hint:"---请选择---",
						width: 180,
						formatString : "yyyy-MM-dd",
						editorType : "DateTimeEditor"
					}]
		});
		// 添加查询区按钮
		_dataGridappendButton();
		_seSearchBox.editors.ssdw.setCss({width:200});
		_seSearchBox.editors.dydj.setCss({width:200});
		_seSearchBox.editors.jclx.setCss({width:200});
		_seSearchBox.editors.sccj.setCss({width:200});
		_seSearchBox.editors.xlmc.setCss({width:200});
		_seSearchBox.editors.tyrq.setCss({width:200});
		_seSearchBox.editors.yxzt.setCss({width:200});
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
					name : "sdzzcx",
					onclick : me.controller._btnCx_onclick
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
					baseUrl : sdzzcx.mappath("~/rest/cmsvlinedevicelogicxt/query"),
					iscID : "-1",
					loadMeta : false,
					primaryKey: "OBJ_ID"
				});
		/* 初始化 DataGrid */
		_dataGrid = new mx.datacontrols.DataGrid({
			alias : "sdzzcxsdzzcxMainViewDataGrid",
			// TODO: 设置显示列。
			columns : [{
						name : "OBJ_ID",
						caption : "OBJ_ID",
						editorType : "TextEditor"
					},{
						name : "WSDEPMC",
						caption : "所属单位",
						dataAlign:"center",
						editorType : "TextEditor"
					},{
						name : "LINKEDLINENAME",
						caption : "所属线路",
						dataAlign:"center",
						editorType : "TextEditor"
					},{
						name : "LINKEDPOLENAME",
						caption : "所属杆塔",
						dataAlign:"center",
						editorType : "TextEditor"
					},{
						name : "DEVICEVOLTAGE",
						caption : "电压等级",
						dataAlign:"center",
						editorType : "TextEditor"
					},{
						name : "DEVICECATEGORY",
						caption : "接入方式",
						dataAlign:"center",
						editorType : "TextEditor"
					},{
						name : "DEVICECATEGORY_DISPLAY",
						caption : "监测类型",
						dataAlign:"center",
						width:200,
						editorType : "TextEditor"
					},{
						name : "LOOKUP",
						caption : "监测信息",
						dataAlign:"center",
						width:80,
						editorType : "TextEditor",
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
						dataAlign:"center",
						width:200,
						editorType : "TextEditor"
					},{
						name : "DEVICEMODEL",
						caption : "装置型号",
						dataAlign:"center",
						editorType : "TextEditor"
					},{
						name : "MANUFACTURER",
						caption : "生产厂家",
						dataAlign:"center",
						editorType : "TextEditor"
					},{
						name : "ISRT",
						caption : "是否实时",
						dataAlign:"center",
						width:80,
						editorType : "TextEditor",
						renderCell : function(p_item, $p_cell) {
								var value = p_item.getValue("SFSS"); // 获取 GENDER 字段的值。
								if("T"==value)
									{
									
									$p_cell.html(value).css({
										
										"color" : "green"
									});
									}
								else 
								{
									$p_cell.html(value).css({
										
										"color" : "red"
									});
									}
								
							}
					},{
						name : "STATUS",
						caption : "运行状态",
						dataAlign : "center",
						renderCell : function(p_item, $p_cell) {
							var value = p_item.getValue("STATUS"); // 获取 GENDER 字段的值。
							switch(value){
							case "00501":
								$p_cell.html("在运");
								break;
							case "00502":
								$p_cell.html("检修/调试");
								break;
							case "00503":
								$p_cell.html("退役");
								break;
							}
						}
					},{
						name : "RUNDATE",
						caption : "投运日期",
						dataAlign:"center",
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
    
    var pattern1 = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]+$");
    function _paramChanged()
	{
		var sccj = _seSearchBox.editors.sccj.value;
		
		if(sccj!=null&&sccj!="")
		{
			if(sccj.length>50|| !pattern1.test(sccj)){
				_seSearchBox.editors.sccj.reset();
				mx.indicate("warn", "生产厂家长度不能超50且不能为特殊字符!");
			}
				
		}	
	}
	
	function _nameChanged()
	{
		var xlmc = _seSearchBox.editors.xlmc.value;
		
		if(xlmc!=null&&xlmc!="")
		{
			if(xlmc.length>50|| !pattern1.test(xlmc)){
				_seSearchBox.editors.xlmc.reset();
				mx.indicate("warn", "线路名称长度不能超50且不能为特殊字符!");
			}
				
		}	
	}
    return me.endOfClass(arguments);
};