$ns("bdgjxxcxtj_query.views");

$import("bdgjxxcxtj_query.views.MainView");
$import("bdgjxxcxtj_query.views.bdgjxxcxtj_queryDetailViewController");
$import("ztjcSynPms.views.MainViewController");
$import("mx.rpc.RESTClient");
$include("~/com.sgcc.pms.zbztjc.util/enum/commonFunction.js");
bdgjxxcxtj_query.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me._searchBox = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdgjxxcxtj_query.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    };
    
  //查询的方法
    me._btnQuery_click = function()
    {
    	var param = me.view.getSearchBox().getSearchParam();
    	me.view.getDataGrid().setFilter(param);
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
        var mvc = new bdgjxxcxtj_query.views.bdgjxxcxtj_queryDetailViewController();
        _detailView = mvc.getView();
        
        _detailView.getForm().entityContainer.on("load",function(){
        var _tabControl = _detailView.getTabcontrol();
       	var client = new mx.rpc.RESTClient();	//初始化RESTClient
       	var restUrl = "~/rest/bdgjxxcxtj_query/queryDatailById/"+objId;	//要执行的Controller方法
   		client.get(bdgjxxcxtj_query.mappath(restUrl), {"objId":objId}, function(e) {
   			
			_detailView.getForm().entityContainer.data.setValue("objId",e[0].OBJ_ID) ;
			_detailView.getForm().entityContainer.data.setValue("LINKEDSTATIONNAME",e[0].LINKEDSTATIONNAME) ;
			_detailView.getForm().entityContainer.data.setValue("LINKEDEQUIPMENTNAME",e[0].LINKEDEQUIPMENTNAME) ;
			_detailView.getForm().entityContainer.data.setValue("DEVICECODE",e[0].DEVICECODE) ;
			_detailView.getForm().entityContainer.data.setValue("MONITORINGTYPE",e[0].MONITORINGTYPE) ;
			_detailView.getForm().entityContainer.data.setValue("monitoringTypeName",e[0].TYPENAME) ;
			_detailView.getForm().entityContainer.data.setValue("ALARMTIME",e[0].ALARMTIME) ;
			_detailView.getForm().entityContainer.data.setValue("ALARMENDTIME",e[0].ALARMENDTIME) ;
			_detailView.getForm().entityContainer.data.setValue("CONTINUANCETIME",e[0].CONTINUANCETIME) ;
			_detailView.getForm().entityContainer.data.setValue("ALARMLEVEL",e[0].ALARMLEVEL) ;
			_detailView.getForm().entityContainer.data.setValue("ALARMMESSAGE",e[0].ALARMMESSAGE) ;
			_detailView.getForm().entityContainer.data.setValue("ALARMRULE",e[0].ALARMRULE) ;
			_detailView.getForm().entityContainer.data.setValue("ALARMSOURCE",e[0].ALARMSOURCE) ;
			_detailView.getForm().entityContainer.data.setValue("ALARMCONSTRUE",e[0].ALARMCONSTRUE) ;
			_detailView.getForm().entityContainer.data.setValue("DISPOSALADVICE",e[0].DISPOSALADVICE) ;
			_detailView.getForm().entityContainer.data.setValue("DISPOSALTIME",e[0].DISPOSALTIME) ;
			_detailView.getForm().entityContainer.data.setValue("DISPOSALRESULT",e[0].DISPOSALRESULT) ;
			_detailView.getForm().entityContainer.data.setValue("TRANSACTOR",e[0].TRANSACTOR) ;
			_detailView.getForm().entityContainer.data.setValue("CHECKTIME",e[0].CHECKTIME) ;
			_detailView.getForm().entityContainer.data.setValue("CHECKEDBY",e[0].CHECKEDBY) ;
			_detailView.getForm().entityContainer.data.setValue("ISHANDLED",e[0].ISHANDLED) ;
			_detailView.getForm().entityContainer.data.setValue("REMARKS",e[0].REMARKS) ;

   			//修改Pages页第二个的text值
   			_tabControl.pages[1].setText(e[0].TYPENAME+"记录");
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
	  	/*var _params = null;        
        var xls = new mxpms.utils.CommUtil();
        _params = me.view.getDataGrid().filter;
 
        xls.exportToExcel(me.view.getDataGrid(),{
            params : {params : JSON.stringify({filter : _params})},
            filename : "变电告警信息查询"
        });*/
		  try {
      		var _dataGrid = me.view.getDataGrid();
          	exportToExcel(_dataGrid,"变电告警信息查询");
  		} catch (e) {
  			mx.indicate("info","导出失败");
  		}
	  }

	  me._synPmsBtn_click = function(){
	  	var ztjcSynPmsView = ztjcSynPms.views.MainViewController().getView();
	  	var win = me.view.getZtjcSynPmsWindow();
    	if(typeof ztjcSynPmsView != "undefined"){
    		win.setView(ztjcSynPmsView);
    		win.setTitle("同步PMS数据");
    	}
    	win.showDialog();
	  }
    
    return me.endOfClass(arguments);
};