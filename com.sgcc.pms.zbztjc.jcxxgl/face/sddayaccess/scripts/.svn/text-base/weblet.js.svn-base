$import("sddayaccess.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "sddayaccess",
    name: "",
    requires:["pmsframework/mxpms","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new sddayaccess.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});