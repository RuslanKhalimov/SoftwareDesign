package state;

import token.BraceToken;
import token.Token;
import token.TokenType;
import token.Tokenizer;

public class BraceState extends State {
    public BraceState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        char c = tokenizer.getCurrentCharacter();
        tokenizer.nextCharacter();
        switch (c) {
            case '(':
                return new BraceToken(TokenType.LEFT_BRACE);
            case ')':
                return new BraceToken(TokenType.RIGHT_BRACE);
            default:
                throw new IllegalStateException();
        }
    }

}
