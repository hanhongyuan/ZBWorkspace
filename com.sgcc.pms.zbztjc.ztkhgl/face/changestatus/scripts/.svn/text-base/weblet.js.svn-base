$import("changestatus.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "changestatus",
    name: "",
    requires:["audiInfo","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new changestatus.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});