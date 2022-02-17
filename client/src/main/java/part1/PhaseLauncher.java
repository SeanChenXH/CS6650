package part1;

import io.swagger.client.api.SkiersApi;
import java.util.concurrent.CountDownLatch;

public class PhaseLauncher {

  private Parameters parameters;

  private int phase1ThreadNum;
  private int phase2ThreadNum;
  private int phase3ThreadNum;

  private CountDownLatch phase1CountDownLatch;
  private CountDownLatch phase2CountDownLatch;
  private CountDownLatch complete;

  private SkiersApi skiersApi;

  private Counter counter;

  public PhaseLauncher(Config config) {

    this.parameters = config.getParameters();

    this.phase1ThreadNum = this.parameters.getNumThreads() / 4;
    this.phase2ThreadNum = this.parameters.getNumThreads();
    this.phase3ThreadNum = this.parameters.getNumThreads() / 10;

    this.phase1CountDownLatch = new CountDownLatch(this.phase1ThreadNum / 5);
    this.phase2CountDownLatch = new CountDownLatch(this.phase2ThreadNum / 5);
    this.complete = new CountDownLatch(
        this.phase1ThreadNum + this.phase2ThreadNum + this.phase3ThreadNum);

    this.skiersApi = config.getSkiersApi();
    this.counter = config.getCounter();
  }

  public void launchAll() {

    new Thread(() -> {
      this.launchPhase1();
    }, "phase1").start();

    new Thread(() -> {
      this.launchPhase2();
    }, "phase2").start();

    new Thread(() -> {
      this.launchPhase3();
    }, "phase3").start();
  }

  public void launchPhase1() {

    for (int i = 0; i < this.phase1ThreadNum; i++) {
      int startSkierID = i * (this.parameters.getNumSkiers() / this.phase1ThreadNum) + 1;
      int endSkierID = (i + 1) * (this.parameters.getNumSkiers() / this.phase1ThreadNum);
      int numPost = (int) (this.parameters.getNumRuns() * 0.2) * (this.parameters.getNumSkiers()
          / this.phase1ThreadNum);
      Thread t = new Thread(
          new PostSender(this.parameters, this.skiersApi, this.phase1CountDownLatch, this.complete,
              numPost,
              startSkierID, endSkierID, Config.PHASE1_START_DAY, Config.PHASE1_END_DAY,
              this.counter));
      t.start();
    }
  }

  public void launchPhase2() {
    try {
      this.phase1CountDownLatch.await();
      for (int i = 0; i < this.phase2ThreadNum; i++) {
        int startSkierID = i * (this.parameters.getNumSkiers() / this.phase2ThreadNum) + 1;
        int endSkierID = (i + 1) * (this.parameters.getNumSkiers() / this.phase2ThreadNum);
        int numPost = (int) (parameters.getNumRuns() * 0.6) * (this.parameters.getNumSkiers()
            / this.phase2ThreadNum);
        Thread t = new Thread(
            new PostSender(this.parameters, this.skiersApi, this.phase2CountDownLatch,
                this.complete, numPost,
                startSkierID, endSkierID, Config.PHASE2_START_DAY, Config.PHASE2_END_DAY,
                this.counter));
        t.start();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void launchPhase3() {
    try {
      this.phase1CountDownLatch.await();
      for (int i = 0; i < this.phase3ThreadNum; i++) {
        int startSkierID = i * (this.parameters.getNumSkiers() / this.phase3ThreadNum) + 1;
        int endSkierID = (i + 1) * (this.parameters.getNumSkiers() / this.phase3ThreadNum);
        int numPost = (int) (parameters.getNumRuns() * 0.1);
        Thread t = new Thread(
            new PostSender(this.parameters, this.skiersApi, null, this.complete, numPost,
                startSkierID, endSkierID, Config.PHASE3_START_DAY, Config.PHASE3_END_DAY,
                this.counter));
        t.start();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void waitComplete() {
    try {
      this.complete.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
