package state;

import token.*;

import java.text.ParseException;

public class StartState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNextState(Tokenizer tokenizer) throws ParseException {
        if (tokenizer.isEndOfInput()) {
            tokenizer.setState(new EndState());
        } else if (tokenizer.isNumber()) {
            tokenizer.setState(new NumberState());
        } else if (tokenizer.isLeftBrace()) {
            tokenizer.setState(new LeftBraceState());
        } else {
            throw new ParseException("Unexpected character : " + tokenizer.getCurrentCharacter(), tokenizer.getCurIndex());
        }
    }

}
