$import("sdzztj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "sdzztj",
    name: "sdzztj",
    requires:["pmsframework/mxpms","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new sdzztj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});