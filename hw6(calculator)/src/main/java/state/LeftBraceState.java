package state;

import token.BraceToken;
import token.Token;
import token.TokenType;
import token.Tokenizer;

import java.text.ParseException;

public class LeftBraceState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        tokenizer.nextCharacter();
        return new BraceToken(TokenType.LEFT_BRACE);
    }

    @Override
    public void setNextState(Tokenizer tokenizer) throws ParseException {
        if (tokenizer.isEndOfInput()) {
            throw new ParseException("Unexpected end of input", -1);
        } else if (tokenizer.isLeftBrace()) {
            tokenizer.setState(this);
        } else if (tokenizer.isNumber()) {
            tokenizer.setState(new NumberState());
        } else {
            throw new ParseException("Unexpected character : " + tokenizer.getCurrentCharacter(), tokenizer.getCurIndex());
        }
    }

}
