package token;

import visitor.TokenVisitor;

import java.util.Objects;

public class BraceToken implements Token {
    private final TokenType braceType;

    public BraceToken(TokenType braceType) {
        this.braceType = braceType;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public TokenType getTokenType() {
        return braceType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BraceToken)) return false;
        BraceToken braceToken = (BraceToken) other;
        return Objects.equals(braceType, braceToken.braceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(braceType);
    }

    @Override
    public String toString() {
        return braceType.toString();
    }

}
