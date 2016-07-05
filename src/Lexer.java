// Author: Momchil Peychev

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;

public class Lexer {

  static private Pattern digitsPattern = Pattern.compile("\\d+");
  static private Pattern lettersPattern = Pattern.compile("[a-zA-Z]+");

  public interface Lexeme {}

  public static class LPar implements Lexeme {
    @Override
    public String toString() {
      return "(";
    }
  }

  public static class RPar implements Lexeme {
    @Override
    public String toString() {
      return ")";
    }
  }

  public static class Less implements Lexeme {
    @Override
    public String toString() {
      return "<";
    }
  }

  public static class Greater implements Lexeme {
    @Override
    public String toString() {
      return ">";
    }
  }

  public static class Equal implements Lexeme {
    @Override
    public String toString() {
      return "=";
    }
  }

  public static class LessOrEqual implements Lexeme {
    @Override
    public String toString() {
      return "<=";
    }
  }

  public static class GreaterOrEqual implements Lexeme {
    @Override
    public String toString() {
      return ">=";
    }
  }

  public static class Plus implements Lexeme {
    @Override
    public String toString() {
      return "+";
    }
  }

  public static class Minus implements Lexeme {
    @Override
    public String toString() {
      return "-";
    }
  }

  public static class Mult implements Lexeme {
    @Override
    public String toString() {
      return "*";
    }
  }

  public static class Div implements Lexeme {
    @Override
    public String toString() {
      return "/";
    }
  }

  public static class If implements Lexeme {
    @Override
    public String toString() {
      return "IF";
    }
  }

  public static class Then implements Lexeme {
    @Override
    public String toString() {
      return "THEN";
    }
  }

  public static class Else implements Lexeme {
    @Override
    public String toString() {
      return "ELSE";
    }
  }

  public static class Fun implements Lexeme {
    @Override
    public String toString() {
      return "FUN";
    }
  }

  public static class SemiColon implements Lexeme {
    @Override
    public String toString() {
      return ";";
    }
  }

  public static class Comma implements Lexeme {
    @Override
    public String toString() {
      return ",";
    }
  }

  public static class Num implements Lexeme {
    private int value;

    public Num(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    @Override
    public String toString() {
      return "NUM(" + value + ")";
    }
  }

  public static class Id implements Lexeme {
    private String value;

    public Id(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return "ID(" + value + ")";
    }
  }

  private static Pair<Lexeme, String> getNextLexeme(String str) throws LexerErrorException {
    if (str.startsWith("(")) {
      return new Pair<>(new Lexer.LPar(), str.substring(1));
    } else if (str.startsWith(")")) {
      return new Pair<>(new Lexer.RPar(), str.substring(1));
    } else if (str.startsWith("=")) {
      return new Pair<>(new Lexer.Equal(), str.substring(1));
    } else if (str.startsWith("<=")) {
      return new Pair<>(new Lexer.LessOrEqual(), str.substring(2));
    } else if (str.startsWith(">=")) {
      return new Pair<>(new Lexer.GreaterOrEqual(), str.substring(2));
    } else if (str.startsWith("<")) {
      return new Pair<>(new Lexer.Less(), str.substring(1));
    } else if (str.startsWith(">")) {
      return new Pair<>(new Lexer.Greater(), str.substring(1));
    } else if (str.startsWith("+")) {
      return new Pair<>(new Lexer.Plus(), str.substring(1));
    } else if (str.startsWith("-")) {
      return new Pair<>(new Lexer.Minus(), str.substring(1));
    } else if (str.startsWith("*")) {
      return new Pair<>(new Lexer.Mult(), str.substring(1));
    } else if (str.startsWith("/")) {
      return new Pair<>(new Lexer.Div(), str.substring(1));
    } else if (str.startsWith(";")) {
      return new Pair<>(new Lexer.SemiColon(), str.substring(1));
    } else if (str.startsWith(",")) {
      return new Pair<>(new Lexer.Comma(), str.substring(1));
    } else if (Character.isDigit(str.charAt(0))) {
      Matcher m = digitsPattern.matcher(str);
      if (m.find()) {
        String number = m.group();
        return new Pair<>(new Lexer.Num(Integer.parseInt(number)), str.substring(number.length()));
      } else {
        throw new LexerErrorException(str);
      }
    } else if (Character.isLetter(str.charAt(0))) {
      Matcher m = lettersPattern.matcher(str);
      if (m.find()) {
        String text = m.group();
        if (text.equals("if")) {
          return new Pair<>(new Lexer.If(), str.substring(2));
        } else if (text.equals("then")) {
          return new Pair<>(new Lexer.Then(), str.substring(4));
        } else if (text.equals("else")) {
          return new Pair<>(new Lexer.Else(), str.substring(4));
        } else if (text.equals("fun")) {
          return new Pair<>(new Lexer.Fun(), str.substring(3));
        } else {
          return new Pair<>(new Lexer.Id(text), str.substring(text.length()));
        }
      } else {
        throw new LexerErrorException(str);
      }
    } else {
      throw new LexerErrorException(str);
    }
  }

  public static List<Lexeme> lex(String line) throws LexerErrorException {
    List<Lexeme> lexemes = new ArrayList<>();
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
