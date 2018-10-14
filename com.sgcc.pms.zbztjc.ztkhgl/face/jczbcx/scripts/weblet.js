$import("jczbcx.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "jczbcx",
    name: "",
    requires:["pmsframework/mxpms","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new jczbcx.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});