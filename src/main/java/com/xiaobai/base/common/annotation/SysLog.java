package com.xiaobai.base.common.annotation;

import java.lang.annotation.*;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.common.annotation
 * @date 2018/11/20 14:43
 * @modified
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}

