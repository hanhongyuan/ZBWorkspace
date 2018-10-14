$ns("zyxtdevicetj.views");
$import("zyxtdevicetj.views.MainView");

zyxtdevicetj.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new zyxtdevicetj.views.MainView({ controller: me });
        }
        return me.view;
    };
    me._onactive = function(e){
    	me._btnStatistics_onclick();
    };
    me._btnStatistics_onclick = function(){
    	var _dataGrid = me.view.getDataGrid();
    	
    	_dataGrid.clearColumns();
    	hidetile(_dataGrid);
    	
    	_dataGrid.load();
    	
    } 
    /**
     * 动态展示列
     */
    function hidetile(dataGird){
    	
    	var jclx = dataGird.searchBox.editors[3].value;
    	var issd = false;
    	var qxhjl = false;
    	var dxjcl = false;
    	var jyzjcl = false;
    	var gtjcl = false;
    	
    	var isbd = false;
    	var dkql = false;
    	var drxl = false;
    	var jsyhwl = false;
    	var gisl = false;
    	var jcdlMap={"018001":"气象环境监测类","018002":"气象环境监测类","013001":"导线监测类","013002":"导线监测类",
    			"013003":"导线监测类","013004":"导线监测类","013005":"导线监测类","013006":"导线监测类","014001":"绝缘子监测类","012001":"杆塔监测类"};
    	var zy = dataGird.searchBox.editors[8].value;
    	//判断监测类型
    		if (jclx != "" &&jclx != null)
			{
				var jclxArr = jclx.split(",");
				for(var i=0; i<jclxArr.length; i++)
				{
					//输电
					if(jcdlMap[jclxArr[i]]=="气象环境监测类"){
						qxhjl = true;
						issd = true;
					}
					if(jcdlMap[jclxArr[i]]=="导线监测类"){
						dxjcl = true;
						issd = true;
					}
					if(jcdlMap[jclxArr[i]]=="绝缘子监测类"){
						jyzjcl = true;
						issd = true;
					}
					if(jcdlMap[jclxArr[i]]=="杆塔监测类"){
						gtjcl = true;
						issd = true;
					}
					
					//变电
					if (jclxArr[i].split("021").length > 1)
					{
						dkql = true;isbd = true;
					}
					if (jclxArr[i].split("022").length > 1)
					{
						drxl = true;isbd = true;
					}
					if (jclxArr[i].split("023").length > 1)
					{
						jsyhwl = true;isbd = true;
					}
					if (jclxArr[i].split("024").length > 1)
					{
						gisl = true;isbd = true;
					}
						
				}	
			}

    		
	  //拼装表头
    		if("all"==zy)
    			{
    				if(!isbd&&!issd)
    					{
    					//未选择监测类型
    					var cloumns =[{name: "SSXTID", caption: "SSXTID" ,visible : false},
    					      		{ name: "SSXTMC", caption: "所属系统名称", dataAlign:"center", editorType: "TextEditor"  },
    					    		{name:"sd",
    					    		caption:"输电",
    					    		columns:[
    					    		{
    					    		name: "group1",
    					    		caption: "气象环境监测类",
    					    		columns: [
    					    		{ name: "QXJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					    		{ name: "QXJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    					    		]
    					    		},
    					    		
    					    		{
    					        		name: "group2",
    					        		caption: "导线监测类",
    					        		columns: [
    					        		{ name: "DXJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					        		{ name: "DXJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    					        		]
    					        	},
    					        	{
    					        		name: "group3",
    					        		caption: "杆塔监测类",
    					        		columns: [
    					        		{ name: "GTJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					        		{ name: "GTJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    					        		]
    					        	},
    					        	{
    					        		name: "group4",
    					        		caption: "绝缘子监测类",
    					        		columns: [
    					        		{ name: "JYZJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					        		{ name: "JYZJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    					        		]
    					        	},
    					        	
    					        	{
    					        		name: "group6",
    					        		caption: "合计",
    					        		columns: [
    					        		{ name: "JRSHJ_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					        		{ name: "ZSHJ_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
    					        		{ name: "SSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
    					        		]
    					        	}]},
    					        	{name:"bd",
    					        	 caption:"变电",
    					        	 columns:[
    					        	{
    					        		name: "group7",
    					        		caption: "变压器/电抗器类",
    					        		columns: [
    					        		{ name: "BYDKLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					        		{ name: "BYDKLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    					        		]
    					        		},
    					        		{
    					            		name: "group8",
    					            		caption: "断路器/GIS类",
    					            		columns: [
    					            		{ name: "DLGISLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					            		{ name: "DLGISLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    					            		]
    					            	},
    					            	{
    					            		name: "group9",
    					            		caption: "电容型设备类",
    					            		columns: [
    					            		{ name: "DRXLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					            		{ name: "DRXLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    					            		]
    					            	},
    					            	{
    					            		name: "group10",
    					            		caption: "金属氧化物避雷器类",
    					            		columns: [
    					            		{ name: "JSYHWLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					            		{ name: "JSYHWLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
    					            		]
    					            	},
    					            
    					            	{
    					            		name: "group12",
    					            		caption: "合计",
    					            		columns: [
    					            		{ name: "BDJRSHJ_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
    					            		{ name: "BDZSHJ_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
    					            		{ name: "BDSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
    					            		]
    					            	}]
    					        	}];
    					        	dataGird.appendColumns(cloumns);   	
    					}
    				else if(issd&&!isbd)
    					{
    					//只选择了输电的监测类型
    					var cloumns = [{name: "SSXTID", caption: "SSXTID" ,visible : false},
    			    		       		{ name: "SSXTMC", caption: "所属系统名称", dataAlign:"center", editorType: "TextEditor"  }];
    			    		dataGird.appendColumns(cloumns);
    			    		
    			    	var col = 	
    			    		"[{name:\"sd\",caption:\"输电\",columns:[";

    			    		 if (qxhjl == true)
    						 {
    			    			 col += 
    			    				 "{name: \"group1\",caption: \"气象环境监测类\",columns: [{ name: \"QXJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"QXJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

    						 }
    				    	 if (dxjcl == true)
    				    	 {
    				    		 col += 
    				    			 "{name: \"group2\",caption: \"导线监测类\",columns: [{ name: \"DXJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"DXJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

    				    	 }
    				    	 if (jyzjcl == true)
    				    	 {
    				    		col +=
    				    			"{name: \"group4\",caption: \"绝缘子监测类\",columns: [{ name: \"JYZJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"JYZJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

    				    	 }
    				    	 if (gtjcl == true)
    				    	 {
    				    		col +=
    				    			"{name: \"group3\",caption: \"杆塔监测类\",columns: [{ name: \"GTJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"GTJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

    								
    				    	 }
    				    	
    				    	
    				    	 col += 
    				    		 "{name: \"group6\",caption: \"合计\",columns: [{ name: \"JRSHJ_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"ZSHJ_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"SSJRL\", caption: \"实时接入率\", dataAlign:\"center\",editorType: \"TextEditor\" }]}"; 
    				    	 
    				    	 col += "]}]";    				    
    				    	 col =  eval('(' + col + ')'); 
    				    	dataGird.appendColumns(col);
    					}
    				else if(!issd&&isbd)
    					{
    					//只选择了变电的监测类型
    					var cloumns = [{name: "SSXTID", caption: "SSXTID" ,visible : false},
   			    		       		{ name: "SSXTMC", caption: "所属系统名称", dataAlign:"center", editorType: "TextEditor"  }];
   			    		dataGird.appendColumns(cloumns);
   			    		
   			    		var col = 	
   	  			    		"[{name:\"bd\",caption:\"变电\",columns:[";
   		  			     if (dkql == true)
   		  				 {
   		  					col += 
   		  						"{name: \"group7\",caption: \"变压器/电抗器类\",columns: [{ name: \"BYDKLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"BYDKLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
   		
   		  				 }
   		  		    	 if (drxl == true)
   		  		    	 {
   		  		    		col += 
   		  		    			"{name: \"group9\",caption: \"电容型设备类\",columns: [{ name: \"DRXLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"DRXLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
   		
   		  		    	 }
   		  		    	 if (jsyhwl == true)
   		  		    	 {
   		  		    		 col += 
   		  		    			"{name: \"group10\",caption: \"金属氧化物避雷器类\",columns: [{ name: \"JSYHWLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"JSYHWLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
   		
   		  		    	 }
   		  		    	 if (gisl == true)
   		  		    	 {
   		  		    		col +=
   		  		    			"{name: \"group8\",caption: \"断路器/GIS类\",columns: [{ name: \"DLGISLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"DLGISLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
   		
   		  		    	 }
   		  		    	
   		  				   col += 
   					    		"{name: \"group12\",caption: \"合计\",columns: [{ name: \"BDJRSHJ_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"BDZSHJ\", caption: \"总装置数_BD\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"BDSSJRL\", caption: \"实时接入率\", dataAlign:\"center\",editorType: \"TextEditor\" }]}";
   				    	 col += "]}]";    				    
   				    	 col =  eval('(' + col + ')'); 
   				    	dataGird.appendColumns(col);
    					}
    				else if(issd&&isbd)
    					{
    					//先输电
    					var cloumns = [{name: "SSXTID", caption: "SSXTID" ,visible : false},
   			    		       		{ name: "SSXTMC", caption: "所属系统名称", dataAlign:"center", editorType: "TextEditor"  }];
   			    		dataGird.appendColumns(cloumns);
   			    		
   			    	var col = 	
   			    		"[{name:\"sd\",caption:\"输电\",columns:[";

   			    		 if (qxhjl == true)
   						 {
   			    			 col += 
   			    				 "{name: \"group1\",caption: \"气象环境监测类\",columns: [{ name: \"QXJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"QXJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

   						 }
   				    	 if (dxjcl == true)
   				    	 {
   				    		 col += 
   				    			 "{name: \"group2\",caption: \"导线监测类\",columns: [{ name: \"DXJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"DXJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

   				    	 }
   				    	 if (jyzjcl == true)
   				    	 {
   				    		col +=
   				    			"{name: \"group4\",caption: \"绝缘子监测类\",columns: [{ name: \"JYZJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"JYZJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

   				    	 }
   				    	 if (gtjcl == true)
   				    	 {
   				    		col +=
   				    			"{name: \"group3\",caption: \"杆塔监测类\",columns: [{ name: \"GTJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"GTJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

   								
   				    	 }
   				    	
   				    	
   				    	 col += 
   				    		 "{name: \"group6\",caption: \"合计\",columns: [{ name: \"JRSHJ_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"ZSHJ_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"SSJRL\", caption: \"实时接入率\", dataAlign:\"center\",editorType: \"TextEditor\" }]}"; 
   				    	 
   				    	 col += "]}]";    				    
   				    	 col =  eval('(' + col + ')'); 
   				    	dataGird.appendColumns(col);
    					//后变电
  			    	var col = 	
  			    		"[{name:\"bd\",caption:\"变电\",columns:[";
	  			     if (dkql == true)
	  				 {
	  					col += 
	  						"{name: \"group7\",caption: \"变压器/电抗器类\",columns: [{ name: \"BYDKLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"BYDKLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
	
	  				 }
	  		    	 if (drxl == true)
	  		    	 {
	  		    		col += 
	  		    			"{name: \"group9\",caption: \"电容型设备类\",columns: [{ name: \"DRXLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"DRXLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
	
	  		    	 }
	  		    	 if (jsyhwl == true)
	  		    	 {
	  		    		 col += 
	  		    			"{name: \"group10\",caption: \"金属氧化物避雷器类\",columns: [{ name: \"JSYHWLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"JSYHWLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
	
	  		    	 }
	  		    	 if (gisl == true)
	  		    	 {
	  		    		col +=
	  		    			"{name: \"group8\",caption: \"断路器/GIS类\",columns: [{ name: \"DLGISLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"DLGISLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
	
	  		    	 }
	  		    	
	  				   col += 
				    		"{name: \"group12\",caption: \"合计\",columns: [{ name: \"BDJRSHJ_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"BDZSHJ\", caption: \"总装置数_BD\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"BDSSJRL\", caption: \"实时接入率\", dataAlign:\"center\",editorType: \"TextEditor\" }]}";
			    	 col += "]}]";    				    
			    	 col =  eval('(' + col + ')'); 
			    	dataGird.appendColumns(col);
	    					
	    					}
    			}
    		else if("L"==zy&&issd)
    			{
    			var cloumns = [{name: "SSXTID", caption: "SSXTID" ,visible : false},
		    		       		{ name: "SSXTMC", caption: "所属系统名称", dataAlign:"center", editorType: "TextEditor"  }];
		    		dataGird.appendColumns(cloumns);
		    		
		    		var col = 	
			    		"[{name:\"sd\",caption:\"输电\",columns:[";

			    		 if (qxhjl == true)
						 {
			    			 col += 
			    				 "{name: \"group1\",caption: \"气象环境监测类\",columns: [{ name: \"QXJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"QXJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

						 }
				    	 if (dxjcl == true)
				    	 {
				    		 col += 
				    			 "{name: \"group2\",caption: \"导线监测类\",columns: [{ name: \"DXJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"DXJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

				    	 }
				    	 if (jyzjcl == true)
				    	 {
				    		col +=
				    			"{name: \"group4\",caption: \"绝缘子监测类\",columns: [{ name: \"JYZJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"JYZJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

				    	 }
				    	 if (gtjcl == true)
				    	 {
				    		col +=
				    			"{name: \"group3\",caption: \"杆塔监测类\",columns: [{ name: \"GTJCLJRS_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"GTJCLZS_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";

								
				    	 }
				    	
				    	
				    	 col += 
				    		 "{name: \"group6\",caption: \"合计\",columns: [{ name: \"JRSHJ_SD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"ZSHJ_SD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"SSJRL\", caption: \"实时接入率\", dataAlign:\"center\",editorType: \"TextEditor\" }]}"; 
				    	 
			    	 col += "]}]";    				    
			    	 col =  eval('(' + col + ')'); 
			    	dataGird.appendColumns(col);
    			}
    		else if("L"==zy&&!issd)
    			{
    			var cloumns =[{name: "SSXTID", caption: "SSXTID" ,visible : false},
					      		{ name: "SSXTMC", caption: "所属系统名称", dataAlign:"center", editorType: "TextEditor"  },
					    		{name:"sd",
					    		caption:"输电",
					    		columns:[
					    		{
					    		name: "group1",
					    		caption: "气象环境监测类",
					    		columns: [
					    		{ name: "QXJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					    		{ name: "QXJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
					    		]
					    		},
					    		
					    		{
					        		name: "group2",
					        		caption: "导线监测类",
					        		columns: [
					        		{ name: "DXJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					        		{ name: "DXJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
					        		]
					        	},
					        	{
					        		name: "group3",
					        		caption: "杆塔监测类",
					        		columns: [
					        		{ name: "GTJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					        		{ name: "GTJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
					        		]
					        	},
					        	{
					        		name: "group4",
					        		caption: "绝缘子监测类",
					        		columns: [
					        		{ name: "JYZJCLJRS_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					        		{ name: "JYZJCLZS_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
					        		]
					        	},
					        	
					        	{
					        		name: "group6",
					        		caption: "合计",
					        		columns: [
					        		{ name: "JRSHJ_SD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					        		{ name: "ZSHJ_SD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
					        		{ name: "SSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
					        		]
					        	}]}];
					        	dataGird.appendColumns(cloumns);  
    			}
    		else if("T"==zy&&!isbd)
	    		{
    			var cloumns =[{name: "SSXTID", caption: "SSXTID" ,visible : false},
					      		{ name: "SSXTMC", caption: "所属系统名称", dataAlign:"center", editorType: "TextEditor"  },
					        	{name:"bd",
					        	 caption:"变电",
					        	 columns:[
					        	{
					        		name: "group7",
					        		caption: "变压器/电抗器类",
					        		columns: [
					        		{ name: "BYDKLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					        		{ name: "BYDKLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
					        		]
					        		},
					        		{
					            		name: "group8",
					            		caption: "断路器/GIS类",
					            		columns: [
					            		{ name: "DLGISLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					            		{ name: "DLGISLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
					            		]
					            	},
					            	{
					            		name: "group9",
					            		caption: "电容型设备类",
					            		columns: [
					            		{ name: "DRXLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					            		{ name: "DRXLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
					            		]
					            	},
					            	{
					            		name: "group10",
					            		caption: "金属氧化物避雷器类",
					            		columns: [
					            		{ name: "JSYHWLJRS_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					            		{ name: "JSYHWLZS_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" }
					            		]
					            	},
					            
					            	{
					            		name: "group12",
					            		caption: "合计",
					            		columns: [
					            		{ name: "BDJRSHJ_BD", caption: "实时接入装置数", dataAlign:"center",editorType: "TextEditor" },
					            		{ name: "BDZSHJ_BD", caption: "总装置数", dataAlign:"center",editorType: "TextEditor" },
					            		{ name: "BDSSJRL", caption: "实时接入率", dataAlign:"center",editorType: "TextEditor" }
					            		]
					            	}]}];
					        	dataGird.appendColumns(cloumns);  
	    		}
    		else if("T"==zy&&isbd)
    			{
    			var cloumns = [{name: "SSXTID", caption: "SSXTID" ,visible : false},
			    		       		{ name: "SSXTMC", caption: "所属系统名称", dataAlign:"center", editorType: "TextEditor"  }];
			    		dataGird.appendColumns(cloumns);
			    		
			    		var col = 	
	  			    		"[{name:\"bd\",caption:\"变电\",columns:[";
		  			     if (dkql == true)
		  				 {
		  					col += 
		  						"{name: \"group7\",caption: \"变压器/电抗器类\",columns: [{ name: \"BYDKLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"BYDKLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
		
		  				 }
		  		    	 if (drxl == true)
		  		    	 {
		  		    		col += 
		  		    			"{name: \"group9\",caption: \"电容型设备类\",columns: [{ name: \"DRXLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"DRXLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
		
		  		    	 }
		  		    	 if (jsyhwl == true)
		  		    	 {
		  		    		 col += 
		  		    			"{name: \"group10\",caption: \"金属氧化物避雷器类\",columns: [{ name: \"JSYHWLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"JSYHWLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
		
		  		    	 }
		  		    	 if (gisl == true)
		  		    	 {
		  		    		col +=
		  		    			"{name: \"group8\",caption: \"断路器/GIS类\",columns: [{ name: \"DLGISLJRS_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"DLGISLZS_BD\", caption: \"总装置数\", dataAlign:\"center\",editorType: \"TextEditor\" }]},";
		
		  		    	 }
		  		    	 
		  				   col += 
					    		"{name: \"group12\",caption: \"合计\",columns: [{ name: \"BDJRSHJ_BD\", caption: \"实时接入装置数\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"BDZSHJ\", caption: \"总装置数_BD\", dataAlign:\"center\",editorType: \"TextEditor\" },{ name: \"BDSSJRL\", caption: \"实时接入率\", dataAlign:\"center\",editorType: \"TextEditor\" }]}";
				    	 col += "]}]";  				    
			    	 col =  eval('(' + col + ')'); 
			    	dataGird.appendColumns(col);
    			}

	}

    /**
     * 初始化DataGrid
     */
    me._initDataGrid = function(){
    	var _dataGrid = me.view.getDataGrid();
    	_col = _dataGrid.$body.find("td").not("#SSXTID,#SSJRL,#BDSSJRL,#rownumber");
		//统计值添加方法
    	for(var i=0;i<_col.length;i++){
			if("0" != _col[i].innerText && "" != _col[i].innerText &&"合计" != _col[i].innerText){
	    		_col[i].setAttribute("class","ywjxLink");
	    	}
		}
    	
    	_dataGrid.$body.find(".ywjxLink").click(function(e) {
			var item = $(this).parent().data();
			var colName = e.target.id;
			var text = e.target.textContent;
			$(this)[0].style.color = "#999999";
			
			if("SSXTMC"==colName)
				{
				showzyxt(item, text);
				}
			else
			 showDetail(item, colName);
		});
    }
    showzyxt = function(item, text)
    {
    	var mvc = new zyxtinfo.views.MainViewController();
    	var detailview = mvc.getView();
    	var window = new mx.windows.WindowManager().create({
	    		resizable: false
	    	});
    	var ssxtid = item.item.id;
    	 var filter = new Array();
    	 
    	 if(null!=ssxtid)
 	    {
 	    	filter.push("xtid=" + ssxtid);
 	    }
    	 
    	 var str = "";
  	    for ( var i = 0; i < filter.length; i++) {
  			str += filter[i] + "&";
  		}
    	detailview.dataGridSD.setFilter(str.substring(0, str.length - 1));
	    	
  		detailview.dataGridSD.load();
    	detailview.dataGridBD.setFilter(str.substring(0, str.length - 1));
	    	
 		detailview.dataGridBD.load();
 		detailview.dataGridcx.setFilter(str.substring(0, str.length - 1));
    	
 		detailview.dataGridcx.load();
 		window.setWidth("1000");
     	window.setHeight("700");
     	window.setTitle(text);
 		window.setView(detailview);
 		window.showDialog();
    }
    
    
    showDetail = function(item, colName)
    {
  
    	var dydj = me.view.dataGrid.searchBox.editors.dydj.value;
    	var ssxt = me.view.dataGrid.searchBox.editors.ssxt.value;
    	var linkeddepws = me.view.dataGrid.searchBox.editors.linkeddepws.value;
    	var linkedlinename = me.view.dataGrid.searchBox.editors.linkedlinename.value;
    	var linkedstationname = me.view.dataGrid.searchBox.editors.linkedstationname.value;
    	var monitoringtype = me.view.dataGrid.searchBox.editors.monitoringtype.value;
    	var srundate = me.view.dataGrid.searchBox.editors.srundate.value;
    	var erundate = me.view.dataGrid.searchBox.editors.erundate.value;
    	
    	var ssxtid = item.item.id;
    	var mvc = new zyxtdetail.views.MainViewController();
    	var detailview = mvc.getView();
    	var window = new mx.windows.WindowManager().create({
	    		resizable: false
	    	});	
	   var filter = new Array();
	 //条件框的条件
	   if("SD"==colName.substring(colName.length-2,colName.length))
		   filter.push("lx=" + "L");
	   else if("BD"==colName.substring(colName.length-2,colName.length))
		   filter.push("lx=" + "T");
	    if(null!=dydj)
	    {
	    	filter.push("dydj=" + dydj);
	    }
	  	if(null!=linkeddepws)
	    {
	    	filter.push("linkeddepws=" + linkeddepws);
	    }
	      if(""!=linkedlinename)
	    {
	    	filter.push("linkedlinename=" + linkedlinename);
	    }
	      if(""!=linkedstationname)
	    {
	    	filter.push("linkedstationname=" + linkedstationname);
	    }
	      if(null!=monitoringtype)
	    {
	    	filter.push("monitoringtype=" + monitoringtype);
	    }
	      if(null!=ssxt)
	    {
	    	filter.push("ssxt=" + ssxt);
	    }
	      if(null!=srundate)
	    {
	    	filter.push("srundate=" + srundate);
	    }
	      if(null!=erundate)
	    {
	    	filter.push("erundate=" + erundate);
	    }
	    if(null!=ssxtid)
	    {
	    	filter.push("ssxtid=" + ssxtid);
	    }
	   //具体数据的条件
	   //是否实时
	    if("JRS"==colName.substring(colName.length-6,colName.length-3)||"JRS"==colName.substring(0,3)||"BDJRS"==colName.substring(0,5))
	   {
	   		filter.push("sfss=T" );
	   }
	   //变电大类
	   if("QXJCL"==colName.substring(0,5))
	   {
	   		filter.push("jclx=018" );
	   }
	   if("JYZJCL"==colName.substring(0,6))
	   {
	   		filter.push("jclx=014" );
	   }
	   if("DXJCL"==colName.substring(0,5))
	   {
	   		filter.push("jclx=013" );
	   }
	   if("GTJCL"==colName.substring(0,5))
	   {
	   		filter.push("jclx=012" );
	   }

	   
	   if("BYDKL"==colName.substring(0,5))
	   {
	   		filter.push("jclx=021" );
	   }
	   if("DLGISL"==colName.substring(0,6))
	   {
	   		filter.push("jclx=024" );
	   }
	   if("DRXL"==colName.substring(0,4))
	   {
	   		filter.push("jclx=022" );
	   }
	   if("JSYHWL"==colName.substring(0,6))
	   {
	   		filter.push("jclx=023" );
	   }
	   
    	
    	var str = "";
 	    for ( var i = 0; i < filter.length; i++) {
 			str += filter[i] + "&";
 		}
     	detailview.dataGrid.setFilter(str.substring(0, str.length - 1));
 	    	
 		detailview.dataGrid.load();
 		window.setWidth("900");
     	window.setHeight("500");
     	if("SD"==colName.substring(colName.length-2,colName.length))
     			window.setTitle("输电监测信息列表");
     	else if("BD"==colName.substring(colName.length-2,colName.length))
     			window.setTitle("变电监测信息列表");
 		window.setView(detailview);
 		window.showDialog();

    }
    
    return me.endOfClass(arguments);
};