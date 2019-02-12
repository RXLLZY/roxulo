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
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class HeaderExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public ResponseEntity handleRRException(RRException e){
		return new ResponseEntity(e.getMessage(), HttpStatus.valueOf(e.getStatus()));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity( "访问地址不存在，请确认后重新请求!",HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		String message = e.getMessage().split(" key ")[1].split("\r\n")[0];
		return new ResponseEntity(message + "不能重复", HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MySQLSyntaxErrorException.class)
	public ResponseEntity handleDuplicateKeyException(MySQLSyntaxErrorException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("SQL语法错误", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("没有权限，请联系管理员授权", HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity handleNullPointerException(JsonParseException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("请求参数json不合法", HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity handleNullPointerException(NullPointerException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("空指针异常", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity handleIndexOutOfBoundsException(IndexOutOfBoundsException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("数组越界", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("不合法参数", HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(ClassCastException.class)
	public ResponseEntity handleClassCastException(ClassCastException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("类型转换异常", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity handleFileNotFoundException(FileNotFoundException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("资源不存在", HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity handleFileNotFoundException(HttpRequestMethodNotSupportedException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("不支持的方法类型",HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity handleFileNotFoundException(HttpMessageNotReadableException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("请求体参数为空", HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(Exception e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity("未知异常，请联系管理员", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
