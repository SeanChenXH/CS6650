package part1;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import org.apache.commons.httpclient.HttpStatus;
import part1.api.ISkiersApi;

/**
 * The PostSender is to send a skiers api post request to upload new lift ride to server.
 */
public class PostSender implements Runnable {

  private Random random = new Random();
  private Parameters parameters;
  private ISkiersApi skiersApi;
  private CountDownLatch countDownLatch;
  private CountDownLatch complete;
  private int numPost;
  private int startSkierId;
  private int endSkierId;
  private int startTime;
  private int endTime;
  private Counter counter;

  /**
   * Instantiates a PostSender
   *
   * @param parameters     the parameters
   * @param skiersApi      the skiers api
   * @param countDownLatch the countdown latch
   * @param complete       the complete
   * @param numPost        the num post
   * @param startSkierId   the start skier id
   * @param endSkierId     the end skier id
   * @param startTime      the start time
   * @param endTime        the end time
   * @param counter        the counter
   */
  public PostSender(Parameters parameters, ISkiersApi skiersApi, CountDownLatch countDownLatch,
      CountDownLatch complete, int numPost, int startSkierId, int endSkierId, int startTime,
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
    return this.skiersApi.writeNewLiftRide(liftID, time, waitTime, 1, "2019", "100", skierID);
  }
}
