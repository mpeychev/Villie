// Author: Momchil Peychev

package interpreter;

import interpreter.evaluator.RunTimeErrorException;
import interpreter.lexer.LexerErrorException;
import interpreter.loader.Loader;
import interpreter.loader.RecursiveLoader;
import interpreter.loader.StackBasedLoader;
import interpreter.parser.ParserErrorException;

public class Villie {

  public static void printHelpMessage() {
    System.out.println("Invalid program arguments. Expected:");
    System.out.println("[--argument] <source.code>");
    System.out.println("where the argument can be --recursive or --stackBased");
    System.out.println("The default abstract machine is stack based.");
  }

  public static void main(String[] args) throws LexerErrorException, ParserErrorException,
          RunTimeErrorException {
    Loader loader;
    if (args.length == 1) {
      loader = new StackBasedLoader(args[0]);
    } else if (args.length == 2) {
      if (!args[0].startsWith("--")) {
        printHelpMessage();
        return;
      } else {
        if (args[0].substring(2).equals("recursive")) {
          loader = new RecursiveLoader(args[1]);
        } else if (args[0].substring(2).equals("stackBased")) {
          loader = new StackBasedLoader(args[1]);
        } else {
          printHelpMessage();
          return;
        }
      }
    } else {
      printHelpMessage();
      return;
    }
    loader.run();
  }

}
