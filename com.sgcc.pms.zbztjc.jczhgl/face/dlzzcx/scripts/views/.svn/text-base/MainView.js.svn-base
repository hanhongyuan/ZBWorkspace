$ns("dlzzcx.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

dlzzcx.views.MainView = function()
{
	 var me = $extend(mx.views.View);
	    var base = {};
	    
	    /**
		 * 初始化日期处理对象
		 */
		var dateutil = new mxpms.utils.DateUtil();
		var _seSearchBox =null;
		/**
		 * 查询区高度,默认为120px
		 */
		var searchBoxheight = "120";
		//数据区
		var _dataGrid= null;
		    //表单窗口对象
	    var _detailWin = null;
	    
	    base.init = me.init;
	    me.init = function()
	    {
	        base.init();

	        _initControls();
	    };
	    
	    function _initControls()
	    {
	    	// 垂直方向的分割控件
			 _hSplit = new mx.containers.HSplit({
				rows : "85px, auto",
				borderThick : 0
			});
			
			// 初始化查询区
			_initSearchBox();
			
			// 初始化工具栏
			//_initToolBar();
			// 初始化表格
			_initDataGrid();
			// 初始化公共弹窗
			_initDetailWindow();
			_hSplit.addControl(me._searchBox,1);
			_hSplit.addControl(_dataGrid,1);
			me.addControl(_hSplit);
	        me.on("activate", me.controller._onactivate);
	    }
	    
	    
	    /**
		 * 初始化查询区
		 */
		function _initSearchBox() {
			_seSearchBox = new mxpms.datacontrols.AdvancedSearchBox({
				reset:reset,
				initHidden : false,
				rootGroupId : "402881704230292b0142316493220006",
				alias: "dlzzcxSearchBox",
				itemNumOfRow : 4,
				fields : [{
							name : "ssdw",caption : "所属单位",displayCheckBox : true,hint:"---请选择---",editorType : "DropDownEditor",
							url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_ssdw"
						}, {
							name : "dydj",caption : "电压等级",displayCheckBox : true,hint:"---请选择---",editorType : "DropDownEditor",
							url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_dydj"
						}, {
							name : "jclx",caption : "监测类型",displayCheckBox : true,hint:"---请选择---",editorType : "DropDownEditor",
							url : "/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_jclxDl"
						}, {name : "yxzt",caption : "运行状态",hint : "--请选择--",editorType : "DropDownEditor",
							url:"/com.sgcc.pms.zbztjc.jczhgl/rest/jczhglcxUtil/dropDownEditor/dicts_yxzt"
						}, {
							name : "sccj",caption : "生产厂家",displayCheckBox : false,hint:"---请输入---",onchanged:_paramChanged,displayCloseButton : false,editorType : "TextEditor"
						}, {
							name : "dlmc",caption : "电力电缆及通道名称",displayCheckBox : false,hint:"---请输入---",onchanged:_nameChanged,displayCloseButton : false,editorType : "TextEditor"
						}, {
							name : "tyrq",caption : "投运日期",hint:"---请选择---",formatString : "yyyy-MM-dd",editorType : "DateTimeEditor"
						}]
			});
			// 添加查询区按钮
			_dataGridappendButton();
			_seSearchBox.editors.ssdw.setCss({width:200});
			_seSearchBox.editors.dydj.setCss({width:200});
			_seSearchBox.editors.jclx.setCss({width:200});
			_seSearchBox.editors.sccj.setCss({width:200});
			_seSearchBox.editors.dlmc.setCss({width:200});
			_seSearchBox.editors.tyrq.setCss({width:200});
			_seSearchBox.editors.yxzt.setCss({width:200});
			//查询区隐藏处理设置
			var _img = _seSearchBox.$("img");
			_img.on("click", function() {
						if (_seSearchBox._isHidden) {
							_hSplit.$panel2.css("top", "28px");
						} else {
							_hSplit.$panel2.css("top", "85px");
						}
					});
			_hSplit.addControl(_seSearchBox, 0);
		}
		
		 /**
		 * 添加统计按钮
		 */
		function _dataGridappendButton() {
			var $searchBox = me.getSearchBox();
			// 隐藏按钮栏
			$searchBox.$("#btnTr").hide();
			var $tbody = $searchBox.$("tbody");
			$tbody.append("<tr><td class = 'newButtonTd1' colspan=9 style='text-align: right; padding-right: 5px;'></td></tr>");
			var $newButtonTd1 = $searchBox.$(".newButtonTd1");
			
			//$tbody
			//		.append("<tr><td class = 'newButtonTd2' colspan=9 style='text-align: right; padding-right: 5px;height:28px'></td></tr>");
			//var $newButtonTd2 = $searchBox.$(".newButtonTd2");
			me.CxBtn = new mxpms.controls.ImageButton({
						text : "查询",
						name : "dlzzcx",
						onclick : me.controller._btnCx_onclick
					});
			$newButtonTd1.append($("<div class=space>"));
			$newButtonTd1.append(me.CxBtn.$e);
			// 将重置按钮放入
			$newButtonTd1.append($searchBox.$("#reset"));
			$searchBox.$("#reset")[0].style.marginBottom = "0px";
			$searchBox.$("#reset")[0].style.marginRight = "5px";
			$searchBox.$("#reset")[0].style.marginLeft = "5px";
			// 定义一个按监测类型统计的按钮
			me.exportBtn = new mxpms.controls.ImageButton({
				text : "导出",
				name : "exportBtn",
				onclick : me.controller._export_click
			});

			$newButtonTd1.append(me.exportBtn.$e);
			
		}
		
		var pattern1 = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]+$");
		function _paramChanged()
		{
			var sccj = _seSearchBox.editors.sccj.value;
			
			if(sccj!=null&&sccj!="")
			{
				if(sccj.length>50|| !pattern1.test(sccj)){
					_seSearchBox.editors.sccj.reset();
					mx.indicate("warn", "生产厂家长度不能超50且不能为特殊字符!");
				}
					
			}	
		}
		
		function _nameChanged()
		{
			var xlmc = _seSearchBox.editors.xlmc.value;
			
			if(xlmc!=null&&xlmc!="")
			{
				if(xlmc.length>50|| !pattern1.test(xlmc)){
					_seSearchBox.editors.xlmc.reset();
					mx.indicate("warn", "线路名称长度不能超50且不能为特殊字符!");
				}
					
			}	
		}
		
		/**
		 * 重置
		 */
		function reset()
		{
			var editor = null;
			for (var i = 0; i < _seSearchBox.editors.length; i++)
	        {
				editor = _seSearchBox.editors[i];
				editor.reset(); //调用editor的原生方法重置
				if(editor.editorType && editor.editorType == "DropDownTreeEditor") { //如果是下拉树，选中的项没有被清除，在此清除
					var nodes = editor.dataTree.getCheckedNodes();
					for(var j = 0; j<nodes.length; j++)
					{
						nodes[j].setChecked(false);
					}
					delete editor.defaultValue; //这里是必要的
				}
				editor.setValue(null);
	        }
		}
	    
		/**
		 * 初始化表格
		 */
		function _initDataGrid() {
			var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
						baseUrl : dlzzcx.mappath("~/rest/zbdlzzcxtjController/query"),
						iscID : "-1",
						loadMeta : false,
						primaryKey: "OBJ_ID"
					});
			/* 初始化 DataGrid */
			_dataGrid = new mx.datacontrols.DataGrid({
				alias : "dlzzcxdlzzcxewDataGrid",
				// TODO: 设置显示列。
				columns : [{
							name : "OBJ_ID",
							caption : "OBJ_ID",
							dataAlign : "center",
							editorType : "TextEditor"
						},{
							name : "DEPMC",
							caption : "所属单位",
							dataAlign : "center",
							editorType : "TextEditor"
						},{
							name : "LINKEDCABLEANDCHANNELNAME",
							caption : "电力电缆及通道名称",
							dataAlign : "center",
							width:200,
							editorType : "TextEditor"
						},{
							name : "LINKEDEQUIPMENTNAME",
							caption : "设备名称",
							dataAlign : "center",
							width:250,
							editorType : "TextEditor"
						},{
							name : "DEVICECATEGORY",
							caption : "接入方式",
							dataAlign : "center",
							editorType : "TextEditor"
						},{
							name : "DEVICECATEGORY_DISPLAY",
							caption : "监测类型",
							dataAlign : "center",
							editorType : "TextEditor"
						},{
							name : "JCXX",
							caption : "监测信息",
							dataAlign : "center",
							editorType : "TextEditor",
							visible:false,
							renderCell : function(p_item, $p_cell) {
								var value = "查看"; // 获取 GENDER 字段的值。 // 获取 GENDER 字段的值。
								var deptws =  p_item.getValue("LINKEDDEPWS")
								var devicecode = p_item.getValue("DEVICECODE");
								var monitor = p_item.getValue("MONITORINGTYPES");
								$p_cell.html(value).css({
									"text-decoration" : "underline",
									"color" : "blue"
								}).mouseover(function() {
									$p_cell.css("cursor", "pointer");
								}).on("click", function() {
									$p_cell.css("color", "grey");
									me.controller._btnTest(devicecode,monitor,deptws);
								});
							}
						},{
							name : "DEVICENAME",
							caption : "装置名称",
							dataAlign : "center",
							width:280,
							editorType : "TextEditor"
						},{
							name : "DEVICEMODEL",
							caption : "装置型号",
							dataAlign : "center",
							editorType : "TextEditor"
						},{
							name : "MANUFACTURER",
							caption : "生产厂家",
							dataAlign : "center",
							editorType : "TextEditor"
						},{
							name : "IFSS",
							caption : "是否实时",
							dataAlign : "center",
							editorType : "TextEditor",
							renderCell : function(p_item, $p_cell) {
									var value = p_item.getValue("IFSS"); // 获取 GENDER 字段的值。
									if("T"==value)
										{
										
										$p_cell.html(value).css({
											
											"color" : "green"
										});
										}
									else 
									{
										$p_cell.html(value).css({
											
											"color" : "red"
										});
										}
									
								}
						},{
					name : "STATUS",
					caption : "运行状态",
					dataAlign : "center",
					renderCell : function(p_item, $p_cell) {
								var value = p_item.getValue("STATUS"); // 获取 GENDER 字段的值。
								switch(value){
								case "00501":
									$p_cell.text("在运");
									break;
								case "00502":
									$p_cell.text("检修/调试");
									break;
								case "00503":
									$p_cell.text("退役");
									break;
								}
							}
				},{
							name : "RUNDATE",
							caption : "投运日期",
							editorType : "TextEditor"
						}
						
						],
						displayCheckBox: false,
						displayPrimaryKey:false,//列表是否显示主键
						pageSize : 20,
						entityContainer : gridEntityContainer
					});
					_dataGrid.load();
					btnlookup_click = me.controller._btnlookup_click;
					// 隐藏表格的放大镜
					_dataGrid.$("#toggleShowBtn").hide();
				}
		    /**
	     * 初始化表单视图窗口对象
	     */
	    function _initDetailWindow(){
	    	_detailWin = dlzzcx.context.windowManager.create({
				reusable: true,//是否复用
				width:640,
				height:520,
				title:"表单维护"
			});
	    }

	    /**
	     * 获取表单视图窗口对象
	     */
	    me.getDetailWindow = function(){
	    	return _detailWin;
	    }
				
	    me.getSearchBox = function(){
	    	return _seSearchBox;
	    }
	    me.getDataGrid = function(){
	    	return _dataGrid;
	    }
	    return me.endOfClass(arguments);
};