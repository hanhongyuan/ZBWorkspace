$import("bdgjxxcxtj_stat.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "bdgjxxcxtj_stat",
    name: "bdgjxxcxtj_stat",
    requires:["pmsframework/mxpms","bdgjxxDetail"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdgjxxcxtj_stat.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});