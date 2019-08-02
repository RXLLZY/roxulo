package com.swt.common.utils;

import java.io.Serializable;

/**
 * 通用结果对象
 * @author sys
 * @param <T>
 */
public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 请求成功
	public final static int STATUS_SUCCESS = 0;
	// 请求失败
	public final static int STATUS_FAILURE = -1;
	// 登录超期或未登录
	public final static int STATUS_NOT_LOGIN = 101;
	// 请求失败：业务错误，提示信息可直接展示给用户
	public final static int STATUS_FAILURE_BIZ = 102;
	// 请求失败：非业务错误（如：空指针、数组越界等代码异常）
	public final static int STATUS_FAILURE_UNBIZ = 103;

	// 状态值
	private int code;

	// 提示信息
	private String msg;

	// 返回结果
	private T result;

	/**
	 * 默认构造函数<p>
	 * 默认生成操作执行成功信息，无返回值
	 */
	public ResultBean() {
		this.code = STATUS_SUCCESS;
		this.msg = null;
		this.result = null;
	}

	/**
	 * 无正常返回
	 *
	 * @return
	 */
	public boolean fail() {
		return this.code != STATUS_SUCCESS;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
