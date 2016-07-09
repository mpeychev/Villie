// Author: Momchil Peychev

import java.util.List;

public class LexerTest {

  private static void test(String line) throws LexerErrorException {
    List<Lexeme> lexemes = Lexer.lex(line);
    for (Lexeme lexem : lexemes) {
      System.out.print(lexem.toString() + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) throws LexerErrorException {
    test("2+4/2-1*(2/2-1);");
    test("fun fact(n)=if n<=1 then 1 else n*fact(n-1);");
    test("fun Sum(N)=if N=1 then 1 else N+Sum(N-1);");
    test("fun func(x,y)=1+(if x+y<3 then 3 else if x=4 then 0 else 1)+x-y;");
    test("fact(4);");
    test("fact(4+6);");
    test("Sum(10);");
    test("fact(Sum(3));");
    test("func(3,0);");
    test("func(3,1);");
    test("func(4,0-1);");
  }

}
