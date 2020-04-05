package com.booking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application")
public class AppConfig {

    private String uiHost;

    public String getUiHost() {
        return uiHost;
    }

    public void setUiHost(String uiHost) {
        this.uiHost = uiHost;
    }
}
