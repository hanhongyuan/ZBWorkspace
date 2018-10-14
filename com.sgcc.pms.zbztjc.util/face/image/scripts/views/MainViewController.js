$ns('image.views');

$import('image.views.MainView');

image.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    
    me.params = {};
    me.chartUrl = image.mappath("~/rest/chart/getImage");
    me.latestUrl = image.mappath("~/rest/chart/getImgLatest");
    me.imgUrl = image.mappath("~/rest/chart/getImgOne?objId=");
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new image.views.MainView( { controller: me } );
        }
        
        return me.view;
    };
    
    me._onactivate = function()
    {
    	initParams();
    	initDeviceName();
    	
    	loadImgs(true);
//    	loadLatest();
    };
    
    function loadImgs(first) {
    	var filter = me.view.getValue("查询框");
    	if(filter == 'x') {
    		var startDate = me.view.getValue("开始日期");
        	var endDate = me.view.getValue("结束日期");
        	filter = Date.parse(startDate)>Date.parse(endDate) ? "结束日期不能小于开始日期" : 
        		((Date.parse(endDate)-Date.parse(startDate))/60000/60/24>90 ? "日期区间不能大于3个月" : startDate + ',' + endDate);
    	}
    	if(filter.length > 2 && filter.split(',').length <= 1) {
    		alert(filter);
    		return;
    	}
    	
    	var params = { filter: filter };
    	me.view.client.post(me.chartUrl, {params: JSON.stringify(params)}, function(result) {
    		if(result.successful) {
    			var items = result.resultValue.items,
    				names = items[items.length-1],
    				imgs = [];
    			
	    		for(var i = 0; i < items.length-1; i++) {
	    			imgs[i] = "<img src='"+ me.imgUrl + items[i][items[i].length-2] +"' data-monitor='";
	    			for(var j = 0; j < names.length; j++) {
	    				if(j > 0) imgs[i] += '_';
	    				imgs[i] += names[j] + ': ' + (items[i][j] ? items[i][j] : '无');
	    			}
	    			imgs[i] += "'>";
	    		}
	    		if(first){ 
    				me.view.imgShow.show("#showImg", imgs);
    			}else{ 
    				me.view.imgShow.reImgs(imgs);
    				me.view.showCount();
    			}
    		} else {
    			error(result);
    		}
    	});
    }
    
    function loadLatest() {
    	me.view.client.post(me.latestUrl, function(result) {
    		if(result.successful) {
    			var items = result.resultValue.items;
				var names = items[items.length-1];
    			var text = "最新数据: <br>";
    			
	    		if(items.length == 1) {
//	    			text += "未采集<br>";
	    			for(var i = 0; i < names.length; i++) {
	        			text += names[i] + ": 未采集<br>";
	        		}
	    			text += "图片: <a href='javascript:void(0)' onclick='showImg(\"noImg\")'>点击查看</a>";
	    		} else {
	    			var latestData = items[0];
//	    				text = '最新数据：';
	    			for(var i = 0; i < names.length; i++) {
	    				text += names[i] +": "+ (latestData[i] ? latestData[i] : '未采集') +"<br>";
	    			}
	    			text += "图片: <a href='javascript:void(0)' onclick='showImg(\"" +latestData[latestData.length-1] +"\")'>点击查看</a>";
	    		}
    			me.view.latest.append($("<div style='font-size: 15px;'>" +text +"</div>"));
    		} else
    			error(result);
    	});
    }
    
    showImg = function(objId) {
    	var imgEditor = me.view.getControl("最新图片");
    	
    	if(objId == 'noImg')
    		imgEditor.setValue('../ztjcmx/resource/noImg.jpg');
    	else
    		imgEditor.setValue(me.imgUrl+objId);
    	imgEditor.$e.children('img').css({
    		width: '100%', 
    		height: '100%'
    	});
    	me.view.imgPanel.show();
    };
    
    me._labelClick = function() {
    	// wait to go...
    };
    
    me._queryClick = function() {
    	loadImgs(false);
    };
    
    me._serachChanged = function(e) {
    	var stratDateControl = me.view.getControl("开始日期");
    	var endDateControl = me.view.getControl("结束日期")
    	if(e.item.getValue() == 'x') {
    		stratDateControl.setReadOnly(false);
    		endDateControl.setReadOnly(false);
    	} else {
    		stratDateControl.setReadOnly(true);
	    	endDateControl.setReadOnly(true);
    	}
    };
    
    me._closeImg = function() {
    	me.view.imgPanel.hide();
    };
    
    function error(result) {
    	mx.indicate("info", "获取数据出错，详细信息请看控制台。");
		console.log(result);
    }
    
    function initDeviceName() {
		var params = { filter: me.params.MONITORINGTYPE + "," + me.params.DEVICECODE + "," + me.params.LINKEDDEPWS };
		var result = me.view.client.getSync(ztjcmx.mappath('~/rest/chart/getDeviceName'), {params: JSON.stringify(params)});
		
		if(result.successful) {
			var items = result.resultValue.items;
			var text = '无名称';
			if($notEmpty(items) && items.length > 0) {
				text = items[0];
			}
			var width = (text.length + 1) * 2.5;
			me.view.label.setText(text);
			me.view.label.setCss({
				width: width + '%'
			});
		} else
			error(result);
    }
    
    function initParams() {
    	var context = location.search;
		if(context.indexOf("?") != -1) {
			var params = context.split("?")[1].split("&");
			for(var i = 0; i < params.length; i++) {
				me.params[params[i].split("=")[0]] = params[i].split("=")[1];
			}
		} else {
			alert('未传 DEVICECODE、MONITORINGTYPE 参数');
		}
    }
    
    return me.endOfClass(arguments);
};