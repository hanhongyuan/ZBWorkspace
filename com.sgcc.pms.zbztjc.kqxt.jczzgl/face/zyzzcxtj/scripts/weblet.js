$import("zyzzcxtj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "zyzzcxtj",
    name: "zyzzcxtj",
    requires:["zyzzcx","zyzztj"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new zyzzcxtj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});