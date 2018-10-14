$import("chart.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "chart",
    name: "组态图",
    requires: ["ztjcChart"],//,"GasInOil"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new chart.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});