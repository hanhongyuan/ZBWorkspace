$ns("bdzztj.views");
$import("bdzztj.views.bdzztjBySccjMainView");

bdzztj.views.bdzztjBySccjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzztj.views.bdzztjBySccjMainView({ controller: me });
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
		    		_col[i].setAttribute("class","bdzztjLink");
		    	}
			}
	    	_dataGrid.$body.find(".bdzztjLink").click(function(e) {
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
		bdzzcxtjDetailView = null;
		if (bdzzcxtjDetailView == null) {
			var mvc = new bdzztj.views.bdzzxxDetailMainViewController();
			bdzzcxtjDetailView = mvc.getView();
		}
		var editor = me.view.controls[0].editors;
		var bdzmc = editor.bdzmc.value;		//线路名称
		var ssdw = editor.sccj.value;		//所属单位
		var tyrq = editor.tyrq.value;		//投运日期
		var dydj = editor.dydj.value;		//电压等级
		var sccj = item.item.values.SSWSMC;						//所属单位
		bdzzcxtjDetailView.searchBox.editors[5].setValue(sccj);
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
		
		if(null != bdzmc && '' != bdzmc.trim()){		//如果变电站名称不为空
    		filter.push("bdzmc = " + bdzmc);
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
			bdzzcxtjDetailView.searchBox.editors[5].value=sccj_searchBox;
    	}else{
    		sccj = item.item.values.SSWSMC;
    		filter.push("sccj = " + sccj);
    		bdzzcxtjDetailView.searchBox.editors[5].value=sccj;
    	}
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		var jclxMap={"021001":"局部放电","021002":"油中溶解气体","021003":"微水","021004":"铁芯接地电流","021005":"顶层油温","021007":"变压器振动波谱","022001":"绝缘监测","023001":"绝缘监测","024001":"局部放电","024002":"分合闸线圈电流波形","024003":"负荷电流波形","024004":"SF6气体压力","024005":"SF6气体水分","024006":"储能电机工作状态","026001":"变电视频监测"};
		var jclxmc = jclxMap[jclx];
		bdzzcxtjDetailView.searchBox.editors[2].setValue(jclxmc);
		bdzzcxtjDetailView.searchBox.editors[2].value=jclx;
		bdzzcxtjDetailView.searchBox.editors[6].value=tyrq;
		bdzzcxtjDetailView.searchBox.setFieldVisible("tyrq", false)
		bdzzcxtjDetailView.searchBox.editors[2].setEnabled(false);
		bdzzcxtjDetailView.searchBox.editors[5].setEnabled(false);
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
	 * 点击按生产厂家类型统计按钮触发的事件
	 */
	me._sccjQueryBtn_click = function() {
		var _dataGrid = me.view.getDataGrid();
		var _seSearchBox = me.view.getSearchBox();
		var sccj = _seSearchBox.editors.sccj.value;
		var params = _seSearchBox.getSearchParam();
		_dataGrid.setFilter(params);
		_dataGrid.load();
	}
	
	
    
    
    return me.endOfClass(arguments);
};