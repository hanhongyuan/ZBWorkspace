$ns("mainSystemssxtInfo.views");

$import("mx.containers.HSplit");

mainSystemssxtInfo.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.sdDataGrid = null;
    me.bdDataGrid = null;
    me.detailDataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initHsplit();
    	_initSbdHsplit();
    	_initSdDataGrid();
    	_initBdDataGrid();
    	_initDetailDataGrid();
    	
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHsplit(){
    	me.hsplit = new mx.containers.HSplit({
    		rows:"50%, 50%",
    		resizable:true,
    		borderThick:1,
    		activePanelIndex:0
    		});
    	me.addControl(me.hsplit);
    }
    
    function _initSbdHsplit(){
    	me.sbdHsplit = new mx.containers.HSplit({
    		rows:"50%, 50%",
    		resizable:true,
    		borderThick:1,
    		activePanelIndex:0
    		});
    	me.hsplit.addControl(me.sbdHsplit,0);
    }
    
    function _initSdDataGrid(){
    	var restUrl = "~/rest/mainSystemssxtInfo/querySdInfo";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : mainSystemssxtInfo.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "OBJ_ID"
        });
        
        me.sdDataGrid = new mx.datacontrols.GroupHeaderGrid({
    		width : "100%",
    		height : "auto",
    		position : "absolute",
    		bottom : "0px",
    		top : "0px",
    		columns: [
	    		{name: "OBJ_ID", caption: "线路编码" ,visible : false},
	    		{ name: "XLMC", caption: "线路名称", dataAlign:"center", editorType: "TextEditor"  },
	    		{ name: "GJZS", caption: "告警总数", dataAlign:"center", editorType: "TextEditor"  },
	    		{ name: "ALLHCNT", caption: "已处理告警总数", dataAlign:"center", editorType: "TextEditor"  },
	    		{ name: "ZS018001", caption: "微气象", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS018002", caption: "图像", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS013001", caption: "导线弧垂", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS013002", caption: "导线温度", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS013003", caption: "微风振动", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS013004", caption: "风偏", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS013005", caption: "覆冰", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS013006", caption: "导线舞动", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS012001", caption: "杆塔倾斜", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS014001", caption: "现场污秽度", dataAlign:"center",editorType: "TextEditor" }
    		],
    		entityContainer : gridEntityContainer,
    		displayCheckBox : false,
    		displayRowNumber : true,
    		allowPaging : false,
    		displayPrimaryKey : false,
    		displayColSplitLine : true,
    		allowEditing : false,
    		rowNumberColWidth : 40,
    		onload:me.controller._initDataGridSD,
    		onitemdoubleclick:me.controller._item_click_sd
    	});
    	me.sbdHsplit.addControl(me.sdDataGrid, 0);
    }
    
    function _initBdDataGrid(){
    	var restUrl = "~/rest/mainSystemssxtInfo/queryBdInfo";
    	
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : mainSystemssxtInfo.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "OBJ_ID"
        });
        
        me.bdDataGrid = new mx.datacontrols.GroupHeaderGrid({
    		/*width : "100%",
    		height : "auto",
    		position : "absolute",*/
    		columns: [
	    		{name: "OBJ_ID", caption: "变电站编码" ,visible : false},
	    		{ name: "BDZMC", caption: "变电站名称", dataAlign:"center", editorType: "TextEditor"  },
	    		{ name: "GJZS", caption: "告警总数", dataAlign:"center", editorType: "TextEditor"  },
	    		{ name: "ALLHCNT", caption: "已处理告警总数", dataAlign:"center", editorType: "TextEditor"  },
	    		{ name: "ZS021001", caption: "变压器局部放电", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS021002", caption: "油中溶解气体", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS021003", caption: "微水", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS021004", caption: "铁芯接地电流", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS021005", caption: "顶层油温", dataAlign:"center",editorType: "TextEditor" },
	    		{ name: "ZS024001", caption: "断路器局部放电", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS024002", caption: "分合闸线圈电流波形", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS024003", caption: "负荷电流波形", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS024004", caption: "SF6气体压力", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS024005", caption: "SF6气体水分", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS024006", caption: "储能电机工作状态", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS022001", caption: "电容型绝缘监测", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS023001", caption: "金属氧化物绝缘监测", dataAlign:"center",editorType: "TextEditor" }
    		],
    		entityContainer : gridEntityContainer,
    		displayCheckBox : false,
    		displayRowNumber : true,
    		allowPaging : false,
    		displayPrimaryKey : false,
    		displayColSplitLine : true,
    		allowEditing : false,
    		rowNumberColWidth : 40,
    		onload:me.controller._initDataGridBD,
    		onitemdoubleclick:me.controller._item_click_bd
    	});
    	me.sbdHsplit.addControl(me.bdDataGrid, 1);
    }
    
    function _initDetailDataGrid(){
    	var restUrl = "~/rest/mainSystemssxtInfo/queryDetailInfo";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : mainSystemssxtInfo.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "OBJ_ID"
        });
        
    	me.detailDataGrid = new mx.datacontrols.DataGrid({
        	columns:[
      		        {	name: "OBJ_ID", caption: "编号" , editorType: "TextEditor",visible : false},
      		        {	name: "LINKEDLINENAME", caption: "线路/变电站名称" , editorType: "TextEditor",width:120},
      		        {	name: "LINKEDPOLENAME", caption: "杆塔/设备名称" , editorType: "TextEditor",width:120},
      		        {	name: "DEVICENAME", caption: "设备名称" , editorType: "TextEditor",width:120},
      		        {	name: "MONITORINGTYPENAME", caption: "监测类型" , editorType: "TextEditor",width:120},
      		        {	name: "ALARMMESSAGE", caption: "告警信息" , editorType: "TextEditor",width:250},
      		        {	name: "ALARMLEVEL", caption: "告警级别" , editorType: "TextEditor",width:80},
      		        {	name: "ALARMTIME", caption: "告警时间" , editorType: "DateTimeEditor",width:170,formatString: "yyyy-MM-dd HH:mm:ss"},
      		        {	name: "DEVICEVOLTAGE", caption: "电压等级" , editorType: "TextEditor",width:80}
    		],
     		displayCheckBox: false,
            displayRowNumber:true,
            rowNumberColWidth:20,
   	        displayPrimaryKey:false,//列表是否显示主键
            llowEditing: false, //列表默认不可编辑
   	        pageSize : 20,
            entityContainer: gridEntityContainer
    	});
    	me.hsplit.addControl(me.detailDataGrid, 1);
    }
    /**
     * 获取输电数据列表
     */
    me.getSdDataGrid = function(){
    	return me.sdDataGrid;
    }
    /**
     * 获取变电数据列表
     */
    me.getBdDataGrid = function(){
    	return me.bdDataGrid;
    }
    /**
     * 获取详细信息的DataGrid
     */
    me.getDetailDataGrid = function(){
    	return me.detailDataGrid;
    }
    
    return me.endOfClass(arguments);
};