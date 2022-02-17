package part1;

public class Part1Stats {

  public void printStats(Config config, long startTime, long endTime) {
    System.out.println("Thread: " + config.getParameters().getNumThreads());
    System.out.println("-----------------------------------------------------");
    System.out.println(
        "Number of successful requests sent:   " + config.getCounter().getSuccessfulReqNum());
    System.out.println(
        "Number of unsuccessful requests sent: " + config.getCounter().getUnSuccessfulReqNum());
    System.out.println("Wall time: " + (endTime - startTime) + " milliseconds");
    double throughput =
        (double) (config.getCounter().getSuccessfulReqNum() + config.getCounter().getUnSuccessfulReqNum()) /
            ((double) (endTime - startTime) / 1000);
    System.out.println(
        "Throughput: " + String.format("%.2f", throughput) + " requests/seconds");
  }
}
