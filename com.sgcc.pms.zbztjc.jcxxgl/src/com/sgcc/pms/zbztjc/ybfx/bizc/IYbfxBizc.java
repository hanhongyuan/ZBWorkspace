package com.sgcc.pms.zbztjc.ybfx.bizc;

import java.util.List;

/**
 * 单表场景逻辑构件
 *
 */
public interface IYbfxBizc {


	
	   /**
		* 
		* 查询线路总数
		*/
		public List querySumLine();

		/**
		* 
		* 查询表数据01
		*/
		public List queryTable_01();
		
		/**
		* 
		* 查询表数据02
		*/
		public List queryTable_02();
		
		/**
		* 
		* 查询表数据03
		*/
		public List queryTable_03();

		
		/**
		* 
		* 查询表数据04
		*/
		public List queryTable_04(String textTime);
		
		/**
		* 
		* 查询表数据05
		*/
		public List queryTable_05(String textTime);
		
		/**
		* 
		* 查询数据01
		*/
		public List queryData_01();
		
		/**
		* 
		* 查询表数据06
		*/
		public List queryTable_06(String textTime);
		
		/**
		* 
		* 查询表数据07
		*/
		public List queryTable_07(String textTime);
		
		/**
		* 
		* 查询表数据08
		*/
		public List queryTable_08(String textTime);
}
