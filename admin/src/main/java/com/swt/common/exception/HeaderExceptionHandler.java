package com.swt.common.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.swt.common.cons.Regex;
import com.swt.common.responses.FailedResponse;
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
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		HttpStatus resolve = HttpStatus.resolve(e.getStatus());
		if(resolve == null){
			FailedResponse build = FailedResponse.builder().message(e.getMessage()).status(e.getStatus()).time(LocalDateTime.now()).build();
			return new ResponseEntity(build, HttpStatus.INTERNAL_SERVER_ERROR);
		}else{
			FailedResponse build = FailedResponse.builder().message(e.getMessage()).status(e.getStatus()).time(LocalDateTime.now()).build();
			return new ResponseEntity(build, resolve);
		}
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		//No handler found for uri [/q1/q1/1/_search] and method [POST]
		return new ResponseEntity(FailedResponse.builder().message("访问地址不存在，请确认后重新请求!").status(400).time(LocalDateTime.now()).build(),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		Pattern pattern= Pattern.compile(Regex.DUPLICATE_ENTRY);
		Matcher matcher = pattern.matcher(e.getMessage());
		String message = "";
		if (matcher.find()) {
			message = matcher.group(0);
		}
		return new ResponseEntity(FailedResponse.builder().message(message + "不能重复").status(409).time(LocalDateTime.now()).build(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MySQLSyntaxErrorException.class)
	public ResponseEntity handleDuplicateKeyException(MySQLSyntaxErrorException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("SQL语法错误").status(500).time(LocalDateTime.now()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("没有权限，请联系管理员授权").status(401).time(LocalDateTime.now()).build(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity handleNullPointerException(JsonParseException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("请求参数json不合法").status(406).time(LocalDateTime.now()).build(), HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity handleNullPointerException(NullPointerException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("空指针异常").status(500).time(LocalDateTime.now()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity handleIndexOutOfBoundsException(IndexOutOfBoundsException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("数组越界").status(500).time(LocalDateTime.now()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("参数不合法").status(406).time(LocalDateTime.now()).build(), HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(ClassCastException.class)
	public ResponseEntity handleClassCastException(ClassCastException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("类型转换异常").status(500).time(LocalDateTime.now()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity handleFileNotFoundException(FileNotFoundException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("资源不存在").status(404).time(LocalDateTime.now()).build(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity handleFileNotFoundException(HttpRequestMethodNotSupportedException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("不支持的方法类型").status(404).time(LocalDateTime.now()).build(),HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity handleFileNotFoundException(HttpMessageNotReadableException e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("参数转换失败").status(406).time(LocalDateTime.now()).build(), HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(Exception e){
		logger.error(e.getMessage(), e);
		return new ResponseEntity(FailedResponse.builder().message("未知异常，请联系管理员").status(500).time(LocalDateTime.now()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
