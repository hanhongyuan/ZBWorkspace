$ns("ztjcmx.galleria");

ztjcmx.galleria.ImgShow = function()
{
	var me = $extend(MXObject);
	var base = {};
	
	// 图片容器
	var imgContainer,
	// 图片src缓存
		cache,
	// 主图图片宽高, 缩略图图片宽高
		wh, thumbnailImgWH = '&w=50&h=50',
	// 	箭头样式
		arrowCss;

	base._ = me._;
	me._ = function(p_options)
	{
		if (me.canConstruct())
		{
			base._(p_options);

			// TODO: Add your constructing code here.
		}
	};
	
	me.show = function(query, imgs) {
		// 初始化图片容器
		setImgContainer(query);

		// 向略缩图容器装入图片
		me.addImgs(imgs);
		
		// 画图片容器
		draw();
		
		imgEvent();
	};
	
	function setImgContainer(query) {
		if($isEmpty(imgContainer)) initArrowCss();
		if($isEmpty(query)) {
			$('body').append($("<div id='imgShow'></div>"));
			query = "#imgShow";
		}
		imgContainer = $(query);
		imgContainer.append($("<div id='mainImg'>" +
				"<div id='left'></div><div id='right'></div>" +
				"<div id='textContent'></div><img></div>" +
				"<div id='thumbnail'></div>"));
		wh = '&w=' + imgContainer.width() + '&h=' + (imgContainer.height()-52); // 略缩图图片高度50px + 上下边框2px
		cache = [];
		
	}
	
	// 重新添加图片集
	me.reImgs = function(imgs) {
		if($isEmpty(imgContainer)) return;
		
		imgContainer.children('#thumbnail').empty();
		cache = [];
		
		me.addImgs(imgs);
	};
	
//	me.replaceImg = function(oldImg, newImg) {
//		
//	}
	
	// 添加图片集
	me.addImgs = function(imgs) {
		if($isEmpty(imgContainer)) return;
		if($isEmpty(imgs) || imgs.length == 0) {
			
			// 如果为第一次插入或重新添加图片集 但图片集又为空 就展示一张空图片
			if(cache.length == 0) {
				imgContainer.children('#mainImg').children('img').attr('src', '../ztjcmx/resource/noImg.jpg');
				imgContainer.children('#mainImg').children('#textContent').text('');
			}
			return;
		} else {

			for(var i = 0; i < imgs.length; i++) {
				addImg(imgs[i]);
			}
			// 如果为第一次插入图片集 即显示第一张图片
			if(cache.length <= imgs.length) positioning(0);
			reThumbnail();
		}
	};
	
//	内部调用不会出现多余的性能消耗 外部调用也能够执行对应的处理
	// 添加图片（外部提供调用）
	me.addImg = function(img) {
		if($isEmpty(imgContainer)) return;

		addImg(img);
		// 如果为第一次插入图片 即显示第一张图片
		if(cache.length == 1 && $notEmpty(img)) positioning(0);
		reThumbnail();
	};
	
	// 添加图片（内部提供调用）
	function addImg(img) {
		if($isEmpty(img)) return;
		
		img = $(img);
		cache.push(img[0].src); //
		img[0].src += thumbnailImgWH;
		imgContainer.children('#thumbnail').append(img);
	}
	
	me.getCount = function() {
		if($isEmpty(cache)) return 0;
		
		return cache.length;
	};
	
	function toPosition(index, direction) {
		positioning(index);

		var img = $(imgContainer.children('#thumbnail').children('img')[index]),
			// 图片容器的左边距离
			left = imgContainer.offset().left,
			// 用来判断是否在可视范围内
			fold = imgContainer.width() + left;
		
		if(direction == 'right') {
//			向右切图是否在可视范围内
			if(index == 0 && cache.length > 1) {
				
				imgContainer.children('#thumbnail').offset({ left: 0 + left });
			} else if(fold <= img.offset().left + (img.width()/2)) {
				
				var toLeft = imgContainer.children('#thumbnail').position().left - imgContainer.width();
				imgContainer.children('#thumbnail').offset({ left: toLeft + left });
			}
		} else {
//			向左切图是否在可视范围内
			if(index == cache.length-1 && cache.length > 1) {
				
				var toRight = imgContainer.width() - imgContainer.children('#thumbnail').width();
				imgContainer.children('#thumbnail').offset({ left: toRight + left });
			} else if(fold - (fold - left) >= img.offset().left + img.width()) {
				
				var toRight = imgContainer.children('#thumbnail').position().left + imgContainer.width();
				imgContainer.children('#thumbnail').offset({ left: toRight + left });
			}
		}
	}
	
	function positioning(index) {
		if(cache.length == 0) return;

		var monitor = imgContainer.children('#thumbnail').children('img')[index].dataset.monitor;
		monitor = monitor ? monitor.replace(/_/g, '   ') : '';
		
		imgContainer.children('#mainImg').children('img').attr('src', cache[index] + wh);
		imgContainer.children('#mainImg').children('#textContent').text(monitor);
		imgContainer.children('#thumbnail').children('img').css("border", "0px");
		imgContainer.children('#thumbnail').children('img')[index].style.border = "1px solid #F44336";
	}
	
	function imgEvent() {
		imgContainer.children('#mainImg').mouseover(function() {
			$('#left,#right').show();
		});
		imgContainer.children('#mainImg').mouseleave(function() {
			$('#left,#right').hide();
		});
		$('#left,#right').mouseover(function(e) {
			$(this).css({ background: getArrowCss(this.id, e.type) });
		});
		$('#left,#right').mouseleave(function(e) {
			$(this).css({ background: getArrowCss(this.id, e.type) });
		});
		$('#left,#right').click(function() {
			if(cache.length == 0) return;
			
			var imgSrc = imgContainer.children('#mainImg').children('img')[0].src.replace(wh, ''),
				index = cache.indexOf(imgSrc);
			
//			if(index == -1) return; 这种情况应该不会出现 也不应该出现
			if(this.id == "left") {
				index = index==0 ? cache.length-1 : index-1;
			} else {
				index = index==cache.length-1 ? 0 : index+1;
			}
			toPosition(index, this.id);
		});
		imgContainer.children('#thumbnail').delegate('img', 'click', function() {
			var index = cache.indexOf(this.src.replace(thumbnailImgWH, ''));
			positioning(index);
		});
	}
	
	function draw() {
		imgContainer.css({
			overflow: 'hidden'
		});
		
		imgContainer.children('#mainImg').children('img').css({
			width: imgContainer.width(),
			height: imgContainer.height() - 52 // 略缩图图片高度50px + 上下边框2px
		});
		
		imgContainer.children('#mainImg').children('#textContent').css({
			position: 'absolute',
		    color: '#9E9E9E',
		    'z-index': 5,
		    top: '80%'
		});
		
		var cutover = {
			'z-index': 2,
			position: 'absolute',
			top: '35%',
			left: '5px',
			display: 'none',
			width: '34px',
			height: '34px',
	    	background: getArrowCss('left', 'mouseleave'),
	    	'border-radius': '50%'
		};
		imgContainer.children('#mainImg').children('#left').css(cutover);
		
		cutover.right = cutover.left;
		cutover.background = getArrowCss('right', 'mouseleave');
		delete cutover['left'];
		imgContainer.children('#mainImg').children('#right').css(cutover);

		imgContainer.children('#thumbnail').css({
	    	overflow: 'hidden',
	        position: 'relative',
			height: 52 // 图片高度50px + 上下边框2px
		});
		reThumbnail();
	}
	
	function reThumbnail() {
		var w = 51 * cache.length + 2; // 图片宽度50px + 右外边距1px + 左右边框2px
		imgContainer.children('#thumbnail').css({
			width: w
		});
		// float javascript保留字 需加引号
		imgContainer.children('#thumbnail').children('img').css({
			'display': 'block',
		    'position': 'relative',
		    'margin': '0 1px 0 0',
		    'float': 'left',
	    	'visibility': 'visible'
		});
	}
	
	function getArrowCss(direction, mouseEvent) {
		return arrowCss[direction][mouseEvent];
	}
	
	function initArrowCss() {
		var background = 'url(../ztjcmx/resource/img.png) center / 40px no-repeat color';
		arrowCss = {
			left: {
				mouseover: background.replace('img', 'overLeftArrow').replace('color', '#ffffff'),
				mouseleave: background.replace('img', 'leftArrow').replace('color', '#9e9e9e')
			},
			right: {
				mouseover: background.replace('img', 'overRightArrow').replace('color', '#ffffff'),
				mouseleave: background.replace('img', 'rightArrow').replace('color', '#9e9e9e')
			}
		};
	}

	return me.endOfClass(arguments);
};