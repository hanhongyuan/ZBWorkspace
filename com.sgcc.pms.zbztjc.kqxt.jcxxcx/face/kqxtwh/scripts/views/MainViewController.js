$ns("kqxtwh.views");
$import("kqxtwh.views.MainView");

kqxtwh.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new kqxtwh.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        // TODO: 窗体激活时的逻辑。
	if (me.view != null && typeof(me.view.getDataGrid()) != "undefined")
	{
 	    me.view.getDataGrid().load();
	}	
    };
    
    me._btnSave_onclick = function()
    {
    	var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.getCheckedIDs().length == 0)
        {
             mx.indicate("info", "请勾选待保存记录。");
             return;
        }
        v_dataGrid.save();
    };
    
    me._btnNew_onclick = function()
    {
    	 var v_dataGrid = me.view.getDataGrid();
    	 v_dataGrid.insertItem(0);
    	 me.view.getDataGrid().save();
    };
    
    me._btnRefresh_onclick = function()
    {
    	 var v_dataGrid = me.view.getDataGrid();
    	 v_dataGrid.load();
    };
    
   
    
    return me.endOfClass(arguments);
};