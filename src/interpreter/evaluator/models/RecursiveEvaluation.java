// Author: Momchil Peychev

package interpreter.evaluator.models;

import java.util.Map;

import interpreter.Loader;
import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.parser.ParserErrorException;

public interface RecursiveEvaluation {

  int evaluate(Loader loader, Map<String, Integer> closure) throws RunTimeErrorException,
          LexerErrorException, ParserErrorException;

}
