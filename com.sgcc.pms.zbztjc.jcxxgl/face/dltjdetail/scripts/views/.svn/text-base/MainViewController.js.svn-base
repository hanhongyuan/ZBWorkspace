$ns("dltjdetail.views");
$import("dltjdetail.views.MainView");

dltjdetail.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dltjdetail.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        // TODO: 窗体激活时的逻辑。
	if (me.view != null && typeof(me.view.dataGrid) != "undefined")
	{
 	    me.view.dataGrid.load();
	}	
    };
    
    
    me._btnNew_onclick = function()
    {
        me.view.dataGrid.appendItem();
    };
    
    me._btnDelete_onclick = function()
    {
        me.view.dataGrid.removeItems(me.view.dataGrid.getCheckedIDs());
    };
    
    me._btnSave_onclick = function()
    {
        me.view.dataGrid.save();
    };
    
    var _detailView = null;
    var _win = null;
    me._btnEdit_onclick = function()
    {        
        if (me.view.dataGrid.selection == null)
        {
            return;
        }
        
        // TODO: 弹出表单视图
        if (_detailView == null)
        {
                            }

        if ($notEmpty(me.view.dataGrid) && $notEmpty(me.view.dataGrid.selection))
        {
            _detailView.objID = me.view.dataGrid.selection.id;
	    _detailView.load();
        }
        
        if (_win == null)
        {
            _win = me.getContext().windowManager.createFromView(_detailView);
        }
        _win.showDialog();
    };
    
    return me.endOfClass(arguments);
};