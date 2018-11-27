package com.xiaobai.base.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author w.x.y
 * @version V1.0
 * @project xiaobai-up
 * @package com.xiaobai.base.sysconfig
 * @date 2018/11/20 09:36
 * @modified
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/static/swagger/");
	}


	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//加了ApiOperation注解的类，才生成接口文档
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				//包下的类，才生成接口文档
				//.apis(RequestHandlerSelectors.basePackage("com.xiaobai.*.controller"))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(security());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("设备云")
				.description("xiaobai-up文档")
				.termsOfServiceUrl("http://www.xiaobai.com")
				.version("2.1")
				.build();
	}

	private List<ApiKey> security() {
		return newArrayList(
				new ApiKey("token", "token", "header")
		);
	}

}
