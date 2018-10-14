$ns("bdzzjrqk.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.ComplexGrid");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontainers.GridEntityContainer");

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
        var restUrl = "~/rest/bdzzjrqk/getBdData";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : bdzzjrqk.mappath(restUrl),
			loadMeta : false,
			iscID : "-1"/*, // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "objId"*/
		});
	 /* 初始化 DataGrid */
        me.dataGrid = new mx.datacontrols.ComplexGrid({     
            columns:[
            	{	name: "linkedprovicedept", caption: "所属单位" , editorType: "DropDownEditor",width:"10%"},
            	{	name: "linkedstationname", caption: "变电站名称" , editorType: "TextEditor",width:"10%"},
            	{	name: "linkedequipmentname", caption: "设备名称" , editorType: "TextEditor",width:"10%"},
            	{	name: "bzdm", caption: "电压等级" , editorType: "DropDownEditor",width:"10%"},
            	{	name: "monitoringtypes", caption: "监测类型" , editorType: "DropDownEditor"	,width:"10%"},
            	{	name: "devicename", caption: "监测装置名称" , editorType: "TextEditor",width:"10%"},
            	{	name: "manufacturer", caption: "生产厂家" , editorType: "TextEditor",width:"15%"},
            	{	name: "atime", caption: "最后一次上传时间" , editorType: "TextEditor"	,width:"10%"},
            	{	name: "days", caption: "中断时间(天)" , editorType: "TextEditor",width:"10%"},
            	{	name: "sfss", caption: "是否实时" , editorType: "DropDownEditor",width:"5%",
            		renderCell: function(p_item, $p_cell){
	        			var value = p_item.getValue("sfss");
	        			if(value=="T"){
	        				$p_cell.css({"color":"green"});
	        				$p_cell.text("是");
	        			}else{
	        				$p_cell.css({"color":"red"});
	        				$p_cell.text("否");
	        			}
	        			//$p_cell.text(value);
	        		}
            	}
             ],
            searchBox: new mx.datacontrols.DataGridSearchBox({
                fields: [
                	{	name: "linkedprovicedept", caption: "所属单位" , editorType: "DropDownEditor",hint : "--请选择--",displayCheckBox:"true"	},
                	{	name: "monitoringtypes", caption: "监测类型" , editorType: "DropDownEditor"	,hint : "--请选择--",displayCheckBox:"true"},
                	//{	name: "rundate", caption: "投运日期" , editorType: "DateTimeEditor", formatString: "yyyy-MM-dd",hint : "--请选择--"	},
                	{	name: "manufacturer", caption: "生产厂家" , editorType: "TextEditor",hint : "--请输入--",displayCheckBox:"true"	},
                	{	name: "bzdm", caption: "电压等级" , editorType: "DropDownEditor",hint : "--请选择--",displayCheckBox:"true"	},
                	{	name: "linkedstationname", caption: "变电站名称" , editorType: "TextEditor",hint : "--请输入--"	},
                	{	name: "sfss", caption: "是否实时" , editorType: "DropDownEditor",hint : "--请选择--"}
                ],
                 itemNumOfRow : 4,
            	 captionColumnWidth : 60
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
			imageUrl : "~/com.sgcc.pms.zbztjc.kqxt.bbcxgl/resource/icons/daochu.png",
			onclick : me.controller._btnExport_onclick
		},true);
     }
     
     me.getDataGrid = function(){
    	return me.dataGrid;
    }
    
    return me.endOfClass(arguments);
};