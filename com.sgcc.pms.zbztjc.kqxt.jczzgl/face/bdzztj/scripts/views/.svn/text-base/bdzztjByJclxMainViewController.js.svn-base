$ns("bdzztj.views");
$import("bdzztj.views.bdzztjByJclxMainView");

bdzztj.views.bdzztjByJclxMainViewController = function() {
	var me = $extend(mx.views.ViewController);
	var base = {};

	me._searchBox = null;
	var gjxxctjDetailView = null;

	me.getView = function() {
		if (me.view == null) {
			me.view = new bdzztj.views.bdzztjByJclxMainView({
				controller : me
			});
		}
		return me.view;
	};

	me._onactivate = function(e) {
	
	};
	
	me._initBdJclxDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#ALLFGL,#FGL37,#rownumber,#FGL36,#FGL35,#FGL34,#FGL33,#FGL32,#FGL30,#FGL25,#FGL86,#FGL85,#FGL84,#FGL83,#FGL82,#FGL81,#FGL80");
			//_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#ALLFGL,#FGL37,#FGXLZS37,#rownumber,#FGL36,#FGXLZS36,#FGL35,#FGXLZS35,#FGL34,#FGXLZS34,#FGL33,#FGXLZS33,#FGL32,#FGXLZS32,#FGL30,#FGXLZS30,#FGL25,#FGXLZS25,#FGL86,#FGXLZS86,#FGL85,#FGXLZS85,#FGL84,#FGXLZS84,#FGL83,#FGXLZS83,#FGL82,#FGXLZS82,#FGL81,#FGXLZS81,#FGL80,#FGXLZS80");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","bdzztjLink");
		    	}
			}
	    	_dataGrid.$body.find(".bdzztjLink").click(function(e) {
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
			var mvc = new bdzztj.views.bdzzxxDetailMainViewController();
			bdzzcxtjDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var bdzmc = editor.bdzmc.value;		//线路名称
		var sccj = editor.sccj.value;		//生产厂家
		var tyrq = editor.tyrq.value;		//投运日期
		var dydj = editor.dydj.value;		//投运日期
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
		
		if(null != tyrq ){		//如果投运日期不为空
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
    	me.view.detailWin.setTitle("变电监测装置信息列表");
		me.view.detailWin.setView(bdzzcxtjDetailView);
		me.view.detailWin.showDialog();
	}
}

    /**
     * 点击导出的方法
     */
    me._jclxExportBtn_click= function(){
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "变电监测装置按监测类型统计详情"
        });
    }

	/**
	 * 点击按监测类型统计按钮触发的事件
	 */
	me._jclxQueryBtn_click = function() {
		var _dataGrid = me.view.getDataGrid();
		var _seSearchBox = me.view.getSearchBox();
		var jclx = _seSearchBox.editors.jclx.value;
		var params =_seSearchBox.getSearchParam();
		_dataGrid.setFilter(params);
		//hidetile(_dataGrid,jclx);
		_dataGrid.load();
	}
	function hidetile(_dataGird,jclx) {
		gtqxjc = false;
		dxhcjc = false;
		dxwdjc = false;
		wfzdjc = false;
		fpjc = false;
		fbjc = false;
		dxwd = false;
		whdjc = false;
		wqxjc = false;
		txjc = false;
		spjc = false;
		if (jclx != null) {
			_dataGrid.clearColumns();
			var cloumns = [{
				name : "SSWS",
				caption : "SSWS",
				visible : false
			}, {
				name : "SSWSMC",
				caption : "所属单位",
				dataAlign : "center",
				editorType : "TextEditor"
			}];
			_dataGird.appendColumns(cloumns);
			var jclxArr = jclx.split(",");
			// 判断哪些监测类显示
			for ( var i = 0; i < jclxArr.length; i++) {
				if (jclxArr[i].split("012001").length > 1) {
					gtqxjc = true;
				}
				if (jclxArr[i].split("013001").length > 1) {
					dxhcjc = true;
				}
				if (jclxArr[i].split("013002").length > 1) {
					dxwdjc = true;
				}
				if (jclxArr[i].split("013003").length > 1) {
					wfzdjc = true;
				}
				if (jclxArr[i].split("013004").length > 1) {
					fpjc = true;
				}
				if (jclxArr[i].split("013005").length > 1) {
					fbjc = true;
				}
				if (jclxArr[i].split("013006").length > 1) {
					dxwd = true;
				}
				if (jclxArr[i].split("014001").length > 1) {
					whdjc = true;
				}
				if (jclxArr[i].split("018001").length > 1) {
					wqxjc = true;
				}
				if (jclxArr[i].split("018002").length > 1) {
					txjc = true;
				}
				if (jclxArr[i].split("018003").length > 1) {
					spjc = true;
				}
			}
			// 显示选择的监测类
			if (gtqxjc) {
				cloumns = [{
					name : "ZS012001",
					caption : "杆塔倾斜",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			if (dxhcjc) {
				cloumns = [{
					name : "ZS013001",
					caption : "导线弧垂",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			if (dxwdjc) {
				cloumns = [ {
					name : "ZS013002",
					caption : "导线温度",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			if (wfzdjc) {
				cloumns = [{
					name : "ZS013003",
					caption : "微风振动",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			if (fpjc) {
				cloumns = [ {
					name : "ZS013004",
					caption : "风偏振动",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			if (fbjc) {
				cloumns = [  {
					name : "ZS013005",
					caption : "覆冰",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			if (dxwd) {
				cloumns = [{
					name : "ZS013006",
					caption : "导线舞动",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			if (whdjc) {
				cloumns = [{
					name : "ZS014001",
					caption : "现场污秽度监测",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			if (wqxjc) {
				cloumns = [ {
					name : "ZS018001",
					caption : "微气象",
					dataAlign : "center",
					editorType : "TextEditor"
				} ];
				_dataGird.appendColumns(cloumns);
			}
			if (txjc) {
				cloumns = [ {
					name : "ZS018002",
					caption : "图像",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			if (spjc) {
				cloumns = [{
					name : "ZS018003",
					caption : "视频",
					dataAlign : "center",
					editorType : "TextEditor"
				}];
				_dataGird.appendColumns(cloumns);
			}
			cloumns = [ {
							name : "ALLZZZS",
							caption : "所有监测装置",
							dataAlign : "center",
							editorType : "TextEditor"

						}];
			_dataGird.appendColumns(cloumns);
		} else {
			var cloumns = [ {
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
			_dataGird.appendColumns(cloumns);
		}
	}
	return me.endOfClass(arguments);
};