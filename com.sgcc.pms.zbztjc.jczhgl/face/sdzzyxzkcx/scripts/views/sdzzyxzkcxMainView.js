$ns("sdzzyxzkcx.views");

sdzzyxzkcx.views.sdzzyxzkcxMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var mes = $extend(mx.editors.Editor);

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
					}, {
						name : "sccj",
						caption : "生产厂家",
						displayCheckBox : false,
						hint:"---请输入---",
						width: 180,
						displayCloseButton : false,
						onchanged:_paramChanged,
						editorType : "TextEditor"
					}, {
						name : "xlmc",
						caption : "线路名称",
						displayCheckBox : false,
						hint:"---请输入---",
						width: 180,
						displayCloseButton : false,
						onchanged:_nameChanged,
						editorType : "TextEditor"
					}, {
						name : "tyrq",
						caption : "指标年月",
						hint:"---请选择---",
						width: 180,
						formatString : "yyyy-MM",
						editorType : "DateTimeEditor"
					}]
		});
		//_seSearchBox.editors[5].beginEditor.hide();
		_seSearchBox.editors[5].$e["0"].childNodes[1].hidden = true;
		_seSearchBox.editors[5].endEditor.hide();
		
		_seSearchBox.editors.ssdw.setCss({width:280});
		_seSearchBox.editors.dydj.setCss({width:280});
		_seSearchBox.editors.jclx.setCss({width:280});
		_seSearchBox.editors.sccj.setCss({width:280});
		_seSearchBox.editors.xlmc.setCss({width:280});
		_seSearchBox.editors.tyrq.setCss({width:180});
		
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
					name : "sdzzyxzkcx",
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
					baseUrl : sdzzyxzkcx.mappath("~/rest/sdzzyxzkcontrol/query"),
					iscID : "-1",
					loadMeta : false,
					primaryKey: "OBJ_ID"
				});
		/* 初始化 DataGrid */
		_dataGrid = new mx.datacontrols.DataGrid({
			alias : "sdzzyxzkcxsdzzcxMainViewDataGrid",
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
						name : "LINKEDLINENAME",
						caption : "所属线路",
						editorType : "TextEditor"
					},{
						name : "LINKEDPOLENAME",
						caption : "所属杆塔",
						editorType : "TextEditor"
					},{
						name : "DEVICEVOLTAGE",
						caption : "电压等级",
						editorType : "TextEditor"
					},{
						name : "DEVICECATEGORY_DISPLAY",
						caption : "监测类型",
						editorType : "TextEditor"
					},{
						name : "MANUFACTURER",
						caption : "生产厂家",
						editorType : "TextEditor"
					},{
						name : "RUNDATE",
						caption : "投运日期",
						editorType : "TextEditor"
					},{
						name : "DATAACCESSRATE",
						caption : "数据接入率",
						editorType : "TextEditor"
					},{
						name : "DATAARACCURACYRATE",
						caption : "数据准确率/图像有效率",
						editorType : "TextEditor"
					},{
						name : "NUMA",
						caption : "告警次数",
						editorType : "TextEditor"
					},{
						name : "BREAKDOWNSTIME",
						caption : "故障时间",
						editorType : "TextEditor"
					},{
						name : "ALLSCORED",
						caption : "得分",
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