// Author: Momchil Peychev

import java.util.List;

import lexer.lexeme.Lexeme;

public class Expression {

  private List<Lexeme> expression;

  public Expression(List<Lexeme> expression) {
    this.expression = expression;
  }

  @Override
  public String toString() {
    return expression.toString();
  }
}
