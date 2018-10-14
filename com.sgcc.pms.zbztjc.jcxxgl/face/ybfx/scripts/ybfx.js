var time = new Date();
var nowYear = time.getFullYear();// 获取当前年份
var nowMonth = time.getMonth()+1;// 0-11 0：1月份
var nowDay = time.getDate();
if (nowMonth == 1 && nowDay<28) {
	nowMonth = 12;
	nowYear = nowYear - 1;
}
if(nowDay<28){
	nowMonth = nowMonth - 1;
}
var newYear = "";
var newMonth = "";

function loadMonth(id) {
	var month = document.getElementById(id);
	for(var i=month.options.length-1;i>=0;i--){
		month.options.remove(i);
	}
	
	for ( var i = 1; i <= 12; i++) {
		var newChild = document.createElement("option");
		var _nowMonth = "";
		month.appendChild(newChild);
		if (i < 10) {
			i = "0" + i;
		}
		if (nowMonth < 10) {
			_nowMonth = "0" + nowMonth;
		}
		
		if (_nowMonth == i && nowYear==newYear) {
			newChild.selected = "true";
			newChild.value = i;
			newChild.text = i;
			break;
		}
		newChild.value = i;
		newChild.text = i;
	}
	newMonth = document.getElementById("monthCheck").value;
}

function loadYear(id) {
	var year = document.getElementById(id);
	
	for ( var i = 2; i >= 0; i--) {
		var newChild = document.createElement("option");
		year.appendChild(newChild);
		if (nowYear == nowYear - i) {
			newChild.selected = "true";
		}
		newChild.value = nowYear - i;
		newChild.text = nowYear - i;
	}
	newYear = document.getElementById("yearStrCheck").value;
	loadMonth("monthCheck");
}

window.addEventListener("load", function() {
	loadYear("yearStrCheck");
	getDate();
	getSumLine();
	getTable1();
	getTable2();
	getTable3();
	getTable4();
	getTable5();
	getTable6();
	getTable7();
	getTable8();
},false);


function changeYear(){
	newYear = document.getElementById("yearStrCheck").value;
	loadMonth("monthCheck");
	newMonth = document.getElementById("monthCheck").value;
	
	loadFun();
	
}

function changeMonth(){
	newYear = document.getElementById("yearStrCheck").value;
	newMonth = document.getElementById("monthCheck").value;
	
	loadFun();
	
}


function getDate()
{
	var year=document.getElementById("yearStrCheck").value;
	var month=document.getElementById("monthCheck").value;
	document.getElementById("yr1").innerText = year;
	document.getElementById("mt1").innerText = month;
	document.getElementById("yr2").innerText = year;
	document.getElementById("mt2").innerText = month;
}




function getTable1()
{
	$.ajax({  
        type:"GET",  
        dataType: 'json',  
        url:window.location.origin+uri+"/rest/ybfx/getTable1",  
        error:function(XMLHttpRequest,data)
        {
        	alert("查询表数据出错_01！"); 
        },
       
        success:function(data){
        	var table1 = document.getElementById("table1");
        	var Rows=table1.rows;//类似数组的Rows 
        	
        	for (var i=0;i<data.resultValue.items.length;i++)
        	{
        		var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
        		var Cells=newRow.cells;//类似数组的Cells
        		var _r=newRow.rowIndex;
        		var _c=Cells.length;
        		for(var j=0;j<4;j++)
        		{
        			var newCell=Rows[_r].insertCell(_c); 
        			if(j==3)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].DWMC+"</td>";
        			}else if(j==2)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SDZYZZS+"</td>";
        			}else if(j==1)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SDTYZZS+"</td>";
        			}else if(j==0)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SDFYZZS+"</td>";
        			}
        		}
        		if(data.resultValue.items[i].DWMC=="国家电网公司")
        		{
        			document.getElementById("ZYZZ").innerHTML = data.resultValue.items[i].SDFYZZS;
        		}
        	}
        }  
        }); 

	 
	
}

function getTable2()
{
	
	$.ajax({  
        type:"GET",  
        dataType: 'json',  
        url:window.location.origin+uri+"/rest/ybfx/getTable2",  
        error:function(XMLHttpRequest,data)
        {
        	alert("查询表数据出错_02！"); 
        },
       
        success:function(data){  
        	var table1 = document.getElementById("table2");
        	var Rows=table1.rows;//类似数组的Rows 
        	
        	for (var i=0;i<data.resultValue.items.length;i++)
        	{
        		var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
        		var Cells=newRow.cells;//类似数组的Cells
        		var _r=newRow.rowIndex;
        		var _c=Cells.length;
        		for(var j=0;j<12;j++)
        		{
        			var newCell=Rows[_r].insertCell(_c); 
        			if(j==11)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].DWMC+"</td>";
        			}
        			else if(j==10)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].YQF+"</td>";
        			}
        			else if(j==9)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].QBWF+"</td>";
        			}
        			else if(j==8)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].WBF+"</td>";
        			}
        			else if(j==7)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SBSF+"</td>";
        			}
        			else if(j==6)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].EBEF+"</td>";
        			}
        			else if(j==5)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].YBYF+"</td>";
        			}
        			else if(j==4)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].LLF+"</td>";
        			}
        			else if(j==3)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZLBBF+"</td>";
        			}
        			else if(j==2)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZLLBLF+"</td>";
        			}
        			else if(j==1)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZLWBF+"</td>";
        			}
        			else if(j==0)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZLSBF+"</td>";
        			}

        		}
        	}
        }  
        }); 
	
}

function getTable3()
{
	$.ajax({  
        type:"GET",  
        dataType: 'json',  
        url:window.location.origin+uri+"/rest/ybfx/getTable3",  
        error:function(XMLHttpRequest,data)
        {
        	alert("查询表数据出错_03！"); 
        },
       
        success:function(data){  
        	var table1 = document.getElementById("table3");
        	var Rows=table1.rows;//类似数组的Rows 

        	for (var i=0;i<data.resultValue.items.length;i++)
        	{
        		var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
        		var Cells=newRow.cells;//类似数组的Cells
        		var _r=newRow.rowIndex;
        		var _c=Cells.length;
        		for(var j=0;j<4;j++)
        		{
        			var newCell=Rows[_r].insertCell(_c); 
        			if(j==3)
        			{
        				newCell.innerHTML="<td'>"+data.resultValue.items[i].TYPENAME+"</td>";
        			}else if(j==2)
        			{
        				newCell.innerHTML="<td'>"+data.resultValue.items[i].SDZYZZS+"</td>";
        			}else if(j==1)
        			{
        				newCell.innerHTML="<td'>"+data.resultValue.items[i].SDTYZZS+"</td>";
        			}else if(j==0)
        			{
        				newCell.innerHTML="<td'>"+data.resultValue.items[i].SDFYZZS+"</td>";
        			}
        		}
        	}
        }  
        }); 
	
}

function getTable4()
{
	var textTime=document.getElementById("yearStrCheck").value+"-"+document.getElementById("monthCheck").value;//获取文本框上的年月

	$.ajax({  
		data:{"textTime":textTime},  
        type:"GET",  
        dataType: 'json',  
        url:window.location.origin+uri+"/rest/ybfx/getTable4",  
        error:function(XMLHttpRequest,data)
        {
        	alert("查询表数据出错_04！"); 
        },
       
        success:function(data){  
        	var table1 = document.getElementById("table4");
        	var Rows=table1.rows;//类似数组的Rows 

        	for (var i=0;i<data.resultValue.items.length;i++){
        		var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
        		var Cells=newRow.cells;//类似数组的Cells
        		var _r=newRow.rowIndex;
        		var _c=Cells.length;
        		for(var j=0;j<6;j++)
        		{
        			var newCell=Rows[_r].insertCell(_c); 
        			if(j==5)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].DWMC+"</td>";
        			}
        			else if(j==4)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZTS+"</td>";
        			}
        			else if(j==3)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZZJRL+"</td>";
        			}
        			else if(j==2)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SJJRL+"</td>";
        			}
        			else if(j==1)
        			{
        				
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SJZQL+"</td>";
        			}
        			else if(j==0)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].PJF+"</td>";
        			}
        		}
        	}
        }  
        }); 
	
}



function getTable5()
{
	var textTime=document.getElementById("yearStrCheck").value+"-"+document.getElementById("monthCheck").value;//获取文本框上的年月	

	$.ajax({  
		data:{"textTime":textTime},  
        type:"GET",  
        dataType: 'json',  
        url:window.location.origin+uri+"/rest/ybfx/getTable5",  
        error:function(XMLHttpRequest,data)
        {
        	alert("查询表数据出错_05！"); 
        },
       
        success:function(data){  
        	var table1 = document.getElementById("table5");
        	var Rows=table1.rows;//类似数组的Rows 

        	if(0==data.resultValue.items.length)
        	{
        		$.ajax({  
        	        type:"GET",  
        	        dataType: 'json',  
        	        url:window.location.origin+uri+"/rest/ybfx/getData1",  
        	        error:function(XMLHttpRequest,data)
        	        {
        	        	alert("查询数据出错_01！"); 
        	        },
        	       
        	        success:function(data1){  
                		for(var k=0;k<data1.resultValue.items.length;k++){
                			var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
                			var Cells=newRow.cells;//类似数组的Cells
                			var _r=newRow.rowIndex;
                			var _c=Cells.length;
                		for(var j=0;j<8;j++)
                		{
                			var newCell=Rows[_r].insertCell(_c); 
                			if(j==7)
                			{
                				newCell.innerHTML="<td>"+data1.resultValue.items[k].TYPENAME+"</td>";
                			}
                			else if(j==6)
                			{
                				newCell.innerHTML="<td>-</td>";
                			}
                			else if(j==5)
                			{
                				newCell.innerHTML="<td>-</td>";
                			}
                			else if(j==4)
                			{
                				newCell.innerHTML="<td>-</td>";
                			}
                			else if(j==3)
                			{
                				newCell.innerHTML="<td>-</td>";
                			}
                			else if(j==2)
                			{
                				newCell.innerHTML="<td>-</td>";
                			}
                			else if(j==1)
                			{
                				newCell.innerHTML="<td>-</td>";
                			}
                			else if(j==0)
                			{
                				newCell.innerHTML="<td>-</td>";
                			}
                		}
                		}
        	        }
        	        }); 

        		
        	}
        	for (var i=0;i<data.resultValue.items.length;i++)
        	{
        		var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
        		var Cells=newRow.cells;//类似数组的Cells
        		var _r=newRow.rowIndex;
        		var _c=Cells.length;
        		for(var j=0;j<8;j++)
        		{
        			var newCell=Rows[_r].insertCell(_c); 
        			if(j==7)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].TYPENAME+"</td>";
        			}
        			else if(j==6)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZZZU+"</td>";
        			}
        			else if(j==5)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZZJRL+"</td>";
        			}
        			else if(j==4)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SJJRL+"</td>";
        			}
        			else if(j==3)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SJZQL+"</td>";
        			}
        			else if(j==2)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SJBCL+"</td>";
        			}
        			else if(j==1)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZZGZSJ+"</td>";
        			}
        			else if(j==0)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].PJF+"</td>";
        			}
        		}
        	}
        }  
        }); 
	
	
	
}


function getTable6()
{
	var textTime=document.getElementById("yearStrCheck").value+"-"+document.getElementById("monthCheck").value;//获取文本框上的年月

	$.ajax({  
		data:{"textTime":textTime},  
        type:"GET",  
        dataType: 'json',  
        url:window.location.origin+uri+"/rest/ybfx/getTable6",  
        error:function(XMLHttpRequest,data)
        {
        	alert("查询表数据出错_06！"); 
        },
       
        success:function(data){  
        	var table1 = document.getElementById("table6");
        	var Rows=table1.rows;//类似数组的Rows 
        	if(0==data.resultValue.items.length)
        	{
        		var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
        		var Cells=newRow.cells;//类似数组的Cells
        		var _r=newRow.rowIndex;
        		var _c=Cells.length;

        			for(var j=0;j<7;j++)
        			{
        				var newCell=Rows[_r].insertCell(_c); 
        				if(j==6)
        				{
        					newCell.innerHTML="<td>图像监测</td>";
        				}
        				else if(j==5)
        				{
        					newCell.innerHTML="<td>-</td>";
        				}
        				else if(j==4)
        				{
        					newCell.innerHTML="<td>-</td>";
        				}
        				else if(j==3)
        				{
        					newCell.innerHTML="<td>-</td>";
        				}
        				else if(j==2)
        				{
        					newCell.innerHTML="<td>-</td>";
        				}
        				else if(j==1)
        				{
        					newCell.innerHTML="<td>-</td>";
        				}
        				else if(j==0)
        				{
        					newCell.innerHTML="<td>-</td>";
        				}
        			}
        		
        		
        	}
        	for (var i=0;i<data.resultValue.items.length;i++)
        	{
        		var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
        		var Cells=newRow.cells;//类似数组的Cells
        		var _r=newRow.rowIndex;
        		var _c=Cells.length;
        		for(var j=0;j<7;j++)
        		{
        			var newCell=Rows[_r].insertCell(_c); 
        			if(j==6)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].TYPENAME+"</td>";
        			}
        			else if(j==5)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZZZU+"</td>";
        			}
        			else if(j==4)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZZJRL+"</td>";
        			}
        			else if(j==3)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SJJRL+"</td>";
        			}
        			else if(j==2)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].SJZQL+"</td>";
        			}
        			else if(j==1)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZZGZSJ+"</td>";
        			}
        			else if(j==0)
        			{
        				newCell.innerHTML="<td>"+data.resultValue.items[i].PJF+"</td>";
        			}
        		}
        	}
        }  
        }); 	  
	
}


function getTable7(){
	var textTime=document.getElementById("yearStrCheck").value+"-"+document.getElementById("monthCheck").value;//获取文本框上的年月

	$.ajax({  
		data:{"textTime":textTime},  
        type:"GET",  
        dataType: 'json',  
        url:window.location.origin+uri+"/rest/ybfx/getTable7",  
        error:function(XMLHttpRequest,data)
        {
        	alert("查询表数据出错_07！"); 
        },
       
        success:function(data){  
        	var table1 = document.getElementById("table7");
        	var Rows=table1.rows;//类似数组的Rows 

        	for (var i=0;i<data.resultValue.items.length;i++){
        		var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
        		var Cells=newRow.cells;//类似数组的Cells
        		var _r=newRow.rowIndex;
        		var _c=Cells.length;
        		for(var j=0;j<11;j++){
        			var newCell=Rows[_r].insertCell(_c); 
        			if(j==10){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].MC+"</td>";
        			}else if(j==9){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GZZZ+"</td>";
        			}else if(j==8){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].ZS+"</td>";
        			}else if(j==7){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GZZSJ+"</td>";
        			}else if(j==6){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].XQ+"</td>";
        			}else if(j==5){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GZCGQ+"</td>";
        			}else if(j==4){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GZYJ+"</td>";
        			}else if(j==3){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GZRJ+"</td>";
        			}else if(j==2){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GZTX+"</td>";
        			}else if(j==1){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GZDY+"</td>";
        			}else if(j==0){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GZQT+"</td>";
        			}
        		}
        	  }
        }
        }); 
	
	
}

function getTable8(){
	var textTime=document.getElementById("yearStrCheck").value+"-"+document.getElementById("monthCheck").value;//获取文本框上的年月

	$.ajax({  
		data:{"textTime":textTime},  
        type:"GET",  
        dataType: 'json',  
        url:window.location.origin+uri+"/rest/ybfx/getTable8",  
        error:function(XMLHttpRequest,data)
        {
        	alert("查询表数据出错_08！"); 
        },
       
        success:function(data){  
        	var table1 = document.getElementById("table8");
        	var Rows=table1.rows;//类似数组的Rows 
        	//debugger;
        	for (var i=0;i<data.resultValue.items.length;i++){
        		var newRow=table1.insertRow(table1.rows.length);//插入新的一行 
        		var Cells=newRow.cells;//类似数组的Cells
        		var _r=newRow.rowIndex;
        		var _c=Cells.length;
        		for(var j=0;j<6;j++){
        			var newCell=Rows[_r].insertCell(_c); 
        			if(j==5){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].PROVINCE_NAME+"</td>";
        			}else if(j==4){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GJZZS+"</td>";
        			}else if(j==3){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].GJCS+"</td>";
        			}else if(j==2){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].WBCS+"</td>";
        			}else if(j==1){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].LBZZS+"</td>";
        			}else if(j==0){
        				newCell.innerHTML="<td>"+data.resultValue.items[i].LBCS+"</td>";
        			}
        		}
        	}
        }  
        }); 
	
}

function getSumLine()
{
	$.ajax({  
        type:"GET",  
        dataType: 'json',  
        url:window.location.origin+uri+"/rest/ybfx/getSumLine",  
        error:function(XMLHttpRequest,data)
        {
        	alert("查询线路总数出错！"); 
        },
       
        success:function(data){  
        	document.getElementById("XLZS").innerHTML=data.resultValue.items[0].XLZS;
        }  
        }); 
	
}



function loadFun()
{
	getDate();
	deleteTabRow("table4");
	deleteTabRow("table5");
	deleteTabRow("table6");
	deleteTabRow("table7");
	deleteTabRow("table8");	
	getTable4();
	getTable5();
	getTable6();
	getTable7();
	getTable8();
}

//清空动态加载的表格
function deleteTabRow(id)
{
	var temp=0;
	if("table7"==id)
	{
		temp=1;
	}
	var table = document.getElementById(id);
	var length = table.rows.length;
	for(var i=length-1;i>temp;i--)
	{
		table.deleteRow(i);
	}
}
