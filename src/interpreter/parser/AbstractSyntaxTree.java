// Author: Momchil Peychev

package interpreter.parser;

import java.util.ArrayList;
import java.util.List;

import interpreter.Utils;
import interpreter.lexer.lexeme.Lexeme;
import interpreter.lexer.lexeme.LexemeType;

public class AbstractSyntaxTree {

  private NodeType type;
  private List<Lexeme> terminals = new ArrayList<>();
  private List<AbstractSyntaxTree> children = new ArrayList<>();

  public AbstractSyntaxTree(NodeType type, Expression expression) throws ParserErrorException {
    this.type = type;

    LexemeType nextLexemeType = expression.getNextLexeme().getType();
    if (type == NodeType.E) {
      if (Utils.canLexemeStartE(nextLexemeType)) {
        children.add(new AbstractSyntaxTree(NodeType.E1, expression));
        children.add(new AbstractSyntaxTree(NodeType.E1Prim, expression));
      } else {
        throw new ParserErrorException("Error parsing E.");
      }
    } else if (type == NodeType.E1Prim) {
      if (Utils.isLexemeTypeCmp(nextLexemeType)) {
        terminals.add(expression.getNextLexeme());
        expression.eat(nextLexemeType);
        children.add(new AbstractSyntaxTree(NodeType.E1, expression));
      } else if (!Utils.canLexemeFollowE(nextLexemeType)) {
        throw new ParserErrorException("Error parsing E1Prim.");
      }
    } else if (type == NodeType.E1) {
      if (Utils.canLexemeStartE(nextLexemeType)) {
        children.add(new AbstractSyntaxTree(NodeType.E2, expression));
        children.add(new AbstractSyntaxTree(NodeType.E2Prim, expression));
      } else {
        throw new ParserErrorException("Error parsing E1.");
      }
    } else if (type == NodeType.E2Prim) {
      if (Utils.isLexemeTypePlusMinus(nextLexemeType)) {
        terminals.add(expression.getNextLexeme());
        expression.eat(nextLexemeType);
        children.add(new AbstractSyntaxTree(NodeType.E2, expression));
        children.add(new AbstractSyntaxTree(NodeType.E2Prim, expression));
      } else if (!Utils.isLexemeTypeCmp(nextLexemeType) && !Utils.canLexemeFollowE(nextLexemeType)) {
        throw new ParserErrorException("Error parsing E2Prim.");
      }
    } else if (type == NodeType.E2) {
      if (Utils.canLexemeStartE(nextLexemeType)) {
        children.add(new AbstractSyntaxTree(NodeType.E3, expression));
        children.add(new AbstractSyntaxTree(NodeType.E3Prim, expression));
      } else {
        throw new ParserErrorException("Error parsing E2.");
      }
    } else if (type == NodeType.E3Prim) {
      if (Utils.isLexemeTypeMultDiv(nextLexemeType)) {
        terminals.add(expression.getNextLexeme());
        expression.eat(nextLexemeType);
        children.add(new AbstractSyntaxTree(NodeType.E3, expression));
        children.add(new AbstractSyntaxTree(NodeType.E3Prim, expression));
      } else if (!Utils.isLexemeTypeCmp(nextLexemeType)
              && !Utils.isLexemeTypePlusMinus(nextLexemeType)
              && !Utils.canLexemeFollowE(nextLexemeType)) {
        throw new ParserErrorException("Error parsing E3Prim.");
      }
    } else if (type == NodeType.E3) {
      if (nextLexemeType == LexemeType.If) {

      } else if (nextLexemeType == LexemeType.LPar) {

      } else if (nextLexemeType == LexemeType.Id) {

      } else if (nextLexemeType == LexemeType.Num) {

      } else {
        throw new ParserErrorException("Error parsing E3.");
      }
    } else {
      throw new ParserErrorException("Invalid node type.");
    }
  }

}
