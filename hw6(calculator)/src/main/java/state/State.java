package state;

import token.Token;
import token.Tokenizer;

public abstract class State {
    protected final Tokenizer tokenizer;

    protected State(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public abstract Token createToken();

}
