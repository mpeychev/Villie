// Author: Momchil Peychev

package interpreter.evaluator.iterative;

import java.util.HashMap;
import java.util.Stack;

import interpreter.evaluator.EvaluationTree;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.loader.Loader;
import interpreter.parser.ParserErrorException;

public class IterativeEvaluation {


  public static int evaluate(EvaluationTree et, Loader loader) throws LexerErrorException,
          ParserErrorException, RunTimeErrorException {
    Stack<StackFrame> runTimeStack = new Stack<>();
    runTimeStack.push(new StackFrame(et, new HashMap<String, Integer>()));
    while (true) {
      if (runTimeStack.peek().isNum()) {
        StackFrame stackFrame = runTimeStack.pop();
        return (Integer) stackFrame.getTopLevelLexeme().getValue();
      } else {
        runTimeStack = Driver.step(loader, runTimeStack);
      }
    }
  }

}
