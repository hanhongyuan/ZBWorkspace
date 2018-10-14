$ns("zyzztj.views");
$import("zyzztj.views.zyxtinfoMainView");

zyzztj.views.zyxtinfoMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new zyzztj.views.zyxtinfoMainView({ controller: me });
        }
        return me.view;
    };
    
    me._initDataGridSD = function()
    {
    	var _dataGrid = me.view.getDataGridsd();
    	_col = _dataGrid.$body.find("td").not("#XLMC,#XLID,#rownumber");
		//统计值添加方法
    	for(var i=0;i<_col.length;i++){
			if("0" != _col[i].innerText && "" != _col[i].innerText ){
	    		_col[i].setAttribute("class","zyzztjLink");
	    	}
		}
    	
    	_dataGrid.$body.find(".zyzztjLink").click(function(e) {
			var item = $(this).parent().data();
			var colName = e.target.id;
			var text = e.target.textContent;
			$(this)[0].style.color = "#999999";
			showDetailsd(item, colName);
		});
    }
    me._initDataGridBD = function()
    {
    	var _dataGrid = me.view.getDataGridbd();
    	_col = _dataGrid.$body.find("td").not("#BDZMC,#BDZID,#rownumber");
		//统计值添加方法
    	for(var i=0;i<_col.length;i++){
			if("0" != _col[i].innerText && "" != _col[i].innerText ){
	    		_col[i].setAttribute("class","zyzztjLink");
	    	}
		}
    	
    	_dataGrid.$body.find(".zyzztjLink").click(function(e) {
			var item = $(this).parent().data();
			var colName = e.target.id;
			var text = e.target.textContent;
			$(this)[0].style.color = "#999999";
			showDetailbd(item, colName);
		});
    }
    
    
    showDetailsd = function(item, colName)
    {
    	var _dataGrid = me.view.getDataGridcx();
    	_dataGrid.clearColumns();
    	var ssxl = item.item.id;
    	var xtmc = item.item.values.DWMC;
    	var columns = [
    	   			{name : "LINKEDLINENAME",
    					caption : "线路名称",
    					dataAlign : "center"
    				},
    				 {name : "DEVICENAME",
    					caption : "装置名称",
    					dataAlign : "center"
    				}, {name : "DEVICECATEGORY_DISPLAY",
    					caption : "监测类型",
    					dataAlign : "center"
    				}, {name : "ISRT",
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
    					dataAlign : "center"}];
    	_dataGrid.appendColumns(columns);
	
    	var filter = new Array();
    	if(null != xtmc){	
    		ssxt = item.item.values.DWBM;
    		filter.push("ssxt = " + ssxt);
    	}
    	if(null!=ssxl)
 	    {
 	    	filter.push("ssxl=" + ssxl);
 	    }
    	if("SSJRZZS"==colName)
  	   {
  	   		filter.push("sfss=T" );
  	   }
    	if("ZS"==colName.substring(0,2))
 	   {
    		var jclx = colName.substring(2,colName.length);
 	   		filter.push("jclxsd=" + jclx );
 	   }
    	
    	filter.push("lx=sd");
    	var str = "";
  	    for ( var i = 0; i < filter.length; i++) {
  			str += filter[i] + "&";
  		}
  	   
    	_dataGrid.setFilter(str.substring(0, str.length - 1));
    	
    	_dataGrid.load();
    }
    
    showDetailbd = function(item, colName)
    {
    	var _dataGrid = me.view.getDataGridcx();
    	_dataGrid.clearColumns();
    	var ssbdz = item.item.id;
    	var columns = [
    	   			{name : "LINKEDSTATIONNAME",
    					caption : "所属变电站",
    					dataAlign : "center"
    				},
    				 {name : "DEVICENAME",
    					caption : "装置名称",
    					dataAlign : "center"
    				}, {name : "DEVICECATEGORY_DISPLAY",
    					caption : "监测类型",
    					dataAlign : "center"
    				}, {name : "ISRT",
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
    					name : "LINKEDEQUIPMENTNAME",
    					caption : "设备名称",
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
    					dataAlign : "center"}];
    	_dataGrid.appendColumns(columns);	
	
    	var filter = new Array();
    	if(null!=ssbdz)
 	    {
 	    	filter.push("ssbdz=" + ssbdz);
 	    }
    	if("SSJRZZS"==colName)
  	   {
  	   		filter.push("ISRT=T" );
  	   }
    	if("ZZZS02"==colName.substring(0,6) )
 	   {
    		var jclx = colName.substring(4,colName.length);
 	   		filter.push("jclxbd=" + jclx );
 	   }
    	filter.push("lx=bd");
    	var str = "";
  	    for ( var i = 0; i < filter.length; i++) {
  			str += filter[i] + "&";
  		}
  	   
    	_dataGrid.setFilter(str.substring(0, str.length - 1));
    	
    	_dataGrid.load();
    }
    
    
    
    
    return me.endOfClass(arguments);
};