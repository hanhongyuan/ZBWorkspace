$ns("mainSystemgjxxcxtj_stat.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.GroupHeaderGrid");

mainSystemgjxxcxtj_stat.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    me.detailWin  = null;
    
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
    	base.init();
    	_initSearchBox();
        _initDataGrid();
        _initDetailWindow();
        me.on("activate", me.controller._onactivate);
    };
    
    function _initSearchBox(){
    	me._searchBox = new mxpms.datacontrols.AdvancedSearchBox({
    				alias:"mainSystemgjxxcxtj_statSearchBox",
        			rootGroupId : "402881704230292b0142316493220006",
        			PagedControl:me._dataGrid,
        			height:"12%",
        			itemNumOfRow:4,
        			fields : [ {name : "ssxt",caption : "所属系统",hint:"--未指定--",displayCheckBox:true,editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_ssxt_mainSystem"
        			},
        			{	name : "ssdw",caption : "所属单位",hint : "--未指定--",displayCheckBox: true,editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_ssdw_mainSystem"
        			},
        			{	name : "dydj",caption : "电压等级",hint : "--未指定--",displayCheckBox: true,editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_dydj"
        			},
        			{	name : "jclx",caption : "监测类型",hint : "--未指定--",displayCheckBox: true,editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_jclx_mainSystem"
        			},
        			{	name : "gjjb",caption : "告警级别",displayCheckBox:true,hint : "--未指定--",editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_gjjb"
        			},
        			{	name : "xlmc",caption : "线路名称",hint : "--请输入名称--",editorType : "TextEditor"
        			},
        			{	name : "bdz",caption : "变电站名称",hint : "--请输入名称--",editorType : "TextEditor"
        			},
        			{name : "gjsj",caption : "告警时间",displayCheckBox: true,formatString:"yyyy-MM-dd",editorType : "DateTimeEditor"}
        			]
        		});
    	
    			_dataGridappendButton();
        		
        		me.addControl(me._searchBox);
    	}
    
	    function _dataGridappendButton() {
	    	var $searchBox = me._searchBox;
	    	
	    	$searchBox.$("#btnTd").hide();
	    	var $tbody =  $searchBox.$("#btnTr");
	    	$tbody.append("<td class='newButtonTd1' colspan=8 style='padding-left:85%; '></td>");
	    	var $newButtonTd1 = $searchBox.$(".newButtonTd1");
	    	//定义一个查询按钮
	    	me.statBtn = new mxpms.controls.ImageButton({
	    		text:"统计",
	    		name:"stat",
	    		onclick:me.controller._btnStat_click,
	    		width:80,
	    		border:"1px solid white"
	    		
	    	});
	    	$newButtonTd1.append(me.statBtn.$e);
	    	//定义一个导出按钮
	    	me.exportBtn = new mxpms.controls.ImageButton({
	    		text:"导出",
	    		name:"queryDetail",
	    		onclick:me.controller._btnExport_click,
	    		width:80,
	    		border:"1px solid white"
	    	});
	    	
	    	$newButtonTd1.append(me.exportBtn.$e);
	    }
	    /**
	     * 按所属系统统计
	     */
	    function _initDataGrid(){
	    	var restUrl = "~/rest/mainSystemgjxxcxtj_stat/statBySsxt";
			var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
				baseUrl : mainSystemgjxxcxtj_stat.mappath(restUrl),
				loadMeta : false,
				iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
				primaryKey : "OBJ_ID"
			});
			
			me._dataGrid = new mx.datacontrols.GroupHeaderGrid({
				//width : "100%",
				height : "88%",
				alias:"ssxtDataGrid",
				columns: [
					{name: "OBJ_ID", caption: "所属系统编码" },
					{name: "WSDEPMC", caption: "所属系统", dataAlign:"center", editorType: "TextEditor",width:"20%"  },
					{name: "group1",caption: "输电",
						columns :[
							{ name: "CNT01", caption: "告警总数", dataAlign:"center",editorType: "TextEditor",width:"10%"  },
							{ name: "HCNT01", caption: "已处理数", dataAlign:"center",editorType: "TextEditor",width:"10%" },
							{ name: "NCNT01", caption: "未处理数", dataAlign:"center",editorType: "TextEditor" ,width:"10%" },
							{ name: "HANDLERATE01", caption: "处理率", dataAlign:"center",editorType: "TextEditor",width:"10%" }
						]	
					},
					{name: "group2",caption: "变电",
						columns :[
							{ name: "CNT02", caption: "告警总数", dataAlign:"center",editorType: "TextEditor",width:"10%" },
							{ name: "HCNT02", caption: "已处理数", dataAlign:"center",editorType: "TextEditor",width:"10%" },
							{ name: "NCNT02", caption: "未处理数", dataAlign:"center",editorType: "TextEditor",width:"10%" },
							{ name: "HANDLERATE02", caption: "处理率", dataAlign:"center",editorType: "TextEditor",width:"10%" }
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
			me.addControl(me._dataGrid);
			me._dataGrid.load();
	    }
	    
	    /**
		 * 初始化表单视图窗口对象
		 */
		function _initDetailWindow() {
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
	    	return me._searchBox;
	    }
	    
	    /**
	     * 获取DataGrid
	     */
	    me.getDataGrid = function(){
	    	return me._dataGrid;
	    }
	    
    return me.endOfClass(arguments);
};