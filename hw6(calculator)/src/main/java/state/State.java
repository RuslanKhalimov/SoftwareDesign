package state;

import token.Token;
import token.Tokenizer;

import java.text.ParseException;

public interface State {

    Token createToken(Tokenizer tokenizer);

    void setNextState(Tokenizer tokenizer) throws ParseException;

}
