$ns("mainSystemgjxxcxtj_query.views");


mainSystemgjxxcxtj_query.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initSearchBox();
        _initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initSearchBox(){
    	me._searchBox = new mxpms.datacontrols.AdvancedSearchBox({
    				alias:"mainSystemgjxxcxtj_querySearchBox",
        			rootGroupId : "402881704230292b0142316493220006",
        			PagedControl:me._dataGrid,
        			height:"15%",
        			itemNumOfRow:4,
        			fields : [ {name : "ssxt",caption : "所属系统",hint:"--未指定--",displayCheckBox:true,editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_ssxt_mainSystem"
        			},
        			{	name : "ssdw",caption : "所属单位",hint : "--未指定--",displayCheckBox: true,editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_ssdw_mainSystem"
        			},
        			{	name : "dydj",caption : "电压等级",hint : "--未指定--",displayCheckBox: true,editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_dydj"
        			},
        			{	name : "jclx",caption : "监测类型",hint : "--未指定--",displayCheckBox: true,editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_jclx_mainSystem"
        			},
        			{	name : "gjjb",caption : "告警级别",displayCheckBox:true,hint : "--未指定--",editorType : "DropDownEditor",
        				url:"com.sgcc.pms.zbztjc.kqxt.jcgjgl/rest/jcgjglUtil/dropDownEditor/dicts_gjjb"
        			},
        			{	name : "xlmc",caption : "线路名称",hint : "--请输入名称--",editorType : "TextEditor"
        			},
        			{	name : "bdz",caption : "变电站名称",hint : "--请输入名称--",editorType : "TextEditor"
        			},
        			{name : "isHandled",caption : "是否处理",editorType : "CheckListEditor",type:"radio",
        				items:[
        				        {text: "全部", value: "all",checked:true},
        				        {text: "是", value: "T"},
        				        {text: "否", value: "F"}
        				      ]
        			},
        			{name : "gjsj",caption : "告警时间",displayCheckBox: true,formatString:"yyyy-MM-dd",editorType : "DateTimeEditor"}
        			]
        		});
    	
    			_dataGridappendButton();
        		
        		me.addControl(me._searchBox);
    	}
    
    function _dataGridappendButton() {
    	var $searchBox = me._searchBox;
    	
    	$searchBox.$("#btnTd").hide();
    	var $tbody =  $searchBox.$("#btnTr");
    	$tbody.append("<td class='newButtonTd1' colspan=8 style='padding-left:85%; '></td>");
    	var $newButtonTd1 = $searchBox.$(".newButtonTd1");
    	//定义一个查询按钮
    	me.queryBtn = new mxpms.controls.ImageButton({
    		text:"查询",
    		name:"query",
    		onclick:me.controller._btnQuery_click,
    		width:80,
    		border:"1px solid white"
    		
    	});
    	$newButtonTd1.append(me.queryBtn.$e);
    	//定义一个导出按钮
    	me.exportBtn = new mxpms.controls.ImageButton({
    		text:"导出",
    		name:"queryDetail",
    		onclick:me.controller._btnExport_click,
    		width:80,
    		border:"1px solid white"
    	});
    	
    	$newButtonTd1.append(me.exportBtn.$e);
    }
    
    function _initDataGrid()
    {
    	var restUrl = "~/rest/mainSystemgjxxcxtj_query/query";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : mainSystemgjxxcxtj_query.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "OBJ_ID"
        });
        
        me._dataGrid = new mx.datacontrols.DataGrid({
        	alias:"mainSystemgjxxcxtj_querymainSystemgjxxcxtj_queryDataGrid",
        	columns:[
        				{	name: "OBJ_ID", caption: "告警编号" , editorType: "TextEditor",width:30,visible:false},
        				{	name: "WSDEPMC", caption: "所属单位" , editorType: "TextEditor",width:150},
        				{	name: "XTMC", caption: "所属系统" , editorType: "TextEditor",width:150},
        				{	name: "LINKEDSTATIONNAME", caption: "变电站名称/线路名称" , editorType: "TextEditor",width:120},
        				{	name: "LINKEDEQUIPMENTNAME", caption: "设备名称/杆塔名称" , editorType: "TextEditor",width:120},
        				{	name: "DEVICEVOLTAGE", caption: "电压等级" , editorType: "TextEditor",width:80},
        				{	name: "DEVICENAME", caption: "装置名称" , editorType: "TextEditor",width:120},
        				{	name: "TYPENAME", caption: "监测类型" , editorType: "TextEditor",width:150},
        				{	name: "ALARMTIME", caption: "告警时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd HH:mm:ss",width:150},
        				{	name: "ALARMLEVEL", caption: "告警级别" , editorType: "TextEditor"	,width:80},
        				{	name: "CONTINUANCETIME", caption: "告警持续时间/分钟" , editorType: "TextEditor"},
        				{	name: "ishandledName", caption: "是否处理" , editorType: "TextEditor"	,width:80,
        					renderCell: function(s,p){
        						var ishandledName = s.values.ishandledName;
        						if("否" == ishandledName){
        							p.html("<a href=\"javascript: void(0);\" style=\"color:red;text-decoration:none\">"+ishandledName+"</a>"); 
        						}else{
        							p.html("<a href=\"javascript: void(0);\" style=\"color:green;text-decoration:none\">"+ishandledName+"</a>"); 
        						}
        				     }
        				},
        				{	name: "ALARMMESSAGE", caption: "告警消息" , editorType: "TextEditor",width:250}
        	            ],
        	            
	        displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
	        pageSize : 10,
	        height:"85%",
	        entityContainer: gridEntityContainer
        });
        //me._dataGrid.setSearchBox(me._searchBox);
        //me._dataGrid.$("#grid").css("margin-top","80px");
        me.addControl(me._dataGrid);
        me._dataGrid.load();
    }
    
    /**
     * 返回DataGrid
     */
    me.getDataGrid = function(){
    	return me._dataGrid;
    }
    
    /**
     * 返回SearchBox
     */
    me.getSearchBox = function(){
    	return me._searchBox;
    }
    
    return me.endOfClass(arguments);
};