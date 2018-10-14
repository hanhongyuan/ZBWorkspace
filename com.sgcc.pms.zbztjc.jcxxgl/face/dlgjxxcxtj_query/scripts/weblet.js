$import("dlgjxxcxtj_query.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "dlgjxxcxtj_query",
    name: "dlgjxxcxtj_query",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new dlgjxxcxtj_query.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});