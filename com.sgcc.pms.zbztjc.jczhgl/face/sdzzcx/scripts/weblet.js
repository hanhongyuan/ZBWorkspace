$import("sdzzcx.views.sdzzcxMainViewController");

mx.weblets.WebletManager.register({
    id: "sdzzcx",
    name: "sdzzcx",
    requires:["pmsframework/mxpms","com.sgcc.pms.zbztjc.util/openChart"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new sdzzcx.views.sdzzcxMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});