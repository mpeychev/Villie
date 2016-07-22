// Author: Momchil Peychev

package interpreter.evaluator.models;

import java.util.Map;

import interpreter.Loader;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.parser.ParserErrorException;

public class IfThenElse implements RecursiveEvaluation {

  private RecursiveEvaluation e1;
  private RecursiveEvaluation e2;
  private RecursiveEvaluation e3;

  public IfThenElse(RecursiveEvaluation e1, RecursiveEvaluation e2, RecursiveEvaluation e3) {
    this.e1 = e1;
    this.e2 = e2;
    this.e3 = e3;
  }

  @Override
  public int evaluate(Loader loader, Map<String, Integer> closure) throws RunTimeErrorException,
          LexerErrorException, ParserErrorException {
    if (e1.evaluate(loader, closure) != 0) {
      return e2.evaluate(loader, closure);
    } else {
      return e3.evaluate(loader, closure);
    }
  }
}
