// Author: Momchil Peychev

import java.util.LinkedList;
import java.util.List;

import lexer.LexerErrorException;
import lexer.lexeme.Lexeme;

public class FunctionDefinition {

  private String name;
  private List<String> arguments = new LinkedList<>();
  private Expression expression;

  public FunctionDefinition(List<Lexeme> lexemes) throws ParserErrorException, LexerErrorException {
    lexemes = Util.eat(lexemes, "FUN");
    this.name = (String) lexemes.get(0).getValue();
    lexemes = Util.eat(lexemes, "ID");
    lexemes = Util.eat(lexemes, "(");
    while (true) {
      arguments.add((String) lexemes.get(0).getValue());
      lexemes = Util.eat(lexemes, "ID");
      if (lexemes.get(0).getType().equals(",")) {
        lexemes = Util.eat(lexemes, ",");
      } else {
        lexemes = Util.eat(lexemes, ")");
        break;
      }
    }
    lexemes = Util.eat(lexemes, "=");
    expression = new Expression(lexemes);
  }

  @Override
  public String toString() {
    return name + "\n" + arguments.toString() + "\n" + expression.toString();
  }
}
