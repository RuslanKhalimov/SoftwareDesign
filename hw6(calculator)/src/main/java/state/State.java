package state;

import token.Token;
import token.Tokenizer;

public interface State {

    Token createToken(Tokenizer tokenizer);

    void setNextState(Tokenizer tokenizer);

}
