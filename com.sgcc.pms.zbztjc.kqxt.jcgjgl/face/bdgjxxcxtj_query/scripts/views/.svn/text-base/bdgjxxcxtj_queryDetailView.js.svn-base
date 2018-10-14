$ns("bdgjxxcxtj_query.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.containers.TabControl");
$import("mx.datacontrols.DataGrid");
$import("mx.rpc.RESTClient");
$import("mx.datacontainers.GridEntityContainer");

bdgjxxcxtj_query.views.bdgjxxcxtj_queryDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * 表单对象id
     */
    me.objID = null;
    /**
      * 表单对象
     */
    var _form = null;
    /**
     * 工具条
     */
    var _toolBar = null;

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

    me.load = function()
    {
    	//加载表单信息
    	_form.load();
    }

    function _initControls()
    {
    	_initToolBar();
        _initDataForm();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initToolBar()
    {
    	_toolBar = new mx.controls.ToolBar({
            width: "100%",
            items: [
                    { name: "save", text:"保存", toolTip:"保存",imageKey:"save",enabled:false, onclick: me.controller._btnSave_onclick},
                    "-",
                    { name: "refresh", text:"刷新", toolTip:"刷新",imageKey:"refresh", onclick: me.controller._btnRefresh_onclick},
                    "-",
                    { name: "revoke", text:"撤销", toolTip:"撤销",imageKey:"revoke", enabled:false,onclick: me.controller._btnRevoke_onclick},
                    "-",
                    { name: "print", text:"打印", toolTip:"打印",imageKey:"print", onclick:function(){_form.printForm()}},
                    "-"
                    ]
        });
    }
    
    function _initDataForm()
    {
    	 var restUrl = "~/rest/bdgjxxcxtj_query/query";
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : bdgjxxcxtj_query.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "objId"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"bdgjxxcxtj_querybdgjxxcxtj_queryDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "objId", caption: "编号", editorType: "TextEditor", visible:false},
	        {	name: "LINKEDSTATIONNAME", caption: "所属变电站", editorType: "TextEditor"},
	        {	name: "LINKEDEQUIPMENTNAME", caption: "所属设备", editorType: "TextEditor"},
	        {	name: "DEVICECODE", caption: "监测装置", editorType: "TextEditor"},
	        {	name: "MONITORINGTYPE", caption: "监测类型编码", editorType: "TextEditor",visible:false},
	        {	name: "monitoringTypeName", caption: "监测类型", editorType: "TextEditor"},
	        {	name: "ALARMTIME", caption: "告警时间", editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss"},
	        {	name: "ALARMENDTIME", caption: "告警结束时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd HH:mm:ss",readOnly:true},
	        {	name: "CONTINUANCETIME", caption: "持续时间（分钟）", editorType: "TextEditor"},
	        {	name: "ALARMLEVEL", caption: "告警级别", editorType: "TextEditor"},
	        {	name: "ALARMMESSAGE", caption: "告警信息", editorType: "TextEditor"},
	        {	name: "ALARMRULE", caption: "告警规则", editorType: "TextEditor"},
	        {	name: "ALARMSOURCE", caption: "告警来源", editorType: "TextEditor"},
	        {	name: "ALARMCONSTRUE", caption: "告警分析", editorType: "TextEditor"},
	        {	name: "DISPOSALADVICE", caption: "告警建议", editorType: "TextEditor"},
	        {	name: "DISPOSALTIME", caption: "处理时间", editorType: "TextEditor"},
	        {	name: "DISPOSALRESULT", caption: "处理结果", editorType: "TextEditor"},
	        {	name: "TRANSACTOR", caption: "处理人", editorType: "TextEditor"},
	        {	name: "CHECKTIME", caption: "审核时间", editorType: "TextEditor"},
	        {	name: "CHECKEDBY", caption: "审核人", editorType: "TextEditor"},
	        {	name: "ISHANDLED", caption: "是否处理", editorType: "TextEditor"},
	        {	name: "REMARKS", caption: "备注", editorType: "TextEditor"}
		    ],
            entityContainer: formEntityContainer,
            width:600,
            left:-20,
            displayPrimaryKey:false,
            enabled:false
        });
        me.addControl(_form);
    }
    
    /**
     * 获取表单对象
     */
    me.getForm = function(){
		return _form;
    }
	
    /**
     * 获取工具条
     */
    me.getToolBar = function(){
		return _toolBar;
    }
    
	me.endOfClass(arguments)
    return me;
};