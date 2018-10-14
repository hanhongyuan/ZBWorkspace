$ns("userlogger.views");

$import("userlogger.views.DetailView");

userlogger.views.DetailViewController = function(){
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function(){
        if (me.view == null){
            me.view = new userlogger.views.DetailView({controller: me, alias:"userOperationDetailView"});
        }
        return me.view;
    };

    me._onactivate = function(e){
       // TODO: 窗体激活时的逻辑。
		me.Queryinfo();
    };
    me.Queryinfo = function(){
    	var dataGrid = me.getView().getDataGrid1();
    	dataGrid.load();
    };
    
    return me.endOfClass(arguments);
};