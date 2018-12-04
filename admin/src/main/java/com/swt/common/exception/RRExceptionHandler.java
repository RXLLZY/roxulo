/**
 *
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.swt.common.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.swt.common.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.FileNotFoundException;

/**
 * 异常处理器
 *
 * @author Mark @shuweitech.com
 * @since 1.0.0 2016-10-27
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e){
		R r = new R();
		r.put("status", e.getStatus());
		r.put("message", e.getMessage());
		return r;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public R handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return R.error(404, "访问地址不存在，请确认后重新请求!");
	}
	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		String message = e.getMessage().split(" key ")[1].split("\r\n")[0];
		return R.error(message + "不能重复");
	}

	@ExceptionHandler(MySQLSyntaxErrorException.class)
	public R handleDuplicateKeyException(MySQLSyntaxErrorException e){
		logger.error(e.getMessage(), e);
		return R.error("SQL语法错误");
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(JsonParseException.class)
	public R handleNullPointerException(JsonParseException e){
		logger.error(e.getMessage(), e);
		return R.error("请求参数json不合法");
	}
	@ExceptionHandler(NullPointerException.class)
	public R handleNullPointerException(NullPointerException e){
		logger.error(e.getMessage(), e);
		return R.error("空指针异常");
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	public R handleIndexOutOfBoundsException(IndexOutOfBoundsException e){
		logger.error(e.getMessage(), e);
		return R.error("数组越界");
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public R handleIllegalArgumentException(IllegalArgumentException e){
		logger.error(e.getMessage(), e);
		return R.error("不合法参数");
	}

	@ExceptionHandler(ClassCastException.class)
	public R handleClassCastException(ClassCastException e){
		logger.error(e.getMessage(), e);
		return R.error("类型转换异常");
	}

	@ExceptionHandler(FileNotFoundException.class)
	public R handleFileNotFoundException(FileNotFoundException e){
		logger.error(e.getMessage(), e);
		return R.error("资源不存在");
	}
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public R handleFileNotFoundException(HttpRequestMethodNotSupportedException e){
		logger.error(e.getMessage(), e);
		return R.error("不支持的方法类型");
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public R handleFileNotFoundException(HttpMessageNotReadableException e){
		logger.error(e.getMessage(), e);
		return R.error("请求体参数为空");
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}
}
