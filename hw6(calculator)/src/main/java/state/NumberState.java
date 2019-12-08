package state;

import token.NumberToken;
import token.Token;
import token.Tokenizer;

public class NumberState extends State {
    public NumberState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
        StringBuilder value = new StringBuilder();
        while (!tokenizer.isEndOfInput() && Character.isDigit(tokenizer.getCurrentCharacter())) {
            value.append(tokenizer.getCurrentCharacter());
            tokenizer.nextCharacter();
        }
        return new NumberToken(Integer.parseInt(value.toString()));
    }

}
