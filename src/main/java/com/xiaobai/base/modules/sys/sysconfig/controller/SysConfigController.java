package com.xiaobai.base.modules.sys.sysconfig.controller;

import com.xiaobai.base.common.annotation.SysLog;
import com.xiaobai.base.common.controller.AbstractController;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.ReturnResultUtils;
import com.xiaobai.base.common.validator.ValidatorUtils;
import com.xiaobai.base.modules.sys.sysconfig.entity.SysConfigEntity;
import com.xiaobai.base.modules.sys.sysconfig.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysconfig.controller
 * @date 2018/11/20 16:12
 * @modified
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 所有配置列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:config:list")
	public ReturnResultUtils list(@RequestParam Map<String, Object> params){
		PageUtils page = sysConfigService.queryPage(params);

		return ReturnResultUtils.ok().put("page", page);
	}


	/**
	 * 配置信息
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public ReturnResultUtils info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.selectById(id);

		return ReturnResultUtils.ok().put("config", config);
	}

	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@PostMapping("/save")
	@RequiresPermissions("sys:config:save")
	public ReturnResultUtils save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.save(config);

		return ReturnResultUtils.ok();
	}

	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@PostMapping("/update")
	@RequiresPermissions("sys:config:update")
	public ReturnResultUtils update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.update(config);

		return ReturnResultUtils.ok();
	}

	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@PostMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public ReturnResultUtils delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);

		return ReturnResultUtils.ok();
	}

}

