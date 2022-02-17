package part1;

public class Counter {

  private int successfulReqNum;
  private int unSuccessfulReqNum;

  public Counter() {
    this.successfulReqNum = 0;
    this.unSuccessfulReqNum = 0;
  }

  public synchronized void countSuccessfulReq() {
    this.successfulReqNum++;
  }

  public synchronized void countUnsuccessfulReq() {
    this.unSuccessfulReqNum++;
  }

  public int getSuccessfulReqNum() {
    return successfulReqNum;
  }

  public void setSuccessfulReqNum(int successfulReqNum) {
    this.successfulReqNum = successfulReqNum;
  }

  public int getUnSuccessfulReqNum() {
    return unSuccessfulReqNum;
  }

  public void setUnSuccessfulReqNum(int unSuccessfulReqNum) {
    this.unSuccessfulReqNum = unSuccessfulReqNum;
  }
}
