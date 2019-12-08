package token;

import visitor.TokenVisitor;

import java.util.Objects;

public class NumberToken implements Token {
    private final int value;

    public NumberToken(int value) {
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.NUMBER;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof NumberToken)) return false;
        NumberToken numberToken = (NumberToken) other;
        return Objects.equals(value, numberToken.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
