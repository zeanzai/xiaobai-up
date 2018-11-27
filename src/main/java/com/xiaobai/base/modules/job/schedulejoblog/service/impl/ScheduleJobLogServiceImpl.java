package com.xiaobai.base.modules.job.schedulejoblog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.QueryUtils;
import com.xiaobai.base.modules.job.schedulejoblog.dao.ScheduleJobLogDao;
import com.xiaobai.base.modules.job.schedulejoblog.entity.ScheduleJobLogEntity;
import com.xiaobai.base.modules.job.schedulejoblog.service.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.job.schedulejoblog.service.impl
 * @date 2018/11/20 18:34
 * @modified
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String jobId = (String)params.get("jobId");

		Page<ScheduleJobLogEntity> page = this.selectPage(
				new QueryUtils<ScheduleJobLogEntity>(params).getPage(),
				new EntityWrapper<ScheduleJobLogEntity>().like(StringUtils.isNotBlank(jobId),"job_id", jobId)
		);

		return new PageUtils(page);
	}

}

