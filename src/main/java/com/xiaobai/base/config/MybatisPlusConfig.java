package com.xiaobai.base.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.sysconfig
 * @date 2018/11/20 09:55
 * @modified
 */
@Configuration
public class MybatisPlusConfig {


	/**
	 * 分页插件
	 *
	 * @author  w.x.y
	 * @date    11/20/2018 9:56 AM
	 * @param   []
	 * @return  com.baomidou.mybatisplus.plugins.PaginationInterceptor
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
}

