package part1;

public class Parameters {

  private int numThreads;
  private int numSkiers;
  private int numLifts;
  private int numRuns;
  private String ip;
  private String port;

  public Parameters() {
  }

  public Parameters(int numThreads, int numSkiers, int numLifts, int numRuns, String ip,
      String port) {
    this.numThreads = numThreads;
    this.numSkiers = numSkiers;
    this.numLifts = numLifts;
    this.numRuns = numRuns;
    this.ip = ip;
    this.port = port;
  }

  public int getNumThreads() {
    return numThreads;
  }

  public void setNumThreads(int numThreads) {
    this.numThreads = numThreads;
  }

  public int getNumSkiers() {
    return numSkiers;
  }

  public void setNumSkiers(int numSkiers) {
    this.numSkiers = numSkiers;
  }

  public int getNumLifts() {
    return numLifts;
  }

  public void setNumLifts(int numLifts) {
    this.numLifts = numLifts;
  }

  public int getNumRuns() {
    return numRuns;
  }

  public void setNumRuns(int numRuns) {
    this.numRuns = numRuns;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }
}
