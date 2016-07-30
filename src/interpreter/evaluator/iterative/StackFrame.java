// Author: Momchil Peychev

package interpreter.evaluator.iterative;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import interpreter.Utils;
import interpreter.evaluator.EvaluationTree;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.lexer.lexeme.Lexeme;
import interpreter.lexer.lexeme.LexemeType;
import interpreter.loader.Loader;
import interpreter.parser.ParserErrorException;

public class StackFrame {

  private EvaluationTree et;
  private Map<String, Integer> closure;
  private List<Integer> calculatedValues = new LinkedList<>();

  public StackFrame(EvaluationTree et, Map<String, Integer> closure) {
    this.et = et;
    this.closure = closure;
  }

  private StackFrame(int number) {
    this.et = EvaluationTree.of(number);
    this.closure = new HashMap<>();
  }

  public Lexeme getTopLevelLexeme() {
    return et.getTopLevelLexeme();
  }

  public boolean isNum() {
    return Utils.isLexemeNum(et.getTopLevelLexeme());
  }

  public void pushNum(Lexeme lexeme) throws LexerErrorException {
    calculatedValues.add((Integer) lexeme.getValue());
  }

  public boolean isFull() throws LexerErrorException, RunTimeErrorException {
    Lexeme topLevelLexeme = et.getTopLevelLexeme();
    LexemeType topLevelLexemeType = topLevelLexeme.getType();
    if (Utils.isLexemeTypeCmp(topLevelLexemeType) || Utils.isLexemeTypePlusMinus(topLevelLexemeType)
            || Utils.isLexemeTypeMultDiv(topLevelLexemeType)) {
      return calculatedValues.size() == 2;
    } else if (Utils.isLexemeNum(topLevelLexeme) || Utils.isLexemeVariable(topLevelLexeme)
            || topLevelLexemeType == LexemeType.LPar) {
      return true;
    } else if (topLevelLexemeType == LexemeType.If) {
      return calculatedValues.size() == 1;
    } else if (Utils.isLexemeFunctionName(topLevelLexeme)) {
      return (calculatedValues.size() == et.getNumberOfChildren());
    } else {
      throw new RunTimeErrorException("Invalid TopLevelLexeme on the top of the RunTimeStack.");
    }
  }

  public StackFrame stackFrameOfNextChild() {
    return new StackFrame(et.getChildWithIndex(calculatedValues.size()), closure);
  }

  private static StackFrame of(Integer number) {
    return new StackFrame(number);
  }

  public StackFrame frameToReplace(Loader loader) throws LexerErrorException, RunTimeErrorException,
          ParserErrorException {
    Lexeme topLevelLexeme = et.getTopLevelLexeme();
    LexemeType topLevelLexemeType = topLevelLexeme.getType();
    switch (topLevelLexemeType) {
      case Less:
        return StackFrame.of(calculatedValues.get(0) < calculatedValues.get(1) ? 1 : 0);
      case Greater:
        return StackFrame.of(calculatedValues.get(0) > calculatedValues.get(1) ? 1 : 0);
      case LessOrEqual:
        return StackFrame.of(calculatedValues.get(0) <= calculatedValues.get(1) ? 1 : 0);
      case GreaterOrEqual:
        return StackFrame.of(calculatedValues.get(0) >= calculatedValues.get(1) ? 1 : 0);
      case Equal:
        return StackFrame.of(calculatedValues.get(0) == calculatedValues.get(1) ? 1 : 0);
      case Plus:
        return StackFrame.of(calculatedValues.get(0) + calculatedValues.get(1));
      case Minus:
        return StackFrame.of(calculatedValues.get(0) - calculatedValues.get(1));
      case Mult:
        return StackFrame.of(calculatedValues.get(0) * calculatedValues.get(1));
      case Div:
        return StackFrame.of(calculatedValues.get(0) / calculatedValues.get(1));
      case LPar:
        return new StackFrame(et.getChildWithIndex(0), closure);
      case If:
        return new StackFrame(calculatedValues.get(0) == 0 ? et.getChildWithIndex(2) :
          et.getChildWithIndex(1), closure);
      case Id:
        if (Utils.isLexemeFunctionName(topLevelLexeme)) {
          String name = (String) topLevelLexeme.getValue();
          EvaluationTree nextEvaluationTree = loader.getEvaluationTreeByName(name);
          Map<String, Integer> nextClosure = new HashMap<>();
          List<String> arguments = loader.getFunctionArguments(name);
          for (int i = 0; i < arguments.size(); i++) {
            nextClosure.put(arguments.get(i), calculatedValues.get(i));
          }
          return new StackFrame(nextEvaluationTree, nextClosure);
        } else {
          if (!closure.containsKey(topLevelLexeme.getValue())) {
            throw new RunTimeErrorException("Variable not found in the closure.");
          }
          return StackFrame.of(closure.get(topLevelLexeme.getValue()));
        }
      default:
        throw new RunTimeErrorException("Cannot compute next frame to replace the top one.");
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(et.toString());
    sb.append("\n");
    sb.append(closure.toString());
    sb.append("\n");
    sb.append(calculatedValues.toString());
    sb.append("\n ----- \n");
    return sb.toString();
  }
}
