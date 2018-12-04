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

package com.swt.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author RoXuLo
 * @email @shuweitech.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("status", 200);
		put("message", "success");
	}

	public static R create() {
		R r = new R();
		r.put("status", 201);
		r.put("message", "资源创建成功");
		return r;
	}

	public static R delete() {
		R r = new R();
		r.put("status", 204);
		return r;
	}

	public static R error() {
		return error(500, "未知异常，请联系管理员");
	}

	public static R error(String message) {
		return error(500, message);
	}
	
	public static R error(int status, String message) {
		R r = new R();
		r.put("status", status);
		r.put("message", message);
		return r;
	}

	public static R ok(String message) {
		R r = new R();
		r.put("message", message);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
