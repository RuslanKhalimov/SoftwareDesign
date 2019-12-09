package state;

import token.BraceToken;
import token.Token;
import token.TokenType;
import token.Tokenizer;

import java.text.ParseException;

public class RightBraceState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        tokenizer.nextCharacter();
        return new BraceToken(TokenType.RIGHT_BRACE);
    }

    @Override
    public void setNextState(Tokenizer tokenizer) throws ParseException {
        if (tokenizer.isEndOfInput()) {
            tokenizer.setState(new EndState());
        } else if (tokenizer.isOperation()) {
            tokenizer.setState(new OperationState());
        } else if (tokenizer.isRightBrace()) {
            tokenizer.setState(this);
        } else {
            throw new ParseException("Unexpected character : " + tokenizer.getCurrentCharacter(), tokenizer.getCurIndex());
        }
    }

}
