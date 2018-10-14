$import("bdzztj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "bdzztj",
    name: "bdzztj",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdzztj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});