$ns("zyzztj.views");

$import("mx.datacontrols.GroupHeaderGrid");

zyzztj.views.zyzztjBySsxtMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.searchBox = null;
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
    	_initSdDydjDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initSearchBox(){
    	me.searchBox = new mxpms.datacontrols.AdvancedSearchBox({
			alias:"zbzyzztj_zyzztjByDydj_searchbox",
			rootGroupId : "402881704230292b0142316493220006",
			PagedControl:_dataGrid,
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
						url : "/com.sgcc.pms.zbztjc.kqxt.jczzgl/rest/zbjczzglUtil/dropDownEditor/dicts_jclx"
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

		_dataGridappendButton();
		
		me.addControl(me.searchBox);
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
    	$newButtonTd1.append(me.queryBtn.$e);
    	// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "0px";
    	
    	//定义一个按监测类型统计的按钮
    	/*me.exportBtn = new mxpms.controls.ImageButton({
    		text:"导出",
    		name:"exportBtn",
    		onclick:me.controller._dydjExportBtn_click
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
    
    function _initDataGrid(){
    	var restUrl = "~/rest/zbzyzzcxtjControl/ssxttj";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : zyzztj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "DWBM"
		});
		
		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"zyzztjBySsxtDataGrid",
			columns: [
				{name: "DWBM", caption: "单位编码" },
				{name: "DWMC", caption: "所属系统", dataAlign:"center", editorType: "TextEditor",width:"240"  },
				{name: "group1",caption: "输电",
					columns :[
						{ name: "SDZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor",width:"140" },
						{ name: "XLS", caption: "覆盖线路数", dataAlign:"center",editorType: "TextEditor",width:"140" },
						{ name: "SDSSJRZZS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor",width:"140" },
						{ name: "SDSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor",width:"140" }
					]	
				},
				{name: "group2",caption: "变电",
					columns :[
						{ name: "BDZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor" ,width:"140"},
						{ name: "BDZS", caption: "覆盖变电站数", dataAlign:"center",editorType: "TextEditor" ,width:"140"},
						{ name: "BDSSJRZZS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor",width:"140" },
						{ name: "BDSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor",width:"140" }
					]	
				}
			],
			// 设置查询条件。
			entityContainer : gridEntityContainer,
			displayCheckBox : false,
			allowAdjusting:false,
			displayRowNumber : true,
			allowPaging : false,
			displayPrimaryKey : false,
			allowEditing : false,
			rowNumberColWidth : 40,
			onload:me.controller._initSdDydjDataGrid
		});
		me.addControl(_dataGrid);
		_dataGrid.load();
    }
    
      /**
	 * 初始化表单视图窗口对象
	 */
	function _initSdDydjDetailWindow() {
		me.detailWin = mxpms.utils.PortalUtil.getGlobalWindowManage().create({
			title : "详细列表",
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
    me.getSearchBox = function(){
    	return me.searchBox;
    }
    /**
     * 获取DataGrid
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
    return me.endOfClass(arguments);
};