package ru.gazprom_neft.amq_client.util;


import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;

import javax.jms.*;

@UtilityClass
public class MessageUtil {
    public static void sendMessageBodyToExchange(Message message, Exchange exchange) throws JMSException {
        if (message instanceof BytesMessage) {
            exchange.getOut().setBody(message.getBody(byte[].class), String.class);
        } else if (message instanceof ObjectMessage) {
            exchange.getOut().setBody(message.getBody(Object.class));
        } else if (message instanceof TextMessage) {
            exchange.getOut().setBody(message.getBody(String.class), String.class);
        }
    }

}
