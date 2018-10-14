$ns("dlzztj.views");
$import("dlzztj.views.dlzztjByJclxMainView");

dlzztj.views.dlzztjByJclxMainViewController = function() {
	var me = $extend(mx.views.ViewController);
	var base = {};

	me._searchBox = null;
	var gjxxctjDetailView = null;

	me.getView = function() {
		if (me.view == null) {
			me.view = new dlzztj.views.dlzztjByJclxMainView({
				controller : me
			});
		}
		return me.view;
	};

	me._onactivate = function(e) {
	
	};
	
	me._initBdJclxDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#rownumber");
			//_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#ALLFGL,#FGL37,#FGXLZS37,#rownumber,#FGL36,#FGXLZS36,#FGL35,#FGXLZS35,#FGL34,#FGXLZS34,#FGL33,#FGXLZS33,#FGL32,#FGXLZS32,#FGL30,#FGXLZS30,#FGL25,#FGXLZS25,#FGL86,#FGXLZS86,#FGL85,#FGXLZS85,#FGL84,#FGXLZS84,#FGL83,#FGXLZS83,#FGL82,#FGXLZS82,#FGL81,#FGXLZS81,#FGL80,#FGXLZS80");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","dlzztjLink");
		    	}
			}
	    	_dataGrid.$body.find(".dlzztjLink").click(function(e) {
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
		dlzzcxtjDetailView = null;
		if (dlzzcxtjDetailView == null) {
			var mvc = new dlzztj.views.dlzzxxDetailMainViewController();
			dlzzcxtjDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var dlmc = editor.dlmc.value;		//线路名称
		var sccj = editor.sccj.value;		//生产厂家
		var tyrq = editor.tyrq.value;		//投运日期
		var dydj = editor.dydj.value;		//投运日期
		var ssdw = item.item.values.SSWSMC;						//所属单位
//		dlzzcxtjDetailView.searchBox.editors[0].setValue(ssdw);
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
		
		if(null != dlmc && '' != dlmc.trim()){		//如果变电站名称不为空
    		filter.push("dlmc = " + dlmc);
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
//			dlzzcxtjDetailView.searchBox.editors[0].value=ssdw_searchBox;
    	}else{
    		ssdw = item.item.values.LINKEDPROVICEDEPT;
    		filter.push("ssdw = " + ssdw);
//    		dlzzcxtjDetailView.searchBox.editors[0].value=ssdw;
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		
		var jclxMap={"031001":"电缆局部放电","031002":"护层接地电流","031003":"分布式光纤测温","031004":"电缆油压","032001":"水位",
					"032002":"供电","032003":"通风","032004":"排水","032005":"照明","032006":"沉降","032007":"气体","032008":"环境温度",
					"032009":"电子井盖","032010":"图像","032011":"视频","032012":"门禁","032013":"防火阀","032014":"红外对射","032015":"声光报警",
					"032016":"火灾报警","032017":"灭火装置","032018":"防火门" };
		var jclxmc = jclxMap[jclx];
//		dlzzcxtjDetailView.searchBox.editors[2].setValue(jclxmc);
//		dlzzcxtjDetailView.searchBox.editors[2].value=jclx;
//		dlzzcxtjDetailView.searchBox.editors[6].value=tyrq;
//		dlzzcxtjDetailView.searchBox.setFieldVisible("tyrq", false)
//		dlzzcxtjDetailView.searchBox.editors[0].setEnabled(false);
//		dlzzcxtjDetailView.searchBox.editors[2].setEnabled(false);
		
		dlzzcxtjDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		dlzzcxtjDetailView.dataGrid.load();
		me.view.detailWin.setWidth("1000");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("电缆监测装置信息列表");
		me.view.detailWin.setView(dlzzcxtjDetailView);
		me.view.detailWin.showDialog();
	}
}
    me._jclxExportBtn_click = function(){
    	
    	 try {
    		 var _dataGrid = me.view.getDataGrid();
    	    	var util = new mxpms.utils.FormDocumentUtil({
    	    		title:"",
    	    		view : _dataGrid.$grid,
    	    		fileName:"电缆测装置按监测类型统计"
    	    	});
    	    	 util.exportExcel(); 
    	    	 var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
    				var transactionType = "状态监测-监测信息管理-电缆测装置按监测类型统计";		//此处为页面的菜单路径信息
    				var result = "操作成功"								//此处为操作结果，二选一
    				var userlogg = new userlogger.views.MainViewController();
    				userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			mx.indicate("info","导出失败");
			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测信息管理-电缆测装置按监测类型统计";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);
		}
    }



	/**
	 * 点击按监测类型统计按钮触发的事件
	 */
	me._jclxQueryBtn_click = function() {
		var _dataGrid = me.view.getDataGrid();
		var bool = checkdate();
		if(bool){
			var _seSearchBox = me.view.getSearchBox();
			var jclx = _seSearchBox.editors.jclx.value;
			var params =_seSearchBox.getSearchParam();
			_dataGrid.setFilter(params);
			//hidetile(_dataGrid,jclx);
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
//					me.view.getSearchBox().editors.tyrq.reset();
					mx.indicate("warn", "开始时间不能大于当前时间!");
					return false;
				}
			}
			if(check[1]!=""&&check[1]!=null ){
				var check1=check[1].replace(/-/g,"/");
				if(Date.parse(check1)>Date.parse(date))
				{
//					me.view.getSearchBox().editors.tyrq.reset();
					mx.indicate("warn", "结束时间不能大于当前时间!");
					return false;
				}
			}
			return true;
		}else{
			return true;
		}
		
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