$import("tgypz.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "tgypz",
    name: "特高压配置",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new tgypz.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});