$ns("userlogger.views");
$import("userlogger.views.ChartView");
$import("userlogger.utils.LeftUpbarChart");
$import("userlogger.views.MainViewController");

userlogger.views.ChartViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    me.urlLeftUp = "~/rest/userlogger/querytj";
    //var params = { };
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new userlogger.views.ChartView({ controller: me });
        }
        return me.view;
    };

    me._onactivate = function(e)
    {
    	me.loadChartLeftUp();
    };
    
     me.loadChartLeftUp = function() {
    	var searchBox = me.view.getSearchBox();
    	var str = searchBox.getSearchParam();
    	loadChartLeftUp(me.urlLeftUp, { filter: str});
    };
    
    function loadChartLeftUp(url, params) {
        me.leftchartUp = new userlogger.utils.LeftUpbarChart(me.view.chartView.id);
      	me.leftchartUp.loadChart(url, params);
    };
    
    me._query = function(){
    	me.loadChartLeftUp();
    	var czlx = "统计";
		var transactionType = "安全审计页面表格统计分析";
		var result = "操作成功";
		var userloggerView = new userlogger.views.MainViewController();
		userloggerView.updateAuticUserLogger(czlx,transactionType,result);
    }
    
    
    me.endOfClass(arguments);
    return me;
};