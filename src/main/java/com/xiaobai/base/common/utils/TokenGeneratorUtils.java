package com.xiaobai.base.common.utils;


import com.xiaobai.base.common.exception.ReturnRuntimeException;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.utils
 * @date 2018/11/20 15:03
 * @modified
 */
public class TokenGeneratorUtils {
	public static String generateValue() {
		return generateValue(UUID.randomUUID().toString());
	}

	private static final char[] hexCode = "0123456789abcdef".toCharArray();

	public static String toHexString(byte[] data) {
		if(data == null) {
			return null;
		}
		StringBuilder r = new StringBuilder(data.length*2);
		for ( byte b : data) {
			r.append(hexCode[(b >> 4) & 0xF]);
			r.append(hexCode[(b & 0xF)]);
		}
		return r.toString();
	}

	public static String generateValue(String param) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(param.getBytes());
			byte[] messageDigest = algorithm.digest();
			return toHexString(messageDigest);
		} catch (Exception e) {
			throw new ReturnRuntimeException("生成Token失败", e);
		}
	}
}
