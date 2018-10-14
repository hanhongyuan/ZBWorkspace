$import("dlzzcxtj.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "dlzzcxtj",
    name: "Dynamic Bind Model",
    requires: ["dlzzcx","dlzztj"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
    	var mvc = new dlzzcxtj.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});