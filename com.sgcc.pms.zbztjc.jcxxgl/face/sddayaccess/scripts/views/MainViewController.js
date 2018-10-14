$ns("sddayaccess.views");
$import("sddayaccess.views.MainView");

sddayaccess.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sddayaccess.views.MainView({ controller: me });
        }
        return me.view;
    };
    

    /**
     * 导出
     */
    me._btnexport_onclick= function()
    {
    	
   try {
	   var _dataGrid = me.view.getDataGrid();
       //导出文档的工具类
    var util = new mxpms.utils.FormDocumentUtil(
      {
          //title为导出文档内容的标题，标题可为“”字符串
          title:"",
          //view为页面视图或支持类型的jquery对象
       view:_dataGrid.$grid,
          //fileName为导出的文档名称
       fileName:"输电日接入率"
       });
  util.exportExcel(); 
  var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
	var transactionType = "状态监测-监测信息查询-输电日接入率";		//此处为页面的菜单路径信息
	var result = "操作成功"								//此处为操作结果，二选一
	var userlogg = new userlogger.views.MainViewController();
	userlogg.updateUserLogger(czlx,transactionType,result);
} catch (e) {
	mx.indicate("info","导出失败");
	var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
	var transactionType = "状态监测-监测信息查询-输电日接入率";		//此处为页面的菜单路径信息
	var result = "操作失败"								//此处为操作结果，二选一
	var userlogg = new userlogger.views.MainViewController();
	userlogg.updateUserLogger(czlx,transactionType,result);	
}
    }
    
    return me.endOfClass(arguments);
};