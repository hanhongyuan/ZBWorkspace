$ns("tdChart.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

tdChart.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var container = new Array();
    
    var containerAll = null;
    me.dataGrid = null;
    me.params = { };
    base.init = me.init;
    me.init = function()
    {
        base.init();
        initParams();
        _initControls();
        _initContainer();
        initData();
    };
    
    function _initControls()
    {
    	//创建整体的容器
    	containerAll  = new mx.containers.Container({
    		width: "100%",
    		height: "100%",
    		border:"1px solid red"
    	});
        me.addControl(containerAll);
    }
    //初始化4个container
    function _initContainer()
    {
    	for(var i = 0;i<4;i++)
    	{
        	
        		container[i]  = new mx.containers.Container({
            		width:"50%",
            		height:"50%",
            		border:"1px solid red"
                	});
        		container[i].setCss({
    				display: "inline-block",
    				backgroundColor: "#221E1F"
    				});
        	
        }
    	containerAll.addControls(container);
    }
    //查询历史数据并展示
    function initData()
    {
    	var w_sql ="  e.devicecode = '"+me.params.DEVICECODE+"' and e.MONITORINGTYPES =  '"+me.params.MONITORINGTYPE+"'  order by LINKEDCABLEANDCHANNEL,DEVICECODE";
		
		var data = {"w_sql":w_sql};
		var url = tdChart.mappath("~/rest/tdChart/queryDataList");
			$.get(url,data,function(data){
	        	var len = data.length;
	        	 if(data!=null){
	        		 var alarmlevelcode = new Array();//告警级别代码
	        		   	var devicecode =  new Array();//设备代码
	        		   	var typecode = new Array();//监测类型代码
	        		   	var typename = new Array();//监测类型名称
	        		   	var _LinkedEquipment =  new Array();//设备号
	        		   	var _LinkedEquipmentName =  new Array();//设备名称
	        		   	var _LinkedCable =  new Array();//变电站号
	        		   	var _LinkedCableName =  new Array();//变电站名称
	        		   	var _phase = new Array();//相别
	        		   	var CableData=  new Array();//数据集
	        		   	var paraArr = new Array();//参数集 
	        		   	for(var i=0;i<len;i++){
	        		   		alarmlevelcode.push(data[i].ALARMLEVELCODE);//告警级别代码
	        			   	devicecode.push(data[i].DEVICECODE);//设备代码
	        			   	typecode.push(data[i].MONITORINGTYPES);//监测类型代码
	        			   	_LinkedEquipment.push(data[i].LINKEDEQUIPMENT);//设备号
	        			   	_LinkedEquipmentName.push(data[i].LINKEDEQUIPMENTNAME);//设备名称
	        			   	_LinkedCable.push(data[i].LINKEDCABLEANDCHANNEL);//所属电缆及通道
	        			   	_LinkedCableName.push(data[i].LINKEDCABLEANDCHANNELNAME);//所属电缆及通道名称
	        			   	typename.push(data[i].TYPENAME);//监测类型名称
	        			   	_phase.push(data[i].PHASE);//相别
	        			   	CableData.push(data[i].MONITORINGDATA);//设备数据
	        			   	comparStr2 = devicecode[i]+typecode[i];
	        			   	if(CableData[i]!=null){
	        		    		paraArr = CableData[i].split("@@");
	        				}else{
	        					paraArr = "";
	        				}
	        				//使用lable显示数据
	        			  
	        			   	init(paraArr);
	        			   	
	        			   	
	        			   	
	        			   	
	        		   	}
	     		}
	         
	        }); 
    }
    
    //在container添加label信息
    function init(paraArr)
    {
    	
    	
    	var length = paraArr.length;
    	var label = new Array();
    	var label1 = new Array();
    	for(var i = 0;i<length-1;i++)
    	{
    		label[i] = new mx.controls.Label({
    	    text: paraArr[i],
    	    autoWrap:false,
    	    width:"100%"
    	});
    		label1[i] = new mx.controls.Label({
        	    text: "",
        	    autoWrap:false,
        	    width:"100%"
        	});
    		label[i].setCss({
    			color: "#2EDD06"
    			});
    	}
    	
    	container[0].addControls(label);
    	container[1].addControls(label1);
  	
    	
    }
    
    
    //获取参数
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