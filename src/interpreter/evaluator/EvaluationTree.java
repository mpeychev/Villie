// Author: Momchil Peychev

package interpreter.evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import interpreter.Utils;
import interpreter.evaluator.models.Cmp;
import interpreter.evaluator.models.Id;
import interpreter.evaluator.models.IfThenElse;
import interpreter.evaluator.models.Num;
import interpreter.evaluator.models.Prod;
import interpreter.evaluator.models.RecursiveEvaluation;
import interpreter.evaluator.models.Skip;
import interpreter.evaluator.models.Sum;
import interpreter.lexer.LexerErrorException;
import interpreter.lexer.lexeme.Lexeme;
import interpreter.lexer.lexeme.LexemeType;
import interpreter.lexer.lexeme.ValueLexeme;
import interpreter.parser.AbstractSyntaxTree;
import interpreter.parser.NodeType;
import interpreter.parser.ParserErrorException;

public class EvaluationTree {

  private Lexeme topLevelLexeme;
  private List<EvaluationTree> children = new ArrayList<>();

  public EvaluationTree(AbstractSyntaxTree ast) throws ParserErrorException, LexerErrorException {
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

    if (ast.getType() == NodeType.E3) {
      switch (ast.getTerminal(0).getType()) {
        case Num:
          topLevelLexeme = ast.getTerminal(0);
          break;
        case Id:
          topLevelLexeme = ast.getTerminal(0);
          if (Utils.isLexemeFunctionName(topLevelLexeme)) {
            for (AbstractSyntaxTree child : ast.getChildren()) {
              children.add(new EvaluationTree(child));
            }
          }
          break;
        case If:
          topLevelLexeme = ast.getTerminal(0);
          children.add(new EvaluationTree(ast.getChild(0)));
          children.add(new EvaluationTree(ast.getChild(1)));
          children.add(new EvaluationTree(ast.getChild(2)));
          break;
        case LPar:
          topLevelLexeme = ast.getTerminal(0);
          children.add(new EvaluationTree(ast.getChild(0)));
          break;
        default:
          throw new ParserErrorException("Mismatch at NodeType E3 when building EvaluationTree");
      }
    } else {
      throw new ParserErrorException("Could not build the evaluation tree successfully.");
    }
  }

  public static EvaluationTree of(int number) {
    return new EvaluationTree(new ValueLexeme<Integer>(LexemeType.Num, Integer.valueOf(number)));
  }

  public RecursiveEvaluation toRecursiveEvaluation() throws ParserErrorException {
    if (Utils.isLexemeTypeCmp(topLevelLexeme.getType())) {
      return new Cmp(topLevelLexeme, children.get(0).toRecursiveEvaluation(),
              children.get(1).toRecursiveEvaluation());
    } else if (Utils.isLexemeTypePlusMinus(topLevelLexeme.getType())) {
      return new Sum(topLevelLexeme, children.get(0).toRecursiveEvaluation(),
              children.get(1).toRecursiveEvaluation());
    } else if (Utils.isLexemeTypeMultDiv(topLevelLexeme.getType())) {
      return new Prod(topLevelLexeme, children.get(0).toRecursiveEvaluation(),
              children.get(1).toRecursiveEvaluation());
    } else {
      switch (topLevelLexeme.getType()) {
        case Id:
          List<RecursiveEvaluation> convertedArguments = new ArrayList<>();
          for (EvaluationTree et : children) {
            convertedArguments.add(et.toRecursiveEvaluation());
          }
          return new Id(topLevelLexeme, convertedArguments);
        case Num:
          return new Num(topLevelLexeme);
        case If:
          return new IfThenElse(children.get(0).toRecursiveEvaluation(),
                  children.get(1).toRecursiveEvaluation(), children.get(2).toRecursiveEvaluation());
        case LPar:
          return new Skip(children.get(0).toRecursiveEvaluation());
        default:
          throw new ParserErrorException("Mismatch in conversion to RecursiveEvaluation");
      }
    }
  }

  public int getNumberOfChildren() {
    return children.size();
  }

  public EvaluationTree getChildWithIndex(int index) {
    return children.get(index);
  }

  public Lexeme getTopLevelLexeme() {
    return topLevelLexeme;
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

  private void flatten(AbstractSyntaxTree ast) throws ParserErrorException, LexerErrorException {
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(topLevelLexeme.toString() + "{ ");
    for (EvaluationTree evaluationTree : children) {
      sb.append(evaluationTree.toString());
    }
    sb.append(" }");
    return sb.toString();
  }

}
