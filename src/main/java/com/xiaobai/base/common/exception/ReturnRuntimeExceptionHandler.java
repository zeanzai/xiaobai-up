package com.xiaobai.base.common.exception;

import com.xiaobai.base.common.utils.ReturnResultUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.exception
 * @date 2018/11/20 11:36
 * @modified
 */
@RestControllerAdvice
public class ReturnRuntimeExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(ReturnRuntimeException.class)
	public ReturnResultUtils handleRRException(ReturnRuntimeException e){
		ReturnResultUtils r = new ReturnResultUtils();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ReturnResultUtils handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return ReturnResultUtils.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ReturnResultUtils handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return ReturnResultUtils.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public ReturnResultUtils handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return ReturnResultUtils.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public ReturnResultUtils handleException(Exception e){
		logger.error(e.getMessage(), e);
		return ReturnResultUtils.error();
	}
}
