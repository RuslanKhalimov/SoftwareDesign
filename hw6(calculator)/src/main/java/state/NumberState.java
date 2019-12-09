package state;

import token.NumberToken;
import token.Token;
import token.Tokenizer;

public class NumberState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        StringBuilder value = new StringBuilder();
        while (!tokenizer.isEndOfInput() && Character.isDigit(tokenizer.getCurrentCharacter())) {
            value.append(tokenizer.getCurrentCharacter());
            tokenizer.nextCharacter();
        }
        return new NumberToken(Integer.parseInt(value.toString()));
    }

    @Override
    public void setNextState(Tokenizer tokenizer) {
        if (tokenizer.isEndOfInput()) {
            tokenizer.setState(new EndState());
        } else if (tokenizer.isOperation()) {
            tokenizer.setState(new OperationState());
        } else if (tokenizer.isRightBrace()) {
            tokenizer.setState(new RightBraceState());
        } else {
            tokenizer.setState(new ErrorState("Unexpected symbol : " + tokenizer.getCurrentCharacter()));
        }
    }

}
