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
    switch (type) {
      case E:
        if (Utils.canLexemeStartE(nextLexemeType)) {
          children.add(new AbstractSyntaxTree(NodeType.E1, expression, functionNameToDefinition));
          children.add(new AbstractSyntaxTree(NodeType.E1Prim, expression, functionNameToDefinition));
        } else {
          throw new ParserErrorException("Error parsing E.");
        }
        break;
      case E1Prim:
        if (Utils.isLexemeTypeCmp(nextLexemeType)) {
          terminals.add(expression.getNextLexeme());
          expression.eat(nextLexemeType);
          children.add(new AbstractSyntaxTree(NodeType.E1, expression, functionNameToDefinition));
        } else if (!Utils.canLexemeFollowE(nextLexemeType)) {
          throw new ParserErrorException("Error parsing E1Prim.");
        }
        break;
      case E1:
        if (Utils.canLexemeStartE(nextLexemeType)) {
          children.add(new AbstractSyntaxTree(NodeType.E2, expression, functionNameToDefinition));
          children.add(new AbstractSyntaxTree(NodeType.E2Prim, expression, functionNameToDefinition));
        } else {
          throw new ParserErrorException("Error parsing E1.");
        }
        break;
      case E2Prim:
        if (Utils.isLexemeTypePlusMinus(nextLexemeType)) {
          terminals.add(expression.getNextLexeme());
          expression.eat(nextLexemeType);
          children.add(new AbstractSyntaxTree(NodeType.E2, expression, functionNameToDefinition));
          children.add(new AbstractSyntaxTree(NodeType.E2Prim, expression, functionNameToDefinition));
        } else if (!Utils.isLexemeTypeCmp(nextLexemeType) && !Utils.canLexemeFollowE(nextLexemeType)) {
          throw new ParserErrorException("Error parsing E2Prim.");
        }
        break;
      case E2:
        if (Utils.canLexemeStartE(nextLexemeType)) {
          children.add(new AbstractSyntaxTree(NodeType.E3, expression, functionNameToDefinition));
          children.add(new AbstractSyntaxTree(NodeType.E3Prim, expression, functionNameToDefinition));
        } else {
          throw new ParserErrorException("Error parsing E2.");
        }
        break;
      case E3Prim:
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
        break;
      case E3:
        switch (nextLexemeType) {
          case If:
            terminals.add(expression.getNextLexeme());
            expression.eat(LexemeType.If);
            children.add(new AbstractSyntaxTree(NodeType.E, expression, functionNameToDefinition));
            terminals.add(expression.getNextLexeme());
            expression.eat(LexemeType.Then);
            children.add(new AbstractSyntaxTree(NodeType.E, expression, functionNameToDefinition));
            terminals.add(expression.getNextLexeme());
            expression.eat(LexemeType.Else);
            children.add(new AbstractSyntaxTree(NodeType.E, expression, functionNameToDefinition));
            break;
          case LPar:
            terminals.add(expression.getNextLexeme());
            expression.eat(LexemeType.LPar);
            children.add(new AbstractSyntaxTree(NodeType.E, expression, functionNameToDefinition));
            terminals.add(expression.getNextLexeme());
            expression.eat(LexemeType.RPar);
            break;
          case Num:
            terminals.add(expression.getNextLexeme());
            expression.eat(LexemeType.Num);
            break;
          case Id:
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
            break;
          default:
            throw new ParserErrorException("Error parsing E3.");
        }
        break;
      default:
        throw new ParserErrorException("Invalid node type.");
    }
  }

  public NodeType getType() {
    return type;
  }

  public Lexeme getTerminal(int index) {
    return terminals.get(index);
  }

  public AbstractSyntaxTree getChild(int index) {
    return children.get(index);
  }

  public List<AbstractSyntaxTree> getChildren() {
    return children;
  }

  public boolean isEmpty() {
    return (terminals.isEmpty() && children.isEmpty());
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
