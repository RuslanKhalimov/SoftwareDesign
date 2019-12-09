package state;

import token.OperationToken;
import token.Token;
import token.TokenType;
import token.Tokenizer;

import java.text.ParseException;

public class OperationState implements State {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        char c = tokenizer.getCurrentCharacter();
        tokenizer.nextCharacter();
        switch (c) {
            case '+':
                return new OperationToken(TokenType.PLUS);
            case '-':
                return new OperationToken(TokenType.MINUS);
            case '*':
                return new OperationToken(TokenType.MUL);
            case '/':
                return new OperationToken(TokenType.DIV);
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void setNextState(Tokenizer tokenizer) throws ParseException {
        if (tokenizer.isEndOfInput()) {
            throw new ParseException("Unexpected end of input", -1);
        } else if (tokenizer.isNumber()) {
            tokenizer.setState(new NumberState());
        } else if (tokenizer.isLeftBrace()) {
            tokenizer.setState(new LeftBraceState());
        } else {
            throw new ParseException("Unexpected character : " + tokenizer.getCurrentCharacter(), tokenizer.getCurIndex());
        }
    }

}
