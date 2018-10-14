$import("devicegeneral.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "devicegeneral",
    name: "",
    requires:["generalchar","generaldetail"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new devicegeneral.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});