package com.xiaobai.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.sysconfig
 * @date 2018/11/20 10:41
 * @modified
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowCredentials(true)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.maxAge(3600);
	}
}
