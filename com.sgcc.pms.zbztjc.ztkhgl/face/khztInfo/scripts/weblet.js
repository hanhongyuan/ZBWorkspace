$import("khztInfo.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "khztInfo",
    name: "",
    requires:["com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new khztInfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});