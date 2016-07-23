// Author: Momchil Peychev

package interpreter.evaluator.models;

import java.util.Map;

import interpreter.loader.Loader;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.lexer.lexeme.Lexeme;

public class Num implements RecursiveEvaluation {

  private Lexeme number;

  public Num(Lexeme number) {
    this.number = number;
  }

  @Override
  public int evaluate(Loader loader, Map<String, Integer> closure) throws RunTimeErrorException,
          LexerErrorException {
    return (int) number.getValue();
  }
}
