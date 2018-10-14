package com.sgcc.pms.zbztjc.kqxt.jcxxcx.xldzgl;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sgcc.pms.zbztjc.kqxt.jcxxcx.xldzgl.bizc.IXldzglBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.RawResponseBody;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;

@Controller
@RequestMapping("/xldzgl")

public class XldzglController {

	@Resource
	private IXldzglBizc xldzglBizc;
	
	 /**
     * xldz信息查询
     * @param params
     * @return
     */
	@RequestMapping("/loadinfo")
    public @ItemResponseBody
	QueryResultObject loadinfo(@QueryRequestParam("params") RequestCondition params) {
    	return xldzglBizc.loadinfo(params);
    }

	 /**
     * xtmc信息查询
     * @param params
     * @return
     */
	@RequestMapping("/loadxt")
    public @ItemResponseBody
	QueryResultObject loadxt(@QueryRequestParam("params") RequestCondition params) {
    	return xldzglBizc.loadxt(params);
    }
	 /**
     * xtmc信息查询
     * @param params
     * @return
     */
  
	@RequestMapping(value="/unioninfo", method=RequestMethod.POST)
    public @RawResponseBody
	void unioninfo(HttpServletRequest request,HttpServletResponse response) {
		System.out.println(request.getParameter("xlid"));
    	xldzglBizc.unioninfo(request.getParameter("xlid"),request.getParameter("xtid"));
    }
}