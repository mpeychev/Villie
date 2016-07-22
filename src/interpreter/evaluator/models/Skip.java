// Author: Momchil Peychev

package interpreter.evaluator.models;

import java.util.Map;

import interpreter.Loader;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.parser.ParserErrorException;

public class Skip implements RecursiveEvaluation {

  private RecursiveEvaluation e;

  public Skip(RecursiveEvaluation e) {
    this.e = e;
  }

  @Override
  public int evaluate(Loader loader, Map<String, Integer> closure) throws RunTimeErrorException,
          LexerErrorException, ParserErrorException {
    return e.evaluate(loader, closure);
  }
}
