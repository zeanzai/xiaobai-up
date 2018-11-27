package com.xiaobai.base.common.exception;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.exception
 * @date 2018/11/20 11:35
 * @modified
 */
public class ReturnRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String msg;
	private int code = 500;

	public ReturnRuntimeException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public ReturnRuntimeException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public ReturnRuntimeException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public ReturnRuntimeException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


}
