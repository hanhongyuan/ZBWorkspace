$ns("audiInfo.views");
$import("audiInfo.views.MainView");

audiInfo.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new audiInfo.views.MainView({ controller: me });
        }
        return me.view;
    };
   
    me.customEditor_click = function()
    {
    	var obj = me.view.form.editors["OBJ_ID"].value;
    	var fileUpLoadPanel = new mxpms.controls.FileUpLoadPanel(
				  {
				    width: "100%", 
				    tableName: "cmst_changestatus",
				    colName: "FJ",
				    //primaryKey: obj,
				    pkVal: obj,
				    type: "grid",
				    height:"100%",
				    pageSize:"18",
				    selectMore : true,
					hideBtns :"add,upload,delete,preview,search"
					
				  });
    	
    	var window = new mx.windows.WindowManager().create({
    		resizable: false
    	});	
    	
    	window.setTitle("申请附件下载");
    	window.setView(fileUpLoadPanel);
		window.showDialog();
    	
    }
    me._save = function()
    {
    	var AUDITSTATUS = me.view.form.editors["AUDITSTATUS"].value;
    	var view = me.view.form.editors["AUDITVIEW"].value;
    	var obj = me.view.form.editors["OBJ_ID"].value;
    	var client = new mx.rpc.RESTClient();
    	var filter = AUDITSTATUS+','+view+','+obj;
    	
    	if(""==view||null==view)
    		{
    		alert("审核意见不能为空");
    		return;
    		}
    	if("1"==AUDITSTATUS)
    		var msg = "当前审核为同意！";
    	else
    		msg ="当前审核为不同意！";
    	if (confirm(msg)==true){
    		var params ={filter: filter};
        	
        	me.restUrl = audiInfo.mappath("~/rest/khzt/save");
        	me.client = new mx.rpc.RESTClient();
    		
    		me.client.post(me.restUrl, {params: JSON.stringify(params)}, function(result){
    			if (result.successful)
    			{
    				  alert("审核成功");
    				  me.view.parent.hide();
    				  //window.close;
    				  var czlx = "修改";												//此处为操作方式：新增/修改/删除 任选其一
    					var transactionType = "状态监测-装置管理-装置运行状态审核";		//此处为页面的菜单路径信息
    					var result = "操作成功"								//此处为操作结果，二选一
    					var userlogg = new userlogger.views.MainViewController();
    					userlogg.updateUserLogger(czlx,transactionType,result);
    			}
    			else
    			{
    			    alert("审核失败");
    			    var czlx = "修改";												//此处为操作方式：新增/修改/删除 任选其一
					var transactionType = "状态监测-装置管理-装置运行状态审核";		//此处为页面的菜单路径信息
					var result = "操作失败"								//此处为操作结果，二选一
					var userlogg = new userlogger.views.MainViewController();
					userlogg.updateUserLogger(czlx,transactionType,result);
    			}
    		});
    	}
    	else
    	{
    		return;
    	}
    	
    	
    	
    	
    	
    	
    }
    
    me._cancle=function()
    {
    	me.view.parent.hide();
    }
    
    return me.endOfClass(arguments);
};