package com.swt.common.utils;

import com.swt.common.exception.RRException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用结果对象
 * @author sys
 * @param <T>
 */
public class ResultBean<T> extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 1L;

	public ResultBean() {
		put("status", 200);
		put("message", "success");
	}

	public static ResultBean create() {
		ResultBean resultBean = new ResultBean();
		resultBean.put("status", 201);
		resultBean.put("message", "资源创建成功");
		return resultBean;
	}

	public static ResultBean error() {
		return error(500, "未知异常，请联系管理员");
	}

	public static ResultBean error(String message) {
		return error(500, message);
	}

	public static ResultBean error(int status, String message) {
		throw new RRException(message, status);
	}

	public static ResultBean ok(String message) {
		ResultBean resultBean = new ResultBean();
		resultBean.put("message", message);
		return resultBean;
	}

	public static ResultBean ok(Map<String, Object> map) {
		ResultBean resultBean = new ResultBean();
		resultBean.putAll(map);
		return resultBean;
	}

	public static ResultBean ok() {
		return new ResultBean();
	}

	@Override
	public ResultBean put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public ResultBean setResult(Object value) {
		super.put("result", value);
		return this;
	}
}
