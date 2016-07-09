// Author: Momchil Peychev

import java.util.List;

import lexer.lexeme.Lexeme;

public class Util {

  public static boolean isFunctionDefinition(List<Lexeme> lexemes) {
    return (lexemes.size() > 0 && lexemes.get(0).getType().equals("FUN"));
  }

  public static List<Lexeme> eat(List<Lexeme> lexemes, String lexemeType)
          throws ParserErrorException {
    if (lexemes.isEmpty() || !lexemes.get(0).getType().equals(lexemeType)) {
      throw new ParserErrorException(lexemes.toString() + " ... " + lexemeType + " expected");
    }

    lexemes.remove(0);
    return lexemes;
  }

}
