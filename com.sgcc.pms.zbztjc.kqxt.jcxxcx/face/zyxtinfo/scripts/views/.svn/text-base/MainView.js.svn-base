$ns("zyxtinfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
zyxtinfo.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    me.dataGridSD = null;
    me.dataGridBD = null;
    me.dataGridcx = null;
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initHsplit();
        _initHsplit2();
        _initControlsSD();
        _initControlsBD();
        _initControlscx();
    };
    
     //33 66%分割
    function _initHsplit()
    {
    	me.hSplit = new mx.containers.HSplit({
    		 rows:"30%, 70%",
    		 resizable:false
    	});
    	me.addControl(me.hSplit);
    }
    function _initHsplit2()
    {
    	me.hSplit2 = new mx.containers.HSplit({
   		 rows:"40%, 60%",
   		 resizable:false
   	});
   	me.hSplit.addControl(me.hSplit2,1);
    }
    
    
    
    function _initControlsSD()
    {
    	var restUrl = "~/rest/zyxtdevice/loadxtsd";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : zyxtinfo.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "XLID"
		});
		
		me.dataGridSD = new mx.datacontrols.GroupHeaderGrid({
    		width : "100%",
    		height : "auto",
    		position : "absolute",
    		bottom : "0px",
    		top : "0px",
    		columns: [
    		{name: "XLID", caption: "XLID" ,visible : false},
    		{ name: "XLMC", caption: "线路名称", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZZZS", caption: "装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "SSJRZZS", caption: "实时接入装置数", dataAlign:"center", editorType: "TextEditor"  },
    		{
    		name: "group1",
    		caption: "气象环境监测类",
    		columns: [
    		{ name: "ZS018001", caption: "微气象", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "ZS018002", caption: "图像", dataAlign:"center",editorType: "TextEditor" }
    		]
    		},
    		{
        		name: "group2",
        		caption: "导线监测类",
        		columns: [
        		{ name: "ZS013001", caption: "导线弧垂", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013002", caption: "导线温度", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013003", caption: "微风振动", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013004", caption: "风偏", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013005", caption: "覆冰", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZS013006", caption: "导线舞动", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group3",
        		caption: "杆塔监测类",
        		columns: [
        		{ name: "ZS012001", caption: "杆塔倾斜", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group4",
        		caption: "绝缘子监测类",
        		columns: [
        		{ name: "ZS014001", caption: "现场污秽度", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}],
    		entityContainer : gridEntityContainer,
    		displayCheckBox : false,
    		displayRowNumber : true,
    		allowPaging : false,
    		displayPrimaryKey : false,
    		displayColSplitLine : true,
    		allowEditing : false,
    		rowNumberColWidth : 40,
    		onload:me.controller._initDataGridSD
    		});
		//me.addControl(me.dataGridSD);
		me.hSplit.addControl(me.dataGridSD,0);
    }
    
    function _initControlsBD()
    {
    	var restUrl = "~/rest/zyxtdevice/loadxtbd";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : zyxtinfo.mappath(restUrl),
			loadMeta : false,
			iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为 "-1" ，表示不应用权限设置。
			primaryKey : "BDZID"
		});
		
		me.dataGridBD = new mx.datacontrols.GroupHeaderGrid({
    		width : "100%",
    		height : "auto",
    		position : "absolute",
    		bottom : "0px",
    		top : "0px",
    		columns: [
    		{name: "BDZID", caption: "BDZID" ,visible : false},
    		{ name: "BDZMC", caption: "变电站名称", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "ZZZS", caption: "装置总数", dataAlign:"center", editorType: "TextEditor"  },
    		{ name: "SSJRZZS", caption: "实时接入装置数", dataAlign:"center", editorType: "TextEditor"  },
    		{
    		name: "group1",
    		caption: "变压器/电抗器类",
    		columns: [
    		{ name: "ZZZS021001", caption: "变压器局部放电", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "ZZZS021002", caption: "油中溶解气体", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "ZZZS021003", caption: "微水", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "ZZZS021004", caption: "铁芯接地电流", dataAlign:"center",editorType: "TextEditor" },
    		{ name: "ZZZS021005", caption: "顶层油温", dataAlign:"center",editorType: "TextEditor" }
    		]
    		},
    		{
        		name: "group2",
        		caption: "断路器/GIS类",
        		columns: [
        		{ name: "ZZZS024001", caption: "断路器局部放电", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZZZS024002", caption: "分合闸线圈电流波形", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZZZS024003", caption: "负荷电流波形", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZZZS024004", caption: "SF6气体压力", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZZZS024005", caption: "SF6气体水分", dataAlign:"center",editorType: "TextEditor" },
        		{ name: "ZZZS024006", caption: "储能电机工作状态", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group3",
        		caption: "电容型设备类",
        		columns: [
        		{ name: "ZZZS022001", caption: "绝缘监测", dataAlign:"center",editorType: "TextEditor" }
        		]
        	},
        	{
        		name: "group4",
        		caption: "金属氧化物避雷器类",
        		columns: [
        		{ name: "ZZZS023001", caption: "绝缘监测", dataAlign:"center",editorType: "TextEditor" }
        		]
        	}],
    		entityContainer : gridEntityContainer,
    		displayCheckBox : false,
    		displayRowNumber : true,
    		allowPaging : false,
    		displayPrimaryKey : false,
    		displayColSplitLine : true,
    		allowEditing : false,
    		rowNumberColWidth : 40,
    		onload:me.controller._initDataGridBD
    		});
		//me.addControl(me.dataGridBD);
		me.hSplit2.addControl(me.dataGridBD,0);
    }
    
    function _initControlscx()
    {
    	var restUrl = "~/rest/zyxtdevice/loadcx";
		/* 初始化 EntityContainer */
		var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			baseUrl : zyxtinfo.mappath(restUrl),
			loadMeta : false,
			iscID : "-1"
		});

		/* 初始化 DataGrid */
		me.dataGridcx = new mx.datacontrols.DataGrid({
			// alias : "montringgrid",
			// 设置显示列。
			columns : [
			{
				name : "LINKEDLINENAME",
				caption : "线路名称",
				dataAlign : "center"
			},
			 {
				name : "DEVICENAME",
				caption : "装置名称",
				dataAlign : "center"
			}, {
				name : "DEVICECATEGORY_DISPLAY",
				caption : "监测类型",
				dataAlign : "center"
			}, {
				name : "ISRT",
				caption : "是否实时",
				dataAlign : "center",
				renderCell : function(p_item, $p_cell) {
					var value = p_item.getValue("ISRT"); // 获取 GENDER 字段的值。
					if("T"==value)
						{
						$p_cell.html("是").css({
							
							"color" : "green"
						});
						}
					else 
					{
						$p_cell.html("否").css({
							
							"color" : "red"
						});
						}
					
				}
			},{
				name : "PROVINCE_NAME",
				caption : "所属单位",
				dataAlign : "center"
			}, {
				name : "LINKEDPOLENAME",
				caption : "杆塔名称",
				dataAlign : "center"
			},{
				name : "DEVICEVOLTAGE",
				caption : "电压等级",
				dataAlign : "center"
			}, {
				name : "DEVICECATEGORY",
				caption : "装置类别",
				dataAlign : "center"
			}, {
				name : "DEVICEMODEL",
				caption : "装置型号",
				dataAlign : "center"
			},{
				name : "MANUFACTURER",
				caption : "生产厂家"
			},
			{
				name : "RUNDATE",
				caption : "投运日期",
				dataAlign : "center"
			}
			],
			displayRowNumber : true,
			displayCheckBox : false,
			rowNumberColWidth : 50,
			displayColSplitLine : true,
			entityContainer : gridEntityContainer,
			enableCellTip : true
			
		});
		me.hSplit2.addControl(me.dataGridcx,1);
    }
    
    
    
    //获取grid
    me.getDataGridsd = function()
    {
    	return me.dataGridSD;
    }
  //获取grid
    me.getDataGridcx = function()
    {
    	return me.dataGridcx;
    }
    //获取grid
    me.getDataGridbd = function()
    {
    	return me.dataGridBD;
    }
    
    return me.endOfClass(arguments);
};