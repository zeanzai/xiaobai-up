package com.xiaobai.base.modules.job.schedulejoblog.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.modules.job.schedulejoblog.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.job.schedulejoblog.service
 * @date 2018/11/20 18:33
 * @modified
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);

}

