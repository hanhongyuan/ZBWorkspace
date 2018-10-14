$ns("bdzzcx.views");


bdzzcx.views.bdzzcxMainView = function()
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
	    //表单窗口对象
    var _detailWin = null;
    
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
		_initDetailWindow();
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
			rootGroupId : "402881704230292b0142316493220006",
			alias: "bdzzcxSearchBox",
		
			fields : [{
						name : "ssdw",caption : "所属单位",displayCheckBox : true,hint:"---请选择---",editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_ssdw"
					}, {
						name : "dydj",caption : "电压等级",displayCheckBox : true,hint:"---请选择---",editorType : "DropDownEditor",
						url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_dydj"
					}, {
						name : "jclx",caption : "监测类型",displayCheckBox : true,hint:"---请选择---",editorType : "DropDownEditor",
						url : "/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_jclxBd"
					}, {
						name : "sccj",caption : "生产厂家",displayCheckBox : false,hint:"---请选择---",displayCloseButton : false,editorType : "TextEditor"
					}, {
						name : "bdzmc",caption : "变电站名称",displayCheckBox : false,hint:"---请选择---",displayCloseButton : false,editorType : "TextEditor"
					}, {
						name : "tyrq",caption : "投运日期",hint:"---请选择---",formatString : "yyyy-MM-dd",editorType : "DateTimeEditor"
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
					name : "bdzzcx",
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
					baseUrl : bdzzcx.mappath("~/rest/zbbdzzcxtjControl/query"),
					iscID : "-1",
					loadMeta : false,
					primaryKey: "OBJ_ID"
				});
		/* 初始化 DataGrid */
		_dataGrid = new mx.datacontrols.DataGrid({
			alias : "bdzzcxbdzzcxMainViewDataGrid",
			// TODO: 设置显示列。
			columns : [{
						name : "OBJ_ID",
						caption : "OBJ_ID",
						dataAlign : "center",
						editorType : "TextEditor"
					},{
						name : "WSDEPMC",
						caption : "所属单位",
						dataAlign : "center",
						editorType : "TextEditor"
					},{
						name : "XTMC",
						caption : "所属系统",
						dataAlign : "center",
						editorType : "TextEditor"
					},{
						name : "LINKEDSTATIONNAME",
						caption : "所属变电站",
						dataAlign : "center",
						editorType : "TextEditor"
					},{
						name : "LINKEDEQUIPMENTNAME",
						caption : "所属一次设备",
						dataAlign : "center",
						editorType : "TextEditor"
					},{
						name : "DEVICECATEGORY",
						caption : "接入方式",
						dataAlign : "center",
						editorType : "TextEditor"
					},{
						name : "DEVICECATEGORY_DISPLAY",
						caption : "监测类型",
						dataAlign : "center",
						editorType : "TextEditor"
					},{
						name : "LOOKUP",
						caption : "监测信息",
						dataAlign : "center",
						editorType : "TextEditor",
						renderCell : function(p_item, $p_cell) {
							var value = p_item.getValue("LOOKUP")
							if("查看" == value){
								//onclick=\"btnlookup_click()\"
								
								$p_cell.html("<a href=\"javascript: void(0);\" style=\"color:blue;text-decoration:none\" >"+value+"</a>"); 
    						}else{
    							$p_cell.html("<a href=\"javascript: void(0);\" style=\"color:red;text-decoration:none\">"+value+"</a>"); 
    						}
						}
					},{
						name : "DEVICENAME",
						caption : "装置名称",
						dataAlign : "center",
						editorType : "TextEditor"
					},{
						name : "DEVICEMODEL",
						caption : "装置型号",
						dataAlign : "center",
						editorType : "TextEditor"
					},{
						name : "MANUFACTURER",
						caption : "生产厂家",
						editorType : "TextEditor"
					},{
						name : "IFSS",dataAlign : "center",
						caption : "是否实时",
						editorType : "TextEditor",
						renderCell : function(p_item, $p_cell) {
								var value = p_item.getValue("IFSS"); // 获取 GENDER 字段的值。
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
						name : "RUNDATE",
						caption : "投运日期",
						dataAlign : "center",
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
				btnlookup_click = me.controller._btnlookup_click;
				// 隐藏表格的放大镜
				_dataGrid.$("#toggleShowBtn").hide();
			}
	    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = bdzzcx.context.windowManager.create({
			reusable: true,//是否复用
			width:640,
			height:520,
			title:"表单维护"
		});
    }

    /**
     * 获取表单视图窗口对象
     */
    me.getDetailWindow = function(){
    	return _detailWin;
    }
			
    me.getSearchBox = function(){
    	return _seSearchBox;
    }
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
    
	me.endOfClass(arguments)
    return me;
};