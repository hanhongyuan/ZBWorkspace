$ns("xldzgl.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.ComplexGrid");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.VSplit");
$import("mx.rpc.RESTClient");
xldzgl.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var client = new mx.rpc.RESTClient();
    me.dataGrid = null;
    var height=window.document.documentElement.clientHeight-30;
    base.init = me.init;
    me.init = function()
    {
        base.init();
        initVspilt();
        _initControls();
        _initSxxt();
    };
    
    function initVspilt()
    {
    	me.vSplit = new mx.containers.VSplit({
    	    
    		cols:"30%, 70%"
    	});
    	
    	me.addControl(me.vSplit);
    }
    /**
     * 线路电站信息查询
     * */
    function _initControls()
    {
    	var restUrl = "~/rest/xldzgl/loadinfo";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : xldzgl.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		/* 初始化 DataGrid */
		me.dataGrid = new mx.datacontrols.ComplexGrid({
			// alias : "montringgrid",
			// 设置显示列。
			columns : [ {
				name : "OBJ_ID",
				caption : "唯一标识",
				dataAlign : "center"
			},
			{
				name : "WSMC",
				caption : "所属单位",
				dataAlign : "center"
			},
			
			{
				name : "XLDZMC",
				caption : "线路/电站名称",
				width : 180,
				dataAlign : "center"
				
			},
			{
				name : "DYDJ",
				caption : "电压等级",
				width : 80,
				dataAlign : "center"
				
			},
			{
				name : "SSXT",
				caption : "所属系统",
				width : 180,
				dataAlign : "center"
			}
			],
			// 设置查询条件。
			searchBox : new mx.datacontrols.DataGridSearchBox({
				fields : [ {
					name : "xldzmc",
					caption : "线路或变电站名称",
					editorType : "textEditor",
					width : 180,
					allowEditing : false,
					displayCheckBox : true,
					hint : "--请输入--"
				},
				 {
					name : "sfkq",
					caption : "是否跨区",
					editorType : "DropDownEditor",
					width : 180,
					displayMember: "name",
    			    valueMember: "value",
					allowEditing : false,
					displayCheckBox : true,
					items: [
					        { name: "是", value: "1" },
					        { name: "否", value: "0" }
					    ],
					hint : "--请选择--"
				}
				],
				itemNumOfRow : 4
			}),
			// displayPrimaryKey : true,
			displayRowNumber : true,
			displayCheckBox : true,
			rowNumberColWidth : 50,
			displayColSplitLine : true,
			entityContainer : gridEntityContainer,
			enableCellTip : true

		});
		_resetToolBar();
		me.dataGrid.toolBar.$e.insertAfter(me.dataGrid.searchBox.$e);
		me.vSplit.addControl(me.dataGrid,1);
		me.dataGrid.load();
    }
    /**
     * 系统名称查询
     * */
    function _initSxxt()
    {
    	
    	var label = new mx.controls.Label({
    	    text: "跨区系统信息",
    	    textAlign: "center",
    	    verticalAlign: "middle",
    	    width:"100%",
    	    fontSize:"15px",
    	    height:"4%"  
    	});
    	me.vSplit.addControl(label,0);
    	label.setCss({"color":"blue"});
    	var restUrl = "~/rest/xldzgl/loadxt";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : xldzgl.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "OBJ_ID"
		});

		/* 初始化 DataGrid */
		me.dataGridxt = new mx.datacontrols.DataGrid({
			// alias : "montringgrid",
			// 设置显示列。
			columns : [ {
				name : "OBJ_ID",
				caption : "唯一标识",
				dataAlign : "center"
			},
			{
				name : "XTMC",
				caption : "系统名称",
				width : 180,
				dataAlign : "center"
			},
			{
				name : "XTJC",
				caption : "系统简称",
				width : 180,
				dataAlign : "center"
			}
			],
			displayRowNumber : false,
			displayCheckBox : true,
			rowNumberColWidth : 50,
			displayColSplitLine : true,
			entityContainer : gridEntityContainer,
			enableCellTip : true,
			height:"96%"

		});
		me.vSplit.addControl(me.dataGridxt,0);
		me.dataGridxt.load();
    }
    
	/**
	 * 加载按钮
	 */
	function _resetToolBar() {
		me.dataGrid.toolBar.clearItems();

		me.dataGrid.toolBar.insertItem(0, {
			name : "union",
			text : "关联系统",
			toolTip : "关联系统",
			onclick : _btnunion_onclick
		}, true);
	}
	/**
	 * 关联系统
	 * */
    function _btnunion_onclick()
    {
    	var xl = me.dataGrid.getCheckedItems();
    	var xt = me.dataGridxt.getCheckedItems();
    	var lengthxl = xl.length;
    	var lengthxt = xt.length;
    	var xlid = "";
    	var xtid = "";
    	if(lengthxl==0)
    	{
    		mx.indicate("info", "线路电站信息请至少选择一条!");
    		return;
    	}
    	else if(lengthxt==0||lengthxt>1)
    	{
    		mx.indicate("info", "系统信息能且只能选择一条!");
    		return;
    	}
    	 for(var i =0;i<lengthxl;i++) 
    		 xlid = xlid+xl[i].id+"&";
    	 	 xlid = xlid.substr(0,xlid.length-1);
    	 	 xtid = xt[0].id;
    	
    	 	
    	var filter = { "xlid":xlid,"xtid":xtid};
    	 
    	var restUrl = "~/rest/xldzgl/unioninfo";
    
    	$.ajax({  
            data:filter,  
            type:"POST",  
            dataType:"json",  
            url:xldzgl.mappath(restUrl),  
            success:function(){
            	me.dataGrid.load();
            },
    	 error:function()
         {
    		 mx.indicate("info", "关联失败!");
         }
          });
    	
    	
    }
	
	
	
    return me.endOfClass(arguments);
};