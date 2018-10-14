$ns("bdzzyxzktj.views");
$import("bdzzyxzktj.views.bdzzyxzktjByJclxMainView");
$import("bdzzyxzktj.views.bdzzyxzkDetailMainViewController");

bdzzyxzktj.views.bdzzyxzktjByJclxMainViewController = function() {
	var me = $extend(mx.views.ViewController);
	var base = {};


	me.getView = function() {
		if (me.view == null) {
			me.view = new bdzzyxzktj.views.bdzzyxzktjByJclxMainView({
				controller : me
			});
		}
		return me.view;
	};

	me._onactivate = function(e) {
		
	};
	
	
	me._initBdJclxDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#rownumber,#OBJ_ID,#SSWSMC,#WSSJRS021001,#SSJRL021001,#SSJRL021002,#SSJRL021003,#SSJRL021004,#SSJRL021005,#SSJRL022001,#SSJRL023001,#SSJRL024001,#SSJRL024002,#SSJRL024003,#SSJRL024004,#SSJRL024005,#SSJRL024006,#SSJRL026001,#SSJRL021007,#WSSJRS021001,#WSSJRS021002,#WSSJRS021003,#WSSJRS021004,#WSSJRS021005,#WSSJRS022001,#WSSJRS023001,#WSSJRS024001,#WSSJRS024002,#WSSJRS024003,#WSSJRS024004,#WSSJRS024005,#WSSJRS024006,#WSSJRS026001,#SSJRL021007,#ALLZZZS,#ALLSSJRZZS,#ALLSSJRL,#ALLWSSJRS");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","bdzzyxzktjLink");
		    	}
			}
	    	_dataGrid.$body.find(".bdzzyxzktjLink").click(function(e) {
				var item = $(this).parent().data();
				var colName = e.target.id;
				$(this)[0].style.color = "#999999";
				showBdJclxDetail(item, colName);
			});
    }
    
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showBdJclxDetail = function(item, colName) {
		if(colName.substring(0,4)=="ZZZS"){
		bdzzcxtjDetailView = null;
		if (bdzzcxtjDetailView == null) {
			var mvc = new bdzzyxzktj.views.bdzzyxzkDetailMainViewController();
			bdzzcxtjDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var bdzmc = editor.bdzmc.value;		//变电站名称
		var sccj = editor.sccj.value;		//生产厂家
		var tyrq = editor.tyrq.value;		//投运日期
		var dydj = editor.dydj.value;		//电压等级
		var ssdw = item.item.values.SSWSMC;						//所属单位
		//bdzzcxtjDetailView.searchBox.editors[0].setValue(ssdw);
		var ssdw_searchBox =  editor.ssdw.value;;		//查询框内的所属单位
		var jclx = colName.substring(colName.length-6,colName.length);		//监测类型
		var type = null;

		var filter = new Array();
		
		
		if(null != jclx){		//如果监测类型不为空
    		filter.push("jclx = " + jclx);
    	}
		
		if(null != dydj){		//如果电压等级不为空
    		filter.push("dydj = " + dydj);
    	}
		
		if(null != bdzmc && '' != bdzmc.trim()){		//如果变电站名称不为空
    		filter.push("bdzmc = " + bdzmc);
    	}
		
		if(null != sccj && '' != sccj.trim()){		//如果生产厂家不为空
    		filter.push("sccj = " + sccj);
    	}
		
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if("国家电网公司" == ssdw){		//如果所属单位为国家电网公司
			if(null != ssdw_searchBox && '' != ssdw_searchBox.trim()){
				filter.push("ssdw = " + ssdw_searchBox);
			}
			//bdzzcxtjDetailView.searchBox.editors[0].value=ssdw_searchBox;
    	}else{
    		ssdw = item.item.values.LINKEDPROVICEDEPT;
    		filter.push("ssdw = " + ssdw);
    		//bdzzcxtjDetailView.searchBox.editors[0].value=ssdw;
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		var jclxMap={"021001":"局部放电","021002":"油中溶解气体","021003":"微水","021004":"铁芯接地电流","021005":"顶层油温","021007":"变压器振动波谱","022001":"绝缘监测","023001":"绝缘监测","024001":"局部放电","024002":"分合闸线圈电流波形","024003":"负荷电流波形","024004":"SF6气体压力","024005":"SF6气体水分","024006":"储能电机工作状态","026001":"变电视频监测"};
		var jclxmc = jclxMap[jclx];
		//bdzzcxtjDetailView.searchBox.editors[2].setValue(jclxmc);
		//bdzzcxtjDetailView.searchBox.editors[2].value=jclx;
		//bdzzcxtjDetailView.searchBox.editors[6].value=tyrq;
		//bdzzcxtjDetailView.searchBox.setFieldVisible("tyrq", false)
		//bdzzcxtjDetailView.searchBox.editors[0].setEnabled(false);
		//bdzzcxtjDetailView.searchBox.editors[2].setEnabled(false);
		bdzzcxtjDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		bdzzcxtjDetailView.dataGrid.load();
		me.view.detailWin.setWidth("900");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("输电监测装置信息列表");
		me.view.detailWin.setView(bdzzcxtjDetailView);
		me.view.detailWin.showDialog();
	}
}

	/**
	 * 点击按监测类型统计按钮触发的事件
	 */
	me._jclxQueryBtn_click = function() {
		var _dataGrid = me.view.getDataGrid();
		var _seSearchBox = me.view.getSearchBox();
		var jclx = _seSearchBox.editors.jclx.value;
		var params = _seSearchBox.getSearchParam();
		_dataGrid.setFilter(params);
		//hidetile(_dataGrid,jclx);
		_dataGrid.load();
	}
	
	function hidetile(_dataGrid,jclx){
		
    	if(null != jclx){
    		var param = jclx.split(",");
    		_dataGrid.clearColumns();
			var cloumns = [{
				name : "SSWSMC",
				caption : "所属单位",
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
    		
    	}else{
    	_dataGrid.clearColumns();
    	var columnsAll = [
			{
				name : "SSWSMC",
				caption : "所属单位",
				dataAlign : "center",
				editorType : "TextEditor"
			}, {
				name : "ZS012001",
				caption : "杆塔倾斜",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013001",
				caption : "导线弧垂",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013002",
				caption : "导线温度",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013003",
				caption : "微风振动",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS018003",
				caption : "视频",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013004",
				caption : "风偏振动",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS013005",
				caption : "覆冰",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS014001",
				caption : "现场污秽度监测",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS018001",
				caption : "微气象",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ZS018002",
				caption : "图像",
				dataAlign : "center",
				editorType : "TextEditor"
			}, {
				name : "ZS013006",
				caption : "导线舞动",
				dataAlign : "center",
				editorType : "TextEditor"
			}

			, {
				name : "ALLZZZS",
				caption : "所有监测装置",
				dataAlign : "center",
				editorType : "TextEditor"

			} ];
		_dataGrid.appendColumns(columnsAll);	
    	}
    	
    }
	
	return me.endOfClass(arguments);
};