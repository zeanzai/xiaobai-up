package com.xiaobai.base.modules.sys.syslog.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.modules.sys.syslog.entity.SysLogEntity;

import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.syslog.service
 * @date 2018/11/20 16:21
 * @modified
 */
public interface SysLogService extends IService<SysLogEntity> {

	PageUtils queryPage(Map<String, Object> params);

}

