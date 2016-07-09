// Author: Momchil Peychev

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;

public class Lexer {

  static private Pattern digitsPattern = Pattern.compile("\\d+");
  static private Pattern lettersPattern = Pattern.compile("[a-zA-Z]+");

  public static class Lexeme {
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
  }

  public static class LPar extends Lexeme {
    public LPar() {
      super("(");
    }
  }

  public static class RPar extends Lexeme {
    public RPar() {
      super(")");
    }
  }

  public static class Less extends Lexeme {
    public Less() {
      super("<");
    }
  }

  public static class Greater extends Lexeme {
    public Greater() {
      super(">");
    }
  }

  public static class Equal extends Lexeme {
    public Equal() {
      super("=");
    }
  }

  public static class LessOrEqual extends Lexeme {
    public LessOrEqual() {
      super("<=");
    }
  }

  public static class GreaterOrEqual extends Lexeme {
    public GreaterOrEqual() {
      super(">=");
    }
  }

  public static class Plus extends Lexeme {
    public Plus() {
      super("+");
    }
  }

  public static class Minus extends Lexeme {
    public Minus() {
      super("-");
    }
  }

  public static class Mult extends Lexeme {
    public Mult() {
      super("*");
    }
  }

  public static class Div extends Lexeme {
    public Div() {
      super("/");
    }
  }

  public static class If extends Lexeme {
    public If() {
      super("IF");
    }
  }

  public static class Then extends Lexeme {
    public Then() {
      super("THEN");
    }
  }

  public static class Else extends Lexeme {
    public Else() {
      super("ELSE");
    }
  }

  public static class Fun extends Lexeme {
    public Fun() {
      super("FUN");
    }
  }

  public static class SemiColon extends Lexeme {
    public SemiColon() {
      super(";");
    }
  }

  public static class Comma extends Lexeme {
    public Comma() {
      super(",");
    }
  }

  public static class Num extends Lexeme {
    private int value;

    public Num(int value) {
      super("NUM");
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    @Override
    public String toString() {
      return type + "(" + value + ")";
    }
  }

  public static class Id extends Lexeme {
    private String value;

    public Id(String value) {
      super("ID");
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return type + "(" + value + ")";
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
