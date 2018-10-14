package com.sgcc.pms.zbztjc.util.tgyjs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sgcc.pms.zbztjc.util.tgyjs.bizc.ITgyjsBizc;
import com.sgcc.uap.rest.annotation.ItemRequestParam;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.TreeResponseBody;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.support.TreeNode;

@Controller
@RequestMapping("/tgyjs")
public class TgyjsController {
	
	@Resource
    private ITgyjsBizc tgyjsBizc;
	
	/**
	 * 注入逻辑构件
	 * @param tgyjsBizc
	 */
	public void setTgyjsBizc(ITgyjsBizc tgyjsBizc) {
		this.tgyjsBizc = tgyjsBizc;
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
		return tgyjsBizc.query(params);
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
		node.setText("1000kV特高压长治-南阳-荆门变电设备状态监视");// 显示字段的get方法
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
		return tgyjsBizc.initDict();
	}

}
