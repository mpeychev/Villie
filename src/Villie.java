// Author: Momchil Peychev

import java.util.logging.Level;
import java.util.logging.Logger;

import lexer.LexerErrorException;

public class Villie {

  private static Logger log = Logger.getLogger(Villie.class.getName());

  public static void main(String[] args) throws LexerErrorException, ParserErrorException {
    if (args.length != 1) {
      log.log(Level.SEVERE, "Expecting a single argument <code> - the program to be interpreted");
      return;
    }

    Loader loader = new Loader(args[0]);
  }

}
