package com.xiaobai.base.modules.sys.support.service;

import com.xiaobai.base.modules.sys.sysuser.entity.SysUserEntity;
import com.xiaobai.base.modules.sys.sysusertoken.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.support.service
 * @date 2018/11/20 16:29
 * @modified
 */
public interface ShiroService {
	/**
	 * 获取用户权限列表
	 */
	Set<String> getUserPermissions(long userId);

	SysUserTokenEntity queryByToken(String token);

	/**
	 * 根据用户ID，查询用户
	 * @param userId
	 */
	SysUserEntity queryUser(Long userId);
}

