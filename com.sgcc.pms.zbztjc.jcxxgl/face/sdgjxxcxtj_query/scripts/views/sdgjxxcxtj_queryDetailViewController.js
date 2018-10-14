$ns("sdgjxxcxtj_query.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("sdgjxxcxtj_query.views.sdgjxxcxtj_queryDetailView");

sdgjxxcxtj_query.views.sdgjxxcxtj_queryDetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdgjxxcxtj_query.views.sdgjxxcxtj_queryDetailView({controller: me, alias:"sdgjxxcxtjsdgjxxcxtjDetailView"});
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	var permission = new mx.permissions.Permission({iscID:"-1"});
        mx.permissions.PermissionAgent.setPermission(permission, me.view);
    };
    
    me._btnSave_onclick = function()
    {
        me.view.getForm().save();
    };
    
    //点击刷新的方法
    me._btnRefresh_onclick = function()
    {
    	me.view.getForm().load();
    }
    
    return me.endOfClass(arguments);
};