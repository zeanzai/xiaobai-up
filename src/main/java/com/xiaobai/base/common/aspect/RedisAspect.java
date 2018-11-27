package com.xiaobai.base.common.aspect;

import com.xiaobai.base.common.exception.ReturnRuntimeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.aspect
 * @date 2018/11/20 14:41
 * @modified
 */
@Aspect
@Configuration
public class RedisAspect {
	private Logger logger = LoggerFactory.getLogger(getClass());

	//是否开启redis缓存  true开启   false关闭
	@Value("${spring.redis.open: false}")
	private boolean open;

	@Around("execution(* com.xiaobai.base.common.utils.RedisUtils.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object result = null;
		if(open){
			try{
				result = point.proceed();
			}catch (Exception e){
				logger.error("redis error", e);
				throw new ReturnRuntimeException("Redis服务异常");
			}
		}
		return result;
	}
}
