package com.xiaobai.base.modules.job.schedulejob.controller;

import com.xiaobai.base.common.annotation.SysLog;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.ReturnResultUtils;
import com.xiaobai.base.common.validator.ValidatorUtils;
import com.xiaobai.base.modules.job.schedulejob.entity.ScheduleJobEntity;
import com.xiaobai.base.modules.job.schedulejob.service.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.job.schedulejoblog.controller
 * @date 2018/11/20 18:38
 * @modified
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * 定时任务列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:schedule:list")
	public ReturnResultUtils list(@RequestParam Map<String, Object> params){
		PageUtils page = scheduleJobService.queryPage(params);

		return ReturnResultUtils.ok().put("page", page);
	}

	/**
	 * 定时任务信息
	 */
	@GetMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	public ReturnResultUtils info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.selectById(jobId);

		return ReturnResultUtils.ok().put("schedule", schedule);
	}

	/**
	 * 保存定时任务
	 */
	@SysLog("保存定时任务")
	@PostMapping("/save")
	@RequiresPermissions("sys:schedule:save")
	public ReturnResultUtils save(@RequestBody ScheduleJobEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);

		scheduleJobService.save(scheduleJob);

		return ReturnResultUtils.ok();
	}

	/**
	 * 修改定时任务
	 */
	@SysLog("修改定时任务")
	@PostMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	public ReturnResultUtils update(@RequestBody ScheduleJobEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);

		scheduleJobService.update(scheduleJob);

		return ReturnResultUtils.ok();
	}

	/**
	 * 删除定时任务
	 */
	@SysLog("删除定时任务")
	@PostMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	public ReturnResultUtils delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);

		return ReturnResultUtils.ok();
	}

	/**
	 * 立即执行任务
	 */
	@SysLog("立即执行任务")
	@PostMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	public ReturnResultUtils run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);

		return ReturnResultUtils.ok();
	}

	/**
	 * 暂停定时任务
	 */
	@SysLog("暂停定时任务")
	@PostMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	public ReturnResultUtils pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);

		return ReturnResultUtils.ok();
	}

	/**
	 * 恢复定时任务
	 */
	@SysLog("恢复定时任务")
	@PostMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	public ReturnResultUtils resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);

		return ReturnResultUtils.ok();
	}

}

