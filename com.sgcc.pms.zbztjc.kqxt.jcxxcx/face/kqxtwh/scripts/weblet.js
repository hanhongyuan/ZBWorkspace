$import("kqxtwh.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "kqxtwh",
    name: "",
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new kqxtwh.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});