$import("deptip.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "deptip",
    name: "",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new deptip.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});