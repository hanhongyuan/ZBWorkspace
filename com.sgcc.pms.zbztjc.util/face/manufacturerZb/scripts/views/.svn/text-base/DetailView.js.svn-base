$ns("manufacturerZb.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


manufacturerZb.views.DetailView = function()
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
            alias:"manufacturerZbDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "manufacturerZbDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cmstmanufacturerzb/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : manufacturerZb.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "objId",
            loadMeta: false
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"manufacturerZbDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "objId", caption: "对象的唯一标识符", editorType: "TextEditor", visible: false },
	        {	name: "name", caption: "厂家名称", editorType: "TextEditor", nullable: false },
	        {	name: "contact", caption: "联系方式", editorType: "TextEditor" },
	        {	name: "address", caption: "厂址", editorType: "TextEditor" },
	        {	name: "displayName", caption: "厂家显示名称", editorType: "TextEditor" },
	        {	name: "everName", caption: "厂家曾用名", editorType: "TextEditor" },
	        {	name: "creatTime", caption: "创建时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd", nullable: false },
	        {	name: "lastmodifyTime", caption: "最后一次修改时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd", nullable: false },
	        {	name: "status", caption: "厂商状态是否可用", editorType: "DropDownEditor", nullable: false },
	        {	name: "creatProvince", caption: "提交网省", editorType: "TextEditor", nullable: false },
		    {	name: "code", caption: "厂家编码", editorType: "TextEditor", nullable: false }
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