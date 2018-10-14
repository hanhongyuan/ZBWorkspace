$import("bdzzyxzkcx.views.bdzzyxzkcxMainViewController");

mx.weblets.WebletManager.register({
    id: "bdzzyxzkcx",
    name: "bdzzyxzkcx",
    requires:["pmsframework/mxpms"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdzzyxzkcx.views.bdzzyxzkcxMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});