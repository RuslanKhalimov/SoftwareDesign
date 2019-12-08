package state;

import token.OperationToken;
import token.Token;
import token.TokenType;
import token.Tokenizer;

public class OperationState extends State {
    public OperationState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public Token createToken() {
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

}
