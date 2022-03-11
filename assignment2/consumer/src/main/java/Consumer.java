import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Consumer {
  private final static String QUEUE_NAME = "lift_ride_messages";
  private final static int THREADS_NUM = 10;
  //private final static String HOST = "34.209.42.249";
  private final static String HOST = "localhost";
  private final static int PORT = 5672;
  private final static Map<Integer, List<LiftRide>> dataMap = new ConcurrentHashMap<>();

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(HOST);
    factory.setPort(PORT);
    Connection connection = factory.newConnection();
    Runnable runnable = () -> {
      Channel channel;
      try {
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
          LiftRide liftRide = new Gson().fromJson(message, LiftRide.class);
          Integer skierID = liftRide.getSkierID();
          if (!dataMap.containsKey(skierID)) {
            dataMap.put(skierID, new CopyOnWriteArrayList<>());
          }
          dataMap.get(skierID).add(liftRide);
          System.out.println(" [x] Received '" + liftRide + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
      } catch (IOException e) {
        e.printStackTrace();
      }
    };

    for (int i = 0; i < THREADS_NUM; i++) {
      new Thread(runnable).start();
    }

  }
}
