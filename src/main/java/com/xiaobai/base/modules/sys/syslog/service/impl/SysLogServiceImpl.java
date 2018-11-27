package com.xiaobai.base.modules.sys.syslog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.QueryUtils;
import com.xiaobai.base.modules.sys.syslog.dao.SysLogDao;
import com.xiaobai.base.modules.sys.syslog.entity.SysLogEntity;
import com.xiaobai.base.modules.sys.syslog.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.syslog.service.impl
 * @date 2018/11/20 16:22
 * @modified
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String key = (String)params.get("key");

		Page<SysLogEntity> page = this.selectPage(
				new QueryUtils<SysLogEntity>(params).getPage(),
				new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
		);

		return new PageUtils(page);
	}
}

