package part1.cmdLine;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import part1.Parameters;

public class CmdParser implements Parser {

  private final static String NUM_THREADS = "numThreads";
  private final static String NUM_SKIERS = "numSkiers";
  private final static String NUM_LIFTS = "numLifts";
  private final static String NUM_RUNS = "numRuns";
  private final static String IP = "ip";
  private final static String PORT = "port";

  private final static int MIN_NUM_THREADS = 1;
  private final static int MAX_NUM_THREADS = 1024;
  private final static int MIN_NUM_SKIERS = 1;
  private final static int MAX_NUM_SKIERS = 100000;
  private final static int MIN_NUM_LIFTS = 5;
  private final static int MAX_NUM_LIFTS = 60;
  private final static int DEFAULT_NUM_LIFTS = 40;
  private final static int MIN_NUM_RUNS = 1;
  private final static int MAX_NUM_RUNS = 20;
  private final static int DEFAULT_NUM_RUNS = 10;

  private final static int SYSTEM_EXIT_NUM = 0;

  private CmdGenerator cmdGenerator;
  private HelpFormatter formatter;


  public CmdParser(CmdGenerator cmdGenerator) {
    this.cmdGenerator = cmdGenerator;
    this.formatter = new HelpFormatter();
  }

  @Override
  public Parameters parse(Parameters parameters, String[] args) {
    cmdGenerator.generate();
    CommandLine cmd = null;
    try {
      cmd = new DefaultParser().parse(cmdGenerator.getOptions(), args);
      if (parseNumThreads(parameters, cmd) &&
          parseNumSkiers(parameters, cmd) &&
          parseNumLifts(parameters, cmd) &&
          parseNumRuns(parameters, cmd) &&
          parseIp(parameters, cmd) &&
          parsePort(parameters, cmd)) {
        System.out.println("Complete reading parameters from command line...");
      } else {
        this.formatter.printHelp(" ", " ", cmdGenerator.getOptions(), "", false);
        System.exit(SYSTEM_EXIT_NUM);
      }
    } catch (ParseException e) {
      this.formatter.printHelp(" ", " ", cmdGenerator.getOptions(), "", false);
      System.exit(SYSTEM_EXIT_NUM);
    }
    return parameters;
  }

//    public Parameters parse(Parameters parameters, String[] args) {
//    parameters.setIp("localhost");
//    parameters.setIp("34.222.11.253");
//    parameters.setPort("8080");
//    parameters.setNumThreads(256);
//    parameters.setNumSkiers(20000);
//    parameters.setNumLifts(40);
//    parameters.setNumRuns(20);
//    return parameters;
//  }


  private boolean parseNumThreads(Parameters parameters, CommandLine cmd)
      throws NumberFormatException {
    int numTreads = Integer.parseInt(cmd.getOptionValue(NUM_THREADS));
    if (numTreads > MAX_NUM_THREADS || numTreads < MIN_NUM_THREADS) {
      return false;
    }
    parameters.setNumThreads(numTreads);
    return true;
  }

  private boolean parseNumSkiers(Parameters parameters, CommandLine cmd)
      throws NumberFormatException {
    int numSkiers = Integer.parseInt(cmd.getOptionValue(NUM_SKIERS));
    if (numSkiers > MAX_NUM_SKIERS || numSkiers < MIN_NUM_SKIERS) {
      return false;
    }
    parameters.setNumSkiers(numSkiers);
    return true;
  }

  private boolean parseNumLifts(Parameters parameters, CommandLine cmd)
      throws NumberFormatException {
    if (cmd.getOptionValue(NUM_LIFTS) == null) {
      parameters.setNumLifts(DEFAULT_NUM_LIFTS);
      return true;
    }
    int numLifts = Integer.parseInt(cmd.getOptionValue(NUM_LIFTS));
    if (numLifts > MAX_NUM_LIFTS || numLifts < MIN_NUM_LIFTS) {
      return false;
    }
    parameters.setNumLifts(numLifts);
    return true;
  }

  private boolean parseNumRuns(Parameters parameters, CommandLine cmd)
      throws NumberFormatException {
    if (cmd.getOptionValue(NUM_RUNS) == null) {
      parameters.setNumRuns(DEFAULT_NUM_RUNS);
      return true;
    }
    int numRuns = Integer.parseInt(cmd.getOptionValue(NUM_RUNS));
    if (numRuns > MAX_NUM_RUNS || numRuns < MIN_NUM_RUNS) {
      return false;
    }
    parameters.setNumRuns(numRuns);
    return true;
  }

  private boolean parseIp(Parameters parameters, CommandLine cmd) {
    parameters.setIp(cmd.getOptionValue(IP));
    return true;
  }

  private boolean parsePort(Parameters parameters, CommandLine cmd) {
    parameters.setPort(cmd.getOptionValue(PORT));
    return true;
  }

}
