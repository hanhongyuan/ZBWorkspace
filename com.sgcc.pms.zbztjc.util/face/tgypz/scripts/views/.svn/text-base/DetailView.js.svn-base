$ns("tgypz.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


tgypz.views.DetailView = function()
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
            alias:"tgypzDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "tgypzDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/tgypz/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : tgypz.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "xh",
            loadMeta: false // 不加载元数据
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"tgypzDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "sblx", caption: "设备类型", editorType: "DropDownEditor", onchanged: me.controller.selectSbmc, nullable: false },
		    {	name: "yxbh", caption: "设备名称", editorType: "DropDownEditor", onchanged: me.controller.selectJclx, nullable: false },
	        {	name: "monitoringtype", caption: "监测类型", editorType: "DropDownEditor", nullable: false },
	        {	name: "sblxmc", caption: "设备类型名称", editorType: "TextEditor", visible: false },
	        {	name: "scmc", caption: "设备名", editorType: "TextEditor", visible: false },
	        {	name: "typename", caption: "监测类型名称", editorType: "TextEditor", visible: false },
	        {	name: "linkedstation", caption: "变电站ID", editorType: "TextEditor", visible: false },
	        {	name: "xh", caption: "型号", editorType: "TextEditor" }
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