package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;

public interface TokenVisitor {
    void visit(NumberToken token);

    void visit(BraceToken token);

    void visit(OperationToken token);

}
