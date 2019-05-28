package ru.gazprom_neft.amq_client.soap;


import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(
        targetNamespace = "http://soap.amq-client.gazprom_neft.ru/wsdl/amq-client",
        name = "AmqClient",
        serviceName = "AmqClient",
        portName = "SoapOverAmq"
)
public interface AMQClient {

    public void pushMessage(@WebParam(name = "body", targetNamespace = "") String body);
    public String popMessage();
}
