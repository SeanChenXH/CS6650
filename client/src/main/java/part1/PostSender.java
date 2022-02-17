package part1;

import io.swagger.client.ApiException;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import org.apache.commons.httpclient.HttpStatus;
import part2.Record;

public class PostSender implements Runnable {

  private Random random = new Random();
  private Parameters parameters;
  private SkiersApi skiersApi;
  private CountDownLatch countDownLatch;
  private CountDownLatch complete;
  private int numPost;
  private int startSkierId;
  private int endSkierId;
  private int startTime;
  private int endTime;
  private Counter counter;

  public PostSender(Parameters parameters, SkiersApi skiersApi,
      CountDownLatch countDownLatch, CountDownLatch complete, int numPost, int startSkierId,
      int endSkierId, int startTime,
      int endTime, Counter counter) {
    this.parameters = parameters;
    this.skiersApi = skiersApi;
    this.countDownLatch = countDownLatch;
    this.complete = complete;
    this.numPost = numPost;
    this.startSkierId = startSkierId;
    this.endSkierId = endSkierId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.counter = counter;
  }

  @Override
  public void run() {
    for (int i = 0; i < this.numPost; i++) {
      int retry = 0;
      int statusCode = post();
      while (retry < 5 && statusCode != HttpStatus.SC_CREATED) {
        statusCode = post();
        retry++;
      }
      if (statusCode == HttpStatus.SC_CREATED) {
        this.counter.countSuccessfulReq();
      } else {
        this.counter.countUnsuccessfulReq();
      }
    }
    if (this.countDownLatch != null) {
      this.countDownLatch.countDown();
    }
    this.complete.countDown();
  }

  private int post() {
    int skierID = this.startSkierId + this.random.nextInt(this.endSkierId - this.startSkierId + 1);
    int liftID = this.random.nextInt(this.parameters.getNumLifts() + 1);
    int time = this.startTime + this.random.nextInt(this.endTime - this.startTime + 1);
    int waitTime = Config.WAIT_TIME_START + this.random.nextInt(
        Config.WAIT_TIME_END - Config.WAIT_TIME_START + 1);

    LiftRide body = new LiftRide();
    body.setLiftID(liftID);
    body.setTime(time);
    body.setWaitTime(waitTime);

    try {
      int statusCode = this.skiersApi.writeNewLiftRideWithHttpInfo(body, 1, "2019",
          "100", skierID).getStatusCode();
      return statusCode;
    } catch (ApiException e) {
      e.printStackTrace();
    }
    return -1;
  }
}
