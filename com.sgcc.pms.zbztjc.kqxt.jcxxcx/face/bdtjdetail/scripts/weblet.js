$import("bdtjdetail.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "bdtjdetail",
    name: "",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdtjdetail.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});