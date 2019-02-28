package ru.gazprom_neft.amq_client.app.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration parameters filled in from application_nonlocal.properties and overridden using env variables on Openshift.
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "amqp")
public class AMQPConfiguration {

    /**
     * AMQ remoteURI
     */
    private String remoteURI;

    /**
     * AMQ service host
     */
    private String host;

    /**
     * AMQ service port
     */
    private Integer port;

    /**
     * AMQ username
     */
    private String username;

    /**
     * AMQ password
     */
    private String password;
}
