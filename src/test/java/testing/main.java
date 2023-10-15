package testing;


import provider.publisherProducer;

import javax.jms.JMSException;

public class main {
    public static String brokerHostname = "tcp://localhost:61616";
    public static String dQueue = "org.provider.testQueue";
    public static String filePath = "C:\\Users\\user\\Desktop\\SG_WSDL\\E2E.xml";

    public static void main(String[] args) throws JMSException {

        publisherProducer publisher = new publisherProducer();

        String dMessage = publisher.xmlReader(filePath);

        publisher.sessionCreator(brokerHostname, dQueue
                , dMessage);


    }
}
