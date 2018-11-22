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

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
