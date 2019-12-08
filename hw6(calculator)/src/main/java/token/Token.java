package token;

import visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);

    TokenType getTokenType();

}
