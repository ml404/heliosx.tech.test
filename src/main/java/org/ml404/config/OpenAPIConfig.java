package org.ml404.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("My API")
                        .description("API documentation for my HeliosX tech test")
                        .version("v1.0.0")
                        .contact(new Contact().name("Matthew Layton").url("https://github.com/ml404").email("matt_gl@outlook.com"))
                        .license(new License().name("Mattpache 2.0 license").url("https://github.com/ml404")));
    }
}
