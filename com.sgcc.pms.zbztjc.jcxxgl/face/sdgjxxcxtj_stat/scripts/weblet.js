$import("sdgjxxcxtj_stat.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "sdgjxxcxtj_stat",
    name: "sdgjxxcxtj_stat",
    requires:["pmsframework/mxpms","sdgjxxDetail","com.sgcc.pms.zbztjc.util/userlogger"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new sdgjxxcxtj_stat.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});