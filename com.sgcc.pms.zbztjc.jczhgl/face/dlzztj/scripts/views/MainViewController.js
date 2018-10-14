$ns('dlzztj.views');

$import('dlzztj.views.MainView');

dlzztj.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new dlzztj.views.MainView( { controller: me } );
        }
        
        return me.view;
    };
       
    
    me._onactivate = function()
    {
       
    };
    
    return me.endOfClass(arguments);
};