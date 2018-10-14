$import("bdzzjrqk.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "bdzzjrqk",
    name: "变电装置接入情况",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdzzjrqk.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});