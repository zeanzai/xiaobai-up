package com.xiaobai.base.modules.sys.sysrolemenu.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaobai.base.modules.sys.sysrolemenu.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysrolemenu.service
 * @date 2018/11/20 16:03
 * @modified
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

	void saveOrUpdate(Long roleId, List<Long> menuIdList);

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);

}

