package state;

import token.BraceToken;
import token.Token;
import token.TokenType;
import token.Tokenizer;

public class RightBraceState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        tokenizer.nextCharacter();
        return new BraceToken(TokenType.RIGHT_BRACE);
    }

    @Override
    public void setNextState(Tokenizer tokenizer) {
        if (tokenizer.isEndOfInput()) {
            tokenizer.setState(new EndState());
        } else if (tokenizer.isOperation()) {
            tokenizer.setState(new OperationState());
        } else if (tokenizer.isRightBrace()) {
            tokenizer.setState(this);
        } else {
            tokenizer.setState(new ErrorState("Unexpected character : " + tokenizer.getCurrentCharacter()));
        }
    }

}
