package com.sgcc.pms.zbztjc.ybfx;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sgcc.pms.zbztjc.ybfx.bizc.IYbfxBizc;
import com.sgcc.pms.zbztjc.ybfx.bizc.YbfxBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;


@Controller
@RequestMapping("/ybfx") 
public class YbfxController {
	
    @Autowired
    private IYbfxBizc ybfxBizc;
	
    private final Log logger = LogFactory.getLog(YbfxBizc.class);
	
	

	
	/**
	 * 查询线路总数
	 * 
	 */
	@RequestMapping(value="/getSumLine", method=RequestMethod.GET)
	public @ItemResponseBody List querySumLine(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.querySumLine();

	}

	
	/**
	 * 查询表数据_01
	 * 
	 */
	@RequestMapping(value="/getTable1", method=RequestMethod.GET)
	public @ItemResponseBody List queryTable_01(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.queryTable_01();

	}
	
	/**
	 * 查询表数据_02
	 * 
	 */
	@RequestMapping(value="/getTable2", method=RequestMethod.GET)
	public @ItemResponseBody List queryTable_02(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.queryTable_02();

	}
	
	/**
	 * 查询表数据_03
	 * 
	 */
	@RequestMapping(value="/getTable3", method=RequestMethod.GET)
	public @ItemResponseBody List queryTable_03(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.queryTable_03();

	}
	
	
	
	/**
	 * 查询表数据_04
	 * 
	 */
	@RequestMapping(value="/getTable4", method=RequestMethod.GET)
	public @ItemResponseBody List queryTable_04(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.queryTable_04(request.getParameter("textTime"));

	}
	
	/**
	 * 查询表数据_05
	 * 
	 */
	@RequestMapping(value="/getTable5", method=RequestMethod.GET)
	public @ItemResponseBody List queryTable_05(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.queryTable_05(request.getParameter("textTime"));

	}
	
	/**
	 * 查询数据_01
	 * 
	 */
	@RequestMapping(value="/getData1", method=RequestMethod.GET)
	public @ItemResponseBody List queryData_01(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.queryData_01();

	}
	
	/**
	 * 查询表数据_06
	 * 
	 */
	@RequestMapping(value="/getTable6", method=RequestMethod.GET)
	public @ItemResponseBody List queryTable_06(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.queryTable_06(request.getParameter("textTime"));

	}
	
	/**
	 * 查询表数据_07
	 * 
	 */
	@RequestMapping(value="/getTable7", method=RequestMethod.GET)
	public @ItemResponseBody List queryTable_07(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.queryTable_07(request.getParameter("textTime"));

	}
	
	/**
	 * 查询表数据_08
	 * 
	 */
	@RequestMapping(value="/getTable8", method=RequestMethod.GET)
	public @ItemResponseBody List queryTable_08(HttpServletRequest request,HttpServletResponse response) {
	
		return ybfxBizc.queryTable_08(request.getParameter("textTime"));

	}
	
	/**
	 * 注入逻辑构件
	 * @param ybfxBizc
	 */
	public void setYbfxBizc(IYbfxBizc ybfxBizc) {
		this.ybfxBizc = ybfxBizc;
	}
}
