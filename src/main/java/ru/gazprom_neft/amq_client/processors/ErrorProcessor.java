package ru.gazprom_neft.amq_client.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.gazprom_neft.amq_client.model.JsonFaultMessage;


/**
 * The type Error processor.
 * It takes care of all Exceptions happend at RunTime
 * and convert them to JSON
 */
public class ErrorProcessor implements Processor {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    /**
     * This method gets Throwable and create JSON with code, description and stacktrace,
     * then it add result JSON in exchange, so it will be returned to user
     * @param exchange
     */
    @Override
    public void process(Exchange exchange) {
        Throwable t = (Throwable) exchange.getProperty("CamelExceptionCaught");
        JsonFaultMessage exception = new JsonFaultMessage(500, t.toString(), t);
        exchange.getOut().setBody(exception.toJson());
        exchange.getOut().setHeader(CONTENT_TYPE, APPLICATION_JSON);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
    }
}
