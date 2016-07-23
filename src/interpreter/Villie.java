// Author: Momchil Peychev

package interpreter;

import java.util.logging.Level;
import java.util.logging.Logger;

import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.loader.Loader;
import interpreter.loader.RecursiveLoader;
import interpreter.parser.ParserErrorException;

public class Villie {

  private static Logger log = Logger.getLogger(Villie.class.getName());

  public static void main(String[] args) throws LexerErrorException, ParserErrorException,
          RunTimeErrorException {
    if (args.length != 1) {
      log.log(Level.SEVERE, "Expecting a single argument <code> - the program to be interpreted");
      return;
    }

    Loader loader = new RecursiveLoader(args[0]);
    loader.run();
  }

}
