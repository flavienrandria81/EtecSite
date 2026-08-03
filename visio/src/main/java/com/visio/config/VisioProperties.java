package com.visio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "visio")
public class VisioProperties {

    private String turnUrl = "";
    private String turnUsername = "";
    private String turnPassword = "";
}
