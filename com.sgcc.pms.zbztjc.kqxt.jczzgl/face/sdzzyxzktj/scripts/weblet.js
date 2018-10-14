$import("sdzzyxzktj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "sdzzyxzktj",
    name: "sdzzyxzktj",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new sdzzyxzktj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});