package com.xiaobai.base.modules.sys.sysuser.entity;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.modules.sys.sysuser.entity
 * @date 2018/11/20 15:49
 * @modified
 */
public class PasswordDO {
	/**
	 * 原密码
	 */
	private String password;
	/**
	 * 新密码
	 */
	private String newPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
