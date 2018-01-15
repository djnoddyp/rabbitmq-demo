package demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private final static String QUEUE_NAME = "yo";

    public void send(String message) throws IOException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("sent " + message);

        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        connection.close();
    }

}
