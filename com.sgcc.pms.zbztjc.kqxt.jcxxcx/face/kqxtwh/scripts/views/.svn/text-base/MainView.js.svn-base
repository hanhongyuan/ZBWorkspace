$ns("kqxtwh.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

kqxtwh.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.objID = null;
    
    me.form = null;
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        initToolBar();
        initDataGrid();
        me.on("activate", me.controller._onactivate);
    };
    
    function initToolBar()
    {
    	me.toolBar = new mx.controls.ToolBar({
    		items :
    		    [
				 { name: "new", text:"新建", toolTip:"新建",imageKey:"new", onclick: me.controller._btnNew_onclick},
				 "-",
    		     { name: "save", text:"保存", toolTip:"保存",imageKey:"save", onclick: me.controller._btnSave_onclick},
                 "-",
                 { name: "refresh", text:"刷新", toolTip:"刷新",imageKey:"refresh", onclick: me.controller._btnRefresh_onclick},
                 "-"
    		    ],
    		    height:"5%"
    	});
    	me.addControl(me.toolBar);
	
    }
    /**
     * 初始化DataGridn
     */
    function initDataGrid(){
    	var restUrl = "~/rest/KqxtwhControl/";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : kqxtwh.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "OBJ_ID"
        });
    	me.dataGrid = new mx.datacontrols.DataGrid({
        	columns:[
      		        {	name: "OBJ_ID", caption: "编号" , editorType: "TextEditor"},
      		        {	name: "XTMC", caption: "系统名称" , editorType: "TextEditor",width:120},
      		        {	name: "XTJC", caption: "系统简称" , editorType: "TextEditor",width:200}
    		],
     		displayCheckBox: true,
            displayRowNumber:true,
            rowNumberColWidth:30,
            allowEditing:true,
            allowInterlacedRow:true,//隔行变色
            height:"95%",
   	        displayPrimaryKey:false,//列表是否显示主键
   	        onitemchecked:me.controller._number_onitemchecked,
   	        oncelledited:me.controller._number_oncelledited,
            entityContainer: gridEntityContainer
    	});
    	me.addControl(me.dataGrid);
    	me.dataGrid.load();
    }
    
  //获取form
    me.getDataGrid = function()
    {
    	return me.dataGrid;
    }
    
    return me.endOfClass(arguments);
};