package com.xiaobai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai
 * @date 2018/11/20 09:05
 * @modified
 */
@SpringBootApplication
public class XiaoBaiApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(XiaoBaiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(XiaoBaiApplication.class);
	}
}
