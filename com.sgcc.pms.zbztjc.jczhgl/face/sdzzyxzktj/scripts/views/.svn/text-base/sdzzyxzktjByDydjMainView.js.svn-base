$ns("sdzzyxzktj.views");

$import("mx.datacontrols.GroupHeaderGrid");

sdzzyxzktj.views.sdzzyxzktjByDydjMainView = function()
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
    	_initSdDydjDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initSearchBox(){
    	_searchBox = new mxpms.datacontrols.AdvancedSearchBox({
			alias:"sdzzyxzktj_sdzzyxzktjByDydj_searchbox",
			rootGroupId : "402881704230292b0142316493220006",
			PagedControl:_dataGrid,
			fields : [ {name : "ssdw",caption : "所属单位",hint:"---请选择---",displayCheckBox:true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_ssdw"
			},
			{	name : "dydj",caption : "电压等级",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_dydj"
			},
			{	name : "jclx",caption : "监测类型",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_jclx"
			}, {
				name : "sccj",caption : "生产厂家",displayCheckBox : false,hint:"---请输入---",onchanged:_paramChanged,displayCloseButton : false,editorType : "TextEditor"
			}, {
				name : "xlmc",caption : "线路名称",displayCheckBox : false,hint:"---请输入---",onchanged:_nameChanged,displayCloseButton : false,editorType : "TextEditor"
			}, {
				name : "tyrq",caption : "投运日期",hint:"---请选择---",formatString : "yyyy-MM-dd",editorType : "DateTimeEditor"
			}]
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
    	
    	//定义一个按监测类型统计的按钮
    	me.exportBtn = new mxpms.controls.ImageButton({
    		text:"导出",
    		name:"exportBtn",
    		onclick:me.controller._dydjExportBtn_click
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
    
    function _initDataGrid(){
    	var restUrl = "~/rest/sdzzyxzkcontrol/dydjtj";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sdzzyxzktj.mappath(restUrl),
			loadMeta : false,
			iscID : "-1" // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
		});
		
		_dataGrid = new mx.datacontrols.GroupHeaderGrid({
			alias:"sdzzyxzktjBydydjDataGrid",
			columns: [
				{name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
				{name: "group1",caption: "1000kV",
					columns :[
						{ name: "KHZZS37", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS37", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL37", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL37", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL37", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF37", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group2",caption: "750kV",
					columns :[
						{ name: "KHZZS36", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS36", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL36", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL36", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL36", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF36", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group3",caption: "500kV",
					columns :[
						{ name: "KHZZS35", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS35", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL35", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL35", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL35", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF35", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group4",caption: "330kV",
					columns :[
						{ name: "KHZZS34", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS34", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL34", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL34", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL34", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF34", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group5",caption: "220kV",
					columns :[
					{ name: "KHZZS33", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS33", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL33", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL33", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL33", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF33", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group6",caption: "110kV",
					columns :[
						{ name: "KHZZS32", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS32", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL32", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL32", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL32", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF32", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group7",caption: "66kV",
					columns :[
					{ name: "KHZZS30", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS30", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL30", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL30", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL30", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF30", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group8",caption: "35kV",
					columns :[
					{ name: "KHZZS25", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS25", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL25", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL25", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL25", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF25", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group9",caption: "±1000kV",
					columns :[
					{ name: "KHZZS86", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS86", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL86", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL86", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL86", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF86", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group10",caption: "±800kV",
					columns :[
						{ name: "KHZZS85", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS85", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL85", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL85", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL85", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF85", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]
				},
				{name: "group11",caption: "±660kV",
					columns :[
						{ name: "KHZZS84", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS84", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL84", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL84", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL84", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF84", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group12",caption: "±500kV",
					columns :[
							{ name: "KHZZS83", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS83", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL83", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL83", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL83", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF83", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group13",caption: "±400kV",
					columns :[
							{ name: "KHZZS82", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS82", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL82", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL82", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL82", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF82", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group14",caption: "±125kV",
					columns :[
						{ name: "KHZZS81", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS81", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL81", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL81", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL81", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF81", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group15",caption: "±120kV",
					columns :[
						{ name: "KHZZS80", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRZS80", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "SSJRL80", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
						//{ name: "JRL80", caption: "数据接入率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "ZQL80", caption: "数据准确率", dataAlign:"center",editorType: "TextEditor" },
						//{ name: "DF80", caption: "得分", dataAlign:"center",editorType: "TextEditor" }
						]	
				},
				{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLKHZZS", caption: "装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLSSJRZS", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
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
			onload:me.controller._initSdDydjDataGrid,
			rowNumberColWidth : 40
			
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