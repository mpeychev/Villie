// Author: Momchil Peychev

package interpreter.evaluator.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interpreter.loader.Loader;
import interpreter.Utils;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.lexer.lexeme.Lexeme;
import interpreter.parser.ParserErrorException;

public class Id implements RecursiveEvaluation {

  private Lexeme name;
  private List<RecursiveEvaluation> children;

  public Id(Lexeme name, List<RecursiveEvaluation> children) {
    this.name = name;
    this.children = children;
  }

  @Override
  public int evaluate(Loader loader, Map<String, Integer> closure) throws RunTimeErrorException,
          LexerErrorException, ParserErrorException {
    if (!Utils.isLexemeFunctionName(name)) {
      if (!closure.containsKey(name.getValue())) {
        throw new RunTimeErrorException("RunTimeError: Could not find a variable in the closure");
      }
      return closure.get(name.getValue());
    }

    Map<String, Integer> nextLevelClosure = new HashMap<>();
    List<String> args = loader.getFunctionArguments((String) name.getValue());
    for (int i = 0; i < args.size(); i++) {
      nextLevelClosure.put(args.get(i), children.get(i).evaluate(loader, closure));
    }
    return loader.getRecursiveEvaluationByName((String) name.getValue())
            .evaluate(loader, nextLevelClosure);
  }
}
