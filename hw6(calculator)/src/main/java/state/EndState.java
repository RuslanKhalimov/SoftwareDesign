package state;

import token.Token;
import token.Tokenizer;

public class EndState implements State {

    @Override
    public Token createToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNextState(Tokenizer tokenizer) {
        throw new UnsupportedOperationException();
    }
}
