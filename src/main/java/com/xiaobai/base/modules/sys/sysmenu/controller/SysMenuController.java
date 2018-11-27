package com.xiaobai.base.modules.sys.sysmenu.controller;

import com.xiaobai.base.common.annotation.SysLog;
import com.xiaobai.base.common.controller.AbstractController;
import com.xiaobai.base.common.exception.ReturnRuntimeException;
import com.xiaobai.base.common.utils.ConstantUtils;
import com.xiaobai.base.common.utils.ReturnResultUtils;
import com.xiaobai.base.modules.sys.support.service.ShiroService;
import com.xiaobai.base.modules.sys.sysmenu.entity.SysMenuEntity;
import com.xiaobai.base.modules.sys.sysmenu.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysmenu.controller
 * @date 2018/11/20 15:54
 * @modified
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private ShiroService shiroService;

	/**
	 * 导航菜单
	 */
	@GetMapping("/nav")
	public ReturnResultUtils nav(){
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
		Set<String> permissions = shiroService.getUserPermissions(getUserId());
		return ReturnResultUtils.ok().put("menuList", menuList).put("permissions", permissions);
	}

	/**
	 * 所有菜单列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public List<SysMenuEntity> list(){
		List<SysMenuEntity> menuList = sysMenuService.selectList(null);
		for(SysMenuEntity sysMenuEntity : menuList){
			SysMenuEntity parentMenuEntity = sysMenuService.selectById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}

		return menuList;
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public ReturnResultUtils select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);

		return ReturnResultUtils.ok().put("menuList", menuList);
	}

	/**
	 * 菜单信息
	 */
	@GetMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public ReturnResultUtils info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = sysMenuService.selectById(menuId);
		return ReturnResultUtils.ok().put("menu", menu);
	}

	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@PostMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public ReturnResultUtils save(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.insert(menu);

		return ReturnResultUtils.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@PostMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public ReturnResultUtils update(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.updateById(menu);

		return ReturnResultUtils.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@PostMapping("/delete/{menuId}")
	@RequiresPermissions("sys:menu:delete")
	public ReturnResultUtils delete(@PathVariable("menuId") long menuId){
		if(menuId <= 31){
			return ReturnResultUtils.error("系统菜单，不能删除");
		}

		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return ReturnResultUtils.error("请先删除子菜单或按钮");
		}

		sysMenuService.delete(menuId);

		return ReturnResultUtils.ok();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new ReturnRuntimeException("菜单名称不能为空");
		}

		if(menu.getParentId() == null){
			throw new ReturnRuntimeException("上级菜单不能为空");
		}

		//菜单
		if(menu.getType() == ConstantUtils.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new ReturnRuntimeException("菜单URL不能为空");
			}
		}

		//上级菜单类型
		int parentType = ConstantUtils.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.selectById(menu.getParentId());
			parentType = parentMenu.getType();
		}

		//目录、菜单
		if(menu.getType() == ConstantUtils.MenuType.CATALOG.getValue() ||
				menu.getType() == ConstantUtils.MenuType.MENU.getValue()){
			if(parentType != ConstantUtils.MenuType.CATALOG.getValue()){
				throw new ReturnRuntimeException("上级菜单只能为目录类型");
			}
			return ;
		}

		//按钮
		if(menu.getType() == ConstantUtils.MenuType.BUTTON.getValue()){
			if(parentType != ConstantUtils.MenuType.MENU.getValue()){
				throw new ReturnRuntimeException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}

