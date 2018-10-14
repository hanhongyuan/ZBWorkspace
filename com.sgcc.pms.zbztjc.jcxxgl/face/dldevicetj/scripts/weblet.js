$import("dldevicetj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "dldevicetj",
    name: "",
    requires:["pmsframework/mxpms","dltjdetail","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new dldevicetj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});