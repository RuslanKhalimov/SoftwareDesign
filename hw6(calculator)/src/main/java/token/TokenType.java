package token;

public enum TokenType {
    LEFT_BRACE,
    RIGHT_BRACE,
    NUMBER,
    PLUS,
    MINUS,
    MUL,
    DIV;

    public int getPriority() {
        if (equals(LEFT_BRACE) || equals(RIGHT_BRACE)) {
            return 0;
        }
        if (equals(PLUS) || equals(MINUS)) {
            return 1;
        }
        if (equals(MUL) || equals(DIV)) {
            return 2;
        }
        return 3;
    }
}
