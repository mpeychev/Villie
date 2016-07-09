// Author: Momchil Peychev

package lexer.lexeme;

import lexer.lexeme.Lexeme;

public class ValueLexeme <T> extends Lexeme {

  private T value;

  public ValueLexeme(String type, T value) {
    super(type);
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  @Override
  public String toString() {
    return type + "(" + value.toString() + ")";
  }

}
