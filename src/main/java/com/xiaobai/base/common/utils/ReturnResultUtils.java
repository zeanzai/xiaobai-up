package com.xiaobai.base.common.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.utils
 * @date 2018/11/20 09:09
 * @modified
 */
public class ReturnResultUtils extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public ReturnResultUtils() {
		put("code", 0);
		put("msg", "success");
	}

	public static ReturnResultUtils error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	public static ReturnResultUtils error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	public static ReturnResultUtils error(int code, String msg) {
		ReturnResultUtils r = new ReturnResultUtils();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static ReturnResultUtils ok(String msg) {
		ReturnResultUtils r = new ReturnResultUtils();
		r.put("msg", msg);
		return r;
	}

	public static ReturnResultUtils ok(Map<String, Object> map) {
		ReturnResultUtils r = new ReturnResultUtils();
		r.putAll(map);
		return r;
	}

	public static ReturnResultUtils ok() {
		return new ReturnResultUtils();
	}

	public ReturnResultUtils put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
