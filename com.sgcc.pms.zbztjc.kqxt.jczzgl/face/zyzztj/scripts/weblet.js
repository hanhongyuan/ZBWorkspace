$import("zyzztj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "zyzztj",
    name: "zyzztj",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new zyzztj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});