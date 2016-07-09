// Author: Momchil Peychev

public class Lexeme {
  private String type;
  private Object value;

  public Lexeme(String type) {
    this.type = type;
  }

  public Lexeme(String type, Object value) {
    this.type = type;
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public Object getValue() {
    return value;
  }

  @Override
  public String toString() {
    return type + ((!type.equals("NUM") && !type.equals("ID")) ? "": "(" + value.toString() + ")");
  }
}
