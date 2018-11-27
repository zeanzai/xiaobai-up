package com.xiaobai.base.modules.sys.sysconfig.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.modules.sys.sysconfig.entity.SysConfigEntity;

import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysconfig.service
 * @date 2018/11/20 16:10
 * @modified
 */
public interface SysConfigService extends IService<SysConfigEntity> {

	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 保存配置信息
	 */
	public void save(SysConfigEntity config);

	/**
	 * 更新配置信息
	 */
	public void update(SysConfigEntity config);

	/**
	 * 根据key，更新value
	 */
	public void updateValueByKey(String key, String value);

	/**
	 * 删除配置信息
	 */
	public void deleteBatch(Long[] ids);

	/**
	 * 根据key，获取配置的value值
	 *
	 * @param key           key
	 */
	public String getValue(String key);

	/**
	 * 根据key，获取value的Object对象
	 * @param key    key
	 * @param clazz  Object对象
	 */
	public <T> T getConfigObject(String key, Class<T> clazz);

}

