$import("sdkhzb.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "sdkhzb",
    name: "",
    requires:["pmsframework/mxpms","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new sdkhzb.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});