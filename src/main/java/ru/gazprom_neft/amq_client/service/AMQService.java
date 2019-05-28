package ru.gazprom_neft.amq_client.service;


import lombok.extern.log4j.Log4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.JmsTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.*;


@Log4j
@Service
public class AMQService {

    @Autowired
    JmsConnectionFactory jmsConnectionFactory;
    private TopicSession topicSession;
    private TopicSubscriber topicSubscriber;
    private TopicConnection topicConnection;
    @Value("${topic.name}")
    private String topicName;

    @PostConstruct
    private void init() throws JMSException {
        TopicConnectionFactory topicConnectionFactory = jmsConnectionFactory;
        TopicConnection connection = topicConnectionFactory.createTopicConnection();
        connection.setClientID("camel");
        String modifiedTopicName = "topic://" + topicName;
        TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(modifiedTopicName);
        log.debug("topic =" + ((JmsTopic) topic).getTopicName() + " address = " + ((JmsTopic) topic).getAddress());
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "processor");
        this.topicConnection = connection;
        this.topicSession = session;
        this.topicSubscriber = subscriber;
        connection.start();
    }

    public Message popMessage() throws JMSException {
        Message message;
        message = topicSubscriber.receive(500);
        if (log.isDebugEnabled() && (message != null)) {
            log.debug("Message is " + message.getClass() + " class");
        }
        return message;
    }

    @PreDestroy
    private void destroy() throws JMSException {
        topicSubscriber.close();
        topicSession.close();
        topicConnection.close();
    }
}
