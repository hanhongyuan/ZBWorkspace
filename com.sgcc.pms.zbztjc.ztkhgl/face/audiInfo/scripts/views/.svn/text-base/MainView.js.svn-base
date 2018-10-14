$ns("audiInfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");
$import("mxpms.controls.FileUpLoadPanel");
audiInfo.views.MainView = function()
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
		        	baseUrl : audiInfo.mappath("~/rest/khzt/getinfo/"),
        
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
						},  {
							name : "JCLX",
							caption : "监测类型",
							readOnly : true,
							visible : false,
							dataAlign : "center"
						},{
							name : "DEVICECODE",
							caption : "装置编码",
							readOnly : true,
							dataAlign : "center"
						},{
							name : "LINKEDLINENAME",
							caption : "所属线路",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "LINKEDPOLENAME",
							caption : "所属杆塔",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "RUNDATE",
							caption : "投运日期",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "APPLICANT",
							caption : "申请人",
							readOnly : true,
							dataAlign : "center"
						},{
							name : "OLDSTATUS",
							caption : "原运行状态",
							editorType : "DropDownEditor",
							readOnly : true,
							dataAlign : "center"
						}, {
							name : "NEWSTATUE",
							caption : "申请运行状态",
							editorType : "DropDownEditor",
							readOnly : true,
							dataAlign : "center"
						},
						{
							name : "FJ",
							caption : "附件",
							editorType : "CustomEditor",
							//readOnly : true,
							visible : false,
							dataAlign : "center",
							onclick: me.controller.customEditor_click
						},{
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
						},
						{
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