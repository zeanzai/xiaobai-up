package com.xiaobai.base.common.utils;

import com.baomidou.mybatisplus.plugins.Page;
import com.xiaobai.base.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.utils
 * @date 2018/11/20 11:42
 * @modified
 */
public class QueryUtils<T> extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	/**
	 * mybatis-plus分页参数
	 */
	private Page<T> page;
	/**
	 * 当前页码
	 */
	private int currPage = 1;
	/**
	 * 每页条数
	 */
	private int limit = 10;

	public QueryUtils(Map<String, Object> params){
		this.putAll(params);

		//分页参数
		if(params.get("page") != null){
			currPage = Integer.parseInt((String)params.get("page"));
		}
		if(params.get("limit") != null){
			limit = Integer.parseInt((String)params.get("limit"));
		}

		this.put("offset", (currPage - 1) * limit);
		this.put("page", currPage);
		this.put("limit", limit);

		//防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
		String sidx = SQLFilter.sqlInject((String)params.get("sidx"));
		String order = SQLFilter.sqlInject((String)params.get("order"));
		this.put("sidx", sidx);
		this.put("order", order);

		//mybatis-plus分页
		this.page = new Page<>(currPage, limit);

		//排序
		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
			this.page.setOrderByField(sidx);
			this.page.setAsc("ASC".equalsIgnoreCase(order));
		}

	}

	public Page<T> getPage() {
		return page;
	}

	public int getCurrPage() {
		return currPage;
	}

	public int getLimit() {
		return limit;
	}
}
