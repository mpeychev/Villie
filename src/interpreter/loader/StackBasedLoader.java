// Author: Momchil Peychev

package interpreter.loader;

import interpreter.evaluator.EvaluationTree;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.evaluator.iterative.IterativeEvaluation;
import interpreter.lexer.LexerErrorException;
import interpreter.parser.AbstractSyntaxTree;
import interpreter.parser.Expression;
import interpreter.parser.NodeType;
import interpreter.parser.ParserErrorException;

public class StackBasedLoader extends Loader {

  public StackBasedLoader(String file) throws LexerErrorException, ParserErrorException {
    super(file);
  }

  @Override
  public void run() throws ParserErrorException, LexerErrorException, RunTimeErrorException {
    for (Expression expression : expressions) {
      AbstractSyntaxTree ast = new AbstractSyntaxTree(NodeType.E, expression,
              functionNameToDefinition);
      EvaluationTree et = new EvaluationTree(ast);
      System.out.println(IterativeEvaluation.evaluate(et, this));
    }
  }

}
