$ns("userlogger.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");

userlogger.views.LimitSetView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.objID = null;
    me.form = null;
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
        var toolBar = new mx.controls.ToolBar({
            width: "100%",
    	    items:[
    	    	{name: "save", text: "保存", imageKey : "save", toolTip: "保存",
    	    	onclick: me.controller._btnSave_onclick}
    	    ]
            });
        me.addControl(toolBar);
    	
        me.form = new mx.datacontrols.DataForm({
            	baseUrl : userlogger.mappath("~/rest/userlogger/queryLimitSetForm"),
            	loadMeta:false,
            	primaryKey : "OBJ_ID",
            	fields: [
    	            {	name: "OBJ_ID", caption: "缺陷类型编码", editorType: "TextEditor",width:100},
    	            {	name: "LIMITSET", caption: "容量上限 MB",editorType: "TextEditor",width:200,nullable: false,
    	            	customValidate:function(p_editor, val){
    		        		if(val!=null){
    		        			if(!me.checkNum(val)){
    		        				alert("审计容量上限只能为数字");
    		        				return;
    		        			}else if(val > 10240 || val <= 0){
    			                	alert("审计容量上限应在0M~10240M之间！");
    			                	return;
    			                }
    		        		}else{
    		        			return;
    		        		}
    		        	}	
    	            },
    	            {	name: "ALARMLIMIT", caption: "超标告警比", enabled:false,editorType: "TextEditor",width:200}
            	]
        });
        me.addControl(me.form);
        me.on("activate", me.controller._onactivate);
    }
    
    me.checkNum = function(val){
    	var reg=/^[1-9]+[0-9]*]*$/; 		//判断字符串是否为数字 ，判断正整数用/^[1-9]+[0-9]*]*$/
    	
    	if(!reg.test(val)){
    	    return false;
    	 }else{
    		 return true;
    	 }
    }
    
    me.getForm = function(){
    	return me.form;
    }
    
    return me.endOfClass(arguments);
};