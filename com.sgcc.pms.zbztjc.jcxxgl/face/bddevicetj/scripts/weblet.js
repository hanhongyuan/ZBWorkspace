$import("bddevicetj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "bddevicetj",
    name: "",
    requires:["pmsframework/mxpms","bdtjdetail","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bddevicetj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});