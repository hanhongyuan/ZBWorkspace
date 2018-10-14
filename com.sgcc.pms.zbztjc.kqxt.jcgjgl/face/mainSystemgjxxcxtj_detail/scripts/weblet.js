$import("mainSystemgjxxcxtj_detail.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "mainSystemgjxxcxtj_detail",
    name: "mainSystemgjxxcxtj_detail",
    requires:["mainSystemgjxxcxtj_stat"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new mainSystemgjxxcxtj_detail.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});