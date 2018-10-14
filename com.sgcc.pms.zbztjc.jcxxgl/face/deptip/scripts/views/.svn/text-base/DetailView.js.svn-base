$ns("deptip.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


deptip.views.DetailView = function()
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
    	_form.load(me.objID);
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
            alias:"deptipDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "deptipDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/deptip/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : deptip.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "OBJ_ID",
            loadMeta: false
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"deptipDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "OBJ_ID", caption: "对象的唯一标识符", editorType: "TextEditor"},
	        {	name: "DEPTNAME", caption: "网省名称", editorType: "TextEditor", nullable: false },
	        {	name: "DEPTID", caption: "网省ID", editorType: "TextEditor", nullable: false },
	        {	name: "DEPTIP", caption: "网省接口地址", editorType: "TextEditor", nullable: false },
	        {	name: "IPPORT", caption: "IP端口", editorType: "TextEditor", nullable: false },
	        {	name: "BZ", caption: "备注", editorType: "TextEditor" }
		    ],
            entityContainer: formEntityContainer
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