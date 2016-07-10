// Author: Momchil Peychev

package interpreter.parser;

import java.util.List;

import interpreter.lexer.lexeme.Lexeme;
import interpreter.lexer.lexeme.LexemeType;

public class Expression {

  private List<Lexeme> expression;

  public Expression(List<Lexeme> expression) {
    this.expression = expression;
  }

  public void eat(LexemeType lexemeType) throws ParserErrorException {
    if (getNextLexeme().getType() != lexemeType) {
      throw new ParserErrorException("Lexeme Type mismatch.");
    }
    expression.remove(0);
  }

  public Lexeme getNextLexeme() throws ParserErrorException {
    if (expression.isEmpty()) {
      throw new ParserErrorException("Trying to get a lexeme from an empty expression.");
    }
    return expression.get(0);
  }

  @Override
  public String toString() {
    return expression.toString();
  }
}
