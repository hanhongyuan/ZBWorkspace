$import("zyxtjczzjrqk.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "zyxtjczzjrqk",
    name: "主要系统监测装置接入情况",
    requires: ["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new zyxtjczzjrqk.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});