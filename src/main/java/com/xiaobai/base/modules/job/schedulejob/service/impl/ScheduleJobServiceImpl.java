package com.xiaobai.base.modules.job.schedulejob.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaobai.base.common.utils.ConstantUtils;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.QueryUtils;
import com.xiaobai.base.common.utils.ScheduleUtils;
import com.xiaobai.base.modules.job.schedulejob.dao.ScheduleJobDao;
import com.xiaobai.base.modules.job.schedulejob.entity.ScheduleJobEntity;
import com.xiaobai.base.modules.job.schedulejob.service.ScheduleJobService;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.job.schedulejoblog.service.impl
 * @date 2018/11/20 18:34
 * @modified
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {
	@Autowired
	private Scheduler scheduler;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJobEntity> scheduleJobList = this.selectList(null);
		for(ScheduleJobEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
			//如果不存在，则创建
			if(cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			}else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String beanName = (String)params.get("beanName");

		Page<ScheduleJobEntity> page = this.selectPage(
				new QueryUtils<ScheduleJobEntity>(params).getPage(),
				new EntityWrapper<ScheduleJobEntity>().like(StringUtils.isNotBlank(beanName),"bean_name", beanName)
		);

		return new PageUtils(page);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(ScheduleJobEntity scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(ConstantUtils.ScheduleStatus.NORMAL.getValue());
		this.insert(scheduleJob);

		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(ScheduleJobEntity scheduleJob) {
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

		this.updateById(scheduleJob);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		}

		//删除数据
		this.deleteBatchIds(Arrays.asList(jobIds));
	}

	@Override
	public int updateBatch(Long[] jobIds, int status){
		Map<String, Object> map = new HashMap<>();
		map.put("list", jobIds);
		map.put("status", status);
		return baseMapper.updateBatch(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void run(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.run(scheduler, this.selectById(jobId));
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void pause(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.pauseJob(scheduler, jobId);
		}

		updateBatch(jobIds, ConstantUtils.ScheduleStatus.PAUSE.getValue());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void resume(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.resumeJob(scheduler, jobId);
		}

		updateBatch(jobIds, ConstantUtils.ScheduleStatus.NORMAL.getValue());
	}

}

