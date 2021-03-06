// Author: Momchil Peychev

package interpreter.evaluator.models;

import java.util.Map;

import interpreter.loader.Loader;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.lexer.lexeme.Lexeme;
import interpreter.parser.ParserErrorException;

public class Prod implements RecursiveEvaluation {

  private Lexeme operation;
  private RecursiveEvaluation e1;
  private RecursiveEvaluation e2;

  public Prod(Lexeme operation, RecursiveEvaluation e1, RecursiveEvaluation e2) {
    this.operation = operation;
    this.e1 = e1;
    this.e2 = e2;
  }

  @Override
  public int evaluate(Loader loader, Map<String, Integer> closure) throws RunTimeErrorException,
          LexerErrorException, ParserErrorException {
    int v1 = e1.evaluate(loader, closure);
    int v2 = e2.evaluate(loader, closure);
    switch (operation.getType()) {
      case Mult:
        return v1 * v2;
      case Div:
        return v1 / v2;
      default:
        throw new RunTimeErrorException("RunTimeError trying to process a product.");
    }
  }
}
