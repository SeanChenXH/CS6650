package part1;

public class Part1 {

  protected String[] args;
  protected Config config;

  public Part1(String[] args, Config config) {
    this.args = args;
    this.config = config;
  }

  public long run() {
    long startTime = System.currentTimeMillis();
    PhaseLauncher phaseLauncher = new PhaseLauncher(config);
    phaseLauncher.launchAll();
    phaseLauncher.waitComplete();
    long endTime = System.currentTimeMillis();
    new Part1Stats().printStats(config, startTime, endTime);
    return endTime - startTime;
  }
}
