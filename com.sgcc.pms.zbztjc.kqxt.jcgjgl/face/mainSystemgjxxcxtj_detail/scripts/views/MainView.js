$ns("mainSystemgjxxcxtj_detail.views");

$import("mainSystemgjxxcxtj_stat.views.MainViewController");
$import("mx.datacontrols.DataGrid");

mainSystemgjxxcxtj_detail.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me._dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	_initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {
    	var restUrl = "~/rest/mainSystemgjxxcxtj_stat/queryDetail";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : mainSystemgjxxcxtj_detail.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "OBJ_ID"
        });
        
        me._dataGrid = new mx.datacontrols.DataGrid({
        	alias:"mainSystemgjxxcxtj_statmainSystemgjxxcxtj_statDataGrid",
        	columns:[
        				{	name: "OBJ_ID", caption: "告警编号" , editorType: "TextEditor",width:30,visible:false},
        				{	name: "WSDEPMC", caption: "所属单位" , editorType: "TextEditor",width:150},
        				{	name: "XTMC", caption: "所属系统" , editorType: "TextEditor",width:150},
        				{	name: "LINKEDLINENAME", caption: "线路名称" , editorType: "TextEditor",width:120},
        				{	name: "LINKEDPOLENAME", caption: "杆塔名称" , editorType: "TextEditor",width:120},
        				{	name: "DEVICEVOLTAGE", caption: "电压等级" , editorType: "TextEditor",width:80},
        				{	name: "DEVICECODE", caption: "装置编码" , editorType: "TextEditor",width:120,visible:false},
        				{	name: "DEVICENAME", caption: "装置名称" , editorType: "TextEditor",width:120},
        				{	name: "MONITORINGTYPE", caption: "监测类型编码" , editorType: "TextEditor",width:150,visible:false},
        				{	name: "TYPENAME", caption: "监测类型" , editorType: "TextEditor",width:150},
        				{	name: "ALARMTIME", caption: "告警时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd HH:mm:ss",width:150},
        				{	name: "ALARMLEVEL", caption: "告警级别" , editorType: "TextEditor"	,width:80},
        				{	name: "CONTINUANCETIME", caption: "告警持续时间/分钟" , editorType: "TextEditor"},
        				{	name: "ishandledName", caption: "是否处理" , editorType: "TextEditor"	,width:80,
        					renderCell: function(s,p){
        						var ishandledName = s.values.ishandledName;
        						if("否" == ishandledName){
        							p.html("<a href=\"javascript: void(0);\" style=\"color:red;text-decoration:none\">"+ishandledName+"</a>"); 
        						}else{
        							p.html("<a href=\"javascript: void(0);\" style=\"color:green;text-decoration:none\">"+ishandledName+"</a>"); 
        						}
        				     }
        				},
        				{	name: "ALARMMESSAGE", caption: "告警消息" , editorType: "TextEditor",width:250}
        	            ],
        	            
	        displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
	        pageSize : 10,
	        entityContainer: gridEntityContainer
        });
        me.addControl(me._dataGrid);
    }
    
    me.getDataGrid = function(){
    	return me._dataGrid;
    }
    
    return me.endOfClass(arguments);
};