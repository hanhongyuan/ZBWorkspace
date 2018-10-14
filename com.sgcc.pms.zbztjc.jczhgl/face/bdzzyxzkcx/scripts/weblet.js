$import("bdzzyxzkcx.views.bdzzyxzkcxMainViewController");

mx.weblets.WebletManager.register({
    id: "bdzzyxzkcx",
    name: "",
    requires:["pmsframework/mxpms","com.sgcc.pms.zbztjc.util/openChart"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new bdzzyxzkcx.views.bdzzyxzkcxMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});