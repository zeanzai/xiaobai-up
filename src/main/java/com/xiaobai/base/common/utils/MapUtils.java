package com.xiaobai.base.common.utils;

import java.util.HashMap;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.utils
 * @date 2018/11/20 11:41
 * @modified
 */
public class MapUtils extends HashMap<String, Object> {

	@Override
	public MapUtils put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
