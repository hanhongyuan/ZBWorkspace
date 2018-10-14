$ns("sdgjxxcxtj_stat.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

sdgjxxcxtj_stat.views.statByJclxMainView = function()
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
			alias:"sdgjxxcxtj_statByJclx_searchbox",
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
    		onclick:me.controller._jclxQueryBtn_click,
    		width:80,
    		border:"1px solid white"
    		
    	});
    	$newButtonTd1.append(me.queryBtn.$e);
    	//定义一个按监测类型统计的按钮
    	me.exportBtn = new mxpms.controls.ImageButton({
    		text:"导出",
    		name:"exportBtn",
    		onclick:me.controller._jclxExportBtn_click,
    		width:80,
    		border:"1px solid white"
    	});
    	
    	$newButtonTd1.append(me.exportBtn.$e);
    	$searchBox.$(".newButtonTd1")[0].children[0].style.marginRight = "5px";
    }
    
    function initDataGrid(){
    	var restUrl = "~/rest/sdgjxxcxtj_stat/jclxtj";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sdgjxxcxtj_stat.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});
		
		me.dataGrid = new mx.datacontrols.GroupHeaderGrid({
			width : "100%",
			height : "87%",
			alias:"jclxDataGrid",
			columns: [
				{name: "OBJ_ID", caption: "所属单位编码" },
				{name: "WSDEPMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
				{ name: "group1", caption: "微气象监测",
					columns:[
						{ name: "CNT018001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT018001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT018001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE018001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				
				{ name: "group2", caption: "导线弧垂",
					columns:[
						{ name: "CNT013001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT013001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT013001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE013001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{ name: "group3", caption: "导线温度",
					columns:[
								{ name: "CNT013002", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT013002", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT013002", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE013002", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group4", caption: "微风振动",
					columns:[
								{ name: "CNT013003", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT013003", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT013003", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE013003", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group5", caption: "风偏",
					columns:[
								{ name: "CNT013004", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT013004", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT013004", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE013004", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group6", caption: "覆冰",
					columns:[
								{ name: "CNT013005", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT013005", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT013005", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE013005", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
				{ name: "group7", caption: "导线舞动",
					columns:[
								{ name: "CNT013006", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HCNT013006", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "NCNT013006", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
								{ name: "HANDLERATE013006", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
							         ]
				},
			
				{ name: "group8", caption: "杆塔倾斜",
					columns:[
						{ name: "CNT012001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT012001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT012001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE012001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
			
				{ name: "group9", caption: "现场污秽度",
					columns:[
						{ name: "CNT014001", caption: "告警总数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HCNT014001", caption: "已处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "NCNT014001", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" },
						{ name: "HANDLERATE014001", caption: "处理率", dataAlign:"center",editorType: "TextEditor" }                           
					         ]
				},
				{name: "group10",caption: "合计",
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
			title : "列表",
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