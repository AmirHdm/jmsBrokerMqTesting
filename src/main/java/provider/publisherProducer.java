package provider;

// JMS required Packages
import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

// File parsing required packages
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

// Logger required packages
import org.apache.log4j.Logger;

public class publisherProducer {

    private static final Logger logger = Logger.getLogger(publisherProducer.class);

    // Default constructor
    public publisherProducer() {
    }


    public static String readXmlFile(String filePath) throws IOException {
        byte[] xmlBytes = Files.readAllBytes(Paths.get(filePath));
        return new String(xmlBytes);
    }

    public static String xmlReader(String filePath) {
        BasicConfigurator.configure();
        String xmlContent = null;

        try {
            xmlContent = readXmlFile(filePath);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return xmlContent;
    }

    public void sessionCreator(String dHostname, String dQueue, String dMessage) {
        BasicConfigurator.configure();
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(dHostname);
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(dQueue);
            MessageProducer producer = session.createProducer(destination);

            if (dMessage != null) {
                TextMessage message = session.createTextMessage(dMessage);
                producer.send(message);
                System.out.println("Data Sent Successfully to : " + dQueue);
            } else {
                System.out.println("Data does not provider to : " + dQueue);
            }
            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

    }


}
