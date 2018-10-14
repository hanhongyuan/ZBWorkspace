$ns('testChart.views');

$import('testChart.views.MainView');
$import('mx.windows.WindowManager');
$import('mx.containers.HtmlContainer');
testChart.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new testChart.views.MainView( { controller: me } );
        }
        
        return me.view;
    };
    
    me._onactivate = function()
    {
       
    };
    
    /**
     * 查看   
     * */
    me._btnTest = function()
    {
    	
		var monit = "monit=013005";
    	var devicecode = "devicecode=09M00000000053909";
    	var parm = monit+"&"+devicecode;
    	var url = "~/com.sgcc.pms.zbztjc.util/openChart/index.jsp?"+parm;
    	
    	var window = new mx.windows.WindowManager().create({
    		title: '组态图查看',
    		resizable: false,
    		width: '800px',
    		height: '500px'
    	});
    	me.htmlContainer = new mx.containers.HtmlContainer({
    		url: url,
    		height: "100%",
    		width: "100%",
    		type: "Iframe"
    	});
    	window.addControl(me.htmlContainer);
    	window.showDialog();

    }
    
    return me.endOfClass(arguments);
};