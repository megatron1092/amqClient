package ru.gazprom_neft.amq_client.app.configuration;

import org.apache.camel.util.jsse.KeyManagersParameters;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.TrustManagersParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * Настройка SSL HTTP-эндпоинта
 */
@Configuration
public class SslContextParametersAutoConfiguration {


    @Autowired
    Environment environment;

    @PostConstruct
    @Bean(name = "sslContextParameters")
    public SSLContextParameters sslContextParameters() {
        KeyStoreParameters trustParams = new KeyStoreParameters();

        TrustManagersParameters trustManParams = new TrustManagersParameters();
        trustManParams.setKeyStore(trustParams);

        KeyStoreParameters keyParams = new KeyStoreParameters();
        keyParams.setResource(environment.getProperty("server.ssl.key-store"));
        keyParams.setPassword(environment.getProperty("server.ssl.key-store-password"));

        KeyManagersParameters keyManParams = new KeyManagersParameters();
        keyManParams.setKeyStore(keyParams);
        keyManParams.setKeyPassword(keyParams.getPassword());

        SSLContextParameters sslContextParameters = new SSLContextParameters();
        sslContextParameters.setTrustManagers(trustManParams);
        sslContextParameters.setKeyManagers(keyManParams);

        return sslContextParameters;
    }
}
