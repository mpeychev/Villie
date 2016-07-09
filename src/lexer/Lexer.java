// Author: Momchil Peychev

package lexer;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import lexer.lexeme.EmptyLexeme;
import lexer.lexeme.Lexeme;
import lexer.lexeme.ValueLexeme;

public class Lexer {

  static private Pattern digitsPattern = Pattern.compile("\\d+");
  static private Pattern lettersPattern = Pattern.compile("[a-zA-Z]+");

  private static Pair<Lexeme, String> getNextLexeme(String str) throws LexerErrorException {
    if (str.startsWith("(")) {
      return new Pair<>(new EmptyLexeme("("), str.substring(1));
    } else if (str.startsWith(")")) {
      return new Pair<>(new EmptyLexeme(")"), str.substring(1));
    } else if (str.startsWith("=")) {
      return new Pair<>(new EmptyLexeme("="), str.substring(1));
    } else if (str.startsWith("<=")) {
      return new Pair<>(new EmptyLexeme("<="), str.substring(2));
    } else if (str.startsWith(">=")) {
      return new Pair<>(new EmptyLexeme(">="), str.substring(2));
    } else if (str.startsWith("<")) {
      return new Pair<>(new EmptyLexeme("<"), str.substring(1));
    } else if (str.startsWith(">")) {
      return new Pair<>(new EmptyLexeme(">"), str.substring(1));
    } else if (str.startsWith("+")) {
      return new Pair<>(new EmptyLexeme("+"), str.substring(1));
    } else if (str.startsWith("-")) {
      return new Pair<>(new EmptyLexeme("-"), str.substring(1));
    } else if (str.startsWith("*")) {
      return new Pair<>(new EmptyLexeme("*"), str.substring(1));
    } else if (str.startsWith("/")) {
      return new Pair<>(new EmptyLexeme("/"), str.substring(1));
    } else if (str.startsWith(";")) {
      return new Pair<>(new EmptyLexeme(";"), str.substring(1));
    } else if (str.startsWith(",")) {
      return new Pair<>(new EmptyLexeme(","), str.substring(1));
    } else if (Character.isDigit(str.charAt(0))) {
      Matcher m = digitsPattern.matcher(str);
      if (m.find()) {
        String number = m.group();
        return new Pair<>(new ValueLexeme<Integer>("NUM", Integer.valueOf(number)),
                str.substring(number.length()));
      } else {
        throw new LexerErrorException(str);
      }
    } else if (Character.isLetter(str.charAt(0))) {
      Matcher m = lettersPattern.matcher(str);
      if (m.find()) {
        String text = m.group();
        if (text.equals("if")) {
          return new Pair<>(new EmptyLexeme("IF"), str.substring(2));
        } else if (text.equals("then")) {
          return new Pair<>(new EmptyLexeme("THEN"), str.substring(4));
        } else if (text.equals("else")) {
          return new Pair<>(new EmptyLexeme("ELSE"), str.substring(4));
        } else if (text.equals("fun")) {
          return new Pair<>(new EmptyLexeme("FUN"), str.substring(3));
        } else {
          return new Pair<>(new ValueLexeme<String>("ID", text), str.substring(text.length()));
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