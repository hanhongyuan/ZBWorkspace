$ns("sdgjxxcxtj_query.views");

$import("sdgjxxcxtj_query.views.MainView");
$import("sdgjxxcxtj_query.views.sdgjxxcxtj_queryDetailViewController");
$import("mx.rpc.RESTClient");

sdgjxxcxtj_query.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdgjxxcxtj_query.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	
    };
    
    //查询的方法
    me._btnQuery_click = function()
    {
    	me._searchBox = me.view.getSearchBox();	//获取
    	//var param = me._searchBox.getSearchParam();
    	var ssdw = me._searchBox.editors.ssdw.value;		//所属单位的值
    	var dydj = me._searchBox.editors.dydj.value;		//电压等级的值
    	var jclx = me._searchBox.editors.jclx.value;		//监测类型的值
    	var xlmc = me._searchBox.editors.xlmc.value;		//线路名称的值
    	var gjjb = me._searchBox.editors.gjjb.value;		//告警级别的值
    	var isHandled = me._searchBox.editors.isHandled.value;		//是否处理的值
    	var gjsj = me._searchBox.editors.gjsj.value;		//告警时间的值
    	
    	var filter = new Array();
    	if(null != ssdw && '' != ssdw.trim()){
    		filter.push("ssdw = " + ssdw);
    	}
    	
    	if(null != dydj && '' != dydj.trim()){
    		filter.push("dydj = " + dydj);
    	}
    	
    	if(null != jclx && '' != jclx.trim()){
    		filter.push("jclx = " + jclx);
    	}
    	
    	if(null != xlmc && '' != xlmc.trim()){
    		filter.push("xlmc = " + xlmc);
    	}
    	
    	if(null != gjjb && '' != gjjb.trim()){
    		filter.push("gjjb = " + gjjb);
    	}
    	
    	if(null != isHandled && '' != isHandled.trim()){
    		filter.push("isHandled = " + isHandled);
    	}
    	
    	if(null != gjsj){
    		filter.push("gjsj = " + gjsj);
    	}
    	
    	var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		
		me.view.getDataGrid().setFilter(str.substring(0, str.length - 1));
		me.view.getDataGrid().load();
    }
    
    me._btnQueryDetail_click = function()
    {
     	var v_dataGrid = me.view.getDataGrid();
     	if (v_dataGrid.getCheckedIDs().length == 0)
         {
              mx.indicate("info", "请勾选一条待编辑记录。");
              return;
         }
     	if(v_dataGrid.getCheckedIDs().length > 1)
     	{
     	       mx.indicate("info", "选定的记录条数不能超过一条。");
     	       return;
     	}
     	var _detailView = _getDetailFromView(v_dataGrid.getCheckedIDs()[0]);
	    _showDetailFormView(_detailView,"查看告警详细信息");
    }
    
    /**
     * 获取表单视图对象
     */
    function _getDetailFromView(objId){
    	_detailView = null;
       var mvc = new sdgjxxcxtj_query.views.sdgjxxcxtj_queryDetailViewController();
       _detailView = mvc.getView();
        
       _detailView.getForm().entityContainer.on("load",function(){
       	var client = new mx.rpc.RESTClient();	//初始化RESTClient
       	var restUrl = "~/rest/sdgjxxcxtj_query/queryDatailById/"+objId;	//要执行的Controller方法
   		client.get(sdgjxxcxtj_query.mappath(restUrl), {"objId":objId}, function(e) {
   			
   			_detailView.getForm().entityContainer.data.setValue("objId",e[0].OBJID) ;
			_detailView.getForm().entityContainer.data.setValue("linkedlineName",e[0].LINKEDLINENAME) ;
			_detailView.getForm().entityContainer.data.setValue("linkedpoleName",e[0].LINKEDPOLENAME) ;
			_detailView.getForm().entityContainer.data.setValue("devicecode",e[0].DEVICECODE) ;
			_detailView.getForm().entityContainer.data.setValue("MONITORINGTYPE",e[0].MONITORINGTYPE) ;
			_detailView.getForm().entityContainer.data.setValue("monitoringtypeName",e[0].TYPENAME) ;
			_detailView.getForm().entityContainer.data.setValue("alarmtime",e[0].ALARMTIME) ;
			_detailView.getForm().entityContainer.data.setValue("alarmlevel",e[0].ALARMLEVEL) ;
			_detailView.getForm().entityContainer.data.setValue("alarmmessage",e[0].ALARMMESSAGE) ;
			_detailView.getForm().entityContainer.data.setValue("alarmsource",e[0].ALARMSOURCE) ;
			_detailView.getForm().entityContainer.data.setValue("alarmconstrue",e[0].ALARMCONSTRUE) ;
			_detailView.getForm().entityContainer.data.setValue("disposaladvice",e[0].DISPOSALADVICE) ;
			_detailView.getForm().entityContainer.data.setValue("disposaltime",e[0].DISPOSALTIME) ;
			_detailView.getForm().entityContainer.data.setValue("disposalresult",e[0].DISPOSALRESULT) ;
			_detailView.getForm().entityContainer.data.setValue("transactor",e[0].TRANSACTOR) ;
			_detailView.getForm().entityContainer.data.setValue("alarmendtime",e[0].ALARMENDTIME) ;
			_detailView.getForm().entityContainer.data.setValue("continuancetime",e[0].CONTINUANCETIME) ;
			_detailView.getForm().entityContainer.data.setValue("ishandled",e[0].ISHANDLED) ;
			_detailView.getForm().entityContainer.data.setValue("remarks",e[0].REMARKS) ;
   		});
   		
        });
    	return _detailView;
    }
    
    /**
     * 显示表单视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showDetailFormView(p_view,p_title){
    	var win = me.view.getDetailWindow();
    	if(typeof p_view != "undefined"){
    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
    me._btnExport_click = function(){
    	var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "输电告警信息数据列表"
        });
    }
    
    
    
    return me.endOfClass(arguments);
};