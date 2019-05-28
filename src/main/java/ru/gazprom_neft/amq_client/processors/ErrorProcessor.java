package ru.gazprom_neft.amq_client.processors;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.gazprom_neft.amq_client.model.JsonFaultMessage;

@Log4j
public class ErrorProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Gson gson = new Gson();
        String camelContextPath = exchange.getIn().getHeader("CamelServletContextPath", String.class);
        if (log.isDebugEnabled()) {
            log.debug("CamelServletContextPath" + camelContextPath);
        }
        Throwable t = (Throwable) exchange.getProperty("CamelExceptionCaught");
        JsonFaultMessage exception;

        exception = new JsonFaultMessage(500, t.toString(), t);

        String sException = gson.toJson(exception, JsonFaultMessage.class);
        if (log.isDebugEnabled()) {
            log.debug("JSONException is " + sException);
        }
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setBody(sException, String.class);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
    }
}
