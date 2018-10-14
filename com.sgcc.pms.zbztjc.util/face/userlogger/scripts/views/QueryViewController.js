$ns("userlogger.views");

$import("userlogger.views.QueryView");
$import("userlogger.views.MainViewController");

userlogger.views.QueryViewController = function()
{
    var me = $extend(mx.views.ViewController);
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new userlogger.views.QueryView( { controller: me } );
        }
        
        return me.view;
    };

    me.Queryinfo = function(){
    	var dataGrid = me.getView().getDataGrid();
    	dataGrid.load();
    	var czlx = "统计";
		var transactionType = "安全审计页面表格统计分析";
		var result = "操作成功";
		var userloggerView = new userlogger.views.MainViewController();
		userloggerView.updateAuticUserLogger(czlx,transactionType,result);
    };
    
    return me.endOfClass(arguments);
};