package ru.gazprom_neft.amq_client.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.JmsTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.jms.*;

public class AMQProcessor implements Processor {

    private static final Logger LOGGER = LogManager.getLogger(AMQProcessor.class);

    @Value("${topic.name}")
    private String topicName;

    @Autowired
    JmsConnectionFactory jmsConnectionFactory;


    private TopicSession topicSession;
    private TopicSubscriber topicSubscriber;
    private TopicConnection topicConnection;

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setHeader("Content-Type", "text/plain");
        try {
            Message message = topicSubscriber.receive(500);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Message is " + message.getClass() + " class");
            }
            if (message instanceof BytesMessage) {
                exchange.getOut().setBody(message.getBody(byte[].class));
            } else if (message instanceof ObjectMessage) {
                exchange.getOut().setBody(message.getBody(Object.class));
            } else if (message instanceof TextMessage) {
                exchange.getOut().setBody(message.getBody(String.class));
            }
        } catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("No messages available");
            }
            exchange.getOut().setBody("No messages available");
        }
    }

    private void init() throws JMSException {
        TopicConnectionFactory topicConnectionFactory = jmsConnectionFactory;
        TopicConnection connection = topicConnectionFactory.createTopicConnection();
        connection.setClientID("camel");
        String modifiedTopicName = "topic://" + topicName;
        TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(modifiedTopicName);
        LOGGER.debug("topic =" + ((JmsTopic) topic).getTopicName() + " address = " + ((JmsTopic) topic).getAddress());
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "processor");
        this.topicConnection = connection;
        this.topicSession = session;
        this.topicSubscriber = subscriber;
        connection.start();
    }

    private void destroy() throws JMSException {
        topicSubscriber.close();
        topicSession.close();
        topicConnection.close();
    }
}
