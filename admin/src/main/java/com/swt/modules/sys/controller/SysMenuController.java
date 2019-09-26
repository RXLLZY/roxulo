package com.swt.modules.sys.controller;

import com.swt.common.annotation.SysLog;
import com.swt.common.controller.AbstractController;
import com.swt.common.exception.RRException;
import com.swt.common.responses.Responses;
import com.swt.common.utils.Constant;
import com.swt.common.utils.R;
import com.swt.modules.sys.entity.SysMenuEntity;
import com.swt.modules.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统菜单
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	public Responses<List<SysMenuEntity>> nav(){
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());

		return success(menuList);
	}
	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public Responses<List<SysMenuEntity>> list(){
		List<SysMenuEntity> menuList = sysMenuService.list(null);
		for(SysMenuEntity sysMenuEntity : menuList){
			SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}

		return success(menuList);
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public Responses<List<SysMenuEntity>> select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();
		
		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);

		return success(menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public Responses<SysMenuEntity> info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = sysMenuService.getById(menuId);
		return success(menu);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@RequestMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public Responses<SysMenuEntity> save(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);
		
		sysMenuService.save(menu);

		return success(menu);
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@RequestMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public Responses<SysMenuEntity> update(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);
				
		sysMenuService.updateById(menu);

		return success(menu);
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public Responses<Void> delete(long menuId){
		if(menuId <= 31){
			R.error("系统菜单，不能删除");
		}

		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			R.error("请先删除子菜单或按钮");
		}

		sysMenuService.delete(menuId);

		return success(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
