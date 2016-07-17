// Author: Momchil Peychev

package interpreter.lexer.lexeme;

import interpreter.lexer.LexerErrorException;

public abstract class Lexeme {
  protected LexemeType type;

  public Lexeme(LexemeType type) {
    this.type = type;
  }

  public LexemeType getType() {
    return type;
  }

  public abstract Object getValue() throws LexerErrorException;

}
