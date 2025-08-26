package expression.exceptions;
public class Lexeme {
    LexemeType type;
    String inputElement;

    public Lexeme(LexemeType type, String inputElement) {
        this.type = type;
        this.inputElement = inputElement;
    }

    public String toString() {
        return "" + this.type + " " + this.inputElement;
    }
}
