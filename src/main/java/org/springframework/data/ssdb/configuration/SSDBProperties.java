package org.springframework.data.ssdb.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create by tuke on 2019-05-20
 */
@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.data.ssdb")
public class SSDBProperties {
    private String host = "localhost";
    private int port = 8888;
    // Separated by comma, such as host1:port1, host2:port2
    private String addresses;

    private int soTimeout;
    private String password;
}
