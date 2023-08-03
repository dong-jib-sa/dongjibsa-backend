package com.djs.dongjibsabackend.domain.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

     @Bean
     public OpenAPI openAPI() {
         return new OpenAPI()
             .components(new Components())
             .info(apiInfo());
     }

     private Info apiInfo() {
         return new Info()
             .title("장바구니 친구 | Our Basket")
             .description("장바구니 친구 앱 개발용 REST API")
             .version("1.0.0");
     }
}