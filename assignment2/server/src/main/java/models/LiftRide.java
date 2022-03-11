package models;

public class LiftRide {

  private Integer resortID;
  private Integer seasonID;
  private Integer dayID;
  private Integer skierID;
  private Integer time;
  private Integer liftID;
  private Integer waitTime;

  public LiftRide(Integer resortID, Integer seasonID, Integer dayID, Integer skierID,
      Integer time, Integer liftID, Integer waitTime) {
    this.resortID = resortID;
    this.seasonID = seasonID;
    this.dayID = dayID;
    this.skierID = skierID;
    this.time = time;
    this.liftID = liftID;
    this.waitTime = waitTime;
  }

  public Integer getResortID() {
    return resortID;
  }

  public void setResortID(Integer resortID) {
    this.resortID = resortID;
  }

  public Integer getSeasonID() {
    return seasonID;
  }

  public void setSeasonID(Integer seasonID) {
    this.seasonID = seasonID;
  }

  public Integer getDayID() {
    return dayID;
  }

  public void setDayID(Integer dayID) {
    this.dayID = dayID;
  }

  public Integer getSkierID() {
    return skierID;
  }

  public void setSkierID(Integer skierID) {
    this.skierID = skierID;
  }

  public Integer getTime() {
    return time;
  }

  public void setTime(Integer time) {
    this.time = time;
  }

  public Integer getLiftID() {
    return liftID;
  }

  public void setLiftID(Integer liftID) {
    this.liftID = liftID;
  }

  public Integer getWaitTime() {
    return waitTime;
  }

  public void setWaitTime(Integer waitTime) {
    this.waitTime = waitTime;
  }

  @Override
  public String toString() {
    return "LiftRide{" +
        "resortID=" + resortID +
        ", seasonID=" + seasonID +
        ", dayID=" + dayID +
        ", skierID=" + skierID +
        ", time=" + time +
        ", liftID=" + liftID +
        ", waitTime=" + waitTime +
        '}';
  }
}
