package com.april.jetbrain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author april
 * @date 2024-04-15
 * @description
 */
@Data
@Configuration
@ConfigurationProperties("jetbrains.help")
public class JetbrainsHelpProperties {

    private String licenseName;

    private String assigneeName;

    private String expiryDate;
}
