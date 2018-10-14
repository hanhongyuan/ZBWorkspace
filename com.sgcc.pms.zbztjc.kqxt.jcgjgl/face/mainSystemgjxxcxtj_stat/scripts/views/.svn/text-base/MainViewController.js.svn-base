$ns("mainSystemgjxxcxtj_stat.views");
$import("mainSystemgjxxcxtj_stat.views.MainView");
$import("mainSystemssxtInfo.views.MainViewController");
$import("mainSystemgjxxcxtj_detail.views.MainViewController");

mainSystemgjxxcxtj_stat.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new mainSystemgjxxcxtj_stat.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    /**
     * 点击统计的方法
     */
    me._btnStat_click = function(){
    	me._searchBox = me.view.getSearchBox();				//获取SearchBox
    	var str = me._searchBox.getSearchParam();
		me.view.getDataGrid().setFilter(str);
		me.view.getDataGrid().load();
    }
    /**
     * 初始化DataGrid时执行的方法，给DataGrid增加样式等
     */
    me._initDataGrid = function(){
    	
    	var _dataGrid = me.view.getDataGrid();
    	_col_ssxt = _dataGrid.$body.find("td").not("#OBJ_ID,#HANDLERATE01,#HANDLERATE02,#rownumber");
    	
		for(var i=0;i<_col_ssxt.length;i++){//
			if("合计" != _col_ssxt[i].innerText && "" != _col_ssxt[i].innerText && 0 != _col_ssxt[i].innerText){
				_col_ssxt[i].setAttribute("class","ssxtLink");
			}
		}
		
    	_dataGrid.$body.find(".ssxtLink").click(function(e) {
			var item = $(this).parent().data();
			var colName = e.target.id;
			$(this)[0].style.color = "#999999";
			showDetail(item, colName);
		});
    }
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showDetail = function(item, colName) {
		var editor = me.view.getSearchBox().editors;
		var ssxt_searchBox = editor.ssxt.value;	//查询框内的所属系统
		var ssdw = editor.ssdw.value;			//所属单位
		var dydj = editor.dydj.value;			//电压等级
		var jclx = editor.jclx.value;			//监测类型
		var gjjb = editor.gjjb.value;			//告警级别
		var xlmc = editor.xlmc.value;			//线路名称
		var bdz = editor.bdz.value;				//变电站名称
		var gjsj = editor.gjsj.value;			//告警时间
		
		var ssxt = item.item.id;				//所属系统
		if("WSDEPMC" == colName){			//如果点击的是所属系统
			var filter = "ssxt = "+ssxt;
			var mvcs = new mainSystemssxtInfo.views.MainViewController();
			var mainSystemdetails = mvcs.getView();
			var dataGrid = mainSystemdetails.getSdDataGrid();
			mainSystemdetails.getSdDataGrid().setFilter(filter);
			mainSystemdetails.getSdDataGrid().load();
			mainSystemdetails.getBdDataGrid().setFilter(filter);
			mainSystemdetails.getBdDataGrid().load();
			mainSystemdetails.getDetailDataGrid().setFilter(filter);
			mainSystemdetails.getDetailDataGrid().load();
			
			me.view.detailWin.setWidth("900");
	    	me.view.detailWin.setHeight("700");
			me.view.detailWin.setView(mainSystemdetails);
			me.view.detailWin.showDialog();
			me.view.detailWin.setTitle(colName+"展示");
		}else{																			//如果点击的是统计数字
			var str = "";
			if(null != ssdw){
				str += "ssdw ="+ssdw+"&";
			}
			
			if(null != dydj){
				str += "dydj ="+dydj+"&";
			}
			
			if(null != jclx){
				str += "jclx ="+jclx+"&";
			}
			
			if(null != gjjb){
				str += "gjjb ="+gjjb+"&";
			}
			
			if(null != gjsj ){
				str += "gjsj = "+gjsj+"&";
			}
			
			if("GW" != ssxt){			//如果是合计
				str += "ssxt = "+ssxt+"&";
			}else{
				if(null != ssxt_searchBox && '' != ssxt_searchBox.trim()){
					str += "ssxt = "+ssxt_searchBox+"&";
				}
			}
			
			if("HCNT" == (colName.substring(0,colName.length-2))){
				str += "ishandled = T&";
			}else if("NCNT" == (colName.substring(0,colName.length-2))){
				str += "ishandled = F&";
			}
			
			if("01" == colName.substring(colName.length-2,colName.length)){							//如果是输电
				var mvcsd = new mainSystemgjxxcxtj_detail.views.MainViewController();
				var sdDetail = mvcsd.getView();
				if(null != xlmc && '' != xlmc.trim()){
					str += "xlmc = "+xlmc+"&";
				}
				 str += "type = sd &";
				
				if('' != str.trim()){
					sdDetail.getDataGrid().setFilter(str.substring(0,str.length-1));
					sdDetail.getDataGrid().load();
				}
				
				me.view.detailWin.setWidth("900");
		    	me.view.detailWin.setHeight("500");
				me.view.detailWin.setView(sdDetail);
				me.view.detailWin.showDialog();
				me.view.detailWin.setTitle("输电告警信息列表");
				
			}else if("02" == colName.substring(colName.length-2,colName.length)){						//如果是变电
				 var mvcbd = new mainSystemgjxxcxtj_detail.views.MainViewController();
				 var bdDetail = mvcbd.getView();
				 if(null != bdz && '' != bdz.trim()){
						str += "bdz = "+bdz+"&";
				 }
				 
				 str += "type = bd &";
				 
				 if('' != str.trim()){
					 bdDetail.getDataGrid().setFilter(str.substring(0,str.length-1));
					 bdDetail.getDataGrid().load();
				 }
				 
				me.view.detailWin.setWidth("900");
		    	me.view.detailWin.setHeight("500");
				me.view.detailWin.setView(bdDetail);
				me.view.detailWin.showDialog();
				me.view.detailWin.setTitle("变电告警信息列表");
			}
		}
	}
	
	/**
	 * 导出操作
	 */
	me._btnExport_click = function(){
		var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "变电告警信息查询数据"
        });
	}
    
    return me.endOfClass(arguments);
};