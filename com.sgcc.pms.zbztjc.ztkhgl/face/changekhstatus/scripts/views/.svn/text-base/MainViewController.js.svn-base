$ns('changekhstatus.views');

$import('changekhstatus.views.MainView');

changekhstatus.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    
    var _detailView = null;
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new changekhstatus.views.MainView( { controller: me } );
        }
        
        return me.view;
    };
    
    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new changekhstatus.views.DetailViewController();
            _detailView = mvc.getView();
        }
    	return _detailView;
    }
       
    me._onactivate = function()
    {
       
    };
    
    
    me._btnAudi = function(OBJ_ID,value)
    {
    	var mvc = new khztInfo.views.MainViewController();
    	var detailview = mvc.getView();
    	var window = new mx.windows.WindowManager().create({
	    		resizable: false
	    	});	
    	detailview.form.load(OBJ_ID,function(e){
			
			if("0"!=value)
				{
				detailview.form.setFieldReadOnly("AUDITSTATUS",true);
				detailview.form.setFieldReadOnly("AUDITVIEW",true);
				detailview.toolBar.clearItems();
				}
		});
		window.setWidth("900");
    	window.setHeight("600");
    	window.setTitle("输电逻辑装置考核状态审核详情");
		window.setView(detailview);
		window.showDialog(function(mvc)
				{
			me.view.dataGrid.load();
				});
    	
    };
    
    me._export = function(){
    	
    	
     	try {
     		var _dataGrid = me.view.getDataGrid();
            //导出文档的工具类
    		var util = new mxpms.utils.FormDocumentUtil({
               //title为导出文档内容的标题，标题可为“”字符串
               title:"",
               //view为页面视图或支持类型的jquery对象
            view:_dataGrid.$grid,
               //fileName为导出的文档名称
            fileName:"装置考核状态数据信息"
            });
         	util.exportExcel();
         	var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测装置管理-装置考核状态数据信息";		//此处为页面的菜单路径信息
			var result = "操作成功"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			mx.indicate("info","导出失败");
			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测装置管理-装置考核状态数据信息";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);	
		}
    }
    
    return me.endOfClass(arguments);
};