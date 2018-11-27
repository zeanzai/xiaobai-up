package com.xiaobai.base.modules.sys.sysrole.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.modules.sys.sysrole.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysrole.service
 * @date 2018/11/20 15:43
 * @modified
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void save(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);


	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}

