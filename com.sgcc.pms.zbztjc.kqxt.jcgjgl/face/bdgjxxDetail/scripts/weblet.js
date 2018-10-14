$import("bdgjxxDetail.views.gjxxDetailMainViewController");

mx.weblets.WebletManager.register({
    id: "bdgjxxDetail",
    name: "bdgjxxDetail",
    requires:["pmsframework/mxpms","bdgjxxcxtj_stat"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdgjxxDetail.views.gjxxDetailMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});