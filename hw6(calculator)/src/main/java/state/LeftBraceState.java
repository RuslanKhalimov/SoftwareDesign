package state;

import token.BraceToken;
import token.Token;
import token.TokenType;
import token.Tokenizer;

public class LeftBraceState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        tokenizer.nextCharacter();
        return new BraceToken(TokenType.LEFT_BRACE);
    }

    @Override
    public void setNextState(Tokenizer tokenizer) {
        if (tokenizer.isEndOfInput()) {
            tokenizer.setState(new ErrorState("Unexpected end of input"));
        } else if (tokenizer.isLeftBrace()) {
            tokenizer.setState(this);
        } else if (tokenizer.isNumber()) {
            tokenizer.setState(new NumberState());
        } else {
            tokenizer.setState(new ErrorState("Unexpected character : " + tokenizer.getCurrentCharacter()));
        }
    }

}
