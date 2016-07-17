// Author: Momchil Peychev

package interpreter.evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import interpreter.lexer.lexeme.Lexeme;
import interpreter.parser.AbstractSyntaxTree;
import interpreter.parser.NodeType;

public class EvaluationTree {

  private Lexeme topLevelLexeme;
  private List<EvaluationTree> children = new ArrayList<>();

  public EvaluationTree(AbstractSyntaxTree ast) {
    if (ast.getType() == NodeType.E) {
      if (canSkip(ast)) {
        ast = skip(ast);
      } else {
        topLevelLexeme = ast.getChild(1).getTerminal(0);
        children.add(new EvaluationTree(ast.getChild(0)));
        children.add(new EvaluationTree(ast.getChild(1).getChild(0)));
        return;
      }
    }

    if (ast.getType() == NodeType.E1 && canSkip(ast)) {
      ast = skip(ast);
    }

    if (ast.getType() == NodeType.E2 && canSkip(ast)) {
      ast = skip(ast);
    }

    if (ast.getType() == NodeType.E1 || ast.getType() == NodeType.E2) {
      flatten(ast);
      return;
    }

    // TODO: Handle E3.
  }

  private EvaluationTree(Lexeme topLevelLexeme) {
    this.topLevelLexeme = topLevelLexeme;
  }

  private EvaluationTree addChild(EvaluationTree evaluationTree) {
    children.add(evaluationTree);
    return this;
  }

  private EvaluationTree combine(Stack<EvaluationTree> numbers, Stack<Lexeme> terminals) {
    if (terminals.isEmpty()) {
      return numbers.pop();
    }

    EvaluationTree result = new EvaluationTree(terminals.pop());
    EvaluationTree rightMost = numbers.pop();
    EvaluationTree left = combine(numbers, terminals);
    return result.addChild(left).addChild(rightMost);
  }

  private void flatten(AbstractSyntaxTree ast) {
    Stack<EvaluationTree> numbers = new Stack<>();
    Stack<Lexeme> terminals = new Stack<>();

    numbers.push(new EvaluationTree(ast.getChild(0)));
    AbstractSyntaxTree prim = ast.getChild(1);
    while (!prim.isEmpty()) {
      terminals.push(prim.getTerminal(0));
      numbers.push(new EvaluationTree(prim.getChild(0)));
      prim = prim.getChild(1);
    }

    topLevelLexeme = terminals.pop();
    EvaluationTree rightMost = numbers.pop();
    EvaluationTree left = combine(numbers, terminals);
    children.add(left);
    children.add(rightMost);
  }

  private boolean canSkip(AbstractSyntaxTree ast) {
    return ast.getChild(1).isEmpty();
  }

  private AbstractSyntaxTree skip(AbstractSyntaxTree ast) {
    return ast.getChild(0);
  }
}
