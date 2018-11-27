package com.xiaobai.base.modules.sys.sysuserrole.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaobai.base.modules.sys.sysuserrole.entity.SysUserRoleEntity;

import java.util.List;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysuserrole.service
 * @date 2018/11/20 15:58
 * @modified
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

	void saveOrUpdate(Long userId, List<Long> roleIdList);

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}

