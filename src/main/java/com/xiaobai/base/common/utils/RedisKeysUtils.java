package com.xiaobai.base.common.utils;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.utils
 * @date 2018/11/20 14:38
 * @modified
 */
public class RedisKeysUtils {

	public static String getSysConfigKey(String key){
		return "sys:sysconfig:" + key;
	}

}
