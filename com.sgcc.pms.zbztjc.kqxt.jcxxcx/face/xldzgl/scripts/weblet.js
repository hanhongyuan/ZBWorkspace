$import("xldzgl.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "xldzgl",
    name: "",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new xldzgl.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});