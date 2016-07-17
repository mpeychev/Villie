// Author: Momchil Peychev

package interpreter;

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

import interpreter.lexer.lexeme.Lexeme;
import interpreter.lexer.Lexer;
import interpreter.lexer.LexerErrorException;
import interpreter.parser.Expression;
import interpreter.parser.FunctionDefinition;
import interpreter.parser.ParserErrorException;

public class Loader {

  private Logger log = Logger.getLogger(Loader.class.getName());
  private List<FunctionDefinition> functionDefinitions = new LinkedList<>();
  private List<Expression> expressions = new LinkedList<>();
  private Map<String, FunctionDefinition> functionNameToDefinition = new HashMap<>();

  public Loader(String file) throws LexerErrorException, ParserErrorException {
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line;
      while ((line = br.readLine()) != null) {
        List<Lexeme> lexemes = Lexer.lex(line);
        if (Utils.isFunctionDefinition(lexemes)) {
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

    for (FunctionDefinition fd : functionDefinitions) {
      functionNameToDefinition.put(fd.getName(), fd);
    }
  }

  public Map<String, FunctionDefinition> getFunctionNameToDefinition() {
    return functionNameToDefinition;
  }

}
