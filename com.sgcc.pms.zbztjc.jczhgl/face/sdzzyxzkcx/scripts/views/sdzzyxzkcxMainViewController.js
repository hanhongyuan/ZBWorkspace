$ns("sdzzyxzkcx.views");
$import("sdzzyxzkcx.views.sdzzyxzkcxMainView");

sdzzyxzkcx.views.sdzzyxzkcxMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdzzyxzkcx.views.sdzzyxzkcxMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
     
    };
    
    me._btnCx_onclick = function(){
    	var _seSearchBox = me.view.getSearchBox();
    	var bool = checkdate();
		if(bool){
			var _dataGrid = me.view.getDataGrid();
	    	var params = _seSearchBox.getSearchParam();
	        _dataGrid.setFilter(params);
			_dataGrid.load();
		}
    	
    };
    
  //时间判断，起始时间和终止时间都不能大于当前时间
	function checkdate(){
		var check = me.view.getSearchBox().editors.tyrq.value;
		var date = new Date();
		var year =date.getFullYear();
		var month =date.getMonth()+1;
		var rdate =year +"/"+ month;
		
		if(check!=""&&check!=null){
			if(check[0]!=""&&check[0]!=null ){
				var check0=check[0].replace(/-/g,"/");
				if(Date.parse(check0)>Date.parse(date))
				{
//					me.view.getSearchBox().editors.tyrq.reset();
					mx.indicate("warn", "时间不能大于当前时间!");
					return false;
				}
			}
			return true;
		}else{
			return true;
		}
	}
    
    return me.endOfClass(arguments);
};