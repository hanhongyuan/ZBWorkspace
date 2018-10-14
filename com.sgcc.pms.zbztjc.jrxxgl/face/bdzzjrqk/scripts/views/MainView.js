$ns("bdzzjrqk.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");

bdzzjrqk.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	/* 初始化 EntityContainer */
    	var restUrl ="~/rest/zzjrqkcx/getBdData";
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : bdzzjrqk.mappath(restUrl),
			loadMeta : false,
			iscID : "-1"
		});
	
	 /* 初始化 DataGrid */
        me.dataGrid = new mx.datacontrols.ComplexGrid({      
            columns:[
            	{	name: "linkedprovicedept", caption: "所属单位" , editorType: "DropDownEditor"	},
            	{	name: "linkedstationname", caption: "所属变电站" , editorType: "TextEditor"	},
            	{	name: "linkedequipmentname", caption: "所属一次设备" , editorType: "TextEditor"	},
            	{	name: "bzdm", caption: "电压等级" , editorType: "DropDownEditor"	},
            	{	name: "monitoringtypes", caption: "监测类型" , editorType: "DropDownEditor"	},
            	{	name: "devicename", caption: "监测装置名称" , editorType: "TextEditor"	},
            	{	name: "rundate", caption: "投运日期" , editorType: "TextEditor"	},
            	{	name: "manufacturer", caption: "生产厂家" , editorType: "TextEditor"	},
            	{	name: "atime", caption: "最后一次上传时间" , editorType: "TextEditor"	},
            	{	name: "days", caption: "中断时间(天)" , editorType: "TextEditor"	},
            	{	name: "sfss", caption: "是否实时" , editorType: "DropDownEditor",
            		renderCell: function(p_item, $p_cell){
	        			var value = p_item.getValue("sfss");
	        			if(value=="T"){
	        				value="是";
	        			}else{
	        				value="否";
	        			}
	        			$p_cell.css({"color":"red"/*,"text-decoration":"underline"*/});
	        			$p_cell.text(value);
	        		}	
            	}
            ],
            searchBox: new mx.datacontrols.DataGridSearchBox({
                fields: [
                	{	name: "linkedprovicedept", caption: "所属单位" , editorType: "DropDownEditor", displayCheckBox:"true"	,hint : "--请输入--"	},
                	{	name: "monitoringtypes", caption: "监测类型" , editorType: "DropDownEditor", displayCheckBox:"true"	,hint : "--请输入--"	},
                	{	name: "manufacturer", caption: "生产厂家" , editorType: "TextEditor"	, displayCheckBox:"true"	,hint : "--请输入--"},
                	{	name: "bzdm", caption: "电压等级" , editorType: "DropDownEditor"	, displayCheckBox:"true"	,hint : "--请输入--"},
                	{	name: "sfss", caption: "是否实时" , editorType: "DropDownEditor"	, displayCheckBox:"true"	,hint : "--请输入--"},
                	{	name: "rundate", caption: "投运日期" , editorType: "DateTimeEditor",hint : "--请输入--"	},
                	//{	name: "jsdate", caption: "至" , editorType: "DateTimeEditor"	,hint : "--请输入--"},
                	{	name: "linkedstationname", caption: "所属变电站" , editorType: "TextEditor"	,hint : "--请输入--"}
                ],
                itemNumOfRow : 5,
            	captionColumnWidth : 90
            }),
            displayCheckBox: true,
	        displayPrimaryKey:false,
            allowEditing: false, 
	        pageSize : 20,
	        displayRowNumber:true,
	        displayColSplitLine : true,
			entityContainer : gridEntityContainer,
			enableCellTip : true
        });
  		
        _resetToolBarItems();
        me.dataGrid.toolBar.$e.insertAfter(me.dataGrid.searchBox.$e);
        me.addControl(me.dataGrid);
        
        me.on("activate", me.controller._onactivate);
    }
    
     function  _resetToolBarItems(){
     	me.dataGrid.toolBar.clearItems();
     	me.dataGrid.toolBar.insertItem(0, {
			name : "exportBdExcel",
			text : "导出",
			toolTip : "导出",
			imageUrl : "~/com.sgcc.pms.zbztjc.jrxxgl/resource/icons/daochu.png",
			onclick : me.controller._btnExport_onclick
		}, true);
     }
     
   me.getDataGrid = function(){
    	return me.dataGrid;
   } 
    
    return me.endOfClass(arguments);
};