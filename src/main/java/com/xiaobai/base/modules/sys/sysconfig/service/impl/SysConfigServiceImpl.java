package com.xiaobai.base.modules.sys.sysconfig.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.xiaobai.base.common.exception.ReturnRuntimeException;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.QueryUtils;
import com.xiaobai.base.common.utils.SysConfigRedisUtils;
import com.xiaobai.base.modules.sys.sysconfig.dao.SysConfigDao;
import com.xiaobai.base.modules.sys.sysconfig.entity.SysConfigEntity;
import com.xiaobai.base.modules.sys.sysconfig.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysconfig.service.impl
 * @date 2018/11/20 16:11
 * @modified
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {
	@Autowired
	private SysConfigRedisUtils sysConfigRedis;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("paramKey");

		Page<SysConfigEntity> page = this.selectPage(
				new QueryUtils<SysConfigEntity>(params).getPage(),
				new EntityWrapper<SysConfigEntity>()
						.like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
						.eq("status", 1)
		);

		return new PageUtils(page);
	}

	@Override
	public void save(SysConfigEntity config) {
		this.insert(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
		this.updateAllColumnById(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
		sysConfigRedis.delete(key);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysConfigEntity config = this.selectById(id);
			sysConfigRedis.delete(config.getParamKey());
		}

		this.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = sysConfigRedis.get(key);
		if(config == null){
			config = baseMapper.queryByKey(key);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getParamValue();
	}

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new ReturnRuntimeException("获取参数失败");
		}
	}
}
