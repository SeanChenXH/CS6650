package part1.cmdLine;

import part1.Parameters;

public interface Parser {
   Parameters parse(Parameters parameters, String[] args);
}
