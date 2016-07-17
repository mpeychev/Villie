// Author: Momchil Peychev

package interpreter.lexer.lexeme;

import interpreter.lexer.LexerErrorException;

public class EmptyLexeme extends Lexeme {

  public EmptyLexeme(LexemeType type) {
    super(type);
  }

  public Object getValue() throws LexerErrorException {
    throw new LexerErrorException("Lexeme mismatch. Empty Lexeme expected");
  }

  @Override
  public String toString() {
    return type.toString();
  }
}
