package part1;

import io.swagger.client.api.SkiersApi;
import part1.api.SwaggerSkiersApi;
import part1.cmdLine.CmdGenerator;
import part1.cmdLine.CmdIParser;
import part1.exceptions.ParserInitializationException;
import part1.exceptions.SkiersApiInitializationException;

/**
 * The Main class is the entry point to run client part 1 by specifying the Parser and SkiersApi
 */
public class Main {

  // --numThreads 32 --numSkiers 20000 --numLifts 40 --numRuns 20 --ip localhost --port 8080
  // --numThreads 32 --numSkiers 2000 --numLifts 40 --numRuns 20 --ip 34.222.11.253 --port 8080
  public static void main(String[] args) {
    Config config = null;
    try {
      config = new Config()
          .setParser(new CmdIParser(new CmdGenerator()))
          .setSkiersApi(new SwaggerSkiersApi(new SkiersApi()))
          .init(args);
      new Part1(config).run();
    } catch (ParserInitializationException | SkiersApiInitializationException e) {
      e.printStackTrace();
    }
  }
}
