$ns("sdmonthzs.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.ComplexGrid");

sdmonthzs.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var myDate = new Date();
    var year = myDate.getFullYear();
    var tjtime = year.toString();
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	 /* 初始化 ToolBar */
    	var restUrl = "~/rest/sddayaccess/loadyfxx";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : sdmonthzs.mappath(restUrl),
			loadMeta : false,
			iscID : "-1"
		});
		
		me.dataGrid = new mx.datacontrols.ComplexGrid({
    		
    		columns: [
    		
    		{ name: "SSWSMC", caption: "所属单位", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ1", caption: "一月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ2", caption: "二月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ3", caption: "三月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ4", caption: "四月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ5", caption: "五月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ6", caption: "六月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ7", caption: "七月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ8", caption: "八月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ9", caption: "九月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ10", caption: "十月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ11", caption: "十一月份装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZSHJ12", caption: "十二月份装置总数", dataAlign:"center", editorType: "TextEditor"  }
	
        	],
        	// 设置查询条件。
        	searchBox : new mx.datacontrols.DataGridSearchBox({
				fields : [ {
					name : "linkeddepws",
					caption : "所属单位",
					editorType : "DropDownEditor",
					width : 180,
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请选择--"
				}, {
					name : "savetime",
					caption : "年份",
					editorType : "DropDownEditor",
					width : 50,
					displayMember: "name",
					allowEditing : false,
					 items: [
							 { name: "2015", value: "2015" },
							 { name: "2016", value: "2016" },
					         { name: "2017", value: "2017" },
					         { name: "2018", value: "2018" },
					         { name: "2019", value: "2019" },
					         { name: "2020", value: "2020" },
					         { name: "2021", value: "2021" },
					         { name: "2022", value: "2022" }
					     ]
				}
				],
				itemNumOfRow : 4
			}),
			// displayPrimaryKey : true,
			displayRowNumber : true,
			displayCheckBox : false,
			rowNumberColWidth : 50,
			pageSize : 28,
			displayColSplitLine : true,
			entityContainer : gridEntityContainer,
			enableCellTip : true
    		});
		_resetToolBar();
		me.dataGrid.toolBar.$e.insertAfter(me.dataGrid.searchBox.$e);
		me.addControl(me.dataGrid);
		me.dataGrid.searchBox.editors["savetime"].setValue(tjtime);
		me.dataGrid.load();
    }
    /**
     * 加载按钮
     */
    function _resetToolBar() {
    	me.dataGrid.toolBar.clearItems();

    	me.dataGrid.toolBar.insertItem(0, {
    		name : "export",
    		text : "导出",
    		toolTip : "导出",
    		imageUrl : "~/com.sgcc.pms.zbztjc.jcxxgl/resource/icons/daochu.png",
    		onclick : me.controller._btnexport_onclick
    	}, true);
    }
    	
        //获取grid
        me.getDataGrid = function()
        {
        	return me.dataGrid;
        }
    return me.endOfClass(arguments);
};