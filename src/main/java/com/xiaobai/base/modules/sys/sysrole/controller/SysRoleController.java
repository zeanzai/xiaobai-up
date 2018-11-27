package com.xiaobai.base.modules.sys.sysrole.controller;

import com.xiaobai.base.common.annotation.SysLog;
import com.xiaobai.base.common.controller.AbstractController;
import com.xiaobai.base.common.utils.ConstantUtils;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.ReturnResultUtils;
import com.xiaobai.base.common.validator.ValidatorUtils;
import com.xiaobai.base.modules.sys.sysrole.entity.SysRoleEntity;
import com.xiaobai.base.modules.sys.sysrole.service.SysRoleService;
import com.xiaobai.base.modules.sys.sysrolemenu.service.SysRoleMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysrole.controller
 * @date 2018/11/20 15:45
 * @modified
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	public ReturnResultUtils list(@RequestParam Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getUserId() != ConstantUtils.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}

		PageUtils page = sysRoleService.queryPage(params);

		return ReturnResultUtils.ok().put("page", page);
	}

	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public ReturnResultUtils select(){
		Map<String, Object> map = new HashMap<>();

		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != ConstantUtils.SUPER_ADMIN){
			map.put("create_user_id", getUserId());
		}
		List<SysRoleEntity> list = sysRoleService.selectByMap(map);

		return ReturnResultUtils.ok().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public ReturnResultUtils info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.selectById(roleId);

		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		return ReturnResultUtils.ok().put("role", role);
	}

	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public ReturnResultUtils save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);

		role.setCreateUserId(getUserId());
		sysRoleService.save(role);

		return ReturnResultUtils.ok();
	}

	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public ReturnResultUtils update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);

		role.setCreateUserId(getUserId());
		sysRoleService.update(role);

		return ReturnResultUtils.ok();
	}

	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public ReturnResultUtils delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);

		return ReturnResultUtils.ok();
	}
}

