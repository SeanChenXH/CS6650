package part1;

import io.swagger.client.api.SkiersApi;
import part1.cmdLine.CmdGenerator;
import part1.cmdLine.CmdParser;
import part1.exceptions.CounterInitializationException;
import part1.exceptions.ParametersInitializationException;
import part1.exceptions.ParserInitializationException;
import part1.exceptions.SkiersApiInitializationException;

public class Main {

  // --numThreads 32 --numSkiers 20000 --numLifts 40 --numRuns 20 --ip localhost --port 8080
  // --numThreads 32 --numSkiers 2000 --numLifts 40 --numRuns 20 --ip 34.222.11.253 --port 8080
  public static void main(String[] args) {
    Config config = null;
    try {
      config = new Config()
          .setParser(new CmdParser(new CmdGenerator()))
          .setSkiersApi(new SkiersApi())
          .setCounter(new Counter())
          .setParameters(new Parameters())
          .init(args);
      new Part1(args, config).run();
    } catch (ParserInitializationException | ParametersInitializationException | SkiersApiInitializationException | CounterInitializationException e) {
      e.printStackTrace();
    }
  }
}
