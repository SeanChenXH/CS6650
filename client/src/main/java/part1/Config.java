package part1;

import io.swagger.client.api.SkiersApi;
import part1.cmdLine.Parser;
import part1.exceptions.CounterInitializationException;
import part1.exceptions.ParametersInitializationException;
import part1.exceptions.ParserInitializationException;
import part1.exceptions.SkiersApiInitializationException;

public class Config {

  public static final int PHASE1_START_DAY = 1;
  public static final int PHASE1_END_DAY = 90;
  public static final int PHASE2_START_DAY = 91;
  public static final int PHASE2_END_DAY = 360;
  public static final int PHASE3_START_DAY = 361;
  public static final int PHASE3_END_DAY = 420;

  public static final int WAIT_TIME_START = 0;
  public static final int WAIT_TIME_END = 10;

  public static final String HTTP = "http://";
  public static final String SERVER_NAME = "server";
  //public static final String SERVER_NAME = "server_war_exploded";

  private Parser parser;
  private Parameters parameters;
  private SkiersApi skiersApi;
  private Counter counter;

  public Config() {
  }

  public Config(Parser parser, Parameters parameters, SkiersApi skiersApi, Counter counter) {
    this.parser = parser;
    this.parameters = parameters;
    this.skiersApi = skiersApi;
    this.counter = counter;
  }

  public Config init(String[] args)
      throws ParserInitializationException, ParametersInitializationException, SkiersApiInitializationException, CounterInitializationException {
    if (this.parameters == null) {
      throw new ParametersInitializationException("Parameters initialization fail.");
    }
    if (this.parser == null || this.parameters == null) {
      throw new ParserInitializationException("Parser initialization fail.");
    }
    if (this.skiersApi == null) {
      throw new SkiersApiInitializationException("Skiers Api initialization fail.");
    }
    if (this.counter == null) {
      throw new CounterInitializationException("Counter initialization fail.");
    }
    parser.parse(parameters, args);
    skiersApi.getApiClient().setBasePath(
        HTTP + this.parameters.getIp() + ":" + this.parameters.getPort() + "/" + SERVER_NAME);
    return this;
  }

  public Parser getParser() {
    return parser;
  }

  public Config setParser(Parser parser) {
    this.parser = parser;
    return this;
  }

  public Parameters getParameters() {
    return parameters;
  }

  public Config setParameters(Parameters parameters) {
    this.parameters = parameters;
    return this;
  }

  public SkiersApi getSkiersApi() {
    return skiersApi;
  }

  public Config setSkiersApi(SkiersApi skiersApi) {
    this.skiersApi = skiersApi;
    return this;
  }

  public Counter getCounter() {
    return counter;
  }

  public Config setCounter(Counter counter) {
    this.counter = counter;
    return this;
  }

}
