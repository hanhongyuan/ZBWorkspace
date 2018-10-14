$import("mainSystemgjxxcxtj_query.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "mainSystemgjxxcxtj_query",
    name: "mainSystemgjxxcxtj_query",
    requires:["pmsframework/mxpms"],
    onload: function(e){
    	
    },
    onstart: function(e)
    {
        var mvc = new mainSystemgjxxcxtj_query.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});