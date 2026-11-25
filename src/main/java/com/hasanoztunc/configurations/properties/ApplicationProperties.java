package com.hasanoztunc.configurations.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "library.automation")
public class ApplicationProperties {

    private String secretKey;
    private int maxLoanCount;
    private Admin admin;
}
