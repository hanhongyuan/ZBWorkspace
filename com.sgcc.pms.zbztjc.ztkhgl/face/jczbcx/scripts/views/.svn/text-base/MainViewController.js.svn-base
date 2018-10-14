$ns('jczbcx.views');

$import('jczbcx.views.MainView');

jczbcx.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new jczbcx.views.MainView( { controller: me } );
        }
        
        return me.view;
    };
       
    me._ExportBtn_click = function(){
    	
    	 try {
    		 var _dataGrid = me.view.getDataGrid();
    	    	var util = new mxpms.utils.FormDocumentUtil({
    	    		title:"",
    	    		view : _dataGrid.$grid,
    	    		fileName:"检查指标查询"
    	    	});
    	    	 util.exportExcel(); 
    	    	 var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
 				var transactionType = "状态监测-监测报表查询-检查指标查询";		//此处为页面的菜单路径信息
 				var result = "操作成功"								//此处为操作结果，二选一
 				var userlogg = new userlogger.views.MainViewController();
 				userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			mx.indicate("info","导出失败");
			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测报表查询-检查指标查询";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);	
		}
    }
    
    me._onactivate = function()
    {
       
    };
    
    return me.endOfClass(arguments);
};