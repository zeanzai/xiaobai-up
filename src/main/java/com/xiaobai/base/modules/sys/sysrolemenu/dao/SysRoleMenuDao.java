package com.xiaobai.base.modules.sys.sysrolemenu.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaobai.base.modules.sys.sysrolemenu.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysrolemenu.dao
 * @date 2018/11/20 16:03
 * @modified
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}

