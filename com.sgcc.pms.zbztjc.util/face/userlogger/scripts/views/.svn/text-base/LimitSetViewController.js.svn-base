$ns("userlogger.views");
$import("userlogger.views.LimitSetView");
$import("userlogger.views.MainViewController");

userlogger.views.LimitSetViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new userlogger.views.LimitSetView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        // TODO: 窗体激活时的逻辑。
	if (me.view != null && typeof(me.view.form) != "undefined")
	{
 	    me.view.form.load(me.view.objID);
	}	
    };
    
    me._btnSave_onclick = function()
    {
    	debugger;
    	var objId = me.view.getForm().editors.OBJ_ID.value;
    	var limitSet = me.view.getForm().editors.LIMITSET.value;
    	if(limitSet != null){
			if(!me.view.checkNum(limitSet)){
				alert("审计容量上限只能为数字");
				return;
			}else if(limitSet > 10240 || limitSet <= 0){
            	alert("审计容量上限应在0M~10240M之间！");
            	return;
            }
		}else{
			alert("审计容量上限不能为空！");
			return;
		}
    	var client = new mx.rpc.RESTClient();	//初始化RESTClient
		var restUrl = "~/rest/userlogger/updateLimitSet";				//要执行的Controller方法
		var params = {};
		params.items = new Array();
		params.items.push({
			"objId":objId,
			"limitSet":limitSet
		});
		try{
			client.send(userlogger.mappath(restUrl), "POST", JSON.stringify(params), false, function(p_context) {
				if(p_context.successful && p_context.resultValue.items["0"] == true){
					mx.indicate("info","更新审计上限成功");
					var czlx = "修改";
					var transactionType = "状态监测-系统管理-状态监测审计";
					var result = "操作成功";
					var userloggerView = new userlogger.views.MainViewController();
					userloggerView.updateAuticUserLogger(czlx,transactionType,result);
					me.view.parent.hide();
				}else{
					mx.indicate("info","更新审计上限失败");
					me.view.parent.hide();
				}
			});
		}catch(e){
			return;		
		}
    };
    
    return me.endOfClass(arguments);
};