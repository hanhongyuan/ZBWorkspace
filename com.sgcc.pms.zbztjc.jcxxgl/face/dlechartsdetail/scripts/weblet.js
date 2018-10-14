$import("dlechartsdetail.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "dlechartsdetail",
    name: "",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new dlechartsdetail.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});