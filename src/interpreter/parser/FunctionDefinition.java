// Author: Momchil Peychev

package interpreter.parser;

import java.util.LinkedList;
import java.util.List;

import interpreter.lexer.LexerErrorException;
import interpreter.lexer.lexeme.Lexeme;
import interpreter.lexer.lexeme.LexemeType;

public class FunctionDefinition {

  private String name;
  private List<String> arguments = new LinkedList<>();
  private Expression expression;

  public FunctionDefinition(List<Lexeme> lexemes) throws ParserErrorException, LexerErrorException {
    expression = new Expression(lexemes);
    expression.eat(LexemeType.Fun);
    name = (String) expression.getNextLexeme().getValue();
    expression.eat(LexemeType.Id);
    expression.eat(LexemeType.LPar);
    while (true) {
      arguments.add((String) expression.getNextLexeme().getValue());
      expression.eat(LexemeType.Id);
      if (expression.getNextLexeme().getType() == LexemeType.Comma) {
        expression.eat(LexemeType.Comma);
      } else {
        expression.eat(LexemeType.RPar);
        break;
      }
    }
    expression.eat(LexemeType.Equal);
  }

  public String getName() {
    return name;
  }

  public int numberOfArguments() {
    return arguments.size();
  }

  @Override
  public String toString() {
    return "(" + name + ": " + arguments.toString() + " = " + expression.toString() + ")";
  }
}
