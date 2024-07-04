package org.ml404.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@AutoConfiguration
public class WebConfig {

    @Bean
    public static WebClient localApiClient() {
        return WebClient.create("http://localhost:8080/api/v1");
    }

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }
}
