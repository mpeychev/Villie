// Author: Momchil Peychev

package interpreter.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import interpreter.Utils;
import interpreter.lexer.LexerErrorException;
import interpreter.lexer.lexeme.Lexeme;
import interpreter.lexer.lexeme.LexemeType;

public class AbstractSyntaxTree {

  private NodeType type;
  private List<Lexeme> terminals = new ArrayList<>();
  private List<AbstractSyntaxTree> children = new ArrayList<>();

  public AbstractSyntaxTree(NodeType type, Expression expression,
                            Map<String, FunctionDefinition> functionNameToDefinition)
          throws ParserErrorException, LexerErrorException {
    this.type = type;

    LexemeType nextLexemeType = expression.getNextLexeme().getType();
    if (type == NodeType.E) {
      if (Utils.canLexemeStartE(nextLexemeType)) {
        children.add(new AbstractSyntaxTree(NodeType.E1, expression, functionNameToDefinition));
        children.add(new AbstractSyntaxTree(NodeType.E1Prim, expression, functionNameToDefinition));
      } else {
        throw new ParserErrorException("Error parsing E.");
      }
    } else if (type == NodeType.E1Prim) {
      if (Utils.isLexemeTypeCmp(nextLexemeType)) {
        terminals.add(expression.getNextLexeme());
        expression.eat(nextLexemeType);
        children.add(new AbstractSyntaxTree(NodeType.E1, expression, functionNameToDefinition));
      } else if (!Utils.canLexemeFollowE(nextLexemeType)) {
        throw new ParserErrorException("Error parsing E1Prim.");
      }
    } else if (type == NodeType.E1) {
      if (Utils.canLexemeStartE(nextLexemeType)) {
        children.add(new AbstractSyntaxTree(NodeType.E2, expression, functionNameToDefinition));
        children.add(new AbstractSyntaxTree(NodeType.E2Prim, expression, functionNameToDefinition));
      } else {
        throw new ParserErrorException("Error parsing E1.");
      }
    } else if (type == NodeType.E2Prim) {
      if (Utils.isLexemeTypePlusMinus(nextLexemeType)) {
        terminals.add(expression.getNextLexeme());
        expression.eat(nextLexemeType);
        children.add(new AbstractSyntaxTree(NodeType.E2, expression, functionNameToDefinition));
        children.add(new AbstractSyntaxTree(NodeType.E2Prim, expression, functionNameToDefinition));
      } else if (!Utils.isLexemeTypeCmp(nextLexemeType) && !Utils.canLexemeFollowE(nextLexemeType)) {
        throw new ParserErrorException("Error parsing E2Prim.");
      }
    } else if (type == NodeType.E2) {
      if (Utils.canLexemeStartE(nextLexemeType)) {
        children.add(new AbstractSyntaxTree(NodeType.E3, expression, functionNameToDefinition));
        children.add(new AbstractSyntaxTree(NodeType.E3Prim, expression, functionNameToDefinition));
      } else {
        throw new ParserErrorException("Error parsing E2.");
      }
    } else if (type == NodeType.E3Prim) {
      if (Utils.isLexemeTypeMultDiv(nextLexemeType)) {
        terminals.add(expression.getNextLexeme());
        expression.eat(nextLexemeType);
        children.add(new AbstractSyntaxTree(NodeType.E3, expression, functionNameToDefinition));
        children.add(new AbstractSyntaxTree(NodeType.E3Prim, expression, functionNameToDefinition));
      } else if (!Utils.isLexemeTypeCmp(nextLexemeType)
              && !Utils.isLexemeTypePlusMinus(nextLexemeType)
              && !Utils.canLexemeFollowE(nextLexemeType)) {
        throw new ParserErrorException("Error parsing E3Prim.");
      }
    } else if (type == NodeType.E3) {
      if (nextLexemeType == LexemeType.If) {
        terminals.add(expression.getNextLexeme());
        expression.eat(LexemeType.If);
        children.add(new AbstractSyntaxTree(NodeType.E, expression, functionNameToDefinition));
        terminals.add(expression.getNextLexeme());
        expression.eat(LexemeType.Then);
        children.add(new AbstractSyntaxTree(NodeType.E, expression, functionNameToDefinition));
        terminals.add(expression.getNextLexeme());
        expression.eat(LexemeType.Else);
        children.add(new AbstractSyntaxTree(NodeType.E, expression, functionNameToDefinition));
      } else if (nextLexemeType == LexemeType.LPar) {
        terminals.add(expression.getNextLexeme());
        expression.eat(LexemeType.LPar);
        children.add(new AbstractSyntaxTree(NodeType.E, expression, functionNameToDefinition));
        terminals.add(expression.getNextLexeme());
        expression.eat(LexemeType.RPar);
      } else if (nextLexemeType == LexemeType.Id) {
        if (!Utils.isLexemeFunctionName(expression.getNextLexeme())) {
          terminals.add(expression.getNextLexeme());
          expression.eat(LexemeType.Id);
        } else {
          String functionName = (String) expression.getNextLexeme().getValue();
          terminals.add(expression.getNextLexeme());
          expression.eat(LexemeType.Id);
          expression.eat(LexemeType.LPar);
          if (!functionNameToDefinition.containsKey(functionName)) {
            throw new ParserErrorException("Error parsing E3. Function not found.");
          }
          int numberOfArguments = functionNameToDefinition.get(functionName).numberOfArguments();
          for (int i = 0; i < numberOfArguments; i++) {
            children.add(new AbstractSyntaxTree(NodeType.E, expression, functionNameToDefinition));
            if (i < numberOfArguments - 1) {
              expression.eat(LexemeType.Comma);
            } else {
              expression.eat(LexemeType.RPar);
            }
          }
        }
      } else if (nextLexemeType == LexemeType.Num) {
        terminals.add(expression.getNextLexeme());
        expression.eat(LexemeType.Num);
      } else {
        throw new ParserErrorException("Error parsing E3.");
      }
    } else {
      throw new ParserErrorException("Invalid node type.");
    }
  }

  public boolean isEmpty() {
    return (terminals.isEmpty() && children.isEmpty());
  }

  public boolean isLeaf() {
    return (type == NodeType.E3 &&
            (terminals.get(0).getType() == LexemeType.Id
                    || terminals.get(0).getType() == LexemeType.Num));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(type.toString());
    sb.append("(");
    for (Lexeme terminal : terminals) {
      sb.append(terminal.toString() + ",");
    }
    sb.append(") [");
    for (AbstractSyntaxTree ast : children) {
      sb.append(ast.toString() + ",");
    }
    sb.append("]");
    return sb.toString();
  }
}
