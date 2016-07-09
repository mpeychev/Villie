// Author: Momchil Peychev

package lexer.lexeme;

import lexer.LexerErrorException;

public class EmptyLexeme extends Lexeme {

  public EmptyLexeme(String type) {
    super(type);
  }

  public Object getValue() throws LexerErrorException {
    throw new LexerErrorException("Lexeme mismatch. Empty Lexeme expected");
  }

}
