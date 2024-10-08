package com.alexeygold2077.MindGate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("application.properties")
@PropertySource("application-private.properties")
public class MindGateConfig {

    @Value("${PROXY_API_KEY}")
    private String PROXY_API_KEY;

    @Bean
    public Proxyapi proxyapi() {
        return new Proxyapi(PROXY_API_KEY);
    }

}