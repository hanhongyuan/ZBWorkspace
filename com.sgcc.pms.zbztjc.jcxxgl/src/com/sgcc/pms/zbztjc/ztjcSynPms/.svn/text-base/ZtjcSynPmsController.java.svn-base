package com.sgcc.pms.zbztjc.ztjcSynPms;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sgcc.pms.zbztjc.Constants;
import com.sgcc.pms.zbztjc.ztjcSynPms.bizc.IZtjcSynPmsBizc;
import com.sgcc.uap.bizc.config.itag.Constant;
import com.sgcc.uap.rest.annotation.ItemResponseBody;

@Controller
@RequestMapping("/ztjcSynPmsBizc")
public class ZtjcSynPmsController {
	private static final Log log = LogFactory.getLog(ZtjcSynPmsController.class);
	
	@Resource
	private IZtjcSynPmsBizc ztjcSynPmsBizc;
	
	/**
	 * 同步PMS表数据到状态监测数据库里的任务--全量
	 * @return
	 * @since 2017-08-01
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/ztjcSynPmsService",method=RequestMethod.GET)
	public @ItemResponseBody List synPmsService(){
		log.info("执行状态监测同步总部PMS数据的任务开始------------------------------");
		List resultList = ztjcSynPmsBizc.synService(Constants.ALL);				//全量抽取
		return resultList;
	}
	
	/**
	 * 同步PMS表数据到状态监测数据库里的任务	--增量
	 * @return
	 * @since 2017-08-01
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/ztjcSynPmsIncreamentService",method=RequestMethod.GET)
	public @ItemResponseBody List synIncrementService(){
		log.info("执行状态监测同步总部PMS数据的任务开始------------------------------");
		List resultList = ztjcSynPmsBizc.synService(Constants.INCREAMENT);		//增量抽取
		return resultList;
	}
	
}
