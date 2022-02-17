package part2;

import part1.Config;
import part1.Part1;

public class Part2 extends Part1 {

  private Record record;

  public Part2(String[] args, Config config) {
    super(args, config);
  }

  public Part2(String[] args, Config config, Record record) {
    super(args, config);
    this.record = record;
  }

  public long run() {
    long part1WallTime = super.run();
    System.out.println("-----------------------------------------------------");
    long startTime = System.currentTimeMillis();
    this.record.setTotalResponseTime(part1WallTime);
    new CsvWriter().writeCsvRecord(config.getParameters(), this.record);
    new Part2Stats(record, config.getCounter()).printStats();
    long endTime = System.currentTimeMillis();
    long part2WallTime = endTime - startTime;
    System.out.println("-----------------------------------------------------");
    System.out.println("Wall time (part1): " + part1WallTime + " milliseconds");
    System.out.println("Wall time (part2): " + part2WallTime + " milliseconds");
    return part2WallTime;
  }
}
