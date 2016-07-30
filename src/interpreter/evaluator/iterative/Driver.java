// Author: Momchil Peychev

package interpreter.evaluator.iterative;

import java.util.Stack;

import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.loader.Loader;
import interpreter.parser.ParserErrorException;

public class Driver {

  public static Stack<StackFrame> step(Loader loader, Stack<StackFrame> runTimeStack) throws
          RunTimeErrorException, LexerErrorException, ParserErrorException {
    if (runTimeStack.isEmpty()) {
      throw new RunTimeErrorException("RunTimeStack is empty.");
    }
    StackFrame topFrame = runTimeStack.peek();
    if (topFrame.isNum()) {
      runTimeStack.pop();
      if (runTimeStack.isEmpty()) {
        throw new RunTimeErrorException("RunTimeStack is empty.");
      }
      StackFrame temporary = runTimeStack.pop();
      temporary.pushNum(topFrame.getTopLevelLexeme());
      runTimeStack.push(temporary);
      return runTimeStack;
    }
    if (topFrame.isFull()) {
      runTimeStack.pop();
      runTimeStack.push(topFrame.frameToReplace(loader));
      return runTimeStack;
    } else {
      runTimeStack.push(topFrame.stackFrameOfNextChild());
      return runTimeStack;
    }
  }

}
