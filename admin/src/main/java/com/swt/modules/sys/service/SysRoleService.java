package com.swt.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.swt.common.utils.PageUtils;
import com.swt.modules.sys.entity.SysRoleEntity;

import java.util.Map;


/**
 * 角色
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveEntity(SysRoleEntity role);

	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);

}
