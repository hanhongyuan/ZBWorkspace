$ns("khztInfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");
khztInfo.views.MainView = function()
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
    	me.toolBar = new mx.controls.ToolBar(
    		{
    			items :[ 
    		         {name : "save",toolTip : "确定",text : "确定",onclick : me.controller._save},
    		         {name : "cancle",toolTip : "取消",text : "取消",onclick : me.controller._cancle}
    				]
    		}
    			);
    	 me.addControl(me.toolBar);
	me.form = new mx.datacontrols.DataForm({
		        	baseUrl : khztInfo.mappath("~/rest/khztControl/getinfo/"),
        
        	primaryKey : "OBJ_ID",
 
        	fields: [
						{
							name : "OBJ_ID",
							caption : "唯一标识",
							readOnly : true,
							dataAlign : "center"
						},
						{
							name : "PROVINCE_NAME",
							caption : "所属单位",
							readOnly : true,
							dataAlign : "center"
						},
						 {
							name : "LINKEDDEPTNAME",
							caption : "所属地市",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "DEVICENAME",
							caption : "装置名称",
							readOnly : true,
							dataAlign : "center"
						},{
							name : "LINKEDDEVICE",
							caption : "装置编码",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "TYPENAME",
							caption : "监测类型",
							readOnly : true,
							dataAlign : "center"
						},{
							name : "MONITOREDCOMPONENT",
							caption : "被监测单元",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "COLLECTIONPERIOD",
							caption : "采集周期",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "APPLICANT",
							caption : "申请人",
							readOnly : true,
							dataAlign : "center"
						},{
							name : "OLDKHSTATUS",
							caption : "原考核状态",
							editorType : "DropDownEditor",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "NEWKHSTATUS",
							caption : "申请考核状态",
							editorType : "DropDownEditor",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "APPLYTIME",
							caption : "申请时间",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "AUDITSTATUS",
							caption : "是否同意申请",
							nullable : false,
							editorType : "DropDownEditor",
							dataAlign : "center"
						}, {
							name : "AUDITTIME",
							caption : "审核时间",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "CHANGECAUSE",
							readOnly : true,
							caption : "变更原因"
						}, {
							name : "AUDITVIEW",
							caption : "审核意见",
							nullable : false,
							textMode : "multiline"
						}
        	],
        	maxCols : 4
        });
        
        me.addControl(me.form);
       
    }
    
    
    
    return me.endOfClass(arguments);
};