package sv.com.masbytes.shopinv.config;

import org.springframework.context.annotation.Bean;
import org.springdoc.core.models.GroupedOpenApi;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("shop-inventory")
            .pathsToMatch("/**")
            .build();
    }
}