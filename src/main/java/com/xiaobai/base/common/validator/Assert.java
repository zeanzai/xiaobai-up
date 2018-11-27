package com.xiaobai.base.common.validator;

import com.xiaobai.base.common.exception.ReturnRuntimeException;
import org.apache.commons.lang.StringUtils;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.validator
 * @date 2018/11/20 11:01
 * @modified
 */
public abstract class Assert {

	public static void isBlank(String str, String message) {
		if (StringUtils.isBlank(str)) {
			throw new ReturnRuntimeException(message);
		}
	}

	public static void isNull(Object object, String message) {
		if (object == null) {
			throw new ReturnRuntimeException(message);
		}
	}
}

