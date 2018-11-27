package com.xiaobai.base.modules.sys.support.service.impl;

import com.xiaobai.base.common.utils.ConstantUtils;
import com.xiaobai.base.modules.sys.support.service.ShiroService;
import com.xiaobai.base.modules.sys.sysmenu.dao.SysMenuDao;
import com.xiaobai.base.modules.sys.sysmenu.entity.SysMenuEntity;
import com.xiaobai.base.modules.sys.sysuser.dao.SysUserDao;
import com.xiaobai.base.modules.sys.sysuser.entity.SysUserEntity;
import com.xiaobai.base.modules.sys.sysusertoken.dao.SysUserTokenDao;
import com.xiaobai.base.modules.sys.sysusertoken.entity.SysUserTokenEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.support.service.impl
 * @date 2018/11/20 16:30
 * @modified
 */
@Service
public class ShiroServiceImpl implements ShiroService {

	@Autowired
	private SysMenuDao sysMenuDao;

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysUserTokenDao sysUserTokenDao;

	@Override
	public Set<String> getUserPermissions(long userId) {
		List<String> permsList;

		//系统管理员，拥有最高权限
		if(userId == ConstantUtils.SUPER_ADMIN){
			List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
			permsList = new ArrayList<>(menuList.size());
			for(SysMenuEntity menu : menuList){
				permsList.add(menu.getPerms());
			}
		}else{
			permsList = sysUserDao.queryAllPerms(userId);
		}
		//用户权限列表
		Set<String> permsSet = new HashSet<>();
		for(String perms : permsList){
			if(StringUtils.isBlank(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		return permsSet;
	}

	@Override
	public SysUserTokenEntity queryByToken(String token) {
		return sysUserTokenDao.queryByToken(token);
	}

	@Override
	public SysUserEntity queryUser(Long userId) {
		return sysUserDao.selectById(userId);
	}
}

