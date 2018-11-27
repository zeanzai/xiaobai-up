package com.xiaobai.base.modules.sys.sysrole.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaobai.base.common.exception.ReturnRuntimeException;
import com.xiaobai.base.common.utils.ConstantUtils;
import com.xiaobai.base.common.utils.PageUtils;
import com.xiaobai.base.common.utils.QueryUtils;
import com.xiaobai.base.modules.sys.sysrole.dao.SysRoleDao;
import com.xiaobai.base.modules.sys.sysrole.entity.SysRoleEntity;
import com.xiaobai.base.modules.sys.sysrole.service.SysRoleService;
import com.xiaobai.base.modules.sys.sysrolemenu.service.SysRoleMenuService;
import com.xiaobai.base.modules.sys.sysuser.service.SysUserService;
import com.xiaobai.base.modules.sys.sysuserrole.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysrole.service.impl
 * @date 2018/11/20 15:44
 * @modified
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String roleName = (String)params.get("roleName");
		Long createUserId = (Long)params.get("createUserId");

		Page<SysRoleEntity> page = this.selectPage(
				new QueryUtils<SysRoleEntity>(params).getPage(),
				new EntityWrapper<SysRoleEntity>()
						.like(StringUtils.isNotBlank(roleName),"role_name", roleName)
						.eq(createUserId != null,"create_user_id", createUserId)
		);

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysRoleEntity role) {
		role.setCreateTime(new Date());
		this.insert(role);

		//检查权限是否越权
		checkPrems(role);

		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysRoleEntity role) {
		this.updateById(role);

		//检查权限是否越权
		checkPrems(role);

		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] roleIds) {
		//删除角色
		this.deleteBatchIds(Arrays.asList(roleIds));

		//删除角色与菜单关联
		sysRoleMenuService.deleteBatch(roleIds);

		//删除角色与用户关联
		sysUserRoleService.deleteBatch(roleIds);
	}


	@Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return baseMapper.queryRoleIdList(createUserId);
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleEntity role){
		//如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if(role.getCreateUserId() == ConstantUtils.SUPER_ADMIN){
			return ;
		}

		//查询用户所拥有的菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());

		//判断是否越权
		if(!menuIdList.containsAll(role.getMenuIdList())){
			throw new ReturnRuntimeException("新增角色的权限，已超出你的权限范围");
		}
	}
}
