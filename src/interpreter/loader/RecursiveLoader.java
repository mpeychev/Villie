// Author: Momchil Peychev

package interpreter.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import interpreter.Utils;
import interpreter.evaluator.EvaluationTree;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.evaluator.models.RecursiveEvaluation;
import interpreter.lexer.Lexer;
import interpreter.lexer.LexerErrorException;
import interpreter.lexer.lexeme.Lexeme;
import interpreter.parser.AbstractSyntaxTree;
import interpreter.parser.Expression;
import interpreter.parser.FunctionDefinition;
import interpreter.parser.NodeType;
import interpreter.parser.ParserErrorException;

public class RecursiveLoader extends Loader {

  public RecursiveLoader(String file) throws LexerErrorException, ParserErrorException {
    super(file);
  }

  @Override
  public void run() throws ParserErrorException, LexerErrorException, RunTimeErrorException {
    for (Expression expression : expressions) {
      AbstractSyntaxTree ast = new AbstractSyntaxTree(NodeType.E, expression,
              functionNameToDefinition);
      EvaluationTree et = new EvaluationTree(ast);
      System.out.println(et.toRecursiveEvaluation().evaluate(this, new HashMap<>()));
    }
  }

}
