$ns("sdzzyxzktj.views");
$import("sdzzyxzktj.views.sdzzyxzktjBySccjMainView");
$import("sdzzyxzktj.views.sdzzyxzktjDetailMainViewController");
$include("~/com.sgcc.pms.zbztjc.util/enum/commonFunction.js");
sdzzyxzktj.views.sdzzyxzktjBySccjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdzzyxzktj.views.sdzzyxzktjBySccjMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {

    };
    
   me._initSdSccjDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#rownumber,#SSWSMC,#JRL012001,#JRL013001,#JRL013002,#JRL013003,#JRL013004,#JRL013005,#JRL013006,#JRL014001,#JRL018001,#JRL018002,#JRL018003,#ALLJRL");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","sdzzyxzktjLink");
		    	}
			}
	    	_dataGrid.$body.find(".sdzzyxzktjLink").click(function(e) {
				var item = $(this).parent().data();
				var colName = e.target.id;
				$(this)[0].style.color = "#999999";
				showSccjDetail(item, colName);
			});
    }
    
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showSccjDetail = function(item, colName) {
		if(colName.indexOf("SSJR") != -1 ){
			sdzzTwoDetailView = null;
		if (sdzzTwoDetailView == null) {
			var mvc = new sdzzyxzktj.views.sdzzxxDetailMainViewController();
			sdzzTwoDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var dydj = editor.dydj.value;		//监测类型的值
		var xlmc = editor.xlmc.value;		//线路名称
		var ssdw = editor.ssdw.value;		//生产厂家
		var tyrq = editor.tyrq.value;		//投运日期
		var sccj = item.item.values.SSWSMC;						//所属单位
		var sccj_searchBox =  editor.sccj.value;;		//查询框内的生产厂家
		var jclx = colName.substring(colName.length-6,colName.length);		//电压等级
		var type = null;

		var filter = new Array();
		filter.push("sfss = T");
		
		if(null != jclx){		//如果监测类型不为空
			if("LLSSJR" != jclx){
    		filter.push("jclx = " + jclx);
			}
    	}
		
		if(null != dydj){		//如果电压等级不为空
    		filter.push("dydj = " + dydj);
    	}
		
		if(null != xlmc && "" != xlmc.trim()){		//如果线路名称不为空
    		filter.push("xlmc = " + xlmc);
    	}
		if("厂家合计" == sccj){		
			if(null != sccj_searchBox && "" != sccj_searchBox.trim()){
				filter.push("sccj = " + sccj_searchBox);
			}
    	}else{
    		sccj = item.item.values.SSWSMC;
    		filter.push("sccj = " + sccj);
    	}
		
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if(null != ssdw){
			filter.push("ssdw = " + ssdw);
		}

		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		sdzzTwoDetailView.getDataGrid().setFilter(str.substring(0, str.length - 1));
		sdzzTwoDetailView.dataGrid.load();
		me.view.detailWin.setWidth("900");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("所属线路查询");
		me.view.detailWin.setView(sdzzTwoDetailView);
		me.view.detailWin.showDialog();
		}
		else if(colName.indexOf("ZZZS") != -1 ){
		
		sdzzyxzktjDetailView = null;
		if (sdzzyxzktjDetailView == null) {
			var mvc = new sdzzyxzktj.views.sdzzyxzktjDetailMainViewController();
			sdzzyxzktjDetailView = mvc.getView();
		}var editor = me.view.controls[0].editors;
		var dydj = editor.dydj.value;		//电压等级
		var xlmc = editor.xlmc.value;		//线路名称
		var ssdw = editor.ssdw.value;		//所属单位
		var tyrq = editor.tyrq.value;		//投运日期
		var sccj = item.item.values.SSWSMC;		//生产厂家
		var sccj_searchBox =  editor.sccj.value;;		//查询框内的生产厂家
		var jclx = colName.substring(colName.length-6,colName.length);		//监测类型

		var filter = new Array();
		
		
		if(null != jclx){		//如果监测类型不为空
    		if("LLZZZS" != jclx){
    		filter.push("jclx = " + jclx);
			}
    	}
		
		if(null != dydj){		//如果电压等级不为空
    		filter.push("dydj = " + dydj);
    	}
		
		if(null != xlmc && "" != xlmc.trim()){		//如果线路名称不为空
    		filter.push("xlmc = " + xlmc);
    	}
		if("厂家合计" == sccj){		//如果生产厂家为生产厂家
			if(null != sccj_searchBox&&"" != sccj_searchBox.trim()){
				filter.push("sccj = " + sccj_searchBox);
			}
//			sdzzyxzktjDetailView.searchBox.editors[5].value=sccj_searchBox;
    	}else{
    		sccj = item.item.values.SSWSMC;
    		filter.push("sccj = " + sccj);
//    		sdzzyxzktjDetailView.searchBox.editors[5].value=sccj;
    	}
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if(null != ssdw && '' != ssdw.trim()){		//如果所属单位为国家电网公司
    		filter.push("ssdw = " + ssdw);
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		var jclxMap={"018001":"微气象","018003":"视频","018002":"图像","013001":"导线弧垂","013002":"导线温度",
			"013003":"微风振动","013004":"风偏","013005":"覆冰","013006":"导线舞动","014001":"现场污秽度","012001":"杆塔倾斜"};
		var jclxmc = jclxMap[jclx];
//		sdzzyxzktjDetailView.searchBox.editors[2].setValue(jclxmc);
//		sdzzyxzktjDetailView.searchBox.editors[2].value=jclx;
//		sdzzyxzktjDetailView.searchBox.editors[2].setEnabled(false);
//		sdzzyxzktjDetailView.searchBox.editors[5].setEnabled(false);
//		sdzzyxzktjDetailView.searchBox.editors[6].value=tyrq;
//		sdzzyxzktjDetailView.searchBox.setFieldVisible("tyrq", false)
		sdzzyxzktjDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		sdzzyxzktjDetailView.dataGrid.load();
		me.view.detailWin.setWidth("900");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("输电监测装置信息列表");
		me.view.detailWin.setView(sdzzyxzktjDetailView);
		me.view.detailWin.showDialog();
	}
}
    
    
    /**
	 * 点击按生产厂家统计按钮触发的事件
	 */
	me._jclxQueryBtn_click = function() {
		var _dataGrid = me.view.getDataGrid();
		var bool = checkdate();
		if(bool){
			var _seSearchBox = me.view.getSearchBox();
			var sccj = _seSearchBox.editors.sccj.value;
			var params = _seSearchBox.getSearchParam();
			
			_dataGrid.setFilter(params);
			//hidetile(_dataGrid,sccj);
			_dataGrid.load();
		}
		
	}
	

	//时间判断，起始时间和终止时间都不能大于当前时间
		function checkdate(){
			var check = me.view.getSearchBox().editors.tyrq.value;
			if(check!=""&&check!=null){
				var date = new Date().toLocaleDateString();
				date = date.replace(/-/g,"/");
				if(check[0]!=""&&check[0]!=null ){
					var check0=check[0].replace(/-/g,"/");
					if(Date.parse(check0)>Date.parse(date))
					{
//						me.view.getSearchBox().editors.tyrq.reset();
						mx.indicate("warn", "开始时间不能大于当前时间!");
						return false;
					}
				}
				if(check[1]!=""&&check[1]!=null ){
					var check1=check[1].replace(/-/g,"/");
					if(Date.parse(check1)>Date.parse(date))
					{
//						me.view.getSearchBox().editors.tyrq.reset();
						mx.indicate("warn", "结束时间不能大于当前时间!");
						return false;
					}
				}
				return true;
			}else{
				return true;
			}
			
		}
	
		function hidetile(_dataGrid,sccj){
		
    	if(null != sccj){
    		var param = sccj.split(",");
    		_dataGrid.clearColumns();
			var cloumns = [{
				name : "SSWSMC",
				caption : "生产厂家",
				dataAlign : "center",
				editorType : "TextEditor"
			}];
			_dataGrid.appendColumns(cloumns);
		
    		for(var i=0;i<param.length;i++){
    			if("012001" == param[i]){
    				var column1 = [
    				    {
					name : "ZS012001",
					caption : "杆塔倾斜",
					dataAlign : "center",
					editorType : "TextEditor"
    				    }
    		        ];
    				_dataGrid.appendColumns(column1);
    			}else if("013001" == param[i]){
    				var column2 = [
					{
						name : "ZS013001",
						caption : "导线弧垂",
						dataAlign : "center",
						editorType : "TextEditor"
					}];
    				_dataGrid.appendColumns(column2);
    			}else if("013002" == param[i]){
    				var column3 = [{
    					name : "ZS013002",
    					caption : "导线温度",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column3);
    			}else if("013003" == param[i]){
    				var column4 = [{
    					name : "ZS013003",
    					caption : "微风振动",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column4);
    			}else if("013004" == param[i]){
    				var column5 = [{
    					name : "ZS013004",
    					caption : "风偏振动",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column5);
    			}else if("013005" == param[i]){
    				var column6 = [{
    					name : "ZS013005",
    					caption : "覆冰",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column6);
    			}else if("013006" == param[i]){
    				var column7 = [{
    					name : "ZS013006",
    					caption : "导线舞动",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column7);
    		    }else if("014001" == param[i]){
    				var column8 = [{
    					name : "ZS014001",
    					caption : "现场污秽度监测",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column8);
    		    }else if("018001" == param[i]){
    				var column9 = [{
    					name : "ZS018001",
    					caption : "微气象",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column9);
    		    }else if("018002" == param[i]){
    				var column10 = [{
    					name : "ZS018002",
    					caption : "图像",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column10);
    		    }else if("018003" == param[i]){
    				var column11 = [{
    					name : "ZS018003",
    					caption : "视频",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column11);
    		    }
    			
    		}
    		var columns = [{
				name : "ALLZZZS",
				caption : "所有监测装置",
				dataAlign : "center",
				editorType : "TextEditor"
			}];
    		_dataGrid.appendColumns(columns);
    		
    	}
    }
    
	me._sccjExportBtn_click = function (){
    	try {
    		/*var _params = null;        
        	var xls = new mxpms.utils.CommUtil();
        	_params = me.view.getDataGrid().filter;
        	xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "输电装置运行状况按生产厂家统计"
        	});*/
    		var _dataGrid = me.view.getDataGrid();
        	exportToExcel(_dataGrid,"输电装置运行状况按生产厂家统计");
        	var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-报表查询-输电装置运行状况按生产厂家统计";		//此处为页面的菜单路径信息
			var result = "操作成功"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			mx.indicate("info","导出失败");
			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测信息查询-输电装置运行状况按生产厂家统计";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		}
    };
    
    return me.endOfClass(arguments);
};