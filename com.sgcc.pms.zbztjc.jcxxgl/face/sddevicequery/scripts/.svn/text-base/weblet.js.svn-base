$import("sddevicequery.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "sddevicequery",
    name: "",
    requires:["sddevicetj","sdtjdetail","com.sgcc.pms.zbztjc.util/openChart","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new sddevicequery.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});