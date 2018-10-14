$ns("bdzzcx.views");

$import("bdzzcx.views.bdzzcxDetailView");

bdzzcx.views.bdzzcxDetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new bdzzcx.views.bdzzcxDetailView({controller: me, alias:"bdzzcxDetailView"});
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    };
    

    return me.endOfClass(arguments);
};