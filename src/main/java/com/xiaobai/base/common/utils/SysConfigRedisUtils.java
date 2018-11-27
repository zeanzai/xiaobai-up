package com.xiaobai.base.common.utils;

import com.xiaobai.base.modules.sys.sysconfig.entity.SysConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.utils
 * @date 2018/11/20 16:17
 * @modified
 */
@Component
public class SysConfigRedisUtils {
	@Autowired
	private RedisUtils redisUtils;

	public void saveOrUpdate(SysConfigEntity config) {
		if(config == null){
			return ;
		}
		String key = RedisKeysUtils.getSysConfigKey(config.getParamKey());
		redisUtils.set(key, config);
	}

	public void delete(String configKey) {
		String key = RedisKeysUtils.getSysConfigKey(configKey);
		redisUtils.delete(key);
	}

	public SysConfigEntity get(String configKey){
		String key = RedisKeysUtils.getSysConfigKey(configKey);
		return redisUtils.get(key, SysConfigEntity.class);
	}
}

