package com.xiaobai.base.modules.sys.sysuser.controller;

import com.xiaobai.base.common.annotation.SysLog;
import com.xiaobai.base.common.controller.AbstractController;
import com.xiaobai.base.common.utils.ConstantUtils;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.ReturnResultUtils;
import com.xiaobai.base.common.validator.Assert;
import com.xiaobai.base.common.validator.ValidatorUtils;
import com.xiaobai.base.common.validator.group.AddGroup;
import com.xiaobai.base.common.validator.group.UpdateGroup;
import com.xiaobai.base.modules.sys.sysuser.entity.PasswordDO;
import com.xiaobai.base.modules.sys.sysuser.entity.SysUserEntity;
import com.xiaobai.base.modules.sys.sysuser.service.SysUserService;
import com.xiaobai.base.modules.sys.sysuserrole.service.SysUserRoleService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysuser.controller
 * @date 2018/11/20 15:41
 * @modified
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public ReturnResultUtils list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != ConstantUtils.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}
		PageUtils page = sysUserService.queryPage(params);

		return ReturnResultUtils.ok().put("page", page);
	}

	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public ReturnResultUtils info(){
		return ReturnResultUtils.ok().put("user", getUser());
	}

	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@PostMapping("/password")
	public ReturnResultUtils password(@RequestBody PasswordDO form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");

		//sha256加密
		String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return ReturnResultUtils.error("原密码不正确");
		}

		return ReturnResultUtils.ok();
	}

	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public ReturnResultUtils info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.selectById(userId);

		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);

		return ReturnResultUtils.ok().put("user", user);
	}

	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public ReturnResultUtils save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.save(user);

		return ReturnResultUtils.ok();
	}

	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public ReturnResultUtils update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		user.setCreateUserId(getUserId());
		sysUserService.update(user);

		return ReturnResultUtils.ok();
	}

	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public ReturnResultUtils delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return ReturnResultUtils.error("系统管理员不能删除");
		}

		if(ArrayUtils.contains(userIds, getUserId())){
			return ReturnResultUtils.error("当前用户不能删除");
		}

		sysUserService.deleteBatch(userIds);

		return ReturnResultUtils.ok();
	}
}

