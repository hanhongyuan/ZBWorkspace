$ns("zyxtdevicequery.views");
$import("zyxtdevicequery.views.MainView");

zyxtdevicequery.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new zyxtdevicequery.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    /**
     * 查看 
     * */
    me._btnTest = function()
    {
//    	var win =  mx.windows.WindowManager().create({ title: "新窗体", width: 500, height: 400 });
//    	
//    	var jclxView = jclx.views.MainViewController().getView().getDataGrid();
//    	
//    	win.setView(jclxView);
//    	jclxView.load();
//    	win.showDialog();

    }
    
    return me.endOfClass(arguments);
};