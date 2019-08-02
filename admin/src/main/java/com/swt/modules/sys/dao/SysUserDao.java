package com.swt.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swt.modules.sys.entity.SysUserEntity;

import java.util.List;

/**
 * 系统用户
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年9月18日 上午9:34:11
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

}
