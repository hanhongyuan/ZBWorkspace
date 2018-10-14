$ns("sdgjxxDetail.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

sdgjxxDetail.views.gjxxDetailMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.searchBox= null;
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	//_initSearchBox();
    	_initDataGrid();
    	
    	// 查询区的隐藏和显示图标的点击事件
		/*var _img = me.searchBox.$("img");
		_img.on("click",function(){
			if(me.searchBox._isHidden){
				me.dataGrid.$e.css("top","-63px");
				me.dataGrid.setHeight(438);
			}
			else{
				me.dataGrid.$e.css("top","-18px");
				me.dataGrid.setHeight(393);
			}
		});*/
    	
        me.on("activate", me.controller._onactivate);
    }
    
    function _initSearchBox(){
    	me.searchBox = new mxpms.datacontrols.AdvancedSearchBox({
			alias:"gjxxcxtjDetail_searchbox",
			rootGroupId : "402881704230292b0142316493220006",
			PagedControl:me.dataGrid,
			height:95,
			itemNumOfRow:3,
			fields : [ {name : "ssdw",caption : "所属单位",hint:"---请选择---",displayCheckBox:true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_ssdw"
			},
			{	name : "dydj",caption : "电压等级",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_dydj"
			},
			{name : "isHandled",caption : "是否处理",editorType : "CheckListEditor",type:"radio",
				items:[
				        {text: "全部", value: "all"},
				        {text: "是", value: "T"},
				        {text: "否", value: "F",checked:true}
				      ]
			},
			{	name : "jclx",caption : "监测类型",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_jclx"
			},
			{	name : "gjjb",caption : "告警级别",hint : "--请选择--",editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_gjjb"
			},
			{name : "gjsj",caption : "告警时间",displayCheckBox: true,formatString:"yyyy-MM-dd",editorType : "DateTimeEditor"}
			]
		});

		_dataGridappendButton();
		
		me.searchBox.$("#ssdw").css("width","200px");
		me.searchBox.$("#dydj").css("width","200px");
		me.searchBox.$("#jclx").css("width","200px");
		me.searchBox.$("#gjjb").css("width","200px");
		me.searchBox.$("#isHandled").css("width","300px");
		me.searchBox.$("#gjsj").css("width","300px");
		
		me.addControl(me.searchBox);
    }
    
    
    /**
     * 给SearchBox添加按钮的方法
     */
    function _dataGridappendButton() {
    	var $searchBox = me.searchBox;
    	
    	$searchBox.$("#btnTd").hide();
    	var $tbody =  $searchBox.$("#btnTr");
    	$tbody.append("<td class='newButtonTd1' colspan=8 style='padding-left:80%;'></td>");
    	var $newButtonTd1 = $searchBox.$(".newButtonTd1");
    	//定义一个按电压等级统计的按钮
    	me.queryBtn = new mxpms.controls.ImageButton({
    		text:"查询",
    		name:"queryBtn",
    		onclick:me.controller._queryBtn_click,
    		width:80,
    		border:"1px solid white"
    		
    	});
    	$newButtonTd1.append(me.queryBtn.$e);
    	
    	//定义一个按监测类型统计的按钮
    	me.exportBtn = new mxpms.controls.ImageButton({
    		text:"导出",
    		name:"exportBtn",
    		onclick:me.controller._export_click,
    		width:80,
    		border:"1px solid white"
    	});
    	me.exportBtn.$e.css("margin-left","10px");
    	$newButtonTd1.append(me.exportBtn.$e);
    }
    
    function _initDataGrid(){

    	
    	var restUrl = "~/rest/sdgjxxcxtj_stat/getDetailList";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : sdgjxxcxtj_stat.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "OBJ_ID"
        });
        
		me.dataGrid = new mx.datacontrols.DataGrid({
			alias:"gjxxDetailgjxxDetailDataGrid",
        	columns:[
     				{	name: "OBJ_ID", caption: "告警编号" , editorType: "TextEditor",width:30,visible:false},
     				{	name: "LINKEDLINENAME", caption: "线路名称" , editorType: "TextEditor",width:120},
     				{	name: "LINKEDPOLENAME", caption: "杆塔名称" , editorType: "TextEditor",width:120},
     				{	name: "TYPENAME", caption: "监测类型" , editorType: "TextEditor",width:120},
     				{	name: "JCXX", caption: "监测信息" , editorType: "TextEditor",width:80,
     					renderCell : function(p_item, $p_cell) {
     						var value = "查看"; // 获取 GENDER 字段的值。 // 获取 GENDER 字段的值。
     	     						var deptws = p_item.getValue("LINKEDDEPWS");
     	     						var devicecode = p_item.getValue("DEVICECODE");
     	     						var monitor = p_item.getValue("MONITORINGTYPE");
     	     						$p_cell.html(value).css({
     	     							"text-decoration" : "underline",
     	     							"color" : "blue"
     	     						}).mouseover(function() {
     	     							$p_cell.css("cursor", "pointer");
     	     						}).on("click", function() {
     	     							$p_cell.css("color", "grey");
     	     							me.controller._btnTest(devicecode,monitor,deptws);
     	     						});
     							}
     				},
     				{	name: "WSDEPMC", caption: "所属单位" , editorType: "TextEditor",width:120},
     				{	name: "DEVICEVOLTAGE", caption: "电压等级" , editorType: "TextEditor",width:80},
     				{	name: "ALARMMESSAGE", caption: "告警消息" , editorType: "TextEditor",width:250},
     				{	name: "ALARMLEVEL", caption: "告警级别" , editorType: "TextEditor"	,width:80},
     				{	name: "ALARMTIME", caption: "告警时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd HH:mm:ss",width:150}
     	            ],
     	            
	        displayPrimaryKey:false,//列表是否显示主键
	        pageSize : 10,
	        entityContainer: gridEntityContainer
		});
		//queryMonitoringInfo_click = me.controller._queryMonitoringInfo_click;
		//me.dataGrid.$e.css("top","-18px");
		me.addControl(me.dataGrid);
    }
    
    me.getSearchBox = function(){
    	return me.searchBox;
    }
    
    me.getDataGrid = function(){
    	return me.dataGrid;
    }
    return me.endOfClass(arguments);
};