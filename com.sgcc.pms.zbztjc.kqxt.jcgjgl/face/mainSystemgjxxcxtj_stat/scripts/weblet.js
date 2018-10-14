$import("mainSystemgjxxcxtj_stat.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "mainSystemgjxxcxtj_stat",
    name: "mainSystemgjxxcxtj_stat",
    requires:["pmsframework/mxpms","mainSystemssxtInfo","mainSystemgjxxcxtj_detail"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new mainSystemgjxxcxtj_stat.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});