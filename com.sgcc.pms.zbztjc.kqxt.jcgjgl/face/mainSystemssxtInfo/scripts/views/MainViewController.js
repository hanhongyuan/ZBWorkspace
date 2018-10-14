$ns("mainSystemssxtInfo.views");
$import("mainSystemssxtInfo.views.MainView");

mainSystemssxtInfo.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new mainSystemssxtInfo.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    
    
    me._initDataGridSD = function(){
    	var _dataGrid = me.view.getSdDataGrid();
    	var _col_ssxt = _dataGrid.$body.find("td").not("#OBJ_ID,#XLMC,#rownumber");
    	
		for(var i=0;i<_col_ssxt.length;i++){//&& 0 != _col_ssxt[i].innerText
			if("" != _col_ssxt[i].innerText && 0 != _col_ssxt[i].innerText){
				_col_ssxt[i].setAttribute("class","ssxtLinkDetail");
			}
		}
		
    	_dataGrid.$body.find(".ssxtLinkDetail").click(function(e) {
			var item = $(this).parent().data();
			var colName = e.target.id;
			$(this)[0].style.color = "#999999";
			showSdDetail(item, colName);
		});
    }
    
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showSdDetail = function(item, colName) {
		var str = "";
		var xlmc = item.item.id;		//线路名称
		var type = "sd";
		
		if("GJZS" == colName){
			str = "xlmc = "+xlmc+" & type = sd";
		}else if("ALLHCNT" == colName){
			str = "xlmc = "+xlmc+" & type = sd & ishandled = T";
		}else{
			var typeCode = colName.substring(colName.length-6,colName.length);
			str = "xlmc = "+xlmc+" & type = sd & jclx = "+typeCode;
		}
		me.view.getDetailDataGrid().setFilter(str);
		me.view.getDetailDataGrid().load();
	}
	
	 me._initDataGridBD = function(){
	    	var _dataGrid = me.view.getBdDataGrid();
	    	var _col_ssxt = _dataGrid.$body.find("td").not("#OBJ_ID,#BDZMC,#rownumber");
	    	
			for(var i=0;i<_col_ssxt.length;i++){//&& 0 != _col_ssxt[i].innerText
				if("" != _col_ssxt[i].innerText && 0 != _col_ssxt[i].innerText){
					_col_ssxt[i].setAttribute("class","ssxtLinkDetail");
				}
			}
			
	    	_dataGrid.$body.find(".ssxtLinkDetail").click(function(e) {
				var item = $(this).parent().data();
				var colName = e.target.id;
				$(this)[0].style.color = "#999999";
				showBdDetail(item, colName);
			});
	    }
	    
	     /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showBdDetail = function(item, colName) {
		var str = "";
		var bdz = item.item.id;		//线路名称
		
		if("GJZS" == colName){
			str = "bdz = "+bdz+" & type = bd";
		}else if("ALLHCNT" == colName){
			str = "bdz = "+bdz+" & type = bd & ishandled = T";
		}else{
			var typeCode = colName.substring(colName.length-6,colName.length);
			str = "bdz = "+bdz+" & type = bd & jclx = "+typeCode;
		}
		me.view.getDetailDataGrid().setFilter(str);
		me.view.getDetailDataGrid().load();
	}
    
	me._item_click_sd = function(e){
    	var lineId = e.item.values.OBJ_ID;			//取点击的条目的OBJ_ID 此处为输电的线路编码
    	var str = "xlmc = "+lineId+" & type = sd";
    	me.view.getDetailDataGrid().setFilter(str);
		me.view.getDetailDataGrid().load();
    }
	
    me._item_click_bd = function(e){
    	var bdzId = e.item.values.OBJ_ID;			//取点击的条目的OBJ_ID 此处为变电的变电站编码
    	str = "bdz = "+bdzId+" & type = bd";
    	me.view.getDetailDataGrid().setFilter(str);
		me.view.getDetailDataGrid().load();
    }
    
    return me.endOfClass(arguments);
};