$import("sdzzyxzkcxtj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "sdzzyxzkcxtj",
    name: "sdzzyxzkcxtj",
    requires:["sdzzyxzkcx","sdzzyxzktj"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new sdzzyxzkcxtj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});