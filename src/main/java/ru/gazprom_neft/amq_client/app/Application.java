package ru.gazprom_neft.amq_client.app;

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import ru.gazprom_neft.amq_client.app.configuration.AMQPConfiguration;


@SpringBootApplication(scanBasePackages = "ru.gazprom_neft.amq_client")
@ImportResource({"classpath:spring/camel-context.xml"})
@PropertySource("classpath:application.properties")
public class Application {

    // must have a main method spring-boot can run
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean(name = "amqp-component")
    AMQPComponent amqpComponent(AMQPConfiguration config) {
        JmsConnectionFactory qpid = jmsConnectionFactory(config);
        PooledConnectionFactory factory = new PooledConnectionFactory();
        factory.setConnectionFactory(qpid);
        return new AMQPComponent(factory);
    }
    @Bean
    JmsConnectionFactory jmsConnectionFactory(AMQPConfiguration config){
        JmsConnectionFactory qpid = new JmsConnectionFactory(config.getUsername(), config.getPassword(), config.getRemoteURI());
        qpid.setTopicPrefix("topic://");
        return qpid;
    }

}
