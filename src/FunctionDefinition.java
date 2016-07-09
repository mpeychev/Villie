// Author: Momchil Peychev

import java.util.LinkedList;
import java.util.List;

public class FunctionDefinition {

  private String name;
  private List<String> arguments = new LinkedList<>();
  private Expression expression;

  public FunctionDefinition(List<Lexeme> lexemes) {
    lexemes = Util.eat(lexemes, "FUN");
    this.name = (String) lexemes.get(0).getValue();
    lexemes = Util.eat(lexemes, "(");
    while (true) {
      arguments.add((String) lexemes.get(0).getValue());
      lexemes = Util.eat(lexemes, "ID");
      if (lexemes.get(0).equals(",")) {
        lexemes = Util.eat(lexemes, ",");
      } else {
        lexemes = Util.eat(lexemes, ")");
        break;
      }
    }
    lexemes = Util.eat(lexemes, "=");
    expression = new Expression(lexemes);
  }

}
