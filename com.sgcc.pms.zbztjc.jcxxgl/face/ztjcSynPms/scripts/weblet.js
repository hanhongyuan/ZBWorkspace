$import("ztjcSynPms.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "ztjcSynPms",
    name: "ztjcSynPms",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new ztjcSynPms.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});