package com.swt.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swt.modules.sys.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户与角色对应关系
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年9月18日 上午9:34:46
 */
public interface ISysUserRoleDao extends BaseMapper<SysUserRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
