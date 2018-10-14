$import("zyxtdetail.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "zyxtdetail",
    name: "",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new zyxtdetail.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});