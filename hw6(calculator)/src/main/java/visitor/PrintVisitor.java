package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.List;

public class PrintVisitor implements TokenVisitor {
    StringBuilder builder = new StringBuilder();

    public String print(List<Token> tokens) {
        tokens.forEach(token -> token.accept(this));
        String result = builder.toString();
        builder = new StringBuilder();
        return result;
    }

    @Override
    public void visit(NumberToken token) {
        print(token);
    }

    @Override
    public void visit(BraceToken token) {
        print(token);
    }

    @Override
    public void visit(OperationToken token) {
        print(token);
    }

    private void print(Token token) {
        builder.append(token).append(" ");
    }

}
