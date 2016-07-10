// Author: Momchil Peychev

package interpreter.lexer;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import interpreter.lexer.lexeme.EmptyLexeme;
import interpreter.lexer.lexeme.Lexeme;
import interpreter.lexer.lexeme.LexemeType;
import interpreter.lexer.lexeme.ValueLexeme;

public class Lexer {

  static private Pattern digitsPattern = Pattern.compile("\\d+");
  static private Pattern lettersPattern = Pattern.compile("[a-zA-Z]+");

  private static Pair<Lexeme, String> getNextLexeme(String str) throws LexerErrorException {
    if (str.startsWith("(")) {
      return new Pair<>(new EmptyLexeme(LexemeType.LPar), str.substring(1));
    } else if (str.startsWith(")")) {
      return new Pair<>(new EmptyLexeme(LexemeType.RPar), str.substring(1));
    } else if (str.startsWith("=")) {
      return new Pair<>(new EmptyLexeme(LexemeType.Equal), str.substring(1));
    } else if (str.startsWith("<=")) {
      return new Pair<>(new EmptyLexeme(LexemeType.LessOrEqual), str.substring(2));
    } else if (str.startsWith(">=")) {
      return new Pair<>(new EmptyLexeme(LexemeType.GreaterOrEqual), str.substring(2));
    } else if (str.startsWith("<")) {
      return new Pair<>(new EmptyLexeme(LexemeType.Less), str.substring(1));
    } else if (str.startsWith(">")) {
      return new Pair<>(new EmptyLexeme(LexemeType.Greater), str.substring(1));
    } else if (str.startsWith("+")) {
      return new Pair<>(new EmptyLexeme(LexemeType.Plus), str.substring(1));
    } else if (str.startsWith("-")) {
      return new Pair<>(new EmptyLexeme(LexemeType.Minus), str.substring(1));
    } else if (str.startsWith("*")) {
      return new Pair<>(new EmptyLexeme(LexemeType.Mult), str.substring(1));
    } else if (str.startsWith("/")) {
      return new Pair<>(new EmptyLexeme(LexemeType.Div), str.substring(1));
    } else if (str.startsWith(";")) {
      return new Pair<>(new EmptyLexeme(LexemeType.Semicolon), str.substring(1));
    } else if (str.startsWith(",")) {
      return new Pair<>(new EmptyLexeme(LexemeType.Comma), str.substring(1));
    } else if (Character.isDigit(str.charAt(0))) {
      Matcher m = digitsPattern.matcher(str);
      if (m.find()) {
        String number = m.group();
        return new Pair<>(new ValueLexeme<Integer>(LexemeType.Num, Integer.valueOf(number)),
                str.substring(number.length()));
      } else {
        throw new LexerErrorException(str);
      }
    } else if (Character.isLetter(str.charAt(0))) {
      Matcher m = lettersPattern.matcher(str);
      if (m.find()) {
        String text = m.group();
        if (text.equals("if")) {
          return new Pair<>(new EmptyLexeme(LexemeType.If), str.substring(2));
        } else if (text.equals("then")) {
          return new Pair<>(new EmptyLexeme(LexemeType.Then), str.substring(4));
        } else if (text.equals("else")) {
          return new Pair<>(new EmptyLexeme(LexemeType.Else), str.substring(4));
        } else if (text.equals("fun")) {
          return new Pair<>(new EmptyLexeme(LexemeType.Fun), str.substring(3));
        } else {
          return new Pair<>(new ValueLexeme<String>(LexemeType.Id, text),
                  str.substring(text.length()));
        }
      } else {
        throw new LexerErrorException(str);
      }
    } else {
      throw new LexerErrorException(str);
    }
  }

  public static List<Lexeme> lex(String line) throws LexerErrorException {
    List<Lexeme> lexemes = new LinkedList<>();
    while (!line.isEmpty()) {
      line = line.trim();
      if (line.isEmpty()) {
        break;
      }
      Pair<Lexeme, String> next = getNextLexeme(line);
      lexemes.add(next.getKey());
      line = next.getValue();
    }
    return lexemes;
  }

}
