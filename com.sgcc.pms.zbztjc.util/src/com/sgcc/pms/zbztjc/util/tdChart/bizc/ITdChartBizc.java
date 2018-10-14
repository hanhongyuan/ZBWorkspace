package com.sgcc.pms.zbztjc.util.tdChart.bizc;


import java.util.List;

/**
 * 单表场景逻辑构件
 *
 */
public interface ITdChartBizc {


	   
	   /**
	    * 输电组合监视组态图
	    * @param params
	    * @return 
	    */
	   public List queryDataList(String w_sql,String other_sql);
	
	  
}
