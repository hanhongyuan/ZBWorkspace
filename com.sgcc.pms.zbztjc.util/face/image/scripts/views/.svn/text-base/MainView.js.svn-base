$ns("image.views");

$import("mx.containers.Container");
$import("mx.rpc.RESTClient");
$import("mx.editors.LinkEditor");
$import("mx.editors.PictureEditor");
$import("mx.editors.CheckListEditor");
$import("mx.editors.DateTimeEditor");
$import("mx.controls.Button");
$import("mx.controls.Label");

$import("ztjcmx.galleria.ImgShow");

image.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        
        _initControls();
    };
    
    function _initControls()
    {
    	_initUtil();
    	_initViews();
        _initEvents();

        me.on("activate", me.controller._onactivate);
    }
    
    function _initUtil() {
    	me.client = new mx.rpc.RESTClient();
    	// 自定义图片展示类
    	me.imgShow = new ztjcmx.galleria.ImgShow();
    }
    
    function _initViews() {
    	// 页面标题
    	me.label = new mx.editors.LinkEditor({
		    "type" : "text",  //指定链接的类型。
		    "text" : "?",
		    onclick: me.controller._labelClick
		});
    	
		// 搜索工具栏
		me.toolBar = new mx.containers.Container({ id: "toolBarView" });
		
		// 日期条件
		var checkListEditor = new mx.editors.CheckListEditor({
			type: "radio",
			items: [
			    {text: "近3天", value: "3", width: 65, checked: true},
			    {text: "近1周", value: "7", width: 65},
			    {text: "近1月", value: "30", width: 65},
			    {text: "时间范围:", value: "x"}
			],
			columns: 4,
		    onitemchanged: me.controller._serachChanged
		});
		var date = new Date();
		var eDate = date.toLocaleDateString().replace(/\//g, '-');
    	date.setDate(date.getDate()-3);
    	var sDate = date.toLocaleDateString().replace(/\//g, '-');
    	// 开始日期
    	var startDate = new mx.editors.DateTimeEditor({
    	    value: sDate,
    	    readOnly: true,
    	    formatString: "yyyy-MM-dd"
    	});
    	// 结束日期
    	var endDate = new mx.editors.DateTimeEditor({
    	    value: eDate,
    	    readOnly: true,
    	    formatString: "yyyy-MM-dd"
    	});
		var labelEditor = new mx.controls.Label({ text: "至" });
		
		var queryButton = new mx.controls.Button({
		    text: "查询"
		});
		
		me.toolBar.addControls([checkListEditor, startDate, labelEditor, endDate, queryButton]);
		
		me.countLabel = new mx.controls.Label();
		
		// 页面中心
		me.container = new mx.containers.Container();
		
		// 图片相册
		me.showImg = new mx.containers.Container({ id: "showImg" });
		
    	
    	me.container.addControls([me.showImg]);
    	
    	me.imgPanel = new mx.containers.Container();
    	var close = new mx.editors.LinkEditor({
		    "type" : "text",  //指定链接的类型。
		    "text" : "",
		    onclick: me.controller._closeImg
    	});
    	var img = new mx.editors.PictureEditor();
    	me.imgPanel.addControls([close, img]);
    	
    	addControl([me.label, me.toolBar, me.countLabel, me.container, me.imgPanel]);
    	drawPage();
    }
    
    function _initEvents() {
    	me.getControl("查询按钮").on("click", me.controller._queryClick);
    }
    
    function drawPage() {
    	me.label.setCss({
    		height: '4%',
	        'text-align': 'right'
    	});
    	me.label.$e[0].firstChild.firstChild.style.fontSize = '20px';
    	
    	me.toolBar.setCss({ 
    		width: '95%',
        	height: '7%',
        	margin: 'auto'
    	});
    	
    	me.getControl("查询框").setCss({
    		width: '42%',
	        height: 'auto',
	        top: '20%',
	        position: 'relative',
	        display: 'inline-block'
    	});
    	
    	me.getControl("开始日期").setCss({ width: '15%' });
    	
    	me.getControl("至").setCss({ width: '3%', 'text-align': 'center' });
    	
    	me.getControl("结束日期").setCss({ width: '15%' });
    	
    	me.getControl("查询按钮").setCss({ 'margin-left': '5%' });
    	
    	me.countLabel.setCss({
    	    width: '19%',
    	    height: '7%',
    	    'text-align': 'center'
    	});
    	me.countLabel.$e[0].firstChild.style.fontSize = '15px';
    	
    	me.container.setCss({
    	    width: '95%',
	        height: '80%',
	        margin: 'auto'
    	});
    	
    	me.showImg.setCss({ 
    		width: '90%', 
    		height: '100%',
    	    'float': 'left'
    	});
    	

    	
    	me.imgPanel.setCss({
    		position: 'absolute',
    		top: '0%',
	        left: '0%',
	        right: '0%',
	        'background-color': 'rgba(0,0,0,0.5)',
	        display: 'none',
	        'z-index': 5
    	});
    	
    	me.getControl("图片关闭").setCss({
    		height: '30px',
	        width: '30px',
	        'margin-left': '82%',
	        'z-index': 5,
	        position: 'absolute',
	        background: 'url("../ztjcmx/resource/close.png") -3px -2px / 31px no-repeat',
	        cursor: 'pointer'
    	});
    	
    	me.getControl("最新图片").setCss({
    		 width: '60%', 
    		 height: '100%',
    		 margin: 'auto'
    	});
    }
    
    me.showCount = function() {
    	me.countLabel.setText("当前共"+ me.imgShow.getCount() +"张图片");
    };
    
    me.getValue = function(name) {
    	return me.getControl(name).value;
    };
    
    me.getControl = function(name) {
    	switch (name) {
			case "查询按钮":
				return me.toolBar.controls[4];
				break;
			case "查询框":
				return me.toolBar.controls[0];
				break;
			case "开始日期":
				return me.toolBar.controls[1];
				break;
			case "结束日期":
				return me.toolBar.controls[3];
				break;
			case "图表":
				return me.container.controls[0];
				break;
			case "至":
				return me.toolBar.controls[2];
				break;
			case "最新图片":
				return me.imgPanel.controls[1];
				break;
			case "图片关闭":
				return me.imgPanel.controls[0];
				break;

			default:
				break;
		}
    };
    
    function addControl(controls) {
    	for(var i = 0; i < controls.length; i++) {
    		 me.addControl(controls[i]);
    	}
    }
    
    return me.endOfClass(arguments);
};