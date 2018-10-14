$ns("tgypz.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("mx.rpc.RESTClient");
$import("tgypz.views.DetailView");

tgypz.views.DetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.data = {};
	var client = new mx.rpc.RESTClient();
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new tgypz.views.DetailView({controller: me, alias:"tgypzDetailView"});
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        // iscID 是界面的统一权限功能编码，默认值为 "-1" ，表示不应用权限设置。
    	var permission = new mx.permissions.Permission({iscID:"-1"});
        // 根据“统一权限”设置组件的可见和只读等属性
    	// me.view 是当前页面的view页面，可根据需要传入其他需要权限控制页面元素
        mx.permissions.PermissionAgent.setPermission(permission, me.view);
    };
    
    me.selectSbmc = function(e) {
    	// e.newValue 设备类型
    	me.data.sblx = e.newValue;
    	var dataForm = me.view.getForm();
    	
    	var linkedStation = me.data.linkedStation ?  me.data.linkedStation : dataForm.getEditor("linkedstation").value;
    	var params = { filter: linkedStation +","+ e.newValue };
    	var result = client.getSync(tgypz.mappath("~/rest/tgypz/selectSbmc"), {params: JSON.stringify(params)});
    	
    	var sbmc = result.resultValue.dicts[0];
    	var jclxEditor = dataForm.getEditor("monitoringtype");
    	var yxbhEditor = dataForm.getEditor("yxbh");
    	yxbhEditor.setValue("");
    	jclxEditor.setValue("");
    	jclxEditor.clearItems();
    	yxbhEditor.clearItems();
    	yxbhEditor.appendItems(sbmc.values);
    };
    
    me.selectJclx = function(e) {
    	// e.newValue 设备名称
    	var linkedStation = me.data.linkedStation ?  me.data.linkedStation : me.view.getForm().getEditor("linkedstation").value;
    	var params = { filter: linkedStation +","+ me.data.sblx  +","+ e.newValue }; //
    	var result = client.getSync(tgypz.mappath("~/rest/tgypz/selectJclx"), {params: JSON.stringify(params)});
    	
    	var jclx = result.resultValue.dicts[0];
    	var jclxEditor = me.view.getForm().getEditor("monitoringtype");
    	jclxEditor.clearItems();
    	jclxEditor.appendItems(jclx.values);
    };
    
    me._btnSave_onclick = function(e)
    {
	    var dataForm = me.view.getForm();

	    // 不加true 保存的时候不会传到后台
	    // 只有在下拉框选完 点击保存时才能获取到值
	    dataForm.editors["sblxmc"].setValue(dataForm.editors["sblx"].text, true);
	    dataForm.editors["scmc"].setValue(dataForm.editors["yxbh"].text, true);
	    dataForm.editors["typename"].setValue(dataForm.editors["monitoringtype"].text, true);
	    
	    // 新建才需要为变电站ID和作为主键的型号赋值
	    if(me.view.objID.contain("New: ")) {
	    	dataForm.editors["linkedstation"].setValue(me.data.linkedStation, true);
	    	dataForm.editors["xh"].setValue(me.data.xh, true); //主键必须作为最后一个赋值 不然影响其他列
	    }
	    dataForm.save();
    };
    
    return me.endOfClass(arguments);
};