$ns("generaldetail.views");
$import("generaldetail.views.MainView");

generaldetail.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new generaldetail.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	me.view.dataGrid.clearColumns();
    	var filter = me.view.dataGrid.filter;
    	//var filter = dwbm+"&"+st+"&"+lx+"&"+sfss;
    	var lxs = filter.split("&");
    	lxs = lxs[2].split("=");
    	var lx = lxs[1];
    	
    	var  cloumns = null;
    	if("sd"==lx)
    		{
    		cloumns =[
    	              {name : "DEPMC",caption : "所属单位",dataAlign : "center",width : 150},
    				  {name : "LINKEDLINENAME",caption : "所属线路",dataAlign : "center",width : 150},
    				  {name : "LINKEDPOLENAME",caption : "所属杆塔",dataAlign : "center"},
    				  {name : "DEVICENAME",caption : "装置名称",dataAlign : "center"},
    				  {name : "STATUS",caption : "运行状态",dataAlign : "center",width : 150},
    				  {name : "DEVICECATEGORY_DISPLAY",caption : "监测类型"},
    				  {name : "DEVICEVOLTAGE",caption : "电压等级",dataAlign : "center"},
    				  {name : "ISRT",caption : "是否实时",dataAlign : "center",width : 80,
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
    					},
    					{name : "RUNDATE",caption : "投运日期",formatString: "yyyy-MM-dd HH:mm:ss",dataAlign : "center"},
    					{name : "MANUFACTURER",caption : "生产厂家",dataAlign : "center"}
    					];
    		}
    	else if("bd"==lx)
	    	{
	    		cloumns =[
	    	              {name : "WSDEPMC",caption : "所属单位",dataAlign : "center",width : 150},
	    				  {name : "LINKEDSTATIONNAME",caption : "所属变电站",dataAlign : "center",width : 150},
	    				  {name : "LINKEDEQUIPMENTNAME",caption : "所属一次设备",dataAlign : "center"},
	    				  {name : "DEVICENAME",caption : "装置名称",dataAlign : "center"},
	    				  {name : "STATUS",caption : "运行状态",dataAlign : "center",width : 150},
	    				  {name : "DEVICECATEGORY_DISPLAY",caption : "监测类型",dataAlign : "center"},
	    				  {name : "DEVICEVOLTAGE",caption : "电压等级",dataAlign : "center"},
	    				  {name : "ISRT",caption : "是否实时",dataAlign : "center",width : 80,
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
	    					},
	    					{name : "MANUFACTURER",caption : "生产厂家",dataAlign : "center"}
	    					];
	    	}
    	
    	else if("dl"==lx)
    	{
    		cloumns =[
    	              {name : "DEPMC",caption : "所属单位",dataAlign : "center",width : 150},
    				  {name : "LINKEDCABLEANDCHANNELNAME",caption : "所属电缆/通道",dataAlign : "center",width : 150},
    				  {name : "LINKEDEQUIPMENTNAME",caption : "所属一次设备",dataAlign : "center"},
    				  {name : "DEVICENAME",caption : "装置名称",dataAlign : "center"},
    				  {name : "STATUS",caption : "运行状态",dataAlign : "center",width : 150},
    				  {name : "DEVICECATEGORY_DISPLAY",caption : "监测类型",dataAlign : "center"},
    				  {name : "DEVICEVOLTAGE",caption : "电压等级",dataAlign : "center"},
    				  {name : "ISRT",caption : "是否实时",dataAlign : "center",width : 80,
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
    					},
    					{name : "MANUFACTURER",caption : "生产厂家",dataAlign : "center"}
    					];
    	}
    	else if("xl"==lx)
    	{
    		cloumns =[
    	              {name : "DWMC",caption : "所属单位",dataAlign : "center",width : 150},
    				  {name : "LINKEDLINENAME",caption : "线路名称",dataAlign : "center",width : 150},
    				  {name : "LINKEDEQUIPMENTDY",caption : "电压等级",dataAlign : "center"}
    				  ];
    	}
    	else if("dz"==lx)
    	{
    		cloumns =[
    	              {name : "PROVINCE_NAME",caption : "所属单位",dataAlign : "center",width : 150},
    				  {name : "LINKEDSTATIONNAME",caption : "变电站",dataAlign : "center",width : 150},
    				  {name : "MC",caption : "电压等级",dataAlign : "center"}
    				  ];
    	}
    	else if("dls"==lx)
    	{
    		cloumns =[
    	              {name : "PROVINCE_NAME",caption : "所属单位",dataAlign : "center",width : 150},
    				  {name : "LINKEDLINENAME",caption : "电缆/通道名称",dataAlign : "center",width : 150},
    				  {name : "MC",caption : "电压等级",dataAlign : "center"}
    				  ];
    	}
    	me.view.dataGrid.setFilter(filter);
    	me.view.dataGrid.appendColumns(cloumns);
    	me.view.dataGrid.load();
    };
    
    
   
    
    return me.endOfClass(arguments);
};