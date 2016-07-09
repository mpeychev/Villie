// Author: Momchil Peychev

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Villie {

  private static Logger log = Logger.getLogger(Villie.class.getName());

  public static void main(String[] args) {
    if (args.length != 1) {
      log.log(Level.SEVERE, "Expecting a single argument <code> - the program to be interpreted");
      return;
    }
    try {
      BufferedReader br = new BufferedReader(new FileReader(args[0]));
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (FileNotFoundException e) {
      log.log(Level.SEVERE, "File not found");
      return;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
