package com.swt.common.controller;

import com.swt.common.responses.Responses;
import com.swt.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller公共组件
 * 
 * @author Roxulo
 * @email shuweitech.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected HttpServletResponse response;

	/**
	 * 成功返回
	 *
	 * @param object
	 * @return
	 */
	public <T> Responses<T> success(T object) {
		return Responses.<T>success(response, object);
	}

	/**
	 * 成功返回
	 *
	 * @return
	 */
	public Responses<Void> success() {
		return success(HttpStatus.OK);
	}

	/**
	 * 成功返回
	 *
	 * @param status
	 * @param object
	 * @return
	 */
	public <T> Responses<T> success(HttpStatus status, T object) {
		return Responses.<T>success(response, status, object);
	}

	/**
	 * 成功返回
	 *
	 * @param status
	 * @return
	 */
	public Responses<Void> success(HttpStatus status) {
		return Responses.<Void>success(response, status);
	}

	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected Long getDeptId() {
		return getUser().getDeptId();
	}
}
