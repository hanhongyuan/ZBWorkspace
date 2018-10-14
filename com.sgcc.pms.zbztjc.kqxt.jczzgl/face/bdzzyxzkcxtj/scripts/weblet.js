$import("bdzzyxzkcxtj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "bdzzyxzkcxtj",
    name: "bdzzyxzkcxtj",
    requires:["bdzzyxzkcx","bdzzyxzktj"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdzzyxzkcxtj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});