$ns("sdgjxxcxtj_stat.views");

$import("mx.datacontrols.GroupHeaderGrid");

sdgjxxcxtj_stat.views.statByDydjMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.searchBox = null;
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        
        _initControls();
    };
    
    function _initControls()
    {
    	_initSearchBox();
    	-initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initSearchBox(){
    	me.searchBox = new mxpms.datacontrols.AdvancedSearchBox({
			alias:"sdgjxxcxtj_statByDydj_searchbox",
			rootGroupId : "402881704230292b0142316493220006",
			PagedControl:me.dataGrid,
			height:"13%",
			itemNumOfRow:4,
			fields : [ {name : "ssdw",caption : "所属单位",hint:"---请选择---",displayCheckBox:true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_ssdw"
			},
			{	name : "dydj",caption : "电压等级",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_dydj"
			},
			{	name : "jclx",caption : "监测类型",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_jclx"
			},
			{	name : "xlmc",caption : "线路名称",hint : "--请选择--",onchanged:_nameChanged,editorType : "TextEditor"
			},
			{	name : "gjjb",caption : "告警级别",displayCheckBox: true,hint : "--请选择--",editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_gjjb"
			},
			{name : "gjsj",caption : "告警时间",displayCheckBox: true,formatString:"yyyy-MM-dd",editorType : "DateTimeEditor"}
			]
		});

		_dataGridappendButton();
		
		me.searchBox.editors.ssdw.setWidth("180px");
		me.searchBox.editors.dydj.setWidth("180px");
		me.searchBox.editors.jclx.setWidth("180px");
		me.searchBox.editors.xlmc.setWidth("180px");
		me.searchBox.editors.gjjb.setWidth("180px");
		me.searchBox.editors.gjsj.setWidth("180px");
		
		me.addControl(me.searchBox);
    }
    
    /**
     * 给SearchBox添加按钮的方法
     */
    function _dataGridappendButton() {
    	var $searchBox = me.searchBox;
    	
    	$searchBox.$("#btnTd").hide();
    	var $tbody =  $searchBox.$("#btnTr");
    	$tbody.append("<td class='newButtonTd1' colspan=8 style='padding-left:85%; '></td>");
    	var $newButtonTd1 = $searchBox.$(".newButtonTd1");
    	//定义一个按电压等级统计的按钮
    	me.queryBtn = new mxpms.controls.ImageButton({
    		text:"统计",
    		name:"queryBtn",
    		onclick:me.controller._dydjQueryBtn_click,
    		width:80,
    		border:"1px solid white"
    		
    	});
    	$newButtonTd1.append(me.queryBtn.$e);
    	//定义一个按监测类型统计的按钮
    	me.exportBtn = new mxpms.controls.ImageButton({
    		text:"导出",
    		name:"exportBtn",
    		onclick:me.controller._dydjExportBtn_click,
    		width:80,
    		border:"1px solid white"
    	});
    	
    	$newButtonTd1.append(me.exportBtn.$e);
    	$searchBox.$(".newButtonTd1")[0].children[0].style.marginRight = "5px";
    }
    
    
    function initDataGrid(){
    	var restUrl = "~/rest/sdgjxxcxtj_stat/dydjtj";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sdgjxxcxtj_stat.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});
		
		me.dataGrid = new mx.datacontrols.GroupHeaderGrid({
			width : "100%",
			height : "87%",
			alias:"dydjDataGrid",
			columns: [
				{name: "OBJ_ID", caption: "所属单位编码" },
				{name: "WSDEPMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
				{name: "group1",caption: "1000kV",
					columns :[
						{ name: "CNT37", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT37", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT37", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE37", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group2",caption: "750kV",
					columns :[
						{ name: "CNT36", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT36", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT36", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE36", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group3",caption: "500kV",
					columns :[
						{ name: "CNT35", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT35", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT35", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE35", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group4",caption: "330kV",
					columns :[
						{ name: "CNT34", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT34", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT34", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE34", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group5",caption: "220kV",
					columns :[
						{ name: "CNT33", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT33", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT33", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE33", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group6",caption: "110kV",
					columns :[
						{ name: "CNT32", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT32", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT32", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE32", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group7",caption: "66kV",
					columns :[
						{ name: "CNT30", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT30", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT30", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE30", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group8",caption: "35kV",
					columns :[
						{ name: "CNT25", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT25", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT25", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE25", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group9",caption: "±1000kV",
					columns :[
						{ name: "CNT86", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT86", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT86", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE86", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group10",caption: "±800kV",
					columns :[
						{ name: "CNT85", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT85", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT85", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE85", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]
				},
				{name: "group11",caption: "±660kV",
					columns :[
						{ name: "CNT84", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT84", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT84", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE84", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group12",caption: "±500kV",
					columns :[
						{ name: "CNT83", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT83", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT83", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE83", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group13",caption: "±400kV",
					columns :[
						{ name: "CNT82", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT82", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT82", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE82", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group14",caption: "±125kV",
					columns :[
						{ name: "CNT81", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT81", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT81", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE81", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group15",caption: "±120kV",
					columns :[
						{ name: "CNT80", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT80", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT80", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE80", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				},
				{name: "group16",caption: "合计",
					columns :[
						{ name: "ALLCNT", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLHCNT", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLNCNT", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "ALLHANDLERATE", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }
					]	
				}
			],
			// 设置查询条件。
			entityContainer : gridEntityContainer,
			displayCheckBox : false,
			displayRowNumber : true,
			allowPaging : false,
			displayPrimaryKey : false,
			displayColSplitLine : true,
			allowEditing : false,
			rowNumberColWidth : 40,
			onload:me.controller._initDataGrid
		});
		me.addControl(me.dataGrid);
		me.dataGrid.load();
    }
    
    
    /**
	 * 初始化表单视图窗口对象
	 */
	function _initDetailWindow() {
		me.detailWin = mxpms.utils.PortalUtil.getGlobalWindowManage().create({
			title : "故障详细列表",
			width : 900,
			height : 500,
			movable : true,
			reusable : true,
			resizable : false,
			top : "center",
			left : "center"
		});
	}
	var pattern1 = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]+$");
    function _nameChanged()
	{
		var xlmc = me.searchBox.editors.xlmc.value;
		
		if(xlmc!=null&&xlmc!="")
		{
			if(xlmc.length>50|| !pattern1.test(xlmc)){
				me.searchBox.editors.xlmc.reset();
				mx.indicate("warn", "线路名称长度不能超50且不能为特殊字符!");
			}
				
		}	
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
    	return me.dataGrid;
    }
    
    return me.endOfClass(arguments);
};