$ns("bdzzyxzktj.views");

$import("mx.datacontrols.GroupHeaderGrid");

bdzzyxzktj.views.bdzzyxzktjByDydjMainView = function()
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
			alias:"bdzzyxzktj_bdzzyxzktjByDydj_searchbox",
			rootGroupId : "402881704230292b0142316493220006",
			PagedControl:_dataGrid,
			fields : [ {name : "ssdw",caption : "所属单位",hint:"---请选择---",displayCheckBox:true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_ssdw"
			},
			{	name : "dydj",caption : "电压等级",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_dydj"
			},
			{	name : "jclx",caption : "监测类型",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_jclxBd"
			}, {
				name : "sccj",caption : "生产厂家",displayCheckBox : false,hint:"---请输入---",displayCloseButton : false,editorType : "TextEditor"
			}, {
				name : "bdzmc",caption : "变电站名称",displayCheckBox : false,hint:"---请输入---",displayCloseButton : false,editorType : "TextEditor"
			}, {
				name : "tyrq",caption : "投运日期",hint:"---请选择---",formatString : "yyyy-MM-dd",editorType : "DateTimeEditor"
			}]
		});

		_dataGridappendButton();
		_searchBox.editors.ssdw.setCss({width:280});
		_searchBox.editors.dydj.setCss({width:280});
		_searchBox.editors.jclx.setCss({width:280});
		_searchBox.editors.sccj.setCss({width:280});
		_searchBox.editors.bdzmc.setCss({width:280});
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
    	$newButtonTd1.append(me.queryBtn.$e);
    	// 将重置按钮放入
		$newButtonTd1.append($searchBox.$("#reset"));
		$searchBox.$("#reset")[0].style.marginBottom = "0px";
		$searchBox.$("#reset")[0].style.marginRight = "0px";
		$searchBox.$("#reset")[0].style.marginLeft = "5px";
    	
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
    	var restUrl = "~/rest/bdzzyxzkcontrol/dydjtj";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : bdzzyxzktj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "LINKEDPROVICEDEPT"
		});
		
		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"bdzzyxzktjBydydjDataGrid",
			columns: [
				{name: "LINKEDPROVICEDEPT", caption: "所属单位编码" },
				{name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
				{name: "group1",caption: "1000kV",
					columns :[
						{ name: "ZZZS37", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS37", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL37", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS37", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group2",caption: "750kV",
					columns :[
						{ name: "ZZZS36", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS36", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL36", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS36", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group3",caption: "500kV",
					columns :[
						{ name: "ZZZS35", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS35", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL35", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS35", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group4",caption: "330kV",
					columns :[
						{ name: "ZZZS34", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS34", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL34", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS34", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group5",caption: "220kV",
					columns :[
					{ name: "ZZZS33", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS33", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL33", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS33", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group6",caption: "110kV",
					columns :[
						{ name: "ZZZS32", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS32", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL32", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS32", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group7",caption: "66kV",
					columns :[
					{ name: "ZZZS30", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS30", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL30", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS30", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group8",caption: "35kV",
					columns :[
					{ name: "ZZZS25", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS25", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL25", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS25", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group9",caption: "±1000kV",
					columns :[
					{ name: "ZZZS86", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS86", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL86", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS86", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group10",caption: "±800kV",
					columns :[
						{ name: "ZZZS85", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS85", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL85", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS85", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]
				},
				{name: "group11",caption: "±660kV",
					columns :[
						{ name: "ZZZS84", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS84", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL84", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS84", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group12",caption: "±500kV",
					columns :[
							{ name: "ZZZS83", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS83", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL83", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS83", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group13",caption: "±400kV",
					columns :[
							{ name: "ZZZS82", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS82", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL82", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS82", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group14",caption: "±125kV",
					columns :[
						{ name: "ZZZS81", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS81", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL81", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS81", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group15",caption: "±120kV",
					columns :[
						{ name: "ZZZS80", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZZS80", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL80", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "WSSJRS80", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLZZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLSSJRZZS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "ALLWSSJRS", caption: "未实时接入装置数", dataAlign:"center",editorType: "TextEditor" }
					]	
				}
			],
			// 设置查询条件。
			height:"90%",
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
    
      /**
	 * 初始化表单视图窗口对象
	 */
	function _initBdDydjDetailWindow() {
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