package com.curso.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	 @Bean
	    public Docket api() { 
		  return new Docket( DocumentationType.SWAGGER_2)
	                .select()
	                .apis( RequestHandlerSelectors.any())
	                .paths( PathSelectors.any())
	                .build()
	                .apiInfo(getApiInfo());
	    }

	 private ApiInfo getApiInfo() {
	        return new ApiInfoBuilder()
	                .title("Documentação com Swagger")
	                .version("1.0")
	                .description("Documentação E-commerce")
	                .contact(new Contact("Pedro Paulo", "http://www.teste.com", "teste@email.com"))
	                .license("Apache License Version 2.0")
	                .build();
	    }

	 
}
