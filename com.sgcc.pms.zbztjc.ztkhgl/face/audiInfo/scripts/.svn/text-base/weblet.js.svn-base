$import("audiInfo.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "audiInfo",
    name: "",
    requires:["pmsframework/mxpms","changestatus","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new audiInfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});