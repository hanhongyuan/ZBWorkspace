$ns("dlgjxxDetail.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

dlgjxxDetail.views.gjxxDetailMainView = function()
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
    	_initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
 function _initDataGrid(){

    	
    	var restUrl = "~/rest/dlgjxxcxtj_stat/getDetailList";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : dlgjxxcxtj_stat.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "OBJ_ID"
        });
        
		me.dataGrid = new mx.datacontrols.DataGrid({
			alias:"gjxxDetailgjxxDetailDataGrid",
        	columns:[
     				{	name: "OBJ_ID", caption: "告警编号" , editorType: "TextEditor",width:30,visible:false},
     				{	name: "LINKEDCABLEANDCHANNELNAME", caption: "电力电缆及通道名称" , editorType: "TextEditor",width:120},
     				{	name: "LINKEDEQUIPMENTNAME", caption: "设备名称" , editorType: "TextEditor",width:120},
     				{	name: "TYPENAME", caption: "监测类型" , editorType: "TextEditor",width:120},
     				{	name: "JCXX", caption: "监测信息" , editorType: "TextEditor",width:80,
     					renderCell : function(p_item, $p_cell) {
     						var value = "查看"; // 获取 GENDER 字段的值。 // 获取 GENDER 字段的值。
     	     						var deptws = p_item.getValue("LINKEDDEPWS");
     	     						var devicecode = p_item.getValue("DEVICECODE");
     	     						var monitor = p_item.getValue("MONITORINGTYPE");
     	     						$p_cell.html(value).css({
     	     							"text-decoration" : "underline",
     	     							"color" : "blue"
     	     						}).mouseover(function() {
     	     							$p_cell.css("cursor", "pointer");
     	     						}).on("click", function() {
     	     							$p_cell.css("color", "grey");
     	     							me.controller._btnTest(devicecode,monitor,deptws);
     	     						});
     							}
     				},
     				{	name: "WSDEPMC", caption: "所属单位" , editorType: "TextEditor",width:120},
     				{	name: "DEVICEVOLTAGE", caption: "电压等级" , editorType: "TextEditor",width:80},
     				{	name: "ALARMMESSAGE", caption: "告警消息" , editorType: "TextEditor",width:250},
     				{	name: "ALARMLEVEL", caption: "告警级别" , editorType: "TextEditor"	,width:80},
     				{	name: "ALARMTIME", caption: "告警时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd HH:mm:ss",width:150}
     	            ],
     	            
	        displayPrimaryKey:false,//列表是否显示主键
	        pageSize : 10,
	        entityContainer: gridEntityContainer
		});
		me.addControl(me.dataGrid);
    }
 
	 me.getDataGrid = function(){
	 	return me.dataGrid;
	 }
    
    return me.endOfClass(arguments);
};