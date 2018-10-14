$ns("bdvideo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");


bdvideo.views.bdvideoMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
	    _initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/bdvideo/loadbdvideo";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : bdvideo.mappath(restUrl),
            loadMeta : false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "obj_id"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
        	
			searchBox: new mx.datacontrols.DataGridSearchBox({
			
				itemNumOfRow : 3,
				onsearching : _searchParamChanged,
				allowSorting:true,
				fields: [
				{	name: "ssdw", caption: "所属单位", editorType: "DropDownEditor",allowEditing : false, displayCheckBox: true,width : 200, hint : "--请选择--"	},
				{	name: "ssxt", caption: "所属系统", editorType: "DropDownEditor",allowEditing : false, displayCheckBox: true,width : 200, hint : "--请选择--"	},
				{	name: "linkedstation", caption: "变电站名称", editorType: "TextEditor",width : 200, hint : "--请输入--"	},
				{	name: "videostate", caption: "视频状态", editorType: "DropDownEditor",allowEditing : false, displayCheckBox: false,width : 200, hint : "--请选择--"	,
	            	items:[
					        {text: "全部", value: "all"},
					        {text: "在线", value: "online"},
					        {text: "离线", value: "offline"},
					        {text: "无效", value: "invalid"}
					        ]
				        },
	            {	name: "srundate", caption: "投运日期", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd",width : 200, hint : "--请选择--"},
	            {	name: "erundate", caption: "至", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd",width : 200, hint : "--请选择--"}
				]
			}),
			
			columns:[
			{	name: "obj_id", caption: "OBJ_ID" , editorType: "TextEditor"	},
			{	name: "ssdw", caption: "所属单位" , editorType: "TextEditor",width:150,dataAlign:"center"	},     
			{	name: "xtmc", caption: "所属系统" , editorType: "TextEditor"	,width:200,dataAlign:"center"},
	        {	name: "linkedstationname", caption: "变电站名称" , editorType: "TextEditor"	,width:200,dataAlign:"center"},
	        {	name: "devicename", caption: "装置名称" , editorType: "TextEditor",width:250,dataAlign:"center"	},
	        {	name: "videostate", caption: "视频状态" , editorType: "TextEditor"	,width:100,dataAlign:"center" ,
	        	renderCell : function(p_item, $p_cell) {
					var value = p_item.getValue("videostate"); // 获取 GENDER 字段的值。
					if("在线"==value)
						{
						$p_cell.html(value).css({
							
							"color" : "green"
						});
						}
					else if("离线"==value)
					{
						$p_cell.html(value).css({
							
							"color" : "red"
						});
						}
					else{
						$p_cell.html(value).css({
							
							"color" : "black"
						});
					}
					
				}
	        },
	        {	name: "JCXX", caption: "视频查看" , editorType: "TextEditor",width:100,dataAlign:"center",
	        	renderCell : function(p_item, $p_cell) {
				var value = p_item.getValue("JCXX"); // 获取 GENDER 字段的值。
				var videocode = p_item.values.videocode;
				var name = p_item.getValue("devicename");
				$p_cell.html("<a href=\"javascript: void(0);\" style=\"color:blue;text-decoration:none;cursor:pointer \" onclick=\"btnLookOne('"+videocode+"_"+name+"')\">"+value+"</a>");
			}	},
	        {	name: "rundate", caption: "投运日期" , editorType: "TextEditor",width:150,dataAlign:"center"}
	        
            ],
            allowSorting:true,
            width:"100%",
            rowNumberColWidth : 40,
            displayRowNumber: true,
            displayCheckBox: false,
	        displayPrimaryKey: false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            entityContainer: gridEntityContainer,
            enableCellTip : true
            
            
        });

        me.addControl(_dataGrid);
        _dataGrid.searchBox.editors["videostate"].setValue("all", true);
        btnLookOne = me.controller._btnLookOne;
        
    }


	//时间判断，起始时间不能大于终止时间
	function _searchParamChanged()
	{
		var stime = _dataGrid.searchBox.editors.srundate.value;
		var etime = _dataGrid.searchBox.editors.erundate.value;
		
		if(stime!=""&&etime!=""&&stime!=null&&etime!=null)
		{
			stime=stime.replace(/-/g,"/");
			etime=etime.replace(/-/g,"/");
			if(Date.parse(stime)>Date.parse(etime))
			{
				alert("起始时间不能大于终止时间！");
			}

		}	
	}
    /**
     * 获取DataGrid网格列表对象
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
	me.endOfClass(arguments)
    return me;
};