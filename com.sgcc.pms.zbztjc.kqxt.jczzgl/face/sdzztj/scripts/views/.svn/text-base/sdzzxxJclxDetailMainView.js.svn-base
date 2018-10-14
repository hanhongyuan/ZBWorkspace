$ns("sdzztj.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

sdzztj.views.sdzzxxJclxDetailMainView = function()
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
			alias:"SdzzcxtjJclxDetail_searchbox",
			rootGroupId : "402881704230292b0142316493220006",
			PagedControl:me.dataGrid,
			height:95,
			itemNumOfRow:4,
			fields : [ {name : "xtmc",caption : "所属系统",displayCheckBox:true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.kqxt.jczzgl/rest/zbjczzglUtil/dropDownEditor/dicts_ssdw"
			},{name : "ssdw",caption : "所属单位",displayCheckBox:true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.kqxt.jczzgl/rest/zbjczzglUtil/dropDownEditor/dicts_ssdw"
			},
			{	name : "dydj",caption : "电压等级",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.kqxt.jczzgl/rest/zbjczzglUtil/dropDownEditor/dicts_dydj"
			},
			{	name : "jclx",caption : "监测类型",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
				url:"/com.sgcc.pms.zbztjc.kqxt.jczzgl/rest/zbjczzglUtil/dropDownEditor/dicts_jclx"
			},
			{	name : "xlmc",caption : "线路名称",displayCheckBox : false,hint:"---请输入---",displayCloseButton : false,editorType : "TextEditor"},
			{	name : "sfss",caption : "是否实时",editorType : "CheckListEditor",type:"radio",
				items:[
				        {text: "全部", value: "all",checked:true},
				        {text: "是", value: "T"},
				        {text: "否", value: "F"}
				      ]
			},{
				name : "tyrq",
				caption : "投运日期",
				formatString : "yyyy-MM-dd",
				editorType : "DateTimeEditor"
			}]
		});

		_dataGridappendButton();
		me.searchBox.$("#xtmc").css("width","200px");
		me.searchBox.$("#ssdw").css("width","200px");
		me.searchBox.$("#dydj").css("width","200px");
		me.searchBox.$("#jclx").css("width","200px");
		me.searchBox.$("#xlmc").css("width","200px");
		me.searchBox.$("#tyrq").css("width","200px");
		me.searchBox.$("#isHandled").css("width","300px");
		
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

    	
    	var restUrl = "~/rest/zbsdzzcxtjControl/getJclxDetailList";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : sdzztj.mappath(restUrl),
            loadMeta : false,
            iscID : "-1"// iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
        });
        
		me.dataGrid = new mx.datacontrols.DataGrid({
			alias:"sdzzxxDetailDataGrid",
			columns:[
	     				{	name: "LINKEDDEPWS", caption: "编号" , editorType: "TextEditor",width:30,visible:false},
	     				{	name: "LINKEDLINENAME", caption: "线路名称" , editorType: "TextEditor",width:120},
	     				{	name: "DEVICENAME", caption: "装置名称" , editorType: "TextEditor",width:120},
	     				{	name: "DEVICECATEGORY_DISPLAY", caption: "监测类型" , editorType: "TextEditor",width:80},
	     				{	name: "LOOKUP", caption: "监测信息" , editorType: "TextEditor",width:80,
	     					renderCell: function(s,p){
	      		        		var objIdValue = s.values.OBJ_ID;
		   						p.html("<a href=\"javascript: void(0);\" style=\"color:blue;text-decoration:none;text-align:center;cursor:pointer \">查看</a>");
	      		        	}
	     				},
	     				{
							name : "ISRT",
							caption : "是否实时",
							editorType : "TextEditor",
							renderCell : function(p_item, $p_cell) {
									var value = p_item.getValue("ISRT"); // 获取 GENDER 字段的值。
									if("是"==value)
										{
										
										$p_cell.html(value).css({
											
											"color" : "green"
										});
										}
									else 
									{
										$p_cell.html(value).css({
											
											"color" : "red"
										});
										}
									
								}
						},{	name: "XTMC", caption: "所属系统" , editorType: "TextEditor",width:120},
						{	name: "WSDEPMC", caption: "所属单位" , editorType: "TextEditor",width:120},
						{	name: "LINKEDPOLENAME", caption: "杆塔名称" , editorType: "TextEditor",width:120},
	     				{	name: "DEVICEVOLTAGE", caption: "电压等级" , editorType: "TextEditor",width:80},
	     				{	name: "DEVICECATEGORY", caption: "装置类别" , editorType: "TextEditor",width:80},
	     				{	name: "DEVICEMODEL", caption: "装置型号" , editorType: "TextEditor"	,width:80},
	     				{	name: "MANUFACTURER", caption: "生产厂家" , editorType: "TextEditor"	,width:120},
	     				{	name: "RUNDATE", caption: "投运日期", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd HH:mm:ss",width:150}
	     				
	     				],
     	            
	        pageSize : 10,
	        //height:393,
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