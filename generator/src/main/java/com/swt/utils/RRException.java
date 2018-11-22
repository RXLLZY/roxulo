package com.swt.utils;

/**
 * 自定义异常
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年10月27日 下午10:11:27
 */
public class RRException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String message;
    private int status = 500;
    
    public RRException(String message) {
		super(message);
		this.message = message;
	}
	
	public RRException(String message, Throwable e) {
		super(message, e);
		this.message = message;
	}
	
	public RRException(String message, int status) {
		super(message);
		this.message = message;
		this.status = status;
	}
	
	public RRException(String message, int status, Throwable e) {
		super(message, e);
		this.message = message;
		this.status = status;
	}

	public String getmessage() {
		return message;
	}

	public void setmessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
