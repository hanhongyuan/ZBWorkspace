$ns("sdmonthzs.views");
$import("sdmonthzs.views.MainView");

sdmonthzs.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdmonthzs.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    /**
     * 导出
     */
    me._btnexport_onclick= function()
    {
    	var savetime = me.view.dataGrid.searchBox.editors.savetime.value;
    	var linkeddepws = me.view.dataGrid.searchBox.editors.linkeddepws.value;
    
	   var filter = new Array();
	 //条件框的条件
	    if(null!=savetime)
	    {
	    	filter.push("savetime=" + savetime);
	    }
	  	if(null!=linkeddepws)
	    {
	    	filter.push("linkeddepws=" + linkeddepws);
	    }
	      
    	var str = "";
 	    for ( var i = 0; i < filter.length; i++) {
 			str += filter[i] + "&";
 		}
  
    	me.view.getDataGrid().setFilter(str.substring(0, str.length - 1));
         var _params = null;        
         var xls = new mxpms.utils.CommUtil();
         _params = me.view.getDataGrid().filter;
         me.view.getDataGrid().filter = "";
         xls.exportToExcel(me.view.getDataGrid(),{
             params : {params : JSON.stringify({filter : _params})},
             filename : "输电月装置总数"
         });
    }
    
    return me.endOfClass(arguments);
};