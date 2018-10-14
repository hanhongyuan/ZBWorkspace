$import("echartsdetail.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "echartsdetail",
    name: "",
    requires:["bdechartsdetail","dlechartsdetail"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new echartsdetail.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});