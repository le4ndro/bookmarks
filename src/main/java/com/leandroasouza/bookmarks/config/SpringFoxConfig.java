package com.leandroasouza.bookmarks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.leandroasouza.bookmarks.api.BookmarkController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


// Verification url: [base_url]/v2/api-docs
// Swagger UI: [base_url/swagger-ui.htm
@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {
		BookmarkController.class
})
public class SpringFoxConfig {

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.regex("/bookmarks.*"))                          
          .build();                                           
    }
}
