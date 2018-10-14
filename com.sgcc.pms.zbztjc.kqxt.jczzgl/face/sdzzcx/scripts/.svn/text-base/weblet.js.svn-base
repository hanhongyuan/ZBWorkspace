$import("sdzzcx.views.sdzzcxMainViewController");

mx.weblets.WebletManager.register({
    id: "sdzzcx",
    name: "Dynamic Bind Model",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new sdzzcx.views.sdzzcxMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});