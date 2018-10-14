$ns("bdgjxxcxtj_query.views");

bdgjxxcxtj_query.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.objID = null;
    me._dataGrid = null;
    me._searchBox = null;
    me._detailWin = null;
    me._ztjcSynPmsWin = null;

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
        _initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initSearchBox(){
    	me._searchBox = new mxpms.datacontrols.AdvancedSearchBox({
    				alias:"bdgjxxcxtj_searchbox",
        			rootGroupId : "402881704230292b0142316493220006",
        			PagedControl:me._dataGrid,
        			height:"12%",
        			itemNumOfRow:4,
        			fields : [ {name : "ssdw",caption : "所属单位",hint:"---请选择---",displayCheckBox:true,editorType : "DropDownEditor",
        				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_ssdw"
        			},
        			{	name : "dydj",caption : "电压等级",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
        				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_dydj"
        			},
        			{	name : "jclx",caption : "监测类型",hint : "--请选择--",displayCheckBox: true,editorType : "DropDownEditor",
        				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_jclxBd"
        			},
        			{	name : "bdz",caption : "变电站名称",hint : "--请选择--",onchanged:_nameChanged,editorType : "TextEditor"
        			},
        			{	name : "gjjb",caption : "告警级别",displayCheckBox: true,hint : "--请选择--",editorType : "DropDownEditor",
        				url:"/com.sgcc.pms.zbztjc.jcxxgl/rest/jcgjcxUtil/dropDownEditor/dicts_gjjb"
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
    			me._searchBox.editors.ssdw.setWidth("180px");
        		me._searchBox.editors.dydj.setWidth("180px");
        		me._searchBox.editors.jclx.setWidth("180px");
        		me._searchBox.editors.bdz.setWidth("180px");
        		me._searchBox.editors.gjjb.setWidth("180px");
        		me._searchBox.editors.isHandled.setWidth("180px");
        		me._searchBox.editors.gjsj.setWidth("180px");
        		
        		me.addControl(me._searchBox);
    	}
    
	    function _dataGridappendButton() {
	    	var $searchBox = me._searchBox;
	    	
	    	$searchBox.$("#btnTd").hide();
	    	var $tbody =  $searchBox.$("#btnTr");
	    	$tbody.append("<td class='newButtonTd1' colspan=8 style='padding-left:75%;margin-left:10px; '></td>");
	    	var $newButtonTd1 = $searchBox.$(".newButtonTd1");
	    	//定义一个查询按钮
	    	me.queryBtn = new mxpms.controls.ImageButton({
	    		text:"查询",
	    		name:"query",
	    		onclick:me.controller._btnQuery_click,
	    		width:80,
	    		border:"2px solid white"
	    	});
	    	
	    	$newButtonTd1.append(me.queryBtn.$e);
	    	//定义一个导出按钮
	    	me.exportBtn = new mxpms.controls.ImageButton({
	    		text:"导出",
	    		name:"queryDetail",
	    		onclick:me.controller._btnExport_click,
	    		width:80,
	    		border:"2px solid white"
	    	});
	    	$newButtonTd1.append(me.exportBtn.$e);
	    	
	    	//定义一个同步PMS数据的按钮
	    	me.synPmsBtn = new mxpms.controls.ImageButton({
	    		text:"同步PMS按钮",
	    		name:"synPmsBtn",
	    		onclick:me.controller._synPmsBtn_click,
	    		width:150,
	    		border:"2px solid white"
	    	});
	    	
	    	$newButtonTd1.append(me.synPmsBtn.$e);
	    	
	    	$searchBox.$(".newButtonTd1")[0].children[0].style.marginRight = "5px";
    		$searchBox.$(".newButtonTd1")[0].children[1].style.marginRight = "5px";
	    }
	    
	    function _initDataGrid()
	    {
	    	var restUrl = "~/rest/bdgjxxcxtj_query/query";
	        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
	            baseUrl : bdgjxxcxtj_query.mappath(restUrl),
	            loadMeta : false,
	            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
	            primaryKey: "OBJ_ID"
	        });
	        
	        me._dataGrid = new mx.datacontrols.DataGrid({
	        	alias:"bdgjxxcxtj_querybdgjxxcxtj_queryDataGrid",
	        	columns:[
	        				{	name: "OBJ_ID", caption: "告警编号" , editorType: "TextEditor",width:30,visible:false},
	        				{	name: "WSDEPMC", caption: "所属单位" , editorType: "TextEditor",width:150},
	        				{	name: "LINKEDSTATIONNAME", caption: "变电站名称" , editorType: "TextEditor",width:120},
	        				{	name: "LINKEDEQUIPMENTNAME", caption: "设备名称" , editorType: "TextEditor",width:120},
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
		        pageSize : 20,
		        height:"88%",
		        entityContainer: gridEntityContainer
	        });
	        //me._dataGrid.setSearchBox(me._searchBox);
	        //me._dataGrid.$("#grid").css("margin-top","80px");
	        me.addControl(me._dataGrid);
	        me._dataGrid.load();
	    }
	    
	    /**
	     * 初始化表单视图窗口对象
	     */
	    function _initDetailWindow(){
	    	me._detailWin = bdgjxxcxtj_query.context.windowManager.create({
				reusable: true,//是否复用
				width:640,
				height:480,
				title:"表单维护"
			});
			
			me._ztjcSynPmsWin = bdgjxxcxtj_query.context.windowManager.create({
				reusable: true,//是否复用
				width:640,
				height:480,
				title:"同步PMS数据"
			});
			
	    }
	    
	    var pattern1 = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]+$");
	    function _nameChanged()
		{
			var bdz = me._searchBox.editors.bdz.value;
			
			if(bdz!=null&&bdz!="")
			{
				if(bdz.length>50|| !pattern1.test(bdz)){
					me._searchBox.editors.bdz.reset();
					mx.indicate("warn", "变电站名称长度不能超50且不能为特殊字符!");
				}
					
			}	
		}
	    me.getDetailWindow = function(){
	    	return me._detailWin;
	    }
	    
	    me.getZtjcSynPmsWindow = function(){
	    	return me._ztjcSynPmsWin;
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