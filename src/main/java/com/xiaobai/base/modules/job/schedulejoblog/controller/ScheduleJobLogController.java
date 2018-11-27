package com.xiaobai.base.modules.job.schedulejoblog.controller;

import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.ReturnResultUtils;
import com.xiaobai.base.modules.job.schedulejoblog.entity.ScheduleJobLogEntity;
import com.xiaobai.base.modules.job.schedulejoblog.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.job.schedulejoblog.controller
 * @date 2018/11/20 18:39
 * @modified
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;

	/**
	 * 定时任务日志列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:schedule:syslog")
	public ReturnResultUtils list(@RequestParam Map<String, Object> params){
		PageUtils page = scheduleJobLogService.queryPage(params);

		return ReturnResultUtils.ok().put("page", page);
	}

	/**
	 * 定时任务日志信息
	 */
	@GetMapping("/info/{logId}")
	public ReturnResultUtils info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.selectById(logId);

		return ReturnResultUtils.ok().put("syslog", log);
	}
}

