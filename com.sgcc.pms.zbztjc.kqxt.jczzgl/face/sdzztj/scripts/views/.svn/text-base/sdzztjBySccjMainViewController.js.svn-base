$ns("sdzztj.views");
$import("sdzztj.views.sdzztjBySccjMainView");
$import("sdzztj.views.sdzzxxDetailMainViewController");

sdzztj.views.sdzztjBySccjMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new sdzztj.views.sdzztjBySccjMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {

    };
    
   me._initSdSccjDataGrid = function(){
	    var _dataGrid = me.view.getDataGrid();
	    	_col = _dataGrid.$body.find("td").not("#OBJ_ID,#SSWSMC,#ALLFGL,#FGL37,#rownumber,#ALLZZZS");
	    	for(var i=0;i<_col.length;i++){
				if("0" != _col[i].innerText && "" != _col[i].innerText ){
		    		_col[i].setAttribute("class","sdzztjLink");
		    	}
			}
	    	_dataGrid.$body.find(".sdzztjLink").click(function(e) {
				var item = $(this).parent().data();
				var colName = e.target.id;
				$(this)[0].style.color = "#999999";
				showSccjDetail(item, colName);
			});
    }
    
    /**
	 * 故障信息列表,点击统计数据时进行查询，弹窗对应的详细列表
	 */
	showSccjDetail = function(item, colName) {
		if(colName.substring(0,2)=="ZS"){
		
		sdzzcxtjDetailView = null;
		if (sdzzcxtjDetailView == null) {
			var mvc = new sdzztj.views.sdzzxxDetailMainViewController();
			sdzzcxtjDetailView = mvc.getView();
		}var editor = me.view.controls[0].editors;
		var dydj = editor.dydj.value;		//电压等级
		var xlmc = editor.xlmc.value;		//线路名称
		var ssdw = editor.ssdw.value;		//所属单位
		var tyrq = editor.tyrq.value;		//投运日期
		var sccj = item.item.values.SSWSMC;						//生产厂家
		sdzzcxtjDetailView.searchBox.editors[5].setValue(sccj);
		var sccj_searchBox =  editor.sccj.value;;		//查询框内的生产厂家
		var jclx = colName.substring(colName.length-6,colName.length);		//监测类型
		var type = null;

		var filter = new Array();
		
		
		if(null != jclx){		//如果监测类型不为空
    		filter.push("jclx = " + jclx);
    	}
		
		if(null != dydj){		//如果电压等级不为空
    		filter.push("dydj = " + dydj);
    	}
		
		if(null != xlmc && '' != xlmc.trim()){		//如果线路名称不为空
    		filter.push("xlmc = " + xlmc);
    	}
		if("厂家合计" == sccj){		//如果生产厂家为生产厂家
			if(null != sccj_searchBox){
				filter.push("sccj = " + sccj_searchBox);
			}
			sdzzcxtjDetailView.searchBox.editors[5].value=sccj_searchBox;
    	}else{
    		sccj = item.item.values.SSWSMC;
    		filter.push("sccj = " + sccj);
    		sdzzcxtjDetailView.searchBox.editors[5].value=sccj;
    	}
		if(null != tyrq){		//如果投运日期不为空
    		filter.push("tyrq = " + tyrq);
    	}
		
		if(null != ssdw && '' != ssdw.trim()){		//如果所属单位为国家电网公司
    		filter.push("ssdw = " + ssdw);
    	}
		
		var str = "";
		for ( var i = 0; i < filter.length; i++) {
			str += filter[i] + "&";
		}
		var jclxMap={"018001":"微气象","018003":"视频","018002":"图像","013001":"导线弧垂","013002":"导线温度",
			"013003":"微风振动","013004":"风偏","013005":"覆冰","013006":"导线舞动","014001":"现场污秽度","012001":"杆塔倾斜"};
		var jclxmc = jclxMap[jclx];
		sdzzcxtjDetailView.searchBox.editors[2].setValue(jclxmc);
		sdzzcxtjDetailView.searchBox.editors[2].value=jclx;
		sdzzcxtjDetailView.searchBox.editors[2].setEnabled(false);
		sdzzcxtjDetailView.searchBox.editors[5].setEnabled(false);
		sdzzcxtjDetailView.searchBox.editors[6].value=tyrq;
		sdzzcxtjDetailView.searchBox.setFieldVisible("tyrq", false)
		sdzzcxtjDetailView.dataGrid.setFilter(str.substring(0, str.length - 1));
		sdzzcxtjDetailView.dataGrid.load();
		me.view.detailWin.setWidth("900");
    	me.view.detailWin.setHeight("500");
    	me.view.detailWin.setTitle("输电监测装置信息列表");
		me.view.detailWin.setView(sdzzcxtjDetailView);
		me.view.detailWin.showDialog();
	}
}
    
    
    /**
	 * 点击按生产厂家统计按钮触发的事件
	 */
	me._jclxQueryBtn_click = function() {
		var _dataGrid = me.view.getDataGrid();
		var _seSearchBox = me.view.getSearchBox();
		var sccj = _seSearchBox.editors.sccj.value;
		var params = _seSearchBox.getSearchParam();
		
		_dataGrid.setFilter(params);
		//hidetile(_dataGrid,sccj);
		_dataGrid.load();
	}
		function hidetile(_dataGrid,sccj){
		
    	if(null != sccj){
    		var param = sccj.split(",");
    		_dataGrid.clearColumns();
			var cloumns = [{
				name : "SSWSMC",
				caption : "生产厂家",
				dataAlign : "center",
				editorType : "TextEditor"
			}];
			_dataGrid.appendColumns(cloumns);
		
    		for(var i=0;i<param.length;i++){
    			if("012001" == param[i]){
    				var column1 = [
    				    {
					name : "ZS012001",
					caption : "杆塔倾斜",
					dataAlign : "center",
					editorType : "TextEditor"
    				    }
    		        ];
    				_dataGrid.appendColumns(column1);
    			}else if("013001" == param[i]){
    				var column2 = [
					{
						name : "ZS013001",
						caption : "导线弧垂",
						dataAlign : "center",
						editorType : "TextEditor"
					}];
    				_dataGrid.appendColumns(column2);
    			}else if("013002" == param[i]){
    				var column3 = [{
    					name : "ZS013002",
    					caption : "导线温度",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column3);
    			}else if("013003" == param[i]){
    				var column4 = [{
    					name : "ZS013003",
    					caption : "微风振动",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column4);
    			}else if("013004" == param[i]){
    				var column5 = [{
    					name : "ZS013004",
    					caption : "风偏振动",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column5);
    			}else if("013005" == param[i]){
    				var column6 = [{
    					name : "ZS013005",
    					caption : "覆冰",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column6);
    			}else if("013006" == param[i]){
    				var column7 = [{
    					name : "ZS013006",
    					caption : "导线舞动",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column7);
    		    }else if("014001" == param[i]){
    				var column8 = [{
    					name : "ZS014001",
    					caption : "现场污秽度监测",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column8);
    		    }else if("018001" == param[i]){
    				var column9 = [{
    					name : "ZS018001",
    					caption : "微气象",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column9);
    		    }else if("018002" == param[i]){
    				var column10 = [{
    					name : "ZS018002",
    					caption : "图像",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column10);
    		    }else if("018003" == param[i]){
    				var column11 = [{
    					name : "ZS018003",
    					caption : "视频",
    					dataAlign : "center",
    					editorType : "TextEditor"
    				}];
    				_dataGrid.appendColumns(column11);
    		    }
    			
    		}
    		var columns = [{
				name : "ALLZZZS",
				caption : "所有监测装置",
				dataAlign : "center",
				editorType : "TextEditor"
			}];
    		_dataGrid.appendColumns(columns);
    		
    	}
    	
    }
    return me.endOfClass(arguments);
};