$import("bdzzcx.views.bdzzcxMainViewController");

mx.weblets.WebletManager.register({
    id: "bdzzcx",
    name: "bdzzcx",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdzzcx.views.bdzzcxMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});