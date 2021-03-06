package ru.gazprom_neft.amq_client.processors;

import lombok.extern.log4j.Log4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gazprom_neft.amq_client.service.AMQService;
import ru.gazprom_neft.amq_client.util.MessageUtil;

import javax.jms.JMSException;
import javax.jms.Message;

@Log4j
public class AMQProcessor implements Processor {


    @Autowired
    AMQService amqService;


    @Override
    public void process(Exchange exchange) {

        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setHeader("Content-Type", "text/plain");
        try {
            Message message = amqService.popMessage();
            if (message != null) {
                MessageUtil.sendMessageBodyToExchange(message, exchange);
            } else {
                exchange.getOut().setBody("No messages available");
            }
        } catch (JMSException ex) {
            if (log.isDebugEnabled()) {
                log.debug("No messages available (in REST get)");
            }
            exchange.getOut().setBody("No messages available");
        }
    }


}
