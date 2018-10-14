$ns("dlzztj.views");
$import("dlzztj.views.dlzztjBySccjMainView");

dlzztj.views.dlzztjBySccjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dlzztj.views.dlzztjBySccjMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    };
    
    me._initBdSccjDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#ALLFGL,#FGL37,#rownumber,#FGL36,#FGL35,#FGL34,#FGL33,#FGL32,#FGL30,#FGL25,#FGL86,#FGL85,#FGL84,#FGL83,#FGL82,#FGL81,#FGL80");
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
				showBdSccjDetail(item, colName);
			});
    }
    
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showBdSccjDetail = function(item, colName) {
		if(colName.substring(0,4)=="ZZZS"){
		dlzzcxtjDetailView = null;
		if (dlzzcxtjDetailView == null) {
			var mvc = new dlzztj.views.dlzzxxDetailMainViewController();
			dlzzcxtjDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var dlmc = editor.dlmc.value;		//线路名称
		var ssdw = editor.sccj.value;		//所属单位
		var tyrq = editor.tyrq.value;		//投运日期
		var dydj = editor.dydj.value;		//电压等级
		var sccj = item.item.values.SSWSMC;						//所属单位
//		dlzzcxtjDetailView.searchBox.editors[5].setValue(sccj);
		var sccj_searchBox =  editor.sccj.value;;		//查询框内的所属单位
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
		
		if(null != ssdw && '' != ssdw.trim()){		//如果所属单位不为空
    		filter.push("ssdw = " + ssdw);
    	}
		
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if("厂家合计"==sccj){		//如果生产厂家为生产厂家
			if(null != sccj_searchBox && '' != sccj_searchBox.trim()){
				filter.push("sccj = " + sccj_searchBox);
			}
//			dlzzcxtjDetailView.searchBox.editors[5].value=sccj_searchBox;
    	}else{
    		sccj = item.item.values.SSWSMC;
    		filter.push("sccj = " + sccj);
//    		dlzzcxtjDetailView.searchBox.editors[5].value=sccj;
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
//		dlzzcxtjDetailView.searchBox.editors[2].setEnabled(false);
//		dlzzcxtjDetailView.searchBox.editors[5].setEnabled(false);
		dlzzcxtjDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		dlzzcxtjDetailView.dataGrid.load();
		me.view.detailWin.setWidth("1000");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("电缆监测装置信息列表");
		me.view.detailWin.setView(dlzzcxtjDetailView);
		me.view.detailWin.showDialog();
	}
}
    
    /**
	 * 点击按生产厂家类型统计按钮触发的事件
	 */
	me._sccjQueryBtn_click = function() {
		var _dataGrid = me.view.getDataGrid();
		var bool = checkdate();
		if(bool){
			var _seSearchBox = me.view.getSearchBox();
			var sccj = _seSearchBox.editors.sccj.value;
			var params = _seSearchBox.getSearchParam();
			_dataGrid.setFilter(params);
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
	
	me._sccjExportBtn_click = function(){
    	
    	 try {
    		 var _dataGrid = me.view.getDataGrid();
    	    	var util = new mxpms.utils.FormDocumentUtil({
    	    		title:"",
    	    		view : _dataGrid.$grid,
    	    		fileName:"电缆监测装置按生产厂家统计"
    	    	});
    	    	 util.exportExcel(); 
    	    	 var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
    				var transactionType = "状态监测-监测装置管理-电缆监测装置按生产厂家统计";		//此处为页面的菜单路径信息
    				var result = "操作成功"								//此处为操作结果，二选一
    				var userlogg = new userlogger.views.MainViewController();
    				userlogg.updateUserLogger(czlx,transactionType,result);
		} catch (e) {
			mx.indicate("info","导出失败");
			var czlx = "导出";												//此处为操作方式：新增/修改/删除 任选其一
			var transactionType = "状态监测-监测装置管理-电缆监测装置按生产厂家统计";		//此处为页面的菜单路径信息
			var result = "操作失败"								//此处为操作结果，二选一
			var userlogg = new userlogger.views.MainViewController();
			userlogg.updateUserLogger(czlx,transactionType,result);	
			}
    }
    
    
    return me.endOfClass(arguments);
};