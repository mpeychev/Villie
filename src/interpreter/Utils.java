package interpreter;// Author: Momchil Peychev

import java.util.List;

import interpreter.lexer.lexeme.Lexeme;
import interpreter.lexer.lexeme.LexemeType;

public class Utils {

  public static boolean isFunctionDefinition(List<Lexeme> lexemes) {
    return (lexemes.size() > 0 && lexemes.get(0).getType().equals("FUN"));
  }

  public static boolean isLexemeTypeCmp(LexemeType lexemeType) {
    switch (lexemeType) {
      case Less:
      case Greater:
      case Equal:
      case LessOrEqual:
      case GreaterOrEqual:
        return true;
      default:
        return false;
    }
  }

  public static boolean isLexemeTypePlusMinus(LexemeType lexemeType) {
    switch (lexemeType) {
      case Plus:
      case Minus:
        return true;
      default:
        return false;
    }
  }

  public static boolean isLexemeTypeMultDiv(LexemeType lexemeType) {
    switch (lexemeType) {
      case Mult:
      case Div:
        return true;
      default:
        return false;
    }
  }

  public static boolean canLexemeStartE(LexemeType lexemeType) {
    switch (lexemeType) {
      case If:
      case LPar:
      case Id:
      case Num:
        return true;
      default:
        return false;
    }
  }

  public static boolean canLexemeFollowE(LexemeType lexemeType) {
    switch (lexemeType) {
      case Then:
      case Else:
      case Semicolon:
      case Comma:
      case RPar:
        return true;
      default:
        return false;
    }
  }

}
