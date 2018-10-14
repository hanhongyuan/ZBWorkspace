package com.sgcc.pms.zbztjc.util.tdChart;


import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sgcc.pms.zbztjc.util.tdChart.bizc.ITdChartBizc;
import com.sgcc.uap.rest.annotation.RawResponseBody;

@Controller
@RequestMapping("/tdChart")

public class TdChartController {

	@Resource
	private ITdChartBizc tdChartBizc;
	
   
	
	  /**
     * 输电组合监视组态图
     * @param params
     * @return
     */
	@RequestMapping(value="/queryDataList", method=RequestMethod.GET)
    public @RawResponseBody List queryDataList(HttpServletRequest request,HttpServletResponse response) {
    	return tdChartBizc.queryDataList(request.getParameter("w_sql"),request.getParameter("other_sql"));
    }
	
	 @RequestMapping("/handleXssBug")
	 public void test(){
		 System.out.println("--------------handleXssBug-----------------");
	 }
	
}
