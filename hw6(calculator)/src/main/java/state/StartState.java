package state;

import token.*;

public class StartState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        char c = tokenizer.getCurrentCharacter();
        tokenizer.nextCharacter();
        switch (c) {
            case '(':
                return new BraceToken(TokenType.LEFT_BRACE);
            case ')':
                return new BraceToken(TokenType.RIGHT_BRACE);
            case '+':
                return new OperationToken(TokenType.PLUS);
            case '-':
                return new OperationToken(TokenType.MINUS);
            case '*':
                return new OperationToken(TokenType.MUL);
            case '/':
                return new OperationToken(TokenType.DIV);
            default:
                return null;
        }
    }

    @Override
    public void setNextState(Tokenizer tokenizer) {
        if (tokenizer.isEndOfInput()) {
            tokenizer.setState(new EndState());
        } else if (tokenizer.isNumber()) {
            tokenizer.setState(new NumberState());
        } else if (tokenizer.isOperationOrBrace()) {
            tokenizer.setState(new StartState());
        } else {
            tokenizer.setState(new ErrorState("Unexpected character : " + tokenizer.getCurrentCharacter()));
        }
    }

}
