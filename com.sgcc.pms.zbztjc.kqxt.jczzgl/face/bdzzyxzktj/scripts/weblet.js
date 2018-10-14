$import("bdzzyxzktj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "bdzzyxzktj",
    name: "bdzzyxzktj",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdzzyxzktj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});