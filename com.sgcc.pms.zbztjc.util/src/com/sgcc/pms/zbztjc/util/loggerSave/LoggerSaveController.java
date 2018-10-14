package com.sgcc.pms.zbztjc.util.loggerSave;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sgcc.pms.zbztjc.util.loggerSave.bizc.ILoggerSaveBizc;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;

@Controller
@RequestMapping("/loggerSave")
public class LoggerSaveController {
	@Resource
	private ILoggerSaveBizc loggerSaveBizc;

	@RequestMapping("/saveLogger")
	public @ItemResponseBody
	List updateUserLogger(@ItemsRequestBody List<Map<String, String>> list) {
		return loggerSaveBizc.saveLogger(list);
	}

}
