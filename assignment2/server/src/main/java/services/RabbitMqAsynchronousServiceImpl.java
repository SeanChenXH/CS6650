package services;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import models.LiftRide;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * The RabbitMqAsynchronousServiceImpl implements the AsynchronousService interface by using
 * RabbitMQ technique
 */
public class RabbitMqAsynchronousServiceImpl implements AsynchronousService {

  private final static String LIFT_RIDE_QUEUE_NAME = "lift_ride_messages";
  private GenericObjectPool<Channel> channelPool;

  /**
   * Instantiates a new Rabbit mq asynchronous service.
   *
   * @param channelPool the channel pool
   */
  public RabbitMqAsynchronousServiceImpl(
      GenericObjectPool<Channel> channelPool) {
    this.channelPool = channelPool;
  }

  public boolean sendLiftRideToAsynQueueChannel(LiftRide liftRide) {
    Channel channel = null;
    try {
      channel = this.channelPool.borrowObject();
      channel.queueDeclare(LIFT_RIDE_QUEUE_NAME, false, false, false, null);
      String message = new Gson().toJson(liftRide);
      channel.basicPublish("", LIFT_RIDE_QUEUE_NAME, null, message.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      this.channelPool.returnObject(channel);
    }
    return true;
  }

}
