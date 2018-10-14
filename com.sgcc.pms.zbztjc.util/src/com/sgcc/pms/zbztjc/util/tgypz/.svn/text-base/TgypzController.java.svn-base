package com.sgcc.pms.zbztjc.util.tgypz;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;

import com.sgcc.pms.zbztjc.util.tgypz.bizc.ITgypzBizc;
import com.sgcc.uap.rest.annotation.IdRequestBody;
import com.sgcc.uap.rest.annotation.ItemRequestParam;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.TreeResponseBody;
import com.sgcc.uap.rest.annotation.VoidResponseBody;
import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.TreeNode;

@Controller
@RequestMapping("/tgypz")
public class TgypzController {
	
    @Resource
    private ITgypzBizc tgypzBizc;
	
	/**
	 * 保存
	 * 请求体格式为:{"items":[{"id":"1","name":"1","sex":"1","age":"1"},{"id":"1","name":"1","sex":"1","age":"1"}]}
	 * @return voList
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody 
	List<Map> save(@ItemsRequestBody List<Map> list){ 
		try {
			return tgypzBizc.saveOrUpdate(list);
		} catch(Exception e) {
			throw new RestClientException("保存方法异常",e);
		}
	}
	
	/**
	 * 删除操作
	 * 
	 * @param list包含map对象
	 *            ，封装ids主键值数组和idName主键名称
	 * @return null
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @VoidResponseBody
	Object delete(@IdRequestBody IDRequestObject idObject) {
		tgypzBizc.remove(idObject);
		return null;
	}
	
	/**
	 * 查询
	 * 
	 * @param params
	 *            将请求参数{"filter":"","orderStr":"age","columns":"id,name"}
	 *            封装为RequestCondition对象
	 * @return queryResult
	 */
	@RequestMapping("/")
	public @ItemResponseBody
	QueryResultObject query(@QueryRequestParam("params") RequestCondition params) {
		return tgypzBizc.query(params);
	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param id
	 *            url中传递的值
	 */
	@RequestMapping("/{id}")
	public @ItemResponseBody
	QueryResultObject query(@PathVariable String id) {
		// 如果为新建 则取到树上的linkedStation 用来查出设备类型的数据字典 
		if(id.contains("New: ")) return tgypzBizc.selectSblx(id.replace("New: ", ""));
		return tgypzBizc.queryById(id);
	}
	
	/**
	 * 查设备名称数据字典
	 * @param params
	 * @return
	 */
	@RequestMapping("/selectSbmc")
	public @ItemResponseBody
	QueryResultObject selectSbmc(@QueryRequestParam("params") RequestCondition params) {
		return tgypzBizc.selectSbmc(params);
	}
	
	/**
	 * 查监测类型数据字典
	 * @param params
	 * @return
	 */
	@RequestMapping("/selectJclx")
	public @ItemResponseBody
	QueryResultObject selectJclx(@QueryRequestParam("params") RequestCondition params) {
		return tgypzBizc.selectJclx(params);
	}
	
//	树
	/**
	 * 获取根节点
	 * @param type
	 * @return
	 */
	@RequestMapping("/tree")
	public @TreeResponseBody List<TreeNode> getRoot() {
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		node.setHasChildren(true);
		node.setText("1000KV特高压长治-南阳-荆门变电设备状态监视配置");// 显示字段的get方法
		node.setId("1"); // 主键的get方法
		node.setItemType("root"); // 根节点的itemType
		nodelist.add(node);
		return nodelist;
	}
	
	/**
	 * 获取指定id的下级节点
	 * @param id
	 * @return nodelist
	 */
	@RequestMapping(value = "/tree/{id}", method = RequestMethod.GET)
	public @TreeResponseBody
	List<TreeNode> getNodes(@PathVariable String id, @ItemRequestParam("params") String itemType) {
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
		
		String[] bdzs = { "长治变电站", "南阳变电站", "荆门变电站" };
		String[] linkedStations = { "32M00000005749094", "32M00000005749090", "32M00000005749092" };
		for(int i = 0; i < 3; i++) {
			TreeNode node = new TreeNode();
			node.setHasChildren(false);
			node.setText(bdzs[i]);// 显示字段的get方法
			node.setId(linkedStations[i]); // 主键的get方法
			node.setItemType("bdz"); // 根节点的itemType
			
			nodelist.add(node);
		}
		return nodelist;
	}
	
	/**
	 * 初始化字典
	 * 
	 * @return QueryResultObject
	 */
	@RequestMapping("/new")
	public @ItemResponseBody
	QueryResultObject initDictValues() {
		return tgypzBizc.initDict();
	}
	
	/**
	 * 注入逻辑构件
	 * @param tgypzBizc
	 */
	public void setTgypzBizc(ITgypzBizc tgypzBizc) {
		this.tgypzBizc = tgypzBizc;
	}
}
