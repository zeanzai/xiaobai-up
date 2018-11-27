package com.xiaobai.base.common.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.oauth2
 * @date 2018/11/20 15:04
 * @modified
 */
public class OAuth2Token implements AuthenticationToken {
	private String token;

	public OAuth2Token(String token){
		this.token = token;
	}

	@Override
	public String getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}

