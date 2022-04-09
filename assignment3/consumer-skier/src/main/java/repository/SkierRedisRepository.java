package repository;

import com.google.gson.Gson;
import model.LiftRide;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SkierRedisRepository implements IGenericRepository<LiftRide> {

  private JedisPool jedisPool = null;

  public SkierRedisRepository(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  @Override
  public void addDataForDaysOnSkierAndSeason(LiftRide item) {
    Jedis jedis = jedisPool.getResource();
    String key = "SKI:SKIER:DAYSONSKIERSEASON:" + item.getSkierID() + ":" + item.getSeasonID();
    String value = String.valueOf(item.getDayID());
    jedis.sadd(key, value);
    jedisPool.returnResource(jedis);
  }

  @Override
  public void addDataForVerticalTotalsOnSkierAndDay(LiftRide item) {
    Jedis jedis = jedisPool.getResource();
    String key = "SKI:SKIER:VERTICALSONSKIERDAY:" + item.getSkierID() + ":" + item.getDayID();
    String old_vertical_totals = jedis.get(key);
    int vertical_totals = 0;
    if (old_vertical_totals != null) {
      vertical_totals += Integer.valueOf(old_vertical_totals);
    }
    vertical_totals += item.getLiftRideBody().getLiftID() * 10;
    String value = String.valueOf(vertical_totals);
    jedis.set(key, value);
    jedisPool.returnResource(jedis);
  }

  @Override
  public void addDataForLiftsOnSkierAndDay(LiftRide item) {
    Jedis jedis = jedisPool.getResource();
    String key = "SKI:SKIER:LIFTSONSKIERDAY" + item.getSkierID() + ":" + item.getDayID();
    String value = new Gson().toJson(item.getLiftRideBody());
    jedis.rpush(key, value);
    jedisPool.returnResource(jedis);
  }
}
