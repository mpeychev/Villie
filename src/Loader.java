// Author: Momchil Peychev

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lexer.lexeme.Lexeme;
import lexer.Lexer;
import lexer.LexerErrorException;

public class Loader {

  private Logger log = Logger.getLogger(Loader.class.getName());
  private List<FunctionDefinition> functionDefinitions = new LinkedList<>();
  private List<Expression> expressions = new LinkedList<>();

  public Loader(String file) throws LexerErrorException, ParserErrorException {
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line;
      while ((line = br.readLine()) != null) {
        List<Lexeme> lexemes = Lexer.lex(line);
        if (Util.isFunctionDefinition(lexemes)) {
          functionDefinitions.add(new FunctionDefinition(lexemes));
        } else {
          expressions.add(new Expression(lexemes));
        }
      }
    } catch (FileNotFoundException e) {
      log.log(Level.SEVERE, "Source file not found");
      return;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return functionDefinitions.toString() + "\n" + expressions.toString();
  }
}
