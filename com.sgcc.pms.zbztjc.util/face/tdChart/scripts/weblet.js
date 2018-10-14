$import("tdChart.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "tdChart",
    name: "tdChart",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new tdChart.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});