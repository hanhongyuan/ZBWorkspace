$import("dlzzcx.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "dlzzcx",
    name: "",
    requires:["pmsframework/mxpms","com.sgcc.pms.zbztjc.util/openChart"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new dlzzcx.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});