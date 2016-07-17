// Author: Momchil Peychev

package interpreter.lexer.lexeme;

public class ValueLexeme <T> extends Lexeme {

  private T value;

  public ValueLexeme(LexemeType type, T value) {
    super(type);
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  @Override
  public String toString() {
    return type.toString() + "(" + value.toString() + ")";
  }
}
