// Author: Momchil Peychev

package lexer.lexeme;

import lexer.LexerErrorException;

public abstract class Lexeme {
  protected String type;

  public Lexeme(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return type;
  }

  public abstract Object getValue() throws LexerErrorException;
}
