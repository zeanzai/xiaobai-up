package com.xiaobai.base.common.validator;


import com.xiaobai.base.common.exception.ReturnRuntimeException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.validator
 * @date 2018/11/20 11:39
 * @modified
 */
public class ValidatorUtils {
	private static Validator validator;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * 校验对象
	 * @param object        待校验对象
	 * @param groups        待校验的组
	 * @throws ReturnRuntimeException  校验不通过，则报RRException异常
	 */
	public static void validateEntity(Object object, Class<?>... groups)
			throws ReturnRuntimeException {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			StringBuilder msg = new StringBuilder();
			for(ConstraintViolation<Object> constraint:  constraintViolations){
				msg.append(constraint.getMessage()).append("<br>");
			}
			throw new ReturnRuntimeException(msg.toString());
		}
	}
}

