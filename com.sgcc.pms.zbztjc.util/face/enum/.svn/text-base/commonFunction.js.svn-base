
function getMsgBoxGJYB(){
	var msgBox = new mx.windows.MessageBox( {
		reusable : true,//一个Boolean值，表示弹出窗口是否可重用。
		title : "提示",// 一个字符串，表示弹出窗口的标题。
		showOkButton : true,//一个Boolean值，表示是否显示确定按纽。
		showCancelButton : true,//一个Boolean值，表示是否显示取消按纽。
		okButtonText : "导出",//一个字符串，表示第一个按纽名称。
		cancelButtonText : "取消",// 一个字符串，表示第二个按纽名称。
		message : "确定要执行该操作？",//一个字符串，表示弹出窗口的内容。
		imgkey : "info",//一个字符串，表示弹出窗口的类型， 支持四种类型
		width : "300px",
		height : "150px",
		zIndex:999999
	});
	return msgBox;
}

/**
 * grid导出excel
 * @param p_grid grid对象
 * @param p_filename 文件名
 * @param speHandleCol 需要特殊处理，隐藏的列集合：["ghr","xxx","sss",…………] 
 */
function exportToExcel(p_grid,p_filename,speHandleCol){
	try{
	    	//获得弹出提示框
			var msgBox = getMsgBoxGJYB();
			msgBox.setMessage("请确认是否导出");
			//确定按钮方法
			msgBox.okButtonCallback = function(){
				exportAll(p_grid,p_filename,speHandleCol);
			};
			//取消按钮方法
			msgBox.cancelButtonCallback = function(){
				return;
			};
	    	msgBox.show();
	}catch(e){
		mx.indicate("info","导出失败");
	}
}

/**
 * 导出所有,提供给exportToExcel方法调用
 * p_grid {Object} grid对象
 * p_filename {String} 导出文件名
 * speHandleCol {Array} 隐藏不需要导出的列
 */
function exportAll(p_grid,p_filename,speHandleCol){
	try{
		debugger;
		var _columns = p_grid.columns;
		var _fileName = "sheetname";
		if($notEmpty(p_filename)){
			_fileName = p_filename;
		}
		var columnNames = getColumns(_columns,speHandleCol);
		var columsWidths = [];
		for(var i=0;i<columnNames.length;i++){
			columsWidths.push(20);
		}
		var exportFilter = p_grid.filter;
		if($notEmpty(p_grid.searchBox)){
			if($notEmpty(p_grid.filter)){
				exportFilter = p_grid.filter + "&" + p_grid.searchBox.getSearchParam();
				if($isEmpty(p_grid.filter)){
					exportFilter =  p_grid.searchBox.getSearchParam();
				}
			}else{
				exportFilter =  p_grid.searchBox.getSearchParam();
			}
		}
		var xls = new mxpms.utils.ExportToExcelUtil({
	       widths: columsWidths,
	       columns: columnNames,
	       filename : _fileName,
	       params: {params : JSON.stringify({filter: exportFilter,sorter : p_grid.sorter,pageSize:2000,pageIndex:1})},
	       url: p_grid.entityContainer.baseUrl
		});
		xls.load();
	}catch(e){
		mx.indicate("info","导出所有失败");
	}
}

/**
 * 导出当前页，提供给exportToExcel方法调用
* p_grid {Object} grid对象
 * p_filename {String} 导出文件名
 * speHandleCol {Array} 隐藏不需要导出的列
 */
function exportCurrent(p_grid,p_filename,speHandleCol){
	try{
		var _columns = p_grid.columns;
		var _fileName = "sheetname";
		if($notEmpty(p_filename)){
			_fileName = p_filename;
		}
		var columnNames = getColumns(_columns,speHandleCol);
		var columsWidths = [];
		for(var i=0;i<columnNames.length;i++){
			columsWidths.push(20);
		}
		/*var exportFilter = p_grid.filter;
		if($notEmpty(p_grid.searchBox)){
			exportFilter = p_grid.filter + "&" + p_grid.searchBox.getSearchParam();
			if($isEmpty(p_grid.filter)){
				exportFilter =  p_grid.searchBox.getSearchParam();
			}
		}*/
		//20160617 modify 解决导出当前页时导出成全部的问题  
		var xls = new mxpms.utils.ExportToExcelUtil({
	       widths: columsWidths,
	       columns: columnNames,
	       filename : _fileName,
	       //params: {params : JSON.stringify({filter: exportFilter,sorter : p_grid.sorter,pageSize:p_grid.pageSize,pageIndex:p_grid.pageIndex})},
	       //url: p_grid.entityContainer.baseUrl
	       data		: {resultValue:{items:p_grid.entityContainer.data,dicts:p_grid.entityContainer.dicts}}
		});
		xls.load();
	}catch(e){
		mx.indicate("info","导出当前失败");
	}
}

/**
 * 获取列,提供给exportToExcel方法调用
 * columns {Object} grid的列集合
 * p_speHandleCol {Array} 不需要导出的列集合
 */
function getColumns(columns,p_speHandleCol){
	var obj = null;
	var speHandleCol = [];
	if($notEmpty(p_speHandleCol)){
		speHandleCol = p_speHandleCol;
	}
	var cols = [];
	for(var i = 0; i < columns.length;i++){
		//主网铭牌需要导出OBJ_ID
		if($isEqual(columns[i].name,"objId",true) ||( 
				columns[i].visible && 
				!speHandleCol.contains(columns[i].name))){
			if($isEqual(columns[i].name,"__temp_Column_rnd__",true)){
				continue;
			}
			obj = new Object();
			obj.caption = columns[i].caption;
			obj.name = columns[i].name;
			obj.editorType = columns[i].editorType;
			cols.push(obj);
			obj = null;
		}
	}
	return cols;
}


