$import("mainSystemssxtInfo.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "mainSystemssxtInfo",
    name: "mainSystemssxtInfo",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new mainSystemssxtInfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});