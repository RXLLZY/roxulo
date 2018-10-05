package com.swt.common.controller;

import com.swt.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 * 
 * @author Roxulo
 * @email shuweitech.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
	protected Integer getUserIntId() {
		return Integer.valueOf(getUser().getUserId().toString());
	}
	protected Long getDeptId() {
		return getUser().getDeptId();
	}
}
