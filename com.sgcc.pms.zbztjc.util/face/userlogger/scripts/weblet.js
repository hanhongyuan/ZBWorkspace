$import("userlogger.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "userlogger",
    name: "userlogger",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new userlogger.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});